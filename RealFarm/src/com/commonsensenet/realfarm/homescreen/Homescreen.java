package com.commonsensenet.realfarm.homescreen;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buzzbox.mob.android.scheduler.SchedulerManager;
import com.commonsensenet.realfarm.Addplot_sm;
import com.commonsensenet.realfarm.ChoosePlotActivity;
import com.commonsensenet.realfarm.DiaryActivity;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.Marketprice_details;
import com.commonsensenet.realfarm.My_setting_plot_info;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.VideoActivity;
import com.commonsensenet.realfarm.WF_details;
import com.commonsensenet.realfarm.admin;
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
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 * @author Oscar Bolaños <@oscarbolanos>
 * 
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {
	public enum InfoType {
		ACTIONS, ADVICE, WARN, WF, YIELD
	}

	public static String LOG_TAG = "Homescreen";
	private final Context mContext = this;
	private RealFarmProvider mDataProvider;
	private String mSelectedLanguage;

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
		((ImageButton) findViewById(R.id.btn_action_fertilize))
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

	public void initAudio() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);

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

	protected void initSoundSys() {
		Log.i(LOG_TAG, "Init sound sys");
		// gets and initializes the SoundQueue.
		SoundQueue.getInstance().init(this);
	}

	protected void initTiles() {
		Log.i(LOG_TAG, "Initializing tiles");
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

	@Override
	public void onBackPressed() {
		// stops all playing sounds.
		SoundQueue.getInstance().stop();

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
								Global.langFlag = 0;
								// Exit the activity
								Homescreen.this.finish();
							}
						}).show();
	}

	public void onClick(View v) {
		Log.i(LOG_TAG, "Button clicked!");
		String txt = "";
		Intent inte = null;

		int no_of_plots = mDataProvider.getPlotsByUserIdAndDeleteFlag(
				Global.userId, 0).size(); // added with audio integration

		if (v.getId() == R.id.btn_info_actions
				|| v.getId() == R.id.home_btn_actions) {
			Log.d(LOG_TAG, "Starting actions info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "actions");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_advice
				|| v.getId() == R.id.home_btn_advice) {
			Log.d(LOG_TAG, "Starting advice info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "advice");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_warn || v.getId() == R.id.home_btn_warn) {
			Log.d(LOG_TAG, "Starting warn info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "warn");
			this.startActivity(inte);
			return;
		}
		if (v.getId() == R.id.btn_info_yield
				|| v.getId() == R.id.home_btn_yield) {
			Log.d(LOG_TAG, "Starting yield info");
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

		if (v.getId() == R.id.home_btn_marketprice) {
			System.out.println("Market Price details clicked");
			inte = new Intent(this, Marketprice_details.class);
			// inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
			return;
		}

		// Action pages

		if (v.getId() == R.id.btn_action_plant) {
			System.out.println("Action Sowing clicked");
			// Global.actionno = 1; // for sow
			Global.selectedAction = action_sowing.class;
			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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

		if (v.getId() == R.id.btn_action_yield) {
			System.out.println("Action harvest clicked");
			// Global.actionno = 2; // for harvest
			Global.selectedAction = action_harvest.class;
			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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

		if (v.getId() == R.id.btn_action_diary) {
			startActivity(new Intent(this, DiaryActivity.class));
			return;
		}

		if (v.getId() == R.id.btn_action_fertilize) {
			System.out.println("Fertilize action clicked");
			// Global.actionno = 4; // for fertilize
			Global.selectedAction = action_fertilizing.class;
			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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

		if (v.getId() == R.id.btn_action_spray) {
			System.out.println("Spraying action clicked");
			// Global.actionno = 5; // for spray
			Global.selectedAction = action_spraying.class;
			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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
			// TODO: why you are not saving the result?
			mDataProvider.getPlots();
			inte = new Intent(this, Addplot_sm.class);
			this.startActivity(inte);
			this.finish();
		}

		// opens the video activity
		if (v.getId() == R.id.btn_action_videos) {
			startActivity(new Intent(this, VideoActivity.class));
			return;
		}

		if (v.getId() == R.id.btn_action_selling) {

			System.out.println("Action selling clicked");
			// Global.actionno = 3; // for selling
			Global.selectedAction = action_selling.class;
			inte = new Intent(this, action_selling.class);
			this.startActivity(inte);
			this.finish();
			return;

			/*
			 * if (no_of_plots > 1) {
			 * 
			 * inte = new Intent(this, ChoosePlotActivity.class);
			 * this.startActivity(inte); this.finish(); return; } if
			 * (no_of_plots == 1) {
			 * 
			 * inte = new Intent(this, action_selling.class);
			 * this.startActivity(inte); this.finish(); return; }
			 * 
			 * if (no_of_plots == 0) { inte = new Intent(this,
			 * My_setting_plot_info.class); this.startActivity(inte);
			 * this.finish(); return;
			 * 
			 * }
			 */

		}

		if (v.getId() == R.id.btn_action_problem) {

			System.out.println("Action Problem clicked");
			// Global.actionno = 8; // for problem
			Global.selectedAction = action_problem.class;

			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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
			// Global.actionno = 8; // for irrigate
			Global.selectedAction = action_irrigate.class;
			if (no_of_plots > 1) {

				inte = new Intent(this, ChoosePlotActivity.class);
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

		if (txt != "") {
			Toast.makeText(this.getApplicationContext(), txt,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (Global.langFlag == 0) {
			selectlang();
		}

		super.onCreate(savedInstanceState, R.layout.homescreen);

		mDataProvider = RealFarmProvider.getInstance(mContext);
		ImageButton btnSound = (ImageButton) findViewById(R.id.dlg_btn_audio_play);
		Log.i(LOG_TAG, "App started");
		Log.i(LOG_TAG, "scheduler activated");
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

		btnSound.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				initAudio();
			}
		});

		TextView tmpText = (TextView) findViewById(R.id.home_lbl_actions);
		tmpText.setText(getString(R.string.k_solved));

		tmpText = (TextView) findViewById(R.id.home_lbl_advice);
		tmpText.setText(getString(R.string.k_news));

		tmpText = (TextView) findViewById(R.id.home_lbl_warnings);
		tmpText.setText(getString(R.string.k_farmers));

		tmpText = (TextView) findViewById(R.id.home_lbl_yield);
		tmpText.setText(getString(R.string.k_harvest));

		// WriteDataBaseToSDcard();
	}

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

				// marks the language as selected.
				Global.langFlag = 1;

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
		mDataProvider.getSeedTypeStages(); // Seedtype stages
		mDataProvider.getUnit(); // units
		mDataProvider.getLog(); // Log
		mDataProvider.getGrowings(); // growings
		mDataProvider.getFertilizer(); // Fertilizer
		// mDataProvider.getActionTranslation(); // Action translation
		mDataProvider.getPesticides(); // Pesticides
		mDataProvider.getStages(); // stages
		mDataProvider.getProblems(); // problems
		mDataProvider.getProblemType(); // problem type

	}

}
