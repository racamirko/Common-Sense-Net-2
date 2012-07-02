package com.commonsensenet.realfarm.dataaccess;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.Fertilizing;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Harvesting;
import com.commonsensenet.realfarm.model.Irrigation;
import com.commonsensenet.realfarm.model.MarketPrice;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Problem;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.Selling;
import com.commonsensenet.realfarm.model.Sowing;
import com.commonsensenet.realfarm.model.Spraying;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;

public class RealFarmProvider {
	public abstract interface OnDataChangeListener {
		public abstract void onDataChanged(int WF_Size, String WF_Date,
				int WF_Value, String WF_Type, String WF_Date1, int WF_Value1,
				String WF_Type1, int WF_adminflag);
	}

	/** Date format used throughout the application. */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** Name of the folder where the log is located. */
	public static final String LOG_FOLDER = "/csn_app_logs";
	private static Map<Context, RealFarmProvider> sMapProviders = new HashMap<Context, RealFarmProvider>();
	private static OnDataChangeListener sWeatherForecastDataListener;

	public static RealFarmProvider getInstance(Context ctx) {
		if (!sMapProviders.containsKey(ctx))
			sMapProviders.put(ctx, new RealFarmProvider(new RealFarmDatabase(
					ctx)));
		return sMapProviders.get(ctx);
	}

