package com.commonsensenet.realfarm.dataaccess;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Advice;
import com.commonsensenet.realfarm.model.AdvicePiece;
import com.commonsensenet.realfarm.model.MarketPrice;
import com.commonsensenet.realfarm.model.Model;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.WeatherType;
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.DateHelper;

public class RealFarmProvider {

	/**
	 * Detects the modification of the WeatherForecast table. When a new value
	 * is inserted, this listener will be called with the new data.
	 * 
	 * @author Oscar Bola–os <@oscarbolanos>
	 * @author Nguyen Lisa
	 */
	public abstract interface OnWeatherForecastDataChangeListener {
		public abstract void onDataChanged(String date, int temperature,
				int weatherTypeId);
	}

	/** Date format used throughout the application. */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	/** Indicates that the value is not required. */
	public static final int NONE = -1;
	/** Date formatter used to store dates in the database with a common format. */
	public static SimpleDateFormat sDateFormat = new SimpleDateFormat(
			RealFarmProvider.DATE_FORMAT);
	/** Map used to handle different instances depending on the context. */
	private static Map<Context, RealFarmProvider> sMapProviders = new HashMap<Context, RealFarmProvider>();
	/** Listener used to detect changes in the weather forecast. */
	private static OnWeatherForecastDataChangeListener sWeatherForecastDataListener;

	public static RealFarmProvider getInstance(Context ctx) {
		if (!sMapProviders.containsKey(ctx))
			sMapProviders.put(ctx, new RealFarmProvider(new RealFarmDatabase(
					ctx)));
		return sMapProviders.get(ctx);
	}

