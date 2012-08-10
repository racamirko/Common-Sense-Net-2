package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;

public class action_harvest extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String SATISFACTION = "satisfaction";
	public static final String UNIT = "unit";
	public static final String VARIETY = "variety";

	private int mAmount;
	private int mDay;
	private int mMonth;
	private int mSatisfaction;
	private int mUnit;
	private int mVariety;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.harvest_dialog);

		playAudio(R.raw.clickingharvest);

		// adds the fields that need to be validated.
		mResultsMap.put(VARIETY, -1);
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);
		mResultsMap.put(AMOUNT, "0");
		mResultsMap.put(UNIT, -1);
		mResultsMap.put(SATISFACTION, -1);

		View item1 = findViewById(R.id.dlg_lbl_harvest_crop);
		View item2 = findViewById(R.id.dlg_lbl_day_harvest);
		View item3 = findViewById(R.id.dlg_lbl_month_harvest);
		View item4 = findViewById(R.id.dlg_lbl_unit_no_harvest);
		View item5 = findViewById(R.id.dlg_lbl_units_harvest);
		View item6 = findViewById(R.id.dlg_lbl_satisfaction_harvest);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

		View harvest_date = findViewById(R.id.harvest_date_tr);
		View Amount = findViewById(R.id.units_harvest_tr);

		harvest_date.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider.getVarieties();
				displayDialog(v, data, VARIETY, "Select the variety",
						R.raw.problems, R.id.dlg_lbl_harvest_crop,
						R.id.var_harvest_crop, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_harvest, R.id.harvest_date_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_harvest,
						R.id.harvest_date_tr, 0);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the number of bags", AMOUNT,
						R.raw.dateinfo, 1, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_harvest, R.id.units_harvest_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_HARVEST_ID);
				displayDialog(v, data, UNIT, "Select the unit", R.raw.problems,
						R.id.dlg_lbl_units_harvest, R.id.units_harvest_tr, 2);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_SATISFACTION);
				displayDialog(v, data, SATISFACTION, "Are you satisfied?",
						R.raw.feedbackgood, R.id.dlg_lbl_satisfaction_harvest,
						R.id.satisfaction_harvest_tr, 1);
			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		// all long click sounds override the sound enabled flag.

		if (v.getId() == R.id.dlg_lbl_harvest_crop) {
			playAudio(R.raw.feedbackgood, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_harvest
				|| v.getId() == R.id.dlg_lbl_units_harvest) {
			playAudio(R.raw.selecttheunits, true);
		} else if (v.getId() == R.id.dlg_lbl_day_harvest) {
			playAudio(R.raw.selectthedate, true);
		} else if (v.getId() == R.id.dlg_lbl_month_harvest) {
			playAudio(R.raw.choosethemonthwhenharvested, true);
		} else if (v.getId() == R.id.harvest_date_tr) {
			playAudio(R.raw.harvestyear, true);
		} else if (v.getId() == R.id.units_harvest_tr) {
			playAudio(R.raw.amount, true);
		} else {
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		mVariety = (Integer) mResultsMap.get(VARIETY);
		mMonth = (Integer) mResultsMap.get(MONTH);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		mUnit = (Integer) mResultsMap.get(UNIT);
		mSatisfaction = (Integer) mResultsMap.get(SATISFACTION);

		boolean isValid = true;

		if (mVariety != -1) {
			highlightField(R.id.var_harvest_crop, false);
		} else {
			isValid = false;
			highlightField(R.id.var_harvest_crop, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.harvest_date_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.harvest_date_tr, true);
		}

		if (mUnit != -1 && mAmount > 0) {
			highlightField(R.id.units_harvest_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_harvest_tr, true);
		}

		if (mSatisfaction != -1) {
			highlightField(R.id.satisfaction_harvest_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.satisfaction_harvest_tr, true);
		}

		// if all fields are valid the data is inserted in the database.
		if (isValid) {

			long result = mDataProvider.addHarvestAction(Global.userId,
					Global.plotId, mVariety, mAmount, mUnit, mSatisfaction,
					getDate(mDay, mMonth), 0);

			return result != -1;
		}
		return false;
	}
}