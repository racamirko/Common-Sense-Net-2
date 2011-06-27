package com.commonsensenet.realfarm.overlay;

import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.database.ObjectDatabase;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

/**
 * Class that defines popup format
 * 
 * @author Julien Freudiger
 */
public class PopupPanel {
	View popup;
	boolean isVisible = false;
	ViewGroup parent;
	MapView mapView;
	Context context;
	ObjectDatabase od;
	
	// Constructor
	public PopupPanel(int layout, MapView mapView, Context context, ObjectDatabase od) {
		this.mapView = mapView;
		this.context = context;
		this.od = od;
		
		parent = (ViewGroup) mapView.getParent();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popup = inflater.inflate(layout, parent, false);
		
		popup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hide();
			}
		});

	}

	View getView() {
		return (popup);	
	}

	void show(GeoPoint geo, Polygon mPolygon) {
		
		MapController myMapController = mapView.getController();
		if (mPolygon != null){ // tapped on polygon
			int[] average = mPolygon.getAverageLL();
			
			// Animate selected plot to bottom
			Projection P = mapView.getProjection();
			GeoPoint G = P.fromPixels(mapView.getWidth()/2, 0);
			GeoPoint G1 = mapView.getMapCenter();
			
			myMapController.animateTo(new GeoPoint(average[0] - (G1.getLatitudeE6() - G.getLatitudeE6()), average[1]));
		}
		else // tapped on standard item
		{
			myMapController.animateTo(geo);
		}
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 60, 0, 20);
		
		hide();
		
		// add popup window
		((ViewGroup) mapView.getParent()).addView(popup, lp);
		
		isVisible = true;

		// fill up layout of popup	
		LinearLayout contentPopup = (LinearLayout) popup.findViewById(R.id.contentpopup);
		contentPopup.removeAllViews();
		
		// check if intercropping
		
		// check if existing actions in diary
		
		// check list of actions
		Map<Integer, String> hm = od.manageActions(); // get list of actions
		
		for (Integer key : hm.keySet()){
			
			String name = hm.get(key);
			
			TextView b = new TextView(context);
			b.setText(name);
			contentPopup.addView(b);
			b.setId(key);
			
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(context, "Action pressed",Toast.LENGTH_SHORT).show();	
				}
			});
			
		}
		
	}

	void hide() {
		if (isVisible) {
			isVisible = false;
			((ViewGroup) popup.getParent()).removeView(popup);
		}
	}
}
