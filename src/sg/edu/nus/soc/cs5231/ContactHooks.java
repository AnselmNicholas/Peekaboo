package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.*;
import static de.robv.android.xposed.XposedBridge.*;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ContactHooks implements IXposedHookLoadPackage {
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodConstructor+"] "+
						"ContactsContract object initialized. "+
						"appInfo = "+lpparam.appInfo);*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodConstructor) + 
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodConstructor+"] "+
						"ContactsContract.Contacts object initialized. "+
						"appInfo = "+lpparam.appInfo+" "+
						"Contacts.DISPLAY_NAME_PRIMARY = "+Contacts.DISPLAY_NAME_PRIMARY);*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodConstructor) + 
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
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodGetLookupUri+"] "+
						"getLookUpUri(CR, URI) invoked.");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodGetLookupUri) + 
						"getLookUpUri(CR, URI) invoked.");
			}
		});
		
		//Contacts.getLookupUri(long contactid, String lookupKey)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodGetLookupUri, long.class, String.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodGetLookupUri+"] "+
						"getLookUpUri(long, String) invoked.");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodGetLookupUri) + 
						"getLookUpUri(long, String) invoked.");
			}
		});
		
		//Contacts.lookupContact(ContentResolver resolver, Uri lookupUri)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodLookupContact, ContentResolver.class, Uri.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodLookupContact+"] "+
						"lookupContact(CR, URI) invoked.");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodLookupContact) + 
						"lookupContact(CR, URI) invoked.");
			}
		});
		
		//Contacts.openContactPhotoInputStream(ContentResolver cr, Uri contactUri, boolean preferHighres)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodOpenContactPhotoInputStream, ContentResolver.class, Uri.class, boolean.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodOpenContactPhotoInputStream+"] "+
						"openContactPhotoInputStream(CR, URI, boolean) invoked.");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodOpenContactPhotoInputStream) + 
						"openContactPhotoInputStream(CR, URI, boolean) invoked.");
			}
		});
		
		//Contacts.openContactPhotoInputStream(ContentResolver cr, Uri contactUri)
		findAndHookMethod(targetClass, lpparam.classLoader, 
				methodOpenContactPhotoInputStream, ContentResolver.class, Uri.class, 
				new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				/*XposedBridge.log("["+SharedUtilities.getTimeNow()+"]"+
						"["+lpparam.processName+"]"+
						"["+targetClass+"]"+
						"["+methodOpenContactPhotoInputStream+"] "+
						"openContactPhotoInputStream(CR, URI) invoked (for thumbnails).");*/
				XposedBridge.log(SharedUtilities.generatePreamble(
						lpparam.processName, targetClass, methodOpenContactPhotoInputStream) + 
						"openContactPhotoInputStream(CR, URI) invoked (for thumbnails).");
			}
		});
	}
	
}
