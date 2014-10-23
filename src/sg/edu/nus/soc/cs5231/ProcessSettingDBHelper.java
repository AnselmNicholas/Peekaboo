package sg.edu.nus.soc.cs5231;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProcessSettingDBHelper {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { 
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_PNAME,
			MySQLiteHelper.COLUMN_ENABLED
	};

	public ProcessSettingDBHelper(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public ProcessSetting createNewProcessSetting(String process_name) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_PNAME, process_name);
		long insertId = database.insert(MySQLiteHelper.TABLE_PROCESS_SETTINGS, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROCESS_SETTINGS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ProcessSetting psetting = cursorToProcessSetting(cursor);
		cursor.close();
		return psetting;
	}
	
	public void updateProcessSetting(ProcessSetting psetting) {
		database = dbHelper.getWritableDatabase();
		String whereClause = MySQLiteHelper.COLUMN_PNAME+"=?";
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ENABLED, psetting.isEnableLogging() == true ? 1 : 0);
		database.update(MySQLiteHelper.TABLE_PROCESS_SETTINGS, values, whereClause, new String[]{psetting.getProcessName()});
	}

	public ProcessSetting getProcessSetting(String process_name) {
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROCESS_SETTINGS, allColumns, MySQLiteHelper.COLUMN_PNAME+"=?", new String[]{process_name}, null, null, null);
		if(cursor.getCount()<1){
			cursor.close();
			return null;
		}
		cursor.moveToFirst();
		ProcessSetting psetting = cursorToProcessSetting(cursor);
		cursor.close();
		return psetting;
	}

	private ProcessSetting cursorToProcessSetting(Cursor cursor) {
		ProcessSetting psetting = new ProcessSetting();
		psetting.setId(cursor.getLong(0));
		psetting.setProcessName(cursor.getString(1));
		psetting.setEnableLogging(cursor.getInt(2) == 1 ? true : false);
		return psetting;
	}




}
