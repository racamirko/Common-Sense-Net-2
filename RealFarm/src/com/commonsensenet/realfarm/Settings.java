package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.admin.admincall;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;

public class Settings extends Activity {

	private ImageButton ib;
	private RealFarmProvider mDataProvider;
	private String mDeviceId;
	private EditText mDeviceIdTextField;
	private EditText mFirstnameTextField;
	private boolean mIsNewSim = false;
	private EditText mLastnameTextField;
	private String mOriginalFirstname;
	private String mOriginalLastname;
	private int mPlotId = 0;
	private int mUserId;

	private void addButton(LinearLayout plotLayout, int plotId) {
		Button b = new Button(this);
		b.setText("x, y");
		b.setBackgroundColor(Color.RED);
		b.setOnClickListener(OnClickDoSomething(plotLayout, plotId));
		plotLayout.addView(b);

	}

	/**
	 * Manage list of things to plot
	 */
	// TODO: add plot is not functional.
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

		tv.setText("plot " + mPlotId);
		long plotID = -1;
		// mDataProvider.addPlot(userId, seedTypeId, imagePath,
		// soilType, size, 0, 0);

		mPlotId = mPlotId + 1;

		if (!(plotID > 0)) {
			tv.setBackgroundColor(Color.RED);
		}

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
		long result = -1;// mDataProvider.setPoint(plotID, lat, lon);

