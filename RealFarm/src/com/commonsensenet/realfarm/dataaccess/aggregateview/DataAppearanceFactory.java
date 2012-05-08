package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Iterator;
import java.util.Vector;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VIAggrRecommendation;
import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VIRecommendation;
import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VisualItem;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.aggregate.AggregateRecommendation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * DataAppearanceFactory
 * 
 * Generates connections between visual items and actual data items. It's a mapping facility basically.
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 */
public class DataAppearanceFactory {
	protected Context ctx;
	protected Activity activity;
	protected LayoutInflater inflater;
	protected String logTag = "DataAppearanceFactory";
	protected RealFarmProvider dataProvider;
	
	public DataAppearanceFactory(Context ctx, Activity activity) {
		this.ctx = ctx;
		this.activity = activity;
		dataProvider = RealFarmProvider.getInstance(ctx);
		
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public BaseAdapter getDataAdapter( Vector<Object> dataItems ){
		Log.i(logTag, "Generating DataAdapter for " + dataItems.size() + " items");
		Iterator<Object> runner = dataItems.iterator();
		Vector<VisualItem> visItems = new Vector<VisualItem>(dataItems.size());
		
		while( runner.hasNext() ){
			visItems.add(getVisualItem(runner.next()));
		}
		return new VIDataAdapter(visItems, inflater);
	}
	
	public VisualItem getVisualItem( Object dataItem ) {
		if( dataItem instanceof AggregateRecommendation ){
			Log.d(logTag, "AggregatedRecommendation generated");
			return new VIAggrRecommendation((AggregateRecommendation)dataItem, ctx, dataProvider);
		}
		if( dataItem instanceof Recommendation ){
			Log.d(logTag, "Recommendation generated");
			return new VIRecommendation((Recommendation)dataItem, ctx, dataProvider);
		}
		Log.e(logTag, "Unsupported data type " + dataItem.getClass().getName() );
		throw new UnsupportedClassVersionError();
	}
	
}
