package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import android.app.PendingIntent;
import android.location.Criteria;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class LocationHooks implements IXposedHookLoadPackage {
	static boolean enableTmiMethods = false; //tmi = too much info. False = disable
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		hookLocation(lpparam); //android.location.Location
		hookLocationManager(lpparam); //android.location.LocationManager
		hookGeocoder(lpparam); //android.location.Geocoder
	}

	private void hookLocation(final LoadPackageParam lpparam) {
		final String targetClass = "android.location.Location";
		final String methodConstructor = "Constructor";
		final String methodGetLon = "getLongitude";
		final String methodGetLat = "getLatitude";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"Location object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});
		
		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//getLongitude()
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLon, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodGetLon, 
					" Result toString() = " +param.getResult().toString());
			}
		});
		
		//getLatitude()
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLat, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodGetLat, 
					" Result toString() = " +param.getResult().toString());
			}
		});
	}
	
	private void hookLocationManager(final LoadPackageParam lpparam) {
		final String targetClass = "android.location.LocationManager";
		final String methodConstructor = "Constructor";
		final String methodRequestLocationUpdates = "_requestLocationUpdates";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"LocationManager object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});
		
		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//_requestLocationUpdates(String provider, Criteria criteria,
        //	long minTime, float minDistance, boolean singleShot, PendingIntent intent)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodRequestLocationUpdates, String.class, Criteria.class, long.class, 
				float.class, boolean.class, PendingIntent.class,
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodRequestLocationUpdates, sb.toString());
			}
		});
	}
	
	private void hookGeocoder(final LoadPackageParam lpparam) {
		final String targetClass = "android.location.Geocoder";
		final String methodConstructor = "Constructor";
		final String methodGetFromLocation = "getFromLocation";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"Geocoder object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});
				
		//getFromLocation(double latitude, double longitude, int maxResults)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetFromLocation, double.class, double.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetFromLocation, 
					sb.toString() + ", Result toString() = " + param.getResult().toString());
			}
		});
	}
}