	public static double round(double unrounded, int precision, int roundingMode) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}

	/** Cached ActionTypes to improve performance. */
	private List<Resource> mActionTypes;
	/** Real farm database access. */
	private RealFarmDatabase mDatabase;
	/** Cached seeds to improve performance. */
	private List<Resource> mSeedTypes;

	/** Cached seeds to improve performance. */
	private HashMap<Integer, WeatherType> mWeatherTypes;

	protected RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDatabase = database;

		// used to force creation.
		mDatabase.open();
		mDatabase.close();
	}

	public long addAction(int actionTypeId, long plotId, Date date,
			int seedTypeId, int cropTypeId, double quantity1, double quantity2,
			int unit1, int unit2, int resource1Id, int resource2Id, int price,
			long userId, int isAdminAction) {

		return addAction(new Date().getTime(), actionTypeId, plotId, date,
				seedTypeId, cropTypeId, quantity1, quantity2, unit1, unit2,
				resource1Id, resource2Id, price, userId, 0, isAdminAction,
				new Date().getTime());
	}

	public long addAction(long id, int actionTypeId, long plotId, Date date,
			int seedTypeId, int cropTypeId, double quantity1, double quantity2,
			int unit1, int unit2, int resource1Id, int resource2Id, int price,
			long userId, int isSent, int isAdminAction, long timestamp) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONTYPEID, actionTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
				sDateFormat.format(date));

		if (seedTypeId != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, seedTypeId);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID);
		}

		if (cropTypeId != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID, cropTypeId);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID);
		}

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, quantity2);

		if (unit1 != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID, unit1);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT1ID);
		}

		if (unit2 != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT2ID, unit2);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_UNIT2ID);
		}

		if (resource1Id != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID,
					resource1Id);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID);
		}

		if (resource2Id != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE2ID,
					resource2Id);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE2ID);
		}

		if (price != NONE) {
			args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PRICE, price);
		} else {
			args.putNull(RealFarmDatabase.COLUMN_NAME_ACTION_PRICE);
		}

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_USERID, userId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS, isSent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
				isAdminAction);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP, timestamp);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result != -1 ? id : -1;
	}

	public long addAdvice(String audioSequence, int problemTypeId,
			int seedTypeId, int stageNumber) {
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ADVICE_AUDIOSEQUENCE,
				audioSequence);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICE_PROBLEMTYPEID,
				problemTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICE_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICE_STAGENUMBER, stageNumber);

		mDatabase.open();

		// inserts the values into the database
		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_ADVICE, args);

		mDatabase.close();

		return result;
	}

	public long addAdvicePiece(long adviceId, int audio, int orderNumber,
			int suggestedResourceId, String comment, int actionTypeId) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_ADVICEID, adviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_AUDIO, audio);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_ORDERNUMBER,
				orderNumber);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_COMMENT, comment);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_SUGGESTEDACTIONID,
				actionTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ADVICEPIECE_SUGGESTEDRESOURCEID,
				suggestedResourceId);

		mDatabase.open();

		// inserts the values into the database
		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_ADVICEPIECE, args);

		mDatabase.close();

		return result;
	}

	public long addFertilizeAction(long userId, long plotId, double quantity1,
			int fertilizerId, int unit1, Date date, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID, plotId,
				date, NONE, NONE, quantity1, NONE, unit1, NONE, fertilizerId,
				NONE, NONE, userId, isAdminAction);
	}

	public long addHarvestAction(long userId, long plotId, int seedTypeId,
			int quantity1, int unit1, int satisfactionId, Date date,
			int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_HARVEST_ID, plotId, date,
				seedTypeId, getCropTypeIdFromSeedTypeId(seedTypeId), quantity1,
				NONE, unit1, NONE, satisfactionId, NONE, NONE, userId,
				isAdminAction);
	}

	public long addIrrigateAction(long userId, long plotId, int quantity1,
			int methodId, Date date, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID, plotId,
				date, NONE, NONE, quantity1, NONE, NONE, NONE, methodId, NONE,
				NONE, userId, isAdminAction);
	}

	public long addMarketPrice(String date, int min, int max, String type) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN, min);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX, max);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE, type);

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, args);

		mDatabase.close();

		return result;
	}

	public long addPlanAction(long userId, long plotId, int advicePieceId,
			int isAdminAction) {
		return addAction(RealFarmDatabase.ACTION_TYPE_PLAN_ID, plotId, Calendar
				.getInstance().getTime(), NONE, NONE, NONE, NONE, NONE, NONE,
				advicePieceId, NONE, NONE, userId, isAdminAction);
	}

	public long addPlot(long userId, int seedTypeId, int soilTypeId,
			String imagePath, double size, int isAdminAction, int plotType) {

		return addPlot(new Date().getTime(), userId, seedTypeId, imagePath,
				soilTypeId, size, 0, 1, isAdminAction, new Date().getTime(),
				plotType);
	}

	// Should only be used by the sync service.
	public long addPlot(long id, long userId, int seedTypeId, String imagePath,
			int soilType, double size, int isSent, int isEnabled,
			int isAdminAction, long timestamp, int plotType) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID, soilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SIZE, size);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SENDSTATUS, isSent);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED, isEnabled);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION, isAdminAction);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP, timestamp);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_TYPE, plotType);

		mDatabase.open();

		// inserts the values into the database
		long result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				args);

		mDatabase.close();

		return result != -1 ? id : -1;
	}

	public long addRecommendation(long plotId, int adviceId, long userId,
			String dataCollectionDate, String actReqByDate,
			String validThroughDate, int severity, int probability) {
		return addRecommendation(new Date().getTime(), plotId, adviceId,
				userId, dataCollectionDate, actReqByDate, validThroughDate,
				severity, probability, 1);
	}

	public long addRecommendation(long id, long plotId, int adviceId,
			long userId, String dataCollectionDate, String actReqByDate,
			String validThroughDate, int severity, int probability, int isUnread) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ADVICEID, adviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_USERID, userId);
		args.put(
				RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_DATACOLLECTIONDATE,
				dataCollectionDate);
		args.put(
				RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ACTIONREQUIREDBYDATE,
				actReqByDate);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_VALIDTHROUGHDATE,
				validThroughDate);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_SEVERITY, severity);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_PROBABILITY,
				probability);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ISUNREAD, isUnread);
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_TIMESTAMP,
				new Date().getTime());

		mDatabase.open();

		// inserts the values into the database
		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_RECOMMENDATION, args);

		mDatabase.close();

		return result;
	}

	public long addReportAction(long userId, long plotId, int seedTypeId,
			int problemTypeId, Date date, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_REPORT_ID, plotId, date,
				seedTypeId, getCropTypeIdFromSeedTypeId(seedTypeId), NONE,
				NONE, NONE, NONE, problemTypeId, NONE, NONE, userId,
				isAdminAction);
	}

	public long addSellAction(long userId, long plotId, int cropTypeId,
			int quantity1, int quantity2, int unit1, int unit2, int price,
			Date date, int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SELL_ID, plotId, date,
				NONE, cropTypeId, quantity1, quantity2, unit1, unit2, NONE,
				NONE, price, userId, isAdminAction);
	}

	public long addSowAction(long userId, long plotId, int quantity1,
			int seedTypeId, int treatmentId, int intercropId, Date date,
			int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SOW_ID, plotId, date,
				seedTypeId, getCropTypeIdFromSeedTypeId(seedTypeId), quantity1,
				NONE, NONE, NONE, treatmentId, intercropId, NONE, userId,
				isAdminAction);
	}

	public long addSprayAction(long userId, long plotId, int problemId,
			int pesticideId, int quantity1, int unit1, Date date,
			int isAdminAction) {

		return addAction(RealFarmDatabase.ACTION_TYPE_SPRAY_ID, plotId, date,
				NONE, NONE, quantity1, NONE, unit1, NONE, problemId,
				pesticideId, NONE, userId, isAdminAction);
	}

	/**
	 * Adds a new User to the database. A Global id is chosen for the user,
	 * based on the DeviceId and a sequential number.
	 * 
	 * @param firstname
	 *            firstname of the user.
	 * @param lastname
	 *            lastname of the user.
	 * @param mobileNumber
	 *            mobile number, or any contact number.
	 * @param deviceId
	 *            identifier of the device where the operation has been done.
	 * @param imagePath
	 *            path location of the avatar image.
	 * @param location
	 *            text describing the location of the user.
	 * @param isAdminAction
	 * 
	 * @return
	 */
	public long addUser(String firstname, String lastname, String mobileNumber,
			String deviceId, String imagePath, String location,
			int isAdminAction) {

		// gets the number of users that share the device id.
		int userDeviceCount = getUserCount(deviceId);

		return addUser(Long.valueOf(deviceId + (userDeviceCount + 1)),
				firstname, lastname, mobileNumber, deviceId, imagePath,
				location, Model.STATUS_UNSENT, 1, isAdminAction,
				new Date().getTime());
	}

	// should be used by the sync service.
	public long addUser(long id, String firstname, String lastname,
			String mobileNumber, String deviceId, String imagePath,
			String location, int isSent, int isEnabled, int isAdminAction,
			long timestamp) {

		// creates the value container.
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_USER_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILENUMBER, mobileNumber);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DEVICEID, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LOCATION, location);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_SENDSTATUS, isSent);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISENABLED, isEnabled);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION, isAdminAction);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP, timestamp);

		long result;

		mDatabase.open();

		// inserts the new user.
		result = mDatabase
				.insertEntries(RealFarmDatabase.TABLE_NAME_USER, args);

		mDatabase.close();

		return result != -1 ? id : -1;
	}

	/**
	 * Adds a new WeatherForecast to the Database. If a forecast of the given
	 * date already existed.
	 * 
	 * 
	 * @param date
	 *            date of the WeatherForecast in yyyy-MM-dd format.
	 * @param temperature
	 *            temperature in Celcius.
	 * @param weatherTypeId
	 *            WeatherTypeId of the received weather forecast
	 * 
	 * @return the id of the inserted weather forecast or -1 if an error was
	 *         produced.
	 */
	public long addWeatherForecast(String date, int temperature,
			int weatherTypeId) {

		// checks if a forecast for the date already exists.
		WeatherForecast wf = getWeatherForecastByDate(date);

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
				temperature);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
				weatherTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TIMESTAMP,
				new Date().getTime());

		long result;

		// opens the database.
		mDatabase.open();
		if (wf == null) {

			// inserts the new data.
			result = mDatabase.insertEntries(
					RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args);

		} else {
			// updates the data since it already existed.
			result = mDatabase.update(
					RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args,
					RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID + " = "
							+ wf.getId(), null);

			// if no results were modified it is changed to
			if (result == 0) {
				result = -1;
			} else {
				// if correctly modified the result gets updated to the id.
				result = wf.getId();
			}
		}

		// closes the database.
		mDatabase.close();

		// notifies any listener that the data changed.
		if (sWeatherForecastDataListener != null) {
			sWeatherForecastDataListener.onDataChanged(date, temperature,
					weatherTypeId);
		}

		return result;
	}

	public long deletePlanAction(long userId, long plotId, int advicePieceId) {
		return mDatabase.deleteEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				"userId=? AND plotId=? AND resource1Id=?",
				new String[] { String.valueOf(userId), String.valueOf(plotId),
						String.valueOf(advicePieceId) });
	}

	public int getActionCount() {
		mDatabase.open();
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID }, null,
				null, null, null, null);
		int actionCount = c.getCount();
		c.close();
		mDatabase.close();
		return actionCount;
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
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP });

		Action a = null;
		if (c.moveToFirst()) {
			do {

				a = new Action(c.getLong(0), c.getInt(1), c.getLong(2),
						c.getString(3), c.getInt(4), c.getInt(5),
						c.getDouble(6), c.getDouble(7), c.getInt(8),
						c.getInt(9), c.getInt(10), c.getInt(11), c.getInt(12),
						c.getLong(13), c.getInt(14), c.getInt(15),
						c.getLong(16));

				// adds the plot to the list
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
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + " = " + plotId
						+ "", null, null, null, null);

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getLong(0), c.getInt(1), c.getLong(2),
						c.getString(3), c.getInt(4), c.getInt(5),
						c.getDouble(6), c.getDouble(7), c.getInt(8),
						c.getInt(9), c.getInt(10), c.getInt(11), c.getInt(12),
						c.getLong(13), c.getInt(14), c.getInt(15),
						c.getLong(16));

				// adds the action to the list.
				tmpActions.add(a);

				Log.d("values: ", a.toString());
			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	// To get the actions based on send status
	public List<Action> getActionsBySendStatus(int sendStatus) {

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
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS + " = "
						+ sendStatus + "", null, null, null, null);

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getLong(0), c.getInt(1), c.getLong(2),
						c.getString(3), c.getInt(4), c.getInt(5),
						c.getDouble(6), c.getDouble(7), c.getInt(8),
						c.getInt(9), c.getInt(10), c.getInt(11), c.getInt(12),
						c.getLong(13), c.getInt(14), c.getInt(15),
						c.getLong(16));

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
	public List<Action> getActionsByUserId(long userId) {

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
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_ACTION_USERID + " = " + userId,
				null, null, null, RealFarmDatabase.COLUMN_NAME_ACTION_DATE
						+ " DESC");

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getLong(0), c.getInt(1), c.getLong(2),
						c.getString(3), c.getInt(4), c.getInt(5),
						c.getDouble(6), c.getDouble(7), c.getInt(8),
						c.getInt(9), c.getInt(10), c.getInt(11), c.getInt(12),
						c.getLong(13), c.getInt(14), c.getInt(15),
						c.getLong(16));

				// adds the action to the list
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
								RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_IMAGE,
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

		if (mActionTypes == null) {
			// opens the database.
			mDatabase.open();

			// query all actions
			Cursor c = mDatabase.getEntries(
					RealFarmDatabase.TABLE_NAME_ACTIONTYPE, new String[] {
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_IMAGE,
							RealFarmDatabase.COLUMN_NAME_ACTIONTYPE_AUDIO },
					null, null, null, null, null);

			mActionTypes = new ArrayList<Resource>();

			ActionType at = null;
			if (c.moveToFirst()) {
				do {
					at = new ActionType(c.getInt(0), c.getString(1),
							c.getInt(2), c.getInt(3));
					mActionTypes.add(at);

				} while (c.moveToNext());
			}
			// remove action plan
			mActionTypes.remove(mActionTypes.size() - 1);
			c.close();
			mDatabase.close();

		}

		return mActionTypes;
	}

	public ArrayList<AdviceSituationItem> getAdviceData(long userId) {

		// gets the current year data.
		// long dateBeginningYear = DateHelper.getBeginningYear();

		// lists where the advices and situations will be stored.
		ArrayList<AdviceSituationItem> situationList = new ArrayList<AdviceSituationItem>();

		// queries the recommendations and other related information.
		final String MY_QUERY = "SELECT rec.id, rec.plotId, rec.adviceId, rec.dataCollectionDate, rec.actionRequiredByDate, rec.validThroughDate, rec.severity, rec.probability, rec.isUnread, rec.timestamp, "
				+ "res.id, res.name, res.shortName, res.audio, res.image1, res.image2, res.backgroundImage, "
				+ "ad.id, ad.problemTypeId, ad.seedTypeId, ad.audioSequence, ad.stageNumber, "
				+ "p.id, p.imagePath "
				+ "FROM recommendation rec, advice ad, plot p, resource res "
				+ "WHERE rec.userId = "
				+ userId
				+ " AND rec.plotId = p.id AND rec.adviceId = ad.id AND ad.problemTypeId = res.id "
				+ "ORDER BY rec.timestamp DESC";

		mDatabase.open();

		// queries the recommendations.
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		AdviceSituationItem asItem;
		Recommendation r;
		Resource res;
		Advice a;

		Log.d("Provider", "" + c.getCount());

		if (c.moveToFirst()) {
			do {

				// list used to store the obtained solutions.
				ArrayList<AdviceSolutionItem> solutionList = new ArrayList<AdviceSolutionItem>();

				// queries the advice pieces that match the
				// recommendations found.
				final String MY_QUERY2 = "SELECT adp.id, adp.audio, adp.comment, adp.adviceId, adp.suggestedActionId, adp.suggestedResourceId, adp.orderNumber, "
						+ "res.id, res.name, res.shortName, res.audio, res.image1, res.image2, res.backgroundImage, at.id, at.audio "
						+ "FROM advicePiece adp, resource res, actionType at "
						+ "WHERE at.id = adp.suggestedActionId AND adp.adviceId = "
						+ c.getInt(2)
						+ " AND adp.suggestedResourceId = res.id "
						+ "ORDER BY adp.orderNumber ASC";
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});

				Log.d("Provider", "" + c2.getCount());

				AdvicePiece ap;
				if (c2.moveToFirst()) {
					do {
						ap = new AdvicePiece(c.getInt(0), c.getInt(1),
								c.getString(2), c.getInt(3), c.getInt(4),
								c.getInt(5), c.getInt(6));

						res = new Resource(c.getInt(7), c.getString(8),
								c.getString(9), c.getInt(10), c.getInt(11),
								c.getInt(12), c.getInt(13), -1, -1);

						// creates and advice item.
						AdviceSolutionItem aSolItem = new AdviceSolutionItem();
						aSolItem.setAdvicePiece(ap);
						aSolItem.setResource(res);

						// action related data.
						aSolItem.setActionId(c2.getInt(14));
						aSolItem.setActionAudio(c2.getInt(15));

						final String MY_QUERY4 = "SELECT COUNT(plotId) FROM action WHERE actionTypeId = "
								+ RealFarmDatabase.ACTION_TYPE_PLAN_ID
								+ " AND date LIKE '"
								+ Calendar.getInstance().get(Calendar.YEAR)
								+ "-%' AND resource1Id = " + res.getId();
						Cursor c4 = mDatabase.rawQuery(MY_QUERY4,
								new String[] {});

						if (c4.moveToFirst()) {
							aSolItem.setLikes(c4.getInt(0));
						}

						c4.close();

						aSolItem.setHasLiked(hasLiked(c2.getInt(4), userId));

						// adds the item into the solution list.
						solutionList.add(aSolItem);

					} while (c2.moveToNext());

					c2.close();
				}

				// creates the situation from the cursor.
				asItem = new AdviceSituationItem();

				r = new Recommendation(c.getLong(0), c.getLong(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getString(5),
						c.getInt(6), c.getInt(7), c.getInt(8), c.getLong(9));
				res = new Resource(c.getInt(10), c.getString(11),
						c.getString(12), c.getInt(13), c.getInt(14),
						c.getInt(15), c.getInt(16), -1, -1);
				a = new Advice(c.getInt(17), c.getInt(18), c.getInt(19),
						c.getString(20), c.getInt(21));

				asItem.setRecommendation(r);
				asItem.setResource(res);
				asItem.setAdvice(a);

				// gets the plot related info.
				asItem.setPlotId(c.getLong(22));
				asItem.setPlotImage(c.getString(23));

				asItem.setItems(solutionList);

				// adds the item to the list.
				situationList.add(asItem);

			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return situationList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsFertilize(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT id, image1, shortName FROM resource WHERE type = "
				+ RealFarmDatabase.RESOURCE_TYPE_FERTILIZER
				+ " ORDER BY id ASC";
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		final String MY_QUERY2 = "SELECT st.shortName, ct.backgroundImage FROM seedType st, cropType ct WHERE st.cropTypeId = ct.id AND st.id = "
				+ seedTypeId;
		Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});

		if (c.moveToFirst() && c2.moveToFirst()) {
			do {

				final String MY_QUERY3 = "SELECT COUNT(p.userId) as users, SUM(CASE WHEN (a.date <= '"
						+ dateNow
						+ "' AND date >= '"
						+ stopDate
						+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, plot p WHERE a.plotId = p.id AND plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
						+ seedTypeId
						+ ") AND a.actionTypeId= "
						+ actionTypeId
						+ " AND a.resource1Id = "
						+ c.getInt(0)
						+ " AND a.date LIKE '"
						+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
				Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});
				if (c3.moveToFirst() && c3.getInt(0) > 0) {
					AggregateItem a = new AggregateItem(actionTypeId);
					a.setLeftBackground(c2.getInt(1));
					a.setLeftText(c2.getString(0));
					a.setNews(c3.getInt(1));
					a.setTotal(c3.getInt(0));
					a.setLeftImage(R.drawable.fertilizingaction);
					a.setCenterImage(c.getInt(1));
					a.setCenterText(c.getString(2));
					a.setSelector2(seedTypeId);
					a.setSelector3(c.getInt(0));
					tmpList.add(a);
				}
				c3.close();
			} while (c.moveToNext());
		}
		c2.close();
		c.close();
		mDatabase.close();
		return tmpList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsHarvest(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT st.shortName, ct.backgroundImage FROM seedType st, cropType ct WHERE st.cropTypeId = ct.id AND st.id = "
				+ seedTypeId;
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		if (c.moveToFirst()) {

			final String MY_QUERY3 = "SELECT AVG((a.quantity1*u.value)/(100*p.size)), SUM(CASE WHEN (a.date <= '"
					+ dateNow
					+ "' AND date >= '"
					+ stopDate
					+ "') THEN 1 ELSE 0 END) AS newsUsers, COUNT(a.date) as dat FROM action a, plot p, unit u WHERE a.plotId = p.id AND a.unit1Id = u.id AND a.actionTypeId= "
					+ actionTypeId
					+ " AND a.seedTypeId = "
					+ seedTypeId
					+ " AND a.date LIKE '"
					+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
			Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});

			if (c3.moveToFirst()) {
				String result = String.valueOf(round(c3.getDouble(0), 2,
						BigDecimal.ROUND_HALF_UP));
				if (c3.getInt(2) != 0) {
					AggregateItem a = new AggregateItem(actionTypeId);
					a.setLeftBackground(c.getInt(1));
					a.setCenterText(result + " qt/acre");
					a.setLeftText(c.getString(0));
					a.setNews(c3.getInt(1));
					a.setTotal(c3.getInt(2));
					a.setLeftImage(R.drawable.harvestingaction);
					a.setSelector1(seedTypeId);
					a.setResult(round(c3.getDouble(0), 2,
							BigDecimal.ROUND_HALF_UP));
					tmpList.add(a);
				}
			}
			c3.close();
		}
		c.close();
		mDatabase.close();
		return tmpList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsIrrigate(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT id, image1 FROM resource WHERE type = "
				+ RealFarmDatabase.RESOURCE_TYPE_IRRIGATIONMETHOD
				+ " ORDER BY id ASC";
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		final String MY_QUERY2 = "SELECT st.shortName, ct.backgroundImage FROM seedType st, cropType ct WHERE st.cropTypeId = ct.id AND st.id = "
				+ seedTypeId;
		Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});

		if (c.moveToFirst() && c2.moveToFirst()) {
			do {

				final String MY_QUERY3 = "SELECT COUNT(p.userId) as users, SUM(CASE WHEN (a.date <= '"
						+ dateNow
						+ "' AND date >= '"
						+ stopDate
						+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, plot p WHERE a.plotId = p.id AND plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
						+ seedTypeId
						+ ") AND a.actionTypeId= "
						+ actionTypeId
						+ " AND a.resource1Id = "
						+ c.getInt(0)
						+ " AND a.date LIKE '"
						+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
				Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});
				if (c3.moveToFirst() && c3.getInt(0) > 0) {
					AggregateItem a = new AggregateItem(actionTypeId);
					a.setLeftBackground(c2.getInt(1));
					a.setLeftText(c2.getString(0));
					a.setNews(c3.getInt(1));
					a.setTotal(c3.getInt(0));
					a.setLeftImage(R.drawable.irrigationaction);
					a.setCenterBackground(c.getInt(1));
					a.setSelector1(seedTypeId);
					a.setSelector2(c.getInt(0));
					tmpList.add(a);
				}
				c3.close();
			} while (c.moveToNext());
		}
		c2.close();
		c.close();
		mDatabase.close();
		return tmpList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsReport(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT id, image1, shortName FROM resource WHERE type = "
				+ RealFarmDatabase.RESOURCE_TYPE_PROBLEM + " ORDER BY id ASC";
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				final String MY_QUERY3 = "SELECT COUNT(p.userId) as users, st.shortName, ct.backgroundImage, SUM(CASE WHEN (a.date <= '"
						+ dateNow
						+ "' AND date >= '"
						+ stopDate
						+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, seedType st, cropType ct, plot p WHERE a.plotId = p.id AND st.cropTypeId = ct.id AND a.seedTypeId = st.id AND a.actionTypeId= "
						+ actionTypeId
						+ " AND a.seedTypeId = "
						+ seedTypeId
						+ " AND a.resource1Id = "
						+ c.getInt(0)
						+ " AND a.date LIKE '"
						+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
				Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});

				if (c3.moveToFirst() && c3.getInt(0) > 0) {
					AggregateItem a = new AggregateItem(actionTypeId);
					a.setLeftBackground(c3.getInt(2));
					a.setCenterImage(c.getInt(1));
					a.setCenterText(c.getString(2));
					a.setLeftText(c3.getString(1));
					a.setNews(c3.getInt(3));
					a.setTotal(c3.getInt(0));
					a.setLeftImage(R.drawable.problemreportingaction);
					a.setSelector1(seedTypeId);
					a.setSelector2(c.getInt(0));

					tmpList.add(a);
				}
				c3.close();
			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsSell(int actionTypeId,
			int cropTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		for (int min = 0; min < 10000; min = min
				+ RealFarmDatabase.SELLING_AGGREGATE_INCREMENT + 1) {

			final String MY_QUERY3 = "SELECT COUNT(a.plotId), ct.shortName, ct.backgroundImage, SUM(CASE WHEN (a.date <= '"
					+ dateNow
					+ "' AND date >= '"
					+ stopDate
					+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, cropType ct WHERE a.cropTypeId = ct.id AND a.actionTypeId= "
					+ actionTypeId
					+ " AND a.cropTypeId = "
					+ cropTypeId
					+ " AND a.price >= "
					+ min
					+ " AND a.price < "
					+ (min + RealFarmDatabase.SELLING_AGGREGATE_INCREMENT)
					+ " AND a.date LIKE '"
					+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
			mDatabase.open();
			Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});

			if (c3.moveToFirst() && c3.getInt(0) > 0) {
				AggregateItem a = new AggregateItem(actionTypeId);
				a.setLeftBackground(c3.getInt(2));
				a.setCenterText(min + " - "
						+ (min + RealFarmDatabase.SELLING_AGGREGATE_INCREMENT)
						+ " Rs/qt");
				a.setLeftText(c3.getString(1));
				a.setNews(c3.getInt(3));
				a.setTotal(c3.getInt(0));
				a.setLeftImage(R.drawable.sellingaction);
				a.setSelector1(cropTypeId);
				a.setSelector2(min);
				tmpList.add(a);
			}
			c3.close();
		}
		mDatabase.close();
		return tmpList;
	}

	// TODO: add optimization
	public List<AggregateItem> getAggregateItemsSow(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT id, image1 FROM resource WHERE type = "
				+ RealFarmDatabase.RESOURCE_TYPE_TREATMENT + " ORDER BY id ASC";
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				final String MY_QUERY3 = "SELECT COUNT(p.userId) as users, st.shortName, ct.backgroundImage, SUM(CASE WHEN (a.date <= '"
						+ dateNow
						+ "' AND date >= '"
						+ stopDate
						+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, seedType st, cropType ct, plot p WHERE a.plotId = p.id AND st.cropTypeId = ct.id AND a.seedTypeId = st.id AND a.actionTypeId= "
						+ actionTypeId
						+ " AND a.seedTypeId = "
						+ seedTypeId
						+ " AND a.resource1Id = "
						+ c.getInt(0)
						+ " AND a.date LIKE '"
						+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
				Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});

				if (c3.moveToFirst() && c3.getInt(0) > 0) {
					AggregateItem a = new AggregateItem(actionTypeId);
					a.setLeftBackground(c3.getInt(2));
					a.setCenterImage(c.getInt(1));
					a.setLeftText(c3.getString(1));
					a.setNews(c3.getInt(3));
					a.setTotal(c3.getInt(0));
					a.setLeftImage(R.drawable.sowingaction);
					a.setSelector1(seedTypeId);
					a.setSelector2(c.getInt(0));

					tmpList.add(a);
				}
				c3.close();
			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: add optimisation
	public List<AggregateItem> getAggregateItemsSpray(int actionTypeId,
			int seedTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper
				.getDatePast(-RealFarmDatabase.NUMBER_DAYS_NEWS);

		final String MY_QUERY = "SELECT id, image1, shortName FROM resource WHERE type = "
				+ RealFarmDatabase.RESOURCE_TYPE_PROBLEM + " ORDER BY id ASC";
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		final String MY_QUERY2 = "SELECT st.shortName, ct.backgroundImage FROM seedType st, cropType ct WHERE st.cropTypeId = ct.id AND st.id = "
				+ seedTypeId;
		Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});

		if (c.moveToFirst() && c2.moveToFirst()) {
			do {

				final String MY_QUERY3 = "SELECT DISTINCT r.shortName, a.resource2Id FROM action a, resource r WHERE a.resource2Id = r.id AND plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
						+ seedTypeId
						+ ") AND a.actionTypeId= "
						+ actionTypeId
						+ " AND a.resource1Id = "
						+ c.getInt(0)
						+ " AND a.date LIKE '"
						+ Calendar.getInstance().get(Calendar.YEAR) + "-%'";
				Cursor c3 = mDatabase.rawQuery(MY_QUERY3, new String[] {});

				if (c3.moveToFirst()) {
					do {
						final String MY_QUERY4 = "SELECT COUNT(p.userId) as users, SUM(CASE WHEN (a.date <= '"
								+ dateNow
								+ "' AND date >= '"
								+ stopDate
								+ "') THEN 1 ELSE 0 END) AS newsUsers FROM action a, plot p WHERE a.plotId = p.id AND a.actionTypeId= "
								+ actionTypeId
								+ " AND a.resource1Id = "
								+ c.getInt(0)
								+ " AND a.resource2Id = "
								+ c3.getInt(1);
						Cursor c4 = mDatabase.rawQuery(MY_QUERY4,
								new String[] {});
						if (c4.moveToFirst() && c4.getInt(0) > 0) {
							do {
								AggregateItem a = new AggregateItem(
										actionTypeId);
								a.setCenterBackground(c2.getInt(1));
								a.setCenterText(c2.getString(0));
								a.setRightText(c3.getString(0));
								a.setNews(c4.getInt(1));
								a.setTotal(c4.getInt(0));
								a.setCenterImage(R.drawable.sprayingaction);
								a.setLeftImage(c.getInt(1));
								a.setLeftText(c.getString(2));
								a.setSelector1(seedTypeId);
								a.setSelector2(c.getInt(0));
								a.setSelector3(c3.getInt(1));
								tmpList.add(a);
							} while (c4.moveToNext());
						}
						c4.close();
					} while (c3.moveToNext());
					c3.close();
				}
			} while (c.moveToNext());
		}
		c2.close();
		c.close();
		mDatabase.close();
		return tmpList;
	}

	public String getAggregatesNumbers(int actionTypeId) {

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper.getDatePast(-14);

		final String MY_QUERY = "SELECT COUNT(id) FROM action WHERE actionTypeId = "
				+ actionTypeId
				+ " AND date <= '"
				+ dateNow
				+ "' AND date >= '"
				+ stopDate + "'";
		mDatabase.open();
		Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});
		int res = 0;
		if (cursor.moveToFirst()) {
			res = cursor.getInt(0);
		}
		cursor.close();
		mDatabase.close();
		if (res < 1)
			return "";
		else
			return String.valueOf(res);
	}

	// TODO: add cache optimization.
	public int getCropTypeIdFromSeedTypeId(int seedTypeId) {

		mDatabase.open();

		// queries the Seed Type table to get the specific crop type.
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_SEEDTYPE,
						new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID },
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "="
								+ seedTypeId, null, null, null, null);

		if (c.moveToFirst()) {
			return c.getInt(0);
		}

		c.close();
		mDatabase.close();

		return -1;
	}

	// TODO: add optimization
	public List<Resource> getCropTypes() {

		List<Resource> tmpList = new ArrayList<Resource>();

		mDatabase.open();
		// query all actions
		Cursor c = mDatabase
				.getAllEntries(
						RealFarmDatabase.TABLE_NAME_CROPTYPE,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_ID,
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_NAME,
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_SHORTNAME,
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_AUDIO,
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_IMAGE,
								RealFarmDatabase.COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE });

		Resource r = null;
		if (c.moveToFirst()) {
			do {
				r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setShortName(c.getString(2));
				r.setAudio(c.getInt(3));
				r.setImage1(c.getInt(4));
				r.setBackgroundImage(c.getInt(5));
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

	public int getLimitPrice(String column) {
		int res = 0;
		final String MY_QUERY = "SELECT " + column
				+ " FROM marketPrice ORDER BY date DESC LIMIT 1";
		mDatabase.open();
		Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});
		if (cursor.moveToFirst()) {
			res = cursor.getInt(0);
		}
		cursor.close();
		mDatabase.close();
		return res;
	}

	public List<AggregateItem> getMarketData(int cropTypeId, int daySpan) {
		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper.getDatePast(daySpan);

		final String MY_QUERY = "SELECT id, image FROM unit WHERE actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_SELL_ID;
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT MIN(a.price) as min, MAX(a.price) as max, COUNT(a.price) as cou, ct.shortName, ct.backgroundImage FROM action a , cropType ct WHERE a.cropTypeId = ct.id AND a.actionTypeId = "
						+ RealFarmDatabase.ACTION_TYPE_SELL_ID
						+ " AND a.date >= '"
						+ stopDate
						+ "' AND a.date <= '"
						+ dateNow
						+ "' AND a.unit1Id = "
						+ c.getInt(0)
						+ " AND a.cropTypeId = " + cropTypeId;
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				if (c2.moveToFirst() && c2.getInt(1) > 0) {
					AggregateItem mi = new AggregateItem(0);
					mi.setRightText(c2.getInt(0) + " - " + c2.getInt(1)
							+ " Rs/qt");
					mi.setCenterImage(c.getInt(1));
					mi.setSelector3(c2.getInt(0));
					mi.setSelector2(c2.getInt(1));
					mi.setSelector1(c.getInt(0));
					mi.setNews(c2.getInt(2));
					mi.setLeftBackground(c2.getInt(4));
					mi.setLeftText(c2.getString(3));
					mi.setLeftImage(R.drawable.sellingaction);
					tmpList.add(mi);
				}
				c2.close();
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
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
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MIN,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_MAX,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE }, null,
				null, null, null, null);

		tmpList = new ArrayList<MarketPrice>();

		MarketPrice mp = null;
		if (c.moveToFirst()) {
			do {
				mp = new MarketPrice(c.getInt(0), c.getString(1), c.getInt(2),
						c.getInt(3), c.getString(4));
				tmpList.add(mp);

				Log.d("MarketPrice values: ", mp.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Resource> getPesticide() {

		final String MY_QUERY = "SELECT name, audio, type, shortName, id FROM pesticide ORDER BY type, id ASC";

		List<Resource> tmpList = new ArrayList<Resource>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		Resource dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT res FROM pesticideType WHERE id = "
						+ c.getInt(2);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();

				dd = new Resource();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(3));
				dd.setImage1(c2.getInt(0));
				dd.setAudio(c.getInt(1));
				dd.setId(c.getInt(4));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	/**
	 * Gets the plot that matches the id and userId combination. These two
	 * values make up the unique identifier of each plot.
	 * 
	 * @param plotId
	 *            local identifier of the plot.
	 * @param userId
	 *            identifier of the user to query.
	 * 
	 * @return the plot that matches the unique plotId + userId combination.
	 */
	public Plot getPlotById(long plotId, long userId) {
		List<Plot> plots = getPlots(RealFarmDatabase.COLUMN_NAME_PLOT_ID + "="
				+ plotId + " AND " + RealFarmDatabase.COLUMN_NAME_PLOT_USERID
				+ "=" + userId);

		return plots.size() > 0 ? plots.get(0) : null;
	}

	public int getPlotCount() {
		mDatabase.open();
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID }, null,
				null, null, null, null);
		int plotCount = c.getCount();
		c.close();
		mDatabase.close();
		return plotCount;
	}

	/**
	 * Queries all available plots in the database.
	 * 
	 * @return all the available plots.
	 */
	public List<Plot> getPlots() {
		return getPlots(null);
	}

	public List<Recommendation> getRecommendations() {

		// list used to store all the objects obtained from the database.
		List<Recommendation> tmpList = new ArrayList<Recommendation>();

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_RECOMMENDATION,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_PLOTID,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ADVICEID,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_DATACOLLECTIONDATE,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ACTIONREQUIREDBYDATE,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_VALIDTHROUGHDATE,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_SEVERITY,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_PROBABILITY,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ISUNREAD,
								RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_TIMESTAMP },
						null, null, null, null, null);

		Recommendation r = null;
		if (c.moveToFirst()) {
			do {
				r = new Recommendation(c.getLong(0), c.getLong(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getString(5),
						c.getInt(6), c.getInt(7), c.getInt(8), c.getLong(9));
				tmpList.add(r);
				Log.d("Recommendation: ", r.toString());
			} while (c.moveToNext());
		}

		// closes the database and the cursor.
		c.close();
		mDatabase.close();

		return tmpList;
	}

	protected List<Plot> getPlots(String selection) {

		// opens the database.
		List<Plot> tmpList = new ArrayList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE,
						RealFarmDatabase.COLUMN_NAME_PLOT_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_PLOT_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_TYPE }, selection,
				null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getLong(0), c.getLong(1), c.getInt(2),
						c.getInt(3), c.getString(4), c.getFloat(5),
						c.getInt(6), c.getInt(7), c.getInt(8), c.getLong(9),
						c.getInt(10));
				tmpList.add(p);
				Log.d("plot: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	// To get the plots based on send status
	public List<Plot> getPlotsBySendStatus(int sendStatus) {
		return getPlots(RealFarmDatabase.COLUMN_NAME_PLOT_SENDSTATUS + "="
				+ sendStatus);
	}

	/**
	 * Gets all plots matching the same user.
	 * 
	 * @param userId
	 *            the userId to query from.
	 * 
	 * @return a list of all the plots that match the given userId.
	 */
	public List<Plot> getPlotsByUserId(long userId) {
		return getPlots(RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId);
	}

	public List<Plot> getPlotsByUserIdAndEnabledFlag(long userId, int isEnabled) {
		return getPlots(RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
				+ " AND " + RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED + "="
				+ isEnabled);
	}

	// TODO: Redo the function.
	// TODO: optimise date test
	public List<Plot> getPlotsByUserIdAndEnabledFlagAndHasCrops(long userId,
			int isEnabled) {

		List<Plot> tmpList = getPlotsByUserIdAndEnabledFlag(userId, isEnabled);
		ArrayList<Plot> tmpList2 = new ArrayList<Plot>();

		for (Plot p : tmpList) {
			long id = p.getId();
			final String MY_QUERY = "SELECT COUNT(id) FROM action a WHERE plotId = "
					+ id
					+ " AND date LIKE '"
					+ Calendar.getInstance().get(Calendar.YEAR)
					+ "-%' AND a.actionTypeId = "
					+ RealFarmDatabase.ACTION_TYPE_SOW_ID;
			mDatabase.open();
			Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});

			if (cursor.moveToFirst()) {
				if (cursor.getInt(0) > 0)
					tmpList2.add(p);
			}
			cursor.close();
			mDatabase.close();
		}

		return tmpList2;
	}

	public int getRecommendationCountByUser(long userId) {

		mDatabase.open();
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_RECOMMENDATION,
						new String[] { RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID },
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_USERID
								+ " = '"
								+ userId
								+ "' AND "
								+ RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ISUNREAD
								+ " = 1 AND "
								+ RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_VALIDTHROUGHDATE
								+ " >= " + new Date().getTime(), null, null,
						null, null);

		// counts the total active recommendations.
		int recommendationCount = c.getCount();

		// closes the cursor and database.
		c.close();
		mDatabase.close();

		return recommendationCount;
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
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE1,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE2,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_BACKGROUNDIMAGE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_TYPE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_VALUE },
				RealFarmDatabase.COLUMN_NAME_RESOURCE_ID + "=" + resourceId,
				null, null, null, null);

		Resource tmpRes = null;
		if (c.moveToFirst()) {

			tmpRes = new Resource();
			tmpRes.setId(c.getInt(0));
			tmpRes.setName(c.getString(1));
			tmpRes.setShortName(c.getString(2));
			tmpRes.setAudio(c.getInt(3));
			tmpRes.setImage1(c.getInt(4));
			tmpRes.setImage2(c.getInt(5));
			tmpRes.setBackgroundImage(c.getInt(6));
			tmpRes.setType(c.getInt(7));
			tmpRes.setValue(c.getInt(8));

		}
		c.close();
		mDatabase.close();

		return tmpRes;
	}

	public int getResourceImageById(long resId, String table, String field) {
		final String MY_QUERY = "SELECT " + field + " FROM " + table
				+ " WHERE id = " + resId;
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		int res = -1;
		if (c.moveToFirst()) {
			res = c.getInt(0);
		}
		c.close();
		mDatabase.close();
		return res;
	}

	public List<Resource> getResources(int resourceType) {

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
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE1,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE2,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_BACKGROUNDIMAGE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_TYPE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_VALUE },
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
				dd.setImage1(c.getInt(4));
				dd.setImage2(c.getInt(5));
				dd.setBackgroundImage(c.getInt(6));
				dd.setType(c.getInt(7));
				dd.setValue(c.getInt(8));

				tmpList.add(dd);

				Log.d("MP values: ", dd.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: implement cache optimization.
	public SeedType getSeedById(int seedId) {

		SeedType res = null;
		mDatabase.open();

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_IMAGE,
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
		if (mSeedTypes == null) {

			// initializes the list used to cache the seeds.
			mSeedTypes = new ArrayList<Resource>();
			mDatabase.open();

			Cursor c = mDatabase.getAllEntries(
					RealFarmDatabase.TABLE_NAME_SEEDTYPE, new String[] {
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_SHORTNAME,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_IMAGE,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO });

			if (c.moveToFirst()) {
				do {
					SeedType s = new SeedType(c.getInt(0), c.getString(1),
							c.getString(2), c.getInt(3), c.getInt(4),
							c.getInt(5));
					mSeedTypes.add(s);

					Log.d("seed type: ", s.toString());

				} while (c.moveToNext());

			}
			c.close();
			mDatabase.close();
		}

		return mSeedTypes;
	}

	public Resource getSoilTypeById(int soilTypeId) {

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SOILTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SOILTYPE_ID,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_SHORTNAME,
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_IMAGE,
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
			tmpRes.setBackgroundImage(c.getInt(3));
			tmpRes.setAudio(c.getInt(4));

		}

		// closes the database and the cursor.
		c.close();
		mDatabase.close();

		return tmpRes;
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
						RealFarmDatabase.COLUMN_NAME_SOILTYPE_IMAGE,
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
				r.setBackgroundImage(c.getInt(3));
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

	public Resource getTopSelectorDataCrop(long userId, long defaultCropTypeId) {
		final String MY_QUERY = "SELECT ct.backgroundImage, ct.shortName, ct.id, ct.audio "
				+ "FROM seedType st, plot p, cropType ct "
				+ "WHERE st.cropTypeId = ct.id AND st.id = p.seedtypeId AND p.userId = "
				+ userId + " " + "ORDER BY st.id ASC";
		final String MY_QUERY2 = "SELECT ct.backgroundImage, ct.shortName, ct.id, ct.audio "
				+ "FROM cropType ct " + "WHERE ct.id = " + defaultCropTypeId;
		mDatabase.open();
		Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});
		Resource r = null;
		if (cursor.moveToFirst()) {
			r = new Resource(cursor.getInt(2), "", cursor.getString(1),
					cursor.getInt(3), -1, -1, cursor.getInt(0), -1, -1);
		}
		cursor.close();
		if (r == null) {
			cursor = mDatabase.rawQuery(MY_QUERY2, new String[] {});
			if (cursor.moveToFirst()) {
				r = new Resource(cursor.getInt(2), "", cursor.getString(1),
						cursor.getInt(3), -1, -1, cursor.getInt(0), -1, -1);
			}
			cursor.close();
		}
		mDatabase.close();
		return r;
	}

	public Resource getTopSelectorDataVar(long userId, long defaultSeedId) {
		final String MY_QUERY = "SELECT ct.backgroundImage, st.shortName, st.id, st.audio FROM seedType st, plot p, cropType ct WHERE st.cropTypeId = ct.id AND st.id = p.seedtypeId AND p.userId = "
				+ userId + " ORDER BY st.id ASC";
		final String MY_QUERY2 = "SELECT ct.backgroundImage, st.shortName, st.id, st.audio FROM seedType st, cropType ct WHERE st.cropTypeId = ct.id AND st.id = "
				+ defaultSeedId;
		mDatabase.open();
		Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});
		Resource r = null;
		if (cursor.moveToFirst()) {
			r = new Resource(cursor.getInt(2), "", cursor.getString(1),
					cursor.getInt(3), -1, -1, cursor.getInt(0), -1, -1);
		}
		cursor.close();
		if (r == null) {
			cursor = mDatabase.rawQuery(MY_QUERY2, new String[] {});
			if (cursor.moveToFirst()) {
				r = new Resource(cursor.getInt(2), "", cursor.getString(1),
						cursor.getInt(3), -1, -1, cursor.getInt(0), -1, -1);
			}
			cursor.close();
		}
		mDatabase.close();
		return r;
	}

	public List<Resource> getUnits(int actionTypeId) {

		final String MY_QUERY = "SELECT DISTINCT u.* FROM unit u WHERE actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_ALL_ID
				+ " OR actionTypeId = "
				+ actionTypeId + " ORDER BY id ASC";

		List<Resource> tmpList = new ArrayList<Resource>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				Resource r = new Resource();
				r.setId(c.getInt(0));
				r.setName(c.getString(1));
				r.setImage1(c.getInt(2));
				r.setAudio(c.getInt(3));

				tmpList.add(r);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemFertilize(
			int actionTypeId, long seedTypeId, long fertilizerId) {
		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.unit1Id, a.quantity1, p.size , u.nameAudio, u.locationAudio, u.id FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
				+ seedTypeId
				+ ") AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.resource1Id = "
				+ fertilizerId
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(11));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setRightImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_IMAGE));
				a.setAudioRightImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO));
				a.setLeftText(String.valueOf(round(
						c.getInt(7) / c.getDouble(8), 2,
						BigDecimal.ROUND_HALF_UP)));
				a.setRightText("/acre");
				a.setAudioName(c.getInt(9));
				a.setAudioLocation(c.getInt(10));
				tmpList.add(a);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemHarvest(
			int actionTypeId, long seedTypeId) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.quantity1, un.value, p.size , u.nameAudio, u.locationAudio, u.id FROM action a, user u, unit un, plot p WHERE a.plotId = p.id AND a.userId = u.id AND a.unit1Id = un.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.seedTypeId = "
				+ seedTypeId
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(11));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setLeftText(String.valueOf(round(c.getInt(6) * c.getInt(7)
						/ (100 * c.getDouble(8)), 2, BigDecimal.ROUND_HALF_UP)));
				a.setRightText("qt/acre");
				a.setAudioName(c.getInt(9));
				a.setAudioLocation(c.getInt(10));
				tmpList.add(a);

			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemIrrigate(
			int actionTypeId, long seedTypeId, long irrigateMethodId) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.quantity1, u.nameAudio, u.locationAudio, u.id FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
				+ seedTypeId
				+ ") AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.resource1Id = "
				+ irrigateMethodId
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(9));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setLeftImage(R.drawable.timeduration);
				a.setAudioLeftImage(getResourceImageById(irrigateMethodId,
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));

				String hour = "hours";
				if (c.getInt(6) == 1)
					hour = "hour";
				a.setLeftText(c.getInt(6) + "");
				a.setRightText(hour);

				a.setAudioName(c.getInt(7));
				a.setAudioLocation(c.getInt(8));

				tmpList.add(a);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemReport(int actionTypeId,
			long seedTypeId, long problemTypeId) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, u.nameAudio, u.locationAudio, u.id  FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.seedTypeId = "
				+ seedTypeId
				+ " AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.resource1Id = "
				+ problemTypeId
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(8));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setAudioLeftImage(getResourceImageById(problemTypeId,
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				a.setAudioName(c.getInt(6));
				a.setAudioLocation(c.getInt(7));
				tmpList.add(a);

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemSell(int actionTypeId,
			long cropTypeId, long min) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.price, a.unit1Id, u.nameAudio, u.locationAudio, u.id FROM action a, user u WHERE a.userId = u.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.cropTypeId = "
				+ cropTypeId
				+ " AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.price >= "
				+ min
				+ " AND a.price < "
				+ (min + RealFarmDatabase.SELLING_AGGREGATE_INCREMENT)
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(10));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setLeftText(c.getInt(6) + "");
				a.setRightText("Rs/qt");
				a.setLeftImage(getResourceImageById(c.getInt(7),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_IMAGE));
				a.setAudioLeftImage(getResourceImageById(c.getInt(7),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO));

				a.setAudioName(c.getInt(8));
				a.setAudioLocation(c.getInt(9));
				tmpList.add(a);

			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemSow(int actionTypeId,
			long seedTypeId, long treatmentId) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.resource1Id, a.resource2Id, a.quantity1, p.size, u.nameAudio, u.locationAudio, u.id FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.seedTypeId = "
				+ seedTypeId
				+ " AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.resource1Id = "
				+ treatmentId
				+ " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(12));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setCenterImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE1));
				a.setAudioCenterImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				a.setLeftImage(getResourceImageById(c.getInt(7),
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_IMAGE1));
				a.setAudioLeftImage(getResourceImageById(c.getInt(7),
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				a.setRightImage(R.drawable.seruonly);
				a.setLeftText(String.valueOf(round(
						c.getInt(8) / c.getDouble(9), 2,
						BigDecimal.ROUND_HALF_UP)));
				a.setRightText("/acre");
				a.setAudioName(c.getInt(10));
				a.setAudioLocation(c.getInt(11));
				tmpList.add(a);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<UserAggregateItem> getUserAggregateItemSpray(int actionTypeId,
			long seedTypeId, long problemId, long pesticideId) {

		int noneId = getResources(RealFarmDatabase.RESOURCE_TYPE_ADVICE).get(0)
				.getId();
		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();
		String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.unit1Id, a.quantity1, p.size, u.nameAudio, u.locationAudio, u.id FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
				+ actionTypeId
				+ " AND a.plotId IN (SELECT DISTINCT plotId FROM action WHERE seedTypeId = "
				+ seedTypeId
				+ ") AND a.resource1Id = "
				+ problemId
				+ " AND a.date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND a.resource2Id = "
				+ pesticideId
				+ " ORDER BY a.date DESC";
		if (pesticideId == noneId) {
			MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.unit1Id, a.quantity1, p.size, u.nameAudio, u.locationAudio, u.id FROM action a, user u, plot p WHERE a.userId = u.id AND a.plotId = p.id AND a.actionTypeId = "
					+ RealFarmDatabase.ACTION_TYPE_REPORT_ID
					+ " AND a.resource1Id = "
					+ problemId
					+ " AND a.seedTypeId = "
					+ seedTypeId
					+ " AND a.date LIKE '"
					+ Calendar.getInstance().get(Calendar.YEAR)
					+ "-%' AND a.plotId NOT IN (SELECT plotId FROM action WHERE actionTypeId = "
					+ RealFarmDatabase.ACTION_TYPE_SPRAY_ID
					+ " AND resource1Id = "
					+ problemId
					+ " ORDER BY date DESC)";
		}
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(11));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setRightImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_IMAGE));
				a.setAudioRightImage(getResourceImageById(c.getInt(6),
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_AUDIO));
				a.setAudioCenterImage(getResourceImageById(problemId,
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				a.setAudioLeftImage(getResourceImageById(pesticideId,
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				a.setAudioName(c.getInt(9));
				a.setAudioLocation(c.getInt(10));

				if (pesticideId == noneId) {
					a.setLeftText("");
					a.setRightText("");
				} else {
					a.setLeftText(String.valueOf(round(
							c.getInt(7) / c.getDouble(8), 2,
							BigDecimal.ROUND_HALF_UP)));
					a.setRightText("/acre");
				}
				tmpList.add(a);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	/**
	 * Gets the User that matches the id. If the User is not found null is
	 * returned instead.
	 * 
	 * @param userId
	 *            the User id to search for.
	 * 
	 * @return the User with the given id.
	 */
	public User getUserById(long userId) {

		// gets the users that match the id. Since the id is unique the list can
		// have maximum one user.
		List<User> userList = getUsers(RealFarmDatabase.COLUMN_NAME_USER_ID
				+ "=" + userId);

		return userList.size() > 0 ? userList.get(0) : null;
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

	/**
	 * Counts the number of users that have the same DeviceId.
	 * 
	 * @param deviceId
	 *            the device id to query.
	 * 
	 * @return the number of users that share the deviceId.
	 */
	public int getUserCount(String deviceId) {
		mDatabase.open();
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID },
				RealFarmDatabase.COLUMN_NAME_USER_DEVICEID + " = '" + deviceId
						+ "'", null, null, null, null);
		int userCount = c.getCount();
		c.close();
		mDatabase.close();
		return userCount;
	}

	public List<UserAggregateItem> getUserMarketData(long cropTypeId,
			long weightId, int daySpan) {

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();
		String dateNow = DateHelper.getDateNow();
		String stopDate = DateHelper.getDatePast(daySpan);

		final String MY_QUERY = "SELECT u.firstname, u.lastname, u.location, a.date, u.mobileNumber, u.imagePath, a.price, u.id FROM action a, user u WHERE a.userId = u.id AND a.actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_SELL_ID
				+ " AND a.cropTypeId = "
				+ cropTypeId
				+ " AND date >= '"
				+ stopDate
				+ "' AND a.date <= '"
				+ dateNow
				+ "' AND a.unit1Id = "
				+ weightId + " ORDER BY a.date DESC";
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		if (c.moveToFirst()) {
			do {
				UserAggregateItem a = new UserAggregateItem(c.getLong(7));
				a.setName(c.getString(0) + " " + c.getString(1) + ", "
						+ c.getString(2));
				String[] tokens = c.getString(3).split("-");
				a.setDate(tokens[2] + "." + tokens[1] + ".");
				a.setTel(c.getString(4));
				a.setAvatar(c.getString(5));
				a.setLeftText(c.getInt(6) + "");
				a.setRightText("Rs/qt");
				a.setLeftImage(getResourceImageById(weightId,
						RealFarmDatabase.TABLE_NAME_UNIT,
						RealFarmDatabase.COLUMN_NAME_UNIT_IMAGE));
				a.setAudioLeftImage(getResourceImageById(weightId,
						RealFarmDatabase.TABLE_NAME_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO));
				tmpList.add(a);

			} while (c.moveToNext());
		}
		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<User> getUsers() {
		return getUsers(null);
	}

	protected List<User> getUsers(String selection) {
		mDatabase.open();
		List<User> tmpList = new ArrayList<User>();

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILENUMBER,
						RealFarmDatabase.COLUMN_NAME_USER_DEVICEID,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_LOCATION,
						RealFarmDatabase.COLUMN_NAME_USER_SENDSTATUS,
						RealFarmDatabase.COLUMN_NAME_USER_ISENABLED,
						RealFarmDatabase.COLUMN_NAME_USER_ISADMINACTION,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_USER_NAME_AUDIO,
						RealFarmDatabase.COLUMN_NAME_USER_LOCATION_AUDIO },
				selection, null, null, null, null);
		// RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " DESC"

		User u = null;
		// user exists in database
		if (c.moveToFirst()) {
			do {
				u = new User(c.getLong(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getString(5),
						c.getString(6), c.getInt(7), c.getInt(8), c.getInt(9),
						c.getLong(10), c.getInt(11), c.getInt(12));

				Log.d("user: ", u.toString());
				tmpList.add(u);

			} while (c.moveToNext());
		}

		// closes the cursor and database.
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<User> getUsersByDeviceId(String deviceId) {

		// adds the default value if the active one is invalid.
		if (deviceId == null) {
			deviceId = RealFarmDatabase.DEFAULT_DEVICE_ID;
		}

		return getUsers(RealFarmDatabase.COLUMN_NAME_USER_DEVICEID + "='"
				+ deviceId + "'");
	}

	public List<User> getUsersByEnabledFlag(int isEnabled) {
		return getUsers(RealFarmDatabase.COLUMN_NAME_USER_ISENABLED + "="
				+ isEnabled);
	}

	// To get the users based on send status
	public List<User> getUsersBySendStatus(int sendStatus) {
		return getUsers(RealFarmDatabase.COLUMN_NAME_USER_SENDSTATUS + "="
				+ sendStatus);
	}

	// TODO: add optimization
	public List<Resource> getVarieties() {

		// raw query.
		final String RAW_QUERY = "SELECT st.*, ct.%s FROM %s st INNER JOIN %s ct ON st.%s = ct.%s ORDER BY %s ASC";
		// substitutes values in the query.
		String processedQuery = String.format(RAW_QUERY,
				RealFarmDatabase.COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE,
				RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				RealFarmDatabase.TABLE_NAME_CROPTYPE,
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
				RealFarmDatabase.COLUMN_NAME_CROPTYPE_ID,
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
				r.setImage1(c.getInt(3));
				r.setAudio(c.getInt(4));
				r.setBackgroundImage(c.getInt(6));
				tmpList.add(r);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Resource> getVarietiesByCrop(int cropTypeId) {

		// raw query.
		final String RAW_QUERY = "SELECT st.*, ct.%s FROM %s st INNER JOIN %s ct ON st.%s = ct.%s WHERE st.cropTypeId = "
				+ cropTypeId + " ORDER BY %s ASC";
		// substitutes values in the query.
		String processedQuery = String.format(RAW_QUERY,
				RealFarmDatabase.COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE,
				RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				RealFarmDatabase.TABLE_NAME_CROPTYPE,
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
				RealFarmDatabase.COLUMN_NAME_CROPTYPE_ID,
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
				r.setImage1(c.getInt(3));
				r.setAudio(c.getInt(4));
				r.setBackgroundImage(c.getInt(6));
				tmpList.add(r);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: add optimization
	// TODO: optimise date selector
	public List<Resource> getVarietiesByPlotAndSeason(long plotId) {

		// creates the result list.
		List<Resource> tmpList = new ArrayList<Resource>();
		final String MY_QUERY = "SELECT DISTINCT seedTypeId FROM action WHERE plotId = "
				+ plotId
				+ " AND date LIKE '"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ "-%' AND actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_SOW_ID
				+ " ORDER BY seedTypeId ASC";
		mDatabase.open();
		Cursor cursor = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (cursor.moveToFirst()) {

			do {
				// raw query.
				final String RAW_QUERY = "SELECT st.*, ct.%s FROM %s st INNER JOIN %s ct ON st.%s = ct.%s WHERE st.id = "
						+ cursor.getInt(0);
				// substitutes values in the query.
				String processedQuery = String.format(RAW_QUERY,
						RealFarmDatabase.COLUMN_NAME_CROPTYPE_BACKGROUNDIMAGE,
						RealFarmDatabase.TABLE_NAME_SEEDTYPE,
						RealFarmDatabase.TABLE_NAME_CROPTYPE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_CROPTYPE_ID,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID);

				// opens the database and executes the query
				Cursor c = mDatabase.rawQuery(processedQuery, new String[] {});

				Resource r = null;
				if (c.moveToFirst()) {
					r = new Resource();
					r.setId(c.getInt(0));
					r.setName(c.getString(1));
					r.setShortName(c.getString(2));
					r.setImage1(c.getInt(3));
					r.setAudio(c.getInt(4));
					r.setBackgroundImage(c.getInt(6));
					tmpList.add(r);
				}
				c.close();

			} while (cursor.moveToNext());
		}

		cursor.close();
		mDatabase.close();

		return tmpList;
	}

	public WeatherForecast getWeatherForecastByDate(String date) {

		// opens the database.
		mDatabase.open();

		// query all actions
		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_WEATHERFORECAST,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_ID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TIMESTAMP },

						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE
								+ " = '" + date + "'", null, null, null,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TIMESTAMP
								+ " ASC");

		WeatherForecast wf = null;
		if (c.moveToFirst()) {

			Log.d("RealFarmProvider",
					"total wf found with date: " + c.getCount());

			wf = new WeatherForecast(c.getInt(0), c.getString(1), c.getInt(2),
					c.getInt(3), c.getLong(4));

			Log.d("WF values: ", wf.toString());

		}

		c.close();
		mDatabase.close();

		return wf;
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
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TIMESTAMP },
						null, null, null, null,
						RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE
								+ " ASC");

		tmpList = new ArrayList<WeatherForecast>();

		WeatherForecast wf = null;
		if (c.moveToFirst()) {
			do {
				wf = new WeatherForecast(c.getInt(0), c.getString(1),
						c.getInt(2), c.getInt(3), c.getInt(4));
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
		return getWeatherForecasts(sDateFormat.format(startDate));
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
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_WEATHERTYPEID,
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TIMESTAMP },
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
						c.getInt(2), c.getInt(3), c.getLong(4));
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
	 * Gets the WeatherType that matches the given identifier.
	 * 
	 * @param weatherTypeId
	 *            the id to query for.
	 * 
	 * @return the WeatherType that matches the given id.
	 */
	public WeatherType getWeatherTypeById(int weatherTypeId) {

		// weather types are not in memory cache.
		if (mWeatherTypes == null) {

			// initializes the list used to cache the seeds.
			mWeatherTypes = new LinkedHashMap<Integer, WeatherType>();
			mDatabase.open();

			Cursor c = mDatabase.getAllEntries(
					RealFarmDatabase.TABLE_NAME_WEATHERTYPE, new String[] {
							RealFarmDatabase.COLUMN_NAME_WEATHERTYPE_ID,
							RealFarmDatabase.COLUMN_NAME_WEATHERTYPE_NAME,
							RealFarmDatabase.COLUMN_NAME_WEATHERTYPE_IMAGE,
							RealFarmDatabase.COLUMN_NAME_WEATHERTYPE_AUDIO });

			WeatherType wt = null;
			if (c.moveToFirst()) {
				do {
					wt = new WeatherType(c.getInt(0), c.getString(1),
							c.getInt(2), c.getInt(3));

					// adds the weather type to the hash
					mWeatherTypes.put(wt.getId(), wt);

					Log.d("weathertype: ", wt.toString());

				} while (c.moveToNext());

			}
			c.close();
			mDatabase.close();
		}

		// returns the WeatherType matching the id.
		return mWeatherTypes.get(weatherTypeId);
	}

	public String[] getYieldData(List<String> resources) {

		String MY_QUERY = "SELECT MAX(yieldInQtPerAcre) as max, MIN(yieldInQtPerAcre) as min, COUNT(id) as count, SUM(yieldInQtPerAcre) as num FROM yieldAgg WHERE seedTypeId='1' and placeId='1' ";
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i) != null) {
				MY_QUERY += resources.get(i);
			}
		}
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		String[] result = new String[c.getColumnCount()];
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getColumnCount(); i++) {
				result[i] = c.getString(i);
			}
		}
		c.close();
		mDatabase.close();
		return result;
	}

	public boolean hasLiked(int advicePieceId, long userId) {
		final String MY_QUERY = "SELECT COUNT(id) FROM action WHERE actionTypeId = "
				+ RealFarmDatabase.ACTION_TYPE_PLAN_ID
				+ " AND userId = "
				+ userId + " AND resource1Id = " + advicePieceId;
		mDatabase.open();
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});
		if (c.moveToFirst()) {
			if (c.getInt(0) == 0)
				return false;
			return true;
		}
		c.close();
		mDatabase.close();
		return false;
	}

	public long setActionStatus(long actionId, int status) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENDSTATUS, status);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_ACTION, args,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "='" + actionId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setRecommendationUnread(long id, int isUnread) {
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ISUNREAD, isUnread);

		long result;

		mDatabase.open();

		result = mDatabase
				.update(RealFarmDatabase.TABLE_NAME_RECOMMENDATION, args,
						RealFarmDatabase.COLUMN_NAME_RECOMMENDATION_ID + "="
								+ id, null);

		mDatabase.close();

		return result;

	}

	/**
	 * Enables or disables the plot with the selected id. If isEnabled is 1, the
	 * plot is enabled, otherwise it gets disabled.
	 * 
	 * @param plotId
	 *            the id of the plot to modify.
	 * @param userId
	 *            the id of the owner of the plot.
	 * @param isEnabled
	 *            the new state of the plot
	 * 
	 * @return the number of rows modified.
	 */
	public long setPlotEnabled(long plotId, long userId, int isEnabled) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ISENABLED, isEnabled);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + "='" + plotId + "' AND "
						+ RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "='"
						+ userId + "'", null);

		mDatabase.close();
		return result;
	}

	public long setPlotStatus(long plotId, int status) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SENDSTATUS, status);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + "='" + plotId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setUserEnabled(long userId, int isEnabled) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ISENABLED, isEnabled);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + "='" + userId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setUserStatus(long userId, int status) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_SENDSTATUS, status);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + "='" + userId + "'",
				null);

		mDatabase.close();
		return result;
	}

	/**
	 * Adds the listener used to detect when the WeatherForecast table is
	 * modified.
	 * 
	 * @param listener
	 *            the Data Change Listener.
	 */
	public void setWeatherForecastDataChangeListener(
			OnWeatherForecastDataChangeListener listener) {
		sWeatherForecastDataListener = listener;
	}
}
