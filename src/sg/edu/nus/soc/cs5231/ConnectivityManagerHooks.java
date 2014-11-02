package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ConnectivityManagerHooks implements IXposedHookLoadPackage {

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		hookConnectivityMngr(lpparam);
	}

	private void hookConnectivityMngr(final LoadPackageParam lpparam) {
		final String classname = "android.net.ConnectivityManager";

		findAndHookMethod(classname, lpparam.classLoader, "getNetworkTypeName", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getNetworkTypeName", "" + param.getResult());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "isNetworkTypeMobile", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "isNetworkTypeMobile(int)", "" + param.args[0] + " " + param.getResult());

			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "setNetworkPreference", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "setNetworkPreference(int)", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getNetworkPreference", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getNetworkPreference()", "" + param.getResult());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getActiveNetworkInfo", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getActiveNetworkInfo", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getActiveNetworkInfoForUid", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getActiveNetworkInfoForUid", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getNetworkInfo", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getNetworkInfo", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getAllNetworkInfo", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getAllNetworkInfo()", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getActiveLinkProperties", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getActiveLinkProperties()", "");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getLinkProperties", Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, classname, "getLinkProperties(int)", "");
			}
		});

	}

}