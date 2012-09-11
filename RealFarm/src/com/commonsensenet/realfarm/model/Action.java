package com.commonsensenet.realfarm.model;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Action {

	private int mActionTypeId;
	private int mCropTypeId;
	private String mDate;
	private long mId;
	private int mIsAdminAction;
	private int mIsSent;
	private long mPlotId;
	private int mPrice;
	private double mQuantity1;
	private double mQuantity2;
	private int mResourceId1;
	private int mResourceId2;
	private int mSeedTypeId;
	private long mTimestamp;
	private int mUnit1;
	private int mUnit2;
	private long mUserId;

	public Action(long id, int actionTypeId, long plotId, String date,
			int seedTypeId, int cropTypeId, double quantity1, double quantity2,
			int unit1, int unit2, int resource1Id, int resource2Id, int price,
			long userId, int isSent, int isAdminAction, long timestamp) {

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

		// fixes the possible null values.
		if (mSeedTypeId == 0) {
			mSeedTypeId = RealFarmProvider.NONE;
		}
		if (mCropTypeId == 0) {
			mCropTypeId = RealFarmProvider.NONE;
		}
		if (mResourceId1 == 0) {
			mResourceId1 = RealFarmProvider.NONE;
		}
		if (mResourceId2 == 0) {
			mResourceId2 = RealFarmProvider.NONE;
		}
		if (mPrice == 0) {
			mPrice = RealFarmProvider.NONE;
		}
		if (mUnit1 == 0) {
			mUnit1 = RealFarmProvider.NONE;
		}
		if (mUnit2 == 0) {
			mUnit2 = RealFarmProvider.NONE;
		}

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

	public long getId() {
		return mId;
	}

	public int getIsAdminAction() {
		return mIsAdminAction;
	}

	public int getIsSent() {
		return mIsSent;
	}

	public long getPlotId() {
		return mPlotId;
	}

	public int getPrice() {
		return mPrice;
	}

	public double getQuantity1() {
		return mQuantity1;
	}

	public double getQuantity2() {
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

	public long getUserId() {
		return mUserId;
	}

	@Override
	public String toString() {

		return String
				.format("[Action id='%d', actionTypeId='%d', plotId='%d', date='%s', seedTypeId='%d', cropTypeId='%d', quantity1='%.2f', quantity2='%.2f', unit1='%d', unit2='%d', resource1='%d', resource2='%d', price='%d', userId='%d', isSent='%d', isAdminAction='%d', timestamp='%d']",
						mId, mActionTypeId, mPlotId, mDate, mSeedTypeId,
						mCropTypeId, mQuantity1, mQuantity2, mUnit1, mUnit2,
						mResourceId1, mResourceId2, mPrice, mUserId, mIsSent,
						mIsAdminAction, mTimestamp);
	}
}
