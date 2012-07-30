package com.commonsensenet.realfarm.model;

public class Fertilizing {

	private int mactionid;
	private int mactionNameId;
	private int mQuantity1;
	private String mTypeofFert;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msend;
	private int mIsadmin;
	

	public Fertilizing(int actionid, int  actionNameId, int qua1,
			String TypeofFert, String Units,
			int plotid, int send, int Isadmin, String day, int userid) {

		mactionid = actionid;
		mQuantity1 = qua1;
		mTypeofFert = TypeofFert;
		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;
		msend = send;
		mIsadmin = Isadmin;
		mactionNameId=actionNameId;
	}

	public int getActionId() {
		return mactionid;
	}

	public int getActionNameId() {
		return mactionNameId;
	}

	public int getquantity1() {
		return mQuantity1;
	}

	public String getTypeFert() {
		return mTypeofFert;
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



}