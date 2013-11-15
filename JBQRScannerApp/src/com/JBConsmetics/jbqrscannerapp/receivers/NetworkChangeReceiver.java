package com.JBConsmetics.jbqrscannerapp.receivers;

import com.JBCosmetics.jbqrscannerapp.common.SendRequestForClaims;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SendRequestForClaims.sendRequest(context);
	}
}
