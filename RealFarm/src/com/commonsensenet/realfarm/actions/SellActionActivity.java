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
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class SellActionActivity extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String CROP = "crop";
	public static final String DAY = "day";
	public static final String DEFAULT_AMOUNT = "0";
	public static final int DEFAULT_CROP = -1;
	public static final String DEFAULT_DAY = "0";
	public static final int DEFAULT_MONTH = -1;
	public static final String DEFAULT_PRICE = "0";
	public static final String DEFAULT_REMAINING_AMOUNT = "0";
	public static final int DEFAULT_UNIT1 = -1;
	public static final int DEFAULT_UNIT2 = -1;
	public static final String MONTH = "month";
	public static final String PRICE = "price";
	public static final String REMAINING = "remaining";
	public static final String UNIT = "unit";
	public static final String UNIT2 = "unit2";

	private int mAmount;
	private int mCrop;
	private List<Resource> mCropList;
	private int mDay;
	private int mMonth;
	private List<Resource> mMonthList;
	private int mPrice;
	private int mRemaining;
	private int mUnit;
	private List<Resource> mUnit1List;
	private int mUnit2;
	private List<Resource> mUnit2List;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "help");
			playAudio(R.raw.sell_help, true);

			return true;
		} else { // asks the parent.
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_sell_action);

		mCropList = mDataProvider.getCropTypes();
		mMonthList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
		mUnit1List = mDataProvider
				.getUnits(RealFarmDatabase.ACTION_TYPE_SELL_ID);
		mUnit2List = mDataProvider
				.getUnits(RealFarmDatabase.ACTION_TYPE_SELL_ID);

		playAudio(R.raw.clickingselling);

		// adds the fields to validate to the map.
		mResultsMap.put(CROP, DEFAULT_CROP);
		mResultsMap.put(DAY, DEFAULT_DAY);
		mResultsMap.put(MONTH, DEFAULT_MONTH);
		mResultsMap.put(AMOUNT, DEFAULT_AMOUNT);
		mResultsMap.put(UNIT, DEFAULT_UNIT1);
		mResultsMap.put(PRICE, DEFAULT_PRICE);
		mResultsMap.put(REMAINING, DEFAULT_REMAINING_AMOUNT);
		mResultsMap.put(UNIT2, DEFAULT_UNIT2);

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

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mCropList, CROP, "Select the crop",
						R.raw.select_the_crop, R.id.dlg_lbl_crop_sell,
						R.id.crop_sell_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_date_sell, R.id.date_sell_tr, R.raw.ok,
						R.raw.cancel, R.raw.day_ok, R.raw.day_cancel);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mMonthList, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_sell,
						R.id.date_sell_tr, 0);
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the number of bags", AMOUNT,
						R.raw.noofbags, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_sell, R.id.quant_sell_tr,
						R.raw.ok, R.raw.cancel, R.raw.bag_ok, R.raw.bag_cancel);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mUnit1List, UNIT, "Select the unit",
						R.raw.selecttheunits, R.id.dlg_lbl_unit_sell,
						R.id.quant_sell_tr, 2);
			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Enter the price", PRICE,
						R.raw.enterpricedetails, 0, 9999, 3200, 50, 0,
						R.id.dlg_lbl_price_sell, R.id.price_sell_tr, R.raw.ok,
						R.raw.cancel, R.raw.pricesaved, R.raw.pricenotsaved);
			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialogNP("Choose the number of bags", REMAINING,
						R.raw.noofbags, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_rem_sell, R.id.rem_quant_sell_tr,
						R.raw.ok, R.raw.cancel, R.raw.bag_ok, R.raw.bag_cancel);
			}
		});

		item8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				displayDialog(v, mUnit2List, UNIT2, "Select the unit",
						R.raw.selecttheunits, R.id.dlg_lbl_unit_rem_sell,
						R.id.rem_quant_sell_tr, 2);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		// forces the sound to play since its the long click
		if (v.getId() == R.id.dlg_lbl_crop_sell) {

			if ((Integer) mResultsMap.get(CROP) == DEFAULT_CROP)
				playAudio(R.raw.select_the_crop, true);
			else
				playAudio(mCropList.get(((Integer) mResultsMap.get(CROP)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_date_sell) {

			if (mResultsMap.get(DAY).equals(DEFAULT_DAY))
				playAudio(R.raw.dateinfo, true);

			else {
				play_integer(Integer.valueOf(mResultsMap.get(DAY).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.dlg_lbl_month_sell) {

			if ((Integer) mResultsMap.get(MONTH) == DEFAULT_MONTH)
				playAudio(R.raw.choosethemonth, true);
			else
				playAudio(mMonthList.get(((Integer) mResultsMap.get(MONTH)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_sell) {

			if (mResultsMap.get(AMOUNT).equals(DEFAULT_AMOUNT))
				playAudio(R.raw.select_unit_number, true);

			else {
				play_integer(Integer
						.valueOf(mResultsMap.get(AMOUNT).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.dlg_lbl_unit_sell) {

			if ((Integer) mResultsMap.get(UNIT) == DEFAULT_UNIT1)
				playAudio(R.raw.selecttheunits, true);
			else
				playAudio(mUnit1List.get(((Integer) mResultsMap.get(UNIT)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_price_sell) {

			if (mResultsMap.get(PRICE).equals(DEFAULT_PRICE))
				playAudio(R.raw.enterpricedetails, true);

			else
				playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.dlg_lbl_unit_no_rem_sell) {

			if (mResultsMap.get(REMAINING).equals(DEFAULT_REMAINING_AMOUNT))
				playAudio(R.raw.select_unit_number, true);

			else
				play_integer(Integer
						.valueOf(mResultsMap.get(AMOUNT).toString()));
		} else if (v.getId() == R.id.dlg_lbl_unit_rem_sell) {

			if ((Integer) mResultsMap.get(UNIT2) == DEFAULT_UNIT2) {
				playAudio(R.raw.selecttheunits, true);
			} else {
				playAudio(mUnit2List.get(((Integer) mResultsMap.get(UNIT2)))
						.getAudio(), true);
			}
		}

		else if (v.getId() == R.id.date_sell_tr) {
			playAudio(R.raw.date, true);
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
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());
		mPrice = Integer.valueOf(mResultsMap.get(PRICE).toString());
		mRemaining = Integer.valueOf(mResultsMap.get(REMAINING).toString());

		boolean isValid = true;

		if ((Integer) mResultsMap.get(CROP) != DEFAULT_CROP) {
			highlightField(R.id.crop_sell_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, CROP);

			isValid = false;
			highlightField(R.id.crop_sell_tr, true);
		}

		if ((Integer) mResultsMap.get(MONTH) != DEFAULT_MONTH
				&& mDay > Integer.parseInt(DEFAULT_DAY)
				&& validDate(mDay,
						mMonthList.get((Integer) mResultsMap.get(MONTH))
								.getId())) {
			highlightField(R.id.date_sell_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, MONTH, DAY);
			isValid = false;
			highlightField(R.id.date_sell_tr, true);
		}

		if ((Integer) mResultsMap.get(UNIT) != DEFAULT_UNIT1
				&& mAmount > Integer.parseInt(DEFAULT_AMOUNT)) {
			highlightField(R.id.quant_sell_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, UNIT, AMOUNT);

			isValid = false;
			highlightField(R.id.quant_sell_tr, true);
		}

		if (mPrice > Integer.parseInt(DEFAULT_PRICE)) {
			highlightField(R.id.price_sell_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, PRICE);

			isValid = false;
			highlightField(R.id.price_sell_tr, true);
		}

		// if the quantity is 0 the unit is useless.
		if (((Integer) mResultsMap.get(UNIT2) != DEFAULT_UNIT2 || mRemaining == 0)
				&& mRemaining > -1) {
			highlightField(R.id.rem_quant_sell_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, UNIT2, REMAINING);
			isValid = false;
			highlightField(R.id.rem_quant_sell_tr, true);
		}

		// validates the form.
		if (isValid) {
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "data entered");

			// gets the values to insert.
			mCrop = mCropList.get((Integer) mResultsMap.get(CROP)).getId();
			mMonth = mMonthList.get((Integer) mResultsMap.get(MONTH)).getId();
			mUnit = mUnit1List.get((Integer) mResultsMap.get(UNIT)).getId();
			if (mRemaining == 0) {
				mUnit2 = RealFarmProvider.NONE;
			} else {
				mUnit2 = mUnit2List.get((Integer) mResultsMap.get(UNIT2))
						.getId();
			}

			long result = mDataProvider.addSellAction(Global.userId, 0, mCrop,
					mAmount, mRemaining, mUnit, mUnit2, mPrice,
					getDate(mDay, mMonth), Global.IsAdmin);

			return result != -1;

		}

		return false;
	}
}