package sg.edu.nus.soc.cs5231;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Logger {

	public static void Log(final LoadPackageParam lpparam, String class_name, String method_name, String message) {
		if (!isLoggingEnabled(lpparam.processName)) {
			return;
		}
		Log(lpparam.packageName + "[" + lpparam.processName + "]", class_name, method_name, message);
	}

	private static void Log(String process_name, String class_name, String method_name, String message) {
		if (!isLoggingEnabled(process_name)) {
			return;
		}
		String formatted_string = String.format("[%s] %s - %s - %s : %s", SharedUtilities.getTimeNow(), process_name, class_name, method_name, message);
		XposedBridge.log(formatted_string);
	}

	private static boolean isLoggingEnabled(String process_name) {

		return true;

	}

}
