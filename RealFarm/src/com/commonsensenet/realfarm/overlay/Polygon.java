package com.commonsensenet.realfarm.overlay;

import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * Minimum Polygon class for Android.
 */

public class Polygon {

	private int[] latit, longi;
	/** Number of sides that the polygon has. */
	private int mNumberOfSides;
	/** Identifier number of the plot. */
	private int mPlotId;
	private int[] polyY, polyX;
	private int ownerId;

	public Polygon(int[] lat, int[] lon, int ps, int id, int ownerId) {
		latit = lat;
		longi = lon;
		mNumberOfSides = ps;
		mPlotId = id;
		this.ownerId = ownerId;
	}

	public Polygon(int[] lat, int[] lon, int ps, int id) {
		latit = lat;
		longi = lon;
		mNumberOfSides = ps;
		mPlotId = id;
		ownerId = 0;
	}

	
	public int[] getLat(){
		return latit;
	}
	
	public int[] getLon(){
		return longi;
	}
	
	/**
	 * Checks if the Polygon contains a point.
	 * 
	 * @see "http://alienryderflex.com/polygon/"
	 * @param x
	 *            Point horizontal pos.
	 * @param y
	 *            Point vertical pos.
	 * @return Point is in Poly flag.
	 */

	public boolean contains(int x, int y, MapView mapView) {

		boolean oddTransitions = false;

		convert(latit, longi, mapView);

		for (int i = 0, j = mNumberOfSides - 1; i < mNumberOfSides; j = i++) {

			if ((polyY[i] < y && polyY[j] >= y)
					|| (polyY[j] < y && polyY[i] >= y)) {

				if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i])
						* (polyX[j] - polyX[i]) < x) {

					oddTransitions = !oddTransitions;
				}
			}
		}
		return oddTransitions;
	}

	public void convert(int[] x1, int[] y1, MapView mapView) {

		int coord[][] = new int[2][x1.length];

		for (int i = 0; i < x1.length; i++) {
			GeoPoint gPoint = new GeoPoint(x1[i], y1[i]);
			Point screenCoords = new Point();
			mapView.getProjection().toPixels(gPoint, screenCoords);

			coord[0][i] = screenCoords.x;
			coord[1][i] = screenCoords.y;
		}

		polyX = coord[0];
		polyY = coord[1];

	}

	public int getOwner() {
		return ownerId;
	}

	public Path getDrawable(MapView mapView) {

		Paint paint = new Paint();
		paint.setStrokeWidth(7);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeWidth(3);
		paint.setARGB(100, 55, 175, 35);

		if (getOwner() == 1)
			paint.setARGB(100, 228, 29, 29);

		Path path = new Path(); // Draw path
		path.setFillType(Path.FillType.EVEN_ODD);

		int[] x = getX(mapView);
		int[] y = getY(mapView);

		for (int i = 0; i < x.length; i++) {

			if (i == 0)
				path.moveTo(x[i], y[i]); // for first point, move to it
			else
				path.lineTo(x[i], y[i]); // For rest, add lines

		}

		path.close();

		// Change paint style depending on user
//		PathShape pShape = new PathShape(path, (float) 100, (float) 100);
//		ShapeDrawable mShape = new ShapeDrawable(pShape);
//		mShape.getPaint().set(paint);
//		mShape.setBounds(0, 0, 100, 100);
//		
		
		return path;

	}

	public int[] getCoordinates(MapView mapView) {

		int[] coord = new int[4];

		convert(latit, longi, mapView);

		Arrays.sort(polyX);
		Arrays.sort(polyY);

		int minx = polyX[0];
		int maxx = polyX[polyX.length - 1];
		int miny = polyY[0];
		int maxy = polyY[polyY.length - 1];

		int width = maxx - minx;
		int height = maxy - miny;

		coord[0] = minx;
		coord[1] = miny;
		coord[2] = width;
		coord[3] = height;

		return coord;
	}

	public int[] getAverage(MapView mapView) {

		int[] average = new int[2];

		convert(latit, longi, mapView);
		Arrays.sort(polyX);
		Arrays.sort(polyY);
		average[0] = (int) polyX[0]
				- (int) ((polyX[0] - polyX[polyX.length - 1]) / 2);
		average[1] = (int) polyY[0]
				- (int) ((polyY[0] - polyY[polyY.length - 1]) / 2);
		return average;
	}

	public int[] getAverageLL() {

		int[] average = new int[2];

		int[] tempLatit = latit.clone();
		int[] tempLongi = longi.clone();
		Arrays.sort(tempLatit);
		Arrays.sort(tempLongi);
		average[0] = (int) tempLatit[0]
				- (int) ((tempLatit[0] - tempLatit[tempLatit.length - 1]) / 2);
		average[1] = (int) tempLongi[0]
				- (int) ((tempLongi[0] - tempLongi[tempLongi.length - 1]) / 2);
		return average;
	}

	public int getId() {
		return mPlotId;
	}

	public int[] getX(MapView mapView) {
		convert(latit, longi, mapView);
		return polyX;
	}

	public int[] getY(MapView mapView) {
		convert(latit, longi, mapView);
		return polyY;
	}
}