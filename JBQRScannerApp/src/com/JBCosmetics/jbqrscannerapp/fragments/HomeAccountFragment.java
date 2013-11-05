package com.JBCosmetics.jbqrscannerapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.activities.PrivacyPolicyActivity;
import com.JBCosmetics.jbqrscannerapp.activities.TermsNConditionActivity;
import com.JBCosmetics.jbqrscannerapp.common.Constants;
import com.JBCosmetics.jbqrscannerapp.common.Utility;

public class HomeAccountFragment extends Fragment {

	private TextView tncTextView;
	private TextView privacyPolicyTextView;
	private Button saveButton;

	private EditText nameEditText;
	private EditText emailEditText;
	private EditText phoneEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_account_layout, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();

		tncTextView = (TextView) getActivity().findViewById(
				R.id.account_tnc_textview);
		privacyPolicyTextView = (TextView) getActivity().findViewById(
				R.id.account_privacy_textview);
		saveButton = (Button) getActivity().findViewById(
				R.id.account_save_button);

		nameEditText = (EditText) getActivity().findViewById(
				R.id.account_name_edittext);
		emailEditText = (EditText) getActivity().findViewById(
				R.id.account_email_edittext);
		phoneEditText = (EditText) getActivity().findViewById(
				R.id.account_phone_edittext);

		tncTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TermsNConditionActivity.class);
				startActivity(intent);
			}
		});

		// underlying the textviews
		String tncText = getString(R.string.terms_and_condition);
		SpannableString tncUnderlinedText = new SpannableString(tncText);
		tncUnderlinedText.setSpan(new UnderlineSpan(), 0, tncText.length(),
				SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		tncTextView.setText(tncUnderlinedText);

		String privacyPolicyText = getString(R.string.privacy_policy);
		SpannableString privacyPolicyUnderlinedText = new SpannableString(
				privacyPolicyText);
		privacyPolicyUnderlinedText.setSpan(new UnderlineSpan(), 0,
				privacyPolicyText.length(),
				SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		privacyPolicyTextView.setText(privacyPolicyUnderlinedText);

		// On click listeners
		privacyPolicyTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PrivacyPolicyActivity.class);
				startActivity(intent);
			}
		});

		String accountName = Utility.getPreference(getActivity(),
				Constants.ACCOUNT_NAME);
		String accountEmail = Utility.getPreference(getActivity(),
				Constants.ACCOUNT_EMAIL);
		String accountPhoneNumber = Utility.getPreference(getActivity(),
				Constants.ACCOUNT_PHONE_NUMBER);

		nameEditText.setText(accountName);
		emailEditText.setText(accountEmail);
		phoneEditText.setText(accountPhoneNumber);

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String accountName = nameEditText.getText().toString();
				if (accountName != null && accountName.trim().length() > 0) {
					Utility.setPreference(getActivity(),
							Constants.ACCOUNT_NAME, accountName);
				}
				String accountEmail = emailEditText.getText().toString();
				if (accountEmail != null && accountEmail.trim().length() > 0) {
					Utility.setPreference(getActivity(),
							Constants.ACCOUNT_EMAIL, accountEmail);
				}
				String accountPhoneNumber = phoneEditText.getText().toString();
				if (accountPhoneNumber != null
						&& accountPhoneNumber.trim().length() > 0) {
					Utility.setPreference(getActivity(),
							Constants.ACCOUNT_PHONE_NUMBER, accountPhoneNumber);
				}

				Utility.setPreference(getActivity(), Constants.ACCOUNT_SAVED,
						"Y");

				Toast.makeText(getActivity(),
						getString(R.string.account_setup_notification),
						Toast.LENGTH_SHORT).show();
			}
		});

		nameEditText.addTextChangedListener(new TextWatcher() {
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
				if (s.length() > 0) {
					saveButton.setBackgroundColor(getResources().getColor(
							R.color.liteblue_1));
					saveButton.setEnabled(true);
				} else {
					if (emailEditText.getText().length() == 0
							&& phoneEditText.getText().length() == 0) {
						saveButton.setBackgroundColor(getResources().getColor(
								R.color.lite_green));
						saveButton.setEnabled(false);
					}
				}

			}
		});

		emailEditText.addTextChangedListener(new TextWatcher() {
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
				if (s.length() > 0) {
					saveButton.setBackgroundColor(getResources().getColor(
							R.color.liteblue_1));
					saveButton.setEnabled(true);
				} else {
					if (phoneEditText.getText().length() == 0
							&& nameEditText.getText().length() == 0) {
						saveButton.setBackgroundColor(getResources().getColor(
								R.color.lite_green));
						saveButton.setEnabled(false);
					}
				}

			}
		});

		phoneEditText.addTextChangedListener(new TextWatcher() {
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
				if (s.length() > 0) {
					saveButton.setBackgroundColor(getResources().getColor(
							R.color.liteblue_1));
					saveButton.setEnabled(true);
				} else {
					if (emailEditText.getText().length() == 0
							&& nameEditText.getText().length() == 0) {
						saveButton.setBackgroundColor(getResources().getColor(
								R.color.lite_green));
						saveButton.setEnabled(false);
					}
				}

			}
		});
	}

}
