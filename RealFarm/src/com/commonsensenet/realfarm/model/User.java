package com.commonsensenet.realfarm.model;

public class User {

	private String mFirstName;
	private String mLastName;
	private String mMobileNumber;
	private int mUserId;

	public User(int userId, String firstName, String lastName,
			String mobileNumber) {
		mUserId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mMobileNumber = mobileNumber;
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

	public String[] getName() {
		return new String[] { mFirstName, mLastName };
	}

	public int getUserId() {
		return mUserId;
	}

}
