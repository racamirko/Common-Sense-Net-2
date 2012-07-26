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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	private String units_harvest = "0", feedback_txt, months_harvest = "0";
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

		final View smiley1;
		final View smiley2;
		final View smiley3;

		smiley1 = findViewById(R.id.home_btn_har_1);
		smiley2 = findViewById(R.id.home_btn_har_2);
		smiley3 = findViewById(R.id.home_btn_har_3);
		smiley1.setBackgroundResource(R.drawable.smiley_good_not);
		smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

		playAudio(R.raw.clickingharvest);
		
		resultsMap = new HashMap<String, String>(); 
		resultsMap.put("units_harvest", "0");
		resultsMap.put("months_harvest", "0");
		resultsMap.put("day_harvest_int", "0");
		resultsMap.put("harvest_no", "0");

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		System.out.println("Plant details entered1");
		final View item1;
		final View item2;
		final View item3;
		final View item4;
		ImageButton home;
		ImageButton help;
		System.out.println("Plant details entered2");
		item1 = findViewById(R.id.dlg_lbl_unit_no_harvest);
		item2 = findViewById(R.id.dlg_lbl_units_harvest);
		item3 = findViewById(R.id.dlg_lbl_month_harvest);
		item4 = findViewById(R.id.dlg_lbl_day_harvest);

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

		final View harvest_date;
		final View Amount;

		harvest_date = (View) findViewById(R.id.harvest_date_tr);
		Amount = (View) findViewById(R.id.units_harvest_tr);

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

				View tr_feedback = (View) findViewById(R.id.tableRow_feedback);
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

				View tr_feedback = (View) findViewById(R.id.tableRow_feedback);
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
				View tr_feedback = (View) findViewById(R.id.tableRow_feedback);

				tr_feedback.setBackgroundResource(R.drawable.def_img);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "feedback", "bad");
			}
		});

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
			
				displayDialogNP("Choose the number of bags", "harvest_no", R.raw.dateinfo, 1, 200, 0, 1, 0, R.id.dlg_lbl_unit_no_harvest, R.id.units_harvest_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units fert dialog", "in dialog");
				
				final ArrayList<DialogData> m_entries = DialogArrayLists.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_harvest", "Select the unit", R.raw.problems, R.id.dlg_lbl_units_harvest, R.id.units_harvest_tr, 2);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_harvest", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_harvest, R.id.harvest_date_tr, 0);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				displayDialogNP("Choose the day", "day_harvest_int", R.raw.dateinfo, 1, 31, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 0, R.id.dlg_lbl_day_harvest, R.id.harvest_date_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);
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
				day_harvest_int = Integer.parseInt(resultsMap.get("day_harvest_int"));
				harvest_no = Integer.parseInt(resultsMap.get("harvest_no"));

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

					View tr_feedback = (View) findViewById(R.id.tableRow_feedback);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "feedback");
				} else {
					flag1 = 0;
					View tr_feedback = (View) findViewById(R.id.tableRow_feedback);

					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);

				}
				if (units_harvest.toString().equalsIgnoreCase("0") 
						|| harvest_no == 0) {
					flag2 = 1;

					View tr_units = (View) findViewById(R.id.units_harvest_tr);
					tr_units.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");
				} else {
					flag2 = 0;
					View tr_units = (View) findViewById(R.id.units_harvest_tr);
					tr_units.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_harvest.toString().equalsIgnoreCase("0")
						|| day_harvest_int == 0) {
					flag3 = 1;

					View tr_months = (View) findViewById(R.id.harvest_date_tr);

					tr_months.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "date");
				} else {
					flag3 = 0;

					final_day_harvest = day_harvest_int + "." + months_harvest;

					View tr_units = (View) findViewById(R.id.harvest_date_tr);
					tr_units.setBackgroundResource(android.R.drawable.list_selector_background);
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
		
		if (v.getId() == R.id.dlg_lbl_unit_no_harvest
				|| v.getId() == R.id.dlg_lbl_units_harvest) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}
		if (v.getId() == R.id.dlg_lbl_day_harvest) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.dlg_lbl_month_harvest) {

			playAudioalways(R.raw.choosethemonthwhenharvested);
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