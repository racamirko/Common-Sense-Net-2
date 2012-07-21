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
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_irrigate extends HelpEnabledActivityOld {
	public static final String LOG_TAG = "action_irrigate";
	private Context context = this;
	private String fert_no_sel;
	private int hrs_irrigate = 0;
	private String hrs_irrigate_sel = "0", irr_method_sel = "0", irr_day_sel;
	private int irr_day_int;
	private RealFarmProvider mDataProvider;
	private String months_irr = "0", irr_day_str;	
	private HashMap<String, String> resultsMap;

	private final action_irrigate parentReference = this;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_irrigate.this, Homescreen.class);

		startActivity(adminintent);
		action_irrigate.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_irrigate.this, Homescreen.class);

		startActivity(adminintent);
		action_irrigate.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState, R.layout.irrigate_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");
		final TextView day_irr = (TextView) findViewById(R.id.dlg_lbl_day_irr);

		playAudio(R.raw.clickingfertilising);
		
		resultsMap = new HashMap<String, String>();
		resultsMap.put("irr_method_sel", "0");
		resultsMap.put("months_irr", "0");

		final ImageView bg_method_irr = (ImageView) findViewById(R.id.img_bg_method_irr);
		final ImageView bg_hrs_irr = (ImageView) findViewById(R.id.img_bg_hrs_irr);
		final ImageView bg_day_irr = (ImageView) findViewById(R.id.img_bg_day_irr);
		final ImageView bg_month_irr = (ImageView) findViewById(R.id.img_bg_month_irr);

		// bg_day_irr.setImageResource(R.drawable.empty_not);
		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_method_irr);

		item3 = (Button) findViewById(R.id.home_btn_day_irr);
		item2 = (Button) findViewById(R.id.home_btn_units_no_irr);
		item4 = (Button) findViewById(R.id.home_btn_month_irr);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);

		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final TableRow method;
		final TableRow duration;
		final TableRow Date;

		method = (TableRow) findViewById(R.id.method_irr_tr);
		duration = (TableRow) findViewById(R.id.units_irr_tr);
		Date = (TableRow) findViewById(R.id.day_irr_tr);

		method.setOnLongClickListener(this);
		duration.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in irrigation method dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getIrrigationArray(v);
				displayDialog(v, m_entries, "irr_method_sel", "Select the irrigation method", R.raw.problems, R.id.dlg_lbl_method_irr, R.id.method_irr_tr);
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
						irr_day_int = mynp1.getValue();
						irr_day_str = String.valueOf(irr_day_int);
						day_irr.setText(irr_day_str);
						if (irr_day_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);

							bg_day_irr.setImageResource(R.drawable.empty_not);

							// tracks the application usage.
							// ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,"no_units_fertilizer", fert_no_sel);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG,
								"no_units_fertilizer", "cancel");
						dlg.cancel();
					}
				});
			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_irr);

		item2.setOnClickListener(new View.OnClickListener() {
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
						hrs_irrigate = mynp1.getValue();
						hrs_irrigate_sel = String.valueOf(hrs_irrigate);
						no_text.setText(hrs_irrigate_sel);
						if (hrs_irrigate != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_hrs_irr.setImageResource(R.drawable.empty_not);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();
					}
				});
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_irr", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_irr, R.id.day_irr_tr);
			}

		});

		Button btnNext = (Button) findViewById(R.id.irr_ok);
		Button cancel = (Button) findViewById(R.id.irr_cancel);

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				irr_method_sel = resultsMap.get("irr_method_sel");
				months_irr = resultsMap.get("months_irr");
				
				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag3;
				if (hrs_irrigate == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (irr_method_sel.toString().equalsIgnoreCase("0")) { 

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_irr.toString().equalsIgnoreCase("0")
						|| irr_day_int == 0) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag3 = 0;

					irr_day_sel = irr_day_int + "." + months_irr;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
					System.out.println("Irrigting Writing");
					mDataProvider.setIrrigation(Global.plotId, hrs_irrigate,
							"hours", irr_day_sel, irr_method_sel, 0, 0);

					System.out.println("Irrigting reading");
					mDataProvider.getharvesting();

					Intent adminintent = new Intent(action_irrigate.this,
							Homescreen.class);

					startActivity(adminintent);
					action_irrigate.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_irrigate.this,
						Homescreen.class);

				startActivity(adminintent);
				action_irrigate.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_method_irr) {
			playAudioalways(R.raw.method);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_no_irr) {
			playAudioalways(R.raw.noofhours);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_day_irr) {
			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.irr_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.irr_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_var_fert_1) {
			playAudioalways(R.raw.method1);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_var_fert_2) {
			playAudioalways(R.raw.method2);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_var_fert_3) {
			playAudioalways(R.raw.method3);
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

		if (v.getId() == R.id.home_btn_month_irr) {
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

		if (v.getId() == R.id.method_irr_tr) {
			playAudioalways(R.raw.method);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_irr_tr) {
			playAudioalways(R.raw.duration);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.day_irr_tr) {
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