package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class MediaRecorderHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
			if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
			{
				return;
			}
				hookMediaRecorder(lpparam);
		}
			
		private void hookMediaRecorder(final LoadPackageParam lpparam)
		{
			findAndHookMethod("android.media.MediaRecorder",lpparam.classLoader, "start", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Xinlei: " + lpparam.processName + " trying to start media recorder.");
				}
			});
		}
		
}

