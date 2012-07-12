package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.commonsensenet.realfarm.R;

/**
 * Class to manage database, i.e., input, remove and read data.
 * 
 * @author Julien Freudiger
 * @author Hendrik Knoche
 * @author Oscar Bolanos <@oscarbolanos>
 * 
 */
public class RealFarmDatabase {

	/**
	 * Class to facilitate creation of database. The database is created only if
	 * needed.
	 * 
	 * @author Julien Freudiger
	 */
	private class RealFarmDatabaseOpenHelper extends SQLiteOpenHelper {

		/**
		 * Creates a new RealFarmDatabaseOpenHandler instance.
		 * 
		 * @param context
		 *            application context to be used.
		 */
		public RealFarmDatabaseOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		/**
		 * Create the database with the table name and column names
		 * 
		 * @param SQLiteDatabase
		 *            to create
		 * @throws SQLException
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.d(LOG_TAG, "Try to fill up database with tables");

			db.execSQL("create table " + TABLE_NAME_ACTION + " ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_ACTION_ACTIONNAMEID + " integer, "
					+ COLUMN_NAME_ACTION_GROWINGID + " integer, "
					+ COLUMN_NAME_ACTION_ACTIONTYPE + " text, "
					+ COLUMN_NAME_ACTION_SEEDVARIETY + " text, "
					+ COLUMN_NAME_ACTION_QUANTITY1 + " integer, "
					+ COLUMN_NAME_ACTION_QUANTITY2 + " integer, "
					+ COLUMN_NAME_ACTION_UNITS + " text, "
					+ COLUMN_NAME_ACTION_DAY + " text, "
					+ COLUMN_NAME_ACTION_USERID + " integer, "
					+ COLUMN_NAME_ACTION_PLOTID + " integer, "
					+ COLUMN_NAME_ACTION_TYPEOFFERTILIZER + " text, "
					+ COLUMN_NAME_ACTION_PROBLEMTYPE + " text, "
					+ COLUMN_NAME_ACTION_HARVESTFEEDBACK + " text, "
					+ COLUMN_NAME_ACTION_SELLINGPRICE + " integer, "
					+ COLUMN_NAME_ACTION_QUALITYOFSEED + " text, "
					+ COLUMN_NAME_ACTION_SELLTYPE + " text, "
					+ COLUMN_NAME_ACTION_SENT + " integer, "
					+ COLUMN_NAME_ACTION_ISADMIN + " integer, "
					+ COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE + " text, "
					+ COLUMN_NAME_ACTION_TREATMENT + " text, "
					+ COLUMN_NAME_ACTION_PESTICIDETYPE + " text, "
					+ COLUMN_NAME_ACTION_IRRIGATE_METHOD + " text "
					+ COLUMN_NAME_ACTION_TIMESTAMP + " integer " + " ); ");
			Log.d(LOG_TAG, "Created action table");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONNAME + " ( "
					+ COLUMN_NAME_ACTIONNAME_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONNAME_RESOURCE + " integer, "
					+ COLUMN_NAME_ACTIONNAME_AUDIO + " integer, "
					+ COLUMN_NAME_ACTIONNAME_NAME + " text not null, "
					+ COLUMN_NAME_ACTIONNAME_NAMEKANNADA + " text not null "
					+ " ); ");
			Log.d(LOG_TAG, "Created actionName table");

			// fertilizers
			db.execSQL("create table " + TABLE_NAME_FERTILIZER + " ( "
					+ COLUMN_NAME_FERTILIZER_ID + " integer primary key, "
					+ COLUMN_NAME_FERTILIZER_NAME + " text, "
					+ COLUMN_NAME_FERTILIZER_AUDIO + " integer, "
					+ COLUMN_NAME_FERTILIZER_UNITID + " references unit(id) "
					+ " ); ");
			Log.d(LOG_TAG, "Created fertilizer table");

			// pesticides
			db.execSQL("create table " + TABLE_NAME_PESTICIDE + " ( "
					+ COLUMN_NAME_PESTICIDE_ID + " integer primary key, "
					+ COLUMN_NAME_PESTICIDE_NAME + " text, "
					+ COLUMN_NAME_PESTICIDE_AUDIO + " integer " + " ); ");
			Log.d(LOG_TAG, "Created pesticide table");

			// plots
			db.execSQL("create table " + TABLE_NAME_PLOT + " ( "
					+ COLUMN_NAME_PLOT_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_PLOT_USERID + " integer, "
					+ COLUMN_NAME_PLOT_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_PLOT_IMAGEPATH + " text, "
					+ COLUMN_NAME_PLOT_SOILTYPE + " text, "
					+ COLUMN_NAME_PLOT_DELETEFLAG + " integer, "
					+ COLUMN_NAME_PLOT_ADMINFLAG + " boolean, "
					+ COLUMN_NAME_PLOT_TIMESTAMP + " integer " + " ); ");
			Log.d(LOG_TAG, "Created plot table");

			// problem type
			db.execSQL("create table " + TABLE_NAME_PROBLEMTYPE + " ( "
					+ COLUMN_NAME_PROBLEMTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_PROBLEMTYPE_NAME + " text, "
					+ COLUMN_NAME_PROBLEMTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_PROBLEMTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_PROBLEMTYPE_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created problem table");

			// problems
			db.execSQL("create table " + TABLE_NAME_PROBLEM + " ( "
					+ COLUMN_NAME_PROBLEM_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_PROBLEM_NAME + " text, "
					+ COLUMN_NAME_PROBLEM_AUDIO + " integer, "
					+ COLUMN_NAME_PROBLEM_RESOURCE + " integer, "
					+ COLUMN_NAME_PROBLEM_PROBLEMTYPEID + " integer, "
					+ COLUMN_NAME_PROBLEM_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created problem table");

			// seedTypes
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ COLUMN_NAME_SEEDTYPE_NAMEKANNADA + " text, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE_BG + " integer, "
					+ COLUMN_NAME_SEEDTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_SEEDTYPE_VARIETY + " text, "
					+ COLUMN_NAME_SEEDTYPE_VARIETYKANNADA + " text" + " ); ");
			Log.d(LOG_TAG, "Created seed type table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text, "
					+ COLUMN_NAME_USER_MOBILE + " text not null, "
					+ COLUMN_NAME_USER_IMAGEPATH + " text, "
					+ COLUMN_NAME_USER_DELETEFLAG + " boolean, "
					+ COLUMN_NAME_USER_ADMINFLAG + " boolean, "
					+ COLUMN_NAME_USER_TIMESTAMP + " integer " + " ); ");
			Log.d(LOG_TAG, "Created user table");

			// units
			db.execSQL("create table " + TABLE_NAME_UNIT + " ( "
					+ COLUMN_NAME_UNIT_ID + " integer primary key, "
					+ COLUMN_NAME_UNIT_NAME + " text not null, "
					+ COLUMN_NAME_UNIT_AUDIO + " integer " + " ); ");
			Log.d(LOG_TAG, "Created unit table");

			// soil moisture
			db.execSQL("create table " + TABLE_NAME_SOILMOISTURE + " ( "
					+ COLUMN_NAME_SOILMOISTURE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SOILMOISTURE_DATE + " date, "
					+ COLUMN_NAME_SOILMOISTURE_VALUE1 + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_VALUE2 + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_CLUSTER + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created soil moisture table");

			// Weather forecast
			db.execSQL("create table " + TABLE_NAME_WEATHERFORECAST + " ( "
					+ COLUMN_NAME_WEATHERFORECAST_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_WEATHERFORECAST_DATE + " date not null, "
					+ COLUMN_NAME_WEATHERFORECAST_TEMPERATURE
					+ " integer not null, " + COLUMN_NAME_WEATHERFORECAST_TYPE
					+ " text not null" + " ); ");
			Log.d(LOG_TAG, "Created weather forecast table");

			// Market price
			db.execSQL("create table " + TABLE_NAME_MARKETPRICE + " ( "
					+ COLUMN_NAME_MARKETPRICE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_MARKETPRICE_DATE + " date, "
					+ COLUMN_NAME_MARKETPRICE_TYPE + " integer, "
					+ COLUMN_NAME_MARKETPRICE_VALUE + " integer, "
					+ COLUMN_NAME_MARKETPRICE_ADMINFLAG + " boolean" + " ); ");
			Log.d(LOG_TAG, "Created market price table");

			Log.d(LOG_TAG, "Database created successfully");

			initValues(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	public static final String COLUMN_NAME_ACTION_ACTIONNAMEID = "actionNameId";
	public static final String COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE = "ActionPerformedDate";
	public static final String COLUMN_NAME_ACTION_ACTIONTYPE = "actionType";
	public static final String COLUMN_NAME_ACTION_DATE = "date";
	public static final String COLUMN_NAME_ACTION_DAY = "day";
	public static final String COLUMN_NAME_ACTION_GROWINGID = "growingId";
	public static final String COLUMN_NAME_ACTION_HARVESTFEEDBACK = "feedback";
	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_IRRIGATE_METHOD = "irrigateMethod";
	public static final String COLUMN_NAME_ACTION_ISADMIN = "IsAdmin";
	public static final String COLUMN_NAME_ACTION_PESTICIDETYPE = "pesticidetype";
	public static final String COLUMN_NAME_ACTION_PLOTID = "plotId";
	public static final String COLUMN_NAME_ACTION_PROBLEMTYPE = "problems";
	public static final String COLUMN_NAME_ACTION_QUALITYOFSEED = "qualityofSeed";
	public static final String COLUMN_NAME_ACTION_QUANTITY1 = "quantity1";
	public static final String COLUMN_NAME_ACTION_QUANTITY2 = "quantity2";
	public static final String COLUMN_NAME_ACTION_SEEDVARIETY = "seedVariety";
	public static final String COLUMN_NAME_ACTION_SELLINGPRICE = "sellingPrice";
	public static final String COLUMN_NAME_ACTION_SELLTYPE = "selltype";
	public static final String COLUMN_NAME_ACTION_SENT = "sent";
	public static final String COLUMN_NAME_ACTION_TREATMENT = "treatment";
	public static final String COLUMN_NAME_ACTION_TYPEOFFERTILIZER = "typeOfFertilizer";
	public static final String COLUMN_NAME_ACTION_UNITS = "units";
	public static final String COLUMN_NAME_ACTION_USERID = "userId";
	public static final String COLUMN_NAME_ACTION_TIMESTAMP = "timestamp";

	public static final String COLUMN_NAME_ACTIONNAME_AUDIO = "audio";
	public static final String COLUMN_NAME_ACTIONNAME_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_NAME = "name";
	public static final String COLUMN_NAME_ACTIONNAME_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_ACTIONNAME_RESOURCE = "res";

	public static final String COLUMN_NAME_FERTILIZER_AUDIO = "audio";
	public static final String COLUMN_NAME_FERTILIZER_ID = "id";
	public static final String COLUMN_NAME_FERTILIZER_NAME = "name";
	public static final String COLUMN_NAME_FERTILIZER_UNITID = "unitId";

	public static final String COLUMN_NAME_MARKETPRICE_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_MARKETPRICE_DATE = "date";
	public static final String COLUMN_NAME_MARKETPRICE_ID = "id";
	public static final String COLUMN_NAME_MARKETPRICE_TYPE = "type";
	public static final String COLUMN_NAME_MARKETPRICE_VALUE = "value";

	public static final String COLUMN_NAME_PESTICIDE_AUDIO = "audio";
	public static final String COLUMN_NAME_PESTICIDE_ID = "id";
	public static final String COLUMN_NAME_PESTICIDE_NAME = "name";

	public static final String COLUMN_NAME_PLOT_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_PLOT_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_PLOT_DELETEFLAG = "deleteFlag";
	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_PLOT_SEEDTYPEID = "seedtypeId";
	public static final String COLUMN_NAME_PLOT_SOILTYPE = "soilType";
	public static final String COLUMN_NAME_PLOT_USERID = "userId";

	public static final String COLUMN_NAME_PROBLEM_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_PROBLEM_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEM_ID = "id";
	public static final String COLUMN_NAME_PROBLEM_NAME = "name";
	public static final String COLUMN_NAME_PROBLEM_PROBLEMTYPEID = "masterId";
	public static final String COLUMN_NAME_PROBLEM_RESOURCE = "res";

	public static final String COLUMN_NAME_PROBLEMTYPE_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_PROBLEMTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEMTYPE_ID = "id";
	public static final String COLUMN_NAME_PROBLEMTYPE_NAME = "name";
	public static final String COLUMN_NAME_PROBLEMTYPE_RESOURCE = "res";

	public static final String COLUMN_NAME_SEEDTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE = "res";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE_BG = "resBg";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETY = "variety";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETYKANNADA = "varietyKannada";

	public static final String COLUMN_NAME_SOILMOISTURE_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_SOILMOISTURE_CLUSTER = "cluster";
	public static final String COLUMN_NAME_SOILMOISTURE_DATE = "date";
	public static final String COLUMN_NAME_SOILMOISTURE_ID = "id";
	public static final String COLUMN_NAME_SOILMOISTURE_VALUE1 = "value1";
	public static final String COLUMN_NAME_SOILMOISTURE_VALUE2 = "value2";

	public static final String COLUMN_NAME_UNIT_AUDIO = "audio";
	public static final String COLUMN_NAME_UNIT_ID = "id";
	public static final String COLUMN_NAME_UNIT_NAME = "name";

	public static final String COLUMN_NAME_USER_ADMINFLAG = "adminFlag";
	public static final String COLUMN_NAME_USER_DELETEFLAG = "deleteFlag";
	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstName";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastName";
	public static final String COLUMN_NAME_USER_MOBILE = "mobileNumber";
	public static final String COLUMN_NAME_USER_TIMESTAMP = "timestamp";

	public static final String COLUMN_NAME_WEATHERFORECAST_DATE = "date";
	public static final String COLUMN_NAME_WEATHERFORECAST_ID = "id";
	public static final String COLUMN_NAME_WEATHERFORECAST_TEMPERATURE = "temperature";
	public static final String COLUMN_NAME_WEATHERFORECAST_TYPE = "type";

	/** Date format used to store the dates. */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** Filename of the database. */
	public static final String DB_NAME = "realFarm.db";
	/** Current version of the database. */
	private static final int DB_VERSION = 1;
	/** Default User number. */
	public static String DEFAULT_NUMBER = "000000000";
	/** Current DeviceId. */
	public static String DEVICE_ID;
	/** Identifier used to debug the database. */
	public static final String LOG_TAG = "RealFarm";
	/** Identifier of the current User. */
	public static int MAIN_USER_ID = -1;

