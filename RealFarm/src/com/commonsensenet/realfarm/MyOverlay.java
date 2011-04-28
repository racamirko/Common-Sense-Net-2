package com.commonsensenet.realfarm;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;
	
	public MyOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
		}

	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	public MyOverlay(Drawable defaultMarker, Context context) {
		  //super(boundCenterBottom(defaultMarker));
		  super(defaultMarker);
		  
		  mContext = context;
	


		    
		}

	
    @Override public void draw(Canvas canvas, MapView mapView, boolean shadow) {

        super.draw(canvas, mapView, shadow);

       /*
        * Load set of points to draw in overlays
        */
        
        /*
         * Draw set of overlays
         */
        
        
        /*
         * Define style of drawn overlay
         */
        Paint paint = new Paint();
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setDither(true);
        paint.setColor(android.graphics.Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
         
        
        /*
         * Define points of figure to draw
         */
        Point screenCoords = new Point();
        Point screenCoords1 = new Point();
        Point screenCoords2 = new Point();
        GeoPoint ckPura = new GeoPoint(14054563,77167003);
        GeoPoint ckPura1 = new GeoPoint(14054763,77167003);
        GeoPoint ckPura2 = new GeoPoint(14054763,77169003);
        
        mapView.getProjection().toPixels(ckPura, screenCoords);
        mapView.getProjection().toPixels(ckPura1, screenCoords1);
        mapView.getProjection().toPixels(ckPura2, screenCoords2);

             
        /*
         * Define path
         */
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        paint.setARGB (100, 100, 100, 100);
        path.moveTo(screenCoords.x,screenCoords.y);
        path.lineTo(screenCoords1.x,screenCoords1.y);
        path.lineTo(screenCoords2.x,screenCoords2.y);
        path.lineTo(screenCoords.x,screenCoords.y);
        path.close();
        
        /*
         * Draw the filled form
         */
        canvas.drawPath(path, paint);

    }

	
	
	@Override protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}
}
