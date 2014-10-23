package sg.edu.nus.soc.cs5231;

import de.robv.android.xposed.XposedBridge;

public class Logger {
	public static void Log(String process_name, String class_name, String method_name, String message){
		String formatted_string = String.format("[%l] %s - %s - %s : %s", System.currentTimeMillis(), process_name, method_name, message);
		XposedBridge.log(formatted_string);
	}
}
