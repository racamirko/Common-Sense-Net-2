package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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

public class action_problem extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_problem parentReference = this;
	private String prob_var_sel = "0", prob_crop_sel = "0", prob_day_sel, months_prob = "0",
			prob_day_str;
	private int prob_day_int;
	private HashMap<String, String> resultsMap;

	public static final String LOG_TAG = "action_problem";

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_problem.this, Homescreen.class);

		startActivity(adminintent);
		action_problem.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_problem.this, Homescreen.class);

		startActivity(adminintent);
		action_problem.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.problem_dialog);
		// System.out.println("problem done");
		super.onCreate(savedInstanceState, R.layout.problem_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		final TextView day_prob = (TextView) findViewById(R.id.dlg_lbl_day_prob);
		// final TextView month_prob = (TextView)
		// findViewById(R.id.dlg_lbl_month_prob);

		playAudio(R.raw.clickingfertilising);
		
		resultsMap = new HashMap<String, String>();  
		resultsMap.put("prob_var_sel", "0");
		resultsMap.put("months_prob", "0");
		resultsMap.put("prob_crop_sel", "0");

		final ImageView bg_type_problem = (ImageView) findViewById(R.id.img_bg_type_prob);
		final ImageView bg_date_problem = (ImageView) findViewById(R.id.img_bg_day_prob);
		final ImageView bg_month_prob = (ImageView) findViewById(R.id.img_bg_month_prob);
		// bg_date_problem.setImageResource(R.drawable.empty_not);

		final Button item1;
		final Button item4;

		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_var_prob);
		item4 = (Button) findViewById(R.id.home_btn_var_prob4);
		final Button item2 = (Button) findViewById(R.id.home_btn_day_prob);
		final Button item3 = (Button) findViewById(R.id.home_btn_month_prob);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		final TableRow problem;
		final TableRow crop;
		final TableRow Date;

		problem = (TableRow) findViewById(R.id.var_prob_tr);
		crop = (TableRow) findViewById(R.id.var_prob_tr4);
		Date = (TableRow) findViewById(R.id.day_prob_tr);

		problem.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getProblems();
				displayDialog(v, m_entries, "prob_var_sel", "Choose the problem type", R.raw.problems, R.id.dlg_lbl_var_prob, R.id.var_prob_tr);
				
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the day");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				
				playAudio(R.raw.dateinfo);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "no_units_fertilizer");

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
																		
						prob_day_int = mynp1.getValue();
						prob_day_str = String.valueOf(prob_day_int);
						day_prob.setText(prob_day_str);
						if (prob_day_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);

							bg_date_problem
									.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
						
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "number_picker",
								"cancel_no_units", "cancelnumberentry");

						dlg.cancel();
					}
				});

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_prob", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_prob, R.id.day_prob_tr);
	
			}

		});
		
		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getCrops();
				displayDialog(v, m_entries, "prob_crop_sel", "Select the crop", R.raw.problems, R.id.dlg_lbl_var_prob4, R.id.var_prob_tr4);

			}
		});

		Button btnNext = (Button) findViewById(R.id.prob_ok);
		Button cancel = (Button) findViewById(R.id.prob_cancel);

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				prob_var_sel = resultsMap.get("prob_var_sel");
				months_prob = resultsMap.get("months_prob");
				prob_crop_sel = resultsMap.get("prob_crop_sel");

				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag4;
				if (prob_var_sel.toString().equalsIgnoreCase("0")) { 
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_prob.toString().equalsIgnoreCase("0")
						|| prob_day_int == 0) {
					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag2 = 0;

					prob_day_sel = prob_day_int + "." + months_prob;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				if (prob_crop_sel.toString().equalsIgnoreCase("0")) {
					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag4 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag4 == 0) {

					System.out.println("Problem writing");
					mDataProvider.setProblem(Global.plotId, prob_day_sel,
							prob_var_sel, 0, 0, prob_crop_sel);

					startActivity(new Intent(action_problem.this,
							Homescreen.class));
					action_problem.this.finish();
					okaudio();

				} else {
					initmissingval();
				}
			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_problem.this,
						Homescreen.class);

				startActivity(adminintent);
				action_problem.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { 
		playAudioalways(R.raw.date);
		if (v.getId() == R.id.home_btn_var_prob) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.home_btn_var_prob4) {
			// playAudioalways(R.raw.problems); // TODO: put the audio
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_day_prob) {
			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.prob_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.prob_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_1) {
			playAudioalways(R.raw.problem1);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_2) {
			playAudioalways(R.raw.problem2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_3) {
			playAudioalways(R.raw.problem3);
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
			playAudioalways(R.raw.today);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_day_5) {
			playAudioalways(R.raw.tomorrows);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_month_prob) {

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.number_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.number_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.var_prob_tr) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.var_prob_tr4) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}


		if (v.getId() == R.id.day_prob_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
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