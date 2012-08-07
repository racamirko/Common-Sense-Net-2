package com.commonsensenet.realfarm.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class AddUserActivity extends Activity {

	private RealFarmProvider mDataProvider;
	private EditText mDeviceIdTextField, mFirstnameTextField,
			mLastnameTextField;

	public void addUserToDatabase() {

		String firstname = mFirstnameTextField.getText().toString();
		String lastname = mLastnameTextField.getText().toString();
		String deviceId = mDeviceIdTextField.getText().toString();

		mDataProvider.addUser(deviceId, firstname, lastname, "", 0, 0);
		Toast.makeText(getBaseContext(), "User Details is put to Database",
				Toast.LENGTH_SHORT).show();
	}

	public void onBackPressed() {

		Intent adminintent = new Intent(AddUserActivity.this, UserListActivity.class);
		startActivity(adminintent);
		AddUserActivity.this.finish();

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminenter);

		mFirstnameTextField = (EditText) findViewById(R.id.et_fileName1);
		mLastnameTextField = (EditText) findViewById(R.id.et_fileName2);
		mDeviceIdTextField = (EditText) this.findViewById(R.id.et_writtenText);
		Button okButton = (Button) findViewById(R.id.OK);

		mDataProvider = RealFarmProvider.getInstance(this);

		// add the event listeners
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				addUserToDatabase();
				startActivity(new Intent(AddUserActivity.this, UserListActivity.class));
				AddUserActivity.this.finish();
			}
		});

	}
}
