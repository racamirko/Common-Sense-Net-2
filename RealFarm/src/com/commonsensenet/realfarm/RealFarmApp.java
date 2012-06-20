package com.commonsensenet.realfarm;

import android.app.Application;

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

	/** Database used by the application. */
	private RealFarmDatabase mDatabase;

	public RealFarmDatabase getDatabase() {
		return mDatabase;
	}

	public void setDatabase(RealFarmDatabase db) {
		mDatabase = db;
	}
}