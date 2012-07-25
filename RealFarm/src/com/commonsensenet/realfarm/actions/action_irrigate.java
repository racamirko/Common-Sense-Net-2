package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.ListView;
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
	private int hrs_irrigate = 0;
	private String irr_method_sel = "0", irr_day_sel;
	private int irr_day_int;
	private RealFarmProvider mDataProvider;
	private String months_irr = "0";	
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

		playAudio(R.raw.clickingfertilising);
		
		resultsMap = new HashMap<String, String>();
		resultsMap.put("irr_method_sel", "0");
		resultsMap.put("months_irr", "0");
		resultsMap.put("irr_day_int", "0");
		resultsMap.put("hrs_irrigate", "0");

		// bg_day_irr.setImageResource(R.drawable.empty_not);
		final View item1;
		final View item2;
		final View item3;
		final View item4;
		ImageButton home;
		ImageButton help;
		item1 = (View) findViewById(R.id.dlg_lbl_method_irr);

		item3 = (View) findViewById(R.id.dlg_lbl_day_irr);
		item2 = (View) findViewById(R.id.dlg_lbl_unit_no_irr);
		item4 = (View) findViewById(R.id.dlg_lbl_month_irr);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);

		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final View method;
		final View duration;
		final View Date;

		method = (View) findViewById(R.id.method_irr_tr);
		duration = (View) findViewById(R.id.units_irr_tr);
		Date = (View) findViewById(R.id.day_irr_tr);

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
			
				displayDialogNP("Choose the day", "irr_day_int", R.raw.dateinfo, 1, 31, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 0, R.id.dlg_lbl_day_irr, R.id.day_irr_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);

			}
		});


		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				displayDialogNP("Choose the irrigation duration", "hrs_irrigate", R.raw.dateinfo, 0, 24, 0, 1, 0, R.id.dlg_lbl_unit_no_irr, R.id.units_irr_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);
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
				irr_day_int = Integer.parseInt(resultsMap.get("irr_day_int"));
				hrs_irrigate = Integer.parseInt(resultsMap.get("hrs_irrigate"));

				
				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag3;
				if (hrs_irrigate == 0) {
					flag1 = 1;

					View tr_feedback = (View) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					View tr_feedback = (View) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (irr_method_sel.toString().equalsIgnoreCase("0")) { 

					flag2 = 1;

					View tr_feedback = (View) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag2 = 0;

					View tr_feedback = (View) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_irr.toString().equalsIgnoreCase("0")
						|| irr_day_int == 0) {

					flag3 = 1;

					View tr_feedback = (View) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag3 = 0;

					irr_day_sel = irr_day_int + "." + months_irr;
					View tr_feedback = (View) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
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

		if (v.getId() == R.id.dlg_lbl_method_irr) {
			playAudioalways(R.raw.method);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_irr) {
			playAudioalways(R.raw.noofhours);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_irr) {
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

		if (v.getId() == R.id.dlg_lbl_month_irr) {
			playAudioalways(R.raw.choosethemonth);
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
				var_text.setText(choice.getShortName());
				resultsMap.put(mapEntry, choice.getValue());  
				View tr_feedback = (View) findViewById(trFeedback);
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
	
	private void displayDialogNP(String title, final String mapEntry, int openAudio, double min, double max, double init, double inc, int nbDigits, int textField, int tableRow, final int okAudio, final int cancelAudio, final int infoOkAudio, final int infoCancelAudio){ 

		final Dialog dialog = new Dialog(parentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		playAudio(openAudio); // opening audio
		
		if(!resultsMap.get(mapEntry).equals("0") && !resultsMap.get(mapEntry).equals("-1")) init = Double.valueOf(resultsMap.get(mapEntry));

		NumberPicker np = new NumberPicker(parentReference, min, max, init, inc, nbDigits);
		dialog.setContentView(np);
		
		final TextView tw_sow = (TextView) findViewById(textField);
		final View tr_feedback = (View) findViewById(tableRow);

		final TextView tw = (TextView)dialog.findViewById(R.id.tw);
		ImageButton ok = (ImageButton)dialog.findViewById(R.id.ok);
		ImageButton cancel = (ImageButton)dialog.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener(){ 
			public void onClick(View view) {
				String result = tw.getText().toString(); 
				resultsMap.put(mapEntry, result); 
				tw_sow.setText(result);
				tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				Toast.makeText(parentReference , result, Toast.LENGTH_LONG).show();
				dialog.cancel();
				playAudio(okAudio); // ok audio
		}});
        cancel.setOnClickListener(new View.OnClickListener(){ 
			public void onClick(View view) {
				dialog.cancel();
				playAudio(cancelAudio); // cancel audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG, "amount", "cancel");
		}});
        ok.setOnLongClickListener(new View.OnLongClickListener(){ 
			public boolean onLongClick(View view) {
				playAudio(infoOkAudio); // info audio
				return true;
		}});
        cancel.setOnLongClickListener(new View.OnLongClickListener(){ 
			public boolean onLongClick(View view) {
				playAudio(infoCancelAudio); // info audio
				return true;
		}});
        tw.setOnLongClickListener(new View.OnLongClickListener(){ 
			public boolean onLongClick(View view) {
				String num = tw.getText().toString();
				playAudio(R.raw.dateinfo); // info audio
				return false;
		}});
        				
		dialog.show();
	}
}