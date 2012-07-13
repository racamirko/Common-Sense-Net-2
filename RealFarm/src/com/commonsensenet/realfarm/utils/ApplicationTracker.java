package com.commonsensenet.realfarm.utils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class ApplicationTracker {
	public enum EventType {
		CLICK, LONG_CLICK, PAGE_VIEW, ERROR
	}

	/**
	 * Format used to stored the data in the log. It corresponds to
	 * <code>[date] EventType activityName label </code>
	 * */
	private static final String DATA_ENTRY_FORMAT = "[%s] %s - %s - %s";
	/** Shorter format used to store data in the log. */
	private static final String DATA_ENTRY_FORMAT_SMALL = "[%s] %s - %s";
	/** Format used to store the date information. */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** Name of the file where the log is stored. */
	public static final String LOG_FILENAME = "UIlog.txt";
	/** Name of the folder where the log is located. */
	public static final String LOG_FOLDER = "/csn_app_logs";
	/** Identifier of the class used for logging. */
	private static final String LOG_TAG = "ApplicationTracker";
	/** Singleton instance of the ActionLogger. */
	private static ApplicationTracker sInstance = null;

	/**
	 * Gets the singleton instance of the ActionTracker.
	 * 
	 * @return an ActionTracker instance.
	 */
	public static ApplicationTracker getInstance() {
		if (sInstance == null) {
			Log.i(LOG_TAG, "Initializing sound system");
			sInstance = new ApplicationTracker();
		}
		return sInstance;
	}

	/** Data structure used to store locally the log. */
	private LinkedList<String> mActivityLog;
	/** Date format used in each log. */
	private SimpleDateFormat mDateFormat;
	/** Path where the log is stored in the SD card. */
	private String mExternalDirectoryLog;

	private ApplicationTracker() {
		// creates the date formatter.
		mDateFormat = new SimpleDateFormat(DATE_FORMAT);
		// initializes the list where the data will be stored.
		mActivityLog = new LinkedList<String>();
	}

	protected void File_Log_Create(String fileName, String data) {

		File mFile;
		FileWriter mFileWriter;
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/csn_app_logs");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// LoggedData is the file to which values will be written
		if (fileName == "value.txt") {
			mExternalDirectoryLog = Environment.getExternalStorageDirectory()
					.toString() + LOG_FOLDER;
			mFile = new File(mExternalDirectoryLog, fileName);

			try {
				mFileWriter = new FileWriter(mFile, true);
				mFileWriter.append(data);

				mFileWriter.close();
			} catch (Exception e) {
				Log.e("WRITE TO SD", e.getMessage());
			}

		}
		if (fileName == "UIlog.txt") {
			mExternalDirectoryLog = Environment.getExternalStorageDirectory()
					.toString() + LOG_FOLDER;
			mFile = new File(mExternalDirectoryLog, fileName);

			try {
				mFileWriter = new FileWriter(mFile, true);
				mFileWriter.append(data);
				mFileWriter.close();
			} catch (Exception e) {
				Log.e("WRITE TO SD", e.getMessage());
			}
		}
	}

	/**
	 * Forces the class to write the activity log
	 */
	// TODO: should output the content to the SD.
	public void flush() {

	}

	/**
	 * Logs an event that occurred in the application. Each event has a type,
	 * the name of the source that generated the event and then optionally any
	 * other parameter. This can be the name of the button, or any important
	 * value to record.
	 * 
	 * 
	 * @param eventType
	 *            type of the event.
	 * @param activityName
	 *            name of the activity that generated the object.
	 * @param args
	 *            additional values to log.
	 */
	public void logEvent(EventType eventType, String activityName,
			Object... args) {

		if (args.length == 0) {

			// synchronized access to the log since concurrent access could be
			// enabled.
			synchronized (mActivityLog) {
				mActivityLog.add(String
						.format(DATA_ENTRY_FORMAT_SMALL,
								mDateFormat.format(new Date()), eventType,
								activityName));
			}
		} else {

			// creates a string with all the availble objects.
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				// appends the next object.
				sb.append(args[i].toString());
				// appends a comma and a space.
				if (i + 1 < args.length) {
					sb.append(", ");
				}
			}
			// synchronized access to the log since concurrent access could be
			// enabled.
			synchronized (mActivityLog) {
				mActivityLog.add(String.format(DATA_ENTRY_FORMAT,
						mDateFormat.format(new Date()), eventType,
						activityName, sb.toString()));
			}

		}
	}
}
