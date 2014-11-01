package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class GPSHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				hookGPS(lpparam);
		}
			
		private void hookGPS(final LoadPackageParam lpparam)
		{

			final Class<?> classFinder = findClass("android.location.LocationManager", lpparam.classLoader);
						
			hookAllMethods(classFinder,"getLastLocation", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam, "LocationManager", "getLastKnownLocation()", " ");
				}
			});
			
			
			hookAllMethods(classFinder,"requestLocationUpdates", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					
					Logger.Log(lpparam, "LocationManager", "requestLocationUpdates()", " ");
				}
			});
			
		}
		
}