package com.commonsensenet.realfarm;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class to manage database, i.e., input, remove and read data. 
 * @author Julien Freudiger
 */
public class ManageDatabase{

	private static final String DB_NAME = "realFarm22.db"; // hardcoded database name
	
	// Tables
	private static final String ACTION_NAME = "actionsNames"; // hardcoded table name
	private static final String ACTION = "actions"; // hardcoded table name
	private static final String GROWING = "growing"; // hardcoded table name
	private static final String PLOT = "plots"; // hardcoded table name
	private static final String POINT = "points"; // hardcoded table name
	private static final String SEED = "seed"; // hardcoded table name
	private static final String SEEDTYPE = "seedType"; // hardcoded table name
	private static final String USER = "users"; // hardcoded table name
	
	// Attributes
	private static final String ID = "id"; 
	private static final String X = "x";
	private static final String Y = "y";
	private static final String NAME = "name";
	private static final String DATE = "date";
	private static final String ACTIONDATE = "actionDate";
	
	// variables
	private Context mContext;
	private SQLiteDatabase db;
	private ManageDatabaseHelper mDB;


	/**
	 * Class constructor
	 * 
	 * @param context
	 * @author Julien Freudiger 
	 */
	public ManageDatabase(Context context){
		mContext = context;
		mDB = new ManageDatabaseHelper(mContext);
	}
	
	/**
	 * Open database helper for writing
	 * @return ManageDatabase Object
	 * @throws SQLException
	 * @author Julien Freudiger 
	 */
	public ManageDatabase open() throws SQLException{
        // Opens the database object in "write" mode.
		db = mDB.getWritableDatabase();

        return this;
	}
	
	/**
	 * Close database helper
	 * @author Julien Freudiger 
	 */
	public void close(){
		mDB.close();
	}

	/**
	 * Defines hardcoded initial values for database. This method is for testing purposes only 
	 * and should be replaced by method to obtain location of plots from farmers directly. 
	 * @author Julien Freudiger 
	 */
	public void initValues(){
		
		// Delete current elements in table
		db.delete(ACTION_NAME, null, null);
		db.delete(ACTION, null, null);
		db.delete(GROWING, null, null);
		db.delete(PLOT, null, null);
		db.delete(POINT, null, null);
		db.delete(SEED, null, null);
		db.delete(SEEDTYPE, null, null);
		db.delete(USER, null, null);
		
		// Get current time
		Calendar c = Calendar.getInstance(); 
		int seconds = c.get(Calendar.SECOND);

		// users 
		ContentValues users = new ContentValues();
		// 1
		users.put("id", 1);
		users.put("firstName", "Julien");
		users.put("lastName", "Freudiger");
		users.put("mobileNumber", 763949342);
		insertEntries(USER, users);
		users.clear();
		// 2		
		users.put("id", 2);
		users.put("firstName", "Hendrik");
		users.put("lastName", "Knoche");
		users.put("mobileNumber", 781827182);
		insertEntries(USER, users);
		Log.d("RealFarm","users works");

		
		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put("id", 1);
		actionNames.put("name", "plough");
		insertEntries(ACTION_NAME, actionNames);
		actionNames.clear();
		actionNames.put("id", 2);
		actionNames.put("name", "seed");
		Log.d("RealFarm","actionName works");

		
		// actions
		ContentValues actions = new ContentValues();
		actions.put("actionID", 1);
		actions.put("growingID", 1);
		insertEntries(ACTION, actions);
		actions.clear();
		actions.put("actionID", 2);
		actions.put("growingID", 1);
		insertEntries(ACTION, actions);
		actions.clear();
		actions.put("actionID", 1);
		actions.put("growingID", 2);
		insertEntries(ACTION, actions);
		actions.clear();
		actions.put("actionID", 1);
		actions.put("growingID", 3);
		insertEntries(ACTION, actions);
		actions.clear();
		
		Log.d("RealFarm","ACTION works");
		
		// growing 
		ContentValues growing = new ContentValues();
		growing.put("id", 1);
		growing.put("plotID", 1);
		growing.put("seedID", 1);
		insertEntries(GROWING, growing);
		growing.clear();
		growing.put("id", 2);
		growing.put("plotID", 2);
		growing.put("seedID", 1);
		insertEntries(GROWING, growing);
		growing.clear();
		growing.put("id", 3);
		growing.put("plotID", 3);
		growing.put("seedID", 1);
		insertEntries(GROWING, growing);
		growing.clear();
		
		Log.d("RealFarm","growing works");

		// plots
		ContentValues plots = new ContentValues();
		plots.put("id", 1);
		plots.put("userID", 1);
		insertEntries(PLOT, plots);
		plots.clear();
		plots.put("id", 2);
		plots.put("userID", 1);
		insertEntries(PLOT, plots);
		plots.clear();
		plots.put("id", 3);
		plots.put("userID", 2);
		insertEntries(PLOT, plots);
		plots.clear();
		Log.d("RealFarm","plots works");


		// points 
		ContentValues pointstoadd = new ContentValues();
		pointstoadd.put(X, (int)14053519);
		pointstoadd.put(Y, (int)77170492);
		pointstoadd.put("plotID", 1);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		Log.d("RealFarm","points 1a works");
		
		pointstoadd.put(X, (int)14053333);
		pointstoadd.put(Y, (int)77170486);
		pointstoadd.put("plotID", 1);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14053322);
		pointstoadd.put(Y, (int)77170775);
		pointstoadd.put("plotID", 1);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14053508);
		pointstoadd.put(Y, (int)77170769);
		pointstoadd.put("plotID", 1);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		Log.d("RealFarm","points 1 works");

