package com.commonsensenet.realfarm.model;

public class WeatherForecast {

	/** String that represents a parseable Date of the forecast. */
	private String mDate;
	/** Temperature in Celcius. */
	private int mTemperature;
	/** Type of weather. It can be Sunny, Cloudy, etc. */
	private String mType;
	/** Identifier of the Weather forecast. */
	private int mId;

	public WeatherForecast(int id, String date, int temperature, String type) {
		mId = id;
		mDate = date;
		mTemperature = temperature;
		mType = type;
	}

	public int getId() {
		return mId;
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
