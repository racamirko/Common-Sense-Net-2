package com.commonsensenet.realfarm.dataaccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Point;

import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.Diary;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

public class RealFarmProvider {
	/** Real farm database access. */
	private RealFarmDatabase mDb;
	private boolean seedListReady = false;
	private List<Seed> allSeeds;

	public RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDb = database;

		// used to force creation.
		mDb.open();
		mDb.close();
	}

	public Action getActionById(int actionId) {

		mDb.open();

		Action tmpAction = null;

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME },
				RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "=" + actionId,
				null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			tmpAction = new Action(actionId, c0.getString(0));
		}

		mDb.close();

		return tmpAction;
	}

	public List<Action> getActionsList() {

		// opens the database.
		mDb.open();

		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME }, null,
				null, null, null, null);
		c.moveToFirst();

		List<Action> tmpList = new LinkedList<Action>();

		int actionId;
		String actionName;

		if (c.getCount() > 0) {
			do {
				actionId = c.getInt(0);
				actionName = c.getString(1);
				tmpList.add(new Action(actionId, actionName));
			} while (c.moveToNext());
		}

		mDb.close();

		return tmpList;
	}

	public Diary getDiary(int plotID) {

		Diary mDiary = new Diary();
		
		Plot mPlot = getPlotById(plotID);
		int[] mGrowing = mPlot.getGrowing();
		
		
		mDb.open();

		
		for (int i=0; i<mGrowing.length;i++){
	
			Cursor c02 = mDb
					.getEntries(RealFarmDatabase.TABLE_NAME_ACTION, new String[] {
							RealFarmDatabase.COLUMN_NAME_ACTION_ID,
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONID,
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONDATE },
							RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID + "="
									+ mGrowing[i], null, null, null, null);
	
			if (c02.getCount() > 0) {
	
				c02.moveToFirst();
	//			res = new long[3][c02.getCount()];
	//			int i = 0;
				do {
					
	//				res[0][i] = c02.getInt(0); // ID
	//				res[1][i] = c02.getInt(1); // actionID
	//				String dateString = c02.getString(2); // actionDate
	//
	//				String format = "yyyy-MM-dd HH:mm:ss";
	//				SimpleDateFormat sdf = new SimpleDateFormat(format);
	//
	//				Date date = null;
	//				try {
	//					date = sdf.parse(dateString);
	//				} catch (ParseException e) {
	//					e.printStackTrace();
	//				}
	//
	//				res[2][i] = date.getTime();
					
					mDiary.addItem(c02.getInt(0), c02.getInt(1), c02.getString(2), mGrowing[i]);
					
	//				i = i + 1;
	
				} while (c02.moveToNext());
	
			}
		}
		
		mDb.close();
		return mDiary;

	}

	public Seed getSeedById(int seedId) {

		Seed res = null;
		mDb.open();
		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = new Seed(seedId, c0.getString(0), c0.getString(1));
		}
		mDb.close();
		return res;

	}

	public List<Seed> getSeedsList() {

		if (seedListReady == false) {

			allSeeds = new ArrayList<Seed>();
			mDb.open();

			Cursor c = mDb.getAllEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
					new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY });

			if (c.getCount() > 0) {
				c.moveToFirst();
				do {
					Seed s = new Seed(c.getInt(0), c.getString(1),
							c.getString(2));
					allSeeds.add(s);
				} while (c.moveToNext());

			}
			mDb.close();

			seedListReady = true;
		}

		return allSeeds;
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
					tmpList.add(new Plot(polyPoints, polyPoints.length, id,
							ownerId));
				}
				i = i + 1;
			} while (c0.moveToNext());
		}
		mDb.close();

		return tmpList;
	}

	public List<Plot> getUserPlots(int userId) {
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
					tmpList.add(new Plot(polyPoints, polyPoints.length, id,
							userId));
				}
				i = i + 1;
			} while (c0.moveToNext());
		}
		mDb.close();

		return tmpList;
	}

	public Plot getPlotById(int plotId) {

		mDb.open();
		Plot mPlot = null;
		int ownerId  = 0;
		
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
					new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_USERID},
					RealFarmDatabase.COLUMN_NAME_PLOT_ID + "=" + plotId, null,
					null, null, null);
			
			if (c.getCount()>0){
				c.moveToFirst();
				ownerId = c.getInt(0);
			}
			
			mPlot = new Plot(polyPoints, polyPoints.length, plotId, ownerId);
		}
		mDb.close();

		return mPlot;
	}

	public User getUserById(int userId) {

		User tmpUser = null;

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE },
				RealFarmDatabase.COLUMN_NAME_USER_ID + "= '" + userId + "'",
				null, null, null, null);

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();

			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2));
		}

		return tmpUser;

	}

	public User getUserByMobile(String deviceID) {

		mDb.open();

		User tmpUser = null;
		String mobile;

		if (deviceID == null)
			mobile = RealFarmDatabase.DEFAULT_NUMBER;
		else
			mobile = deviceID;

		Cursor c = mDb
				.getEntries(RealFarmDatabase.TABLE_NAME_USER, new String[] {
						RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME },

						RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '"
								+ mobile + "'", null, null, null, null);

		if (c.getCount() > 0) { // user exists in database
			c.moveToFirst();

			tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
					mobile);

		}
		mDb.close();

		return tmpUser;
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

			Recommendation r = new Recommendation(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getString(3));
			result.add(r);
		}

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

	public long setAction(int actionID, int growingID, String date) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID, growingID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONID, actionID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONDATE, date);

		mDb.open();

		long result = mDb.insertEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDb.close();

		return result;
	}
	
	public void removeAction(int id) {
		mDb.open();

		mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + id, null);

		mDb.close();
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

	

	public long setUserInfo(String deviceId, String firstname, String lastname) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);

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

	
}
