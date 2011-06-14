package com.commonsensenet.realfarm;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.commonsensenet.realfarm.realFarmMainActivity.PopupPanel;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PlotOverlay extends Overlay {
	private Context mContext;
	private ManageDatabase db;
	private Polygon mPoly[];
	private PopupPanel panel;
	private int nbofPlots;
	private SlidingDrawer slidingDrawer;
	private boolean sliderClicked = false;

	PlotOverlay(ManageDatabase database, Context context, PopupPanel RPanel, SlidingDrawer slidingDrawer){
		this.db = database;
		mContext = context;
		panel = RPanel;
		this.slidingDrawer = slidingDrawer;
		
		// Load database if needed
		db.open();
		db.initValues();
		db.close();
		
	}

	
	@Override public void draw(Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);
		
		sliderClicked = false;
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
        * Load set of points to draw in overlays
        */
        
        db.open();
        
        // Get all plots
        c = db.GetAllEntries("plots", new String[] {"id", "userID"});
        
        if ((!c.isClosed()) && (c.getCount() > 0)) // if there are users in the table
        {
        	int i = 0;
        	nbofPlots = c.getCount();
        	 mPoly = new Polygon[nbofPlots];
        	 
        	c.moveToFirst();
        	do { // for each plot, draw them
        		
        		int id = c.getInt(0);

        		// Draw path
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
        		
        		// Read points from database for each user
        		Cursor c2 = db.GetEntries("points", new String[] {"x", "y"}, "plotID ="+ id, null, null, null, null);
        		
        		if (c2.getCount() > 0) // if there are points to plot
        		{
        			int j = 0;
        			c2.moveToFirst();
                    int[] polyX = new int[c2.getCount()];
                    int[] polyY= new int[c2.getCount()];

        			do { // for each point in the plot, draw it
        				
        				int x1 = c2.getInt(0);
            			int y1 = c2.getInt(1);
            			
                		// Draw overlays
                        GeoPoint ckPura = new GeoPoint(x1,y1);
                        Point screenCoords = new Point();
                        mapView.getProjection().toPixels(ckPura, screenCoords);
                        paint.setARGB(100, 228, 29, 29);
                        
                        if (j==0)
                            path.moveTo(screenCoords.x,screenCoords.y); // for first point, move to it
                        else
                        	path.lineTo(screenCoords.x,screenCoords.y); // For rest, add lines
                        
                        polyX[j] = screenCoords.x;
                        polyY[j] = screenCoords.y;

                        j = j + 1;
        			}
        			while (c2.moveToNext());
        			
                    path.close();

                    // Change paint style depending on user
                    PathShape pShape = new PathShape(path, (float) 100, (float) 100);
                    ShapeDrawable mShape = new ShapeDrawable(pShape); 
                    mShape.getPaint().set(paint);
                    mShape.setBounds(0, 0, 100, 100);
                    
                    mShape.draw(canvas);
                    
                    // Change overlay depending on user
                    
                    mPoly[i] = new Polygon(polyX, polyY, polyX.length);

        		}

                i = i + 1;
        	} 
        	while (c.moveToNext());
        } 

        // Close database
        db.close();
		
	}
	
	@Override public boolean onTap(GeoPoint p, MapView mapView){
		
        Projection mProjection = mapView.getProjection();
        Point touched = new Point();
        mProjection.toPixels(p, touched);
        
        if (mPoly[0]!=null){

        	for (int i=0; i < nbofPlots ;i++ ){
	        	if (mPoly[i].contains(touched.x, touched.y)){
	        	
	        		View view = panel.getView();

	        		((TextView) view.findViewById(R.id.latitude)).setText(String.valueOf(p.getLatitudeE6() / 1000000.0));
	        		((TextView) view.findViewById(R.id.longitude)).setText(String.valueOf(p.getLongitudeE6() / 1000000.0));
	        		((TextView) view.findViewById(R.id.x)).setText(String.valueOf(touched.x));
	        		((TextView) view.findViewById(R.id.y)).setText(String.valueOf(touched.y));
	        		
	        		panel.show(touched.y * 2 > mapView.getHeight());
	        	}
	        }
        }
        
        // When user clicks out of slidingDrawer, close the sliding drawer.
        if (slidingDrawer.isOpened()) {
        	slidingDrawer.animateClose();
        	return false;
        }
        
		return true;
	}
	
}
