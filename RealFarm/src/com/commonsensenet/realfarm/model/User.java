package com.commonsensenet.realfarm.model;

public class User {

	protected String mFirstName;
	protected String mLastName;
	protected String mMobileNumber;
	protected int mUserId;
	protected String mImgName;
	protected String mUsrAudioFile;

	public User(int userId, String firstName, String lastName,
			String mobileNumber, String imgName, String audioFile) {
		mUserId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mMobileNumber = mobileNumber;
		mImgName = imgName;
		mUsrAudioFile = audioFile;
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

	public String getUsrAudioFile() {
		return mUsrAudioFile;
	}

}
