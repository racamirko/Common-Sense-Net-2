package com.commonsensenet.realfarm.actions;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.commonsensenet.realfarm.DataFormActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;

public class action_irrigate extends DataFormActivity {
	private int hrs_irrigate = 0;
	private int irr_day_int;
	private String irr_day_sel;
	private String irr_method_sel = "0";
	private String months_irr = "0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.irrigate_dialog);

		System.out.println("plant done");

		playAudio(R.raw.clickingfertilising);

		// adds the values that need to be validated.
		mResultsMap.put("irr_method_sel", "0");
		mResultsMap.put("months_irr", "0");
		mResultsMap.put("irr_day_int", "0");
		mResultsMap.put("hrs_irrigate", "0");

		// bg_day_irr.setImageResource(R.drawable.empty_not);
		final View item1;
		final View item2;
		final View item3;
		final View item4;
		ImageButton home;
		ImageButton help;
		item1 = (View) findViewById(R.id.dlg_lbl_method_irr);

		item3 = (View) findViewById(R.id.dlg_lbl_day_irr);
		item2 = (View) findViewById(R.id.dlg_lbl_unit_no_irr);
		item4 = (View) findViewById(R.id.dlg_lbl_month_irr);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);

		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final View method;
		final View duration;
		final View Date;

		method = (View) findViewById(R.id.method_irr_tr);
		duration = (View) findViewById(R.id.units_irr_tr);
		Date = (View) findViewById(R.id.day_irr_tr);

		method.setOnLongClickListener(this);
		duration.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in irrigation method dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_IRRIGATIONMETHOD);
				displayDialog(v, data, "irr_method_sel",
						"Select the irrigation method", R.raw.problems,
						R.id.dlg_lbl_method_irr, R.id.method_irr_tr, 0);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the day", "irr_day_int",
						R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_irr, R.id.day_irr_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the irrigation duration",
						"hrs_irrigate", R.raw.dateinfo, 0, 24, 0, 1, 0,
						R.id.dlg_lbl_unit_no_irr, R.id.units_irr_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, "months_irr", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_irr,
						R.id.day_irr_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.dlg_lbl_method_irr) {
			playAudio(R.raw.method);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_irr) {
			playAudio(R.raw.noofhours);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_day_irr) {
			playAudio(R.raw.selectthedate);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.irr_ok) {
			playAudio(R.raw.ok);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.irr_cancel) {
			playAudio(R.raw.cancel);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_irr) {
			playAudio(R.raw.choosethemonth);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.method_irr_tr) {
			playAudio(R.raw.method);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.units_irr_tr) {
			playAudio(R.raw.duration);
			showHelpIcon(v);
		}
		if (v.getId() == R.id.day_irr_tr) {
			playAudio(R.raw.date);
			showHelpIcon(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		irr_method_sel = mResultsMap.get("irr_method_sel").toString();
		months_irr = mResultsMap.get("months_irr").toString();
		irr_day_int = Integer.parseInt(mResultsMap.get("irr_day_int")
				.toString());
		hrs_irrigate = Integer.parseInt(mResultsMap.get("hrs_irrigate")
				.toString());

		int flag1, flag2, flag3;
		if (hrs_irrigate == 0) {
			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.units_irr_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag1 = 0;

			View tr_feedback = (View) findViewById(R.id.units_irr_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (irr_method_sel.toString().equalsIgnoreCase("0")) {

			flag2 = 1;

			View tr_feedback = (View) findViewById(R.id.method_irr_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag2 = 0;

			View tr_feedback = (View) findViewById(R.id.method_irr_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_irr.toString().equalsIgnoreCase("0") || irr_day_int == 0) {

			flag3 = 1;

			View tr_feedback = (View) findViewById(R.id.day_irr_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag3 = 0;

			irr_day_sel = irr_day_int + "." + months_irr;
			View tr_feedback = (View) findViewById(R.id.day_irr_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
			System.out.println("Irrigting Writing");
			// mDataProvider.setIrrigation(Global.plotId, hrs_irrigate,
			// "hours", irr_day_sel, irr_method_sel, 0, 0);

			return true;

		}
		return false;
	}
}