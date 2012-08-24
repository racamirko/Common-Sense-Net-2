package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.List;

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
		
		for(int i=0; i < adapter.getGroupCount(); i++) {
			expandListView.expandGroup(i);
		}

		expandListView.setOnChildClickListener(this);
		expandListView.setOnGroupClickListener(this);
		expandListView.setOnItemLongClickListener(this);
	}

	public ArrayList<AdviceSituationItem> initLists() {
		return mDataProvider.getAdviceData(Global.userId);

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
		playAudio(R.raw.a10);

		 AdviceSituationItem situationItem = situationItems.get(groupPosition);
		 AdviceSolutionItem solutionItem = situationItem.getItems().get(childPosition);
		 System.out.println("child " + situationItem.getCropShortName()+" "+ solutionItem.getName());

		 return false;
	 }

	 public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		 playAudio(R.raw.a20);

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
			System.out.println("child " + situationItem.getCropShortName()+" "+ solutionItem.getName());
		} else {
			System.out.println("parent " +situationItem.getCropShortName());
		}
		return true;
	}

	public void onLikeClick(int groupPosition, int childPosition) {
		 playAudio(R.raw.a30);
	}

	public void onLikeLongClick(int groupPosition, int childPosition) {
		 playAudio(R.raw.a40);
		
	}
}

