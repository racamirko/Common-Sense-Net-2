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

public class SellActionActivity extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String CROP = "crop";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String PRICE = "price";
	public static final String REMAINING = "remaining";
	public static final String UNIT = "unit";
	public static final String UNIT2 = "unit2";

	private int mCrop;
	private int mDay;
	private int mMonth;
	private int mAmount;
	private int mRemaining;
	private int mPrice;
	private int mUnit2;
	private int mUnit;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_sell_action);

		playAudio(R.raw.clickingselling);

		// adds the fields to validate to the map.
		mResultsMap.put(CROP, -1);
		mResultsMap.put(DAY, "0");
		mResultsMap.put(MONTH, -1);
		mResultsMap.put(AMOUNT, "0");
		mResultsMap.put(UNIT, -1);
		mResultsMap.put(PRICE, "0");
		mResultsMap.put(REMAINING, "0");
		mResultsMap.put(UNIT2, -1);

		View item1 = findViewById(R.id.dlg_lbl_crop_sell);
		View item2 = findViewById(R.id.dlg_lbl_date_sell);
		View item3 = findViewById(R.id.dlg_lbl_month_sell);
		View item4 = findViewById(R.id.dlg_lbl_unit_no_sell);
		View item5 = findViewById(R.id.dlg_lbl_unit_sell);
		View item6 = findViewById(R.id.dlg_lbl_price_sell);
		View item7 = findViewById(R.id.dlg_lbl_unit_no_rem_sell);
		View item8 = findViewById(R.id.dlg_lbl_unit_rem_sell);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		item8.setOnLongClickListener(this);

		View crop = findViewById(R.id.crop_sell_tr);
		View date = findViewById(R.id.date_sell_tr);
		View quantity = findViewById(R.id.quant_sell_tr);
		View priceperquint = findViewById(R.id.price_sell_tr);
		View remain = findViewById(R.id.rem_quant_sell_tr);

		crop.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		quantity.setOnLongClickListener(this);
		priceperquint.setOnLongClickListener(this);
		remain.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider.getCropTypes();
				displayDialog(v, data, CROP, "Select the crop", R.raw.problems,
						R.id.dlg_lbl_crop_sell, R.id.crop_sell_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_date_sell, R.id.date_sell_tr,
						R.raw.ok, R.raw.cancel, R.raw.day_ok,
						R.raw.day_cancel);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
				displayDialog(v, data, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_sell,
						R.id.date_sell_tr, 0);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the number of bags", AMOUNT,
						R.raw.noofbags, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_sell, R.id.quant_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.bag_ok,
						R.raw.bag_cancel);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_SELL_ID);
				displayDialog(v, data, UNIT, "Select the unit", R.raw.problems,
						R.id.dlg_lbl_unit_sell, R.id.quant_sell_tr, 2);
			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Enter the price", PRICE, R.raw.enterpricedetails, 0,
						9999, 3200, 50, 0, R.id.dlg_lbl_price_sell,
						R.id.price_sell_tr, R.raw.ok, R.raw.cancel,
						R.raw.pricesaved, R.raw.pricenotsaved);
			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Choose the number of bags", REMAINING,
						R.raw.noofbags, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_rem_sell, R.id.rem_quant_sell_tr,
						R.raw.ok, R.raw.cancel, R.raw.bag_ok,
						R.raw.bag_cancel);
			}
		});

		item8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				List<Resource> data = mDataProvider
						.getUnits(RealFarmDatabase.ACTION_TYPE_SELL_ID);
				displayDialog(v, data, UNIT2, "Select the unit",
						R.raw.problems, R.id.dlg_lbl_unit_rem_sell,
						R.id.rem_quant_sell_tr, 2);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// forces the sound to play since its the long click

		if (v.getId() == R.id.date_sell_tr) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.quant_sell_tr) {
			playAudio(R.raw.quantity, true);
		} else if (v.getId() == R.id.crop_sell_tr) {
			playAudio(R.raw.crop, true);
		} else if (v.getId() == R.id.date_sell_tr) {
			playAudio(R.raw.date, true);
		} else if (v.getId() == R.id.price_sell_tr) {
			playAudio(R.raw.priceperquintal, true);
		} else if (v.getId() == R.id.rem_quant_sell_tr) {
			playAudio(R.raw.remaining, true);
		} else if (v.getId() == R.id.dlg_lbl_crop_sell) {
			playAudio(R.raw.crop, true);
		} else if (v.getId() == R.id.dlg_lbl_date_sell) {
			playAudio(R.raw.date, true);
		} else if (v.getId() == R.id.dlg_lbl_month_sell) {
			playAudio(R.raw.choosethemonth, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_sell) {
			playAudio(R.raw.noofbags, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_sell) {
			playAudio(R.raw.keygis, true);
		} else if (v.getId() == R.id.dlg_lbl_price_sell) {
			playAudio(R.raw.value, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_rem_sell) {
			playAudio(R.raw.noofbags, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_rem_sell) {
			playAudio(R.raw.keygis, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.sell_help, true);
		} else {
			return super.onLongClick(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// gets the values to validate.
		mCrop = (Integer) mResultsMap.get(CROP);
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mMonth = (Integer) mResultsMap.get(MONTH);
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		mUnit = (Integer) mResultsMap.get(UNIT);
		mPrice = Integer.valueOf(mResultsMap.get(PRICE).toString());
		mUnit2 = (Integer) mResultsMap.get(UNIT2);
		mRemaining = Integer.valueOf(mResultsMap.get(REMAINING).toString());

		boolean isValid = true;

		if (mCrop != -1) {
			highlightField(R.id.crop_sell_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.crop_sell_tr, true);
		}

		if (mMonth != -1 && mDay > 0) {
			highlightField(R.id.date_sell_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.date_sell_tr, true);
		}

		if (mUnit != -1 && mAmount > 0) {
			highlightField(R.id.quant_sell_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.quant_sell_tr, true);
		}

		if (mPrice > 0) {
			highlightField(R.id.price_sell_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.price_sell_tr, true);
		}

		if (mUnit2 != -1 && mRemaining > -1) {
			highlightField(R.id.rem_quant_sell_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.rem_quant_sell_tr, true);
		}

		// validates the form.
		if (isValid) {
			long result = mDataProvider.addSellAction(Global.userId,
					Global.plotId, mCrop, mAmount, mRemaining, mUnit, mUnit2,
					mPrice, getDate(mDay, mMonth), 0);

			return result != -1;

		}

		return false;
	}
}