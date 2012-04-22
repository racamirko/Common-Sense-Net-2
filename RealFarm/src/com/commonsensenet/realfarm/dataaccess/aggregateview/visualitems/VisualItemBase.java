package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.view.View;
import android.view.View.OnClickListener;

abstract public class VisualItemBase implements VisualItem, OnClickListener {
	
	public View populateView(View view){
		throw new UnsupportedOperationException();
	}

	protected void populateLayoutAggregate(){
		throw new UnsupportedOperationException();
	}
	
	public void onClick(View v) {
		throw new UnsupportedOperationException();
	}
	
	public Object getDataItem(){
		throw new UnsupportedOperationException();
	}

}
