package com.commonsensenet.realfarm.model;

public class Harvesting {

	private int mactionid;
	// private int mactionnameid;
	private String mactionType;
	private int mQuantity1;
	private int mQuantity2;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private String mharvFeedback;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;

	public Harvesting(int actionid, String actionType, int Quantity1,
			int Quantity2, String Units, String day, int userid, int plotid,
			String harvfeedback, int send, int Isadmin,
			String ActionPerformedDate) {

		mactionid = actionid;
		mQuantity1 = Quantity1;
		mQuantity2 = Quantity2;

		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;

		mharvFeedback = harvfeedback;

		msend = send;
		mIsadmin = Isadmin;
		mActionPerformedDate = ActionPerformedDate;

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

	public int getquantity2() {
		return mQuantity2;
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

	public String getharvestfeedback() {
		return mharvFeedback;
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

}
