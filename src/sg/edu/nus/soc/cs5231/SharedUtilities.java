package sg.edu.nus.soc.cs5231;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class SharedUtilities {
	public static String getTimeNow() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return df.format(cal.getTime());
	}
	
	public static String generatePreamble(String procName, String className, String funcName) {
		String output = "[" + getTimeNow() + "][" + procName + "][" + 
							className + "][" + funcName + "] ";
		
		return output;
	}
}
