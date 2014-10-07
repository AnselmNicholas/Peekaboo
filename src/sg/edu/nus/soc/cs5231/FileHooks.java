package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class FileHooks implements IXposedHookLoadPackage {
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				XposedBridge.log(lpparam.packageName + " is loaded...");
				hookListFiles(lpparam);
		}
		
		private void hookListFiles(final LoadPackageParam lpparam)
		{
			String [] whiteList = { "com.google.android.gms:snet" };
			if( IsInWhiteList(whiteList,lpparam.processName) )
			{
				return;
			}
			findAndHookMethod("java.io.File", lpparam.classLoader, "listFiles", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String directory = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " listing files in "+directory+".");
				}
			});
		}
		
		private static boolean IsInWhiteList(String [] whiteList, String target)
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