package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class WeatherForecast {

	/** String that represents a parseable Date of the forecast. */
	private String mDate;
	/** Identifier of the Weather forecast. */
	private int mId;
	/** Temperature in Celcius. */
	private int mTemperature;
	/** WeatherType id that describes the weather forecast. */
	private int mWeatherTypeId;

	public WeatherForecast(int id, String date, int temperature, int type) {
		mId = id;
		mDate = date;
		mTemperature = temperature;
		mWeatherTypeId = type;
	}

	public String getDate() {
		return mDate;
	}

	public int getId() {
		return mId;
	}

	public int getTemperature() {
		return mTemperature;
	}

	public int getWeatherTypeId() {
		return mWeatherTypeId;
	}

	@Override
	public String toString() {

		return String
				.format("[WeatherForecast id='%s', date='%s', temperature='%d', weatherTypeId='%d']",
						mId, mDate, mTemperature, mWeatherTypeId);
	}

}
