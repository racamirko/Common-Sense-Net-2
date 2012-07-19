package com.commonsensenet.realfarm.model.aggregate;

import java.util.Hashtable;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class AggregateItem {

	private int mActionNameId;

	private Hashtable<String, String> mAggregateValues;

	private int mUserCount;

	public AggregateItem(int actionNameID, int userCount) {
		mUserCount = userCount;
		mActionNameId = actionNameID;

		// initializes the data structure
		mAggregateValues = new Hashtable<String, String>();

	}

	public void addValue(String key, String value) {
		mAggregateValues.put(key, value);
	}

	public int getActionNameId() {
		return mActionNameId;
	}

	public int getUserCount() {
		return mUserCount;
	}

	public String getValue(String key) {
		return mAggregateValues.get(key);
	}

	public void setActionNameId(int value) {
		this.mActionNameId = value;
	}

	public void setUserCount(int value) {
		this.mUserCount = value;
	}

}
