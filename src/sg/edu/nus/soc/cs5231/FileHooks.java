package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class FileHooks implements IXposedHookLoadPackage {
		static String [] whiteList = { 	"com.google.android.gms:snet",
										"com.android.vending",
									 };
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				//XposedBridge.log(lpparam.packageName + " is loaded...");
				if( IsInWhiteList(lpparam.processName) )
				{
					return;
				}
				hookListFiles(lpparam);
				hookRenameFile(lpparam);
				hookDeleteFile(lpparam);
				hookCreateFile(lpparam);
				hookReadFile(lpparam);
				hookWriteFile(lpparam);
		}
			
		private void hookReadFile(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("java.io.FileInputStream", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object arg1 = param.args[0];
					String fileName = "unknown";
					if(arg1 instanceof java.io.File)
					{
						java.io.File file = (java.io.File)arg1;
						fileName = file.getAbsolutePath();
					}
					if(arg1 instanceof java.io.FileDescriptor)
					{
						java.io.FileDescriptor fd = (java.io.FileDescriptor)arg1;
						fileName = fd.toString();
					}
					if(arg1 instanceof String)
					{
						fileName = (String)arg1;
					}
					XposedBridge.log("Jason: " + lpparam.processName + " trying to read file " + fileName + ".");
				}
			});
		}
		
		private void hookWriteFile(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("java.io.FileOutputStream", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object arg1 = param.args[0];
					String fileName = "unknown";
					if(arg1 instanceof java.io.File)
					{
						java.io.File file = (java.io.File)arg1;
						fileName = file.getAbsolutePath();
					}
					if(arg1 instanceof java.io.FileDescriptor)
					{
						java.io.FileDescriptor fd = (java.io.FileDescriptor)arg1;
						fileName = fd.toString();
					}
					if(arg1 instanceof String)
					{
						fileName = (String)arg1;
					}
					XposedBridge.log("Jason: " + lpparam.processName + " trying to write file " + fileName + ".");
				}
			});
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