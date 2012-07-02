package com.commonsensenet.realfarm.model;

public class Sowing {

	private int mactionid;
	// private int mactionnameid;
	private String mactionType;
	private int mQuantity1;
	private String mseedvariety;

	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;
	private String mTreatment;

	public Sowing(int actionid, String actionType, int qua1,
			String seedvariety, String Units, String day, int userid,
			int plotid, int send, int Isadmin, String ActionPerformedDate,
			String Treatment) {

		mactionid = actionid;
		// mactionnameid=actionnameid;
		mactionType = actionType;
		mQuantity1 = qua1;
		mseedvariety = seedvariety;

		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;

		msend = send;
		mIsadmin = Isadmin;
		mActionPerformedDate = ActionPerformedDate;
		mTreatment = Treatment;

	}

	public int getActionId() {
		return mactionid;
	}

	// public int getActionNameId() {
	// return mactionnameid;
	// }
	public String getActionType() {
		return mactionType;
	}

	public int getquantity1() {
		return mQuantity1;
	}

	public String Seedvariety() {
		return mseedvariety;
	}

	public String getUnits() {
		return mUnits;
	}

	public String getday() {
		return mday;
	}

	public int getuserid() {
		return muserid;
	}

	public int getplotid() {
		return mplotid;
	}

	public int getsent() {
		return msend;
	}

	public int getadmin() {
		return mIsadmin;
	}

	public String getactionPerfDate() {
		return mActionPerformedDate;
	}

	public String getreatment() {
		return mTreatment;
	}

}
