package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class adminenter extends Activity {

	private final Context context = this;
	private RealFarmProvider mDataProvider;
	private EditText MobileNumber, firstname12, lastname12;

	public void onBackPressed() {

		Intent adminintent = new Intent(adminenter.this, admincall.class);
		startActivity(adminintent);
		adminenter.this.finish();

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminenter);

		System.out.println("In admin enter");
		firstname12 = (EditText) findViewById(R.id.et_fileName1); // First name
		lastname12 = (EditText) findViewById(R.id.et_fileName2);
		MobileNumber = (EditText) this.findViewById(R.id.et_writtenText);
		Button OK = (Button) findViewById(R.id.OK); // Added
		// Mobile number

		mDataProvider = RealFarmProvider.getInstance(context); // Working

		// add the event listeners
		OK.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				System.out.println("In OK button of settings");
				UserDetailsDatabase();
				Intent adminintent = new Intent(adminenter.this,
						admincall.class);
				startActivity(adminintent);
				adminenter.this.finish();
			}
		});

	}

	public void UserDetailsDatabase() {

		System.out.println("User details is put to database");

		String firstname12String = firstname12.getText().toString();

		String lastname12String = lastname12.getText().toString();
		String MobileNumberString = MobileNumber.getText().toString();

		// deviceID = RealFarmDatabase.DEFAULT_NUMBER;

		System.out.println("User details is put to database");
		System.out.println(firstname12String);
		System.out.println(lastname12String);
		System.out.println(MobileNumberString);

		mDataProvider.setUserInfo(MobileNumberString, firstname12String,
				lastname12String);
		// mDataProvider.setUserInfo("124","jljf","ldl");
		// TODO: why the return value is not stored?
		mDataProvider.getUsers();
		Toast.makeText(getBaseContext(), "User Details is put to Database",
				Toast.LENGTH_SHORT).show();

	}

}
