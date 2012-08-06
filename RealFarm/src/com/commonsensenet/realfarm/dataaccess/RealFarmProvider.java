package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.model.MarketPrice;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;

public class RealFarmProvider {
	public abstract interface OnDataChangeListener {
		public abstract void onDataChanged(String date, int temperature,
				String type);
	}

	/** Date format used throughout the application. */
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static final int NONE = -1;
	/** Map used to handle different instances depending on the context. */
	private static Map<Context, RealFarmProvider> sMapProviders = new HashMap<Context, RealFarmProvider>();

	/** Listener used to detect changes in the weather forecast. */
	private static OnDataChangeListener sWeatherForecastDataListener;

	public static RealFarmProvider getInstance(Context ctx) {
		if (!sMapProviders.containsKey(ctx))
			sMapProviders.put(ctx, new RealFarmProvider(new RealFarmDatabase(
					ctx)));
		return sMapProviders.get(ctx);
	}

	/** Cached ActionTypes to improve performance. */
	private List<Resource> mAllActionTypes;
	/** Cached seeds to improve performance. */
	private List<Resource> mAllSeeds;

	/** Real farm database access. */
	private RealFarmDatabase mDatabase;

	protected RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDatabase = database;

		// used to force creation.
		mDatabase.open();
		mDatabase.close();
	}

	public long addAction(int actionTypeId, int plotId, String date,
			int seedTypeId, int cropTypeId, int quantity1, int quantity2,
			int unit1, int unit2, int resource1Id, int resource2Id, int price,
			int globalId, int isSent, int isAdminAction) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPEID, actionTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
				seedTypeId != NONE ? seedTypeId : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
				cropTypeId != NONE ? cropTypeId : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, quantity2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID,
				unit1 != NONE ? unit1 : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID,
				unit2 != NONE ? unit2 : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID,
				resource1Id != NONE ? resource1Id : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE2ID,
				resource2Id != NONE ? resource2Id : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PRICE,
				price != NONE ? price : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_GLOBALID,
				globalId != NONE ? globalId : null);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISSENT, isSent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
				isAdminAction);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP,
				new Date().getTime());

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long addMarketPrice(String date, int value, String type) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE, type);

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, args);

		mDatabase.close();

		return result;
	}

	// main crop info corresponds to seed type id
	public long addPlot(int userId, int seedTypeId, String imagePath,
			int soilType, double size, int deleteFlag, int adminFlag) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SIZE, size);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID, soilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED, deleteFlag);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION, adminFlag);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
				new Date().getTime());

		mDatabase.open();

		// inserts the values into the database
		long result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				args);

		mDatabase.close();

		return result;
	}

	public long addReportAction(int plotId, int seedTypeId, int problemTypeId,
			String date, int isSent, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_REPORT_ID, plotId, date,
				seedTypeId, getCropIdFromSeedId(seedTypeId), NONE, NONE, NONE,
				NONE, problemTypeId, NONE, NONE, NONE, isSent, isAdminAction);
	}

	public long addSellAction(int plotId, int seedTypeId, int quantity1,
			int quantity2, int unit1, int unit2, int price, String date,
			int isSent, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SELL_ID, plotId, date,
				seedTypeId, getCropIdFromSeedId(seedTypeId), quantity1,
				quantity2, unit1, unit2, NONE, NONE, price, NONE, isSent,
				isAdminAction);
	}

	public long addSowAction(int plotId, int quantity1, int seedTypeId,
			int unit1, String date, int treatmentId, int isSent,
			int isAdminAction, int intercropId) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SOW_ID, plotId, date,
				seedTypeId, getCropIdFromSeedId(seedTypeId), quantity1, NONE,
				unit1, NONE, treatmentId, intercropId, NONE, NONE, isSent,
				isAdminAction);
	}

	public long addSprayAction(int userId, int plotId, int quantity1,
			int unit1, String date, int problemId, int isSent,
			int isAdminAction, int pesticideId) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SELL_ID, plotId, date,
				NONE, NONE, quantity1, NONE, unit1, NONE, problemId,
				pesticideId, NONE, NONE, isSent, isAdminAction);
	}

	/**
	 * Adds a new User to the database. If a user already exists with the same
	 * DEVICEID, the User is updated instead
	 * 
	 * @param deviceId
	 * @param firstname
	 * @param lastname
	 * @param imagePath
	 * @param isEnabled
	 * @param isAdminAction
	 * @return
	 */
	public long addUser(String deviceId, String firstname, String lastname,
			String imagePath, int isEnabled, int isAdminAction) {

		// creates the value container.
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DEVICEID, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISENABLED, isEnabled);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION, isAdminAction);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP,
				new Date().getTime());

		long result;

		// queries the user using the deviceId
		User user = getUserByDeviceId(deviceId);

		mDatabase.open();

		// user exists in database => update
		if (user != null) {
			result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
					RealFarmDatabase.COLUMN_NAME_USER_DEVICEID + " = '"
							+ deviceId + "'", null);
		} else { // user must be created
			result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_USER,
					args);
		}

		// if main id is undefined and result is good
		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1)) {
			RealFarmDatabase.MAIN_USER_ID = (int) result;
		}

		mDatabase.close();

		return result;
	}

	// TODO: check that date is respected.
	public long addWeatherForecast(String date, int value, String type) {

		Log.d("WF values: ", "in setdata");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
				value);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
				type);
		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args);

		mDatabase.close();

		// notifies any listener that the data changed.
		if (sWeatherForecastDataListener != null) {
			sWeatherForecastDataListener.onDataChanged(date, value, type);
		}
		Log.d("done: ", "wf setdata");
		return result;
	}

	public List<Action> getActions() {

		List<Action> tmpActions = new ArrayList<Action>();

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIT2ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE2ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_GLOBALID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISSENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP });

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getInt(5), c.getInt(6),
						c.getInt(7), c.getInt(8), c.getInt(9), c.getInt(10),
						c.getInt(11), c.getInt(12), c.getInt(13), c.getInt(14),
						c.getInt(15), c.getInt(16));
				tmpActions.add(a);

				Log.d("values: ", a.toString());

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	// TODO: sort by date
	public List<Action> getActionsByPlotId(long plotId) {

		List<Action> tmpActions = new ArrayList<Action>();

		mDatabase.open();

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIT2ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE2ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_GLOBALID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISSENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + " = " + plotId
						+ "", null, null, null, null);

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getInt(5), c.getInt(6),
						c.getInt(7), c.getInt(8), c.getInt(9), c.getInt(10),
						c.getInt(11), c.getInt(12), c.getInt(13), c.getInt(14),
						c.getInt(15), c.getInt(16));

				// adds the action to the list.
				tmpActions.add(a);

				Log.d("values: ", a.toString());
			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	// TODO: should order elements properly.
	public List<Action> getActionsByUserId(int userId) {

		List<Action> tmpActions = new ArrayList<Action>();

		mDatabase.open();

		final String RAW_QUERY = "SELECT a.* FROM %s a INNER JOIN %s p ON a.%s = p.%s WHERE p.%s = %d ORDER BY %s DESC";
		String processedQuery = String.format(RAW_QUERY,
				RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.TABLE_NAME_PLOT,
				RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID,
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userId,
				RealFarmDatabase.COLUMN_NAME_ACTION_DATE);

		Cursor c = mDatabase.rawQuery(processedQuery, new String[] {});

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getInt(5), c.getInt(6),
						c.getInt(7), c.getInt(8), c.getInt(9), c.getInt(10),
						c.getInt(11), c.getInt(12), c.getInt(13), c.getInt(14),
						c.getInt(15), c.getInt(16));
				tmpActions.add(a);

				Log.d("values: ", a.toString());
			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	public ActionType getActionTypeById(int actionTypeId) {

		mDatabase.open();

		ActionType tmpAction = null;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTIONTYPE,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_NAME,
								RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_RESOURCE,
								RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_AUDIO },
						RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_ID + "="
								+ actionTypeId, null, null, null, null);

		if (c.moveToFirst()) {
			tmpAction = new ActionType(actionTypeId, c.getString(0),
					c.getInt(1), c.getInt(2));
		}
		c.close();
		mDatabase.close();

		return tmpAction;
	}

	public List<Resource> getActionTypes() {

		if (mAllActionTypes == null) {
			// opens the database.
			mDatabase.open();

			// query all actions
			Cursor c = mDatabase.getEntries(
					RealFarmDatabase.TABLE_NAME_ACTIONTYPE, new String[] {
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_RESOURCE,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_AUDIO },
					null, null, null, null, null);

			mAllActionTypes = new ArrayList<Resource>();

			ActionType at = null;
			if (c.moveToFirst()) {
				do {
					at = new ActionType(c.getInt(0), c.getString(1),
							c.getInt(2), c.getInt(3));
					mAllActionTypes.add(at);

					Log.d("action name values: ", at.toString());

				} while (c.moveToNext());
			}

			c.close();
			mDatabase.close();

		}

		return mAllActionTypes;
	}

	public List<AggregateItem> getAggregateItems(int actionTypeId,
			int seedTypeId) {

		String add = "";
		// if(seedTypeId != 0) add = "AND a.seedTypeId = "+seedTypeId;

		final String QUERY;
		switch (actionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			QUERY = "SELECT a.actionTypeId, COUNT(p.userId) AS users, a.seedTypeId, SUM(case when a.treatment='treated' THEN 1 ELSE 0 END) AS treatment FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionTypeId= %1$d GROUP BY a.actionTypeId, a.seedTypeId ORDER BY a.seedTypeId ASC";
			break;
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			QUERY = "SELECT a.actionTypeId, COUNT(p.userId) AS users, a.irrigateMethod FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionTypeId= %1$d "
					+ add
					+ " GROUP BY a.actionTypeId, a.irrigateMethod ORDER BY a.irrigateMethod ASC";
			break;
		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			QUERY = "SELECT a.actionTypeId, COUNT(p.userId) AS users, a.problemType FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionTypeId= %1$d "
					+ add
					+ " GROUP BY a.actionTypeId, a.problemType ORDER BY a.problemType ASC";
			break;
		default:
			QUERY = "SELECT a.actionTypeId, COUNT(p.userId) AS users FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionTypeId= %1$d GROUP BY a.actionTypeId";
			break;
		}

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(String.format(QUERY, actionTypeId),
				new String[] {});

		AggregateItem a = null;
		if (c.moveToFirst()) {
			do {
				a = new AggregateItem(c.getInt(0), c.getInt(1));
				// adds any additionally found column into the aggregate item
				// starts in 2 since actionTypeId and users are ignored.
				for (int x = 2; x < c.getColumnCount(); x++) {
					a.addValue(c.getColumnName(x), c.getString(x));
				}
				tmpList.add(a);

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<AggregateItem> getAggregateItemsSowing(int actionTypeId,
			String groupField, int seedTypeId) {
		final String MY_QUERY = "SELECT a.actionTypeId, COUNT(p.userId) as users, a.%2$s FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionTypeId= %1$d GROUP BY a.actionTypeId, a.%2$s ORDER BY a.%2$s ASC";

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(
				String.format(MY_QUERY, actionTypeId, groupField),
				new String[] {});

		AggregateItem a = null;
		if (c.moveToFirst()) {
			do {
				a = new AggregateItem(c.getInt(0), c.getInt(1));
				// adds any additionally found column into the aggregate item
				// starts in 2 since actionTypeId and users are ignored.
				for (int x = 2; x < c.getColumnCount(); x++) {
					a.addValue(c.getColumnName(x), c.getString(x));
				}
				tmpList.add(a);

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public int getCropIdFromSeedId(int seedId) {

		final String MY_QUERY = "SELECT masterId FROM seedType WHERE id = "
				+ seedId;

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		if (c.moveToFirst()) {
			return c.getInt(0);
		}

		c.close();
		mDatabase.close();

		return -1;
	}

	public List<Resource> getCrops() {

		List<Resource> tmpList = new ArrayList<Resource>();

		mDatabase.open();
		// query all actions
		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_CROP,
				new String[] { RealFarmDatabase.COLUMN_NAME_CROP_ID,
						RealFarmDatabase.COLUMN_NAME_CROP_NAME,
						RealFarmDatabase.COLUMN_NAME_CROP_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_CROP_AUDIO,
						RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_CROP_RESOURCEBG });

		Resource r = null;
		if (c.moveToFirst()) {
			do {
				r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setShortName(c.getString(2));
				r.setAudio(c.getInt(3));
				r.setResource1(c.getInt(4));
				r.setBackgroundResource(c.getInt(5));
				tmpList.add(r);
			} while (c.moveToNext());
		}

		// closes the cursor and the database.
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public RealFarmDatabase getDatabase() {
		return mDatabase;
	}

	/**
	 * Gets the resource that matches the given id.
	 * 
	 * @param resourceId
	 *            the Resource to find.
	 * 
	 * @return the Resource that matches the given id.
	 */
	// TODO: add optimization
	public Resource getResourceById(int resourceId) {

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_RESOURCE,
				new String[] { RealFarmDatabase.COLUMN_NAME_RESOURCE_ID,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_NAME,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCE1,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCE2,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCEBG,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_TYPE },
				RealFarmDatabase.COLUMN_NAME_RESOURCE_ID + "=" + resourceId,
				null, null, null, null);

		Resource tmpRes = null;
		if (c.moveToFirst()) {

			tmpRes = new Resource();
			tmpRes.setId(c.getInt(0));
			tmpRes.setName(c.getString(1));
			tmpRes.setShortName(c.getString(2));
			tmpRes.setAudio(c.getInt(3));
			tmpRes.setResource1(c.getInt(4));
			tmpRes.setResource2(c.getInt(5));
			tmpRes.setBackgroundResource(c.getInt(6));
			tmpRes.setType(c.getInt(7));

		}
		c.close();
		mDatabase.close();

		return tmpRes;
	}

	public List<Resource> getResources(int resourceType) {

		// final String MY_QUERY =
		// "SELECT name, shortName, resource, resource2, audio, value, number, resourceBg FROM dialogArrays WHERE type = "
		// + dataType + " ORDER BY type, id ASC";

		List<Resource> tmpList;

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase
				.getEntries(RealFarmDatabase.TABLE_NAME_RESOURCE, new String[] {
						RealFarmDatabase.COLUMN_NAME_RESOURCE_ID,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_NAME,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCE1,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCE2,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_RESOURCEBG,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_TYPE },
						RealFarmDatabase.COLUMN_NAME_RESOURCE_TYPE + "="
								+ resourceType, null, null, null, null);

		tmpList = new ArrayList<Resource>();

		Resource dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new Resource();
				dd.setId(c.getInt(0));
				dd.setName(c.getString(1));
				dd.setShortName(c.getString(2));
				dd.setAudio(c.getInt(3));
				dd.setResource1(c.getInt(4));
				dd.setResource2(c.getInt(5));
				dd.setBackgroundResource(c.getInt(6));
				dd.setType(c.getInt(7));

				tmpList.add(dd);

				Log.d("MP values: ", dd.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Resource> getSoilTypes() {

		List<Resource> tmpList;

		// opens the database.
		mDatabase.open();

		// queries all soil types
		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_SOILTYPE, new String[] {
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_ID,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_AUDIO });

		tmpList = new ArrayList<Resource>();

		Resource r = null;
		if (c.moveToFirst()) {
			do {
				// initializes a new Resource.
				r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setShortName(c.getString(2));
				r.setBackgroundResource(c.getInt(3));
				r.setAudio(c.getInt(4));

				// adds it to the list.
				tmpList.add(r);

			} while (c.moveToNext());
		}

		// closes the database and the cursor.
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public Resource getSoilTypeById(int soilTypeId) {

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SOILTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SOILTYPE_ID,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_AUDIO },
				RealFarmDatabase.COLUMN_NAME_SOILTYPE_ID + "=" + soilTypeId,
				null, null, null, null);

		Resource tmpRes = null;
		if (c.moveToFirst()) {

			// initializes a new Resource.
			tmpRes = new Resource();
			tmpRes.setId(c.getInt(0));
			tmpRes.setName(c.getString(1));
			tmpRes.setShortName(c.getString(2));
			tmpRes.setBackgroundResource(c.getInt(2));
			tmpRes.setAudio(c.getInt(4));

		}

		// closes the database and the cursor.
		c.close();
		mDatabase.close();

		return tmpRes;
	}

	public List<MarketPrice> getMarketPrices() {
		List<MarketPrice> tmpList;

		// opens the database.
		mDatabase.open();
		Log.d("done: ", "in market price");
		// query all actions
		Cursor c = mDatabase.getEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, new String[] {
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE }, null,
				null, null, null, null);

		tmpList = new ArrayList<MarketPrice>();

		MarketPrice mp = null;
		if (c.moveToFirst()) {
			do {
				mp = new MarketPrice(c.getInt(0), c.getString(1),
						c.getString(2), c.getInt(3));
				tmpList.add(mp);

				Log.d("MP values: ", mp.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<DialogData> getPesticide() {

		final String MY_QUERY = "SELECT name, audio, type, shortName, id FROM pesticide ORDER BY type, id ASC";

		List<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT res FROM pesticideType WHERE id = "
						+ c.getInt(2);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();

				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(3));
				dd.setImage(c2.getInt(0));
				dd.setAudio(c.getInt(1));
				dd.setId(c.getInt(4));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public Plot getPlotById(int plotId) {

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + "=" + plotId, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8));

				Log.d("plot values: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return p;
	}

	public List<Plot> getPlotDelete(int delete) {

		mDatabase.open();

		List<Plot> tmpList;

		// int id, int userId, int seedTypeId, String imagePath,
		// String soilType, float size, int isEnabled, int isAdminAction,
		// int timestamp
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED + "=" + delete,
				null, null, null, null);

		tmpList = new ArrayList<Plot>();

		Plot p = null;
		if (c.moveToFirst()) {
			do {

				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8));

				tmpList.add(p);

				Log.d("plot values: ", p.toString());

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Plot> getPlots() {

		// opens the database.
		List<Plot> tmpList = new ArrayList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP }, null,
				null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8));
				tmpList.add(p);

				Log.d("plot values: ", p.toString());

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<Plot> getPlotsByUserId(int userId) {

		// opens the database.
		List<Plot> tmpList = new ArrayList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8));
				tmpList.add(p);

				Log.d("plot values: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	// modified(You can take seedtypyId corresponding to the userId and plotId)
	public List<Plot> getPlotsByUserIdAndDeleteFlag(int userId, int delete) {

		// opens the database.
		List<Plot> tmpList = new ArrayList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND " + RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED
						+ "=" + delete + "", null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getInt(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8));
				tmpList.add(p);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	// TODO: implement cache optimization.
	public SeedType getSeedById(int seedId) {

		SeedType res = null;
		mDatabase.open();

		seedId = 1;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c.moveToFirst()) {
			res = new SeedType(seedId, c.getString(0), c.getString(1),
					c.getInt(2), c.getInt(3), c.getInt(4));
		}

		c.close();
		mDatabase.close();
		return res;

	}

	/**
	 * Gets the list of available SeedTypes.
	 * 
	 * @return the list of available SeedTypes.
	 */
	public List<Resource> getSeedTypes() {

		// seeds are not in cache
		if (mAllSeeds == null) {

			// initializes the list used to cache the seeds.
			mAllSeeds = new ArrayList<Resource>();
			mDatabase.open();

			Cursor c = mDatabase.getAllEntries(
					RealFarmDatabase.TABLE_NAME_SEEDTYPE, new String[] {
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_SHORTNAME,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO });

			if (c.moveToFirst()) {
				do {
					SeedType s = new SeedType(c.getInt(0), c.getString(1),
							c.getString(2), c.getInt(3), c.getInt(4),
							c.getInt(5));
					mAllSeeds.add(s);

					Log.d("seed type: ", s.toString());

				} while (c.moveToNext());

			}
			c.close();
			mDatabase.close();
		}

		return mAllSeeds;
	}

	public List<Resource> getUnits(int actionTypeId) {
		final String MY_QUERY = "SELECT DISTINCT u.* FROM unit u WHERE actionTypeId = "
				+ actionTypeId
				+ " OR actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_ALL_ID + " ORDER BY id ASC";

		List<Resource> tmpList = new ArrayList<Resource>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		Resource r = null;
		if (c.moveToFirst()) {
			do {
				r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setResource1(c.getInt(2));
				r.setAudio(c.getInt(3));

				tmpList.add(r);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItem(int actionTypeId,
			String selector) {

		final String QUERY;
		switch (actionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionTypeId = %1$d AND a.seedTypeId = %2$s ORDER BY a.date DESC";
			break;
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionTypeId = %1$d AND a.irrigateMethod = '%2$s' ORDER BY a.date DESC";
			break;
		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionTypeId = %1$d AND a.problemType = '%2$s' ORDER BY a.date DESC";
			break;
		default:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionTypeId = %1$d AND a.seedTypeId = %2$s ORDER BY a.date DESC";
		}

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();
		mDatabase.open();

		Cursor c = mDatabase.rawQuery(
				String.format(QUERY, actionTypeId, selector), new String[] {});

		UserAggregateItem ua = null;
		User u = null;
		if (c.moveToFirst()) {
			do {
				u = new User(c.getInt(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));

				ua = new UserAggregateItem(u, c.getString(8));
				tmpList.add(ua);

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public User getUserByDeviceId(String deviceId) {

		mDatabase.open();

		User tmpUser = null;

		// adds the default value if the active one is invalid.
		if (deviceId == null) {
			deviceId = RealFarmDatabase.DEFAULT_NUMBER;
		}

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },

				RealFarmDatabase.COLUMN_NAME_USER_DEVICEID + "= '" + deviceId
						+ "'", null, null, null, null);

		// user exists in database
		if (c.moveToFirst()) {

			tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
					deviceId, c.getString(3), c.getInt(4), c.getInt(5),
					c.getInt(6));
		}
		c.close();
		mDatabase.close();

		return tmpUser;
	}

	public User getUserById(int userId) {
		mDatabase.open();
		User tmpUser = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_DEVICEID,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = " + userId, null,
				null, null, null);

		// user exists in database
		if (c.moveToFirst()) {

			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2), c.getString(3), c.getInt(4), c.getInt(5),
					c.getInt(6));
		}

		// closes the cursor and database.
		c.close();
		mDatabase.close();

		return tmpUser;
	}

	/**
	 * Gets the total number of Users.
	 * 
	 * @return integer total number of Users.
	 */
	public int getUserCount() {
		mDatabase.open();
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID }, null,
				null, null, null, null);
		int userCount = c.getCount();
		c.close();
		mDatabase.close();
		return userCount;
	}

	public List<User> getUsersByIsEnabled(int isEnabled) {

		mDatabase.open();

		List<User> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_DEVICEID,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_USER_ISENABLED + "=" + isEnabled,
				null, null, null, null);

		tmpList = new ArrayList<User>();

		User u = null;
		if (c.moveToFirst()) {
			do {
				u = new User(c.getInt(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), isEnabled, c.getInt(5),
						c.getInt(6));
				tmpList.add(u);

				Log.d("sowing values: ", u.toString());

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<User> getUsers() {

		// opens the database.
		List<User> tmpList = new ArrayList<User>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_DEVICEID,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP }, null,
				null, null, null, null);

		User u = null;
		if (c.moveToFirst()) {
			do {
				u = new User(c.getInt(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));
				tmpList.add(u);

				Log.d("user: ", u.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	// TODO: add optimization
	public List<Resource> getVarieties() {

		// raw query.
		final String RAW_QUERY = "SELECT st.*, ct.%s FROM %s st INNER JOIN %s ct ON st.%s = ct.%s ORDER BY %s ASC";
		// substitutes values in the query.
		String processedQuery = String.format(RAW_QUERY,
				RealFarmDatabase.COLUMN_NAME_CROP_RESOURCEBG,
				RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				RealFarmDatabase.TABLE_NAME_CROP,
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
				RealFarmDatabase.COLUMN_NAME_CROP_ID,
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID);

		// creates the result list.
		List<Resource> tmpList = new ArrayList<Resource>();

		// opens the database and executes the query
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(processedQuery, new String[] {});

		Resource r = null;
		if (c.moveToFirst()) {
			do {

				r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setShortName(c.getString(2));
				r.setResource1(c.getInt(3));
				r.setAudio(c.getInt(4));
				r.setBackgroundResource(c.getInt(6));
				tmpList.add(r);
			} while (c.moveToNext());
		}

		// final String MY_QUERY3 =
		// "SELECT name, id, resBg, audio FROM cropType WHERE cropType.id IN (SELECT cropType.id FROM cropType WHERE cropType.id NOT IN (SELECT seedType.resBg FROM seedType)) ORDER BY id ASC";

		c.close();
		mDatabase.close();

		return tmpList;
	}

	/**
	 * Gets all the available WeatherForecasts, sorted by date. Old forecasts
	 * will be included as well.
	 * 
	 * @return a List of all the available WeatherForecasts.
	 */
	public List<WeatherForecast> getWeatherForecasts() {
		List<WeatherForecast> tmpList;

		// opens the database.
		mDatabase.open();
		Log.d("done: ", "in Wf getdata");
		// query all actions
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_WEATHERFORECAST,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID },
						null, null, null, null,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE
								+ " ASC");

		tmpList = new ArrayList<WeatherForecast>();

		WeatherForecast wf = null;
		if (c.moveToFirst()) {
			do {
				wf = new WeatherForecast(c.getInt(0), c.getString(1),
						c.getInt(2), c.getString(3));
				tmpList.add(wf);

				Log.d("WF values: ", wf.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished Wf getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	/**
	 * Gets the list of available WeatherForecasts based on a start date. Any
	 * date matching the start date is included as well in the list.
	 * 
	 * @param startDate
	 *            the initial date to be considered.
	 * 
	 * @return a List of WeatherForecasts that match this criteria.
	 */
	public List<WeatherForecast> getWeatherForecasts(Date startDate) {

		// creates the formatter based on the format using in the database.
		SimpleDateFormat df = new SimpleDateFormat(RealFarmProvider.DATE_FORMAT);

		return getWeatherForecasts(df.format(startDate));
	}

	/**
	 * Gets the list of available WeatherForecasts based on a start date. Any
	 * date matching the start date is included as well in the list. The given
	 * date has to be formatted according to RealFarmProvider.DATE_FORMAT.
	 * 
	 * @param startDate
	 *            the initial date to be considered.
	 * 
	 * @return a List of WeatherForecasts that match this criteria.
	 */
	public List<WeatherForecast> getWeatherForecasts(String startDate) {
		List<WeatherForecast> tmpList;

		// opens the database.
		mDatabase.open();
		Log.d("done: ", "in Wf getdata");

		// query all actions
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_WEATHERFORECAST,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID },
						"date("
								+ RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE
								+ ") >= date('" + startDate + "')", null, null,
						null, RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE
								+ " ASC");

		// creates the result list.
		tmpList = new ArrayList<WeatherForecast>();

		WeatherForecast wf = null;
		if (c.moveToFirst()) {
			do {
				wf = new WeatherForecast(c.getInt(0), c.getString(1),
						c.getInt(2), c.getString(3));
				tmpList.add(wf);

				Log.d("WF values: ", wf.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished Wf getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	/**
	 * Enables or disables the plot with the selected id. If isEnabled is 1, the
	 * plot is enabled, otherwise it gets disabled.
	 * 
	 * @param plotId
	 *            the id of the plot to modify.
	 * @param isEnabled
	 *            the new state of the plot
	 * @return the number of rows modified.
	 */
	public long setPlotEnabled(int plotId, int isEnabled) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED, isEnabled);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + " = '" + plotId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setUserEnabled(int userId, int isEnabled) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISENABLED, isEnabled);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = '" + userId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long addFertilizeAction(int plotId, int quantity1, int fertilizerId,
			int unit1, String date, int isSent, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID, plotId,
				date, NONE, NONE, quantity1, NONE, unit1, NONE, fertilizerId,
				NONE, NONE, NONE, isSent, isAdminAction);
	}

	public long addHarvestAction(int userId, int plotId, int quantity1,
			int quantity2, int unit1, String date, int satisfactionId,
			int isSent, int isAdminAction, int seedTypeId) {

		return addAction(RealFarmDatabase.ACTION_TYPE_HARVEST_ID, plotId, date,
				seedTypeId, getCropIdFromSeedId(seedTypeId), quantity1,
				quantity2, unit1, NONE, satisfactionId, NONE, NONE, NONE,
				isSent, isAdminAction);
	}

	public long addIrrigateAction(int plotId, int quantity1, int unit1,
			String date, int methodId, int isSent, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID, plotId,
				date, NONE, NONE, quantity1, NONE, unit1, NONE, methodId, NONE,
				NONE, NONE, isSent, isAdminAction);
	}

	/**
	 * Adds the listener used to detect when the WeatherForecast table is
	 * modified.
	 * 
	 * @param listener
	 *            the Data Change Listener.
	 */
	public void setWeatherForecastDataChangeListener(
			OnDataChangeListener listener) {
		sWeatherForecastDataListener = listener;
	}
}
