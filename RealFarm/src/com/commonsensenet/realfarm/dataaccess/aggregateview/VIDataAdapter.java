package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Vector;

import com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems.VisualItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class VIDataAdapter extends BaseAdapter {
	protected Vector<VisualItem> items;

	public VIDataAdapter( Vector<VisualItem> items ){
		this.items = items;
	}
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO not clear what this does
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
