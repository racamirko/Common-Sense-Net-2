package com.commonsensenet.realfarm.model;

public class MarketPrice {

	/** Date when the price occurred. */
	private String mDate;
	/** Unique identifier of the MarketPrice. */
	private int mId;
	private String mType;
	/** Price in the given date. */
	private int mValue;

	public MarketPrice(int id, String date, String type, int value) {
		mId = id;
		mDate = date;
		mValue = value;
		mType = type;
	}

	public String getDate() {
		return mDate;
	}

	public int getId() {
		return mId;
	}

	public String getType() {
		return mType;
	}

	public int getValue() {
		return mValue;
	}
}
