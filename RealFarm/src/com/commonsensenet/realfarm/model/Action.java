package com.commonsensenet.realfarm.model;

public class Action {

	private int mActionNameId;
	private String mDate;
	private int mGrowingId;
	private int mId;
	private int mQuantity;
	private int mQuantity2;
	private int mUnitId;

	public Action(int id, int growingId, int actionNameId, int quantity,
			int unitId, int quantity2, String date) {

		mId = id;
		mGrowingId = growingId;
		mActionNameId = actionNameId;
		mQuantity = quantity;
		mUnitId = unitId;
		mQuantity2 = quantity2;
		mDate = date;
	}

	public int getActionNameId() {
		return mActionNameId;
	}

	public String getDate() {
		return mDate;
	}

	public int getGrowingId() {
		return mGrowingId;
	}

	public int getId() {
		return mId;
	}

	public int getQuantity() {
		return mQuantity;
	}

	public int getQuantity2() {
		return mQuantity2;
	}

	public int getUnitId() {
		return mUnitId;
	}

	public void setActionNameId(int actionNameId) {
		mActionNameId = actionNameId;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public void setGrowingId(int growingId) {
		mGrowingId = growingId;
	}

	public void setId(int id) {
		mId = id;
	}

	public void setQuantity(int quantity) {
		mQuantity = quantity;
	}

	public void setQuantity2(int quantity2) {
		mQuantity2 = quantity2;
	}

	public void setUnitId(int unitId) {
		mUnitId = unitId;
	}
}
