package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
 * @author Oscar Bolanos (oscar.bolanos@epfl.ch)
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

			Log.d(DEBUG_ID, "Try to fill up database with tables");

			// actions
			db.execSQL("create table " + TABLE_NAME_ACTION + "  ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_ACTION_GROWINGID
					+ " references growing(id), " + COLUMN_NAME_ACTION_ACTIONNAMEID
					+ " references actionName(id), " + COLUMN_NAME_ACTION_QUANTITY
					+ " integer, " + COLUMN_NAME_ACTION_UNITID
					+ " references unit(id), " + COLUMN_NAME_ACTION_QUANTITY2
					+ " integer, " + COLUMN_NAME_ACTION_DATE + " date "
					+ " ); ");
			Log.d(DEBUG_ID, "Created action table");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONNAME + " ( "
					+ COLUMN_NAME_ACTIONNAME_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONNAME_RESOURCE + " integer, "
					+ COLUMN_NAME_ACTIONNAME_AUDIO + " integer, "
					+ COLUMN_NAME_ACTIONNAME_NAME + " text not null " + " ); ");
			Log.d(DEBUG_ID, "Created actionName table");

			// actionTranslation
			db.execSQL("create table " + TABLE_NAME_ACTIONTRANSLATION + " ( "
					+ COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID
					+ " integer references actionName(id), "
					+ COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD + " text, "
					+ COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE + " text, "
					+ COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD
					+ " text, " + COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE
					+ " text " + " ); ");
			Log.d(DEBUG_ID, "Created action translation table");

			// fertilizers
			db.execSQL("create table " + TABLE_NAME_FERTILIZER + " ( "
					+ COLUMN_NAME_FERTILIZER_ID + " integer primary key, "
					+ COLUMN_NAME_FERTILIZER_NAME + " text, "
					+ COLUMN_NAME_FERTILIZER_AUDIO + " integer, "
					+ COLUMN_NAME_FERTILIZER_STAGEID
					+ " references stage(id), " + COLUMN_NAME_FERTILIZER_UNITID
					+ " references unit(id) " + " ); ");
			Log.d(DEBUG_ID, "Created fertilizer table");

			// growing
			db.execSQL("create table " + TABLE_NAME_GROWING + " ( "
					+ COLUMN_NAME_GROWING_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_GROWING_PLOTID + " references plot(id), "
					+ COLUMN_NAME_GROWING_SEEDID + " references seed(id), "
					+ COLUMN_NAME_GROWING_SOWINGDATE + " date " + " ); ");
			Log.d(DEBUG_ID, "Created growing table");

			// log
			db.execSQL("create table " + TABLE_NAME_LOG + " ( "
					+ COLUMN_NAME_LOG_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_LOG_NAME + " text not null, "
					+ COLUMN_NAME_LOG_VALUE + " text, " + COLUMN_NAME_LOG_DATE
					+ " date " + " ); ");
			Log.d(DEBUG_ID, "Created log table");

			// pesticides

			db.execSQL("create table " + TABLE_NAME_PESTICIDE + " ( "
					+ COLUMN_NAME_PESTICIDE_ID + " integer primary key, "
					+ COLUMN_NAME_PESTICIDE_NAME + " text, "
					+ COLUMN_NAME_PESTICIDE_AUDIO + " integer " + " ); ");
			Log.d(DEBUG_ID, "Created pesticide table");

			// plots
			db.execSQL("create table " + TABLE_NAME_PLOT + " ( "
					+ COLUMN_NAME_PLOT_ID + " integer primary key, "
					+ COLUMN_NAME_PLOT_USERID + " references user(id) "
					+ " ); ");
			Log.d(DEBUG_ID, "Created plot table");

			// points
			db.execSQL("create table " + TABLE_NAME_POINT + " ( "
					+ COLUMN_NAME_POINT_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_POINT_X + " integer, " + COLUMN_NAME_POINT_Y
					+ " integer, " + COLUMN_NAME_POINT_PLOTID
					+ " references plot(id) " + " ); ");
			Log.d(DEBUG_ID, "Created point table");

			// problems
			db.execSQL("create table " + TABLE_NAME_PROBLEMTYPE + " ( "
					+ COLUMN_NAME_PROBLEMTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_PROBLEMTYPE_NAME + " text, "
					+ COLUMN_NAME_PROBLEMTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_PROBLEMTYPE_RESOURCE + " integer " + " ); ");
			Log.d(DEBUG_ID, "Created problem table");

			// problems
			db.execSQL("create table " + TABLE_NAME_PROBLEM + " ( "
					+ COLUMN_NAME_PROBLEM_ID + " integer primary key, "
					+ COLUMN_NAME_PROBLEM_NAME + " text, "
					+ COLUMN_NAME_PROBLEM_AUDIO + " integer, "
					+ COLUMN_NAME_PROBLEM_RESOURCE + " integer, "
					+ COLUMN_NAME_PROBLEM_PROBLEMTYPEID + " integer " + " ); ");
			Log.d(DEBUG_ID, "Created problem table");

			// recommendations
			db.execSQL("create table " + TABLE_NAME_RECOMMENDATION + " ( "
					+ COLUMN_NAME_RECOMMENDATION_ID + " integer primary key, "
					+ COLUMN_NAME_RECOMMENDATION_SEEDID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_ACTIONID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_DATE + " date " + " ); ");
			Log.d(DEBUG_ID, "Created recommendation table");

			// seedTypes
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ COLUMN_NAME_SEEDTYPE_NAMEKANNADA + " text, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_SEEDTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST + " integer, "
					+ COLUMN_NAME_SEEDTYPE_VARIETY + " text, "
					+ COLUMN_NAME_SEEDTYPE_VARIETYKANNADA + " text " + " ); ");
			Log.d(DEBUG_ID, "Created seed type table");

			// seedTypeStages
			db.execSQL("create table " + TABLE_NAME_SEEDTYPESTAGE + " ( "
					+ COLUMN_NAME_SEEDTYPESTAGE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SEEDTYPESTAGE_STAGEID + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS + " integer "
					+ " ); ");
			Log.d(DEBUG_ID, "Created seed type stages table");

			// stages
			db.execSQL("create table " + TABLE_NAME_STAGE + " ( "
					+ COLUMN_NAME_STAGE_ID + " integer primary key, "
					+ COLUMN_NAME_STAGE_NAME + " text" + " ); ");
			Log.d(DEBUG_ID, "Created stage table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID + " integer primary key, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text, "
					+ COLUMN_NAME_USER_MOBILE + " text " + " ); ");
			Log.d(DEBUG_ID, "Created user table");

			// units
			db.execSQL("create table " + TABLE_NAME_UNIT + " ( "
					+ COLUMN_NAME_UNIT_ID + " integer primary key, "
					+ COLUMN_NAME_UNIT_NAME + " text not null, "
					+ COLUMN_NAME_UNIT_AUDIO + " integer " + " ); ");
			Log.d(DEBUG_ID, "Created unit table");

			Log.d(DEBUG_ID, "Database created successfully");

			initValues(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	public static final String COLUMN_NAME_ACTION_DATE = "actionDate";
	public static final String COLUMN_NAME_ACTION_ACTIONNAMEID = "actionNameID";
	public static final String COLUMN_NAME_ACTION_GROWINGID = "growingID";
	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_QUANTITY = "quantity";
	public static final String COLUMN_NAME_ACTION_QUANTITY2 = "quantity2";
	public static final String COLUMN_NAME_ACTION_UNITID = "unitID";
	
	public static final String COLUMN_NAME_ACTIONNAME_AUDIO = "audio";
	public static final String COLUMN_NAME_ACTIONNAME_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_NAME = "name";
	public static final String COLUMN_NAME_ACTIONNAME_RESOURCE = "res";

	public static final String COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID = "actionNameID";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD = "indObjectIDField";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE = "indObjectTable";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD = "targetIDField";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE = "targetTable";

	private static final String COLUMN_NAME_FERTILIZER_AUDIO = "audio";
	private static final String COLUMN_NAME_FERTILIZER_ID = "id";
	private static final String COLUMN_NAME_FERTILIZER_NAME = "name";
	private static final String COLUMN_NAME_FERTILIZER_STAGEID = "stageID";
	private static final String COLUMN_NAME_FERTILIZER_UNITID = "unitID";
	
	public static final String COLUMN_NAME_GROWING_ID = "id";
	public static final String COLUMN_NAME_GROWING_PLOTID = "plotID";
	public static final String COLUMN_NAME_GROWING_SEEDID = "seedID";
	public static final String COLUMN_NAME_GROWING_SOWINGDATE = "sowingDate";
	
	public static final String COLUMN_NAME_LOG_DATE = "logDate";
	public static final String COLUMN_NAME_LOG_ID = "id";
	public static final String COLUMN_NAME_LOG_NAME = "name";
	public static final String COLUMN_NAME_LOG_VALUE = "value";

	public static final String COLUMN_NAME_PESTICIDE_AUDIO = "audio";
	public static final String COLUMN_NAME_PESTICIDE_ID = "id";
	public static final String COLUMN_NAME_PESTICIDE_NAME = "name";
	
	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_USERID = "userID";

	public static final String COLUMN_NAME_POINT_ID = "id";
	public static final String COLUMN_NAME_POINT_PLOTID = "plotID";
	public static final String COLUMN_NAME_POINT_X = "x";
	public static final String COLUMN_NAME_POINT_Y = "y";

	public static final String COLUMN_NAME_PROBLEM_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEM_ID = "id";
	public static final String COLUMN_NAME_PROBLEM_NAME = "name";
	public static final String COLUMN_NAME_PROBLEM_PROBLEMTYPEID = "masterID";
	public static final String COLUMN_NAME_PROBLEM_RESOURCE = "res";
	
	public static final String COLUMN_NAME_PROBLEMTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEMTYPE_ID = "id";
	public static final String COLUMN_NAME_PROBLEMTYPE_NAME = "name";
	public static final String COLUMN_NAME_PROBLEMTYPE_RESOURCE = "res";
	
	public static final String COLUMN_NAME_RECOMMENDATION_ACTIONID = "actionID";
	public static final String COLUMN_NAME_RECOMMENDATION_DATE = "recommendationDate";
	public static final String COLUMN_NAME_RECOMMENDATION_ID = "id";
	public static final String COLUMN_NAME_RECOMMENDATION_SEEDID = "seedID";
	
	public static final String COLUMN_NAME_SEED_AUDIO = "seedAudio";
	public static final String COLUMN_NAME_SEED_ID = "id";
	public static final String COLUMN_NAME_SEED_SEEDID = "seedID";
	
	public static final String COLUMN_NAME_SEEDTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST = "daysToHarvest";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE = "res";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETY = "variety";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETYKANNADA = "varietyKannada";

	public static final String COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS = "fromCountDays";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID = "seedTypeID";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_STAGEID = "stageID";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS = "toCountDays";
	
	public static final String COLUMN_NAME_STAGE_ID = "id";
	public static final String COLUMN_NAME_STAGE_NAME = "name";

	public static final String COLUMN_NAME_UNIT_AUDIO = "audio";
	public static final String COLUMN_NAME_UNIT_ID = "id";
	public static final String COLUMN_NAME_UNIT_NAME = "name";
	
	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstName";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastName";
	public static final String COLUMN_NAME_USER_MOBILE = "mobileNumber";

	/** Date format used to store the dates. */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** Filename of the database. */
	public static final String DB_NAME = "realFarm.db";
	/** Current version of the database. */
	private static final int DB_VERSION = 1;

	public static final String DEBUG_ID = "RealFarm";
	public static String DEFAULT_NUMBER = "000000000";
	public static String DEVICE_ID;
	public static int MAIN_USER_ID = -1;
	
	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONNAME = "actionName";
	public static final String TABLE_NAME_ACTIONTRANSLATION = "actionTranslation";
	public static final String TABLE_NAME_FERTILIZER = "fertilizer";
	public static final String TABLE_NAME_GROWING = "growing";
	public static final String TABLE_NAME_LOG = "log";
	public static final String TABLE_NAME_PESTICIDE = "pesticide";
	public static final String TABLE_NAME_PLOT = "plot";
	public static final String TABLE_NAME_POINT = "point";
	public static final String TABLE_NAME_PROBLEM = "problem";
	public static final String TABLE_NAME_PROBLEMTYPE = "problemType";
	public static final String TABLE_NAME_RECOMMENDATION = "recommendation";
	public static final String TABLE_NAME_SEEDTYPE = "seedType";
	public static final String TABLE_NAME_SEEDTYPESTAGE = "seedTypeStage";
	public static final String TABLE_NAME_STAGE = "stage";
	public static final String TABLE_NAME_UNIT = "unit";
	public static final String TABLE_NAME_USER = "user";
	
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
		mDb.delete(TABLE_NAME_ACTIONTRANSLATION, null, null);
		mDb.delete(TABLE_NAME_GROWING, null, null);
		mDb.delete(TABLE_NAME_FERTILIZER, null, null);
		mDb.delete(TABLE_NAME_PESTICIDE, null, null);
		mDb.delete(TABLE_NAME_PROBLEM, null, null);
		mDb.delete(TABLE_NAME_PROBLEMTYPE, null, null);
		mDb.delete(TABLE_NAME_PLOT, null, null);
		mDb.delete(TABLE_NAME_POINT, null, null);
		mDb.delete(TABLE_NAME_RECOMMENDATION, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPESTAGE, null, null);
		mDb.delete(TABLE_NAME_STAGE, null, null);
		mDb.delete(TABLE_NAME_UNIT, null, null);
		mDb.delete(TABLE_NAME_USER, null, null);

		Log.d(DEBUG_ID, "Cleared existing content if any");
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
				Log.d(DEBUG_ID, "Exception" + e);
			}
		}
		return result;
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
	 * Runs the provided SQL and returns a Cursor over the result set.
	 * 
	 * @param sql the SQL query. The SQL string must not be ; terminated
	 * @param selectionArgs You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.
	 * @return a Cursor object with the query result.
	 *
	 */
	public Cursor executeQuery(String sql, String[] selectionArgs) {
		return mDb.rawQuery(sql, selectionArgs);
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

		Log.d(DEBUG_ID, "Try to fill up tables with content" + db.getVersion());

		// users
		ContentValues users = new ContentValues();
		// 1

		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getLine1Number();

		DEVICE_ID = deviceID;

		String mobileNumber;
		if (deviceID == null)
			mobileNumber = DEFAULT_NUMBER;
		else
			mobileNumber = deviceID;

		// users 1 rest
		users.put(COLUMN_NAME_USER_ID, 1);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "John");
		users.put(COLUMN_NAME_USER_LASTNAME, "Doe");
		users.put(COLUMN_NAME_USER_MOBILE, mobileNumber);
		insertEntries(TABLE_NAME_USER, users, db);
		users.clear();

		// 2
		users.put(COLUMN_NAME_USER_ID, 2);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Hendrik");
		users.put(COLUMN_NAME_USER_LASTNAME, "Knoche");
		users.put(COLUMN_NAME_USER_MOBILE, "788844672");
		insertEntries(TABLE_NAME_USER, users, db);
		Log.d(DEBUG_ID, "users works");

		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Sow");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_sowing);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 4);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Fertilize");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_fertilizing2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio2);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 5);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Spray");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_spraying3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio3);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 7);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Irrigate");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_irrigation2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio4);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 8);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Harvest");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_harvesting1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio5);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 6);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Report");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_reporting);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio6);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);

		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 9);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Diary");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_diary1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio7);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);

		Log.d(DEBUG_ID, "actionName works");

		// actionTranslation

		// 3 for sowing
		ContentValues actionTranslation = new ContentValues();
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 3);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_PLOT_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_PLOT);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				COLUMN_NAME_SEEDTYPE_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE,
				TABLE_NAME_SEEDTYPE);
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		// 4 for fertilizing
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 4);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_GROWING_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_GROWING);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				COLUMN_NAME_FERTILIZER_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE,
				TABLE_NAME_FERTILIZER);
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		// 5 for spraying x
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 5);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_GROWING_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_GROWING);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				COLUMN_NAME_PESTICIDE_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE,
				TABLE_NAME_PESTICIDE);
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		// 6 for reporting (of problems)
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 6);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_GROWING_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_GROWING);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				COLUMN_NAME_PROBLEM_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE,
				TABLE_NAME_PROBLEM);
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		// 7 for irrigation
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 7);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_PLOT_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_PLOT);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				"");
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE, "");
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		// 8 for harvesting
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID, 8);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
				COLUMN_NAME_GROWING_ID);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE,
				TABLE_NAME_GROWING);
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
				"");
		actionTranslation.put(COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE, "");
		insertEntries(TABLE_NAME_ACTIONTRANSLATION, actionTranslation, db);
		actionTranslation.clear();

		Log.d(DEBUG_ID, "action translation works");

		// 2
		users.put(COLUMN_NAME_USER_ID, 2);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Hendrik");
		users.put(COLUMN_NAME_USER_LASTNAME, "Knoche");
		users.put(COLUMN_NAME_USER_MOBILE, "788479621");
		insertEntries(TABLE_NAME_USER, users, db);
		Log.d(DEBUG_ID, "users works");

		// 2
		ContentValues unit = new ContentValues();
		unit.put(COLUMN_NAME_UNIT_ID, 1);
		unit.put(COLUMN_NAME_UNIT_NAME, "unknown");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);

		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();

		unit.put(COLUMN_NAME_UNIT_ID, 2);
		unit.put(COLUMN_NAME_UNIT_NAME, "none");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 3);
		unit.put(COLUMN_NAME_UNIT_NAME, "cart/tractor loads");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 4);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of days");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 5);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of year");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 6);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of nights");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 7);
		unit.put(COLUMN_NAME_UNIT_NAME, "bags of 50 kg");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 8);
		unit.put(COLUMN_NAME_UNIT_NAME,
				"number of main crop rows between each row");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);

		insertEntries(TABLE_NAME_UNIT, unit, db);
		unit.clear();

		Log.d(DEBUG_ID, "unit works");

		// actions
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -15);

		ContentValues actions = new ContentValues();
		actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		actions.put(COLUMN_NAME_ACTION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		calendar.add(Calendar.DATE, 2);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 4);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		actions.put(COLUMN_NAME_ACTION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		calendar.add(Calendar.DATE, 1);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 2);
		actions.put(COLUMN_NAME_ACTION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		calendar.add(Calendar.DATE, 1);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 3);
		actions.put(COLUMN_NAME_ACTION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();

		Log.d(DEBUG_ID, "ACTION works");

		// growing
		ContentValues growing = new ContentValues();
		growing.put(COLUMN_NAME_GROWING_ID, 1);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 1);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 3);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 2);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 2);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 4);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 3);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 1);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 5);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 4);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 3);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 6);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();

		Log.d(DEBUG_ID, "growing works");

		//
		// STAGE
		ContentValues stage = new ContentValues();
		stage.put(COLUMN_NAME_STAGE_ID, 3);
		stage.put(COLUMN_NAME_STAGE_NAME, "planning");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 4);
		stage.put(COLUMN_NAME_STAGE_NAME, "soil preparation");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 5);
		stage.put(COLUMN_NAME_STAGE_NAME, "sowing");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 6);
		stage.put(COLUMN_NAME_STAGE_NAME, "vegetative growth");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 7);
		stage.put(COLUMN_NAME_STAGE_NAME, "flowering");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 8);
		stage.put(COLUMN_NAME_STAGE_NAME, "pod-filling");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 9);
		stage.put(COLUMN_NAME_STAGE_NAME, "pod maturity");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 10);
		stage.put(COLUMN_NAME_STAGE_NAME, "harvesting");
		insertEntries(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		Log.d(DEBUG_ID, "stages works");

		// seedTypeStages

		ContentValues seedtypestage = new ContentValues();

		String[] columnNames = { COLUMN_NAME_SEEDTYPESTAGE_STAGEID,
				COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID,
				COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS,
				COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS };

		int[][] rows = { { 5, 3, 0, 10 }, { 6, 3, 11, 30 }, { 7, 3, 31, 60 },
				{ 8, 3, 61, 90 }, { 9, 3, 90, 110 }, { 10, 3, 100, 110 },
				{ 5, 4, 0, 10 }, { 6, 4, 11, 35 }, { 7, 4, 36, 80 },
				{ 8, 4, 70, 110 }, { 9, 4, 111, 120 }, { 10, 4, 115, 130 } };

		for (int i = 0; i < rows.length - 1; i++) {
			for (int j = 0; j < rows[j].length - 1; j++) {
				seedtypestage.put(columnNames[j], rows[i][j]);
				// Log.d(DEBUG_ID, columnNames[j] + rows[i][j]);
			}
		}
		insertEntries(TABLE_NAME_SEEDTYPESTAGE, seedtypestage, db);

		seedtypestage.clear();
		Log.d(DEBUG_ID, "seed type stages works");

		// plots
		ContentValues plots = new ContentValues();
		plots.put(COLUMN_NAME_PLOT_ID, 1);
		plots.put(COLUMN_NAME_PLOT_USERID, 1);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		plots.put(COLUMN_NAME_PLOT_ID, 2);
		plots.put(COLUMN_NAME_PLOT_USERID, 2);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		plots.put(COLUMN_NAME_PLOT_ID, 3);
		plots.put(COLUMN_NAME_PLOT_USERID, 1);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		Log.d(DEBUG_ID, "plots works");

		// //problem types
		//
		// //problems NOT DONE
		//
		// ContentValues problems = new ContentValues();
		//
		// String[] problemcolumns = {COLUMN_NAME_SEEDTYPESTAGE_STAGEID,
		// COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID,
		// COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS,
		// COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS};
		//
		// int[][] problemmatrix = {
		// {8, 4, 70, 110},
		// {9, 4, 111, 120},
		// {10, 4, 115, 130}};
		//
		// for (int i = 0; i < rows.length - 1; i++){
		// for (int j=0; j<rows[j].length - 1; j++){
		// seedtypestage.put(problemcolumns[j], problemmatrix[i][j]);
		// //Log.d(DEBUG_ID, problemcolumns[j] + problemmatrix[i][j]);
		// }
		// }
		// insertEntries(TABLE_NAME_PROBLEM, problems, db);
		//
		// seedtypestage.clear();
		// Log.d(DEBUG_ID, "problem table works");

		// points
		final int[][] PLOT1 = { { -50, -42 }, { -48, 50 }, { 49, 52 },
				{ 58, -49 } };

		ContentValues pointstoadd = new ContentValues();
		for (int x = 0; x < PLOT1.length; x++) {
			pointstoadd.put(COLUMN_NAME_POINT_X, PLOT1[x][0]);
			pointstoadd.put(COLUMN_NAME_POINT_Y, PLOT1[x][1]);
			pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
			insertEntries(TABLE_NAME_POINT, pointstoadd, db);
			pointstoadd.clear();
		}

		// ContentValues pointstoadd = new ContentValues();
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053519);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170492);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053333);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170486);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053322);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170775);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053508);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170769);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();

		// User 2
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053733);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77169697);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053689);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170225);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053372);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170200);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053442);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77169622);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();

		final int[][] PLOT2 = { { -150, -96 }, { -110, 14 }, { -10, 70 },
				{ 5, -102 } };
		for (int x = 0; x < PLOT2.length; x++) {
			pointstoadd.put(COLUMN_NAME_POINT_X, PLOT2[x][0] + 200);
			pointstoadd.put(COLUMN_NAME_POINT_Y, PLOT2[x][1] + 200);
			pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
			insertEntries(TABLE_NAME_POINT, pointstoadd, db);
			pointstoadd.clear();
		}

		final int[][] PLOT3 = { { -1800 + 1, -1800 + 3 },
				{ -1800 + 2, -1800 + 112 }, { -1800 + 174, -1800 + 188 },
				{ -1800 + 246, -1800 + 5 } };

		for (int x = 0; x < PLOT3.length; x++) {
			pointstoadd.put(COLUMN_NAME_POINT_X, PLOT3[x][0]);
			pointstoadd.put(COLUMN_NAME_POINT_Y, PLOT3[x][1]);
			pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
			insertEntries(TABLE_NAME_POINT, pointstoadd, db);
			pointstoadd.clear();
		}

		// User 1 again
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14054019);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170783);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14054017);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77171331);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053656);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77171344);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();
		//
		// pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053675);
		// pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170778);
		// pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		// insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		// pointstoadd.clear();

		Log.d(DEBUG_ID, "points works");

		ContentValues seedtype = new ContentValues();
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
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 3);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Groundnut");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಕಡಲೆ ಕಾಯಿ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "TMV2");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETYKANNADA, "ನೆಟೆ ಕಾಯಿ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Groundnut");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಕಡಲೆ ಕಾಯಿ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "Samrat");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETYKANNADA, "ಸಮ್ರಟ್");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 5);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Bajra");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಸಜ್ಜೆ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_bajra);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 6);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Castor");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಔಡಲ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_castor);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 7);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Cowpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಅಲಸಂದಿ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_cowpea);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 8);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Greengram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಹೆಸರು ಕಾಳು");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_greengram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 9);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Horsegram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಹುರಳಿ ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_horsegram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 10);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Pigeonpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ತೊಗರಿ ಬೀಜ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_pigeonpea);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 11);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Redgram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಕಾಳು");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_redgram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 12);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Sorghum");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA, "ಸಜ್ಜೆ");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_sorghum);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.audio1);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		Log.d(DEBUG_ID, "seedtype works");

		ContentValues recommendation = new ContentValues();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_RECOMMENDATION, recommendation, db);
		recommendation.clear();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ID, 2);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 2);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntries(TABLE_NAME_RECOMMENDATION, recommendation, db);
		recommendation.clear();
		Log.d(DEBUG_ID, "recommendation works");

	}

	/**
	 * Method to insert values into the database
	 * 
	 * @param ContentValues
	 *            to insert
	 * @return long result is 0 if db close, -1 if error, rowID if success
	 */
	public long insertEntries(String TableName, ContentValues values,
			SQLiteDatabase db) {
		long result = -1;

		if ((TableName != null) && (values != null)) {
			// result = db.insert(TableName, null, values);
			try {
				result = db.insertOrThrow(TableName, null, values);
			} catch (SQLException e) {
				Log.d(DEBUG_ID, "Exception" + e);
			}
		}
		return result;
	}

	public long insertEntriesdb(String TableName, ContentValues values) {
		long result = -1;

		if ((TableName != null) && (values != null)) {
			// result = db.insert(TableName, null, values);
			try {
				result = mDb.insertOrThrow(TableName, null, values);
			} catch (SQLException e) {
				Log.d(DEBUG_ID, "Exception" + e);
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
		if (mDb == null || !mDb.isOpen())
			mDb = mOpenHelper.getWritableDatabase();
	}

	public int update(String tableName, ContentValues args, String whereClause,
			String[] whereArgs) {

		return mDb.update(tableName, args, whereClause, whereArgs);

	}

}
