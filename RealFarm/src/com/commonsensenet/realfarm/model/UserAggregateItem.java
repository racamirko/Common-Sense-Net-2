package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class UserAggregateItem {

	private String mDate;

	private User mUser;

	public UserAggregateItem(User user, String date) {
		mUser = user;
		mDate = date;
	}

	public String getDate() {
		return mDate;
	}

	public User getUser() {
		return mUser;
	}

}
