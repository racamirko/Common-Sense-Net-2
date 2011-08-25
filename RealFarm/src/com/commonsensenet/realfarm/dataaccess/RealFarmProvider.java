package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;

import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.Diary;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

public class RealFarmProvider {
	/** Cached seeds to improve performance. */
	private List<Seed> mAllSeeds;
	/** Real farm database access. */
	private RealFarmDatabase mDb;

	public RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDb = database;

		// used to force creation.
		mDb.open();
		mDb.close();
	}

	public ActionName getActionNameById(int actionNameId) {

		mDb.open();

		ActionName tmpAction = null;

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO },
				RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "=" + actionNameId,
				null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			tmpAction = new ActionName(actionNameId, c0.getString(0), c0.getInt(1),
					c0.getInt(2));
		}
		c0.close();
		mDb.close();

		if (tmpAction == null) {
			Log.d(RealFarmDatabase.DEBUG_ID, "action doesn't exist");
		}

		return tmpAction;
	}

	public List<ActionName> getActionNamesList() {

		// opens the database.
		mDb.open();

		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO }, null,
				null, null, null, null);
		c.moveToFirst();

		List<ActionName> tmpList = new LinkedList<ActionName>();

		if (c.getCount() > 0) {
			do {
				tmpList.add(new ActionName(c.getInt(0), c.getString(1),
						c.getInt(2), c.getInt(3)));
			} while (c.moveToNext());
		}

		c.close();
		mDb.close();

		return tmpList;
	}

	public Diary getDiary(int plotID) {

		Diary mDiary = new Diary();

		List<Growing> mGrowing = getGrowingsByPlotId(plotID);

		mDb.open();

		for (int i = 0; i < mGrowing.size(); i++) {

			Cursor c02 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
					new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
							RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
							RealFarmDatabase.COLUMN_NAME_ACTION_DATE },
					RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID + "="
							+ mGrowing.get(i).getId(), null, null, null, null);

			if (c02.getCount() > 0) {

				c02.moveToFirst();
				do {
					mDiary.addItem(c02.getInt(0), c02.getInt(1),
							c02.getString(2), mGrowing.get(i).getId());

				} while (c02.moveToNext());
			}
			c02.close();
		}

		mDb.close();
		return mDiary;

	}

	public List<Growing> getGrowingsByPlotId(int plotId) {
		mDb.open();

		List<Growing> mGrowing = new ArrayList<Growing>();

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
				new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
						RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID },
				RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID + "=" + plotId,
				null, null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				Growing s = new Growing(c.getInt(0), c.getInt(1), c.getInt(2));
				mGrowing.add(s);
			} while (c.moveToNext());
		}
		c.close();
		mDb.close();
		return mGrowing;

	}

	public Plot getPlotById(int plotId) {

		mDb.open();
		Plot mPlot = null;
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

			mPlot = new Plot(polyPoints, plotId, ownerId);
		}
		c02.close();
		mDb.close();

		return mPlot;
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

			Recommendation r = new Recommendation(c.getInt(0), c.getInt(1),
					c.getInt(2), c.getString(3));
			result.add(r);
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
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = new Seed(seedId, c0.getString(0), c0.getString(1),
					c0.getInt(2), c0.getInt(3), c0.getInt(4), c0.getString(5),
					c0.getString(6));
		}
		c0.close();
		mDb.close();
		return res;

	}

	public List<Seed> getSeedsList() {

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
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA });

			if (c0.getCount() > 0) {
				c0.moveToFirst();
				do {
					Seed s = new Seed(c0.getInt(0), c0.getString(1),
							c0.getString(2), c0.getInt(3), c0.getInt(4),
							c0.getInt(5), c0.getString(6), c0.getString(7));
					mAllSeeds.add(s);
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
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE },
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = " + userId, null,
				null, null, null);

		// user exists in database
		if (c.getCount() > 0) {
			c.moveToFirst();

			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2));
		}

		c.close();
		mDb.close();

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
		c.close();
		mDb.close();

		return tmpUser;
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

	public long logAction(String name, String value) {
		SimpleDateFormat formatter = new SimpleDateFormat(RealFarmDatabase.DATE_FORMAT);
		
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_NAME, name);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_LOG_DATE, formatter.format(new Date()));

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

}
