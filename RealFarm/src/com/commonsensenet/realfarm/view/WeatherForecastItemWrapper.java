package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.WeatherForecastActivity;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.utils.DateHelper;

/**
 * Class that wraps up the contents of a WeatherForecast, which is presented on
 * a list adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class WeatherForecastItemWrapper {
	/** Day of the forecast. */
	private TextView mDay;
	/** Forecast description. */
	private TextView mForecast;
	/** Icon representing the forecast. */
	private ImageView mIcon;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;
	/** Forecasted temperature. */
	private TextView mTemperature;

	/**
	 * Creates a new WeatherForecastItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public WeatherForecastItemWrapper(View row) {
		mRow = row;
	}

	public TextView getDay() {
		if (mDay == null) {
			mDay = (TextView) mRow
					.findViewById(R.id.label_weather_forecast_day);
		}
		return (mDay);
	}

	public TextView getForecast() {
		if (mForecast == null) {
			mForecast = (TextView) mRow
					.findViewById(R.id.label_weather_forecast_forecast);
		}
		return (mForecast);
	}

	public ImageView getIcon() {
		if (mIcon == null) {
			mIcon = (ImageView) mRow.findViewById(R.id.icon_weather_forecast);
		}
		return (mIcon);
	}

	public TextView getTemperature() {
		if (mTemperature == null) {
			mTemperature = (TextView) mRow
					.findViewById(R.id.label_weather_forecast_temperature);
		}
		return (mTemperature);
	}

	public void populateFrom(WeatherForecast weatherForecast,
			RealFarmProvider provider) {

		// gets the WeatherType from the database.
		WeatherType wt = provider.getWeatherTypeById(weatherForecast
				.getWeatherTypeId());

		if (wt != null) {
			getIcon().setImageResource(wt.getImage());
			getForecast().setText(wt.getName());
		}

		getTemperature().setText(
				weatherForecast.getTemperature()
						+ WeatherForecastActivity.CELSIUS);
		getDay().setText(
				DateHelper.formatWithDay(weatherForecast.getDate())
						.toUpperCase());
	}
}
