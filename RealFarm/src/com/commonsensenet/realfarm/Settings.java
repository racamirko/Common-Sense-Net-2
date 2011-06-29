package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.database.ManageDatabase;


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
		String deviceID = telephonyManager.getLine1Number(); //getDeviceId();
		
		
		realFarm mainApp = ((realFarm)getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		RealFarmProvider mDataProvider = new RealFarmProvider(db);
		String[] name;
		if (deviceID != null){
			
			name = mDataProvider.getUserInfo(deviceID);
		
			EditText firstname = (EditText) this.findViewById(R.id.editText1);
	        EditText lastname = (EditText) this.findViewById(R.id.editText2);
	        
	        firstname.setText(name[0]);
	        lastname.setText(name[1]);
		}        
        
//		managedb.open();
//    	
//    	Cursor c = managedb.GetEntries(RealFarmDatabase.TABLE_NAME_USER, new String[] {RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, RealFarmDatabase.COLUMN_NAME_USER_LASTNAME}, RealFarmDatabase.COLUMN_NAME_USER_MOBILE + deviceID, null, null, null, null);
//    	
//    	if (c.getCount()>0) { // user exists in database
//    		c.moveToFirst();
//           	
//
//    		// display his information in box
//        	EditText firstname = (EditText) this.findViewById(R.id.editText1);
//            EditText lastname = (EditText) this.findViewById(R.id.editText2);
//            
//            firstname.setText(c.getString(0));
//            lastname.setText(c.getString(1));
//    	}
//    	managedb.close();
        
        
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
            Cursor c = managedb.GetEntries("users", new String[] {RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, RealFarmDatabase.COLUMN_NAME_USER_ID}, RealFarmDatabase.COLUMN_NAME_USER_MOBILE+ userPhone, null, null, null, null);
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
    			usertoadd.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstnameString);
    			usertoadd.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastnameString);
    			usertoadd.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, userPhone);
    			managedb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_USER, usertoadd);
        	}
        	managedb.close();
        }
        finish();
	}
}
