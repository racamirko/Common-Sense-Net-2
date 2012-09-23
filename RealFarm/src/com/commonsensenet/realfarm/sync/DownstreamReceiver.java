package com.commonsensenet.realfarm.sync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Model;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class DownstreamReceiver extends BroadcastReceiver {

	/** SimpleDataFormat used to parse the obtained dates. */
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			RealFarmProvider.DATE_FORMAT);

	/** Access to the underlying Database. */
	private RealFarmProvider mDataProvider;

	@SuppressLint("ParserError")
	@Override
	public void onReceive(Context context, Intent intent) {

		// gets the data access object.
		mDataProvider = RealFarmProvider.getInstance(context);

		// checks if an SMS has been passed.
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";

		if (bundle != null) {
			// retrieves the received SMS.
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];

			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

				// gets the message body---
				str += msgs[i].getMessageBody().toString();
				Log.d("Received : ", str);
			}

			// splits the string using the '%'
			String[] separated1 = str.split("%");

			// checks if the message corresponds to a server message.
			// it needs to begin with '%100' and have 3 parts after splitting
			// using %
			if (str.indexOf("%100") == 0 && separated1.length == 3) {

				// stops the SMS message from being broadcasted
				this.abortBroadcast();

				// tracks that a message has been received.
				ApplicationTracker.getInstance().logSyncEvent(EventType.SYNC,
						"DOWNSTREAM", str);

				// gets the type of message received.
				int messageType = Integer.valueOf(separated1[1]);

				// extracts the message parts.
				String[] messageData = separated1[2].split("#");

				// result of the insertion.
				long result = -1;

				// inserts the action
				if (messageType == 1000) {

					Date date1 = null;
					try {
						date1 = DATE_FORMATTER.parse(messageData[3]);

						System.out.println("try in date" + date1);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					result = mDataProvider.addAction(
							Long.valueOf(messageData[0]),
							Integer.valueOf(messageData[1]),
							Long.valueOf(messageData[2]), date1,
							Integer.valueOf(messageData[4]),
							Integer.valueOf(messageData[5]),
							Double.valueOf(messageData[6]),
							Double.valueOf(messageData[7]),
							Integer.valueOf(messageData[8]),
							Integer.valueOf(messageData[9]),
							Integer.valueOf(messageData[10]),
							Integer.valueOf(messageData[11]),
							Integer.valueOf(messageData[12]),
							Long.valueOf(messageData[13]),
							Model.STATUS_CONFIRMED,
							Integer.valueOf(messageData[14]),
							Long.valueOf(messageData[15]));

					// plays the sound if the value was inserted.
					if (result != -1) {
						playNotificationSound();
					}

					// tracks the result of the insertion.
					ApplicationTracker.getInstance().logSyncEvent(
							EventType.SYNC, "DOWNSTREAM/ACTION",
							"result: " + result);

					// inserts the plot
				} else if (messageType == 1001) {

					// test if the Plot already existed.
					Plot tmpPlot = mDataProvider.getPlotById(
							Long.valueOf(messageData[0]),
							Long.valueOf(messageData[1]));

					if (tmpPlot == null) {

						result = mDataProvider.addPlot(
								Long.valueOf(messageData[0]),
								Long.valueOf(messageData[1]),
								Integer.valueOf(messageData[2]),
								messageData[4],
								Integer.valueOf(messageData[3]),
								Double.valueOf(messageData[5]),
								Model.STATUS_CONFIRMED,
								Integer.valueOf(messageData[6]),
								Integer.valueOf(messageData[7]),
								Long.valueOf(messageData[8]),
								Integer.valueOf(messageData[9]));

						// plays the sound if the value was inserted.
						if (result != -1) {
							playNotificationSound();
						}

						// tracks the result of the insertion.
						ApplicationTracker.getInstance().logSyncEvent(
								EventType.SYNC, "DOWNSTREAM/PLOT",
								"result: " + result);
					} else {
						// tracks the result of the insertion.
						ApplicationTracker.getInstance().logSyncEvent(
								EventType.SYNC, "DOWNSTREAM/PLOT", "duplicate");
					}

					// inserts the user
				} else if (messageType == 1002) {

					// tests if the User currently exists.
					User tmpUser = mDataProvider.getUserById(Long
							.valueOf(messageData[0]));

					if (tmpUser == null) {

						result = mDataProvider.addUser(messageData[0],
								messageData[1], messageData[2], messageData[3],
								messageData[4], messageData[5], messageData[6],
								Model.STATUS_CONFIRMED,
								Integer.valueOf(messageData[7]),
								Integer.valueOf(messageData[8]),
								Long.valueOf(messageData[9]));

						// plays the sound if the value was inserted.
						if (result != -1) {
							playNotificationSound();
						}

						// tracks the result of the insertion.
						ApplicationTracker.getInstance().logSyncEvent(
								EventType.SYNC, "DOWNSTREAM/USER",
								"result: " + result);
					} else {
						// tracks the result of the insertion.
						ApplicationTracker.getInstance().logSyncEvent(
								EventType.SYNC, "DOWNSTREAM/USER", "duplicate");
					}

					// inserts the weather forecast
				} else if (messageType == 1003) {

					try {

						// parses the base date from the server.
						Date baseDate = DATE_FORMATTER.parse(messageData[0]);

						// creates a calendar
						Calendar cal = GregorianCalendar.getInstance();
						cal.setTime(baseDate);

						String[] weatherData;
						for (int y = 1; y < messageData.length; y++, cal.add(
								Calendar.DAY_OF_MONTH, 1)) {
							weatherData = messageData[y].split("/");

							result = mDataProvider.addWeatherForecast(
									DATE_FORMATTER.format(cal.getTime()),
									Integer.valueOf(weatherData[0]),
									Integer.valueOf(weatherData[1]));

						}

						// plays the sound if the value was inserted.
						playNotificationSound();

					} catch (ParseException e) {
					}

					// tracks the result of the insertion.
					ApplicationTracker.getInstance().logSyncEvent(
							EventType.SYNC, "DOWNSTREAM/WEATHERFORECAST",
							"result: " + result);

				} else if (messageType == 1004) {

					// inserts the market price
					result = mDataProvider.addMarketPrice(messageData[0],
							Integer.valueOf(messageData[1]),
							Integer.valueOf(messageData[2]), messageData[3]);

					// plays the sound if the value was inserted.
					if (result != -1) {
						playNotificationSound();
					}

					// tracks the result of the insertion.
					ApplicationTracker.getInstance().logSyncEvent(
							EventType.SYNC, "DOWNSTREAM/MARKETPRICE",
							"result: " + result);

					// inserts the recommendation
				} else if (messageType == 1005) {

					// inserts the recommendation.
					result = mDataProvider.addRecommendation(
							Long.valueOf(messageData[0]),
							Long.valueOf(messageData[1]),
							Integer.valueOf(messageData[2]),
							Long.valueOf(messageData[3]), messageData[4],
							messageData[5], messageData[6],
							Integer.valueOf(messageData[7]),
							Integer.valueOf(messageData[8]),
							Integer.valueOf(messageData[9]));

					// plays the sound if the value was inserted.
					if (result != -1) {
						playNotificationSound();
					}

					// tracks the result of the insertion.
					ApplicationTracker.getInstance().logSyncEvent(
							EventType.SYNC, "DOWNSTREAM/RECOMMENDATION",
							"result: " + result);

				}
			}
		}
	}

	/**
	 * Plays a sound indicating that new data has arrived.
	 */
	protected void playNotificationSound() {
		// adds the sound to queue and plays it.
		SoundQueue.getInstance().addToQueue(R.raw.pong);
		SoundQueue.getInstance().play();
	}
}