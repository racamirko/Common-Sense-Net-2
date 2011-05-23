package com.commonsensenet.realfarm;

import java.util.Calendar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to manage database, i.e., input, remove and read data. 
 * @author Julien Freudiger
 */
public class ManageDatabase{

	private static final String DB_NAME = "realFarm7.db"; // hardcoded database name
	private static final String TABLE_NAME = "tableApp"; // hardcoded table name
	
	private static final String ID = "_fid"; 
	private static final String X1 = "x1";
	private static final String Y1 = "y1";
	private static final String X2 = "x2";
	private static final String Y2 = "y2";
	private static final String X3 = "x3";
	private static final String Y3 = "y3";
	private static final String X4 = "x4";
	private static final String Y4 = "y4";
	private static final String OWNER = "owner";
	private static final String DATE = "date";
	
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
		db.delete(TABLE_NAME, null, null);
		
		// Get current time
		Calendar c = Calendar.getInstance(); 
		int seconds = c.get(Calendar.SECOND);

		// User 1
		ContentValues values = new ContentValues();
		values.put(X1, (int)14053519);
		values.put(Y1, (int)77170492);
		values.put(X2, (int)14053333);
		values.put(Y2, (int)77170486);
		values.put(X3, (int)14053322);
		values.put(Y3, (int)77170775);
		values.put(X4, (int)14053508);
		values.put(Y4, (int)77170769);	        
		values.put(DATE, (int) seconds);
		values.put(OWNER, "Vijay");
		insertEntries(values);
		
		// User 2
		ContentValues values2 = new ContentValues();
		values2.put(X1, (int)14053733);
		values2.put(Y1, (int)77169697);
		values2.put(X2, (int)14053689);
		values2.put(Y2, (int)77170225);
		values2.put(X3, (int)14053372);
		values2.put(Y3, (int)77170200);
		values2.put(X4, (int)14053442);
		values2.put(Y4, (int)77169622);	        
		values2.put(DATE, (int)seconds+1);
		values2.put(OWNER, "Nitish");
		insertEntries(values2);
		
		// User 1 again
		ContentValues values3 = new ContentValues();
		values3.put(X1, (int)14054019);
		values3.put(Y1, (int)77170783);
		values3.put(X2, (int)14054017);
		values3.put(Y2, (int)77171331);
		values3.put(X3, (int)14053656);
		values3.put(Y3, (int)77171344);
		values3.put(X4, (int)14053675);
		values3.put(Y4, (int)77170778);	        
		values3.put(DATE, (int)seconds+2);
		values3.put(OWNER, "Vijay");
		insertEntries(values3);
	}	        
	
	/**
	 * Method to insert values into the database
	 * @param ContentValues to insert
	 * @return long result is 0 if db close, -1 if error, rowID if success
	 * @author Julien Freudiger 
	 */
	public long insertEntries(ContentValues values){

		long result = db.insert(TABLE_NAME, null, values);
	
		if (result == -1){
			throw new SQLException("Failed to insert row");
		} 

		return result;

	}
	
	/**
	 * Method to read values from the database. 
	 * @return A cursor containing the result of the query.
	 * @author Julien Freudiger 
	 */
	public Cursor GetAllEntries(){
		
		return db.query(TABLE_NAME, new String[] {ID, X1, Y1, X2, Y2, X3, Y3, X4, Y4, DATE, OWNER}, null, null, null, null, null);
		
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
	        
	        try {
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
