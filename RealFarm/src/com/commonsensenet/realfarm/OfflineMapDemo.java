package com.commonsensenet.realfarm;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.R;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.commonsensenet.realfarm.map.GeoPoint;
import com.commonsensenet.realfarm.map.OfflineMapView;
import com.commonsensenet.realfarm.overlay.MyOverlay;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.PopupPanel;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class OfflineMapDemo extends Activity {

	private OfflineMapView mOfflineMap;	
	private LocationManager lm;
	private GeoPoint ckPura;
	private MyOverlay overlayOld = null;
	private PlotOverlay myPlot;
	private MediaPlayer mp;
	private PopupPanel panel;

	
//	<com.markupartist.android.widget.ActionBar
//	android:id="@+id/actionBar" style="@style/ActionBar" />
//  <com.commonsensenet.realfarm.map.OfflineMapView
//	android:id="@+id/offlineMap" android:layout_width="fill_parent"
//	android:layout_height="fill_parent" />
 
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
				// myMapController.setZoom(20);

				// Display icon at my current location

				/*
				List<Overlay> mapOverlays = mOfflineMap.getOverlays();
				Drawable drawable = getResources().getDrawable(
						R.drawable.marker);
				MyOverlay itemizedoverlay = new MyOverlay(drawable, getApplicationContext(), mapView, panel);
				OverlayItem overlayitem = new OverlayItem(p, "Hello!",
						"You are here");
				itemizedoverlay.addOverlay(overlayitem);

				// mapOverlays.removeAll(mapOverlays);
				mOfflineMap.getOverlays().remove(overlayOld);
				mapOverlays.add(itemizedoverlay);
				overlayOld = itemizedoverlay;
				*/
				mOfflineMap.invalidate();
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// sets the layout
		setContentView(R.layout.main);
		
		// gets the map from the UI.
		mOfflineMap = (OfflineMapView) findViewById(R.id.offlineMap);
		
		final ActionItem first = new ActionItem();

		first.setTitle("Dashboard");
		first.setIcon(getResources().getDrawable(R.drawable.dashboard));
		first.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(OfflineMapDemo.this, "Dashboard",
						Toast.LENGTH_SHORT).show();
			}
		});

		final ActionItem second = new ActionItem();

		second.setTitle("Users & Groups");
		second.setIcon(getResources().getDrawable(R.drawable.kontak));
		second.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(OfflineMapDemo.this, "User & Group",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		
		// gets the action bar.
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionBar);
		actionBar.setTitle(R.string.app_name);
		
		// adds the required actions
		actionBar.setHomeAction(new Action() {
			public void performAction(View view) {
				Toast.makeText(OfflineMapDemo.this,
	                    "home action", Toast.LENGTH_SHORT).show();
			}
			public int getDrawable() {
				return R.drawable.ic_home_white;
			}
		});

        actionBar.addAction(new Action() {
			public void performAction(View view) {
				Toast.makeText(OfflineMapDemo.this,
                    "location action", Toast.LENGTH_SHORT).show();				
			}
			public int getDrawable() {
				return R.drawable.ic_location_white;
			}
		});
        
        actionBar.addAction(new Action() {
			public void performAction(View view) {
				
				QuickAction qa = new QuickAction(view);

				qa.addActionItem(first);
				qa.addActionItem(second);

				qa.show();
			}
			public int getDrawable() {
				return R.drawable.ic_crops_white;
			}
		});

        
        actionBar.addAction(new Action() {
			public void performAction(View view) {
				Toast.makeText(OfflineMapDemo.this,
                    "news action", Toast.LENGTH_SHORT).show();				
			}
			public int getDrawable() {
				return R.drawable.ic_news_white;
			}
		});
//		
//		
//		// Define location manager
//		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		
//		// Define overlays
//		List<Overlay> mapOverlays = mOfflineMap.getOverlays();
//		ckPura = new GeoPoint(14054563, 77167003);
//	    		
//		if (lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null)
//			mOfflineMap.animateTo(ckPura);
//		
//	    // Create class managing objects from database 
//		realFarm mainApp = ((realFarm)getApplicationContext());
//		ObjectDatabase od = new ObjectDatabase(mainApp, this, mOfflineMap);
//	    
//	    // Create popup panel that is displayed when tapping on an element
//	    panel = new PopupPanel(R.layout.popup, mOfflineMap, this, od);
//	    
//	    // create overlay for field plots (includes calls to panel when field tap and closing drawer)
//	    // myPlot = new PlotOverlay(panel, slidingDrawer);
//		// mapOverlays.add(myPlot); // Add overlay to mapView
//		
//		// Create plots on overlay and in menu
//		// LinearLayout container = null; // = (LinearLayout) findViewById(R.id.contentplot);
//		// od.managePlots(myPlot, container);		
//		
//		// Criteria on how to obtain location information
//		final Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_FINE);
//		criteria.setAltitudeRequired(false);
//		criteria.setBearingRequired(false);
//		criteria.setCostAllowed(true);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//
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
//		// Define action on home button
//		final Button button2 = (Button) findViewById(R.id.topBtn2);
//		button2.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				
//				mOfflineMap.animateTo(ckPura);
//				// mOfflineMap.setZoom(20);
//
//				
////				List<Overlay> mapOverlays = mOfflineMap.getOverlays();
////
////				Drawable drawable = getResources().getDrawable(
////						R.drawable.marker);
////				MyOverlay itemizedoverlay = new MyOverlay(drawable, getApplicationContext(), mapView, panel);
////
////				OverlayItem overlayitem = new OverlayItem(ckPura, "Hello!",
////						"CKPura");
////				itemizedoverlay.addOverlay(overlayitem);
////
////				// mapOverlays.removeAll(mapOverlays);
////				mapOverlays.add(itemizedoverlay);
//				
//
//			}
//		});

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mOfflineMap.dispose();
	}
}