package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ContactHooks implements IXposedHookLoadPackage {
	static boolean enableTmiMethods = false; //tmi = too much info. False = disable
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookContactsContract(lpparam); //android.provider.ContactsContract
		hookContactsContractContact(lpparam); //android.provider.ContactsContract.Contact
	}

	private void hookContactsContract(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.ContactsContract";
		final String methodConstructor = "Constructor";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"ContactsContract object initialized. "+
					"appInfo = "+lpparam.appInfo);
			}
		});		
	}
	
	private void hookContactsContractContact(final LoadPackageParam lpparam) {
		final String targetClass = "android.provider.ContactsContract.Contacts";
		final String methodConstructor = "Constructor";
		final String methodGetLookupUri = "getLookupUri";
		final String methodLookupContact = "lookupContact";
		final String methodOpenContactPhotoInputStream = "openContactPhotoInputStream";
		
		final Class<?> classFinder = findClass(targetClass, lpparam.classLoader);
		
		//Constructors
		hookAllConstructors(classFinder, new XC_MethodHook() { 
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, targetClass, methodConstructor, 
					"ContactsContract.Contacts object initialized. "+
					"appInfo = "+lpparam.appInfo+" "+
					"Contacts.DISPLAY_NAME_PRIMARY = "+Contacts.DISPLAY_NAME_PRIMARY);
			}
		});	
		
		//Contacts.getLookupUri(ContentResolver resolver, Uri contactUri)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLookupUri, ContentResolver.class, Uri.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodGetLookupUri, sb.toString());
			}
			
			//protected void afterHookedMethod() throws Throwable {
				//Do nothing for now
				//Supposed to change return object
			//}
		});
		
		//Contacts.getLookupUri(long contactid, String lookupKey)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLookupUri, long.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodGetLookupUri, sb.toString());
			}
		});
		
		//Contacts.lookupContact(ContentResolver resolver, Uri lookupUri)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodLookupContact, ContentResolver.class, Uri.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodLookupContact, sb.toString());
			}
		});
		
		//Contacts.openContactPhotoInputStream(ContentResolver cr, Uri contactUri, boolean preferHighres)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodOpenContactPhotoInputStream, ContentResolver.class, Uri.class, boolean.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);

				Logger.Log(lpparam, targetClass, methodOpenContactPhotoInputStream, sb.toString());
			}
		});
		
		//Contacts.openContactPhotoInputStream(ContentResolver cr, Uri contactUri)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodOpenContactPhotoInputStream, ContentResolver.class, Uri.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<param.args.length; i++) sb.append(" args["+i+"] = "+param.args[i]);
				
				Logger.Log(lpparam, targetClass, methodOpenContactPhotoInputStream, sb.toString());
			}
		});
	}
	
}
