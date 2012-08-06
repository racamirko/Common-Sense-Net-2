package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase.ResourceType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class action_sowing extends DataFormActivity {
	public static final String LOG_TAG = "action_sowing";

	private String cropType_sow = "0";
	private int day_sow_int;
	private String days_sel_sow = "0";
	private String months_sow = "0";
	private int seed_sow = 0;
	private int sow_no;
	private String treatment_sow = "0";
	private String units_sow = "10";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.sowing_dialog, LOG_TAG);

		// adds the fields to the map
		mResultsMap.put("months_sow", "0");
		mResultsMap.put("treatment_sow", "0");
		mResultsMap.put("cropType_sow", "0");
		mResultsMap.put("seed_sow", "0");
		mResultsMap.put("day_sow_int", "0");
		mResultsMap.put("sow_no", "0");

		System.out.println("plant done");

		playAudio(R.raw.thankyouclickingactionsowing);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final View item1 = findViewById(R.id.dlg_var_text_sow);
		final View item3 = findViewById(R.id.dlg_lbl_day_sow);
		final View item4 = findViewById(R.id.dlg_lbl_treat_sow);
		final View item5 = findViewById(R.id.dlg_lbl_unit_no_sow);
		final View item6 = findViewById(R.id.dlg_lbl_month_sow);
		final View item7 = findViewById(R.id.dlg_lbl_intercrop_sow);

		item1.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);

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
				stopAudio();
				Log.d("in variety sow dialog", "in dialog");

				List<Resource> m_entries = mDataProvider.getVarieties();
				displayDialog(v, m_entries, "seed_sow", "Select the variety",
						R.raw.problems, R.id.dlg_var_text_sow,
						R.id.seed_type_sow_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
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
				stopAudio();
				Log.d("in treatment sow dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(ResourceType.TREATMENT);
				displayDialog(v, data, "treatment_sow",
						"Select if the seeds were treated", R.raw.bagof50kg,
						R.id.dlg_lbl_treat_sow, R.id.treatment_sow_tr, 0);

			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
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
				stopAudio();
				Log.d("in intercrop sow dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(ResourceType.INTERCROP);
				displayDialog(v, data, "cropType_sow",
						"Main crop or intercrop?", R.raw.bagof50kg,
						R.id.dlg_lbl_intercrop_sow, R.id.intercrop_sow_tr, 0);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in treat sow dialog", "in dialog");

				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(ResourceType.MONTH);
				displayDialog(v, data, "months_sow", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_sow,
						R.id.day_sow_tr, 0);

			}

		});
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_var_text_sow) {

			playAudio(R.raw.varietyofseedssowd, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "variety");
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_sow) {

			playAudio(R.raw.selecttheunits, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.dlg_lbl_month_sow) {

			playAudio(R.raw.selectthedate, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day");
		}

		if (v.getId() == R.id.dlg_lbl_treat_sow) {

			playAudio(R.raw.treatmenttoseeds1, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "treatment");
		}

		if (v.getId() == R.id.sow_ok) {
			playAudio(R.raw.ok, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.sow_cancel) {
			playAudio(R.raw.cancel, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
			showHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help");
		}

		if (v.getId() == R.id.seed_type_sow_tr) {
			playAudio(R.raw.variety, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.units_sow_tr) {
			playAudio(R.raw.amount, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.treatment_sow_tr) {
			playAudio(R.raw.treatment, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.intercrop_sow_tr
				|| v.getId() == R.id.dlg_lbl_intercrop_sow) {
			playAudio(R.raw.intercrop, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_sow) {

			playAudio(R.raw.choosethemonth, true);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.day_sow_tr) {

			playAudio(R.raw.date, true);
			showHelpIcon(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"ok");

		months_sow = mResultsMap.get("months_sow").toString();
		treatment_sow = mResultsMap.get("treatment_sow").toString();
		cropType_sow = mResultsMap.get("cropType_sow").toString();
		seed_sow = Integer.parseInt(mResultsMap.get("seed_sow").toString());
		day_sow_int = Integer.parseInt(mResultsMap.get("day_sow_int")
				.toString());
		sow_no = Integer.parseInt(mResultsMap.get("sow_no").toString());

		int flag1, flag2, flag3, flag4, flag5;
		if (seed_sow == 0) {
			flag1 = 1;

			View tr_feedback = findViewById(R.id.seed_type_sow_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"variety");

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
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"units");

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
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"treatment");

		} else {

			flag3 = 0;

			View tr_feedback = findViewById(R.id.treatment_sow_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_sow.toString().equalsIgnoreCase("0") || day_sow_int == 0) {

			flag4 = 1;

			View tr_feedback = findViewById(R.id.day_sow_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"day");

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
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, LOG_TAG,
					"intercrop");

		} else {

			flag5 = 0;

			View tr_feedback = findViewById(R.id.intercrop_sow_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {

			// mDataProvider.setSowing(Global.plotId, sow_no, seed_sow,
			// units_sow, days_sel_sow, treatment_sow, 0, 0,
			// cropType_sow);

			return true;

		}

		return false;

	}

}