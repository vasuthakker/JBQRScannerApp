package com.JBCosmetics.jbqrscannerapp.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.common.JBConstants;
import com.JBCosmetics.jbqrscannerapp.common.Utility;

public class CashierActivity extends FragmentActivity {

	private Button cancelButton;
	private Button resetButton;

	private EditText edittext_1;
	private EditText edittext_2;
	private EditText edittext_3;
	private EditText edittext_4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cashier);

		edittext_1 = (EditText) findViewById(R.id.cashier_edit_1);
		edittext_2 = (EditText) findViewById(R.id.cashier_edit_2);
		edittext_3 = (EditText) findViewById(R.id.cashier_edit_3);
		edittext_4 = (EditText) findViewById(R.id.cashier_edit_4);

		cancelButton = (Button) findViewById(R.id.cashier_cancel);
		resetButton = (Button) findViewById(R.id.cashier_reset);

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				StringBuffer enteredPasscode = new StringBuffer();
				enteredPasscode.append(edittext_1.getText().toString());
				enteredPasscode.append(edittext_2.getText().toString());
				enteredPasscode.append(edittext_3.getText().toString());
				enteredPasscode.append(edittext_4.getText().toString());

				if (enteredPasscode.toString().equals(JBConstants.RESETPASSCODE)) {

					// getting values from preference
					String isAccountAdded = Utility.getPreference(
							getApplicationContext(), JBConstants.ACCOUNT_SAVED);
					String accountName = Utility.getPreference(
							getApplicationContext(), JBConstants.ACCOUNT_NAME);
					String accountEmail = Utility.getPreference(
							getApplicationContext(), JBConstants.ACCOUNT_EMAIL);
					String accountPhoneNumber = Utility.getPreference(
							getApplicationContext(),
							JBConstants.ACCOUNT_PHONE_NUMBER);
					boolean QRScandialogShown = Utility.getBooleanPreference(
							getApplicationContext(),
							JBConstants.MESSAGE_DIALOG_SHOWN);

					// reseting the preference
					Utility.resetPreference(getApplicationContext());

					// putting the old values
					Utility.setPreference(getApplicationContext(),
							JBConstants.ACCOUNT_SAVED, isAccountAdded);
					Utility.setPreference(getApplicationContext(),
							JBConstants.ACCOUNT_NAME, accountName);
					Utility.setPreference(getApplicationContext(),
							JBConstants.ACCOUNT_EMAIL, accountEmail);
					Utility.setPreference(getApplicationContext(),
							JBConstants.ACCOUNT_PHONE_NUMBER, accountPhoneNumber);
					Utility.setPreference(getApplicationContext(),
							JBConstants.MESSAGE_DIALOG_SHOWN, QRScandialogShown);

					setResult(RESULT_OK, null);
					finish();
				} else {
					DialogFragment dialog = new ErrorDialog();
					dialog.show(getSupportFragmentManager(), "wrongpasscode");
				}

				edittext_1.setText("");
				edittext_2.setText("");
				edittext_3.setText("");
				edittext_4.setText("");

			}
		});

		edittext_1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 1) {
					edittext_2.requestFocus();
				}
			}
		});
		edittext_2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 1) {
					edittext_3.requestFocus();
				}
			}
		});
		edittext_3.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 1) {
					edittext_4.requestFocus();
				}
			}
		});

		edittext_1.requestFocus();

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

			TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
			title.setText(getString(R.string.wrong_passcode_title));
			title.setTextColor(getResources().getColor(R.color.maroon));

			TextView message = (TextView) dialog
					.findViewById(R.id.dialog_message);
			message.setText(getString(R.string.wrong_passcode_message));

			View lineView = (View) dialog.findViewById(R.id.dialog_lineview);
			lineView.setBackgroundColor(getResources().getColor(R.color.maroon));

			Button btnOk = (Button) dialog.findViewById(R.id.message_dialog_ok);

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					edittext_1.requestFocus();

				}
			});
			return dialog;
		}
	}
}
