package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;
import com.commonsensenet.realfarm.view.AdviceAdapter;

public class AdviceActivity extends HelpEnabledActivity implements OnChildClickListener, OnGroupClickListener, OnItemLongClickListener {
	private RealFarmProvider mDataProvider;
		
	private AdviceAdapter adapter;
	private ArrayList<AdviceSituationItem> situationItems;
	private ExpandableListView expandListView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_advice);
		mDataProvider = RealFarmProvider.getInstance(this);
		
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
		 System.out.println("child " + situationItem.getCropShortName()+" "+ solutionItem.getPesticideShortName());

		 return false;
	 }

	 public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

		 AdviceSituationItem situationItem = situationItems.get(groupPosition);
		 System.out.println("parent " +situationItem.getCropShortName());

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
			 situationItems = mDataProvider.getAdviceData(Global.userId);
			 adapter.setGroups(mDataProvider.getAdviceData(Global.userId));

			 adapter.notifyDataSetChanged();
		 }
		 
		 return true;
	}

	public void onLikeClick(int groupPosition, int childPosition) {
		 AdviceSituationItem situationItem = situationItems.get(groupPosition);
		 AdviceSolutionItem solutionItem = situationItem.getItems().get(childPosition);
		 // TODO: delete system
		 
		 if(mDataProvider.getLike(solutionItem.getId(), Global.userId)) {
			 System.out.println("true");
		 } else 			 System.out.println("false");

		 
		 situationItems = mDataProvider.getAdviceData(Global.userId);
		 adapter.setGroups(mDataProvider.getAdviceData(Global.userId));
		 adapter.notifyDataSetChanged();

		 mDataProvider.addPlanAction(Global.userId, situationItem.getPlotId(), solutionItem.getId());
		
	}

	public void onLikeLongClick(int groupPosition, int childPosition) {
		// TODO AUDIO: explain what the like button does
		 playAudio(R.raw.problems);
		
	}

}

