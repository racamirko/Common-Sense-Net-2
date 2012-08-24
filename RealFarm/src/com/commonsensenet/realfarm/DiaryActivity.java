package com.commonsensenet.realfarm;


import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.ActionItemAdapter;

/**
 * Activity that displays the diary of activities of the current user.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class DiaryActivity extends HelpEnabledActivity implements
	OnItemClickListener, OnItemLongClickListener {
	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** ListAdapter used to handle the actions. */
	private ActionItemAdapter mActionItemAdapter;
	/** ListView where the diary is rendered. */
	private ListView mDiaryListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.act_diary);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

		// gets the actions from the database
		List<Action> actions = mDataProvider.getActionsByUserId(Global.userId);
		
		if(actions == null || actions.size() == 0) 	playAudio(R.raw.problems, true);

		// creates the custom adapter.
		mActionItemAdapter = new ActionItemAdapter(this, actions, mDataProvider);

		// gets the list from the UI.
		mDiaryListView = (ListView) findViewById(R.id.diary_listview_list);
		// enables the focus on the items.
		mDiaryListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mDiaryListView.setAdapter(mActionItemAdapter);
		// detects when a diary item has been touched.
		mDiaryListView.setOnItemClickListener(this);
		mDiaryListView.setOnItemLongClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// gets the selected view using the position
		// Action selectedAction = mActionItemAdapter.getItem(position);
		
		// TODO: play sound based on the selectedAction.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), position);
		ApplicationTracker.getInstance().flush();
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		//Action selectedAction = mActionItemAdapter.getItem(position);

		// TODO: play sound based on the selectedAction.
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), position);
		ApplicationTracker.getInstance().flush();
		
		return true;
	}
}
