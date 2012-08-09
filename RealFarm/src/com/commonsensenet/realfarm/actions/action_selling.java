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

public class action_selling extends DataFormActivity {

	private String crop_sell = "0";
	private int date_sel;
	private String months_harvest = "0";
	private int sell_no;
	private int sell_no_rem = -1;
	private int sell_price;
	private String units_rem_sell = "0";

	private String units_sell = "0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.selling_dialog);

		System.out.println("selling done");

		playAudio(R.raw.clickingselling);

		// adds the fields to validate to the map.
		mResultsMap.put("crop_sell", "0");
		mResultsMap.put("months_harvest", "0");
		mResultsMap.put("units_sell", "0");
		mResultsMap.put("units_rem_sell", "0");
		mResultsMap.put("date_sel", "0");
		mResultsMap.put("sell_no", "0");
		mResultsMap.put("sell_price", "0");
		mResultsMap.put("sell_no_rem", "0");

		// bg_day_sow.setImageResource(R.drawable.empty_not);

		final View item1 = (View) findViewById(R.id.dlg_lbl_crop_sell);
		final View item2 = (View) findViewById(R.id.dlg_lbl_date_sell);
		final View item3 = (View) findViewById(R.id.dlg_lbl_month_sell);
		final View item4 = (View) findViewById(R.id.dlg_lbl_unit_no_sell);
		final View item5 = (View) findViewById(R.id.dlg_lbl_unit_sell);
		final View item6 = (View) findViewById(R.id.dlg_lbl_price_sell);
		final View item7 = (View) findViewById(R.id.dlg_lbl_unit_no_rem_sell);
		final View item8 = (View) findViewById(R.id.dlg_lbl_unit_rem_sell);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		item8.setOnLongClickListener(this);

		final View crop;
		final View date;
		final View quantity;
		final View priceperquint;
		final View remain;

		crop = (View) findViewById(R.id.crop_sell_tr);
		date = (View) findViewById(R.id.date_sell_tr);

		quantity = (View) findViewById(R.id.quant_sell_tr);
		priceperquint = (View) findViewById(R.id.price_sell_tr);
		remain = (View) findViewById(R.id.rem_quant_sell_tr);

		crop.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		quantity.setOnLongClickListener(this);
		priceperquint.setOnLongClickListener(this);
		remain.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider.getCropTypes();
				displayDialog(v, data, "crop_sell", "Select the crop",
						R.raw.problems, R.id.dlg_lbl_crop_sell,
						R.id.crop_sell_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the day", "date_sel", R.raw.dateinfo,
						1, 31, Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH), 1, 0,
						R.id.dlg_lbl_date_sell, R.id.date_sell_tr,
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
				displayDialog(v, data, "months_harvest", "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_sell,
						R.id.date_sell_tr, 0);

			}

		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of bags", "sell_no",
						R.raw.dateinfo, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_sell, R.id.quant_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units sow dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_UNIT);
				displayDialog(v, data, "units_sell", "Select the unit",
						R.raw.problems, R.id.dlg_lbl_unit_sell,
						R.id.quant_sell_tr, 2);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Enter the price", "sell_price",
						R.raw.dateinfo, 0, 9999, 3200, 50, 0,
						R.id.dlg_lbl_price_sell, R.id.price_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of bags", "sell_no_rem",
						R.raw.dateinfo, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_rem_sell, R.id.rem_quant_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units sow dialog", "in dialog");

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_UNIT);
				displayDialog(v, data, "units_rem_sell", "Select the unit",
						R.raw.problems, R.id.dlg_lbl_unit_rem_sell,
						R.id.rem_quant_sell_tr, 2);

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.date_sell_tr) {

			playAudio(R.raw.choosethemonth);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.quant_sell_tr) {

			playAudio(R.raw.quantity);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.crop_sell_tr) {

			playAudio(R.raw.crop);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.date_sell_tr) {

			playAudio(R.raw.date);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.price_sell_tr) {

			playAudio(R.raw.priceperquintal);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.rem_quant_sell_tr) {

			playAudio(R.raw.remaining);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_crop_sell) {

			playAudio(R.raw.crop);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_date_sell) {

			playAudio(R.raw.date);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_sell) {

			playAudio(R.raw.choosethemonth);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_sell) {

			playAudio(R.raw.noofbags);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_sell) {
			playAudio(R.raw.keygis);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_price_sell) {

			playAudio(R.raw.value);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_rem_sell) {

			playAudio(R.raw.noofbags);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_rem_sell) {

			playAudio(R.raw.keygis);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help);
			showHelpIcon(v);
		}

		if (v.getId() == R.id.sell_ok) {

			playAudio(R.raw.ok);
			showHelpIcon(v);

		}
		if (v.getId() == R.id.sell_cancel) {

			playAudio(R.raw.cancel);
			showHelpIcon(v);

		}

		return true;
	}

	@Override
	protected Boolean validateForm() {
		crop_sell = mResultsMap.get("crop_sell").toString();
		months_harvest = mResultsMap.get("months_harvest").toString();
		units_sell = mResultsMap.get("units_sell").toString();
		units_rem_sell = mResultsMap.get("units_rem_sell").toString();
		date_sel = Integer.parseInt(mResultsMap.get("date_sel").toString());
		sell_no = Integer.parseInt(mResultsMap.get("sell_no").toString());
		sell_price = Integer.parseInt(mResultsMap.get("sell_price").toString());
		sell_no_rem = Integer.parseInt(mResultsMap.get("sell_no_rem")
				.toString());

		int flag1, flag2, flag3, flag4, flag5;

		if (crop_sell.toString().equalsIgnoreCase("0")) {

			flag1 = 1;

			View tr_feedback = (View) findViewById(R.id.crop_sell_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag1 = 0;

			View tr_feedback = (View) findViewById(R.id.crop_sell_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (months_harvest.toString().equalsIgnoreCase("0") || date_sel == 0) {

			flag2 = 1;

			View tr_feedback = (View) findViewById(R.id.date_sell_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag2 = 0;

			View tr_feedback = (View) findViewById(R.id.date_sell_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (units_sell.toString().equalsIgnoreCase("0") || sell_no == 0) {

			flag3 = 1;

			View tr_feedback = (View) findViewById(R.id.quant_sell_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag3 = 0;

			View tr_feedback = (View) findViewById(R.id.quant_sell_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (sell_price == 0) {
			flag4 = 1;

			View tr_feedback = (View) findViewById(R.id.price_sell_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag4 = 0;

			View tr_feedback = (View) findViewById(R.id.price_sell_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (units_rem_sell.toString().equalsIgnoreCase("0")
				|| sell_no_rem == -1) {

			flag5 = 1;

			View tr_feedback = (View) findViewById(R.id.rem_quant_sell_tr);

			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {

			flag5 = 0;

			View tr_feedback = (View) findViewById(R.id.rem_quant_sell_tr);

			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {

			return true;

		}
		return false;
	}
}