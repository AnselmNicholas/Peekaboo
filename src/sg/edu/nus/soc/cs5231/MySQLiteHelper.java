package sg.edu.nus.soc.cs5231;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PROCESS_SETTINGS = "psettings";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PNAME = "pname";
	public static final String COLUMN_ENABLED = "enabled";


	private static final String DATABASE_NAME = "cs5231.db";
	private static final int DATABASE_VERSION = 1;
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PROCESS_SETTINGS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_PNAME
			+ " text not null, " + COLUMN_ENABLED
			+ " integer default 0 );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROCESS_SETTINGS);
		onCreate(db);

	}

}
