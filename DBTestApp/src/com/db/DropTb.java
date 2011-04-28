package com.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class DropTb extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	SQLiteDatabase myDB= null;
	String TableName = "myTable";
	
	/**
	* Create a Database.
	* */
	try {
		myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
		myDB.execSQL("DROP TABLE IF EXISTS "+TableName+";");

	}
	catch(Exception e) {
		Log.e("Error", "Error", e);
	} finally {
		if (myDB != null)
			myDB.close();
		}
	}
} 