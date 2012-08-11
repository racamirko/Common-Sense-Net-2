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

public class ReportActionActivity extends DataFormActivity {

	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String PROBLEM = "problem";
	public static final String VARIETY = "variety";

	private int mDay;
	private int mMonth;
	private int mProblem;
	private int mVariety;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_report_action);

		playAudio(R.raw.clickingfertilising);

		// adds the values that need to be validated.
		mResultsMap.put(PROBLEM, -1);
		mResultsMap.put(VARIETY, -1);
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);

		View item1 = findViewById(R.id.dlg_lbl_var_prob);
		View item2 = findViewById(R.id.dlg_lbl_var_prob4);
		View item3 = findViewById(R.id.dlg_lbl_day_prob);
		View item4 = findViewById(R.id.dlg_lbl_month_prob);

		item1.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);

		View problem = findViewById(R.id.var_prob_tr);
		View crop = findViewById(R.id.var_prob_tr4);
		View Date = findViewById(R.id.day_prob_tr);

		problem.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PROBLEM);
				displayDialog(v, data, PROBLEM, "Choose the problem type",
						R.raw.problems, R.id.dlg_lbl_var_prob,
						R.id.var_prob_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider.getVarieties();
				displayDialog(v, data, VARIETY, "Select the variety",
						R.raw.problems, R.id.dlg_lbl_var_prob4,
						R.id.var_prob_tr4, 0);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_prob, R.id.day_prob_tr,
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
						R.raw.bagof50kg, R.id.dlg_lbl_month_prob,
						R.id.day_prob_tr, 0);

			}

		});

	}

	@Override
	public boolean onLongClick(View v) {

		// forces all audio sounds to be played.

		if (v.getId() == R.id.dlg_lbl_var_prob) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.dlg_lbl_var_prob4) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.dlg_lbl_day_prob) {
			playAudio(R.raw.selectthedate, true);
		} else if (v.getId() == R.id.dlg_lbl_month_prob) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.var_prob_tr) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.var_prob_tr4) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.day_prob_tr) {
			playAudio(R.raw.date, true);
		} else {
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets the values to validate.
		mProblem = (Integer) mResultsMap.get(PROBLEM);
		mVariety = (Integer) mResultsMap.get(VARIETY);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mMonth = (Integer) mResultsMap.get(MONTH);

		// flag to indicate the validity of the form.
		boolean isValid = true;

		if (mProblem != -1) {
			highlightField(R.id.var_prob_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.var_prob_tr, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.day_prob_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.day_prob_tr, true);
		}

		if (mVariety != -1) {
			highlightField(R.id.var_prob_tr4, false);

		} else {
			isValid = false;
			highlightField(R.id.var_prob_tr4, true);
		}

		if (isValid) {

			long result = mDataProvider
					.addReportAction(Global.userId, Global.plotId, mVariety,
							mProblem, getDate(mDay, mMonth), 0);
			return result != -1;
		}

		return false;
	}
}