package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.commonsensenet.realfarm.actions.action_fertilizing;
import com.commonsensenet.realfarm.actions.action_harvest;
import com.commonsensenet.realfarm.actions.action_irrigate;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.actions.action_spraying;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.view.ActionItemAdapter;

public class DiaryActivity extends Activity {
	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** ListAdapter used to handle the actions. */
	private ActionItemAdapter mActionItemAdapter;
	/** ListView where the plots are rendered. */
	private ListView mPlotsListView;

	private void listViewSettings() {

		// gets the actions from the database
		List<Action> actions = mDataProvider.getActions();

		// creates the custom adapter.
		mActionItemAdapter = new ActionItemAdapter(this, actions, mDataProvider);

		// gets the list from the UI.
		mPlotsListView = (ListView) findViewById(R.id.list_diary);
		mPlotsListView.setItemsCanFocus(true);

		// sets the custom adapter.
		mPlotsListView.setAdapter(mActionItemAdapter);
	}

	public void onBackPressed() {

		Intent adminintent123 = new Intent(DiaryActivity.this, Homescreen.class);
		startActivity(adminintent123);
		DiaryActivity.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.act_diary);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

		// updates the list view
		listViewSettings();

		// test
		mPlotsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent intent = null;
						switch (Global.actionno) {
						case 1:
							intent = new Intent(view.getContext(),
									action_sowing.class);
							break;
						case 2:
							intent = new Intent(view.getContext(),
									action_harvest.class);
							break;
						case 3:
							intent = new Intent(view.getContext(),
									action_selling.class);
							break;
						case 4:
							intent = new Intent(view.getContext(),
									action_fertilizing.class);
							break;
						case 5:
							intent = new Intent(view.getContext(),
									action_spraying.class);
							break;
						case 6:
							break;
						case 7:
							intent = new Intent(view.getContext(),
									SM_enter.class);
							break;
						case 8:
							intent = new Intent(view.getContext(),
									action_irrigate.class);
							break;
						}

						// if the intent is valid it opens the activity
						if (intent != null) {
							view.getContext().startActivity(intent);
							DiaryActivity.this.finish();
						}
					}
				});
	}

	// private View.OnClickListener OnClickDiary(final int actionID) {
	// return new View.OnClickListener() {
	//
	// public void onClick(View v) {
	// // removes the selected action
	// mDataProvider.removeAction(actionID);
	// }
	// };
	// }
}
