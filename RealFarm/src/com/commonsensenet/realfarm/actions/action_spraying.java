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

public class action_spraying extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String PESTICIDE = "pesticide";
	public static final String PROBLEM = "problem";
	public static final String UNIT = "unit";

	private int mAmount;
	private int mDay;
	private int mMonth;
	private int mPesticide;
	private int mProblem;
	private int mUnit;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.spraying_dialog);

		playAudio(R.raw.clickingspraying);

		// adds the name of the fields to validate.
		mResultsMap.put(PROBLEM, -1);
		mResultsMap.put(PESTICIDE, -1);
		mResultsMap.put(AMOUNT, "0");
		mResultsMap.put(UNIT, -1);
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);

		View item1 = findViewById(R.id.dlg_lbl_prob_spray);
		View item2 = findViewById(R.id.dlg_lbl_pest_spray);
		View item3 = findViewById(R.id.dlg_lbl_unit_no_spray);
		View item4 = findViewById(R.id.dlg_lbl_units_spray);
		View item5 = findViewById(R.id.dlg_lbl_day_spray);
		View item6 = findViewById(R.id.dlg_lbl_month_spray);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

		View problemRow = findViewById(R.id.prob_spray_tr);
		View pesticideRow = findViewById(R.id.pest_spray_tr);
		View amountRow = findViewById(R.id.units_spray_tr);
		View dateRow = findViewById(R.id.day_spray_tr);

		problemRow.setOnLongClickListener(this);
		pesticideRow.setOnLongClickListener(this);
		amountRow.setOnLongClickListener(this);
		dateRow.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PROBLEM);
				displayDialog(v, data, PROBLEM,
						"Choose the problem for spraying", R.raw.problems,
						R.id.dlg_lbl_prob_spray, R.id.prob_spray_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PESTICIDE);
				displayDialog(v, data, PESTICIDE, "Choose the pesticide",
						R.raw.problems, R.id.dlg_lbl_pest_spray,
						R.id.pest_spray_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the quantity", AMOUNT, R.raw.dateinfo,
						1, 20, 1, 1, 0, R.id.dlg_lbl_unit_no_spray,
						R.id.units_spray_tr, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo, R.raw.dateinfo);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_SPRAY_ID);
				displayDialog(v, data, UNIT, "Choose the unit", R.raw.problems,
						R.id.dlg_lbl_units_spray, R.id.units_spray_tr, 1);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_spray, R.id.day_spray_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_spray,
						R.id.day_spray_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// forces all long click sounds.

		if (v.getId() == R.id.dlg_lbl_prob_spray) {
			playAudio(R.raw.selecttheproblem, true);
		} else if (v.getId() == R.id.dlg_lbl_pest_spray) {
			playAudio(R.raw.selectthepesticide, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_spray
				|| v.getId() == R.id.dlg_lbl_units_spray) {
			playAudio(R.raw.selecttheunits, true);
		} else if (v.getId() == R.id.dlg_lbl_day_spray) {
			playAudio(R.raw.selectthedate, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
		} else if (v.getId() == R.id.dlg_lbl_month_spray) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.prob_spray_tr) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.day_spray_tr) {
			playAudio(R.raw.date, true);
		} else if (v.getId() == R.id.units_spray_tr) {
			playAudio(R.raw.amount, true);
		} else if (v.getId() == R.id.pest_spray_tr) {
			playAudio(R.raw.pesticidename, true);
		} else {
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets all the values to validate.
		mProblem = (Integer) mResultsMap.get(PROBLEM);
		mPesticide = (Integer) mResultsMap.get(PESTICIDE);
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		mUnit = (Integer) mResultsMap.get(UNIT);
		mMonth = (Integer) mResultsMap.get(MONTH);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());

		// flag used to determine the validity of the form.
		boolean isValid = true;

		if (mUnit != -1 && mAmount > 0) {
			highlightField(R.id.units_spray_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_spray_tr, true);
		}

		if (mPesticide != -1) {
			highlightField(R.id.pest_spray_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.pest_spray_tr, true);
		}

		if (mProblem != -1) {
			highlightField(R.id.prob_spray_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.prob_spray_tr, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.day_spray_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.day_spray_tr, true);
		}

		// inserts the action if the form is valid.
		if (isValid) {

			long result = mDataProvider.addSprayAction(Global.userId,
					Global.plotId, mProblem, mPesticide, mAmount, mUnit,
					getDate(mDay, mMonth), 0);

			return result != -1;
		}

		return false;
	}
}