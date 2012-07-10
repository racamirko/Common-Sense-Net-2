package com.commonsensenet.realfarm.model;

public class WeatherForecast {

	private int mAdminFlag;
	/** String that represents a parseable Date of the forecast. */
	private String mDate;
	/** Temperature in Celcius. */
	private int mTemperature;
	/** Type of weather. It can be Sunny, Cloudy, etc. */
	private String mType;

	public WeatherForecast(String date, int temperature, String type,
			int adminflag) {
		mDate = date;
		mTemperature = temperature;
		mType = type;
		mAdminFlag = adminflag;
	}

	public int getAdminFlag() {
		return mAdminFlag;
	}

	public String getDate() {
		return mDate;
	}

	public int getTemperature() {
		return mTemperature;
	}

	public String getType() {
		return mType;
	}

}
