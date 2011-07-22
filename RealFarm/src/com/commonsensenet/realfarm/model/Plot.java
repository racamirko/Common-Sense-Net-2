package com.commonsensenet.realfarm.model;

import android.graphics.Point;
import android.util.Log;

import com.commonsensenet.realfarm.map.OfflineMapView;

public class Plot {
	/** Calculated center of the map using the coordinates. */
	private Point mCenterCoordinate;
	/** Original points obtained from the database that represent the plot. */
	private Point[] mCoordinates;
	/** Identifier of the owner of the plot. */
	private int mOwnerId;
	/** Identifier number of the plot. */
	private int mPlotId;
	/** Temporal points computed from the map. */
	private Point[] mPoints;

	
	public Plot(Point[] coordinates, int id, int ownerId) {

		mCoordinates = coordinates;
		mPoints = null;
		mPlotId = id;
		mOwnerId = ownerId;

		int sumX = 0;
		int sumY = 0;

		for (int x = 0; x < coordinates.length; x++) {
			sumX += coordinates[x].x;
			sumY += coordinates[x].y;
		}

		mCenterCoordinate = new Point(sumX / coordinates.length, sumY
				/ coordinates.length);

	}

	
	/**
	 * Checks if the given point is contained inside the polygon.
	 * 
	 * @see "http://alienryderflex.com/polygon/"
	 * @param x
	 *            coordinate in the x axis of the point.
	 * @param y
	 *            coordinate in the y axis of the point.
	 * @return true if the given position is inside the polygon otherwise false.
	 */

	public boolean contains(int x, int y, OfflineMapView mapView) {

		boolean oddTransitions = false;

		// converts the points to positions in the map.
		convert(mCoordinates, mapView);

		for (int i = 0, j = mPoints.length - 1; i < mPoints.length; j = i++) {
		    	
			if ((mPoints[i].y < y && mPoints[j].y >= y)
					|| (mPoints[j].y < y && mPoints[i].y >= y)) {

				// TODO: division by zero!!	
				if(mPoints[i].x+(y-mPoints[i].y)/(mPoints[j].y-mPoints[i].y)*(mPoints[j].x-mPoints[i].x)<x) {

					oddTransitions = !oddTransitions;
				}
			}
		}
		return oddTransitions;
	}

	private void convert(Point[] originalPoints, OfflineMapView mapView) {

		Point[] coord = new Point[originalPoints.length];

		for (int i = 0; i < originalPoints.length; i++) {
			// TODO: do proper conversion.
			// GeoPoint gPoint = new GeoPoint(x1[i], y1[i]);
			// Point screenCoords = new Point();
			// mapView.getProjection().toPixels(gPoint, screenCoords);
			Point mapCenter = mapView.getCenterPoint();
			mapCenter.offset(originalPoints[i].x, originalPoints[i].y);

			coord[i] = mapCenter;
		}

		mPoints = coord;
	}

	public Point getCenterCoordinate() {
		return mCenterCoordinate;
	}

	public Point[] getCoordinates() {
		return mCoordinates;
	}

	public int getId() {
		return mPlotId;
	}

	// public int[] getCoordinates(OfflineMapView mapView) {
	//
	// int[] coord = new int[4];
	//
	// convert(mCoordinates, mapView);
	//
	// Arrays.sort(polyX);
	// Arrays.sort(polyY);
	//
	// int minx = polyX[0];
	// int maxx = polyX[polyX.length - 1];
	// int miny = polyY[0];
	// int maxy = polyY[polyY.length - 1];
	//
	// int width = maxx - minx;
	// int height = maxy - miny;
	//
	// coord[0] = minx;
	// coord[1] = miny;
	// coord[2] = width;
	// coord[3] = height;
	//
	// return coord;
	// }

	// public int[] getAverage(OfflineMapView mapView) {
	//
	// int[] average = new int[2];
	//
	// convert(mCoordX, mCoordY, mapView);
	// Arrays.sort(polyX);
	// Arrays.sort(polyY);
	// average[0] = (int) polyX[0]
	// - (int) ((polyX[0] - polyX[polyX.length - 1]) / 2);
	// average[1] = (int) polyY[0]
	// - (int) ((polyY[0] - polyY[polyY.length - 1]) / 2);
	// return average;
	// }

	public int getOwnerId() {
		return mOwnerId;
	}

	public Point[] getPoints(OfflineMapView mapView) {
		convert(mCoordinates, mapView);
		return mPoints;
	}
}
