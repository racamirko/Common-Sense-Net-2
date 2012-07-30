package com.commonsensenet.realfarm.model;

public class Selling {

	private int mactionid;
	private int mactionNameId;
	private int mQuantity1;
	private int mQuantity2;
	private String mUnits;
	private String mday;
	private int muserid;
	private int mSellingPrice;
	private int mCropTypeId;
	private int msent;
	private int muserId;
	private int mIsadmin;
	
	public Selling(int actionid, int actionNameId, int quantity1, int quantity2,
			String Units, int SellingPrice, int cropType_id, int sent, int Isadmin
			,String day, int userid) {

		mactionid = actionid;
		mactionNameId=actionNameId;
		mQuantity1 = quantity1;
		mQuantity2 = quantity2;
		mUnits = Units;
		mday = day;
		muserid = userid;
		mSellingPrice = SellingPrice;
		mCropTypeId = cropType_id;
		msent = sent;
		mIsadmin = Isadmin;
		

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



	public int getSellingPrice() {
		return mSellingPrice;
	}

	public int getCropTypeId() {
		return mCropTypeId;
	}


	public int getsent() {
		return msent;
	}

	public int getadmin() {
		return mIsadmin;
	}

	

}
