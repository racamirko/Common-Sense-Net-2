package com.commonsensenet.realfarm.model;

public class Sowing {

	private String mActionType;
	private String mDatePerformed;
	private String mDay;
	private int mId;
	private int mIsadmin;
	private int mPlotId;
	private int mQuantity1;
	private String mSeedVariety;
	private int mSent;
	private String mTreated;
	private String mUnits;
	private int mUserId;

	public Sowing(int id, String actionType, int quantity, String seedVariety,
			String units, String day, int userId, int plotId, int sent,
			int isAdmin, String datePerformed, String treated) {

		mId = id;
		mActionType = actionType;
		mQuantity1 = quantity;
		mSeedVariety = seedVariety;
		mUnits = units;
		mDay = day;
		mUserId = userId;
		mPlotId = plotId;
		mSent = sent;
		mIsadmin = isAdmin;
		mDatePerformed = datePerformed;
		mTreated = treated;

	}

	public String getActionType() {
		return mActionType;
	}

	public String getDatePerformed() {
		return mDatePerformed;
	}

	public String getDay() {
		return mDay;
	}

	public int getId() {
		return mId;
	}

	public int getIsAdmin() {
		return mIsadmin;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public int getQuantity1() {
		return mQuantity1;
	}

	public String getSeedVariety() {
		return mSeedVariety;
	}

	public int getSent() {
		return mSent;
	}

	public String getTreated() {
		return mTreated;
	}

	public String getUnits() {
		return mUnits;
	}

	public int getUserId() {
		return mUserId;
	}

}
