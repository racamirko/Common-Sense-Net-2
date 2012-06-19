package com.commonsensenet.realfarm.model;

public class Action {

	private int mActionNameId;
	private String mActionPerformedDate;
	private String mActionType;
	private String mDay;
	private int mGrowingId;
	private String mHarvFeedback;
	private int mId;
	private int mIsAdmin;
	private String mPestcideType;
	private int mPlotId;
	private String mProbType;
	private String mQualityOfSeed;
	private int mQuantity1;
	private int mQuantity2;
	private String mSeedVariety;
	private int mSellingPrice;
	private String mSellType;
	private int mSend;
	private String mTreatment;
	private String mTypeOfFert;
	private String mUnits;
	private int mUserId;

	public Action(int actionid, int actionnameid, int GrowingId,
			String actiontype, String seedvariety, int Quantity1,
			int Quantity2, String Units, String day, int userid, int plotid,
			String TypeofFert, String probType, String harvFeedback,
			int SellingPrice, String Quaofseed, String selltype, int send,
			int Isadmin, String ActionPerformedDate, String Treatment,
			String PestcideType) {

		mId = actionid;
		mActionNameId = actionnameid;
		mGrowingId = GrowingId;
		mActionType = actiontype;
		mSeedVariety = seedvariety;
		mQuantity1 = Quantity1;
		mQuantity2 = Quantity2;
		mUnits = Units;
		mDay = day;
		mUserId = userid;
		mPlotId = plotid;
		mTypeOfFert = TypeofFert;
		mProbType = probType;

		mHarvFeedback = harvFeedback;
		mSellingPrice = SellingPrice;
		mQualityOfSeed = Quaofseed;
		mSellType = selltype;
		mSend = send;
		mIsAdmin = Isadmin;
		mActionPerformedDate = ActionPerformedDate;
		mTreatment = Treatment;
		mPestcideType = PestcideType;
	}

	public int getActionNameId() {
		return mActionNameId;
	}

	public String getActionPerfDate() {
		return mActionPerformedDate;
	}

	public String getActionType() {
		return mActionType;
	}

	public String getDay() {
		return mDay;
	}

	public int getGrowingId() {
		return mGrowingId;
	}

	public String getHarvestFeedback() {
		return mHarvFeedback;
	}

	public int getId() {
		return mId;
	}

	public int getIsAdmin() {
		return mIsAdmin;
	}

	public String getPesticidType() {
		return mPestcideType;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public String getProbType() {
		return mProbType;
	}

	public String getQualityOfSeed() {
		return mQualityOfSeed;
	}

	public int getQuantity1() {
		return mQuantity1;
	}

	public int getQuantity2() {
		return mQuantity2;
	}

	public String getSeedVariery() {
		return mSeedVariety;
	}

	public int getSellingPrice() {
		return mSellingPrice;
	}

	public String getSellType() {
		return mSellType;
	}

	public int getSend() {
		return mSend;
	}

	public String getTreatment() {
		return mTreatment;
	}

	public String getTypeFert() {
		return mTypeOfFert;
	}

	public String getUnits() {
		return mUnits;
	}

	public int getUserId() {
		return mUserId;
	}
}
