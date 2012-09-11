package com.commonsensenet.realfarm.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.ContextWrapper;
import android.telephony.SmsManager;

import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;

/**
 * Recurring Task that implements your business logic. The BuzzBox SDK Scheduler
 * will take care of running the doWork method according to the scheduling.
 * 
 */
public class ReminderTask implements Task {
	protected PendingIntent sentPI;
	protected PendingIntent deliveredPI;
	private RealFarmProvider mDataProvider;
	
	public List<Action> ActionList;
	public List<Plot> PlotList;
	public List<User> UserList;
	
	ArrayList<String> action_list = new ArrayList<String>();
	ArrayList<String> plot_list = new ArrayList<String>();
	ArrayList<String> users_list = new ArrayList<String>();
	String ActionStr = "%1000%";             //Indicates message type--"action"  
	String PlotStr = "%1001%";				 //Indicates message type--"plot" 
	String UserStr = "%1002%";				 //Indicates message type--"users" 
	String number = "9742016861";              //Server number


	public String getTitle() {
		return "Reminder";
	}

	public String getId() {
		return "reminder"; // give it an ID
	}

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();
		 mDataProvider = RealFarmProvider.getInstance(ctx);

		System.out.println("All actions read , called from ReminderTask");

		
		ActionList=mDataProvider.getActionsBySentFlag(0);
		PlotList=mDataProvider.getPlotsBySentFlag(0);
		UserList=mDataProvider.getUsersBySentFlag(0);
		
		 
		 for(int x = 0; x < ActionList.size(); x++) {                //Putting actions together
				 
				 int seedTypeId=ActionList.get(x).getSeedTypeId();
				 if(Integer.valueOf(seedTypeId) == null)
				 {
					 seedTypeId=0;
				 }
				 int cropTypeId=ActionList.get(x).getCropTypeId();
				 if(Integer.valueOf(cropTypeId)==null)
				 {
					 cropTypeId=0;
				 }
				 
				 double quantity1=ActionList.get(x).getQuantity1();
				 if(Double.valueOf(quantity1)==null)
				 {
					 quantity1=0;
				 }
				 double quantity2=ActionList.get(x).getQuantity2();
				 if(Double.valueOf(quantity2)==null)
				 {
					 quantity2=0;
				 }
				 int unit1=ActionList.get(x).getUnit1();
				 if(Integer.valueOf(unit1)==null)
				 {
					 unit1=0;
				 }
				 int unit2=ActionList.get(x).getUnit2();
				 if(Integer.valueOf(unit2)==null)
				 {
					 unit2=0;
				 }
				 int res1=ActionList.get(x).getResource1Id();
				 if(Integer.valueOf(res1)==null)
				 {
					 res1=0;
				 }
				 int res2=ActionList.get(x).getResource2Id();
				 if(Integer.valueOf(res2)==null)
				 {
					 res2=0;
				 }
				 int price=ActionList.get(x).getPrice();
				 if(Integer.valueOf(price)==null)
				 {
					 price=0;
				 }
				 
				
				 action_list.add(
				 ActionList.get(x).getId()+"#"+ActionList.get(x).getActionTypeId()+ "#"
				 +ActionList.get(x).getPlotId()+ "#" +
				 ActionList.get(x).getDate()+ "#"+ seedTypeId + 
				 "#" + cropTypeId+
				 "#" + quantity1+ "#" + quantity2+
				 "#" + unit1+ "#" +unit2+ "#" +
				 res1+ "#" + res2 + "#" +
		 price+ "#" + ActionList.get(x).getUserId() + "#" +
				 ActionList.get(x).getIsAdminAction()+ "#" + ActionList.get(x).getTimetamp()
		+ "%"); }
		
		
		
		 for(int x = 0; x < PlotList.size(); x++) { plot_list.add(                //Putting plots together
				 PlotList.get(x).getId()+ "#"+ PlotList.get(x).getUserId()+ "#" +PlotList.get(x).getSeedTypeId()+ "#" +
				 PlotList.get(x).getSoilTypeId()+ "#" + PlotList.get(x).getImagePath()+
		  "#" + PlotList.get(x).getSize()+ "#" + PlotList.get(x).getIsEnabled()+
		  "#" + PlotList.get(x).getIsAdminFlag()+ "#" + PlotList.get(x).getTimestamp()+"#"+PlotList.get(x).getType()+
		 "%"); }
		 
