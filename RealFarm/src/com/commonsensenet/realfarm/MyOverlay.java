package com.commonsensenet.realfarm;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.commonsensenet.realfarm.realFarmMainActivity.PopupPanel;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;
	MapView map;
	private PopupPanel panel;
	
	
    /**
     * Constructor 
     * @param defaultMarker
     */
	public MyOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
		}

	/**
	 * Add Overlay item
	 * @param OverlayItem overlay
	 * @author Julien Freudiger
	 */
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	/**
	 * Create Overlay item
	 * @param int i index of overlay
	 * @return OverlayItem 
	 * @author Julien Freudiger
	 */
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	  
	}
	
	/**
	 * Obtain number of overlays
	 * @return int number of overlays 
	 * @author Julien Freudiger
	 */
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	/**
	 * Contstructor
	 * @param defaultMarker
	 * @param context
	 */
	public MyOverlay(Drawable defaultMarker, Context context, MapView mapView, PopupPanel RPanel) {

		super(boundCenterBottom(defaultMarker));

		mContext = context;
		map = mapView;
		panel = RPanel;

	}

	/**
	 * Method called every time overlay is updated
	 * @param Canvas canvas that defines the canvas on which to draw
	 * @param MapView mapview that defines the mapview on which to add overlay
	 * @param boolean shadow that defines whether to draw shadow or not
	 * @author Julien Freudiger
	 */
    @Override public void draw(Canvas canvas, MapView mapView, boolean shadow) {

        super.draw(canvas, mapView, shadow);

        
    }

	
    /**
     * Convert lat,lon to geopoint
     * @param lat
     * @param lon
     * @return GeoPoint
     */
    private GeoPoint getPoint(double lat, double lon) {
    	return(new GeoPoint((int)(lat*1000000.0), (int)(lon*1000000.0)));
    }

    
    /**
	 * Reacts on taps on overlay objects
	 * @param int index defines object being tapped
	 * @return boolean defining whether tap action was correctly performed
	 * @author Julien Freudiger
	 */
	@Override
	protected boolean onTap(int index) {

		OverlayItem item = mOverlays.get(index);

		GeoPoint geo = item.getPoint();
		Point pt = map.getProjection().toPixels(geo, null);
		View view = panel.getView();

		((TextView) view.findViewById(R.id.latitude)).setText(String.valueOf(geo.getLatitudeE6() / 1000000.0));
		((TextView) view.findViewById(R.id.longitude)).setText(String.valueOf(geo.getLongitudeE6() / 1000000.0));
		((TextView) view.findViewById(R.id.x)).setText(String.valueOf(pt.x));
		((TextView) view.findViewById(R.id.y)).setText(String.valueOf(pt.y));

		panel.show(pt.y * 2 > map.getHeight(), index);

		return true;
	}
	
	
	
}
