package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class realFarmMainActivity extends MapActivity {

	private MapController myMapController;
	private SlidingDrawer slidingDrawer;
	Button drawerButton;
	LocationManager lm;
	private MapView mapView = null;
	private GeoPoint ckPura;
	private ManageDatabase db;
	private PopupPanel panel;
	private MediaPlayer mp;
	private MyOverlay overlayOld = null;
	private ArrayList<GeoPoint> pointToFocus; 
	/**
	 * default method called by android on activity creation
	 * 
	 * @param Bundle
	 *            current state of application
	 * @author Julien Freudiger
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Load layout
		setContentView(R.layout.main);

		// Define map
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setSatellite(true);
		myMapController = mapView.getController();
		myMapController.setZoom(20);

		// Define location manager
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Define overlays
		List<Overlay> mapOverlays = mapView.getOverlays();
		ckPura = new GeoPoint(14054563, 77167003);

		if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null) {
			myMapController.animateTo(ckPura);
		}
		
		// Create slider button
		drawerButton = (Button) findViewById(R.id.drawerHandle);

		// Create slider
		slidingDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer);

		// Create popup panel that is displayed when tapping on an element
		panel = new PopupPanel(R.layout.popup);

	    realFarm mainApp = ((realFarm)getApplicationContext());
		db = mainApp.setDatabase();
		db.open();
		db.initValues();
		db.close();
		PlotOverlay myPlot = new PlotOverlay(db, this, panel, slidingDrawer );
		mapOverlays.add(myPlot);

		// Criteria on how to obtain location information
		final Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		// Load sounds
		mp = MediaPlayer.create(this, R.raw.sound22);

		// Define action on short click on "here" button
		final Button button = (Button) findViewById(R.id.topBtn);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Get best location provider given criteria
				// String provider = lm.getBestProvider(criteria, true);

				LocationListener locationListenerGps = new MyLocationListener();
				// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				// locationListenerGps);
				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
						10000, locationListenerGps);
				// lm.requestLocationUpdates(provider, 0, 0,
				// locationListenerGps);

			}
		});

		// Action on long click of here button
		button.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {

				mp.start();
				// Get best location provider given criteria
				// String provider = lm.getBestProvider(criteria, true);

				// LocationListener locationListenerGps = new
				// MyLocationListener();
				// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				// locationListenerGps);
				// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				// 0, 10000, locationListenerGps);
				// lm.requestLocationUpdates(provider, 0, 0,
				// locationListenerGps);

				return true;
			}
		});

		// Define action on home button
		final Button button2 = (Button) findViewById(R.id.topBtn2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				myMapController.animateTo(ckPura);
				myMapController.setZoom(20);

				List<Overlay> mapOverlays = mapView.getOverlays();

				Drawable drawable = getResources().getDrawable(
						R.drawable.marker);
				MyOverlay itemizedoverlay = new MyOverlay(drawable, getApplicationContext(), mapView, panel);

				OverlayItem overlayitem = new OverlayItem(ckPura, "Hello!",
						"CKPura");
				itemizedoverlay.addOverlay(overlayitem);

				// mapOverlays.removeAll(mapOverlays);
				mapOverlays.add(itemizedoverlay);

			}
		});


		
		// manage plots in menu
		managePlots();

	}

//	public ManageDatabase getDatabase() {
//		return db;
//	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/*
	 * location class
	 */

	/**
	 * Class that listens to location requests
	 * 
	 * @author Julien Freudiger
	 */
	public class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			if (location != null) {
				int lat = (int) (location.getLatitude() * 1000000);
				int lng = (int) (location.getLongitude() * 1000000);
				GeoPoint p = new GeoPoint(lat, lng);
				myMapController.animateTo(p);
				myMapController.setZoom(20);

				// Display icon at my current location

				List<Overlay> mapOverlays = mapView.getOverlays();
				Drawable drawable = getResources().getDrawable(
						R.drawable.marker);
				MyOverlay itemizedoverlay = new MyOverlay(drawable, getApplicationContext(), mapView, panel);
				OverlayItem overlayitem = new OverlayItem(p, "Hello!",
						"You are here");
				itemizedoverlay.addOverlay(overlayitem);

				// mapOverlays.removeAll(mapOverlays);
				mapView.getOverlays().remove(overlayOld);
				mapOverlays.add(itemizedoverlay);
				overlayOld = itemizedoverlay;
				mapView.invalidate();
			}

		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), provider + " Disabled",Toast.LENGTH_SHORT).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), provider + " Enabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

	/*
	 * Popup class
	 */

	/**
	 * Class that defines popup format
	 * 
	 * @author Julien Freudiger
	 */
	class PopupPanel {
		View popup;
		boolean isVisible = false;
		ViewGroup parent;
		LayoutInflater inflater;

		// Constructor
		PopupPanel(int layout) {

			parent = (ViewGroup) mapView.getParent();
			popup = getLayoutInflater().inflate(layout, parent, false);
			popup.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					hide();
				}
			});

		}

		View getView() {
			return (popup);
		}

		void show(boolean alignTop) {

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			if (alignTop) {
				lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				lp.setMargins(0, 20, 0, 0);
			} else {
				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.setMargins(0, 0, 0, 60);
			}

			hide();

			((ViewGroup) mapView.getParent()).addView(popup, lp);

			isVisible = true;
		}

		void hide() {
			if (isVisible) {
				isVisible = false;
				((ViewGroup) popup.getParent()).removeView(popup);
			}
		}
	}

	/*
	 * Menu definition
	 */

	/**
	 * Create options menu
	 * 
	 * @param Menu
	 *            android menu object
	 * @return boolean true if options menu created successfully
	 * @author Julien Freudiger
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Detects presses on options menu and creates corresponding activity
	 * 
	 * @param MenuItem
	 *            clicked menu item
	 * @return boolean true if item pressed
	 * @author Julien Freudiger
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings:
			Intent myIntent = new Intent(realFarmMainActivity.this, Settings.class);
			startActivity(myIntent);

			return true;
		case R.id.help:
			// todo: add help support
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Intercept back button press by user in main screen and request quitting
	 * confirmation from user
	 * 
	 * @author Julien Freudiger
	 */
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exitTitle)
				.setMessage(R.string.exitMsg)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Exit the activity
								realFarmMainActivity.this.finish();
							}
						}).show();

	}

	

	/**
	 * Manage submenu of plots
	 * 
	 * @author Julien Freudiger
	 * @return boolean indicating success/failure of operations
	 */
	public boolean managePlots() {
		int[] averageX= null;
		int[] averageY= null;
		
		db.open();

		// query all plots of a given user
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getDeviceId();

		Cursor c = db.GetEntries("users", new String[] {"id"}, "mobileNumber=" + deviceID, null, null, null, null);
    	
		

		c.moveToFirst();
		int id = c.getInt(0);
		
		//Cursor c1 = db.GetEntries("plots", new String[] {"id"}, "userID ="+ id, null, null, null, null);
		Cursor c1 = db.GetEntries("plots", new String[] {"id"}, "userID ="+ 1, null, null, null, null);
		//Toast.makeText(getApplicationContext(), "c1"+c1.getCount(),Toast.LENGTH_SHORT).show();
		if (c1.getCount()>0) {
			c1.moveToFirst();
			int j = 0;
			averageX = new int[c1.getCount()];
			averageY = new int[c1.getCount()];
			
			do { // for each plot
				Cursor c2 = db.GetEntries("points", new String[] {"x", "y"}, "plotID ="+ c1.getInt(0), null, null, null, null);
				c2.moveToFirst();
				float[] x = new float[c2.getCount()];
				float[] y = new float[c2.getCount()];
				int k = 0;
				do {
					// compute average point
					x[k] = (float)c2.getInt(0);
					y[k] = (float)c2.getInt(1);
					k=k+1;
				}
				while (c2.moveToNext());
				
				Arrays.sort(x);
				Arrays.sort(y);
				averageX[j]= (int) x[0] - (int) ((x[0] - x[x.length-1])/2);
				averageY[j]= (int) y[0] - (int) (y[0] - y[y.length-1])/2;
				j = j + 1;
			}
			while (c1.moveToNext());
		}

		db.close();

		// Load layout in which to add buttons
		LinearLayout container = (LinearLayout) findViewById(R.id.contentplot);

		// for each plot entry, add button
		pointToFocus = new ArrayList<GeoPoint>(c1.getCount());
		for (int i = 0; i < c1.getCount(); i++) {
			Button b = new Button(this);
			b.setText("ref" + i);
			pointToFocus.add(new GeoPoint(averageX[i], averageY[i]));
			//Toast.makeText(getApplicationContext(), "ptf:"+pointToFocus ,Toast.LENGTH_SHORT).show();
			b.setId(i);
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					myMapController.animateTo(pointToFocus.get(v.getId()));
				}
			});
			
			container.addView(b);
			
		}

		return true;

	}

}
