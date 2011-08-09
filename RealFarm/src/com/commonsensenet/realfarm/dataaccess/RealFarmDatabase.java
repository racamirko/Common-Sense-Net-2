package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.commonsensenet.realfarm.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Class to manage database, i.e., input, remove and read data.
 * 
 * @author Julien Freudiger
 */
public class RealFarmDatabase {

	/**
	 * Class to facilitate creation of database. The database is created only if
	 * needed.
	 * 
	 * @author Julien Freudiger
	 */
	private class RealFarmDatabaseOpenHelper extends SQLiteOpenHelper {

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

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONNAME + " ( "
					+ COLUMN_NAME_ACTIONNAME_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONNAME_RESOURCE + " integer, "
					+ COLUMN_NAME_ACTIONNAME_NAME + " text not null " + " ); ");
			Log.d(DEBUG_ID, "Created actionName table");

			// actions
			db.execSQL("create table " + TABLE_NAME_ACTION + "  ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_ACTION_GROWINGID
					+ " references growing(id), " + COLUMN_NAME_ACTION_ACTIONID
					+ " references actionsNames(id), "
					+ COLUMN_NAME_ACTION_ACTIONDATE + " date " + " ); ");
			Log.d(DEBUG_ID, "Created action table");

			// growing
			db.execSQL("create table " + TABLE_NAME_GROWING + " ( "
					+ COLUMN_NAME_GROWING_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_GROWING_PLOTID + " references plots(id), "
					+ COLUMN_NAME_GROWING_SEEDID + " references seeds(id) "
					+ " ); ");
			Log.d(DEBUG_ID, "Created growing table");

			// plots
			db.execSQL("create table " + TABLE_NAME_PLOT + " ( "
					+ COLUMN_NAME_PLOT_ID + " integer primary key, "
					+ COLUMN_NAME_PLOT_USERID + " references users(id) "
					+ " ); ");
			Log.d(DEBUG_ID, "Created plot table");

			// points
			db.execSQL("create table " + TABLE_NAME_POINT + " ( "
					+ COLUMN_NAME_POINT_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_POINT_X + " integer, " + COLUMN_NAME_POINT_Y
					+ " integer, " + COLUMN_NAME_POINT_PLOTID
					+ " references plots(id) " + " ); ");
			Log.d(DEBUG_ID, "Created point table");

			// seeds
			db.execSQL("create table " + TABLE_NAME_SEED + " ( "
					+ COLUMN_NAME_SEED_ID + " integer primary key, "
					+ COLUMN_NAME_SEED_SEEDID + " references seedTypes(id) "
					+ " ); ");
			Log.d(DEBUG_ID, "Created seed table");

