package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public abstract class AggregateMarketActivity extends TopSelectorActivity
		implements OnItemClickListener, OnLongClickListener,
		OnItemLongClickListener {

	protected Resource mDaysSelectorData;
	protected List<AggregateItem> mItems;
	protected ListView mListView;
	protected AggregateItemAdapter mItemAdapter;
	protected int mActionTypeId = RealFarmDatabase.ACTION_TYPE_SELL_ID;
	protected int mCurrentAction = 0;
	protected boolean mHeader;

	public void setList() {

		// sets the selector information.
		setTopSelector(mActionTypeId);

		int cropSeedTypeId = mTopSelectorData.getId();

		// gets the data to show.
		mItems = ActionDataFactory.getData(mCurrentAction, mDataProvider,
				mActionTypeId, cropSeedTypeId, mDaysSelectorData);

		// plays a sound to indicate that no items are available.
		if (mItems == null || mItems.size() < 1) {
			playAudio(R.raw.no_info_seeds);
		}

		// creates the data adapter.
		mItemAdapter = ActionDataFactory.getAdapter(mCurrentAction,
				mActionTypeId, this, mItems, mDataProvider, mDataProvider);
		mListView = (ListView) findViewById(R.id.list_market_aggregates);
		mListView.setItemsCanFocus(false);
		mListView.setAdapter(mItemAdapter);

		// sets the listener
		mListView.setOnItemClickListener(this);
		// sets the listener for the sound
		mListView.setOnItemLongClickListener(this);

		if (mCurrentAction == TopSelectorActivity.LIST_WITH_TOP_SELECTOR_TYPE_MARKET) {
			setDaysSelector();
		}

	}

	public void setList(int type, Resource choice) {

		if (type == 2) { // change the query
			mTopSelectorData = choice;
		} else if (type == 1) { // days span
			mDaysSelectorData = choice;
		}

		setList();
	}

	private void setDaysSelector() {
		final TextView selectorText = (TextView) findViewById(R.id.days_selector_label);
		selectorText.setText("last " + mDaysSelectorData.getShortName());
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(), position);

		// gets the selected view using the position
		final AggregateItem selectedItem = mItemAdapter.getItem(position);
		// gets the wrapper to extract data directly from it.
		// AggregateItemWrapper selectedItemView = ActionDataFactory
		// .getAggregateWrapper(view, mActionTypeId);

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
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, Global.userId, getLogTag(),
								"back");
						dialog.dismiss();
						stopAudio();
					}
				});

		// sets the data of the header using the old view.
		RelativeLayout rl = (RelativeLayout) layout
				.findViewById(R.id.top_user_info);
		View tmpView = mLayoutInflater.inflate(R.layout.tpl_aggregate_item,
				null);
		copyView(selectedItem, tmpView);
		rl.addView(tmpView);

		// gets the data and data adapter.
		final List<UserAggregateItem> list = ActionDataFactory.getUserList(
				mCurrentAction, mTopSelectorData.getId(), selectedItem,
				mDaysSelectorData, mDataProvider);

		if (list == null || list.size() < 1) {
			playAudio(R.raw.no_info_farmers);
		}

		// gets the ListView from the layout
		ListView userListView = (ListView) layout
				.findViewById(R.id.list_dialog_aggregate);

		// selectedItem.getSeedTypeId()

		UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
				this, list);
		// sets the adapter.
		userListView.setAdapter(userAdapter);

		// userListView.setOnItemLongClickListener(mParentReference);

		// disables the title in the dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// sets the view
		dialog.setContentView(layout);
		dialog.setCancelable(true);

		// displays the dialog & describes the top bar with audio
		dialog.show();
		makeAudioUserTopBar(false);

		ImageView helpDetail = (ImageView) layout
				.findViewById(R.id.aggr_details_img_help);
		View dialogAggregateHeader = layout
				.findViewById(R.id.dialog_aggregate_header);

		helpDetail.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(), "dialog help");

				playAudio(R.raw.help, true);
				return true;
			}
		});

		helpDetail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "dialog help");

				playAudio(R.raw.help, true);
			}
		});

		dialogAggregateHeader
				.setOnLongClickListener(new View.OnLongClickListener() {
					public boolean onLongClick(View v) {
						// Say something according to the layout's contents.
						// This is the top header of the dialog to call people
						// in the aggregates
						// makeAudioUserTopBar(true);
						ApplicationTracker.getInstance().logEvent(
								EventType.LONG_CLICK, Global.userId,
								getLogTag(), "dialog header");
						makeAudioAggregateMarketItem(selectedItem, true);
						mHeader = true;
						return true;
					}
				});

		dialogAggregateHeader.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "dialog header");
			}
		});

		userListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (list.get(position).getId() == Global.userId) {
					Toast.makeText(getBaseContext(),
							"You cannot call yourself", Toast.LENGTH_SHORT)
							.show();

					playAudio(R.raw.you_cant_call_yourself);
					return;
				}

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						"dialog call " + list.get(position).getName());

				// TODO: calling Mr ...
				UserAggregateItem choice = list.get(position);
				makeAudioCallUser(choice);

				String phoneNumber = choice.getTel();
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + phoneNumber));
				startActivity(intent);
			}
		});

		userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(),
						"dialog call " + list.get(position).getName());

				// TODO: audio
				UserAggregateItem choice = list.get(position);
				makeAudioUserItem(choice);

				return true;
			}
		});

	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(), position);
		makeAudioAggregateMarketItem(mItemAdapter.getItem(position), false);
		mHeader = false;
		return true;
	}

	public void copyView(AggregateItem aggregate, View destination) {
		destination.setBackgroundColor(Color.LTGRAY);

		TextView tw = (TextView) destination.findViewById(R.id.label_news);
		tw.setText(aggregate.getNewsText());
		tw.setBackgroundColor(Color.parseColor("#FFFFCC"));

		tw = (TextView) destination.findViewById(R.id.label_left);
		tw.setText(aggregate.getLeftText());

		RelativeLayout rl = (RelativeLayout) destination
				.findViewById(R.id.relative_layout_left);
		if (aggregate.getLeftBackground() != -1)
			rl.setBackgroundResource(aggregate.getLeftBackground());
		else
			tw.setTextColor(Color.BLACK);

		tw = (TextView) destination.findViewById(R.id.label_center);
		tw.setText(aggregate.getCenterText());

		rl = (RelativeLayout) destination
				.findViewById(R.id.relative_layout_center);
		if (aggregate.getCenterBackground() != -1)
			rl.setBackgroundResource(aggregate.getCenterBackground());
		else {
			tw.setTextColor(Color.BLACK);
			// hack
			if (!aggregate.getCenterText().equals("")) {
				rl.getLayoutParams().width = 200;
				tw.setTextSize(20);
			} else {
				rl.getLayoutParams().width = 20;
				rl = (RelativeLayout) destination
						.findViewById(R.id.relative_layout_right);
				rl.getLayoutParams().width = 300;
			}
		}

		ImageView iw = (ImageView) destination.findViewById(R.id.image_center);
		if (aggregate.getCenterImage() != -1) {
			iw.setImageResource(aggregate.getCenterImage());
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}

		tw = (TextView) destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());

	}

	private void makeAudioUserTopBar(boolean canHear) {

		if (!canHear) {
			// playAudio(R.raw.cant_hear_when_turned_off, true);
		}
	}

	private void makeAudioCallUser(UserAggregateItem user) {

		int userName = user.getAudioName();

		if (userName != -1) {
			addToSoundQueue(userName);
			addToSoundQueue(R.raw.calling);
			playSound();
		}

	}

	protected void makeAudioUserItem(UserAggregateItem user) {

		// Intro
		String date = user.getDate();
		int userName = user.getAudioName();
		int userLocation = user.getAudioLocation();
		// int action =
		// mDataProvider.getActionTypeById(mActionTypeId).getAudio();

		date = date + "2012";

		addToSoundQueue(userName);
		addToSoundQueue(userLocation);
		add_action_aggregate(7); // says "in"

		// Mid
		int variety = mTopSelectorData.getAudio();
		switch (mActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
			float numberOffertilizer = Float.parseFloat(user.getLeftText());
			int unit = user.getAudioRightImage();

			if ((variety != -1) & (unit != -1) & (numberOffertilizer != -1)) {
				// says "every acre"
				add_action_aggregate(8);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				playFloat(numberOffertilizer);
				addToSoundQueue(R.raw.so_much);
				addToSoundQueue(unit);
				addToSoundQueue(R.raw.fertilizer_put);

			}

			break;

		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			float amount = Float.parseFloat(user.getLeftText());

			if ((amount != -1) & (variety != -1)) {

				addToSoundQueue(R.raw.every_acre);
				playFloat(amount);
				addToSoundQueue(R.raw.quintal_so_much);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.done_harvest);

			}
			break;

		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			int hours = Integer.parseInt(user.getLeftText());
			int irrigation = user.getAudioLeftImage();

			if ((hours != -1) & (irrigation != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				playInteger(hours);
				addToSoundQueue(R.raw.hourly_duration);
				addToSoundQueue(irrigation);
				addToSoundQueue(R.raw.irrigation_done);
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			int problem = user.getAudioLeftImage();

			if ((problem != -1) & (variety != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(problem);
				addToSoundQueue(R.raw.there_reported);

			}
			break;

		case RealFarmDatabase.ACTION_TYPE_SELL_ID:
			int kg = user.getAudioLeftImage();
			int price = Integer.parseInt(user.getLeftText());

			if ((variety != -1) & (price != -1) & (kg != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.every_quintals);
				addToSoundQueue(kg);
				addToSoundQueue(R.raw.ss);
				playInteger(price);
				addToSoundQueue(R.raw.to_rupees_sold);
			}
			break;

		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			float numberOfSerus = Float.parseFloat(user.getLeftText());
			int treatment = user.getAudioCenterImage();
			int intercrop = user.getAudioLeftImage();

			if ((treatment != -1) & (intercrop != -1) & (numberOfSerus != -1)) {
				add_action_aggregate(8); // says "every acre"
				playFloat(numberOfSerus);
				add_action_aggregate(9); // says "seru"
				addToSoundQueue(variety);
				addToSoundQueue(treatment);
				addToSoundQueue(R.raw.and);
				addToSoundQueue(intercrop);
				add_action_aggregate(10); // says "sowing done"
			}
			break;

		case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
			float amountSprayed = Float.parseFloat(user.getLeftText());
			int uni = user.getAudioRightImage();
			int pesticide = user.getAudioLeftImage();
			int pb = user.getAudioCenterImage();

			if ((uni != -1) & (pesticide != -1) & (variety != -1) & (pb != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(R.raw.infected);
				addToSoundQueue(pb);
				addToSoundQueue(R.raw.to_solve_every_acre);
				playFloat(amountSprayed);
				addToSoundQueue(uni);
				addToSoundQueue(R.raw.quantity_s);
				addToSoundQueue(pesticide);
				addToSoundQueue(R.raw.its_sprayed);
			}
			break;

		default:
			break;
		}

		// Outro
		if (mHeader == false) {
			if (userName == -1) {
				// says "farmer" if username is not there.
				addToSoundQueue(R.raw.farmers);
			} else {
				addToSoundQueue(userName);
			}
			// says "to call touch here briefly"
			addToSoundQueue(R.raw.to_call_click);

		}
		playSound();

	}

	protected abstract void makeAudioAggregateMarketItem(AggregateItem item,
			boolean header);

}
