package com.commonsensenet.realfarm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.AdviceAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class AdviceActivity extends HelpEnabledActivity implements
		OnChildClickListener, OnGroupClickListener, OnItemLongClickListener {

	/** Adapter used to manage the advices. */
	private AdviceAdapter mAdviceAdapter;
	/** Inflater used to add custom layouts. */
	private LayoutInflater mLayoutInflater;
	/** ListView used to display the situations. */
	private ExpandableListView mListView;
	/** List of Situations for the user. */
	private ArrayList<AdviceSituationItem> mSituationItems;

	public void copyView(AggregateItem aggregate, View destination) {
		destination.setBackgroundColor(Color.LTGRAY);

		TextView tw = (TextView) destination.findViewById(R.id.label_news);
		tw.setText(aggregate.getNewsText());
		tw.setBackgroundColor(Color.parseColor("#FFFFCC"));

		tw = (TextView) destination.findViewById(R.id.label_left);
		tw.setText(aggregate.getLeftText());

		RelativeLayout rl = (RelativeLayout) destination
				.findViewById(R.id.relative_layout_left);
		if (aggregate.getLeftBackground() != -1) {
			rl.setBackgroundResource(aggregate.getLeftBackground());
		} else {
			tw.setTextColor(Color.BLACK);
		}

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
		if (aggregate.getCenterImage() != -1)
			iw.setImageResource(aggregate.getCenterImage());

		tw = (TextView) destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());

		// iw = (ImageView) destination.findViewById(R.id.image_left);
		// if (aggregate.getLeftImage() != -1) {
		// iw.setImageResource(aggregate.getLeftImage());
		// }

		iw = (ImageView) destination.findViewById(R.id.image_left_bottom);
		if (aggregate.getLeftBottomImage() != -1)
			iw.setImageResource(aggregate.getLeftBottomImage());
	}

	private void expandCurrentLists(
			ArrayList<AdviceSituationItem> situationItems) {
		for (int i = 0; i < mAdviceAdapter.getGroupCount(); i++) {
			// if (situationItems.get(i).getValidDate() >= new Date().getTime())
			mListView.expandGroup(i);
		}
	}

	private AggregateItem getSelectedItem(AdviceSituationItem situationItem,
			AdviceSolutionItem solutionItem) {

		int actionTypeId = solutionItem.getAdvicePiece().getSuggestedActionId();

		// spray is the default action type.
		if (actionTypeId == -1) {
			actionTypeId = RealFarmDatabase.ACTION_TYPE_SPRAY_ID;
		}

		// creates a new Aggregate Item.
		AggregateItem selectedItem = new AggregateItem(actionTypeId);

		// sets the values.
		selectedItem.setLeftText(situationItem.getProblem().getShortName());
		// selectedItem.setLeftImage(situationItem.getProblem().getImage1());
		selectedItem.setCenterImage(solutionItem.getResource().getImage1());
		selectedItem.setRightText(solutionItem.getResource().getShortName());
		selectedItem.setSelector1(situationItem.getProblem().getId());
		selectedItem.setSelector2(solutionItem.getResource().getId());
		return selectedItem;
	}

	private void makeAudioCallUser(UserAggregateItem user) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a20);

		int userName = user.getAudioName();
		// TODO AUDIO: "Calling Mr" + user.getAudio(). When the sound is turned
		// off, nothing is heard
		// TODO AUDIO: Test the int. if == -1, don't say anything
		System.out.println(userName);
	}

	private void makeAudioSituation(AdviceSituationItem situationItem) {

		// gets the related variables.
		String audioSequence = situationItem.getAdvice().getAudioSequence();
		int severity = situationItem.getRecommendation().getSeverity();
		String dataCollectionDate = situationItem.getRecommendation()
				.getDataCollectionDate();

		// gets the plots listed by the user.
		List<Plot> plotList = mDataProvider.getPlotsByUserId(Global.userId);

		// finds out the plot number.
		int plotNumber = 0;
		for (int x = 0; x < plotList.size(); x++) {
			if (plotList.get(x).getId() == situationItem.getPlotId()) {
				plotNumber = x;
				break;
			}
		}

		try {
			Date date = RealFarmProvider.sDateFormat.parse(dataCollectionDate);
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);

			cal.get(Calendar.MONTH); // starts with 0
			cal.get(Calendar.YEAR);
			cal.get(Calendar.DAY_OF_MONTH); // starts with 1

			// separates the sequence using the , as separator.
			String[] audioPieces = audioSequence.split(",");

			// plays the advice audio.
			playInteger(cal.get(Calendar.MONTH) + 1);
			playInteger(cal.get(Calendar.MONTH));
			playInteger(cal.get(Calendar.YEAR));
			addToSoundQueue(Integer.valueOf(audioPieces[0]));
			playInteger(plotNumber);
			addToSoundQueue(Integer.valueOf(audioPieces[1]));
			playInteger(severity);
			addToSoundQueue(Integer.valueOf(audioPieces[2]));

			playSound();

		} catch (ParseException e) {
		}
	}

	private void makeAudioSolution(AdviceSolutionItem solutionItem) {

		// int number = solutionItem.getNumber();
		// int action = solutionItem.getActionAudio();
		// long pesticide = solutionItem.getPesticideAudio();
		// int comment = solutionItem.getAudio();
		// int didIt = solutionItem.getDidIt();
		// int plan = solutionItem.getLikes();
		int audio = solutionItem.getAdvicePiece().getAudio();

		addToSoundQueue(audio);
		playSound();
	}

	protected void makeAudioUserItem(UserAggregateItem user) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a10, true);

		// Intro
		String date = user.getDate();
		int userName = user.getAudioName();
		int userLocation = user.getAudioLocation();

		// TODO AUDIO: Say something here: "On" + say(date) + userName + "from"
		// + userLocation + action
		// TODO AUDIO: Test each of the int. if == -1, don't say anything
		System.out.println("On " + date + userName + " from " + userLocation);

		// Mid
		// int uni = user.getAudioRightImage();
		// int pesticide = user.getAudioLeftImage();
		// int pb = user.getAudioCenterImage();
		// TODO AUDIO: Say something here: uni + "per acre of" + pesticide +
		// "against" + pb
		// TODO AUDIO: Test each of the int. if == -1, don't say anything

		// Outro
		// TODO AUDIO: "To call " + userName + " touch here briefly"
		System.out.println("To call " + userName + " touch here briefly");
	}

	private void makeAudioUserTopBar(boolean canHear) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a30, canHear);

		// TODO AUDIO: if(!canHear) then you can't hear the audio when the sound
		// is disabled
	}

	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// Object e = (Object)adapter.getChild(groupPosition, childPosition);
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()),
				"gr:" + groupPosition + " pos:" + childPosition);

		AdviceSituationItem situationItem = mSituationItems.get(groupPosition);
		AdviceSolutionItem solutionItem = situationItem.getItems().get(
				childPosition);
		AggregateItem selectedItem = getSelectedItem(situationItem,
				solutionItem);
		final List<UserAggregateItem> list = ActionDataFactory
				.getUserAggregateData(selectedItem, mDataProvider);

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

		if (list == null || list.size() < 1)
			playAudio(R.raw.no_info_farmers, true);

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

		// displays the dialog & describes the topbar with audio
		dialog.show();
		makeAudioUserTopBar(false);

		ImageView helpDetail = (ImageView) layout
				.findViewById(R.id.aggr_details_img_help);
		LinearLayout dialogAggregateHeader = (LinearLayout) layout
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
						makeAudioUserTopBar(true);
						ApplicationTracker.getInstance().logEvent(
								EventType.LONG_CLICK, Global.userId,
								getLogTag(), "dialog header");
						// makeAudioAggregateMarketItem(selectedItem, true);
						return true;
					}
				});

		dialogAggregateHeader.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
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

		return false;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.act_advice);

		// layout inflater used to add new elements.
		mLayoutInflater = getLayoutInflater();
		// gets the list from the layout.
		mListView = (ExpandableListView) findViewById(R.id.exp_list);
		// fetches the data from the database.
		mSituationItems = mDataProvider.getAdviceData(Global.userId);

		// plays a sound indicating that no items where found.
		if (mSituationItems == null || mSituationItems.size() == 0) {
			playAudio(R.raw.no_information);
		}

		// creates the adapter
		mAdviceAdapter = new AdviceAdapter(AdviceActivity.this,
				mSituationItems, this);
		mListView.setAdapter(mAdviceAdapter);

		expandCurrentLists(mSituationItems);

		// adds the events.
		mListView.setOnChildClickListener(this);
		mListView.setOnGroupClickListener(this);
		mListView.setOnItemLongClickListener(this);
	}

	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		return false;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		stopAudio();

		long data = mListView.getExpandableListPosition(position);
		int type = ExpandableListView.getPackedPositionType(data);
		AdviceSituationItem situationItem = mSituationItems
				.get(ExpandableListView.getPackedPositionGroup(data));

		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			AdviceSolutionItem solutionItem = situationItem.getItems().get(
					ExpandableListView.getPackedPositionChild(data));
			if (situationItem.getRecommendation().getIsUnread() == 0)
				makeAudioSituation(situationItem);
			makeAudioSolution(solutionItem);
		} else {
			makeAudioSituation(situationItem);
		}

		if (situationItem.getRecommendation().getIsUnread() == 0) {
			mDataProvider.setRecommendationUnread(situationItem
					.getRecommendation().getIsUnread(), 0);
			mSituationItems
					.get(ExpandableListView.getPackedPositionGroup(data))
					.getRecommendation().setIsUnread(1);
			mAdviceAdapter.setGroups(mSituationItems);
			mAdviceAdapter.notifyDataSetChanged();
		}

		ApplicationTracker.getInstance().logEvent(
				EventType.LONG_CLICK,
				Global.userId,
				getLogTag(),
				getResources().getResourceEntryName(view.getId()),
				"gr:" + ExpandableListView.getPackedPositionGroup(data)
						+ " pos:"
						+ ExpandableListView.getPackedPositionChild(data));

		return true;
	}

	public void onLikeClick(int groupPosition, int childPosition) {
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId,
				"like gr:" + groupPosition + " pos:" + childPosition);

		AdviceSituationItem situationItem = mSituationItems.get(groupPosition);
		AdviceSolutionItem solutionItem = situationItem.getItems().get(
				childPosition);

		// TODO: delete system
		if (!mDataProvider.hasLiked(solutionItem.getAdvicePiece().getId(),
				Global.userId)) {
			mDataProvider.addPlanAction(Global.userId, situationItem
					.getPlotId(), solutionItem.getAdvicePiece().getId(),
					Global.isAdmin);
			mSituationItems.get(groupPosition).getItems().get(childPosition)
					.setHasLiked(true);
			mSituationItems.get(groupPosition).getItems().get(childPosition)
					.setLikes(solutionItem.getLikes() + 1);
			mAdviceAdapter.setGroups(mSituationItems);
			mAdviceAdapter.notifyDataSetChanged();
		}/*
		 * else { mDataProvider.deletePlanAction(Global.userId,
		 * situationItem.getPlotId(), solutionItem.getId());
		 * situationItems.get(groupPosition
		 * ).getItems().get(childPosition).setHasLiked(false);
		 * situationItems.get
		 * (groupPosition).getItems().get(childPosition).setLikes
		 * (solutionItem.getLikes()-1);
		 * 
		 * } adapter.setGroups(situationItems); adapter.notifyDataSetChanged();
		 */

	}

	public void onLikeLongClick(int groupPosition, int childPosition) {
		// TODO AUDIO: explain what the like button does
		playAudio(R.raw.problems, true);
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId,
				"like gr:" + groupPosition + " pos:" + childPosition);
	}
}
