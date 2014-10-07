package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class FileHooks implements IXposedHookLoadPackage {
		static String [] whiteList = { "com.google.android.gms:snet" };
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				XposedBridge.log(lpparam.packageName + " is loaded...");
				if( IsInWhiteList(lpparam.processName) )
				{
					return;
				}
				hookListFiles(lpparam);
				hookRenameFile(lpparam);
				hookDeleteFile(lpparam);
				hookCreateFile(lpparam);
		}
		
		private void hookCreateFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "createNewFile", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " creating new file " + fileName + ".");
				}
			});
		}
		
		private void hookDeleteFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "delete", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " deleting " + fileName + ".");
				}
			});
			findAndHookMethod("java.io.File", lpparam.classLoader, "deleteOnExit", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " deleting " + fileName + ".");
				}
			});
		}
		
		private void hookRenameFile(final LoadPackageParam lpparam) {
			findAndHookMethod("java.io.File", lpparam.classLoader, "renameTo", java.io.File.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String originalName = ((java.io.File)param.thisObject).getAbsolutePath();
					String newName = ((java.io.File)param.args[0]).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " renaming " + originalName + " to " + newName +".");
				}
			});
			
		}

		private void hookListFiles(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "listFiles", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String directory = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " listing files in "+directory+".");
				}
			});
		}
		
		private static boolean IsInWhiteList(String target)
		{
			for(String str : whiteList)
			{
				if(target.equals(str))
				{
					return true;
				}
			}
			return false;
		}
}