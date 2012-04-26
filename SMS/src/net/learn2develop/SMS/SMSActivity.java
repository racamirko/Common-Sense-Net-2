package net.learn2develop.SMS;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends Activity {
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";
	PendingIntent sentPI, deliveredPI;
	BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    IntentFilter intentFilter;
    
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //—-display the SMS received in the TextView—-
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            SMSes.setText(intent.getExtras().getString("sms"));
        }
    };
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(SENT), 0);

		deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);
		
        //—-intent to filter for SMS messages received—-
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
	}

	@Override
	public void onResume() {
		super.onResume();

        //---register the receiver---
        //registerReceiver(intentReceiver, intentFilter);
		  System.out.println(10);
          Log.d("Sent  : ", "sent Msg ..");
		//---create the BroadcastReceiver when the SMS is sent---
		smsSentReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();
					  System.out.println(10);
		                Log.d("sent success : ", "recvd Msg ..");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		//---create the BroadcastReceiver when the SMS is delivered---
		smsDeliveredReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}; 

		//---register the two BroadcastReceivers---
		registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));      
		registerReceiver(smsSentReceiver, new IntentFilter(SENT));
	}

	@Override
	public void onPause() {
		super.onPause();
		
        //---unregister the receiver---
        //unregisterReceiver(intentReceiver);

		//---unregister the two BroadcastReceivers---
		unregisterReceiver(smsSentReceiver);
		unregisterReceiver(smsDeliveredReceiver);    	
	}

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
        //---unregister the receiver---
        unregisterReceiver(intentReceiver);        
    }

	public void onClick(View v) {
		sendSMS("5554", " 1 WF 25 1 28 2 MP GN 3400");
	}
	
	public void onSMSIntentClick (View v) {
		Intent i = new 
				Intent(android.content.Intent.ACTION_VIEW);
		i.putExtra("address", "5556; 5558; 5560");

		i.putExtra("sms_body", "Hello How are you!");
		i.setType("vnd.android-dir/mms-sms");
		startActivity(i);
	}

	//—-sends an SMS message to another device—-
	private void sendSMS(String phoneNumber, String message)
	{
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
}