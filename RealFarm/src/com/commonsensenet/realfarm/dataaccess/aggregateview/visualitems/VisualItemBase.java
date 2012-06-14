package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	protected RealFarmProvider dataProvider;
	protected Context ctx;
	
	public VisualItemBase(Context ctx,RealFarmProvider dataProvider){
		this.dataProvider = dataProvider;
		this.ctx = ctx;
	}
	
	public View populateView(View view, ViewGroup parent, LayoutInflater inflater){
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
	
	public int getLayoutTag(){
		Log.e(logTag, "getLayoutTag - generic visual item");
		throw new UnsupportedOperationException();
	}
	
}
