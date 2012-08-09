package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class action_harvest extends DataFormActivity {
	private int feedback_sel;
	private int harvest_no, day_harvest_int;
	private String units_harvest = "0";
	private String feedback_txt;
	private String months_harvest = "0";
	private int crop_harvest = 0;
	private String final_day_harvest;
	private String mSelectedMonth;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.harvest_dialog);

		playAudio(R.raw.clickingharvest);

		// adds the fields that need to be validated.
		mResultsMap.put("units_harvest", "0");
		mResultsMap.put("months_harvest", "0");
		mResultsMap.put("day_harvest_int", "0");
		mResultsMap.put("harvest_no", "0");
		mResultsMap.put("feedback_sel", "0");
		mResultsMap.put("crop_harvest", "0");

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;
		final View item6;

		item1 = findViewById(R.id.dlg_lbl_unit_no_harvest);
		item2 = findViewById(R.id.dlg_lbl_units_harvest);
		item3 = findViewById(R.id.dlg_lbl_month_harvest);
		item4 = findViewById(R.id.dlg_lbl_day_harvest);
		item5 = findViewById(R.id.dlg_lbl_satisfaction_harvest);
		item6 = findViewById(R.id.dlg_lbl_harvest_crop);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

		final View harvest_date;
		final View Amount;

		harvest_date = (View) findViewById(R.id.harvest_date_tr);
		Amount = (View) findViewById(R.id.units_harvest_tr);

		harvest_date.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		System.out.println("Plant details entered4");

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of bags", "harvest_no",
						R.raw.dateinfo, 1, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_harvest, R.id.units_harvest_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units fert dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_UNIT);
				displayDialog(v, data, "units_harvest", "Select the unit",
						R.raw.problems, R.id.dlg_lbl_units_harvest,
						R.id.units_harvest_tr, 2);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, "months_harvest", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_harvest,
						R.id.harvest_date_tr, 0);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				displayDialogNP("Choose the day", "day_harvest_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_harvest, R.id.harvest_date_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_SMILEY);
				displayDialog(v, data, "feedback_sel", "Are you satisfied?",
						R.raw.feedbackgood, R.id.dlg_lbl_satisfaction_harvest,
						R.id.satisfaction_harvest_tr, 1);
			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				List<Resource> data = mDataProvider.getVarieties();
				displayDialog(v, data, "crop_harvest", "Select the variety",
						R.raw.problems, R.id.dlg_lbl_harvest_crop,
						R.id.var_harvest_crop, 0);
			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_harvest_crop) {

			playAudio(R.raw.feedbackgood);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.harvest_ok) {

			playAudio(R.raw.ok);
			showHelpIcon(v);

		}
		if (v.getId() == R.id.harvest_cancel) {

			playAudio(R.raw.cancel);
			showHelpIcon(v);

		}
		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "help");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_harvest
				|| v.getId() == R.id.dlg_lbl_units_harvest) {

			playAudio(R.raw.selecttheunits);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "units");
		}
		if (v.getId() == R.id.dlg_lbl_day_harvest) {

			playAudio(R.raw.selectthedate);
			showHelpIcon(v);

		}

		if (v.getId() == R.id.dlg_lbl_month_harvest) {

			playAudio(R.raw.choosethemonthwhenharvested);
			showHelpIcon(v);

		}

		if (v.getId() == R.id.harvest_date_tr) {
			playAudio(R.raw.harvestyear);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.units_harvest_tr) {
			playAudio(R.raw.amount);
			showHelpIcon(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		units_harvest = mResultsMap.get("units_harvest").toString();
		months_harvest = mResultsMap.get("months_harvest").toString();
		day_harvest_int = Integer.parseInt(mResultsMap.get("day_harvest_int")
				.toString());
		harvest_no = Integer.parseInt(mResultsMap.get("harvest_no").toString());
		feedback_sel = Integer.parseInt(mResultsMap.get("feedback_sel")
				.toString());
		crop_harvest = Integer.parseInt(mResultsMap.get("crop_harvest")
				.toString());

		int flag1, flag2, flag3, flag4;
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
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"ok_button");

		if (feedback_sel == 0) {
			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.satisfaction_harvest_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"feedback");
		} else {
			flag1 = 0;
			View tr_feedback = (View) findViewById(R.id.satisfaction_harvest_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);

		}
		if (units_harvest.toString().equalsIgnoreCase("0") || harvest_no == 0) {
			flag2 = 1;

			View tr_units = (View) findViewById(R.id.units_harvest_tr);
			tr_units.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"units");
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
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"date");
		} else {
			flag3 = 0;

			final_day_harvest = day_harvest_int + "." + months_harvest;

			View tr_units = (View) findViewById(R.id.harvest_date_tr);
			tr_units.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (crop_harvest == 0) {
			flag4 = 1;

			View tr_months = (View) findViewById(R.id.var_harvest_crop);

			tr_months.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"date");
		} else {
			flag4 = 0;

			View tr_units = (View) findViewById(R.id.var_harvest_crop);
			tr_units.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {
			System.out.println("harvesting writing");
			// mDataProvider.setHarvest(Global.userId, Global.plotId,
			// harvest_no, 0, units_harvest, final_day_harvest,
			// feedback_txt, 1, 0, crop_harvest);

			return true;

		}
		return false;
	}

}