package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_harvest extends HelpEnabledActivityOld {
	private Context context = this;
	private int feedback_sel;
	private int harvest_no, day_harvest_int;
	private String harvest_no_sel, units_harvest = "0", feedback_txt,
			months_harvest = "0", day_harvest_sel_1 = "0";
	private RealFarmProvider mDataProvider;
	private final action_harvest parentReference = this;
	private String final_day_harvest;
	String mSelectedMonth;
	private HashMap<String, String> resultsMap;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_harvest.this, Homescreen.class);

		startActivity(adminintent);
		action_harvest.this.finish();
	}

	public static final String LOG_TAG = "action_harvest";

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		startActivity(new Intent(action_harvest.this, Homescreen.class));
		action_harvest.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState, R.layout.harvest_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		final Button smiley1;
		final Button smiley2;
		final Button smiley3;

		final ImageView bg_day_harvest = (ImageView) findViewById(R.id.img_bg_day_harvest);
		final ImageView bg_units_no_harvest = (ImageView) findViewById(R.id.img_bg_units_no_harvest);
		final ImageView bg_units_harvest = (ImageView) findViewById(R.id.img_bg_units_harvest);
		final ImageView bg_month_harvest = (ImageView) findViewById(R.id.img_bg_month_harvest);

		smiley1 = (Button) findViewById(R.id.home_btn_har_1);
		smiley2 = (Button) findViewById(R.id.home_btn_har_2);
		smiley3 = (Button) findViewById(R.id.home_btn_har_3);
		smiley1.setBackgroundResource(R.drawable.smiley_good_not);
		smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

		playAudio(R.raw.clickingharvest);
		
		resultsMap = new HashMap<String, String>(); 
		resultsMap.put("units_harvest", "0");
		resultsMap.put("months_harvest", "0");

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		System.out.println("Plant details entered1");
		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;
		System.out.println("Plant details entered2");
		item1 = (Button) findViewById(R.id.home_btn_units_no_harvest);
		item2 = (Button) findViewById(R.id.home_btn_units_harvest);
		item3 = (Button) findViewById(R.id.home_btn_month_harvest);
		item4 = (Button) findViewById(R.id.home_btn_day_harvest);

		System.out.println("Plant details entered3");
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);
		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		smiley1.setOnLongClickListener(this);
		smiley2.setOnLongClickListener(this);
		smiley3.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		final TableRow harvest_date;
		final TableRow Amount;

		harvest_date = (TableRow) findViewById(R.id.harvest_date_tr);
		Amount = (TableRow) findViewById(R.id.units_harvest_tr);

		harvest_date.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		System.out.println("Plant details entered4");
		smiley1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 1;
				feedback_txt = "good";
				smiley1.setBackgroundResource(R.drawable.smiley_good);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
				tr_feedback.setBackgroundResource(R.drawable.def_img);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "feedback", "good");
			}
		});

		smiley2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 2;
				feedback_txt = "medium";
				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
				tr_feedback.setBackgroundResource(R.drawable.def_img);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "feedback", "medium");
			}
		});

		smiley3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 3;
				feedback_txt = "bad";
				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad);
				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

				tr_feedback.setBackgroundResource(R.drawable.def_img);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "feedback", "bad");
			}
		});

		final TextView no_text_1 = (TextView) findViewById(R.id.dlg_lbl_unit_no_harvest);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Number of bags");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.noofbags);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "no_of_bags");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						harvest_no = mynp1.getValue();
						harvest_no_sel = String.valueOf(harvest_no);
						no_text_1.setText(harvest_no_sel);

						if (harvest_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_harvest
									.setImageResource(R.drawable.empty_not);

							// tracks the application usage.
							ApplicationTracker.getInstance().logEvent(
									EventType.CLICK, LOG_TAG, "no_of_bags",
									harvest_no_sel);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "no_of_bags",
								"cancel");
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units fert dialog", "in dialog");
				
				final ArrayList<DialogData> m_entries = DialogArrayLists.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_harvest", "Select the unit", R.raw.problems, R.id.dlg_lbl_units_harvest, R.id.units_harvest_tr);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_harvest", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_harvest, R.id.harvest_date_tr);
			}
		});

		final TextView day_fert = (TextView) findViewById(R.id.dlg_lbl_day_harvest);

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Year when harvested");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.dateinfo);

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						day_harvest_int = mynpd.getValue();
						day_harvest_sel_1 = String.valueOf(day_harvest_int);
						day_fert.setText(day_harvest_sel_1);
						if (day_harvest_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_day_harvest
									.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "no_bags_selection",
								"cancel");
					}
				});
			}
		});

		Button btnNext = (Button) findViewById(R.id.harvest_ok);
		Button cancel = (Button) findViewById(R.id.harvest_cancel);

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "cancel");
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				units_harvest = resultsMap.get("units_harvest");
				months_harvest = resultsMap.get("months_harvest");

				int flag1, flag2, flag3;
				// Toast.makeText(action_harvest.this, "User selected " +
				// strDateTime + "Time", Toast.LENGTH_LONG).show(); //Generate a
				// toast only if you want
				// finish(); // If you want to continue on that TimeDateActivity
				// If you want to go to new activity that code you can also
				// write here

				// to know which feedback user clicked

				// String feedback = String.valueOf(feedback_sel);
				// Toast.makeText(action_harvest.this,
				// "User selected feedback  " + feedback,
				// Toast.LENGTH_LONG).show();

				// to obtain the + - values

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok_button");

				if (feedback_sel == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "feedback");
				} else {
					flag1 = 0;
					TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

					tr_feedback.setBackgroundResource(R.drawable.def_img);

				}
				if (units_harvest.toString().equalsIgnoreCase("0") 
						|| harvest_no == 0) {
					flag2 = 1;

					TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);
					tr_units.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");
				} else {
					flag2 = 0;
					TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);
					tr_units.setBackgroundResource(R.drawable.def_img);
				}

				if (months_harvest.toString().equalsIgnoreCase("0")
						|| day_harvest_int == 0) {
					flag3 = 1;

					TableRow tr_months = (TableRow) findViewById(R.id.harvest_date_tr);

					tr_months.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "date");
				} else {
					flag3 = 0;

					final_day_harvest = day_harvest_int + "." + months_harvest;

					TableRow tr_units = (TableRow) findViewById(R.id.harvest_date_tr);
					tr_units.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
					System.out.println("harvesting writing");
					mDataProvider.setHarvest(Global.userId, Global.plotId,
							harvest_no, 0, units_harvest, final_day_harvest,
							feedback_txt, 1, 0);

					//System.out.println("harvesting reading");
					//mDataProvider.getharvesting();

					startActivity(new Intent(action_harvest.this,
							Homescreen.class));
					action_harvest.this.finish();
					okaudio();

				} else {
					initmissingval();
				}
			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(action_harvest.this, Homescreen.class));
				action_harvest.this.finish();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");
			}
		});
	}

	protected void callmonthlist() {
		// TODO Auto-generated method stub
		Log.d("in Lang selection", "in dialog");
		final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_harvest);
		final ImageView bg_month_harvest = (ImageView) findViewById(R.id.img_bg_month_harvest);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// gets the language list from the resources.
		Resources res = getResources();
		String[] languages = res.getStringArray(R.array.array_months);

		ListView monthList = new ListView(this);
		ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				languages);
		// sets the adapter.
		monthList.setAdapter(languageAdapter);

		// adds the event listener to detect the language selection.
		monthList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// stores the selected language.
				mSelectedMonth = (String) parent.getAdapter().getItem(position);

				TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

				tr_feedback.setBackgroundResource(R.drawable.def_img);
				bg_month_harvest.setImageResource(R.drawable.empty_not);

				if (mSelectedMonth.toString().equalsIgnoreCase("January")) {
					var_text.setText("01");
					months_harvest = "01";
				}

				if (mSelectedMonth.toString().equalsIgnoreCase("February")) {
					var_text.setText("02");
					months_harvest = "02";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("March")) {
					var_text.setText("03");
					months_harvest = "03";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("April")) {
					var_text.setText("04");
					months_harvest = "04";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("May")) {
					var_text.setText("05");
					months_harvest = "05";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("June")) {
					var_text.setText("06");
					months_harvest = "06";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("July")) {
					var_text.setText("07");
					months_harvest = "07";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("August")) {
					var_text.setText("08");
					months_harvest = "08";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("September")) {
					var_text.setText("09");
					months_harvest = "09";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("October")) {
					var_text.setText("10");
					months_harvest = "10";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("November")) {
					var_text.setText("11");
					months_harvest = "11";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("December")) {
					var_text.setText("12");
					months_harvest = "12";
				}

				// closes the dialog.
				dialog.dismiss();
			}

		});

		// sets the view
		dialog.setContentView(monthList);
		// sets the properties of the dialog.
		dialog.setTitle("Please select the month");
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_har_1) {

			playAudioalways(R.raw.feedbackgood);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "feedback", "audio");
		}

		if (v.getId() == R.id.home_btn_har_2) {

			playAudioalways(R.raw.feedbackmoderate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "feedback", "medium");
		}
		if (v.getId() == R.id.home_btn_har_3) {

			playAudioalways(R.raw.feedbackbad);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "feedback");

		}
		if (v.getId() == R.id.harvest_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.harvest_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.aggr_img_help) {

			playAudioalways(R.raw.help);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help");
		}

		if (v.getId() == R.id.home_btn_units_no_harvest
				|| v.getId() == R.id.home_btn_units_harvest) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.home_btn_units_1) {

			playAudioalways(R.raw.bagof10kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_units_2) {

			playAudioalways(R.raw.bagof20kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_units_3) {

			playAudioalways(R.raw.bagof50kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_day_harvest) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_month_harvest) {

			playAudioalways(R.raw.choosethemonthwhenharvested);
			ShowHelpIcon(v);

		}

		/*if (v.getId() == R.id.home_month_1) {

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.home_month_2) {

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_3) {

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_4) {

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_5) {

			playAudioalways(R.raw.may);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_6) {

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_7) {

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_8) {

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_9) {

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_10) {

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_11) {

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_12) {

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_1) {

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.home_month_2) {

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_3) {

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_4) {

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_5) {

			playAudioalways(R.raw.may);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_6) {

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_7) {

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_8) {

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_9) {

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_10) {

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_11) {

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_12) {

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v);
		}*/

		if (v.getId() == R.id.number_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.number_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.harvest_date_tr) {
			playAudioalways(R.raw.harvestyear);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_harvest_tr) {
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		return true;
	}

	protected void stopaudio() {
		SoundQueue.getInstance().stop();
	}
	
	private void displayDialog(View v, final ArrayList<DialogData> m_entries, final String mapEntry, final String title, int entryAudio, final int varText, final int trFeedback){ 
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(), R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView)dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener(){ // TODO: adapt the audio in the db
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Does whatever is specific to the application
				Log.d("var "+position+" picked ", "in dialog");
				TextView var_text = (TextView) findViewById(varText);
				DialogData choice = m_entries.get(position);
				var_text.setText(choice.getName());
				resultsMap.put(mapEntry, choice.getValue());  
				TableRow tr_feedback = (TableRow) findViewById(trFeedback);
				tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(
						EventType.CLICK, LOG_TAG, title,
						choice.getValue());
				
				Toast.makeText(parentReference, resultsMap.get(mapEntry), Toast.LENGTH_SHORT).show();
						
				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + choice.getAudio(), null, null);
				playAudio(iden);
			}});

		mList.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + m_entries.get(position).getAudio(), null, null);
				playAudioalways(iden);
				return true;
			}});
	}
}