package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.commonsensenet.realfarm.actions.HarvestActionActivity;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.view.PlotItemAdapter;

/**
 * Activity that enables the selection of one plot.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * @author Lisa Nguyen
 * 
 */
public class ChoosePlotActivity extends HelpEnabledActivity implements
		OnItemClickListener {
	/** ListAdapter used to handle the plots. */
	private PlotItemAdapter mPlotItemAdapter;
	/** ListView where the plots are rendered. */
	private ListView mPlotsListView;

	public void onBackPressed() {

		startActivity(new Intent(this, Homescreen.class));
		ChoosePlotActivity.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.act_choose_plot);

		// gets the users from the database.
		List<Plot> plots;
		if (Global.selectedAction == HarvestActionActivity.class) {
			plots = mDataProvider.getPlotsByUserIdAndEnabledFlagAndHasCrops(
					Global.userId, 1);
		} else {
			plots = mDataProvider.getPlotsByUserIdAndEnabledFlag(Global.userId,
					1);
		}

		// indicates that no plots where found.
		if (plots == null || plots.size() == 0) {
			playAudio(R.raw.problems);
		}

		// adapter used to handle the data.
		mPlotItemAdapter = new PlotItemAdapter(this, plots, mDataProvider);
		// gets the list from the UI.
		mPlotsListView = (ListView) findViewById(R.id.choose_plot_listview_list);
		// enables the focus on the items.
		mPlotsListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mPlotsListView.setAdapter(mPlotItemAdapter);
		// sets the listener
		mPlotsListView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// gets the selected view using the position
		Plot selectedPlot = mPlotItemAdapter.getItem(position);

		// sets the active plotId
		Global.plotId = selectedPlot.getId();

		// loads the target activity
		if (Global.selectedAction != null) {
			view.getContext().startActivity(
					new Intent(view.getContext(), Global.selectedAction));
			// ensures that back will not reach it.
			ChoosePlotActivity.this.finish();
		}
	}
}
