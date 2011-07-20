package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;

public class Settings extends Activity {

	private boolean flagNewSim = false;
	private ImageButton ib;
	private RealFarmProvider mDataProvider;
	private String origFirstname;
	private String origLastname;
	private int plotNumber = 0;
	// private TelephonyManager telephonyManager;
	private int userId;

	private void addButton(LinearLayout plotLayout, int plotID) {
		Button b = new Button(this);
		b.setText("x, y");
		b.setBackgroundColor(Color.RED);
		b.setOnClickListener(OnClickDoSomething(plotLayout, plotID));
		plotLayout.addView(b);
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
		long plotID = mDataProvider.setPlot(userId);

		plotNumber = plotNumber + 1;

		if (!(plotID > 0))
			tv.setBackgroundColor(Color.RED);

		plotLayout.addView(tv);
		addButton(plotLayout, (int) plotID);

		container2.addView(plotLayout);

		// put back + button
		container2.addView(ib);

	}

	private int[] addPlotxy(int plotID) {

		int[] res = new int[3];

		// use location from the GPS
		int lat = 0;
		int lon = 0;

		getApplicationContext();
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListenerGps = new LocationListener() {

			public void onLocationChanged(Location location) {
				if (location != null) {
					// lat = (int) (location.getLatitude() * 1000000);
					// lon = (int) (location.getLongitude() * 1000000);
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

	public void changeLatLon(int plotID, int lat, int lon, int newLat,
			int newLon) {
		mDataProvider.updatePoint(plotID, lat, lon, newLat, newLon);
		plotList();
	}

	public void deleteLatLon(int plotID, int lat, int lon) {
		mDataProvider.removePoint(plotID, lat, lon);
		plotList();
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

	View.OnClickListener OnClickAllowEdit(final int plotID, final int lat,
			final int lon) {
		return new View.OnClickListener() {

			private EditText text;
			private EditText text2;

			public void editValue(int param) {

				if (param == 1)
					changeLatLon(plotID, lat, lon,
							Integer.parseInt(text.getText().toString()),
							Integer.parseInt(text2.getText().toString()));
				else if (param == 2)
					deleteLatLon(plotID, lat, lon);

			}

			public void onClick(View v) {

				LayoutInflater inflater = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.custom_dialog,
						(ViewGroup) findViewById(R.id.layout_root));

				AlertDialog.Builder alert = new AlertDialog.Builder(
						Settings.this);

				text = (EditText) layout.findViewById(R.id.lat);
				text.setText(Integer.toString(lat));
				text2 = (EditText) layout.findViewById(R.id.lon);
				text2.setText(Integer.toString(lon));

				alert.setView(layout);
				alert.setTitle("Latitude and longitude");
				alert.setMessage("Edit them to change values.");

				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								/* User clicked ok so do some stuff */

								editValue(1);

							}
						});

				alert.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								/* User clicked cancel so do some stuff */
								editValue(2);
							}
						});
				alert.show();

			}
		};

	}

	View.OnClickListener OnClickDoSomething(final LinearLayout plotLayout,
			final int plotID) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				int[] res = addPlotxy(plotID);

				if (res[0] > 0) {
					v.setBackgroundColor(Color.GREEN);
					v.setOnClickListener(Settings.this.OnClickAllowEdit(plotID,
							res[1], res[2]));
					addButton(plotLayout, plotID);
				}
			}
		};
	}

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

		RealFarmApp mainApp = ((RealFarmApp) getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		mDataProvider = new RealFarmProvider(db);

		User tmpUser;

		if (deviceID != null) { // SIM card exists
			// get current information about user.
			tmpUser = mDataProvider.getUserByMobile(deviceID);

			if (tmpUser == null) { // if no information, try to fetch default
									// number in case user configured phone
									// without SIM card
				tmpUser = mDataProvider
						.getUserByMobile(RealFarmDatabase.DEFAULT_NUMBER);
				flagNewSim = true;
			}

		} else { // user must insert a SIM card, use default user mode
			Toast.makeText(getApplicationContext(), "Insert SIM card",
					Toast.LENGTH_SHORT).show();
			tmpUser = mDataProvider
					.getUserByMobile(RealFarmDatabase.DEFAULT_NUMBER);
		}

		// sets the textfields
		firstname.setText(tmpUser.getFirstName());
		lastname.setText(tmpUser.getLastName());

		origFirstname = firstname.getText().toString();
		origLastname = lastname.getText().toString();

		// plot list of things
		plotList();

	}

	private void plotList() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);

		container2.removeAllViews();

		// Set header
		TextView tvHeader = new TextView(this);
		tvHeader.setText(R.string.plot);
		tvHeader.setTextSize(30);
		container2.addView(tvHeader);

		userId = mDataProvider.getUserByMobile(RealFarmDatabase.DEVICE_ID)
				.getUserId();

		// get plot list from db
		List<Plot> poly = mDataProvider.getPlots(userId);
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

			Point[] coords = poly.get(i).getCoordinates();

			for (int j = 0; j < coords.length; j++) {
				Button b = new Button(this);
				b.setText("x, y");
				b.setBackgroundColor(Color.GREEN);
				b.setOnClickListener(OnClickAllowEdit(poly.get(i).getId(),
						coords[j].x, coords[j].y));
				plotLayout.addView(b);
			}
			int plotId = poly.get(i).getId();
			addButton(plotLayout, plotId);
			container2.addView(plotLayout);
		}

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
}
