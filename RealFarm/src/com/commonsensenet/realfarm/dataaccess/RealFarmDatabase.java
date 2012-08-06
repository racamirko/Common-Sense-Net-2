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
		 * Helps building the reference statement for the table creation.
		 * 
		 * @param fieldName
		 *            name of the field that references another one.
		 * @param tableName
		 *            name of the reference table.
		 * @param id
		 *            name of the column to reference.
		 * 
		 * @return a string that contains the reference to add.
		 */
		private String references(String fieldName, String tableName,
				String id, boolean addComma) {
			return String.format("%s references %s(%s)%s ", fieldName,
					tableName, id, addComma ? "," : "");
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

			db.execSQL("create table "
					+ TABLE_NAME_ACTION
					+ " ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer primary key autoincrement, "
					+ references(COLUMN_NAME_ACTION_ACTIONTYPEID,
							TABLE_NAME_ACTIONTYPE, COLUMN_NAME_ACTIONTYPE_ID,
							true)
					+ references(COLUMN_NAME_ACTION_SEEDTYPEID,
							TABLE_NAME_SEEDTYPE, COLUMN_NAME_SEEDTYPE_ID, true)
					+ references(COLUMN_NAME_ACTION_CROPTYPEID,
							TABLE_NAME_CROP, COLUMN_NAME_CROP_ID, true)
					+ COLUMN_NAME_ACTION_QUANTITY1
					+ " integer, "
					+ COLUMN_NAME_ACTION_QUANTITY2
					+ " integer, "
					+ references(COLUMN_NAME_ACTION_UNIT1ID, TABLE_NAME_UNIT,
							COLUMN_NAME_UNIT_ID, true)
					+ references(COLUMN_NAME_ACTION_UNIT2ID, TABLE_NAME_UNIT,
							COLUMN_NAME_UNIT_ID, true)
					+ references(COLUMN_NAME_ACTION_PLOTID, TABLE_NAME_PLOT,
							COLUMN_NAME_PLOT_ID, true)
					+ references(COLUMN_NAME_ACTION_RESOURCE1ID,
							TABLE_NAME_RESOURCE, COLUMN_NAME_RESOURCE_ID, true)
					+ references(COLUMN_NAME_ACTION_RESOURCE2ID,
							TABLE_NAME_RESOURCE, COLUMN_NAME_RESOURCE_ID, true)
					+ COLUMN_NAME_ACTION_PRICE + " integer, "
					+ COLUMN_NAME_ACTION_ISSENT + " boolean, "
					+ COLUMN_NAME_ACTION_ISADMINACTION + " boolean, "
					+ COLUMN_NAME_ACTION_DATE + " text not null, "
					+ COLUMN_NAME_ACTION_TIMESTAMP + " integer not null, "
					+ COLUMN_NAME_ACTION_GLOBALID + " integer " + " ); ");
			Log.d(LOG_TAG, "Created action table");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONTYPE + " ( "
					+ COLUMN_NAME_ACTIONTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_ACTIONTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_ACTIONTYPE_NAME + " text not null, "
					+ COLUMN_NAME_ACTIONTYPE_NAMEKANNADA + " text not null "
					+ " ); ");
			Log.d(LOG_TAG, "Created actiontype table");

			// dialogArrays
			db.execSQL("create table " + TABLE_NAME_DIALOG_ARRAYS + " ( "
					+ COLUMN_NAME_DIALOG_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_DIALOG_NAME + " text, "
					+ COLUMN_NAME_DIALOG_SHORTNAME + " text, "
					+ COLUMN_NAME_DIALOG_RES + " integer, "
					+ COLUMN_NAME_DIALOG_RES2 + " integer, "
					+ COLUMN_NAME_DIALOG_AUDIO + " integer, "
					+ COLUMN_NAME_DIALOG_VALUE + " integer, "
					+ COLUMN_NAME_DIALOG_TYPE + " integer, "
					+ COLUMN_NAME_DIALOG_NUMBER + " integer, "
					+ COLUMN_NAME_DIALOG_RES_BG + " integer " + " ); ");
			Log.d(LOG_TAG, "Created dialogArrays table");

			// cropTypes
			db.execSQL("create table " + TABLE_NAME_CROP + " ( "
					+ COLUMN_NAME_CROP_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_CROP_NAME + " text not null, "
					+ COLUMN_NAME_CROP_RESOURCE + " integer, "
					+ COLUMN_NAME_CROP_RESOURCEBG + " integer, "
					+ COLUMN_NAME_CROP_SHORTNAME + " text, "
					+ COLUMN_NAME_CROP_AUDIO + " integer" + " ); ");
			Log.d(LOG_TAG, "Created crop type table");

			// Market price
			db.execSQL("create table " + TABLE_NAME_MARKETPRICE + " ( "
					+ COLUMN_NAME_MARKETPRICE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_MARKETPRICE_DATE + " text, "
					+ COLUMN_NAME_MARKETPRICE_TYPE + " integer, "
					+ COLUMN_NAME_MARKETPRICE_VALUE + " integer " + " ); ");
			Log.d(LOG_TAG, "Created market price table");

			// plots
			db.execSQL("create table "
					+ TABLE_NAME_PLOT
					+ " ( "
					+ COLUMN_NAME_PLOT_ID
					+ " integer primary key autoincrement, "
					+ references(COLUMN_NAME_PLOT_USERID, TABLE_NAME_USER,
							COLUMN_NAME_USER_ID, true)
					+ references(COLUMN_NAME_PLOT_SEEDTYPEID,
							TABLE_NAME_SEEDTYPE, COLUMN_NAME_SEEDTYPE_ID, true)
					+ COLUMN_NAME_PLOT_SOILTYPE + " text, "
					+ COLUMN_NAME_PLOT_IMAGEPATH + " text, "
					+ COLUMN_NAME_PLOT_ISENABLED + " boolean, "
					+ COLUMN_NAME_PLOT_ISADMINACTION + " boolean, "
					+ COLUMN_NAME_PLOT_TIMESTAMP + " integer not null, "
					+ COLUMN_NAME_PLOT_SIZE + " real " + " ); ");
			Log.d(LOG_TAG, "Created plot table");
			// resource
			db.execSQL("create table " + TABLE_NAME_RESOURCE + " ( "
					+ COLUMN_NAME_RESOURCE_ID + " integer primary key, "
					+ COLUMN_NAME_RESOURCE_NAME + " text not null, "
					+ COLUMN_NAME_RESOURCE_SHORTNAME + " text, "
					+ COLUMN_NAME_RESOURCE_AUDIO + " integer, "
					+ COLUMN_NAME_RESOURCE_RESOURCE1 + " integer, "
					+ COLUMN_NAME_RESOURCE_RESOURCE2 + " integer, "
					+ COLUMN_NAME_RESOURCE_RESOURCEBG + " integer, "
					+ COLUMN_NAME_RESOURCE_TYPE + " integer " + " ); ");
			Log.d(LOG_TAG, "Created Resources Type table");

			// seed type
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ COLUMN_NAME_SEEDTYPE_NAMEKANNADA + " text, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_SEEDTYPE_SHORTNAME + " text, "
					+ COLUMN_NAME_SEEDTYPE_CROPTYPEID + " integer, "
					+ COLUMN_NAME_SEEDTYPE_AUDIO + " integer " + " ); ");
			Log.d(LOG_TAG, "Created seed type table");

			// soil moisture
			db.execSQL("create table " + TABLE_NAME_SOILMOISTURE + " ( "
					+ COLUMN_NAME_SOILMOISTURE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SOILMOISTURE_DATE + " text, "
					+ COLUMN_NAME_SOILMOISTURE_VALUE + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_CLUSTER + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_ISADMINACTION + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created soil moisture table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text not null, "
					+ COLUMN_NAME_USER_DEVICEID + " text unique not null, "
					+ COLUMN_NAME_USER_IMAGEPATH + " text, "
					+ COLUMN_NAME_USER_ISENABLED + " boolean, "
					+ COLUMN_NAME_USER_ISADMINACTION + " boolean, "
					+ COLUMN_NAME_USER_TIMESTAMP + " integer not null" + " ); ");
			Log.d(LOG_TAG, "Created user table");

			// units
			db.execSQL("create table "
					+ TABLE_NAME_UNIT
					+ " ( "
					+ COLUMN_NAME_UNIT_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_UNIT_NAME
					+ " text not null, "
					+ COLUMN_NAME_UNIT_RESOURCE
					+ " integer, "
					+ COLUMN_NAME_UNIT_AUDIO
					+ " integer, "
					+ references(COLUMN_NAME_UNIT_ACTIONTYPEID,
							TABLE_NAME_ACTIONTYPE, COLUMN_NAME_ACTIONTYPE_ID,
							false) + " ); ");
			Log.d(LOG_TAG, "Created unit table");

			// Weather forecast
			db.execSQL("create table " + TABLE_NAME_WEATHERFORECAST + " ( "
					+ COLUMN_NAME_WEATHERFORECAST_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_WEATHERFORECAST_DATE + " text not null, "
					+ COLUMN_NAME_WEATHERFORECAST_TEMPERATURE
					+ " integer not null, "
					+ COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID
					+ " text not null" + " ); ");
			Log.d(LOG_TAG, "Created weather forecast table");

			Log.d(LOG_TAG, "Database created successfully");

			initValues(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	public static final int ACTION_TYPE_ALL_ID = 0;
	public static final int ACTION_TYPE_FERTILIZE_ID = 2;
	public static final int ACTION_TYPE_HARVEST_ID = 6;
	public static final int ACTION_TYPE_IRRIGATE_ID = 3;
	public static final int ACTION_TYPE_REPORT_ID = 4;
	public static final int ACTION_TYPE_SELL_ID = 7;
	public static final int ACTION_TYPE_SOW_ID = 1;
	public static final int ACTION_TYPE_SPRAY_ID = 5;

	public static final int RESOURCE_TYPE_PROBLEM = 1;
	public static final int RESOURCE_TYPE_SOILDTYPE = 2;

	public static final String COLUMN_NAME_ACTION_ACTIONTYPEID = "actionTypeId";
	public static final String COLUMN_NAME_ACTION_CROPTYPEID = "cropTypeId";
	public static final String COLUMN_NAME_ACTION_DATE = "date";
	public static final String COLUMN_NAME_ACTION_GLOBALID = "globalId";
	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_ACTION_ISSENT = "isSent";
	public static final String COLUMN_NAME_ACTION_PLOTID = "plotId";
	public static final String COLUMN_NAME_ACTION_PRICE = "price";
	public static final String COLUMN_NAME_ACTION_QUANTITY1 = "quantity1";
	public static final String COLUMN_NAME_ACTION_QUANTITY2 = "quantity2";
	public static final String COLUMN_NAME_ACTION_RESOURCE1ID = "resource1Id";
	public static final String COLUMN_NAME_ACTION_RESOURCE2ID = "resource2Id";
	public static final String COLUMN_NAME_ACTION_SEEDTYPEID = "seedTypeId";
	public static final String COLUMN_NAME_ACTION_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_ACTION_UNIT1ID = "unit1Id";
	public static final String COLUMN_NAME_ACTION_UNIT2ID = "unit2Id";

	public static final String COLUMN_NAME_ACTIONTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_ACTIONTYPE_ID = "id";
	public static final String COLUMN_NAME_ACTIONTYPE_NAME = "name";
	public static final String COLUMN_NAME_ACTIONTYPE_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_ACTIONTYPE_RESOURCE = "resource";

	public static final String COLUMN_NAME_CROP_AUDIO = "audio";
	public static final String COLUMN_NAME_CROP_ID = "id";
	public static final String COLUMN_NAME_CROP_NAME = "name";
	public static final String COLUMN_NAME_CROP_RESOURCE = "resource";
	public static final String COLUMN_NAME_CROP_RESOURCEBG = "resourceBg";
	public static final String COLUMN_NAME_CROP_SHORTNAME = "shortName";

	public static final String COLUMN_NAME_DIALOG_AUDIO = "audio";
	public static final String COLUMN_NAME_DIALOG_ID = "id";
	public static final String COLUMN_NAME_DIALOG_NAME = "name";
	public static final String COLUMN_NAME_DIALOG_NUMBER = "number";
	public static final String COLUMN_NAME_DIALOG_RES = "resource";
	public static final String COLUMN_NAME_DIALOG_RES_BG = "resourceBg";
	public static final String COLUMN_NAME_DIALOG_RES2 = "resource2";
	public static final String COLUMN_NAME_DIALOG_SHORTNAME = "shortName";
	public static final String COLUMN_NAME_DIALOG_TYPE = "type";
	public static final String COLUMN_NAME_DIALOG_VALUE = "value";

	public static final String COLUMN_NAME_MARKETPRICE_DATE = "date";
	public static final String COLUMN_NAME_MARKETPRICE_ID = "id";
	public static final String COLUMN_NAME_MARKETPRICE_TYPE = "type";
	public static final String COLUMN_NAME_MARKETPRICE_VALUE = "value";

	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_PLOT_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_PLOT_ISENABLED = "isEnabled";
	public static final String COLUMN_NAME_PLOT_SEEDTYPEID = "seedtypeId";
	public static final String COLUMN_NAME_PLOT_SIZE = "size";
	// maybe add this into its own table.
	public static final String COLUMN_NAME_PLOT_SOILTYPE = "soilType";
	public static final String COLUMN_NAME_PLOT_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_PLOT_USERID = "userId";

	public static final String COLUMN_NAME_RESOURCE_AUDIO = "audio";
	public static final String COLUMN_NAME_RESOURCE_ID = "id";
	public static final String COLUMN_NAME_RESOURCE_NAME = "name";
	public static final String COLUMN_NAME_RESOURCE_RESOURCE1 = "resource1";
	public static final String COLUMN_NAME_RESOURCE_RESOURCE2 = "resource2";
	public static final String COLUMN_NAME_RESOURCE_RESOURCEBG = "resourceBg";
	public static final String COLUMN_NAME_RESOURCE_SHORTNAME = "shortName";
	public static final String COLUMN_NAME_RESOURCE_TYPE = "type";

	public static final String COLUMN_NAME_SEEDTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SEEDTYPE_CROPTYPEID = "cropTypeId";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE = "resource";
	public static final String COLUMN_NAME_SEEDTYPE_SHORTNAME = "shortName";

	public static final String COLUMN_NAME_SOILMOISTURE_CLUSTER = "cluster";
	public static final String COLUMN_NAME_SOILMOISTURE_DATE = "date";
	public static final String COLUMN_NAME_SOILMOISTURE_ID = "id";
	public static final String COLUMN_NAME_SOILMOISTURE_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_SOILMOISTURE_VALUE = "value";

	public static final String COLUMN_NAME_UNIT_ACTIONTYPEID = "actionTypeId";
	public static final String COLUMN_NAME_UNIT_AUDIO = "audio";
	public static final String COLUMN_NAME_UNIT_ID = "id";
	public static final String COLUMN_NAME_UNIT_NAME = "name";
	public static final String COLUMN_NAME_UNIT_RESOURCE = "resource";

	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstname";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_USER_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_USER_ISENABLED = "isEnabled";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastname";
	public static final String COLUMN_NAME_USER_DEVICEID = "deviceId";
	public static final String COLUMN_NAME_USER_TIMESTAMP = "timestamp";

	public static final String COLUMN_NAME_WEATHERFORECAST_DATE = "date";
	public static final String COLUMN_NAME_WEATHERFORECAST_ID = "id";
	public static final String COLUMN_NAME_WEATHERFORECAST_TEMPERATURE = "temperature";
	public static final String COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID = "weatherTypeId";

	public static final String COLUMN_NAME_WEATHERTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_WEATHERTYPE_ID = "id";
	public static final String COLUMN_NAME_WEATHERTYPE_NAME = "name";
	public static final String COLUMN_NAME_WEATHERTYPE_RESOURCE = "resource";

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

	public static final int DIALOG_INTERCRIOP_ID = 3;
	public static final int DIALOG_IRRIGATION_METHOD_ID = 4;
	public static final int DIALOG_MONTH_ID = 1;
	public static final int DIALOG_SMILEYS_ID = 6;
	public static final int DIALOG_TREATMENT_ID = 2;
	public static final int DIALOG_UNITS_ID = 7;

	/** Identifier used to debug the database. */
	public static final String LOG_TAG = "RealFarm";
	/** Identifier of the current User. */
	public static long MAIN_USER_ID = -1;

	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONTYPE = "actionType";
	public static final String TABLE_NAME_CROP = "cropType";
	public static final String TABLE_NAME_DIALOG_ARRAYS = "dialogArrays";
	public static final String TABLE_NAME_MARKETPRICE = "marketPrice";
	public static final String TABLE_NAME_PLOT = "plot";
	public static final String TABLE_NAME_RESOURCE = "resource";
	public static final String TABLE_NAME_SEEDTYPE = "seedType";
	public static final String TABLE_NAME_SOILMOISTURE = "soilMoisture";
	public static final String TABLE_NAME_UNIT = "unit";
	public static final String TABLE_NAME_USER = "user";
	public static final String TABLE_NAME_WEATHERFORECAST = "weatherForecast";
	public static final String TABLE_NAME_WEATHERTYPE = "weatherType";

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
		mDb.delete(TABLE_NAME_ACTIONTYPE, null, null);
		mDb.delete(TABLE_NAME_ACTION, null, null);
		mDb.delete(TABLE_NAME_DIALOG_ARRAYS, null, null);
		mDb.delete(TABLE_NAME_RESOURCE, null, null);
		mDb.delete(TABLE_NAME_PLOT, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
		mDb.delete(TABLE_NAME_CROP, null, null);
		mDb.delete(TABLE_NAME_UNIT, null, null);
		mDb.delete(TABLE_NAME_USER, null, null);
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
		String deviceId = telephonyManager.getLine1Number();

		DEVICE_ID = deviceId;

		// sets the default value if invalid.
		if (deviceId == null) {
			deviceId = DEFAULT_NUMBER;
		}

		String[][] userData = {
				{ "John", "Doe", deviceId, "farmer_90px_kiran_kumar_g" },
				{ "Hendrik", "Knoche", "788844672", "farmer_90px_adam_jones" },
				{ "Chris", "Bishop", "788244421", "farmer_90px_neil_palmer" },
				{ "Chris", "McDougall", "781122672", "farmer_90px_neil_palmer2" },
				{ "Frank", "Herbert", "788111172", "farmer_90px_walmart_stores" } };

		// users
		ContentValues users = new ContentValues();
		for (int x = 0; x < userData.length; x++) {
			users.put(COLUMN_NAME_USER_FIRSTNAME, userData[x][0]);
			users.put(COLUMN_NAME_USER_LASTNAME, userData[x][1]);
			users.put(COLUMN_NAME_USER_DEVICEID, userData[x][2]);
			users.put(COLUMN_NAME_USER_IMAGEPATH, userData[x][3]);
			users.put(COLUMN_NAME_USER_ISENABLED, 0);
			users.put(COLUMN_NAME_USER_ISADMINACTION, 0);
			users.put(COLUMN_NAME_USER_TIMESTAMP, new Date().getTime());
			insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
			users.clear();
		}

		Log.d(LOG_TAG, "users works");

		Object[][] actionTypeData = {
				{ ACTION_TYPE_SOW_ID, "sow", "", R.drawable.ic_sow,
						R.raw.audio1 },
				{ ACTION_TYPE_FERTILIZE_ID, "fertilize", "",
						R.drawable.ic_fertilize, R.raw.audio2 },
				{ ACTION_TYPE_IRRIGATE_ID, "irrigate", "",
						R.drawable.ic_irrigate, R.raw.audio4 },
				{ ACTION_TYPE_REPORT_ID, "report", "", R.drawable.ic_problem,
						R.raw.audio6 },
				{ ACTION_TYPE_SPRAY_ID, "spray", "", R.drawable.ic_spray,
						R.raw.audio3 },
				{ ACTION_TYPE_HARVEST_ID, "harvest", "", R.drawable.ic_harvest,
						R.raw.audio5 },
				{ ACTION_TYPE_SELL_ID, "sell", "", R.drawable.ic_sell,
						R.raw.audio7 }

		};

		ContentValues actionType = new ContentValues();
		for (int x = 0; x < actionTypeData.length; x++) {
			actionType.put(COLUMN_NAME_ACTIONTYPE_ID,
					(Integer) actionTypeData[x][0]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_NAME,
					(String) actionTypeData[x][1]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_NAMEKANNADA,
					(String) actionTypeData[x][2]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_RESOURCE,
					(Integer) actionTypeData[x][3]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_AUDIO,
					(Integer) actionTypeData[x][4]);
			insertEntriesIntoDatabase(TABLE_NAME_ACTIONTYPE, actionType, db);
			actionType.clear();
		}

		Log.d(LOG_TAG, "actionType works");

		// pesticide types
		Object[][] pesticideTypesData = {
				{ "Pesticide", R.drawable.pesticide, R.raw.audio1 },
				{ "Fungicide", R.drawable.fungicide, R.raw.audio1 }

		};

		Log.d(LOG_TAG, "pesticide types works");

		// pesticides
		Object[][] pesticideData = {
				{ "Monocrotophos", R.drawable.icon, R.raw.audio1, 1,
						"Monocrotopho" },
				{ "Dimethoate", R.drawable.icon, R.raw.audio1, 1, "Dimethoate" },
				{ "Pesticide not listed", R.drawable.icon, R.raw.audio1, 1,
						"P-unlisted" },
				{ "Dithane M-45", R.drawable.icon, R.raw.audio1, 2,
						"Dithane M-45" },
				{ "Triazole", R.drawable.icon, R.raw.audio1, 2, "Triazole" },
				{ "Fungicide not listed", R.drawable.icon, R.raw.audio1, 2,
						"F-unlisted" }

		};

		Log.d(LOG_TAG, "pesticide works");

		// dialogArrays
		Object[][] dialogArrays = {

				{ "01 January", "01", -1, -1, R.raw.jan, 1, DIALOG_MONTH_ID,
						-1, -1 },
				{ "02 February", "02", -1, -1, R.raw.feb, 2, DIALOG_MONTH_ID,
						-1, -1 },
				{ "03 March", "03", -1, -1, R.raw.mar, 3, DIALOG_MONTH_ID, -1,
						-1 },
				{ "04 April", "04", -1, -1, R.raw.apr, 4, DIALOG_MONTH_ID, -1,
						-1 },
				{ "05 May", "05", -1, -1, R.raw.may, 5, DIALOG_MONTH_ID, -1, -1 },
				{ "06 June", "06", -1, -1, R.raw.jun, 6, DIALOG_MONTH_ID, -1,
						-1 },
				{ "07 July", "07", -1, -1, R.raw.jul, 7, DIALOG_MONTH_ID, -1,
						-1 },
				{ "08 August", "08", -1, -1, R.raw.aug, 8, DIALOG_MONTH_ID, -1,
						-1 },
				{ "09 September", "09", -1, -1, R.raw.sep, 9, DIALOG_MONTH_ID,
						-1, -1 },
				{ "10 October", "10", -1, -1, R.raw.oct, 10, DIALOG_MONTH_ID,
						-1, -1 },
				{ "11 November", "11", -1, -1, R.raw.nov, 11, DIALOG_MONTH_ID,
						-1, -1 },
				{ "12 December", "12", -1, -1, R.raw.dec, 12, DIALOG_MONTH_ID,
						-1, -1 },
				{ "Treated", "Treated", R.drawable.ic_sowingseedtreated, -1,
						R.raw.bagof10kg, 1, DIALOG_TREATMENT_ID, -1, -1 },
				{ "Not treated", "Not treated",
						R.drawable.ic_sowingseednottreated, -1,
						R.raw.bagof20kg, 2, DIALOG_TREATMENT_ID, -1, -1 },
				{ "Main crop", "Main crop", R.drawable.ic_maincrop, -1,
						R.raw.bagof10kg, 1, DIALOG_INTERCRIOP_ID, -1, -1 },
				{ "Intercrop", "Intercrop", R.drawable.ic_intercrop, -1,
						R.raw.bagof20kg, 2, DIALOG_INTERCRIOP_ID, -1, -1 },
				{ "Flooding", "Flooding", R.drawable.ic_flooding, -1,
						R.raw.bagof10kg, 1, DIALOG_IRRIGATION_METHOD_ID, -1, -1 },
				{ "Sprinkling", "Sprinkling", R.drawable.ic_sprinkling, -1,
						R.raw.bagof20kg, 2, DIALOG_IRRIGATION_METHOD_ID, -1, -1 },
				{ "Good", "", R.drawable.smiley_good, -1, R.raw.feedbackgood,
						1, DIALOG_SMILEYS_ID, -1, -1 },
				{ "Moderate", "", R.drawable.smiley_medium, -1,
						R.raw.feedbackmoderate, 2, DIALOG_SMILEYS_ID, -1, -1 },
				{ "Bad", "", R.drawable.smiley_bad, -1, R.raw.feedbackbad, 3,
						DIALOG_SMILEYS_ID, -1, -1 },
				{ "bag of 20 kgs", "20", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 20, DIALOG_UNITS_ID, 20, -1 },
				{ "bag of 21 kgs", "21", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 21, DIALOG_UNITS_ID, 21, -1 },
				{ "bag of 22 kgs", "22", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 22, DIALOG_UNITS_ID, 22, -1 },
				{ "bag of 23 kgs", "23", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 23, DIALOG_UNITS_ID, 23, -1 },
				{ "bag of 24 kgs", "24", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 24, DIALOG_UNITS_ID, 24, -1 },
				{ "bag of 25 kgs", "25", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 25, DIALOG_UNITS_ID, 25, -1 },
				{ "bag of 26 kgs", "26", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 26, DIALOG_UNITS_ID, 26, -1 },
				{ "bag of 27 kgs", "27", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 27, DIALOG_UNITS_ID, 27, -1 },
				{ "bag of 28 kgs", "28", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 28, DIALOG_UNITS_ID, 28, -1 },
				{ "bag of 29 kgs", "29", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 29, DIALOG_UNITS_ID, 29, -1 },
				{ "bag of 30 kgs", "30", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 30, DIALOG_UNITS_ID, 30, -1 },
				{ "bag of 31 kgs", "31", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 31, DIALOG_UNITS_ID, 31, -1 },
				{ "bag of 32 kgs", "32", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 32, DIALOG_UNITS_ID, 32, -1 },
				{ "bag of 33 kgs", "33", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 33, DIALOG_UNITS_ID, 33, -1 },
				{ "bag of 34 kgs", "34", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 34, DIALOG_UNITS_ID, 34, -1 },
				{ "bag of 35 kgs", "35", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 35, DIALOG_UNITS_ID, 35, -1 },
				{ "bag of 36 kgs", "36", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 36, DIALOG_UNITS_ID, 36, -1 },
				{ "bag of 37 kgs", "37", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 37, DIALOG_UNITS_ID, 37, -1 },
				{ "bag of 38 kgs", "38", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 38, DIALOG_UNITS_ID, 38, -1 },
				{ "bag of 39 kgs", "39", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 39, DIALOG_UNITS_ID, 39, -1 },
				{ "bag of 40 kgs", "40", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 40, DIALOG_UNITS_ID, 40, -1 },
				{ "bag of 41 kgs", "41", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 41, DIALOG_UNITS_ID, 41, -1 },
				{ "bag of 42 kgs", "42", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 42, DIALOG_UNITS_ID, 42, -1 },
				{ "bag of 43 kgs", "43", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 43, DIALOG_UNITS_ID, 43, -1 },
				{ "bag of 44 kgs", "44", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 44, DIALOG_UNITS_ID, 44, -1 },
				{ "bag of 45 kgs", "45", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 45, DIALOG_UNITS_ID, 45, -1 },
				{ "bag of 46 kgs", "46", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 46, DIALOG_UNITS_ID, 46, -1 },
				{ "bag of 47 kgs", "47", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 47, DIALOG_UNITS_ID, 47, -1 },
				{ "bag of 48 kgs", "48", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 48, DIALOG_UNITS_ID, 48, -1 },
				{ "bag of 49 kgs", "49", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 49, DIALOG_UNITS_ID, 49, -1 },
				{ "bag of 50 kgs", "50", R.drawable.ic_genericbaglarger, -1,
						R.raw.bagof10kg, 50, DIALOG_UNITS_ID, 50, -1 } };

		ContentValues dialogArray = new ContentValues();
		for (int x = 0; x < dialogArrays.length; x++) {
			dialogArray.put(COLUMN_NAME_DIALOG_NAME,
					(String) dialogArrays[x][0]);
			dialogArray.put(COLUMN_NAME_DIALOG_SHORTNAME,
					(String) dialogArrays[x][1]);
			dialogArray.put(COLUMN_NAME_DIALOG_RES,
					(Integer) dialogArrays[x][2]);
			dialogArray.put(COLUMN_NAME_DIALOG_RES2,
					(Integer) dialogArrays[x][3]);
			dialogArray.put(COLUMN_NAME_DIALOG_AUDIO,
					(Integer) dialogArrays[x][4]);
			dialogArray.put(COLUMN_NAME_DIALOG_VALUE,
					(Integer) dialogArrays[x][5]);
			dialogArray.put(COLUMN_NAME_DIALOG_TYPE,
					(Integer) dialogArrays[x][6]);
			dialogArray.put(COLUMN_NAME_DIALOG_NUMBER,
					(Integer) dialogArrays[x][7]);
			dialogArray.put(COLUMN_NAME_DIALOG_RES_BG,
					(Integer) dialogArrays[x][8]);
			insertEntriesIntoDatabase(TABLE_NAME_DIALOG_ARRAYS, dialogArray, db);
			dialogArray.clear();
		}

		Log.d(LOG_TAG, "dialog arrays works");

		// fertilizer
		Object[][] fertilizerData = {
				{ "Complex", R.raw.audio1, R.drawable.whitespaceicon, "Complex" },
				{ "Compost", R.raw.audio1, R.drawable.whitespaceicon, "Compost" },
				{ "DAP", R.raw.audio1, R.drawable.whitespaceicon, "DAP" },
				{ "Farm Yard Manure / FYM", R.raw.audio1,
						R.drawable.whitespaceicon, "FYM" },
				{ "Gypsum", R.raw.audio1, R.drawable.whitespaceicon, "Gypsum" },
				{ "Potash", R.raw.audio1, R.drawable.whitespaceicon, "Potash" },
				{ "Salt", R.raw.audio1, R.drawable.whitespaceicon, "Salt" },
				{ "Super", R.raw.audio1, R.drawable.whitespaceicon, "Super" },
				{ "Urea", R.raw.audio1, R.drawable.whitespaceicon, "Urea" },
				{ "Not in the list", R.raw.audio1, R.drawable.whitespaceicon,
						"Unlisted" } };

		Log.d(LOG_TAG, "fertilizer works");

		// problem types
		// Object[][] problemTypeData = {
		// { "Disease", R.raw.audio1, R.drawable.ic_diseasecategory },
		// { "Pest", R.raw.audio1, R.drawable.ic_pestcategory },
		// { "Other", R.raw.audio1, R.drawable.ic_otherproblemcategory } };

		// resources
		Object[][] resourceData = {
				/** SoilType */
				{ "Loamy", "Loamy", R.raw.bagof10kg, -1, -1, -1,
						RESOURCE_TYPE_SOILDTYPE },
				{ "Sandy", "Sandy", R.raw.bagof20kg, -1, -1, -1,
						RESOURCE_TYPE_SOILDTYPE },
				{ "Clay", "Clay", R.raw.bagof50kg, -1, -1, -1,
						RESOURCE_TYPE_SOILDTYPE },
				/** Problems */
				{ "Late leaf spot", "LLS", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Pod rot", "Pod rot", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Unknown disease", "? disease", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Disease not listed", "Aphids", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Aphids", "Aphids", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Leaf miner", "Leaf miner", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Pod borer", "Pod borer", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Red hairy caterpillar", "R H Caterpillar", R.raw.audio1, -1,
						-1, -1, RESOURCE_TYPE_PROBLEM },
				{ "Root grub", "Root grub", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Unknown pest", "? pest", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Pest not listed", "Pe-unlisted", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Low growth", "Low growth", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Pegs not developed", "Pegs undev", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Pod germination", "Pod germination", R.raw.audio1, -1, -1,
						-1, RESOURCE_TYPE_PROBLEM },
				{ "Reduced flowering", "Red flowering", R.raw.audio1, -1, -1,
						-1, RESOURCE_TYPE_PROBLEM },
				{ "Rot of stalks", "Stalk rot", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Too much vegetative growth", "Veg growth", R.raw.audio1, -1,
						-1, -1, RESOURCE_TYPE_PROBLEM },
				{ "Weeds", "Weeds", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Wild boar", "Wild boar", R.raw.audio1, -1, -1, -1,
						RESOURCE_TYPE_PROBLEM },
				{ "Problem not listed", "Pb-unlisted", R.raw.audio1, -1, -1,
						-1, RESOURCE_TYPE_PROBLEM } };

		ContentValues resource = new ContentValues();
		for (int x = 0; x < resourceData.length; x++) {
			resource.put(COLUMN_NAME_RESOURCE_NAME, (String) resourceData[x][0]);
			resource.put(COLUMN_NAME_RESOURCE_SHORTNAME,
					(String) resourceData[x][1]);
			resource.put(COLUMN_NAME_RESOURCE_AUDIO,
					(Integer) resourceData[x][2]);
			resource.put(COLUMN_NAME_RESOURCE_RESOURCE1,
					(Integer) resourceData[x][3]);
			resource.put(COLUMN_NAME_RESOURCE_RESOURCE2,
					(Integer) resourceData[x][4]);
			resource.put(COLUMN_NAME_RESOURCE_RESOURCEBG,
					(Integer) resourceData[x][5]);
			resource.put(COLUMN_NAME_RESOURCE_TYPE,
					(Integer) resourceData[x][6]);
			insertEntriesIntoDatabase(TABLE_NAME_RESOURCE, resource, db);
			resource.clear();
		}

		// units
		Object[][] unitData = {
				{ "seru(s)", R.drawable.ic_seruunit, R.raw.audio1,
						ACTION_TYPE_HARVEST_ID },
				{ "1L can(s)", R.drawable.ic_pesticideherbicidecan,
						R.raw.audio1, ACTION_TYPE_SPRAY_ID },
				{ "bag(s) of 1 kg", R.drawable.onekgbagpesticides,
						R.raw.audio1, ACTION_TYPE_SPRAY_ID },
				{ "bag(s) of 50 kg", R.drawable.ic_50kgbag, R.raw.audio1,
						ACTION_TYPE_FERTILIZE_ID },
				{ "cart load(s)", R.drawable.ic_cartload, R.raw.audio1,
						ACTION_TYPE_FERTILIZE_ID },
				{ "tractor load(s)", R.drawable.ic_tractorload, R.raw.audio1,
						ACTION_TYPE_FERTILIZE_ID },
				{ "unknown", R.drawable.icon, R.raw.audio1, ACTION_TYPE_ALL_ID },
				{ "none", R.drawable.icon, R.raw.audio1, ACTION_TYPE_ALL_ID } };

		ContentValues unit = new ContentValues();
		for (int x = 0; x < unitData.length; x++) {
			unit.put(COLUMN_NAME_UNIT_NAME, (String) unitData[x][0]);
			unit.put(COLUMN_NAME_UNIT_RESOURCE, (Integer) unitData[x][1]);
			unit.put(COLUMN_NAME_UNIT_AUDIO, (Integer) unitData[x][2]);
			unit.put(COLUMN_NAME_UNIT_ACTIONTYPEID, (Integer) unitData[x][3]);
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
		String[] typeForecast = { "Cloudy", "Sunny", "Chance of Rain",
				"Light Rain", "Rain" };

		for (int x = 0; x < 5; x++, calendar.add(Calendar.DAY_OF_MONTH, 1)) {

			wf.put(COLUMN_NAME_WEATHERFORECAST_DATE,
					df.format(calendar.getTime()));
			wf.put(COLUMN_NAME_WEATHERFORECAST_TEMPERATURE, tempForecast[x]);
			wf.put(COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID, typeForecast[x]);
			insertEntriesIntoDatabase(TABLE_NAME_WEATHERFORECAST, wf, db);
			wf.clear();
		}

		Log.d(LOG_TAG, "WeatherForecast inserted works");

		// seedType
		ContentValues seedtype = new ContentValues();
		Object[][] seedData = {
				{ "JL24", "", R.drawable.pic_72px_groundnut, 1,
						R.raw.msg_plant, "JL24" },
				{ "K6 / Kadari ghat", "", R.drawable.pic_72px_groundnut, 1,
						R.raw.msg_plant, "K6" },
				{ "Samrat", "", R.drawable.pic_72px_bajra, 1, R.raw.msg_plant,
						"Samrat" },
				{ "TMV2 / Bunching", "", R.drawable.pic_72px_castor, 1,
						R.raw.msg_plant, "TMV2" },
				{ "Bajra / pearl millet", "", R.drawable.pic_72px_bajra, 2,
						R.raw.msg_plant, "Bajra" },
				{ "Castor", "", R.drawable.pic_72px_castor, 3, R.raw.msg_plant,
						"Castor" },
				{ "Cow pea", "", R.drawable.pic_72px_cowpea, 4,
						R.raw.msg_plant, "Cow pea" },
				{ "Field beans", "", R.drawable.fieldbean, 5, R.raw.msg_plant,
						"Field beans" },
				{ "Green / moong gram", "", R.drawable.pic_72px_greengram, 6,
						R.raw.msg_plant, "Green gram" },
				{ "Horse gram", "", R.drawable.pic_72px_horsegram, 7,
						R.raw.msg_plant, "Horse gram" },
				{ "Padddy / rice", "", R.drawable.paddy, 8, R.raw.msg_plant,
						"Paddy" },
				{ "Ragi / finger millet", "", R.drawable.ragi, 9,
						R.raw.msg_plant, "Ragi" },
				{ "Sorghum", "", R.drawable.pic_72px_sorghum, 10,
						R.raw.msg_plant, "Sorghum" } };

		for (int x = 0; x < seedData.length; x++) {
			seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, (String) seedData[x][0]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
					(String) seedData[x][1]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
					(Integer) seedData[x][2]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_CROPTYPEID,
					(Integer) seedData[x][3]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, (Integer) seedData[x][4]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_SHORTNAME,
					(String) seedData[x][5]);
			insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
			seedtype.clear();
		}

		Log.d(LOG_TAG, "seedtype works");

		// cropTypes
		ContentValues croptype = new ContentValues();
		Object[][] cropData = {
				{ "Groundnut", R.drawable.pic_72px_groundnut,
						R.drawable.pic_90px_groundnut_tiled, R.raw.msg_plant,
						"Groundnut" },
				{ "Bajra / pearl millet", R.drawable.pic_72px_bajra,
						R.drawable.pic_90px_bajra_tiled, R.raw.msg_plant,
						"Bajra" },
				{ "Castor", R.drawable.pic_72px_castor,
						R.drawable.pic_90px_castor_tiled, R.raw.msg_plant,
						"Castor" },
				{ "Cow pea", R.drawable.pic_72px_cowpea,
						R.drawable.pic_90px_cowpea_tiled, R.raw.msg_plant,
						"Cow pea" },
				{ "Field beans", R.drawable.fieldbean, R.drawable.fieldbean,
						R.raw.msg_plant, "Field beans" },
				{ "Green / moong gram", R.drawable.pic_72px_greengram,
						R.drawable.pic_90px_greengram_tiled, R.raw.msg_plant,
						"Green gram" },
				{ "Horse gram", R.drawable.pic_72px_horsegram,
						R.drawable.pic_90px_horsegram_tiled, R.raw.msg_plant,
						"Horse gram" },
				{ "Padddy / rice", R.drawable.paddy, R.drawable.paddy,
						R.raw.msg_plant, "Padddy" },
				{ "Ragi / finger millet", R.drawable.ragi, R.drawable.ragi,
						R.raw.msg_plant, "Ragi" },
				{ "Sorghum", R.drawable.pic_72px_sorghum,
						R.drawable.pic_90px_sorghum_tiled, R.raw.msg_plant,
						"Sorghum" } };

		for (int x = 0; x < cropData.length; x++) {
			croptype.put(COLUMN_NAME_CROP_NAME, (String) cropData[x][0]);
			croptype.put(COLUMN_NAME_CROP_RESOURCE, (Integer) cropData[x][1]);
			croptype.put(COLUMN_NAME_CROP_RESOURCEBG, (Integer) cropData[x][2]);
			croptype.put(COLUMN_NAME_CROP_AUDIO, (Integer) cropData[x][3]);
			croptype.put(COLUMN_NAME_CROP_SHORTNAME, (String) cropData[x][4]);
			insertEntriesIntoDatabase(TABLE_NAME_CROP, croptype, db);
			croptype.clear();
		}
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

	protected Cursor rawQuery(String sql, String[] selectionArgs) {
		return mDb.rawQuery(sql, selectionArgs);
	}

	public int update(String tableName, ContentValues args, String whereClause,
			String[] whereArgs) {
		return mDb.update(tableName, args, whereClause, whereArgs);
	}
}
