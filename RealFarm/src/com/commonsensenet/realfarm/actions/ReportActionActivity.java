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

public class ReportActionActivity extends DataFormActivity {

	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String PROBLEM = "problem";
	public static final String VARIETY = "variety";

	private int mDay;
	private int mMonth;
	private int mProblem;
	private int mVariety;

	private int defaultProblem = -1;
	private int defaultVariety = -1;
	private int defaultMonth = -1;
	private String defaultDay = "0";

	private List<Resource> problemList;
	private List<Resource> varietyList;
	private List<Resource> monthList;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "help");
			playAudio(R.raw.report_help, true);

			return true;
		} else { // asks the parent.
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_report_action);

		problemList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_PROBLEM);
		varietyList = mDataProvider.getVarietiesByPlotAndSeason(Global.plotId);
		monthList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);

		playAudio(R.raw.clickingreporting);

		// adds the values that need to be validated.
		mResultsMap.put(PROBLEM, defaultProblem);
		mResultsMap.put(VARIETY, defaultVariety);
		mResultsMap.put(DAY, defaultDay);
		mResultsMap.put(MONTH, defaultMonth);

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

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, problemList, PROBLEM,
						"Choose the problem type", R.raw.selecttheproblem,
						R.id.dlg_lbl_var_prob, R.id.var_prob_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, varietyList, VARIETY, "Select the variety",
						R.raw.select_the_variety, R.id.dlg_lbl_var_prob4,
						R.id.var_prob_tr4, 0);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_prob, R.id.day_prob_tr, R.raw.ok,
						R.raw.cancel, R.raw.day_ok, R.raw.day_cancel);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, monthList, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_prob,
						R.id.day_prob_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		// forces all audio sounds to be played.
		if (v.getId() == R.id.dlg_lbl_var_prob) {

			if ((Integer) mResultsMap.get(PROBLEM) == defaultProblem)
				playAudio(R.raw.selecttheproblem, true);
			else
				playAudio(problemList.get(((Integer) mResultsMap.get(PROBLEM)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_var_prob4) {

			if ((Integer) mResultsMap.get(VARIETY) == defaultVariety)
				playAudio(R.raw.select_the_variety, true);
			else
				playAudio(varietyList.get(((Integer) mResultsMap.get(VARIETY)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_day_prob) {

			if (mResultsMap.get(DAY).equals(defaultDay))
				playAudio(R.raw.dateinfo, true);

			else {
				play_integer(Integer.valueOf(mResultsMap.get(DAY).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.dlg_lbl_month_prob) {

			if ((Integer) mResultsMap.get(MONTH) == defaultMonth)
				playAudio(R.raw.choosethemonth, true);
			else
				playAudio(monthList.get(((Integer) mResultsMap.get(MONTH)))
						.getAudio(), true);
		}

		else if (v.getId() == R.id.var_prob_tr) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.var_prob_tr4) {
			playAudio(R.raw.variety, true);
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
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());

		// flag to indicate the validity of the form.
		boolean isValid = true;

		if ((Integer) mResultsMap.get(PROBLEM) != defaultProblem) {
			highlightField(R.id.var_prob_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, PROBLEM);
			isValid = false;
			highlightField(R.id.var_prob_tr, true);
		}

		if ((Integer) mResultsMap.get(MONTH) != defaultMonth
				&& mDay > Integer.parseInt(defaultDay)
				&& validDate(mDay,
						monthList.get((Integer) mResultsMap.get(MONTH)).getId())) {
			highlightField(R.id.day_prob_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, MONTH, DAY);
			isValid = false;
			highlightField(R.id.day_prob_tr, true);
		}

		if ((Integer) mResultsMap.get(VARIETY) != defaultVariety) {
			highlightField(R.id.var_prob_tr4, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, VARIETY);

			isValid = false;
			highlightField(R.id.var_prob_tr4, true);
		}

		if (isValid) {

			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "data entered");

			mProblem = problemList.get((Integer) mResultsMap.get(PROBLEM))
					.getId();
			mVariety = varietyList.get((Integer) mResultsMap.get(VARIETY))
					.getId();
			mMonth = monthList.get((Integer) mResultsMap.get(MONTH)).getId();

			long result = mDataProvider.addReportAction(Global.userId,
					Global.plotId, mVariety, mProblem, getDate(mDay, mMonth),
					Global.IsAdmin);
			return result != -1;
		}

		return false;
	}
}