package com.example.CSN2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AutomaticUpdate extends BroadcastReceiver {
	
	

	@Override
	 public void onReceive(Context context, Intent intent)
    {
       String str="";
     sendSMS("5556","Hello IISc");
       
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("SMS_RECEIVED_ACTION");
		broadcastIntent.putExtra("sms", str);
		context.sendBroadcast(broadcastIntent);
        Toast.makeText(context, "Log Activity SMS Sent", Toast.LENGTH_SHORT).show();
        
    }
	private void sendSMS(String phoneNumber, String message)
    
	     {  
	      SmsManager sms= SmsManager.getDefault();
		   sms.sendTextMessage(phoneNumber, null, message, null, null);
				      
	   }
}
