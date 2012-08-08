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

		mDataProvider = RealFarmProvider.getInstance(this);

		// gets the buttons from the layout
		Button addUserButton = (Button) findViewById(R.id.admin_user_list_button_add_user);

		populateUserList();

		// add the event listeners
		addUserButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");

				Intent adminintent123 = new Intent(UserListActivity.this,
						AddUserActivity.class);
				startActivity(adminintent123);
				UserListActivity.this.finish();

			}
		});

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				// gets all the available users.
				List<User> userList = mDataProvider.getUsers();

				// TODO: should use the underlying list adapter instead
				// of querying the database again.
				// sets the id of the selected user.
				Global.userId = userList.get(position).getId();

				// redirects to the homescreen.
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

						// TODO: should use the underlying list adapter instead
						// of querying the database again.

						// gets the full list of users.
						List<User> userListNoDelete = mDataProvider.getUsers();

						// sets the user id corresponding to the position that
						// is selected.
						Global.userId = userListNoDelete.get(position).getId();

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
		List<User> userList = mDataProvider.getUsers();
		// adds the users into the list adapter.
		for (int x = 0; x < userList.size(); x++) {
			mListAdapter.add(userList.get(x).getFirstname() + " "
					+ userList.get(x).getLastname());

		}

		// indicates that the data has been changed.
		mListAdapter.notifyDataSetChanged();
	}

}
