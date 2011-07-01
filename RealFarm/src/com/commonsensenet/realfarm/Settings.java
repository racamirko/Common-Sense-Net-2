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
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getLine1Number(); //getDeviceId();
		EditText firstname = (EditText) this.findViewById(R.id.editText1);
        EditText lastname = (EditText) this.findViewById(R.id.editText2);
        
		origFirstname = firstname.getText().toString();
		origLastname = lastname.getText().toString();
		
		realFarm mainApp = ((realFarm)getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		mDataProvider = new RealFarmProvider(db);

		if (deviceID != null){
			String[] name = mDataProvider.getUserInfo(deviceID);
	        firstname.setText(name[0]);
	        lastname.setText(name[1]);
		}       
		else { // user must insert a sim card
			Toast.makeText(getApplicationContext(), "Insert SIM card",Toast.LENGTH_SHORT).show();
		}
			
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
