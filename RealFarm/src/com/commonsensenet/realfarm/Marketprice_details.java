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
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class Marketprice_details extends AggregateMarketActivity implements OnLongClickListener {

	private final Context context = this;

	public void onBackPressed() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();

		// eliminates the listener.
		mDataProvider.setWeatherForecastDataChangeListener(null);

		SoundQueue sq = SoundQueue.getInstance();
		sq.stop();
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.marketdetails, context);
		currentAction = RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET;

		TextView tw = (TextView)findViewById(R.id.max_price);
		tw.setText(String.valueOf(mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX)));


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
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "home");
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "back");

			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,img_1, 2);
			}
		});

		daysSelector.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

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

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		playAudio(R.raw.problems, true);
		// TODO: Add the audio. 

		switch (mActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			// retrieve what you need and say something.
			// int nbUsers = aggregates.get(position).getUserCount();
			break;

		}
		return true;
	}
}
