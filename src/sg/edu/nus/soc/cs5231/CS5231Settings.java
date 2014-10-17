package sg.edu.nus.soc.cs5231;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class CS5231Settings extends Activity {
	public static List<PackageInfo> installedPackages = null;
	public static ArrayList<String> processNames = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cs5231_settings);
		installedPackages = getPackageManager().getInstalledPackages(4096);
		LinearLayout mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
		mainLinearLayout.removeAllViews();
		processNames = new ArrayList<String>();
		for(PackageInfo pkgInfo : installedPackages){
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			if(appInfo!=null){
				processNames.add(appInfo.processName);
				
				
			}	
		}
		Collections.sort(processNames);
		for(String processName : processNames){
			addProcessName(processName);
		}
			
	}
	
	private void addProcessName(String processName){
		LinearLayout mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
		LinearLayout.LayoutParams lp = new    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    TextView tv = new TextView(this);
	    tv.setTextSize(18);
	    tv.setText(processName);
	    tv.setLayoutParams(lp);
	    tv.setHorizontallyScrolling(true);
	    tv.setClickable(true);
	    tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Individual App Settings
				TextView tv = (TextView)v;
				tv.setAllCaps(true);
				tv.refreshDrawableState();
			}
		});
	    mainLinearLayout.addView(tv);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cs5231_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
