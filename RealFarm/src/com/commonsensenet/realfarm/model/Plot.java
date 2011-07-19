package com.commonsensenet.realfarm.model;

import java.util.Arrays;

import android.graphics.Point;

import com.commonsensenet.realfarm.map.OfflineMapView;

public class Plot {
	private int[] mCoordX, mCoordY;
	/** Number of sides that the polygon has. */
	private int mNumberOfSides;
	/** Identifier number of the plot. */
	private int mPlotId;
	private int[] polyY, polyX;
	/** Identifier of the owner of the plot. */
	private int mOwnerId;

	public Plot(int[] coordX, int[] coordY, int numberOfSides, int id, int ownerId) {
		mCoordX = coordX;
		mCoordY = coordY;
		mNumberOfSides = numberOfSides;
		mPlotId = id;
		mOwnerId = ownerId;
	}
	
	public int[] getLat(){
		return mCoordX;
	}
	
	public int[] getLon(){
		return mCoordY;
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

	public boolean contains(int x, int y, OfflineMapView mapView) {

		boolean oddTransitions = false;

		convert(mCoordX, mCoordY, mapView);

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

	public void convert(int[] x1, int[] y1, OfflineMapView mapView) {

		int coord[][] = new int[2][x1.length];

		for (int i = 0; i < x1.length; i++) {
			// TODO: do proper convertion.
//			GeoPoint gPoint = new GeoPoint(x1[i], y1[i]);
			Point screenCoords = new Point();
//			mapView.getProjection().toPixels(gPoint, screenCoords);

			coord[0][i] = screenCoords.x;
			coord[1][i] = screenCoords.y;
		}

		polyX = coord[0];
		polyY = coord[1];

	}

	public int getOwnerId() {
		return mOwnerId;
	}

	public int[] getCoordinates(OfflineMapView mapView) {

		int[] coord = new int[4];

		convert(mCoordX, mCoordY, mapView);

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

	public int[] getAverage(OfflineMapView mapView) {

		int[] average = new int[2];

		convert(mCoordX, mCoordY, mapView);
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

		int[] tempLatit = mCoordX.clone();
		int[] tempLongi = mCoordY.clone();
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

	public int[] getX(OfflineMapView mapView) {
		convert(mCoordX, mCoordY, mapView);
		return polyX;
	}

	public int[] getY(OfflineMapView mapView) {
		convert(mCoordX, mCoordY, mapView);
		return polyY;
	}
}
