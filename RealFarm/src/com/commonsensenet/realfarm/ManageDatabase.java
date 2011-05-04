package com.commonsensenet.realfarm;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ManageDatabase{

	private static final String DB_NAME = "realFarm.db";
	private static final String TABLE_NAME = "tableApp";   
	
	private Context context;
	private SQLiteDatabase db;
	   
	public ManageDatabase(Context context){
		this.context = context;
		
		//ManageDatabaseHelper mDB = new ManageDatabaseHelper(this.context);
		//db = mDB.getWritableDatabase();
	}

	/*
	 * Class to facilitate create of database (if needed)
	 */
	private static class ManageDatabaseHelper extends SQLiteOpenHelper{
		
		public ManageDatabaseHelper(Context context){
			super(context,DB_NAME, null, 1);
		}		
	
		@Override public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + "(fid INTEGER PRIMARY KEY, x1 INTEGER, y1 INTEGER, x2 INTEGER, y2 INTEGER, x3 INTEGER, y3 INTEGER, x4 INTEGER, y4 INTEGER, owner TEXT");
			ContentValues values = new ContentValues();
			values.put("fid", 1);
			values.put("x1", 14054563);
			values.put("y1", 77167003);
			values.put("x2", 14054763);
			values.put("y2", 77167003);
			values.put("x3", 14054763);
			values.put("y3", 77169003);
			values.put("x4", 14054765);
			values.put("y4", 77169003);
			values.put("owner", "Vijay");
			db.insert(TABLE_NAME, null, values);
		}
		
		@Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	}
}
