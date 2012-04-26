package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

public class AggregateDataProvider {

	enum MessageType { ADVICE, ACTION, WARN, YIELD, ALL };
	enum StatusType { READ, UNREAD, ALL };
	enum FilterType { MESSAGE_TYPE, DATE, LOCATION, CROP, ACTION, STATUS };
	enum DateFilterModifier { BEFORE, AFTER, ON, ALL };
	
	public class AggregateDataFilter {
		protected MessageType typeFilter;
		protected Plot plot;
		protected Seed seed;
		protected User user;
		
		protected DateFilterModifier dateMod;
		protected Date dateValue;
		
		public AggregateDataFilter setMessageType( MessageType mesType ){
			typeFilter = mesType;
			return this;
		}
		
		public AggregateDataFilter setPlot( Plot plot ){
			this.plot = plot;
			return this;
		}

		public AggregateDataFilter setSeed( Seed seed ){
			this.seed = seed;
			return this;
		}

		public AggregateDataFilter setUser( User user ){
			this.user = user;
			return this;
		}

		public AggregateDataFilter setDate( Date date, DateFilterModifier dateMod ){
			this.dateValue = date;
			this.dateMod = dateMod;
			return this;
		}

	}
	
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
