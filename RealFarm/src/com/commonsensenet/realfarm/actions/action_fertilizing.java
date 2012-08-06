package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase.ResourceType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class action_fertilizing extends DataFormActivity implements
		OnLongClickListener {

	private String units_fert = "0";
	private String fert_var_sel = "0";
	private String day_fert_sel = "0";
	private String day_fert_sel_1;
	private int fert_no, day_fert_int;
	private String months_fert = "0";

	public static final String LOG_TAG = "action_fertilizing";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.fertilizing_dialog, LOG_TAG);

		// adds the values that need to be validated.
		mResultsMap.put("units_fert", "0");
		mResultsMap.put("months_fert", "0");
		mResultsMap.put("fert_var_sel", "0");
		mResultsMap.put("day_fert_int", "0");
		mResultsMap.put("fert_no", "0");

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

				List<Resource> data = mDataProvider
						.getResources(ResourceType.FERTILIZER);
				displayDialog(v, data, "fert_var_sel", "Choose the fertilizer",
						R.raw.problems, R.id.dlg_lbl_var_fert,
						R.id.var_fert_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units fert dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID);
				displayDialog(v, data, "units_fert", "Choose the unit",
						R.raw.problems, R.id.dlg_lbl_units_fert,
						R.id.units_fert_tr, 1);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the day", "day_fert_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_fert, R.id.day_fert_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the amount", "fert_no", R.raw.dateinfo,
						0, 100, 1, 0.25, 2, R.id.dlg_lbl_unit_no_fert,
						R.id.units_fert_tr, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo, R.raw.dateinfo);

			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(ResourceType.MONTH);
				displayDialog(v, data, "months_fert", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_fert,
						R.id.day_fert_tr, 0);
			}

		});

	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_var_fert) {

			Toast.makeText(action_fertilizing.this, ((TextView) (v)).getText(),
					Toast.LENGTH_LONG).show();

			playAudio(R.raw.selecttypeoffertilizer);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "type_fertilizer");

		}

		if (v.getId() == R.id.dlg_lbl_units_fert) {

			playAudio(R.raw.selecttheunits);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_fert) {

			playAudio(R.raw.selecttheunits);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.dlg_lbl_day_fert) {

			playAudio(R.raw.selectthedate);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day_fertilizer");
		}

		if (v.getId() == R.id.button_ok) {

			playAudio(R.raw.ok);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.button_cancel) {

			playAudio(R.raw.cancel);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help_fertilizer");
		}

		if (v.getId() == R.id.var_fert_tr) {
			playAudio(R.raw.amount);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.day_fert_tr) {
			playAudio(R.raw.date);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.units_fert_tr) {
			playAudio(R.raw.fertilizername);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_fert) {
			playAudio(R.raw.choosethemonth);
			showHelpIcon(v);
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

	@Override
	protected Boolean validateForm() {
		units_fert = mResultsMap.get("units_fert").toString();

		months_fert = mResultsMap.get("months_fert").toString();
		fert_var_sel = mResultsMap.get("fert_var_sel").toString();
		day_fert_int = Integer.parseInt(mResultsMap.get("day_fert_int")
				.toString());
		fert_no = (int) (Double.parseDouble(mResultsMap.get("fert_no")
				.toString()));

		int flag1, flag2, flag3;
		if (units_fert.toString().equalsIgnoreCase("0") || fert_no == 0) {
			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.units_fert_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"units_fertilizer");

		} else {
			flag1 = 0;
			View tr_feedback = (View) findViewById(R.id.units_fert_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (fert_var_sel.toString().equalsIgnoreCase("0")) {

			flag2 = 1;

			View tr_feedback = (View) findViewById(R.id.var_fert_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"type_fertilizer");

		} else {

			flag2 = 0;
			View tr_feedback = (View) findViewById(R.id.var_fert_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_fert.toString().equalsIgnoreCase("0") || day_fert_int == 0) {

			flag3 = 1;

			View tr_feedback = (View) findViewById(R.id.day_fert_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"type_fertilizer");

		} else {

			flag3 = 0;
			day_fert_sel = day_fert_sel_1 + "." + months_fert;
			View tr_feedback = (View) findViewById(R.id.var_fert_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
			//
			// System.out.println("fertilizing writing");
			// mDataProvider.setFertilizing(Global.plotId, fert_no,
			// fert_var_sel, units_fert, day_fert_sel, 1, 0);

			// System.out.println("fertilizing reading");
			// mDataProvider.getfertizing();

			return true;

		}

		return false;
	}

}