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

public class SowActionActivity extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String INTERCROP = "intercrop";
	public static final String MONTH = "month";
	public static final String TREATMENT = "treatment";
	public static final String VARIETY = "variety";

	private int mAmount;
	private int mDay;
	private int mIntercrop;
	private int mMonth;
	private int mSeedType;
	private int mTreatment;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_sow_action);

		// adds the fields to validate to the map.
		mResultsMap.put(VARIETY, -1);
		mResultsMap.put(AMOUNT, "0");
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);
		mResultsMap.put(TREATMENT, -1);
		mResultsMap.put(INTERCROP, -1);

		playAudio(R.raw.thankyouclickingactionsowing);

		View item1 = findViewById(R.id.dlg_var_text_sow);
		View item2 = findViewById(R.id.dlg_lbl_unit_no_sow);
		View item3 = findViewById(R.id.dlg_lbl_day_sow);
		View item4 = findViewById(R.id.dlg_lbl_month_sow);
		View item5 = findViewById(R.id.dlg_lbl_treat_sow);
		View item6 = findViewById(R.id.dlg_lbl_intercrop_sow);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

		View varietyRow = findViewById(R.id.seed_type_sow_tr);
		View amountRow = findViewById(R.id.units_sow_tr);
		View dateRow = findViewById(R.id.day_sow_tr);
		View treatmentRow = findViewById(R.id.treatment_sow_tr);
		View intercropRow = findViewById(R.id.intercrop_sow_tr);

		varietyRow.setOnLongClickListener(this);
		amountRow.setOnLongClickListener(this);
		dateRow.setOnLongClickListener(this);
		treatmentRow.setOnLongClickListener(this);
		intercropRow.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> m_entries = mDataProvider.getVarieties();
				displayDialog(v, m_entries, VARIETY, "Select the variety",
						R.raw.problems, R.id.dlg_var_text_sow,
						R.id.seed_type_sow_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_sow, R.id.day_sow_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_TREATMENT);
				displayDialog(v, data, TREATMENT,
						"Select if the seeds were treated", R.raw.bagof50kg,
						R.id.dlg_lbl_treat_sow, R.id.treatment_sow_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the number of serus", AMOUNT,
						R.raw.dateinfo, 1, 999, 1, 1, 0,
						R.id.dlg_lbl_unit_no_sow, R.id.units_sow_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_INTERCROP);
				displayDialog(v, data, INTERCROP, "Main crop or intercrop?",
						R.raw.bagof50kg, R.id.dlg_lbl_intercrop_sow,
						R.id.intercrop_sow_tr, 0);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_sow,
						R.id.day_sow_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// long click sounds are always forced since they represent
		// the helping system.

		// checks which view was clicked.
		if (v.getId() == R.id.dlg_var_text_sow) {
			playAudio(R.raw.varietyofseedssowd, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_sow) {
			playAudio(R.raw.selecttheunits, true);
		} else if (v.getId() == R.id.dlg_lbl_month_sow) {
			playAudio(R.raw.selectthedate, true);
		} else if (v.getId() == R.id.dlg_lbl_treat_sow) {
			playAudio(R.raw.treatmenttoseeds1, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
		} else if (v.getId() == R.id.seed_type_sow_tr) {
			playAudio(R.raw.variety, true);
		} else if (v.getId() == R.id.units_sow_tr) {
			playAudio(R.raw.amount, true);
		} else if (v.getId() == R.id.treatment_sow_tr) {
			playAudio(R.raw.treatment, true);
		} else if (v.getId() == R.id.intercrop_sow_tr) {
			playAudio(R.raw.intercrop, true);
		} else if (v.getId() == R.id.dlg_lbl_day_sow) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.day_sow_tr) {
			playAudio(R.raw.date, true);
		} else {
			// checks if the parent has the sound.
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets the current values from the map.
		mSeedType = (Integer) mResultsMap.get(VARIETY);
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		// month corresponds to the id in the resource table.
		mMonth = (Integer) mResultsMap.get(MONTH);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mTreatment = (Integer) mResultsMap.get(TREATMENT);
		mIntercrop = (Integer) mResultsMap.get(INTERCROP);

		// flag that indicates if the form is valid.
		boolean isValid = true;

		if (mSeedType != -1) {
			highlightField(R.id.seed_type_sow_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.seed_type_sow_tr, true);
		}

		if (mAmount > 0) {
			highlightField(R.id.units_sow_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_sow_tr, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.day_sow_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.day_sow_tr, true);
		}

		if (mTreatment != -1) {
			highlightField(R.id.treatment_sow_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.treatment_sow_tr, true);
		}

		if (mIntercrop != -1) {
			highlightField(R.id.intercrop_sow_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.intercrop_sow_tr, true);
		}

		// if all the fields are valid the data is inserted into the database.
		if (isValid) {

			// inserts the new plot into the table.
			long result = mDataProvider.addSowAction(Global.userId,
					Global.plotId, mAmount, mSeedType, mTreatment, mIntercrop,
					getDate(mDay, mMonth), 0);

			// returns true if no error was produced.
			return result != -1;
		}
		return false;
	}
}