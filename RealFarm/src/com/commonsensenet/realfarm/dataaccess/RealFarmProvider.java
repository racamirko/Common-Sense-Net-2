package com.commonsensenet.realfarm.dataaccess;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.Fertilizing;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Harvesting;
import com.commonsensenet.realfarm.model.MarketPrice;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.Selling;
import com.commonsensenet.realfarm.model.Sowing;
import com.commonsensenet.realfarm.model.Spraying;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WFList;

public class RealFarmProvider {
	/** Cached seeds to improve performance. */
	private List<Seed> mAllSeeds;
	/** Cached actionnames to improve performance. */
	private List<ActionName> mAllActionNames;
	/** Real farm database access. */
	private RealFarmDatabase mDb;
	protected RealFarmProvider mDataProvider;
	protected int maxWF;
	private List<Action> mAllActions;
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static Map<Context, RealFarmProvider> mapProviders = new HashMap<Context, RealFarmProvider>();
    public int Add_user_id=0;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	Calendar calendar = Calendar.getInstance();
	
	//Used for logging data related to database
			private String mExternalDirectoryLog;
			public static final String LOG_FOLDER = "/csn_app_logs";
			//public static final String LOG_FOLDER = "/Android/data/com.commonsensenet.realfarm/files";
			File file,file1;
			FileWriter fWriter, fWriter1;
			String DatabaseEntryDate;
			
	static public RealFarmProvider getInstance(Context ctx) {
		if (!mapProviders.containsKey(ctx))
			mapProviders.put(ctx, new RealFarmProvider(
					new RealFarmDatabase(ctx)));
		return mapProviders.get(ctx);
	}

	protected RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDb = database;

		// used to force creation.
		mDb.open();
		mDb.close();
	}

	public RealFarmDatabase getDatabase() {
		return mDb;
	}

	public ActionName getActionNameById(int actionNameId) {

		mDb.open();

		ActionName tmpAction = null;

		Cursor c0 = mDb
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTIONNAME,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "="
								+ actionNameId, null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			tmpAction = new ActionName(actionNameId, c0.getString(0),
					c0.getString(1), c0.getInt(2), c0.getInt(3), c0.getInt(4));
		}
		c0.close();
		mDb.close();

		return tmpAction;
	}

	public List<ActionName> getActionNamesList() {

		if (mAllActionNames == null) {
			// opens the database.
			mDb.open();

			// query all actions
			Cursor c = mDb
					.getEntries(
							RealFarmDatabase.TABLE_NAME_ACTIONNAME,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME_KANNADA,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
							null, null, null, null, null);
			c.moveToFirst();

			mAllActionNames = new LinkedList<ActionName>();

			if (c.getCount() > 0) {
				do {
					mAllActionNames.add(new ActionName(c.getInt(0), c
							.getString(1), c.getString(2), c.getInt(3), c
							.getInt(4), c.getInt(5)));

					String log = "ACTIONNAME_ID: "
							+ c.getInt(0)
							+ " ,NAME " 
							+ c.getString(1) + " ,NAME_KANNADA: "
							+ c.getString(2) + " ,RESOURCE" + c.getInt(3)
							+ " AUDIO " + c.getInt(4) + " ADMINFLAG "
							+ c.getInt(5) + "\r\n";
					Log.d("action name values: ", log);
					
					if(Global.WriteToSD==true)
					{
					File_Log_Create("value.txt","Action names table \r\n");
					File_Log_Create("value.txt",log);
					}

				} while (c.moveToNext());
			}

			c.close();
			mDb.close();

		}

		return mAllActionNames;
	}
/*	public List<Action> getActionsByUserId(int userId) {

		List<Action> tmpList = new ArrayList<Action>();
		
		List<Plot> plots = getPlotsByUserId(userId);
		for(int x = 0; x < plots.size(); x++) {
			tmpList.addAll(getActionsByPlotId(plots.get(x).getId()));
		}
		
		return tmpList;
	}
*/
/*	public List<Action> getActionsByPlotId(int plotId) {

		List<Action> tmpList = new ArrayList<Action>();

		mDb.open();

		final String SQL = "SELECT a.%s, a.%s, a.%s, a.%s, a.%s, a.%s, a.%s FROM "
				+ RealFarmDatabase.TABLE_NAME_ACTION
				+ " a INNER JOIN "
				+ RealFarmDatabase.TABLE_NAME_GROWING
				+ " b ON a.%s = b.%s WHERE b.%s = " + plotId;

		Cursor c = mDb.executeQuery(String.format(SQL,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID,
				RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY,
				RealFarmDatabase.COLUMN_NAME_ACTION_UNITID,
				RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
				RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
				RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
				RealFarmDatabase.COLUMN_NAME_GROWING_ID,
				RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID), null);

		if (c.getCount() > 0) {

			c.moveToFirst();
			do {
				tmpList.add(new Action(c.getInt(0), c.getInt(1), c.getInt(2), c
						.getInt(3), c.getInt(4), c.getInt(5), c.getString(6)));

			} while (c.moveToNext());
		}

		c.close();
		mDb.close();
		return tmpList;

	}
	*/
