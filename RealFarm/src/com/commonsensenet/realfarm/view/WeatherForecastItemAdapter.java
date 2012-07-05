package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.WeatherForecast;

public class WeatherForecastItemAdapter extends ArrayAdapter<WeatherForecast> {

	/**
	 * Creates a new WeatherForecasttemAdapter instance.
	 */
	public WeatherForecastItemAdapter(Context context,
			List<WeatherForecast> weatherForecasts,
			RealFarmProvider dataProvider) {
		super(context, android.R.layout.simple_list_item_1, weatherForecasts);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeatherForecastItemWrapper wrapper = null;
		if (row == null) {

			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(R.layout.tpl_weather_forecast_item, null);
			wrapper = new WeatherForecastItemWrapper(row);
			row.setTag(wrapper);
		} else {
			wrapper = (WeatherForecastItemWrapper) row.getTag();
		}

		wrapper.populateFrom(this.getItem(position), this.getContext());
		return (row);
	}
}
