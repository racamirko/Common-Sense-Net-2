package com.commonsensenet.realfarm.model;

public class Plot {

	protected int mAdminFlag;
	protected int mCenterX;
	protected int mCenterY;
	protected int mDeleteFlag;
	protected int mId;
	protected String mImageName;
	protected int mSeedTypeId;
	protected String mSoilType;
	protected int mUserId;

	public Plot(int id, int userId, int seedTypeId, int centerX, int centerY,
			String imageName, String soilType, int deleteFlag, int adminFlag) {
		mId = id;
		mUserId = userId;
		mSeedTypeId = seedTypeId;
		mCenterX = centerX;
		mCenterY = centerY;
		mImageName = imageName;
		mSoilType = soilType;
		mDeleteFlag = deleteFlag;
		mAdminFlag = adminFlag;
	}

	public int getAdminFlag() {
		return mAdminFlag;
	}

	public int getCenterX() {
		return mCenterX;
	}

	public int getCenterY() {
		return mCenterY;
	}

	public int getDeleteFlag() {
		return mDeleteFlag;
	}

	public int getId() {
		return mId;
	}

	public String getImageName() {
		return mImageName;
	}

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public String getSoilType() {
		return mSoilType;
	}

	public int getUserId() {
		return mUserId;
	}
}
