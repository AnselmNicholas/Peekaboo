package sg.edu.nus.soc.cs5231;

//NOTE: THIS THING DOESN'T WORK

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ContentContextHooks implements IXposedHookLoadPackage {
	static boolean enableTmiMethods = false; //tmi = too much info. False = disable
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookContentContext(lpparam); //android.content.Context
	}

	private void hookContentContext(final LoadPackageParam lpparam) {
		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		final String targetClass = "android.content.Context";
		final String classShorthand = "content.Context";
		
		final String methodConstructor = "Constructor";
		final String methodGetSystemService = "getSystemService";
		final String methodGetSharedPreferences = "getSharedPreferences";
		final String methodOpenFileOutput = "openFileOutput";	
		
		final String methodGetConnectivityService = "CONNECTIVITY_SERVICE";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					classShorthand + " object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});

		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetConnectivityService, String.class, 
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetSystemService, "HELLO WORLD!!!");
			}
		});
		
		//Context.getSystemService(String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetSystemService, String.class, 
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetSystemService, 
						sb.toString() + " Result Class Name = " + param.getResult().getClass().getName());
			}
		});
		
		//Context.getSharedPreferences(String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetSharedPreferences, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetSharedPreferences, 
						sb.toString() + " Result Class Name = " + param.getResult().getClass().getName());
			}
		});
		
		//Context.openFileOutput(String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodOpenFileOutput, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetSharedPreferences, 
						sb.toString() + " Result Class Name = " + param.getResult().getClass().getName());
			}
		});
	}
}
