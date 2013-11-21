package com.JBCosmetics.jbqrscannerapp.common;

import java.util.List;

import android.content.Context;

import com.JBCosmetics.jbqrscannerapp.entities.ClaimRequestEntity;
import com.JBCosmetics.jbqrscannerapp.entities.AuthenticationResponseEntity.Response;
import com.JBCosmetics.jbqrscannerapp.helper.QRClaimsHelper;
import com.google.gson.Gson;

public class SendRequestForClaims {

	public static void sendRequest(Context context) {

		if (Utility.isConnectedToInternet(context)) {
			Utility.setStrictPolicy();

			// get all the claims
			List<ClaimRequestEntity> claimRequestEntities = QRClaimsHelper
					.selectClaim(
							context,
							QRClaimsHelper.KEY_VARIFIED + "= ?",
							new String[] { String.valueOf(JBConstants.INACTIVE) },
							null, null, null);

			if (claimRequestEntities != null && !claimRequestEntities.isEmpty()) {
				// sending request
				for (ClaimRequestEntity claim : claimRequestEntities) {
					claim.setAuth(Utility.getPreference(context,
							JBConstants.AUTH_TOKEN));
					claim.setDeviceid(Utility.getPreference(context,
							JBConstants.DEVICE_ID));
					Gson gson = new Gson();
					String jsonRequest = gson.toJson(claim);
					String jsonResponse = Utility.sendPost(PropertyReader
							.getProperty(context, JBConstants.QR_SCAN_URL),
							jsonRequest.replace("longs", "long"));

					Response response = gson.fromJson(
							jsonResponse.substring(jsonResponse.indexOf('{')),
							Response.class);
					if (response != null) {
						if (response.getResult() == JBConstants.ACTIVE) {
							// update verification of the claim
							claim.setIsVarified(JBConstants.ACTIVE);
							QRClaimsHelper.updateClaim(context, claim);
						}
					}

				}

			}
		}
	}
}
