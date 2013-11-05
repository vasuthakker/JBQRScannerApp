package com.JBCosmetics.jbqrscannerapp.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.JBCosmetics.jbqrscannerapp.R;
import com.JBCosmetics.jbqrscannerapp.fragments.HomeAccountFragment;
import com.JBCosmetics.jbqrscannerapp.fragments.HomeMapFragment;
import com.JBCosmetics.jbqrscannerapp.fragments.HomeMiddleFragment;

public class HomeActivity extends FragmentActivity {

	private static ViewPager homeBaseViewpager;

	private RelativeLayout accountBtnLayout;
	private RelativeLayout cardBtnLayout;
	private RelativeLayout mapBtnLayout;
	private RelativeLayout homeHeaderLayout;

	private LinearLayout menuLayout;

	private static final int TOTAL_FRAGMENTS = 3;

	private static final int ACCOUNT_FRAGMENT_POS = 0;
	private static final int CARD_FRAGMENT_POS = 1;
	private static final int MAP_FRAGMENT_POS = 2;

	private static final String LOYALTY_CARD = "LOYALTY CARD";
	private static final String MY_ACCOUNT = "MY ACCOUNT";
	private static final String LOCATIONS = "LOCATIONS";

	private ImageView homeHeaderImageView;
	private TextView homeHeaderTitleTextView;

	private Drawable accountImageDrawable;
	private Drawable cardImageDrawable;
	private Drawable locationImageDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// UI element initialization
		homeBaseViewpager = (ViewPager) findViewById(R.id.home_viewpager);

		// relative layouts
		accountBtnLayout = (RelativeLayout) findViewById(R.id.home_account_btn_imageview);
		cardBtnLayout = (RelativeLayout) findViewById(R.id.home_card_btn_imageview);
		mapBtnLayout = (RelativeLayout) findViewById(R.id.home_map_btn_imageview);
		homeHeaderLayout = (RelativeLayout) findViewById(R.id.home_header_layout);

		// linear layout
		menuLayout = (LinearLayout) findViewById(R.id.menu_layout);

		// textview
		homeHeaderTitleTextView = (TextView) findViewById(R.id.home_header_title_textview);

		// imageview
		homeHeaderImageView = (ImageView) findViewById(R.id.home_header_imageview);

		// getting ids of image views
		accountImageDrawable = getResources().getDrawable(
				R.drawable.account_button);
		cardImageDrawable = getResources().getDrawable(R.drawable.card_button);
		locationImageDrawable = getResources().getDrawable(
				R.drawable.map_button);

		// viewpager adapter
		homeBaseViewpager.setAdapter(new ViewPagerElements(
				getSupportFragmentManager()));

		homeBaseViewpager.setCurrentItem(CARD_FRAGMENT_POS);

		// home header layout on click listener
		homeHeaderLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (menuLayout.getVisibility() != View.VISIBLE) {
					menuLayout.setVisibility(View.VISIBLE);
				} else {
					menuLayout.setVisibility(View.GONE);
				}
			}
		});

		accountBtnLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// changing to account page
				homeBaseViewpager.setCurrentItem(ACCOUNT_FRAGMENT_POS, false);
				menuLayout.setVisibility(View.GONE);

				homeHeaderImageView.setImageDrawable(accountImageDrawable);
				homeHeaderTitleTextView.setText(MY_ACCOUNT);
			}
		});
		cardBtnLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// changing to account page
				homeBaseViewpager.setCurrentItem(CARD_FRAGMENT_POS, false);
				menuLayout.setVisibility(View.GONE);

				homeHeaderImageView.setImageDrawable(cardImageDrawable);
				homeHeaderTitleTextView.setText(LOYALTY_CARD);
			}
		});
		mapBtnLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// changing to account page
				homeBaseViewpager.setCurrentItem(MAP_FRAGMENT_POS, false);
				menuLayout.setVisibility(View.GONE);

				homeHeaderImageView.setImageDrawable(locationImageDrawable);
				homeHeaderTitleTextView.setText(LOCATIONS);
			}
		});

		homeBaseViewpager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (menuLayout.getVisibility() == View.VISIBLE) {
					menuLayout.setVisibility(View.GONE);
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Intent refresh = new Intent(this, HomeActivity.class);
			startActivity(refresh);
			this.finish();
		}
	}

	private class ViewPagerElements extends FragmentStatePagerAdapter {

		public ViewPagerElements(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int fragment_pos) {
			Fragment fragment = null;
			switch (fragment_pos) {
			case ACCOUNT_FRAGMENT_POS:
				fragment = new HomeAccountFragment();
				break;
			case CARD_FRAGMENT_POS:
				fragment = new HomeMiddleFragment();
				break;
			case MAP_FRAGMENT_POS:
				fragment = new HomeMapFragment();
				break;

			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return TOTAL_FRAGMENTS;
		}

	}

	public static void changePageToAccountPage() {
		homeBaseViewpager.setCurrentItem(ACCOUNT_FRAGMENT_POS);

	}
}
