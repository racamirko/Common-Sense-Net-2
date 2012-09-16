package com.commonsensenet.realfarm.dataaccess;

import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.Model;

/**
 * Class to manage database, i.e., input, remove and read data.
 * 
 * @author Julien Freudiger
 * @author Hendrik Knoche
 * @author Oscar Bolaños <@oscarbolanos>
 * @author Lisa Nguyen
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

			db.execSQL("create table "
					+ TABLE_NAME_ACTION
					+ " ( "
					+ COLUMN_NAME_ACTION_ID
					+ " integer, "
					+ COLUMN_NAME_ACTION_ACTIONTYPEID
					+ " integer, "
					+ COLUMN_NAME_ACTION_PLOTID
					+ " integer, "
					+ COLUMN_NAME_ACTION_DATE
					+ " text not null, "
					+ COLUMN_NAME_ACTION_SEEDTYPEID
					+ " integer, "
					+ COLUMN_NAME_ACTION_CROPTYPEID
					+ " integer, "
					+ COLUMN_NAME_ACTION_QUANTITY1
					+ " real, "
					+ COLUMN_NAME_ACTION_QUANTITY2
					+ " real, "
					+ COLUMN_NAME_ACTION_UNIT1ID
					+ " integer, "
					+ COLUMN_NAME_ACTION_UNIT2ID
					+ " integer, "
					+ COLUMN_NAME_ACTION_RESOURCE1ID
					+ " integer, "
					+ COLUMN_NAME_ACTION_RESOURCE2ID
					+ " integer, "
					+ COLUMN_NAME_ACTION_PRICE
					+ " integer, "
					+ COLUMN_NAME_ACTION_USERID
					+ " integer not null, "
					+ COLUMN_NAME_ACTION_SENDSTATUS
					+ " integer, "
					+ COLUMN_NAME_ACTION_ISADMINACTION
					+ " boolean, "
					+ COLUMN_NAME_ACTION_TIMESTAMP
					+ " integer not null, "
					+ references(COLUMN_NAME_ACTION_ACTIONTYPEID,
							TABLE_NAME_ACTIONTYPE, COLUMN_NAME_ACTIONTYPE_ID)
					+ references(COLUMN_NAME_ACTION_SEEDTYPEID,
							TABLE_NAME_SEEDTYPE, COLUMN_NAME_SEEDTYPE_ID)
					+ references(COLUMN_NAME_ACTION_CROPTYPEID,
							TABLE_NAME_CROPTYPE, COLUMN_NAME_CROPTYPE_ID)
					+ references(COLUMN_NAME_ACTION_UNIT1ID, TABLE_NAME_UNIT,
							COLUMN_NAME_UNIT_ID)
					+ references(COLUMN_NAME_ACTION_UNIT2ID, TABLE_NAME_UNIT,
							COLUMN_NAME_UNIT_ID)
					+ references(COLUMN_NAME_ACTION_RESOURCE1ID,
							TABLE_NAME_RESOURCE, COLUMN_NAME_RESOURCE_ID)
					+ references(COLUMN_NAME_ACTION_RESOURCE2ID,
							TABLE_NAME_RESOURCE, COLUMN_NAME_RESOURCE_ID)
					// + references(COLUMN_NAME_ACTION_USERID, TABLE_NAME_USER,
					// COLUMN_NAME_USER_ID)
					//
					// + references(COLUMN_NAME_ACTION_PLOTID + ", "
					// + COLUMN_NAME_ACTION_USERID, TABLE_NAME_PLOT,
					// COLUMN_NAME_PLOT_ID + ", "
					// + COLUMN_NAME_PLOT_USERID)
					+ "PRIMARY KEY (" + COLUMN_NAME_ACTION_ID + ", "
					+ COLUMN_NAME_ACTION_USERID + ")" + " ); ");
			Log.d(LOG_TAG, "Created action table");

			// actionsNames
			db.execSQL("create table " + TABLE_NAME_ACTIONTYPE + " ( "
					+ COLUMN_NAME_ACTIONTYPE_ID + " integer primary key, "
					+ COLUMN_NAME_ACTIONTYPE_NAME + " text not null, "
					+ COLUMN_NAME_ACTIONTYPE_IMAGE + " integer, "
					+ COLUMN_NAME_ACTIONTYPE_AUDIO + " integer" + " ); ");
			Log.d(LOG_TAG, "Created actiontype table");

			// advice topics
			db.execSQL("create table " + TABLE_NAME_ADVICE + " ( "
					+ COLUMN_NAME_ADVICE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_ADVICE_PROBLEMID + " integer, "
					+ COLUMN_NAME_ADVICE_AUDIOSEQUENCE + " text, "
					+ COLUMN_NAME_ADVICE_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_ADVICE_STAGENUMBER + " integer" + " ); ");
			Log.d(LOG_TAG, "Created advice table");

			// advice pieces
			db.execSQL("create table " + TABLE_NAME_ADVICEPIECE + " ( "
					+ COLUMN_NAME_ADVICEPIECE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_ADVICEPIECE_AUDIO + " integer, "
					+ COLUMN_NAME_ADVICEPIECE_COMMENT + " text, "
					+ COLUMN_NAME_ADVICEPIECE_ADVICEID + " integer, "
					+ COLUMN_NAME_ADVICEPIECE_SUGGESTEDACTIONID + " integer, "
					+ COLUMN_NAME_ADVICEPIECE_SUGGESTEDRESOURCEID
					+ " integer, " + COLUMN_NAME_ADVICEPIECE_ORDERNUMBER
					+ " integer" + " ); ");
			Log.d(LOG_TAG, "Created advice table");

			// cropTypes
			db.execSQL("create table " + TABLE_NAME_CROPTYPE + " ( "
					+ COLUMN_NAME_CROPTYPE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_CROPTYPE_NAME + " text not null, "
					+ COLUMN_NAME_CROPTYPE_SHORTNAME + " text, "
					+ COLUMN_NAME_CROPTYPE_IMAGE + " integer, "
					+ COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE + " integer, "
					+ COLUMN_NAME_CROPTYPE_AUDIO + " integer" + " ); ");
			Log.d(LOG_TAG, "Created crop type table");

			// Market price
			db.execSQL("create table " + TABLE_NAME_MARKETPRICE + " ( "
					+ COLUMN_NAME_MARKETPRICE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_MARKETPRICE_MIN + " integer, "
					+ COLUMN_NAME_MARKETPRICE_MAX + " integer, "
					+ COLUMN_NAME_MARKETPRICE_DATE + " text, "
					+ COLUMN_NAME_MARKETPRICE_TYPE + " text" + " ); ");
			Log.d(LOG_TAG, "Created market price table");

			// plots
			db.execSQL("create table "
					+ TABLE_NAME_PLOT
					+ " ( "
					+ COLUMN_NAME_PLOT_ID
					+ " integer, "
					+ COLUMN_NAME_PLOT_USERID
					+ " integer not null, "
					+ COLUMN_NAME_PLOT_SEEDTYPEID
					+ " integer, "
					+ COLUMN_NAME_PLOT_SOILTYPEID
					+ " integer, "
					+ COLUMN_NAME_PLOT_IMAGEPATH
					+ " text, "
					+ COLUMN_NAME_PLOT_SIZE
					+ " real, "
					+ COLUMN_NAME_PLOT_SENDSTATUS
					+ " integer, "
					+ COLUMN_NAME_PLOT_ISENABLED
					+ " boolean, "
					+ COLUMN_NAME_PLOT_ISADMINACTION
					+ " boolean, "
					+ COLUMN_NAME_PLOT_TIMESTAMP
					+ " integer not null, "
					+ COLUMN_NAME_PLOT_TYPE
					+ " integer, "
					// + references(COLUMN_NAME_PLOT_USERID, TABLE_NAME_USER,
					// COLUMN_NAME_USER_ID)
					+ references(COLUMN_NAME_PLOT_SEEDTYPEID,
							TABLE_NAME_SEEDTYPE, COLUMN_NAME_SEEDTYPE_ID)
					+ references(COLUMN_NAME_PLOT_SOILTYPEID,
							TABLE_NAME_SOILTYPE, COLUMN_NAME_SOILTYPE_ID)
					+ references(COLUMN_NAME_PLOT_TYPE, TABLE_NAME_RESOURCE,
							COLUMN_NAME_RESOURCE_ID) + "PRIMARY KEY ("
					+ COLUMN_NAME_PLOT_ID + ", " + COLUMN_NAME_PLOT_USERID
					+ ")" + " ); ");
			Log.d(LOG_TAG, "Created plot table");

			// recommendation
			db.execSQL("create table " + TABLE_NAME_RECOMMENDATION + " ( "
					+ COLUMN_NAME_RECOMMENDATION_ID + " integer primary key, "
					+ COLUMN_NAME_RECOMMENDATION_TIMESTAMP
					+ " integer not null, " + COLUMN_NAME_RECOMMENDATION_PLOTID
					+ " integer, " + COLUMN_NAME_RECOMMENDATION_ADVICEID
					+ " integer, " + COLUMN_NAME_RECOMMENDATION_USERID
					+ " integer, "
					+ COLUMN_NAME_RECOMMENDATION_ACTIONREQUIREDBYDATE
					+ " integer, "
					+ COLUMN_NAME_RECOMMENDATION_VALIDTHROUGHDATE
					+ " integer not null, "
					+ COLUMN_NAME_RECOMMENDATION_SEVERITY + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_PROBABILITY + " integer, "
					+ COLUMN_NAME_RECOMMENDATION_ISUNREAD + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created recommendation table");

			// resource
			db.execSQL("create table " + TABLE_NAME_RESOURCE + " ( "
					+ COLUMN_NAME_RESOURCE_ID + " integer primary key, "
					+ COLUMN_NAME_RESOURCE_NAME + " text not null, "
					+ COLUMN_NAME_RESOURCE_SHORTNAME + " text, "
					+ COLUMN_NAME_RESOURCE_IMAGE1 + " integer, "
					+ COLUMN_NAME_RESOURCE_IMAGE2 + " integer, "
					+ COLUMN_NAME_RESOURCE_VALUE + " integer, "
					+ COLUMN_NAME_RESOURCE_BACKGROUNDIMAGE + " integer, "
					+ COLUMN_NAME_RESOURCE_AUDIO + " integer, "
					+ COLUMN_NAME_RESOURCE_TYPE + " integer " + " ); ");
			Log.d(LOG_TAG, "Created Resources Type table");

			// seed type
			db.execSQL("create table "
					+ TABLE_NAME_SEEDTYPE
					+ " ( "
					+ COLUMN_NAME_SEEDTYPE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SEEDTYPE_NAME
					+ " text not null, "
					+ COLUMN_NAME_SEEDTYPE_SHORTNAME
					+ " text, "
					+ COLUMN_NAME_SEEDTYPE_IMAGE
					+ " integer, "
					+ COLUMN_NAME_SEEDTYPE_AUDIO
					+ " integer, "
					+ COLUMN_NAME_SEEDTYPE_CROPTYPEID
					+ " integer, "
					+ references(COLUMN_NAME_SEEDTYPE_CROPTYPEID,
							TABLE_NAME_CROPTYPE, COLUMN_NAME_CROPTYPE_ID, false)
					+ " ); ");
			Log.d(LOG_TAG, "Created seed type table");

			// soil moisture
			db.execSQL("create table " + TABLE_NAME_SOILMOISTURE + " ( "
					+ COLUMN_NAME_SOILMOISTURE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SOILMOISTURE_VALUE + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_DATE + " text, "
					+ COLUMN_NAME_SOILMOISTURE_CLUSTER + " integer, "
					+ COLUMN_NAME_SOILMOISTURE_ISADMINACTION + " boolean "
					+ " ); ");
			Log.d(LOG_TAG, "Created soil moisture table");

			// soil type
			db.execSQL("create table " + TABLE_NAME_SOILTYPE + " ( "
					+ COLUMN_NAME_SOILTYPE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_SOILTYPE_NAME + " text, "
					+ COLUMN_NAME_SOILTYPE_SHORTNAME + " text, "
					+ COLUMN_NAME_SOILTYPE_IMAGE + " integer, "
					+ COLUMN_NAME_SOILTYPE_AUDIO + " integer " + " ); ");
			Log.d(LOG_TAG, "Created soil type table");

			// users
			db.execSQL("create table " + TABLE_NAME_USER + " ( "
					+ COLUMN_NAME_USER_ID + " integer primary key, "
					+ COLUMN_NAME_USER_FIRSTNAME + " text not null, "
					+ COLUMN_NAME_USER_LASTNAME + " text not null, "
					+ COLUMN_NAME_USER_MOBILENUMBER + " text not null, "
					+ COLUMN_NAME_USER_DEVICEID + " text not null, "
					+ COLUMN_NAME_USER_IMAGEPATH + " text, "
					+ COLUMN_NAME_USER_LOCATION + " text, "
					+ COLUMN_NAME_USER_NAME_AUDIO + " integer, "
					+ COLUMN_NAME_USER_LOCATION_AUDIO + " integer, "
					+ COLUMN_NAME_USER_SENDSTATUS + " integer, "
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
					+ COLUMN_NAME_UNIT_IMAGE
					+ " integer, "
					+ COLUMN_NAME_UNIT_AUDIO
					+ " integer, "
					+ COLUMN_NAME_UNIT_VALUE
					+ " integer, "
					+ COLUMN_NAME_UNIT_ACTIONTYPEID
					+ " integer, "
					+ references(COLUMN_NAME_UNIT_ACTIONTYPEID,
							TABLE_NAME_ACTIONTYPE, COLUMN_NAME_ACTIONTYPE_ID,
							false) + " ); ");
			Log.d(LOG_TAG, "Created unit table");

			// Weather forecast
			db.execSQL("create table "
					+ TABLE_NAME_WEATHERFORECAST
					+ " ( "
					+ COLUMN_NAME_WEATHERFORECAST_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_WEATHERFORECAST_TEMPERATURE
					+ " integer not null, "
					+ COLUMN_NAME_WEATHERFORECAST_DATE
					+ " text unique not null, "
					+ COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID
					+ " integer, "
					+ COLUMN_NAME_WEATHERFORECAST_TIMESTAMP
					+ " integer not null, "
					+ references(COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
							TABLE_NAME_WEATHERTYPE,
							COLUMN_NAME_WEATHERFORECAST_ID, false) + " ); ");
			Log.d(LOG_TAG, "Created weather forecast table");

			// Weather types
			db.execSQL("create table " + TABLE_NAME_WEATHERTYPE + " ( "
					+ COLUMN_NAME_WEATHERTYPE_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_WEATHERTYPE_NAME + " text not null, "
					+ COLUMN_NAME_WEATHERTYPE_IMAGE + " integer, "
					+ COLUMN_NAME_WEATHERTYPE_AUDIO + " integer" + " ); ");
			Log.d(LOG_TAG, "Created weather type table");

			db.execSQL("create table " + TABLE_NAME_YIELDAGG + " ( "
					+ COLUMN_NAME_YIELDAGG_ID
					+ " integer primary key autoincrement, "
					+ COLUMN_NAME_YIELDAGG_SEASONID + " integer, "
					+ COLUMN_NAME_YIELDAGG_PLACEID + " integer, "
					+ COLUMN_NAME_YIELDAGG_SEEDTYPEID + " integer, "
					+ COLUMN_NAME_YIELDAGG_SOILTYPEID
					+ " integer, "
					+ COLUMN_NAME_YIELDAGG_SOWINGWINDOWID
					+ " integer, "
					+ COLUMN_NAME_YIELDAGG_HASIRRIGATED
					+ " boolean, "
					// tri-state yes, no, unknown
					+ COLUMN_NAME_YIELDAGG_HASFERTILIZED + " boolean, "
					+ COLUMN_NAME_YIELDAGG_HADPEST + " boolean, "
					+ COLUMN_NAME_YIELDAGG_HADDISEASE + " boolean, "
					+ COLUMN_NAME_YIELDAGG_SPRAYED + " boolean, "
					+ COLUMN_NAME_YIELDAGG_YIELDINQTPACRE + " real " + " ); ");
			Log.d(LOG_TAG, "Created AggYield table");

			Log.d(LOG_TAG, "Database created successfully");

			initValues(db);

		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
			if (!db.isReadOnly()) {
				// enables the foreign key constraints.
				db.execSQL("PRAGMA foreign_keys=ON;");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

		private String references(String fieldName, String tableName, String id) {
			return references(fieldName, tableName, id, true);
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
			return String.format("FOREIGN KEY (%s) REFERENCES %s(%s)%s ",
					fieldName, tableName, id, addComma ? "," : "");
		}
	}

	public static final int ACTION_TYPE_ALL_ID = 0;
	public static final int ACTION_TYPE_FERTILIZE_ID = 2;
	public static final int ACTION_TYPE_HARVEST_ID = 6;
	public static final int ACTION_TYPE_IRRIGATE_ID = 3;
	public static final int ACTION_TYPE_PLAN_ID = 8;
	public static final int ACTION_TYPE_REPORT_ID = 4;
	public static final int ACTION_TYPE_SELL_ID = 7;
	public static final int ACTION_TYPE_SOW_ID = 1;
	public static final int ACTION_TYPE_SPRAY_ID = 5;

	public static final String COLUMN_NAME_ACTION_ACTIONTYPEID = "actionTypeId";
	public static final String COLUMN_NAME_ACTION_CROPTYPEID = "cropTypeId";
	public static final String COLUMN_NAME_ACTION_DATE = "date";
	public static final String COLUMN_NAME_ACTION_ID = "id";
	public static final String COLUMN_NAME_ACTION_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_ACTION_PLOTID = "plotId";
	public static final String COLUMN_NAME_ACTION_PRICE = "price";
	public static final String COLUMN_NAME_ACTION_QUANTITY1 = "quantity1";
	public static final String COLUMN_NAME_ACTION_QUANTITY2 = "quantity2";
	public static final String COLUMN_NAME_ACTION_RESOURCE1ID = "resource1Id";
	public static final String COLUMN_NAME_ACTION_RESOURCE2ID = "resource2Id";
	public static final String COLUMN_NAME_ACTION_SEEDTYPEID = "seedTypeId";
	public static final String COLUMN_NAME_ACTION_SENDSTATUS = "sendStatus";
	public static final String COLUMN_NAME_ACTION_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_ACTION_UNIT1ID = "unit1Id";
	public static final String COLUMN_NAME_ACTION_UNIT2ID = "unit2Id";
	public static final String COLUMN_NAME_ACTION_USERID = "userId";

	public static final String COLUMN_NAME_ACTIONTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_ACTIONTYPE_ID = "id";
	public static final String COLUMN_NAME_ACTIONTYPE_IMAGE = "image";
	public static final String COLUMN_NAME_ACTIONTYPE_NAME = "name";

	public static final String COLUMN_NAME_ADVICE_AUDIOSEQUENCE = "audio";
	public static final String COLUMN_NAME_ADVICE_ID = "id";
	public static final String COLUMN_NAME_ADVICE_PROBLEMID = "problemTypeId";
	public static final String COLUMN_NAME_ADVICE_SEEDTYPEID = "seedTypeId";
	public static final String COLUMN_NAME_ADVICE_STAGENUMBER = "stageNumber";

	public static final String COLUMN_NAME_ADVICEPIECE_ADVICEID = "adviceId";
	public static final String COLUMN_NAME_ADVICEPIECE_AUDIO = "audio";
	public static final String COLUMN_NAME_ADVICEPIECE_COMMENT = "comment";
	public static final String COLUMN_NAME_ADVICEPIECE_ID = "id";
	public static final String COLUMN_NAME_ADVICEPIECE_ORDERNUMBER = "orderNumber";
	public static final String COLUMN_NAME_ADVICEPIECE_SUGGESTEDACTIONID = "suggestedActionId";
	public static final String COLUMN_NAME_ADVICEPIECE_SUGGESTEDRESOURCEID = "suggestedResourceId";

	public static final String COLUMN_NAME_CROPTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE = "backgroundImage";
	public static final String COLUMN_NAME_CROPTYPE_ID = "id";
	public static final String COLUMN_NAME_CROPTYPE_IMAGE = "image";
	public static final String COLUMN_NAME_CROPTYPE_NAME = "name";
	public static final String COLUMN_NAME_CROPTYPE_SHORTNAME = "shortName";

	public static final String COLUMN_NAME_MARKETPRICE_DATE = "date";
	public static final String COLUMN_NAME_MARKETPRICE_ID = "id";
	public static final String COLUMN_NAME_MARKETPRICE_MAX = "max";
	public static final String COLUMN_NAME_MARKETPRICE_MIN = "min";
	public static final String COLUMN_NAME_MARKETPRICE_TYPE = "type";

	public static final String COLUMN_NAME_PLOT_ID = "id";
	public static final String COLUMN_NAME_PLOT_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_PLOT_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_PLOT_ISENABLED = "isEnabled";
	public static final String COLUMN_NAME_PLOT_SEEDTYPEID = "seedtypeId";
	public static final String COLUMN_NAME_PLOT_SENDSTATUS = "sendStatus";
	public static final String COLUMN_NAME_PLOT_SIZE = "size";
	public static final String COLUMN_NAME_PLOT_SOILTYPEID = "soilTypeId";
	public static final String COLUMN_NAME_PLOT_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_PLOT_TYPE = "type";
	public static final String COLUMN_NAME_PLOT_USERID = "userId";

	public static final String COLUMN_NAME_RECOMMENDATION_ACTIONREQUIREDBYDATE = "actionRequiredByDate";
	public static final String COLUMN_NAME_RECOMMENDATION_ADVICEID = "adviceId";
	public static final String COLUMN_NAME_RECOMMENDATION_ID = "id";
	public static final String COLUMN_NAME_RECOMMENDATION_ISUNREAD = "isUnread";
	public static final String COLUMN_NAME_RECOMMENDATION_PLOTID = "plotId";
	public static final String COLUMN_NAME_RECOMMENDATION_PROBABILITY = "probability";
	public static final String COLUMN_NAME_RECOMMENDATION_SEVERITY = "severity";
	public static final String COLUMN_NAME_RECOMMENDATION_TIMESTAMP = "timestamp";
	public static final String COLUMN_NAME_RECOMMENDATION_USERID = "userId";
	public static final String COLUMN_NAME_RECOMMENDATION_VALIDTHROUGHDATE = "validThroughDate";

	public static final String COLUMN_NAME_RESOURCE_AUDIO = "audio";
	public static final String COLUMN_NAME_RESOURCE_BACKGROUNDIMAGE = "backgroundImage";
	public static final String COLUMN_NAME_RESOURCE_ID = "id";
	public static final String COLUMN_NAME_RESOURCE_IMAGE1 = "image1";
	public static final String COLUMN_NAME_RESOURCE_IMAGE2 = "image2";
	public static final String COLUMN_NAME_RESOURCE_NAME = "name";
	public static final String COLUMN_NAME_RESOURCE_SHORTNAME = "shortName";
	public static final String COLUMN_NAME_RESOURCE_TYPE = "type";
	public static final String COLUMN_NAME_RESOURCE_VALUE = "value";

	public static final String COLUMN_NAME_SEEDTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SEEDTYPE_CROPTYPEID = "cropTypeId";
	public static final String COLUMN_NAME_SEEDTYPE_ID = "id";
	public static final String COLUMN_NAME_SEEDTYPE_IMAGE = "resource";
	public static final String COLUMN_NAME_SEEDTYPE_NAME = "name";
	public static final String COLUMN_NAME_SEEDTYPE_SHORTNAME = "shortName";

	public static final String COLUMN_NAME_SOILMOISTURE_CLUSTER = "cluster";
	public static final String COLUMN_NAME_SOILMOISTURE_DATE = "date";
	public static final String COLUMN_NAME_SOILMOISTURE_ID = "id";
	public static final String COLUMN_NAME_SOILMOISTURE_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_SOILMOISTURE_VALUE = "value";

	public static final String COLUMN_NAME_SOILTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_SOILTYPE_ID = "id";
	public static final String COLUMN_NAME_SOILTYPE_IMAGE = "image";
	public static final String COLUMN_NAME_SOILTYPE_NAME = "name";
	public static final String COLUMN_NAME_SOILTYPE_SHORTNAME = "shortName";

	public static final String COLUMN_NAME_UNIT_ACTIONTYPEID = "actionTypeId";
	public static final String COLUMN_NAME_UNIT_AUDIO = "audio";
	public static final String COLUMN_NAME_UNIT_ID = "id";
	public static final String COLUMN_NAME_UNIT_IMAGE = "image";
	public static final String COLUMN_NAME_UNIT_NAME = "name";
	public static final String COLUMN_NAME_UNIT_VALUE = "value";

	public static final String COLUMN_NAME_USER_DEVICEID = "deviceId";
	public static final String COLUMN_NAME_USER_FIRSTNAME = "firstname";
	public static final String COLUMN_NAME_USER_ID = "id";
	public static final String COLUMN_NAME_USER_IMAGEPATH = "imagePath";
	public static final String COLUMN_NAME_USER_ISADMINACTION = "isAdminAction";
	public static final String COLUMN_NAME_USER_ISENABLED = "isEnabled";
	public static final String COLUMN_NAME_USER_LASTNAME = "lastname";
	public static final String COLUMN_NAME_USER_LOCATION = "location";
	public static final String COLUMN_NAME_USER_LOCATION_AUDIO = "locationAudio";
	public static final String COLUMN_NAME_USER_MOBILENUMBER = "mobileNumber";
	public static final String COLUMN_NAME_USER_NAME_AUDIO = "nameAudio";
	public static final String COLUMN_NAME_USER_SENDSTATUS = "sendStatus";
	public static final String COLUMN_NAME_USER_TIMESTAMP = "timestamp";

	public static final String COLUMN_NAME_WEATHERFORECAST_DATE = "date";
	public static final String COLUMN_NAME_WEATHERFORECAST_ID = "id";
	public static final String COLUMN_NAME_WEATHERFORECAST_TEMPERATURE = "temperature";
	public static final String COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID = "weatherTypeId";
	public static final String COLUMN_NAME_WEATHERFORECAST_TIMESTAMP = "timestamp";

	public static final String COLUMN_NAME_WEATHERTYPE_AUDIO = "audio";
	public static final String COLUMN_NAME_WEATHERTYPE_ID = "id";
	public static final String COLUMN_NAME_WEATHERTYPE_IMAGE = "image";
	public static final String COLUMN_NAME_WEATHERTYPE_NAME = "name";

	public static final String COLUMN_NAME_YIELDAGG_HADDISEASE = "hadDisease";
	public static final String COLUMN_NAME_YIELDAGG_HADPEST = "hadPest";
	public static final String COLUMN_NAME_YIELDAGG_HASFERTILIZED = "hasFertilized";
	public static final String COLUMN_NAME_YIELDAGG_HASIRRIGATED = "hasIrrigated";
	public static final String COLUMN_NAME_YIELDAGG_ID = "id";
	public static final String COLUMN_NAME_YIELDAGG_PLACEID = "placeId";
	public static final String COLUMN_NAME_YIELDAGG_SEASONID = "seasonId";
	public static final String COLUMN_NAME_YIELDAGG_SEEDTYPEID = "seedTypeId";
	public static final String COLUMN_NAME_YIELDAGG_SOILTYPEID = "soilTypeId";
	public static final String COLUMN_NAME_YIELDAGG_SOWINGWINDOWID = "sowingWindowId";
	public static final String COLUMN_NAME_YIELDAGG_SPRAYED = "hasSprayed";
	public static final String COLUMN_NAME_YIELDAGG_YIELDINQTPACRE = "yieldInQtPerAcre";

	/** Filename of the database. */
	public static final String DB_NAME = "realFarm.db";
	/** Current version of the database. */
	public static final int DB_VERSION = 1;
	/** Default Device Id. */
	public static final String DEFAULT_DEVICE_ID = "000000000";
	/** Identifier used to debug the database. */
	public static final String LOG_TAG = "RealFarm";
	// TODO: This is for the selling aggregate and the aggregates number
	// indicators on the homescreen. Put that somewhere into the database
	public static final int NUMBER_DAYS_NEWS = 14;

	public static final int RESOURCE_TYPE_ADVICE = 12;
	public static final int RESOURCE_TYPE_DAYS_SPAN = 10;
	public static final int RESOURCE_TYPE_DISEASE_SELECTOR = 17;
	public static final int RESOURCE_TYPE_FERTILIZE_SELECTOR = 20;
	public static final int RESOURCE_TYPE_FERTILIZER = 3;
	public static final int RESOURCE_TYPE_INTERCROP = 4;
	public static final int RESOURCE_TYPE_IRRIGATION_SELECTOR = 18;
	public static final int RESOURCE_TYPE_IRRIGATIONMETHOD = 5;
	public static final int RESOURCE_TYPE_LOCATION = 13;
	public static final int RESOURCE_TYPE_MONTH = 6;
	public static final int RESOURCE_TYPE_PEST_SELECTOR = 16;
	public static final int RESOURCE_TYPE_PESTICIDE = 2;
	public static final int RESOURCE_TYPE_PLOT_TYPE = 11;
	public static final int RESOURCE_TYPE_PROBLEM = 0;
	public static final int RESOURCE_TYPE_SATISFACTION = 7;
	public static final int RESOURCE_TYPE_SOIL_TYPE = 22;
	public static final int RESOURCE_TYPE_SOWING_WINDOW = 15;
	public static final int RESOURCE_TYPE_SPRAYING_SELECTOR = 19;
	public static final int RESOURCE_TYPE_TREATMENT = 8;
	public static final int RESOURCE_TYPE_UNIT = 9;
	public static final int RESOURCE_TYPE_VARIETY = 21;
	public static final int RESOURCE_TYPE_YEAR = 14;

	public static final int SELLING_AGGREGATE_INCREMENT = 99;

	public static final String TABLE_NAME_ACTION = "action";
	public static final String TABLE_NAME_ACTIONTYPE = "actionType";
	public static final String TABLE_NAME_ADVICE = "advice";
	public static final String TABLE_NAME_ADVICEPIECE = "advicePiece";
	public static final String TABLE_NAME_CROPTYPE = "cropType";
	public static final String TABLE_NAME_MARKETPRICE = "marketPrice";
	public static final String TABLE_NAME_PLOT = "plot";
	public static final String TABLE_NAME_RECOMMENDATION = "recommendation";
	public static final String TABLE_NAME_RESOURCE = "resource";
	public static final String TABLE_NAME_SEEDTYPE = "seedType";
	public static final String TABLE_NAME_SOILMOISTURE = "soilMoisture";
	public static final String TABLE_NAME_SOILTYPE = "soilType";
	public static final String TABLE_NAME_UNIT = "unit";
	public static final String TABLE_NAME_USER = "user";
	public static final String TABLE_NAME_WEATHERFORECAST = "weatherForecast";
	public static final String TABLE_NAME_WEATHERTYPE = "weatherType";
	public static final String TABLE_NAME_YIELDAGG = "yieldAgg";

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
		// deletes all the tables.
		mDb.delete(TABLE_NAME_ACTION, null, null);
		mDb.delete(TABLE_NAME_ACTIONTYPE, null, null);
		mDb.delete(TABLE_NAME_ADVICE, null, null);
		mDb.delete(TABLE_NAME_ADVICEPIECE, null, null);
		mDb.delete(TABLE_NAME_CROPTYPE, null, null);
		mDb.delete(TABLE_NAME_MARKETPRICE, null, null);
		mDb.delete(TABLE_NAME_PLOT, null, null);
		mDb.delete(TABLE_NAME_RECOMMENDATION, null, null);
		mDb.delete(TABLE_NAME_RESOURCE, null, null);
		mDb.delete(TABLE_NAME_SEEDTYPE, null, null);
		mDb.delete(TABLE_NAME_SOILMOISTURE, null, null);
		mDb.delete(TABLE_NAME_SOILTYPE, null, null);
		mDb.delete(TABLE_NAME_UNIT, null, null);
		mDb.delete(TABLE_NAME_USER, null, null);
		mDb.delete(TABLE_NAME_WEATHERFORECAST, null, null);
		mDb.delete(TABLE_NAME_WEATHERTYPE, null, null);
		mDb.delete(TABLE_NAME_YIELDAGG, null, null);
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

	public long deleteEntries(String tableName, String selector, String[] values) {
		return deleteFromDatabase(tableName, selector, values, mDb);
	}

	public long deleteEntriesdb(String TableName, String whereClause,
			String[] whereArgs) {
		long result = -1;

		if (TableName != null) {
			try {
				result = mDb.delete(TableName, whereClause, whereArgs);
			} catch (SQLException e) {
				Log.d(LOG_TAG, "Exception" + e);
			}
		}
		return result;
	}

	public long deleteFromDatabase(String tableName, String selector,
			String[] values, SQLiteDatabase database) {
		return database.delete(tableName, selector, values);
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

		// gets the device id of the current user
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();

		// sets the default value if invalid.
		if (deviceId == null) {
			deviceId = DEFAULT_DEVICE_ID;
		}

		// { "John", "Doe", deviceId, "", "farmer_default", "CK Pura",
		// R.raw.john_doe, R.raw.ckpura },
		Object[][] userData = {
				{ "Allappa", "", "911208750943398", "8151906806",
						"farmer_911208750943398", "CK Pura", R.raw.allappa,
						R.raw.ckpura },/* 1 */
				{ "SH Hanumantharayappa", "", "911208750673037", "8151906807",
						"farmer_911208750673037", "CK Pura",
						R.raw.s_h_hanumantharayappa, R.raw.ckpura },/* 2 */
				{ "Raamanji", "", "911208750512359", "8151906858",
						"farmer_911208750512359", "CK Pura", R.raw.ramanji,
						R.raw.ckpura },/* 3 */
				{ "Sanjeevappa", "", "911208750941913", "8151906857",
						"farmer_911208750941913", "CK Pura", R.raw.sanjevappa,
						R.raw.ckpura },/* 4 */
				{ "Fakruddin", "", "911208750963651", "8151906855",
						"farmer_911208750963651", "CK Pura", R.raw.fakruddin,
						R.raw.ckpura },/* 5 */
				{ "Subramanya", "", "911208750485879", "8722577881",
						"farmer_911208750485879", "CK Pura", R.raw.subramanya,
						R.raw.ckpura },/* 6 */
				{ "Nagendra", "", "911208750507219", "8151906788",
						"farmer_911208750507219", "CK Pura", R.raw.nagendra,
						R.raw.ckpura },/* 7 */
				{ "Sanna", "Mallapa", "911208750707959", "8151906790",
						"farmer_911208750707959", "CK Pura",
						R.raw.sanna_malappa, R.raw.ckpura },/* 8 */
				{ "Gangadhara", "", "911208750961135", "8151905805",
						"farmer_911208750961135", "CK Pura", R.raw.gangadhara,
						R.raw.ckpura },/* 9 */
				{ "M Hanumantharayappa", "", "911208750956119", "9740744471",
						"farmer_default", "CK Pura",
						R.raw.s_h_hanumantharayappa, R.raw.ckpura },/* 10 */
				{ "Eranna", "", "911208750715812", "8748923287",
						"farmer_911208750715812", "CK Pura", R.raw.john_doe,
						R.raw.ckpura },/* 11 */
				{ "Pakali", "Nagaraja", "911208750027572", "8151906861",
						"farmer_911208750027572", "CK Pura",
						R.raw.pakali_nagaraja, R.raw.ckpura },/* 12 */
				{ "G Prabhakar", "", "911217500767007", "8151906849",
						"farmer_911217500767007", "CK Pura", R.raw.g_prabhakar,
						R.raw.ckpura },/* 13 */
				{ "Paramesha", "", "911217500770324", "8151906841",
						"farmer_911217500770324", "CK Pura", R.raw.paramesha,
						R.raw.ckpura },/* 14 */
				{ "Bindu", "", "911217500727225", "8151906854",
						"farmer_911217500727225", "CK Pura", R.raw.bindu,
						R.raw.ckpura },/* 15 */
				{ "Sheshagiri", "", "354717046643814", "9448379025",
						"farmer_default", "CK Pura", R.raw.sheshagiri,
						R.raw.ckpura } /* 16 */

		};

		ContentValues users = new ContentValues();
		for (int x = 0; x < userData.length; x++) {
			users.put(COLUMN_NAME_USER_ID, userData[x][2] + "1");
			users.put(COLUMN_NAME_USER_FIRSTNAME, (String) userData[x][0]);
			users.put(COLUMN_NAME_USER_LASTNAME, (String) userData[x][1]);
			users.put(COLUMN_NAME_USER_DEVICEID, (String) userData[x][2]);
			users.put(COLUMN_NAME_USER_MOBILENUMBER, (String) userData[x][3]);
			users.put(COLUMN_NAME_USER_IMAGEPATH, (String) userData[x][4]);
			users.put(COLUMN_NAME_USER_LOCATION, (String) userData[x][5]);
			users.put(COLUMN_NAME_USER_NAME_AUDIO, (Integer) userData[x][6]);
			users.put(COLUMN_NAME_USER_LOCATION_AUDIO, (Integer) userData[x][7]);
			users.put(COLUMN_NAME_USER_SENDSTATUS, userData[x][2]
					.equals(deviceId) ? Model.STATUS_UNSENT
					: Model.STATUS_CONFIRMED);
			users.put(COLUMN_NAME_USER_ISENABLED, 1);
			users.put(COLUMN_NAME_USER_ISADMINACTION, 0);
			users.put(COLUMN_NAME_USER_TIMESTAMP, new Date().getTime());
			insertEntriesIntoDatabase(TABLE_NAME_USER, users, db);
			users.clear();
		}
		Log.d(LOG_TAG, "users works");

		Object[][] actionTypeData = {
				{ ACTION_TYPE_SOW_ID, "Sow", R.drawable.sowingaction,
						R.raw.sowing },
				{ ACTION_TYPE_FERTILIZE_ID, "Fertilize",
						R.drawable.fertilizingaction, R.raw.fertilizing },
				{ ACTION_TYPE_IRRIGATE_ID, "Irrigate",
						R.drawable.irrigationaction, R.raw.irrigate },
				{ ACTION_TYPE_REPORT_ID, "Report",
						R.drawable.problemreportingaction, R.raw.problems },
				{ ACTION_TYPE_SPRAY_ID, "Spray", R.drawable.sprayingaction,
						R.raw.spraying },
				{ ACTION_TYPE_HARVEST_ID, "Harvest",
						R.drawable.harvestingaction, R.raw.harvest },
				{ ACTION_TYPE_SELL_ID, "Sell", R.drawable.sellingaction,
						R.raw.selling }, { ACTION_TYPE_PLAN_ID, "", -1, -1 }

		};

		ContentValues actionType = new ContentValues();
		for (int x = 0; x < actionTypeData.length; x++) {
			actionType.put(COLUMN_NAME_ACTIONTYPE_ID,
					(Integer) actionTypeData[x][0]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_NAME,
					(String) actionTypeData[x][1]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_IMAGE,
					(Integer) actionTypeData[x][2]);
			actionType.put(COLUMN_NAME_ACTIONTYPE_AUDIO,
					(Integer) actionTypeData[x][3]);
			insertEntriesIntoDatabase(TABLE_NAME_ACTIONTYPE, actionType, db);
			actionType.clear();
		}

		Log.d(LOG_TAG, "actionType works");

		// resources
		Object[][] resourceData = {
				/** Problems */
				{ "Late leaf spot", "LLS", R.raw.late_leaf_spot,
						R.drawable.ic_diseasecategory,
						R.drawable.pr_late_leaf_spot, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Pod rot", "Pod rot", R.raw.pod_rot,
						R.drawable.ic_diseasecategory, R.drawable.pr_pod_rot,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Unknown disease", "? disease", R.raw.unknown_disease,
						R.drawable.ic_diseasecategory,
						R.drawable.pr_unknown_disease, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Disease not listed", "D unlisted", R.raw.disease_not_listed,
						R.drawable.ic_diseasecategory,
						R.drawable.square_btn_normal, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Aphids", "Aphids", R.raw.aphids, R.drawable.ic_pestcategory,
						R.drawable.pr_aphids, -1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Leaf miner", "Leaf miner", R.raw.leaf_miner,
						R.drawable.ic_pestcategory, R.drawable.pr_leaf_miner,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Pod borer", "Pod borer", R.raw.pod_borer,
						R.drawable.ic_pestcategory, R.drawable.pr_pod_borer,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Red hairy caterpillar", "R H Caterpillar",
						R.raw.red_hairy_caterpillar,
						R.drawable.ic_pestcategory,
						R.drawable.pr_red_hairy_caterpillar, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Root grub", "Root grub", R.raw.root_grub,
						R.drawable.ic_pestcategory, R.drawable.pr_root_grub,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Unknown pest", "? pest", R.raw.unknown_pest,
						R.drawable.ic_pestcategory, R.drawable.pr_pestunknown,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Pest not listed", "Pe-unlisted", R.raw.pest_not_listed,
						R.drawable.ic_pestcategory,
						R.drawable.square_btn_normal, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "No rain", "No rain", R.raw.no_sound_help,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_norain, -1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Low growth", "Low growth", R.raw.low_growth,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_low_growth, -1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Pegs not developed", "Pegs undev", R.raw.pegs_not_developed,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_pegs_not_developed, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Pod germination", "Pod germination", R.raw.pod_germination,
						R.drawable.ic_otherproblemcategory,
						R.drawable.square_btn_normal, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Reduced flowering", "Red flowering",
						R.raw.reduced_flowering,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_low_flowring, -1, RESOURCE_TYPE_PROBLEM,
						-1 },
				{ "Rot of stalks", "Stalk rot", R.raw.rot_of_stalks,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_rot_of_stalks, -1, RESOURCE_TYPE_PROBLEM,
						-1 },
				{ "Too much vegetative growth", "Veg growth",
						R.raw.too_much_vegetative_growth,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_too_much_vegetation_growth, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				{ "Weeds", "Weeds", R.raw.weeds,
						R.drawable.ic_otherproblemcategory, R.drawable.pr_weed,
						-1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Wild boar", "Wild boar", R.raw.wild_boar,
						R.drawable.ic_otherproblemcategory,
						R.drawable.pr_wild_boar, -1, RESOURCE_TYPE_PROBLEM, -1 },
				{ "Problem not listed", "Pb-unlisted",
						R.raw.problem_not_listed,
						R.drawable.ic_otherproblemcategory,
						R.drawable.square_btn_normal, -1,
						RESOURCE_TYPE_PROBLEM, -1 },
				/** Treatment */
				{ "Treated", "Treated", R.raw.treatmenttoseeds2,
						R.drawable.ic_sowingseedtreated, -1, -1,
						RESOURCE_TYPE_TREATMENT, -1 },
				{ "Not treated", "Not treated", R.raw.treatmenttoseeds3,
						R.drawable.ic_sowingseednottreated, -1, -1,
						RESOURCE_TYPE_TREATMENT, -1 },
				/** Month */
				{ "01 January", "Jan", R.raw.jan, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.JANUARY },
				{ "02 February", "Feb", R.raw.feb, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.FEBRUARY },
				{ "03 March", "Mar", R.raw.mar, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.MARCH },
				{ "04 April", "Apr", R.raw.apr, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.APRIL },
				{ "05 May", "May", R.raw.may, -1, -1, -1, RESOURCE_TYPE_MONTH,
						Calendar.MAY },
				{ "06 June", "Jun", R.raw.jun, -1, -1, -1, RESOURCE_TYPE_MONTH,
						Calendar.JUNE },
				{ "07 July", "Jul", R.raw.jul, -1, -1, -1, RESOURCE_TYPE_MONTH,
						Calendar.JULY },
				{ "08 August", "Aug", R.raw.aug, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.AUGUST },
				{ "09 September", "Sep", R.raw.sep, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.SEPTEMBER },
				{ "10 October", "Oct", R.raw.oct, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.OCTOBER },
				{ "11 November", "Nov", R.raw.nov, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.NOVEMBER },
				{ "12 December", "Dec", R.raw.dec, -1, -1, -1,
						RESOURCE_TYPE_MONTH, Calendar.DECEMBER },
				/** Days span */
				{ "7 days", "7 days", R.raw.seven_days, -1, -1, -1,
						RESOURCE_TYPE_DAYS_SPAN, 7 },
				{ "14 days", "14 days", R.raw.fourteen_days, -1, -1, -1,
						RESOURCE_TYPE_DAYS_SPAN, 14 },
				{ "21 days", "21 days", R.raw.twentyone_days, -1, -1, -1,
						RESOURCE_TYPE_DAYS_SPAN, 21 },
				{ "1 month", "1 month", R.raw.one_month, -1, -1, -1,
						RESOURCE_TYPE_DAYS_SPAN, 31 },
				{ "1 year", "1 year", R.raw.year, -1, -1, -1,
						RESOURCE_TYPE_DAYS_SPAN, 366 },

				/** Treated */
				{ "Main crop", "Main crop", R.raw.maincrop,
						R.drawable.ic_maincrop, -1, -1,
						RESOURCE_TYPE_INTERCROP, -1 },
				{ "Intercrop", "Intercrop", R.raw.intercrop,
						R.drawable.ic_intercrop, -1, -1,
						RESOURCE_TYPE_INTERCROP, -1 },
				/** Fertilizer */
				{ "Complex", "Complex", R.raw.complex, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Compost", "Compost", R.raw.compost, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "DAP", "DAP", R.raw.dap, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Farm Yard Manure / FYM", "FYM", R.raw.fym, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Gypsum", "Gypsum", R.raw.gypsum, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Potash", "Potash", R.raw.potash, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Salt", "Salt", R.raw.salt, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Super", "Super", R.raw.superr, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Urea", "Urea", R.raw.superr, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				{ "Unlisted", "Not in the list", R.raw.unlisted, -1, -1, -1,
						RESOURCE_TYPE_FERTILIZER, -1 },
				/** Pesticide */
				{ "Monocrotophos", "Monocrot.", R.raw.monocrotophos,
						R.drawable.pesticide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Dimethoate", "Dimethoa.", R.raw.dimethoate,
						R.drawable.pesticide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Thiodicarb", "Thiodicarb", R.raw.thiodicarb,
						R.drawable.pesticide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Fenvelerate", "Fenvelerate", R.raw.fenvelerate,
						R.drawable.pesticide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Quinolphos", "Quinolphos", R.raw.quinolphos,
						R.drawable.pesticide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Pesticide not listed", "P-unlisted",
						R.raw.pesticide_not_listed, R.drawable.pesticide, -1,
						-1, RESOURCE_TYPE_PESTICIDE, -1 },
				{ "Dithane M-45", "D. M-45", R.raw.dithane_m_45,
						R.drawable.fungicide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Chlorothalonil", "Chlorot.", R.raw.chlorothalonil,
						R.drawable.fungicide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Cyproconazole", "Cyproconazole", R.raw.cyproconazole,
						R.drawable.fungicide, -1, -1, RESOURCE_TYPE_PESTICIDE,
						-1 },
				{ "Triazole", "Triazole", R.raw.triazole, R.drawable.fungicide,
						-1, -1, RESOURCE_TYPE_PESTICIDE, -1 },
				{ "Fungicide not listed", "F-unlisted",
						R.raw.fungicide_not_listed, R.drawable.fungicide, -1,
						-1, RESOURCE_TYPE_PESTICIDE, -1 },
				/** Irrigation Method. */
				{ "Flooding", "Flooding", R.raw.flooding,
						R.drawable.ic_flooding, -1, -1,
						RESOURCE_TYPE_IRRIGATIONMETHOD, -1 },
				{ "Sprinkling", "Sprinkling", R.raw.sprinkling,
						R.drawable.ic_sprinkling, -1, -1,
						RESOURCE_TYPE_IRRIGATIONMETHOD, -1 },
				/** Satisfaction */
				{ "Good", "Good", R.raw.feedbackgood, R.drawable.smiley_good,
						-1, -1, RESOURCE_TYPE_SATISFACTION, -1 },
				{ "Moderate", "Moderate", R.raw.feedbackmoderate,
						R.drawable.smiley_medium, -1, -1,
						RESOURCE_TYPE_SATISFACTION, -1 },
				{ "Bad", "Bad", R.raw.feedbackbad, R.drawable.smiley_bad, -1,
						-1, RESOURCE_TYPE_SATISFACTION, -1 },
				/** Plot type */
				{ "Irrigated", "Irrigated", R.raw.irrigated,
						R.drawable.irrigatedland, -1, -1,
						RESOURCE_TYPE_PLOT_TYPE, -1 },
				{ "Rainfed", "Rainfed", R.raw.rainfed, R.drawable.rainfedland,
						-1, -1, RESOURCE_TYPE_PLOT_TYPE, -1 },
				/** None image advice page */
				{ "", "", R.raw.none, -1, -1, R.drawable.noactionforadvice,
						RESOURCE_TYPE_ADVICE, -1 },
				/** Locations */
				{ "CK Pura", "CK Pura", R.raw.ckpura, -1, -1, -1,
						RESOURCE_TYPE_LOCATION, -1 },
				/** Sowing window */
				{ "2011", "2011", R.raw.a2011, -1, -1, -1, RESOURCE_TYPE_YEAR,
						-1 },
				/** Sowing window */
				{ "Bharani 27.4. - 10.5.", "27.4. 10.5.", R.raw.bharani, -1,
						-1, -1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Kritika 11.5. - 24.5.", "11.5. 24.5.", R.raw.kritika, -1,
						-1, -1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Rohini 24.5. - 07.6.", "24.5 07.6", R.raw.rohini, -1, -1,
						-1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Mrigashira 08.6 -. 21.6.", "08.6. 21.6.", R.raw.mrigashira,
						-1, -1, -1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Ardra 22.6. - 05.7.", "22.6. 05.7.", R.raw.ardra, -1, -1,
						-1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Punarvasu 06.7. - 19.7.", "06.7. 19.7.", R.raw.punarvasu,
						-1, -1, -1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				{ "Pushya 20.7. - 02.8.", "20.7. 02.8.", R.raw.pushya, -1, -1,
						-1, RESOURCE_TYPE_SOWING_WINDOW, -1 },
				/** Selectors */
				{ "Yes", "", -1, R.drawable.yt_diseased, -1, -1,
						RESOURCE_TYPE_DISEASE_SELECTOR, -1 },
				{ "No", "", -1, R.drawable.yt_diseaseno, -1, -1,
						RESOURCE_TYPE_DISEASE_SELECTOR, -1 },
				{ "All", "", -1, R.drawable.yt_diseasesnotfiltered, -1, -1,
						RESOURCE_TYPE_DISEASE_SELECTOR, -1 },
				{ "Yes", "", -1, R.drawable.yt_pested, -1, -1,
						RESOURCE_TYPE_PEST_SELECTOR, -1 },
				{ "No", "", -1, R.drawable.yt_pestedno, -1, -1,
						RESOURCE_TYPE_PEST_SELECTOR, -1 },
				{ "All", "", -1, R.drawable.yt_pestnotfiltered, -1, -1,
						RESOURCE_TYPE_PEST_SELECTOR, -1 },
				{ "Yes", "", -1, R.drawable.yt_irrigated, -1, -1,
						RESOURCE_TYPE_IRRIGATION_SELECTOR, -1 },
				{ "No", "", -1, R.drawable.yt_irrigatedno, -1, -1,
						RESOURCE_TYPE_IRRIGATION_SELECTOR, -1 },
				{ "All", "", -1, R.drawable.yt_irrigatednotfiltered, -1, -1,
						RESOURCE_TYPE_IRRIGATION_SELECTOR, -1 },
				{ "Yes", "", -1, R.drawable.yt_sprayingselected, -1, -1,
						RESOURCE_TYPE_SPRAYING_SELECTOR, -1 },
				{ "No", "", -1, R.drawable.yt_sprayingno, -1, -1,
						RESOURCE_TYPE_SPRAYING_SELECTOR, -1 },
				{ "All", "", -1, R.drawable.yt_sprayingnotfiltered, -1, -1,
						RESOURCE_TYPE_SPRAYING_SELECTOR, -1 },
				{ "Yes", "", -1, R.drawable.yt_fertilized, -1, -1,
						RESOURCE_TYPE_FERTILIZE_SELECTOR, -1 },
				{ "No", "", -1, R.drawable.yt_fertilizedno, -1, -1,
						RESOURCE_TYPE_FERTILIZE_SELECTOR, -1 },
				{ "All", "", -1, R.drawable.yt_fertilizednotfiltered, -1, -1,
						RESOURCE_TYPE_FERTILIZE_SELECTOR, -1 },
				/** TODO: orbolanos: This should not go here. */
				{ "TMV2", "TMV2", R.raw.tmv_2, R.drawable.yt_varietyselected,
						-1, R.drawable.pic_72px_castor, RESOURCE_TYPE_VARIETY,
						-1 },
				{ "K6", "K6", R.raw.k_6, R.drawable.yt_varietyselected, -1,
						R.drawable.pic_72px_groundnut, RESOURCE_TYPE_VARIETY,
						-1 },
				{ "Samrat", "Samrat", R.raw.samrat,
						R.drawable.yt_varietyselected, -1,
						R.drawable.pic_72px_bajra, RESOURCE_TYPE_VARIETY, -1 },
				{ "JL24", "JL24", R.raw.jl_24, R.drawable.yt_varietyselected,
						-1, R.drawable.pic_72px_groundnut,
						RESOURCE_TYPE_VARIETY, -1 },
				{ "All", "All", -1, R.drawable.yt_varietiesnotfiltered, -1, -1,
						RESOURCE_TYPE_VARIETY, -1 },
				{ "Red loam", "Red loam", R.raw.red_loam,
						R.drawable.yt_soiltypeselected, -1,
						R.drawable.st_redloam, RESOURCE_TYPE_SOIL_TYPE, -1 },
				{ "Sandy", "Sandy", R.raw.sandy,
						R.drawable.yt_soiltypeselected, -1,
						R.drawable.st_sandloam, RESOURCE_TYPE_SOIL_TYPE, -1 },
				{ "Black loam", "Black loam", R.raw.black_loam,
						R.drawable.yt_soiltypeselected, -1,
						R.drawable.st_kappumannu, RESOURCE_TYPE_SOIL_TYPE, -1 },
				{ "Black clayey loam", "BC loam", R.raw.black_clay_loam,
						R.drawable.yt_soiltypeselected, -1,
						R.drawable.st_clayloam, RESOURCE_TYPE_SOIL_TYPE, -1 },
				{ "Jedi Maralu", "Sand", R.raw.jedi_maralu,
						R.drawable.yt_soiltypeselected, -1,
						R.drawable.st_irrigatesoilloam,
						RESOURCE_TYPE_SOIL_TYPE, -1 }, };

		ContentValues resource = new ContentValues();
		for (int x = 0; x < resourceData.length; x++) {
			resource.put(COLUMN_NAME_RESOURCE_NAME, (String) resourceData[x][0]);
			resource.put(COLUMN_NAME_RESOURCE_SHORTNAME,
					(String) resourceData[x][1]);
			resource.put(COLUMN_NAME_RESOURCE_AUDIO,
					(Integer) resourceData[x][2]);
			resource.put(COLUMN_NAME_RESOURCE_IMAGE1,
					(Integer) resourceData[x][3]);
			resource.put(COLUMN_NAME_RESOURCE_IMAGE2,
					(Integer) resourceData[x][4]);
			resource.put(COLUMN_NAME_RESOURCE_BACKGROUNDIMAGE,
					(Integer) resourceData[x][5]);
			resource.put(COLUMN_NAME_RESOURCE_TYPE,
					(Integer) resourceData[x][6]);
			resource.put(COLUMN_NAME_RESOURCE_VALUE,
					(Integer) resourceData[x][7]);
			insertEntriesIntoDatabase(TABLE_NAME_RESOURCE, resource, db);
			resource.clear();
		}

		// units
		Object[][] unitData = {
				{ "1L can(s)", R.drawable.ic_pesticideherbicidecan,
						R.raw.one_litre_can, ACTION_TYPE_SPRAY_ID, -1 },
				{ "bag(s) of 1 kg", R.drawable.onekgbagpesticides,
						R.raw.bagof1kg, ACTION_TYPE_SPRAY_ID, -1 },
				{ "bag(s) of 50 kg", R.drawable.ic_50kgbag, R.raw.bagof50kg,
						ACTION_TYPE_FERTILIZE_ID, -1 },
				{ "cart load(s)", R.drawable.ic_cartload, R.raw.cart_load,
						ACTION_TYPE_FERTILIZE_ID, -1 },
				{ "tractor load(s)", R.drawable.ic_tractorload,
						R.raw.tractor_load, ACTION_TYPE_FERTILIZE_ID, -1 },

				{ "bag of 20 kgs", R.drawable.bag20kg, R.raw.bagof20kg,
						ACTION_TYPE_HARVEST_ID, 20 },
				{ "bag of 21 kgs", R.drawable.bag21kg, R.raw.bagof21kg,
						ACTION_TYPE_HARVEST_ID, 21 },
				{ "bag of 22 kgs", R.drawable.bag22kg, R.raw.bagof22kg,
						ACTION_TYPE_HARVEST_ID, 22 },
				{ "bag of 23 kgs", R.drawable.bag23kg, R.raw.bagof23kg,
						ACTION_TYPE_HARVEST_ID, 23 },
				{ "bag of 24 kgs", R.drawable.bag24kg, R.raw.bagof24kg,
						ACTION_TYPE_HARVEST_ID, 24 },
				{ "bag of 25 kgs", R.drawable.bag25kg, R.raw.bagof25kg,
						ACTION_TYPE_HARVEST_ID, 25 },
				{ "bag of 26 kgs", R.drawable.bag26kg, R.raw.bagof26kg,
						ACTION_TYPE_HARVEST_ID, 26 },
				{ "bag of 27 kgs", R.drawable.bag27kg, R.raw.bagof27kg,
						ACTION_TYPE_HARVEST_ID, 27 },
				{ "bag of 28 kgs", R.drawable.bag28kg, R.raw.bagof28kg,
						ACTION_TYPE_HARVEST_ID, 28 },
				{ "bag of 29 kgs", R.drawable.bag29kg, R.raw.bagof29kg,
						ACTION_TYPE_HARVEST_ID, 29 },
				{ "bag of 30 kgs", R.drawable.bag30kg, R.raw.bagof30kg,
						ACTION_TYPE_HARVEST_ID, 30 },
				{ "bag of 31 kgs", R.drawable.bag31kg, R.raw.bagof31kg,
						ACTION_TYPE_HARVEST_ID, 31 },
				{ "bag of 32 kgs", R.drawable.bag32kg, R.raw.bagof32kg,
						ACTION_TYPE_HARVEST_ID, 32 },
				{ "bag of 33 kgs", R.drawable.bag33kg, R.raw.bagof33kg,
						ACTION_TYPE_HARVEST_ID, 33 },
				{ "bag of 34 kgs", R.drawable.bag34kg, R.raw.bagof34kg,
						ACTION_TYPE_HARVEST_ID, 34 },
				{ "bag of 35 kgs", R.drawable.bag35kg, R.raw.bagof35kg,
						ACTION_TYPE_HARVEST_ID, 35 },
				{ "bag of 36 kgs", R.drawable.bag36kg, R.raw.bagof36kg,
						ACTION_TYPE_HARVEST_ID, 36 },
				{ "bag of 37 kgs", R.drawable.bag37kg, R.raw.bagof37kg,
						ACTION_TYPE_HARVEST_ID, 37 },
				{ "bag of 38 kgs", R.drawable.bag38kg, R.raw.bagof10kg,
						ACTION_TYPE_HARVEST_ID, 38 },
				{ "bag of 39 kgs", R.drawable.bag39kg, R.raw.bagof38kg,
						ACTION_TYPE_HARVEST_ID, 39 },
				{ "bag of 40 kgs", R.drawable.bag40kg, R.raw.bagof40kg,
						ACTION_TYPE_HARVEST_ID, 40 },
				{ "bag of 41 kgs", R.drawable.bag41kg, R.raw.bagof41kg,
						ACTION_TYPE_HARVEST_ID, 41 },
				{ "bag of 42 kgs", R.drawable.bag42kg, R.raw.bagof42kg,
						ACTION_TYPE_HARVEST_ID, 42 },
				{ "bag of 43 kgs", R.drawable.bag43kg, R.raw.bagof43kg,
						ACTION_TYPE_HARVEST_ID, 43 },
				{ "bag of 44 kgs", R.drawable.bag44kg, R.raw.bagof44kg,
						ACTION_TYPE_HARVEST_ID, 44 },
				{ "bag of 45 kgs", R.drawable.bag45kg, R.raw.bagof45kg,
						ACTION_TYPE_HARVEST_ID, 45 },
				{ "bag of 46 kgs", R.drawable.bag46kg, R.raw.bagof46kg,
						ACTION_TYPE_HARVEST_ID, 46 },
				{ "bag of 47 kgs", R.drawable.bag47kg, R.raw.bagof47kg,
						ACTION_TYPE_HARVEST_ID, 47 },
				{ "bag of 48 kgs", R.drawable.bag48kg, R.raw.bagof48kg,
						ACTION_TYPE_HARVEST_ID, 48 },
				{ "bag of 49 kgs", R.drawable.bag49kg, R.raw.bagof49kg,
						ACTION_TYPE_HARVEST_ID, 49 },
				{ "bag of 50 kgs", R.drawable.bag50kg, R.raw.bagof50kg,
						ACTION_TYPE_HARVEST_ID, 50 },

				{ "bag of 20 kgs", R.drawable.bag20kg, R.raw.bagof20kg,
						ACTION_TYPE_SELL_ID, 20 },
				{ "bag of 21 kgs", R.drawable.bag21kg, R.raw.bagof21kg,
						ACTION_TYPE_SELL_ID, 21 },
				{ "bag of 22 kgs", R.drawable.bag22kg, R.raw.bagof22kg,
						ACTION_TYPE_SELL_ID, 22 },
				{ "bag of 23 kgs", R.drawable.bag23kg, R.raw.bagof23kg,
						ACTION_TYPE_SELL_ID, 23 },
				{ "bag of 24 kgs", R.drawable.bag24kg, R.raw.bagof24kg,
						ACTION_TYPE_SELL_ID, 24 },
				{ "bag of 25 kgs", R.drawable.bag25kg, R.raw.bagof25kg,
						ACTION_TYPE_SELL_ID, 25 },
				{ "bag of 26 kgs", R.drawable.bag26kg, R.raw.bagof26kg,
						ACTION_TYPE_SELL_ID, 26 },
				{ "bag of 27 kgs", R.drawable.bag27kg, R.raw.bagof27kg,
						ACTION_TYPE_SELL_ID, 27 },
				{ "bag of 28 kgs", R.drawable.bag28kg, R.raw.bagof28kg,
						ACTION_TYPE_SELL_ID, 28 },
				{ "bag of 29 kgs", R.drawable.bag29kg, R.raw.bagof29kg,
						ACTION_TYPE_SELL_ID, 29 },
				{ "bag of 30 kgs", R.drawable.bag30kg, R.raw.bagof30kg,
						ACTION_TYPE_SELL_ID, 30 },
				{ "bag of 31 kgs", R.drawable.bag31kg, R.raw.bagof31kg,
						ACTION_TYPE_SELL_ID, 31 },
				{ "bag of 32 kgs", R.drawable.bag32kg, R.raw.bagof32kg,
						ACTION_TYPE_SELL_ID, 32 },
				{ "bag of 33 kgs", R.drawable.bag33kg, R.raw.bagof33kg,
						ACTION_TYPE_SELL_ID, 33 },
				{ "bag of 34 kgs", R.drawable.bag34kg, R.raw.bagof34kg,
						ACTION_TYPE_SELL_ID, 34 },
				{ "bag of 35 kgs", R.drawable.bag35kg, R.raw.bagof35kg,
						ACTION_TYPE_SELL_ID, 35 },
				{ "bag of 36 kgs", R.drawable.bag36kg, R.raw.bagof36kg,
						ACTION_TYPE_SELL_ID, 36 },
				{ "bag of 37 kgs", R.drawable.bag37kg, R.raw.bagof37kg,
						ACTION_TYPE_SELL_ID, 37 },
				{ "bag of 38 kgs", R.drawable.bag38kg, R.raw.bagof38kg,
						ACTION_TYPE_SELL_ID, 38 },
				{ "bag of 39 kgs", R.drawable.bag39kg, R.raw.bagof39kg,
						ACTION_TYPE_SELL_ID, 39 },
				{ "bag of 40 kgs", R.drawable.bag40kg, R.raw.bagof40kg,
						ACTION_TYPE_SELL_ID, 40 },
				{ "bag of 41 kgs", R.drawable.bag41kg, R.raw.bagof41kg,
						ACTION_TYPE_SELL_ID, 41 },
				{ "bag of 42 kgs", R.drawable.bag42kg, R.raw.bagof42kg,
						ACTION_TYPE_SELL_ID, 42 },
				{ "bag of 43 kgs", R.drawable.bag43kg, R.raw.bagof43kg,
						ACTION_TYPE_SELL_ID, 43 },
				{ "bag of 44 kgs", R.drawable.bag44kg, R.raw.bagof44kg,
						ACTION_TYPE_SELL_ID, 44 },
				{ "bag of 45 kgs", R.drawable.bag45kg, R.raw.bagof45kg,
						ACTION_TYPE_SELL_ID, 45 },
				{ "bag of 46 kgs", R.drawable.bag46kg, R.raw.bagof46kg,
						ACTION_TYPE_SELL_ID, 46 },
				{ "bag of 47 kgs", R.drawable.bag47kg, R.raw.bagof47kg,
						ACTION_TYPE_SELL_ID, 47 },
				{ "bag of 48 kgs", R.drawable.bag48kg, R.raw.bagof48kg,
						ACTION_TYPE_SELL_ID, 48 },
				{ "bag of 49 kgs", R.drawable.bag49kg, R.raw.bagof49kg,
						ACTION_TYPE_SELL_ID, 49 },
				{ "bag of 50 kgs", R.drawable.bag50kg, R.raw.bagof50kg,
						ACTION_TYPE_SELL_ID, 50 },

				{ "unknown", R.drawable.unitunknown, R.raw.unit_unknown,
						ACTION_TYPE_ALL_ID, 0 },
				{ "none", R.drawable.unitnone, R.raw.none, ACTION_TYPE_ALL_ID,
						0 }

		};

		ContentValues unit = new ContentValues();
		for (int x = 0; x < unitData.length; x++) {
			unit.put(COLUMN_NAME_UNIT_NAME, (String) unitData[x][0]);
			unit.put(COLUMN_NAME_UNIT_IMAGE, (Integer) unitData[x][1]);
			unit.put(COLUMN_NAME_UNIT_AUDIO, (Integer) unitData[x][2]);
			unit.put(COLUMN_NAME_UNIT_ACTIONTYPEID, (Integer) unitData[x][3]);
			unit.put(COLUMN_NAME_UNIT_VALUE, (Integer) unitData[x][4]);
			insertEntriesIntoDatabase(TABLE_NAME_UNIT, unit, db);
			unit.clear();
		}

		Log.d(LOG_TAG, "unit works");

		Object[][] soilTypes = {
				{ "Red loam", "Red loam", R.drawable.st_redloam, R.raw.red_loam },
				{ "Sandy", "Sandy", R.drawable.st_sandloam, R.raw.sandy },
				{ "Black loam", "Black loam", R.drawable.st_kappumannu,
						R.raw.black_loam },
				{ "Black clayey loam", "BC loam", R.drawable.st_clayloam,
						R.raw.black_clay_loam },
				{ "Jedi Maralu", "Sand", R.drawable.st_irrigatesoilloam,
						R.raw.jedi_maralu }

		};

		ContentValues soilType = new ContentValues();
		for (int x = 0; x < soilTypes.length; x++) {
			soilType.put(COLUMN_NAME_SOILTYPE_NAME, (String) soilTypes[x][0]);
			soilType.put(COLUMN_NAME_SOILTYPE_SHORTNAME,
					(String) soilTypes[x][1]);
			soilType.put(COLUMN_NAME_SOILTYPE_IMAGE, (Integer) soilTypes[x][2]);
			soilType.put(COLUMN_NAME_SOILTYPE_AUDIO, (Integer) soilTypes[x][3]);
			insertEntriesIntoDatabase(TABLE_NAME_SOILTYPE, soilType, db);
			soilType.clear();
		}

		Log.d(LOG_TAG, "soil type works");

		Object[][] weatherTypeData = {
				{ "Sunny", R.drawable.wf_sunny, R.raw.sunny },
				{ "Chance of Rain", R.drawable.wf_lightrain, R.raw.lightshowers },
				{ "Cloudy", R.drawable.wf_cloudy, R.raw.cloudy },
				{ "Rain", R.drawable.wf_rain, R.raw.rain },
				{ "Partly Cloudy", R.drawable.wf_partlycloudy, R.raw.overcast },
				{ "Storm", R.drawable.wf_storm, R.raw.stormy }

		};

		ContentValues wt = new ContentValues();
		for (int x = 0; x < weatherTypeData.length; x++) {

			wt.put(COLUMN_NAME_WEATHERTYPE_NAME, (String) weatherTypeData[x][0]);
			wt.put(COLUMN_NAME_WEATHERTYPE_IMAGE,
					(Integer) weatherTypeData[x][1]);
			wt.put(COLUMN_NAME_WEATHERTYPE_AUDIO,
					(Integer) weatherTypeData[x][2]);

			insertEntriesIntoDatabase(TABLE_NAME_WEATHERTYPE, wt, db);
			wt.clear();
		}

		Log.d(LOG_TAG, "weather type works");

		// cropTypes
		ContentValues croptype = new ContentValues();
		Object[][] cropData = {
				{ "Groundnut", "Gndnut", R.drawable.pic_72px_groundnut,
						R.drawable.pic_90px_groundnut_tiled, R.raw.gnut },
				{ "Bajra / pearl millet", "Bajra", R.drawable.pic_72px_bajra,
						R.drawable.pic_90px_bajra_tiled, R.raw.bajra },
				{ "Castor", "Castor", R.drawable.pic_72px_castor,
						R.drawable.pic_90px_castor_tiled, R.raw.castor },
				{ "Cow pea", "Cow pea", R.drawable.pic_72px_cowpea,
						R.drawable.pic_90px_cowpea_tiled, R.raw.cowpea },
				{ "Field beans", "F beans", R.drawable.fieldbean,
						R.drawable.fieldbean, R.raw.field_beans },
				{ "Green / moong gram", "Gr gram",
						R.drawable.pic_72px_greengram,
						R.drawable.pic_90px_greengram_tiled, R.raw.greengram },
				{ "Horse gram", "H gram", R.drawable.pic_72px_horsegram,
						R.drawable.pic_90px_horsegram_tiled, R.raw.horsegram },
				{ "Padddy / rice", "Padddy", R.drawable.paddy,
						R.drawable.paddy, R.raw.paddy },
				{ "Ragi / finger millet", "Ragi", R.drawable.ragi,
						R.drawable.ragi, R.raw.ragi },
				{ "Sorghum", "Sorghum", R.drawable.pic_72px_sorghum,
						R.drawable.pic_90px_sorghum_tiled, R.raw.sorghum }

		};

		for (int x = 0; x < cropData.length; x++) {
			croptype.put(COLUMN_NAME_CROPTYPE_NAME, (String) cropData[x][0]);
			croptype.put(COLUMN_NAME_CROPTYPE_SHORTNAME,
					(String) cropData[x][1]);
			croptype.put(COLUMN_NAME_CROPTYPE_IMAGE, (Integer) cropData[x][2]);
			croptype.put(COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE,
					(Integer) cropData[x][3]);
			croptype.put(COLUMN_NAME_CROPTYPE_AUDIO, (Integer) cropData[x][4]);
			insertEntriesIntoDatabase(TABLE_NAME_CROPTYPE, croptype, db);
			croptype.clear();
		}

		// seedType
		ContentValues seedtype = new ContentValues();
		Object[][] seedData = {
				{ "JL24", "JL24", R.drawable.pic_72px_groundnut, R.raw.jl_24, 1 },
				{ "K6 / Kadari ghat", "K6", R.drawable.pic_72px_groundnut,
						R.raw.k_6, 1 },
				{ "Samrat", "Samrat", R.drawable.pic_72px_bajra, R.raw.samrat,
						1 },
				{ "TMV2 / Bunching", "TMV2", R.drawable.pic_72px_castor,
						R.raw.tmv_2, 1 },
				{ "Bajra / pearl millet", "Bajra", R.drawable.pic_72px_bajra,
						R.raw.bajra, 2 },
				{ "Castor", "Castor", R.drawable.pic_72px_castor, R.raw.castor,
						3 },
				{ "Cow pea", "Cow pea", R.drawable.pic_72px_cowpea,
						R.raw.cowpea, 4 },
				{ "Field beans", "F beans", R.drawable.fieldbean,
						R.raw.field_beans, 5 },
				{ "Green / moong gram", "Gr gram",
						R.drawable.pic_72px_greengram, R.raw.greengram, 6 },
				{ "Horse gram", "H gram", R.drawable.pic_72px_horsegram,
						R.raw.horsegram, 7 },
				{ "Paddy / rice", "Paddy", R.drawable.paddy, R.raw.paddy, 8 },
				{ "Ragi / finger millet", "Ragi", R.drawable.ragi, R.raw.ragi,
						9 },
				{ "Sorghum", "Sorghum", R.drawable.pic_72px_sorghum,
						R.raw.sorghum, 10 }

		};

		for (int x = 0; x < seedData.length; x++) {
			seedtype.put(COLUMN_NAME_SEEDTYPE_NAME, (String) seedData[x][0]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_SHORTNAME,
					(String) seedData[x][1]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_IMAGE, (Integer) seedData[x][2]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_AUDIO, (Integer) seedData[x][3]);
			seedtype.put(COLUMN_NAME_SEEDTYPE_CROPTYPEID,
					(Integer) seedData[x][4]);
			insertEntriesIntoDatabase(TABLE_NAME_SEEDTYPE, seedtype, db);
			seedtype.clear();
		}

		Object[][] yieldData = {};

		ContentValues yieldAgg = new ContentValues();
		for (int x = 0; x < yieldData.length; x++) {
			yieldAgg.put(COLUMN_NAME_YIELDAGG_HADPEST, (String) yieldData[x][0]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_HASFERTILIZED,
					(String) yieldData[x][1]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_HADDISEASE,
					(String) yieldData[x][2]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_HASIRRIGATED,
					(String) yieldData[x][3]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_ID, (String) yieldData[x][4]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_PLACEID, (String) yieldData[x][5]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_SEASONID,
					(String) yieldData[x][6]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_SEEDTYPEID,
					(String) yieldData[x][7]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_SOWINGWINDOWID,
					(String) yieldData[x][8]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_SPRAYED, (String) yieldData[x][9]);
			yieldAgg.put(COLUMN_NAME_YIELDAGG_YIELDINQTPACRE,
					(Double) yieldData[x][10]);
			insertEntriesIntoDatabase(TABLE_NAME_YIELDAGG, yieldAgg, db);
			yieldAgg.clear();
		}

		Object[][] adviceData = {
				{
						R.raw.till_date_collected_info + ","
								+ R.raw.because_lateeafspot_podrot + ","
								+ R.raw.percent_loss, -1, -1, 1 },
				{
						R.raw.till_date_collected_info + ","
								+ R.raw.caused_by_leaf_miner + ","
								+ R.raw.percent_loss, -1, -1, 1 }

		};

		ContentValues adviceAgg = new ContentValues();
		for (int x = 0; x < adviceData.length; x++) {
			adviceAgg.put(COLUMN_NAME_ADVICE_AUDIOSEQUENCE,
					(String) adviceData[x][0]);
			adviceAgg.put(COLUMN_NAME_ADVICE_PROBLEMID,
					(Integer) adviceData[x][1]);
			adviceAgg.put(COLUMN_NAME_ADVICE_SEEDTYPEID,
					(Integer) adviceData[x][2]);
			adviceAgg.put(COLUMN_NAME_ADVICE_STAGENUMBER,
					(Integer) adviceData[x][3]);
			insertEntriesIntoDatabase(TABLE_NAME_ADVICE, adviceAgg, db);
			adviceAgg.clear();
		}

		Object[][] advicePieceData = {
				{
						1,
						R.raw.advice_lateleafspot_podrot,
						"34 g/ha  (0.5 g per liter) of cyproconazole and 420 g/ha (1 g per liter) of chlorothalonil",
						1, ACTION_TYPE_SPRAY_ID, -1 },
				{ 2, R.raw.advice_leafminer, "monocrotophos 36 SL @ 1ml/l", 1,
						ACTION_TYPE_SPRAY_ID, -1 },

		};

		ContentValues advicePieceAgg = new ContentValues();
		for (int x = 0; x < advicePieceData.length; x++) {
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_ADVICEID,
					(Integer) advicePieceData[x][0]);
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_AUDIO,
					(Integer) advicePieceData[x][1]);
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_COMMENT,
					(String) advicePieceData[x][2]);
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_ORDERNUMBER,
					(Integer) advicePieceData[x][3]);
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_SUGGESTEDACTIONID,
					(Integer) advicePieceData[x][4]);
			advicePieceAgg.put(COLUMN_NAME_ADVICEPIECE_SUGGESTEDRESOURCEID,
					(Integer) advicePieceData[x][5]);
			insertEntriesIntoDatabase(TABLE_NAME_ADVICEPIECE, advicePieceAgg,
					db);
			advicePieceAgg.clear();
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
				Log.d(LOG_TAG, "Exception: " + e);
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

	public long update(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		long result = -1;
		if (tableName != null && values != null) {
			result = mDb.update(tableName, values, whereClause, whereArgs);
		}
		return result;
	}
}