public List<Growing> getGrowingsByUserId(int userId) {
		
		// gets all the plots of the current user.
		List<Plot> tmpPlots = getPlotsByUserId(userId);
		
		List <Growing> growing = new ArrayList<Growing>();
		
		// obtains the growing information of all the available plots.
		for(int x = 0; x < tmpPlots.size(); x++) {
			// adds all the growing information from the given plot
			growing.addAll(getGrowingsByPlotId(tmpPlots.get(x).getId()));
		}
		
		return growing;
	}

	public List<Growing> getGrowingsByPlotId(int plotId) {
		mDb.open();

		List<Growing> growing = new ArrayList<Growing>();

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
				new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
						RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID },
				RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID + "=" + plotId,
				null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				growing.add(new Growing(c.getInt(0), c.getInt(1), c.getInt(2)));
			} while (c.moveToNext());
		}
		c.close();
		mDb.close();
		return growing;

	}

	public Plot getPlotById(int plotId) {

		mDb.open();
		Plot tmpPlot = null;
		int ownerId = 0;

		// Read points from database for each user
		Cursor c02 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_POINT,
				new String[] { RealFarmDatabase.COLUMN_NAME_POINT_X,
						RealFarmDatabase.COLUMN_NAME_POINT_Y },
				RealFarmDatabase.COLUMN_NAME_POINT_PLOTID + "=" + plotId, null,
				null, null, null);

		// if there are points to plot
		if (c02.getCount() > 0) {
			int j = 0;
			c02.moveToFirst();

			Point[] polyPoints = new Point[c02.getCount()];

			do { // for each point in the plot, draw it
				int x1 = c02.getInt(0);
				int y1 = c02.getInt(1);
				polyPoints[j] = new Point(x1, y1);
				j = j + 1;
			} while (c02.moveToNext());

			Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
					new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_USERID },
					RealFarmDatabase.COLUMN_NAME_PLOT_ID + "=" + plotId, null,
					null, null, null);

			if (c.getCount() > 0) {
				c.moveToFirst();
				ownerId = c.getInt(0);
			}
			c.close();

			tmpPlot = new Plot(polyPoints, plotId, ownerId);
		}
		c02.close();
		mDb.close();

		return tmpPlot;
	}

	public List<Plot> getPlotsByUserId(int userId) {
		List<Plot> tmpList = new ArrayList<Plot>();

		// opens the database
		mDb.open();

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId, null,
				null, null, null);

		// if there are users in the table
		if ((!c0.isClosed()) && (c0.getCount() > 0)) {
			int i = 0;

			// goes to the first entry
			c0.moveToFirst();

			do { // for each plot, draw them
				int id = c0.getInt(0);

				// Read points from database for each user
				Cursor c02 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_POINT,
						new String[] { RealFarmDatabase.COLUMN_NAME_POINT_X,
								RealFarmDatabase.COLUMN_NAME_POINT_Y },
						RealFarmDatabase.COLUMN_NAME_POINT_PLOTID + "=" + id,
						null, null, null, null);

				// if there are points to plotx
				if (c02.getCount() > 0) {
					int j = 0;
					c02.moveToFirst();

					Point[] polyPoints = new Point[c02.getCount()];

					do { // for each point in the plot, draw it

						int x1 = c02.getInt(0);
						int y1 = c02.getInt(1);

						polyPoints[j] = new Point(x1, y1);

						j = j + 1;
					} while (c02.moveToNext());

					// adds the polygon to the list.
					tmpList.add(new Plot(polyPoints, id, userId));
				}

				c02.close();
				i = i + 1;
			} while (c0.moveToNext());
		}

		c0.close();
		mDb.close();

		return tmpList;
	}

	public List<Plot> getPlotsList() {
		List<Plot> tmpList = new ArrayList<Plot>();

		mDb.open();

		// Create objects to display in plotOverlay for all users
		Cursor c0 = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID });

		// if there are users in the table
		if ((!c0.isClosed()) && (c0.getCount() > 0)) {
			int i = 0;
			c0.moveToFirst();

			do { // for each plot, draw them
				int id = c0.getInt(0);
				int ownerId = c0.getInt(1);
				// Read points from database for each user
				Cursor c02 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_POINT,
						new String[] { RealFarmDatabase.COLUMN_NAME_POINT_X,
								RealFarmDatabase.COLUMN_NAME_POINT_Y,
								RealFarmDatabase.COLUMN_NAME_POINT_ID },
						RealFarmDatabase.COLUMN_NAME_POINT_PLOTID + "=" + id,
						null, null, null, null);

				// if there are points to plot
				if (c02.getCount() > 0) {
					int j = 0;
					c02.moveToFirst();

					Point[] polyPoints = new Point[c02.getCount()];

					do { // creates each polygon object using the given points.

						int x1 = c02.getInt(0);
						int y1 = c02.getInt(1);

						polyPoints[j] = new Point(x1, y1);

						j = j + 1;
					} while (c02.moveToNext());

					// adds the polygon to the list.
					tmpList.add(new Plot(polyPoints, id, ownerId));
				}
				c02.close();
				i = i + 1;
			} while (c0.moveToNext());
		}
		c0.close();
		mDb.close();

		return tmpList;
	}

	public List<Recommendation> getRecommendationsList() {

		mDb.open();

		List<Recommendation> result = new ArrayList<Recommendation>();

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_RECOMMENDATION,
				new String[] { RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_SEEDID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ACTIONID,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_DATE },
				null, null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();

			do {
				result.add(new Recommendation(c.getInt(0), c.getInt(1), c
						.getInt(2), c.getString(3)));
			} while (c.moveToNext());
		}

		c.close();
		mDb.close();

		return result;

	}

	public Seed getSeedById(int seedId) {                                        

		Seed res = null;
		mDb.open();
		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ADMINFLAG},
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = new Seed(seedId, c0.getString(0), c0.getString(1),
					c0.getInt(2), c0.getInt(3), c0.getInt(4), c0.getString(5),
					c0.getString(6), c0.getInt(7),c0.getInt(8));                             //modified
		}
		c0.close();
		mDb.close();
		return res;

	}

	public List<Seed> getSeedsList() {                             //modified

		// seeds are not in cache
		if (mAllSeeds == null) {

			mAllSeeds = new ArrayList<Seed>();
			mDb.open();

			Cursor c0 = mDb
					.getAllEntries(
							RealFarmDatabase.TABLE_NAME_SEEDTYPE,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ADMINFLAG});

			if (c0.getCount() > 0) {
				c0.moveToFirst();
				do {
					Seed s = new Seed(c0.getInt(0), c0.getString(1),
							c0.getString(2), c0.getInt(3), c0.getInt(4),
							c0.getInt(5), c0.getString(6), c0.getString(7),
							c0.getInt(8),c0.getInt(9));
					mAllSeeds.add(s);
					
					String log = "id: " + c0.getInt(0) + " ,name: "                   
							+ c0.getString(1) + " ,namekannada: " +c0.getString(2)
								+ ",resource: " +c0.getInt(3) 
								+ " ,audio: " +c0.getInt(4) + " ,days to harvest: "
							+c0.getInt(5) + " ,variety: " +c0.getString(6)
							+" ,variety kannada: " +c0.getString(7)+ ",resource bg: " +c0.getInt(8)
							+" ,adminFlag: " +c0.getInt(9)+ "\r\n";
							Log.d("seed type: ", log);
							
							if(Global.WriteToSD==true)
							{
							File_Log_Create("value.txt","seed type table \r\n");
							File_Log_Create("value.txt",log);
							}
				} while (c0.moveToNext());

			}
			c0.close();
			mDb.close();
		}

		return mAllSeeds;
	}

	public User getUserById(int userId) {
		mDb.open();
		User tmpUser = null;

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG},
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = " + userId, null,
				null, null, null);

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();

		//	tmpUser = new User(userId, c.getString(0), c.getString(1),
		//			c.getString(2), c.getString(3));
			
			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2),c.getString(3),c.getInt(4),c.getInt(5));
		}

		c.close();
		mDb.close();

		return tmpUser;

	}

	public User getUserByMobile(String deviceId) {

		mDb.open();

		User tmpUser = null;
		String mobile;

		if (deviceId == null)
			mobile = RealFarmDatabase.DEFAULT_NUMBER;
		else
			mobile = deviceId;

		Cursor c = mDb
				.getEntries(RealFarmDatabase.TABLE_NAME_USER, new String[] {
						RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG},

						RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '"
								+ mobile + "'", null, null, null, null);

		if (c.getCount() > 0) { // user exists in database
			c.moveToFirst();

		//	tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
		//			mobile, c.getString(3));
			
			tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
					mobile, c.getString(3), c.getInt(4),c.getInt(5));
		}
		c.close();
		mDb.close();

		return tmpUser;
	}

	public List<User> getUserList() {
		// No caching, the number of users will be potentially large, and the
		// usage now doesn't require them all in memory.

		// opens the database.
		List<User> userList = new LinkedList<User>();

		mDb.open();

		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMG,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG }, null,
				null, null, null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				userList.add(new User(c.getInt(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getInt(5),c.getInt(6)));
				
				

				String log = "Id: "
						+ c.getString(0)
						+ " ,FirstName: " 
						+ c.getString(1) + " ,LastName: " + c.getString(2)
						+ "Mobile: " + c.getString(3) + " ,Img: "
						+ c.getString(4) + " ,DeleteFlag: " + c.getInt(5)
						+ " ,AdminFlag: " + c.getInt(6) + "\r\n";
				Log.d("user: ", log);
				
				if(Global.WriteToSD==true)
				{
					
					
				File_Log_Create("value.txt","User table \r\n");
				File_Log_Create("value.txt",log);
				}
			} while (c.moveToNext());
		}
		

		c.close();
		mDb.close();
		return userList;
	}

	/**
	 * 
	 * @return integer number of users in the DB
	 */
	public int getUserCount() {
		mDb.open();
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID }, null,
				null, null, null, null);
		int userCount = c.getCount();
		c.close();
		mDb.close();
		return userCount;
	}

	public long logAction(String name, String value) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				RealFarmDatabase.DATE_FORMAT);

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_NAME, name);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_DATE,
				formatter.format(new Date()));

		mDb.open();

		long result = mDb
				.insertEntriesdb(RealFarmDatabase.TABLE_NAME_LOG, args);

		mDb.close();

		return result;

	}

	public long removeAction(int id) {
		mDb.open();

		long result = mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + id, null);

		mDb.close();
		return result;
	}

	public long removePoint(int plotId, int lat, int lon) {

		mDb.open();

		long result = mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_POINT,
				RealFarmDatabase.COLUMN_NAME_POINT_X + "=" + lat + " and "
						+ RealFarmDatabase.COLUMN_NAME_POINT_Y + "=" + lon
						+ " and " + RealFarmDatabase.COLUMN_NAME_POINT_PLOTID
						+ " = " + plotId, null);

		mDb.close();
		return result;

	}

	public long setAction(int actionNameID, int growingID, String date) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID, growingID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, actionNameID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, date);

		mDb.open();

		long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDb.close();

		return result;
	}

	public long setGrowing(int plotId, int seedId) {
		mDb.open();

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID, seedId);

		long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_GROWING,
				args);

		mDb.close();
		return result;
	}

	public long setPlot(int userID) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userID);

		mDb.open();

		// add to plot list
		long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_PLOT,
				args);

		mDb.close();
		return result;

	}

	public long setPoint(int plotID, int lat, int lon) {

		ContentValues pointstoadd = new ContentValues();
		pointstoadd.put(RealFarmDatabase.COLUMN_NAME_POINT_X, lat);
		pointstoadd.put(RealFarmDatabase.COLUMN_NAME_POINT_Y, lon);
		pointstoadd.put(RealFarmDatabase.COLUMN_NAME_POINT_PLOTID, plotID);

		mDb.open();

		// add to points list
		long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_POINT,
				pointstoadd);

		mDb.close();

		return result;

	}
	
	
