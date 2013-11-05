package com.JBCosmetics.jbqrscannerapp.activities;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
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
import android.widget.TextView;

import com.JBCosemetics.jbscannerapp.views.CameraPreview;
import com.JBCosmetics.jbqrscannerapp.R;

public class BarCodeScannerActvitiy extends FragmentActivity {

	protected static final String TAG = "CameraTestActivity";
	private Camera mCamera;
	private CameraPreview mPreview;
	private Handler autoFocusHandler;

	private FrameLayout preview;

	TextView scanText;
	Button scanButton;

	ImageScanner scanner;

	private boolean barcodeScanned = false;
	private boolean previewing = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bar_code_scanner_actvitiy);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		autoFocusHandler = new Handler();
		mCamera = getCameraInstance();

		/* Instance barcode scanner */
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);

		mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
		preview = (FrameLayout) findViewById(R.id.cameralayout);

		DialogFragment messageDialog = new MessageDialog();
		messageDialog.show(getSupportFragmentManager(), "message");

		scanText = (TextView) findViewById(R.id.scantext);

		// scanButton = (Button)findViewById(R.id.ScanButton);
		//
		// scanButton.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// if (barcodeScanned) {
		// barcodeScanned = false;
		// scanText.setText("Scanning...");
		// mCamera.setPreviewCallback(previewCb);
		// mCamera.startPreview();
		// previewing = true;
		// mCamera.autoFocus(autoFocusCB);
		// }
		// }
		// });
	}

	public void onPause() {
		super.onPause();
		releaseCamera();
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

			int result = scanner.scanImage(barcode);

			if (result != 0) {
				previewing = false;
				mCamera.setPreviewCallback(null);
				mCamera.stopPreview();

				SymbolSet syms = scanner.getResults();
				Log.i(TAG, " size of symbol set is " + syms.size());

				for (Symbol sym : syms) {
					scanText.setText("barcode result " + sym.getData());
					barcodeScanned = true;
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
			dialog.setContentView(R.layout.qrmessage_dialog);

			Button btnOk = (Button) dialog.findViewById(R.id.message_dialog_ok);

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					preview.addView(mPreview);
					dialog.dismiss();
				}
			});
			return dialog;
		}

	}
}
