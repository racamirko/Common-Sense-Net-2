package com.commonsensenet.realfarm.model;

public class Irrigation {

	private int mactionid;
	
	private String mactionType;
	private int mQuantity1;
	

	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;
	private String mMethod;
	

	public Irrigation(int actionid, String actionType, int qua1,
			 String Units, String day, int userid,
			int plotid, int send, int Isadmin, String ActionPerformedDate,String Method) {  //Quantity1 mapped to no of hours

		mactionid = actionid;
		
		mactionType = actionType;
		mQuantity1 = qua1;
		

		mUnits = Units;
		mday = day;
		muserid = userid;
		mplotid = plotid;

		msend = send;
		mIsadmin = Isadmin;
		mActionPerformedDate = ActionPerformedDate;
		mMethod=Method;

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

	public int getquantity1() {              //gives no of hours
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

	
	public String getactionPerfDate() {
		return mActionPerformedDate;
	}
	

}
