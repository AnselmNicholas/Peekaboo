package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class CallLogHooks implements IXposedHookLoadPackage {
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookCallLog(lpparam); //android.provider.CallLog
		hookCallLogCalls(lpparam); //android.provider.CallLog.Calls
	}

	private void hookCallLog(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.CallLog";
		final String methodConstructor = "Constructor";

		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);

		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"CallLog object initialized. "+ "appInfo = "+lpparam.appInfo);
			}
		});		
	}

	private void hookCallLogCalls(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.CallLog.Calls";
		final String methodConstructor = "Constructor";
		final String methodGetLastOutgoingCall = "getLastOutgoingCall";

		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);

		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"CallLog.Calls object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});

		//Calls.getLastOutgoingCall(Context context)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLastOutgoingCall, Context.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetLastOutgoingCall, sb.toString());
			}
		});
	}
}
