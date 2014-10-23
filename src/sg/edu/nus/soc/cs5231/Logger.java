package sg.edu.nus.soc.cs5231;

import de.robv.android.xposed.XposedBridge;

public class Logger {
	public static void Log(String process_name, String class_name, String method_name, String message){
		if(!isLoggingEnabled(process_name)){
			return;
		}
		String formatted_string = String.format("[%d] %s - %s - %s : %s", System.currentTimeMillis(), process_name, class_name, method_name, message);
		XposedBridge.log(formatted_string);
	}
	private static boolean isLoggingEnabled(String process_name){
		
		return true;

	}
	
	
}
