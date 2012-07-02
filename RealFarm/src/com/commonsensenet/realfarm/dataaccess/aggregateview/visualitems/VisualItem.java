package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 * 
 */
public interface VisualItem {

	public Object getDataItem();

	public int getLayoutTag();

	public void playAudio();

	public View populateView(View view, ViewGroup parent,
			LayoutInflater inflater);

}
