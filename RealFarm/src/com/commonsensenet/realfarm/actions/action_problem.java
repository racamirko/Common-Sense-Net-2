package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_problem extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_problem parentReference = this;
	private String prob_var_sel = "0", prob_crop_sel = "0", prob_day_sel, months_prob = "0";
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

		// final TextView month_prob = (TextView)
		// findViewById(R.id.dlg_lbl_month_prob);

		playAudio(R.raw.clickingfertilising);
		
		resultsMap = new HashMap<String, String>();  
		resultsMap.put("prob_var_sel", "0");
		resultsMap.put("months_prob", "0");
		resultsMap.put("prob_crop_sel", "0");
		resultsMap.put("prob_day_int", "0");
		
		// bg_date_problem.setImageResource(R.drawable.empty_not);

		final View item1;
		final View item4;

		ImageButton home;
		ImageButton help;
		item1 = (View) findViewById(R.id.dlg_lbl_var_prob);
		item4 = (View) findViewById(R.id.dlg_lbl_var_prob4);
		final View item2 = (View) findViewById(R.id.dlg_lbl_day_prob);
		final View item3 = (View) findViewById(R.id.dlg_lbl_month_prob);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		final View problem;
		final View crop;
		final View Date;

		problem = (View) findViewById(R.id.var_prob_tr);
		crop = (View) findViewById(R.id.var_prob_tr4);
		Date = (View) findViewById(R.id.day_prob_tr);

		problem.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getProblems();
				displayDialog(v, m_entries, "prob_var_sel", "Choose the problem type", R.raw.problems, R.id.dlg_lbl_var_prob, R.id.var_prob_tr, 0);
				
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				displayDialogNP("Choose the day", "prob_day_int", R.raw.dateinfo, 1, 31, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 0, R.id.dlg_lbl_day_prob, R.id.day_prob_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_prob", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_prob, R.id.day_prob_tr, 0);
	
			}

		});
		
		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getVarieties1();
				displayDialog(v, m_entries, "prob_crop_sel", "Select the variety", R.raw.problems, R.id.dlg_lbl_var_prob4, R.id.var_prob_tr4, 0);

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
				prob_day_int = Integer.parseInt(resultsMap.get("prob_day_int"));


				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag4;
				if (prob_var_sel.toString().equalsIgnoreCase("0")) { 
					flag1 = 1;

					View tr_feedback = (View) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					View tr_feedback = (View) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_prob.toString().equalsIgnoreCase("0")
						|| prob_day_int == 0) {
					flag2 = 1;

					View tr_feedback = (View) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag2 = 0;

					prob_day_sel = prob_day_int + "." + months_prob;
					View tr_feedback = (View) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}
				
				if (prob_crop_sel.toString().equalsIgnoreCase("0")) {
					flag4 = 1;

					View tr_feedback = (View) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag4 = 0;

					View tr_feedback = (View) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
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
		if (v.getId() == R.id.dlg_lbl_var_prob) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.dlg_lbl_var_prob4) {
			// playAudioalways(R.raw.problems); // TODO: put the audio
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_prob) {
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

		if (v.getId() == R.id.dlg_lbl_month_prob) {

			playAudioalways(R.raw.choosethemonth);
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
	
	private void putBackgrounds(DialogData choice, TextView var_text, int imageType){
		if(choice.getBackgroundRes() != -1) var_text.setBackgroundResource(choice.getBackgroundRes());
		if(imageType == 1 || imageType == 2){
			BitmapDrawable bd=(BitmapDrawable) parentReference.getResources().getDrawable(choice.getImageRes());
			int width = bd.getBitmap().getWidth();
			if(width>80) width = 80;
			
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(10, 0, 80-width-20, 0); 
		    var_text.setLayoutParams(llp);
			
			var_text.setBackgroundResource(choice.getImageRes());
			if (imageType == 1) var_text.setTextColor(Color.TRANSPARENT);
			else{ 
			    var_text.setGravity(Gravity.TOP); 
			    var_text.setPadding(0, 0, 0, 0); 
			    var_text.setTextSize(20); 
				var_text.setTextColor(Color.BLACK);
			}
		}
	}
	
	private void displayDialog(View v, final ArrayList<DialogData> m_entries, final String mapEntry, final String title, int entryAudio, final int varText, final int trFeedback, final int imageType){ 
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
				
				// put backgrounds (specific to the application) TODO: optimize the resize
				putBackgrounds(choice, var_text, imageType);

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