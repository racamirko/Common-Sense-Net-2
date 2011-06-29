package com.commonsensenet.realfarm.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;
	MapView mapView;
	
	
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
	
	public void removeOverlay(OverlayItem overlay) {
	    mOverlays.remove(overlay);
	    populate();
	}
	
	public void removeAll() {
	    mOverlays.removeAll(mOverlays);
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
	public MyOverlay(Drawable defaultMarker, Context context, MapView mapView) {

		super(boundCenterBottom(defaultMarker));

		mContext = context;
		this.mapView = mapView;

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
    public GeoPoint getPoint(double lat, double lon) {
    	
    	final double PRECISION = 1000000.0;
    	return(new GeoPoint((int)(lat*PRECISION), (int)(lon*PRECISION)));
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
		Point pt = mapView.getProjection().toPixels(geo, null);
		
		final QuickAction qa = new QuickAction(mapView);
		ActionItem first = new ActionItem();
		first.setTitle(item.getSnippet());
		first.setIcon(mapView.getResources().getDrawable(R.drawable.ic_dialog_map));
		first.setId(1);
		qa.addActionItem(first);
		qa.show(new int[]{pt.x, pt.y, 2, 1});
		
		return true;
	}
	
	
	
}
