package com.commonsensenet.realfarm.model;

public class Recommendation {

	protected int mId;
	protected int mActionId;
	protected int mSeedId;
	protected String mDate;

	public Recommendation(int id, int seedId, int actionId, String date) {
		mId = id;
		mSeedId = seedId;
		mActionId = actionId;
		mDate = date;
	}

	public int getAction() {
		return mActionId;
	}

	public String getDate() {
		return mDate;
	}

	public int getId() {
		return mId;
	}

	public int getSeed() {
		return mSeedId;
	}

}
