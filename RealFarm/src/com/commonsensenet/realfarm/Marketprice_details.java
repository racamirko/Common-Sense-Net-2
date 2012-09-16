package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class Marketprice_details extends AggregateMarketActivity implements
		OnLongClickListener {

	private final Context context = this;
	private int min = 0;
	private int max = 0;

	public void onBackPressed() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();

		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, "back");
		ApplicationTracker.getInstance().flushAll();

		SoundQueue sq = SoundQueue.getInstance();
		sq.stop();
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.marketdetails, context);
		mCurrentAction = TopSelectorActivity.LIST_WITH_TOP_SELECTOR_TYPE_MARKET;

		min = mDataProvider
				.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN);
		max = mDataProvider
				.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX);

		TextView tw = (TextView) findViewById(R.id.max_price);
		tw.setText(String.valueOf(max));
		tw = (TextView) findViewById(R.id.min_price);
		tw.setText(String.valueOf(min));

		// default seed/crop type id
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId,
				mDataProvider, Global.userId);
		// default 1 day
		mDaysSelectorData = mDataProvider.getResources(
				RealFarmDatabase.RESOURCE_TYPE_DAYS_SPAN).get(0);
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
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		marketInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		daySelectorRow.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		help.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			
				playAudio(R.raw.mp_help);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(
						mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.select_the_variety,
						img_1, 2);
			}
		});

		daysSelector.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_DAYS_SPAN);
				displayDialog(v, data, "Select the time span", R.raw.problems,
						null, 1);
			}
		});

	}

	protected void cancelAudio() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();
	}

	
	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.homepage, true);
		} else if (v.getId() == R.id.aggr_crop) {
			int crop = topSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId)
					.getAudio();
		

			playAudio(topSelectorData.getAudio(), true);
		} else if (v.getId() == R.id.market_info) {
			addToSoundQueue(R.raw.chal_max_price);
			play_integer(max);
			addToSoundQueue(R.raw.rupees_every_quintal);
			
			addToSoundQueue(R.raw.chal_min_price);
			play_integer(min);
			addToSoundQueue(R.raw.rupees_every_quintal);
			playSound();
			
			
			
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.mp_help, true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.back_button, true);
		} else if (v.getId() == R.id.selector_days) {
			playAudio(mDaysSelectorData.getAudio(), true);
		} else if (v.getId() == R.id.days_selector_row) {
			playAudio(R.raw.select_time_span, true);
		}
		return true;
	}

	protected void makeAudioAggregateMarketItem(AggregateItem item,
			boolean header) {
		
		
		int variety = topSelectorData.getAudio();
		int days = mDaysSelectorData.getAudio();
		int number = item.getNews();
		long min = item.getSelector3();
		long max = item.getSelector2();
		int kg = mDataProvider.getResourceImageById(item.getSelector1(),
				RealFarmDatabase.TABLE_NAME_UNIT,
				RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO);

		
		addToSoundQueue(R.raw.last);
		addToSoundQueue(days);
		addToSoundQueue(R.raw.days_paid_price_touch_here);
		playSound();
		

	}
}
