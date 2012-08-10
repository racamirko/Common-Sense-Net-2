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

public class IrrigateActionActivity extends DataFormActivity {

	public static final String DAY = "day";
	public static final String HOURS = "hours";
	public static final String METHOD = "method";
	public static final String MONTH = "month";

	private int mDay;
	private int mHours;
	private int mMethod;
	private int mMonth;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.irrigate_dialog);

		System.out.println("plant done");

		playAudio(R.raw.clickingfertilising);

		// adds the values that need to be validated.
		mResultsMap.put(METHOD, -1);
		mResultsMap.put(HOURS, "0");
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);

		View item1 = findViewById(R.id.dlg_lbl_method_irr);
		View item2 = findViewById(R.id.dlg_lbl_unit_no_irr);
		View item3 = findViewById(R.id.dlg_lbl_day_irr);
		View item4 = findViewById(R.id.dlg_lbl_month_irr);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		View method = findViewById(R.id.method_irr_tr);
		View duration = findViewById(R.id.units_irr_tr);
		View Date = findViewById(R.id.day_irr_tr);

		method.setOnLongClickListener(this);
		duration.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_IRRIGATIONMETHOD);
				displayDialog(v, data, METHOD, "Select the irrigation method",
						R.raw.problems, R.id.dlg_lbl_method_irr,
						R.id.method_irr_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the irrigation duration", HOURS,
						R.raw.dateinfo, 0, 24, 0, 1, 0,
						R.id.dlg_lbl_unit_no_irr, R.id.units_irr_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_irr, R.id.day_irr_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_irr,
						R.id.day_irr_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// long click sounds are forced played, since they are part of a help
		// feature.

		if (v.getId() == R.id.dlg_lbl_method_irr) {
			playAudio(R.raw.method, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_irr) {
			playAudio(R.raw.noofhours, true);
		} else if (v.getId() == R.id.dlg_lbl_day_irr) {
			playAudio(R.raw.selectthedate, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
		} else if (v.getId() == R.id.dlg_lbl_month_irr) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.method_irr_tr) {
			playAudio(R.raw.method, true);
		} else if (v.getId() == R.id.units_irr_tr) {
			playAudio(R.raw.duration, true);
		} else if (v.getId() == R.id.day_irr_tr) {
			playAudio(R.raw.date, true);
		} else {
			return onLongClick(v);
		}

		// shows the help icon
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets the values from the hash map.
		mMethod = (Integer) mResultsMap.get(METHOD);
		mHours = Integer.valueOf(mResultsMap.get(HOURS).toString());
		mMonth = (Integer) mResultsMap.get(MONTH);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());

		// flag used to indicate the validity of the form.
		boolean isValid = true;

		if (mMethod != -1) {
			highlightField(R.id.method_irr_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.method_irr_tr, true);
		}

		if (mHours > 0) {
			highlightField(R.id.units_irr_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_irr_tr, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.day_irr_tr, false);
		} else {
			isValid = true;
			highlightField(R.id.day_irr_tr, true);
		}

		if (isValid) {
			long result = mDataProvider.addIrrigateAction(Global.userId,
					Global.plotId, mHours, mMethod, getDate(mDay, mMonth), 0);

			return result != -1;
		}
		return false;
	}
}