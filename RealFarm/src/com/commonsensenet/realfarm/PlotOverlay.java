package com.commonsensenet.realfarm;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class PlotOverlay extends Overlay {
	private Context mContext;
	private ManageDatabase db;
	private String user;
	private int time;
	private int x1, y1, x2, y2, x3, y3, x4, y4;

	PlotOverlay(ManageDatabase database, Context context){
		this.db = database;
		mContext = context;
		
		// Define database
    	db = new ManageDatabase(mContext);
		db.open();
		db.initValues();
		db.close();
		
	}

	
	@Override public void draw(Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);
		
		 Cursor c;
	        
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
         * Define points of figure to draw
         */
        Point screenCoords = new Point();
        Point screenCoords1 = new Point();
        Point screenCoords2 = new Point();
        Point screenCoords3 = new Point();
        
		
       /*
        * Load set of points to draw in overlays
        */
        db.open();
        c = db.GetAllEntries();

       
        if (c.getCount() > 0)
        {
        	c.moveToFirst();
        	do {
        		
        		int x1 = c.getInt(1);
        		int y1 = c.getInt(2);
        		int x2 = c.getInt(3);
        		int y2 = c.getInt(4);
        		int x3 = c.getInt(5);
        		int y3 = c.getInt(6);
        		int x4 = c.getInt(7);
        		int y4 = c.getInt(8);
        		
        		/*
                 * Draw set of overlays
                 */
                
                GeoPoint ckPura = new GeoPoint(x1,y1);
                GeoPoint ckPura1 = new GeoPoint(x2,y2);
                GeoPoint ckPura2 = new GeoPoint(x3,y3);
                GeoPoint ckPura3 = new GeoPoint(x4,y4);
                
                mapView.getProjection().toPixels(ckPura, screenCoords);
                mapView.getProjection().toPixels(ckPura1, screenCoords1);
                mapView.getProjection().toPixels(ckPura2, screenCoords2);
                mapView.getProjection().toPixels(ckPura3, screenCoords3);
                     
                /*
                 * Define path
                 */
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(screenCoords.x,screenCoords.y);
                path.lineTo(screenCoords1.x,screenCoords1.y);
                path.lineTo(screenCoords2.x,screenCoords2.y);
                path.lineTo(screenCoords3.x,screenCoords3.y);
                path.lineTo(screenCoords.x,screenCoords.y);
                path.close();
                
                /*
                 * Change paint style depending on user
                 */
                
                String user = c.getString(10);

                if (user.compareTo("Nitish")==0)
               		paint.setARGB(100, 100, 100, 100);
                else
                	paint.setARGB(100, 200, 200, 100);
                
                /*
                 * Draw the filled form
                 */
                canvas.drawPath(path, paint);
                
                
                
        	} 
        	while (c.moveToNext());
        } 

        // Close database
        db.close();
		
	}
	
	@Override public boolean onTap(GeoPoint p, MapView mapView){
		
		// Need to test where the tap is in the one of the above defined areas and which one		
        //Toast.makeText(mContext, "Hell yeah",Toast.LENGTH_SHORT).show();

		
		return true;
	}
	
}
