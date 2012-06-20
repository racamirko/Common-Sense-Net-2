package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.view.ActionItemAdapter;

/**
 * Activity that displays the diary of activities of the current user.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class DiaryActivity extends Activity {
	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** ListAdapter used to handle the actions. */
	private ActionItemAdapter mActionItemAdapter;
	/** ListView where the plots are rendered. */
	private ListView mPlotsListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.act_diary);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

		// gets the actions from the database
		List<Action> actions = mDataProvider.getActionsByUserId(Global.userId);

		// creates the custom adapter.
		mActionItemAdapter = new ActionItemAdapter(this, actions, mDataProvider);

		// gets the list from the UI.
		mPlotsListView = (ListView) findViewById(R.id.list_diary);
		mPlotsListView.setItemsCanFocus(true);

		// sets the custom adapter.
		mPlotsListView.setAdapter(mActionItemAdapter);
	}
}
