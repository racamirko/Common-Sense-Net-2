package com.commonsensenet.realfarm.overlay;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.view.MotionEvent;

import com.commonsensenet.realfarm.PlotEditor;
import com.commonsensenet.realfarm.map.OfflineMapView;
import com.commonsensenet.realfarm.map.Overlay;
import com.commonsensenet.realfarm.model.Plot;

public class PlotOverlay extends Overlay {

	/** Polygon that represents the overlay. */
	private Plot mPlot;

	/**
	 * Creates a new Plot2Overlay instance.
	 * 
	 * @param polygon
	 *            a Polygon object to be drawn.
	 */
	public PlotOverlay(Plot plot) {
		mPlot = plot;
	}
	
	@Override
	public void draw(Canvas canvas, OfflineMapView offlineMapView) {
		super.draw(canvas, offlineMapView);

		// defines the style of the overlay
		Paint paint = new Paint();
		paint.setStrokeWidth(7);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeWidth(3);

		// loads the points that need to be drawn.
		Path path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);

		// selects the color based on the owner of the plot
		paint.setARGB(100, 55, 175, 35);
		if (mPlot.getOwnerId() == 1)
			paint.setARGB(100, 228, 29, 29);

		// gets the points
		Point[] points = mPlot.getPoints(offlineMapView);

		if (points.length > 0) {
			// move for the first point
			path.moveTo(points[0].x, points[0].y);

			// adds the rest of the lines.
			for (int i = 1; i < points.length; i++) {
				path.lineTo(points[i].x, points[i].y);
			}
		}

		// closes the path.
		path.close();

		// Change paint style depending on user
		PathShape pShape = new PathShape(path, (float) 100, (float) 100);
		ShapeDrawable mShape = new ShapeDrawable(pShape);
		mShape.getPaint().set(paint);
		mShape.setBounds(0, 0, 100, 100);

		mShape.draw(canvas);

	}

	@Override
	public boolean onTouchEvent(MotionEvent e, OfflineMapView mapView) {

		Point touched = new Point((int) e.getX(), (int) e.getY());

		if (mPlot.contains(touched.x, touched.y, mapView)) {

			Intent myIntent = new Intent();
			myIntent.setClass(mapView.getContext(), PlotEditor.class);
			int test = mPlot.getId();
			myIntent.putExtra("ID", Integer.toString(test));

			// draw in bitmap
//			Bitmap myBitmap = Bitmap.createBitmap(100, 100, Config.ARGB_8888);
//			Canvas myCanvas = new Canvas(myBitmap);
//
//			Paint paint = new Paint();
//			paint.setStrokeWidth(7);
//			paint.setAntiAlias(true);
//			paint.setDither(true);
//			paint.setStrokeWidth(3);
//			paint.setARGB(100, 55, 175, 35);

			// Path path = mPlot.getDrawable(mapView);

			// myCanvas.drawPath(path, paint);

			//myIntent.putExtra("Bitmap", myBitmap);
			mapView.getContext().startActivity(myIntent);
			
		}
		return false;
		//return false;
	}
}
