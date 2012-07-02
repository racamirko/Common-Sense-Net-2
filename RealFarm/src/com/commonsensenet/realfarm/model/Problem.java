
package com.commonsensenet.realfarm.model;

public class Problem {

	private int mactionid;
	
	private String mactionType;
	private String mday;
	private int muserid;
	private int mplotid;
	private String mProbType;
	private int msend;
	private int mIsadmin;
	private String mActionPerformedDate;

	

	public Problem(int actionid, String actionType,String day, int userid,
			int plotid, String probType, int send, int Isadmin, String ActionPerformedDate) {  //Quantity1 mapped to no of hours

		mactionid = actionid;
		
		mactionType = actionType;
	    mday = day;
		muserid = userid;
		mplotid = plotid;
		mProbType = probType;
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

	
	public String getday() {
		return mday;
	}

	public int getuserid() {
		return muserid;
	}

	public int getplotid() {
		return mplotid;
	}
	public String getProbType() {
		return mProbType;
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
