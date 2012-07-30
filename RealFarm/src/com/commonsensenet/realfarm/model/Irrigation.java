package com.commonsensenet.realfarm.model;

public class Irrigation {

	private int mactionid;
	private int mactionameId;
	private int mQuantity1;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msend;
	private int mIsadmin;
	private String mMethod;

	public Irrigation(int actionid, int actionNameId, int quantity1,
			String Units, int plotid, int send, int Isadmin, String day,
			String Method, int userid) {

		mactionid = actionid;
		mactionameId = actionNameId;
		mQuantity1 = quantity1;
		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;
		msend = send;
		mIsadmin = Isadmin;
		mMethod = Method;
	}

	public int getActionId() {
		return mactionid;
	}

	public int getActionNameId() {
		return mactionameId;
	}

	public int getquantity1() {
		return mQuantity1;
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

	public String getMethod() {
		return mMethod;
	}

}
