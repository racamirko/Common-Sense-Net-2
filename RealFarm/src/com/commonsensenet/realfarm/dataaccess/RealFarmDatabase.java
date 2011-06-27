package com.commonsensenet.realfarm.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
		 * @author Julien Freudiger
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.d("RealFarm", "Try to fill up database with tables");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONNAME + " ( "
					+ COLUMN_NAME_ACTIONNAME_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONNAME_NAME + " text not null " + " ); ");

			Log.d("RealFarm", "Created actionName table");

			// actions
			db.execSQL("create table " + TABLE_NAME_ACTION + "  ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer primary key autoincrement, "
					+ "growingID references growing(id), "
					+ "actionID references actionsNames(id), " + ACTIONDATE
					+ " integer " + " ); ");
			Log.d("RealFarm", "Created action table");

			// growing
			db.execSQL("create table " + TABLE_NAME_GROWING + " ( "
					+ COLUMN_NAME_GROWING_ID + " integer primary key, "
					+ "plotID references plots(id), "
					+ "seedID references seeds(id) " + " ); ");
			Log.d("RealFarm", "Created growing table");

			// plots
			db.execSQL("create table " + TABLE_NAME_PLOT + " ( "
					+ COLUMN_NAME_PLOT_ID + " integer primary key, "
					+ "userID references users(id) " + " ); ");
			Log.d("RealFarm", "Created plot table");

			// points
			db.execSQL("create table " + TABLE_NAME_POINT + " ( "
					+ COLUMN_NAME_POINT_ID
					+ " integer primary key autoincrement, " + "x integer, "
					+ "y integer, " + "plotID references plots(id) " + " ); ");
			Log.d("RealFarm", "Created point table");

			// seeds
			db.execSQL("create table " + TABLE_NAME_SEED + " ( "
					+ COLUMN_NAME_SEED_ID + " integer primary key, "
					+ "seedID references seedTypes(id) " + " ); ");
			Log.d("RealFarm", "Created seed table");

			// seedTypes
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ "variety text " + " ); ");
			Log.d("RealFarm", "Created seedtype table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID + " integer primary key, "
					+ "firstName text not null, " + "lastName text, "
					+ "mobileNumber integer " + " ); ");
			Log.d("RealFarm", "Created user table");

			Log.d("RealFarm", "Database created successfully");

			initValues(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	// table
	private static final String ACTIONDATE = "actionDate";
	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_NAME = "name";
	public static final String COLUMN_NAME_ACTIONNAME_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_NAME = "name";
	public static final String COLUMN_NAME_GROWING_ID = "id";
	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_USERID = "userID";
	public static final String COLUMN_NAME_POINT_ID = "id";
	public static final String COLUMN_NAME_POINT_PLOTID = "plotID";
	public static final String COLUMN_NAME_POINT_X = "x";
	public static final String COLUMN_NAME_POINT_Y = "y";
	public static final String COLUMN_NAME_SEED_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_USER_ID = "id";

	private static final String DB_NAME = "realFarm.db";
	private static final int DB_VERSION = 2;

	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONNAME = "actionName";
	public static final String TABLE_NAME_GROWING = "growing";
	public static final String TABLE_NAME_PLOT = "plot";
	public static final String TABLE_NAME_POINT = "point";
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
	 * @author Julien Freudiger
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
		Log.d("RealFarm", "Cleared existing content if any");
	}

	/**
	 * Close database helper
	 * 
	 * @author Julien Freudiger
	 */
	public synchronized void close() {
		mOpenHelper.close();

		if (mDb != null && mDb.isOpen())
			mDb.close();
		mDb = null;
	}

	/**
	 * Method to read values from the database.
	 * 
	 * @return A cursor containing the result of the query.
	 * @author Julien Freudiger
	 */
	public Cursor getAllEntries(String tableName, String[] parameters) {

		return mDb.query(tableName, parameters, null, null, null, null, null);
	}

	/**
	 * Method to read specfic values from table.
	 * 
	 * @return A cursor containing the result of the query.
	 * @author Julien Freudiger
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
	 * @author Julien Freudiger
	 * @param db
	 */
	public void initValues(SQLiteDatabase db) {

		Log.d("RealFarm",
				"Try to fill up tables with content" + db.getVersion());

		// users
		ContentValues users = new ContentValues();
		// 1
		users.put(COLUMN_NAME_USER_ID, 1);
		users.put("firstName", "Julien");
		users.put("lastName", "Freudiger");
		users.put("mobileNumber", 763949342);
		insertEntries(TABLE_NAME_USER, users, db);
		users.clear();
		// 2
		users.put(COLUMN_NAME_USER_ID, 2);
		users.put("firstName", "Hendrik");
		users.put("lastName", "Knoche");
		users.put("mobileNumber", 781827182);
		insertEntries(TABLE_NAME_USER, users, db);
		Log.d("RealFarm", "users works");

		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "plough");
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "seed");
		insertEntries(TABLE_NAME_ACTIONNAME, actionNames, db);
		Log.d("RealFarm", "actionName works");

		// actions
		ContentValues actions = new ContentValues();
		actions.put("actionID", 1);
		actions.put("growingID", 1);
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put("actionID", 2);
		actions.put("growingID", 1);
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put("actionID", 1);
		actions.put("growingID", 2);
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();
		actions.put("actionID", 1);
		actions.put("growingID", 3);
		insertEntries(TABLE_NAME_ACTION, actions, db);
		actions.clear();

		Log.d("RealFarm", "ACTION works");

		// growing
		ContentValues growing = new ContentValues();
		growing.put(COLUMN_NAME_GROWING_ID, 1);
		growing.put("plotID", 1);
		growing.put("seedID", 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 2);
		growing.put("plotID", 2);
		growing.put("seedID", 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 3);
		growing.put("plotID", 3);
		growing.put("seedID", 1);
		insertEntries(TABLE_NAME_GROWING, growing, db);
		growing.clear();

		Log.d("RealFarm", "growing works");

		// plots
		ContentValues plots = new ContentValues();
		plots.put(COLUMN_NAME_PLOT_ID, 1);
		plots.put(COLUMN_NAME_PLOT_USERID, 1);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		plots.put(COLUMN_NAME_PLOT_ID, 2);
		plots.put(COLUMN_NAME_PLOT_USERID, 1);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		plots.put(COLUMN_NAME_PLOT_ID, 3);
		plots.put(COLUMN_NAME_PLOT_USERID, 2);
		insertEntries(TABLE_NAME_PLOT, plots, db);
		plots.clear();
		Log.d("RealFarm", "plots works");

		// points
		ContentValues pointstoadd = new ContentValues();
		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053519);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170492);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053333);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170486);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053322);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170775);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053508);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170769);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 1);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

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

		// User 1 again
		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14054019);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170783);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14054017);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77171331);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053656);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77171344);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		pointstoadd.put(COLUMN_NAME_POINT_X, (int) 14053675);
		pointstoadd.put(COLUMN_NAME_POINT_Y, (int) 77170778);
		pointstoadd.put(COLUMN_NAME_POINT_PLOTID, 3);
		insertEntries(TABLE_NAME_POINT, pointstoadd, db);
		pointstoadd.clear();

		Log.d("RealFarm", "points works");

		ContentValues seed = new ContentValues();
		seed.put(COLUMN_NAME_SEED_ID, 1);
		seed.put("seedID", 1);
		insertEntries(TABLE_NAME_SEED, seed, db);
		seed.clear();
		Log.d("RealFarm", "seed works");

		ContentValues seedtype = new ContentValues();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 1);
		seedtype.put("name", "Groundnut");
		seedtype.put("variety", "TMV2");
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 2);
		seedtype.put("name", "Groundnut");
		seedtype.put("variety", "Samrat");
		insertEntries(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();
		Log.d("RealFarm", "seedtype works");

		// }
	}

	/**
	 * Method to insert values into the database
	 * 
	 * @param ContentValues
	 *            to insert
	 * @return long result is 0 if db close, -1 if error, rowID if success
	 * @author Julien Freudiger
	 */
	public long insertEntries(String TableName, ContentValues values,
			SQLiteDatabase db) {
		long result = -1;

		if ((TableName != null) && (values != null)) {
			// result = db.insert(TableName, null, values);
			try {
				result = db.insertOrThrow(TableName, null, values);
			} catch (SQLException e) {
				Log.d("RealFarm", "Exception" + e);
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
				Log.d("RealFarm", "Exception" + e);
			}
		}
		return result;
	}

	/**
	 * Open database helper for writing
	 * 
	 * @return ManageDatabase Object
	 * @throws SQLException
	 * @author Julien Freudiger
	 */
	public synchronized void open() throws SQLException {
		// opens the database
		if (mDb != null && !mDb.isOpen())
			mDb = mOpenHelper.getWritableDatabase();
	}

	/**
	 * Method to update user name in database
	 * 
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @return boolean indicating success of database update
	 * @author Julien Freudiger
	 */
	public boolean updateUserName(int id, String firstname, String lastname) {
		ContentValues args = new ContentValues();
		args.put("id", id);
		args.put("firstName", firstname);
		args.put("lastName", lastname);
		return mDb.update(TABLE_NAME_USER, args, "id =" + id, null) > 0;
	}

}