//		values.put(DATE, (int) seconds);
		
		// User 2
		pointstoadd.put(X, (int)14053733);
		pointstoadd.put(Y, (int)77169697);
		pointstoadd.put("plotID", 2);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14053689);
		pointstoadd.put(Y, (int)77170225);
		pointstoadd.put("plotID", 2);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		pointstoadd.put(X, (int)14053372);
		pointstoadd.put(Y, (int)77170200);
		pointstoadd.put("plotID", 2);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		pointstoadd.put(X, (int)14053442);
		pointstoadd.put(Y, (int)77169622);
		pointstoadd.put("plotID", 2);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		Log.d("RealFarm","points 2 works");

//		values.put(DATE, (int)seconds+1);

		
		// User 1 again
		pointstoadd.put(X, (int)14054019);
		pointstoadd.put(Y, (int)77170783);
		pointstoadd.put("plotID", 3);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14054017);
		pointstoadd.put(Y, (int)77171331);
		pointstoadd.put("plotID", 3);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14053656);
		pointstoadd.put(Y, (int)77171344);
		pointstoadd.put("plotID", 3);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		pointstoadd.put(X, (int)14053675);
		pointstoadd.put(Y, (int)77170778);
		pointstoadd.put("plotID", 3);
		insertEntries(POINT, pointstoadd);
		pointstoadd.clear();
		
		Log.d("RealFarm","points works");

		ContentValues seed = new ContentValues();
		seed.put("id", 1);
		seed.put("seedID", 1);
		insertEntries(SEED, seed);
		seed.clear();
		Log.d("RealFarm","seed works");
		

		ContentValues seedtype = new ContentValues();
		seedtype.put("id", 1);
		seedtype.put("name", "Groundnut");
		seedtype.put("variety", "TMV2");
		insertEntries(SEEDTYPE, seedtype);
		seedtype.clear();
		seedtype.put("id", 2);
		seedtype.put("name", "Groundnut");
		seedtype.put("variety", "Samrat");
		insertEntries(SEEDTYPE, seedtype);
		seedtype.clear();
		Log.d("RealFarm","seedtype works");

		
	}	        
	
	/**
	 * Method to insert values into the database
	 * @param ContentValues to insert
	 * @return long result is 0 if db close, -1 if error, rowID if success
	 * @author Julien Freudiger 
	 */
	public long insertEntries(String TableName, ContentValues values){

		long result = db.insert(TableName, null, values);
	
		if (result == -1){
			throw new SQLException("Failed to insert row");
		} 

		return result;

	}
	
	/**
	 * Method to read specfic values from table. 
	 * @return A cursor containing the result of the query.
	 * @author Julien Freudiger 
	 */
	
	public Cursor GetEntries(String TableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		
		return db.query(TableName, columns, selection, selectionArgs, groupBy, having, orderBy);

	}
	
	
	/**
	 * Method to read values from the database. 
	 * @return A cursor containing the result of the query.
	 * @author Julien Freudiger 
	 */
	public Cursor GetAllEntries(String TableName, String[] parameters){
		
		return db.query(TableName, parameters, null, null, null, null, null);
		 //new String[] {ID, X1, Y1, X2, Y2, X3, Y3, X4, Y4, DATE, OWNER}
	}
	
	/**
	 * Class to facilitate creation of database. The database is created only if needed.
	 * @author Julien Freudiger 
	 */
	private class ManageDatabaseHelper extends SQLiteOpenHelper{
				
		public ManageDatabaseHelper(Context context){
			super(context,DB_NAME, null, 1);
		}		
	
		/**
		 * Create the database with the table name and column names
		 * @param SQLiteDatabase to create
		 * @throws SQLException
		 * @author Julien Freudiger 
		 */
		@Override public void onCreate(SQLiteDatabase db) {
	        
        	Log.d("RealFarm","Try to fill up database with tables");

	        try {
	        	
	        	/*
	        	 * Create tables
	        	 */
	        		
	        	// actionsNames
	        	db.execSQL("create table " + ACTION_NAME + " ( "
						+ ID + " integer primary key, "
						+ NAME + " text not null "
						+ " ); ");
		    
	        	Log.d("RealFarm","Created actionName table");
	        	
	        	// actions
	        	db.execSQL("create table " + ACTION + "  ( "
						+ ID + " integer primary key autoincrement, "
						+ "growingID references growing(id), "
						+ "actionID references actionsNames(id), "
						+ ACTIONDATE + " integer "
						+ " ); ");
	        	Log.d("RealFarm","Created action table");
	        	
	        	// growing
	        	db.execSQL("create table " + GROWING + " ( "
						+ ID + " integer primary key, "
						+ "plotID references plots(id), "
						+ "seedID references seeds(id) "
						+ " ); ");
	        	Log.d("RealFarm","Created growing table");
	        	
	        	// plots
	        	db.execSQL("create table "+ PLOT + " ( "
						+ ID + " integer primary key, "
						+ "userID references users(id) "
						+ " ); ");
	        	Log.d("RealFarm","Created plot table");
		        	
	        	// points
	        	db.execSQL("create table " + POINT + " ( "
						+ ID + " integer primary key autoincrement, "
						+ "x integer, "
						+ "y integer, "
						+ "plotID references plots(id) "
						+ " ); ");
	        	Log.d("RealFarm","Created point table");
	
	        	// seeds
	        	db.execSQL("create table " + SEED + " ( "
						+ ID + " integer primary key, "
						+ "seedID references seedTypes(id) "
						+ " ); ");
	        	Log.d("RealFarm","Created seed table");
		        	
	        	// seedTypes
	        	db.execSQL("create table " + SEEDTYPE + " ( "
						+ ID + " integer primary key, "
						+ NAME + " text not null, "
						+ "variety text "
						+ " ); ");
	        	Log.d("RealFarm","Created seedtype table");
	        	
	        	// users
	        	db.execSQL("create table " + USER + " ( "
						+ ID + " integer primary key, "
						+ "firstName text not null, "
						+ "lastName text, "
						+ "mobileNumber integer "
						+ " ); ");
	        	Log.d("RealFarm","Created user table");
	        	
	        	Log.d("RealFarm","Database created successfully");

/*	        	
	        	db.execSQL("create table " + TABLE_NAME + " ( "
					+ ID + " integer primary key autoincrement, "
					+ X1 + " integer, "
					+ Y1 + " integer, "
					+ X2 + " integer, " 
					+ Y2 + " integer, "
					+ X3 + " integer, " 
					+ Y3 + " integer, "
					+ X4 + " integer, "
					+ Y4 + " integer, "
					+ DATE + " integer, "
					+ OWNER + " text not null" + " ); ");
	        	*/
	        	
        	} catch (SQLException e){
        		e.printStackTrace();
        	}

		}
		
		/**
		 *  If the database is upgraded.
		 */
		@Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	         // Recreates the database with a new version
	         onCreate(db);
		}
	}
	
	
	
}
