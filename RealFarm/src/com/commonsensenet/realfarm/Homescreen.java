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

import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.commonsensenet.realfarm.actions.action_fertilizing;
import com.commonsensenet.realfarm.actions.action_harvest;
import com.commonsensenet.realfarm.actions.action_irrigate;
import com.commonsensenet.realfarm.actions.action_problem;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.actions.action_spraying;
import com.commonsensenet.realfarm.aggregates.fertilize_aggregate;
import com.commonsensenet.realfarm.aggregates.harvest_aggregate;
import com.commonsensenet.realfarm.aggregates.problem_aggregate;
import com.commonsensenet.realfarm.aggregates.selling_aggregate;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.utils.ReminderTask;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * 
 * @author Oscar Bolaños <@oscarbolanos>
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {

	/** Indicates whether the demo data has been inserted or not. */
	public static boolean IS_INITIALIZED = false;
	/** Tag used to log the App activity. */
	public static String LOG_TAG = "Homescreen";

	/** Database provider. */
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
			Object[][] plotData = {
					{ 1, 1, "farmer_90px_kiran_kumar_g", "Clay" },
					{ 1, 2, "farmer_90px_adam_jones", "Sandy" },
					{ 2, 2, "farmer_90px_adam_jones", "Sandy" },
					{ 3, 1, "farmer_90px_adam_jones", "Loamy" },
					{ 4, 1, "farmer_90px_walmart_stores", "Loamy" } };

			List<SeedType> seeds = mDataProvider.getSeedTypes();

			for (int x = 0; x < plotData.length; x++) {
				int plotId = (int) mDataProvider.insertPlot(
						(Integer) plotData[x][0], seeds.get(x).getId(),
						(String) plotData[x][2], (String) plotData[x][3], 3.0f,
						0, 0);
				// updates the id if I am the owner
				if ((Integer) plotData[x][0] == Global.userId) {
					Global.plotId = plotId;
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
		if (Global.selectedAction == action_selling.class) { // Temporary fix
			this.startActivity(new Intent(this, Global.selectedAction));
			return;
		}

		Intent intent = null;
		int plotCount = mDataProvider.getPlotsByUserIdAndDeleteFlag(
				Global.userId, 0).size();

		// selects the next activity based on the amount of plots.
		if (plotCount == 1) {
			intent = new Intent(this, Global.selectedAction);
		} else if (plotCount == 0) {
			intent = new Intent(this, PlotsActivity.class);
		} else {
			intent = new Intent(this, ChoosePlotActivity.class);
		}

		// starts the new activity
		this.startActivity(intent);
	}

	@Override
	public void onBackPressed() {

		this.stopAudio();
		// Production code, commented out for quicker development
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

		// super.onBackPressed();
	}

	public void onClick(View v) {
		Log.i(LOG_TAG, "Button clicked!");
		String txt = "";
		Intent inte = null;

		if (v.getId() == R.id.hmscrn_btn_weather) {
			this.startActivity(new Intent(this, WeatherForecastActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_advice) {
			// Log.d(LOG_TAG, "Starting warn info");
			// inte = new Intent(this, fertilize_aggregate.class);
			// inte.putExtra("type", "warn");
			// this.startActivity(inte);
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			startActivity(new Intent(this, VideoActivity.class));
			return;
		}

		/** aggregate action descriptions */
		if (v.getId() == R.id.btn_action_fertilize) {
			Log.d(LOG_TAG, "Starting Fertilize aggregate info");
			inte = new Intent(this, fertilize_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.btn_action_sell) {
			Log.d(LOG_TAG, "Starting Selling aggregate info");
			inte = new Intent(this, selling_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.btn_action_report) {
			Log.d(LOG_TAG, "Starting Problem aggregate info");
			inte = new Intent(this, problem_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.btn_action_irrigate) {
			inte = new Intent(this, ActionAggregateActivity.class);
			inte.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
					RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID);
			this.startActivity(inte);
			return;
		}

		if (v.getId() == R.id.btn_action_harvest) {
			Log.d(LOG_TAG, "Starting harvest aggregate info");
			inte = new Intent(this, harvest_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.btn_action_sow) {
			inte = new Intent(this, ActionAggregateActivity.class);
			inte.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE, RealFarmDatabase.ACTION_TYPE_SOW_ID);
			this.startActivity(inte);
			return;
		}

		if (v.getId() == R.id.btn_action_spray) {/*
												 * TODO inte = new Intent(this,
												 * ActionAggregateActivity
												 * .class);
												 * inte.putExtra(RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
												 * RealFarmDatabase
												 * .ACTION_NAME_SOW_ID);
												 * this.startActivity(inte);
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
												 * this.startActivity(inte);
												 * this.finish();
												 */
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_market) {
			inte = new Intent(this, Marketprice_details.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);

			return;
		}

		if (v.getId() == R.id.hmscrn_btn_actions) {

			Dialog dlg = new Dialog(this);
			dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dlg.setCanceledOnTouchOutside(true);
			dlg.setContentView(R.layout.dialog_action);

			final View sow_btn = dlg.findViewById(R.id.button_action_sow);
			final View fert_btn = dlg
					.findViewById(R.id.button_action_fertilize);
			final View spray_btn = dlg.findViewById(R.id.button_action_spray);
			final View prob_btn = dlg.findViewById(R.id.button_action_report);
			final View irr_btn = dlg.findViewById(R.id.button_action_irrigate);
			final View harv_btn = dlg.findViewById(R.id.button_action_harvest);
			final View sell_btn = dlg.findViewById(R.id.button_action_sell);

			sow_btn.setOnLongClickListener(this);
			fert_btn.setOnLongClickListener(this);
			spray_btn.setOnLongClickListener(this);
			prob_btn.setOnLongClickListener(this);
			irr_btn.setOnLongClickListener(this);
			harv_btn.setOnLongClickListener(this);
			sell_btn.setOnLongClickListener(this);

			dlg.setCancelable(true);
			dlg.setOwnerActivity(this);
			dlg.show();

			sow_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Sowing clicked");
					Global.selectedAction = action_sowing.class;
					launchActionIntent();
				}
			});

			fert_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Fertilizer clicked");
					Global.selectedAction = action_fertilizing.class;
					launchActionIntent();
				}
			});

			spray_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Spraying clicked");
					Global.selectedAction = action_spraying.class;
					launchActionIntent();
				}
			});

			prob_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Problem clicked");
					Global.selectedAction = action_problem.class;
					launchActionIntent();
				}
			});

			irr_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Irrigation clicked");
					Global.selectedAction = action_irrigate.class;
					launchActionIntent();
				}
			});

			harv_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Harvest clicked");
					Global.selectedAction = action_harvest.class;
					launchActionIntent();
				}
			});

			sell_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Selling clicked");
					Global.selectedAction = action_selling.class;
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
			this.startActivity(new Intent(this, PlotsActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_sound) {

			if (!Global.enableAudio) {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.ic_71px_sound_on);
				// enables the sound.
				Global.enableAudio = true;
			} else {
				ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
				snd.setImageResource(R.drawable.soundoff);
				// disables the audio.
				Global.enableAudio = false;
			}

			return;
		}

		// help

		// usericon

		/*
		 * // Action pages
		 * 
		 * if (v.getId() == R.id.btn_action_plant) {
		 * System.out.println("Action Sowing clicked"); // Global.actionno = 1;
		 * // for sow Global.selectedAction = action_sowing.class; if
		 * (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_sowing.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * } }
		 * 
		 * if (v.getId() == R.id.btn_action_yield) {
		 * System.out.println("Action harvest clicked"); // Global.actionno = 2;
		 * // for harvest Global.selectedAction = action_harvest.class; if
		 * (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_harvest.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * } }
		 * 
		 * if (v.getId() == R.id.btn_action_diary) { startActivity(new
		 * Intent(this, DiaryActivity.class)); return; }
		 * 
		 * if (v.getId() == R.id.btn_action_fertilize) {
		 * System.out.println("Fertilize action clicked"); // Global.actionno =
		 * 4; // for fertilize Global.selectedAction = action_fertilizing.class;
		 * if (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_fertilizing.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * } }
		 * 
		 * if (v.getId() == R.id.btn_action_spray) {
		 * System.out.println("Spraying action clicked"); // Global.actionno =
		 * 5; // for spray Global.selectedAction = action_spraying.class; if
		 * (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_spraying.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * } }
		 * 
		 * if (v.getId() == R.id.home_btn_PlotInfo) {
		 * System.out.println("My settings clicked");
		 * 
		 * System.out.println("Displaying plot information list"); // TODO: why
		 * you are not saving the result? mDataProvider.getPlots(); inte = new
		 * Intent(this, Addplot_sm.class); this.startActivity(inte);
		 * this.finish(); }
		 * 
		 * // opens the video activity if (v.getId() == R.id.hmscrn_btn_video) {
		 * startActivity(new Intent(this, VideoActivity.class)); return; }
		 * 
		 * if (v.getId() == R.id.btn_action_selling) {
		 * 
		 * System.out.println("Action selling clicked"); // Global.actionno = 3;
		 * // for selling Global.selectedAction = action_selling.class; inte =
		 * new Intent(this, action_selling.class); this.startActivity(inte);
		 * this.finish(); return;
		 */
		/*
		 * if (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_selling.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * }
		 */
		// }

		/*
		 * if (v.getId() == R.id.btn_action_problem) {
		 * 
		 * System.out.println("Action Problem clicked"); // Global.actionno = 8;
		 * // for problem Global.selectedAction = action_problem.class;
		 * 
		 * if (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_problem.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (v.getId() == R.id.btn_action_irrigate) {
		 * 
		 * System.out.println("Action irrigate clicked"); // Global.actionno =
		 * 8; // for irrigate Global.selectedAction = action_irrigate.class; if
		 * (no_of_plots > 1) {
		 * 
		 * inte = new Intent(this, ChoosePlotActivity.class);
		 * this.startActivity(inte); this.finish(); return; } if (no_of_plots ==
		 * 1) {
		 * 
		 * inte = new Intent(this, action_irrigate.class);
		 * this.startActivity(inte); this.finish(); return; }
		 * 
		 * if (no_of_plots == 0) { inte = new Intent(this,
		 * My_setting_plot_info.class); this.startActivity(inte); this.finish();
		 * return;
		 * 
		 * }
		 * 
		 * /* Global.actionno=6;
		 * System.out.println("Displaying plot information list");
		 * mDataProvider.getAllPlotList(); inte = new Intent(this,
		 * Plot_Image.class); this.startActivity(inte);
		 */
		// }

		if (!txt.equals("")) {
			Toast.makeText(this.getApplicationContext(), txt,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Log.i(LOG_TAG, "App started");

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
		if (Global.enableAudio) {
			ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
			snd.setImageResource(R.drawable.ic_71px_sound_on);
		} else {
			ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
			snd.setImageResource(R.drawable.soundoff);
		}

		// gets the data provider.
		mDataProvider = RealFarmProvider.getInstance(this);

		// gets the current user object.
		User user = mDataProvider.getUserById(Global.userId);

		// sets the name of the user.
		getSupportActionBar().setTitle(
				user.getFirstname() + ' ' + user.getLastname());
		getSupportActionBar().setSubtitle("CK Pura");

		// gets the image from the resources.
		int resID = this.getResources().getIdentifier(user.getImage(),
				"drawable", "com.commonsensenet.realfarm");
		((ImageView) findViewById(R.id.hmscrn_usr_icon))
				.setImageResource(resID);

		Log.i(LOG_TAG, "scheduler activated");
		SchedulerManager.getInstance().saveTask(this.getApplicationContext(),
				"*/1 * * * *", // a cron string
				ReminderTask.class);
		SchedulerManager.getInstance().restart(this.getApplicationContext(),
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

			// sets the icon
			weatherImage.setImageResource(wf.getTypeResourceId());

		}
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.menu, menu);
	// return true;
	// }

	// public boolean onOptionsItemSelected(MenuItem item) {
	// // handle item selection
	//
	// System.out.println("Admin menu pressed ");
	// Intent HomeToAdmin = new Intent(Homescreen.this, admin.class);
	// startActivity(HomeToAdmin);
	// // admin.this.finish();
	//
	// // default:
	// return super.onOptionsItemSelected(item);
	// }

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

	// TODO: this should be modified since it will make the DB slower.
	protected void writeDatabaseToSDcard() {

		mDataProvider.getUsers(); // User
		mDataProvider.getActions(); // New action table
		mDataProvider.getMarketPrices(); // Market price
		mDataProvider.getPlots(); // New plot list
		mDataProvider.getActionTypes(); // Action names

	}

	public boolean onLongClick(View v) { // TODO: replace all sounds
		Log.i(LOG_TAG, "Long click");

		if (v.getId() == R.id.hmscrn_btn_market) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_yield) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_fertilize) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_spray) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_sell) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_report) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_irrigate) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_harvest) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.btn_action_sow) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_actions) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.hmscrn_btn_sound) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_sow) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_fertilize) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_spray) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_report) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_irrigate) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_harvest) {
			playAudio(R.raw.problems, true);
		}

		if (v.getId() == R.id.button_action_sell) {
			playAudio(R.raw.problems, true);
		}

		return true;
	}
}
