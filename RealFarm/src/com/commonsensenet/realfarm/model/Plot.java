package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Plot {

	protected int mAdminFlag;
	protected int mDeleteFlag;
	protected int mId;
	protected String mImagePath;
	protected int mSeedTypeId;
	protected String mSoilType;
	protected int mTimestamp;
	protected int mUserId;

	public Plot(int id, int userId, int seedTypeId, String imagePath,
			String soilType, int deleteFlag, int adminFlag, int timestamp) {
		mId = id;
		mUserId = userId;
		mSeedTypeId = seedTypeId;
		mImagePath = imagePath;
		mSoilType = soilType;
		mDeleteFlag = deleteFlag;
		mAdminFlag = adminFlag;
		mTimestamp = timestamp;
	}

	public int getAdminFlag() {
		return mAdminFlag;
	}

	public int getDeleteFlag() {
		return mDeleteFlag;
	}

	public int getId() {
		return mId;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public String getSoilType() {
		return mSoilType;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	public int getUserId() {
		return mUserId;
	}

	@Override
	public String toString() {

		return String
				.format("[Plot id='%s', userId='%d', seedTypeId='%d', imagePath='%s', soilType='%s', deleteFlag='%d', adminFlag='%d', timestamp='%d']",
						mId, mUserId, mSeedTypeId, mImagePath, mSoilType,
						mDeleteFlag, mAdminFlag, mTimestamp);

	}
}
