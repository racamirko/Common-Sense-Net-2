package com.commonsensenet.realfarm.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.User;

public class admincall extends Activity {

	private final Context context = this;
	private ArrayAdapter<String> listAdapter;
	private ListView mainListView;
	private RealFarmProvider mDataProvider;
	private int Position;

	public void onBackPressed() {

		// Intent adminintent = new Intent(admincall.this,Homescreen.class);

		// startActivity(adminintent);
		admincall.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admincall);
		System.out.println("In admin call");

		mDataProvider = RealFarmProvider.getInstance(context); // Working

		System.out.println("In admincall getting the Action list ");

		// gets the buttons from the layout
		Button AddUser = (Button) findViewById(R.id.AddUser);

		updateUser();

		// add the event listeners
		AddUser.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");

				Intent adminintent123 = new Intent(admincall.this,
						adminenter.class);
				startActivity(adminintent123);
				admincall.this.finish();

			}
		});

		// final List<User> userList = mDataProvider.getUserList(); // Working

		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						// Start your Activity according to the item just
						// clicked.

						System.out
								.println("in main list SHORT CLICK of admin call");

						Position = position + 1;

						System.out.println(Position);
						List<User> userListNoDelete = mDataProvider
								.getUsersByIsEnabled(0); // Get the users whose delete
													// flag=0

						Global.userId = userListNoDelete.get(position).getId(); // Set
																				// the
																				// user
																				// id
																				// corresponding
																				// to
																				// the
																				// position
																				// who
																				// is
																				// selected

						System.out.println("Global userId in admincall");
						System.out.println(Global.userId);

						// gets the selected user.
						// ReadUser = mDataProvider.getUserById(userListNoDelete
						// .get(position).getUserId());

						// mDataProvider.getuserDelete(0);

						// Intent AdmincallToHome = new Intent(admincall.this,
						// Homescreen.class); // This works When user is pressed
						// it goes to homescreen
						// startActivity(AdmincallToHome);

						// TODO: finish this method
						admincall.this.finish();

					}
				});

		mainListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View v, int position, long id) {
						// Start your Activity according to the item just
						// clicked.

						System.out.println("in main list LONG CLICK");
						Position = position + 1;
						// ReadUser= mDataProvider.getUserById(position+1);
						System.out.println(position);
						List<User> userListNoDelete = mDataProvider
								.getUsersByIsEnabled(0); // Get the users whose delete
													// flag=0

						// sets the user id corresponding to the position that
						// is selected.
						Global.userId = userListNoDelete.get(position).getId();

						System.out.println("Global userId in admincall");
						System.out.println(Global.userId);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								context);

						// set title
						alertDialogBuilder.setTitle("Delete");

						// set dialog message
						alertDialogBuilder
								.setMessage("Click Yes to delete !")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// if this button is clicked,
												// close
												// current activity
												System.out.println("Yes");

												mDataProvider.setUserEnabled(
														Global.userId, 0);

												finish();
												startActivity(getIntent());

											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// if this button is clicked,
												// just close
												// the dialog box and do nothing
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

	} // End of oncreate()

	public void updateUser() {

		mainListView = (ListView) findViewById(R.id.mainListView);

		mainListView.setItemsCanFocus(true);
		String[] planets = new String[] {}; // Sets parameters for list view
		ArrayList<String> planetList = new ArrayList<String>();
		planetList.addAll(Arrays.asList(planets));
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, planetList);
		mainListView.setAdapter(listAdapter);

		// gets the users from the database.
		// List<User> userList = mDataProvider.getUserList();
		List<User> userList = mDataProvider.getUsersByIsEnabled(0);
		// adds the users into the list adapter.
		for (int x = 0; x < userList.size(); x++) {
			listAdapter.add(userList.get(x).getFirstname() + " "
					+ userList.get(x).getLastname());

		}
	}

}
