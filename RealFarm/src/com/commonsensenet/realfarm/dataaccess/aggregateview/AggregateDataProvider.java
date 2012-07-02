package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class AggregateDataProvider {

	public enum DateFilterModifier {
		AFTER, ALL, BEFORE, ON
	};

	public enum FilterType {
		ACTION, CROP, DATE, LOCATION, MESSAGE_TYPE, STATUS
	};

	public enum MessageType {
		ACTION, ADVICE, ALL, WARN, YIELD
	};

	public enum StatusType {
		ALL, READ, UNREAD
	};

	protected Activity activity;
	protected Context ctx;
	protected RealFarmProvider dataProvider;

	public AggregateDataProvider(Context ctx, Activity activity) {
		this.ctx = ctx;
		this.activity = activity;
		dataProvider = RealFarmProvider.getInstance(ctx);
	}

	public Vector<Object> getItems(AggregateDataFilter filter) {
		Vector<Object> results = new Vector<Object>();

		// TODO: filter interpretation and DB querying
		return results;
	}
}
