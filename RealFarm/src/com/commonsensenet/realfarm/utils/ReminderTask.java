package com.commonsensenet.realfarm.utils;

import java.util.ArrayList;
import java.util.List;

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

	public static final String ACTION_HEADER = "%1000%";
	public static final String PLOT_HEADER = "%1001%";
	/** Phone number of the server. */
	public static final String SERVER_PHONE_NUMBER = "9742016861";
	public static final String USER_HEADER = "%1002%";

	private ArrayList<String> action_list = new ArrayList<String>();
	private List<Action> mActionList;
	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	private List<Plot> mPlotList;
	private List<User> mUserList;
	private ArrayList<String> plot_list = new ArrayList<String>();
	private ArrayList<String> users_list = new ArrayList<String>();

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();
		mDataProvider = RealFarmProvider.getInstance(ctx);

		mActionList = mDataProvider.getActionsBySentFlag(0);
		mPlotList = mDataProvider.getPlotsBySentFlag(0);
		mUserList = mDataProvider.getUsersBySentFlag(0);

		// putting actions together
		for (int x = 0; x < mActionList.size(); x++) {

			action_list.add(mActionList.get(x).getId() + "#"
					+ mActionList.get(x).getActionTypeId() + "#"
					+ mActionList.get(x).getPlotId() + "#"
					+ mActionList.get(x).getDate() + "#"
					+ mActionList.get(x).getSeedTypeId() + "#"
					+ mActionList.get(x).getCropTypeId() + "#"
					+ mActionList.get(x).getQuantity1() + "#"
					+ mActionList.get(x).getQuantity2() + "#"
					+ mActionList.get(x).getUnit1() + "#"
					+ mActionList.get(x).getUnit2() + "#"
					+ mActionList.get(x).getResource1Id() + "#"
					+ mActionList.get(x).getResource2Id() + "#"
					+ mActionList.get(x).getPrice() + "#"
					+ mActionList.get(x).getUserId() + "#"
					+ mActionList.get(x).getIsAdminAction() + "#"
					+ mActionList.get(x).getTimetamp() + "%");
		}

		// putting plots together
		for (int x = 0; x < mPlotList.size(); x++) {
			plot_list.add(mPlotList.get(x).getId() + "#"
					+ mPlotList.get(x).getUserId() + "#"
					+ mPlotList.get(x).getSeedTypeId() + "#"
					+ mPlotList.get(x).getSoilTypeId() + "#"
					+ mPlotList.get(x).getImagePath() + "#"
					+ mPlotList.get(x).getSize() + "#"
					+ mPlotList.get(x).getIsEnabled() + "#"
					+ mPlotList.get(x).getIsAdminFlag() + "#"
					+ mPlotList.get(x).getTimestamp() + "#"
					+ mPlotList.get(x).getType() + "%");
		}

		// putting users together
		for (int x = 0; x < mUserList.size(); x++) {
			users_list.add(mUserList.get(x).getId() + "#"
					+ mUserList.get(x).getFirstname() + "#"
					+ mUserList.get(x).getLastname() + "#"
					+ mUserList.get(x).getMobileNumber() + "#"
					+ mUserList.get(x).getDeviceId() + "#"
					+ mUserList.get(x).getImagePath() + "#"
					+ mUserList.get(x).getLocation() + "#"
					+ mUserList.get(x).getIsEnabled() + "#"
					+ mUserList.get(x).getIsAdminAction() + "#"
					+ mUserList.get(x).getTimestamp() + "%");
		}

		// has unsent actions from database.
		String[] actionArr = new String[action_list.size()];
		actionArr = action_list.toArray(actionArr);

		// creates a buffer to create the action message.
		StringBuffer actionData = new StringBuffer(ACTION_HEADER);

		for (int i = 1; i <= actionArr.length; i++) {

			actionData.append(actionArr[i - 1]);

			// send the actions to server via SMS
			sendMessage(actionData.toString());
		}

		// has unsent plots from database.
		String[] plotArr = new String[plot_list.size()];
		plotArr = plot_list.toArray(plotArr);

		// creates the buffer where the plot data will be added.
		StringBuffer plotData = new StringBuffer(PLOT_HEADER);

		for (int i = 1; i <= plotArr.length; i++) {

			plotData.append(plotArr[i - 1]);

			// sends the plots to server via SMS
			sendMessage(plotData.toString());
		}

		// has unsent users from database.
		String[] userArr = new String[users_list.size()];
		userArr = users_list.toArray(userArr);

		// creates the buffer where the user data will be added.
		StringBuffer userData = new StringBuffer(USER_HEADER);

		for (int i = 1; i <= userArr.length; i++) {

			userData.append(userArr[i - 1]);

			// sends the users to server via SMS
			sendMessage(userData.toString());
		}

		// Setting the sent flag for action
		for (int x = 0; x < mActionList.size(); x++) {
			mDataProvider.SentFlagForAction(mActionList.get(x).getId(), 1);
		}
		// Setting the sent flag for plot
		for (int x = 0; x < mPlotList.size(); x++) {
			mDataProvider.SentFlagForPlot(mPlotList.get(x).getId(), 1);
		}
		// Setting the sent flag for user
		for (int x = 0; x < mUserList.size(); x++) {
			mDataProvider.SentFlagForUser(mUserList.get(x).getId(), 1);
		}

		return res;
	}

	public String getId() {
		return "reminder";
	}

	public String getTitle() {
		return "Reminder";
	}

	protected void sendMessage(String send) {

		// gets the manager in charge of sending SMS.
		SmsManager sm = SmsManager.getDefault();

		// sends the messages from the phone number
		sm.sendTextMessage(SERVER_PHONE_NUMBER, null, send, null, null);
	}
}
