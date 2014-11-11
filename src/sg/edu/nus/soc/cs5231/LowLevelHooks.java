package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class LowLevelHooks implements IXposedHookLoadPackage {
	static String [] whiteList = { 	"jp.naver.line.android"	};
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
//		if( !IsInWhiteList(lpparam.processName) )
//		{
//			return;
//		}
//		if(lpparam.packageName.equals("android") || lpparam.packageName.contains(".android.") || lpparam.packageName.contains(".google.") || lpparam.packageName.equals("com.qualcomm.location"))
//		{
//			return;
//		}
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookLoadSharedLibrary(lpparam);
		hookExec(lpparam);
	}
	
	private void hookLoadSharedLibrary(final LoadPackageParam lpparam)
	{
		findAndHookMethod("java.lang.System", lpparam.classLoader, "loadLibrary", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.System", "loadLibrary()", "trying to load shared library \"" + param.args[0] + "\"");
			}
		});
		findAndHookMethod("java.lang.System", lpparam.classLoader, "load", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.System", "loadLibrary()", "trying to load shared library \"" + param.args[0] + "\"");
			}
		});
		
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "loadLibrary", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.Runtime", "loadLibrary()", "trying to load shared library \"" + param.args[0] + "\"");
			}
		});
		
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "load", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.Runtime", "load()", "trying to load shared library at \"" + param.args[0] + "\"");
			}
		});
	}
	
	private void hookExec(final LoadPackageParam lpparam)
	{
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + param.args[0] + "\"");
			}
		});
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String[].class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				String[] args = (String[])param.args[0];
				String cmd = "";
				for(String s : args)
				{
					cmd = cmd + " " + s;
				}
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + cmd + "\"");
			}
		});
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String.class, String[].class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + param.args[0] + "\"");
			}
		});
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String.class, String[].class, File.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + param.args[0] + "\"");
			}
		});
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String[].class, String[].class, File.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				String[] args = (String[])param.args[0];
				String cmd = "";
				for(String s : args)
				{
					cmd = cmd + " " + s;
				}
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + cmd + "\"");
			}
		});
		findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String[].class, String[].class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				String[] args = (String[])param.args[0];
				String cmd = "";
				for(String s : args)
				{
					cmd = cmd + " " + s;
				}
				Logger.Log(lpparam, "java.lang.Runtime", "exec()", "trying to exec command \"" + cmd + "\"");
			}
		});
	}
	
	private boolean IsInWhiteList(String target)
	{
		for(String s : whiteList)
		{
			if(s.equals(target))
			{
				return true;
			}
		}
		return false;
	}
}
