package com.commonsensenet.realfarm;

import android.app.Application;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.database.ManageDatabase;

/**
 * Main class of application. This class is used to share global information
 * across activities such as the database
 * 
 * @author Julien Freudiger
 * 
 */
public class realFarm extends Application {

	private ManageDatabase mDb;
	private RealFarmDatabase db;
	private int mId = 0;

	public RealFarmDatabase getDatabase() {
		//return mDb;
		return db;
	}

	public int getUserId() {
		return mId;
	}

	public RealFarmDatabase setDatabase() {
		db = new RealFarmDatabase(getApplicationContext());

		//mDb = new ManageDatabase(getApplicationContext());
		//return mDb;
		return db;
	}

	public void setUserId(int userId) {
		mId = userId;
	}
}
