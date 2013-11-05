package com.JBCosmetics.jbqrscannerapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.activities.BarCodeScannerActvitiy;
import com.JBCosmetics.jbqrscannerapp.activities.CashierActivity;
import com.JBCosmetics.jbqrscannerapp.activities.HomeActivity;
import com.JBCosmetics.jbscannerapp.common.Constants;
import com.JBCosmetics.jbscannerapp.common.Utility;

public class HomeMiddleFragment extends Fragment {
	private String isAccountSetup = null;

	private static final String TAG = "HomeMiddleFragment";
	private static RelativeLayout btn1;
	private static RelativeLayout btn2;
	private static RelativeLayout btn3;
	private static RelativeLayout btn4;
	private static RelativeLayout btn5;
	private static RelativeLayout btn6;

	private static TextView btn1Text;
	private static TextView btn2Text;
	private static TextView btn3Text;
	private static TextView btn4Text;
	private static TextView btn5Text;
	private static TextView btn6Text;

	private static ImageView btn1Image;
	private static ImageView btn2Image;
	private static ImageView btn3Image;
	private static ImageView btn4Image;
	private static ImageView btn5Image;
	private static ImageView btn6Image;

	private RelativeLayout reedeemButton;

	private static final int RESULT_OK = 1;

	private int count = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater
				.inflate(R.layout.fragment_home_layout, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		btn1 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_1);
		btn2 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_2);
		btn3 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_3);
		btn4 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_4);
		btn5 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_5);
		btn6 = (RelativeLayout) getActivity().findViewById(R.id.relative_btn_6);

		btn1Text = (TextView) getActivity().findViewById(R.id.btn_1_text);
		btn2Text = (TextView) getActivity().findViewById(R.id.btn_2_text);
		btn3Text = (TextView) getActivity().findViewById(R.id.btn_3_text);
		btn4Text = (TextView) getActivity().findViewById(R.id.btn_4_text);
		btn5Text = (TextView) getActivity().findViewById(R.id.btn_5_text);
		btn6Text = (TextView) getActivity().findViewById(R.id.btn_6_text);

		btn1Image = (ImageView) getActivity().findViewById(R.id.btn_1_image);
		btn2Image = (ImageView) getActivity().findViewById(R.id.btn_2_image);
		btn3Image = (ImageView) getActivity().findViewById(R.id.btn_3_image);
		btn4Image = (ImageView) getActivity().findViewById(R.id.btn_4_image);
		btn5Image = (ImageView) getActivity().findViewById(R.id.btn_5_image);
		btn6Image = (ImageView) getActivity().findViewById(R.id.btn_6_image);

		reedeemButton = (RelativeLayout) getActivity().findViewById(
				R.id.reddem_button_layout);

		// on click listeners for button
		btn1.setOnClickListener(new ButtonClickListener("", 1));
		btn2.setOnClickListener(new ButtonClickListener("", 2));
		btn3.setOnClickListener(new ButtonClickListener("", 3));
		btn4.setOnClickListener(new ButtonClickListener("", 4));
		btn5.setOnClickListener(new ButtonClickListener("", 5));
		btn6.setOnClickListener(new ButtonClickListener("", 6));

		reedeemButton.setOnClickListener(null);

	}

	private Integer getValueFromPreference(String key) {
		int value = -1;
		try {
			value = Integer.parseInt(Utility.getPreference(getActivity(), key));
		} catch (NumberFormatException e) {
			Log.e(TAG, "NumberFormatException");
		}
		return value;
	}

	// on click listeners for button
	private class ButtonClickListener implements OnClickListener {

		String barcode;
		int pos;

		ButtonClickListener(String barcode, int pos) {
			this.barcode = barcode;
			this.pos = pos;
		}

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(),
					BarCodeScannerActvitiy.class);
			intent.putExtra(Constants.BARCODE, barcode);
			intent.putExtra(Constants.TICKET_NO, getImageCount() + 1);
			startActivity(intent);
			getActivity().finish();

		}
	}

	public static void getPosForResult(Context context, int pos) {

		String posValue = null;
		switch (pos) {
		case 1:
			posValue = "btn1";
			btn1Text.setVisibility(View.GONE);
			btn1Image.setVisibility(View.VISIBLE);
			btn1.setOnClickListener(null);
			break;
		case 2:
			posValue = "btn2";
			btn2Text.setVisibility(View.GONE);
			btn2Image.setVisibility(View.VISIBLE);
			btn2.setOnClickListener(null);
			break;
		case 3:
			posValue = "btn3";
			btn3Text.setVisibility(View.GONE);
			btn3Image.setVisibility(View.VISIBLE);
			btn3.setOnClickListener(null);
			break;
		case 4:
			posValue = "btn4";
			btn4Text.setVisibility(View.GONE);
			btn4Image.setVisibility(View.VISIBLE);
			btn4.setOnClickListener(null);
			break;
		case 5:
			posValue = "btn5";
			btn5Text.setVisibility(View.GONE);
			btn5Image.setVisibility(View.VISIBLE);
			btn5.setOnClickListener(null);
			break;
		case 6:
			posValue = "btn6";
			btn6Text.setVisibility(View.GONE);
			btn6Image.setVisibility(View.VISIBLE);
			btn6.setOnClickListener(null);
			break;

		default:

			break;
		}

		if (posValue != null) {
			Utility.setPreference(context, posValue,
					Character.toString(posValue.charAt(posValue.length() - 1)));
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		// getting values for preference
		List<Integer> cardList = new ArrayList<Integer>();
		cardList.add(getValueFromPreference("btn1"));
		cardList.add(getValueFromPreference("btn2"));
		cardList.add(getValueFromPreference("btn3"));
		cardList.add(getValueFromPreference("btn4"));
		cardList.add(getValueFromPreference("btn5"));
		cardList.add(getValueFromPreference("btn6"));

		for (int i : cardList) {
			getPosForResult(getActivity(), i);
		}

		count = getImageCount();

		if (count == 3 || count == 5) {
			isAccountSetup = Utility.getPreference(getActivity(),
					Constants.ACCOUNT_SAVED);
			if (isAccountSetup == null || isAccountSetup.isEmpty()) {
				DialogFragment popup = new MessageDialog();
				popup.show(getChildFragmentManager(), "popup");
			}

		}
		if (count >= 6) {
			reedeemButton.setBackgroundColor(getResources().getColor(
					R.color.liteblue_1));
			reedeemButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isAccountSetup = Utility.getPreference(getActivity(),
							Constants.ACCOUNT_SAVED);
					DialogFragment dialog = null;
					String dialogTag = null;
					if (isAccountSetup != null && !isAccountSetup.isEmpty()) {
						dialog = new RedeemDialog();
						dialogTag = "redeem";
					} else {
						dialog = new MessageDialog();
						dialogTag = "popup";
					}
					dialog.show(getChildFragmentManager(), dialogTag);

				}
			});
		} else {
			reedeemButton.setBackgroundColor(getResources().getColor(
					R.color.grey));
			reedeemButton.setOnClickListener(null);
		}
	}

	private int getImageCount() {
		int cnt = 0;
		if (btn1Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		if (btn2Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		if (btn3Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		if (btn4Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		if (btn5Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		if (btn6Image.getVisibility() == View.VISIBLE) {
			cnt++;
		}
		return cnt;
	}

	@SuppressLint("ValidFragment")
	private class MessageDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.account_popup_dialog);

			Button btnLater = (Button) dialog.findViewById(R.id.acc_pop_later);

			btnLater.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});

			Button btnSetup = (Button) dialog.findViewById(R.id.acc_pop_setup);

			btnSetup.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					HomeActivity.changePageToAccountPage();
				}
			});
			return dialog;
		}

	}

	@SuppressLint("ValidFragment")
	private class RedeemDialog extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.account_popup_dialog);

			TextView titleTextView = (TextView) dialog
					.findViewById(R.id.popup_title);
			TextView messageTextView = (TextView) dialog
					.findViewById(R.id.popup_message);

			titleTextView.setText(getString(R.string.congratulation));
			messageTextView.setText(R.string.redeem_pop_message);

			Button btnLater = (Button) dialog.findViewById(R.id.acc_pop_later);
			btnLater.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});

			Button btnSetup = (Button) dialog.findViewById(R.id.acc_pop_setup);
			btnSetup.setText(getString(R.string.cashier));

			btnSetup.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(),
							CashierActivity.class);
					startActivityForResult(intent, RESULT_OK);
				}
			});
			return dialog;
		}
	}

}