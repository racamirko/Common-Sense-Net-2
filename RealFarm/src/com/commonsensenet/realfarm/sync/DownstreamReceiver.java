package com.commonsensenet.realfarm.sync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Model;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

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

				// tracks that a message has been received.
				ApplicationTracker.getInstance().logSyncEvent(EventType.SYNC,
						"DOWNSTREAM", str);

				// gets the type of message received.
				String messageType = separated1[1];

				for (int i = 2; i < separated1.length; i++) {

					String[] separated2 = separated1[i].split("#");

					if (Integer.valueOf(messageType) == 1000) {
						// actions insertions

						Date date1 = null;
						try {
							date1 = DATE_FORMATTER.parse(separated2[3]);

							System.out.println("try in date" + date1);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						mDataProvider.addAction(Long.valueOf(separated2[0]),
								Integer.valueOf(separated2[1]),
								Long.valueOf(separated2[2]), date1,
								Integer.valueOf(separated2[4]),
								Integer.valueOf(separated2[5]),
								Double.valueOf(separated2[6]),
								Double.valueOf(separated2[7]),
								Integer.valueOf(separated2[8]),
								Integer.valueOf(separated2[9]),
								Integer.valueOf(separated2[10]),
								Integer.valueOf(separated2[11]),
								Integer.valueOf(separated2[12]),
								Long.valueOf(separated2[13]),
								Model.STATUS_CONFIRMED,
								Integer.valueOf(separated2[14]),
								Long.valueOf(separated2[15]));

					} else if (Integer.valueOf(messageType) == 1001) {

						mDataProvider.addPlot(Long.valueOf(separated2[0]),
								Long.valueOf(separated2[1]),
								Integer.valueOf(separated2[2]), separated2[4],
								Integer.valueOf(separated2[3]),
								Double.valueOf(separated2[5]), 0,
								Integer.valueOf(separated2[6]),
								Integer.valueOf(separated2[7]),
								Long.valueOf(separated2[8]),
								Integer.valueOf(separated2[9]));

					} else if (Integer.valueOf(messageType) == 1002) {

						mDataProvider.addUser(separated2[0], separated2[1],
								separated2[2], separated2[3], separated2[4],
								separated2[5], separated2[6], 0,
								Integer.valueOf(separated2[7]),
								Integer.valueOf(separated2[8]),
								Long.valueOf(separated2[9]));
					} else if (Integer.valueOf(messageType) == 1003) {

						mDataProvider.addWeatherForecast(separated2[0],
								Integer.valueOf(separated2[1]), separated2[2]);
					} else if (Integer.valueOf(messageType) == 1004) {

						String sep0 = separated2[0];
						int sep1 = Integer.valueOf(separated2[1]);
						int sep2 = Integer.valueOf(separated2[2]);
						String sep3 = separated2[3];
						mDataProvider.addMarketPrice(sep0, sep1, sep2, sep3);

					} else if (Integer.valueOf(messageType) == 1005) {

						mDataProvider.addRecommendation(
								Long.valueOf(separated2[0]),
								Long.valueOf(separated2[1]),
								Long.valueOf(separated2[2]),
								Long.valueOf(separated2[3]),
								Long.valueOf(separated2[4]),
								Integer.valueOf(separated2[5]),
								Long.valueOf(separated2[6]),
								Integer.valueOf(separated2[7]),
								Integer.valueOf(separated2[8]),
								Integer.valueOf(separated2[9]));
					}
				}

				// stops the SMS message from being broadcasted
				this.abortBroadcast();
			}

			// ---launch the SMSActivity---
			// Intent mainActivityIntent = new Intent(context,
			// SMSActivity.class);
			// mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(mainActivityIntent);

			// ---send a broadcast intent to update the SMS received in the
			// activity---
			// Intent broadcastIntent = new Intent();
			// broadcastIntent.setAction("SMS_RECEIVED_ACTION");
			// broadcastIntent.putExtra("sms", str);
			// context.sendBroadcast(broadcastIntent);

		}
	}
}