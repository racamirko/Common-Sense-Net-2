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
 * @author Oscar Bolanos <@oscarbolanos>
 * @author Mirko Raca <mirko.raca@epfl.ch>
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
					+ COLUMN_NAME_ACTION_IRRIGATE_METHOD + " text " + " ); ");
			Log.d(LOG_TAG, "Created action table");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONNAME + " ( "
					+ COLUMN_NAME_ACTIONNAME_ID
					+ " integer primary key, "
					+ COLUMN_NAME_ACTIONNAME_RESOURCE + " integer, "
					+ COLUMN_NAME_ACTIONNAME_AUDIO + " integer, "
					+ COLUMN_NAME_ACTIONNAME_NAME + " text not null, "
					+ COLUMN_NAME_ACTIONNAME_NAME_KANNADA + " text not null, "
					+ COLUMN_NAME_ACTIONNAME_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created actionName table");

			// actionTranslation
			db.execSQL("create table " + TABLE_NAME_ACTIONTRANSLATION + " ( "
					+ COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID
					+ " integer references actionName(id), "
					+ COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD + " text, "
					+ COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE + " text, "
					+ COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD
					+ " text, " + COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE
					+ " text, " + COLUMN_NAME_ACTIONTRANSLATION_ADMINFLAG
					+ " boolean " + " ); ");
			Log.d(LOG_TAG, "Created action translation table");

			// fertilizers
			db.execSQL("create table " + TABLE_NAME_FERTILIZER + " ( "
					+ COLUMN_NAME_FERTILIZER_ID
					+ " integer primary key, "
					+ COLUMN_NAME_FERTILIZER_NAME + " text, "
					+ COLUMN_NAME_FERTILIZER_AUDIO + " integer, "
					+ COLUMN_NAME_FERTILIZER_STAGEID
					+ " references stage(id), " + COLUMN_NAME_FERTILIZER_UNITID
					+ " references unit(id), "
					+ COLUMN_NAME_FERTILIZER_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created fertilizer table");

			// growing
			db.execSQL("create table " + TABLE_NAME_GROWING + " ( "
					+ COLUMN_NAME_GROWING_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_GROWING_PLOTID + " references plot(id), "
					+ COLUMN_NAME_GROWING_SEEDID + " references seed(id), "
					+ COLUMN_NAME_GROWING_SOWINGDATE + " date, "
					+ COLUMN_NAME_GROWING_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created growing table");

			// log
			db.execSQL("create table " + TABLE_NAME_LOG + " ( "
					+ COLUMN_NAME_LOG_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_LOG_NAME + " text not null, "
					+ COLUMN_NAME_LOG_VALUE + " text, " + COLUMN_NAME_LOG_DATE
					+ " date, " + COLUMN_NAME_LOG_ADMINFLAG + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created log table");

			// pesticides

			db.execSQL("create table " + TABLE_NAME_PESTICIDE + " ( "
					+ COLUMN_NAME_PESTICIDE_ID
					+ " integer primary key, "
					+ COLUMN_NAME_PESTICIDE_NAME + " text, "
					+ COLUMN_NAME_PESTICIDE_AUDIO + " integer, "
					+ COLUMN_NAME_PESTICIDE_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created pesticide table");

			// plots
			// plots
			db.execSQL("create table " + TABLE_NAME_PLOT + " ( "
					+ COLUMN_NAME_PLOT_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_PLOT_USERID + " integer, "
					+ COLUMN_NAME_PLOT_PLOT_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_PLOT_CENTERX + " integer, "
					+ COLUMN_NAME_PLOT_CENTERY + " integer, "
					+ COLUMN_NAME_PLOT_IMAGENAME + " text, "
					+ COLUMN_NAME_PLOT_SOILTYPE + " text, "
					+ COLUMN_NAME_PLOT_DELETEFLAG + " int, "
					+ COLUMN_NAME_PLOT_ADMINFLAG + " int " + " ); ");
			Log.d(LOG_TAG, "Created plot table");

			// problemtype
			db.execSQL("create table " + TABLE_NAME_PROBLEMTYPE + " ( "
					+ COLUMN_NAME_PROBLEMTYPE_ID
					+ " integer primary key, "
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

			// recommendations
			db.execSQL("create table " + TABLE_NAME_RECOMMENDATION + " ( "
					+ COLUMN_NAME_RECOMMENDATION_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_RECOMMENDATION_SEEDID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_ACTIONID + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_DATE + " date, "
					+ COLUMN_NAME_RECOMMENDATION_ADMINFLAG + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created recommendation table");

			// seedTypes
			db.execSQL("create table " + TABLE_NAME_SEEDTYPE + " ( "
					+ COLUMN_NAME_SEEDTYPE_ID
					+ " integer primary key, "
					+ COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
					+ COLUMN_NAME_SEEDTYPE_NAMEKANNADA + " text, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
					+ COLUMN_NAME_SEEDTYPE_RESOURCE_BG + " integer, "
					+ COLUMN_NAME_SEEDTYPE_AUDIO + " integer, "
					+ COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST + " integer, "
					+ COLUMN_NAME_SEEDTYPE_VARIETY + " text, "
					+ COLUMN_NAME_SEEDTYPE_VARIETYKANNADA + " text, "
					+ COLUMN_NAME_SEEDTYPE_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created seed type table");

			// seedTypeStages
			db.execSQL("create table " + TABLE_NAME_SEEDTYPESTAGE + " ( "
					+ COLUMN_NAME_SEEDTYPESTAGE_ID
					+ " integer primary key, "
					+ COLUMN_NAME_SEEDTYPESTAGE_STAGEID + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS + " integer, "
					+ COLUMN_NAME_SEEDTYPESTAGE_ADMINFLAG + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created seed type stages table");

			// stages
			db.execSQL("create table " + TABLE_NAME_STAGE + " ( "
					+ COLUMN_NAME_STAGE_ID
					+ " integer primary key, "
					+ COLUMN_NAME_STAGE_NAME + " text,"
					+ COLUMN_NAME_STAGE_ADMINFLAG + " booelan " + " ); ");
			Log.d(LOG_TAG, "Created stage table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text, "
					+ COLUMN_NAME_USER_MOBILE + " text, "
					+ COLUMN_NAME_USER_IMG + " text, "
					+ COLUMN_NAME_USER_DELETEFLAG + " boolean, "
					+ COLUMN_NAME_USER_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created user table");

			// units
			db.execSQL("create table " + TABLE_NAME_UNIT + " ( "
					+ COLUMN_NAME_UNIT_ID
					+ " integer primary key, "
					+ COLUMN_NAME_UNIT_NAME + " text not null, "
					+ COLUMN_NAME_UNIT_AUDIO + " integer, "
					+ COLUMN_NAME_UNIT_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created unit table");

			// soil moisture
			db.execSQL("create table " + TABLE_NAME_SOILMOISTURE + " ( "
					+ COLUMN_NAME_SOILMOISTURE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SOILMOISTURE_DATE + " date, "
					+ COLUMN_NAME_SOILMOISTURE_SM1 + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_SM2 + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_CLUSTER + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_ADMINFLAG + " boolean " + " ); ");
			Log.d(LOG_TAG, "Created soil moisture table");

			// Weather forecast
			db.execSQL("create table " + TABLE_NAME_WEATHERFORECAST + " ( "
					+ COLUMN_NAME_WEATHERFORECAST_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_WEATHERFORECAST_DATE + " text, "
					+ COLUMN_NAME_WEATHERFORECAST_VALUE + " integer, "
					+ COLUMN_NAME_WEATHERFORECAST_TYPE + " text, "
					+ COLUMN_NAME_WEATHERFORECAST_DATE1 + " text, "
					+ COLUMN_NAME_WEATHERFORECAST_VALUE1 + " integer, "
					+ COLUMN_NAME_WEATHERFORECAST_TYPE1 + " text, "
					+ COLUMN_NAME_WEATHERFORECAST_ADMINFLAG + " boolean "
					+ " ); ");
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

	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_ACTIONNAMEID = "actionNameId";
	public static final String COLUMN_NAME_ACTION_GROWINGID = "growingId";
	public static final String COLUMN_NAME_ACTION_ACTIONTYPE = "actionType";
	public static final String COLUMN_NAME_ACTION_SEEDVARIETY = "seedVariety";
	public static final String COLUMN_NAME_ACTION_QUANTITY1 = "quantity1";
	public static final String COLUMN_NAME_ACTION_QUANTITY2 = "quantity2";
	public static final String COLUMN_NAME_ACTION_UNITS = "units";
	public static final String COLUMN_NAME_ACTION_DAY = "day";
	public static final String COLUMN_NAME_ACTION_USERID = "userId";
	public static final String COLUMN_NAME_ACTION_PLOTID = "plotId";
	public static final String COLUMN_NAME_ACTION_TYPEOFFERTILIZER = "typeOfFertilizer";
	public static final String COLUMN_NAME_ACTION_PROBLEMTYPE = "problems";
	public static final String COLUMN_NAME_ACTION_DATE = "date";
	public static final String COLUMN_NAME_ACTION_HARVESTFEEDBACK = "feedback";
	public static final String COLUMN_NAME_ACTION_SELLINGPRICE = "sellingPrice";
	public static final String COLUMN_NAME_ACTION_QUALITYOFSEED = "qualityofSeed";
	public static final String COLUMN_NAME_ACTION_SELLTYPE = "selltype";
	public static final String COLUMN_NAME_ACTION_SENT = "sent";
	public static final String COLUMN_NAME_ACTION_ISADMIN = "IsAdmin";
	public static final String COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE = "ActionPerformedDate";
	public static final String COLUMN_NAME_ACTION_TREATMENT = "treatment";
	public static final String COLUMN_NAME_ACTION_PESTICIDETYPE = "pesticidetype";
	public static final String COLUMN_NAME_ACTION_IRRIGATE_METHOD = "irrigateMethod";

	public static final String COLUMN_NAME_ACTIONNAME_AUDIO = "audio";
	public static final String COLUMN_NAME_ACTIONNAME_ID = "id";
	public static final String COLUMN_NAME_ACTIONNAME_NAME = "name";
	public static final String COLUMN_NAME_ACTIONNAME_NAME_KANNADA = "nameKannada";
	public static final String COLUMN_NAME_ACTIONNAME_RESOURCE = "res";
	public static final String COLUMN_NAME_ACTIONNAME_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID = "actionNameId";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD = "indObjectIdField";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE = "indObjectTable";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD = "targetIdField";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE = "targetTable";
	public static final String COLUMN_NAME_ACTIONTRANSLATION_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_FERTILIZER_AUDIO = "audio";
	public static final String COLUMN_NAME_FERTILIZER_ID = "id";
	public static final String COLUMN_NAME_FERTILIZER_NAME = "name";
	public static final String COLUMN_NAME_FERTILIZER_STAGEID = "stageId";
	public static final String COLUMN_NAME_FERTILIZER_UNITID = "unitId";
	public static final String COLUMN_NAME_FERTILIZER_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_GROWING_ID = "id";
	public static final String COLUMN_NAME_GROWING_PLOTID = "plotId";
	public static final String COLUMN_NAME_GROWING_SEEDID = "seedId";
	public static final String COLUMN_NAME_GROWING_SOWINGDATE = "sowingDate";
	public static final String COLUMN_NAME_GROWING_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_LOG_DATE = "logDate";
	public static final String COLUMN_NAME_LOG_ID = "id";
	public static final String COLUMN_NAME_LOG_NAME = "name";
	public static final String COLUMN_NAME_LOG_VALUE = "value";
	public static final String COLUMN_NAME_LOG_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_PESTICIDE_AUDIO = "audio";
	public static final String COLUMN_NAME_PESTICIDE_ID = "id";
	public static final String COLUMN_NAME_PESTICIDE_NAME = "name";
	public static final String COLUMN_NAME_PESTICIDE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_USERID = "userId";
	public static final String COLUMN_NAME_PLOT_PLOT_SEEDTYPEID = "plotSeedtypeId";
	public static final String COLUMN_NAME_PLOT_CENTERX = "centerX";
	public static final String COLUMN_NAME_PLOT_CENTERY = "centerY";
	public static final String COLUMN_NAME_PLOT_IMAGENAME = "imageName";
	public static final String COLUMN_NAME_PLOT_SOILTYPE = "soilType";
	public static final String COLUMN_NAME_PLOT_MAINCROP = "mainCrop";
	public static final String COLUMN_NAME_PLOT_DELETEFLAG = "deleteFlag";
	public static final String COLUMN_NAME_PLOT_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_PROBLEM_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEM_ID = "id";
	public static final String COLUMN_NAME_PROBLEM_NAME = "name";
	public static final String COLUMN_NAME_PROBLEM_PROBLEMTYPEID = "masterId";
	public static final String COLUMN_NAME_PROBLEM_RESOURCE = "res";
	public static final String COLUMN_NAME_PROBLEM_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_PROBLEMTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_PROBLEMTYPE_ID = "id";
	public static final String COLUMN_NAME_PROBLEMTYPE_NAME = "name";
	public static final String COLUMN_NAME_PROBLEMTYPE_RESOURCE = "res";
	public static final String COLUMN_NAME_PROBLEMTYPE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_RECOMMENDATION_ACTIONID = "actionId";
	public static final String COLUMN_NAME_RECOMMENDATION_DATE = "recommendationDate";
	public static final String COLUMN_NAME_RECOMMENDATION_ID = "id";
	public static final String COLUMN_NAME_RECOMMENDATION_SEEDID = "seedId";
	public static final String COLUMN_NAME_RECOMMENDATION_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_SEED_AUDIO = "seedAudio";
	public static final String COLUMN_NAME_SEED_ID = "id";
	public static final String COLUMN_NAME_SEED_SEEDID = "seedId";
	public static final String COLUMN_NAME_SEED_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_SEEDTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST = "daysToHarvest";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_NAMEKANNADA = "nameKannada";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE = "res";
	public static final String COLUMN_NAME_SEEDTYPE_RESOURCE_BG = "resBg";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETY = "variety";
	public static final String COLUMN_NAME_SEEDTYPE_VARIETYKANNADA = "varietyKannada";
	public static final String COLUMN_NAME_SEEDTYPE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS = "fromCountDays";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID = "seedTypeId";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_STAGEID = "stageId";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS = "toCountDays";
	public static final String COLUMN_NAME_SEEDTYPESTAGE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_STAGE_ID = "id";
	public static final String COLUMN_NAME_STAGE_NAME = "name";
	public static final String COLUMN_NAME_STAGE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_UNIT_AUDIO = "audio";
	public static final String COLUMN_NAME_UNIT_ID = "id";
	public static final String COLUMN_NAME_UNIT_NAME = "name";
	public static final String COLUMN_NAME_UNIT_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstName";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastName";
	public static final String COLUMN_NAME_USER_MOBILE = "mobileNumber";
	public static final String COLUMN_NAME_USER_IMG = "img";
	public static final String COLUMN_NAME_USER_DELETEFLAG = "deleteFlag"; // Prakruthi
	public static final String COLUMN_NAME_USER_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_SOILMOISTURE_ID = "id";
	public static final String COLUMN_NAME_SOILMOISTURE_DATE = "date";
	public static final String COLUMN_NAME_SOILMOISTURE_SM1 = "sm1";
	public static final String COLUMN_NAME_SOILMOISTURE_SM2 = "sm2";
	public static final String COLUMN_NAME_SOILMOISTURE_CLUSTER = "cluster";
	public static final String COLUMN_NAME_SOILMOISTURE_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_WEATHERFORECAST_ID = "id";
	public static final String COLUMN_NAME_WEATHERFORECAST_DATE = "date";
	public static final String COLUMN_NAME_WEATHERFORECAST_VALUE = "value";
	public static final String COLUMN_NAME_WEATHERFORECAST_TYPE = "type";
	public static final String COLUMN_NAME_WEATHERFORECAST_DATE1 = "date1";
	public static final String COLUMN_NAME_WEATHERFORECAST_VALUE1 = "value1";
	public static final String COLUMN_NAME_WEATHERFORECAST_TYPE1 = "type1";
	public static final String COLUMN_NAME_WEATHERFORECAST_ADMINFLAG = "adminFlag";

	public static final String COLUMN_NAME_MARKETPRICE_ID = "id";
	public static final String COLUMN_NAME_MARKETPRICE_DATE = "date";
	public static final String COLUMN_NAME_MARKETPRICE_TYPE = "type";
	public static final String COLUMN_NAME_MARKETPRICE_VALUE = "value";
	public static final String COLUMN_NAME_MARKETPRICE_ADMINFLAG = "adminFlag";

	/** Date format used to store the dates. */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** Filename of the database. */
	public static final String DB_NAME = "realFarm.db";
	/** Current version of the database. */
	private static final int DB_VERSION = 1;

	/** Identifier used to debug the database. */
	public static final String LOG_TAG = "RealFarm";
	/** Default User number. */
	public static String DEFAULT_NUMBER = "000000000";
	/** Current DeviceId. */
	public static String DEVICE_ID;
	/** Identifier of the current User. */
	public static int MAIN_USER_ID = -1;

	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONNAME = "actionName";
	public static final String TABLE_NAME_ACTIONTRANSLATION = "actionTranslation";
	public static final String TABLE_NAME_FERTILIZER = "fertilizer";
	public static final String TABLE_NAME_GROWING = "growing";
	public static final String TABLE_NAME_LOG = "log";
	public static final String TABLE_NAME_PESTICIDE = "pesticide";
	public static final String TABLE_NAME_PLOT = "plot";

	public static final String TABLE_NAME_PROBLEM = "problem";
	public static final String TABLE_NAME_PROBLEMTYPE = "problemType";
	public static final String TABLE_NAME_RECOMMENDATION = "recommendation";
	public static final String TABLE_NAME_SEEDTYPE = "seedType";
	public static final String TABLE_NAME_SEEDTYPESTAGE = "seedTypeStage";
	public static final String TABLE_NAME_STAGE = "stage";
	public static final String TABLE_NAME_UNIT = "unit";
	public static final String TABLE_NAME_USER = "user";
	public static final String TABLE_NAME_SOILMOISTURE = "soilMoisture"; // changes
	public static final String TABLE_NAME_WEATHERFORECAST = "weatherForecast";
	public static final String TABLE_NAME_MARKETPRICE = "marketPrice";

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

		mDb.delete(TABLE_NAME_RECOMMENDATION, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPESTAGE, null, null);
		mDb.delete(TABLE_NAME_STAGE, null, null);
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
	 * Defines hard-coded initial values for database. All base tables and user
	 * data, the latter is for testing purposes only and should be replaced by
	 * method to obtain location of plots from farmers directly. base table
	 * conventions: 1 - unknown, 2 - none, 3 - ... real entries
	 * 
	 * @param db
	 *            database where the values will be inserted.
	 */
	public void initValues(SQLiteDatabase db) {

		Log.d(LOG_TAG, "Try to fill up tables with content" + db.getVersion());

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
		users.put(COLUMN_NAME_USER_IMG, "farmer_90px_kiran_kumar_g");
		users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
		insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
		users.clear();

		// 2
		users.put(COLUMN_NAME_USER_ID, 2);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Hendrik");
		users.put(COLUMN_NAME_USER_LASTNAME, "Knoche");
		users.put(COLUMN_NAME_USER_MOBILE, "788844672");
		users.put(COLUMN_NAME_USER_IMG, "farmer_90px_adam_jones");
		users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
		insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
		users.clear();

		// 3
		users.put(COLUMN_NAME_USER_ID, 3);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Chris");
		users.put(COLUMN_NAME_USER_LASTNAME, "Bishop");
		users.put(COLUMN_NAME_USER_MOBILE, "788244421");
		users.put(COLUMN_NAME_USER_IMG, "farmer_90px_neil_palmer");
		users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
		insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
		users.clear();

		// 4
		users.put(COLUMN_NAME_USER_ID, 4);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Chris");
		users.put(COLUMN_NAME_USER_LASTNAME, "McDougall");
		users.put(COLUMN_NAME_USER_MOBILE, "781122672");
		users.put(COLUMN_NAME_USER_IMG, "farmer_90px_neil_palmer2");
		users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
		insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
		users.clear();

		// 5
		users.put(COLUMN_NAME_USER_ID, 5);
		users.put(COLUMN_NAME_USER_FIRSTNAME, "Frank");
		users.put(COLUMN_NAME_USER_LASTNAME, "Herbert");
		users.put(COLUMN_NAME_USER_MOBILE, "788111172");
		users.put(COLUMN_NAME_USER_IMG, "farmer_90px_walmart_stores");
		users.put(COLUMN_NAME_USER_DELETEFLAG, 0);
		insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
		users.clear();

		Log.d(LOG_TAG, "users works");

		// actionNames
		ContentValues actionNames = new ContentValues();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "sow");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_sow));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_sowing);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 4);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "fertilize");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_apply_fertilizer));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_fertilizing2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio2);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 5);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "spray");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_spraying));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_spraying3);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio3);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 7);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "irrigate");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_irrigate));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_irrigation2);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio4);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 8);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "harvest");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_harvest));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_harvesting1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio5);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);
		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 6);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "report");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_reporting_of_problems));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_reporting);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio6);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);

		actionNames.clear();
		actionNames.put(COLUMN_NAME_ACTIONNAME_ID, 9);
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME, "Diary");
		actionNames.put(COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
				mContext.getString(R.string.k_diary));
		actionNames.put(COLUMN_NAME_ACTIONNAME_RESOURCE,
				R.drawable.ic_90px_diary1);
		actionNames.put(COLUMN_NAME_ACTIONNAME_AUDIO, R.raw.audio7);
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONNAME, actionNames, db);

		Log.d(LOG_TAG, "actionName works");

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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
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
		insertEntriesIntoDatabase(TABLE_NAME_ACTIONTRANSLATION,
				actionTranslation, db);
		actionTranslation.clear();

		Log.d(LOG_TAG, "action translation works");

		// 2
		ContentValues unit = new ContentValues();
		unit.put(COLUMN_NAME_UNIT_ID, 1);
		unit.put(COLUMN_NAME_UNIT_NAME, "unknown");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);

		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();

		unit.put(COLUMN_NAME_UNIT_ID, 2);
		unit.put(COLUMN_NAME_UNIT_NAME, "none");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 3);
		unit.put(COLUMN_NAME_UNIT_NAME, "cart/tractor loads");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 4);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of days");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 5);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of year");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 6);
		unit.put(COLUMN_NAME_UNIT_NAME, "number of nights");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 7);
		unit.put(COLUMN_NAME_UNIT_NAME, "bags of 50 kg");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);
		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();
		unit.put(COLUMN_NAME_UNIT_ID, 8);
		unit.put(COLUMN_NAME_UNIT_NAME,
				"number of main crop rows between each row");
		unit.put(COLUMN_NAME_UNIT_AUDIO, R.raw.audio1);

		insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
		unit.clear();

		Log.d(LOG_TAG, "unit works");

		// actions
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -15);

		/*
		 * ContentValues actions = new ContentValues();
		 * actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		 * actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		 * actions.put(COLUMN_NAME_ACTION_DATE,
		 * dateFormat.format(calendar.getTime()));
		 * insertEntries(TABLE_NAME_ACTION, actions, db);
		 * calendar.add(Calendar.DATE, 2); actions.clear();
		 * actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 4);
		 * actions.put(COLUMN_NAME_ACTION_GROWINGID, 1);
		 * actions.put(COLUMN_NAME_ACTION_DATE,
		 * dateFormat.format(calendar.getTime()));
		 * insertEntries(TABLE_NAME_ACTION, actions, db);
		 * calendar.add(Calendar.DATE, 1); actions.clear();
		 * actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		 * actions.put(COLUMN_NAME_ACTION_GROWINGID, 2);
		 * actions.put(COLUMN_NAME_ACTION_DATE,
		 * dateFormat.format(calendar.getTime()));
		 * insertEntries(TABLE_NAME_ACTION, actions, db);
		 * calendar.add(Calendar.DATE, 1); actions.clear();
		 * actions.put(COLUMN_NAME_ACTION_ACTIONNAMEID, 3);
		 * actions.put(COLUMN_NAME_ACTION_GROWINGID, 3);
		 * actions.put(COLUMN_NAME_ACTION_DATE,
		 * dateFormat.format(calendar.getTime()));
		 * insertEntries(TABLE_NAME_ACTION, actions, db); actions.clear();
		 * 
		 * Log.d(DEBUG_ID, "ACTION works");
		 */
		// growing
		ContentValues growing = new ContentValues();
		growing.put(COLUMN_NAME_GROWING_ID, 1);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 1);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 3);
		insertEntriesIntoDatabase(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 2);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 2);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 4);
		insertEntriesIntoDatabase(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 3);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 1);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 5);
		insertEntriesIntoDatabase(TABLE_NAME_GROWING, growing, db);
		growing.clear();
		growing.put(COLUMN_NAME_GROWING_ID, 4);
		growing.put(COLUMN_NAME_GROWING_PLOTID, 3);
		growing.put(COLUMN_NAME_GROWING_SEEDID, 6);
		insertEntriesIntoDatabase(TABLE_NAME_GROWING, growing, db);
		growing.clear();

		Log.d(LOG_TAG, "growing works");

		//
		// STAGE
		ContentValues stage = new ContentValues();
		stage.put(COLUMN_NAME_STAGE_ID, 3);
		stage.put(COLUMN_NAME_STAGE_NAME, "planning");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 4);
		stage.put(COLUMN_NAME_STAGE_NAME, "soil preparation");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 5);
		stage.put(COLUMN_NAME_STAGE_NAME, "sowing");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 6);
		stage.put(COLUMN_NAME_STAGE_NAME, "vegetative growth");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 7);
		stage.put(COLUMN_NAME_STAGE_NAME, "flowering");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 8);
		stage.put(COLUMN_NAME_STAGE_NAME, "pod-filling");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 9);
		stage.put(COLUMN_NAME_STAGE_NAME, "pod maturity");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		stage.put(COLUMN_NAME_STAGE_ID, 10);
		stage.put(COLUMN_NAME_STAGE_NAME, "harvesting");
		insertEntriesIntoDatabase(TABLE_NAME_STAGE, stage, db);
		stage.clear();

		Log.d(LOG_TAG, "stages works");

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
			}
		}
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPESTAGE, seedtypestage, db);

		seedtypestage.clear();
		Log.d(LOG_TAG, "seed type stages works");

		// weather forecast

		ContentValues wf = new ContentValues();
		wf.put(COLUMN_NAME_WEATHERFORECAST_DATE, "22-05-2012");
		wf.put(COLUMN_NAME_WEATHERFORECAST_VALUE, 28);
		wf.put(COLUMN_NAME_WEATHERFORECAST_TYPE, "Sunny");
		wf.put(COLUMN_NAME_WEATHERFORECAST_DATE1, 1);
		wf.put(COLUMN_NAME_WEATHERFORECAST_VALUE1, 22);
		wf.put(COLUMN_NAME_WEATHERFORECAST_TYPE1, "Light Showers");
		wf.put(COLUMN_NAME_WEATHERFORECAST_ADMINFLAG, 1);

		insertEntriesIntoDatabase(TABLE_NAME_WEATHERFORECAST, wf, db);
		Log.d(LOG_TAG, "WF inserted works");
		wf.clear();

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

		Log.d(LOG_TAG, "points works");

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
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_groundnut));
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "TMV2");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
				mContext.getString(R.string.k_tmv2));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_groundnut_tiled);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 4);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Groundnut");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_groundnut));
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETY, "Samrat");
		seedtype.put(COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
				mContext.getString(R.string.k_samrat));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_groundnut);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_groundnut_tiled);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 5);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Bajra");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_bajra));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_bajra);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_bajra_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 6);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Castor");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_castor));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_castor);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_castor_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 7);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Cowpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_cowpea));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_cowpea);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_cowpea_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 8);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Greengram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_green_gram));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_greengram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_greengram_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 9);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Horsegram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_horsegram));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_horsegram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_horsegram_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 10);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Pigeonpea");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_pigeonpea));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE,
				R.drawable.pic_72px_pigeonpea);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_pidgeonpea_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 11);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Redgram");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_red_gram));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_redgram);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_redgram_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		seedtype.put(COLUMN_NAME_SEEDTYPE_ID, 12);
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, "Sorghum");
		seedtype.put(COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
				mContext.getString(R.string.k_sorghum));
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE, R.drawable.pic_72px_sorghum);
		seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, R.raw.msg_plant);
		seedtype.put(COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
				R.drawable.pic_90px_sorghum_tiled);
		insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
		seedtype.clear();

		Log.d(LOG_TAG, "seedtype works");

		ContentValues recommendation = new ContentValues();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntriesIntoDatabase(TABLE_NAME_RECOMMENDATION, recommendation, db);
		recommendation.clear();
		recommendation.put(COLUMN_NAME_RECOMMENDATION_SEEDID, 1);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_ACTIONID, 2);
		recommendation.put(COLUMN_NAME_RECOMMENDATION_DATE,
				dateFormat.format(calendar.getTime()));
		insertEntriesIntoDatabase(TABLE_NAME_RECOMMENDATION, recommendation, db);
		recommendation.clear();
		Log.d(LOG_TAG, "recommendation works");

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
