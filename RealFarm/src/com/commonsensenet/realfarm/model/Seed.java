package com.commonsensenet.realfarm.model;

public class Seed {

	private int mId;
	private String mName;
	private int mRes;
	private String mVariety;

	public Seed(int id, String name, String variety, int res) {
		mId = id;
		mName = name;
		mVariety = variety;
		mRes = res;
	}

	public String getFullName() {
		if (mVariety != null)
			return mName + " - " + mVariety;
		else
			return mName;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getRes() {
		return mRes;
	}

	public String getVariety() {
		return mVariety;
	}
}
