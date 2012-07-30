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
import com.commonsensenet.realfarm.model.Sowing;
import com.commonsensenet.realfarm.model.Spraying;
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

	// TODO: check that date is respected.
	public long addWeatherForecast(String date, int value, String type) {

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
			sWeatherForecastDataListener.onDataChanged(date, value, type);
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

			ActionName an = null;
			if (c.moveToFirst()) {
				do {
					an = new ActionName(c.getInt(0), c.getString(1),
							c.getString(2), c.getInt(3), c.getInt(4));
					mAllActionNames.add(an);

					Log.d("action name values: ", an.toString());

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
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
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
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIQUEIDSERVER,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID });

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getInt(3), c.getInt(4), c.getInt(5), c.getString(6),
						c.getInt(7), c.getString(8), c.getString(9),
						c.getString(10), c.getInt(11), c.getString(12),
						c.getString(13), c.getInt(14), c.getInt(15),
						c.getString(16), c.getString(17), c.getString(18),
						c.getInt(19), c.getInt(20), c.getInt(21));
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
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
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
						RealFarmDatabase.COLUMN_NAME_ACTION_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNIQUEIDSERVER,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID + " = " + plotId
						+ "", null, null, null, null);

		Action a = null;
		if (c.moveToFirst()) {
			do {
				a = new Action(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getInt(3), c.getInt(4), c.getInt(5), c.getString(6),
						c.getInt(7), c.getString(8), c.getString(9),
						c.getString(10), c.getInt(11), c.getString(12),
						c.getString(13), c.getInt(14), c.getInt(15),
						c.getString(16), c.getString(17), c.getString(18),
						c.getInt(19), c.getInt(20), c.getInt(21));
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
						c.getInt(3), c.getInt(4), c.getInt(5), c.getString(6),
						c.getInt(7), c.getString(8), c.getString(9),
						c.getString(10), c.getInt(11), c.getString(12),
						c.getString(13), c.getInt(14), c.getInt(15),
						c.getString(16), c.getString(17), c.getString(18),
						c.getInt(19), c.getInt(20), c.getInt(21));
				tmpActions.add(a);

				Log.d("values: ", a.toString());
			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpActions;
	}

	public List<AggregateItem> getAggregateItems(int actionNameId,
			String groupField) {
		final String MY_QUERY = "SELECT a.actionNameId, COUNT(p.userId) as users, a.%2$s FROM action a LEFT JOIN plot p ON a.plotId = p.id WHERE a.actionNameId= %1$d GROUP BY a.actionNameId, a.%2$s ORDER BY a.%2$s ASC";

		List<AggregateItem> tmpList = new ArrayList<AggregateItem>();

		mDatabase.open();

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

	public ArrayList<DialogData> getCrops() {
		final String MY_QUERY = "SELECT name, id, resBg, audio, shortName FROM cropType ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(4));
				dd.setAudio(c.getInt(3));
				dd.setValue(c.getInt(1) + "");
				dd.setBackground(c.getInt(2));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public RealFarmDatabase getDatabase() {
		return mDatabase;
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

	public List<Sowing> getSowing() {

		mDatabase.open();
		int sow = RealFarmDatabase.ACTION_NAME_SOW_ID;

		List<Sowing> tmpList = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_INTERCROP,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + sow,
				null, null, null, null);

		tmpList = new ArrayList<Sowing>();

		if (c.moveToFirst()) {
			do {
				tmpList.add(new Sowing(c.getInt(0), sow, c.getInt(1), c
						.getInt(2), c.getString(3), c.getInt(4), c.getInt(5), c
						.getInt(6), c.getString(7), c.getString(8), c
						.getString(9), c.getInt(10)));

				String log = "action id: " + c.getInt(0) + " action name id: "
						+ sow + " ,quantity1: " + c.getInt(1)
						+ " ,seed type id: " + c.getString(2) + " ,units"
						+ c.getString(3) + "plotId " + c.getInt(4) + " sent "
						+ c.getInt(5) + " admin " + c.getInt(6) + "day  "
						+ c.getString(7) + "treatment  " + c.getString(8)
						+ "intercrop  " + c.getString(9) + " action user id"
						+ c.getInt(10) + "\r\n";
				Log.d("Sowing values: ", log);

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public List<Fertilizing> getfertilizing() {

		mDatabase.open();
		int fert = RealFarmDatabase.ACTION_NAME_FERTILIZE_ID;

		List<Fertilizing> tmpList = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_ACTION,
				new String[] { RealFarmDatabase.COLUMN_NAME_ACTION_ID,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_TYPEOFFERTILIZER,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + fert,
				null, null, null, null);

		tmpList = new ArrayList<Fertilizing>();

		if (c.moveToFirst()) {
			do {
				// tmpList.add(new Fertilizing(c.getInt(0), c.getString(1), c
				// .getInt(2), c.getString(3), c.getString(4), c
				// .getString(5), c.getInt(6), c.getInt(7), c.getInt(8), c
				// .getInt(9), c.getString(10)));

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
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_HARVESTFEEDBACK,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + harv,
				null, null, null, null);

		tmpList = new ArrayList<Harvesting>();

		if (c.moveToFirst()) {
			do {

				// tmpList.add(new Harvesting(c.getInt(0), c.getString(1), c
				// .getInt(2), c.getInt(3), c.getString(4),
				// c.getString(5), c.getInt(6), c.getInt(7), c
				// .getString(8), c.getInt(9), c.getInt(10), c
				// .getString(11)));

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
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATE_METHOD,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
						+ irrigate, null, null, null, null);

		//
		tmpList = new ArrayList<Irrigation>();

		if (c.moveToFirst()) {
			do {
				// tmpList.add(new Irrigation(c.getInt(0), c.getString(1), c
				// .getInt(2), c.getString(3), c.getString(4),
				// c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c
				// .getString(9), c.getString(10)));

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

		MarketPrice mp = null;
		if (c.moveToFirst()) {
			do {
				mp = new MarketPrice(c.getInt(0), c.getString(1),
						c.getString(2), c.getInt(3), c.getInt(4));
				tmpList.add(mp);

				Log.d("MP values: ", mp.toString());

			} while (c.moveToNext());
		}
		Log.d("done: ", "finished MP getdata");
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public ArrayList<DialogData> getPesticide() {

		final String MY_QUERY = "SELECT name, audio, type, shortName FROM pesticide ORDER BY type, id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

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
				dd.setValue(dd.getName());
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return tmpList;
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
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE },
				RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "=" + delete,
				null, null, null, null);

		tmpList = new ArrayList<Plot>();

		Plot p = null;
		if (c.moveToFirst()) {
			do {

				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), delete, c.getInt(5),
						c.getInt(6), c.getFloat(7));

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
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE }, null, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7), c.getFloat(8));
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
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7), c.getFloat(8));
				tmpList.add(p);

				Log.d("plot values: ", p.toString());
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
						RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE,
						RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG,
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE },
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + "=" + plotId, null,
				null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7), c.getFloat(8));

				Log.d("plot values: ", p.toString());
			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();
		return p;
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
						RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
						RealFarmDatabase.COLUMN_NAME_PLOT_SIZE },
				RealFarmDatabase.COLUMN_NAME_PLOT_USERID + "=" + userId
						+ " AND "
						+ RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG + "="
						+ delete + "", null, null, null, null);

		Plot p = null;
		if (c.moveToFirst()) {
			do {
				p = new Plot(c.getInt(0), c.getInt(1), c.getInt(2),
						c.getString(3), c.getString(4), c.getInt(5),
						c.getInt(6), c.getInt(7), c.getFloat(8));
				tmpList.add(p);
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
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "="
						+ problem, null, null, null, null);

		tmpList = new ArrayList<Problem>();

		Problem p = null;
		if (c.moveToFirst()) {
			do {

				p = new Problem(c.getInt(0), c.getString(1), c.getString(2),
						c.getInt(3), c.getInt(4), c.getString(5), c.getInt(6),
						c.getInt(7), c.getString(8));
				tmpList.add(p);

				Log.d("Problem values: ", p.toString());

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
				final String MY_QUERY2 = "SELECT res FROM problemType WHERE id = "
						+ c.getInt(3);
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

	// TODO: implement optimization
	public SeedType getSeedById(int seedId) {

		SeedType res = null;
		mDatabase.open();

		seedId = 1;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_CROP,
				new String[] { RealFarmDatabase.COLUMN_NAME_CROP_NAME,
						RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE,
						RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
						RealFarmDatabase.COLUMN_NAME_CROP_RESOURCEBG },
				RealFarmDatabase.COLUMN_NAME_CROP_ID + "=" + seedId, null,
				null, null, null);

		if (c.moveToFirst()) {
			res = new SeedType(seedId, c.getString(0), "", c.getInt(1),
					c.getInt(2), "", "", c.getInt(3));
		}

		c.close();
		mDatabase.close();
		return res;

	}

	public List<SeedType> getSeeds() {

		// seeds are not in cache
		if (mAllSeeds == null) {

			mAllSeeds = new ArrayList<SeedType>();
			mDatabase.open();

			Cursor c = mDatabase.getAllEntries(
					RealFarmDatabase.TABLE_NAME_CROP, new String[] {
							RealFarmDatabase.COLUMN_NAME_CROP_ID,
							RealFarmDatabase.COLUMN_NAME_CROP_NAME,
							RealFarmDatabase.COLUMN_NAME_CROP_RESOURCE,
							RealFarmDatabase.COLUMN_NAME_SEEDTYPE_AUDIO,
							RealFarmDatabase.COLUMN_NAME_CROP_RESOURCEBG });

			if (c.moveToFirst()) {
				do {
					SeedType s = new SeedType(c.getInt(0), c.getString(1), "",
							c.getInt(2), c.getInt(3), "", "", c.getInt(4));
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
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY2,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_SELLINGPRICE,
						RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + sell,
				null, null, null, null);

		tmpList = new ArrayList<Selling>();

		if (c.moveToFirst()) {
			do {
				// tmpList.add(new Selling(c.getInt(0), c.getString(1), c
				// .getInt(2), c.getInt(3), c.getString(4),
				// c.getString(5), c.getInt(6), c.getInt(7), c.getInt(8),
				// c.getString(9), c.getString(10), c.getInt(11), c
				// .getInt(12), c.getString(13)));

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
						RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1,
						RealFarmDatabase.COLUMN_NAME_ACTION_UNITS,
						RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID,
						RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_SENT,
						RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN,
						RealFarmDatabase.COLUMN_NAME_ACTION_DATE,
						RealFarmDatabase.COLUMN_NAME_ACTION_PESTICIDETYPE,
						RealFarmDatabase.COLUMN_NAME_ACTION_USERID },
				RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID + "=" + spray,
				null, null, null, null);

		//
		tmpList = new ArrayList<Spraying>();

		if (c.moveToFirst()) {
			do {
				// tmpList.add(new Spraying(c.getInt(0), c.getString(1), c
				// .getInt(2), c.getString(3), c.getString(4),
				// c.getInt(5), c.getInt(6), c.getString(7), c.getInt(8),
				// c.getInt(9), c.getString(10), c.getString(11)));

			} while (c.moveToNext());

		}
		c.close();
		mDatabase.close();

		return tmpList;
	}

	public ArrayList<DialogData> getUnits(int id) {
		final String MY_QUERY = "SELECT DISTINCT name, resource, audio FROM unit WHERE action = "
				+ id
				+ " OR action = "
				+ RealFarmDatabase.ACTION_NAME_ALL_ID
				+ " ORDER BY id ASC";

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

	public User getUserById(int userId) {
		mDatabase.open();
		User tmpUser = null;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINAFLAG,
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
						RealFarmDatabase.COLUMN_NAME_USER_ADMINAFLAG,
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

		List<User> tmpList;

		Cursor c = mDatabase.getEntries(RealFarmDatabase.TABLE_NAME_USER,
				new String[] { RealFarmDatabase.COLUMN_NAME_USER_ID,
						RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_LASTNAME,
						RealFarmDatabase.COLUMN_NAME_USER_MOBILE,
						RealFarmDatabase.COLUMN_NAME_USER_IMAGEPATH,
						RealFarmDatabase.COLUMN_NAME_USER_ADMINAFLAG,
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
						RealFarmDatabase.COLUMN_NAME_USER_ADMINAFLAG,
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

	public ArrayList<DialogData> getVarieties() {
		final String MY_QUERY = "SELECT name, id, audio, masterId, shortName FROM seedType ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT resBg FROM cropType WHERE id = "
						+ c.getInt(3);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();

				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(4));
				dd.setAudio(c.getInt(2));
				dd.setValue(c.getInt(1) + "");
				dd.setBackground(c2.getInt(0));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

		// final String MY_QUERY3 =
		// "SELECT name, id, resBg, audio FROM cropType WHERE cropType.id IN (SELECT cropType.id FROM cropType WHERE cropType.id NOT IN (SELECT seedType.resBg FROM seedType)) ORDER BY id ASC";

		c.close();
		mDatabase.close();

		return tmpList;
	}

	public ArrayList<DialogData> getVarieties1() { // TODO; get varieties on
													// this plot, in this season
													// (action_harvest, item 6)

		final String MY_QUERY = "SELECT name, id, audio, masterId, shortName FROM seedType ORDER BY id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				final String MY_QUERY2 = "SELECT resBg FROM cropType WHERE id = "
						+ c.getInt(3);
				Cursor c2 = mDatabase.rawQuery(MY_QUERY2, new String[] {});
				c2.moveToFirst();

				dd = new DialogData();
				dd.setName(c.getString(0));
				dd.setShortName(c.getString(4));
				dd.setAudio(c.getInt(2));
				dd.setValue(c.getInt(1) + "");
				dd.setBackground(c2.getInt(0));
				tmpList.add(dd);
			} while (c.moveToNext());
		}

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
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE },
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
								RealFarmDatabase.COLUMN_NAME_WEATHERFORECAST_TYPE },
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

	public ArrayList<DialogData> getDialogData(int dataType) {

		final String MY_QUERY = "SELECT name, shortName, res, res2, audio, value, number, resBg FROM dialogArrays WHERE type = "
				+ dataType + " ORDER BY type, id ASC";

		ArrayList<DialogData> tmpList = new ArrayList<DialogData>();

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		DialogData dd = null;
		if (c.moveToFirst()) {
			do {
				dd = new DialogData(c.getString(0), c.getString(1),
						c.getInt(2), c.getInt(3), c.getInt(4), String.valueOf(c
								.getInt(5)), c.getInt(7), c.getInt(6));
				tmpList.add(dd);
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

	// main crop info corresponds to seed type id
	public long insertPlot(int userId, int seedTypeId, String imagePath,
			String soilType, float size, int deleteFlag, int adminFlag) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_USERID, userId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SIZE, size);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_IMAGEPATH, imagePath);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_SOILTYPE, soilType);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, deleteFlag);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_ADMINFLAG, adminFlag);
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_TIMESTAMP,
				new Date().getTime());

		mDatabase.open();

		// inserts the values into the database
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
			String Seedvariety, String Cropvariety, int quantity1,
			int quantity2, String Units, String day, int userid, int plotid,
			String TypeFert, String Prob, String feedback, int sp,
			String QuaSeed, String type, int sent, int isAdmin, String treat,
			String pesttype) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		Calendar calendar = Calendar.getInstance();
		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID, actionnameid);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, Seedvariety);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID, Cropvariety);
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

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_PLOT_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_PLOT, args,
				RealFarmDatabase.COLUMN_NAME_PLOT_ID + " = '" + plotId + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setDeleteFlagForUser(int userid) {

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 1);

		long result;

		mDatabase.open();

		result = mDatabase.update(RealFarmDatabase.TABLE_NAME_USER, args,
				RealFarmDatabase.COLUMN_NAME_USER_ID + " = '" + userid + "'",
				null);

		mDatabase.close();
		return result;
	}

	public long setFertilizing(long plotId, int quantity1,
			String typeOfFertilizer, String units, String day, int sent,
			int admin) {

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
			String Units, String day, String harvfeedback, int sent, int admin,
			int varietyId) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_HARVEST_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, varietyId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
				getCropIdFromSeedId(varietyId));
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

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_REPORT_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PLOTID, plotId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE, probType);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SENT, sent);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ISADMIN, admin);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_DATE, day);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
				getCropIdFromSeedId(Integer.parseInt(crop)));
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
			String units, String day, String treat, int sent, int admin,
			String intercrop) {

		ContentValues args = new ContentValues();

		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_ACTIONNAMEID,
				RealFarmDatabase.ACTION_NAME_SOW_ID);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_QUANTITY1, quantity);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID, seedTypeId);
		args.put(RealFarmDatabase.COLUMN_NAME_ACTION_CROPTYPEID,
				getCropIdFromSeedId(seedTypeId));
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

		ContentValues args = new ContentValues();
		args.put(RealFarmDatabase.COLUMN_NAME_USER_MOBILE, deviceId);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_FIRSTNAME, firstname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_LASTNAME, lastname);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_DELETEFLAG, 0);
		args.put(RealFarmDatabase.COLUMN_NAME_USER_ADMINAFLAG, 0);

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
		if ((result > 0) && (RealFarmDatabase.MAIN_USER_ID == -1)) {
			RealFarmDatabase.MAIN_USER_ID = (int) result;
		}

		mDatabase.close();

		return result;
	}

	public void setWeatherForecastDataChangeListener(
			OnDataChangeListener listener) {
		sWeatherForecastDataListener = listener;
	}

	public int getFertIdIdFromFertilizer(String text) {

		int fert_id = 0;
		final String MY_QUERY = "SELECT * FROM fertilizer";

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				if (c.getString(1).equals(text)) {
					fert_id = c.getInt(0);
				}

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return fert_id;

	}

	public int getProblemIdFromProblem(String text) {

		int problemId = 0;
		final String MY_QUERY = "SELECT * FROM problem";

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				if (c.getString(1).equals(text)) {
					problemId = c.getInt(0);

				}

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return problemId;

	}

	public int getUnitIdIdFromUnit(String text) {

		int unitId = 0;
		final String MY_QUERY = "SELECT * FROM unit";

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				if (c.getString(1).equals(text)) {
					unitId = c.getInt(0);
				}

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return unitId;

	}

	public int getPestIdIdIdFromPest(String text) {

		int pestId = 0;
		final String MY_QUERY = "SELECT * FROM pesticide";

		mDatabase.open();

		Cursor c = mDatabase.rawQuery(MY_QUERY, new String[] {});

		if (c.moveToFirst()) {
			do {
				if (c.getString(1).equals(text)) {
					pestId = c.getInt(0);
				}

			} while (c.moveToNext());
		}

		c.close();
		mDatabase.close();

		return pestId;
	}
}
