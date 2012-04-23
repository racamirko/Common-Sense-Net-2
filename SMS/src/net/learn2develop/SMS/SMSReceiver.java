package net.learn2develop.SMS;

import java.util.StringTokenizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if (i==0) {
                	//---get the sender address/phone number---
                	str += msgs[i].getOriginatingAddress();
                	//str += ": ";
                } 
                //---get the message body---
                str += msgs[i].getMessageBody().toString();                	
                System.out.println(10);
                Log.d("Received : ", "recvd Msg ..");
            }
            
            //---display the new SMS message---
       //System.out.print(str);
   
            Log.d("Processing Msg : ", "Begining to Process ..");   
      /*      StringTokenizer tokens = new StringTokenizer(str, " ");
            String first = tokens.nextToken();// this will contain "Fruit"
            String second = tokens.nextToken();// this will contain " they taste good"
            String third = tokens.nextToken();// this will contain " they taste good"
            String fourth = tokens.nextToken();// this will contain " they taste good"
            Log.d("first : ", first);  
            Log.d("second : ", second);  
            Log.d("third : ", third);  
            Log.d("fourth : ", fourth);  */
          
            
            String[] separated = str.split(" ");
            Log.d("first sep : ", separated[0]); 
            Log.d("second sep: ", separated[1]); 
            Log.d("third sep : ", separated[2]); 
            Log.d("fourth sep: ", separated[3]); 
            Log.d("fifth sep : ", separated[4]); 
            Log.d("6 sep: ", separated[5]); 
            Log.d("7 sep : ", separated[6]); 
            Log.d("8 sep: ", separated[7]); 
            if(separated[2].toString().equalsIgnoreCase("WF"))
            {
            	String et=separated[4];
            	int typeval= new Integer(et);
            	  Log.d("Type val: ", et); 
            	String type = null;
            	switch(typeval){
            	case 0:  type="Sunny"; break;
            	case 1:  type="Clear"; break;
            	case 2:  type="Overcast"; break;
            	case 3:  type="Mostly Cloudy"; break;
            	case 4:  type="Partly Cloudy"; break;
            	}
            //	Log.d("Its a weather forecast:", "WF");
              	String et1=separated[6];
            	int typeval1= new Integer(et1);
            	  Log.d("Type val: ", et1); 
            	String type1 = null;
            	switch(typeval1){
            	case 0:  type1="Sunny"; break;
            	case 1:  type1="Clear"; break;
            	case 2:  type1="Overcast"; break;
            	case 3:  type1="Mostly Cloudy"; break;
            	case 4:  type1="Partly Cloudy"; break;
            	}
            	
            	String wfmsg= "Today's forecast in CKP is " + type + " with Max Temperature around " + separated[3] + "\u00b0 C and Tomorrow's forecast is " + type1 + " with temperature around" 
            			+ separated[5] + "\u00b0 C";
            	 Toast.makeText(context, wfmsg, Toast.LENGTH_SHORT).show();
            }
        //    Log.d("Im out of WF:", "WF");
            if(separated[7].toString().equalsIgnoreCase("MP"))
            {
            	Log.d("Its a market price", "MP");
            	String mpmsg= "Today's Groundnut price in Pavagada is " + separated[9] + " Rs";
            	 Toast.makeText(context, mpmsg, Toast.LENGTH_SHORT).show();
            }
          // 	int len = str.length();
            	
         //  	 Toast.makeText(context, len, Toast.LENGTH_SHORT).show();
            
            /**************************************************************
             * These lines when commented does not display activity based text view
             * */
             
            //---stop the SMS message from being broadcasted---
            this.abortBroadcast();
            
            //---launch the SMSActivity---
            Intent mainActivityIntent = new Intent(context, SMSActivity.class);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);

            
            //---send a broadcast intent to update the SMS received in the activity---
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
            /*************************************************************
                         */
        }
    }
}

