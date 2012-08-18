package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.MarketItem;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.MarketItemAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class Marketprice_details extends HelpEnabledActivityOld implements
OnItemClickListener, OnLongClickListener, OnItemLongClickListener {

	private final Context context = this;
	private RealFarmProvider mDataProvider;
	private int mActionActionTypeId = RealFarmDatabase.ACTION_TYPE_SELL_ID;
	private final Marketprice_details mParentReference = this;
	private List<MarketItem> mMarketItems;
	private ListView mMarketListView;
	private MarketItemAdapter mMarketItemAdapter;
	// TODO: set second selector
	private int daySpan = 14;

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

		super.onCreate(savedInstanceState);
		mDataProvider = RealFarmProvider.getInstance(context);
		setContentView(R.layout.marketdetails);
		
		TextView tw = (TextView)findViewById(R.id.max_price);
		tw.setText(String.valueOf(mDataProvider.getLimitPrice(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX)));


		// default seed/crop type id
		// TODO: if user doesn't have a plot
		Resource topSelectorData = ActionDataFactory.getTopSelectorData(mActionActionTypeId, mDataProvider, Global.userId);
		setList(topSelectorData);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View crop = findViewById(R.id.aggr_crop);
		final View marketInfo = findViewById(R.id.market_info);
		final View daysSelector = findViewById(R.id.days_selector);
		Button back = (Button) findViewById(R.id.button_back);

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
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,img_1, 2);
			}
		});

		/*
		 * // home_btn_gn_good_mp
		 * 
		 * final Button GN_good; final Button GN_medium; final Button GN_poor;
		 * 
		 * final Button Today_gnut; // final Button Today_castor;
		 * 
		 * ImageButton home; ImageButton help;
		 * 
		 * GN_good = (Button) findViewById(R.id.home_btn_gn_good_mp); GN_medium
		 * = (Button) findViewById(R.id.home_btn_gn_medium_mp); GN_poor =
		 * (Button) findViewById(R.id.home_btn_gn_poor_mp); Today_gnut =
		 * (Button) findViewById(R.id.home_btn_mp_1);// btn // Today_castor =
		 * (Button) findViewById(R.id.home_btn_mp_2);
		 * 
		 * home = (ImageButton) findViewById(R.id.aggr_img_home1); help =
		 * (ImageButton) findViewById(R.id.aggr_img_help);
		 * 
		 * GN_good.setOnLongClickListener(this); // Integration
		 * GN_medium.setOnLongClickListener(this);
		 * GN_poor.setOnLongClickListener(this);
		 * Today_gnut.setOnLongClickListener(this); //
		 * Today_castor.setOnLongClickListener(this);
		 * 
		 * help.setOnLongClickListener(this);
		 * 
		 * GN_good.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * } });
		 * 
		 * GN_medium.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * } });
		 * 
		 * GN_poor.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * } });
		 * 
		 * TextView text_1; // TextView text_2;
		 * 
		 * text_1 = (TextView) findViewById(R.id.mp_text1); // text_2 =
		 * (TextView) findViewById(R.id.mp_text2);
		 * 
		 * mpvalue = 4800; text_1.setText(mpvalue + unit); //
		 * text_4.setText(wfvalue1 + unit);
		 * 
		 * home.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { Intent adminintent = new
		 * Intent(Marketprice_details.this, Homescreen.class);
		 * 
		 * startActivity(adminintent); Marketprice_details.this.finish();
		 * 
		 * } });
		 * 
		 * }
		 * 
		 * public void onDataChanged(int WF_Size, String WF_Date, int WF_Value,
		 * String WF_Type, String WF_Date1, int WF_Value1, String WF_Type1, int
		 * WF_adminflag) {
		 * 
		 * System.out.println("WF details updating"); text_1 = (TextView)
		 * findViewById(R.id.wf_text1); text_2 = (TextView)
		 * findViewById(R.id.wf_text2); text_4 = (TextView)
		 * findViewById(R.id.wf_text4); text_5 = (TextView)
		 * findViewById(R.id.wf_text5);
		 * 
		 * img_1 = (ImageView) findViewById(R.id.wfimg_1); img_2 = (ImageView)
		 * findViewById(R.id.wfimg_2);
		 * 
		 * arr[0] = R.drawable.sunny; arr[1] = R.drawable.rain;
		 * 
		 * wfvalue = WF_Value; final String wftype = WF_Type; final int wfvalue1
		 * = WF_Value1; final String wftype1 = WF_Type1;
		 * 
		 * System.out.println("changing layout of WF"); //
		 * getWindow().setTitle("Weather Forecast"); // //
		 * setBackgroundDrawableResource(arr[0]);
		 * 
		 * // based on Type change the img1, img2
		 * 
		 * img_1.setImageResource(R.drawable.sunny);
		 * img_2.setImageResource(R.drawable.rain);
		 * 
		 * // change the temperature values text_1.setText(wfvalue + unit);
		 * text_4.setText(wfvalue1 + unit);
		 * 
		 * // Change the weather forcast msg
		 * 
		 * text_2.setText(wftype); text_5.setText(wftype1);
		 * 
		 * System.out.println("WF details finished updating");
		 * 
		 * }
		 * 
		 * @Override public boolean onLongClick(View v) { // latest
		 * 
		 * if (v.getId() == R.id.aggr_img_help) { // Audio integration
		 * 
		 * playAudio(R.raw.help);
		 * 
		 * }
		 * 
		 * if (v.getId() == R.id.home_btn_mp_1) {
		 * 
		 * System.out.println(wfvalue);
		 * 
		 * playAudio(111); // "111" corresponds to ckpura
		 * 
		 * }
		 * 
		 * if (v.getId() == R.id.home_btn_gn_good_mp) {
		 * 
		 * playAudio(11); // "11" corresponds to good
		 * 
		 * } if (v.getId() == R.id.home_btn_gn_medium_mp) {
		 * 
		 * playAudio(12); // "12" corresponds to medium
		 * 
		 * } if (v.getId() == R.id.home_btn_gn_poor_mp) {
		 * 
		 * playAudio(13); // "13" corresponds to poor
		 * 
		 * }
		 * 
		 * return true; }
		 * 
		 * public void playAudio(int Currentvalue) // Audio integration { if
		 * (Global.enableAudio) // checking for audio enable {
		 * 
		 * System.out.println("play audio called");
		 * System.out.println(Currentvalue);
		 * 
		 * SoundQueue sq = SoundQueue.getInstance(); sq.stop(); if
		 * (Global.enableAudio == true) // checking for audio enable { if
		 * (Currentvalue == 111) { // sq.addToQueue(R.raw.seekepura1);
		 * 
		 * // sq.addToQueue(R.raw.a4000);
		 * 
		 * sq.addToQueue(R.raw.todayinpavagdamarket); sq.addToQueue(R.raw.a1);
		 * sq.addToQueue(R.raw.quintals); sq.addToQueue(R.raw.groundnut1);
		 * sq.addToQueue(R.raw.value); sq.addToQueue(R.raw.a4800);
		 * sq.addToQueue(R.raw.rupees2);
		 * 
		 * sq.play(); }
		 * 
		 * if (Currentvalue == 11) // 11 { // SoundQueue sq =
		 * SoundQueue.getInstance(); sq.addToQueue(R.raw.goodqualityprice1);
		 * 
		 * sq.addToQueue(R.raw.a8000); sq.addToQueue(R.raw.rupees2);
		 * 
		 * sq.play(); } if (Currentvalue == 12) // 12 { // SoundQueue sq =
		 * SoundQueue.getInstance(); sq.addToQueue(R.raw.mediumqualityprice1);
		 * 
		 * sq.addToQueue(R.raw.a7000); sq.addToQueue(R.raw.rupees2);
		 * 
		 * sq.play(); }
		 * 
		 * if (Currentvalue == 13) { // SoundQueue sq =
		 * SoundQueue.getInstance(); sq.addToQueue(R.raw.poorqualityprice1); //
		 * 13
		 * 
		 * sq.addToQueue(R.raw.a6000); sq.addToQueue(R.raw.rupees2);
		 * 
		 * sq.play(); }
		 * 
		 * if (Currentvalue == 1000) { // SoundQueue sq =
		 * SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a1000);
		 * 
		 * sq.play(); } if (Currentvalue == 2000) { // SoundQueue sq =
		 * SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a2000); sq.play(); } if (Currentvalue == 3000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a3000); sq.play(); } if (Currentvalue == 4000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a4000); sq.play(); } if (Currentvalue == 5000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a5000); sq.play(); } if (Currentvalue == 6000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a6000); sq.play(); } if (Currentvalue == 7000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a7000); sq.play(); } if (Currentvalue == 8000) {
		 * // SoundQueue sq = SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a8000); sq.play(); }
		 * 
		 * if (Currentvalue == 10000) { // SoundQueue sq =
		 * SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.a10000); sq.play(); }
		 * 
		 * if (Currentvalue == R.raw.help) // added { // SoundQueue sq =
		 * SoundQueue.getInstance();
		 * 
		 * sq.addToQueue(R.raw.help); sq.play(); }
		 * 
		 * // sq.addToQueue(Currentvalue); // sq.play();
		 * 
		 * } } }
		 */
	}
	
	protected void cancelAudio() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();
	}

	public void setList(Resource topSelectorData) {		
		int cropTypeId = topSelectorData.getId();
		
		// gets the list of aggregate data.
		mMarketItems = mDataProvider.getMarketData(cropTypeId, daySpan);

		if(mMarketItems == null || mMarketItems.size() < 1) playAudio(R.raw.problems, true);

		// creates the data adapter.
		mMarketItemAdapter = new MarketItemAdapter(this, mMarketItems, mDataProvider);

		// gets the list from the UI.
		mMarketListView = (ListView) findViewById(R.id.list_market_prices);
		// enables the focus on the items.
		mMarketListView.setItemsCanFocus(false);
		// sets the custom adapter.
		mMarketListView.setAdapter(mMarketItemAdapter);
		// sets the listener
		mMarketListView.setOnItemClickListener(this);
		// sets the listener for the sound
		mMarketListView.setOnItemLongClickListener(this);

		// sets the top selector
		ActionType actionType = mDataProvider.getActionTypeById(mActionActionTypeId);
		final ImageView actionImg = (ImageView) findViewById(R.id.aggr_action);
		actionImg.setImageResource(actionType.getImage1());
		final ImageView selectorImg = (ImageView) findViewById(R.id.aggr_crop_img);
		selectorImg.setBackgroundResource(topSelectorData.getBackgroundImage());
		final TextView selectorText = (TextView) findViewById(R.id.textView1);
		selectorText.setText(topSelectorData.getShortName());
	}

	
	
	// TODO: put audio
		public boolean onLongClick(View v) {

			if (v.getId() == R.id.market_info) {
				playAudio(R.raw.problems, true);
			} else if(v.getId() == R.id.days_selector) {
				playAudio(R.raw.problems, true);
			}
			return true;
		}
	
	private void displayDialog(View v, final List<Resource> data,
			final String title, int entryAudio,
			final ImageView actionTypeImage, final int type) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, data);
		ListView mList = (ListView) dialog.findViewById(R.id.dialog_list);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio);

		// TODO: adapt the audio in the database.
		mList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				Resource choice = data.get(position);

				// TODO: this won't work.
				if (type == 2) { // change the query
					setList(data.get(position));
				} else if (type == 1) { // change the action
					mActionActionTypeId = data.get(position).getId();
				}

				actionTypeImage
						.setBackgroundResource(data.get(position).getBackgroundImage());

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), title, choice.getId());

				/*Toast.makeText(mParentReference, data.get(position).getName(),
						Toast.LENGTH_SHORT).show();*/

				// onClose
				dialog.cancel();
				int iden = choice.getAudio();
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			// TODO: adapt the audio in the database
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int iden = data.get(position).getAudio();
				playAudio(iden);
				return true;
			}
		});
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		playAudio(R.raw.problems, true);
		// TODO: Add the audio. 

		switch (mActionActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			// retrieve what you need and say something.
			// int nbUsers = aggregates.get(position).getUserCount();
			break;

		}
		return true;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

}
