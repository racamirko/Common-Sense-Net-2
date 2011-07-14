package com.commonsensenet.realfarm.dataaccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

import com.commonsensenet.realfarm.realFarmMainActivity;
import com.commonsensenet.realfarm.overlay.Polygon;

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

	private String[] getUserName(int userId) {
		String[] name = new String[2];

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME },
				RealFarmDatabase.COLUMN_NAME_USER_ID + "= '" + userId + "'",
				null, null, null, null);

		if (c.getCount() > 0) { // user exists in database
			c.moveToFirst();

			name[0] = c.getString(0);
			name[1] = c.getString(1);
		}

		return name;

	}

	/**
	 * Get first/last name of owner of a plot
	 * 
	 * @param plotId
	 * @return
	 */
	public String[] getPlotOwner(int plotId) {

		String[] name = new String[2];
		int userId = 0;

		// opens the db.
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

		name = getUserName(userId);
		mDb.close();

		return name;

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

	public int[][] getPlotSeed(int plotId) {

		int[][] res = null;

		// opens the db.
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

	public String getActionName(int actionId) {

		mDb.open();

		String name = null;

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME },
				RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "=" + actionId,
				null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			name = c0.getString(0);

		}

		mDb.close();

		return name;
	}

	public int getLastAction(int growingId) {

		int l = 0;

		// opens the db.
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

	public int getUserId(String deviceID) {

		int userID = 0;
		String mobileNumber = null;
		if (deviceID == null)
			mobileNumber = RealFarmDatabase.DEFAULT_NUMBER;
		else
			mobileNumber = deviceID;

		mDb.open();

		Cursor c0 = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID },
				RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '" + mobileNumber
						+ "'", null, null, null, null);

		if (c0.getCount() > 0) {
			c0.moveToFirst();

			userID = c0.getInt(0);
		}

		mDb.close();
		return userID;
	}
	
	public long removePoint(int plotID, int lat, int lon){
		
		mDb.open();
		
		long result = mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_POINT,
				RealFarmDatabase.COLUMN_NAME_POINT_X + "=" + lat + " and "
						+ RealFarmDatabase.COLUMN_NAME_POINT_Y + "=" + lon, null);
		
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

		//

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

	public List<Polygon> getPlots(int userId) {
		List<Polygon> tmpList = new ArrayList<Polygon>();

		// opens the db.
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

					int[] polyX = new int[c02.getCount()];
					int[] polyY = new int[c02.getCount()];

					do { // for each point in the plot, draw it

						int x1 = c02.getInt(0);
						int y1 = c02.getInt(1);

						polyX[j] = x1;
						polyY[j] = y1;

						j = j + 1;
					} while (c02.moveToNext());

					// adds the polygon to the list.
					tmpList.add(new Polygon(polyX, polyY, polyX.length, id));
				}
				i = i + 1;
			} while (c0.moveToNext());
		}
		mDb.close();

		return tmpList;
	}

	public List<Polygon> getPlots() {
		List<Polygon> tmpList = new ArrayList<Polygon>();

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

					int[] polyX = new int[c02.getCount()];
					int[] polyY = new int[c02.getCount()];

					do { // creates each polygon object using the given points.

						int x1 = c02.getInt(0);
						int y1 = c02.getInt(1);

						polyX[j] = x1;
						polyY[j] = y1;

						j = j + 1;
					} while (c02.moveToNext());

					// adds the polygon to the list.
					tmpList.add(new Polygon(polyX, polyY, polyX.length, id,
							ownerId));
				}
				i = i + 1;
			} while (c0.moveToNext());
		}
		mDb.close();

		return tmpList;
	}

	public void removeAction(int ID) {
		mDb.open();

		mDb.deleteEntriesdb(RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + ID, null);

		mDb.close();
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

	/**
	 * 
	 * 
	 * @return
	 */
	public Map<Integer, String> getActions() {

		// opens the database.
		mDb.open();

		// query all actions
		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_ACTIONNAME,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME }, null,
				null, null, null, null);
		c.moveToFirst();

		Map<Integer, String> tmpMap = new HashMap<Integer, String>();

		int actionId;
		String actionName;

		if (c.getCount() > 0) {
			do {
				actionId = c.getInt(0);
				actionName = c.getString(1);
				tmpMap.put(actionId, actionName);
			} while (c.moveToNext());
		}

		mDb.close();

		return tmpMap;
	}

	public String[] getUserInfo(String deviceID) {
		mDb.open();

		String[] name = new String[2];

		Cursor c = mDb.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME },
				RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '" + deviceID
						+ "'", null, null, null, null);

		if (c.getCount() > 0) { // user exists in database
			c.moveToFirst();

			name[0] = c.getString(0);
			name[1] = c.getString(1);

		}
		mDb.close();

		return name;
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

	public long setUserInfo(String deviceId, String firstname, String lastname) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		long result;
		String[] name = getUserInfo(deviceId);

		mDb.open();

		if (name[0] != null) { // user exists in database => update
			result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
					RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
							+ deviceId + "'", null);
		} else { // user must be created
			result = mDb
					.insertEntriesdb(RealFarmDatabase.TABLE_NAME_USER, args);
		}

		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1)) // if main id
																	// is
																	// undefined
																	// and
																	// result is
																	// good
			RealFarmDatabase.MAIN_USER_ID = (int) result;

		mDb.close();

		return result;
	}

}