public List<PlotNew> getAllPlotList() {
		
		
		// opens the database.
		List<PlotNew> plotList = new LinkedList<PlotNew>();
		
		mDb.open();

		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMG,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_MAINCROP,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG},
				null, null, null, null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			do {
				plotList.add( new PlotNew(c.getInt(0), c.getInt(1),
									   c.getString(2), c.getString(3), c.getString(4), c.getInt(5)));
				
				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "                                       //Prakruthi
						+ c.getInt(1) + " ,PlotImage: " + c.getString(2)
						+ "SoilType: " + c.getString(3)+ " ,MainCrop: " + c.getString(4) 
						+ " ,AdminFlag: " + c.getInt(5);
				Log.d("values: ", log);
			} while (c.moveToNext());
		}

		c.close();
		mDb.close();
		return plotList;
	}
	
	public long setPlotNew(String PlotImage, String SoilType, String MainCrop) {

		
		System.out.println("SETPLOTNEW");
		ContentValues args = new ContentValues();
		Global.plotId++;
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMG, PlotImage);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,SoilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_MAINCROP, MainCrop);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG, 0);

		long result;

	//	User user = getUserByMobile(deviceId);

		mDb.open();

		//if (user != null) { // user exists in database => update
		//	result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		//			RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
		//					+ deviceId + "'", null);
		//} else { // user must be created
			//result = mDb
			//		.update(RealFarmDatabase.TABLE_NAME_PLOT, args,null,null);
			
			 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_PLOT,
					args);
		//}



		mDb.close();

		return result;
	}
	

	public long setUserInfo(String deviceId, String firstname, String lastname) {

		
		getUserCount();
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ID, (getUserCount())+1);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 0);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG, 0);

		long result;

		User user = getUserByMobile(deviceId);

		mDb.open();

		if (user != null) { // user exists in database => update
			result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
					RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
							+ deviceId + "'", null);
		} else { // user must be created
			result = mDb
					.insertEntriesdb(RealFarmDatabase.TABLE_NAME_USER, args);
		}

		// if main id is undefined and result is good
		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1))
			RealFarmDatabase.MAIN_USER_ID = (int) result;

		mDb.close();

		return result;
	}

	public long updatePoint(int plotID, int lat, int lon, int newLat, int newLon) {

		long result = 0;

		// find point id
		mDb.open();

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_POINT,
				new String[] { RealFarmDatabase.COLUMN_NAME_POINT_ID },
				RealFarmDatabase.COLUMN_NAME_POINT_X + "=" + lat + " and "
						+ RealFarmDatabase.COLUMN_NAME_POINT_Y + "=" + lon,
				null, null, null, null);

		// if there is such point in the table, update
		if (c0.getCount() > 0) {

			c0.moveToFirst();

			int pointID = c0.getInt(0); // pointID to modify

			ContentValues pointtoupdate = new ContentValues();
			pointtoupdate.put(RealFarmDatabase.COLUMN_NAME_POINT_X, newLat);
			pointtoupdate.put(RealFarmDatabase.COLUMN_NAME_POINT_Y, newLon);
			pointtoupdate
					.put(RealFarmDatabase.COLUMN_NAME_POINT_PLOTID, plotID);

			result = mDb.update(RealFarmDatabase.TABLE_NAME_POINT,
					pointtoupdate, RealFarmDatabase.COLUMN_NAME_POINT_ID
							+ " = " + pointID, null);

		}
		c0.close();
		mDb.close();
		return result;
	}

	public long setDeleteFlagForUser(int userid) {

		System.out.println("in setDeleteFlagForUser ");
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 1);

		long result;

		mDb.open();

		result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = '"
						+ userid + "'", null);
		//result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
			//	RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " = '"
			//			+ firstname +   " AND " + RealFarmDatabase.COLUMN_NAME_USER_LASTNAME + " = '"
			//			+ lastname , null);

		mDb.close();
		System.out.println(result);
		return result;
	}

	// Get WF data

	public List<WFList> getWFData() {
		List<WFList> tmpList;

		// opens the database.
		mDb.open();
		Log.d("done: ", "in Wf getdata");
		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_WEATHERFORECAST,
				new String[] { RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE1,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE1,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE1,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG },
				null, null, null, null, null);
		c.moveToFirst();

		tmpList = new LinkedList<WFList>();

		if (c.getCount() > 0) {
			do {
				tmpList.add(new WFList(c.getString(1), c.getInt(2), c
						.getString(3), c.getString(4), c.getInt(5), c
						.getString(6), c.getInt(7)));

				String log = "WF_ID: " + c.getInt(0) + " ,WF_date "
						+ c.getString(1) + " ,WF_values: " + c.getInt(2)
						+ " ,WF_type" + c.getString(3) + "WF_date1 "
						+ c.getString(4) + " WF_value1 " + c.getInt(5)
						+ " WF_type11 " + c.getString(6);
				Log.d("WF values: ", log);

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished Wf getdata");
		c.close();
		mDb.close();

		return tmpList;
	}

	public abstract interface OnDataChangeListener {
		public abstract void onDataChanged(int WF_Size, String WF_Date,
				int WF_Value, String WF_Type, String WF_Date1, int WF_Value1,
				String WF_Type1, int WF_adminflag);
	}

	private static OnDataChangeListener wpDataListener;

	public void setWFDataChangeListener(OnDataChangeListener listener) {
		wpDataListener = listener;
	}

	public long setWFData(int WF_Size, String WF_Date, int WF_Value,
			String WF_Type, String WF_Date1, int WF_Value1, String WF_Type1,
			int WF_adminflag) {

		ContentValues args = new ContentValues();
		Log.d("WF values: ", "before");
		// Context context = null;
		// mDataProvider = RealFarmProvider.getInstance(context);
		maxWF = WF_Size; // mDataProvider.getWFData().size();
		Log.d("WF values: ", "in setdata");
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID, maxWF);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE, WF_Date);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE, WF_Value);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE, WF_Type);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE1, WF_Date1);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_VALUE1, WF_Value1);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE1, WF_Type1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ADMINFLAG,
				WF_adminflag);
		mDb.open();

		long result = mDb.insertEntriesdb(
				RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args);

		mDb.close();

		// notifies any listener that the data changed.
		if (wpDataListener != null) {
			wpDataListener.onDataChanged(WF_Size, WF_Date, WF_Value, WF_Type,
					WF_Date1, WF_Value1, WF_Type1, WF_adminflag);
		}
		Log.d("done: ", "wf setdata");
		return result;
	}
	
	public List<Action> getActions(int userId, int plotId) {                                           //modified

		// seeds are not in cache
		//if (mAllActions == null) {
			mAllActions = new LinkedList<Action>();
			
			
			mDb.open();       

			Cursor ca = mDb
					.getEntries(
							RealFarmDatabase.TABLE_NAME_ACTION,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_ACTION_ID,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
									RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
									RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
									RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
									RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
									RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
									RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
									RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
									RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
									RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
									RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
									RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
									RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE
												},RealFarmDatabase.COLUMN_NAME_ACTION_USERID + "= " +
													userId + " AND " + RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + "= " +
													plotId + "", null, null, null, null);

			if (ca.getCount() > 0) {
				ca.moveToFirst();
				do {
					Action aa = new Action(ca.getInt(0), ca.getInt(1),
							ca.getInt(2), ca.getString(3),
							ca.getString(4),ca.getInt(5),
							ca.getInt(6),ca.getString(7),
							ca.getString(8),ca.getInt(9),
							ca.getInt(10),ca.getString(11),
							ca.getString(12),
							ca.getString(13),ca.getInt(14),
							ca.getString(15), ca.getString(16),
							ca.getInt(17),ca.getInt(18),
							ca.getString(19),ca.getString(20),
							ca.getString(21));
					mAllActions.add(aa);
					
				String log = "ACTION_ID: " + ca.getInt(0) + " ,ACTIONNAMEID: "                   
						+ ca.getInt(1) + " ,GROWINGID " +ca.getInt(2)
							+ ",ACTIONTYPE: " +ca.getString(3) + " ,SEEDVARIETY: " +ca.getString(4)
							+ " Quantity1: " 
						+ca.getInt(5)+ " Quantity2: " 
								+ca.getInt(6)+  " Units: " 
								+ca.getString(7)+" , DAY: " +ca.getString(8)+ " , user id: " +ca.getInt(9)
						+ " , plot id: " +ca.getInt(10)+ " ,TYPEOFFERTILIZER " +ca.getString(11)
						+ " , PROBLEMStype: " +ca.getString(12)+ " , , FEEDBACK: " +ca.getString(13)+ 
						" , SELLINGPRICE: " +ca.getInt(14)
						+ " ,QUALITYOFSEED: " +ca.getString(15)+ " , sell TYPE: " +ca.getString(16)
						            + " , SENT: " +ca.getInt(17)
                            + " , ISADMIN: " +ca.getInt(18)+ " , ACTIONPERFORMEDDATE: " +ca.getString(19)
                            + " , TREATMENT: " +ca.getString(20)
                            + " , PESTICIDETYPE: " +ca.getString(21);
						Log.d("values: ", log);
				} while (ca.moveToNext());

			}
			ca.close();
			mDb.close();
	//	}

		return mAllActions;
	}
	
	
	public List<Action> getNewActionsList() {                                           //modified

		// seeds are not in cache
		//if (mAllActions == null) {
			mAllActions = new LinkedList<Action>();
			
			
			mDb.open();       

			Cursor ca = mDb
					.getAllEntries(
							RealFarmDatabase.TABLE_NAME_ACTION,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_ACTION_ID,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
									RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
									RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
									RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
									RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
									RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
									RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
									RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
									RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
									RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
									RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
									RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
									RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
									RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
									RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
									RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE
												});

			if (ca.getCount() > 0) {
				ca.moveToFirst();
				do {
					Action aa = new Action(ca.getInt(0), ca.getInt(1),
							ca.getInt(2), ca.getString(3),
							ca.getString(4),ca.getInt(5),
							ca.getInt(6),ca.getString(7),
							ca.getString(8),ca.getInt(9),
							ca.getInt(10),ca.getString(11),
							ca.getString(12),
							ca.getString(13),ca.getInt(14),
							ca.getString(15), ca.getString(16),
							ca.getInt(17),ca.getInt(18),
							ca.getString(19),ca.getString(20),
							ca.getString(21));
					mAllActions.add(aa);
					
				String log = "ACTION_ID: " + ca.getInt(0) + " ,ACTIONNAMEID: "                   
						+ ca.getInt(1) + " ,GROWINGID " +ca.getInt(2)
							+ ",ACTIONTYPE: " +ca.getString(3) + " ,SEEDVARIETY: " +ca.getString(4)
							+ " Quantity1: " 
						+ca.getInt(5)+ " Quantity2: " 
								+ca.getInt(6)+  " Units: " 
								+ca.getString(7)+" , DAY: " +ca.getString(8)+ " , user id: " +ca.getInt(9)
						+ " , plot id: " +ca.getInt(10)+ " ,TYPEOFFERTILIZER " +ca.getString(11)
						+ " , PROBLEMStype: " +ca.getString(12)+ " , , FEEDBACK: " +ca.getString(13)+ 
						" , SELLINGPRICE: " +ca.getInt(14)
						+ " ,QUALITYOFSEED: " +ca.getString(15)+ " , sell TYPE: " +ca.getString(16)
						            + " , SENT: " +ca.getInt(17)
                            + " , ISADMIN: " +ca.getInt(18)+ " , ACTIONPERFORMEDDATE: " +ca.getString(19)
                            + " , TREATMENT: " +ca.getString(20)
                            + " , PESTICIDETYPE: " +ca.getString(21) + "\r\n";
						Log.d("values: ", log);
						
						if(Global.WriteToSD==true)
						{
						File_Log_Create("value.txt","New Action table \r\n");
						File_Log_Create("value.txt",log);
						}
				} while (ca.moveToNext());

			}
			ca.close();
			mDb.close();
	//	}

		return mAllActions;
	}
	
	
	public long setActionNew(int actionid, int actionnameid, int growingid, String actiontype, String Seedvariety,int quantity1, int quantity2,
			 String Units,String day, int userid, int plotid,String TypeFert, String Prob, String feedback, int sp, String QuaSeed,
			String type,int i, int j ,String treat, String pesttype) {
		Global.actionid++;
			
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, -15);
			System.out.println("SETACTIONNEW");
			ContentValues args = new ContentValues();
			
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, actionnameid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID, growingid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,actiontype);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY, Seedvariety);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, quantity2);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, userid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER, TypeFert);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, Prob);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK, feedback);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sp);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaSeed);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, type);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, i);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, j);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE,pesttype);

			long result;

		//	User user = getUserByMobile(deviceId);

			mDb.open();

			//if (user != null) { // user exists in database => update
			//	result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
			//			RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
			//					+ deviceId + "'", null);
			//} else { // user must be created
				//result = mDb
				//		.update(RealFarmDatabase.TABLE_NAME_PLOT, args,null,null);
				
				 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
						args);
			//}



			mDb.close();

			return result;
		}
	
	public long setSowing(int qua1,String Seedvariety,String Units,String day, String treat, int sent, int admin) {

			
		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		
			System.out.println("SET SOWING");
			ContentValues args = new ContentValues();
			
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,3);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,"Sowing");
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,qua1);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY, Seedvariety);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);
			

			long result;

			mDb.open();

				 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
						args);
			



			mDb.close();

			return result;
		}
	
	public  List<Sowing> getsowing() {

		mDb.open();
		int sow=3;

		List<Sowing> tmpList;

		Cursor c = mDb
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_SEEDVARIETY,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT },
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
									+ sow , null, null, null, null);

		
	//	
		c.moveToFirst();
		tmpList = new LinkedList<Sowing>();
		System.out.println(c.getCount());

		 if (c.getCount() > 0) {
			do {
				tmpList.add(new Sowing(c.getInt(0) ,c.getString(1),c.getInt(2),c
						.getString(3), c.getString(4), c.getString(5),c.getInt(6),
						c.getInt(7),c.getInt(8),c.getInt(9),
						c.getString(10), c.getString(11)));

				String log = "action id: " + c.getInt(0)+ " ,Action type: " + c.getString(1) + " ,QUANTITY1: " + c.getInt(2) 
						+ " ,seed variety: " + c.getString(3)
						+ " ,units" + c.getString(4) + "day "
						+ c.getString(5) + " user id " + c.getInt(6)
						+ " plot id " + c.getInt(7)+ "sent  " + c.getInt(8)
					+ " Is admin " + c.getInt(9)+ " action performed date " + c.getString(10)
					+ " treatment " + c.getString(11) + "\r\n";
				Log.d("sowing values: ", log);
				
				if(Global.WriteToSD==true)
				{
				File_Log_Create("value.txt","Sowing action \r\n");
				File_Log_Create("value.txt",log);
				}

			} while (c.moveToNext());
			
		}
		c.close();
		mDb.close();

		return tmpList;
	}
	
	public  List<Fertilizing> getfertizing() {

		mDb.open();
		int fert=4;

		List<Fertilizing> tmpList;

		Cursor c = mDb
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE },
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
									+ fert , null, null, null, null);

		
	//	
		c.moveToFirst();
		tmpList = new LinkedList<Fertilizing>();
		System.out.println(c.getCount());

		 if (c.getCount() > 0) {
			do {
				tmpList.add(new Fertilizing(c.getInt(0),c.getString(1),c.getInt(2), c
						.getString(3), c.getString(4), c.getString(5),c.getInt(6),
						c.getInt(7),c.getInt(8),c.getInt(9),
						c.getString(10)));

				String log = "action id: " + c.getInt(0)  + " ,Action type: " + c.getString(1)+ " ,quantity1: " + c.getInt(2) + " ,Type of fertilize: " 
				+ c.getString(3)
						+ " ,units" + c.getString(4) + "day "
						+ c.getString(5) + " user id " + c.getInt(6)
						+ " plot id " + c.getInt(7)+ "sent  " + c.getInt(8)
					+ " Is admin " + c.getInt(9)+ " action performed date " + c.getString(10) + "\r\n"
					 ;
				Log.d("Fertilizing values: ", log);
				
				if(Global.WriteToSD==true)
				{
				File_Log_Create("value.txt","fertilizing action \r\n");
				File_Log_Create("value.txt",log);
				}

			} while (c.moveToNext());
			
		}
		c.close();
		mDb.close();

		return tmpList;
	}
	
	public long setFertilizing(int qua1,String TypeofFert,String Units,String day, int sent, int admin) {

		
		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		
			System.out.println("SET fertilizing");
			ContentValues args = new ContentValues();
			
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,4);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Fertilizing");
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,qua1);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER, TypeofFert);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
			
			

			long result;

			mDb.open();

				 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
						args);
			



			mDb.close();

			return result;
		}
		
	
	public  List<Spraying> getspraying() {

		mDb.open();
		int spray=5;

		List<Spraying> tmpList;

		Cursor c = mDb
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTION_ID,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
								RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
								RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
								RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
								RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
								RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,
								RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE },
								RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
									+ spray , null, null, null, null);

		
	//	
		c.moveToFirst();
		tmpList = new LinkedList<Spraying>();
		System.out.println(c.getCount());

		 if (c.getCount() > 0) {
			do {
				tmpList.add(new Spraying(c.getInt(0),c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4),c.getInt(5),
						c.getInt(6),c.getString(7),c.getInt(8),
						c.getInt(9), c.getString(10),c.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,action type" + c.getString(1) + " ,quantity1" + c.getString(2)
						
						+ " ,units" + c.getString(3) + "day "
						+ c.getString(4) + " user id " + c.getInt(5)
						+ " plot id " + c.getInt(6)+ " ,Problem type" + c.getString(7)
						+ "sent  " + c.getInt(8)
					+ " Is admin " + c.getInt(9)+ " action performed date " + c.getString(10)
					+ " Pesticide type " + c.getString(11) + "\r\n";
				Log.d("spraying values: ", log);
				
				if(Global.WriteToSD==true)
				{
				File_Log_Create("value.txt","spraying action \r\n");
				File_Log_Create("value.txt",log);
				}

			} while (c.moveToNext());
			
		}
		c.close();
		mDb.close();

		return tmpList;
	}
	
