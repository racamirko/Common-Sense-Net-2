package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class SowActionActivity extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String DEFAULT_AMOUNT = "0";
	public static final String DEFAULT_DAY = "0";
	public static final int DEFAULT_INTERCROP = -1;
	public static final int DEFAULT_MONTH = -1;
	public static final int DEFAULT_TREATMENT = -1;
	public static final int DEFAULT_VARIETY = -1;
	public static final String INTERCROP = "intercrop";
	public static final String MONTH = "month";
	public static final String TREATMENT = "treatment";
	public static final String VARIETY = "variety";

	private int mAmount;
	private int mDay;
	private int mIntercrop;
	private List<Resource> mIntercropList;
	private int mMonth;
	private List<Resource> mMonthsList;
	private int mSeedType;
	private int mTreatment;
	private List<Resource> mTreatmentList;
	private List<Resource> mVarietiesList;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_sow_action);

		// loads the data from the database.
		mVarietiesList = mDataProvider.getVarieties();
		mTreatmentList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_TREATMENT);
		mIntercropList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_INTERCROP);
		mMonthsList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);

		// adds the fields to validate to the map.
		mResultsMap.put(VARIETY, DEFAULT_VARIETY);
		mResultsMap.put(AMOUNT, DEFAULT_AMOUNT);
		mResultsMap.put(DAY, DEFAULT_DAY);
		mResultsMap.put(MONTH, DEFAULT_MONTH);
		mResultsMap.put(TREATMENT, DEFAULT_TREATMENT);
		mResultsMap.put(INTERCROP, DEFAULT_INTERCROP);

		playAudio(R.raw.thankyouclickingactionsowing);

		View varietyLabel = findViewById(R.id.dlg_var_text_sow);
		View amountLabel = findViewById(R.id.dlg_lbl_amount_sow);
		View dayLabel = findViewById(R.id.dlg_lbl_day_sow);
		View monthLabel = findViewById(R.id.dlg_lbl_month_sow);
		View treatmentLabel = findViewById(R.id.dlg_lbl_treat_sow);
		View intercropLabel = findViewById(R.id.dlg_lbl_intercrop_sow);

		varietyLabel.setOnLongClickListener(this);
		amountLabel.setOnLongClickListener(this);
		dayLabel.setOnLongClickListener(this);
		monthLabel.setOnLongClickListener(this);
		treatmentLabel.setOnLongClickListener(this);
		intercropLabel.setOnLongClickListener(this);

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

		varietyLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mVarietiesList, VARIETY, "Select the variety",
						R.raw.select_the_variety, R.id.dlg_var_text_sow,
						R.id.seed_type_sow_tr, 0);
			}
		});

		dayLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_sow, R.id.day_sow_tr, R.raw.ok,
						R.raw.cancel, R.raw.day_ok, R.raw.day_cancel);
			}
		});

		treatmentLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mTreatmentList, TREATMENT,
						"Select if the seeds were treated",
						R.raw.treatmenttoseeds1, R.id.dlg_lbl_treat_sow,
						R.id.treatment_sow_tr, 0);

			}
		});

		amountLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the number of serus", AMOUNT,
						R.raw.choose_serus, 1, 999, 1, 1, 0,
						R.id.dlg_lbl_amount_sow, R.id.units_sow_tr, R.raw.ok,
						R.raw.cancel, R.raw.seru_ok, R.raw.seru_cancel);
			}
		});

		intercropLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mIntercropList, INTERCROP,
						"Main crop or intercrop?", R.raw.maincrop_intercrop,
						R.id.dlg_lbl_intercrop_sow, R.id.intercrop_sow_tr, 0);
			}
		});

		monthLabel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mMonthsList, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_sow,
						R.id.day_sow_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		// long click sounds are always forced since they represent
		// the helping system.
		// checks which view was clicked.
		if (v.getId() == R.id.dlg_var_text_sow) {

			if ((Integer) mResultsMap.get(VARIETY) == DEFAULT_VARIETY)
				playAudio(R.raw.select_the_variety, true);
			else
				playAudio(
						mVarietiesList
								.get(((Integer) mResultsMap.get(VARIETY)))
								.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_amount_sow) {

			if (mResultsMap.get(AMOUNT).equals(DEFAULT_AMOUNT)) {
				playAudio(R.raw.choose_serus, true);
			} else {
				playInteger(Integer.valueOf(mResultsMap.get(AMOUNT).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.dlg_lbl_month_sow) {

			if ((Integer) mResultsMap.get(MONTH) == DEFAULT_MONTH) {
				playAudio(R.raw.choosethemonth, true);
			} else {
				playAudio(mMonthsList.get(((Integer) mResultsMap.get(MONTH)))
						.getAudio(), true);
			}
		} else if (v.getId() == R.id.dlg_lbl_treat_sow) {

			if ((Integer) mResultsMap.get(TREATMENT) == DEFAULT_TREATMENT) {
				playAudio(R.raw.treatmenttoseeds1, true);
			} else {
				playAudio(
						mMonthsList.get(((Integer) mResultsMap.get(TREATMENT)))
								.getAudio(), true);
			}
		} else if (v.getId() == R.id.dlg_lbl_intercrop_sow) {

			if ((Integer) mResultsMap.get(INTERCROP) == DEFAULT_INTERCROP) {
				playAudio(R.raw.maincrop_intercrop, true);
			} else {
				playAudio(
						mIntercropList.get(
								((Integer) mResultsMap.get(INTERCROP)))
								.getAudio(), true);
			}
		} else if (v.getId() == R.id.dlg_lbl_day_sow) {

			if (mResultsMap.get(DAY).equals(DEFAULT_DAY)) {
				playAudio(R.raw.dateinfo, true);
			} else {
				playInteger(Integer.valueOf(mResultsMap.get(DAY).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.seed_type_sow_tr) {
			playAudio(R.raw.variety, true);
		} else if (v.getId() == R.id.units_sow_tr) {
			playAudio(R.raw.amount, true);
		} else if (v.getId() == R.id.treatment_sow_tr) {
			playAudio(R.raw.treatmentdone, true);
		} else if (v.getId() == R.id.intercrop_sow_tr) {
			playAudio(R.raw.intercrop, true);
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
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), item.getTitle());
			playAudio(R.raw.sow_help, true);

			return true;
		} else { // asks the parent.
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Boolean validateForm() {

		// gets the current values from the map.
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		// month corresponds to the id in the resource table.
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());

		// flag that indicates if the form is valid.
		boolean isValid = true;

		if ((Integer) mResultsMap.get(VARIETY) != DEFAULT_VARIETY) {
			highlightField(R.id.seed_type_sow_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, VARIETY);
			isValid = false;
			highlightField(R.id.seed_type_sow_tr, true);
		}

		if (mAmount > Integer.parseInt(DEFAULT_AMOUNT)) {
			highlightField(R.id.units_sow_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, AMOUNT);

			isValid = false;
			highlightField(R.id.units_sow_tr, true);
		}

		if ((Integer) mResultsMap.get(MONTH) != DEFAULT_MONTH
				&& mDay > Integer.parseInt(DEFAULT_DAY)
				&& validDate(mDay,
						mMonthsList.get((Integer) mResultsMap.get(MONTH))
								.getId())) {
			highlightField(R.id.day_sow_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, MONTH, DAY);
			isValid = false;
			highlightField(R.id.day_sow_tr, true);
		}

		if ((Integer) mResultsMap.get(TREATMENT) != DEFAULT_TREATMENT) {
			highlightField(R.id.treatment_sow_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, TREATMENT);

			isValid = false;
			highlightField(R.id.treatment_sow_tr, true);
		}

		if ((Integer) mResultsMap.get(INTERCROP) != DEFAULT_INTERCROP) {
			highlightField(R.id.intercrop_sow_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, INTERCROP);

			isValid = false;
			highlightField(R.id.intercrop_sow_tr, true);
		}

		// if all the fields are valid the data is inserted into the database.
		if (isValid) {
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "data entered");

			mSeedType = mVarietiesList.get((Integer) mResultsMap.get(VARIETY))
					.getId();
			mMonth = mMonthsList.get((Integer) mResultsMap.get(MONTH)).getId();
			mTreatment = mTreatmentList.get(
					(Integer) mResultsMap.get(TREATMENT)).getId();
			mIntercrop = mIntercropList.get(
					(Integer) mResultsMap.get(INTERCROP)).getId();

			// inserts the new plot into the table.
			long result = mDataProvider.addSowAction(Global.userId,
					Global.plotId, mAmount, mSeedType, mTreatment, mIntercrop,
					getDate(mDay, mMonth), Global.isAdmin);

			// returns true if no error was produced.
			return result != -1;
		}
		return false;
	}
}