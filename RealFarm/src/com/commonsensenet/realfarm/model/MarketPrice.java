package com.commonsensenet.realfarm.model;

public class MarketPrice {

	/** Date when the price occurred. */
	private String mDate;
	/** Unique identifier of the MarketPrice. */
	private int mId;
	/** Price type in the Market. */
	private String mType;
	/** Price in the given date. */
	private int mValue;

	public MarketPrice(int id, String date, int value, String type) {
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

	@Override
	public String toString() {

		return String.format(
				"[MarketPrice id='%d', date='%d', value='%s', type='%s']", mId,
				mDate, mValue, mType);
	}
}
