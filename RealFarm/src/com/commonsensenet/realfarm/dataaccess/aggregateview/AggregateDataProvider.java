package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class AggregateDataProvider {

	public enum MessageType { ADVICE, ACTION, WARN, YIELD, ALL };
	public enum StatusType { READ, UNREAD, ALL };
	public enum FilterType { MESSAGE_TYPE, DATE, LOCATION, CROP, ACTION, STATUS };
	public enum DateFilterModifier { BEFORE, AFTER, ON, ALL };
	
	protected Context ctx;
	protected Activity activity;
	protected RealFarmProvider dataProvider;
	
	public AggregateDataProvider(Context ctx, Activity activity){
		this.ctx = ctx;
		this.activity = activity;
		dataProvider = RealFarmProvider.getInstance(ctx);
	}
	
	public Vector<Object> getItems( AggregateDataFilter filter ){
		Vector<Object> results = new Vector<Object>();
		
		// TODO: filter interpretation and DB querying
		return results;
	}
	
}
