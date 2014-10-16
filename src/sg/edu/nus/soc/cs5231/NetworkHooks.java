package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;

import de.robv.android.xposed.*;
import static de.robv.android.xposed.XposedBridge.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class NetworkHooks implements IXposedHookLoadPackage {

	// static String [] blackList = { "jp.naver.line.android" };

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// XposedBridge.log(lpparam.packageName + " is loaded...");
		// if( !IsInBlackList(lpparam.processName) )
		// {
		// return;
		// }
		hookServerSocket(lpparam);
	}

	public void hookServerSocket(final LoadPackageParam lpparam) {
		final Class<?> classFinder = findClass("java.net.ServerSocket", lpparam.classLoader);
		hookAllConstructors(classFinder, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				int port = -1;
				if (param.args.length > 0) {
					Object arg1 = param.args[0];

					if (arg1 instanceof Integer) {
						port = (Integer) port;
					}
				}
				XposedBridge.log("AN: " + lpparam.processName + " created a server socket on port " + port + ".");
			}
		});

		findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "accept", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				XposedBridge.log("AN: " + lpparam.processName + " listening on " + thisObj.getLocalSocketAddress() + ":" + thisObj.getLocalPort() + ".");
			}
		});

		findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "close", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				XposedBridge.log("AN: " + lpparam.processName + " stopped listening on " + thisObj.getLocalSocketAddress() + ":" + thisObj.getLocalPort() + ".");
			}
		});
	}

	// private static boolean IsInBlackList(String target) {
	// for (String str : blackList) {
	// if (target.equals(str)) {
	// return true;
	// }
	// }
	// return false;
	// }
}