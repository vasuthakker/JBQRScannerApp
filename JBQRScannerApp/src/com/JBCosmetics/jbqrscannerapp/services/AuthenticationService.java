package com.JBCosmetics.jbqrscannerapp.services;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.util.Log;

import com.JBCosmetics.jbqrscannerapp.common.JBConstants;
import com.JBCosmetics.jbqrscannerapp.common.PropertyReader;
import com.JBCosmetics.jbqrscannerapp.common.Utility;
import com.JBCosmetics.jbqrscannerapp.entities.AuthenticationRequestEntity;
import com.JBCosmetics.jbqrscannerapp.entities.AuthenticationResponseEntity.QrCode;
import com.JBCosmetics.jbqrscannerapp.entities.AuthenticationResponseEntity.Response;
import com.JBCosmetics.jbqrscannerapp.helper.QRCodesHelper;
import com.google.gson.Gson;

public class AuthenticationService extends IntentService {

	private static final String TAG = "AuthenticationService";
	private static final int SUCCESS_RESULT = 1;

	public AuthenticationService() {
		super("AuthenticationService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		// Getting a unique device id
		String deviceId = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);

		Log.i(TAG, "Device Id=" + deviceId);

		// setting device id to preference
		Utility.setPreference(getApplicationContext(), JBConstants.DEVICE_ID,
				deviceId);

		// making request for authentication
		AuthenticationRequestEntity request = new AuthenticationRequestEntity();
		request.setDeviceid(deviceId);
		request.setUsername(PropertyReader.getProperty(getApplicationContext(),
				JBConstants.USERNAME));
		request.setPassword(PropertyReader.getProperty(getApplicationContext(),
				JBConstants.PASSWORD));

		Gson gson = new Gson();
		String jsonResponse = Utility.sendPost(PropertyReader.getProperty(
				getApplicationContext(), JBConstants.AUTH_URL), gson
				.toJson(request));

		Log.i(TAG, "resposne ----->" + jsonResponse);

		Response responseObjects = gson.fromJson(jsonResponse, Response.class);

		if (responseObjects != null) {
			// setting auth token to preference
			if (responseObjects.getResult() == SUCCESS_RESULT) {
				Utility.setPreference(getApplicationContext(),
						JBConstants.AUTH_TOKEN, responseObjects.getAuth());

				// inserting qrcodes to database
				List<QrCode> qrCodes = responseObjects.getQrCodes();
				if (qrCodes != null && !qrCodes.isEmpty()) {
					QRCodesHelper
							.insetQRCodes(getApplicationContext(), qrCodes);
				}
			}
		}

	}

}
