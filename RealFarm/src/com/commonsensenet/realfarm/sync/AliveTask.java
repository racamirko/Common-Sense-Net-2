package com.commonsensenet.realfarm.sync;

import android.content.ContextWrapper;
import android.telephony.SmsManager;

import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

/**
 * Recurring Task that implements your business logic. The BuzzBox SDK Scheduler
 * will take care of running the doWork method according to the scheduling.
 * 
 */
public class AliveTask implements Task {

	/** Code used to identify an Alive message. */
	public static final int ALIVE_CODE = 1006;
	/** Phone number of the server. */
	public static final String SERVER_PHONE_NUMBER = "9742016861";

	/** List of Actions obtained from the Database. */
	private int mActionCount;
	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	/** List of Plots obtained from the Database. */
	private int mPlotCount;
	/** List of Users obtained from the Database. */
	private int mUserCount;

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();

		// gets the database provider.
		mDataProvider = RealFarmProvider.getInstance(ctx);

		// gets all the data from the server that has not being sent.
		mActionCount = mDataProvider.getActionCount();
		mPlotCount = mDataProvider.getPlotCount();
		mUserCount = mDataProvider.getUserCount();

		// sends the message.
		sendMessage("%" + ALIVE_CODE + "%" + mPlotCount + "#" + mUserCount
				+ "#" + mActionCount + "%");

		return res;
	}

	public String getId() {
		return "Ping";
	}

	public String getTitle() {
		return "Ping";
	}

	protected void sendMessage(String message) {

		// tracks that the data that has been sent to the Server.
		ApplicationTracker.getInstance().logSyncEvent(EventType.SYNC, "ALIVE",
				message);
		// gets the manager in charge of sending SMS.
		SmsManager sm = SmsManager.getDefault();
		// sends the messages from the phone number
		sm.sendTextMessage(SERVER_PHONE_NUMBER, null, message, null, null);
	}
}
