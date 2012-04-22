package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Iterator;
import java.util.Vector;

import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VIRecommendation;
import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VisualItem;
import com.commonsensenet.realfarm.model.Recommendation;

import android.app.Activity;
import android.content.Context;
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
	
	public DataAppearanceFactory(Context ctx, Activity activity) {
		this.ctx = ctx;
		this.activity = activity;
	}
	
	public BaseAdapter getDataAdapter( Vector<Object> dataItems ){
		
		Iterator<Object> runner = dataItems.iterator();
		Vector<VisualItem> visItems = new Vector<VisualItem>(dataItems.size());
		
		while( runner.hasNext() ){
			visItems.add(getVisualItem(runner.next()));
		}
		return new VIDataAdapter(visItems);
	}
	
	public VisualItem getVisualItem( Object dataItem ) {
		if( dataItem instanceof Recommendation ){
			return new VIRecommendation((Recommendation)dataItem);
		}
		return null;
	}
	
}
