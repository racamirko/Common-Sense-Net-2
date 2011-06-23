package com.commonsensenet.realfarm;

import com.commonsensenet.realfarm.database.ManageDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	private String firstnameString;
	private String lastnameString;
	TelephonyManager telephonyManager;
	private ManageDatabase managedb;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getDeviceId();

		realFarm mainApp = ((realFarm)getApplicationContext());
		managedb = mainApp.getDatabase();
		managedb.open();
    	
    	Cursor c = managedb.GetEntries("users", new String[] {"firstName", "lastName"}, "mobileNumber=" + deviceID, null, null, null, null);
    	
    	if (c.getCount()>0) { // user exists in database
    		c.moveToFirst();
           	

    		// display his information in box
        	EditText firstname = (EditText) this.findViewById(R.id.editText1);
            EditText lastname = (EditText) this.findViewById(R.id.editText2);
            
            firstname.setText(c.getString(0));
            lastname.setText(c.getString(1));
    	}
    	managedb.close();
        
        
	}
	
	
	@Override
	public void onBackPressed(){ 
		
		// before closing check if user has defined his name
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
        EditText lastname = (EditText) this.findViewById(R.id.editText2);
        firstnameString = firstname.getText().toString();
    	lastnameString = lastname.getText().toString();
        String origVal = getResources().getText(R.string.firstname).toString();
       
        if (!firstnameString.equalsIgnoreCase(origVal)) // user defined a name
        {	
        	// check whether to store name in database
           	// if user exits in database, do nothing, else add him
            String userPhone = telephonyManager.getDeviceId();

            managedb.open();
            Cursor c = managedb.GetEntries("users", new String[] {"firstName", "lastName", "id"}, "mobileNumber="+ userPhone, null, null, null, null);
            c.moveToFirst();
        	if (c.getCount()>0) // user is in database
        	{
        		// modify name if needed
        		managedb.updateUserName(c.getInt(2), firstnameString, lastnameString);
        	}
        	else
        	{
        		// user not in database, add him
        		ContentValues usertoadd = new ContentValues();
    			usertoadd.put("firstName", firstnameString);
    			usertoadd.put("lastName", lastnameString);
    			usertoadd.put("mobileNumber", userPhone);
    			managedb.insertEntriesdb("users", usertoadd);
        	}
        	managedb.close();
        }
        finish();
	}
}
