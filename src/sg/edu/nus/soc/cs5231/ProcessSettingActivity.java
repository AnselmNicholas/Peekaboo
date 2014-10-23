package sg.edu.nus.soc.cs5231;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ProcessSettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		final String process_name = intent.getStringExtra("process_name");
		ProcessSettingDBHelper dbHelper = new ProcessSettingDBHelper(this.getApplicationContext());
		ProcessSetting psetting = dbHelper.getProcessSetting(process_name);
		if(psetting == null)
		{
			psetting = dbHelper.createNewProcessSetting(process_name);
		}
		this.setTitle("Settings for " + process_name);
		
		setContentView(R.layout.activity_process_setting);
		CheckBox repeatChkBx = ( CheckBox ) findViewById( R.id.enableLoggingChkBox );
		repeatChkBx.setChecked(psetting.isEnableLogging());
		repeatChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		    	ProcessSettingDBHelper dbHelper = new ProcessSettingDBHelper(ProcessSettingActivity.this.getApplicationContext());
	    		ProcessSetting psetting_new = new ProcessSetting();
	    		psetting_new.setProcessName(process_name);
		        if ( isChecked )
		        {
		    		psetting_new.setEnableLogging(true);
		        }else{
		    		psetting_new.setEnableLogging(false);
		        }
		        dbHelper.updateProcessSetting(psetting_new);

		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.process_setting, menu);
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
