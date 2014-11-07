package sg.edu.nus.soc.cs5231;

public class PackageWhiteList {

	public final static String [] whiteList = {
		"com.magicwach.rdefense",
		"com.xxx.yyy",
		"cn.beauty.leg",
		"com.android.root",
		"jackpal.androidterm",
		"net.youmi.android",
		"com.google.ssearch",
		"com.tutusw.phonespeedup",
		"uk.co.lilhermit.android.core",
		"com.adwo.adsdk",
		"com.google.update",
		"com.ps.llk",
		"com.android.vending.licensing",
		"com.flurry.android",
		"com.masshabit.build",
		"com.masshabit.common",
		"com.masshabit.common.curve",
		"com.masshabit.common.entity",
		"com.masshabit.common.physics",
		"com.masshabit.common.resource",
		"com.masshabit.common.tween",
		"com.masshabit.squibble",
		"com.masshabit.squibble.free",
		"com.masshabit.squibble.free.activity",
		"com.masshabit.squibble.free.activity.a",
		"com.masshabit.squibble.free.activity.b",
		"com.masshabit.squibble.free.activity.c",
		"com.masshabit.squibble.free.activity.d",
		"com.masshabit.squibble.free.activity.e",
		"de.mobinauten.smsspy"
	};
	
	public static boolean IsInWhiteList(String target)
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
