package com.commonsensenet.realfarm;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.commonsensenet.realfarm.aggregates.irrigate_aggregate;
import com.commonsensenet.realfarm.aggregates.problem_aggregate;
import com.commonsensenet.realfarm.aggregates.selling_aggregate;
import com.commonsensenet.realfarm.aggregates.sowing_aggregate;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.ReminderTask;
import com.commonsensenet.realfarm.homescreen.aggregateview.AggregateView;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 * @author Oscar Bolaños <@oscarbolanos>
 * 
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {

	/** Tag used to log the App activity. */
	public static String LOG_TAG = "Homescreen";
	/** Database provider. */
	private RealFarmProvider mDataProvider;
	/** Currently selected language. */
	private String mSelectedLanguage;

	private void initActionListener() {

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

	public void initAudio() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Enable audio");

		// set dialog message
		alertDialogBuilder
				.setMessage("Click Yes to enable audio !")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Global.enableAudio = true;
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Global.enableAudio = false;
						ImageButton snd = (ImageButton) findViewById(R.id.hmscrn_btn_sound);
						snd.setImageResource(R.drawable.soundoff);
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	protected void initDb() {
		Log.i(LOG_TAG, "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
	}

	protected void launchactionintent() {
		Intent inte1 = null;
		final int no_of_plots = mDataProvider.getPlotsByUserIdAndDeleteFlag(
				Global.userId, 0).size(); // added with audio integration
		if (no_of_plots > 1) {

			inte1 = new Intent(this, ChoosePlotActivity.class);
			this.startActivity(inte1);
			this.finish();
			return;
		}
		if (no_of_plots == 1) {

			inte1 = new Intent(this, Global.selectedAction);
			this.startActivity(inte1);
			this.finish();
			return;
		}

		if (no_of_plots == 0) {
			inte1 = new Intent(this, My_setting_plot_info.class);
			this.startActivity(inte1);
			this.finish();
			return;

		}
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
			Log.d(LOG_TAG, "Starting warn info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "warn");
			this.startActivity(inte);
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
			Log.d(LOG_TAG, "Starting irrigate aggregate info");
			inte = new Intent(this, irrigate_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
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
			Log.d(LOG_TAG, "Starting Sowing aggregate info");
			inte = new Intent(this, sowing_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_yield
				|| v.getId() == R.id.hmscrn_btn_yield) {
			Log.d(LOG_TAG, "Starting yield info");
			inte = new Intent(this, yielddetails.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_market) {
			System.out.println("Market Price details clicked");
			inte = new Intent(this, Marketprice_details.class);
			// inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.hmscrn_btn_actions) {
			// Log.d(logTag, "Starting home actions dlg");
			Dialog dlg = new Dialog(this);
			// dlg.setOnDismissListener(this);
			dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dlg.setCanceledOnTouchOutside(true);
			// dlg.setContentView(R.layout.home_action_buttons);
			dlg.setContentView(R.layout.action_selector);
			final Button sow_btn = (Button) dlg
					.findViewById(R.id.btn_action_sowing);// Action buttons

			final Button fert_btn = (Button) dlg
					.findViewById(R.id.btn_action_fertilize);// Action buttons

			final Button spray_btn = (Button) dlg
					.findViewById(R.id.btn_action_spray);// Action buttons

			final Button prob_btn = (Button) dlg
					.findViewById(R.id.btn_action_problem);// Action buttons

			final Button irr_btn = (Button) dlg
					.findViewById(R.id.btn_action_irrigate);// Action buttons

			final Button harv_btn = (Button) dlg
					.findViewById(R.id.btn_action_harvesting);// Action buttons

			final Button sell_btn = (Button) dlg
					.findViewById(R.id.btn_action_selling);// Action buttons

			dlg.findViewById(R.id.btn_action_sowing).setOnLongClickListener(
					this);

			dlg.findViewById(R.id.btn_action_fertilize).setOnLongClickListener(
					this);

			dlg.findViewById(R.id.btn_action_spray)
					.setOnLongClickListener(this);

			dlg.findViewById(R.id.btn_action_problem).setOnLongClickListener(
					this);

			dlg.findViewById(R.id.btn_action_irrigate).setOnLongClickListener(
					this);

			dlg.findViewById(R.id.btn_action_harvesting)
					.setOnLongClickListener(this);

			dlg.findViewById(R.id.btn_action_selling).setOnLongClickListener(
					this);

			// setHelpIcon(dlg.findViewById(R.id.dlg_help_indicator));

			dlg.setCancelable(true);
			dlg.setOwnerActivity(this);
			dlg.show();

			sow_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Sowing clicked");
					Global.selectedAction = action_sowing.class;
					launchactionintent();
				}
			});

			fert_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Fertilizer clicked");
					Global.selectedAction = action_fertilizing.class;
					launchactionintent();
				}
			});

			spray_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Spraying clicked");
					Global.selectedAction = action_spraying.class;
					launchactionintent();
				}
			});

			prob_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Problem clicked");
					Global.selectedAction = action_problem.class;
					launchactionintent();
				}
			});

			irr_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Irrigation clicked");
					Global.selectedAction = action_irrigate.class;
					launchactionintent();
				}
			});

			harv_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Harvest clicked");
					Global.selectedAction = action_harvest.class;
					launchactionintent();
				}
			});

			sell_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					System.out.println("Action Selling clicked");
					Global.selectedAction = action_selling.class;
					launchactionintent();
				}
			});

			return;
		}

		if (v.getId() == R.id.hmscrn_lay_btn_diary) {
			startActivity(new Intent(this, DiaryActivity.class));
			return;
		}

		if (v.getId() == R.id.hmscrn_lay_btn_plots) {
			this.startActivity(new Intent(this, Addplot_sm.class));
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

		// other buttons
		// case R.id.btn_action_diary:
		// case R.id.btn_action_fertilize:
		// case R.id.btn_action_irrigate:
		// case R.id.btn_action_plant:
		// case R.id.btn_action_problem:
		// case R.id.btn_action_spray:
		// case R.id.btn_action_yield:

		// this.startActivity(new Intent(this, OfflineMapDemo.class));

		if (!txt.equals("")) {
			Toast.makeText(this.getApplicationContext(), txt,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Log.i(LOG_TAG, "App started");

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
				user.getFirstName() + ' ' + user.getLastName());
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

		initActionListener();

		// WriteDataBaseToSDcard();
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
		Log.d("in Lang selection", "in dialog");

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
		dialog.setTitle("Please select your language");
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	};

	public void writeActionToDatabase() {

		/*
		 * System.out.println("action writing"); mDataProvider .setActionNew(1,
		 * 3, 2, "Sowing", "groundnut", 1000, 2000, "kg", "monday", 4, 5,
		 * "fertilizer 1", "problem 1", "good", 10000, "medium", "pickup", 1, 0,
		 * "treated", "pest1");
		 * 
		 * mDataProvider .setActionNew(1, 4, 5, "fertilizing", "tmv-2", 2000,
		 * 3000, "kg", "monday", 4, 5, "fertilizer 1", "problem 3", "good",
		 * 10000, "medium", "pickup", 1, 0, "treated", "pest1");
		 * 
		 * mDataProvider .setActionNew(1, 5, 3, "spraying", "groundnut", 1000,
		 * 2000, "kg", "monday", 4, 5, "fertilizer 1", "problem 1", "good",
		 * 10000, "medium", "pickup", 1, 0, "treated", "pest1");
		 * 
		 * mDataProvider .setActionNew(1, 8, 3, "harvest", "groundnut", 1000,
		 * 2000, "kg", "monday", 4, 5, "fertilizer 1", "problem 1", "good",
		 * 10000, "medium", "pickup", 1, 0, "treated", "pest1");
		 * 
		 * mDataProvider .setActionNew(8, 12, 3, "selling", "groundnut", 1000,
		 * 2000, "kg", "monday", 4, 5, "fertilizer 1", "problem 1", "good",
		 * 10000, "medium", "pickup", 1, 0, "treated", "pest1");
		 * 
		 * /* mDataProvider.setActionNew(1, 4, 5, "fertilizing", "tmv-1",2000,
		 * 3000, "kg","monday", 4, 5,"fertilizer 1", "problem 3","good", 10000,
		 * "medium", "pickup",1, 0 , "treated", "pest1");
		 */

		/*
		 * System.out.println("action reading");
		 * mDataProvider.getNewActionsList();
		 * 
		 * System.out.println("sowing writing"); mDataProvider.setSowing(500,
		 * "castor", "kgs", "today", "treated", 0, 0);
		 * 
		 * System.out.println("sowing reading"); mDataProvider.getsowing();
		 * 
		 * System.out.println("fertilizing writing");
		 * mDataProvider.setFertilizing(200, "fert 1", "kgs", "today", 1, 0);
		 * 
		 * System.out.println("fertilizing reading");
		 * mDataProvider.getfertizing();
		 * 
		 * System.out.println("spraying writing");
		 * mDataProvider.setspraying(100, "quint", "today", "prob 1", 1, 0,
		 * "pest1");
		 * 
		 * System.out.println("spraying reading"); mDataProvider.getspraying();
		 * 
		 * System.out.println("harvesting writing");
		 * mDataProvider.setHarvest(100, 200, "quintals", "tomorrow", "good", 1,
		 * 0);
		 * 
		 * System.out.println("harvesting reading");
		 * mDataProvider.getharvesting();
		 * 
		 * System.out.println("selling writing"); mDataProvider.setselling(300,
		 * 400, "kgs", "today", 5000, "medium", "pickup", 1, 0);
		 * 
		 * System.out.println("selling reading"); mDataProvider.getselling();
		 * 
		 * 
		 * System.out.println("market price writing");
		 * mDataProvider.setMarketPrice(1,"22-05-2012","groundnut", 2000, 0);
		 * 
		 * System.out.println("market price reading");
		 * mDataProvider.getMarketPrice();
		 */
		/*
		 * mSoundQueue = new SoundQueue(this);
		 * mSoundQueue.addToQueue(R.raw.audio1);
		 * mSoundQueue.addToQueue(R.raw.audio2);
		 * mSoundQueue.addToQueue(R.raw.audio3); mSoundQueue.play();
		 */

		// System.out.println("Reading getuser delete");
		System.out.println("Irrigation writing");
		mDataProvider.setIrrigation(2, "hours", // qua1 mapped to no of hours
				"today", 0, 0, "Fertilizer1");

		mDataProvider.setIrrigation(4, "hours", // qua1 mapped to no of hours
				"tomorrow", 0, 0, "Fertilizer2");

		System.out.println("Irrigation reading");
		mDataProvider.getirrigate();

		System.out.println("Problem writing");
		mDataProvider.setProblem("today", "Problem1", 0, 0);
		mDataProvider.setProblem("tomorrow", "Problem2", 0, 0);
		// mDataProvider.setProblem(String day,String probType, int sent, int
		// admin);

		System.out.println("Problem reading");
		mDataProvider.getProblem();

		System.out.println("New plot writing");
		mDataProvider.setPlotNew(1, 123, 456, "plot image1", "Loamy", 0, 0);
		mDataProvider.setPlotNew(2, 468, 356, "plot image2", "Sandy", 0, 0);
		mDataProvider.setPlotNew(2, 468, 356, "plot image2", "Sandy", 0, 0);

		System.out.println("newplot  reading");
		mDataProvider.getPlots();
		mDataProvider.getPlotDelete(0);

		System.out.println("newplot  reading based on userid");
		mDataProvider.getPlotsByUserId(1);

		System.out.println("newplot reading based on userid and plotid");
		mDataProvider.getPlotsByUserIdAndPlotId(1, 1);

	}

	public void writeDatabaseToSDcard() {
		Global.writeToSD = true;
		mDataProvider.Log_Database_backupdate();
		mDataProvider.getUsers(); // User
		mDataProvider.getActions(); // New action table
		mDataProvider.getsowing(); // Sowing
		mDataProvider.getfertizing(); // Fertilizing action
		mDataProvider.getspraying(); // Spraying action
		mDataProvider.getharvesting(); // Harvesting acion
		mDataProvider.getselling(); // Selling action
		mDataProvider.getMarketPrices(); // Market price
		mDataProvider.getPlots(); // New plot list
		mDataProvider.getSeeds(); // Seed type
		mDataProvider.getActionNames(); // Action names
		mDataProvider.getUnit(); // units
		mDataProvider.getFertilizer(); // Fertilizer
		mDataProvider.getPesticides(); // Pesticides
		mDataProvider.getProblems(); // problems
		mDataProvider.getProblemType(); // problem type
	}
}
