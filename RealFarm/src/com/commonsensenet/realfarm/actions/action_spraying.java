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

public class action_spraying extends DataFormActivity {

	private String day_sel_spray = "0";
	private int day_spray_int;
	private String months_spray = "0";
	private String pest_sel_spray = "0";
	private String prob_sel_spray = "0";
	private int spray_no;
	private String unit_sel_spray = "0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.spraying_dialog);

		playAudio(R.raw.clickingspraying);

		// adds the name of the fields to validate.
		mResultsMap.put("unit_sel_spray", "0");
		mResultsMap.put("pest_sel_spray", "0");
		mResultsMap.put("prob_sel_spray", "0");
		mResultsMap.put("months_spray", "0");
		mResultsMap.put("day_spray_int", "0");
		mResultsMap.put("spray_no", "0");

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;

		item1 = (View) findViewById(R.id.dlg_lbl_prob_spray);
		item2 = (View) findViewById(R.id.dlg_lbl_pest_spray);
		item3 = (View) findViewById(R.id.dlg_lbl_units_spray);
		item4 = (View) findViewById(R.id.dlg_lbl_day_spray);
		item5 = (View) findViewById(R.id.dlg_lbl_unit_no_spray);
		final View item6 = (View) findViewById(R.id.dlg_lbl_month_spray);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

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
				stopAudio();
				Log.d("in problem spray dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PROBLEM);
				displayDialog(v, data, "prob_sel_spray",
						"Choose the problem for spraying", R.raw.problems,
						R.id.dlg_lbl_prob_spray, R.id.prob_spray_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in pest spray dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PESTICIDE);
				displayDialog(v, data, "pest_sel_spray",
						"Choose the pesticide", R.raw.problems,
						R.id.dlg_lbl_pest_spray, R.id.pest_spray_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units fert dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_SPRAY_ID);
				displayDialog(v, data, "unit_sel_spray", "Choose the unit",
						R.raw.problems, R.id.dlg_lbl_units_spray,
						R.id.units_spray_tr, 1);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the day", "day_spray_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_spray, R.id.day_spray_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the quantity", "spray_no",
						R.raw.dateinfo, 1, 20, 1, 1, 0,
						R.id.dlg_lbl_unit_no_spray, R.id.units_spray_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, "months_spray", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_spray,
						R.id.day_spray_tr, 0);
			}

		});

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_prob_spray) {
			playAudio(R.raw.selecttheproblem, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "problem");
		}

		if (v.getId() == R.id.dlg_lbl_pest_spray) {
			playAudio(R.raw.selectthepesticide, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "pest");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_spray
				|| v.getId() == R.id.dlg_lbl_units_spray) {
			playAudio(R.raw.selecttheunits, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "units");
		}

		if (v.getId() == R.id.dlg_lbl_day_spray) {
			playAudio(R.raw.selectthedate, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "day");
		}

		if (v.getId() == R.id.spray_ok) {
			playAudio(R.raw.ok, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.spray_cancel) {
			playAudio(R.raw.cancel, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					getLogTag(), "help");
		}

		if (v.getId() == R.id.dlg_lbl_month_spray) {
			playAudio(R.raw.choosethemonth, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.prob_spray_tr) {
			playAudio(R.raw.problems, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.day_spray_tr) {
			playAudio(R.raw.date, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.units_spray_tr) {
			playAudio(R.raw.amount, true);
			showHelpIcon(v);
		}
		if (v.getId() == R.id.pest_spray_tr) {
			playAudio(R.raw.pesticidename, true);
			showHelpIcon(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"ok");

		unit_sel_spray = mResultsMap.get("unit_sel_spray").toString();
		pest_sel_spray = mResultsMap.get("pest_sel_spray").toString();
		prob_sel_spray = mResultsMap.get("prob_sel_spray").toString();
		months_spray = mResultsMap.get("months_spray").toString();
		day_spray_int = Integer.parseInt(mResultsMap.get("day_spray_int")
				.toString());
		spray_no = Integer.parseInt(mResultsMap.get("spray_no").toString());

		int flag1, flag2, flag3, flag4;
		if (unit_sel_spray.toString().equalsIgnoreCase("0") || spray_no == 0) {
			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.units_spray_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"units");

		} else {
			flag1 = 0;

			View tr_feedback = (View) findViewById(R.id.units_spray_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (pest_sel_spray.toString().equalsIgnoreCase("0")) {

			flag2 = 1;

			View tr_feedback = (View) findViewById(R.id.pest_spray_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"pesticide");
		} else {

			flag2 = 0;

			View tr_feedback = (View) findViewById(R.id.pest_spray_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (prob_sel_spray.toString().equalsIgnoreCase("0")) {

			flag3 = 1;

			View tr_feedback = (View) findViewById(R.id.prob_spray_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"problems");
		} else {

			flag3 = 0;

			View tr_feedback = (View) findViewById(R.id.prob_spray_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_spray.toString().equalsIgnoreCase("0") || day_spray_int == 0) {

			flag4 = 1;

			View tr_feedback = (View) findViewById(R.id.day_spray_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, getLogTag(),
					"month");
		} else {

			flag4 = 0;

			day_sel_spray = day_spray_int + "." + months_spray;

			View tr_feedback = (View) findViewById(R.id.day_spray_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {
			System.out.println("spraying writing");
			// mDataProvider.setSpraying(Global.userId, Global.plotId,
			// spray_no, unit_sel_spray, day_sel_spray,
			// prob_sel_spray, 1, 0, pest_sel_spray);

			// System.out.println("spraying reading");
			// mDataProvider.getspraying();
			/* Crash */

			return true;
		}

		return false;
	}
}