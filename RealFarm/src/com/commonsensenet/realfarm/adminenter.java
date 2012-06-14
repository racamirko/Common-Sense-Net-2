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
	public int Click_Count = 0;
	public Button btn_write, btn_read;
	public EditText et_write, et_read, et_fileName1, et_fileName2,
			et_writtenText;

	long dbreturn;
	public RealFarmProvider mDataProvider;
	final Context context = this;
	private EditText MobileNumber, firstname12, lastname12;

	public static final String DEBUG_ID = "RealFarm Testing";
	public static final String TABLE_NAME_USER = "user";
	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstName";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastName";
	public static final String COLUMN_NAME_USER_MOBILE = "mobileNumber";

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
		mDataProvider.getUserList();
		Toast.makeText(getBaseContext(), "User Details is put to Database",
				Toast.LENGTH_SHORT).show();

	}

}
