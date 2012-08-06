package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Plot {

	/** Indicates if the Plot was added by the system administrator. */
	protected int mIsAdminAction;
	/** Indicates whether the Plot is enabled or not. */
	protected int mIsEnabled;
	/** Unique identifier of the Plot. */
	protected int mId;
	/** Path where the image of the Plot is located. */
	protected String mImagePath;
	/** Type of seed planted in the Plot as a main crop. */
	protected int mSeedTypeId;
	/** Size in acres of the Plot. */
	protected float mSize;
	/** Type of soil that the Plot has. */
	protected int mSoilTypeId;
	/** Timestamp that indicates the creation of the Plot. */
	protected int mTimestamp;
	/** Owner of the plot. */
	protected int mUserId;

	public Plot(int id, int userId, int seedTypeId, String imagePath,
			int soilTypeId, float size, int isEnabled, int isAdminAction,
			int timestamp) {
		mId = id;
		mUserId = userId;
		mSeedTypeId = seedTypeId;
		mImagePath = imagePath;
		mSoilTypeId = soilTypeId;
		mSize = size;
		mIsEnabled = isEnabled;
		mIsAdminAction = isAdminAction;
		mTimestamp = timestamp;
	}

	public int getIsAdminFlag() {
		return mIsAdminAction;
	}

	public int getIsEnabled() {
		return mIsEnabled;
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

	public float getSize() {
		return mSize;
	}

	public int getSoilTypeId() {
		return mSoilTypeId;
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
				.format("[Plot id='%s', userId='%d', seedTypeId='%d', imagePath='%s', soilTypeId='%s', size='%f', deleteFlag='%d', adminFlag='%d', timestamp='%d']",
						mId, mUserId, mSeedTypeId, mImagePath, mSoilTypeId,
						mSize, mIsEnabled, mIsAdminAction, mTimestamp);
	}
}