public long setspraying(int quantity1,String Units,String day,String probtype, int sent, int admin, String pesttype) {

		
		Global.actionid++;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		
			System.out.println("SET spraying");
			ContentValues args = new ContentValues();
			
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Spraying");
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,5);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,quantity1);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probtype);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE, pesttype);
			

			long result;

			mDb.open();

				 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
						args);
			



			mDb.close();

			return result;
		}
		
public long setHarvest(int qua1,int qua2,String Units,String day, String harvfeedback, int sent, int admin) {   //day takes the date

	
	Global.actionid++;
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	Calendar calendar = Calendar.getInstance();
	
		System.out.println("SET harvest");
		ContentValues args = new ContentValues();
		
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,8);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE, "Harvesting");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK, harvfeedback);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
		
		

		long result;

		mDb.open();

			 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
					args);
		



		mDb.close();

		return result;
	}

public  List<Harvesting> getharvesting() {

	mDb.open();
	int harv=8;

	List<Harvesting> tmpList;

	Cursor c = mDb
			.getEntries(
					RealFarmDatabase.TABLE_NAME_ACTION,
					new String[] {
							RealFarmDatabase.COLUMN_NAME_ACTION_ID,
							 RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
							RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
							RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
							RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
							RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
							RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
							RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
							RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
							RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
							RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE
							 },
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ harv , null, null, null, null);

	
//	
	c.moveToFirst();
	tmpList = new LinkedList<Harvesting>();
	System.out.println(c.getCount());

	 if (c.getCount() > 0) {
		do {
			tmpList.add(new Harvesting(c.getInt(0),c.getString(1), c
					.getInt(2), c.getInt(3), c.getString(4),c.getString(5),
					c.getInt(6),c.getInt(7),c.getString(8),
					c.getInt(9), c.getInt(10), c.getString(11)));

			String log = "action id: " + c.getInt(0)+ " ,action type: " + c.getString(1)
					+ " ,quantity1: " + c.getInt(2)
					+ " ,quantity2" + c.getInt(3) + "units "
					+ c.getString(4) + " day " + c.getString(5)+ " user  id " + c.getInt(6)
					+ " plot id " + c.getInt(7)+ " harvest feedback " + c.getString(8)
					+ "sent  " + c.getInt(9)
				+ " Is admin " + c.getInt(10)+ " action performed date " + c.getString(11) + "\r\n";
				;
			Log.d("harvesting values: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","harvesting action \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
		
	}
	c.close();
	mDb.close();

	return tmpList;
}

public  List<Selling> getselling() {

	mDb.open();
	int sell=12;

	List<Selling> tmpList;

	Cursor c = mDb
			.getEntries(
					RealFarmDatabase.TABLE_NAME_ACTION,
					new String[] {
							RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						 RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,
						 RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
							RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
							RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
							RealFarmDatabase.COLUMN_NAME_ACTION_DAY,
							RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
							RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
							RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
							RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
							RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
							RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
							RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE
						},
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
								+ sell , null, null, null, null);

	
//	
	c.moveToFirst();
	tmpList = new LinkedList<Selling>();
	System.out.println(c.getCount());

	 if (c.getCount() > 0) {
		do {
			tmpList.add(new Selling(c.getInt(0),c.getString(1), c
					.getInt(2), c.getInt(3), c.getString(4),c.getString(5),
					c.getInt(6),c.getInt(7),c.getInt(8),
					c.getString(9), c.getString(10) , c.getInt(11)
					, c.getInt(12), c.getString(13)));

			String log = "action id: " + c.getInt(0)+ " ,action type: " + c.getString(1)
					+ " ,quantity1: " + c.getInt(2)+ " ,quantity2: " + c.getInt(3)
					+ " ,units" + c.getString(4) + "day "
					+ c.getString(5) + " user id " + c.getInt(6)
					+ " plot id " + c.getInt(7)+ " selling price " + c.getInt(8)
					+ " quality of seed " + c.getString(9) + " selling type " + c.getString(10)
					+ "sent  " + c.getInt(11)
				+ " Is admin " + c.getInt(12)+ " action performed date " + c.getString(13) + "\r\n"
				;
			Log.d("selling values: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","selling a \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
		
	}
	c.close();
	mDb.close();

	return tmpList;
}


public long setselling(int qua1,int qua2,String Units,String day, int sellingprice, String QuaOfSeed, String selltype, 
		int sent, int admin) {   //day takes the date

	
	Global.actionid++;
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	Calendar calendar = Calendar.getInstance();
	
		System.out.println("SET selling");
		ContentValues args = new ContentValues();
		
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, Global.actionid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,12);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPE,"Selling");
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DAY, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, Global.userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, Global.plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sellingprice);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaOfSeed);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, selltype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONPERFORMEDDATE,dateFormat.format(calendar.getTime()));  
		
		

		long result;

		mDb.open();

			 result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
					args);
		



		mDb.close();

		return result;
	}
