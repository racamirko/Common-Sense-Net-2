package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class Marketprice_details extends AggregateMarketActivity implements OnLongClickListener {

	private final Context context = this;
	private int min = 0;
	private int max = 0;

	public void onBackPressed() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();

		// eliminates the listener.
		mDataProvider.setWeatherForecastDataChangeListener(null);
		
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, "back");
		ApplicationTracker.getInstance().flush();

		SoundQueue sq = SoundQueue.getInstance();
		sq.stop();
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.marketdetails, context);
		currentAction = RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET;

		min = mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN);
		max = mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX);
		
		TextView tw = (TextView)findViewById(R.id.max_price);
		tw.setText(String.valueOf(max));
		tw = (TextView)findViewById(R.id.min_price);
		tw.setText(String.valueOf(min));

		// default seed/crop type id
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId, mDataProvider, Global.userId);
		daysSelectorData = mDataProvider.getResources(RealFarmDatabase.RESOURCE_TYPE_DAYS_SPAN).get(0); // default: 1 day 
		setList();

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View crop = findViewById(R.id.aggr_crop);
		final View daySelectorRow = findViewById(R.id.days_selector_row);
		final View marketInfo = findViewById(R.id.market_info);
		final View daysSelector = findViewById(R.id.selector_days);
		Button back = (Button) findViewById(R.id.button_back);

		daySelectorRow.setOnLongClickListener(this);
		daysSelector.setOnLongClickListener(this);
		marketInfo.setOnLongClickListener(this);
		home.setOnLongClickListener(this);
		back.setOnLongClickListener(this);
		help.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(Marketprice_details.this,
						Homescreen.class));

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();
			}
		});
		
		marketInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();
			}
		});
		
		daySelectorRow.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();
			}
		});
		
		help.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// TODO AUDIO: help audio
				playAudio(R.raw.help);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,img_1, 2);
			}
		});

		daysSelector.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
				ApplicationTracker.getInstance().flush();
				
				List<Resource> data = mDataProvider.getResources(RealFarmDatabase.RESOURCE_TYPE_DAYS_SPAN);
				displayDialog(v, data, "Select the time span", R.raw.problems, null, 1);
			}
		});

	}

	protected void cancelAudio() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();
	}
	
	// TODO AUDIO: check the right audio
	public boolean onLongClick(View v) {
		
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(),getResources().getResourceEntryName(v.getId()));
		ApplicationTracker.getInstance().flush();

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_crop) {
			int crop = topSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId).getAudio();
			// TODO AUDIO: Say something: action + crop
			System.out.println(action +" "+ crop);
			
			playAudio(topSelectorData.getAudio(), true);
		} else if (v.getId() == R.id.market_info) {
			// TODO AUDIO: Say something: "Market Challekere, today prices go from " + say(min) + " to " + say(max) + " rupees" 
			System.out.println("Market Challekere, today prices go from " + min + " to " + max + " rupees");
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.selector_days) {
			playAudio(daysSelectorData.getAudio(), true);
		} else if (v.getId() == R.id.days_selector_row) {
			playAudio(R.raw.problems, true);
		}
		return true;
	}
	
	protected void makeAudioAggregateMarketItem(AggregateItem item, boolean header) {
		// TODO AUDIO IMPORTANT: if header == true, don't say the outro (Touch briefly once to see...)
		
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a2, true);
		
		int variety = topSelectorData.getAudio();
		int days = daysSelectorData.getAudio();
		int number = item.getNews();
		long min = item.getSelector3();
		long max = item.getSelector2();
		int kg = mDataProvider.getResourceImageById(item.getSelector1(), RealFarmDatabase.TABLE_NAME_UNIT, RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO);
		
		// TODO  AUDIO: Say something here: say(number) + " people have sold bags of " + kg + " of " + variety + " at prices between " + say(min) + " and " + say(max) + " rupees per quintal " + " these last " + days + " days. Touch briefly to view the farmers and their individual prices"
		// TODO  AUDIO: Test each of the int. if == -1, don't say anything
		System.out.println(number + " people have sold bags of " + kg + " of " + variety + " at prices between " + min + " and " + max + " rupees per quintal " + " these last " + days + " days. Touch briefly to view the farmers and their individual prices");
		
	}
}