		 for(int x = 0; x < UserList.size(); x++) { users_list.add(                //Putting users together
				 UserList.get(x).getId()+ "#" +UserList.get(x).getFirstname()+ "#" +
				 UserList.get(x).getLastname()+ "#" + UserList.get(x).getMobileNumber()+
		  "#" + UserList.get(x).getDeviceId()+ "#" + UserList.get(x).getImagePath()+
		  "#" + UserList.get(x).getLocation()+ "#" + UserList.get(x).getIsEnabled()+
		  "#" + UserList.get(x).getIsAdminAction()+
		  "#" + UserList.get(x).getTimestamp()+"%"); }

		 String[] actionArr = new String[action_list.size()];    //Has unsent  actions from database 
		  actionArr = action_list.toArray(actionArr);
	 
		 
					
		 // System.out.println(actionArr);
				  
		
		
			 for(int i=1;i<=actionArr.length;i++) {
				 System.out.println("Length: \n"+actionArr.length);
				 System.out.println(actionArr[i-1]);
				 ActionStr+=actionArr[i-1];
				 System.out.println("Actions log "+ActionStr);
				Send_message(ActionStr);                                  //Send actions to server via SMS
				 ActionStr = "%1000%";
				}
			
			
			 //*************End of actions sent******************
			
			 String[] plotArr = new String[plot_list.size()];    //Has unsent  plots from database 
			 plotArr = plot_list.toArray(plotArr);
		 
			
						
			 // System.out.println(plotArr);
					  
			 System.out.println( "**************DISPLAYING PLOTS ONLY IN REMINDERTASK************");
			
				 for(int i=1;i<=plotArr.length;i++) {
					 System.out.println("Length: \n"+plotArr.length);
					 System.out.println(plotArr[i-1]);
					 PlotStr+=plotArr[i-1];
					 System.out.println("plots log "+PlotStr);
					Send_message(PlotStr);		//Send plots to server via SMS
					 PlotStr = "%1001%";
					}
				
				 
				 //End of plots  sent
				 
				 String[] userArr = new String[users_list.size()];    //Has unsent  users from database 
				 userArr = users_list.toArray(userArr);
			 				
				  System.out.println("userArr"+userArr);
						  
			
					 for(int i=1;i<=userArr.length;i++) {
						 System.out.println("Length: \n"+userArr.length);
						 System.out.println(userArr[i-1]);
						 UserStr+=userArr[i-1];
						 System.out.println("users log "+UserStr);
						 Send_message(UserStr);		//Send users to server via SMS
						 UserStr = "%1002%";
						}
					 //System.out.println("users log "+UserStr);
		
					 
					 //End of users sent
					 
			//Setting the sent flag for action
					 for(int x = 0; x < ActionList.size(); x++) {                
						 mDataProvider.SentFlagForAction(ActionList.get(x).getId(), 1);
					 }
			//Setting the sent flag for plot
					 for(int x = 0; x < PlotList.size(); x++) {                
						 mDataProvider.SentFlagForPlot(PlotList.get(x).getId(), 1);
					 }
			//Setting the sent flag for user
					 for(int x = 0; x < UserList.size(); x++) {                
						 mDataProvider.SentFlagForUser(UserList.get(x).getId(), 1);
					 }
					 
						System.out.println("##################DISPLAYING ACTIONS IN REMINDER TASK############################");
						mDataProvider.getActions();
						System.out.println("##################FINISHED ACTIONS IN REMINDER TASK############################");
						
						System.out.println("##################DISPLAYING PLOTS IN REMINDER TASK############################");
						mDataProvider.getPlots();
						System.out.println("##################FINISHED PLOTS IN REMINDER TASK############################");
						
						
						System.out.println("##################DISPLAYING USERS IN REMINDER TASK############################");
						mDataProvider.getUsers();
						System.out.println("##################FINISHED USERS IN REMINDER TASK############################");
	
		return res;
	}

	public void Send_message(String send) {

		SmsManager sm = SmsManager.getDefault();
		// here is where the destination of the text should go
		//String number = "9900917284";
		sm.sendTextMessage(number, null, send, null, null);
		// sm.sendMultipartTextMessage(number, null,action_list , null, null);
		System.out.println("/*******************MESSAGE SENT FROM REMINDERTASK**************/");
		// Log.d("Msg Sent: ", "MSG sent ..");
		// TODO implement your business logic here
		// i.e. query the DB, connect to a web service using HttpUtils, etc..

	}

}
