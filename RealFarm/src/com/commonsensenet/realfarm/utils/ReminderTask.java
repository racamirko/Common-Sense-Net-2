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

	/** Header used to identify the action header. */
	public static final String ACTION_HEADER = "%1000%";
	/** Header used to identify a plot message. */
	public static final String PLOT_HEADER = "%1001%";
	/** Phone number of the server. */
	public static final String SERVER_PHONE_NUMBER = "9742016861";
	/** Header used to identify a user message. */
	public static final String USER_HEADER = "%1002%";

	/** List of Actions obtained from the Database. */
	private List<Action> mActionList;
	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	/** List of messages to send to the server. */
	private ArrayList<String> mMessageList;
	/** List of Plots obtained from the Database. */
	private List<Plot> mPlotList;
	/** List of Users obtained from the Database. */
	private List<User> mUserList;

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();

		// gets the database provider.
		mDataProvider = RealFarmProvider.getInstance(ctx);

		// gets all the data from the server.
		mActionList = mDataProvider.getActionsBySentFlag(0);
		mPlotList = mDataProvider.getPlotsBySentFlag(0);
		mUserList = mDataProvider.getUsersBySentFlag(0);

		// initializes the list used to send the messages.
		mMessageList = new ArrayList<String>();

		// putting actions together
		for (int x = 0; x < mActionList.size(); x++) {

			mMessageList.add(ACTION_HEADER + mActionList.get(x).getId() + "#"
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
			mMessageList.add(PLOT_HEADER + mPlotList.get(x).getId() + "#"
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
			mMessageList.add(USER_HEADER + mUserList.get(x).getId() + "#"
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

		// sends all the messages to the server.
		for (int i = 0; i < mMessageList.size(); i++) {

			// send the actions to server via SMS
			sendMessage(mMessageList.get(i));
		}

		// sets the flag for the sent actions.
		for (int x = 0; x < mActionList.size(); x++) {
			mDataProvider.setActionFlag(mActionList.get(x).getId(), 1);
		}
		// sets the flag for the sent plots.
		for (int x = 0; x < mPlotList.size(); x++) {
			mDataProvider.setPlotFlag(mPlotList.get(x).getId(), 1);
		}
		// sets the flag for the sent users.
		for (int x = 0; x < mUserList.size(); x++) {
			mDataProvider.setUserFlag(mUserList.get(x).getId(), 1);
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
