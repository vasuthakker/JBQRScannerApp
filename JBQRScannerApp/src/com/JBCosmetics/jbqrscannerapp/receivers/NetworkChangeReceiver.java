package com.JBCosmetics.jbqrscannerapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.JBCosmetics.jbqrscannerapp.common.SendRequestForClaims;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SendRequestForClaims.sendRequest(context);
	}
}
