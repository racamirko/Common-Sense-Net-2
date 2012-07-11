package com.commonsensenet.realfarm;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnDataChangeListener;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.WeatherForecastItemAdapter;

public class WeatherForecastActivity extends HelpEnabledActivity implements
		OnDataChangeListener {

	/** Celsius indicator. */
	public static String CELSIUS = "¡";
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
		mDataProvider.setWFDataChangeListener(this);

		// gets the forecast from the database.
		List<WeatherForecast> wf = mDataProvider.getWeatherForecasts();

		// creates the adapter used to manage the data.
		mWeatherForecastItemAdapter = new WeatherForecastItemAdapter(this, wf,
				mDataProvider);

		// gets the list from the UI.
		mWeatherForecastListView = (ListView) findViewById(R.id.list_weather_forecast);
		// enables the focus on the items.
		mWeatherForecastListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mWeatherForecastListView.setAdapter(mWeatherForecastItemAdapter);
		// sets the listener
		// mWeatherForecastListView.setOnItemClickListener(this);

	}

	public void onDataChanged(int WF_Size, String WF_Date, int WF_Value,
			String WF_Type, String WF_Date1, int WF_Value1, String WF_Type1,
			int WF_adminflag) {

		System.out.println("WF details updating");
		// text_2 = (TextView) findViewById(R.id.wf_text2);
		// text_4 = (TextView) findViewById(R.id.wf_text4);
		// text_5 = (TextView) findViewById(R.id.wf_text5);
		//
		// img_2 = (ImageView) findViewById(R.id.wfimg_2);
		//
		// arr[0] = R.drawable.sunny;
		// arr[1] = R.drawable.rain;
		//
		// wfvalue = WF_Value;
		// final String wftype = WF_Type;
		// final int wfvalue1 = WF_Value1;
		// final String wftype1 = WF_Type1;
		//
		// System.out.println("changing layout of WF");
		// // getWindow().setTitle("Weather Forecast"); //
		// // setBackgroundDrawableResource(arr[0]);
		//
		// // based on Type change the img1, img2
		//
		// img_1.setImageResource(R.drawable.sunny);
		// img_2.setImageResource(R.drawable.rain);
		//
		// // change the temperature values
		// text_1.setText(wfvalue + unit);
		// text_4.setText(wfvalue1 + unit);
		//
		// // Change the weather forecast message
		//
		// text_2.setText(wftype);
		// text_5.setText(wftype1);

		System.out.println("WF details finished updating");

		/*
		 * final Handler handler=new Handler(); final Runnable r = new
		 * Runnable() { public void run() {
		 * 
		 * System.out.println("changing layout of WF"); //
		 * getWindow().setTitle("Weather Forecast"); //
		 * setBackgroundDrawableResource(arr[0]);
		 * 
		 * //based on Type change the img1, img2
		 * 
		 * img_1.setImageResource(R.drawable.sunny);
		 * img_2.setImageResource(R.drawable.rain);
		 * 
		 * // change the temperature values text_1.setText(wfvalue+unit);
		 * text_4.setText(wfvalue1+unit);
		 * 
		 * //Change the weather forcast msg
		 * 
		 * text_2.setText(wftype); text_5.setText(wftype1);
		 * 
		 * handler.postDelayed(this, 60000);
		 * 
		 * } };
		 * 
		 * handler.postDelayed(r, 60000); Thread thread = new Thread() {
		 * 
		 * @Override public void run() { try { while(true) { if(i== 2){
		 * //finish(); i=0; } sleep(60000); handler.post(r); //i++; } } catch
		 * (InterruptedException e) { e.printStackTrace(); } } };
		 */

	}

	protected void onDestroy() {
		// removes the listener
		mDataProvider.setWFDataChangeListener(null);
		mDataProvider = null;
		super.onDestroy();
	}

	public void playAudio(int currentValue) {

		SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();

		if (Global.enableAudio == true) // checking for audio enable
		{
			if (currentValue == 1) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a1);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 2) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a2);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 3) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a3);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 4) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a4);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 5) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a5);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 6) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a6);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 7) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a7);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 8) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a8);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 9) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a9);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 10) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a10);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 11) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a11);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 12) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a12);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 13) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a13);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 14) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a14);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 15) {

				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a15);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 16) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a16);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 17) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a17);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 18) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a18);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.stormy);
				sq.play();
			} else if (currentValue == 19) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a19);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 20) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a20);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.overcast);
				sq.play();
			} else if (currentValue == 21) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a21);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 22) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a22);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.lightshowers);
				sq.play();
			} else if (currentValue == 23) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a23);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 24) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a24);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 25) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a25);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 26) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a26);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 27) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a27);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 28) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a28);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 29) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a29);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			} else if (currentValue == 30) {
				sq.addToQueue(R.raw.todayweatherforecast);
				sq.addToQueue(R.raw.a30);
				sq.addToQueue(R.raw.degree);
				sq.addToQueue(R.raw.and);
				sq.addToQueue(R.raw.weather);
				sq.addToQueue(R.raw.sunny);
				sq.play();
			}
		}
	}
}
