package com.JBCosmetics.jbqrscannerapp.common;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "piggybank";

	// Contacts table name
	private static final String TABLE_ACCOUNTS = "ACCOUNTS";
	private static final String TABLE_TRANSACTIONS = "TRANSACTIONS";

	private static final String TAG = "DatbaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		List<String> queries = TableCreationQueries.getQueries();
		for (String query : queries) {
			Log.v(TAG, "Creating table->" + query);
			db.execSQL(query);
		}
		/*
		 * String insert = "insert into " + TABLE_ACCOUNTS + "(" + KEY_NAME +
		 * "," + KEY_balance + "," + KEY_date+"," +
		 * KEY_ISACTIVE+") values('default',0000000,"
		 * +System.currentTimeMillis()+","+AppConstants.ACTIVESTATUS+")";
		 * 
		 * db.execSQL(insert);
		 */

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
		// Create tables again
		onCreate(db);
	}

	// Deleting single record
	public void deleteRecord(String tableName, String key, String keyValue) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, key + " = ?", new String[] { keyValue });
		db.close();
	}

	public void updateRecord(String tableName, ContentValues cv, String key,
			String value) {
		SQLiteDatabase db = this.getWritableDatabase();
		long noOfUpdatedRows = db.update(tableName, cv, key + " = ?",
				new String[] { value });
		Log.v(TAG, "no of rows updated " + noOfUpdatedRows);
		db.close();
	}

	public long insertRecord(String table_name, ContentValues cv) {
		SQLiteDatabase db = this.getWritableDatabase();
		long status = db.insert(table_name, null, cv);
		db.close();
		return status;
	}

	public Cursor fetchRecord(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}

	public void executeQeury(String query) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}
}
