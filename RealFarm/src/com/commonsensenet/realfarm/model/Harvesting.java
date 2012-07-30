package com.commonsensenet.realfarm.model;

public class Harvesting {

	private int mactionid;
	private int mactionnameid;
	private int mQuantity1;
		private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private String mharvFeedback;
	private int msent;
	private int mIsadmin;
	
	public Harvesting(int actionid,int actionNameId, int Quantity1,
			 String Units,int plotid,String harvfeedback, 
			 int sent, int Isadmin,String day, int userid) {

		mactionid = actionid;
		mactionnameid=actionNameId;
		mQuantity1 = Quantity1;
		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;
     	mharvFeedback = harvfeedback;
		msent = sent;
		mIsadmin = Isadmin;
		
	}

	public int getActionId() {
		return mactionid;
	}



	public int getActionNameId() {
		return mactionnameid;
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

	public String getharvestfeedback() {
		return mharvFeedback;
	}

	public int getsent() {
		return msent;
	}

	public int getadmin() {
		return mIsadmin;
	}


}
