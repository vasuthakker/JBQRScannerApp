package com.JBCosmetics.jbqrscannerapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DataBaseHelper";

	// The Android's default system path of your application database.
	@SuppressLint("SdCardPath")
	private static String DB_NAME = "gmatdatabase";

	private SQLiteDatabase myDataBase;

	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_QR_CLAIMS = "CREATE TABLE QR_CLAIMS(_ID INTEGER PRIMARY KEY AUTOINCREMENT,QR_CODE_ID INTEGER NOT NULL,LATTITUTDE TEXT NOT NULL,LONGITUDE TEXT NOT NULL,SCAN_TIME INTEGER)";
	
	private static final String CREATE_TABLE_QR_CODES = "CREATE TABLE QR_CODES(_ID INTEGER PRIMARY KEY AUTOINCREMENT,CODE_ID INTEGER NOT NULL,CODE TEXT NOT NULL,PIN TEXT NOT NULL,LOCATION_ID INTEGER NOT NULL)";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_QR_CLAIMS);
		db.execSQL(CREATE_TABLE_QR_CODES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * Method to read values from table
	 * 
	 * @param tableName
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return cursor
	 */
	public Cursor readValues(SQLiteDatabase db, String tableName,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = null;

		try {

			cursor = db.query(tableName, columns, selection, selectionArgs,
					groupBy, having, orderBy);
		} catch (SQLException e) {
			Log.e(TAG, "SQLException ", e);
		}
		return cursor;
	}

}