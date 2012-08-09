package com.commonsensenet.realfarm.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
		CLICK, ERROR, LONG_CLICK, ACTIVITY_VIEW
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
	/** Default value for the maximum size of the log that will force a flush. */
	public static final int DEFAULT_MAX_LOG_SIZE = 5;
	/** Name of the file where the log is stored. */
	public static final String LOG_FILENAME = "UIlog.txt";
	/** Name of the folder where the log is located. */
	public static final String LOG_FOLDER = "/.csn_app_logs";
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
	/**
	 * Size of the log that will force the ApplicationTracker to flush its data.
	 * Set this value to -1 to disable this feature.
	 */
	public int mMaxLogSize;

	private ApplicationTracker() {
		// creates the date formatter.
		mDateFormat = new SimpleDateFormat(DATE_FORMAT);
		// initializes the list where the data will be stored.
		mActivityLog = new LinkedList<String>();
		// initializes the value with the default
		mMaxLogSize = DEFAULT_MAX_LOG_SIZE;
	}

	/**
	 * Forces the class to write the activity log
	 */
	public void flush() {

		// cancels the flush operation if there is nothing to flush
		synchronized (mActivityLog) {
			if (mActivityLog.size() == 0) {
				return;
			}
		}

		File mFile;
		FileWriter mFileWriter;
		File folder = new File(Environment.getExternalStorageDirectory()
				+ LOG_FOLDER);

		// if the folder does not exist it is created.
		if (!folder.exists()) {
			folder.mkdir();
		}

		// gets the path of the folder where the data will be stored.
		mExternalDirectoryLog = folder.getAbsolutePath();

		// creates the log file.
		mFile = new File(mExternalDirectoryLog, LOG_FILENAME);

		try {
			// initializes the file writer
			mFileWriter = new FileWriter(mFile, true);
			// forces every line to be a log entry.
			PrintWriter pw = new PrintWriter(mFileWriter);

			// writes the stored files into the log file.
			synchronized (mActivityLog) {
				for (int x = 0; x < mActivityLog.size(); x++) {
					pw.println(mActivityLog.get(x));
				}

				// removes all current elements of the list.
				mActivityLog.clear();
			}

			// closes the file writer.
			mFileWriter.close();
		} catch (Exception e) {
			Log.e("WRITE TO SD", e.getMessage());
		}
	}

	public int getMaxLogSize() {
		return mMaxLogSize;
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

			// creates a string with all the available objects.
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

		// if the maximum size has been exceeded, the data is flushed
		// automatically.
		synchronized (mActivityLog) {
			if (mActivityLog.size() > mMaxLogSize && mMaxLogSize != -1) {
				flush();
			}
		}
	}

	public void setMaxLogSize(int value) {
		mMaxLogSize = value;
	}
}
