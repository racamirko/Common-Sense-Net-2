package com.commonsensenet.realfarm.model;

public class MarketPrice {

	/** Date when the price occurred. */
	private String mDate;
	/** Unique identifier of the MarketPrice. */
	private int mId;
	/** Price type in the Market. */
	private String mType;
	/** Price in the given date. */
	private int mMin;
	private int mMax;

	public MarketPrice(int id, String date, int min, int max, String type) {
		mId = id;
		mDate = date;
		mMin = min;
		mMax = max;
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

	public int getMin() {
		return mMin;
	}
	
	public int getMax() {
		return mMax;
	}

	@Override
	public String toString() {

		return String.format(
				"[MarketPrice id='%d', date='%d', min='%s', max='%s', type='%s']", mId,
				mDate, mMin, mMax, mType);
	}
}
