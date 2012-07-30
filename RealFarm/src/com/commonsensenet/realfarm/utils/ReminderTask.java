package com.commonsensenet.realfarm.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.ContextWrapper;
import android.telephony.SmsManager;
import android.util.Log;

import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;

/**
 * Recurring Task that implements your business logic. The BuzzBox SDK Scheduler
 * will take care of running the doWork method according to the scheduling.
 * 
 */
public class ReminderTask implements Task {
	protected PendingIntent sentPI;
	protected PendingIntent deliveredPI;
	private RealFarmProvider mDataProvider;
	public List<Action> tmpList;
  ArrayList<String> action_list = new ArrayList<String>();
  String tempstr="";
	public String getTitle() {
		return "Reminder";
	}

	public String getId() {
		return "reminder"; // give it an ID
	}

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();
	    mDataProvider = RealFarmProvider.getInstance(ctx);
	    
		System.out.println("Action table called from ReminderTask");
	//	tmpList=mDataProvider.getActionBySentFlag(0);
		
/*		for(int x = 0; x < tmpList.size(); x++)
		{
			action_list.add(" "+
					tmpList.get(x).getId()+ " "
					+tmpList.get(x).getActionNameId()+ " "
					+ tmpList.get(x).getSeedTypeId()+ " "
					+ tmpList.get(x).getQuantity1()+ " "
					+ tmpList.get(x).getQuantity2()+ " "
					+ tmpList.get(x).getUnits()+ " "
					+ tmpList.get(x).getPlotId()+ " "
					+ tmpList.get(x).getTypeFert()+ " "
					+ tmpList.get(x).getProblemType()+ " "
					+ tmpList.get(x).getHarvestFeedback()+ " "
					+ tmpList.get(x).getSellingPrice()+ " "
					+ tmpList.get(x).getQualityOfSeed()+ " "
					+ tmpList.get(x).getSellType()+ " "
					+ tmpList.get(x).getSent()+ " "
					+ tmpList.get(x).getIsAdmin()+ " "
					+ tmpList.get(x).getDate()+ " "
					+ tmpList.get(x).getTreatment()+ " "
					+ tmpList.get(x).getPesticideType()+ " "
					+ tmpList.get(x).getIrrigateMethod()+ " "
					+ tmpList.get(x).getTimestamp()+ " "
					+ tmpList.get(x).getInterCrop()+ " "+"%");
		}
		
		String[] actionArr = new String[action_list.size()];
		actionArr = action_list.toArray(actionArr);
					
		//	System.out.println(actionArr);
			System.out.println("**************DISPLAYING STRING ONLY IN REMINDERTASK*************************");
			
			for(int i=0;i<actionArr.length;i++) 
			{
				tempstr+=actionArr[i];
			}
				System.out.println(tempstr);
*/    
	//	String actions="1#5#4#1#2#0#0#24.12#1#1%2#5#1#1#2#1#0#24.12%3#5#4#1#2#0#0#24.12#1#1%4#5#4#1#2#0#0#24.12#1#1%";
	//	String plots="1#35#2#3#3#0#0#10:30#10.3%2#35#2#3#3#0#0#10:30#10.3%";
	//	String user="1#35#4#Hendrik Knoche#1234567891#0#0#10:30%2#35#4#Hendrik Knoche#1234567891#0#0#10:30";
	
		
		String actions1="2000#1000%1#5#7#2#2#0#24.12#2#1#20%2#5#1#1#2#0#24.12#21%4#5#1#2#2#0#24.12#2#3#22%7#3#2#0#24.12#2#23%3#10#1#4#0#24.12#2#24%5#2#1#3#1#0#24.12#25%";
		String actions2="2000#1000%6#2#4#1#2000#1#0#24.12#26%";
		String plots="2000#1001%1#35#2#3#3#0#0#10:3024.12#10.3#1%2#36#3#4#4#0#0#10:3524.12#11.3#2%";
		String users="2000#1002%1#124567891#1#Hendrik#Knoche#124567891#0#1#10:3024.12%2#124567892#2#John#Doe#124567892#0#1#10:3524.12%";
	//	Send_message(actions1);
	//	Send_message(actions2);
	 //   Send_message(plots);
   // 	Send_message(users);
		return res;
	}
	
	public void Send_message(String send)
	{
		
		SmsManager sm = SmsManager.getDefault();
		// here is where the destination of the text should go
		 String number = "9900917284";
	 sm.sendTextMessage(number, null, send , null, null);
	//	 sm.sendMultipartTextMessage(number, null,action_list , null, null);
		 System.out.println("/*******************MESSAGE SENT FROM REMINDERTASK**************/");
	//	Log.d("Msg Sent: ", "MSG sent ..");
		// TODO implement your business logic here
		// i.e. query the DB, connect to a web service using HttpUtils, etc..

	}
	
}
