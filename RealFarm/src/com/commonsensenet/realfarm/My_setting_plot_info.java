package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.view.PlotItemAdapter;

public class My_setting_plot_info extends Activity {

	/** Context of the current application. */
	private final Context mContext = this;

	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** Identifier of the plot what will be deleted. */
	private int mPlotIdDelete;
	/** ListAdapter used to handle the plots. */
	private PlotItemAdapter mPlotItemAdapter;
	/** ListView where the plots are rendered. */
	private ListView mPlotsListView;

	private void listViewSettings() {

		// gets the users from the database.
		List<Plot> plots = mDataProvider.getPlotsByUserIdAndDeleteFlag(
				Global.userId, 0);

		// initializes the list adapter
		mPlotItemAdapter = new PlotItemAdapter(this, plots, mDataProvider);

		// gets the list from the UI.
		mPlotsListView = (ListView) findViewById(R.id.list_plots);
		mPlotsListView.setItemsCanFocus(true);
		// sets the custom adapter.
		mPlotsListView.setAdapter(mPlotItemAdapter);
	}

	public void onBackPressed() {

		Intent adminintent123 = new Intent(My_setting_plot_info.this,
				Homescreen.class);
		startActivity(adminintent123);
		My_setting_plot_info.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout of the activity
		setContentView(R.layout.my_setting_plot_info);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(mContext);

		// default value
		mPlotIdDelete = -1;

		listViewSettings();

		Button btnAddPlot = (Button) findViewById(R.id.button_add_plot);

		// add the event listeners
		btnAddPlot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent adminintent123 = new Intent(My_setting_plot_info.this,
						My_settings_plot_details.class);
				startActivity(adminintent123);
				My_setting_plot_info.this.finish();
				listViewSettings();
			}
		});

		mPlotsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						// Start your Activity according to the item just
						// clicked.

						System.out
								.println("in main list SHORT CLICK of my_setting_plot_info ");

						// Position=position+1;
						// ReadUser= mDataProvider.getUserById(position+1);
						// mDataProvider.getUserList();

						// Intent AdmincallToHome = new Intent(admincall.this,
						// Homescreen.class); // This works When user is pressed
						// it goes to homescreen
						// startActivity(AdmincallToHome);

					}
				});

		mPlotsListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View v, int position, long id) {
						// Start your Activity according to the item just
						// clicked.

						System.out
								.println("in main list LONG CLICK of my settings plot info");
						// Position = position + 1;
						// ReadUser= mDataProvider.getUserById(position+1);

						System.out.println(position);
						// get plot list for that user id whose deleteFlag = 0;
						List<Plot> PlotList = mDataProvider
								.getPlotsByUserIdAndDeleteFlag(Global.userId, 0);

						mPlotIdDelete = PlotList.get(position).getId();

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								mContext);

						// set title
						alertDialogBuilder.setTitle("Delete");

						// set dialog message
						alertDialogBuilder
								.setMessage("Click Yes to delete the plot !")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {

												System.out.println("Yes");

												mDataProvider
														.setDeleteFlagForPlot(mPlotIdDelete);
												mDataProvider.getPlots();

												finish();
												startActivity(getIntent());

											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {

												System.out.println("No");
												dialog.cancel();
											}
										});

						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();

						// show it
						alertDialog.show();

						return true;
					}
				});
	}
}
