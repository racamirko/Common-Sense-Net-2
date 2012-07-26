package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class action_fertilizing extends HelpEnabledActivityOld implements
		OnLongClickListener {
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** Reference to the current instance. */
	private final action_fertilizing mParentReference = this;
	private String units_fert = "0", fert_var_sel = "0", day_fert_sel = "0",
			day_fert_sel_1;
	private int fert_no, day_fert_int;
	private String months_fert = "0";
	private HashMap<String, String> resultsMap;

	public static final String LOG_TAG = "action_fertilizing";

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		startActivity(new Intent(action_fertilizing.this, Homescreen.class));
		action_fertilizing.this.finish();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(this);

		super.onCreate(savedInstanceState, R.layout.fertilizing_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");
		
		resultsMap = new HashMap<String, String>();
		resultsMap.put("units_fert", "0");
		resultsMap.put("months_fert", "0");
		resultsMap.put("fert_var_sel", "0");
		resultsMap.put("day_fert_int", "0");
		resultsMap.put("fert_no", "0");

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		playAudio(R.raw.clickingfertilising);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;
		item1 = findViewById(R.id.dlg_lbl_var_fert);
		item2 = findViewById(R.id.dlg_lbl_units_fert);
		item3 = findViewById(R.id.dlg_lbl_day_fert);
		item4 = findViewById(R.id.dlg_lbl_unit_no_fert);
		item5 = findViewById(R.id.dlg_lbl_month_fert);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);

		final View fertilizerName;
		final View amount;
		final View date;

		fertilizerName = (View) findViewById(R.id.var_fert_tr);
		amount = (View) findViewById(R.id.units_fert_tr);
		date = (View) findViewById(R.id.day_fert_tr);

		amount.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		fertilizerName.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety fert dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getFertilizers();
				displayDialog(v, m_entries, "fert_var_sel", "Choose the fertilizer", R.raw.problems, R.id.dlg_lbl_var_fert, R.id.var_fert_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units fert dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = mDataProvider.getUnits(RealFarmDatabase.ACTION_NAME_FERTILIZE_ID);
				displayDialog(v, m_entries, "units_fert", "Choose the unit", R.raw.problems, R.id.dlg_lbl_units_fert, R.id.units_fert_tr, 1);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				displayDialogNP("Choose the day", "day_fert_int", R.raw.dateinfo, 1, 31, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 0, R.id.dlg_lbl_day_fert, R.id.day_fert_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				displayDialogNP("Choose the amount", "fert_no", R.raw.dateinfo, 0, 100, 1, 0.25, 2, R.id.dlg_lbl_unit_no_fert, R.id.units_fert_tr, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo);

			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_fert", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_fert, R.id.day_fert_tr, 0);
			}

		});

		Button btnNext = (Button) findViewById(R.id.button_ok);
		Button cancel = (Button) findViewById(R.id.button_cancel);

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "cancel");
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok_btn");
				
				units_fert = resultsMap.get("units_fert");
				months_fert = resultsMap.get("months_fert");
				fert_var_sel = resultsMap.get("fert_var_sel");
				day_fert_int = Integer.parseInt(resultsMap.get("day_fert_int"));
				fert_no = (int)(Double.parseDouble(resultsMap.get("fert_no")));

				int flag1, flag2, flag3;
				if (units_fert.toString().equalsIgnoreCase("0") || fert_no == 0) {
					flag1 = 1;

					View tr_feedback = (View) findViewById(R.id.units_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units_fertilizer");

				} else {
					flag1 = 0;
					View tr_feedback = (View) findViewById(R.id.units_fert_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (fert_var_sel.toString().equalsIgnoreCase("0")) { 

					flag2 = 1;

					View tr_feedback = (View) findViewById(R.id.var_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "type_fertilizer");

				} else {

					flag2 = 0;
					View tr_feedback = (View) findViewById(R.id.var_fert_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_fert.toString().equalsIgnoreCase("0")
						|| day_fert_int == 0) {

					flag3 = 1;

					View tr_feedback = (View) findViewById(R.id.day_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "type_fertilizer");

				} else {

					flag3 = 0;
					day_fert_sel = day_fert_sel_1 + "." + months_fert;
					View tr_feedback = (View) findViewById(R.id.var_fert_tr);
					tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {

					System.out.println("fertilizing writing");
					long result = mDataProvider.setFertilizing(Global.plotId, fert_no,
							fert_var_sel, units_fert, day_fert_sel, 1, 0);
					
					//System.out.println("fertilizing reading");
					//mDataProvider.getfertizing();

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.CLICK,
							LOG_TAG, "ok_button");

					startActivity(new Intent(action_fertilizing.this,
							Homescreen.class));
					action_fertilizing.this.finish();
					okAudio();

				} else {
					initmissingval();
				}
			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_fertilizing.this,
						Homescreen.class);

				startActivity(adminintent);
				action_fertilizing.this.finish();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");
			}
		});

	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_var_fert) {
			
			Toast.makeText(action_fertilizing.this, ((TextView)(v)).getText(), Toast.LENGTH_LONG).show();

			playAudioalways(R.raw.selecttypeoffertilizer);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "type_fertilizer");

		}
		
		if (v.getId() == R.id.dlg_lbl_units_fert) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_fert) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.dlg_lbl_day_fert) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day_fertilizer");
		}

		if (v.getId() == R.id.button_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudioalways(R.raw.help);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help_fertilizer");
		}

		if (v.getId() == R.id.var_fert_tr) {
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.day_fert_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_fert_tr) {
			playAudioalways(R.raw.fertilizername);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_fert) {
			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);
		}

		return true;
	}

	protected void cancelAudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_fertilizing.this,
				Homescreen.class);

		startActivity(adminintent);
		action_fertilizing.this.finish();
	}

	protected void okAudio() {

		playAudio(R.raw.ok);

	}

	private void putBackgrounds(DialogData choice, TextView var_text, int imageType){
		if(choice.getBackgroundRes() != -1) var_text.setBackgroundResource(choice.getBackgroundRes());
		if(imageType == 1 || imageType == 2){
			BitmapDrawable bd=(BitmapDrawable) mParentReference.getResources().getDrawable(choice.getImageRes());
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
				
				Toast.makeText(mParentReference, resultsMap.get(mapEntry), Toast.LENGTH_SHORT).show();
						
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

		final Dialog dialog = new Dialog(mParentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		playAudio(openAudio); // opening audio
		
		if(!resultsMap.get(mapEntry).equals("0") && !resultsMap.get(mapEntry).equals("-1")) init = Double.valueOf(resultsMap.get(mapEntry));
		
		NumberPicker np = new NumberPicker(mParentReference, min, max, init, inc, nbDigits);
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
				Toast.makeText(mParentReference , result, Toast.LENGTH_LONG).show();
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