package com.example.CSN2;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class csn extends Activity {
	
	Toast mToast;
	
	static int market=0;
	static int news=0;
	static int radio=0;
	static int weather=0;
	
	private EditText text;
	//private EditText text2;
	
	String STR_MARKET="Market ";
	String STR_NEWS="News ";
	String STR_RADIO="Radio ";
	String STR_WEATHER="Weather ";
	
	
	
	String phoneNo="5556";
	String send_str="";
	String save_str="";
	String read_str="";
	
	DBAdapter csn_db = new DBAdapter(this);
	Cursor m;
	Cursor n;
	Cursor r;
	Cursor w;

	 private TextView mDateDisplay;
	 
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mSecond;

	IntentFilter intentFilter;
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
		//---display the SMS received in the TextView---
		TextView SMSes = (TextView) findViewById(R.id.textView3);
		//SMSes.setText("IISC");
		SMSes.setText(intent.getExtras().getString("sms"));
		
		csn_db.open();
		m = csn_db.getContact(1);
		n = csn_db.getContact(2);
		r = csn_db.getContact(3);
		w = csn_db.getContact(4);
		
		send_str =STR_MARKET+m.getString(2)+"\n"+STR_NEWS+n.getString(2)+"\n"+STR_RADIO+r.getString(2)+"\n"+STR_WEATHER+w.getString(2);
		sendSMS(phoneNo,send_str);
		
		csn_db.close();
		
		}
		};
	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDateDisplay = (TextView) findViewById(R.id.timestamp);
        
        Button button;
        button = (Button)findViewById(R.id.start_repeating);
        button.setOnClickListener(mStartRepeatingListener);
        button = (Button)findViewById(R.id.stop_repeating);
        button.setOnClickListener(mStopRepeatingListener);
        
        
        csn_db.open();
        
        /* This part of the code must be run the first time the application is installed
         * csn_db.insertContact("Market", "0");
        csn_db.insertContact("News", "0");
        csn_db.insertContact("Radio", "0");
        csn_db.insertContact("Weather", "0"); */
        
        
       Cursor c = csn_db.getAllContacts();
        if (c.moveToFirst())
        {
        do {
        DisplayContact(c);
        } while (c.moveToNext());
        } 
        csn_db.close();
          
      //---intent to filter for SMS messages received---
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        
        text = (EditText) findViewById(R.id.editText1);
      //  text2 = (EditText) findViewById(R.id.editText2);
           
       }
    public void DisplayContact(Cursor c)
    {
    Toast.makeText(this, "id: " + c.getString(0) +
  		  " Field: " + c.getString(1) +
  		  " Value: " + c.getString(2),
  		  Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onResume() {
    	
    //---register the receiver---
    registerReceiver(intentReceiver, intentFilter);
    super.onResume();
    }
    @Override
    protected void onPause() {
    //---unregister the receiver---
    unregisterReceiver(intentReceiver);
    super.onPause();
    }
    
    private OnClickListener mStartRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            // Note that unlike above, this IntentSender is configured to
            // allow itself to be sent multiple times.
            Intent intent = new Intent(csn.this, AutomaticUpdate.class);
            PendingIntent sender = PendingIntent.getBroadcast(csn.this,
                    0, intent, 0);

            // We want the alarm to go off 30 seconds from now.
            long firstTime = SystemClock.elapsedRealtime();
            firstTime += 60*1000;

            // Schedule the alarm!
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            firstTime, 60*1000, sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(csn.this, "Automatic 1 minute Update Started",
                    Toast.LENGTH_SHORT);
            mToast.show();
        }
    };
    
   
    private OnClickListener mStopRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // Create the same intent, and thus a matching IntentSender, for
            // the one that was scheduled.
            Intent intent = new Intent(csn.this, AutomaticUpdate.class);
            PendingIntent sender = PendingIntent.getBroadcast(csn.this,
                    0, intent, 0);

            // And cancel the alarm.
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.cancel(sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(csn.this, "Automatic Update Cancelled",
                    Toast.LENGTH_SHORT);
            mToast.show();
        }
    };
    
    
    public void sendSMS(String phoneNumber, String message)
    
    {
 	   String SENT= "SMS_SENT";
 	   String DELIVERED= "SMS_DELIVERED";
 	   PendingIntent sentPI= PendingIntent.getBroadcast(this, 0,new Intent(SENT),0);
 	   PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
 	   // WHEN THE SMS HAS BEEN SENT
 	   registerReceiver(new BroadcastReceiver(){
 		   @Override
 		   public void onReceive(Context arg0, Intent arg1){
 			   switch(getResultCode())
 			   {
 			   case Activity.RESULT_OK:
 				   Toast.makeText(getBaseContext(),"SMS sent",Toast.LENGTH_SHORT).show();
 				   break;
 			   case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
 				   Toast.makeText(getBaseContext(),"Generic failure",Toast.LENGTH_SHORT).show();
 				   break;
 			   case SmsManager.RESULT_ERROR_NO_SERVICE:
 				   Toast.makeText(getBaseContext(),"No Sevice",Toast.LENGTH_SHORT).show();
 				   break;
 			   case SmsManager.RESULT_ERROR_NULL_PDU:
 				   Toast.makeText(getBaseContext(),"Null PDU",Toast.LENGTH_SHORT).show();
 				   break;
 			   case SmsManager.RESULT_ERROR_RADIO_OFF:
 				   Toast.makeText(getBaseContext(),"Radio off",Toast.LENGTH_SHORT).show();
 				   break;
 			   }
 		   }
 	   },new IntentFilter(SENT));
 	   // when sms has been delivered
 	   registerReceiver(new BroadcastReceiver(){
 		   @Override
 		   public void onReceive(Context arg0, Intent arg1){
 			   switch (getResultCode())
 			   {
 			   case Activity.RESULT_OK:
 				   Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
 				   break;
 			   case Activity.RESULT_CANCELED:
 				   Toast.makeText(getBaseContext(), "SMS not Delivered", Toast.LENGTH_SHORT).show();
 				   break;
 			   }
 		   }
 	   }, new IntentFilter(DELIVERED));
 	   
 	   SmsManager sms= SmsManager.getDefault();
 	   sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
 			      
    } 
     
	public void myClickHandler(View view) {
	
		switch (view.getId()) {
		
		case R.id.button1:
			
			csn_db.open();
			 m = csn_db.getContact(1);
			market=Integer.valueOf(m.getString(2));
			market++;
			text.setText(STR_MARKET.concat(String.valueOf(market)));
			updateDisplay();
			csn_db.updateContact(1, "Market",String.valueOf(market));
			csn_db.close();
			
			break;
			
		case R.id.button2:
			
			csn_db.open();
			 n = csn_db.getContact(2);
			news=Integer.valueOf(n.getString(2));
			news++;
			text.setText(STR_NEWS.concat(String.valueOf(news)));
			updateDisplay();
			csn_db.updateContact(2, "News",String.valueOf(news));
			csn_db.close();
			
			break;
			
		case R.id.button3:
			
			csn_db.open();
			r = csn_db.getContact(3);
			radio=Integer.valueOf(r.getString(2));
			radio++;
			text.setText(STR_RADIO.concat(String.valueOf(radio)));
			updateDisplay();
			csn_db.updateContact(3, "Radio",String.valueOf(radio));
			csn_db.close();
			
			break;
			
		case R.id.button4:
			csn_db.open();
			w = csn_db.getContact(4);
			weather=Integer.valueOf(w.getString(2));
			weather++;
			text.setText(STR_WEATHER.concat(String.valueOf(weather)));
			updateDisplay();
			csn_db.updateContact(4, "Weather",String.valueOf(weather));
			csn_db.close();
			
			break;
			
		case R.id.button5:
			
			csn_db.open();
			m = csn_db.getContact(1);
			n = csn_db.getContact(2);
			r = csn_db.getContact(3);
			w = csn_db.getContact(4);
			send_str =STR_MARKET+m.getString(2)+"\n"+STR_NEWS+n.getString(2)+"\n"+STR_RADIO+r.getString(2)+"\n"+STR_WEATHER+w.getString(2);
			sendSMS(phoneNo,send_str);
			
			csn_db.close();
			break;
					
		}
    }
	private void updateDisplay() {
		
   	 final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        mSecond = cal.get(Calendar.SECOND);
        
        mDateDisplay.setText(
           new StringBuilder()
                   // Month is 0 based so add 1
                   .append(mMonth + 1).append("-")
                   .append(mDay).append("-")
                   .append(mYear).append(" ")
                   .append(mHour).append(":")
                   .append(mMinute).append(" ")
                   .append(mSecond).append("."));
   }

	
}