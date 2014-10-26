package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;

import java.net.SocketAddress;

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
		hookSocket(lpparam);
	}

	public void hookServerSocket(final LoadPackageParam lpparam) {
		final String classname = "java.net.ServerSocket";
		final Class<?> classFinder = findClass(classname, lpparam.classLoader);
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

				Logger.Log(lpparam.processName, classname, "constructor", "created a server socket on port " + port + ".");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "accept", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				Logger.Log(lpparam.processName, classname, "accept", "listening on " + thisObj.getLocalSocketAddress() + " " + thisObj.getLocalPort() + ".");
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "close", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				Logger.Log(lpparam.processName, classname, "close", "stopped listening on " + thisObj.getLocalSocketAddress() + " " + thisObj.getLocalPort() + ".");
			}
		});
	}

	public void hookSocket(final LoadPackageParam lpparam) {
		final String classname = "java.net.Socket";
		final Class<?> classFinder = findClass(classname, lpparam.classLoader);
		hookAllConstructors(classFinder, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Anselm Find out what type of socket is created
				// int port = -1;
				// if (param.args.length > 0) {
				// Object arg1 = param.args[0];
				//
				// if (arg1 instanceof Integer) {
				// port = (Integer) port;
				// }
				// }

				Logger.Log(lpparam.processName, classname, "constructor", "created a socket");
			}
		});

		// TODO anselm: find how to hook only one method here
		hookAllMethods(classFinder, "connect", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
				SocketAddress sa = (SocketAddress) param.args[0];
				Logger.Log(lpparam.processName, classname, "connect", "open socket to " + sa);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "close", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam.processName, classname, "close", "closed socket to " + thisObj.getRemoteSocketAddress());
			}
		});
		
		findAndHookMethod(classname, lpparam.classLoader, "getInputStream", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam.processName, classname, "getInputStream", "received an inputstream from " + thisObj.getRemoteSocketAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getOutputStream", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam.processName, classname, "getOutputStream", "received an outputstream from " + thisObj.getRemoteSocketAddress());
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