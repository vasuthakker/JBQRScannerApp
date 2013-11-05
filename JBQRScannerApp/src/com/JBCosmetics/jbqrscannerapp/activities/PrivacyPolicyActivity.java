package com.JBCosmetics.jbqrscannerapp.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.JBCosmetics.jbqrscannerapp.R;

public class PrivacyPolicyActivity extends Activity {

	private static final String TAG = "PrivacyPolicyActivity";
	private EditText privacyPolicyEditText;
	private ImageView closeImageView;
	private FrameLayout accountFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privacy_policy);

		privacyPolicyEditText = (EditText) findViewById(R.id.privacy_policy_text_edittext);
		closeImageView = (ImageView) findViewById(R.id.privacy_plocy_back_imageview);
		accountFrameLayout = (FrameLayout) findViewById(R.id.privacy_policy_framelayout);
		privacyPolicyEditText.setText(Html.fromHtml(readText()));

		closeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		accountFrameLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private String readText() {
		StringBuffer fileData = new StringBuffer();
		try {

			InputStream is = getResources().openRawResource(R.raw.tnc);
			BufferedReader bis = new BufferedReader(new InputStreamReader(is));
			if (is != null) {
				String line;
				while ((line = bis.readLine()) != null) {
					fileData.append(line);
				}
			}
		} catch (IOException e) {
			Log.e(TAG, "unable to read file");
		}
		return fileData.toString();
	}
}
