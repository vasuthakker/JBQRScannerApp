package com.JBCosmetics.jbqrscannerapp.common;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.JBConsmetics.jbqrscannerapp.entities.ClaimRequestEntity;

public class LocationTracker {

	public static ClaimRequestEntity getLatLong(Context context) {
		ClaimRequestEntity claimRequest = new ClaimRequestEntity();
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		String locationProvider = LocationManager.NETWORK_PROVIDER;
		// Or use LocationManager.GPS_PROVIDER

		Location lastKnownLocation = locationManager
				.getLastKnownLocation(locationProvider);

		claimRequest.setLat(String.valueOf(lastKnownLocation.getLatitude()));
		claimRequest.setLongs(String.valueOf(lastKnownLocation.getLongitude()));

		return claimRequest;
	}

}
