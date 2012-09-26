package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class FertilizeActionActivity extends DataFormActivity implements
		OnLongClickListener {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String FERTILIZER = "fertilizer";
	public static final String MONTH = "month";
	public static final String UNIT = "unit";

	private double mAmount;
	private int mDay;
	private int mFertilizer;
	private int mMonth;
	private int mUnit;

	private int defaultFertilizer = -1;
	private int defaultUnit = -1;
	private int defaultMonth = -1;
	private String defaultAmount = "0";
	private String defaultDay = "0";

	private List<Resource> fertilizerList;
	private List<Resource> monthList;
	private List<Resource> unitList;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), item.getTitle());
			playAudio(R.raw.fert_help, true);

			return true;
		} else { // asks the parent.
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_fertilize_action);

		fertilizerList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_FERTILIZER);
		monthList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
		unitList = mDataProvider
				.getUnits(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID);

		// adds the values that need to be validated.
		mResultsMap.put(FERTILIZER, defaultFertilizer);
		mResultsMap.put(AMOUNT, defaultAmount);
		mResultsMap.put(UNIT, defaultUnit);
		mResultsMap.put(DAY, defaultDay);
		mResultsMap.put(MONTH, defaultMonth);

		playAudio(R.raw.clickingfertilising);

		View item1 = findViewById(R.id.dlg_lbl_var_fert);
		View item2 = findViewById(R.id.dlg_lbl_unit_no_fert);
		View item3 = findViewById(R.id.dlg_lbl_units_fert);
		View item4 = findViewById(R.id.dlg_lbl_day_fert);
		View item5 = findViewById(R.id.dlg_lbl_month_fert);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);

		final View fertilizerName;
		final View amount;
		final View date;

		fertilizerName = findViewById(R.id.var_fert_tr);
		amount = findViewById(R.id.units_fert_tr);
		date = findViewById(R.id.day_fert_tr);

		amount.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		fertilizerName.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				// TODO AUDIO: "Select the fertilizer" This is the audio that is
				// heard when the selector dialog opens
				displayDialog(v, fertilizerList, FERTILIZER,
						"Choose the fertilizer", R.raw.selecttypeoffertilizer,
						R.id.dlg_lbl_var_fert, R.id.var_fert_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the amount", AMOUNT,
						R.raw.select_unit_number, 0, 100, 1, 0.25, 2,
						R.id.dlg_lbl_unit_no_fert, R.id.units_fert_tr,
						R.raw.ok, R.raw.cancel, R.raw.fert_ok,
						R.raw.fert_cancel);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, unitList, UNIT, "Choose the unit",
						R.raw.selecttheunits, R.id.dlg_lbl_units_fert,
						R.id.units_fert_tr, 1);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_fert, R.id.day_fert_tr, R.raw.ok,
						R.raw.cancel, R.raw.day_ok, R.raw.day_cancel);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, monthList, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_fert,
						R.id.day_fert_tr, 0);
			}
		});
	}

	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		if (v.getId() == R.id.dlg_lbl_var_fert) {

			if ((Integer) mResultsMap.get(FERTILIZER) == defaultFertilizer)
				playAudio(R.raw.selecttypeoffertilizer, true);
			else
				playAudio(
						fertilizerList.get(
								((Integer) mResultsMap.get(FERTILIZER)))
								.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_units_fert) {

			if ((Integer) mResultsMap.get(UNIT) == defaultUnit)
				playAudio(R.raw.selecttheunits, true);
			else
				playAudio(unitList.get(((Integer) mResultsMap.get(UNIT)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_fert) {

			if (mResultsMap.get(AMOUNT).equals(defaultAmount))
				playAudio(R.raw.select_unit_number, true);
			else {

				playFloat(Float.valueOf(mResultsMap.get(AMOUNT).toString()));
				playSound();
			}

		} else if (v.getId() == R.id.dlg_lbl_day_fert) {

			if (mResultsMap.get(DAY).equals(defaultDay))
				playAudio(R.raw.dateinfo, true);

			else {
				playInteger(Integer.valueOf(mResultsMap.get(DAY).toString()));
				playSound();
			}

		} else if (v.getId() == R.id.dlg_lbl_month_fert) {

			if ((Integer) mResultsMap.get(MONTH) == defaultMonth)
				playAudio(R.raw.choosethemonth, true);
			else
				playAudio(monthList.get(((Integer) mResultsMap.get(MONTH)))
						.getAudio(), true);
		}

		else if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.ok, true);
		} else if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.cancel, true);
		} else if (v.getId() == R.id.var_fert_tr) {
			playAudio(R.raw.fertilizer_name, true);
			// playAudio(R.raw.a1, true);
		} else if (v.getId() == R.id.day_fert_tr) {
			playAudio(R.raw.date, true);
		} else if (v.getId() == R.id.units_fert_tr) {
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

		// gets the current values from the hash
		mAmount = Double.valueOf(mResultsMap.get(AMOUNT).toString());
		mDay = Integer.parseInt(mResultsMap.get(DAY).toString());

		// flag to indicate the validity of the form.
		boolean isValid = true;

		if ((Integer) mResultsMap.get(UNIT) != defaultUnit
				&& mAmount > Double.parseDouble(defaultAmount)) {
			highlightField(R.id.units_fert_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, UNIT, AMOUNT);
			isValid = false;
			highlightField(R.id.units_fert_tr, true);
		}

		if ((Integer) mResultsMap.get(FERTILIZER) != defaultFertilizer) {
			highlightField(R.id.var_fert_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, FERTILIZER);
			isValid = false;
			highlightField(R.id.var_fert_tr, true);
		}

		if ((Integer) mResultsMap.get(MONTH) != defaultMonth
				&& mDay > Integer.parseInt(defaultDay)
				&& validDate(mDay,
						monthList.get((Integer) mResultsMap.get(MONTH)).getId())) {
			highlightField(R.id.day_fert_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, MONTH, DAY);
			isValid = false;
			highlightField(R.id.day_fert_tr, true);
		}

		// inserts the action if all fields are valid.
		if (isValid) {

			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "data entered");

			mFertilizer = fertilizerList.get(
					(Integer) mResultsMap.get(FERTILIZER)).getId();
			mUnit = unitList.get((Integer) mResultsMap.get(UNIT)).getId();
			mMonth = monthList.get((Integer) mResultsMap.get(MONTH)).getId();

			long result = mDataProvider.addFertilizeAction(Global.userId,
					Global.plotId, mAmount, mFertilizer, mUnit,
					getDate(mDay, mMonth), Global.isAdmin);

			return result != -1;
		}
		return false;
	}
}