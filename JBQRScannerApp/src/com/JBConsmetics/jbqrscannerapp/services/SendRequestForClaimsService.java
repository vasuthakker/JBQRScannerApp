package com.JBConsmetics.jbqrscannerapp.services;

import com.JBCosmetics.jbqrscannerapp.common.SendRequestForClaims;

import android.app.IntentService;
import android.content.Intent;

public class SendRequestForClaimsService extends IntentService {

	public SendRequestForClaimsService() {
		super("SendRequestForClaimsService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		SendRequestForClaims.sendRequest(getApplicationContext());

	}

}
