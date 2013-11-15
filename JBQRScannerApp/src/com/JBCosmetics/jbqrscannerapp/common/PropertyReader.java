package com.JBCosmetics.jbqrscannerapp.common;

import java.io.IOException;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

import com.JBCosmetics.jbqrscannerapp.R;

public final class PropertyReader {
	private static Properties prop = new Properties();

	public static String getProperty(Context context, String propKey) {

		String propValue = null;
		try {
			// load a properties file
			prop.load(context.getResources().openRawResource(R.raw.appvar));
			propValue = prop.getProperty(propKey);

		} catch (IOException ex) {
			Log.e("prop", "IOException while reading property file",ex);
		}
		return propValue;
	}

}
