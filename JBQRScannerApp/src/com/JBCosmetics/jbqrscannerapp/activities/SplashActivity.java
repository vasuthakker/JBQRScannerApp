package com.JBCosmetics.jbqrscannerapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.JBCosmetics.jbqrscannerapp.R;
import com.crashlytics.android.Crashlytics;

public class SplashActivity extends Activity {

	private static final int SPLASH_TIMER = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		setContentView(R.layout.activity_splash);

		// showing splash image for 2 seconds
		Handler splashHandler = new Handler();

		splashHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();
				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(intent);

			}
		}, SPLASH_TIMER);
	}

}
