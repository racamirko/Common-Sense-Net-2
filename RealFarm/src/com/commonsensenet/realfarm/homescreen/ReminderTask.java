package com.commonsensenet.realfarm.homescreen;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.buzzbox.mob.android.scheduler.NotificationMessage;
import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;

/**
 * Recurring Task that implements your business logic.
 * The BuzzBox SDK Scheduler will take care of running the doWork method according to
 * the scheduling.
 * 
 */
public class ReminderTask implements Task {
	PendingIntent sentPI, deliveredPI;
	
	
    public String getTitle() {                        
        return "Reminder";
    }
    
 
    public String getId() {                        
        return "reminder"; // give it an ID
    }
    
   
    public TaskResult doWork(ContextWrapper ctx) {
        TaskResult res = new TaskResult();
        
        SmsManager sm = SmsManager.getDefault();
     // here is where the destination of the text should go
   //  String number = "0789082881";
  //   sm.sendTextMessage(number, null, "Test SMS Message", null, null);
    	
        Log.d("Msg Sent: ", "MSG sent ..");   
        // TODO implement your business logic here
        // i.e. query the DB, connect to a web service using HttpUtils, etc..
             
        return res;
    }
	
}
