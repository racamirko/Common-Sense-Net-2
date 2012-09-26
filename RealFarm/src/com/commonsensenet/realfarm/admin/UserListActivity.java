package com.commonsensenet.realfarm.admin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.RealFarmApp;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.User;

public class UserListActivity extends Activity {

	/** Access to the underlying database of the application. */
	private RealFarmProvider mDataProvider;
	/** Adapter used to handle the data to display. */
	private ArrayAdapter<String> mListAdapter;
	/** ListView used to render the available users. */
	private ListView mListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_admin_user_list);

		// gets an instance of the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

		// gets the buttons from the layout
		Button addUserButton = (Button) findViewById(R.id.admin_user_list_button_add_user);

		// adds all the users to the ListView
		populateUserList();

		// add the event listeners
		addUserButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent adminintent123 = new Intent(UserListActivity.this,
						AddUserActivity.class);
				startActivity(adminintent123);
				UserListActivity.this.finish();

			}
		});

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				// sets the id of the selected user.
				Global.userId = mUserList.get(position).getId();

				// If the user's device id is the same as the current device's
				// id
				if (mUserList.get(position).getId() == Long
						.valueOf(((RealFarmApp) getApplication()).getDeviceId()
								+ "1")) {
					Global.isAdmin = 0;
				} else {
					Global.isAdmin = 1;
				}

				// redirects to the home screen.
				startActivity(new Intent(UserListActivity.this,
						Homescreen.class));

				// kills the activity so it is not reachable by doing back.
				UserListActivity.this.finish();
			}
		});

		mListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View v, int position, long id) {

						// sets the user id corresponding to the position that
						// is selected.
						Global.userId = mUserList.get(position).getId();

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								UserListActivity.this);

						// sets the title of the dialog.
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

												// disables the selected user
												mDataProvider.setUserEnabled(
														Global.userId, 0);

												startActivity(getIntent());
												finish();
											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// no action required. The
												// dialog is dismissed.
												dialog.dismiss();
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

	/** List of the Users obtained from the Database. */
	private List<User> mUserList;

	/**
	 * Populates the listView
	 */
	public void populateUserList() {

		// gets the list where the data is displayed
		mListView = (ListView) findViewById(R.id.admin_user_listview_list);
		mListView.setItemsCanFocus(true);

		// creates a new list to hold the user data.
		ArrayList<String> emptyList = new ArrayList<String>();

		mListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, emptyList);
		mListView.setAdapter(mListAdapter);

		// shows all the available users.
		mUserList = mDataProvider
				.getUsersByDeviceId(((RealFarmApp) getApplication())
						.getDeviceId());

		// adds the users into the list adapter.
		for (int x = 0; x < mUserList.size(); x++) {
			mListAdapter.add(mUserList.get(x).getFirstname() + " "
					+ mUserList.get(x).getLastname());
		}

		// indicates that the data has been changed.
		mListAdapter.notifyDataSetChanged();
	}
}
