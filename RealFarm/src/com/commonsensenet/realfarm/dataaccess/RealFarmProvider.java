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
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.User;

public class RealFarmProvider {
	/** Real farm database access. */
	private RealFarmDatabase mDb;

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

	public List<Action> getActions() {

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

	public long[][] getDiary(int growingID) {

		mDb.open();

		long[][] res = null;

		Cursor c02 = mDb
				.getEntries(RealFarmDatabase.TABLE_NAME_ACTION, new String[] {
						RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONDATE },
						RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID + "="
								+ growingID, null, null, null, null);

		if (c02.getCount() > 0) {

			c02.moveToFirst();
			res = new long[3][c02.getCount()];
			int i = 0;
			do {

				res[0][i] = c02.getInt(0); // ID
				res[1][i] = c02.getInt(1); // actionID
				String dateString = c02.getString(2); // actionDate

				String format = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(format);

				Date date = null;
				try {
					date = sdf.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				res[2][i] = date.getTime();

				i = i + 1;

			} while (c02.moveToNext());

		}

		mDb.close();
		return res;

	}

	public int getLastAction(int growingId) {

		int l = 0;

		// opens the database
		mDb.open();

		Cursor c0 = mDb
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTION,
						new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID },
						RealFarmDatabase.COLUMN_NAME_ACTION_GROWINGID + "="
								+ growingId, null, null, null, null);
		mDb.close();

		if (c0.getCount() > 0) {

			c0.moveToLast();
			l = c0.getInt(0);

		}

		mDb.close();
		return l;

	}

	/**
	 * Get first/last name of owner of a plot
	 * 
	 * @param plotId
	 * @return
	 */
	public User getPlotOwner(int plotId) {

		User tmpUser;
		int userId = 0;

		// opens the database
		mDb.open();

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_USERID },
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + "=" + plotId, null,
				null, null, null);

		// if there is information about this plot in the table
		if ((!c0.isClosed()) && (c0.getCount() == 1)) {

			// go to the first entry
			c0.moveToFirst();

			// get user name
			userId = c0.getInt(0);
		}

		tmpUser = getUserById(userId);
		mDb.close();

		return tmpUser;

	}

	public List<Plot> getPlots() {
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

	public List<Plot> getPlots(int userId) {
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

	public int[][] getPlotSeed(int plotId) {

		int[][] res = null;

		// opens the database
		mDb.open();

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_GROWING,
				new String[] { RealFarmDatabase.COLUMN_NAME_GROWING_ID,
						RealFarmDatabase.COLUMN_NAME_GROWING_SEEDID },
				RealFarmDatabase.COLUMN_NAME_GROWING_PLOTID + "=" + plotId,
				null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = new int[c0.getCount()][2];
			int i = 0;
			do {
				// get growing+seed id
				int growingID = c0.getInt(0);
				int seedID = c0.getInt(1);
				res[i][0] = growingID;
				res[i][1] = seedID;
				i = i + 1;
			} while (c0.moveToNext()); // there may be intercropping (i.e.,
										// multiple seeds per plot)

		}
		mDb.close();

		return res;

	}

	public String getSeedName(int seedId) {

		String res = null;
		mDb.open();
		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();
			res = c0.getString(0);
		}
		mDb.close();
		return res;

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

	public void removeAction(int id) {
		mDb.open();

		mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + id, null);

		mDb.close();
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

		mDb.close();
		return result;
	}
}
