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
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class IrrigateActionActivity extends DataFormActivity {

	public static final String DAY = "day";
	public static final String HOURS = "hours";
	public static final String METHOD = "method";
	public static final String MONTH = "month";

	private int mDay;
	private int mHours;
	private int mMethod;
	private int mMonth;
	
	private int defaultMethod = -1;
	private int defaultMonth = -1;
	private String defaultDay = "0";
	private String defaultHours = "0";
	
	private List<Resource> methodList;
	private List<Resource> monthList;	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_irrigate_action);
		
		methodList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_IRRIGATIONMETHOD); 
		monthList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_MONTH);

		playAudio(R.raw.clickingirrigation);

		// adds the values that need to be validated.
		mResultsMap.put(METHOD, defaultMethod);
		mResultsMap.put(HOURS, defaultHours);
		mResultsMap.put(DAY, defaultDay);
		mResultsMap.put(MONTH, defaultMonth);

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
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

				
				displayDialog(v, methodList, METHOD, "Select the irrigation method",
						R.raw.selecttheirrigationmethod, R.id.dlg_lbl_method_irr,
						R.id.method_irr_tr, 0);
			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

				
				
				
				
				displayDialogNP("Choose the irrigation duration", HOURS,
						R.raw.select_irr_duration, 0, 24, 0, 1, 0,
						R.id.dlg_lbl_unit_no_irr, R.id.units_irr_tr,
						R.raw.ok, R.raw.cancel, R.raw.irr_dur_ok,
						R.raw.irr_dur_cancel);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

				
				displayDialogNP("Choose the day", DAY, R.raw.dateinfo, 1, 31,
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1,
						0, R.id.dlg_lbl_day_irr, R.id.day_irr_tr,
						R.raw.ok, R.raw.cancel, R.raw.day_ok,
						R.raw.day_cancel);

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

				
				displayDialog(v, monthList, MONTH, "Select the month",
						R.raw.choosethemonth, R.id.dlg_lbl_month_irr,
						R.id.day_irr_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {
		
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
		ApplicationTracker.getInstance().flush();

		// long click sounds are forced played, since they are part of a help
		// feature.

		if (v.getId() == R.id.dlg_lbl_method_irr) {
			
			if((Integer) mResultsMap.get(METHOD) == defaultMethod) playAudio(R.raw.selecttheirrigationmethod, true); 
			else playAudio(methodList.get(((Integer)mResultsMap.get(METHOD))).getAudio(), true); 
		} else if (v.getId() == R.id.dlg_lbl_unit_no_irr) {
			
			if(mResultsMap.get(HOURS).equals(defaultHours)) playAudio(R.raw.noofhours, true); 
			
			else play_day_audio(Integer.valueOf(mResultsMap.get(HOURS).toString()));
		} else if (v.getId() == R.id.dlg_lbl_day_irr) {
			
			if(mResultsMap.get(DAY).equals(defaultDay)) playAudio(R.raw.selectthedate, true); 
			
			else play_day_audio(Integer.valueOf(mResultsMap.get(DAY).toString()));   
		} else if (v.getId() == R.id.dlg_lbl_month_irr) {
			
			if((Integer) mResultsMap.get(MONTH) == defaultMonth) playAudio(R.raw.choosethemonth, true); 
			else playAudio(monthList.get(((Integer)mResultsMap.get(MONTH))).getAudio(), true); 
		}
		
		
		else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.irr_help, true);
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
		mHours = Integer.valueOf(mResultsMap.get(HOURS).toString());
		mDay = Integer.valueOf(mResultsMap.get(DAY).toString());

		// flag used to indicate the validity of the form.
		boolean isValid = true;

		if ((Integer)mResultsMap.get(METHOD) != defaultMethod) {
			highlightField(R.id.method_irr_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, METHOD);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.method_irr_tr, true);
		}

		if (mHours > Integer.parseInt(defaultHours)) {
			highlightField(R.id.units_irr_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, HOURS);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.units_irr_tr, true);
		}
		
		if ((Integer) mResultsMap.get(MONTH) != defaultMonth && mDay > Integer.parseInt(defaultDay) && validDate(mDay, monthList.get((Integer) mResultsMap.get(MONTH)).getId())) {
			highlightField(R.id.day_irr_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, MONTH, DAY);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.day_irr_tr, true);
		}

		if (isValid) {
			
			ApplicationTracker.getInstance().logEvent(EventType.CLICK, "data entered");
			ApplicationTracker.getInstance().flush();
			
			mMethod = methodList.get((Integer)mResultsMap.get(METHOD)).getId();;
			mMonth = monthList.get((Integer)mResultsMap.get(MONTH)).getId();

			long result = mDataProvider.addIrrigateAction(Global.userId,
					Global.plotId, mHours, mMethod, getDate(mDay, mMonth), 0);

			return result != -1;
		}
		return false;
	}

}