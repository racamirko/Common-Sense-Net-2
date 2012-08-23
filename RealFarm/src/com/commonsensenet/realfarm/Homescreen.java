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
import android.widget.AdapterView.OnItemLongClickListener;
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
import com.commonsensenet.realfarm.actions.FertilizeActionActivity;
import com.commonsensenet.realfarm.actions.HarvestActionActivity;
import com.commonsensenet.realfarm.actions.IrrigateActionActivity;
import com.commonsensenet.realfarm.actions.ReportActionActivity;
import com.commonsensenet.realfarm.actions.SowActionActivity;
import com.commonsensenet.realfarm.actions.SellActionActivity;
import com.commonsensenet.realfarm.actions.SprayActionActivity;
import com.commonsensenet.realfarm.admin.LoginActivity;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.ReminderTask;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;

/**
 * 
 * @author Oscar Bolaños <@oscarbolanos>
 * @author Nguyen Lisa
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {

	/** Indicates whether the demo data has been inserted or not. */
	public static boolean IS_INITIALIZED = false;

	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** Currently selected language. */
	private String mSelectedLanguage;
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
		Log.i(getLogTag(), "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
	}

	private void insertDemoData() {
		if (!IS_INITIALIZED) {
			
			mDataProvider.addMarketPrice("2012-08-18",3200, 4900,"");
			mDataProvider.addMarketPrice("2012-08-19",4000, 5200,"");
			
			/*mDataProvider.addPlot(0, 1, 3, "", 2, 2.0, 1, 1, 0, 2);

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

			Log.d(getLogTag(), "plot works");*/

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

	@Override
	public void onBackPressed() {

		// tracks the back button.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"back");

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
								// exits the application.
								ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),"exit application");
								ApplicationTracker.getInstance().flush();
								Homescreen.this.finish();
							}
						}).show();
	}

	public void onClick(View v) {
		
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
		ApplicationTracker.getInstance().flush();
		
		// activity that will be opened.
		Intent intent = null;

		if (v.getId() == R.id.hmscrn_btn_weather) {
			intent = new Intent(this, WeatherForecastActivity.class);
		} else if (v.getId() == R.id.hmscrn_btn_advice) {
			// startActivity(new Intent(this, fertilize_aggregate.class));
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
		} else if (v.getId() == R.id.hmscrn_btn_yield) {
			/*intent = new Intent(this, yielddetails.class);
			intent.putExtra("type", "yield");*/
		} else if (v.getId() == R.id.hmscrn_btn_market) {
			intent = new Intent(this, Marketprice_details.class);
			intent.putExtra("type", "yield");
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
					R.layout.mc_dialog_row, data);
			ListView dialogList = (ListView) dialog
					.findViewById(R.id.dialog_list);
			dialogList.setAdapter(adapter);

			// opens the dialog.
			dialog.show();
			
			dialogList.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					int iden = data.get(position).getAudio();
					playAudio(iden, true);
					
					ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), data.get(position).getShortName());
					ApplicationTracker.getInstance().flush();
					
					return true;
				}
			});

			dialogList.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), data.get(position).getShortName());
					ApplicationTracker.getInstance().flush();

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
					
					if (Global.selectedAction == SellActionActivity.class) { // selling does not require a plot to be done
						Intent intent = new Intent(mParentReference, Global.selectedAction);
						dialog.dismiss();
						startActivity(intent);
						return;
					} else if (Global.selectedAction == HarvestActionActivity.class){ // harvest requires the plot to have been sown during this season. It displays varieties sown this season
						plotList = mDataProvider.getPlotsByUserIdAndEnabledFlagAndHasCrops(Global.userId, 1);
						if(plotList.size() == 0){ // if no plot available, do nothing
							playAudio(R.raw.problems, true); // TODO AUDIO: put audio
							Toast.makeText(mParentReference, "Please sow on your plot before you harvest.",Toast.LENGTH_SHORT).show();
							return; 
						}
					} else { // other acticities don't need a sowing action. They display varieties sown this season ???
						plotList = mDataProvider.getPlotsByUserIdAndEnabledFlag(Global.userId, 1);
					}
					
					Intent intent = null;
					switch(plotList.size()){
						case 0: // TODO: put audio
							playAudio(R.raw.problems, true); // TODO AUDIO: put audio
							Toast.makeText(mParentReference, "Please add a plot before you do anything.",Toast.LENGTH_SHORT).show();
							intent = new Intent(mParentReference, AddPlotActivity.class);
							break;
						case 1:
							Global.plotId = plotList.get(0).getId();
							intent = new Intent(mParentReference, Global.selectedAction);
							break;
						default:
							intent = new Intent(mParentReference, ChoosePlotActivity.class);
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
				snd.setImageResource(R.drawable.ic_71px_sound_on);
				// enables the sound.
				Global.isAudioEnabled = true;
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),"audio enabled");
			} else {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.soundoff);
				// disables the audio.
				Global.isAudioEnabled = false;
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),"audio disabled");
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
				user.getFirstname() + " " + user.getLastname());
		getSupportActionBar().setSubtitle(user.getLocation());

		// gets the image from the resources.
		int resID = getResources().getIdentifier(user.getImagePath(),
				"drawable", "com.commonsensenet.realfarm");
		((ImageView) findViewById(R.id.hmscrn_usr_icon))
				.setImageResource(resID);

		Log.i(getLogTag(), "scheduler activated");
		SchedulerManager.getInstance().saveTask(getApplicationContext(),
				"*/1 * * * *", // a cron string
				ReminderTask.class);
		SchedulerManager.getInstance().restart(getApplicationContext(),
				ReminderTask.class);

		// clears the database
		// initDb();
		insertDemoData();
		
		// updates the news indicators for the aggregates
		updateAggregatesNumbers();
		
		// updates the market prices
		updateMarketPrices();
		
		// adds the widgets
		updateWidgets();
		// adds the listeners
		initActionListener();

	}

	private void updateMarketPrices() {
		TextView tw = (TextView)findViewById(R.id.hmscrn_lbl_market_price_min);
		tw.setText(String.valueOf(mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN)));
		tw = (TextView)findViewById(R.id.hmscrn_lbl_market_price_max);
		tw.setText(String.valueOf(mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX)));
	}

	private void updateAggregatesNumbers() {
		TextView tw = (TextView)findViewById(R.id.news_sow);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SOW_ID));
		tw = (TextView)findViewById(R.id.news_fertilize);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID));
		tw = (TextView)findViewById(R.id.news_irrigate);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID));
		tw = (TextView)findViewById(R.id.news_problem);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_REPORT_ID));
		tw = (TextView)findViewById(R.id.news_spray);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SPRAY_ID));
		tw = (TextView)findViewById(R.id.news_harvest);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_HARVEST_ID));
		tw = (TextView)findViewById(R.id.news_sell);
		tw.setText(mDataProvider.getAggregatesNumbers(RealFarmDatabase.ACTION_TYPE_SELL_ID));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// TODO: replace all sounds
	public boolean onLongClick(View v) {

		// long click sounds are always played, no matter the audio setting.
		if (v.getId() == R.id.hmscrn_btn_market) {
			int min = mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN);
			int max = mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX);
			// TODO AUDIO: Say the min and max prices: "Market Challekere, today prices go from " + say(min) + " to " + say(max) + " rupees"
			System.out.println("Market Challekere, today prices go from " + min + " to " + max + " rupees");
			playAudio(R.raw.ckpura_avgmarketprice, true);
		} else if (v.getId() == R.id.hmscrn_btn_yield) {
			playAudio(R.raw.ckpura_avgyield, true);
		} else if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.advice_maincrop, true);
		} else if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.weather_today, true);
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
		} else {
			return super.onLongClick(v);
		}
		
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
		ApplicationTracker.getInstance().flush();

		// shows the help icon for the view.
		showHelpIcon(v);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(item.getItemId()));
		ApplicationTracker.getInstance().flush();

		// handles the item selection.
		switch (item.getItemId()) {
		case R.id.menu_settings:

			// starts a new activity
			startActivity(new Intent(this, LoginActivity.class));

		default:
			return super.onOptionsItemSelected(item);
		}

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

	protected void writeDatabaseToSDcard() {
		throw new Error("Not implemented");
	}
}
