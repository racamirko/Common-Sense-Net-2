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
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_spraying extends HelpEnabledActivityOld {

	public static final String LOG_TAG = "action_spraying";

	private Context context = this;
	private String day_sel_spray = "0";
	private int day_spray_int;
	private RealFarmProvider mDataProvider;
	private String months_spray = "0";
	private final action_spraying parentReference = this;
	private String pest_sel_spray = "0";
	private String prob_sel_spray = "0";
	private int spray_no;
	private String unit_sel_spray = "0";
	private HashMap<String, String> resultsMap;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_spraying.this, Homescreen.class);

		startActivity(adminintent);
		action_spraying.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		startActivity(new Intent(action_spraying.this, Homescreen.class));
		action_spraying.this.finish();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);
		Log.d("in spray dialog", "in dialog");
		
		super.onCreate(savedInstanceState, R.layout.spraying_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		playAudio(R.raw.clickingspraying);

		resultsMap = new HashMap<String, String>();
		resultsMap.put("unit_sel_spray", "0");
		resultsMap.put("pest_sel_spray", "0");
		resultsMap.put("prob_sel_spray", "0");
		resultsMap.put("months_spray", "0");
		resultsMap.put("day_spray_int", "0");
		resultsMap.put("spray_no", "0");
		
		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;

		ImageButton home;
		ImageButton help;
		item1 = (View) findViewById(R.id.dlg_lbl_prob_spray);
		item2 = (View) findViewById(R.id.dlg_lbl_pest_spray);
		item3 = (View) findViewById(R.id.dlg_lbl_units_spray);
		item4 = (View) findViewById(R.id.dlg_lbl_day_spray);
		item5 = (View) findViewById(R.id.dlg_lbl_unit_no_spray);
		final View item6 = (View) findViewById(R.id.dlg_lbl_month_spray);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final View Problems;
		final View Amount;
		final View Date;
		final View PestName;

		Problems = (View) findViewById(R.id.prob_spray_tr);
		PestName = (View) findViewById(R.id.pest_spray_tr);
		Amount = (View) findViewById(R.id.units_spray_tr);
		Date = (View) findViewById(R.id.day_spray_tr);
		
		Problems.setOnLongClickListener(this);
		PestName.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in problem spray dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getProblems();
				displayDialog(v, m_entries, "prob_sel_spray", "Choose the problem for spraying", R.raw.problems, R.id.dlg_lbl_prob_spray, R.id.prob_spray_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in pest spray dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getPesticide();
				displayDialog(v, m_entries, "pest_sel_spray", "Choose the pesticide", R.raw.problems, R.id.dlg_lbl_pest_spray, R.id.pest_spray_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units fert dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getUnits(RealFarmDatabase.ACTION_NAME_SPRAY_ID);
				displayDialog(v, m_entries, "unit_sel_spray", "Choose the unit", R.raw.problems, R.id.dlg_lbl_units_spray, R.id.units_spray_tr, 1);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				displayDialogNP("Choose the day", "day_spray_int", R.raw.dateinfo, 1, 31, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 0, R.id.dlg_lbl_day_spray, R.id.day_spray_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				displayDialogNP("Choose the quantity", "spray_no", R.raw.dateinfo, 1, 20, 1, 1, 0, R.id.dlg_lbl_unit_no_spray, R.id.units_spray_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");

				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_spray", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_spray, R.id.day_spray_tr, 0);
			}

		});

		Button btnNext = (Button) findViewById(R.id.spray_ok);
		Button cancel = (Button) findViewById(R.id.spray_cancel);

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
				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok");
				
				unit_sel_spray = resultsMap.get("unit_sel_spray");
				pest_sel_spray = resultsMap.get("pest_sel_spray");
				prob_sel_spray = resultsMap.get("prob_sel_spray");
				months_spray = resultsMap.get("months_spray");
				day_spray_int = Integer.parseInt(resultsMap.get("day_spray_int"));
				spray_no = Integer.parseInt(resultsMap.get("spray_no"));
				
				int flag1, flag2, flag3, flag4;
				if (unit_sel_spray.toString().equalsIgnoreCase("0")
						|| spray_no == 0) {
					flag1 = 1;

					View tr_feedback = (View) findViewById(R.id.units_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");

				} else {
					flag1 = 0;

					View tr_feedback = (View) findViewById(R.id.units_spray_tr);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (pest_sel_spray.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					View tr_feedback = (View) findViewById(R.id.pest_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "pesticide");
				} else {

					flag2 = 0;

					View tr_feedback = (View) findViewById(R.id.pest_spray_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (prob_sel_spray.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					View tr_feedback = (View) findViewById(R.id.prob_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "problems");
				} else {

					flag3 = 0;

					View tr_feedback = (View) findViewById(R.id.prob_spray_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_spray.toString().equalsIgnoreCase("0")
						|| day_spray_int == 0) {

					flag4 = 1;

					View tr_feedback = (View) findViewById(R.id.day_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "month");
				} else {

					flag4 = 0;

					day_sel_spray = day_spray_int + "." + months_spray;

					View tr_feedback = (View) findViewById(R.id.day_spray_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {
					System.out.println("spraying writing");
					mDataProvider.setSpraying(Global.userId, Global.plotId,
							spray_no, unit_sel_spray, day_sel_spray,
							prob_sel_spray, 1, 0, pest_sel_spray);

					//System.out.println("spraying reading");
					//mDataProvider.getspraying();
/* Crash */
					Intent adminintent = new Intent(action_spraying.this,
							Homescreen.class);

					startActivity(adminintent);
					action_spraying.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");

				Intent adminintent = new Intent(action_spraying.this,
						Homescreen.class);

				startActivity(adminintent);
				action_spraying.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.dlg_lbl_prob_spray) {
			playAudioalways(R.raw.selecttheproblem);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "problem");
		}

		if (v.getId() == R.id.dlg_lbl_pest_spray) {
			playAudioalways(R.raw.selectthepesticide);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "pest");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_spray
				|| v.getId() == R.id.dlg_lbl_units_spray) {
			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.dlg_lbl_day_spray) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day");
		}

		if (v.getId() == R.id.spray_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.spray_cancel) {
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
		
		if (v.getId() == R.id.dlg_lbl_month_spray) { // added

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.prob_spray_tr) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.day_spray_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_spray_tr) {
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.pest_spray_tr) {
			playAudioalways(R.raw.pesticidename);
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