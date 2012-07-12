package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class User {

	/** Flag that indicates whether the user was inserted by an admin or not. */
	protected int mAdminFlag;
	/** Flag that indicates whether the user is enabled or not. */
	protected int mDeleteFlag;
	/** First name of the User. */
	protected String mFirstName;
	/** Unique identifier. */
	protected int mId;
	/** Image file of the user. */
	protected String mImagePath;
	/** Last name of the User. */
	protected String mLastName;
	/** Mobile Number of the user. */
	protected String mMobileNumber;
	/** Timestamp that indicates the creation of the User. */
	protected int mTimestamp;

	public User(int userId, String firstName, String lastName,
			String mobileNumber, String imagePath, int deleteFlag,
			int adminFlag, int timestamp) {
		mId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mMobileNumber = mobileNumber;
		mImagePath = imagePath;
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

	public String getFirstName() {
		return mFirstName;
	}

	public int getId() {
		return mId;
	}

	public String getImage() {
		return mImagePath;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getMobileNumber() {
		return mMobileNumber;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	@Override
	public String toString() {

		return String
				.format("[User id='%s', firstName='%s', lastName='%s', mobileNumber='%s', imagePath='%s', deleteFlag='%d', adminFlag='%d', timestamp='%d']",
						mId, mFirstName, mLastName, mMobileNumber, mImagePath,
						mDeleteFlag, mAdminFlag, mTimestamp);

	}

}
