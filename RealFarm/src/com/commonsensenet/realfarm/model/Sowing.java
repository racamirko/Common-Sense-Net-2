package com.commonsensenet.realfarm.model;

public class Sowing {

	private int mactionid;
	private int mactionNameId;
	private String mactionType;
	private int mQuantity1;
	private int mseedTypeId;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mplotid;
	private int msent;
	private int mIsadmin;
	private String mTreatment;
	private String mInterCrop;

	public Sowing(int actionid, int actionNameId , int quantity1,
			int seedTypeId , String Units, int plotid,int sent, int Isadmin,
			String day, String Treatment,String intercrop, int userId) {

		mactionid = actionid;
		mactionNameId=actionNameId;
		mQuantity1 = quantity1;
		mseedTypeId = seedTypeId;
		mUnits = Units;
		mday = day;
		muserid = userId;
		mplotid = plotid;
		msent = sent;
		mIsadmin = Isadmin;
		 mTreatment = Treatment;
		 mInterCrop=intercrop;
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

	public int getSeedTypeId() {
		return mseedTypeId;
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
		return msent;
	}

	public int getadmin() {
		return mIsadmin;
	}


	public String getreatment() {
		return mTreatment;
	}
	
	public String getInterCrop() {
		return mInterCrop;
	}

}