	public static final String TABLE_NAME_ACTION = "action"; // ok
	public static final String TABLE_NAME_ACTIONNAME = "actionName"; // ok
	public static final String TABLE_NAME_FERTILIZER = "fertilizer";
	public static final String TABLE_NAME_MARKETPRICE = "marketPrice"; // ok
	public static final String TABLE_NAME_PESTICIDE = "pesticide";
	public static final String TABLE_NAME_PLOT = "plot"; // ok
	public static final String TABLE_NAME_PROBLEM = "problem";
	public static final String TABLE_NAME_PROBLEMTYPE = "problemType";
	public static final String TABLE_NAME_SEEDTYPE = "seedType"; // ok
	public static final String TABLE_NAME_SOILMOISTURE = "soilMoisture"; // ok
	public static final String TABLE_NAME_UNIT = "unit"; // ok
	public static final String TABLE_NAME_USER = "user"; // ok
	public static final String TABLE_NAME_WEATHERFORECAST = "weatherForecast"; // ok

	/** Application context. */
	private Context mContext;
	/** Database where the statements are performed. */
	private SQLiteDatabase mDb;
	/** Helper used to access the database. */
	private RealFarmDatabaseOpenHelper mOpenHelper;

	/**
	 * Creates a new RealFarmDatabase instance.
	 * 
	 * @param context
	 *            application context
	 */
	public RealFarmDatabase(Context context) {
		mContext = context;
		mOpenHelper = new RealFarmDatabaseOpenHelper(mContext);
	}

