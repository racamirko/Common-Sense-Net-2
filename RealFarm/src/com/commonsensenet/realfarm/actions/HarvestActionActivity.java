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

public class HarvestActionActivity extends DataFormActivity {

	public static final String AMOUNT = "amount";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String SATISFACTION = "satisfaction";
	public static final String UNIT = "unit";
	public static final String VARIETY = "variety";

	private int mAmount;
	private int mDay;
	private int mMonth;
	private int mSatisfaction;
	private int mUnit;
	private int mVariety;
	
	private int defaultVariety = -1;
	private int defaultMonth = -1;
	private int defaultUnit = -1;
	private int defaultSatisfaction = -1;
	private String defaultAmount = "0";
	private String defaultDay = "0";
	
	private List<Resource> varietyList;
	private List<Resource> monthList;
	private List<Resource> unitList;
	private List<Resource> satisfactionList;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_harvest_action);
		
		varietyList =  mDataProvider.getVarietiesByPlotAndSeason(Global.plotId);
		monthList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);
		unitList = mDataProvider
				.getUnits(RealFarmDatabase.ACTION_TYPE_HARVEST_ID);
		satisfactionList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_SATISFACTION);

		playAudio(R.raw.clickingharvest);

		// adds the fields that need to be validated.
		mResultsMap.put(VARIETY, defaultVariety);
		mResultsMap.put(DAY, defaultDay);
		mResultsMap.put(MONTH, defaultMonth);
		mResultsMap.put(AMOUNT, defaultAmount);
		mResultsMap.put(UNIT, defaultUnit);
		mResultsMap.put(SATISFACTION, defaultSatisfaction);

		View item1 = findViewById(R.id.dlg_lbl_harvest_crop);
		View item2 = findViewById(R.id.dlg_lbl_day_harvest);
		View item3 = findViewById(R.id.dlg_lbl_month_harvest);
		View item4 = findViewById(R.id.dlg_lbl_unit_no_harvest);
		View item5 = findViewById(R.id.dlg_lbl_units_harvest);
		View item6 = findViewById(R.id.dlg_lbl_satisfaction_harvest);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);

		View harvest_date = findViewById(R.id.harvest_date_tr);
		View Amount = findViewById(R.id.units_harvest_tr);

		harvest_date.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				// TODO AUDIO: "Select the variety" This is the audio that is heard when the selector dialog opens
				displayDialog(v, varietyList, VARIETY, "Select the variety",
						R.raw.problems, R.id.dlg_lbl_harvest_crop,
						R.id.var_harvest_crop, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				// TODO AUDIO: "Choose the day" This is the audio that is heard when the selector dialog opens
				// TODO AUDIO:  Text on tap on ok button in Number picker
				// TODO AUDIO:  Text on tap on cancel button in Number picker
				// TODO AUDIO:  Info on long tap on ok button in Number picker
				// TODO AUDIO:  Info on long tap on cancel button in Number picker
				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_harvest, R.id.harvest_date_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				
				// TODO AUDIO: "Select the month" This is the audio that is heard when the selector dialog opens
				displayDialog(v, monthList, MONTH, "Select the month",
						R.raw.bagof50kg, R.id.dlg_lbl_month_harvest,
						R.id.harvest_date_tr, 0);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				// TODO AUDIO: "Choose the number of bags" This is the audio that is heard when the selector dialog opens
				// TODO AUDIO:  Text on tap on ok button in Number picker
				// TODO AUDIO:  Text on tap on cancel button in Number picker
				// TODO AUDIO:  Info on long tap on ok button in Number picker
				// TODO AUDIO:  Info on long tap on cancel button in Number picker
				displayDialogNP("Choose the number of bags", AMOUNT,
						R.raw.dateinfo, 1, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_harvest, R.id.units_harvest_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				// TODO AUDIO: "Select the unit" This is the audio that is heard when the selector dialog opens
				displayDialog(v, unitList, UNIT, "Select the unit", R.raw.problems,
						R.id.dlg_lbl_units_harvest, R.id.units_harvest_tr, 2);

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				// TODO AUDIO: "Are you satisfied?" This is the audio that is heard when the selector dialog opens
				displayDialog(v, satisfactionList, SATISFACTION, "Are you satisfied?",
						R.raw.feedbackgood, R.id.dlg_lbl_satisfaction_harvest,
						R.id.satisfaction_harvest_tr, 1);
			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		// all long click sounds override the sound enabled flag.

		if (v.getId() == R.id.dlg_lbl_harvest_crop) {
			// TODO AUDIO: "Select the variety" default if nothing is in the field
			if((Integer) mResultsMap.get(VARIETY) == defaultVariety) playAudio(R.raw.feedbackgood, true); 
			else playAudio(varietyList.get(((Integer)mResultsMap.get(VARIETY))).getAudio()); 
		} else if (v.getId() == R.id.dlg_lbl_unit_no_harvest) {
			// TODO AUDIO: "Select the amount" default if nothing is in the field
			if(mResultsMap.get(AMOUNT).equals(defaultAmount)) playAudio(R.raw.selecttheunits, true); 
			// TODO AUDIO: Say the number Integer.valueOf(mResultsMap.get(DAY).toString());
			else playAudio(R.raw.problems, true); 
		} else if (v.getId() == R.id.dlg_lbl_units_harvest) {
			// TODO AUDIO: "Select the variety" default if nothing is in the field
			if((Integer) mResultsMap.get(UNIT) == defaultUnit) playAudio(R.raw.selecttheunits, true); 
			else playAudio(unitList.get(((Integer)mResultsMap.get(UNIT))).getAudio()); 
		} else if (v.getId() == R.id.dlg_lbl_day_harvest) {
			// TODO AUDIO: "Select the day" default if nothing is in the field
			if(mResultsMap.get(DAY).equals(defaultDay)) playAudio(R.raw.selectthedate, true); 
			// TODO AUDIO: Say the number Integer.valueOf(mResultsMap.get(DAY).toString());
			else playAudio(R.raw.problems, true);   
		} else if (v.getId() == R.id.dlg_lbl_month_harvest) {
			// TODO AUDIO: "Choose the month" default if nothing is in the field
			if((Integer) mResultsMap.get(MONTH) == defaultMonth) playAudio(R.raw.choosethemonthwhenharvested, true); 
			else playAudio(monthList.get(((Integer)mResultsMap.get(MONTH))).getAudio()); 
		} else if (v.getId() == R.id.dlg_lbl_satisfaction_harvest) {
			// TODO AUDIO: "Are you satisfied?" default if nothing is in the field
			if((Integer) mResultsMap.get(SATISFACTION) == defaultSatisfaction) playAudio(R.raw.feedbackgood, true); 
			else playAudio(satisfactionList.get(((Integer)mResultsMap.get(SATISFACTION))).getAudio()); 
		} 
		
		// TODO AUDIO: Check the remaining audio
		else if (v.getId() == R.id.harvest_date_tr) {
			playAudio(R.raw.harvestyear, true);
		} else if (v.getId() == R.id.units_harvest_tr) {
			playAudio(R.raw.amount, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help, true);
		} else {
			return super.onLongClick(v);
		}

		// shows the help icon.
		showHelpIcon(v);

		return true;
	}

	@Override
	protected Boolean validateForm() {

		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());
		mAmount = Integer.valueOf(mResultsMap.get(AMOUNT).toString());

		boolean isValid = true;

		if ((Integer)mResultsMap.get(VARIETY) != defaultVariety) {
			highlightField(R.id.var_harvest_crop, false);
		} else {
			isValid = false;
			highlightField(R.id.var_harvest_crop, true);
		}

		if ((Integer) mResultsMap.get(MONTH) != defaultMonth && mDay > Integer.parseInt(defaultDay) && validDate(mDay, monthList.get((Integer) mResultsMap.get(MONTH)).getId())) {
			highlightField(R.id.harvest_date_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.harvest_date_tr, true);
		}

		if ((Integer)mResultsMap.get(UNIT) != defaultUnit && mAmount > Integer.parseInt(defaultAmount)) {
			highlightField(R.id.units_harvest_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.units_harvest_tr, true);
		}

		if ((Integer)mResultsMap.get(SATISFACTION) != defaultSatisfaction) {
			highlightField(R.id.satisfaction_harvest_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.satisfaction_harvest_tr, true);
		}

		// if all fields are valid the data is inserted in the database.
		if (isValid) {
			
			mVariety = varietyList.get((Integer)mResultsMap.get(VARIETY)).getId();
			mMonth = monthList.get((Integer)mResultsMap.get(MONTH)).getId();
			mUnit = unitList.get((Integer)mResultsMap.get(UNIT)).getId();
			mSatisfaction = satisfactionList.get((Integer)mResultsMap.get(SATISFACTION)).getId();
			
			long result = mDataProvider.addHarvestAction(Global.userId,
					Global.plotId, mVariety, mAmount, mUnit, mSatisfaction,
					getDate(mDay, mMonth), 0);

			return result != -1;
		}
		return false;
	}
}