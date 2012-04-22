package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * VisualItemBase
 * 
 * TODO If no common methods emerge, eliminate this class. Usage of VisualItem interface is encouraged throughout the code.
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 *
 */
abstract public class VisualItemBase implements VisualItem, OnClickListener {
	private String logTag = "VisualItemBase";
	protected LayoutInflater inflater;
	
	public View populateView(View view){
		Log.e(logTag, "populateView - generic visual item");
		throw new UnsupportedOperationException();
	}

	public Object getDataItem(){
		Log.e(logTag, "getDataItem - generic visual item");
		throw new UnsupportedOperationException();
	}

	public void onClick(View v) {
		Log.e(logTag, "onClick - generic visual item");
		throw new UnsupportedOperationException();
	}
	
}
