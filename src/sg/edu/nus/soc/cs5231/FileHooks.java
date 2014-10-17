package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedBridge.hookAllConstructors;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

import java.net.URI;

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
				//XposedBridge.log(lpparam.packageName + " is loaded...");
				if( !IsInBlackList(lpparam.processName) )
				{
					return;
				}
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
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get the user's data directory.");
				}
			});
			//Download/Cache Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getDownloadCacheDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get the download/cache content directory.");
				}
			});
			//External Storage Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStorageDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get primary external storage directory.");
				}
			});
			//External Storage Directory w/ Type
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStoragePublicDirectory", String.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get primary external storage directory that stores "+param.args[0]+".");
				}
			});
			//External Storage State
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStorageState", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get primary external storage directory's state.");
				}
			});
			//Root Directory
			findAndHookMethod("android.os.Environment", lpparam.classLoader, "getRootDirectory", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XposedBridge.log("Jason: " + lpparam.processName + " trying to get the root directory.");
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
					XposedBridge.log("Jason: " + lpparam.processName + " trying to open file " + fileName + ".");
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
					XposedBridge.log("Jason: " + lpparam.processName + " trying to read file " + fileName + ".");
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
					XposedBridge.log("Jason: " + lpparam.processName + " trying to write file " + fileName + ".");
				}
			});
		}
		
		private void hookCreateFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "createNewFile", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " creating new file " + fileName + ".");
				}
			});
		}
		
		private void hookDeleteFile(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "delete", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " deleting " + fileName + ".");
				}
			});
			findAndHookMethod("java.io.File", lpparam.classLoader, "deleteOnExit", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String fileName = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " deleting " + fileName + ".");
				}
			});
		}
		
		private void hookRenameFile(final LoadPackageParam lpparam) {
			findAndHookMethod("java.io.File", lpparam.classLoader, "renameTo", java.io.File.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String originalName = ((java.io.File)param.thisObject).getAbsolutePath();
					String newName = ((java.io.File)param.args[0]).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " renaming " + originalName + " to " + newName +".");
				}
			});
			
		}

		private void hookListFiles(final LoadPackageParam lpparam)
		{
			findAndHookMethod("java.io.File", lpparam.classLoader, "listFiles", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					String directory = ((java.io.File)param.thisObject).getAbsolutePath();
					XposedBridge.log("Jason: " + lpparam.processName + " listing files in "+directory+".");
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
}