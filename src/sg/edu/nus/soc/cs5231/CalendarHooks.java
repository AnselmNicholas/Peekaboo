package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import android.net.Uri;
import android.os.CancellationSignal;
import android.provider.CalendarContract.Calendars;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class CalendarHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				hookCalendar(lpparam);
		}
			
		private void hookCalendar(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("android.content.ContentResolver", lpparam.classLoader);
			XposedBridge.hookMethod(findMethodBestMatch(classFinder,"query", Uri.class, String[].class, String.class, String[].class, 
                    String.class, CancellationSignal.class ), 
                    new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object uri = param.args[0];
					if(uri.equals(Calendars.CONTENT_URI))
						Logger.Log(lpparam, "ContentResolver", "query()", " "+uri);
				}
			});
		}
		
}