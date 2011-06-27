package com.commonsensenet.realfarm.overlay;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PlotOverlay extends Overlay {
	
	private PopupPanel panel;
	private SlidingDrawer slidingDrawer;
	private SlidingDrawer newsSlidingDrawer;
	private Map<Integer, Polygon> hm; 
	
	public PlotOverlay(PopupPanel RPanel, SlidingDrawer slidingDrawer, SlidingDrawer newsSlidingDrawer){
		panel = RPanel;
		this.slidingDrawer = slidingDrawer;
		this.newsSlidingDrawer = newsSlidingDrawer;
		hm = new HashMap<Integer, Polygon>(); 
	}
	
	public void addOverlay(Polygon polygon){
		hm.put(polygon.getID(), polygon);
	}
	
	public Polygon getOverlay(int id){
		return hm.get(id);
	}
	
	@Override public void draw(Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);
		
        /*
         * Define style of overlay
         */
        Paint paint = new Paint();
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        
       /*
        * Load set of points to draw in overlays
        */

        for (Integer key : hm.keySet()){
        	Polygon mPolygon = hm.get(key);// get Polygon
            Path path = new Path(); // Draw path
            path.setFillType(Path.FillType.EVEN_ODD);
            
            int[] x = mPolygon.getX(mapView);
			int[] y = mPolygon.getY(mapView);
			
			for (int i=0; i<x.length; i++){
	            paint.setARGB(100, 228, 29, 29);
	            
	            if (i==0)
	                path.moveTo(x[i],y[i]); // for first point, move to it
	            else
	            	path.lineTo(x[i],y[i]); // For rest, add lines
	            
			}
        
            path.close();

            // Change paint style depending on user
            PathShape pShape = new PathShape(path, (float) 100, (float) 100);
            ShapeDrawable mShape = new ShapeDrawable(pShape); 
            mShape.getPaint().set(paint);
            mShape.setBounds(0, 0, 100, 100);
            
            mShape.draw(canvas);
            
        	}
   	
	}
	
	@Override public boolean onTap(GeoPoint p, MapView mapView){
		
        Projection mProjection = mapView.getProjection();
        Point touched = new Point();
        mProjection.toPixels(p, touched);
        
        if (hm.size()>0){
        
            for (Integer key : hm.keySet()){
            	Polygon mPolygon = hm.get(key);// get Polygon
	        	if (mPolygon.contains(touched.x, touched.y, mapView)){
	        	
	        		View view = panel.getView();

	        		((TextView) view.findViewById(R.id.latitude)).setText(String.valueOf(p.getLatitudeE6() / 1000000.0));
	        		((TextView) view.findViewById(R.id.longitude)).setText(String.valueOf(p.getLongitudeE6() / 1000000.0));
	        		((TextView) view.findViewById(R.id.x)).setText(String.valueOf(touched.x));
	        		((TextView) view.findViewById(R.id.y)).setText(String.valueOf(touched.y));
	        		
	        		panel.show(p, mPolygon);
	        	}
	        }
        }
        
        // When user clicks out of slidingDrawer, close the sliding drawer.
        if (slidingDrawer.isOpened()) {
        	slidingDrawer.animateClose();
        	return false;
        }
        
        if (newsSlidingDrawer.isOpened()) {
        	newsSlidingDrawer.animateClose();
        	return false;
        }
        
        
		return true;
	}
	
}
