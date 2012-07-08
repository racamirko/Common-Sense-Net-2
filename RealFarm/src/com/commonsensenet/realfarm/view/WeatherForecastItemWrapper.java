package com.commonsensenet.realfarm.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.WeatherForecastActivity;
import com.commonsensenet.realfarm.model.WeatherForecast;
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
	/** Forecasted temperature. */
	private TextView mTemperature;
	/** Forecast description. */
	private TextView mForecast;
	/** Icon representing the forecast. */
	private ImageView mIcon;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;

	/**
	 * Creates a new WeatherForecastItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public WeatherForecastItemWrapper(View row) {
		this.mRow = row;
	}

	public TextView getDay() {
		if (mDay == null) {
			mDay = (TextView) mRow
					.findViewById(R.id.label_weather_forecast_day);
		}
		return (mDay);
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

	public TextView getForecast() {
		if (mForecast == null) {
			mForecast = (TextView) mRow
					.findViewById(R.id.label_weather_forecast_forecast);
		}
		return (mForecast);
	}

	public void populateFrom(WeatherForecast weatherForecast, Context context) {

		String type = weatherForecast.getType();
		int typeIconId = 0;

		// gets the icon based on the text.
		if (type.toString().equalsIgnoreCase("Sunny")) {
			typeIconId = R.drawable.sunny;
		} else if (type.toString().equalsIgnoreCase("Light Showers")) {
			typeIconId = R.drawable.lightrain;
		}
		getTemperature().setText(
				weatherForecast.getValue() + WeatherForecastActivity.CELSIUS);
		getIcon().setImageResource(typeIconId);
		getDay().setText(
				DateHelper.formatWithDay(weatherForecast.getDate())
						.toUpperCase());
		getForecast().setText(type);
	}
}
