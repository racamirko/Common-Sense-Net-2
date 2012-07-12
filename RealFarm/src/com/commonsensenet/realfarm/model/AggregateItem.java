package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class AggregateItem {

	private int mActionNameId;

	private int mSeedTypeId;

	private int mUserCount;

	public AggregateItem(int userCount, int actionNameID, int seedTypeId) {
		mUserCount = userCount;
		mActionNameId = actionNameID;
		mSeedTypeId = seedTypeId;
	}

	public int getActionNameId() {
		return mActionNameId;
	}

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public int getUserCount() {
		return mUserCount;
	}

	public void setActionNameId(int value) {
		this.mActionNameId = value;
	}

	public void setSeedTypeId(int value) {
		this.mSeedTypeId = value;
	}

	public void setUserCount(int value) {
		this.mUserCount = value;
	}

}
