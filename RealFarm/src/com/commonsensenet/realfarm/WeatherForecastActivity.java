package com.commonsensenet.realfarm;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnDataChangeListener;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.WeatherForecastItemAdapter;

public class WeatherForecastActivity extends HelpEnabledActivity implements
		OnDataChangeListener, OnItemLongClickListener {

	/** Celsius indicator. */
	public static final String CELSIUS = "¡";
	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** ListAdapter used to handle the weather forecasts. */
	private WeatherForecastItemAdapter mWeatherForecastItemAdapter;
	/** ListView where the weather forecasts are rendered. */
	private ListView mWeatherForecastListView;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// sets the layout
		setContentView(R.layout.act_weather_forecast);

		// gets the Database
		mDataProvider = RealFarmProvider.getInstance(this);
		// sets the data change listener in case the weather information is
		// updated.
		mDataProvider.setWeatherForecastDataChangeListener(this);

		// gets the forecast from the database for today.
		List<WeatherForecast> wf = mDataProvider
				.getWeatherForecasts(new Date());

		// creates the adapter used to manage the data.
		mWeatherForecastItemAdapter = new WeatherForecastItemAdapter(this, wf,
				mDataProvider);

		// gets the list from the UI.
		mWeatherForecastListView = (ListView) findViewById(R.id.list_weather_forecast);
		// enables the focus on the items.
		mWeatherForecastListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mWeatherForecastListView.setAdapter(mWeatherForecastItemAdapter);
		// adds the long click to enable the help feature
		mWeatherForecastListView.setOnItemLongClickListener(this);
	}

	// TODO: this is not implemented.
	public void onDataChanged(String date, int temperature, String type) {

		// item should be added to the adapter.
	}

	protected void onDestroy() {
		// removes the listener
		mDataProvider.setWeatherForecastDataChangeListener(null);
		mDataProvider = null;
		super.onDestroy();
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();

		// gets the selected forecast
		WeatherForecast wf = mWeatherForecastItemAdapter.getItem(position);

		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a1);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);

		if (wf.getType().equals("Sunny")) {
			sq.addToQueue(R.raw.sunny);
		} else if (wf.getType().equals("Cloudy")) {
			sq.addToQueue(R.raw.cloudy);
		}

		// sq.addToQueue(R.raw.sunny);
		// sq.addToQueue(R.raw.lightshowers);
		// sq.addToQueue(R.raw.overcast);
		// sq.addToQueue(R.raw.stormy);

		sq.play();

		return true;
	}
}
