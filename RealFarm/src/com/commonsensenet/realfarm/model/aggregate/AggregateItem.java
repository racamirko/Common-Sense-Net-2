package com.commonsensenet.realfarm.model.aggregate;

import java.util.Hashtable;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class AggregateItem {

	private int mActionTypeId;

	private Hashtable<String, String> mAggregateValues;

	private int mUserCount;

	public AggregateItem(int actionTypeId, int userCount) {
		mUserCount = userCount;
		mActionTypeId = actionTypeId;

		// initializes the data structure
		mAggregateValues = new Hashtable<String, String>();

	}

	public void addValue(String key, String value) {
		mAggregateValues.put(key, value);
	}

	public int getActionTypeId() {
		return mActionTypeId;
	}

	public int getUserCount() {
		return mUserCount;
	}

	public String getValue(String key) {
		return mAggregateValues.get(key);
	}

	public void setActionTypeId(int value) {
		mActionTypeId = value;
	}

	public void setUserCount(int value) {
		mUserCount = value;
	}

}