public List<MarketPrice> getMarketPrice() {
	List<MarketPrice> tmpList;

	// opens the database.
	mDb.open();
	Log.d("done: ", "in market price");
	// query all actions
	Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_MARKETPRICE,
			new String[] { RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID,
					RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE,
					RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE,
					RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE,
					RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG
					},
			null, null, null, null, null);
	c.moveToFirst();

	tmpList = new LinkedList<MarketPrice>();

	if (c.getCount() > 0) {
		do {
			tmpList.add(new MarketPrice(c.getInt(0), c.getString(1), c
					.getString(2), c.getInt(3), c.getInt(4)));

			String log = "MP_ID: " + c.getInt(0) + " ,MP_date "
					+ c.getString(1) + " ,MP_TYPE: " + c.getString(2)
					+ " ,MP_value" + c.getInt(3) + "MP_admin flag "
					+ c.getInt(4) + "\r\n" ;
			Log.d("MP values: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","market price table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}
	Log.d("done: ", "finished MP getdata");
	c.close();
	mDb.close();

	return tmpList;
}

public long setMarketPrice(int id,String date,String type, int value,int adminflag) {

	ContentValues args = new ContentValues();
	args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID,id);
	args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE, date);
	args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE, type);
	args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE, value);
	args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG, adminflag);

	mDb.open();

	long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_MARKETPRICE,
			args);

	mDb.close();

	return result;
}

