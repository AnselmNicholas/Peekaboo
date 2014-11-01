package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class NetworkHooks implements IXposedHookLoadPackage {

	// static String [] blackList = { "jp.naver.line.android" };

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// hookSocket(lpparam);
		// if( !IsInBlackList(lpparam.processName) )
		// {
		// return;
		// }
		//

		// if (lpparam.packageName.equals("android"))
		// return;
		//

		hookServerSocket(lpparam);
		hookSocket(lpparam);
		hookDatagramSocket(lpparam);

	}

	public void hookServerSocket(final LoadPackageParam lpparam) {
		final String classname = "java.net.ServerSocket";
		// final Class<?> classFinder = findClass(classname, lpparam.classLoader);
		// hookAllConstructors(classFinder, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		// int port = -1;
		// if (param.args.length > 0) {
		// Object arg1 = param.args[0];
		//
		// if (arg1 instanceof Integer) {
		// port = (Integer) port;
		// }
		// }
		//
		// Logger.Log(lpparam, classname, "constructor", "created a server socket on port " + port + ".");
		// }
		// });

		findAndHookConstructor(classname, lpparam.classLoader, Integer.TYPE, Integer.TYPE, InetAddress.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

				Logger.Log(lpparam, classname, "constructor", "port:" + param.args[0] + " backlog:" + param.args[1] + " localAddress:" + param.args[2]);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "bind", SocketAddress.class, Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {

				Logger.Log(lpparam, classname, "bind", "socketAddress:" + param.args[0] + " backlog:" + param.args[1]);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "accept", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				Logger.Log(lpparam, classname, "accept", "socketAddress:" + thisObj.getLocalSocketAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "close", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.ServerSocket thisObj = ((java.net.ServerSocket) param.thisObject);
				Logger.Log(lpparam, classname, "close", "socketAddress:" + thisObj.getLocalSocketAddress());
			}
		});
	}

	public void hookSocket(final LoadPackageParam lpparam) {
		final String classname = "java.net.Socket";
		// final Class<?> classFinder = findClass(classname, lpparam.classLoader);
		// hookAllConstructors(classFinder, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		// // TODO Anselm Find out what type of socket is created
		// // int port = -1;
		// // if (param.args.length > 0) {
		// // Object arg1 = param.args[0];
		// //
		// // if (arg1 instanceof Integer) {
		// // port = (Integer) port;
		// // }
		// // }
		//
		// Logger.Log(lpparam, classname, "constructor", "created a socket");
		// }
		// });

		// // TODO anselm: find how to hook only one method here
		// hookAllMethods(classFinder, "connect", new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		// java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
		// SocketAddress sa = (SocketAddress) param.args[0];
		// Logger.Log(lpparam, classname, "connect", "open socket to " + sa);
		// }
		// });

		// findAndHookConstructor(classname, lpparam.classLoader, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "");
		// }
		// });
		//
		// findAndHookConstructor(classname, lpparam.classLoader, Proxy.class, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "Proxy:" + param.args[0]);
		// }
		// });
		//
		// findAndHookConstructor(classname, lpparam.classLoader, String.class, Integer.TYPE, InetAddress.class, Integer.TYPE, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "destHost:" + param.args[0] + " destPort:" + param.args[1] + " localAddress:" + param.args[2] + " localPort:" + param.args[3]);
		// }
		// });

		// private void startupSocket(InetAddress dstAddress, int dstPort, InetAddress localAddress, int localPort, boolean streaming)
		findAndHookMethod(classname, lpparam.classLoader, "startupSocket", InetAddress.class, Integer.TYPE, InetAddress.class, Integer.TYPE, Boolean.TYPE, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.Socket thisObj = ((java.net.Socket)
				// param.thisObject);

				Logger.Log(lpparam, classname, "startupSocket", "rAddr:" + param.args[0] + " rPort:" + param.args[1] + "lAddr:" + param.args[2] + " lPort:" + param.args[3] + " streaming:" + param.args[4]);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "connect", SocketAddress.class, Integer.TYPE, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "connect", "addr:" + param.args[0] + " timeout:" + param.args[1]);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "close", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "close", "addr:" + thisObj.getRemoteSocketAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getInputStream", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "getInputStream", "addr:" + thisObj.getRemoteSocketAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "getOutputStream", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "getOutputStream", "addr:" + thisObj.getRemoteSocketAddress());
			}
		});

	}

	public void hookDatagramSocket(final LoadPackageParam lpparam) {
		final String classname = "java.net.DatagramSocket";
		// final Class<?> classFinder = findClass(classname, lpparam.classLoader);
		// hookAllConstructors(classFinder, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		// // TODO Anselm Find out what type of socket is created
		// int port = -1;
		// InetAddress addr = null;
		// if (param.args.length > 0) {
		// Object arg1 = param.args[0];
		//
		// if (arg1 instanceof Integer) {
		// port = (Integer) port;
		// }
		//
		// if (param.args.length > 1) {
		// arg1 = param.args[1];
		//
		// if (arg1 instanceof InetAddress) {
		// addr = (InetAddress) arg1;
		// }
		// }
		//
		// }
		//
		// Logger.Log(lpparam, classname, "constructor", "created a socket." + Arrays.toString(param.args));
		// }
		// });

		// findAndHookConstructor(classname, lpparam.classLoader, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "0");
		// }
		// });

		// findAndHookConstructor(classname, lpparam.classLoader, Integer.TYPE, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "port:" + param.args[0]);
		// }
		// });
		//
		// findAndHookConstructor(classname, lpparam.classLoader, Integer.TYPE, InetAddress.class, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "port:" + param.args[0] + " address:" + param.args[1]);
		// }
		// });
		//
		// findAndHookConstructor(classname, lpparam.classLoader, SocketAddress.class, new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		//
		// Logger.Log(lpparam, classname, "constructor", "socketAddress:" + param.args[0]);
		// }
		// });

		findAndHookMethod(classname, lpparam.classLoader, "createSocket", Integer.TYPE, InetAddress.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.Socket thisObj = ((java.net.Socket)
				// param.thisObject);

				Logger.Log(lpparam, classname, "createSocket", "port:" + param.args[0] + " address:" + param.args[1]);
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "connect", SocketAddress.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "connect", param.args[0].toString());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "disconnect", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				java.net.DatagramSocket thisObj = ((java.net.DatagramSocket) param.thisObject);

				Logger.Log(lpparam, classname, "disconnect", thisObj.getInetAddress().toString());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "receive", DatagramPacket.class, new XC_MethodHook() {
			// @Override
			// protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
			// // java.net.DatagramSocket thisObj = ((java.net.DatagramSocket) param.thisObject);
			//
			// Logger.Log(lpparam, classname, "receive", "receiving " + "port:"+((DatagramPacket) param.args[0]).getPort()+" Address:" + ((DatagramPacket) param.args[0]).getAddress());
			// }

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.DatagramSocket thisObj = ((java.net.DatagramSocket) param.thisObject);

				Logger.Log(lpparam, classname, "receive", "port:" + ((DatagramPacket) param.args[0]).getPort() + " Address:" + ((DatagramPacket) param.args[0]).getAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "send", DatagramPacket.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.DatagramSocket thisObj = ((java.net.DatagramSocket) param.thisObject);

				Logger.Log(lpparam, classname, "send", "port:" + ((DatagramPacket) param.args[0]).getPort() + " Address:" + ((DatagramPacket) param.args[0]).getAddress());
			}
		});

		findAndHookMethod(classname, lpparam.classLoader, "bind", SocketAddress.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// java.net.Socket thisObj = ((java.net.Socket) param.thisObject);

				Logger.Log(lpparam, classname, "bind", "binding " + param.args[0].toString());
			}

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				java.net.DatagramSocket thisObj = ((java.net.DatagramSocket) param.thisObject);

				Logger.Log(lpparam, classname, "bind", "bound " + thisObj.getLocalSocketAddress().toString());
			}
		});

		//
		// findAndHookMethod(classname, lpparam.classLoader, "getOutputStream",
		// new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param) throws
		// Throwable {
		// java.net.Socket thisObj = ((java.net.Socket) param.thisObject);
		//
		// Logger.Log(lpparam, classname, "getOutputStream",
		// "received an outputstream from " + thisObj.getRemoteSocketAddress());
		// }
		// });
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