	/** Cached ActionNames to improve performance. */
	private List<ActionName> mAllActionNames;
	/** Cached seeds to improve performance. */
	private List<Seed> mAllSeeds;
	private Calendar mCalendar = Calendar.getInstance();
	/** Real farm database access. */
	private RealFarmDatabase mDatabase;
	private String mDatabaseEntryDate;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);
	/** Path of the logging directory inside the SD card. */
	private String mExternalDirectoryLog;
	private File mFile;
	private File mFile1;
	private FileWriter mFileWriter;
	private FileWriter mFileWriter1;
	private int mMaxWeatherForecasts;

	protected RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDatabase = database;

		// used to force creation.
		mDatabase.open();
		mDatabase.close();
	}

	public void File_Log_Create(String FileNameWrite, String Data) {

		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/csn_app_logs");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// File file = new File(mExternalDirectoryLog, "Test5.txt");
		// //LoggedData is the file to which values will be written
		if (FileNameWrite == "value.txt") {
			mExternalDirectoryLog = Environment.getExternalStorageDirectory()
					.toString() + LOG_FOLDER;
			// mExternalDirectoryLog =
			// Environment.getExternalStorageDirectory().toString();
			mFile = new File(mExternalDirectoryLog, FileNameWrite);
			// fWriter =new FileWriter(file);

			try {
				mFileWriter = new FileWriter(mFile, true);
				mFileWriter.append(Data);
				// fWriter.newLine();
				mFileWriter.close();
				// Toast.makeText(this,
				// "Your text was written to SD Card successfully...",
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Log.e("WRITE TO SD", e.getMessage());
			}

		}
		if (FileNameWrite == "UIlog.txt") {
			mExternalDirectoryLog = Environment.getExternalStorageDirectory()
					.toString() + LOG_FOLDER;
			// mExternalDirectoryLog =
			// Environment.getExternalStorageDirectory().toString();
			mFile1 = new File(mExternalDirectoryLog, FileNameWrite);
			// fWriter =new FileWriter(file);

			try {
				mFileWriter1 = new FileWriter(mFile1, true);
				mFileWriter1.append(Data);
				// fWriter.newLine();
				mFileWriter1.close();
				// Toast.makeText(this,
				// "Your text was written to SD Card successfully...",
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Log.e("WRITE TO SD", e.getMessage());
			}
		}

	}

	public ActionName getActionNameById(int actionNameId) {

		mDatabase.open();

		ActionName tmpAction = null;

		Cursor c0 = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTIONNAME,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "="
								+ actionNameId, null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			tmpAction = new ActionName(actionNameId, c0.getString(0),
					c0.getString(1), c0.getInt(2), c0.getInt(3), c0.getInt(4));
		}
		c0.close();
		mDatabase.close();

		return tmpAction;
	}

	public List<ActionName> getActionNames() {

		if (mAllActionNames == null) {
			// opens the database.
			mDatabase.open();

			// query all actions
			Cursor c = mDatabase
					.getEntries(
							RealFarmDatabase.TABLE_NAME_ACTIONNAME,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
							null, null, null, null, null);
			c.moveToFirst();

			mAllActionNames = new LinkedList<ActionName>();

			if (c.getCount() > 0) {
				do {
					mAllActionNames.add(new ActionName(c.getInt(0), c
							.getString(1), c.getString(2), c.getInt(3), c
							.getInt(4), c.getInt(5)));

					String log = "ACTIONNAME_ID: " + c.getInt(0) + " ,NAME "
							+ c.getString(1) + " ,NAME_KANNADA: "
							+ c.getString(2) + " ,RESOURCE" + c.getInt(3)
							+ " AUDIO " + c.getInt(4) + " ADMINFLAG "
							+ c.getInt(5) + "\r\n";
					Log.d("action name values: ", log);

					if (Global.writeToSD == true) {
						File_Log_Create("value.txt", "Action names table \r\n");
						File_Log_Create("value.txt", log);
					}

				} while (c.moveToNext());
			}

			c.close();
			mDatabase.close();

		}

		return mAllActionNames;
	}

	public List<Action> getActions() {

		List<Action> tmpActions = new LinkedList<Action>();

		mDatabase.open();

		Cursor ca = mDatabase
				.getAllEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
								RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE });

		if (ca.getCount() > 0) {
			ca.moveToFirst();
			do {
				Action aa = new Action(ca.getInt(0), ca.getInt(1),
						ca.getInt(2), ca.getString(3), ca.getString(4),
						ca.getInt(5), ca.getInt(6), ca.getString(7),
						ca.getString(8), ca.getInt(9), ca.getInt(10),
						ca.getString(11), ca.getString(12), ca.getString(13),
						ca.getInt(14), ca.getString(15), ca.getString(16),
						ca.getInt(17), ca.getInt(18), ca.getString(19),
						ca.getString(20), ca.getString(21));
				tmpActions.add(aa);

				String log = "ACTION_ID: " + ca.getInt(0) + " ,ACTIONNAMEID: "
						+ ca.getInt(1) + " ,GROWINGID " + ca.getInt(2)
						+ ",ACTIONTYPE: " + ca.getString(3) + " ,SEEDVARIETY: "
						+ ca.getString(4) + " Quantity1: " + ca.getInt(5)
						+ " Quantity2: " + ca.getInt(6) + " Units: "
						+ ca.getString(7) + " , DAY: " + ca.getString(8)
						+ " , user id: " + ca.getInt(9) + " , plot id: "
						+ ca.getInt(10) + " ,TYPEOFFERTILIZER "
						+ ca.getString(11) + " , PROBLEMStype: "
						+ ca.getString(12) + " , , FEEDBACK: "
						+ ca.getString(13) + " , SELLINGPRICE: "
						+ ca.getInt(14) + " ,QUALITYOFSEED: "
						+ ca.getString(15) + " , sell TYPE: "
						+ ca.getString(16) + " , SENT: " + ca.getInt(17)
						+ " , ISADMIN: " + ca.getInt(18)
						+ " , ACTIONPERFORMEDDATE: " + ca.getString(19)
						+ " , TREATMENT: " + ca.getString(20)
						+ " , PESTICIDETYPE: " + ca.getString(21) + "\r\n";
				Log.d("values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "New Action table \r\n");
					File_Log_Create("value.txt", log);
				}
			} while (ca.moveToNext());

		}
		ca.close();
		mDatabase.close();

		return tmpActions;
	}

	public List<Action> getActionsByUserId(int userId) {

		List<Action> tmpActions = new LinkedList<Action>();

		mDatabase.open();

		Cursor ca = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
								RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE },
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID + "= "
								+ userId, null, null, null, null);

		if (ca.getCount() > 0) {
			ca.moveToFirst();
			do {
				Action aa = new Action(ca.getInt(0), ca.getInt(1),
						ca.getInt(2), ca.getString(3), ca.getString(4),
						ca.getInt(5), ca.getInt(6), ca.getString(7),
						ca.getString(8), ca.getInt(9), ca.getInt(10),
						ca.getString(11), ca.getString(12), ca.getString(13),
						ca.getInt(14), ca.getString(15), ca.getString(16),
						ca.getInt(17), ca.getInt(18), ca.getString(19),
						ca.getString(20), ca.getString(21));
				tmpActions.add(aa);

				String log = "ACTION_ID: " + ca.getInt(0) + " ,ACTIONNAMEID: "
						+ ca.getInt(1) + " ,GROWINGID " + ca.getInt(2)
						+ ",ACTIONTYPE: " + ca.getString(3) + " ,SEEDVARIETY: "
						+ ca.getString(4) + " Quantity1: " + ca.getInt(5)
						+ " Quantity2: " + ca.getInt(6) + " Units: "
						+ ca.getString(7) + " , DAY: " + ca.getString(8)
						+ " , user id: " + ca.getInt(9) + " , plot id: "
						+ ca.getInt(10) + " ,TYPEOFFERTILIZER "
						+ ca.getString(11) + " , PROBLEMStype: "
						+ ca.getString(12) + " , , FEEDBACK: "
						+ ca.getString(13) + " , SELLINGPRICE: "
						+ ca.getInt(14) + " ,QUALITYOFSEED: "
						+ ca.getString(15) + " , sell TYPE: "
						+ ca.getString(16) + " , SENT: " + ca.getInt(17)
						+ " , ISADMIN: " + ca.getInt(18)
						+ " , ACTIONPERFORMEDDATE: " + ca.getString(19)
						+ " , TREATMENT: " + ca.getString(20)
						+ " , PESTICIDETYPE: " + ca.getString(21);
				Log.d("values: ", log);
			} while (ca.moveToNext());

		}
		ca.close();
		mDatabase.close();

		return tmpActions;
	}

	public List<Action> getActionsByUserIdAndPlotId(int userId, int plotId) { // modified

		List<Action> tmpActions = new LinkedList<Action>();

		mDatabase.open();

		Cursor ca = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
								RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE },
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID + "= "
								+ userId + " AND "
								+ RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID
								+ "= " + plotId + "", null, null, null, null);

		if (ca.getCount() > 0) {
			ca.moveToFirst();
			do {
				Action aa = new Action(ca.getInt(0), ca.getInt(1),
						ca.getInt(2), ca.getString(3), ca.getString(4),
						ca.getInt(5), ca.getInt(6), ca.getString(7),
						ca.getString(8), ca.getInt(9), ca.getInt(10),
						ca.getString(11), ca.getString(12), ca.getString(13),
						ca.getInt(14), ca.getString(15), ca.getString(16),
						ca.getInt(17), ca.getInt(18), ca.getString(19),
						ca.getString(20), ca.getString(21));
				tmpActions.add(aa);

				String log = "ACTION_ID: " + ca.getInt(0) + " ,ACTIONNAMEID: "
						+ ca.getInt(1) + " ,GROWINGID " + ca.getInt(2)
						+ ",ACTIONTYPE: " + ca.getString(3) + " ,SEEDVARIETY: "
						+ ca.getString(4) + " Quantity1: " + ca.getInt(5)
						+ " Quantity2: " + ca.getInt(6) + " Units: "
						+ ca.getString(7) + " , DAY: " + ca.getString(8)
						+ " , user id: " + ca.getInt(9) + " , plot id: "
						+ ca.getInt(10) + " ,TYPEOFFERTILIZER "
						+ ca.getString(11) + " , PROBLEMStype: "
						+ ca.getString(12) + " , , FEEDBACK: "
						+ ca.getString(13) + " , SELLINGPRICE: "
						+ ca.getInt(14) + " ,QUALITYOFSEED: "
						+ ca.getString(15) + " , sell TYPE: "
						+ ca.getString(16) + " , SENT: " + ca.getInt(17)
						+ " , ISADMIN: " + ca.getInt(18)
						+ " , ACTIONPERFORMEDDATE: " + ca.getString(19)
						+ " , TREATMENT: " + ca.getString(20)
						+ " , PESTICIDETYPE: " + ca.getString(21);
				Log.d("values: ", log);
			} while (ca.moveToNext());

		}
		ca.close();
		mDatabase.close();

		return tmpActions;
	}

	public RealFarmDatabase getDatabase() {
		return mDatabase;
	}

	public Cursor getFertilizer() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_FERTILIZER, new String[] {
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_ID,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_NAME,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_AUDIO,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_STAGEID,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_UNITID,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_ADMINFLAG, });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + "STAGEID: " + c.getInt(3)
						+ " UNITID: " + c.getInt(4) + " ADMINFLAG: "
						+ c.getInt(5) + "\r\n";
				Log.d("fertilizer: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Fertilizer table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	public List<Fertilizing> getfertizing() {

		mDatabase.open();
		int fert = 4;

		List<Fertilizing> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ fert, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Fertilizing>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Fertilizing(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4), c
						.getString(5), c.getInt(6), c.getInt(7), c.getInt(8), c
						.getInt(9), c.getString(10)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,Type of fertilize: " + c.getString(3) + " ,units"
						+ c.getString(4) + "day " + c.getString(5)
						+ " user id " + c.getInt(6) + " plot id " + c.getInt(7)
						+ "sent  " + c.getInt(8) + " Is admin " + c.getInt(9)
						+ " action performed date " + c.getString(10) + "\r\n";
				Log.d("Fertilizing values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "fertilizing action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Growing> getGrowings() {
		mDatabase.open();

		List<Growing> growing = new ArrayList<Growing>();

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
				new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
						RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SOWINGDATE,
						RealFarmDatabase.COLUMN_NAME_GROWING_ADMINFLAG }, null,
				null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				growing.add(new Growing(c.getInt(0), c.getInt(1), c.getInt(2)));

				String log = "Growing id: " + c.getInt(0) + " ,Plot id: "
						+ c.getInt(1) + " ,seed id: " + c.getInt(2)
						+ " ,SOWINGDATE: " + c.getInt(3) + " ,adminFlag: "
						+ c.getInt(4) + "\r\n";
				Log.d("growings: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Growing table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return growing;

	}

	public List<Growing> getGrowingsByPlotId(int plotId) {
		mDatabase.open();

		List<Growing> growing = new ArrayList<Growing>();

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
				new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
						RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID },
				RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID + "=" + plotId,
				null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				growing.add(new Growing(c.getInt(0), c.getInt(1), c.getInt(2)));
			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return growing;

	}

	public List<Growing> getGrowingsByUserId(int userId) {

		// gets all the plots of the current user.
		List<Plot> tmpPlots = getPlotsByUserId(userId);

		List<Growing> growing = new ArrayList<Growing>();

		// obtains the growing information of all the available plots.
		for (int x = 0; x < tmpPlots.size(); x++) {
			// adds all the growing information from the given plot
			growing.addAll(getGrowingsByPlotId(tmpPlots.get(x).getId()));
		}

		return growing;
	}

	public List<Harvesting> getharvesting() {

		mDatabase.open();
		int harv = 8;

		List<Harvesting> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ harv, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Harvesting>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Harvesting(c.getInt(0), c.getString(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), c.getInt(6), c.getInt(7), c
								.getString(8), c.getInt(9), c.getInt(10), c
								.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,quantity2" + c.getInt(3) + "units "
						+ c.getString(4) + " day " + c.getString(5)
						+ " user  id " + c.getInt(6) + " plot id "
						+ c.getInt(7) + " harvest feedback " + c.getString(8)
						+ "sent  " + c.getInt(9) + " Is admin " + c.getInt(10)
						+ " action performed date " + c.getString(11) + "\r\n";
				;
				Log.d("harvesting values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "harvesting action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Irrigation> getirrigate() {

		mDatabase.open();
		int irrigate = 7; // id 7 from actionname table

		List<Irrigation> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATE_METHOD },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ irrigate, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Irrigation>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Irrigation(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4),
						c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c
								.getString(9), c.getString(10)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,Action name id: " + irrigate
						+ " ,QUANTITY1: " + c.getInt(2)

						+ " ,units" + c.getString(3) + "day " + c.getString(4)
						+ " user id " + c.getInt(5) + " plot id " + c.getInt(6)
						+ "sent  " + c.getInt(7) + " Is admin " + c.getInt(8)
						+ " action performed date " + c.getString(9)
						+ " method " + c.getString(10) + "\r\n";
				Log.d("Irrigation values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Irrigation action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: this name is incorrect
	public void getLog() {

		mDatabase.open();
		// User tmpUser = null;

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_LOG,
				new String[] { RealFarmDatabase.COLUMN_NAME_LOG_ID,
						RealFarmDatabase.COLUMN_NAME_LOG_NAME,
						RealFarmDatabase.COLUMN_NAME_LOG_VALUE,
						RealFarmDatabase.COLUMN_NAME_LOG_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "LOG_ID: " + c.getInt(0) + " ,LOG_NAME: "
						+ c.getString(1) + " ,LOG_VALUE: " + c.getInt(2)
						+ " ,LOG_ADMINFLAG: " + c.getInt(3) + "\r\n";
				Log.d("values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Log table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		c.close();
		mDatabase.close();

	}

	public List<MarketPrice> getMarketPrices() {
		List<MarketPrice> tmpList;

		// opens the database.
		mDatabase.open();
		Log.d("done: ", "in market price");
		// query all actions
		Cursor c = mDatabase.getEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, new String[] {
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG },
				null, null, null, null, null);
		c.moveToFirst();

		tmpList = new LinkedList<MarketPrice>();

		if (c.getCount() > 0) {
			do {
				tmpList.add(new MarketPrice(c.getInt(0), c.getString(1), c
						.getString(2), c.getInt(3), c.getInt(4)));

				String log = "MP_ID: " + c.getInt(0) + " ,MP_date "
						+ c.getString(1) + " ,MP_TYPE: " + c.getString(2)
						+ " ,MP_value" + c.getInt(3) + "MP_admin flag "
						+ c.getInt(4) + "\r\n";
				Log.d("MP values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "market price table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public Cursor getPesticides() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_PESTICIDE, new String[] {
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_ID,
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_NAME,
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + "ADMINFLAG: "
						+ c.getInt(3) + "\r\n";
				Log.d("pesticides: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Pesticides table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	public List<Plot> getPlotDelete(int delete) {

		mDatabase.open();
		// int delete=0;

		System.out.println("In getPlotDelete");

		List<Plot> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] {
						RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						// RealFarmDatabase.COLUMN_NAME_PLOT_PLOTID,
						RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "=" + delete,
				null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Plot>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Plot(c.getInt(0), c.getInt(1), c.getInt(2), c
						.getInt(3), c.getInt(4), c.getString(5),
						c.getString(6), delete, c.getInt(7)));

				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "
						+ c.getInt(1) + " ,PlotSeedTypeId: " + c.getInt(2)
						+ " ,point X: " + c.getInt(3) + " ,point Y: "
						+ c.getInt(4) + "PlotImage: " + c.getString(5)
						+ " ,SoilType: " + c.getString(6)

						+ " ,deleteFlag: " + delete + " ,AdminFlag: "
						+ c.getInt(7) + "\r\n";
				Log.d("plot values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Plot> getPlots() {

		// opens the database.
		List<Plot> plotList = new LinkedList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] {
						RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						// RealFarmDatabase.COLUMN_NAME_PLOT_PLOTID,
						RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG }, null,
				null, null, null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				plotList.add(new Plot(c.getInt(0), c.getInt(1), c.getInt(2), c
						.getInt(3), c.getInt(4), c.getString(5),
						c.getString(6), c.getInt(7), c.getInt(8)));

				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "
						+ c.getInt(1) + " ,PlotSeedTypeId: " + c.getInt(2)
						+ " ,point X: " + c.getInt(3) + " ,point Y: "
						+ c.getInt(4) + "PlotImage: " + c.getString(5)
						+ " ,SoilType: " + c.getString(6)

						+ " ,deleteFlag: " + c.getInt(7) + " ,AdminFlag: "
						+ c.getInt(8) + "\r\n";
				Log.d("plot values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "New plot  table \r\n");
					File_Log_Create("value.txt", log);
				}
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return plotList;
	}

	public List<Plot> getPlotsByUserId(int userId) {

		// opens the database.
		List<Plot> plotList = new LinkedList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] {
						RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						// RealFarmDatabase.COLUMN_NAME_PLOT_PLOTID,
						RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId, null,
				null, null, null);

		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				plotList.add(new Plot(c.getInt(0), userId, c.getInt(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), c.getInt(6), c.getInt(7)));

				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "
						+ userId + " ,PlotSeedTypeId: " + c.getInt(1)
						+ " ,point X: " + c.getInt(2) + " ,point Y: "
						+ c.getInt(3) + "PlotImage: " + c.getString(4)
						+ " ,SoilType: " + c.getString(5)

						+ " ,deleteFlag: " + c.getInt(6) + " ,AdminFlag: "
						+ c.getInt(7) + "\r\n";
				Log.d("plot values: ", log);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return plotList;
	}

	// modified(You can take seedtypyId corresponding to the userId and plotId)
	public List<Plot> getPlotsByUserIdAndDeleteFlag(int userId, int delete) {

		// opens the database.
		List<Plot> plotList = new LinkedList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND "
						+ RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "="
						+ delete + "", null, null, null, null);

		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				plotList.add(new Plot(c.getInt(0), userId, c.getInt(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), delete, c.getInt(6)));
/*
				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "
						+ userId + " ,PlotSeedTypeId: " + c.getInt(1)
						+ " ,point X: " + c.getInt(2) + " ,point Y: "
						+ c.getInt(3) + "PlotImage: " + c.getString(4)
						+ " ,SoilType: " + c.getString(5)

						+ " ,deleteFlag: " + delete + " ,AdminFlag: "
						+ c.getInt(6) + "\r\n";
				Log.d("plot values: ", log);*/
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return plotList;
	}

	// modified(You can take seedtypyId corresponding to the userId and plotId)
	public List<Plot> getPlotsByUserIdAndPlotId(int userId, int plotId) {
		// opens the database.
		List<Plot> plotList = new LinkedList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] {
						RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX,
						RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND " + RealFarmDatabase.COLUMN_NAME_PLOT_ID + "="
						+ plotId + "", null, null, null, null);

		// },RealFarmDatabase.COLUMN_NAME_ACTION_USERID + "= " +
		// userId + " AND " + RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + "= "
		// +
		// plotId + "", null, null, null, null);

		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				plotList.add(new Plot(plotId, userId, c.getInt(0), c.getInt(1),
						c.getInt(2), c.getString(3), c.getString(4), c
								.getInt(5), c.getInt(6)));

				String log = "PlotId: " + plotId + " ,PlotUserId: " + userId
						+ " ,PlotSeedTypeId: " + c.getInt(0) + " ,point X: "
						+ c.getInt(1) + " ,point Y: " + c.getInt(2)
						+ "PlotImage: " + c.getString(3) + " ,SoilType: "
						+ c.getString(4)

						+ " ,deleteFlag: " + c.getInt(5) + " ,AdminFlag: "
						+ c.getInt(6) + "\r\n";
				Log.d("plot values: ", log);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return plotList;
	}

	// Get WF data

	public List<Problem> getProblem() {

		mDatabase.open();
		int problem = 6; // id 7 from actionname table for reporting of problems

		List<Problem> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ problem, null, null, null, null);

		c.moveToFirst();
		tmpList = new LinkedList<Problem>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Problem(c.getInt(0), c.getString(1), c
						.getString(2), c.getInt(3), c.getInt(4),
						c.getString(5), c.getInt(6), c.getInt(7), c
								.getString(8)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,Actionnameid: " + problem
						+ "day " + c.getString(2) + " user id " + c.getInt(3)
						+ " plot id " + c.getInt(4) + " Problem type "
						+ c.getString(5) + "sent  " + c.getInt(6)
						+ " Is admin " + c.getInt(7)
						+ " action performed date " + c.getString(8) + "\r\n";
				Log.d("Problem values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Problem action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: should not return a cursor!
	public Cursor getProblems() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_PROBLEM,
				new String[] { RealFarmDatabase.COLUMN_NAME_PROBLEM_ID,
						RealFarmDatabase.COLUMN_NAME_PROBLEM_NAME,
						RealFarmDatabase.COLUMN_NAME_PROBLEM_AUDIO,
						RealFarmDatabase.COLUMN_NAME_PROBLEM_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_PROBLEM_PROBLEMTYPEID,
						RealFarmDatabase.COLUMN_NAME_PROBLEM_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + "RESOURCE: " + c.getInt(3)
						+ " PROBLEMTYPEID: " + c.getInt(4) + " ADMINFLAG: "
						+ c.getInt(5) + "\r\n";
				Log.d("problems: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Problems table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	// TODO: should not return a cursor!
	public Cursor getProblemType() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_PROBLEMTYPE, new String[] {
						RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_ID,
						RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_ADMINFLAG

				});

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + "RESOURCE: " + c.getInt(3)
						+ " ADMINFLAG: " + c.getInt(4) + "\r\n";
				Log.d("problems: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "ProblemType table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	public List<Recommendation> getRecommendations() {

		mDatabase.open();

		List<Recommendation> result = new ArrayList<Recommendation>();

		Cursor c = mDatabase.getEntries(
				RealFarmDatabase.TABLE_NAME_RECOMMENDATION, new String[] {
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_SEEDID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ACTIONID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_DATE },
				null, null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();

			do {
				result.add(new Recommendation(c.getInt(0), c.getInt(1), c
						.getInt(2), c.getString(3)));
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return result;

	}

	public Seed getSeedById(int seedId) {

		Seed res = null;
		mDatabase.open();
		Cursor c0 = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = new Seed(seedId, c0.getString(0), c0.getString(1),
					c0.getInt(2), c0.getInt(3), c0.getInt(4), c0.getString(5),
					c0.getString(6), c0.getInt(7), c0.getInt(8)); // modified
		}
		c0.close();
		mDatabase.close();
		return res;

	}

	public List<Seed> getSeeds() { // modified

		// seeds are not in cache
		if (mAllSeeds == null) {

			mAllSeeds = new ArrayList<Seed>();
			mDatabase.open();

			Cursor c0 = mDatabase
					.getAllEntries(
							RealFarmDatabase.TABLE_NAME_SEEDTYPE,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ADMINFLAG });

			if (c0.getCount() > 0) {
				c0.moveToFirst();
				do {
					Seed s = new Seed(c0.getInt(0), c0.getString(1),
							c0.getString(2), c0.getInt(3), c0.getInt(4),
							c0.getInt(5), c0.getString(6), c0.getString(7),
							c0.getInt(8), c0.getInt(9));
					mAllSeeds.add(s);

					String log = "id: " + c0.getInt(0) + " ,name: "
							+ c0.getString(1) + " ,namekannada: "
							+ c0.getString(2) + ",resource: " + c0.getInt(3)
							+ " ,audio: " + c0.getInt(4)
							+ " ,days to harvest: " + c0.getInt(5)
							+ " ,variety: " + c0.getString(6)
							+ " ,variety kannada: " + c0.getString(7)
							+ ",resource bg: " + c0.getInt(8) + " ,adminFlag: "
							+ c0.getInt(9) + "\r\n";
					Log.d("seed type: ", log);

					if (Global.writeToSD == true) {
						File_Log_Create("value.txt", "seed type table \r\n");
						File_Log_Create("value.txt", log);
					}
				} while (c0.moveToNext());

			}
			c0.close();
			mDatabase.close();
		}

		return mAllSeeds;
	}

	// TODO: incorrect name!
	public void getSeedTypeStages() {

		// List<User> tmpUsers = new ArrayList<User>();
		mDatabase.open();
		// User tmpUser = null;

		Cursor c = mDatabase
				.getAllEntries(
						RealFarmDatabase.TABLE_NAME_SEEDTYPESTAGE,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_STAGEID,
								RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID,
								RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS,
								RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS,
								RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.
				// tmpUsers.add(new User(c.getInt(0), c.getInt(1), c
				// .getInt(2), c.getInt(3)));

				String log = "STAGEID: " + c.getInt(0) + " ,SEEDTYPEID: "
						+ c.getInt(1) + " ,FROMCOUNTDAYS: " + c.getInt(2)
						+ ",TOCOUNTDAYS: " + c.getInt(3) + ",ADMINFLAG: "
						+ c.getInt(4) + "\r\n";
				Log.d("seed type stages: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Seed type stages table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		c.close();
		mDatabase.close();

	}

	public List<Selling> getselling() {

		mDatabase.open();
		int sell = 12;

		List<Selling> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
								RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ sell, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Selling>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Selling(c.getInt(0), c.getString(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), c.getInt(6), c.getInt(7), c.getInt(8),
						c.getString(9), c.getString(10), c.getInt(11), c
								.getInt(12), c.getString(13)));

				String log = "action id: " + c.getInt(0) + " ,action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,quantity2: " + c.getInt(3) + " ,units"
						+ c.getString(4) + "day " + c.getString(5)
						+ " user id " + c.getInt(6) + " plot id " + c.getInt(7)
						+ " selling price " + c.getInt(8) + " quality of seed "
						+ c.getString(9) + " selling type " + c.getString(10)
						+ "sent  " + c.getInt(11) + " Is admin " + c.getInt(12)
						+ " action performed date " + c.getString(13) + "\r\n";
				Log.d("selling values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "selling a \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Sowing> getsowing() {

		mDatabase.open();
		int sow = 3;

		List<Sowing> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ sow, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Sowing>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Sowing(c.getInt(0), c.getString(1),
						c.getInt(2), c.getString(3), c.getString(4), c
								.getString(5), c.getInt(6), c.getInt(7), c
								.getInt(8), c.getInt(9), c.getString(10), c
								.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,QUANTITY1: " + c.getInt(2)
						+ " ,seed variety: " + c.getString(3) + " ,units"
						+ c.getString(4) + "day " + c.getString(5)
						+ " user id " + c.getInt(6) + " plot id " + c.getInt(7)
						+ "sent  " + c.getInt(8) + " Is admin " + c.getInt(9)
						+ " action performed date " + c.getString(10)
						+ " treatment " + c.getString(11) + "\r\n";
				Log.d("sowing values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Sowing action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Spraying> getspraying() {

		mDatabase.open();
		int spray = 5;

		List<Spraying> tmpList;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE },
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ spray, null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<Spraying>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new Spraying(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4),
						c.getInt(5), c.getInt(6), c.getString(7), c.getInt(8),
						c.getInt(9), c.getString(10), c.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,action type"
						+ c.getString(1) + " ,quantity1" + c.getString(2)

						+ " ,units" + c.getString(3) + "day " + c.getString(4)
						+ " user id " + c.getInt(5) + " plot id " + c.getInt(6)
						+ " ,Problem type" + c.getString(7) + "sent  "
						+ c.getInt(8) + " Is admin " + c.getInt(9)
						+ " action performed date " + c.getString(10)
						+ " Pesticide type " + c.getString(11) + "\r\n";
				Log.d("spraying values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "spraying action \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public Cursor getStages() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_STAGE,
				new String[] { RealFarmDatabase.COLUMN_NAME_STAGE_ID,
						RealFarmDatabase.COLUMN_NAME_STAGE_NAME,
						RealFarmDatabase.COLUMN_NAME_STAGE_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " NAME: " + c.getString(1)
						+ "  ADMINFLAG: " + c.getInt(2) + "\r\n";
				Log.d("values: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Stages table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	// TODO: incorrect name!
	public void getUnit() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_UNIT,
				new String[] { RealFarmDatabase.COLUMN_NAME_UNIT_ID,
						RealFarmDatabase.COLUMN_NAME_UNIT_NAME,
						RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO,
						RealFarmDatabase.COLUMN_NAME_UNIT_ADMINFLAG });

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				// adds the users into the list.

				String log = "UNIT_ID: " + c.getInt(0) + " ,UNIT_NAME: "
						+ c.getString(1) + " ,UNIT_AUDIO: " + c.getInt(2)
						+ " ,UNIT_ADMIN FLAG: " + c.getInt(3) + "\r\n";

				Log.d("Unit: ", log);

				if (Global.writeToSD == true) {
					File_Log_Create("value.txt", "Unit table \r\n");
					File_Log_Create("value.txt", log);
				}

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		c.close();
		mDatabase.close();

	}

	public User getUserById(int userId) {
		mDatabase.open();
		User tmpUser = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = " + userId, null,
				null, null, null);

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();

			// tmpUser = new User(userId, c.getString(0), c.getString(1),
			// c.getString(2), c.getString(3));

			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2), c.getString(3), c.getInt(4), c.getInt(5));
		}

		c.close();
		mDatabase.close();

		return tmpUser;

	}

	public User getUserByMobile(String deviceId) {

		mDatabase.open();

		User tmpUser = null;
		String mobile;

		if (deviceId == null)
			mobile = RealFarmDatabase.DEFAULT_NUMBER;
		else
			mobile = deviceId;

		Cursor c = mDatabase
				.getEntries(RealFarmDatabase.TABLE_NAME_USER, new String[] {
						RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG },

						RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '"
								+ mobile + "'", null, null, null, null);

		if (c.getCount() > 0) { // user exists in database
			c.moveToFirst();

			// tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
			// mobile, c.getString(3));

			tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
					mobile, c.getString(3), c.getInt(4), c.getInt(5));
		}
		c.close();
		mDatabase.close();

		return tmpUser;
	}

	/**
	 * 
	 * @return integer number of users in the DB
	 */
	public int getUserCount() {
		mDatabase.open();
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID }, null,
				null, null, null, null);
		int userCount = c.getCount();
		c.close();
		mDatabase.close();
		return userCount;
	}

	public List<User> getUserDelete(int delete) {

		mDatabase.open();
		// int delete=0;

		System.out.println("In getuserDelete");

		List<User> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG },
				RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG + "=" + delete,
				null, null, null, null);

		//
		c.moveToFirst();
		tmpList = new LinkedList<User>();
		System.out.println(c.getCount());

		if (c.getCount() > 0) {
			do {
				tmpList.add(new User(c.getInt(0), c.getString(1), c
						.getString(2), c.getString(3), c.getString(4), delete,
						c.getInt(5)));

				String log = "user id: " + c.getInt(0) + " ,First name: "
						+ c.getString(1) + " ,Last name: " + c.getString(2)
						+ " ,Mobile: " + c.getString(3) + " ,Image"
						+ c.getString(4) + " delete flag " + delete
						+ "Admin flag " + c.getInt(5) + "\r\n";
				Log.d("sowing values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<User> getUsers() {
		// No caching, the number of users will be potentially large, and the
		// usage now doesn't require them all in memory.

		// opens the database.
		List<User> userList = new LinkedList<User>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG }, null,
				null, null, null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				userList.add(new User(c.getInt(0), c.getString(1), c
						.getString(2), c.getString(3), c.getString(4), c
						.getInt(5), c.getInt(6)));

				String log = "Id: " + c.getString(0) + " ,FirstName: "
						+ c.getString(1) + " ,LastName: " + c.getString(2)
						+ "Mobile: " + c.getString(3) + " ,Img: "
						+ c.getString(4) + " ,DeleteFlag: " + c.getInt(5)
						+ " ,AdminFlag: " + c.getInt(6) + "\r\n";
				Log.d("user: ", log);

				if (Global.writeToSD == true) {

					File_Log_Create("value.txt", "User table \r\n");
					File_Log_Create("value.txt", log);
				}
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return userList;
	}

	public List<WeatherForecast> getWeatherForecasts() {
		List<WeatherForecast> tmpList;

		// opens the database.
		mDatabase.open();
		Log.d("done: ", "in Wf getdata");
		// query all actions
		Cursor c = mDatabase.getEntries(
				RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, new String[] {
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE1,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE1,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE1,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
				null, null, null, null, null);
		c.moveToFirst();

		tmpList = new LinkedList<WeatherForecast>();

		if (c.getCount() > 0) {
			do {
				tmpList.add(new WeatherForecast(c.getString(1), c.getInt(2), c
						.getString(3), c.getString(4), c.getInt(5), c
						.getString(6), c.getInt(7)));

				String log = "WF_ID: " + c.getInt(0) + " ,WF_date "
						+ c.getString(1) + " ,WF_values: " + c.getInt(2)
						+ " ,WF_type" + c.getString(3) + "WF_date1 "
						+ c.getString(4) + " WF_value1 " + c.getInt(5)
						+ " WF_type11 " + c.getString(6);
				Log.d("WF values: ", log);

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished Wf getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public void Log_Database_backupdate() {
		File_Log_Create("value.txt",
				"/******************************************************/");
		File_Log_Create("value.txt",
				"Database backup date for the following tables: \r\n");

		mDatabaseEntryDate = mDateFormat.format(mCalendar.getTime());
		File_Log_Create("value.txt", mDatabaseEntryDate);
		File_Log_Create("value.txt",
				"/******************************************************/");
	}

	public long logAction(String name, String value) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				RealFarmDatabase.DATE_FORMAT);

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_NAME, name);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_DATE,
				formatter.format(new Date()));

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_LOG, args);

		mDatabase.close();

		return result;

	}

	public long removeAction(int id) {
		mDatabase.open();

		long result = mDatabase.deleteEntriesdb(
				RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + id, null);

		mDatabase.close();
		return result;
	}

	public long setAction(int actionid, int actionnameid, int growingid,
			String actiontype, String Seedvariety, int quantity1,
			int quantity2, String Units, String day, int userid, int plotid,
			String TypeFert, String Prob, String feedback, int sp,
			String QuaSeed, String type, int i, int j, String treat,
			String pesttype) {

		// increments the id of the current action.
		Global.actionid++;

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.DATE, -15);
		System.out.println("SETACTIONNEW");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, actionnameid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID, growingid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, actiontype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY, Seedvariety);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, quantity2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, userid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER, TypeFert);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, Prob);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK, feedback);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sp);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaSeed);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, type);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, i);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, j);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE, pesttype);

		long result;

		// User user = getUserByMobile(deviceId);

		mDatabase.open();

		// if (user != null) { // user exists in database => update
		// result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		// RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
		// + deviceId + "'", null);
		// } else { // user must be created
		// result = mDb
		// .update(RealFarmDatabase.TABLE_NAME_PLOT, args,null,null);

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);
		// }

		mDatabase.close();

		return result;
	}

	public long setDeleteFlagForPlot(int plotId) { // added with audio
													// integration

		System.out.println("in setDeleteFlagForPlot ");
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + " = '" + plotId + "'",
				null);
		// result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		// RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " = '"
		// + firstname + " AND " + RealFarmDatabase.COLUMN_NAME_USER_LASTNAME +
		// " = '"
		// + lastname , null);

		mDatabase.close();
		System.out.println(result);
		return result;
	}

	public long setDeleteFlagForUser(int userid) {

		System.out.println("in setDeleteFlagForUser ");
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = '" + userid + "'",
				null);
		// result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		// RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " = '"
		// + firstname + " AND " + RealFarmDatabase.COLUMN_NAME_USER_LASTNAME +
		// " = '"
		// + lastname , null);

		mDatabase.close();
		System.out.println(result);
		return result;
	}

	public long setFertilizing(int qua1, String TypeofFert, String Units,
			String day, int sent, int admin) {

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET fertilizing");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 4);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Fertilizing");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
				TypeofFert);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setGrowing(int plotId, int seedId) {
		mDatabase.open();

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID, seedId);

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_GROWING, args);

		mDatabase.close();
		return result;
	}

	public long setHarvest(int qua1, int qua2, String Units, String day,
			String harvfeedback, int sent, int admin) { // day takes the date

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET harvest");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 8);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Harvesting");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
				harvfeedback);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setIrrigation(int qua1, String Units, // qua1 mapped to no of
														// hours
			String day, int sent, int admin, String method) {

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET IRRIGATION");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 7);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Irrigate");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATE_METHOD, method);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setMarketPrice(int id, String date, String type, int value,
			int adminflag) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE, type);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG, adminflag);

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, args);

		mDatabase.close();

		return result;
	}

	public long setPlot(int userID) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userID);

		mDatabase.open();

		// add to plot list
		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_PLOT, args);

		mDatabase.close();
		return result;

	}

	// main crop info corresponds to seed type id
	public long setPlotNew(int seedTypeId, int centerX, int centerY,
			String plotImage, String soilType, int delete, int admin) {

		// increases the current plot id
		System.out.println("SETPLOTNEW");
		ContentValues args = new ContentValues();

		// TODO: you can't guarantee the plot id using
		// Global.plotId, maybe the result has the current id?

		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_PLOT_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_CENTERX, centerX);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_CENTERY, centerY);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMAGENAME, plotImage);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE, soilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, delete);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG, admin);

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_PLOT, args);

		// sets the current plotid based on the result of the insert operation.
		// TODO: shouldn't cast the result plotId should be a long.
		Global.plotId = (int) result;

		mDatabase.close();

		return result;
	}

	public long setProblem(String day, String probType, int sent, int admin) {

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET PROBLEM");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 6);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Problem");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probType);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setselling(int qua1, int qua2, String Units, String day,
			int sellingprice, String QuaOfSeed, String selltype, int sent,
			int admin) { // day takes the date

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET selling");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 12);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Selling");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sellingprice);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaOfSeed);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, selltype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setSowing(int qua1, String Seedvariety, String Units,
			String day, String treat, int sent, int admin) {

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET SOWING");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Sowing");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY, Seedvariety);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setspraying(int quantity1, String Units, String day,
			String probtype, int sent, int admin, String pesttype) {

		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();

		System.out.println("SET spraying");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Spraying");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, 5);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probtype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
				dateFormat.format(calendar.getTime()));
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE, pesttype);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setUserInfo(String deviceId, String firstname, String lastname) {

		getUserCount();
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ID, (getUserCount()) + 1);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 0);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG, 0);

		long result;

		User user = getUserByMobile(deviceId);

		mDatabase.open();

		if (user != null) { // user exists in database => update
			result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
					RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
							+ deviceId + "'", null);
		} else { // user must be created
			result = mDatabase.insertEntries(
					RealFarmDatabase.TABLE_NAME_USER, args);
		}

		// if main id is undefined and result is good
		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1))
			RealFarmDatabase.MAIN_USER_ID = (int) result;

		mDatabase.close();

		return result;
	}

	public long setWFData(int WF_Size, String WF_Date, int WF_Value,
			String WF_Type, String WF_Date1, int WF_Value1, String WF_Type1,
			int WF_adminflag) {

		ContentValues args = new ContentValues();
		Log.d("WF values: ", "before");
		// Context context = null;
		// mDataProvider = RealFarmProvider.getInstance(context);
		mMaxWeatherForecasts = WF_Size; // mDataProvider.getWFData().size();
		Log.d("WF values: ", "in setdata");
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
				mMaxWeatherForecasts);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE, WF_Date);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE, WF_Value);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE, WF_Type);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE1, WF_Date1);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE1, WF_Value1);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE1, WF_Type1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG,
				WF_adminflag);
		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args);

		mDatabase.close();

		// notifies any listener that the data changed.
		if (sWeatherForecastDataListener != null) {
			sWeatherForecastDataListener.onDataChanged(WF_Size, WF_Date,
					WF_Value, WF_Type, WF_Date1, WF_Value1, WF_Type1,
					WF_adminflag);
		}
		Log.d("done: ", "wf setdata");
		return result;
	}

	public void setWFDataChangeListener(OnDataChangeListener listener) {
		sWeatherForecastDataListener = listener;
	}
}
