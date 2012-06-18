package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.commonsensenet.realfarm.actions.action_fertilizing;
import com.commonsensenet.realfarm.actions.action_harvest;
import com.commonsensenet.realfarm.actions.action_irrigate;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.actions.action_spraying;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;

public class Plot_Image extends Activity {
	/** View where the items are displayed. */

	protected RealFarmProvider mDataProvider;

	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	public User ReadUser = null;
	public int Position; // Has copy of mainlistview position

	final Context context = this;
	String name;

	public void onBackPressed() {

		Intent adminintent123 = new Intent(Plot_Image.this, Homescreen.class);
		startActivity(adminintent123);
		Plot_Image.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plot_image);
		System.out.println("In My_setting_plot_info call");
		int no_of_plots;
		mDataProvider = RealFarmProvider.getInstance(context); // Working

	//	no_of_plots = mDataProvider.getAllPlotList().size();
		no_of_plots = mDataProvider.getAllPlotListByUserDeleteFlag(Global.userId,0).size();                                     //added with audio integration
		String no_of_plots_str = String.valueOf(no_of_plots);

		ListViewSettings();

		Toast.makeText(Plot_Image.this, "No of Plots     " + no_of_plots_str,
				Toast.LENGTH_LONG).show();
		// test
		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView parent, View v,
							int position, long id) {
						// Start your Activity according to the item just
						// clicked.

						System.out
								.println("in main list SHORT CLICK of Plot_Image ");
						if (Global.actionno == 1) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_sowing.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();

						}

						if (Global.actionno == 2) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_harvest.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();
						}
						if (Global.actionno == 3) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_selling.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();
						}
						if (Global.actionno == 4) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_fertilizing.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();
						}
						if (Global.actionno == 5) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_spraying.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();
						}
						if (Global.actionno == 6) {

						}

						if (Global.actionno == 7) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									SM_enter.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();

						}
						if (Global.actionno == 8) {
							Intent adminintent123 = new Intent(Plot_Image.this,
									action_irrigate.class);
							startActivity(adminintent123);
							Plot_Image.this.finish();

						}

						// Plot_Image.this.finish();

					}
				});

	} // End of oncreate()

	public void ListViewSettings() {

		mainListView = (ListView) findViewById(R.id.mainListView);

		mainListView.setItemsCanFocus(true);
		String[] planets = new String[] {}; // Sets parameters for list view
		ArrayList<String> planetList = new ArrayList<String>();
		planetList.addAll(Arrays.asList(planets));
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,
				planetList);
		mainListView.setAdapter(listAdapter);

		// gets the users from the database.
		//List<PlotNew> userList = mDataProvider.getAllPlotList();
		List<Plot>  plotList=mDataProvider.
		getAllPlotListByUserDeleteFlag(Global.userId,0);                                     //added with audio integration

		// adds the plot into the list adapter.
		for (int x = 0; x < plotList.size(); x++) {
			listAdapter.add("Plot id:  " + plotList.get(x).getPlotId() + " "
					+ "Soil type:  " + plotList.get(x).getSoilType());

		}
	}

}
