package com.commonsensenet.realfarm.homescreen;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.commonsensenet.realfarm.Addplot_sm;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.Marketprice_details;
import com.commonsensenet.realfarm.My_setting_plot_info;
import com.commonsensenet.realfarm.Plot_Image;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.WF_details;
import com.commonsensenet.realfarm.admin;
import com.commonsensenet.realfarm.videos;
import com.commonsensenet.realfarm.actions.action_fertilizing;
import com.commonsensenet.realfarm.actions.action_harvest;
import com.commonsensenet.realfarm.actions.action_irrigate;
import com.commonsensenet.realfarm.actions.action_problem;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.actions.action_spraying;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DummyHomescreenData;
import com.commonsensenet.realfarm.homescreen.aggregateview.AggregateView;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.overlay.PlotInformationWindow;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 * 
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {
	private String logTag = "Homescreen", lang_selected;
	private RealFarmProvider mDataProvider;
	private final Context context = this;
	private PlotInformationWindow mCurrentWindow;
	private SoundQueue mSoundQueue;

	// MediaPlayer mp = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (Global.lang_flag == 0) {
			selectlang();

		}
		super.onCreate(savedInstanceState, R.layout.homescreen);

		mDataProvider = RealFarmProvider.getInstance(context);
		ImageButton btnSound = (ImageButton) findViewById(R.id.dlg_btn_audio_play);
		Log.i(logTag, "App started");
		Log.i(logTag, "scheduler activated");
		SchedulerManager.getInstance().saveTask(this.getApplicationContext(),
				"*/1 * * * *", // a cron string
				ReminderTask.class);
		SchedulerManager.getInstance().restart(this.getApplicationContext(),
				ReminderTask.class);

		// setup listener to all buttons
		// initDb(); //Clears the database
		initActionListener();
		initTiles();
		initSoundSys();
		setHelpIcon(findViewById(R.id.helpIndicator));
		// WriteActionToDatabase();

		btnSound.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				InitAudio();
			}
		});

		WriteDataBaseToSDcard();
	}

	protected void selectlang() {
		Global.lang_flag = 1;
		Log.d("in Lang selection", "in dialog");
		final Dialog dlg = new Dialog(this);
		dlg.setContentView(R.layout.language_dialog);
		dlg.setCancelable(true);
		dlg.setTitle("Please select the language");
		Log.d("in variety sowing dialog", "in dialog");
		dlg.show();

		final Button lang1 = (Button) dlg.findViewById(R.id.home_lang_1);
		final Button lang2 = (Button) dlg.findViewById(R.id.home_lang_2);
		final Button lang3 = (Button) dlg.findViewById(R.id.home_lang_3);
		final Button lang4 = (Button) dlg.findViewById(R.id.home_lang_4);
		final Button lang5 = (Button) dlg.findViewById(R.id.home_lang_5);

		lang1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("var 1 picked ", "in dialog");
				// img_1.setMaxWidth(300);
				lang_selected = "Kannada";
				Toast.makeText(Homescreen.this,
						"The Language selected is " + lang_selected,
						Toast.LENGTH_SHORT).show();
				dlg.cancel();
			}
		});

		lang2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("var 1 picked ", "in dialog");
				// img_1.setMaxWidth(300);
				lang_selected = "Telugu";
				Toast.makeText(Homescreen.this,
						"The Language selected is " + lang_selected,
						Toast.LENGTH_SHORT).show();
				dlg.cancel();
			}
		});

		lang3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("var 1 picked ", "in dialog");
				// img_1.setMaxWidth(300);
				lang_selected = "Hindi";
				Toast.makeText(Homescreen.this,
						"The Language selected is " + lang_selected,
						Toast.LENGTH_SHORT).show();
				dlg.cancel();
			}
		});

		lang4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("var 1 picked ", "in dialog");
				// img_1.setMaxWidth(300);
				lang_selected = "Tamil";
				Toast.makeText(Homescreen.this,
						"The Language selected is " + lang_selected,
						Toast.LENGTH_SHORT).show();
				dlg.cancel();
			}
		});

		lang5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("var 1 picked ", "in dialog");
				// img_1.setMaxWidth(300);
				lang_selected = "Malayalam";
				Toast.makeText(Homescreen.this,
						"The Language selected is " + lang_selected,
						Toast.LENGTH_SHORT).show();
				dlg.cancel();
			}
		});

	}

	public void InitAudio() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Enable audio");

		// set dialog message
		alertDialogBuilder
				.setMessage("Click Yes to enable audio !")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								System.out.println("Yes");

								Global.EnableAudio = true;

							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						System.out.println("No");
						Global.EnableAudio = false;
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	public void WriteDataBaseToSDcard() {
		Global.WriteToSD = true;
		mDataProvider.Log_Database_backupdate();
		mDataProvider.getUserList(); // User
		mDataProvider.getNewActionsList(); // New action table
		mDataProvider.getsowing(); // Sowing
		mDataProvider.getfertizing(); // Fertilizing action
		mDataProvider.getspraying(); // Spraying action
		mDataProvider.getharvesting(); // Harvesting acion
		mDataProvider.getselling(); // Selling action
		mDataProvider.getMarketPrice(); // Market price
		mDataProvider.getAllPlotList(); // New plot list
		mDataProvider.getSeedsList(); // Seed type
		mDataProvider.getActionNamesList(); // Action names
		mDataProvider.getSeedTypeStages(); // Seedtype stages
		mDataProvider.getUnit(); // units
		mDataProvider.getLog(); // Log
		mDataProvider.getGrowings(); // growings
		mDataProvider.getFertilizer(); // Fertilizer
		mDataProvider.getActionTranslation(); // Action translation
		mDataProvider.getPesticides(); // Pesticides
		mDataProvider.getStages(); // stages
		mDataProvider.getProblems(); // problems
		mDataProvider.getProblemType(); // problem type

	}

	protected void initSoundSys() {
		Log.i(logTag, "Init sound sys");
		// gets and initializes the SoundQueue.
		mSoundQueue = SoundQueue.getInstance();
		mSoundQueue.init(this);

	}

	protected void initDb() {
		Log.i(logTag, "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
	}

	protected void initTiles() {
		Log.i(logTag, "Initializing tiles");
		LinearLayout layAdvice = (LinearLayout) findViewById(R.id.home_lay_advice);
		LinearLayout layActions = (LinearLayout) findViewById(R.id.home_lay_actions);
		LinearLayout layWarn = (LinearLayout) findViewById(R.id.home_lay_warn);
		LinearLayout layYield = (LinearLayout) findViewById(R.id.home_lay_yield);
		LinearLayout layWf = (LinearLayout) findViewById(R.id.home_lay_wf);

		populateTiles(InfoType.ADVICE, layAdvice);
		populateTiles(InfoType.ACTIONS, layActions);
		populateTiles(InfoType.WARN, layWarn);
		populateTiles(InfoType.YIELD, layYield);
		populateTiles(InfoType.WF, layWf);
	}

	protected void populateTiles(InfoType infoType, LinearLayout layout) {
		Vector<Recommendation> info = new Vector<Recommendation>();

		/* dummy implementation */
		DummyHomescreenData dummyData = new DummyHomescreenData(this, this, 5);
		Random rn = new Random();
		dummyData.generateDummyItems(rn.nextInt(5), info);
		RealFarmProvider dataProv = dummyData.getDataProvider();
		/* end dummy impl */

		Iterator<Recommendation> iter = info.iterator();
		while (iter.hasNext()) {
			Recommendation tmpRec = iter.next();
			ImageView tmpView = new ImageView(this);
			tmpView.setImageResource(dataProv.getActionNameById(
					tmpRec.getAction()).getRes());
			tmpView.setBackgroundResource(R.drawable.circular_icon_bg);
			layout.addView(tmpView);
			tmpView.getLayoutParams().height = 45;
			tmpView.getLayoutParams().width = 45;
		}
		info.clear();
	}

	public void WriteActionToDatabase() {

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

		System.out.println("Reading getuser delete");

	}

	private void initActionListener() {
		((Button) findViewById(R.id.home_btn_advice)).setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_advice))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_advice)).setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_actions)).setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_actions))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_actions)).setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_warn)).setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_warn))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_warn)).setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_yield)).setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_yield))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_yield)).setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_wf)).setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_wf)).setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_wf)).setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_marketprice))
				.setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_marketprice))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_marketprice))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_diary))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_diary))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_diary))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_fertilize))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_fertilize))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_diary))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_irrigate))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_irrigate))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_irrigate))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_plant))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_plant))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_plant))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_problem))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_problem))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_problem))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_spray))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_spray))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_spray))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_yield))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_yield))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_yield))
				.setOnTouchListener(this);

		((Button) findViewById(R.id.home_btn_PlotInfo))
				.setOnClickListener(this);
		((Button) findViewById(R.id.home_btn_PlotInfo))
				.setOnLongClickListener(this);
		((Button) findViewById(R.id.home_btn_PlotInfo))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_videos))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_videos))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_videos))
				.setOnTouchListener(this);

		((ImageButton) findViewById(R.id.btn_action_selling))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_selling))
				.setOnLongClickListener(this);
		((ImageButton) findViewById(R.id.btn_action_selling))
				.setOnTouchListener(this);

	}

	@Override
	protected void initKannada() {
		Log.i(logTag, "Init kannada");

		TextView tmpText = (TextView) findViewById(R.id.home_lbl_actions);
		tmpText.setTypeface(mKannadaTypeface);
		tmpText.setText(getString(R.string.k_solved));

		tmpText = (TextView) findViewById(R.id.home_lbl_advice);
		tmpText.setTypeface(mKannadaTypeface);
		tmpText.setText(getString(R.string.k_news));

		tmpText = (TextView) findViewById(R.id.home_lbl_warnings);
		tmpText.setTypeface(mKannadaTypeface);
		tmpText.setText(getString(R.string.k_farmers));

		tmpText = (TextView) findViewById(R.id.home_lbl_yield);
		tmpText.setTypeface(mKannadaTypeface);
		tmpText.setText(getString(R.string.k_harvest));
	}

	@Override
	public void onBackPressed() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}

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
								Global.lang_flag = 0;
								// Exit the activity
								Homescreen.this.finish();
							}
						}).show();
	}

	public void onClick(View v) {
		Log.i(logTag, "Button clicked!");
		String txt = "";
		Intent inte;

		int no_of_plots = mDataProvider.getAllPlotList().size();
		// String no_of_plots_str = String.valueOf(no_of_plots);

		if (v.getId() == R.id.btn_info_actions
				|| v.getId() == R.id.home_btn_actions) {
			Log.d(logTag, "Starting actions info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "actions");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_advice
				|| v.getId() == R.id.home_btn_advice) {
			Log.d(logTag, "Starting advice info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "advice");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_warn || v.getId() == R.id.home_btn_warn) {
			Log.d(logTag, "Starting warn info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "warn");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_yield
				|| v.getId() == R.id.home_btn_yield) {
			Log.d(logTag, "Starting yield info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}

		if (v.getId() == R.id.btn_info_yield || v.getId() == R.id.home_btn_wf) {
			System.out.println("WF details clicked");
			inte = new Intent(this, WF_details.class);
			// inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		if (v.getId() == R.id.home_btn_marketprice
				|| v.getId() == R.id.home_btn_marketprice) {
			System.out.println("Market Price details clicked");
			inte = new Intent(this, Marketprice_details.class);
			// inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		// Action pages

		if (v.getId() == R.id.btn_action_plant
				|| v.getId() == R.id.btn_action_plant) {
			System.out.println("Action Sowing clicked");
			Global.actionno = 1; // for sow
			// inte = new Intent(this, action_sowing.class);
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_sowing.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}
		}

		if (v.getId() == R.id.btn_action_yield
				|| v.getId() == R.id.btn_action_yield) {
			System.out.println("Action harvest clicked");
			Global.actionno = 2; // for harvest
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_harvest.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}
		}

		if (v.getId() == R.id.btn_action_diary
				|| v.getId() == R.id.btn_action_diary) {

			System.out.println("My diray clicked");

			// only one window can be displayed at the time.
			if (mCurrentWindow == null) {

				// displays the information about the plot on a different
				// window.
				mCurrentWindow = new PlotInformationWindow(
						findViewById(R.id.linearLayout1), Global.userId,
						mDataProvider);
				// detects when it gets closed.
				mCurrentWindow.setOnDismissListener(new OnDismissListener() {

					public void onDismiss() {
						mDataProvider
								.logAction(
										com.commonsensenet.realfarm.model.Log.PLOTINFO_DISMISS,
										null);
						// clears the current window when it gets
						// closed.
						mCurrentWindow = null;
					}
				});
				// shows the window.
				mCurrentWindow.show();
			}

			return;

		}

		if (v.getId() == R.id.btn_action_fertilize
				|| v.getId() == R.id.btn_action_fertilize) {
			System.out.println("Fertilize action clicked");
			Global.actionno = 4; // for fertilize
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_fertilizing.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}
		}

		if (v.getId() == R.id.btn_action_spray
				|| v.getId() == R.id.btn_action_spray) {
			System.out.println("Spraying action clicked");
			Global.actionno = 5; // for spray
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_spraying.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}
		}

		if (v.getId() == R.id.home_btn_PlotInfo) {
			System.out.println("My settings clicked");

			System.out.println("Displaying plot information list");
			mDataProvider.getAllPlotList();
			inte = new Intent(this, Addplot_sm.class);
			this.startActivity(inte);
			this.finish();
		}

		if (v.getId() == R.id.btn_action_videos
				|| v.getId() == R.id.btn_action_videos) {

			inte = new Intent(this, videos.class);
			this.startActivity(inte);
			this.finish();
			return;

		}

		if (v.getId() == R.id.btn_action_selling) {

			System.out.println("Action selling clicked");
			Global.actionno = 3; // for selling
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_selling.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}

		}

		if (v.getId() == R.id.btn_action_problem) {

			System.out.println("Action Problem clicked");
			Global.actionno = 8; // for selling
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_problem.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}

		}

		if (v.getId() == R.id.btn_action_irrigate) {

			System.out.println("Action irrigate clicked");
			Global.actionno = 8; // for selling
			if (no_of_plots > 1) {

				inte = new Intent(this, Plot_Image.class);
				this.startActivity(inte);
				this.finish();
				return;
			}
			if (no_of_plots == 1) {

				inte = new Intent(this, action_irrigate.class);
				this.startActivity(inte);
				this.finish();
				return;
			}

			if (no_of_plots == 0) {
				inte = new Intent(this, My_setting_plot_info.class);
				this.startActivity(inte);
				this.finish();
				return;

			}

			/*
			 * Global.actionno=6;
			 * System.out.println("Displaying plot information list");
			 * mDataProvider.getAllPlotList(); inte = new Intent(this,
			 * Plot_Image.class); this.startActivity(inte);
			 */
		}

		// other buttons
		// case R.id.btn_action_diary:
		// case R.id.btn_action_fertilize:
		// case R.id.btn_action_irrigate:
		// case R.id.btn_action_plant:
		// case R.id.btn_action_problem:
		// case R.id.btn_action_spray:
		// case R.id.btn_action_yield:

		// this.startActivity(new Intent(this, OfflineMapDemo.class));

		if (txt != "")
			Toast.makeText(this.getApplicationContext(), txt,
					Toast.LENGTH_SHORT).show();
	}

	public enum InfoType {
		ADVICE, ACTIONS, WARN, YIELD, WF
	};

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection

		System.out.println("Admin menu pressed ");
		Intent HomeToAdmin = new Intent(Homescreen.this, admin.class);
		startActivity(HomeToAdmin);
		// admin.this.finish();

		// default:
		return super.onOptionsItemSelected(item);
	}

}
