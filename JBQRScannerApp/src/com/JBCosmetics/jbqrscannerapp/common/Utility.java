package com.JBCosmetics.jbqrscannerapp.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

public final class Utility {

	private static final String TAG = "Utility";

	/**
	 * Method to set value to preference
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPreference(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				JBConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
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
				JBConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
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
				JBConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
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
				JBConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		return preferences.getString(key, null);
	}

	public static void resetPreference(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				JBConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
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

	/**
	 * Method to send data as post request
	 * 
	 * @param url
	 * @param jsonParameter
	 * @return reponse String
	 */
	public static String sendPost(String url, String jsonParameter) {
		String response = null;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add request header
			con.setRequestMethod("POST");
			// con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonParameter);
			wr.flush();
			wr.close();

			Log.i(TAG, "\nSending 'POST' request to URL : " + url);
			Log.i(TAG, "REQUEST------------------------> " + jsonParameter);

			int responseCode = con.getResponseCode();

			Log.i(TAG, "Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();

			// print result
			response = responseBuffer.toString();
			
			Log.i(TAG, "RESPONSE------------------->"+response);
		} catch (MalformedURLException e) {
			Log.e(TAG, "MalformedURLException while sending request", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException while sending request", e);
		}

		return response;

	}

	/**
	 * Method to detect whether an active internet connection is available or
	 * not
	 * 
	 * @param context
	 * @return connectivity status
	 */
	public static boolean isConnectedToInternet(Context context) {
		boolean isConnected = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			isConnected = networkInfo.isConnected();
		}
		return isConnected;
	}

	/**
	 * Method for setting strict policy
	 */
	public static void setStrictPolicy() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
	}
}
