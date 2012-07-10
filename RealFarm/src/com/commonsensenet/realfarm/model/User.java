package com.commonsensenet.realfarm.model;

public class User {

	/** Flag that indicates whether the user was inserted by an admin or not. */
	protected int mAdmin;
	/** Flag that indicates whether the user is enabled or not. */
	protected int mDelete;
	/** First name of the User. */
	protected String mFirstName;
	/** Image file of the user. */
	protected String mImgName;
	/** Last name of the User. */
	protected String mLastName;
	/** Mobile Number of the user. */
	protected String mMobileNumber;
	/** Unique identifier. */
	protected int mUserId;

	public User(int userId, String firstName, String lastName,
			String mobileNumber, String imgName, int delete, int admin) {
		mUserId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mMobileNumber = mobileNumber;
		mImgName = imgName;
		mDelete = delete;
		mAdmin = admin;
	}

	public int getAdmin() {
		return mAdmin;
	}

	public int getDelete() {
		return mDelete;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getMobileNumber() {
		return mMobileNumber;
	}

	public int getUserId() {
		return mUserId;
	}

	public String getUserImgName() {
		return mImgName;
	}

}
