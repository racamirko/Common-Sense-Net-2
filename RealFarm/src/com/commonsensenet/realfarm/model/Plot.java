package com.commonsensenet.realfarm.model;

import android.graphics.Point;

public class Plot {
	/** Calculated center of the map using the coordinates. */
	private Point mCenterCoordinate;
	/** Original points obtained from the database that represent the plot. */
	private Point[] mCoordinates;
	/** Identifier of the owner of the plot. */
	private int mOwnerId;
	/** Identifier number of the plot. */
	private int mPlotId;

	public Plot(Point[] coordinates, int id, int ownerId) {

		mCoordinates = coordinates;
		mPlotId = id;
		mOwnerId = ownerId;

		int sumX = 0;
		int sumY = 0;

		for (int x = 0; x < coordinates.length; x++) {
			sumX += coordinates[x].x;
			sumY += coordinates[x].y;
		}

		// gets the center point if the plot
		mCenterCoordinate = new Point(sumX / coordinates.length, sumY
				/ coordinates.length);
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

	public Point[] getNormalizedCoordinates() {

		Point[] points = new Point[mCoordinates.length];

		// obtains the values from the first point.
		int minX = mCoordinates[0].x;
		int minY = mCoordinates[0].y;

		// gets the minimum point of the polygon.
		for (int x = 1; x < mCoordinates.length; x++) {
			if (mCoordinates[x].x < minX)
				minX = mCoordinates[x].x;
			if (mCoordinates[x].y < minY)
				minY = mCoordinates[x].y;
		}

		// offsets each coordinate according to the minimum
		for (int x = 0; x < mCoordinates.length; x++) {

			points[x] = new Point(mCoordinates[x]);
			points[x].offset(-minX, -minY);
		}

		return points;
	}

	public int getOwnerId() {
		return mOwnerId;
	}
}
