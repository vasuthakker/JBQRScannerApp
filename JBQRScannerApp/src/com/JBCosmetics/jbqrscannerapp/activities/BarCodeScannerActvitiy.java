package com.JBCosmetics.jbqrscannerapp.activities;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.JBConsmetics.jbqrscannerapp.entities.AuthenticationResponseEntity.QrCode;
import com.JBConsmetics.jbqrscannerapp.entities.ClaimRequestEntity;
import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.common.JBConstants;
import com.JBCosmetics.jbqrscannerapp.common.LocationTracker;
import com.JBCosmetics.jbqrscannerapp.common.SendRequestForClaims;
import com.JBCosmetics.jbqrscannerapp.common.Utility;
import com.JBCosmetics.jbqrscannerapp.fragments.HomeMiddleFragment;
import com.JBCosmetics.jbqrscannerapp.helper.QRClaimsHelper;
import com.JBCosmetics.jbqrscannerapp.helper.QRCodesHelper;
import com.JBCosmetics.jbqrscannerapp.views.CameraPreview;

@SuppressLint("DefaultLocale")
public class BarCodeScannerActvitiy extends FragmentActivity {

	protected static final String TAG = "CameraTestActivity";
	private Camera mCamera;
	private CameraPreview mPreview;
	private Handler autoFocusHandler;

	private FrameLayout preview;

	Button scanButton;

	ImageScanner scanner;

	private boolean barcodeScanRequested = false;
	private boolean previewing = true;

	private Button backButtonImageView;
	private ImageView cameraFocusImageView;

	private int postToScan;

	private static List<String> locations;
	private static List<QrCode> codes;

	static {
		System.loadLibrary(JBConstants.ICONVLIBRARYNAME);
	}

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bar_code_scanner_actvitiy);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		autoFocusHandler = new Handler();
		mCamera = getCameraInstance();

		/* Instance barcode scanner */
		try {
			scanner = new ImageScanner();
			scanner.setConfig(0, Config.X_DENSITY, 3);
			scanner.setConfig(0, Config.Y_DENSITY, 3);

		} catch (ExceptionInInitializerError e) {
			Utility.sendReport(e);
			Log.e(TAG, "ExceptionInInitializerError");
		} catch (Throwable e) {
			// Utility.sendReport(e);
			Log.e(TAG, "ExceptionInInitializerError");
		}

		mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
		preview = (FrameLayout) findViewById(R.id.cameralayout);

		if (!Utility.getBooleanPreference(getApplicationContext(),
				JBConstants.MESSAGE_DIALOG_SHOWN)) {
			DialogFragment messageDialog = new MessageDialog();
			messageDialog.show(getSupportFragmentManager(), "message");
		}
		barcodeScanRequested = true;

		backButtonImageView = (Button) findViewById(R.id.barcode_scanner_back_button_imageview);
		cameraFocusImageView = (ImageView) findViewById(R.id.barcode_scanner_camera_foucs_imageview);

		// by default setting focus image to white
		cameraFocusImageView.setImageResource(R.drawable.barcode_border_white);

		postToScan = getIntent().getIntExtra(JBConstants.TICKET_NO, -1);

		try {
			preview.addView(mPreview);
			cameraFocusImageView.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
		}

		// back button
		backButtonImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// close the activity
				Intent in = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(in);
				finish();
			}
		});

		codes = QRCodesHelper.getQRCodes(getApplicationContext(), null, null,
				null, null, null);
		if (codes != null) {

			locations = new ArrayList<String>();

			for (QrCode code : codes) {
				locations.add(code.getCode().toLowerCase());
			}
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		releaseCamera();
		Log.i(TAG, "paused");
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
		}
		return c;
	}

	private void releaseCamera() {
		if (mCamera != null) {
			previewing = false;
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}

	private Runnable doAutoFocus = new Runnable() {
		public void run() {
			if (previewing)
				mCamera.autoFocus(autoFocusCB);
		}
	};

	PreviewCallback previewCb = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();

			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);

			if (scanner != null) {
				int result = scanner.scanImage(barcode);

				if (result != 0 && barcodeScanRequested) {
					// change focus image color to green
					cameraFocusImageView
							.setImageResource(R.drawable.barcode_border_green);

					SymbolSet syms = scanner.getResults();
					Log.i(TAG, " size of symbol set is " + syms.size());

					for (Symbol sym : syms) {
						String barcodeResult = sym.getData();

						try {
							if (locations.contains(barcodeResult.toLowerCase())) {

								// insert claim
								insertClaim();

								SendRequestForClaims
										.sendRequest(getApplicationContext());

								// start home activity
								Intent in = new Intent(getApplicationContext(),
										HomeActivity.class);
								startActivity(in);
								previewing = false;
								mCamera.setPreviewCallback(null);
								mCamera.stopPreview();

								HomeMiddleFragment.getPosForResult(
										getApplicationContext(), postToScan);
								finish();

							} else {

								DialogFragment messageDialog = new ErrorDialog();
								messageDialog.show(getSupportFragmentManager(),
										"message");

							}
						} catch (StringIndexOutOfBoundsException e) {
							DialogFragment messageDialog = new ErrorDialog();
							messageDialog.show(getSupportFragmentManager(),
									"message");
							Log.e(TAG, "StringIndexOutOfBoundsException");
						}
						barcodeScanRequested = false;
					}
				}
			}
		}
	};

	// Mimic continuous auto-focusing
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};

	@SuppressLint("ValidFragment")
	private class MessageDialog extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.qrmessage_dialog);

			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);

			Button btnOk = (Button) dialog.findViewById(R.id.message_dialog_ok);

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// setting preference for dialog
					Utility.setPreference(getApplicationContext(),
							JBConstants.MESSAGE_DIALOG_SHOWN, true);

					dialog.dismiss();
					// barcodeScanRequested = true;
				}
			});
			return dialog;
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
			title.setText(getString(R.string.error_dialog_title));
			title.setTextColor(getResources().getColor(R.color.maroon));

			TextView message = (TextView) dialog
					.findViewById(R.id.dialog_message);
			message.setText(getString(R.string.error_dialog_message));

			View lineView = (View) dialog.findViewById(R.id.dialog_lineview);
			lineView.setBackgroundColor(getResources().getColor(R.color.maroon));

			Button btnOk = (Button) dialog.findViewById(R.id.message_dialog_ok);

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cameraFocusImageView
							.setImageResource(R.drawable.barcode_border_white);
					dialog.dismiss();
					barcodeScanRequested = true;
				}
			});
			return dialog;
		}

	}

	// insert a claim
	private void insertClaim() {
		ClaimRequestEntity claim = LocationTracker
				.getLatLong(getApplicationContext());
		claim.setQr_code_id(postToScan);
		claim.setScanTime(System.currentTimeMillis());

		// inserting claim
		QRClaimsHelper.insetClaim(getApplicationContext(), claim);

	}

}