			// seedTypes
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_SEEDTYPE_VARIETY + " text " + " ); ");
			Log.d(DEBUG_ID, "Created seedtype table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID + " integer primary key, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text, "
					+ COLUMN_NAME_USER_MOBILE + " text " + " ); ");
			Log.d(DEBUG_ID, "Created user table");

			// users
			db.execSQL("create table " + TABLE_NAME_RECOMMENDATION + " ( "
					+ COLUMN_NAME_RECOMMENDATION_ID + " integer primary key, "
					+ COLUMN_NAME_RECOMMENDATION_SEEDID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_ACTIONID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_DATE + " date " + " ); ");
			Log.d(DEBUG_ID, "Created recommendation table");

			Log.d(DEBUG_ID, "Database created successfully");

			initValues(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	public static final String COLUMN_NAME_ACTION_ACTIONDATE = "actionDate";
	public static final String COLUMN_NAME_ACTION_ACTIONID = "actionID";
	public static final String COLUMN_NAME_ACTION_GROWINGID = "growingID";

	// table

	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_NAME = "name";
	public static final String COLUMN_NAME_ACTIONNAME_RESOURCE = "res";

	public static final String COLUMN_NAME_GROWING_ID = "id";
	public static final String COLUMN_NAME_GROWING_PLOTID = "plotID";
	public static final String COLUMN_NAME_GROWING_SEEDID = "seedID";

	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_USERID = "userID";

	public static final String COLUMN_NAME_POINT_ID = "id";
	public static final String COLUMN_NAME_POINT_PLOTID = "plotID";
	public static final String COLUMN_NAME_POINT_X = "x";
	public static final String COLUMN_NAME_POINT_Y = "y";

	public static final String COLUMN_NAME_RECOMMENDATION_ACTIONID = "actionID";
	public static final String COLUMN_NAME_RECOMMENDATION_DATE = "recommendationDate";
	public static final String COLUMN_NAME_RECOMMENDATION_ID = "id";
	public static final String COLUMN_NAME_RECOMMENDATION_SEEDID = "seedID";

	public static final String COLUMN_NAME_SEED_ID = "id";
	public static final String COLUMN_NAME_SEED_SEEDID = "seedID";

	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE = "res";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETY = "variety";

	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstName";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastName";
	public static final String COLUMN_NAME_USER_MOBILE = "mobileNumber";

	private static final String DB_NAME = "realFarm.db";
	private static final int DB_VERSION = 2;
	public static final String DEBUG_ID = "RealFarm";

	public static String DEFAULT_NUMBER = "000000000";
	public static String DEVICE_ID;

	public static int MAIN_USER_ID = -1;
	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONNAME = "actionName";
	public static final String TABLE_NAME_GROWING = "growing";
	public static final String TABLE_NAME_PLOT = "plot";
	public static final String TABLE_NAME_POINT = "point";
	public static final String TABLE_NAME_RECOMMENDATION = "recommendation";
	public static final String TABLE_NAME_SEED = "seed";
	public static final String TABLE_NAME_SEEDTYPE = "seedType";

	public static final String TABLE_NAME_USER = "user";

	private Context mContext;
	private SQLiteDatabase mDb;
	private RealFarmDatabaseOpenHelper mOpenHelper;

	/**
	 * Class constructor
	 * 
	 * @param context
	 */
	public RealFarmDatabase(Context context) {
		mContext = context;
		mOpenHelper = new RealFarmDatabaseOpenHelper(mContext);
	}

	public void clearValues() {
		// Delete current elements in table
		mDb.delete(TABLE_NAME_ACTIONNAME, null, null);
		mDb.delete(TABLE_NAME_ACTION, null, null);
		mDb.delete(TABLE_NAME_GROWING, null, null);
		mDb.delete(TABLE_NAME_PLOT, null, null);
		mDb.delete(TABLE_NAME_POINT, null, null);
		mDb.delete(TABLE_NAME_SEED, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
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
	 * Defines hardcoded initial values for database. This method is for testing
	 * purposes only and should be replaced by method to obtain location of
	 * plots from farmers directly.
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
		users.put(COLUMN_NAME_USER_MOBILE, "781827182");
		insertEntries(TABLE_NAME_USER, users, db);
		Log.d(DEBUG_ID, "users works");

		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Sow");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_sowing);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Fertilize");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_fertilizing1);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Spray");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_spraying1);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 4);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Irrigate");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_irrigation1);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 5);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Harvest");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_harvesting1);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 6);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Report");
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_72px_reporting);
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);

		Log.d(DEBUG_ID, "actionName works");

		// actions
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		ContentValues actions = new ContentValues();
		actions.put(COLUMN_NAME_ACTION_ACTIONID, 1);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		actions.put(COLUMN_NAME_ACTION_ACTIONDATE, dateFormat.format(date));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONID, 2);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		actions.put(COLUMN_NAME_ACTION_ACTIONDATE, dateFormat.format(date));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONID, 1);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 2);
		actions.put(COLUMN_NAME_ACTION_ACTIONDATE, dateFormat.format(date));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put(COLUMN_NAME_ACTION_ACTIONID, 1);
		actions.put(COLUMN_NAME_ACTION_GROWINGID, 3);
		actions.put(COLUMN_NAME_ACTION_ACTIONDATE, dateFormat.format(date));
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();

		Log.d(DEBUG_ID, "ACTION works");

		// growing
		ContentValues growing = new ContentValues();
		growing.put(COLUMN_NAME_GROWING_ID, 1);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 1);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 2);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 2);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 3);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 3);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();

		Log.d(DEBUG_ID, "growing works");

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

		// points

		final int[][] PLOT1 = { { 0, 8 }, { 10, 114 }, { 150, 170 },
				{ 105, -2 } };

		// final int[][] PLOT2 = { { -100, -96 }, { -90, 14 }, { 50, 70 }, { 5,
		// -102 } };

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
		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053733);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77169697);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053689);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170225);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();
		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053372);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170200);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();
		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053442);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77169622);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 2);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		final int[][] PLOT2 = { { -1800 + 1, -1800 + 3 },
				{ -1800 + 2, -1800 + 112 }, { -1800 + 174, -1800 + 188 },
				{ -1800 + 246, -1800 + 5 } };
		for (int x = 0; x < PLOT2.length; x++) {
			pointstoadd.put(COLUMN_NAME_POINT_X, PLOT2[x][0]);
			pointstoadd.put(COLUMN_NAME_POINT_Y, PLOT2[x][1]);
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

		ContentValues seed = new ContentValues();
		seed.put(COLUMN_NAME_SEED_ID, 1);
		seed.put(COLUMN_NAME_SEED_SEEDID, 1);
		insertEntries(TABLE_NAME_SEED, seed, db);
		seed.clear();
		seed.put(COLUMN_NAME_SEED_ID, 2);
		seed.put(COLUMN_NAME_SEED_SEEDID, 2);
		insertEntries(TABLE_NAME_SEED, seed, db);
		seed.clear();
		seed.put(COLUMN_NAME_SEED_ID, 3);
		seed.put(COLUMN_NAME_SEED_SEEDID, 3);
		insertEntries(TABLE_NAME_SEED, seed, db);
		seed.clear();
		seed.put(COLUMN_NAME_SEED_ID, 4);
		seed.put(COLUMN_NAME_SEED_SEEDID, 4);
		insertEntries(TABLE_NAME_SEED, seed, db);
		seed.clear();
		Log.d(DEBUG_ID, "seed works");

		ContentValues seedtype = new ContentValues();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 1);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Groundnut");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "TMV2");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 2);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Groundnut");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "Samrat");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 3);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Bajra");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_bajra);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Castor");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_castor);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Cowpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_cowpea);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Greengram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_greengram);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Horsegram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_horsegram);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Pigeonpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_pigeonpea);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Redgram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_redgram);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Sorghum");
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_sorghum);
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		Log.d(DEBUG_ID, "seedtype works");

		ContentValues recommendation = new ContentValues();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(date));
		insertEntries(TABLE_NAME_RECOMMENDATION, recommendation, db);
		recommendation.clear();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ID, 2);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 2);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(date));
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
