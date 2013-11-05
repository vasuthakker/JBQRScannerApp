package com.JBCosmetics.jbqrscannerapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbscannerapp.common.Utility;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeMapFragment extends Fragment {
	private GoogleMap map;
	private SupportMapFragment mapFragment;
	private static final LatLng BRISTOL = new LatLng(51.481589, -2.512764);
	private static final LatLng BATH = new LatLng(51.386328, -2.362029);
	private static final LatLng LANGFORD = new LatLng(51.350371, -2.784327);

	private static final String BRISTOL_TITLE = "BRISTOL";
	private static final String BATH_TITLE = "BATH";
	private static final String LANGFORD_TITLE = "LANGFORD";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_map_layout, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		try {
			MapsInitializer.initialize(getActivity());
			ViewGroup mapHost = (ViewGroup) getActivity()
					.findViewById(R.id.map);
			mapHost.requestTransparentRegion(mapHost);

			mapFragment = (SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.map);

			if (mapFragment != null) {
				map = mapFragment.getMap();
				Marker bristol = map.addMarker(new MarkerOptions()
						.position(BRISTOL)
						.title(BRISTOL_TITLE)
						.snippet("37 High Street\nStaple Hill\nBristol")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.map_pin)));
				Marker bath = map.addMarker(new MarkerOptions()
						.position(BATH)
						.title(BATH_TITLE)
						.snippet("1 Saville Row\nBath")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.map_pin)));

				Marker langford = map.addMarker(new MarkerOptions()
						.position(LANGFORD)
						.title(LANGFORD_TITLE)
						.snippet("Poplar Farm\nStock Lane\nLangford\nBristol")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.map_pin)));

				// Move the camera instantly to hamburg with a zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(BATH, 15));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
			}
		} catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(getActivity(),
					getString(R.string.errormsg_maperror), Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			Utility.sendReport(e);
		
		}

	}

	@Override
	public void onPause() {
		super.onPause();

		if (mapFragment != null) {
			FragmentManager fM = getFragmentManager();
			fM.beginTransaction().remove(mapFragment).commit();
		}

	}
}
