package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class CameraHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
			if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
			{
				return;
			}
				hookCamera(lpparam);
		}
			
		private void hookCamera(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("android.hardware.Camera", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Xinlei: " + lpparam.processName + " trying to access camera.");
				}
			});
		}
		
}