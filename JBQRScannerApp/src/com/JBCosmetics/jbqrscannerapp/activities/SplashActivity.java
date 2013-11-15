package com.JBCosmetics.jbqrscannerapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.JBConsmetics.jbqrscannerapp.services.AuthenticationService;
import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.common.JBConstants;
import com.JBCosmetics.jbqrscannerapp.common.Utility;

public class SplashActivity extends Activity {

	private static final int SPLASH_TIMER = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Crashlytics.start(this);
		setContentView(R.layout.activity_splash);

		// showing splash image for 2 seconds
		Handler splashHandler = new Handler();

		splashHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!checkAuthToken()) {
					// start authntication service
					Intent authService = new Intent(getApplicationContext(),
							AuthenticationService.class);
					startService(authService);
				}
				finish();
				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(intent);

			}

		}, SPLASH_TIMER);
	}

	private boolean checkAuthToken() {
		boolean result = true;
		String authToken = Utility.getPreference(getApplicationContext(),
				JBConstants.AUTH_TOKEN);
		if (authToken == null || authToken.isEmpty()) {
			result = false;
		}
		return result;
	}
}
