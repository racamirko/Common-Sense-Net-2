package com.commonsensenet.realfarm.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.RealFarmApp;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class AddUserActivity extends Activity {

	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	/** TextField used to capture the Firstname of the user. */
	private EditText mFirstnameTextField;
	/** TextField used to capture the Lastname of the user. */
	private EditText mLastnameTextField;
	/** TextField used to capture the mobile number of the user. */
	private EditText mMobileNumberTextField;

	public void addUserToDatabase() {

		String firstname = mFirstnameTextField.getText().toString();
		String lastname = mLastnameTextField.getText().toString();
		String mobileNumber = mMobileNumberTextField.getText().toString();
		String deviceId = ((RealFarmApp) getApplication()).getDeviceId();

		mDataProvider.addUser(firstname, lastname, mobileNumber, deviceId,
				null, "CK Pura", 1);
		Toast.makeText(getBaseContext(), "User Details is put to Database",
				Toast.LENGTH_SHORT).show();
	}

	public void onBackPressed() {

		Intent adminintent = new Intent(AddUserActivity.this,
				UserListActivity.class);
		startActivity(adminintent);
		AddUserActivity.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminenter);

		mFirstnameTextField = (EditText) findViewById(R.id.et_fileName1);
		mLastnameTextField = (EditText) findViewById(R.id.et_fileName2);
		mMobileNumberTextField = (EditText) this
				.findViewById(R.id.et_writtenText);
		Button okButton = (Button) findViewById(R.id.OK);

		mDataProvider = RealFarmProvider.getInstance(this);

		// add the event listeners
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				addUserToDatabase();
				startActivity(new Intent(AddUserActivity.this,
						UserListActivity.class));
				AddUserActivity.this.finish();
			}
		});
	}
}
