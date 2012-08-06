package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;

public class action_problem extends DataFormActivity {

	private String prob_var_sel = "0";
	private String prob_crop_sel = "0";
	private String prob_day_sel;
	private String months_prob = "0";
	private int prob_day_int;

	public static final String LOG_TAG = "action_problem";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.problem_dialog, LOG_TAG);

		playAudio(R.raw.clickingfertilising);

		// adds the values that need to be validated.
		mResultsMap.put("prob_var_sel", "0");
		mResultsMap.put("months_prob", "0");
		mResultsMap.put("prob_crop_sel", "0");
		mResultsMap.put("prob_day_int", "0");

		final View item1;
		final View item4;

		item1 = (View) findViewById(R.id.dlg_lbl_var_prob);
		item4 = (View) findViewById(R.id.dlg_lbl_var_prob4);
		final View item2 = (View) findViewById(R.id.dlg_lbl_day_prob);
		final View item3 = (View) findViewById(R.id.dlg_lbl_month_prob);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		final View problem;
		final View crop;
		final View Date;

		problem = (View) findViewById(R.id.var_prob_tr);
		crop = (View) findViewById(R.id.var_prob_tr4);
		Date = (View) findViewById(R.id.day_prob_tr);

		problem.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_PROBLEM);
				displayDialog(v, data, "prob_var_sel",
						"Choose the problem type", R.raw.problems,
						R.id.dlg_lbl_var_prob, R.id.var_prob_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				displayDialogNP("Choose the day", "prob_day_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_prob, R.id.day_prob_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, "months_prob", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_prob,
						R.id.day_prob_tr, 0);

			}

		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider.getVarieties();
				displayDialog(v, data, "prob_crop_sel", "Select the variety",
						R.raw.problems, R.id.dlg_lbl_var_prob4,
						R.id.var_prob_tr4, 0);

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {
		playAudio(R.raw.date);
		if (v.getId() == R.id.dlg_lbl_var_prob) {
			playAudio(R.raw.problems);
			showHelpIcon(v);
		}

		// TODO: put the audio
		if (v.getId() == R.id.dlg_lbl_var_prob4) {
			// playAudioalways(R.raw.problems);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_prob) {
			playAudio(R.raw.selectthedate);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.prob_ok) {
			playAudio(R.raw.ok);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.prob_cancel) {
			playAudio(R.raw.cancel);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_prob) {

			playAudio(R.raw.choosethemonth);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.var_prob_tr) {
			playAudio(R.raw.problems);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.var_prob_tr4) {
			playAudio(R.raw.problems);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.day_prob_tr) {
			playAudio(R.raw.date);
			showHelpIcon(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		prob_var_sel = mResultsMap.get("prob_var_sel").toString();
		months_prob = mResultsMap.get("months_prob").toString();
		prob_crop_sel = mResultsMap.get("prob_crop_sel").toString();
		prob_day_int = Integer.parseInt(mResultsMap.get("prob_day_int")
				.toString());

		int flag1, flag2, flag4;
		if (prob_var_sel.toString().equalsIgnoreCase("0")) {
			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.var_prob_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag1 = 0;

			View tr_feedback = (View) findViewById(R.id.var_prob_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_prob.toString().equalsIgnoreCase("0") || prob_day_int == 0) {
			flag2 = 1;

			View tr_feedback = (View) findViewById(R.id.day_prob_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag2 = 0;

			prob_day_sel = prob_day_int + "." + months_prob;
			View tr_feedback = (View) findViewById(R.id.day_prob_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (prob_crop_sel.toString().equalsIgnoreCase("0")) {
			flag4 = 1;

			View tr_feedback = (View) findViewById(R.id.var_prob_tr4);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag4 = 0;

			View tr_feedback = (View) findViewById(R.id.var_prob_tr4);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag4 == 0) {

			// mDataProvider.setProblem(Global.plotId, prob_day_sel,
			// prob_var_sel, 0, 0, prob_crop_sel);

			return true;
		}

		return false;
	}
}