		res[0] = (int) result;
		res[1] = lat;
		res[2] = lon;
		return res;
	}

	public void changeLatLon(int plotID, int lat, int lon, int newLat,
			int newLon) {
		// mDataProvider.updatePoint(plotID, lat, lon, newLat, newLon);
		plotList();
	}

	public void deleteLatLon(int plotID, int lat, int lon) {
		// mDataProvider.removePoint(plotID, lat, lon);
		plotList();
	}

	@Override
	public void onBackPressed() {

		// before closing check if user has defined his name
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
		EditText lastname = (EditText) this.findViewById(R.id.editText2);
		String firstnameString = firstname.getText().toString();
		String lastnameString = lastname.getText().toString();

		long result = 0;

		// user has no sim card, use default number
		if (mDeviceId == null) {
			mDeviceId = RealFarmDatabase.DEFAULT_NUMBER;
		}

		// user inserted a sim card and already had
		// account => update device id information
		if (mIsNewSim == true) {

			// TODO: indicate the image path
			result = mDataProvider.addUser(mDeviceId, firstnameString,
					lastnameString, "", 0, 0);
			// detect changes
		} else if ((!firstnameString.equalsIgnoreCase(mOriginalFirstname))
				|| (!lastnameString.equalsIgnoreCase(mOriginalLastname))) {
			result = mDataProvider.addUser(mDeviceId, firstnameString,
					lastnameString, "", 0, 0);
		}

		// success
		if (result > 0) {
			Toast.makeText(getApplicationContext(), "User created/modified",
					Toast.LENGTH_SHORT).show();
		}

		// finish();
		Intent adminintent = new Intent(Settings.this, admincall.class);

		startActivity(adminintent);
		Settings.this.finish();

	}

	View.OnClickListener OnClickAllowEdit(final int plotID, final int lat,
			final int lon) {
		return new View.OnClickListener() {

			// private EditText text;
			// private EditText text2;

			// public void editValue(int param) {
			//
			// if (param == 1)
			// changeLatLon(plotID, lat, lon,
			// Integer.parseInt(text.getText().toString()),
			// Integer.parseInt(text2.getText().toString()));
			// else if (param == 2)
			// deleteLatLon(plotID, lat, lon);
			//
			// }

			public void onClick(View v) {

				// LayoutInflater inflater = (LayoutInflater)
				// getApplicationContext()
				// .getSystemService(LAYOUT_INFLATER_SERVICE);
				// View layout = inflater.inflate(
				// R.layout.settings_add_point_dialog,
				// (ViewGroup) findViewById(R.id.layout_root));
				//
				// AlertDialog.Builder alert = new AlertDialog.Builder(
				// Settings.this);
				//
				// text = (EditText) layout.findViewById(R.id.lat);
				// text.setText(Integer.toString(lat));
				// text2 = (EditText) layout.findViewById(R.id.lon);
				// text2.setText(Integer.toString(lon));
				//
				// alert.setView(layout);
				// alert.setTitle(R.string.editPoint);
				// alert.setMessage(R.string.valuemicro);
				//
				// alert.setPositiveButton("ok",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int whichButton) {
				// /* User clicked ok so do some stuff */
				//
				// editValue(1);
				//
				// }
				// });
				//
				// alert.setNegativeButton("Delete",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int whichButton) {
				//
				// /* User clicked cancel so do some stuff */
				// editValue(2);
				// }
				// });
				// alert.show();

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

		mFirstnameTextField = (EditText) findViewById(R.id.editText1); // First
																		// name
		mLastnameTextField = (EditText) findViewById(R.id.editText2);
		mDeviceIdTextField = (EditText) this.findViewById(R.id.MobileNo);
		Button OK = (Button) findViewById(R.id.OK); // Prakruthi

		// String deviceID = RealFarmDatabase.DEVICE_ID;
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		mDeviceId = telephonyManager.getLine1Number();

		EditText firstname = (EditText) this.findViewById(R.id.editText1);
		EditText lastname = (EditText) this.findViewById(R.id.editText2);

		// hide soft keyboard by default
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		mDataProvider = RealFarmProvider.getInstance(getApplicationContext());

		if (mDeviceId != null) { // sim card exists

			User user = mDataProvider
					.getUserByDeviceId(RealFarmDatabase.DEVICE_ID);

			RealFarmDatabase.DEVICE_ID = mDeviceId; // update main device ID

			// if no information, try to fetch the default.
			if (user == null || user.getFirstname() == null) {
				// number in case user configured phone
				// without sim card
				user = mDataProvider
						.getUserByDeviceId(RealFarmDatabase.DEVICE_ID);
				mIsNewSim = true;
			}

			firstname.setText(user.getFirstname());
			lastname.setText(user.getLastname());
		} else { // user must insert a sim card, use default user mode
			Toast.makeText(getApplicationContext(), "Insert SIM card",
					Toast.LENGTH_SHORT).show();
			User user = mDataProvider
					.getUserByDeviceId(RealFarmDatabase.DEVICE_ID);

			firstname.setText(user.getFirstname());
			lastname.setText(user.getLastname());
		}

		mOriginalFirstname = firstname.getText().toString();
		mOriginalLastname = lastname.getText().toString();

		/*
		 * Manage list of plots
		 */

		// plot list of things
		plotList();

		OK.setOnClickListener(new View.OnClickListener() { // Prakruthi
			public void onClick(View v) {

				// UserDetailsDatabase();
				System.out.println("In OK button of settings");
				addUserToDatabase();
				Intent adminintent = new Intent(Settings.this, admincall.class);
				startActivity(adminintent);
				Settings.this.finish();

			}
		});

	}

	private void plotList() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);

		container2.removeAllViews();

		// Set header
		TextView tvHeader = new TextView(this);
		tvHeader.setText(R.string.plot);
		tvHeader.setTextSize(30);
		container2.addView(tvHeader);

		mUserId = mDataProvider.getUserByDeviceId(RealFarmDatabase.DEVICE_ID)
				.getId();

		// get plot list from db
		List<Plot> poly = mDataProvider.getPlotsByUserId(mUserId);

		mPlotId = poly.size();

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

	public void addUserToDatabase() {

		String firstname = mFirstnameTextField.getText().toString();
		String lastname = mLastnameTextField.getText().toString();
		String deviceId = mDeviceIdTextField.getText().toString();

		mDeviceId = RealFarmDatabase.DEFAULT_NUMBER;

		mDataProvider.addUser(deviceId, firstname, lastname, "", 0, 0);
		Toast.makeText(getBaseContext(), "User Details is put to Database",
				Toast.LENGTH_SHORT).show();

	}

}
