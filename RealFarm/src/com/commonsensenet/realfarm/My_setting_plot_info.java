package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

public class My_setting_plot_info extends Activity {
	View view;
	/** View where the items are displayed. */

	protected RealFarmProvider mDataProvider;

	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	Cursor cc;
	String log;
	public User ReadUser = null;
	public int Position; // Has copy of mainlistview position
	public int PlotIdDelete=0;  //Contains plot id to which delete flag is set

	final Context context = this;
	String name;

	public void onBackPressed() {

		Intent adminintent123 = new Intent(My_setting_plot_info.this,
				Homescreen.class);
		startActivity(adminintent123);
		My_setting_plot_info.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_setting_plot_info);
		System.out.println("In My_setting_plot_info call");

		mDataProvider = RealFarmProvider.getInstance(context); // Working

		Button AddPlot = (Button) findViewById(R.id.AddPlot);
		// Button DeleteUser;
		// Button DeleteUser = (Button) findViewById(R.id.DeleteUser);
		ListViewSettings();
		// listimgview();

		// add the event listeners
		AddPlot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");

				Global.CallToPlotInfo = 1;
				Intent adminintent123 = new Intent(My_setting_plot_info.this,
						My_settings_plot_details.class);
				startActivity(adminintent123);
				My_setting_plot_info.this.finish();
				ListViewSettings();
				// listimgview();
			}
		});

		// final List<User> userList =mDataProvider.getUserList(); //Working

		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView parent, View v,
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
		
		
		mainListView
		.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView parent, View v,
					int position, long id) {
				// Start your Activity according to the item just
				// clicked.

				System.out.println("in main list LONG CLICK of my settings plot info");
				Position = position + 1;
				// ReadUser= mDataProvider.getUserById(position+1);
				
				System.out.println(position);
				List<PlotNew> PlotList = mDataProvider.
				getAllPlotListByUserDeleteFlag(Global.userId,0);    //Get plot list for that user id whose deleteFlag=0

			PlotIdDelete = PlotList.get(position)
					.getPlotId(); 

			

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

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
												.setDeleteFlagForPlot(PlotIdDelete);
										mDataProvider.getAllPlotList(); 

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

	} // End of onCreate()

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
		List<PlotNew> plotList = mDataProvider.getAllPlotListByUserDeleteFlag(Global.userId,0);

		// adds the plot into the list adapter.
		for (int x = 0; x < plotList.size(); x++) {
			listAdapter.add("Plot id:  " + plotList.get(x).getPlotId() + " "
					+ "Soil type:  " + plotList.get(x).getSoilType());

		}

	}

}
