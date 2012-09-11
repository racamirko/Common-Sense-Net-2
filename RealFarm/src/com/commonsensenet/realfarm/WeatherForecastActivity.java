package com.commonsensenet.realfarm;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnWeatherForecastDataChangeListener;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.WeatherForecastItemAdapter;

public class WeatherForecastActivity extends HelpEnabledActivity implements
		OnWeatherForecastDataChangeListener, OnItemLongClickListener,
		OnItemClickListener {

	/** Celsius indicator. */
	public static final String CELSIUS = "¡";
	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** ListAdapter used to handle the weather forecasts. */
	private WeatherForecastItemAdapter mWeatherForecastItemAdapter;
	/** ListView where the weather forecasts are rendered. */
	private ListView mWeatherForecastListView;
	/** WeatherForecasts obtained from the Database. */
	private List<WeatherForecast> mWeatherForecasts;

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
		mWeatherForecasts = mDataProvider.getWeatherForecasts(new Date());
		// TODO AUDIO: no results
		if (mWeatherForecasts == null || mWeatherForecasts.size() == 0)
			playAudio(R.raw.no_wf);

		// creates the adapter used to manage the data.
		mWeatherForecastItemAdapter = new WeatherForecastItemAdapter(this,
				mWeatherForecasts, mDataProvider);

		// gets the list from the UI.
		mWeatherForecastListView = (ListView) findViewById(R.id.weather_forecast_list);
		// enables the focus on the items.
		mWeatherForecastListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mWeatherForecastListView.setAdapter(mWeatherForecastItemAdapter);
		// adds the long click to enable the help feature
		mWeatherForecastListView.setOnItemClickListener(this);
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

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(),
				mWeatherForecasts.get(position).getDate());
		ApplicationTracker.getInstance().flush();

	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				mWeatherForecasts.get(position).getDate());
		ApplicationTracker.getInstance().flush();

		SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();

		// gets the selected forecast
		WeatherForecast wf = mWeatherForecastItemAdapter.getItem(position);
		WeatherType wt = mDataProvider
				.getWeatherTypeById(wf.getWeatherTypeId());

		/*
		 * sq.addToQueue(R.raw.todayweatherforecast); sq.addToQueue(R.raw.a1);
		 * sq.addToQueue(R.raw.degree); sq.addToQueue(R.raw.and);
		 * sq.addToQueue(R.raw.weather); sq.addToQueue(wt.getAudio());
		 * 
		 * sq.play();
		 */
		System.out.println("date" + wf.getDate());
		System.out.println("Id" + wf.getId());
		System.out.println("Temp" + wf.getTemperature());
		System.out.println("Wf type" + wf.getWeatherTypeId());

		String date1 = wf.getDate();
		String[] separated1 = date1.split("-");
		play_integer(Integer.valueOf(separated1[2]));
		play_integer(Integer.valueOf(separated1[1]));
		play_integer(Integer.valueOf(separated1[0]));
		addToSoundQueue(R.raw.forecastss);
		addToSoundQueue(wt.getAudio());
		addToSoundQueue(R.raw.and);
		addToSoundQueue(R.raw.max_temp);
		play_integer(wf.getTemperature());
		addToSoundQueue(R.raw.degree_centigrade);

		playSound(true);

		return true;
	}
}
