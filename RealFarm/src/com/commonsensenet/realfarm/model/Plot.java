package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class Plot {

	/** Unique identifier of the Plot. */
	private int mId;
	/** Path where the image of the Plot is located. */
	private String mImagePath;
	/** Indicates if the Plot was added by the system administrator. */
	private int mIsAdminAction;
	/** Indicates whether the Plot is enabled or not. */
	private int mIsEnabled;
	/**
	 * Indicates whether the Plot information has been synchronized with the
	 * server.
	 */
	private int mIsSent;
	/** Type of seed planted in the Plot as a main crop. */
	private int mSeedTypeId;
	/** Size in acres of the Plot. */
	private float mSize;
	/** Type of soil that the Plot has. */
	private int mSoilTypeId;
	/** Timestamp that indicates the creation of the Plot. */
	private long mTimestamp;
	/** Owner of the plot. */
	private int mUserId;

	public Plot(int id, int userId, int seedTypeId, int soilTypeId,
			String imagePath, float size, int isSent, int isEnabled,
			int isAdminAction, long timestamp) {

		mId = id;
		mUserId = userId;
		mSeedTypeId = seedTypeId;
		mSoilTypeId = soilTypeId;
		mImagePath = imagePath;
		mSize = size;
		mIsSent = isSent;
		mIsEnabled = isEnabled;
		mIsAdminAction = isAdminAction;
		mTimestamp = timestamp;
	}

	public int getId() {
		return mId;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public int getIsAdminFlag() {
		return mIsAdminAction;
	}

	public int getIsEnabled() {
		return mIsEnabled;
	}

	public int getIsSent() {
		return mIsSent;
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

	public long getTimestamp() {
		return mTimestamp;
	}

	public int getUserId() {
		return mUserId;
	}

	@Override
	public String toString() {

		return String
				.format("[Plot id='%d', userId='%d', seedTypeId='%d', imagePath='%s', soilTypeId='%d', size='%f', isSent='%d', isEnabled='%d', isAdminAction='%d', timestamp='%d']",
						mId, mUserId, mSeedTypeId, mImagePath, mSoilTypeId,
						mSize, mIsSent, mIsEnabled, mIsAdminAction, mTimestamp);
	}
}