public void Log_Database_backupdate()
{
	File_Log_Create("value.txt","/******************************************************/");
	File_Log_Create("value.txt","Database backup date for the following tables: \r\n");
	
	DatabaseEntryDate=dateFormat.format(calendar.getTime());
	File_Log_Create("value.txt",DatabaseEntryDate);
	File_Log_Create("value.txt","/******************************************************/");
}

public void File_Log_Create(String FileNameWrite,String Data)
{
	
	// File folder = new File(Environment.getExternalStorageDirectory() + "/csn_app_logs");
//	boolean success = false;
//	if (!folder.exists()) {
//	    success = folder.mkdir();
//	}
	
//	File file = new File(mExternalDirectoryLog, "Test5.txt");       //LoggedData is the file to which values will be written
	if(FileNameWrite=="value.txt")
	{
	mExternalDirectoryLog = Environment.getExternalStorageDirectory().toString()+ LOG_FOLDER;
	//	mExternalDirectoryLog = Environment.getExternalStorageDirectory().toString();
	 file = new File(mExternalDirectoryLog, FileNameWrite); 
	// fWriter =new FileWriter(file);
	

    try {
    	fWriter =new FileWriter(file,true);
    	fWriter.append(Data);
   // 	fWriter.newLine();
        fWriter.close();
   //     Toast.makeText(this, "Your text was written to SD Card succesfully...", Toast.LENGTH_LONG).show();
	} catch (Exception e) {
		Log.e("WRITE TO SD", e.getMessage());
	}
    
	}
    if(FileNameWrite=="UIlog.txt")
	{
	mExternalDirectoryLog = Environment.getExternalStorageDirectory().toString()+ LOG_FOLDER;
	//	mExternalDirectoryLog = Environment.getExternalStorageDirectory().toString();
	 file1 = new File(mExternalDirectoryLog, FileNameWrite); 
	// fWriter =new FileWriter(file);


    try {
    	fWriter1 =new FileWriter(file1,true);
    	fWriter1.append(Data);
   // 	fWriter.newLine();
        fWriter1.close();
   //     Toast.makeText(this, "Your text was written to SD Card succesfully...", Toast.LENGTH_LONG).show();
	} catch (Exception e) {
		Log.e("WRITE TO SD", e.getMessage());
	}
	}
    
	
}

