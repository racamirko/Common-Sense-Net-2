package com.commonsensenet.realfarm.model;

import com.commonsensenet.realfarm.R;

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
	/** Type of weather. It can be Sunny, Cloudy, etc. */
	private String mType;

	public WeatherForecast(int id, String date, int temperature, String type) {
		mId = id;
		mDate = date;
		mTemperature = temperature;
		mType = type;
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

	public String getType() {
		return mType;
	}

	/**
	 * Gets the ResourceId based on the type of WeatherForecast. Accepted values
	 * are 'Sunny', 'Light Rain', 'Cloudy', 'Change of Rain', 'Rain' and
	 * 'Storm'.
	 * 
	 * @return the ResourceId that represents the WeatherForecast type.
	 */

	public int getTypeResourceId() {

		int typeIconId = -1;
		if (mType.toString().equalsIgnoreCase("Sunny")) {
			typeIconId = R.drawable.wf_sunny;
		} else if (mType.toString().equalsIgnoreCase("Light Rain")) {
			typeIconId = R.drawable.wf_lightrain;
		} else if (mType.toString().equalsIgnoreCase("Cloudy")) {
			typeIconId = R.drawable.wf_cloudy;
		} else if (mType.toString().equalsIgnoreCase("Chance of Rain")) {
			typeIconId = R.drawable.wf_chance_of_rain;
		} else if (mType.toString().equalsIgnoreCase("Rain")) {
			typeIconId = R.drawable.wf_rain;
		} else if (mType.toString().equalsIgnoreCase("Storm")) {
			typeIconId = R.drawable.wf_storm;
		}

		return typeIconId;
	}

}
