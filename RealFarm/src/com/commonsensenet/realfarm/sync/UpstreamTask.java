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
import com.commonsensenet.realfarm.model.Model;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

/**
 * Recurring Task that implements your business logic. The BuzzBox SDK Scheduler
 * will take care of running the doWork method according to the scheduling.
 * 
 */
public class UpstreamTask implements Task {

	/** Identifies when an SMS has been delivered. */
	private static final String DELIVERED = "SMS_DELIVERED";
	/** Identifies when an SMS has been sent. */
	private static final String SENT = "SMS_SENT";
	/** Phone number of the server. */
	public static final String SERVER_PHONE_NUMBER = "9742016861";

	/** List of Actions obtained from the Database. */
	private List<Action> mActionList;
	/** Access to the underlying database. */
	private RealFarmProvider mDataProvider;
	/** Receiver used to detect if an SMS was delivered correctly. */
	private BroadcastReceiver mDeliveredBroadcastReceiver;
	/** List of messages to send to the server. */
	private List<Model> mMessageList;
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

		// gets all the data from the server that has not being sent.
		mActionList = mDataProvider.getActionsBySendStatus(0);
		mPlotList = mDataProvider.getPlotsBySendStatus(0);
		mUserList = mDataProvider.getUsersBySendStatus(0);

		// initializes the list used to send the messages.
		mMessageList = new ArrayList<Model>();

		// adds all the elements into the message list.
		mMessageList.addAll(mUserList);
		mMessageList.addAll(mPlotList);
		mMessageList.addAll(mActionList);

		// sends all the messages to the server via SMS.
		for (int i = 0; i < mMessageList.size(); i++) {
			// tracks that the data that has been sent to the Server.
			ApplicationTracker.getInstance().logSyncEvent(EventType.SYNC,
					"Upstream", mMessageList.get(i).toSmsString());
			// sends the message.
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

	protected void sendMessage(ContextWrapper context, Model message) {

		Intent sentIntent = new Intent(SENT);
		sentIntent.putExtra("type", message.getModelTypeId());
		sentIntent.putExtra("id", message.getId());
		PendingIntent sentPI = PendingIntent.getBroadcast(
				context.getApplicationContext(), 0, sentIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent deliveredIntent = new Intent(DELIVERED);
		deliveredIntent.putExtra("type", message.getModelTypeId());
		deliveredIntent.putExtra("id", message.getId());
		PendingIntent deliveredPI = PendingIntent.getBroadcast(
				context.getApplicationContext(), 0, deliveredIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// gets the manager in charge of sending SMS.
		SmsManager sm = SmsManager.getDefault();
		// sends the messages from the phone number
		sm.sendTextMessage(SERVER_PHONE_NUMBER, null, message.toSmsString(),
				sentPI, deliveredPI);
	}
}