public void getSeedTypeStages() {

	//List<User> tmpUsers = new ArrayList<User>();
	mDb.open();
	// User tmpUser = null;

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPESTAGE,
			new String[] { RealFarmDatabase. COLUMN_NAME_SEEDTYPESTAGE_STAGEID,
					RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_SEEDTYPEID,
					RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_FROMCOUNTDAYS, 
					RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_TOCOUNTDAYS,
					RealFarmDatabase.COLUMN_NAME_SEEDTYPESTAGE_ADMINFLAG});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
		//	tmpUsers.add(new User(c.getInt(0), c.getInt(1), c
		//			.getInt(2), c.getInt(3)));
			
			String log = "STAGEID: " + c.getInt(0) + " ,SEEDTYPEID: "
					+ c.getInt(1) + " ,FROMCOUNTDAYS: " + c.getInt(2)
					+ ",TOCOUNTDAYS: " + c.getInt(3)+ ",ADMINFLAG: " + c.getInt(4)+ "\r\n";
			Log.d("seed type stages: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Seed type stages table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
	c.close();
	mDb.close();
	
}

public void getUnit() {

	
	mDb.open();
	
	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_UNIT,
			new String[] { RealFarmDatabase.COLUMN_NAME_UNIT_ID,
					RealFarmDatabase.COLUMN_NAME_UNIT_NAME,RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO,
					RealFarmDatabase.COLUMN_NAME_UNIT_ADMINFLAG
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
		
			String log = "UNIT_ID: " + c.getInt(0) + " ,UNIT_NAME: "
					+ c.getString(1)+ " ,UNIT_AUDIO: "
							+ c.getInt(2)+ " ,UNIT_ADMIN FLAG: "
									+ c.getInt(3) + "\r\n" ;
					
			Log.d("Unit: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Unit table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
	c.close();
	mDb.close();

	
}

public void getLog() {

	
	mDb.open();
	// User tmpUser = null;

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_LOG,
			new String[] { RealFarmDatabase.COLUMN_NAME_LOG_ID,
					RealFarmDatabase.COLUMN_NAME_LOG_NAME,
					RealFarmDatabase.COLUMN_NAME_LOG_VALUE,RealFarmDatabase.COLUMN_NAME_LOG_ADMINFLAG});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			
			
			String log = "LOG_ID: " + c.getInt(0) + " ,LOG_NAME: "
					+ c.getString(1) + " ,LOG_VALUE: " + c.getInt(2)+ " ,LOG_ADMINFLAG: " + c.getInt(3)
					+ "\r\n";
			Log.d("values: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Log table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
	c.close();
	mDb.close();

	
}

public List<Growing> getGrowings() {
	mDb.open();

	List<Growing> growing = new ArrayList<Growing>();

	Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
			new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
					RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID,
					RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID,RealFarmDatabase.COLUMN_NAME_GROWING_SOWINGDATE,RealFarmDatabase.COLUMN_NAME_GROWING_ADMINFLAG},
			null,
			null, null, null, null);

	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			growing.add(new Growing(c.getInt(0), c.getInt(1), c.getInt(2)));
			
			String log = "Growing id: " + c.getInt(0) + " ,Plot id: "                   
					+ c.getInt(1) + " ,seed id: " +c.getInt(2)+ " ,SOWINGDATE: " +c.getInt(3)
					+ " ,adminFlag: " +c.getInt(4) + "\r\n";
					Log.d("growings: ", log);
					
					if(Global.WriteToSD==true)
					{
					File_Log_Create("value.txt","Growing table \r\n");
					File_Log_Create("value.txt",log);
					}
			
		} while (c.moveToNext());
	}
	c.close();
	mDb.close();
	return growing;

}

