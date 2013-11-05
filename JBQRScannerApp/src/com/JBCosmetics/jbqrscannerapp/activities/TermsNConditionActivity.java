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

public class TermsNConditionActivity extends Activity {

	private static final String TAG = "TermsNConditionActivity";
	private EditText termsNConditionEditText;
	private ImageView closeImageView;
	private FrameLayout accountFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_ncondition);

		termsNConditionEditText = (EditText) findViewById(R.id.terms_condition_edittext);
		closeImageView = (ImageView) findViewById(R.id.terms_condition_back_imageview);

		accountFrameLayout = (FrameLayout) findViewById(R.id.terms_condition_framelayout);

		termsNConditionEditText.setText(Html.fromHtml(readText()));

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
