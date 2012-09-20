package com.commonsensenet.realfarm;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.commonsensenet.realfarm.actions.FertilizeActionActivity;
import com.commonsensenet.realfarm.actions.HarvestActionActivity;
import com.commonsensenet.realfarm.actions.IrrigateActionActivity;
import com.commonsensenet.realfarm.actions.ReportActionActivity;
import com.commonsensenet.realfarm.actions.SellActionActivity;
import com.commonsensenet.realfarm.actions.SowActionActivity;
import com.commonsensenet.realfarm.actions.SprayActionActivity;
import com.commonsensenet.realfarm.admin.LoginActivity;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnWeatherForecastDataChangeListener;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.sync.AliveTask;
import com.commonsensenet.realfarm.sync.UpstreamTask;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;

/**
 * 
 * @author Oscar Bolaños <@oscarbolanos>
 * @author Nguyen Lisa
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener,
		OnWeatherForecastDataChangeListener {

	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** Currently selected language. */
	final private Homescreen mParentReference = this;

	/**
	 * Adds the Click and LongClick events to the buttons in the UI.
	 */
	protected void initActionListener() {

		// sets up the listeners in the home screen.
		findViewById(R.id.hmscrn_btn_weather).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_weather).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_advice).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_advice).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_video).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_video).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_market).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_market).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_actions).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_actions).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_lay_btn_diary).setOnClickListener(this);
		findViewById(R.id.hmscrn_lay_btn_diary).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_lay_btn_plots).setOnClickListener(this);
		findViewById(R.id.hmscrn_lay_btn_plots).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_sound).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_sound).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_usr_icon).setOnClickListener(this);
		findViewById(R.id.hmscrn_usr_icon).setOnLongClickListener(this);

		findViewById(R.id.btn_action_sow).setOnClickListener(this);
		findViewById(R.id.btn_action_sow).setOnLongClickListener(this);

		findViewById(R.id.btn_action_fertilize).setOnClickListener(this);
		findViewById(R.id.btn_action_fertilize).setOnLongClickListener(this);

		findViewById(R.id.btn_action_irrigate).setOnClickListener(this);
		findViewById(R.id.btn_action_irrigate).setOnLongClickListener(this);

		findViewById(R.id.btn_action_report).setOnClickListener(this);
		findViewById(R.id.btn_action_report).setOnLongClickListener(this);

		findViewById(R.id.btn_action_spray).setOnClickListener(this);
		findViewById(R.id.btn_action_spray).setOnLongClickListener(this);

		findViewById(R.id.btn_action_harvest).setOnClickListener(this);
		findViewById(R.id.btn_action_harvest).setOnLongClickListener(this);

		findViewById(R.id.btn_action_sell).setOnClickListener(this);
		findViewById(R.id.btn_action_sell).setOnLongClickListener(this);
	}

	protected void initDb() {
		Log.i(getLogTag(), "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
	}

	@Override
	public void onBackPressed() {

		// tracks the back button.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(), "back");

		// forces a flush operation of the application could be closed.
		ApplicationTracker.getInstance().flushAll();

		// stops all active audio from playing.
		stopAudio();

		// confirms that the user wants to leave the application.
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exitTitle)
				.setMessage(R.string.exitMsg)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// exits the application.
								ApplicationTracker.getInstance().logEvent(
										EventType.CLICK, Global.userId,
										getLogTag(), "exit application");
								Homescreen.this.finish();
							}
						}).show();
	}

	public void onClick(View v) {
		stopAudio();

		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		// activity that will be opened.
		Intent intent = null;

		if (v.getId() == R.id.hmscrn_btn_weather) {
			intent = new Intent(this, WeatherForecastActivity.class);
		} else if (v.getId() == R.id.hmscrn_btn_advice) {
			intent = new Intent(this, AdviceActivity.class);
		} else if (v.getId() == R.id.hmscrn_btn_video) {
			intent = new Intent(this, VideoActivity.class);
		} else if (v.getId() == R.id.btn_action_fertilize) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID);
		} else if (v.getId() == R.id.btn_action_sell) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_SELL_ID);
		} else if (v.getId() == R.id.btn_action_report) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_REPORT_ID);
		} else if (v.getId() == R.id.btn_action_irrigate) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID);
		} else if (v.getId() == R.id.btn_action_harvest) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_HARVEST_ID);
		} else if (v.getId() == R.id.btn_action_sow) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_SOW_ID);
		} else if (v.getId() == R.id.btn_action_spray) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_SPRAY_ID);
		} else if (v.getId() == R.id.hmscrn_btn_market) {
			intent = new Intent(this, Marketprice_details.class);
		} else if (v.getId() == R.id.hmscrn_btn_actions) {

			// creates a new dialog and configures it.
			final Dialog dialog = new Dialog(this);
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.mc_dialog);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.setOwnerActivity(this);

			// gets the available action types.
			final List<Resource> data = mDataProvider.getActionTypes();
			// creates an adapter that handles the data.
			final DialogAdapter adapter = new DialogAdapter(v.getContext(),
					data);
			ListView dialogList = (ListView) dialog
					.findViewById(R.id.dialog_list);
			dialogList.setAdapter(adapter);

			// opens the dialog.
			dialog.show();

			dialogList
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							int iden = data.get(position).getAudio();
							playAudio(iden, true);

							ApplicationTracker.getInstance().logEvent(
									EventType.LONG_CLICK, Global.userId,
									getLogTag(),
									data.get(position).getShortName());

							return true;
						}
					});

			dialogList.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					ApplicationTracker.getInstance().logEvent(EventType.CLICK,
							Global.userId, getLogTag(),
							data.get(position).getShortName());

					Resource selectedAction = adapter.getItem(position);

					switch (selectedAction.getId()) {
					case RealFarmDatabase.ACTION_TYPE_SOW_ID:
						Global.selectedAction = SowActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
						Global.selectedAction = FertilizeActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
						Global.selectedAction = IrrigateActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
						Global.selectedAction = ReportActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
						Global.selectedAction = SprayActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
						Global.selectedAction = HarvestActionActivity.class;
						break;
					case RealFarmDatabase.ACTION_TYPE_SELL_ID:
						Global.selectedAction = SellActionActivity.class;
						break;
					default:
						return;
					}

					// intent to launch
					List<Plot> plotList;

					// selling does not require a plot to be done.
					if (Global.selectedAction == SellActionActivity.class) {
						Intent intent = new Intent(mParentReference,
								Global.selectedAction);
						dialog.dismiss();
						startActivity(intent);
						return;

						// harvest requires the plot to have been sown during
						// this season. It displays the varieties sown this
						// season.
					} else if (Global.selectedAction == HarvestActionActivity.class) {
						plotList = mDataProvider
								.getPlotsByUserIdAndEnabledFlagAndHasCrops(
										Global.userId, 1);
						// if no plot available, do nothing
						if (plotList.size() == 0) {
							Toast.makeText(
									mParentReference,
									"Please sow on your plot before you harvest.",
									Toast.LENGTH_SHORT).show();
							playAudio(R.raw.please_sow_before_harvest, true);

							return;
						}
					} else {
						// other activities do not need a sowing action.
						// They display varieties sown this season ???
						plotList = mDataProvider
								.getPlotsByUserIdAndEnabledFlag(Global.userId,
										1);
					}

					Intent intent = null;
					switch (plotList.size()) {
					case 0:
						Toast.makeText(mParentReference,
								"Please add a plot before you do anything.",
								Toast.LENGTH_SHORT).show();

						playAudio(R.raw.please_plot_before_anything, true);

						intent = new Intent(mParentReference,
								AddPlotActivity.class);
						break;
					case 1:
						Global.plotId = plotList.get(0).getId();
						intent = new Intent(mParentReference,
								Global.selectedAction);
						break;
					default:
						intent = new Intent(mParentReference,
								ChoosePlotActivity.class);
						break;

					}
					dialog.dismiss();
					startActivity(intent);

				}
			});

			// no need to start an intent.
			return;
		} else if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			intent = new Intent(this, DiaryActivity.class);
		} else if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			intent = new Intent(this, PlotListActivity.class);
		} else if (v.getId() == R.id.hmscrn_btn_sound) {

			if (!Global.isAudioEnabled) {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.ic_sound_on);
				// enables the sound.
				Global.isAudioEnabled = true;
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "audio enabled");
				playAudio(R.raw.yes_sound_help);

			} else {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.ic_sound_off);
				// disables the audio.
				Global.isAudioEnabled = false;
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "audio disabled");
				playAudio(R.raw.no_sound_help);
			}

			// no need to start an intent
			return;
		}

		// starts the intent if valid.
		if (intent != null) {
			startActivity(intent);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// sets the DeviceId which is used as part of the name of the log file.
		ApplicationTracker.getInstance().setDeviceId(
				((RealFarmApp) getApplication()).getDeviceId());

		// adds the Task to the scheduler.
		SchedulerManager.getInstance().saveTask(getApplicationContext(),
				"*/1 * * * *", // a cron string
				UpstreamTask.class);
		SchedulerManager.getInstance().saveTask(getApplicationContext(),
				"0 12 * * *", // a cron string
				AliveTask.class);
		SchedulerManager.getInstance().restart(getApplicationContext(),
				UpstreamTask.class);
		SchedulerManager.getInstance().restart(getApplicationContext(),
				AliveTask.class);

		// clears the navigation to an action.
		Global.selectedAction = null;

		// disables the back button since this is the home screen
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		// disables the home button in the home screen.
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		// sets the layout of the activity.
		setContentView(R.layout.act_homescreen);

		// starts the audio manager.
		SoundQueue.getInstance().init(this);

		// sets the audio icon based on the audio preferences.
		if (Global.isAudioEnabled) {
			ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
			snd.setImageResource(R.drawable.ic_sound_on);
		} else {
			ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
			snd.setImageResource(R.drawable.ic_sound_off);
		}

		// gets the data provider.
		mDataProvider = RealFarmProvider.getInstance(this);

		User user = null;
		// if there is no valid userId, the user is obtained using the deviceId.
		if (Global.userId == -1) {

			List<User> userList = mDataProvider
					.getUsersByDeviceId(((RealFarmApp) getApplication())
							.getDeviceId());

			// gets the first user in the list.
			if (userList.size() > 0) {
				user = userList.get(0);

				// sets the user based on the deviceId.
				Global.userId = user.getId();
			}
		} else {
			user = mDataProvider.getUserById(Global.userId);
		}

		// sets the name of the user.
		getSupportActionBar().setTitle(
				user.getFirstname() + " " + user.getLastname());
		getSupportActionBar().setSubtitle(user.getLocation());

		// sets the
		int userImageResId;
		if (user.getImagePath() != null) {
			// gets the image from the resources.
			userImageResId = getResources().getIdentifier(user.getImagePath(),
					"drawable", "com.commonsensenet.realfarm");
		} else {
			userImageResId = R.drawable.farmer_default;
		}

		((ImageView) findViewById(R.id.hmscrn_usr_icon))
				.setImageResource(userImageResId);

		// adds the listeners
		initActionListener();
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// loads the menu from the XML.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	public void onDataChanged(String date, int temperature, int weatherTypeId) {
		String today = RealFarmProvider.sDateFormat.format(new Date());

		// if it is today's date the forecast is updated.
		if (today.equalsIgnoreCase(date)) {

			// gets the weather tile elements.
			ImageView weatherImage = (ImageView) findViewById(R.id.hmscrn_img_weather);
			TextView weatherTemp = (TextView) findViewById(R.id.hmscrn_lbl_weather);

			// sets the temperature
			weatherTemp.setText(temperature + WeatherForecastActivity.CELSIUS);

			// loads the active weather type.
			WeatherType wt = mDataProvider.getWeatherTypeById(weatherTypeId);

			// sets the icon
			if (wt != null) {
				weatherImage.setImageResource(wt.getImage());
			}
		}
	}

	public boolean onLongClick(View v) {

		// long click sounds are always played, no matter the audio setting.
		if (v.getId() == R.id.hmscrn_btn_market) {
			int min = mDataProvider
					.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN);
			int max = mDataProvider
					.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX);

			addToSoundQueue(R.raw.chal_max_price);
			play_integer(max);
			addToSoundQueue(R.raw.rupees_every_quintal);

			addToSoundQueue(R.raw.chal_min_price);
			play_integer(min);
			addToSoundQueue(R.raw.rupees_every_quintal);
			playSound();
		} else if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.advice_maincrop, true);
		} else if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.wf_forecast, true);
		} else if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.new_video, true);
		} else if (v.getId() == R.id.btn_action_fertilize) {
			playAudio(R.raw.fertilizing_lastweek, true);
		} else if (v.getId() == R.id.btn_action_spray) {
			playAudio(R.raw.spraying_lastweek, true);
		} else if (v.getId() == R.id.btn_action_sell) {
			playAudio(R.raw.selling_lastweek, true);
		} else if (v.getId() == R.id.btn_action_report) {
			playAudio(R.raw.report_lastweek, true);
		} else if (v.getId() == R.id.btn_action_irrigate) {
			playAudio(R.raw.irrigation_lastweek, true);
		} else if (v.getId() == R.id.btn_action_harvest) {
			playAudio(R.raw.harvest_lastweek, true);
		} else if (v.getId() == R.id.btn_action_sow) {
			playAudio(R.raw.sowing_lastweek, true);
		} else if (v.getId() == R.id.hmscrn_btn_actions) {
			playAudio(R.raw.latestfarm, true);
		} else if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			playAudio(R.raw.your_diary, true);
		} else if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			playAudio(R.raw.your_plot, true);
		} else if (v.getId() == R.id.hmscrn_btn_sound) {
			playAudio(R.raw.sound22, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.home_help, true);

		} else {
			return super.onLongClick(v);
		}

		// tracks the user action.
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		// shows the help icon for the view since an audio was played.
		showHelpIcon(v);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "help");
			playAudio(R.raw.home_help, true);
			stopAudio();
			return true;
		}

		// handles the item selection.
		switch (item.getItemId()) {
		case R.id.menu_settings:
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "menu_settings");
			// starts a new activity
			startActivity(new Intent(this, LoginActivity.class));

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	protected void onPause() {
		super.onPause();

		// removes the listener
		mDataProvider.setWeatherForecastDataChangeListener(null);
	}

	protected void onResume() {
		super.onResume();

		// adds the weather listener used to detect when new weather data is
		// available.
		mDataProvider.setWeatherForecastDataChangeListener(this);
	}

	@Override
	public void onStart() {
		super.onStart();

		// updates the news indicators for the aggregates
		updateAggregatesNumbers();
		// updates the market prices
		updateMarketPrices();
		// updates the advice news
		updateAdviceNumbers();
		// adds the widgets
		updateWeatherForecast();
	}

	/**
	 * Updates the number of advices for the user.
	 */
	private void updateAdviceNumbers() {
		TextView tw = (TextView) findViewById(R.id.news_advice);
		tw.setText(mDataProvider.getRecommendationCountByUser(Global.userId)
				+ "");
	}

	/**
	 * Updates all the aggregate numbers indicating the number of actions
	 * performed in the last 15 days that match this.
	 */
	private void updateAggregatesNumbers() {
		TextView tw = (TextView) findViewById(R.id.news_sow);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SOW_ID));
		tw = (TextView) findViewById(R.id.news_fertilize);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID));
		tw = (TextView) findViewById(R.id.news_irrigate);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID));
		tw = (TextView) findViewById(R.id.news_problem);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_REPORT_ID));
		tw = (TextView) findViewById(R.id.news_spray);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SPRAY_ID));
		tw = (TextView) findViewById(R.id.news_harvest);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_HARVEST_ID));
		tw = (TextView) findViewById(R.id.news_sell);
		tw.setText(mDataProvider
				.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SELL_ID));
	}

	/**
	 * Updates the MarkerPrice Tile.
	 */
	private void updateMarketPrices() {
		TextView tw = (TextView) findViewById(R.id.hmscrn_lbl_market_price_min);
		tw.setText(String.valueOf(mDataProvider
				.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN)));
		tw = (TextView) findViewById(R.id.hmscrn_lbl_market_price_max);
		tw.setText(String.valueOf(mDataProvider
				.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX)));
	}

	/**
	 * Updates the WeatherTile with the the forecast matching the current date.
	 * If the data is not available an icon indicating this is used.
	 */
	private void updateWeatherForecast() {

		// gets the forecast matching the date.
		WeatherForecast wf = mDataProvider
				.getWeatherForecastByDate(RealFarmProvider.sDateFormat
						.format(new Date()));

		// if there is at least one value
		if (wf != null) {

			// sets the image and text related to the forecast.
			ImageView weatherImage = (ImageView) findViewById(R.id.hmscrn_img_weather);
			TextView weatherTemp = (TextView) findViewById(R.id.hmscrn_lbl_weather);
			weatherTemp.setText(wf.getTemperature()
					+ WeatherForecastActivity.CELSIUS);

			WeatherType wt = mDataProvider.getWeatherTypeById(wf
					.getWeatherTypeId());

			// sets the icon
			if (wt != null) {
				weatherImage.setImageResource(wt.getImage());
			}
		} else {
			// shows the unknown weather forecast icon.
			ImageView weatherImage = (ImageView) findViewById(R.id.hmscrn_img_weather);
			TextView weatherTemp = (TextView) findViewById(R.id.hmscrn_lbl_weather);
			weatherTemp.setText("?");
			weatherImage.setImageResource(R.drawable.wf_unknown);

		}
	}
}
