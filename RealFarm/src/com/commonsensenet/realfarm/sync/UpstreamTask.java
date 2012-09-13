package com.commonsensenet.realfarm.sync;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

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
public class UpstreamTask implements Task {

	/** Header used to identify the action header. */
	public static final String ACTION_HEADER = "%1000%";
	/** Identifies when an SMS has been delivered. */
	private static final String DELIVERED = "SMS_DELIVERED";
	/** Header used to identify a plot message. */
	public static final String PLOT_HEADER = "%1001%";
	/** Identifies when an SMS has been sent. */
	private static final String SENT = "SMS_SENT";
	/** Phone number of the server. */
	public static final String SERVER_PHONE_NUMBER = "9742016861";
	/** Header used to identify a user message. */
	public static final String USER_HEADER = "%1002%";

	/** List of Actions obtained from the Database. */
	private List<Action> mActionList;
	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	/** Receiver used to detect if an SMS was delivered correctly. */
	private BroadcastReceiver mDeliveredBroadcastReceiver;
	/** List of messages to send to the server. */
	private ArrayList<String> mMessageList;
	/** List of Plots obtained from the Database. */
	private List<Plot> mPlotList;
	/** Receiver used to detect if an SMS was sent correctly. */
	private BroadcastReceiver mSendBroadcastReceiver;
	/** List of Users obtained from the Database. */
	private List<User> mUserList;

	public void registerReceivers(Context context) {
		mSendBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(arg0, "SMS sent", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(arg0, "Generic failure", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(arg0, "No service", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(arg0, "Null PDU", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(arg0, "Radio off", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
		};

		// when the SMS has been sent
		context.registerReceiver(mSendBroadcastReceiver, new IntentFilter(SENT));

		mDeliveredBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(arg0, "SMS delivered", Toast.LENGTH_SHORT)
							.show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(arg0, "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		// when the SMS has been delivered
		context.registerReceiver(mDeliveredBroadcastReceiver, new IntentFilter(
				DELIVERED));
	}

	public TaskResult doWork(ContextWrapper ctx) {
		TaskResult res = new TaskResult();

		// adds the Broadcast receivers if needed.
		registerReceivers(ctx.getApplicationContext());

		// gets the database provider.
		mDataProvider = RealFarmProvider.getInstance(ctx);

		// gets all the data from the server.
		mActionList = mDataProvider.getActionsBySentFlag(0);
		mPlotList = mDataProvider.getPlotsBySentFlag(0);
		mUserList = mDataProvider.getUsersBySentFlag(0);

		// initializes the list used to send the messages.
		mMessageList = new ArrayList<String>();

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

		// sends all the messages to the server via SMS.
		for (int i = 0; i < mMessageList.size(); i++) {
			sendMessage(ctx, mMessageList.get(i));
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

	protected void sendMessage(ContextWrapper context, String message) {

		Intent sentIntent = new Intent(SENT);
		sentIntent.putExtra("smsNumber", 1);
		PendingIntent sentPI = PendingIntent.getBroadcast(
				context.getApplicationContext(), 0, sentIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent deliveredIntent = new Intent(DELIVERED);
		deliveredIntent.putExtra("smsNumber", 1);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(
				context.getApplicationContext(), 0, deliveredIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// gets the manager in charge of sending SMS.
		SmsManager sm = SmsManager.getDefault();
		// sends the messages from the phone number
		sm.sendTextMessage(SERVER_PHONE_NUMBER, null, message, sentPI,
				deliveredPI);
	}
}
