package com.commonsensenet.realfarm.model;

public class WeatherForecast {

	private int mAdminFlag;
	private String mDate;
	private String mDate1;
	private String mType;
	private String mType1;
	private int mValue;
	private int mValue1;

	public WeatherForecast(String date, int value, String type, String date1,
			int value1, String type1, int adminflag) {
		mDate = date;
		mValue = value;
		mType = type;
		mDate1 = date1;
		mValue1 = value1;
		mType1 = type1;
		mAdminFlag = adminflag;
	}

	public int getAdminFlag() {
		return mAdminFlag;
	}

	public String getDate() {
		return mDate;
	}

	public String getDate1() {
		return mDate1;
	}

	public String getType() {
		return mType;
	}

	public String getType1() {
		return mType1;
	}

	public int getValue() {
		return mValue;
	}

	public int getValue1() {
		return mValue1;
	}

}
