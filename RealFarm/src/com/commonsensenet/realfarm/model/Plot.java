package com.commonsensenet.realfarm.model;

import java.util.Arrays;

import android.graphics.Rect;

import com.commonsensenet.realfarm.map.OfflineMapView;

public class Plot {
	/** Coordinates in pixels of the polygon. */
	private int[] mCoordX, mCoordY;
	/** Number of sides that the polygon has. */
	private int mNumberOfSides;
	/** Identifier of the owner of the plot. */
	private int mOwnerId;
	/** Identifier number of the plot. */
	private int mPlotId;

	/**
	 * Creates a new Plot instance.
	 * 
	 * @param coordX array with x coordinates
	 * @param coordY array with y coordinates
	 * @param sides number of sides that the plot has
	 * @param id id of the plot
	 * @param ownerId id of the owner.
	 */
	public Plot(int[] coordX, int[] coordY, int sides, int id, int ownerId) {
		mCoordX = coordX;
		mCoordY = coordY;
		mNumberOfSides = sides;
		mPlotId = id;
		mOwnerId = ownerId;
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

		for (int i = 0, j = mNumberOfSides - 1; i < mNumberOfSides; j = i++) {

			if ((mCoordY[i] < y && mCoordY[j] >= y)
					|| (mCoordY[j] < y && mCoordY[i] >= y)) {

				if (mCoordX[i] + (y - mCoordY[i]) / (mCoordY[j] - mCoordY[i])
						* (mCoordX[j] - mCoordX[i]) < x) {

					oddTransitions = !oddTransitions;
				}
			}
		}
		return oddTransitions;
	}

	public int[] getAverage(OfflineMapView mapView) {

		int[] average = new int[2];

		// sorts both arrays.
		// Arrays.sort(mCoordX);
		// Arrays.sort(mCoordY);
		average[0] = (int) mCoordX[0]
				- (int) ((mCoordX[0] - mCoordX[mCoordX.length - 1]) / 2);
		average[1] = (int) mCoordY[0]
				- (int) ((mCoordY[0] - mCoordY[mCoordY.length - 1]) / 2);
		return average;
	}

	public Rect getBounds(OfflineMapView mapView) {

		Arrays.sort(mCoordX);
		Arrays.sort(mCoordY);

		// gets minimum values
		int minx = mCoordX[0];
		int miny = mCoordY[0];

		// gets max values
		int maxx = mCoordX[mCoordX.length - 1];
		int maxy = mCoordY[mCoordY.length - 1];

		return new Rect(minx, miny, maxx, maxy);
	}

	public int getId() {
		return mPlotId;
	}

	public int getOwnerId() {
		return mOwnerId;
	}

	public int[] getX(OfflineMapView mapView) {
		return mCoordX;
	}

	public int[] getY(OfflineMapView mapView) {
		return mCoordY;
	}
}
