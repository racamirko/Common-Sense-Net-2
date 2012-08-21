package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_fertilize_action);

		// adds the values that need to be validated.
		mResultsMap.put(FERTILIZER, -1);
		mResultsMap.put(AMOUNT, "0");
		mResultsMap.put(UNIT, -1);
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);

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

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_FERTILIZER);
				displayDialog(v, data, FERTILIZER, "Choose the fertilizer",
						R.raw.selecttypeoffertilizer, R.id.dlg_lbl_var_fert,
						R.id.var_fert_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the amount", AMOUNT, R.raw.select_unit_number, 0,
						100, 1, 0.25, 2, R.id.dlg_lbl_unit_no_fert,
						R.id.units_fert_tr, R.raw.ok, R.raw.cancel,
						R.raw.fert_ok, R.raw.fert_cancel);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID);
				displayDialog(v, data, UNIT, "Choose the unit", R.raw.problems,
						R.id.dlg_lbl_units_fert, R.id.units_fert_tr, 1);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_fert, R.id.day_fert_tr,
						R.raw.ok, R.raw.cancel, R.raw.day_ok,
						R.raw.day_cancel);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_fert,
						R.id.day_fert_tr, 0); 
			}
		});
	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_var_fert) {
			playAudio(R.raw.selecttypeoffertilizer);
		} else if (v.getId() == R.id.dlg_lbl_units_fert) {
			playAudio(R.raw.selecttheunits);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_fert) {
			playAudio(R.raw.select_unit_number);
		} else if (v.getId() == R.id.dlg_lbl_day_fert) {
			playAudio(R.raw.selectthedate);
		} else if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.ok);
		} else if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.cancel);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.fert_help);
		} else if (v.getId() == R.id.var_fert_tr) {
			playAudio(R.raw.fertilizername);
		} else if (v.getId() == R.id.day_fert_tr) {
			playAudio(R.raw.date);
		} else if (v.getId() == R.id.units_fert_tr) {
			playAudio(R.raw.amount);
		} else if (v.getId() == R.id.dlg_lbl_month_fert) {
			playAudio(R.raw.choosethemonth);
		} else {
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				getLogTag(), v.getId());

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets the current values from the hash
		mFertilizer = (Integer) mResultsMap.get(FERTILIZER);
		mAmount = Double.valueOf(mResultsMap.get(AMOUNT).toString());
		mUnit = (Integer) mResultsMap.get(UNIT);
		mDay = Integer.parseInt(mResultsMap.get(DAY).toString());
		mMonth = (Integer) mResultsMap.get(MONTH);

		// flag to indicate the validity of the form.
		boolean isValid = true;

		if (mUnit != -1 && mAmount > 0) {
			highlightField(R.id.units_fert_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_fert_tr, true);
		}

		if (mFertilizer != -1) {
			highlightField(R.id.units_fert_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.var_fert_tr, true);
		}

		if (mMonth != -1 && mDay > 0 && validDate(mDay, mMonth)) {
			highlightField(R.id.day_fert_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.day_fert_tr, true);
		}

		// inserts the action if all fields are valid.
		if (isValid) {
			long result = mDataProvider.addFertilizeAction(Global.userId,
					Global.plotId, mAmount, mFertilizer, mUnit,
					getDate(mDay, mMonth), 0);

			return result != -1;
		}
		return false;
	}
}