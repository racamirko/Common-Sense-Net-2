package com.commonsensenet.realfarm;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.overlay.ActionItem;
import com.commonsensenet.realfarm.overlay.MyOverlay;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.Polygon;
import com.commonsensenet.realfarm.overlay.QuickAction;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class realFarmMainActivity extends MapActivity{
	
	LocationManager lm;
	private MapView mapView = null;
	private MapController myMapController;
	
	private PlotOverlay myPlotOverlay;
	private MyOverlay itemizedoverlay ;
	private List<Overlay> mapOverlays ;
	
	
	
	/** Class used to extract the data from the database. */
	private RealFarmProvider mDataProvider;
	
	/** List of Polygons that represent the plots of the user. */
	private List<Polygon> mMyPlots;
	
	public static final GeoPoint CKPURA_LOCATION = new GeoPoint(14054563,77167003);
	
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
		if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null)
			myMapController.animateTo(CKPURA_LOCATION);
		
		// Create data provider
		getApplicationContext().deleteDatabase("realFarm.db");		// comment out if you want to reuse existing database
		realFarm mainApp = ((realFarm)getApplicationContext());
		//RealFarmDatabase db = new RealFarmDatabase(getApplicationContext());
		RealFarmDatabase db = mainApp.setDatabase();
		mDataProvider = new RealFarmProvider(db);
		
		Drawable drawable = getResources().getDrawable(R.drawable.marker);
		itemizedoverlay = new MyOverlay(drawable, getApplicationContext(), mapView);
	    
		// Get mapView overlay
		mapOverlays = mapView.getOverlays();

		// create overlay for field plots
	    myPlotOverlay = new PlotOverlay();
	    
	    
		onUpdateUI();
		
	    
//		// Load sounds
//		mp = MediaPlayer.create(this, R.raw.sound22);
//
//		// Define action on short click on "here" button
//		final Button button = (Button) findViewById(R.id.topBtn);
//		button.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//
//				// Get best location provider given criteria
//				// String provider = lm.getBestProvider(criteria, true);
//
//				LocationListener locationListenerGps = new MyLocationListener();
//				// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
//				// locationListenerGps);
//				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
//						10000, locationListenerGps);
//				// lm.requestLocationUpdates(provider, 0, 0,
//				// locationListenerGps);
//
//			}
//		});
//
//		// Action on long click of here button
//		button.setOnLongClickListener(new View.OnLongClickListener() {
//			public boolean onLongClick(View v) {
//				mp.start();
//				return true;
//			}
//		});
//
//		
	}


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	
	public void onUpdateUI(){
		
		// define action bar
		mMyPlots = mDataProvider.getPlots(1);
		setUpActionBar();
		
	    List<Polygon> mPolygons = mDataProvider.getPlots();
	    for (int x = 0; x < mPolygons.size(); x++) {
	    	myPlotOverlay.addOverlay(mPolygons.get(x));
	    }
	    mapOverlays.add(myPlotOverlay); // Add overlay to mapView
		
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
				
				// remove existing itemizedoverlays 
				mapOverlays.remove(itemizedoverlay);
				itemizedoverlay.removeAll();
				
				// Display icon at my current location
				List<Overlay> mapOverlays = mapView.getOverlays();
				OverlayItem overlayitem = new OverlayItem(p, "Hello!","You are here");
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);

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
			// TODO: add help support
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

	
	
	

	private void setUpActionBar() {
		
		// gets the action bar.
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		
		actionBar.removeAllActions();
		
		// Home action button
		actionBar.setHomeAction(new Action() {
			public void performAction(View view) {
				myMapController.animateTo(CKPURA_LOCATION);

				 List<Overlay> mapOverlays = mapView.getOverlays();
				 
				 mapOverlays.remove(itemizedoverlay);
				 itemizedoverlay.removeAll();
				 
				 OverlayItem overlayitem = new OverlayItem(CKPURA_LOCATION, "Hello!","CKPura");
				 itemizedoverlay.addOverlay(overlayitem);

				 mapOverlays.add(itemizedoverlay);
				
				 
				 mapView.invalidate();
				 
			}
			
			public int getDrawable() {
				return R.drawable.ic_title_home_default;
			}
			
		});

		// locate me action button
		actionBar.addAction(new Action() {

			public void performAction(View view) {
				LocationListener locationListenerGps = new MyLocationListener();
				// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				// locationListenerGps);
				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
						10000, locationListenerGps);
			}
			
			public int getDrawable() {
				return R.drawable.ic_menu_mylocation;
			}
		});

		actionBar.addAction(new Action() {

			public void performAction(View view) {
				final QuickAction qa = new QuickAction(view);

				// creates an action item for each available plot.
				ActionItem tmpItem;
				for (int x = 0; x < mMyPlots.size(); x++) {
					// creates a new item based
					tmpItem = new ActionItem();
					tmpItem.setTitle("Plot " + mMyPlots.get(x).getId());
					tmpItem.setId(x);
					tmpItem.setIcon(getResources().getDrawable(R.drawable.ic_dialog_map));
					tmpItem.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							
							// animates to the center of the plot
							int[] average = mMyPlots.get(v.getId()).getAverageLL();
							myMapController.animateTo(new GeoPoint(average[0], average[1]));
						}

					});
					// adds the item
					qa.addActionItem(tmpItem);
				}

				qa.show();
			}
			
			public int getDrawable() {
				return R.drawable.ic_menu_sort_by_size;
			}
		});

		// news button in action bar
		actionBar.addAction(new Action() {

			public void performAction(View view) {
				final QuickAction qa1 = new QuickAction(view);

				ActionItem tem = new ActionItem();		
				tem.setTitle("No news yet");
				tem.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						int test;
					}
				});
				qa1.addActionItem(tem);
				qa1.show();
				
				
			}
			
			public int getDrawable() {
				return R.drawable.ic_menu_news;
			}
		});
		
		
	}
	
}
