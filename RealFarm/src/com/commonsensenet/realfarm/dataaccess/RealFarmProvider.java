package com.commonsensenet.realfarm.dataaccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.model.Fertilizing;
import com.commonsensenet.realfarm.model.Harvesting;
import com.commonsensenet.realfarm.model.Irrigation;
import com.commonsensenet.realfarm.model.MarketPrice;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Problem;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.Selling;
import com.commonsensenet.realfarm.model.Spraying;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WeatherForecast;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;

public class RealFarmProvider {
	public abstract interface OnDataChangeListener {
		public abstract void onDataChanged(String data, int temperature,
				String type, int adminflag);
	}

	/** Date format used throughout the application. */
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
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

	/** Cached ActionNames to improve performance. */
	private List<ActionName> mAllActionNames;
	/** Cached seeds to improve performance. */
	private List<SeedType> mAllSeeds;
	/** Real farm database access. */
	private RealFarmDatabase mDatabase;

	protected RealFarmProvider(RealFarmDatabase database) {

		// database that will be used to handle data.
		mDatabase = database;

		// used to force creation.
		mDatabase.open();
		mDatabase.close();
	}

	public long addWeatherForecast(String date, int value, String type,
			int adminFlag) {

		Log.d("WF values: ", "in setdata");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TEMPERATURE,
				value);
		args.put(RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE, type);
		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_WEATHERFORECAST, args);

		mDatabase.close();

		// notifies any listener that the data changed.
		if (sWeatherForecastDataListener != null) {
			sWeatherForecastDataListener.onDataChanged(date, value, type,
					adminFlag);
		}
		Log.d("done: ", "wf setdata");
		return result;
	}

	public ActionName getActionNameById(int actionNameId) {

		mDatabase.open();

		ActionName tmpAction = null;

		Cursor c = mDatabase
				.getEntries(
						RealFarmDatabase.TABLE_NAME_ACTIONNAME,
						new String[] {
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
								RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO },
						RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID + "="
								+ actionNameId, null, null, null, null);

		if (c.moveToFirst()) {

			tmpAction = new ActionName(actionNameId, c.getString(0),
					c.getString(1), c.getInt(2), c.getInt(3));
			System.out.println(c.getString(0) +" "+ c.getString(1)+" "+ c.getInt(2)+" "+ c.getInt(3));
		}
		c.close();
		mDatabase.close();

		return tmpAction;
	}

	public List<ActionName> getActionNames() {

		if (mAllActionNames == null) {
			// opens the database.
			mDatabase.open();

			// query all actions
			Cursor c = mDatabase
					.getEntries(
							RealFarmDatabase.TABLE_NAME_ACTIONNAME,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_ID,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAME,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_NAMEKANNADA,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_ACTIONNAME_AUDIO },
							null, null, null, null, null);

			mAllActionNames = new ArrayList<ActionName>();

			if (c.moveToFirst()) {
				do {
					mAllActionNames.add(new ActionName(c.getInt(0), c
							.getString(1), c.getString(2), c.getInt(3), c
							.getInt(4)));

					String log = "ACTIONNAME_ID: " + c.getInt(0) + " ,NAME "
							+ c.getString(1) + " ,NAME_KANNADA: "
							+ c.getString(2) + " ,RESOURCE" + c.getInt(3)
							+ " AUDIO " + c.getInt(4) + " ADMINFLAG "
							+ c.getInt(5) + "\r\n";
					Log.d("action name values: ", log);

				} while (c.moveToNext());
			}

			c.close();
			mDatabase.close();

		}

		return mAllActionNames;
	}

	public List<Action> getActions() {

		List<Action> tmpActions = new ArrayList<Action>();

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP });

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getInt(3), c.getInt(4), c.getString(5), c.getInt(6),
						c.getString(7), c.getString(8), c.getString(9),
						c.getInt(10), c.getString(11), c.getString(12),
						c.getInt(13), c.getInt(14), c.getString(15),
						c.getString(16), c.getString(17), c.getInt(18));
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

		// String[] test = new String[] {
		// RealFarmDatabase.COLUMN_NAME_ACTION_ID,
		// RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
		// RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
		// RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
		// RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
		// RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
		// RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
		// RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
		// RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
		// RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
		// RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
		// RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
		// RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
		// RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
		// RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
		// RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
		// RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
		// RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE,
		// RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP };

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getInt(3), c.getInt(4), c.getString(5), c.getInt(6),
						c.getString(7), c.getString(8), c.getString(9),
						c.getInt(10), c.getString(11), c.getString(12),
						c.getInt(13), c.getInt(14), c.getString(15),
						c.getString(16), c.getString(17), c.getInt(18));
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
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + " = " + plotId
						+ "", null, null, null, null);

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getInt(3), c.getInt(4), c.getString(5), c.getInt(6),
						c.getString(7), c.getString(8), c.getString(9),
						c.getInt(10), c.getString(11), c.getString(12),
						c.getInt(13), c.getInt(14), c.getString(15),
						c.getString(16), c.getString(17), c.getInt(18));
				tmpActions.add(a);

				Log.d("values: ", a.toString());
			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	public RealFarmDatabase getDatabase() {
		return mDatabase;
	}

	public Cursor getFertilizer() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_FERTILIZER, new String[] {
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_ID,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_NAME,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_AUDIO,
						RealFarmDatabase.COLUMN_NAME_FERTILIZER_UNITID });

		// user exists in database
		if (c.moveToFirst()) {
			do {
				// adds the users into the list.
				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + " UNITID: " + c.getInt(3)
						+ "\r\n";
				Log.d("fertilizer: ", log);

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	public List<Fertilizing> getfertizing() {

		mDatabase.open();
		int fert = RealFarmDatabase.ACTION_NAME_FERTILIZE_ID;

		List<Fertilizing> tmpList = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + fert,
				null, null, null, null);

		tmpList = new ArrayList<Fertilizing>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Fertilizing(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4), c
						.getString(5), c.getInt(6), c.getInt(7), c.getInt(8), c
						.getInt(9), c.getString(10)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,Type of fertilize: " + c.getString(3) + " ,units"
						+ c.getString(4) + "day " + c.getString(5)
						+ " user id " + c.getInt(6) + " plot id " + c.getInt(7)
						+ "sent  " + c.getInt(8) + " Is admin " + c.getInt(9)
						+ " action performed date " + c.getString(10) + "\r\n";
				Log.d("Fertilizing values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Harvesting> getharvesting() {

		mDatabase.open();
		int harv = RealFarmDatabase.ACTION_NAME_HARVEST_ID;

		List<Harvesting> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + harv,
				null, null, null, null);

		tmpList = new ArrayList<Harvesting>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Harvesting(c.getInt(0), c.getString(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), c.getInt(6), c.getInt(7), c
								.getString(8), c.getInt(9), c.getInt(10), c
								.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,quantity2" + c.getInt(3) + "units "
						+ c.getString(4) + " day " + c.getString(5)
						+ " user  id " + c.getInt(6) + " plot id "
						+ c.getInt(7) + " harvest feedback " + c.getString(8)
						+ "sent  " + c.getInt(9) + " Is admin " + c.getInt(10)
						+ " action performed date " + c.getString(11) + "\r\n";

				Log.d("harvesting values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Irrigation> getirrigate() {

		mDatabase.open();
		int irrigate = RealFarmDatabase.ACTION_NAME_IRRIGATE_ID;

		List<Irrigation> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATE_METHOD },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
						+ irrigate, null, null, null, null);

		//
		tmpList = new ArrayList<Irrigation>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Irrigation(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4),
						c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c
								.getString(9), c.getString(10)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,Action name id: " + irrigate
						+ " ,QUANTITY1: " + c.getInt(2)

						+ " ,units" + c.getString(3) + "day " + c.getString(4)
						+ " user id " + c.getInt(5) + " plot id " + c.getInt(6)
						+ "sent  " + c.getInt(7) + " Is admin " + c.getInt(8)
						+ " action performed date " + c.getString(9)
						+ " method " + c.getString(10) + "\r\n";
				Log.d("Irrigation values: ", log);

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
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE,
						RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG },
				null, null, null, null, null);

		tmpList = new ArrayList<MarketPrice>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new MarketPrice(c.getInt(0), c.getString(1), c
						.getString(2), c.getInt(3), c.getInt(4)));

				String log = "MP_ID: " + c.getInt(0) + " ,MP_date "
						+ c.getString(1) + " ,MP_TYPE: " + c.getString(2)
						+ " ,MP_value" + c.getInt(3) + "MP_admin flag "
						+ c.getInt(4) + "\r\n";
				Log.d("MP values: ", log);

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public Cursor getPesticides() {

		mDatabase.open();

		Cursor c = mDatabase.getAllEntries(
				RealFarmDatabase.TABLE_NAME_PESTICIDE, new String[] {
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_ID,
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_NAME,
						RealFarmDatabase.COLUMN_NAME_PESTICIDE_AUDIO });

		// user exists in database
		if (c.moveToFirst()) {
			do {
				// adds the users into the list.

				String log = "ID: " + c.getInt(0) + " ,NAME: " + c.getString(1)
						+ " AUDIO: " + c.getInt(2) + "\r\n";
				Log.d("pesticides: ", log);

			} while (c.moveToNext());
		}

		// closes the DB and the cursor.
		// c.close();
		mDatabase.close();

		return c;
	}

	public List<Plot> getPlotDelete(int delete) {

		mDatabase.open();

		List<Plot> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "=" + delete,
				null, null, null, null);

		tmpList = new ArrayList<Plot>();

		if (c.moveToFirst()) {
			do {

				tmpList.add(new Plot(c.getInt(0), c.getInt(1), c.getInt(2), c
						.getString(3), c.getString(4), delete, c.getInt(5), c
						.getInt(6)));

				String log = "PlotId: " + c.getInt(0) + " ,PlotUserId: "
						+ c.getInt(1) + " ,PlotSeedTypeId: " + c.getInt(2)
						+ "PlotImage: " + c.getString(3) + " ,SoilType: "
						+ c.getString(4)

						+ " ,deleteFlag: " + delete + " ,AdminFlag: "
						+ c.getInt(7) + "\r\n";
				Log.d("plot values: ", log);

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
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP }, null,
				null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));
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
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));
				tmpList.add(p);

				Log.d("plot values: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<AggregateItem> getAggregateItems(int actionNameId,
			String groupField) {
		final String MY_QUERY = "SELECT a.actionNameId, COUNT(p.userId) as users, a.%2$s FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionNameId= %1$d GROUP BY a.actionNameId, a.%2$s ORDER BY a.%2$s ASC";

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		mDatabase.open();
		
		//System.out.println("lala "+String.format(MY_QUERY, actionNameId, groupField));

		Cursor c = mDatabase.rawQuery(
				String.format(MY_QUERY, actionNameId, groupField),
				new String[] {});

		AggregateItem a = null;
		if (c.moveToFirst()) {
			do {
				a = new AggregateItem(c.getInt(0), c.getInt(1));
				// adds any additionally found column into the aggregate item
				// starts in 2 since actionNameId and users are ignored.
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

	public List<UserAggregateItem> getUserAggregateItem(int actionNameId,
			String selector) {

		final String QUERY;
		switch (actionNameId) {
		case RealFarmDatabase.ACTION_NAME_SOW_ID:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionNameId = %1$d AND a.seedTypeId = %2$s ORDER BY a.date DESC";
			break;
		case RealFarmDatabase.ACTION_NAME_IRRIGATE_ID:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionNameId = %1$d AND a.irrigateMethod = '%2$s' ORDER BY a.date DESC";
			break;
		default:
			QUERY = "SELECT u.*, a.date FROM action a, plot p, user u WHERE a.plotId = p.id AND p.userId = u.id AND a.actionNameId = %1$d AND a.seedTypeId = %2$s ORDER BY a.date DESC";
		}

		List<UserAggregateItem> tmpList = new ArrayList<UserAggregateItem>();
		mDatabase.open();

		Cursor c = mDatabase.rawQuery(
				String.format(QUERY, actionNameId, selector), new String[] {});

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
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND "
						+ RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "="
						+ delete + "", null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));
				tmpList.add(p);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	// modified(You can take seedtypyId corresponding to the userId and plotId)
	public List<Plot> getPlotsByUserIdAndPlotId(int userId, int plotId) {
		// opens the database.
		List<Plot> tmpList = new ArrayList<Plot>();

		mDatabase.open();

		// query all actions
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				new String[] { RealFarmDatabase.COLUMN_NAME_PLOT_ID,
						RealFarmDatabase.COLUMN_NAME_PLOT_USERID,
						RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND " + RealFarmDatabase.COLUMN_NAME_PLOT_ID + "="
						+ plotId + "", null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7));
				tmpList.add(p);

				Log.d("plot values: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return tmpList;
	}

	public List<Problem> getProblem() {

		mDatabase.open();
		int problem = RealFarmDatabase.ACTION_NAME_REPORT_ID;

		List<Problem> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
						+ problem, null, null, null, null);

		tmpList = new ArrayList<Problem>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Problem(c.getInt(0), c.getString(1), c
						.getString(2), c.getInt(3), c.getInt(4),
						c.getString(5), c.getInt(6), c.getInt(7), c
								.getString(8)));

				String log = "action id: " + c.getInt(0) + " ,Action type: "
						+ c.getString(1) + " ,Actionnameid: " + problem
						+ "day " + c.getString(2) + " user id " + c.getInt(3)
						+ " plot id " + c.getInt(4) + " Problem type "
						+ c.getString(5) + "sent  " + c.getInt(6)
						+ " Is admin " + c.getInt(7)
						+ " action performed date " + c.getString(8) + "\r\n";
				Log.d("Problem values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	// TODO: implement optimization
	public SeedType getSeedById(int seedId) { 

		SeedType res = null;
		mDatabase.open();
		/*Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_SEEDTYPE,
				new String[] { RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
						 RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
						 RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG },
				RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID + "=" + seedId, null,
				null, null, null);

		if (c.moveToFirst()) {
			res = new SeedType(seedId, c.getString(0), c.getString(1),
					c.getInt(2), c.getInt(3), c.getString(4), c.getString(5),
					c.getInt(6));
		}*/
		
		seedId = 1;
		
		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_CROP,
				new String[] {
					RealFarmDatabase.COLUMN_NAME_CROP_NAME,
					RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
					RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE_BG  },
					RealFarmDatabase.COLUMN_NAME_CROP_ID + "=" + seedId, null,
				null, null, null);

		if (c.moveToFirst()) {
			res = new SeedType(seedId, c.getString(0), "",
					c.getInt(1), c.getInt(2), "", "",
					c.getInt(3));
		}
		
		c.close();
		mDatabase.close();
		return res;

	}

	public List<SeedType> getSeeds() { /* Modified ? */

		// seeds are not in cache
		if (mAllSeeds == null) {

			mAllSeeds = new ArrayList<SeedType>();
			mDatabase.open();

			/*Cursor c = mDatabase
					.getAllEntries(
							RealFarmDatabase.TABLE_NAME_SEEDTYPE,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_ID,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAME,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_NAMEKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
									 RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETY,
									 RealFarmDatabase.COLUMN_NAME_SEEDTYPE_VARIETYKANNADA,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_RESOURCE_BG });
									
				if (c.moveToFirst()) {
				do {
					SeedType s = new SeedType(c.getInt(0), c.getString(1),
							c.getString(2), c.getInt(3), c.getInt(4),
							c.getString(5), c.getString(6), c.getInt(7));
					mAllSeeds.add(s);

					Log.d("seed type: ", s.toString());

				} while (c.moveToNext());

			}*/
			
			Cursor c = mDatabase
					.getAllEntries(
							RealFarmDatabase.TABLE_NAME_CROP,
							new String[] {
									RealFarmDatabase.COLUMN_NAME_CROP_ID,
									RealFarmDatabase.COLUMN_NAME_CROP_NAME,
									RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE,
									RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
									RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE_BG });

			if (c.moveToFirst()) {
				do {
					SeedType s = new SeedType(c.getInt(0), c.getString(1),
							"", c.getInt(2), c.getInt(3),
							"", "", c.getInt(4));
					mAllSeeds.add(s);

					Log.d("seed type: ", s.toString());

				} while (c.moveToNext());

			}
			c.close();
			mDatabase.close();
		}

		return mAllSeeds;
	}

	public List<Selling> getselling() {

		mDatabase.open();
		int sell = RealFarmDatabase.ACTION_NAME_SELL_ID;

		List<Selling> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + sell,
				null, null, null, null);

		tmpList = new ArrayList<Selling>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Selling(c.getInt(0), c.getString(1), c
						.getInt(2), c.getInt(3), c.getString(4),
						c.getString(5), c.getInt(6), c.getInt(7), c.getInt(8),
						c.getString(9), c.getString(10), c.getInt(11), c
								.getInt(12), c.getString(13)));

				String log = "action id: " + c.getInt(0) + " ,action type: "
						+ c.getString(1) + " ,quantity1: " + c.getInt(2)
						+ " ,quantity2: " + c.getInt(3) + " ,units"
						+ c.getString(4) + "day " + c.getString(5)
						+ " user id " + c.getInt(6) + " plot id " + c.getInt(7)
						+ " selling price " + c.getInt(8) + " quality of seed "
						+ c.getString(9) + " selling type " + c.getString(10)
						+ "sent  " + c.getInt(11) + " Is admin " + c.getInt(12)
						+ " action performed date " + c.getString(13) + "\r\n";
				Log.d("selling values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Spraying> getspraying() {

		mDatabase.open();
		int spray = RealFarmDatabase.ACTION_NAME_SPRAY_ID;

		List<Spraying> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + spray,
				null, null, null, null);

		//
		tmpList = new ArrayList<Spraying>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Spraying(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3), c.getString(4),
						c.getInt(5), c.getInt(6), c.getString(7), c.getInt(8),
						c.getInt(9), c.getString(10), c.getString(11)));

				String log = "action id: " + c.getInt(0) + " ,action type"
						+ c.getString(1) + " ,quantity1" + c.getString(2)

						+ " ,units" + c.getString(3) + "day " + c.getString(4)
						+ " user id " + c.getInt(5) + " plot id " + c.getInt(6)
						+ " ,Problem type" + c.getString(7) + "sent  "
						+ c.getInt(8) + " Is admin " + c.getInt(9)
						+ " action performed date " + c.getString(10)
						+ " Pesticide type " + c.getString(11) + "\r\n";
				Log.d("spraying values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public User getUserById(int userId) {
		mDatabase.open();
		User tmpUser = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = " + userId, null,
				null, null, null);

		// user exists in database
		if (c.moveToFirst()) {

			// tmpUser = new User(userId, c.getString(0), c.getString(1),
			// c.getString(2), c.getString(3));

			tmpUser = new User(userId, c.getString(0), c.getString(1),
					c.getString(2), c.getString(3), c.getInt(4), c.getInt(5),
					c.getInt(6));
		}

		c.close();
		mDatabase.close();

		return tmpUser;

	}

	public User getUserByMobile(String deviceId) {

		mDatabase.open();

		User tmpUser = null;
		String mobile;

		if (deviceId == null)
			mobile = RealFarmDatabase.DEFAULT_NUMBER;
		else
			mobile = deviceId;

		Cursor c = mDatabase
				.getEntries(RealFarmDatabase.TABLE_NAME_USER, new String[] {
						RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },

						RealFarmDatabase.COLUMN_NAME_USER_MOBILE + "= '"
								+ mobile + "'", null, null, null, null);

		if (c.moveToFirst()) { // user exists in database

			// tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
			// mobile, c.getString(3));

			tmpUser = new User(c.getInt(0), c.getString(1), c.getString(2),
					mobile, c.getString(3), c.getInt(4), c.getInt(5),
					c.getInt(6));
		}
		c.close();
		mDatabase.close();

		return tmpUser;
	}

	/**
	 * 
	 * @return integer number of users in the DB
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

	public List<User> getUserDelete(int delete) {

		mDatabase.open();
		// int delete=0;

		System.out.println("In getuserDelete");

		List<User> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_TIMESTAMP },
				RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG + "=" + delete,
				null, null, null, null);

		tmpList = new ArrayList<User>();

		User u = null;
		if (c.moveToFirst()) {
			do {
				u = new User(c.getInt(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), delete, c.getInt(5),
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
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG,
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
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE },
						null, null, null, null, null);

		tmpList = new ArrayList<WeatherForecast>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new WeatherForecast(c.getInt(0), c.getString(1), c
						.getInt(2), c.getString(3)));

				String log = "WF_ID: " + c.getInt(0) + " , WF_date "
						+ c.getString(1) + " , WF_temperature: " + c.getInt(2)
						+ " , WF_type" + c.getString(3);
				Log.d("WF values: ", log);

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished Wf getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getCrops() {
		final String MY_QUERY = "SELECT name, id, resBg, audio FROM cropType ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setAudio(c.getInt(3));
				dd.setValue(c.getInt(1)+"");
				dd.setBackground(c.getInt(2));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getVarieties() {
		final String MY_QUERY = "SELECT name, id, audio, resBg FROM seedType ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT resBg FROM cropType WHERE id = "+c.getInt(3);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();
				
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setAudio(c.getInt(2));
				dd.setValue(c.getInt(1)+"");
				dd.setBackground(c2.getInt(0));
				tmpList.add(dd);
			} while (c.moveToNext());
		}
		
		//final String MY_QUERY3 = "SELECT name, id, resBg, audio FROM cropType WHERE cropType.id IN (SELECT cropType.id FROM cropType WHERE cropType.id NOT IN (SELECT seedType.resBg FROM seedType)) ORDER BY id ASC";
		
		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getUnits(int id) {
		final String MY_QUERY = "SELECT DISTINCT name, resource, audio FROM unit WHERE action = "+id+" OR action = "+RealFarmDatabase.ACTION_NAME_ALL_ID+" ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setImage(c.getInt(1));
				dd.setAudio(c.getInt(2));
				dd.setValue(dd.getName());
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getFertilizers() {
		final String MY_QUERY = "SELECT DISTINCT name, audio, shortName FROM fertilizer ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(2));
				dd.setAudio(c.getInt(1));
				dd.setValue(dd.getName());
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getPesticide() {
		// final String MY_QUERY = "SELECT p.name, p.resource, p.audio, a.res FROM pesticide p, pesticideType a WHERE p.type = a.id ORDER BY p.type, p.id ASC";
		
		final String MY_QUERY = "SELECT name, audio, type, shortName FROM pesticide ORDER BY type, id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT res FROM pesticideType WHERE id = "+c.getInt(2);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();
				
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(3));
				dd.setImage(c2.getInt(0));
				dd.setAudio(c.getInt(1));
				dd.setValue(dd.getName());
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}
	
	public ArrayList<DialogData> getProblems() {
		final String MY_QUERY = "SELECT name, audio, res, masterId, shortName FROM problem ORDER BY masterId, id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();
		
		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT res FROM problemType WHERE id = "+c.getInt(3);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();
				
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(4));
				dd.setImage(c2.getInt(0));
				dd.setImage2(c.getInt(2));
				dd.setAudio(c.getInt(1));
				dd.setValue(dd.getName());
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	// main crop info corresponds to seed type id
	public long insertPlot(int userId, int seedTypeId, String imagePath,
			String soilType, int delete, int admin, int size) {

		// increases the current plot id
		System.out.println("SETPLOTNEW");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE, soilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, delete);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
				new Date().getTime());
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SIZE, size);

		// public static final String COLUMN_NAME_PLOT_CENTERX = "centerX";
		// public static final String COLUMN_NAME_PLOT_CENTERY = "centerY";
		// public static final String COLUMN_NAME_PLOT_DATE = "date";
		// public static final String COLUMN_NAME_PLOT_SEEDTYPEID =
		// "seedtypeId";
		mDatabase.open();

		long result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				args);

		mDatabase.close();

		return result;
	}

	public long removeAction(int id) {
		mDatabase.open();

		long result = mDatabase.deleteEntriesdb(
				RealFarmDatabase.TABLE_NAME_ACTION,
				RealFarmDatabase.COLUMN_NAME_ACTION_ID + "=" + id, null);

		mDatabase.close();
		return result;
	}

	public long setAction(int actionid, int actionnameid, String actiontype,
			String Seedvariety, int quantity1, int quantity2, String Units,
			String day, int userid, int plotid, String TypeFert, String Prob,
			String feedback, int sp, String QuaSeed, String type, int sent,
			int isAdmin, String treat, String pesttype) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.DATE, -15);
		System.out.println("SETACTIONNEW");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, actionnameid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, Seedvariety);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, quantity2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER, TypeFert);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, Prob);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK, feedback);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sp);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaSeed);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, type);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, isAdmin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
				dateFormat.format(calendar.getTime()));
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE, pesttype);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setDeleteFlagForPlot(int plotId) {

		System.out.println("in setDeleteFlagForPlot ");
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + " = '" + plotId + "'",
				null);
		// result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		// RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " = '"
		// + firstname + " AND " + RealFarmDatabase.COLUMN_NAME_USER_LASTNAME +
		// " = '"
		// + lastname , null);

		mDatabase.close();
		System.out.println(result);
		return result;
	}

	public long setDeleteFlagForUser(int userid) {

		System.out.println("in setDeleteFlagForUser ");
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = '" + userid + "'",
				null);
		// result = mDb.update(RealFarmDatabase.TABLE_NAME_USER, args,
		// RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME + " = '"
		// + firstname + " AND " + RealFarmDatabase.COLUMN_NAME_USER_LASTNAME +
		// " = '"
		// + lastname , null);

		mDatabase.close();
		System.out.println(result);
		return result;
	}

	public long setFertilizing(long plotId, int quantity1,
			String typeOfFertilizer, String units, String day, int sent,
			int admin) {

		System.out.println("SET fertilizing");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_FERTILIZE_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
				typeOfFertilizer);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	// day takes the date
	public long setHarvest(int userId, long plotId, int qua1, int qua2,
			String Units, String day, String harvfeedback, int sent, int admin) {

		System.out.println("SET harvest");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_HARVEST_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
				harvfeedback);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	// qua1 mapped to no of hours
	public long setIrrigation(long plotId, int qua1, String units, String day,
			String method, int sent, int admin) {

		System.out.println("SET IRRIGATION");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_IRRIGATE_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATE_METHOD, method);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setMarketPrice(int id, String date, String type, int value,
			int adminflag) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ID, id);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_DATE, date);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_TYPE, type);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_VALUE, value);
		args.put(RealFarmDatabase.COLUMN_NAME_MARKETPRICE_ADMINFLAG, adminflag);

		mDatabase.open();

		long result = mDatabase.insertEntries(
				RealFarmDatabase.TABLE_NAME_MARKETPRICE, args);

		mDatabase.close();

		return result;
	}

	public long setPlot(int userID) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userID);

		mDatabase.open();

		// add to plot list
		long result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_PLOT,
				args);

		mDatabase.close();
		return result;

	}

	public long setProblem(long plotId, String day, String probType, int sent,
			int admin, String crop) {

		System.out.println("SET PROBLEM");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_REPORT_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probType);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, crop);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	// day takes the date
	public long setselling(int plotId, int qua1, int qua2, String Units,
			String day, int sellingprice, String QuaOfSeed, String selltype,
			int sent, int admin) {

		System.out.println("SET selling");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_SELL_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, qua1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2, qua2);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE, sellingprice);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUALITYOFSEED, QuaOfSeed);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SELLTYPE, selltype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setSowing(long plotId, int quantity, int seedTypeId,
			String units, String day, String treat, int sent, int admin, String intercrop) {

		System.out.println("SET SOWING");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_SOW_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT, treat);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_INTERCROP, intercrop);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setSpraying(int userId, long plotId, int quantity1,
			String Units, String day, String probtype, int sent, int admin,
			String pesttype) {

		System.out.println("SET spraying");
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_SPRAY_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity1);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_UNITS, Units);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probtype);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE, pesttype);

		long result;

		mDatabase.open();

		result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				args);

		mDatabase.close();

		return result;
	}

	public long setUserInfo(String deviceId, String firstname, String lastname) {

		getUserCount();
		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 0);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ADMINFLAG, 0);

		long result;

		User user = getUserByMobile(deviceId);

		mDatabase.open();

		if (user != null) { // user exists in database => update
			result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
					RealFarmDatabase.COLUMN_NAME_USER_MOBILE + " = '"
							+ deviceId + "'", null);
		} else { // user must be created
			result = mDatabase.insertEntries(RealFarmDatabase.TABLE_NAME_USER,
					args);
		}

		// if main id is undefined and result is good
		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1))
			RealFarmDatabase.MAIN_USER_ID = (int) result;

		mDatabase.close();

		return result;
	}

	public void setWeatherForecastDataChangeListener(
			OnDataChangeListener listener) {
		sWeatherForecastDataListener = listener;
	}
}
