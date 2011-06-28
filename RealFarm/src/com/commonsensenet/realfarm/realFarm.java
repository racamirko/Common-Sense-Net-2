package com.commonsensenet.realfarm;

import android.app.Application;

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
	private int mId = 0;

	public ManageDatabase getDatabase() {
		return mDb;
	}

	public int getUserId() {
		return mId;
	}

	public ManageDatabase setDatabase() {
		// comment out if you want to reuse existing database
		getApplicationContext().deleteDatabase("realFarm.db");

		mDb = new ManageDatabase(getApplicationContext());
		return mDb;
	}

	public void setUserId(int userId) {
		mId = userId;
	}
}
