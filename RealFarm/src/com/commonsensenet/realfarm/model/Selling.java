package com.commonsensenet.realfarm.model;

public class Selling {

	private int mactionid;

	private String mactionType;
	private int mQuantity1;
	private int mQuantity2;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int mSellingPrice;
	private String mQuaofseed;
	private String mselltype;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;

	public Selling(int actionid, String actionType, int qua1, int qua2,
			String Units, String day, int userid, int plotid, int SellingPrice,
			String Quaofseed, String selltype, int send, int Isadmin,
			String ActionPerformedDate) {

		mactionid = actionid;
		mactionType = actionType;
		mQuantity1 = qua1;
		mQuantity2 = qua2;

		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;

		mSellingPrice = SellingPrice;
		mQuaofseed = Quaofseed;
		mselltype = selltype;

		msend = send;
		mIsadmin = Isadmin;
		mActionPerformedDate = ActionPerformedDate;

	}

	public int getActionId() {
		return mactionid;
	}

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

	public int getsp() {
		return mSellingPrice;
	}

	public String getQuaSeed() {
		return mQuaofseed;
	}

	public String getselltype() {
		return mselltype;
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
