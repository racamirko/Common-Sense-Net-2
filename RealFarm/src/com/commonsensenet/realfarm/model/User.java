package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class User {

	/** First name of the User. */
	protected String mFirstname;
	/** Unique User Identifier */
	protected int mId;
	/** Path where the image of the User is located. */
	protected String mImagePath;
	/**
	 * Flag that indicates whether the user was inserted by an administrator or
	 * not.
	 */
	protected int mIsAdminAction;
	/** Flag that indicates whether the user is enabled or not. */
	protected int mIsEnabled;
	/** Lastname of the User. */
	protected String mLastname;
	/** Mobile number of the User. */
	protected String mMobile;
	/** Timestamp that indicates the creation of the User. */
	protected int mTimestamp;

	public User(int userId, String firstname, String lastname, String mobile,
			String imagePath, int isEnabled, int isAdminAction, int timestamp) {
		mId = userId;
		mFirstname = firstname;
		mLastname = lastname;
		mMobile = mobile;
		mImagePath = imagePath;
		mIsEnabled = isEnabled;
		mIsAdminAction = isAdminAction;
		mTimestamp = timestamp;
	}

	public String getFirstname() {
		return mFirstname;
	}

	public int getId() {
		return mId;
	}

	public String getImage() {
		return mImagePath;
	}

	public int getIsAdminAction() {
		return mIsAdminAction;
	}

	public int getIsEnabled() {
		return mIsEnabled;
	}

	public String getLastname() {
		return mLastname;
	}

	public String getMobile() {
		return mMobile;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	@Override
	public String toString() {

		return String
				.format("[User id='%s', firstName='%s', lastName='%s', mobileNumber='%s', imagePath='%s', deleteFlag='%d', adminFlag='%d', timestamp='%d']",
						mId, mFirstname, mLastname, mMobile, mImagePath,
						mIsEnabled, mIsAdminAction, mTimestamp);

	}

}
