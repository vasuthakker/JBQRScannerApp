package com.JBCosmetics.jbqrscannerapp.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.JBConsmetics.jbqrscannerapp.entities.AuthenticationResponseEntity;
import com.JBConsmetics.jbqrscannerapp.entities.AuthenticationResponseEntity.QrCode;
import com.JBCosmetics.jbqrscannerapp.common.DataBaseHelper;

public class QRCodesHelper {

	private static final String TABLE_NAME = "QR_CODES";

	public static final String KEY_ID = "_ID";
	public static final String KEY_CODE_ID = "CODE_ID";
	public static final String KEY_CODE = "CODE";
	public static final String KEY_PIN = "PIN";
	public static final String KEY_LOCATION_ID = "LOCATION_ID";

	private static final String TAG = "QRCodesHelper";

	/**
	 * Method for inserting values
	 * 
	 */
	public static void insetQRCodes(Context context, List<QrCode> qrCodes) {

		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			ContentValues cv = null;
			for (QrCode code : qrCodes) {
				cv = new ContentValues();
				cv.put(KEY_CODE_ID, code.getId());
				cv.put(KEY_CODE, code.getCode());
				cv.put(KEY_PIN, code.getPin());
				cv.put(KEY_LOCATION_ID, code.getLocationID());
				db.insert(TABLE_NAME, null, cv);
			}
		} catch (SQLException e) {
			Log.e(TAG, "SQLException", e);
		} finally {
			db.close();
		}

	}

	/**
	 * Method for fetching qrCodes
	 */
	public static List<QrCode> getQRCodes(Context context, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		List<QrCode> codes = new ArrayList<QrCode>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					selection, selectionArgs, groupBy, having, orderBy);

			QrCode code = null;
			if (cursor != null) {
				while (cursor.moveToNext()) {
					code = new AuthenticationResponseEntity().new QrCode();
					code.setId(cursor.getInt(cursor.getColumnIndex(KEY_CODE_ID)));
					code.setCode(cursor.getString(cursor
							.getColumnIndex(KEY_CODE)));
					code.setPin(cursor.getString(cursor.getColumnIndex(KEY_PIN)));
					code.setLocationID(cursor.getInt(cursor
							.getColumnIndex(KEY_LOCATION_ID)));
					codes.add(code);
				}
			}
		} catch (SQLException e) {
			Log.e(TAG, "SQLException", e);
		} catch (CursorIndexOutOfBoundsException e) {
			Log.e(TAG, "CursorIndexOutOfBoundsException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} finally {
			db.close();
		}
		return codes;
	}
}
