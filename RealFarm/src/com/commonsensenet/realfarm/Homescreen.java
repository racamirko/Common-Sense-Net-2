package com.commonsensenet.realfarm;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.admin.LoginActivity;
import com.commonsensenet.realfarm.aggregates.fertilize_aggregate;
import com.commonsensenet.realfarm.aggregates.harvest_aggregate;
import com.commonsensenet.realfarm.aggregates.problem_aggregate;
import com.commonsensenet.realfarm.aggregates.selling_aggregate;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ReminderTask;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;

/**
 * 
 * @author Oscar Bolaños <@oscarbolanos>
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {

	/** Indicates whether the demo data has been inserted or not. */
	public static boolean IS_INITIALIZED = false;
	/** Tag used to log the App activity. */
	public static String LOG_TAG = "Homescreen";

	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** Currently selected language. */
	private String mSelectedLanguage;

	protected void initActionListener() {

		// sets up the listeners in the home screen.
		findViewById(R.id.hmscrn_btn_weather).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_weather).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_advice).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_advice).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_video).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_video).setOnLongClickListener(this);

		findViewById(R.id.hmscrn_btn_yield).setOnClickListener(this);
		findViewById(R.id.hmscrn_btn_yield).setOnLongClickListener(this);

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
		Log.i(LOG_TAG, "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
	}

	private void insertDemoData() {
		if (!IS_INITIALIZED) {

			List<User> users = mDataProvider.getUsers();
			List<Resource> soilTypes = mDataProvider.getSoilTypes();
			List<Resource> seeds = mDataProvider.getSeedTypes();

			Object[][] plotData = {
					{ users.get(0).getId(), seeds.get(0).getId(),
							soilTypes.get(0).getId(),
							"farmer_90px_kiran_kumar_g", 1.5 },
					{ users.get(0).getId(), seeds.get(3).getId(),
							soilTypes.get(1).getId(), "farmer_90px_adam_jones",
							2.1 },
					{ users.get(1).getId(), seeds.get(5).getId(),
							soilTypes.get(0).getId(), "farmer_90px_adam_jones",
							5.6 },
					{ users.get(2).getId(), seeds.get(7).getId(),
							soilTypes.get(2).getId(), "farmer_90px_adam_jones",
							10.0 },
					{ users.get(3).getId(), seeds.get(9).getId(),
							soilTypes.get(3).getId(),
							"farmer_90px_walmart_stores", 3.0 }

			};

			for (int x = 0; x < plotData.length; x++) {
				long plotId = mDataProvider.addPlot((Long) plotData[x][0],
						(Integer) plotData[x][1], (Integer) plotData[x][2],
						(String) plotData[x][3], (Double) plotData[x][4]);
				// updates the id if I am the owner
				if ((Long) plotData[x][0] == Global.userId) {
					Global.plotId = (int) plotId;
				}
			}

			Log.d(LOG_TAG, "plot works");

			// sowing
			// mDataProvider.setSowing(1, 1, seeds.get(0).getId(),
			// "Bag of 10 Kgs", "01.12", "treated", 0, 0, "maincrop");
			// mDataProvider.setSowing(1, 1, seeds.get(0).getId(),
			// "Bag of 10 Kgs", "02.12", "treated", 0, 0, "maincrop");
			// mDataProvider.setSowing(2, 1, seeds.get(1).getId(),
			// "Bag of 10 Kgs", "03.12", "treated", 0, 0, "maincrop");
			// mDataProvider.setSowing(3, 1, seeds.get(3).getId(),
			// "Bag of 10 Kgs", "04.12", "treated", 0, 0, "intercrop");
			// mDataProvider.setSowing(4, 1, seeds.get(3).getId(),
			// "Bag of 10 Kgs", "05.12", "treated", 0, 0, "intercrop");
			// mDataProvider.setSowing(5, 1, seeds.get(4).getId(),
			// "Bag of 10 Kgs", "01.12", "not treated", 0, 0, "intercrop");
			// mDataProvider.setSowing(5, 1, seeds.get(2).getId(),
			// "Bag of 10 Kgs", "01.12", "treated", 0, 0, "intercrop");
			// mDataProvider.setSowing(5, 1, seeds.get(5).getId(),
			// "Bag of 10 Kgs", "01.12", "treated", 0, 0, "intercrop");
			//
			// // Fertilizing
			// mDataProvider.setFertilizing(1, 2, "Complex", "1L can(s)",
			// "24.12",
			// 0, 0);
			// mDataProvider.setFertilizing(2, 2, "Gypsum", "cart load(s)",
			// "25.12", 0, 0);
			// mDataProvider.setFertilizing(1, 2, "Urea", "tractor load(s)",
			// "26.12", 0, 0);
			// mDataProvider.setFertilizing(2, 2, "Super", "1L can(s)", "27.12",
			// 0, 0);
			//
			// // irrigating
			// mDataProvider.setIrrigation(1, 4, "hours", "01.12", "Spraying",
			// 0,
			// 0);
			// mDataProvider.setIrrigation(2, 4, "hours", "01.12", "Flooding",
			// 0,
			// 0);
			// mDataProvider.setIrrigation(3, 5, "hours", "02.12", "Spraying",
			// 0,
			// 0);
			// mDataProvider.setIrrigation(4, 1, "hours", "04.12", "Flooding",
			// 0,
			// 0);
			// mDataProvider.setIrrigation(2, 1, "hours", "04.12", "Flooding",
			// 0,
			// 0);

			// flags the data insertion as done.
			IS_INITIALIZED = true;
		}

	}

	protected void launchActionIntent() {
		// Temporary fix
		if (Global.selectedAction == action_selling.class) {
			startActivity(new Intent(this, Global.selectedAction));
			return;
		}

		Intent intent = null;
		int plotCount = mDataProvider.getPlotsByUserIdAndEnabledFlag(
				Global.userId, 1).size();

		// selects the next activity based on the amount of plots.
		if (plotCount == 1) {
			intent = new Intent(this, Global.selectedAction);
		} else if (plotCount == 0) {
			intent = new Intent(this, PlotListActivity.class);
		} else {
			intent = new Intent(this, ChoosePlotActivity.class);
		}

		// starts the new activity
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {

		// forces a flush operation of the application could be closed.
		ApplicationTracker.getInstance().flush();

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
								// Exit the activity
								Homescreen.this.finish();
							}
						}).show();
	}

	public void onClick(View v) {
		Log.i(LOG_TAG, "Button clicked!");
		String toastText = "";
		Intent intent = null;

		if (v.getId() == R.id.hmscrn_btn_weather) {
			startActivity(new Intent(this, WeatherForecastActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_advice) {
			// Log.d(LOG_TAG, "Starting warn info");
			// inte = new Intent(this, fertilize_aggregate.class);
			// inte.putExtra("type", "warn");
			// startActivity(inte);
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			startActivity(new Intent(this, VideoActivity.class));
			return;
		}

		/** aggregate action descriptions */
		if (v.getId() == R.id.btn_action_fertilize) {
			Log.d(LOG_TAG, "Starting Fertilize aggregate info");
			intent = new Intent(this, fertilize_aggregate.class);
			intent.putExtra("type", "yield");
			startActivity(intent);
			finish();
			return;
		}

		if (v.getId() == R.id.btn_action_sell) {
			Log.d(LOG_TAG, "Starting Selling aggregate info");
			intent = new Intent(this, selling_aggregate.class);
			intent.putExtra("type", "yield");
			startActivity(intent);
			finish();
			return;
		}

		if (v.getId() == R.id.btn_action_report) {
			Log.d(LOG_TAG, "Starting Problem aggregate info");
			intent = new Intent(this, problem_aggregate.class);
			intent.putExtra("type", "yield");
			startActivity(intent);
			finish();
			return;
		}

		if (v.getId() == R.id.btn_action_irrigate) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID);
			startActivity(intent);
			return;
		}

		if (v.getId() == R.id.btn_action_harvest) {
			Log.d(LOG_TAG, "Starting harvest aggregate info");
			intent = new Intent(this, harvest_aggregate.class);
			intent.putExtra("type", "yield");
			startActivity(intent);
			finish();
			return;
		}

		if (v.getId() == R.id.btn_action_sow) {
			intent = new Intent(this, ActionAggregateActivity.class);
			intent.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_SOW_ID);
			startActivity(intent);
			return;
		}

		if (v.getId() == R.id.btn_action_spray) {/*
												 * TODO inte = new Intent(this,
												 * ActionAggregateActivity
												 * .class);
												 * inte.putExtra(RealFarmDatabase
												 * .TABLE_NAME_ACTIONTYPE,
												 * RealFarmDatabase
												 * .ACTION_NAME_SOW_ID);
												 * startActivity(inte);
												 */
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_yield) { /*
												 * TODO Log.d(LOG_TAG,
												 * "Starting yield info"); inte
												 * = new Intent(this,
												 * yielddetails.class);
												 * inte.putExtra("type",
												 * "yield");
												 * startActivity(inte);
												 * finish();
												 */
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_market) {
			intent = new Intent(this, Marketprice_details.class);
			intent.putExtra("type", "yield");
			startActivity(intent);

			return;
		}

		if (v.getId() == R.id.hmscrn_btn_actions) {

			// creates a new dialog and configures it.
			final Dialog dialog = new Dialog(this);
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.mc_dialog);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.setOwnerActivity(this);

			// gets the available action types.
			List<Resource> data = mDataProvider.getActionTypes();
			// creates an adapter that handles the data.
			DialogAdapter adapter = new DialogAdapter(v.getContext(),
					R.layout.mc_dialog_row, data);
			ListView dialogList = (ListView) dialog
					.findViewById(R.id.dialog_list);
			dialogList.setAdapter(adapter);

			// opens the dialog.
			dialog.show();

			dialogList.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Global.selectedAction = action_sowing.class;
					launchActionIntent();

				}
			});

			return;
		}

		if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			startActivity(new Intent(this, DiaryActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			startActivity(new Intent(this, PlotListActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_sound) {

			if (!Global.isAudioEnabled) {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.ic_71px_sound_on);
				// enables the sound.
				Global.isAudioEnabled = true;
			} else {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.soundoff);
				// disables the audio.
				Global.isAudioEnabled = false;
			}

			return;
		}

		// if the text is valid it is shown as a toast.
		if (!toastText.equals("")) {
			Toast.makeText(getApplicationContext(), toastText,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

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
			snd.setImageResource(R.drawable.ic_71px_sound_on);
		} else {
			ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
			snd.setImageResource(R.drawable.soundoff);
		}

		// gets the data provider.
		mDataProvider = RealFarmProvider.getInstance(this);

		User user = null;
		// if there is no valid userId, the user is obtained using the deviceId.
		if (Global.userId == -1) {

			// TODO: what happens if the User does not exist?
			user = mDataProvider.getUsersByDeviceId(
					((RealFarmApp) getApplication()).getDeviceId()).get(0);

			// sets the user based on the deviceId.
			Global.userId = user.getId();
		} else {
			user = mDataProvider.getUserById(Global.userId);
		}

		// sets the name of the user.
		getSupportActionBar().setTitle(
				user.getFirstname() + ' ' + user.getLastname());
		getSupportActionBar().setSubtitle(user.getLocation());

		// gets the image from the resources.
		int resID = getResources().getIdentifier(user.getImagePath(),
				"drawable", "com.commonsensenet.realfarm");
		((ImageView) findViewById(R.id.hmscrn_usr_icon))
				.setImageResource(resID);

		Log.i(LOG_TAG, "scheduler activated");
		SchedulerManager.getInstance().saveTask(getApplicationContext(),
				"*/1 * * * *", // a cron string
				ReminderTask.class);
		SchedulerManager.getInstance().restart(getApplicationContext(),
				ReminderTask.class);

		// clears the database
		// initDb();
		insertDemoData();

		// adds the widgets
		updateWidgets();
		// adds the listeners
		initActionListener();
	}

	protected void updateWidgets() {

		// gets the forecast from the database.
		List<WeatherForecast> forecastList = mDataProvider
				.getWeatherForecasts(new Date());

		// if there is at least one value
		if (forecastList.size() != 0) {

			// sets the first forecast
			WeatherForecast wf = forecastList.get(0);
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
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		// handles the item selection.
		switch (item.getItemId()) {
		case R.id.menu_settings:

			// starts a new activity
			startActivity(new Intent(this, LoginActivity.class));

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void selectlang() {

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// gets the language list from the resources.
		Resources res = getResources();
		String[] languages = res.getStringArray(R.array.array_languages);

		ListView languageList = new ListView(this);
		ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				languages);
		// sets the adapter.
		languageList.setAdapter(languageAdapter);

		// adds the event listener to detect the language selection.
		languageList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// stores the selected language.
				mSelectedLanguage = (String) parent.getAdapter().getItem(
						position);

				Toast.makeText(Homescreen.this,
						"The Language selected is " + mSelectedLanguage,
						Toast.LENGTH_SHORT).show();

				// closes the dialog.
				dialog.dismiss();
			}

		});

		// sets the view
		dialog.setContentView(languageList);
		// sets the properties of the dialog.
		dialog.setTitle(R.string.dialog_select_language_title);
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	};

	protected void writeDatabaseToSDcard() {
		throw new Error("Not implemented");
	}

	// TODO: replace all sounds
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.hmscrn_btn_market) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_yield) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_fertilize) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_spray) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_sell) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_report) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_irrigate) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_harvest) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.btn_action_sow) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_actions) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			playAudio(R.raw.problems);
		} else if (v.getId() == R.id.hmscrn_btn_sound) {
			playAudio(R.raw.problems);
		} else {
			return super.onLongClick(v);
		}

		return true;
	}
}
