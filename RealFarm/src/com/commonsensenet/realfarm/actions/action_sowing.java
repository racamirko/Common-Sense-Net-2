package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_sowing extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_sowing parentReference = this;
	private int sow_no;
	private int day_sow_int;
	private String sow_no_sel;
	private String day_sow_str;
	private String months_sow = "0";
	private String treatment_sow = "0";
	private String cropType_sow = "0";
	private String days_sel_sow = "0";
	private String units_sow = "10"; // TODO: find out what
	private int seed_sow = 0; // seed_sow needs to be an int
	private HashMap<String, String> resultsMap;

	public static final String LOG_TAG = "action_sowing";

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState, R.layout.sowing_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));
		
		resultsMap = new HashMap<String, String>();
		resultsMap.put("months_sow", "0");
		resultsMap.put("treatment_sow", "0");
		resultsMap.put("cropType_sow", "0");
		resultsMap.put("seed_sow", "0");

		System.out.println("plant done");
		final TextView day_sow = (TextView) findViewById(R.id.dlg_lbl_day_sow);
		// final TextView month_sow = (TextView)
		// findViewById(R.id.dlg_lbl_month_sow);

		playAudio(R.raw.thankyouclickingactionsowing);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final ImageView bg_units_no_sow = (ImageView) findViewById(R.id.img_bg_units_no_sow);
		final ImageView bg_units_sow = (ImageView) findViewById(R.id.img_bg_units_sow);
		final ImageView bg_treatment_sow = (ImageView) findViewById(R.id.img_bg_treatment_sow);
		final ImageView bg_day_sow = (ImageView) findViewById(R.id.img_bg_day_sow);
		final ImageView bg_month_sow = (ImageView) findViewById(R.id.img_bg_month_sow);

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;
		final View item7;
		View home;
		View help;
		item1 = findViewById(R.id.home_btn_var_sow);
		item2 = findViewById(R.id.home_btn_units_sow);
		item3 = findViewById(R.id.home_btn_day_sow);
		item4 = findViewById(R.id.home_btn_treat_sow);
		item5 = findViewById(R.id.home_btn_units_no_sow);
		item7 = findViewById(R.id.home_btn_intercrop_sow);

		final Button item6 = (Button) findViewById(R.id.home_btn_month_sow);
		home = findViewById(R.id.aggr_img_home);
		help = findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final TableRow variety;
		final TableRow Amount;
		final TableRow Date;
		final TableRow Treatment;
		final TableRow Intercrop;

		variety = (TableRow) findViewById(R.id.seed_type_sow_tr);
		Amount = (TableRow) findViewById(R.id.units_sow_tr);
		Date = (TableRow) findViewById(R.id.day_sow_tr);
		Treatment = (TableRow) findViewById(R.id.treatment_sow_tr);
		Intercrop = (TableRow) findViewById(R.id.intercrop_sow_tr);

		variety.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);
		Treatment.setOnLongClickListener(this);
		Intercrop.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sow dialog", "in dialog");

				ArrayList<DialogData> m_entries = mDataProvider.getVarieties();
				displayDialog(v, m_entries, "seed_sow", "Select the variety", R.raw.problems, R.id.dlg_var_text_sow, R.id.seed_type_sow_tr);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the day");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.dateinfo); // 20-06-2012

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok)) // 20-06-2012
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						day_sow_int = mynpd.getValue();
						day_sow_str = String.valueOf(day_sow_int);
						day_sow.setText(day_sow_str);
						if (day_sow_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_day_sow.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units", "cancel");
					}
				});
			}
		});
                       
		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in treatment sow dialog", "in dialog");

				ArrayList<DialogData> m_entries = DialogArrayLists.getTreatmentArray(v);
				displayDialog(v, m_entries, "treatment_sow", "Select if the seeds were treated", R.raw.bagof50kg, R.id.dlg_lbl_treat_sow, R.id.treatment_sow_tr);
		
			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_sow);
		
		item5.setOnClickListener(new View.OnClickListener() {
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
						LOG_TAG, "bags");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				dlg.findViewById(R.id.number_ok).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.number_cancel).setOnLongClickListener(
						parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						sow_no = mynp1.getValue();
						sow_no_sel = String.valueOf(sow_no);
						no_text.setText(sow_no_sel);
						if (sow_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_sow
									.setImageResource(R.drawable.empty_not);

							// tracks the application usage.
							ApplicationTracker.getInstance().logEvent(
									EventType.CLICK, LOG_TAG, "bags",
									sow_no_sel);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", "cancel");
					}
				});

			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in intercrop sow dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getIntercropArray(v);
				displayDialog(v, m_entries, "cropType_sow", "Main crop or intercrop?", R.raw.bagof50kg, R.id.dlg_lbl_intercrop_sow, R.id.intercrop_sow_tr);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in treat sow dialog", "in dialog");
				
				stopaudio();
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_sow", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_sow, R.id.day_sow_tr);
			}

		});

		Button btnNext = (Button) findViewById(R.id.sow_ok);
		Button cancel = (Button) findViewById(R.id.sow_cancel);

		btnNext.setOnLongClickListener(this); // Integration
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

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok");
				
				months_sow = resultsMap.get("months_sow");
				treatment_sow = resultsMap.get("treatment_sow");
				cropType_sow = resultsMap.get("cropType_sow");
				seed_sow = Integer.parseInt(resultsMap.get("seed_sow"));
				

				// Toast.makeText(action_sowing.this, "User enetred " +
				// sow_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag3, flag4, flag5;
				if (seed_sow == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "variety");

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (/*units_sow.toString().equalsIgnoreCase("0") || */sow_no == 0) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (treatment_sow.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "treatment");

				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_sow.toString().equalsIgnoreCase("0")
						|| day_sow_int == 0) {

					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "day");

				} else {

					flag4 = 0;

					days_sel_sow = day_sow_int + "." + months_sow;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				if (cropType_sow.toString().equalsIgnoreCase("0")) {

					flag5 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.intercrop_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "intercrop");

				} else {

					flag5 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.intercrop_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
					System.out.println("sowing writing");
					mDataProvider.setSowing(Global.plotId, sow_no, seed_sow,
							units_sow, days_sel_sow, treatment_sow, 0, 0, cropType_sow);

					Intent adminintent = new Intent(action_sowing.this,
							Homescreen.class);
					startActivity(adminintent);
					action_sowing.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_sowing.this,
						Homescreen.class);

				startActivity(adminintent);
				action_sowing.this.finish();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_var_sow) {

			playAudioalways(R.raw.varietyofseedssowd);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "variety");
		}

		if (v.getId() == R.id.home_btn_units_sow
				|| v.getId() == R.id.home_btn_units_no_sow) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.home_btn_day_sow) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day");
		}

		if (v.getId() == R.id.home_btn_treat_sow) {

			playAudioalways(R.raw.treatmenttoseeds1);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "treatment");
		}

		if (v.getId() == R.id.sow_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.sow_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help");
		}

		if (v.getId() == R.id.button_variety_1) {

			System.out.println("variety sow1 called");
			playAudioalways(R.raw.bajra);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_2) {

			playAudioalways(R.raw.castor);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_3) {

			playAudioalways(R.raw.cowpea);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_4) {

			playAudioalways(R.raw.greengram);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_5) {

			playAudioalways(R.raw.groundnut1);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_6) {

			playAudioalways(R.raw.horsegram);
			ShowHelpIcon(v);

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

		if (v.getId() == R.id.home_day_1) {

			playAudioalways(R.raw.twoweeksbefore);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_2) {

			playAudioalways(R.raw.oneweekbefore);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_3) {

			playAudioalways(R.raw.yesterday);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_4) {
			playAudioalways(R.raw.todayonly);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_day_5) {
			playAudioalways(R.raw.tomorrows);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_treat_sow_1) {
			playAudioalways(R.raw.treatmenttoseeds2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_treat_sow_2) {
			playAudioalways(R.raw.treatmenttoseeds3);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.seed_type_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.variety);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		/*
		 * if (v.getId() == R.id.variety_pest_txt_btn) { //20-06-2012
		 * playAudio(R.raw.pesticidename); ShowHelpIcon(v); }
		 */

		if (v.getId() == R.id.treatment_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.treatment);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.intercrop_sow_tr || v.getId() == R.id.home_btn_intercrop_sow) { // 20-06-2012 + added
			playAudioalways(R.raw.intercrop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_month_sow) { // added

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.number_ok) { // added

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.number_cancel) { // added

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.day_sow_tr) { // added

			playAudioalways(R.raw.date);
			ShowHelpIcon(v); // added for help icon
		}

		return true;
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