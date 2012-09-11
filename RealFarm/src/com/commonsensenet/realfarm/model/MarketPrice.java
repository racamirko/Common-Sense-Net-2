package com.commonsensenet.realfarm.model;

public class MarketPrice {

	/** Date when the price occurred. */
	private String mDate;
	/** Unique identifier of the MarketPrice. */
	private int mId;
	/** Maximum price in the market. */
	private int mMax;
	/** Minimum price in the market. */
	private int mMin;
	/** Price type in the Market. */
	private String mType;

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

	public int getMax() {
		return mMax;
	}

	public int getMin() {
		return mMin;
	}

	public String getType() {
		return mType;
	}

	@Override
	public String toString() {

		return String
				.format("[MarketPrice id='%d', date='%s', min='%s', max='%s', type='%s']",
						mId, mDate, mMin, mMax, mType);
	}
}
