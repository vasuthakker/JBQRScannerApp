package com.JBCosmetics.jbqrscannerapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.JBCosmetics.jbqrscannerapp.R;

public class HomeActivity extends Activity {

	RelativeLayout btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		btn1 = (RelativeLayout) findViewById(R.id.relative_btn_1);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						BarCodeScannerActvitiy.class);
				startActivity(intent);

			}
		});
	}

}
