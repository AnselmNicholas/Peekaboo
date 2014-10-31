package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import android.app.PendingIntent;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class SMSHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				hookSMS(lpparam);
		}
			
		private void hookSMS(final LoadPackageParam lpparam)
		{
			findAndHookMethod("android.telephony.SmsManager",lpparam.classLoader, "sendTextMessage",String.class, 
					String.class, String.class,
		            PendingIntent.class, PendingIntent.class, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.telephony.SmsManager", "sendTextMessage()", 
							"To: "+param.args[0]+", From: "+param.args[1] + "Text:" + param.args[2]);
				}
			});
			

			findAndHookMethod("android.telephony.SmsManager",lpparam.classLoader, "getDefault", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.telephony.SmsManager", "getDefault()", "trying to get SMS instance");
				}
			});
			
			

			findAndHookMethod("android.telephony.gsm.SmsManager",lpparam.classLoader, "sendTextMessage",String.class, 
					String.class, String.class,
		            PendingIntent.class, PendingIntent.class, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.telephony.gsm.SmsManager", "sendTextMessage()", 
							"To: "+param.args[0]+", From: "+param.args[1] + "Text:" + param.args[2]);
				}
			});
		}
		
}

