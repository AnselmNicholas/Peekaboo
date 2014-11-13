package sg.edu.nus.soc.cs5231;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class TelephonyManagerHook implements IXposedHookLoadPackage {

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		if( !PackageWhiteList.IsInWhiteList(lpparam.packageName) )
		{
			return;
		}
		hookTelephonyManager(lpparam);
	}

	private void hookTelephonyManager(final LoadPackageParam lpparam){
		//getCallState
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getCallState", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getCallState()", "trying to get the call state on the device.");
			}
		});

		//getCellLocation
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getCellLocation", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getCellLocation()", "trying to get the current location of device.");
			}
		});

		//getDataActivity
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDataActivity", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getDataActivity()", "trying to get the type of activity on a data connection (cellular).");
			}
		});
		//getDataState
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDataState", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getDataState()", "trying to get the constant indicating the current data connection state (cellular).");
			}
		});
		//getDeviceId
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getDeviceId()", "trying to get unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.");
			}
		});
		//getDeviceSoftwareVersion
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceSoftwareVersion", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getDeviceSoftwareVersion()", "trying to get  software version number for the device, for example, the IMEI/SV for GSM phones.");
			}
		});
		//getLine1Number
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getLine1Number", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getLine1Number()", "trying to get phone number string for line 1, for example, the MSISDN for a GSM phone.");
			}
		});
		//getNeighboringCellInfo
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getNeighboringCellInfo", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getNeighboringCellInfo()", "trying to get the neighboring cell information of the device.");
			}
		});
		//getNetworkCountryIso
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getNetworkCountryIso", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getNetworkCountryIso()", "trying to get ISO country code equivalent of the current registered operator's MCC (Mobile Country Code).");
			}
		});
		//getNetworkOperator
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getNetworkOperator", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getNetworkOperator()", "trying to get numeric name (MCC+MNC) of current registered operator.");
			}
		});
		//getNetworkOperatorName
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getNetworkOperatorName", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getNetworkOperatorName()", "trying to get alphabetic name of current registered operator.");
			}
		});
		//getNetworkType
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getNetworkType", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getNetworkType()", "trying to get the type of network.");
			}
		});
		//getPhoneType
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getPhoneType", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getPhoneType()", "trying to get constant indicating the device phone type.");
			}
		});
		//getSimCountryIso
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSimCountryIso", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSimCountryIso()", "trying to get the ISO country code equivalent for the SIM provider's country code.");
			}
		});
		//getSimOperator
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSimOperator", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSimOperator()", "trying to get the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM.");
			}
		});
		//getSimOperatorName
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSimOperatorName", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSimOperatorName()", "trying to get the Service Provider Name (SPN).");
			}
		});
		//getSimSerialNumber
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSimSerialNumber", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSimSerialNumber()", "trying to get serial number of the SIM, if applicable.");
			}
		});
		//getSimState
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSimState", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSimState()", "trying to get a constant indicating the state of the device SIM card.");
			}
		});
		//getSubscriberId
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getSubscriberId", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getSubscriberId()", "trying to get the unique subscriber ID, for example, the IMSI for a GSM phone.");
			}
		});
		//getVoiceMailAlphaTag
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getVoiceMailAlphaTag", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getVoiceMailAlphaTag()", "trying to get alphabetic identifier associated with the voice mail number.");
			}
		});
		//getVoiceMailNumber
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getVoiceMailNumber", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "getVoiceMailNumber()", "trying to get voice mail number.");
			}
		});
		//isNetworkRoaming
		findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "isNetworkRoaming", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Logger.Log(lpparam, "android.telephony.TelephonyManager", "isNetworkRoaming()", "trying to find out if device is roaming on the current network.");
			}
		});





	}
}

