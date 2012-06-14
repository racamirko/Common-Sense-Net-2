package com.commonsensenet.realfarm;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.Cursor;



import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.overlay.ActionItem;
import com.commonsensenet.realfarm.overlay.QuickAction;
import com.commonsensenet.realfarm.model.User;



public class adminenter extends Activity{
	
	ContentValues User_Details = new ContentValues();
	ContentValues users = new ContentValues();
	public int Click_Count=0;
	  public Button btn_write, btn_read;
	  public EditText et_write, et_read, et_fileName1,et_fileName2,et_writtenText;

	   SQLiteDatabase db;
	   long dbreturn;
	   public RealFarmProvider mDataProvider;
	   final Context context = this;
	   EditText MobileNumber, firstname12,lastname12;  
	   private String deviceID;
	   
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
		 firstname12 = (EditText)findViewById(R.id.et_fileName1);                        //First name             
	       lastname12 = (EditText)findViewById(R.id.et_fileName2);
		MobileNumber = (EditText) this.findViewById(R.id.et_writtenText);
		Button OK = (Button) findViewById(R.id.OK);                          //Added
		                      // Mobile number
        
     
 	
 		mDataProvider = RealFarmProvider.getInstance(context);    //Working
 		
 		// add the event listeners
 		OK.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {
 				
 				
				 System.out.println("In OK button of settings");
				UserDetailsDatabase();
				 Intent adminintent = new Intent(adminenter.this, admincall.class);
	 				startActivity(adminintent);	
	 		       adminenter.this.finish();
                 }
 		});
        
        
          }
	
	

public void UserDetailsDatabase()
{
	
	System.out.println("User details is put to database");
	
	String firstname12String = firstname12.getText().toString();
	
	String lastname12String = lastname12.getText().toString();
	String MobileNumberString = MobileNumber.getText().toString();
	
//	deviceID = RealFarmDatabase.DEFAULT_NUMBER;
	
		System.out.println("User details is put to database");
		System.out.println(firstname12String);
		System.out.println(lastname12String);
		System.out.println(MobileNumberString);
		
		
	
		mDataProvider.setUserInfo(MobileNumberString,firstname12String,lastname12String);
	//	mDataProvider.setUserInfo("124","jljf","ldl");
		mDataProvider.getUserList();
		Toast.makeText(getBaseContext(), "User Details is put to Database",
		 		Toast.LENGTH_SHORT).show();
	

		
	 
}


}
