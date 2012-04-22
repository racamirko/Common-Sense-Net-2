package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.view.View;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 *
 */
public interface VisualItem {

	public View populateView(View view); 

	public Object getDataItem();
	
}
