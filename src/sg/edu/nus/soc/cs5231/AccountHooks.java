package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedBridge.hookAllMethods;
import static de.robv.android.xposed.XposedHelpers.*;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class AccountHooks implements IXposedHookLoadPackage {
	
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
				hookAccount(lpparam);
				
				
		}
			
		private void hookAccount(final LoadPackageParam lpparam)
		{

			final Class<?> classFinder = findClass("android.accounts.AccountManager", lpparam.classLoader);
				
			hookAllMethods(classFinder,"getAccounts", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "AccountManager", "getAccounts()", "trying to get account info.");
				}
			});
		}
		
}