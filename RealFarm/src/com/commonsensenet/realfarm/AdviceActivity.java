package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.Date;
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
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AdviceAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class AdviceActivity extends HelpEnabledActivity implements
		OnChildClickListener, OnGroupClickListener, OnItemLongClickListener {
	private AdviceAdapter adapter;
	private ExpandableListView expandListView;

	private RealFarmProvider mDataProvider;
	protected LayoutInflater mLayoutInflater;
	private ArrayList<AdviceSituationItem> situationItems;

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
		if (aggregate.getCenterImage() != -1)
			iw.setImageResource(aggregate.getCenterImage());

		tw = (TextView) destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());

		iw = (ImageView) destination.findViewById(R.id.image_left);
		if (aggregate.getLeftImage() != -1)
			iw.setImageResource(aggregate.getLeftImage());

		iw = (ImageView) destination.findViewById(R.id.image_left_bottom);
		if (aggregate.getLeftBottomImage() != -1)
			iw.setImageResource(aggregate.getLeftBottomImage());
	}

	private void expandCurrentLists(
			ArrayList<AdviceSituationItem> situationItems) {
		for (int i = 0; i < adapter.getGroupCount(); i++) {
			if (situationItems.get(i).getValidDate() >= new Date().getTime())
				expandListView.expandGroup(i);
		}
	}

	private AggregateItem getSelectedItem(AdviceSituationItem situationItem,
			AdviceSolutionItem solutionItem) {
		int actionTypeId = solutionItem.getSuggestedActionId();
		if (actionTypeId == -1)
			actionTypeId = RealFarmDatabase.ACTION_TYPE_SPRAY_ID;
		AggregateItem selectedItem = new AggregateItem(actionTypeId);

		selectedItem.setCenterText(situationItem.getCropShortName());
		selectedItem.setCenterBackground(situationItem.getCropBackground());
		selectedItem.setLeftText(situationItem.getProblemShortName());
		selectedItem.setLeftImage(situationItem.getProblemImage());
		selectedItem.setCenterImage(solutionItem.getPesticideImage());
		selectedItem.setRightText(solutionItem.getPesticideShortName());

		selectedItem.setSelector1(situationItem.getCropId());
		selectedItem.setSelector2(situationItem.getProblemId());
		selectedItem.setSelector3(solutionItem.getPesticideId());
		return selectedItem;
	}

	public ArrayList<AdviceSituationItem> initLists() {
		ArrayList<AdviceSituationItem> adviceData = mDataProvider
				.getAdviceData(Global.userId);
		if (adviceData == null || adviceData.size() == 0) {
			// TODO AUDIO: No data to present
			playAudio(R.raw.problems);
		}
		return adviceData;
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
		playAudio(R.raw.a10);

		long crop = situationItem.getCropAudio();
		int chance = situationItem.getChance();
		int loss = situationItem.getLoss();
		long problem = situationItem.getProblemAudio();
		int audio = situationItem.getAudio();
		// TODO AUDIO: Say something here:
		// "On the plot where you sowed "+crop+", you have a "+say(chance)+"% chance of losing"+say(loss)+"kg/acre to the problem"+problem+audio
		// TODO AUDIO: Test each of the int. if == -1, don't say anything
	}

	private void makeAudioSolution(AdviceSolutionItem solutionItem) {
		playAudio(R.raw.a20);

		int number = solutionItem.getNumber();
		int action = solutionItem.getActionAudio();
		long pesticide = solutionItem.getPesticideAudio();
		int comment = solutionItem.getAudio();
		int didIt = solutionItem.getDidIt();
		int plan = solutionItem.getLikes();
		int audio = solutionItem.getAudio();

		// TODO AUDIO IMPORTANT:
		// if(solutionItem.getPesticideShortName().equals("")) don't say how
		// many people planned it and don't say that you can plan it
		// TODO AUDIO: Say something here:
		// "Solution"+say(number)+action+pesticide+comment+say(didIt)+"people did it and"+say(plan)+"people plan to do it. To see who did it, tap on the row. If you plan to do it, tap on the right icon."+audio
		// TODO AUDIO: Test each of the int. if == -1, don't say anything

		/*
		 * if((number!=-1)&(action!=-1)&(pesticide!=-1)&(comment!=-1)&(didIt!=-1)
		 * &(plan!=-1)&(audio!=-1)) { playAudio(R.raw.solution, true);
		 * play_integer(number); playAudio(action, true);
		 * playAudio((int)pesticide, true); playAudio(comment, true);
		 * playAudio(didIt, true); playAudio(R.raw.people_did_it, true);
		 * if(solutionItem.getPesticideShortName().equals("")) {
		 * playAudio(R.raw.and, true); playAudio(plan, true);
		 * layAudio(R.raw.you_plan_tap_right_icon, true); playAudio(audio,
		 * true); } playAudio(R.raw.see_who_did_tap_row, true);
		 * 
		 * 
		 * }
		 */
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
		int uni = user.getAudioRightImage();
		int pesticide = user.getAudioLeftImage();
		int pb = user.getAudioCenterImage();
		// TODO AUDIO: Say something here: uni + "per acre of" + pesticide +
		// "against" + pb
		// TODO AUDIO: Test each of the int. if == -1, don't say anything

		// Outro
		// TODO AUDIO: "To call " + userName + " touch here briefly"
		System.out.println("To call " + userName + " touch here briefly");
	}

	private void makeAudioUserTopBar(boolean canHear) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a30, true);

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

		AdviceSituationItem situationItem = situationItems.get(groupPosition);
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
		// TODO AUDIO: In case there is no users in the result. Set an error
		// message
		if (list == null || list.size() < 1)
			playAudio(R.raw.problems, true);

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_advice);
		mDataProvider = RealFarmProvider.getInstance(this);
		mLayoutInflater = getLayoutInflater();

		expandListView = (ExpandableListView) findViewById(R.id.exp_list);
		situationItems = initLists();
		adapter = new AdviceAdapter(AdviceActivity.this, situationItems, this);
		expandListView.setAdapter(adapter);

		expandCurrentLists(situationItems);

		expandListView.setOnChildClickListener(this);
		expandListView.setOnGroupClickListener(this);
		expandListView.setOnItemLongClickListener(this);
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

		long data = expandListView.getExpandableListPosition(position);
		int type = ExpandableListView.getPackedPositionType(data);
		AdviceSituationItem situationItem = situationItems
				.get(ExpandableListView.getPackedPositionGroup(data));

		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			AdviceSolutionItem solutionItem = situationItem.getItems().get(
					ExpandableListView.getPackedPositionChild(data));
			if (situationItem.getUnread() == 0)
				makeAudioSituation(situationItem);
			makeAudioSolution(solutionItem);
		} else {
			makeAudioSituation(situationItem);
		}

		if (situationItem.getUnread() == 0) {
			mDataProvider.setAdviceRead(situationItem.getId());
			situationItems.get(ExpandableListView.getPackedPositionGroup(data))
					.setUnread(1);
			adapter.setGroups(situationItems);
			adapter.notifyDataSetChanged();
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

		AdviceSituationItem situationItem = situationItems.get(groupPosition);
		AdviceSolutionItem solutionItem = situationItem.getItems().get(
				childPosition);

		// TODO: delete system
		if (!mDataProvider.hasLiked(solutionItem.getId(), Global.userId)) {
			mDataProvider.addPlanAction(Global.userId,
					situationItem.getPlotId(), solutionItem.getId(),
					Global.IsAdmin);
			situationItems.get(groupPosition).getItems().get(childPosition)
					.setHasLiked(true);
			situationItems.get(groupPosition).getItems().get(childPosition)
					.setLikes(solutionItem.getLikes() + 1);
			adapter.setGroups(situationItems);
			adapter.notifyDataSetChanged();
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
		playAudio(R.raw.problems);
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId,
				"like gr:" + groupPosition + " pos:" + childPosition);
	}
}
