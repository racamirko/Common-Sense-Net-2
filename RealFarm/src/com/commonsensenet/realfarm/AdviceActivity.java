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

		/*ArrayList<AdviceSolutionItem> solutionList = new ArrayList<AdviceSolutionItem>();

		AdviceSituationItem gru1 = new AdviceSituationItem();
		gru1.setName("Comedy");
		AdviceSolutionItem ch1_1 = new AdviceSolutionItem();
		ch1_1.setName("A movie");
		ch1_1.setTag(null);
		solutionList.add(ch1_1);
		AdviceSolutionItem ch1_2 = new AdviceSolutionItem();
		ch1_2.setName("An other movie");
		ch1_2.setTag(null);
		solutionList.add(ch1_2);
		AdviceSolutionItem ch1_3 = new AdviceSolutionItem();
		ch1_3.setName("And an other movie");
		ch1_3.setTag(null);
		solutionList.add(ch1_3);
		AdviceSolutionItem ch1_4 = new AdviceSolutionItem();
		ch1_4.setName("Surprise!");
		ch1_4.setTag(null);
		solutionList.add(ch1_4);
		gru1.setItems(solutionList);
		solutionList = new ArrayList<AdviceSolutionItem>();

		AdviceSituationItem gru2 = new AdviceSituationItem();
		gru2.setName("Action");
		AdviceSolutionItem ch2_1 = new AdviceSolutionItem();
		ch2_1.setName("A movie A movie A movie A movie ");
		ch2_1.setTag(null);
		solutionList.add(ch2_1);
		AdviceSolutionItem ch2_2 = new AdviceSolutionItem();
		ch2_2.setName("An other movie An other movie An other movie An other movie ");
		ch2_2.setTag(null);
		solutionList.add(ch2_2);
		AdviceSolutionItem ch2_3 = new AdviceSolutionItem();
		ch2_3.setName("And an other movie And an other movie And an other movie And an other movie ");
		ch2_3.setTag(null);
		solutionList.add(ch2_3);
		gru2.setItems(solutionList);
		situationList.add(gru1);
		situationList.add(gru2);*/
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
		 
		 if(situationItem.getUnread() == 0 && mDataProvider.setAdviceRead(situationItem.getId()) > 0) {
			 situationItems.get(0).setUnread(1);
			 adapter.getGroups().get(0).setUnread(1);
			 adapter.notifyDataSetChanged();
		 }
		 
		 return true;
	}

	public void onLikeClick(int groupPosition, int childPosition) {
		 AdviceSituationItem situationItem = situationItems.get(groupPosition);
		 AdviceSolutionItem solutionItem = situationItem.getItems().get(childPosition);

		mDataProvider.addPlanAction(Global.userId, situationItem.getPlotId(), solutionItem.getId());
		
	}

	public void onLikeLongClick(int groupPosition, int childPosition) {
		// TODO AUDIO: explain what the like button does
		 playAudio(R.raw.problems);
		
	}

}

