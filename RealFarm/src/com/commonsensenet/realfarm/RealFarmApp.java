package com.commonsensenet.realfarm;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;

/**
 * Main class of application. This class is used to share global information
 * across activities such as the database
 * 
 * @author Julien Freudiger
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class RealFarmApp extends Application {
	/** Defines the theme that will be used. */
	public static int THEME = R.style.Theme_Sherlock_Light;
	/** Database used by the application. */
	private RealFarmDatabase mDatabase;
	/** DeviceId of the current phone where the App is running. */
	private String mDeviceId;

	public RealFarmDatabase getDatabase() {
		return mDatabase;
	}

	public void setDatabase(RealFarmDatabase db) {
		mDatabase = db;
	}

	public String getDeviceId() {

		// if the value is null it is table from the database.
		if (mDeviceId == null) {

			// gets the device id of the current user
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			mDeviceId = telephonyManager.getLine1Number();

			// sets the default value if invalid.
			if (mDeviceId == null) {
				mDeviceId = RealFarmDatabase.DEFAULT_NUMBER;
			}
		}

		return mDeviceId;
	}
}