	public void clearValues() {
		// Delete current elements in table
		mDb.delete(TABLE_NAME_ACTIONNAME, null, null);
		mDb.delete(TABLE_NAME_ACTION, null, null);
		mDb.delete(TABLE_NAME_FERTILIZER, null, null);
		mDb.delete(TABLE_NAME_PESTICIDE, null, null);
		mDb.delete(TABLE_NAME_PROBLEM, null, null);
		mDb.delete(TABLE_NAME_PROBLEMTYPE, null, null);
		mDb.delete(TABLE_NAME_PLOT, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
		mDb.delete(TABLE_NAME_UNIT, null, null);
		mDb.delete(TABLE_NAME_USER, null, null);

		Log.d(LOG_TAG, "Cleared existing content if any");
	}

	/**
	 * Closes the database and its handler.
	 */
	public synchronized void close() {
		mOpenHelper.close();

		if (mDb != null)
			mDb.close();
		mDb = null;
	}

	public long deleteEntriesdb(String TableName, String whereClause,
			String[] whereArgs) {
		long result = -1;

		if (TableName != null) {
			// result = db.insert(TableName, null, values);
			try {
				result = mDb.delete(TableName, whereClause, whereArgs);
			} catch (SQLException e) {
				Log.d(LOG_TAG, "Exception" + e);
			}
		}
		return result;
	}

	/**
	 * Runs the provided SQL and returns a Cursor over the result set.
	 * 
	 * @param sql
	 *            the SQL query. The SQL string must not be ; terminated
	 * @param selectionArgs
	 *            You may include ?s in where clause in the query, which will be
	 *            replaced by the values from selectionArgs. The values will be
	 *            bound as Strings.
	 * @return a Cursor object with the query result.
	 * 
	 */
	public Cursor executeQuery(String sql, String[] selectionArgs) {
		return mDb.rawQuery(sql, selectionArgs);
	}

	/**
	 * Reads all the entries in a given table that match certain parameters.
	 * 
	 * @return A cursor containing the result of the query.
	 */
	public Cursor getAllEntries(String tableName, String[] parameters) {
		return mDb.query(tableName, parameters, null, null, null, null, null);
	}

	/**
	 * Method to read specific values from table.
	 * 
	 * @return A cursor containing the result of the query.
	 */
	public Cursor getEntries(String tableName, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		return mDb.query(tableName, columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	/**
	 * Defines hard-coded initial values for database. All base tables and user
	 * data, the latter is for testing purposes only and should be replaced by
	 * method to obtain location of plots from farmers directly. base table
	 * conventions: 1 - unknown, 2 - none, 3 - ... real entries
	 * 
	 * @param db
	 *            database where the values will be inserted.
	 */
	public void initValues(SQLiteDatabase db) {

		Log.d(LOG_TAG, "Try to fill up tables with content " + db.getVersion());

		// 1
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getLine1Number();

		DEVICE_ID = deviceID;

		String mobileNumber;
		if (deviceID == null) {
			mobileNumber = DEFAULT_NUMBER;
		} else {
			mobileNumber = deviceID;
		}

		String[][] userData = {
				{ "John", "Doe", mobileNumber, "farmer_90px_kiran_kumar_g" },
				{ "Hendrik", "Knoche", "788844672", "farmer_90px_adam_jones" },
				{ "Chris", "Bishop", "788244421", "farmer_90px_neil_palmer" },
				{ "Chris", "McDougall", "781122672", "farmer_90px_neil_palmer2" },
				{ "Frank", "Herbert", "788111172", "farmer_90px_walmart_stores" } };

		// users
		ContentValues users = new ContentValues();
		for (int x = 0; x < userData.length; x++) {
			users.put(COLUMN_NAME_USER_ID, (x + 1));
			users.put(COLUMN_NAME_USER_FIRSTNAME, userData[x][0]);
			users.put(COLUMN_NAME_USER_LASTNAME, userData[x][1]);
			users.put(COLUMN_NAME_USER_MOBILE, userData[x][2]);
			users.put(COLUMN_NAME_USER_IMAGEPATH, userData[x][3]);
			users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
			insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
			users.clear();
		}

		Log.d(LOG_TAG, "users works");

		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "sow");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_sow));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_sowing);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 4);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "fertilize");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_apply_fertilizer));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_fertilizing2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio2);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 5);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "spray");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_spraying));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_spraying3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio3);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 7);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "irrigate");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_irrigate));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_irrigation2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio4);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 8);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "harvest");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_harvest));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_harvesting1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio5);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 6);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "report");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_reporting_of_problems));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_reporting);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio6);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 9);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Diary");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
				mContext.getString(R.string.k_diary));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_diary1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio7);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);

		Log.d(LOG_TAG, "actionName works");

		// units
		Object[][] unitData = { { "unknown", R.raw.audio1 },
				{ "none", R.raw.audio1 },
				{ "cart/tractor loads", R.raw.audio1 },
				{ "number of days", R.raw.audio1 },
				{ "number of year", R.raw.audio1 },
				{ "number of nights", R.raw.audio1 },
				{ "bags of 50 kg", R.raw.audio1 },
				{ "number of main crop rows between each row", R.raw.audio1 }

		};

		ContentValues unit = new ContentValues();
		for (int x = 0; x < unitData.length; x++) {
			unit.put(COLUMN_NAME_UNIT_ID, (x + 1));
			unit.put(COLUMN_NAME_UNIT_NAME, (String) unitData[x][0]);
			unit.put(COLUMN_NAME_UNIT_AUDIO, (Integer) unitData[x][1]);
			insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
			unit.clear();
		}

		Log.d(LOG_TAG, "unit works");

		// inserts the current date in the database.
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date now = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(now);

		// goes back one day to test the behavior
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		ContentValues wf = new ContentValues();
		int[] tempForecast = { 28, 30, 27, 29, 35 };
		String[] typeForecast = { "Sunny", "Cloudy", "Chance of Rain",
				"Light Rain", "Rain" };

		for (int x = 0; x < 5; x++, calendar.add(Calendar.DAY_OF_MONTH, 1)) {
			wf.put(COLUMN_NAME_WEATHERFORECAST_DATE,
					df.format(calendar.getTime()));
			wf.put(COLUMN_NAME_WEATHERFORECAST_TEMPERATURE, tempForecast[x]);
			wf.put(COLUMN_NAME_WEATHERFORECAST_TYPE, typeForecast[x]);
			insertEntriesIntoDatabase(TABLE_NAME_WEATHERFORECAST, wf, db);
			wf.clear();
		}

		Log.d(LOG_TAG, "WeatherForecast inserted works");

		// seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 2);
		// seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "None");
		// seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "-");
		// seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		// seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
		// R.drawable.ic_72px_none);
		// insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		// seedtype.clear();
		// seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 1);
		// seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Unknown");
		// seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "?");
		// seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		// seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
		// R.drawable.ic_72px_unknown);
		// insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		// seedtype.clear();
		ContentValues seedtype = new ContentValues();
		Object[][] seedData = {
				{ "Groundnut", "", "TMV2", "", R.drawable.pic_72px_groundnut,
						R.drawable.pic_90px_groundnut_tiled, R.raw.msg_plant },
				{ "Groundnut", "", "Samrat", "", R.drawable.pic_72px_groundnut,
						R.drawable.pic_90px_groundnut_tiled, R.raw.msg_plant },
				{ "Bajra", "", "", "", R.drawable.pic_72px_bajra,
						R.drawable.pic_90px_bajra_tiled, R.raw.msg_plant },
				{ "Castor", "", "", "", R.drawable.pic_72px_castor,
						R.drawable.pic_90px_castor_tiled, R.raw.msg_plant },
				{ "Cowpea", "", "", "", R.drawable.pic_72px_cowpea,
						R.drawable.pic_90px_cowpea_tiled, R.raw.msg_plant },
				{ "Greengram", "", "", "", R.drawable.pic_72px_greengram,
						R.drawable.pic_90px_greengram_tiled, R.raw.msg_plant },
				{ "Horsegram", "", "", "", R.drawable.pic_72px_horsegram,
						R.drawable.pic_90px_horsegram_tiled, R.raw.msg_plant },
				{ "Pigeonpea", "", "", "", R.drawable.pic_72px_pigeonpea,
						R.drawable.pic_90px_pidgeonpea_tiled, R.raw.msg_plant },
				{ "Redgram", "", "", "", R.drawable.pic_72px_redgram,
						R.drawable.pic_90px_redgram_tiled, R.raw.msg_plant },
				{ "Sorghum", "", "", "", R.drawable.pic_72px_sorghum,
						R.drawable.pic_90px_sorghum_tiled, R.raw.msg_plant }

		};

		for (int x = 0; x < seedData.length; x++) {
			seedtype.put(COLUMN_NAME_SEEDTYPE_ID, (x + 1));
			seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, (String) seedData[x][0]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
					(String) seedData[x][1]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, (String) seedData[x][2]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
					(String) seedData[x][3]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
					(Integer) seedData[x][4]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
					(Integer) seedData[x][5]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, (Integer) seedData[x][6]);
			insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
			seedtype.clear();
		}

		Log.d(LOG_TAG, "seedtype works");

	}

	/**
	 * Inserts the values into the current database in the specified table.
	 * 
	 * @param tableName
	 *            name of the table where the values will be inserted.
	 * @param values
	 *            values to insert
	 * 
	 * @return the row id of the newly inserted row, or -1 if an error occurred.
	 */
	public long insertEntries(String tableName, ContentValues values) {
		return insertEntriesIntoDatabase(tableName, values, mDb);
	}

	/**
	 * Inserts the given values inside the specified table.
	 * 
	 * @param tableName
	 *            name of the table where the values will be inserted.
	 * @param values
	 *            values to insert
	 * @param database
	 *            database where the insert operation will be performed
	 * 
	 * @return the row id of the newly inserted row, or -1 if an error occurred.
	 */
	public long insertEntriesIntoDatabase(String tableName,
			ContentValues values, SQLiteDatabase database) {
		long result = -1;

		if (tableName != null && values != null) {
			try {
				result = database.insertOrThrow(tableName, null, values);
			} catch (SQLException e) {
				Log.d(LOG_TAG, "Exception" + e);
			}
		}
		return result;
	}

	/**
	 * Open database helper for writing
	 * 
	 * @return ManageDatabase Object
	 * @throws SQLException
	 */
	public synchronized void open() throws SQLException {
		// opens the database
		if (mDb == null || !mDb.isOpen()) {
			mDb = mOpenHelper.getWritableDatabase();
		}
	}

	public int update(String tableName, ContentValues args, String whereClause,
			String[] whereArgs) {
		return mDb.update(tableName, args, whereClause, whereArgs);
	}
}
