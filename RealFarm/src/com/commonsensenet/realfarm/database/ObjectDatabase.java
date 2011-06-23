package com.commonsensenet.realfarm.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.realFarm;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.Polygon;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ObjectDatabase {
	private ManageDatabase managedb;
	private Context context;
	private MapView mapView;
	private MapController myMapController;
	private ArrayList<GeoPoint> pointToFocus; 
	private Map<Integer, String> hm;
	
	public ObjectDatabase(realFarm mainApp, Context context, MapView mapView){
		this.context = context;
		this.mapView = mapView;
		
		myMapController = mapView.getController();
	    
		managedb = mainApp.setDatabase();
	    managedb.open();
	    managedb.close();
	}
	
	/**
	 * Manage submenu of plots
	 * 
	 * @author Julien Freudiger
	 * @return boolean indicating success/failure of operations
	 */
	
	public void managePlots(PlotOverlay myPlot, LinearLayout container){

		managedb.open();

		// Create objects to display in plotOverlay for all users
        Cursor c0 = managedb.GetAllEntries("plots", new String[] {"id", "userID"});
        
        if ((!c0.isClosed()) && (c0.getCount() > 0)) // if there are users in the table
        {
        	int i = 0;        	        	 
        	c0.moveToFirst();
        	
        	do { // for each plot, draw them        		
        		int id = c0.getInt(0);
	
        		// Read points from database for each user
        		Cursor c02 = managedb.GetEntries("points", new String[] {"x", "y"}, "plotID ="+ id, null, null, null, null);
        		
        		if (c02.getCount() > 0) // if there are points to plot
        		{
        			int j = 0;
        			c02.moveToFirst();
        			
                    int[] polyX = new int[c02.getCount()];
                    int[] polyY= new int[c02.getCount()];

        			do { // for each point in the plot, draw it
        				
        				int x1 = c02.getInt(0);
            			int y1 = c02.getInt(1);
            			
                        polyX[j] = x1;
                        polyY[j] = y1;
                        
                        j = j + 1;
        			}
        			while (c02.moveToNext());
        			
                    // Change overlay depending on user
        			myPlot.addOverlay(new Polygon(polyX, polyY, polyX.length, id));
        		}
                i = i + 1;
        	} 
        	while (c0.moveToNext());
        } 
		
		// query all plots of a given user
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telephonyManager.getDeviceId();

		Cursor c = managedb.GetEntries("users", new String[] {"id"}, "mobileNumber=" + deviceID, null, null, null, null);
    	
		// get main user id
		if (c.getCount()>0){
			c.moveToFirst();
			int id = c.getInt(0);
		}
		// todo: use main user id in the following
		Cursor c1 = managedb.GetEntries("plots", new String[] {"id"}, "userID ="+ 1, null, null, null, null);
		Polygon[] a = null;
		
		if (c1.getCount()>0) { // get all plots of main user
			c1.moveToFirst();
			int j = 0;
			a = new Polygon[c1.getCount()]; 
			
			do { // for each plot
				a[j] = myPlot.getOverlay(c1.getInt(0));
				j = j + 1;
			}
			while (c1.moveToNext());
		}
		managedb.close();

		
		if (a.length>0) // plots to show 
		{
			// Load layout in which to add buttons
			
			
			// for each plot entry, add button
			pointToFocus = new ArrayList<GeoPoint>(c1.getCount());
			for (int i = 0; i < c1.getCount(); i++) {
				TextView b = new TextView(context);
				b.setText("Plot " + i);
				
				int[] average = a[i].getAverageLL();
				pointToFocus.add(new GeoPoint(average[0], average[1]));
				b.setId(i);
				b.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						myMapController.animateTo(pointToFocus.get(v.getId()));
					}
				});
				
				container.addView(b);
			}
		}
		
	}
	
	public Map<Integer, String> manageActions(){
		managedb.open();
		
		// query all actions
		Cursor c = managedb.GetEntries("actionsNames", new String[] {"id", "name"}, null, null, null, null, null);
		c.moveToFirst();
		
		hm = new HashMap<Integer, String>(); 
		
		if (c.getCount()>0) {
			do { // for each action
				
				int id = c.getInt(0);
				String name = c.getString(1); //string 
				hm.put(id, name);
			}
			while (c.moveToNext());
		}

		managedb.close();
		
		return hm;
	}
}
