package com.example.CSN2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.telephony.SmsMessage;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle= intent.getExtras();
		SmsMessage[] msgs = null;
		String str= "";
		String SMSBody= "";
		String TEST_STR="GET";
		if(bundle !=null)
		{
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs= new SmsMessage[pdus.length];
			for(int i=0; i<msgs.length;i++)
			{
				msgs[i]= SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "SMS from" + msgs[i].getOriginatingAddress();
				str +=" :";
				str += msgs[i].getMessageBody().toString();
				str +="\n";
				SMSBody = msgs[i].getMessageBody().toString();
			}
			if (TEST_STR.equals(SMSBody))
			{
			Toast.makeText(context, str, Toast.LENGTH_LONG).show();
			//---send a broadcast intent to update the SMS received in the activity---
			
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction("SMS_RECEIVED_ACTION");
			broadcastIntent.putExtra("sms", str);
			context.sendBroadcast(broadcastIntent);
			
			
			}
			}
		}
	/*private void sendSMS(String phoneNumber, String message)
    
	   {  
		   SmsManager sms= SmsManager.getDefault();
		   sms.sendTextMessage(phoneNumber, null, message, null, null);
				      
	   }*/
	}
