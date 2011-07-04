package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;


public class Settings extends Activity {
	
	private TelephonyManager telephonyManager;
	private RealFarmProvider mDataProvider;

	private String origFirstname;
	private String origLastname;
	private boolean flagNewSim = false;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getLine1Number(); 
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
        EditText lastname = (EditText) this.findViewById(R.id.editText2);
		
		realFarm mainApp = ((realFarm)getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		mDataProvider = new RealFarmProvider(db);
		
		
		if (deviceID != null){ // sim car exists
			String[] name = mDataProvider.getUserInfo(deviceID); // get current information about user
			
			if (name[0] == null) { // if no information, try to fetch default number in case user configured phone without sim card
				name = mDataProvider.getUserInfo(RealFarmDatabase.DEFAULT_NUMBER);
				flagNewSim = true;
			}
			
	        firstname.setText(name[0]);
	        lastname.setText(name[1]);
		}       
		else { // user must insert a sim card, use default user mode
			Toast.makeText(getApplicationContext(), "Insert SIM card",Toast.LENGTH_SHORT).show();
			String[] name = mDataProvider.getUserInfo(RealFarmDatabase.DEFAULT_NUMBER);
	        firstname.setText(name[0]);
	        lastname.setText(name[1]);
		}

		origFirstname = firstname.getText().toString();
		origLastname = lastname.getText().toString();
		
	}
	
	
	@Override
	public void onBackPressed(){ 
		
		// before closing check if user has defined his name
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
        EditText lastname = (EditText) this.findViewById(R.id.editText2);
        String firstnameString = firstname.getText().toString();
    	String lastnameString = lastname.getText().toString();
        String deviceID = telephonyManager.getLine1Number();
        long result = 0;
        
        if (deviceID == null) // user has no sim card, use default number
        	deviceID= RealFarmDatabase.DEFAULT_NUMBER;
        
        if (flagNewSim == true) // user inserted a sim card
        	result = mDataProvider.setUserInfo(deviceID, firstnameString, lastnameString);
        
        // detect changes
        if ( (!firstnameString.equalsIgnoreCase(origFirstname)) || (!lastnameString.equalsIgnoreCase(origLastname)) ) // user changed something
        {	
            result = mDataProvider.setUserInfo(deviceID, firstnameString, lastnameString);
        }
        
        if (result > 0) { // success
			Toast.makeText(getApplicationContext(), "User created/modified",Toast.LENGTH_SHORT).show();
        }
        	
        
        finish();
	}
}
