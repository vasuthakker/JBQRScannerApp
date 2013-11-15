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

import com.JBConsmetics.jbqrscannerapp.entities.ClaimRequestEntity;
import com.JBCosmetics.jbqrscannerapp.common.DataBaseHelper;

/**
 * Helper class for qr claims
 * 
 * @author Vasu
 * 
 */
public class QRClaimsHelper {

	private static final String TABLE_NAME = "QR_CLAIMS";

	private static final String KEY_ID = "_ID";
	private static final String KEY_QR_CODE_ID = "QR_CODE_ID";
	private static final String KEY_LATTITUTDE = "LATTITUTDE";
	private static final String KEY_LONGITUDE = "LONGITUDE";
	public static final String KEY_SCAN_TIME = "SCAN_TIME";

	private static final String TAG = "QRClaimsHelper";

	/**
	 * Method for inserting claims
	 * 
	 * @param context
	 * @param claim
	 */
	public static void insetClaim(Context context, ClaimRequestEntity claim) {
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_QR_CODE_ID, claim.getQr_code_id());
			cv.put(KEY_LATTITUTDE, claim.getLat());
			cv.put(KEY_LONGITUDE, claim.getLongs());
			cv.put(KEY_SCAN_TIME, claim.getScanTime());
			db.insert(TABLE_NAME, null, cv);

		} catch (SQLException e) {
			Log.e(TAG, "SQLException while inserting claim", e);
		} finally {
			db.close();
		}
	}

	/**
	 * Method for selecting all claims
	 * 
	 * @param context
	 * @param qrId
	 * @return
	 */
	public static ClaimRequestEntity selectClaim(Context context, String qrId) {
		ClaimRequestEntity claim = new ClaimRequestEntity();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					KEY_QR_CODE_ID + "=?", new String[] { qrId }, null, null,
					null);

			if (cursor != null && cursor.moveToNext()) {
				claim = getEntityFromCursor(cursor);
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

		return claim;
	}

	/**
	 * Method for selecting claims
	 * 
	 * @param context
	 * @param qrId
	 * @return
	 */
	public static List<ClaimRequestEntity> selectClaim(Context context,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		List<ClaimRequestEntity> claims = new ArrayList<ClaimRequestEntity>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					selection, selectionArgs, groupBy, having, orderBy);

			ClaimRequestEntity claim;
			if (cursor != null && cursor.moveToNext()) {
				while (cursor.moveToNext()) {
					claim = getEntityFromCursor(cursor);
					claims.add(claim);
				}
				claim = new ClaimRequestEntity();
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
		return claims;
	}

	// Method to get entity from cursor
	private static ClaimRequestEntity getEntityFromCursor(Cursor cursor) {
		ClaimRequestEntity claim = new ClaimRequestEntity();
		claim.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		claim.setLongs(cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)));
		claim.setLat(cursor.getString(cursor.getColumnIndex(KEY_LATTITUTDE)));
		claim.setQr_code_id(cursor.getInt(cursor.getColumnIndex(KEY_QR_CODE_ID)));
		claim.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		claim.setScanTime(cursor.getLong(cursor.getColumnIndex(KEY_SCAN_TIME)));
		return claim;
	}
}
