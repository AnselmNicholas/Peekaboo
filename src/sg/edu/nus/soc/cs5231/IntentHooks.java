package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class IntentHooks implements IXposedHookLoadPackage {
	static boolean enableTmiMethods = true; //tmi = too much info. False = disable
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookIntent(lpparam); //android.content.Intent
	}

	private void hookIntent(final LoadPackageParam lpparam) {
		final String targetClass = "android.content.Intent";
		final String methodConstructor = "Constructor";
		final String methodGetAction = "getAction";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"Intent object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});
		
		//getAction()
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetAction, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				int sizeLimit = 50;
				String result;
				
				if(param != null && param.getResult() != null) {
					result = param.getResult().toString();
				} else {
					result = "Empty Result";
				}
				
				if(result.length() > sizeLimit) result = result.substring(0, sizeLimit);
				
				Logger.Log(lpparam, targetClass, methodGetAction, 
					" Result toString() = " + result);
			}
		});
	}
}