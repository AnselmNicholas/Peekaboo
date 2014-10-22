package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class CallLogHooks implements IXposedHookLoadPackage {
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodConstructor+"] "+
						"CallLog object initialized. "+
						"appInfo = "+lpparam.appInfo);*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodConstructor) + 
						"CallLog object initialized. "+
						"appInfo = "+lpparam.appInfo);
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodConstructor+"] "+
						"CallLog.Calls object initialized. "+
						"appInfo = "+lpparam.appInfo);*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodConstructor) + 
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodGetLastOutgoingCall+"] "+
						"getLastOutgoingCall(Context) invoked.");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodGetLastOutgoingCall) + 
						"getLastOutgoingCall(Context) invoked.");
			}
		});
	}
}