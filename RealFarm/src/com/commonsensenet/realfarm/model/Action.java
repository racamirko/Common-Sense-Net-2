package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Action {

	private int mActionNameId;
	private String mDate;
	private String mHarvestFeedback;
	private int mId;
	private int mIsAdmin;
	private String mPestcideType;
	private int mPlotId;
	private String mProblemType;
	private String mQualityOfSeed;
	private int mQuantity1;
	private int mQuantity2;
	private int mSeedTypeId;
	private int mSellingPrice;
	private String mSellType;
	private int mSent;
	private int mTimestamp;
	private String mTreatment;
	private String mTypeOfFert;
	private String mUnits;

	public Action(int id, int actionNameId, int seedTypeId, int quantity1,
			int quantity2, String units, int plotId, String typeOfFertilizer,
			String problemType, String harvestFeedback, int sellingPrice,
			String qualityOfSeed, String selltype, int sent, int isAdmin,
			String date, String treatment, String pesticideType, int timestamp) {

		mId = id;
		mActionNameId = actionNameId;
		mSeedTypeId = seedTypeId;
		mQuantity1 = quantity1;
		mQuantity2 = quantity2;
		mUnits = units;
		mPlotId = plotId;
		mTypeOfFert = typeOfFertilizer;
		mProblemType = problemType;

		mHarvestFeedback = harvestFeedback;
		mSellingPrice = sellingPrice;
		mQualityOfSeed = qualityOfSeed;
		mSellType = selltype;
		mSent = sent;
		mIsAdmin = isAdmin;
		mDate = date;
		mTreatment = treatment;
		mPestcideType = pesticideType;

		mTimestamp = timestamp;
	}

	public int getActionNameId() {
		return mActionNameId;
	}

	public String getDate() {
		return mDate;
	}

	public String getHarvestFeedback() {
		return mHarvestFeedback;
	}

	public int getId() {
		return mId;
	}

	public int getIsAdmin() {
		return mIsAdmin;
	}

	public String getPesticideType() {
		return mPestcideType;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public String getProblemType() {
		return mProblemType;
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

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public int getSellingPrice() {
		return mSellingPrice;
	}

	public String getSellType() {
		return mSellType;
	}

	public int getSent() {
		return mSent;
	}

	public int getTimestamp() {
		return mTimestamp;
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
}
