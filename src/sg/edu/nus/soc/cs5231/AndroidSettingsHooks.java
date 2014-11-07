package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import android.content.ContentResolver;
import android.content.res.Configuration;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class AndroidSettingsHooks implements IXposedHookLoadPackage {
	static boolean enableTmiMethods = true; //tmi = too much info. False = disable
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookSettings(lpparam); //android.provider.Settings
		hookSettingsSecure(lpparam); //android.provider.Settings.Secure
		hookSettingsSystem(lpparam); //android.provider.Settings.System
	}

	private void hookSettings(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.Settings";
		final String methodConstructor = "Constructor";

		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
						"Settings object initialized. "+ "appInfo = "+lpparam.appInfo);
			}
		});		
	}

	private void hookSettingsSecure(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.Settings.Secure";
		final String targetClassShorthand = "Settings.Secure";
		final String methodConstructor = "Constructor";

		//Getters
		final String methodGetFloat = "getFloat"; 	//(ContentResolver cr, String name, float def)
													//(ContentResolver cr, String name)
		final String methodGetInt = "getInt";		//(ContentResolver cr, String name, int def)
													//(ContentResolver cr, String name)
		final String methodGetLong = "getLong";		//(ContentResolver cr, String name, long def)
													//(ContentResolver cr, String name)
		final String methodGetString = "getString";	//(ContentResolver cr, String name)
		final String methodGetUri = "getUriFor"; 	//(String name)

		//Setters
		final String methodPutFloat = "putFloat";	//(ContentResolver cr, String name, float value)
		final String methodPutInt = "putInt";		//(ContentResolver cr, String name, int value)
		final String methodPutLong = "putLong";		//(ContentResolver cr, String name, long value)
		final String methodPutString = "putString";	//(ContentResolver cr, String name, String value)

		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);

		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
						targetClassShorthand + " object initialized. "+
						"appInfo = "+lpparam.appInfo);
			}
		});

		//Settings.Secure.getFloat(ContentSolver, String, float)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetFloat, ContentResolver.class, String.class, float.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetFloat, sb.toString());
			}
		});

		//Settings.Secure.getFloat(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetFloat, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetFloat, sb.toString());
			}
		});

		//Settings.Secure.getInt(ContentSolver, String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetInt, ContentResolver.class, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetInt, sb.toString());
			}
		});

		//Settings.Secure.getInt(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetInt, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetInt, sb.toString());
			}
		});

		//Settings.Secure.getLong(ContentSolver, String, long)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLong, ContentResolver.class, String.class, long.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetLong, sb.toString());
			}
		});

		//Settings.Secure.getLong(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLong, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetLong, sb.toString());
			}
		});
		
		//Settings.Secure.getString(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetString, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetString, sb.toString());
			}
		});
		
		//Settings.Secure.getUri(String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetUri, String.class,
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetUri, sb.toString());
			}
		});
		
		//Settings.Secure.putFloat(ContentSolver, String, float)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutFloat, ContentResolver.class, String.class, float.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodPutFloat, sb.toString());
			}
		});

		//Settings.Secure.putInt(ContentSolver, String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutInt, ContentResolver.class, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutInt, sb.toString());
			}
		});
		
		//Settings.Secure.putLong(ContentSolver, String, long)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutLong, ContentResolver.class, String.class, long.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutLong, sb.toString());
			}
		});

		//Settings.Secure.putString(ContentSolver, String, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutString, ContentResolver.class, String.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutString, sb.toString());
			}
		});
	}
	
	private void hookSettingsSystem(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.Settings.System";
		final String targetClassShorthand = "Settings.System";
		final String methodConstructor = "Constructor";

		//Getters
		final String methodGetConfiguration = "getConfiguration"; //(ContentResolver, Configuration)
		
		final String methodGetFloat = "getFloat"; 	//(ContentResolver cr, String name, float def)
													//(ContentResolver cr, String name)
		final String methodGetInt = "getInt";		//(ContentResolver cr, String name, int def)
													//(ContentResolver cr, String name)
		final String methodGetLong = "getLong";		//(ContentResolver cr, String name, long def)
													//(ContentResolver cr, String name)
		final String methodGetString = "getString";	//(ContentResolver cr, String name)
		final String methodGetUri = "getUriFor"; 	//(String name)

		//Setters
		final String methodPutConfiguration ="putConfiguration"; //(ContentResolver, Configuration)
		
		final String methodPutFloat = "putFloat";	//(ContentResolver cr, String name, float value)
		final String methodPutInt = "putInt";		//(ContentResolver cr, String name, int value)
		final String methodPutLong = "putLong";		//(ContentResolver cr, String name, long value)
		final String methodPutString = "putString";	//(ContentResolver cr, String name, String value)

		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);

		//Put your TMI methods here:
		if(!enableTmiMethods) return;
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
						targetClassShorthand + " object initialized. "+
						"appInfo = "+lpparam.appInfo);
			}
		});
		
		//Settings.Secure.getConfiguration(ContentSolver, Configuration)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetConfiguration, ContentResolver.class, Configuration.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetConfiguration, sb.toString());
			}
		});

		//Settings.Secure.getFloat(ContentSolver, String, float)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetFloat, ContentResolver.class, String.class, float.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetFloat, sb.toString());
			}
		});

		//Settings.Secure.getFloat(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetFloat, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetFloat, sb.toString());
			}
		});

		//Settings.Secure.getInt(ContentSolver, String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetInt, ContentResolver.class, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetInt, sb.toString());
			}
		});

		//Settings.Secure.getInt(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetInt, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetInt, sb.toString());
			}
		});

		//Settings.Secure.getLong(ContentSolver, String, long)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLong, ContentResolver.class, String.class, long.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetLong, sb.toString());
			}
		});

		//Settings.Secure.getLong(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLong, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetLong, sb.toString());
			}
		});
		
		//Settings.Secure.getString(ContentSolver, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetString, ContentResolver.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetString, sb.toString());
			}
		});
		
		//Settings.Secure.getUri(String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetUri, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetUri, sb.toString());
			}
		});
		
		//Settings.Secure.putConfiguration(ContentSolver, Configuration)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutConfiguration, ContentResolver.class, Configuration.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutConfiguration, sb.toString());
			}
		});
		
		//Settings.Secure.putFloat(ContentSolver, String, float)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutFloat, ContentResolver.class, String.class, float.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodPutFloat, sb.toString());
			}
		});

		//Settings.Secure.putInt(ContentSolver, String, int)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutInt, ContentResolver.class, String.class, int.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutInt, sb.toString());
			}
		});
		
		//Settings.Secure.putLong(ContentSolver, String, long)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutLong, ContentResolver.class, String.class, long.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodPutLong, sb.toString());
			}
		});

		//Settings.Secure.putString(ContentSolver, String, String)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodPutString, ContentResolver.class, String.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodPutString, sb.toString());
			}
		});
	}
}
