package com.commonsensenet.realfarm.model;

import java.util.Arrays;

import android.graphics.Point;

import com.commonsensenet.realfarm.map.OfflineMapView;

public class Plot {
	/** Original points obtained from the database that represent the plot.*/
	private Point[] mCoordinates;
	/** Temporal points computed from the map. */
	private Point[] mPoints;
	/** Number of sides that the polygon has. */
	private int mNumberOfSides;
	/** Identifier number of the plot. */
	private int mPlotId;
	/** Identifier of the owner of the plot. */
	private int mOwnerId;

	public Plot(Point[] coordinates, int numberOfSides, int id, int ownerId) {
		
		mPoints = coordinates;
		mNumberOfSides = numberOfSides;
		mPlotId = id;
		mOwnerId = ownerId;
	}
	
	public Point[] getCoordinates() {
		return mCoordinates;
	}
	
	public Point[] getPoints(OfflineMapView mapView)
	{
		convert(mCoordinates, mapView);
		return mPoints;
	}
	
	/**
	 * Checks if the given point is contained inside the polygon.
	 * 
	 * @see "http://alienryderflex.com/polygon/"
	 * @param x
	 *            Point horizontal pos.
	 * @param y
	 *            Point vertical pos.
	 * @return Point is in Poly flag.
	 */

	public boolean contains(int x, int y, OfflineMapView mapView) {

		boolean oddTransitions = false;

		// converts the points to positions in the map.
		convert(mCoordinates, mapView);

		for (int i = 0, j = mNumberOfSides - 1; i < mNumberOfSides; j = i++) {

			if ((mPoints[i].y < y && mPoints[j].y >= y)
					|| (mPoints[j].x < y && mPoints[i].x >= y)) {

				if (mPoints[i].x + (y - mPoints[i].y) / (mPoints[j].y - mPoints[i].y)
						* (mPoints[j].x - mPoints[i].x) < x) {

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
//			GeoPoint gPoint = new GeoPoint(x1[i], y1[i]);
//			Point screenCoords = new Point();
//			mapView.getProjection().toPixels(gPoint, screenCoords);

			coord[i] = originalPoints[i];
		}
		
		mPoints = coord;
	}

	public int getOwnerId() {
		return mOwnerId;
	}

//	public int[] getCoordinates(OfflineMapView mapView) {
//
//		int[] coord = new int[4];
//
//		convert(mCoordinates, mapView);
//
//		Arrays.sort(polyX);
//		Arrays.sort(polyY);
//
//		int minx = polyX[0];
//		int maxx = polyX[polyX.length - 1];
//		int miny = polyY[0];
//		int maxy = polyY[polyY.length - 1];
//
//		int width = maxx - minx;
//		int height = maxy - miny;
//
//		coord[0] = minx;
//		coord[1] = miny;
//		coord[2] = width;
//		coord[3] = height;
//
//		return coord;
//	}

//	public int[] getAverage(OfflineMapView mapView) {
//
//		int[] average = new int[2];
//
//		convert(mCoordX, mCoordY, mapView);
//		Arrays.sort(polyX);
//		Arrays.sort(polyY);
//		average[0] = (int) polyX[0]
//				- (int) ((polyX[0] - polyX[polyX.length - 1]) / 2);
//		average[1] = (int) polyY[0]
//				- (int) ((polyY[0] - polyY[polyY.length - 1]) / 2);
//		return average;
//	}

//	public int[] getAverageLL() {
//
//		int[] average = new int[2];
//
//		int[] tempLatit = mCoordX.clone();
//		int[] tempLongi = mCoordY.clone();
//		Arrays.sort(tempLatit);
//		Arrays.sort(tempLongi);
//		average[0] = (int) tempLatit[0]
//				- (int) ((tempLatit[0] - tempLatit[tempLatit.length - 1]) / 2);
//		average[1] = (int) tempLongi[0]
//				- (int) ((tempLongi[0] - tempLongi[tempLongi.length - 1]) / 2);
//		return average;
//	}

	public int getId() {
		return mPlotId;
	}
}
