package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getStaticObjectField;

import java.net.URI;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class FileHooks implements IXposedHookLoadPackage {
//		static String [] blackList = { 	"com.google.android.gms:snet",
//										"com.android.vending",
//										"android",
//									 };
		static String [] blackList = { 	"jp.naver.line.android"	};
		
		public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
//				XposedBridge.log(lpparam.packageName + " is loaded...");
				if( !IsInBlackList(lpparam.processName) )
				{
					return;
				}
				Context context = getContext();
				if(context == null)
				{
					XposedBridge.log("CONTEXT NULL");
				}
				try {
					context.createPackageContext(CS5231Settings.class.getPackage().getName(), Context.CONTEXT_IGNORE_SECURITY);
				} catch (NameNotFoundException e) {
					XposedBridge.log("NAME NOT FOUND");
				}
				ProcessSettingDBHelper db = new ProcessSettingDBHelper(context);
				XposedBridge.log("Got db");
				
				hookListFiles(lpparam);
				hookRenameFile(lpparam);
				hookDeleteFile(lpparam);
				hookCreateFile(lpparam);
				hookReadFile(lpparam);
				hookWriteFile(lpparam);
				hookOpenFile(lpparam);
				hookOSEnvironment(lpparam);
		}
		
		private void hookOSEnvironment(final LoadPackageParam lpparam)
		{
			//Data Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getDataDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getDataDirectory()", "trying to get the user's data directory");
				}
			});
			//Download/Cache Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getDownloadCacheDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getDownloadCacheDirectory()", "trying to get the download/cache content directory");
				}
			});
			//External Storage Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStorageDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getExternalStorageDirectory()", "trying to get primary external storage directory");
				}
			});
			//External Storage Directory w/ Type
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStoragePublicDirectory", String.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getExternalStoragePublicDirectory()", " trying to get primary external storage directory that stores "+param.args[0]);
				}
			});
			//External Storage State
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStorageState", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getExternalStorageState()", "trying to get primary external storage directory's state");
				}
			});
			//Root Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getRootDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Logger.Log(lpparam.processName, "android.os.Environment", "getRootDirectory()", "trying to get the root directory");
				}
			});
			
		}
		
		private void hookOpenFile(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("java.io.File", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object arg1 = param.args[0];
					String fileName = "unknown";
					//XposedBridge.log("DEBUG: param.args.length = " + param.args.length);
					if(param.args.length == 1)
					{
						if(arg1 instanceof URI)
						{
							URI uri = (URI)arg1;
							fileName = uri.getRawPath();
						}
						if(arg1 instanceof String)
						{
							fileName = (String)arg1;
						}
					}
					else if(param.args.length == 2)
					{
						if(arg1 instanceof String)
						{
							fileName = (String)arg1+"/"+param.args[1];
						}
						if(arg1 instanceof java.io.File)
						{
							java.io.File file = (java.io.File)arg1;
							fileName = file.getAbsolutePath();
						}
						
					}
					Logger.Log(lpparam.processName, "java.io.File", "File()", "trying to open file " + fileName);
				}
			});
		}
			
		private void hookReadFile(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("java.io.FileInputStream", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object arg1 = param.args[0];
					String fileName = "unknown";
					if(arg1 instanceof java.io.File)
					{
						java.io.File file = (java.io.File)arg1;
						fileName = file.getAbsolutePath();
					}
					if(arg1 instanceof java.io.FileDescriptor)
					{
						java.io.FileDescriptor fd = (java.io.FileDescriptor)arg1;
						fileName = fd.toString();
					}
					if(arg1 instanceof String)
					{
						fileName = (String)arg1;
					}
					Logger.Log(lpparam.processName, "java.io.FileInputStream", "FileInputStream()", "trying to read file " + fileName);
				}
			});
		}
		
		private void hookWriteFile(final LoadPackageParam lpparam)
		{
			final Class<?> classFinder = findClass("java.io.FileOutputStream", lpparam.classLoader);
			hookAllConstructors(classFinder, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					Object arg1 = param.args[0];
					String fileName = "unknown";
					if(arg1 instanceof java.io.File)
					{
						java.io.File file = (java.io.File)arg1;
						fileName = file.getAbsolutePath();
					}
					if(arg1 instanceof java.io.FileDescriptor)
					{
						java.io.FileDescriptor fd = (java.io.FileDescriptor)arg1;
						fileName = fd.toString();
					}
					if(arg1 instanceof String)
					{
						fileName = (String)arg1;
					}
					Logger.Log(lpparam.processName, "java.io.FileOutputStream", "FileOutputStream()", "trying to write file " + fileName);
				}
			});
		}
		
		private void hookCreateFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "createNewFile", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					Logger.Log(lpparam.processName, "java.io.File", "createNewFile()", "creating new file " + fileName);
				}
			});
		}
		
		private void hookDeleteFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "delete", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					Logger.Log(lpparam.processName, "java.io.File", "delete()", "deleting " + fileName);
				}
			});
			findAndHookMethod("java.io.File", lpparam.classLoader, "deleteOnExit", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					Logger.Log(lpparam.processName, "java.io.File", "deleteOnExit()", "deleting " + fileName);
				}
			});
		}
		
		private void hookRenameFile(final LoadPackageParam lpparam) {
			findAndHookMethod("java.io.File", lpparam.classLoader, "renameTo", java.io.File.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String originalName = ((java.io.File)param.thisObject).getAbsolutePath();
					String newName = ((java.io.File)param.args[0]).getAbsolutePath();
					Logger.Log(lpparam.processName, "java.io.File", "renameTo()",  " renaming " + originalName + " to " + newName);
				}
			});
			
		}

		private void hookListFiles(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "listFiles", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String directory = ((java.io.File)param.thisObject).getAbsolutePath();
					Logger.Log(lpparam.processName, "java.io.File", "listFiles()",  "listing files in "+directory);
				}
			});
		}
		
		private static boolean IsInBlackList(String target)
		{
			for(String str : blackList)
			{
				if(target.equals(str))
				{
					return true;
				}
			}
			return false;
		}
		
		 private Context getContext(){
	         // Try to get a context in one way or another from system
	         Context context;

	         // Seems to work for 4.4
	         Log.i("getContext", "Trying to get context from AndroidAppHelper");
	         context = AndroidAppHelper.currentApplication();

	         // Seems to work for 4.2
	         if (context == null) {
	             Log.i("getContext", "Trying to get context from mSystemContext");
	             Object systemContext = getStaticObjectField(findClass("android.app.ActivityThread", null), "mSystemContext");
	             if (systemContext != null) {
	                 context = (Context) systemContext;
	             }
	         }

	         // Seems to work for 4.1 and 4.0
	         if (context == null) {
	             Log.i("getContext", "Trying to get activityThread from systemMain");
	             Object activityThread = callStaticMethod(findClass("android.app.ActivityThread", null), "systemMain");
	             if (activityThread != null){
	                 Log.i("getContext", "Trying to get context from getSystemContext");
	                 context = (Context) callMethod(activityThread, "getSystemContext");
	             }
	         }

	         return context;
	     }
}