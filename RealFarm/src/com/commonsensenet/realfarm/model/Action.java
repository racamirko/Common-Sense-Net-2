package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Action {

	private int mActionTypeId;
	private int mCropTypeId;
	private String mDate;
	private int mId;
	private int mIsAdminAction;
	private int mIsSent;
	private int mPlotId;
	private int mPrice;
	private int mQuantity1;
	private int mQuantity2;
	private int mResourceId1;
	private int mResourceId2;
	private int mSeedTypeId;
	private long mTimestamp;
	private int mUnit1;
	private int mUnit2;
	private int mUserId;

	public Action(int id, int actionTypeId, int plotId, String date,
			int seedTypeId, int cropTypeId, int quantity1, int quantity2,
			int unit1, int unit2, int resource1Id, int resource2Id, int price,
			int userId, int isSent, int isAdminAction, long timestamp) {

		mId = id;
		mActionTypeId = actionTypeId;
		mPlotId = plotId;
		mDate = date;

		mSeedTypeId = seedTypeId;
		mCropTypeId = cropTypeId;
		mQuantity1 = quantity1;
		mQuantity2 = quantity2;
		mUnit1 = unit1;
		mUnit2 = unit2;
		mResourceId1 = resource1Id;
		mResourceId2 = resource2Id;
		mPrice = price;
		mUserId = userId;

		mIsSent = isSent;
		mIsAdminAction = isAdminAction;
		mTimestamp = timestamp;

	}

	public int getActionTypeId() {
		return mActionTypeId;
	}

	public int getCropTypeId() {
		return mCropTypeId;
	}

	public String getDate() {
		return mDate;
	}

	public int getId() {
		return mId;
	}

	public int getIsAdminAction() {
		return mIsAdminAction;
	}

	public int getIsSent() {
		return mIsSent;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public int getPrice() {
		return mPrice;
	}

	public int getQuantity1() {
		return mQuantity1;
	}

	public int getQuantity2() {
		return mQuantity2;
	}

	public int getResource1Id() {
		return mResourceId1;
	}

	public int getResource2Id() {
		return mResourceId2;
	}

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public long getTimetamp() {
		return mTimestamp;
	}

	public int getUnit1() {
		return mUnit1;
	}

	public int getUnit2() {
		return mUnit2;
	}

	public int getUserId() {
		return mUserId;
	}

	@Override
	public String toString() {

		return String
				.format("[Action id='%d', actionTypeId='%d', plotId='%d', date='%s', seedTypeId='%d', cropTypeId='%d', quantity1='%d', quantity2='%d', unit1='%d', unit2='%d', resource1='%d', resource2='%d', price='%d', userId='%d', isSent='%d', isAdminAction='%d', timestamp='%d']",
						mId, mActionTypeId, mPlotId, mDate, mSeedTypeId,
						mCropTypeId, mQuantity1, mQuantity2, mUnit1, mUnit2,
						mResourceId1, mResourceId2, mPrice, mUserId, mIsSent,
						mIsAdminAction, mTimestamp);
	}
}
