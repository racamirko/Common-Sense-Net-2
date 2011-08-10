package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.map.GeoPoint;
import com.commonsensenet.realfarm.map.OfflineMapView;
import com.commonsensenet.realfarm.map.Overlay;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.overlay.ActionItem;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.QuickAction;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class OfflineMapDemo extends Activity {
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
				mOfflineMap.animateTo(p);

				// remove existing itemizedoverlays
				// TODO: support itemized overlays.
				// mOfflineMap.remove(itemizedoverlay);
				// itemizedoverlay.removeAll();

				// Display icon at my current location
				// List<Overlay> mapOverlays = mOfflineMap.getOverlays();
				// OverlayItem overlayitem = new OverlayItem(p, "Hello!",
				// "You are here");
				// itemizedoverlay.addOverlay(overlayitem);
				// mapOverlays.add(itemizedoverlay);

				mOfflineMap.invalidate();
			}
		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), provider + " Disabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), provider + " Enabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}

	/** Location of the village used for this demo. */
	public static final GeoPoint CKPURA_LOCATION = new GeoPoint(14054563,
			77167003);

	// private MyOverlay itemizedoverlay;
	/** Manager used to obtain location from the system. */
	private LocationManager lm;
	// private List<Overlay> mapOverlays;
	/** Class used to extract the data from the database. */
	private RealFarmProvider mDataProvider;
	/** List of Polygons that represent the plots of the user. */
	private List<Plot> mMyPlots;
	/** View that handles the map of the area. */
	private OfflineMapView mOfflineMap;

	/**
	 * Intercepts the back button press by the user in the main screen and
	 * requests a confirmation from the user before quitting.
	 * 
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
								OfflineMapDemo.this.finish();
							}
						}).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.main);

		// gets the map from the UI.
		mOfflineMap = (OfflineMapView) findViewById(R.id.offlineMap);

		// sets the items included in the action bar.
		setUpActionBar();

		// Define location manager
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null)
			mOfflineMap.animateTo(CKPURA_LOCATION);

		// Creates the data provider
		// comment out if you want to reuse the existing database.
		getApplicationContext().deleteDatabase("realFarm.db");

		RealFarmApp mainApp = ((RealFarmApp) getApplicationContext());
		RealFarmDatabase db = mainApp.setDatabase();
		mDataProvider = new RealFarmProvider(db);

		// Define overlays
		List<Overlay> mapOverlays = mOfflineMap.getOverlays();

		int userId = mDataProvider.getUserByMobile(RealFarmDatabase.DEVICE_ID)
				.getUserId();

		// adds an overlay for each plot found.
		mMyPlots = mDataProvider.getUserPlots(userId);
		for (int x = 0; x < mMyPlots.size(); x++) {
			mapOverlays.add(new PlotOverlay(mMyPlots.get(x)));
		}

		// Drawable drawable = getResources().getDrawable(R.drawable.marker);
		// itemizedoverlay = new MyOverlay(drawable, getApplicationContext(),
		// mapView);

	}

	/**
	 * Create options menu
	 * 
	 * @param Menu
	 *            Android menu object
	 * @return boolean true if options menu created successfully
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mOfflineMap.dispose();
	}

	/**
	 * Detects presses on options menu and creates corresponding activity
	 * 
	 * @param MenuItem
	 *            clicked menu item
	 * @return boolean true if item pressed
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings:
			Intent myIntent = new Intent(OfflineMapDemo.this, Settings.class);
			startActivity(myIntent);

			return true;
		case R.id.help:
			// TODO: add help support
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sendSMS(String phoneNumber, String message) {
		// PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
		//		OfflineMapDemo.class), 0);
		SmsManager sms = SmsManager.getDefault(); 

		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

	private void setUpActionBar() {

		// gets the action bar.
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.removeAllActions();
		actionBar.setBackgroundColor(Color.LTGRAY);

		// Home action button
		actionBar.setHomeAction(new Action() {
			public int getDrawable() {
				return R.drawable.ic_48px_home;
			}

			public void performAction(View view) {
				mOfflineMap.animateTo(CKPURA_LOCATION);

				// TODO: check this
				// List<Overlay> mapOverlays = mOfflineMap.getOverlays();

				// mapOverlays.remove(itemizedoverlay);
				// itemizedoverlay.removeAll();

				// OverlayItem overlayitem = new OverlayItem(CKPURA_LOCATION,
				// "Hello!", "CKPura");
				// itemizedoverlay.addOverlay(overlayitem);

				// mapOverlays.add(itemizedoverlay);

				mOfflineMap.invalidate();

				sendSMS("+41762348225", "Esta es una prueba");

			}

		});

		// locate me action button
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_48px_myposition;
			}

			public void performAction(View view) {
				LocationListener locationListenerGps = new MyLocationListener();
				// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				// locationListenerGps);
				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
						0, locationListenerGps);
			}
		});

		// my field
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_48px_myfields;
			}

			public void performAction(View view) {
				final QuickAction qa = new QuickAction(view);

				// creates an action item for each available plot.
				ActionItem tmpItem;
				for (int x = 0; x < mMyPlots.size(); x++) {
					// creates a new item based
					tmpItem = new ActionItem();
					tmpItem.setTitle("Plot " + mMyPlots.get(x).getId());
					tmpItem.setId(x);
					tmpItem.setIcon(getResources().getDrawable(
							R.drawable.ic_dialog_map));
					tmpItem.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {

							// animates to the center of the plot
							Point center = mMyPlots.get(v.getId())
									.getCenterCoordinate();
							mOfflineMap.animateTo(new GeoPoint(center.x,
									center.y));
						}

					});
					// adds the item
					qa.addActionItem(tmpItem);
				}

				qa.show();
			}
		});

		// news button in action bar
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_48px_news;
			}

			public void performAction(View view) {
				final QuickAction qa1 = new QuickAction(view);

				ActionItem tem = new ActionItem();
				tem.setTitle("No news yet");
				tem.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {

					}
				});
				qa1.addActionItem(tem);
				qa1.show();

			}
		});

	}
}