public Cursor getFertilizer() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_FERTILIZER,
			new String[] { RealFarmDatabase.COLUMN_NAME_FERTILIZER_ID,
					RealFarmDatabase.COLUMN_NAME_FERTILIZER_NAME,
					RealFarmDatabase.COLUMN_NAME_FERTILIZER_AUDIO, 
					RealFarmDatabase.COLUMN_NAME_FERTILIZER_STAGEID,
					RealFarmDatabase.COLUMN_NAME_FERTILIZER_UNITID,
					RealFarmDatabase.COLUMN_NAME_FERTILIZER_ADMINFLAG,
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ID: " + c.getInt(0) + " ,NAME: "
					+ c.getString(1) + " AUDIO: " + c.getInt(2)
					+ "STAGEID: " + c.getInt(3)+ " UNITID: " + c.getInt(4) + " ADMINFLAG: " + c.getInt(5)+
					"\r\n";
			Log.d("fertilizer: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Fertilizer table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}

public Cursor getActionTranslation() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_ACTIONTRANSLATION,
			new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_ACTIONNAMEID,
					RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_TARGETIDFIELD,
					RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_TARGETTABLE, 
					RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTIDFIELD,
					RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_INDOBJECTTABLE,
					RealFarmDatabase.COLUMN_NAME_ACTIONTRANSLATION_ADMINFLAG,
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ACTIONNAMEID: " + c.getInt(0) + " ,TARGETIDFIELD: "
					+ c.getString(1) + " ,TARGETTABLE: " + c.getString(2)
					+ ",INDOBJECTIDFIELD: " + c.getString(3)+ " ,INDOBJECTTABLE: " 
					+ c.getString(4) + " ,ADMINFLAG: " + c.getInt(5)+ "\r\n";
			Log.d("action translation: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Action translation table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}

public Cursor getPesticides() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_PESTICIDE,
			new String[] { RealFarmDatabase.COLUMN_NAME_PESTICIDE_ID,
					RealFarmDatabase.COLUMN_NAME_PESTICIDE_NAME,
					RealFarmDatabase.COLUMN_NAME_PESTICIDE_AUDIO, 
					RealFarmDatabase.COLUMN_NAME_PESTICIDE_ADMINFLAG
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ID: " + c.getInt(0) + " ,NAME: "
					+ c.getString(1) + " AUDIO: " + c.getInt(2)
					+ "ADMINFLAG: " + c.getInt(3)+"\r\n";
			Log.d("pesticides: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Pesticides table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}

public Cursor getStages() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_STAGE,
			new String[] { RealFarmDatabase.COLUMN_NAME_STAGE_ID,
					RealFarmDatabase.COLUMN_NAME_STAGE_NAME,
					RealFarmDatabase.COLUMN_NAME_STAGE_ADMINFLAG 
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ID: " + c.getInt(0) + " NAME: "
					+ c.getString(1)
					+ "  ADMINFLAG: " + c.getInt(2) +"\r\n";
			Log.d("values: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Stages table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}
public Cursor getProblems() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_PROBLEM,
			new String[] { RealFarmDatabase.COLUMN_NAME_PROBLEM_ID,
					RealFarmDatabase.COLUMN_NAME_PROBLEM_NAME,
					RealFarmDatabase.COLUMN_NAME_PROBLEM_AUDIO, 
					RealFarmDatabase.COLUMN_NAME_PROBLEM_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_PROBLEM_PROBLEMTYPEID,
					RealFarmDatabase.COLUMN_NAME_PROBLEM_ADMINFLAG
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ID: " + c.getInt(0) + " ,NAME: "
					+ c.getString(1) + " AUDIO: " + c.getInt(2)
					+ "RESOURCE: " + c.getInt(3)+ " PROBLEMTYPEID: "
					+ c.getInt(4) + " ADMINFLAG: " + c.getInt(5)+ "\r\n";
			Log.d("problems: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","Problems table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}

public Cursor getProblemType() {

	
	mDb.open();
	

	Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_PROBLEMTYPE,
			new String[] { RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_ID,
					RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_NAME,
					RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_AUDIO, 
					RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_PROBLEMTYPE_ADMINFLAG
				
					});

	// user exists in database
	if (c.getCount() > 0) {
		c.moveToFirst();
		do {
			// adds the users into the list.
			
			String log = "ID: " + c.getInt(0) + " ,NAME: "
					+ c.getString(1) + " AUDIO: " + c.getInt(2)
					+ "RESOURCE: " + c.getInt(3) + " ADMINFLAG: " + c.getInt(4)+ "\r\n";
			Log.d("problems: ", log);
			
			if(Global.WriteToSD==true)
			{
			File_Log_Create("value.txt","ProblemType table \r\n");
			File_Log_Create("value.txt",log);
			}

		} while (c.moveToNext());
	}

	// closes the DB and the cursor.
//	c.close();
	mDb.close();

	return c;
}


public  List<User> getuserDelete(int delete) {

	mDb.open();
	//int delete=0;
	
	System.out.println("In getuserDelete");

	List<User> tmpList;

	Cursor c = mDb
			.getEntries(
					RealFarmDatabase.TABLE_NAME_USER,
					new String[] {
							RealFarmDatabase.COLUMN_NAME_USER_ID,
							RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
							RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
							RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
							RealFarmDatabase.COLUMN_NAME_USER_IMG,
							RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG},
							RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG + "="
								+ delete , null, null, null, null);

	
//	
	c.moveToFirst();
	tmpList = new LinkedList<User>();
	System.out.println(c.getCount());

	 if (c.getCount() > 0) {
		do {
			tmpList.add(new User(c.getInt(0) ,c.getString(1),c.getString(2),c
					.getString(3), c.getString(4),delete, c.getInt(5)));

			String log = "user id: " + c.getInt(0)+ " ,First name: " + c.getString(1) + " ,Last name: " + c.getString(2) 
					+ " ,Mobile: " + c.getString(3)
					+ " ,Image" + c.getString(4)  + " delete flag " + delete
					+ "Admin flag " + c.getInt(5)+ "\r\n";
			Log.d("sowing values: ", log);
			
			

		} while (c.moveToNext());
		
	}
	c.close();
	mDb.close();

	return tmpList;
}
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

