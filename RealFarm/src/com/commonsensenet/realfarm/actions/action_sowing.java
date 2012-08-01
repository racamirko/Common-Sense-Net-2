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
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;

public class action_sowing extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_sowing parentReference = this;
	private int sow_no;
	private int day_sow_int;
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
		resultsMap.put("day_sow_int", "0");
		resultsMap.put("sow_no", "0");

		System.out.println("plant done");
		// final TextView day_sow = (TextView)
		// findViewById(R.id.dlg_lbl_day_sow);
		// final TextView month_sow = (TextView)
		// findViewById(R.id.dlg_lbl_month_sow);

		playAudio(R.raw.thankyouclickingactionsowing);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final View item1;
		final View item3;
		final View item4;
		final View item5;
		final View item7;
		View home;
		View help;
		item1 = findViewById(R.id.dlg_var_text_sow);
		item3 = findViewById(R.id.dlg_lbl_day_sow);
		item4 = findViewById(R.id.dlg_lbl_treat_sow);
		item5 = findViewById(R.id.dlg_lbl_unit_no_sow);
		item7 = findViewById(R.id.dlg_lbl_intercrop_sow);

		final View item6 = findViewById(R.id.dlg_lbl_month_sow);
		home = findViewById(R.id.aggr_img_home);
		help = findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final View variety;
		final View Amount;
		final View Date;
		final View Treatment;
		final View Intercrop;

		variety = findViewById(R.id.seed_type_sow_tr);
		Amount = findViewById(R.id.units_sow_tr);
		Date = findViewById(R.id.day_sow_tr);
		Treatment = findViewById(R.id.treatment_sow_tr);
		Intercrop = findViewById(R.id.intercrop_sow_tr);

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
				displayDialog(v, m_entries, "seed_sow", "Select the variety",
						R.raw.problems, R.id.dlg_var_text_sow,
						R.id.seed_type_sow_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				displayDialogNP("Choose the day", "day_sow_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_sow, R.id.day_sow_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in treatment sow dialog", "in dialog");

				ArrayList<DialogData> m_entries = mDataProvider
						.getDialogData(RealFarmDatabase.DIALOG_TREATMENT_ID);
				displayDialog(v, m_entries, "treatment_sow",
						"Select if the seeds were treated", R.raw.bagof50kg,
						R.id.dlg_lbl_treat_sow, R.id.treatment_sow_tr, 0);

			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of serus", "sow_no",
						R.raw.dateinfo, 1, 999, 1, 1, 0,
						R.id.dlg_lbl_unit_no_sow, R.id.units_sow_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in intercrop sow dialog", "in dialog");

				ArrayList<DialogData> m_entries = mDataProvider
						.getDialogData(RealFarmDatabase.DIALOG_INTERCRIOP_ID);
				displayDialog(v, m_entries, "cropType_sow",
						"Main crop or intercrop?", R.raw.bagof50kg,
						R.id.dlg_lbl_intercrop_sow, R.id.intercrop_sow_tr, 0);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in treat sow dialog", "in dialog");

				stopaudio();

				ArrayList<DialogData> m_entries = mDataProvider
						.getDialogData(RealFarmDatabase.DIALOG_MONTH_ID);
				displayDialog(v, m_entries, "months_sow", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_sow,
						R.id.day_sow_tr, 0);

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
				day_sow_int = Integer.parseInt(resultsMap.get("day_sow_int"));
				sow_no = Integer.parseInt(resultsMap.get("sow_no"));

				int flag1, flag2, flag3, flag4, flag5;
				if (seed_sow == 0) {
					flag1 = 1;

					View tr_feedback = findViewById(R.id.seed_type_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "variety");

				} else {
					flag1 = 0;

					View tr_feedback = findViewById(R.id.seed_type_sow_tr);
					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (sow_no == 0) {

					flag2 = 1;

					View tr_feedback = findViewById(R.id.units_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");

				} else {

					flag2 = 0;

					View tr_feedback = findViewById(R.id.units_sow_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (treatment_sow.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					View tr_feedback = findViewById(R.id.treatment_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "treatment");

				} else {

					flag3 = 0;

					View tr_feedback = findViewById(R.id.treatment_sow_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_sow.toString().equalsIgnoreCase("0")
						|| day_sow_int == 0) {

					flag4 = 1;

					View tr_feedback = findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "day");

				} else {

					flag4 = 0;

					days_sel_sow = day_sow_int + "." + months_sow;
					View tr_feedback = findViewById(R.id.day_sow_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (cropType_sow.toString().equalsIgnoreCase("0")) {

					flag5 = 1;

					View tr_feedback = findViewById(R.id.intercrop_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "intercrop");

				} else {

					flag5 = 0;

					View tr_feedback = findViewById(R.id.intercrop_sow_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0
						&& flag5 == 0) {
					System.out.println("sowing writing");
					// mDataProvider.setSowing(Global.plotId, sow_no, seed_sow,
					// units_sow, days_sel_sow, treatment_sow, 0, 0,
					// cropType_sow);

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

		if (v.getId() == R.id.dlg_var_text_sow) {

			playAudioalways(R.raw.varietyofseedssowd);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "variety");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_sow) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.dlg_lbl_month_sow) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day");
		}

		if (v.getId() == R.id.dlg_lbl_treat_sow) {

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

		if (v.getId() == R.id.intercrop_sow_tr
				|| v.getId() == R.id.dlg_lbl_intercrop_sow) { // 20-06-2012 +
																// added
			playAudioalways(R.raw.intercrop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_sow) { // added

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.day_sow_tr) { // added

			playAudioalways(R.raw.date);
			ShowHelpIcon(v); // added for help icon
		}

		return true;
	}

	private void putBackgrounds(DialogData choice, TextView var_text,
			int imageType) {
		if (choice.getBackgroundRes() != -1)
			var_text.setBackgroundResource(choice.getBackgroundRes());
		if (imageType == 1 || imageType == 2) {
			BitmapDrawable bd = (BitmapDrawable) parentReference.getResources()
					.getDrawable(choice.getImageRes());
			int width = bd.getBitmap().getWidth();
			if (width > 80)
				width = 80;

			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(10, 0, 80 - width - 20, 0);
			var_text.setLayoutParams(llp);

			var_text.setBackgroundResource(choice.getImageRes());
			if (imageType == 1)
				var_text.setTextColor(Color.TRANSPARENT);
			else {
				var_text.setGravity(Gravity.TOP);
				var_text.setPadding(0, 0, 0, 0);
				var_text.setTextSize(20);
				var_text.setTextColor(Color.BLACK);
			}
		}
	}

	private void displayDialog(View v, final ArrayList<DialogData> m_entries,
			final String mapEntry, final String title, int entryAudio,
			final int varText, final int trFeedback, final int imageType) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView) dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener() { // TODO: adapt
			// the audio
			// in the db
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				TextView var_text = (TextView) findViewById(varText);
				DialogData choice = m_entries.get(position);
				var_text.setText(choice.getShortName());
				resultsMap.put(mapEntry, choice.getValue());
				View tr_feedback = findViewById(trFeedback);
				tr_feedback
						.setBackgroundResource(android.R.drawable.list_selector_background);

				// put backgrounds (specific to the application) TODO: optimize
				// the resize
				putBackgrounds(choice, var_text, imageType);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, title, choice.getValue());

				Toast.makeText(parentReference, resultsMap.get(mapEntry),
						Toast.LENGTH_SHORT).show();

				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + choice.getAudio(), null, null);
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + m_entries.get(position).getAudio(), null, null);
				playAudioalways(iden);
				return true;
			}
		});
	}

	private void displayDialogNP(String title, final String mapEntry,
			int openAudio, double min, double max, double init, double inc,
			int nbDigits, int textField, int tableRow, final int okAudio,
			final int cancelAudio, final int infoOkAudio,
			final int infoCancelAudio) {

		final Dialog dialog = new Dialog(parentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		playAudio(openAudio); // opening audio

		if (!resultsMap.get(mapEntry).equals("0")
				&& !resultsMap.get(mapEntry).equals("-1"))
			init = Double.valueOf(resultsMap.get(mapEntry));

		NumberPicker np = new NumberPicker(parentReference, min, max, init,
				inc, nbDigits);
		dialog.setContentView(np);

		final TextView tw_sow = (TextView) findViewById(textField);
		final View tr_feedback = findViewById(tableRow);

		final TextView tw = (TextView) dialog.findViewById(R.id.tw);
		ImageButton ok = (ImageButton) dialog.findViewById(R.id.ok);
		ImageButton cancel = (ImageButton) dialog.findViewById(R.id.cancel);
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String result = tw.getText().toString();
				resultsMap.put(mapEntry, result);
				tw_sow.setText(result);
				tr_feedback
						.setBackgroundResource(android.R.drawable.list_selector_background);
				Toast.makeText(parentReference, result, Toast.LENGTH_LONG)
						.show();
				dialog.cancel();
				playAudio(okAudio); // ok audio
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.cancel();
				playAudio(cancelAudio); // cancel audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "amount", "cancel");
			}
		});
		ok.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoOkAudio); // info audio
				return true;
			}
		});
		cancel.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoCancelAudio); // info audio
				return true;
			}
		});
		tw.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				// String num = tw.getText().toString();
				playAudio(R.raw.dateinfo); // info audio
				return false;
			}
		});

		dialog.show();
	}

}