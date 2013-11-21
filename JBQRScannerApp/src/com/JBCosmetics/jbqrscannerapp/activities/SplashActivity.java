package com.JBCosmetics.jbqrscannerapp.activities;

import com.crashlytics.android.Crashlytics;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.JBConsmetics.jbqrscannerapp.services.AuthenticationService;
import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.common.JBConstants;
import com.JBCosmetics.jbqrscannerapp.common.Utility;

public class SplashActivity extends FragmentActivity {

	private static final int SPLASH_TIMER = 1000;
	private ProgressBar progressBar;
	private boolean isIntternetAvailable = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		// Crashlytics.start(this);
		setContentView(R.layout.activity_splash);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		if (!checkAuthToken()) {
			// start authentication service
			if (Utility.isConnectedToInternet(getApplicationContext())) {
				Intent authService = new Intent(getApplicationContext(),
						AuthenticationService.class);
				startService(authService);
			} else {
				isIntternetAvailable = false;
				progressBar.setVisibility(View.GONE);
				DialogFragment dialog = new ErrorDialog();
				dialog.show(getSupportFragmentManager(), "noInterentDialog");
			}
		}
		if (isIntternetAvailable) {
			progressBar.setVisibility(View.VISIBLE);
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

	@SuppressLint("ValidFragment")
	private class ErrorDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.qrmessage_dialog);

			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
			title.setText(getString(R.string.error_internet_not_available_title));
			title.setTextColor(getResources().getColor(R.color.maroon));

			TextView message = (TextView) dialog
					.findViewById(R.id.dialog_message);
			message.setText(getString(R.string.error_internet_not_available_detail));

			View lineView = (View) dialog.findViewById(R.id.dialog_lineview);
			lineView.setBackgroundColor(getResources().getColor(R.color.maroon));

			Button btnOk = (Button) dialog.findViewById(R.id.message_dialog_ok);

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
			return dialog;
		}
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
