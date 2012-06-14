package com.commonsensenet.realfarm;

import android.app.Application;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;

/**
 * Main class of application. This class is used to share global information
 * across activities such as the database
 * 
 * @author Julien Freudiger
 * 
 */
public class RealFarmApp extends Application {

	private RealFarmDatabase db;
	private int mId = 0;

	public RealFarmDatabase getDatabase() {
		return db;
	}

	public int getUserId() {
		return mId;
	}

	public RealFarmDatabase setDatabase() {
		db = new RealFarmDatabase(getApplicationContext());
		return db;
	}

	public void setDatabase(RealFarmDatabase db) {
		this.db = db;
	}

	public void setUserId(int userId) {
		mId = userId;
	}
}