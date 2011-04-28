package com.example.CSN2;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import android.content.BroadcastReceiver;
//import android.content.IntentFilter;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class csn extends Activity {
	
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
	
	private static final int READ_BLOCK_SIZE = 500;
	
	
	IntentFilter intentFilter;
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
		//---display the SMS received in the TextView---
		TextView SMSes = (TextView) findViewById(R.id.textView3);
		//SMSes.setText("IISC");
		SMSes.setText(intent.getExtras().getString("sms"));
		send_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
		sendSMS(phoneNo,send_str);
		}
		};
	
	
	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      //---intent to filter for SMS messages received---
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        
        text = (EditText) findViewById(R.id.editText1);
      //  text2 = (EditText) findViewById(R.id.editText2);
           
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
			market++;
			text.setText(STR_MARKET.concat(String.valueOf(market)));
			
			try { // catches IOException below
		        
		        
				//FileOutputStream fOut = new FileOutputStream("csnlog.txt",false);  
		        FileOutputStream fOut = openFileOutput("csnlog.txt",MODE_PRIVATE);
		        OutputStreamWriter osw = new OutputStreamWriter(fOut);
		        
		        
		        // Write the string to the file
		        save_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
		        
		        osw.write(save_str);
		        
		        osw.flush();
		        osw.close();
		        
		        } 
			catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
			
			break;
		case R.id.button2:
			news++;
			text.setText(STR_NEWS.concat(String.valueOf(news)));
			
			try { // catches IOException below
		        
		        
				//FileOutputStream fOut = new FileOutputStream("csnlog.txt",false); 
		        FileOutputStream fOut = openFileOutput("csnlog.txt",MODE_PRIVATE);
		        OutputStreamWriter osw = new OutputStreamWriter(fOut);
		        
		        
		        // Write the string to the file
		        save_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
		        
		        osw.write(save_str);
		        
		        osw.flush();
		        osw.close();
		        
		        } 
			catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
			
			break;
		case R.id.button3:
			radio++;
			text.setText(STR_RADIO.concat(String.valueOf(radio)));
			
			try { // catches IOException below
		        
		        
				//FileOutputStream fOut = new FileOutputStream("csnlog.txt",false);  
		        FileOutputStream fOut = openFileOutput("csnlog.txt",MODE_PRIVATE);
		        OutputStreamWriter osw = new OutputStreamWriter(fOut);
		        
		        
		        // Write the string to the file
		        save_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
		        
		        osw.write(save_str);
		        
		        osw.flush();
		        osw.close();
		        
		        } 
			catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
			
			break;
		case R.id.button4:
			weather++;
			text.setText(STR_WEATHER.concat(String.valueOf(weather)));
			
			try { // catches IOException below
		       // boolean append= false;
				//FileOutputStream(File fOut, boolean append)
				  
				//FileOutputStream (fOut, FALSE);  
				//fOut = openFileOutput("csnlog.txt",MODE_WORLD_READABLE);
				
				//FileOutputStream fOut = new FileOutputStream("csnlog.txt",false);
				FileOutputStream fOut = openFileOutput("csnlog.txt",MODE_APPEND);
		        OutputStreamWriter osw = new OutputStreamWriter(fOut);
		
		        
		        
		        // Write the string to the file
		        save_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
		        
		        osw.write(save_str);
		        
		        osw.flush();
		        osw.close();
		        
		        } 
			catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
			
			break;
		case R.id.button5:
			
			try
			{
			FileInputStream fIn = openFileInput("csnlog.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			char[] inputBuffer = new char[READ_BLOCK_SIZE];
			
			int charRead;
			while ((charRead = isr.read(inputBuffer))>0)
			{
			//---convert the chars to a String---
			String readString = String.copyValueOf(inputBuffer, 0,charRead);
			read_str += readString;
			inputBuffer = new char[READ_BLOCK_SIZE];
			}
			//---set the EditText to the text that has been
			// read---
			
			}
			catch (IOException ioe) {
			ioe.printStackTrace();
			}
			
			//send_str =STR_MARKET+String.valueOf(market)+"\n"+STR_NEWS+String.valueOf(news)+"\n"+STR_RADIO+String.valueOf(radio)+"\n"+STR_WEATHER+String.valueOf(weather)+"\n";
			sendSMS(phoneNo,read_str);
			break;
			
				
				
				
				
		}
    }
	
}