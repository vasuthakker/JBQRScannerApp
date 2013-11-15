package com.JBCosmetics.jbqrscannerapp.common;

import java.util.List;

import android.content.Context;

import com.JBConsmetics.jbqrscannerapp.entities.ClaimRequestEntity;
import com.JBCosmetics.jbqrscannerapp.helper.QRClaimsHelper;
import com.google.gson.Gson;

public class SendRequestForClaims {

	public static void sendRequest(Context context) {

		if (Utility.isConnectedToInternet(context)) {
			Utility.setStrictPolicy();
			String lastQRClaimPushdTime = Utility.getPreference(context,
					JBConstants.LAST_CLAIM_PUSHED_TIME);
			if (lastQRClaimPushdTime == null || lastQRClaimPushdTime.isEmpty()) {
				lastQRClaimPushdTime = "0";
			}

			// get all the claims
			List<ClaimRequestEntity> claimRequestEntities = QRClaimsHelper
					.selectClaim(context, QRClaimsHelper.KEY_SCAN_TIME + "> ?",
							new String[] { lastQRClaimPushdTime }, null, null,
							null);

			if (claimRequestEntities != null && !claimRequestEntities.isEmpty()) {
				// sending request
				for (ClaimRequestEntity claim : claimRequestEntities) {
					claim.setAuth(Utility.getPreference(context,
							JBConstants.AUTH_TOKEN));
					claim.setDeviceid(Utility.getPreference(context,
							JBConstants.DEVICE_ID));
					String jsonRequest = new Gson().toJson(claim);
					Utility.sendPost(PropertyReader.getProperty(context,
							JBConstants.QR_SCAN_URL), jsonRequest.replace(
							"longs", "long"));
				}

				Utility.setPreference(context,
						JBConstants.LAST_CLAIM_PUSHED_TIME,
						String.valueOf(System.currentTimeMillis()));

			}
		}
	}
}
