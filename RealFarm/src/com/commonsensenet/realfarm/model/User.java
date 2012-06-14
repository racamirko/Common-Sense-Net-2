package com.commonsensenet.realfarm.model;

public class User {

	protected String mFirstName;
	protected String mLastName;
	protected String mMobileNumber;
	protected int mUserId;
	protected String mImgName;
	protected int mDelete;
	protected int mAdmin;

	public User(int userId, String firstName, String lastName,
			String mobileNumber, String imgName, int delete, int admin) {             //added
		mUserId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mMobileNumber = mobileNumber;
		mImgName = imgName;
		mDelete=delete;
		mAdmin=admin;
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
	
	public String getUserImgName(){
		return mImgName;
	}
	
	public int getDelete(){
		return mDelete;
	}
	public int getAdmin(){
		return mAdmin;
	}

}
