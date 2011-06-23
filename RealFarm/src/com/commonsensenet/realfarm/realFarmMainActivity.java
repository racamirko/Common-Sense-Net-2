package com.commonsensenet.realfarm;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.commonsensenet.realfarm.database.ObjectDatabase;
import com.commonsensenet.realfarm.overlay.MyOverlay;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.PopupPanel;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class realFarmMainActivity extends MapActivity{

	private MapController myMapController;
	private SlidingDrawer slidingDrawer;
	Button drawerButton;
	LocationManager lm;
	private MapView mapView = null;
	private GeoPoint ckPura;
	private PopupPanel panel;
	private MediaPlayer mp;
	private MyOverlay overlayOld = null;
	private PlotOverlay myPlot;
	
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

		
	    		
		if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null)
			myMapController.animateTo(ckPura);

			

		// Create slider button
		drawerButton = (Button) findViewById(R.id.drawerHandle);

		// Create slider
		slidingDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer);
		slidingDrawer.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (slidingDrawer.isOpened())
				{
					if (slidingDrawer.getHandle().isPressed()) // default behavior
						return false;
					return true; // else do not do anything
				}
				return false; // else default behavior
			}
		});
		

	    // Create class managing objects from database 
		realFarm mainApp = ((realFarm)getApplicationContext());
		ObjectDatabase od = new ObjectDatabase(mainApp, this, mapView);
	    
	    // Create popup panel that is displayed when tapping on an element
	    panel = new PopupPanel(R.layout.popup, mapView, this, od);
	    
	    // create overlay for field plots (includes calls to panel when field tap and closing drawer)
	    myPlot = new PlotOverlay(panel, slidingDrawer);
		mapOverlays.add(myPlot); // Add overlay to mapView
		
		// Create plots on overlay and in menu
		LinearLayout container = (LinearLayout) findViewById(R.id.contentplot);
		od.managePlots(myPlot, container);		
		
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

	}


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


}
