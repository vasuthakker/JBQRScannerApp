package com.JBCosmetics.jbscannerapp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.crashlytics.android.Crashlytics;

public final class Utility {

	/**
	 * Method to set value to preference
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPreference(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * Method to set value to preference
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPreference(Context context, String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * Method to get value from preference
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static boolean getBooleanPreference(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	/**
	 * Method to get value from preference
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static String getPreference(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		return preferences.getString(key, null);
	}

	public static void resetPreference(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();

	}

	/**
	 * Method to send report via crashlytics
	 * 
	 * @param e
	 */
	public static void sendReport(Throwable e) {
		Crashlytics.logException(e);
	}
}
