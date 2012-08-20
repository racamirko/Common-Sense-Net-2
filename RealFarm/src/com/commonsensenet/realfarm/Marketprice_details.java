package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
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
	private List<AggregateItem> mMarketItems;
	private ListView mMarketListView;
	private MarketItemAdapter mMarketItemAdapter;
	private Resource topSelectorData;
	private Resource daysSelectorData;
	private LayoutInflater mLayoutInflater;

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

		mLayoutInflater = getLayoutInflater();

		// default seed/crop type id
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionActionTypeId, mDataProvider, Global.userId);
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
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionActionTypeId, mDataProvider);
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

	public void setList() {		
		int cropTypeId = topSelectorData.getId();
		
		// gets the list of aggregate data.
		mMarketItems = mDataProvider.getMarketData(cropTypeId, - daysSelectorData.getImage1());

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
		
		setTopSelector();
		setDaysSelector();
	}	

	private void setDaysSelector() {
		final TextView selectorText = (TextView) findViewById(R.id.days_selector_label);
		selectorText.setText("last "+daysSelectorData.getShortName());
	}

	private void setTopSelector() {
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
			} else if(v.getId() == R.id.days_selector_row) {
				playAudio(R.raw.problems, true);
			} else if(v.getId() == R.id.selector_days) {
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
						topSelectorData = data.get(position);
					} else if (type == 1) { // days span
						daysSelectorData = data.get(position);
					}
					setList();

					if(actionTypeImage != null) actionTypeImage
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

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// gets the selected view using the position
			AggregateItem selectedItem = mMarketItemAdapter.getItem(position);
			// gets the wrapper to extract data directly from it.
			AggregateItemWrapper selectedItemView = ActionDataFactory
					.getAggregateWrapper(view, mActionActionTypeId);

			// dialog used to request the information
			final Dialog dialog = new Dialog(this);

			// loads the dialog layout
			View layout = mLayoutInflater.inflate(
					R.layout.dialog_aggregate_details, null);

			// adds the event to dismiss the dialog.
			layout.findViewById(R.id.button_back).setOnClickListener(
					new View.OnClickListener() {

						public void onClick(View v) {
							// closes the dialog.
							dialog.dismiss();
						}
					});

			// sets the data of the header using the old view.
			RelativeLayout rl = (RelativeLayout)layout.findViewById(R.id.top_user_info);
			View tmpView = mLayoutInflater.inflate(R.layout.tpl_aggregate_item, null);
			selectedItemView.copyView(selectedItem, tmpView);
			rl.addView(tmpView);

			// gets the data and data adapter.
			final List<UserAggregateItem> list = mDataProvider.getUserMarketData(
					topSelectorData.getId(), selectedItem.getSelector1(), - daysSelectorData.getImage1());
			
			if(list == null || list.size() < 1) playAudio(R.raw.problems, true);

			// gets the ListView from the layout
			ListView userListView = (ListView) layout.findViewById(R.id.list_dialog_aggregate);

			// selectedItem.getSeedTypeId()
			UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
					this, list, mDataProvider);
			// sets the adapter.
			userListView.setAdapter(userAdapter);

			// userListView.setOnItemLongClickListener(mParentReference);

			// disables the title in the dialog
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// sets the view
			dialog.setContentView(layout);
			dialog.setCancelable(true);

			// displays the dialog.
			dialog.show();
			playAudio(R.raw.problems, true);
			
			ImageView helpDetail = (ImageView)layout.findViewById(R.id.aggr_details_img_help);
			LinearLayout dialogAggregateHeader = (LinearLayout)layout.findViewById(R.id.dialog_aggregate_header);
			
			helpDetail.setOnLongClickListener(new View.OnLongClickListener() {
				public boolean onLongClick(View v) {
					playAudio(R.raw.a10, true);
					return false;
				}
			});
			
			dialogAggregateHeader.setOnLongClickListener(new View.OnLongClickListener() {
				public boolean onLongClick(View v) {
					// TODO; say something according to the layout's contents. This is the top header of the dialog to call people in the aggregates
					playAudio(R.raw.problems, true);
					return false;
				}
			});
			
			userListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					// TODO: calling Mr ...
					playAudio(R.raw.problems, true);
					UserAggregateItem choice = list.get(position);
					String phoneNumber = choice.getTel();
					
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phoneNumber));
					startActivity(intent);
				}
			});

			userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					
					// TODO: audio
					UserAggregateItem choice = list.get(position);
					playAudio(R.raw.problems, true);
					
					return true;
				}
			});

		}

}
