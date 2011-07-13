package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.overlay.Polygon;

public class Settings extends Activity {

	private TelephonyManager telephonyManager;
	private RealFarmProvider mDataProvider;

	private String origFirstname;
	private String origLastname;
	private boolean flagNewSim = false;
	private ImageButton ib;
	private int plotNumber = 0;
	private int userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		String deviceID = RealFarmDatabase.DEVICE_ID;

		EditText firstname = (EditText) this.findViewById(R.id.editText1);
		EditText lastname = (EditText) this.findViewById(R.id.editText2);

		// hide soft keyboard by default
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		realFarm mainApp = ((realFarm) getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		mDataProvider = new RealFarmProvider(db);

		if (deviceID != null) { // sim car exists
			String[] name = mDataProvider.getUserInfo(deviceID); // get current
																	// information
																	// about
																	// user

			if (name[0] == null) { // if no information, try to fetch default
									// number in case user configured phone
									// without sim card
				name = mDataProvider
						.getUserInfo(RealFarmDatabase.DEFAULT_NUMBER);
				flagNewSim = true;
			}

			firstname.setText(name[0]);
			lastname.setText(name[1]);
		} else { // user must insert a sim card, use default user mode
			Toast.makeText(getApplicationContext(), "Insert SIM card",
					Toast.LENGTH_SHORT).show();
			String[] name = mDataProvider
					.getUserInfo(RealFarmDatabase.DEFAULT_NUMBER);
			firstname.setText(name[0]);
			lastname.setText(name[1]);
		}

		origFirstname = firstname.getText().toString();
		origLastname = lastname.getText().toString();

		/*
		 * Manage list of plots
		 */
		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);
		container2.removeAllViews();

		// Set header
		TextView tv = new TextView(this);
		tv.setText(R.string.plot);
		tv.setTextSize(30);
		container2.addView(tv);

		// plot list of things
		plotList();

		// Set + button
		ib = new ImageButton(this);
		ib.setImageResource(android.R.drawable.ic_input_add);

		ib.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addPlot();
			}
		});

		container2.addView(ib);

	}

	private void plotList() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);

		userId = mDataProvider.getUserId(RealFarmDatabase.DEVICE_ID);

		// get plot list from db
		List<Polygon> poly = mDataProvider.getPlots(userId);
		plotNumber = poly.size();

		// for each stored row
		for (int i = 0; i < plotNumber; i++) {

			LinearLayout plotLayout = new LinearLayout(this);
			LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			plotLayout.setLayoutParams(layoutParams);
			plotLayout.setWeightSum(1);

			TextView tv = new TextView(this);
			tv.setText("plot " + i);
			plotLayout.addView(tv);

			int[] lat = poly.get(i).getLat();
			int[] lon = poly.get(i).getLon();

			for (int j = 0; j < lat.length; j++) {
				Button b = new Button(this);
				// b.setText(valueX.get(j) + ", " + valueY.get(j));
				b.setText("x, y");
				b.setBackgroundColor(Color.GREEN);
				b.setOnClickListener(OnClickAllowEdit(lat[j], lon[j]));
				plotLayout.addView(b);
			}
			int plotId = poly.get(i).getId();
			addButton(plotLayout, plotId);
			container2.addView(plotLayout);
		}
	}

	/**
	 * Manage list of things to plot
	 */
	private void addPlot() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);
		container2.removeView(ib); // remove + button

		// add new linear layout for new plot
		LinearLayout plotLayout = new LinearLayout(this);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		plotLayout.setLayoutParams(layoutParams);
		plotLayout.setWeightSum(1);

		// add plot header
		TextView tv = new TextView(this);

		tv.setText("plot " + plotNumber);
		long plotID = mDataProvider.setPlot(plotNumber, userId);

		plotNumber = plotNumber + 1;

		if (!(plotID > 0))
			tv.setBackgroundColor(Color.RED);

		plotLayout.addView(tv);
		addButton(plotLayout, (int) plotID);

		container2.addView(plotLayout);

		// put back + button
		container2.addView(ib);

	}

	private void addButton(LinearLayout plotLayout, int plotID) {
		Button b = new Button(this);
		b.setText("x, y");
		b.setBackgroundColor(Color.RED);
		b.setOnClickListener(OnClickDoSomething(plotLayout, plotID));
		plotLayout.addView(b);
	}

	View.OnClickListener OnClickDoSomething(final LinearLayout plotLayout,
			final int plotID) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				int[] res = addPlotxy(plotID);

				if (res[0] > 0) {
					v.setBackgroundColor(Color.GREEN);
					v.setOnClickListener(Settings.this.OnClickAllowEdit(res[1], res[2]));
					addButton(plotLayout, plotID);
				}
			}
		};
	}

	View.OnClickListener OnClickAllowEdit(final int lat, final int lon) {
		return new View.OnClickListener() {
			public void onClick(View v) {

				
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.custom_dialog,
				                               (ViewGroup) findViewById(R.id.layout_root));

				AlertDialog.Builder alert = new AlertDialog.Builder(Settings.this);
				
				EditText text = (EditText) layout.findViewById(R.id.lat);
				text.setText(Integer.toString(lat));
				EditText text2 = (EditText) layout.findViewById(R.id.lon);
				text2.setText(Integer.toString(lon));		
				
				alert.setView(layout);
				alert.setTitle("Latitude and longitude");
				alert.setMessage("Edit them to change values.");

				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								/* User clicked ok so do some stuff */
							}
						});

				alert.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								/* User clicked cancel so do some stuff */
							}
						});
				alert.show();

			}
		};
	}

	private int[] addPlotxy(int plotID) {

		int[] res = new int[3];
		
		// use location from gps
		int lat = 0;
		int lon = 0;

		LocationManager lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

		LocationListener locationListenerGps = new LocationListener() {

			int lat;
			int lon;

			public void onLocationChanged(Location location) {
				if (location != null) {
					lat = (int) (location.getLatitude() * 1000000);
					lon = (int) (location.getLongitude() * 1000000);
				}
			}

			public void onProviderDisabled(String provider) {

			}

			public void onProviderEnabled(String provider) {

			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};

		// Request location
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10000,
				locationListenerGps);

		Location loc = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (loc != null) {
			lat = (int) (loc.getLatitude() * 1000000);
			lon = (int) (loc.getLongitude() * 1000000);
		}

		// add plot to list and coordinates
		long result = mDataProvider.setPoint(plotID, lat, lon);

		res[0] = (int) result;
		res[1] = lat; 
		res[2] = lon;
		return res;
	}

	@Override
	public void onBackPressed() {

		// before closing check if user has defined his name
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
		EditText lastname = (EditText) this.findViewById(R.id.editText2);
		String firstnameString = firstname.getText().toString();
		String lastnameString = lastname.getText().toString();
		String deviceID = RealFarmDatabase.DEVICE_ID;
		long result = 0;

		if (deviceID == null) // user has no sim card, use default number
			deviceID = RealFarmDatabase.DEFAULT_NUMBER;

		if (flagNewSim == true) // user inserted a sim card
			result = mDataProvider.setUserInfo(deviceID, firstnameString,
					lastnameString);

		// detect changes
		if ((!firstnameString.equalsIgnoreCase(origFirstname))
				|| (!lastnameString.equalsIgnoreCase(origLastname))) // user
																		// changed
																		// something
		{
			result = mDataProvider.setUserInfo(deviceID, firstnameString,
					lastnameString);
		}

		if (result > 0) { // success
			Toast.makeText(getApplicationContext(), "User created/modified",
					Toast.LENGTH_SHORT).show();
		}

		finish();
	}
}
