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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RelativeLayout;

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

public class AdviceActivity extends HelpEnabledActivity implements OnChildClickListener, OnGroupClickListener, OnItemLongClickListener {
	private RealFarmProvider mDataProvider;
	protected LayoutInflater mLayoutInflater;
		
	private AdviceAdapter adapter;
	private ArrayList<AdviceSituationItem> situationItems;
	private ExpandableListView expandListView;
	
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
	
	private void expandCurrentLists(ArrayList<AdviceSituationItem> situationItems) {
		for(int i=0; i < adapter.getGroupCount(); i++) {
			if(situationItems.get(i).getValidDate() >= new Date().getTime())
				expandListView.expandGroup(i);
		}		
	}

	public ArrayList<AdviceSituationItem> initLists() {
		ArrayList<AdviceSituationItem> adviceData = mDataProvider.getAdviceData(Global.userId);
		if(adviceData == null || adviceData.size() == 0){
			// TODO AUDIO: No data to present
			playAudio(R.raw.problems);
		}
		return adviceData;
	}

	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		//Object e = (Object)adapter.getChild(groupPosition, childPosition);

		AdviceSituationItem situationItem = situationItems.get(groupPosition);
		AdviceSolutionItem solutionItem = situationItem.getItems().get(childPosition);
		AggregateItem selectedItem = getSelectedItem(situationItem, solutionItem);
		final List<UserAggregateItem> list = ActionDataFactory.getUserAggregateData(selectedItem, mDataProvider);

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
					ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "back");
					ApplicationTracker.getInstance().flush();
					dialog.dismiss();
				}
		});

		// sets the data of the header using the old view.
		RelativeLayout rl = (RelativeLayout)layout.findViewById(R.id.top_user_info);
		View tmpView = mLayoutInflater.inflate(R.layout.tpl_aggregate_item, null);
		copyView(selectedItem, tmpView);
		rl.addView(tmpView);

		// gets the data and data adapter.
		// TODO AUDIO: In case there is no users in the result. Set an error message
		if(list == null || list.size() < 1) playAudio(R.raw.problems, true);


		// gets the ListView from the layout
		ListView userListView = (ListView) layout.findViewById(R.id.list_dialog_aggregate);

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
		//	makeAudioUserTopBar(false);

		ImageView helpDetail = (ImageView)layout.findViewById(R.id.aggr_details_img_help);
		LinearLayout dialogAggregateHeader = (LinearLayout)layout.findViewById(R.id.dialog_aggregate_header);

		helpDetail.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog help");
				ApplicationTracker.getInstance().flush();

				playAudio(R.raw.help, true);
				return true;
			}
		});

		helpDetail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog help");
				ApplicationTracker.getInstance().flush();

				playAudio(R.raw.help, true);
			}
		});

		dialogAggregateHeader.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// Say something according to the layout's contents. This is the top header of the dialog to call people in the aggregates
				//makeAudioUserTopBar(true);
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog header");
				ApplicationTracker.getInstance().flush();
				//makeAudioAggregateMarketItem(selectedItem, true);
				return true;
			}
		});

		dialogAggregateHeader.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog header");
				ApplicationTracker.getInstance().flush();
			}
		});

		userListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog call "+list.get(position).getName());
				ApplicationTracker.getInstance().flush();

				// TODO: calling Mr ...
				UserAggregateItem choice = list.get(position);
				//	makeAudioCallUser(choice);

				String phoneNumber = choice.getTel();
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + phoneNumber));
				startActivity(intent);
			}
		});

		userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog call "+list.get(position).getName());
				ApplicationTracker.getInstance().flush();

				// TODO: audio
				UserAggregateItem choice = list.get(position);
				//	makeAudioUserItem(choice);

				return true;
			}
		});

		return false;
	}
	
	public void copyView(AggregateItem aggregate, View destination) {
		destination.setBackgroundColor(Color.LTGRAY);
		
		TextView tw = (TextView)destination.findViewById(R.id.label_news);
		tw.setText(aggregate.getNewsText());
		tw.setBackgroundColor(Color.parseColor("#FFFFCC"));		
		
		tw = (TextView)destination.findViewById(R.id.label_left);
		tw.setText(aggregate.getLeftText());
				
		RelativeLayout rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_left);
		if(aggregate.getLeftBackground() != -1) rl.setBackgroundResource(aggregate.getLeftBackground());
		else tw.setTextColor(Color.BLACK);

		tw = (TextView)destination.findViewById(R.id.label_center);
		tw.setText(aggregate.getCenterText());
		
		rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_center);
		if(aggregate.getCenterBackground() != -1) rl.setBackgroundResource(aggregate.getCenterBackground());
		else {
			tw.setTextColor(Color.BLACK);
			// hack
			if(!aggregate.getCenterText().equals("")){
				rl.getLayoutParams().width = 200;
				tw.setTextSize(20);
			} else{
				rl.getLayoutParams().width = 20;
				rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_right);
				rl.getLayoutParams().width = 300;
			}
		}
		
		ImageView iw = (ImageView)destination.findViewById(R.id.image_center);
		if(aggregate.getCenterImage() != -1) iw.setImageResource(aggregate.getCenterImage());

		tw = (TextView)destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());
		
		iw = (ImageView)destination.findViewById(R.id.image_left);
		if(aggregate.getLeftImage() != -1) iw.setImageResource(aggregate.getLeftImage());
		
		iw = (ImageView)destination.findViewById(R.id.image_left_bottom);
		if(aggregate.getLeftBottomImage() != -1) iw.setImageResource(aggregate.getLeftBottomImage());
	}

	private AggregateItem getSelectedItem(AdviceSituationItem situationItem, AdviceSolutionItem solutionItem) {
		int actionTypeId = solutionItem.getSuggestedActionId();
		if(actionTypeId == -1) actionTypeId = RealFarmDatabase.ACTION_TYPE_SPRAY_ID;
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

	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		/*AdviceSituationItem situationItem = situationItems.get(groupPosition);
		 System.out.println("parent " +situationItem.getCropShortName());*/
		return false;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		stopAudio();
		playAudio(R.raw.problems);

		long data = expandListView.getExpandableListPosition(position);
		int type = ExpandableListView.getPackedPositionType(data);
		AdviceSituationItem situationItem = situationItems.get(ExpandableListView.getPackedPositionGroup(data));

		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			AdviceSolutionItem solutionItem = situationItem.getItems().get(ExpandableListView.getPackedPositionChild(data));
			System.out.println("child " + situationItem.getCropShortName()+" "+ solutionItem.getPesticideShortName());
		} else {
			System.out.println("parent " +situationItem.getCropShortName());
		}

		if(situationItem.getUnread() == 0) {
			mDataProvider.setAdviceRead(situationItem.getId());
			situationItems.get(ExpandableListView.getPackedPositionGroup(data)).setUnread(1);
			adapter.setGroups(situationItems);
			adapter.notifyDataSetChanged();
		}

		return true;
	}

	public void onLikeClick(int groupPosition, int childPosition) {
		AdviceSituationItem situationItem = situationItems.get(groupPosition);
		AdviceSolutionItem solutionItem = situationItem.getItems().get(childPosition);
		// TODO: delete system

		if(!mDataProvider.hasLiked(solutionItem.getId(), Global.userId)) {
			mDataProvider.addPlanAction(Global.userId, situationItem.getPlotId(), solutionItem.getId());
			situationItems.get(groupPosition).getItems().get(childPosition).setHasLiked(true);
			situationItems.get(groupPosition).getItems().get(childPosition).setLikes(solutionItem.getLikes()+1);
		} else {
			mDataProvider.deletePlanAction(Global.userId, situationItem.getPlotId(), solutionItem.getId());
			situationItems.get(groupPosition).getItems().get(childPosition).setHasLiked(false);
			situationItems.get(groupPosition).getItems().get(childPosition).setLikes(solutionItem.getLikes()-1);

		}
		adapter.setGroups(situationItems);
		adapter.notifyDataSetChanged();

	}

	public void onLikeLongClick(int groupPosition, int childPosition) {
		// TODO AUDIO: explain what the like button does
		playAudio(R.raw.problems);

	}

}

