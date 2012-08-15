package com.commonsensenet.realfarm.model.aggregate;

import com.commonsensenet.realfarm.model.User;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class UserAggregateItem {

	private String mDate;
	private String mName;
	private String mTelNumber;
	private String mAvatarImg;
	private String mRightText;
	private int mRightImg;

	//private User mUser;

	public UserAggregateItem(String name, String date, String telNumber, String avatarImg, String rightText, int rightImg) {
	//	mUser = user;
		mDate = date;
		mName = name;
		mTelNumber = telNumber;
		mAvatarImg = avatarImg;
		mRightImg = rightImg;
		mRightText = rightText;
	}

	public String getDate() {
		return mDate;
	}

	public String getName() {
		return mName;
	}

	public String getTel() {
		return mTelNumber;
	}
	
	public String getAvatar() {
		return mAvatarImg;
	}
	
	public String getRightText() {
		return mRightText;
	}
	
	public int getRightImg() {
		return mRightImg;
	}
	
/*	public User getUser() {
		return mUser;
	}*/

}
