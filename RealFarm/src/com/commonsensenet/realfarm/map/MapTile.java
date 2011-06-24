package com.commonsensenet.realfarm.map;

import android.graphics.Bitmap;

public class MapTile implements Comparable<MapTile> {

	/** Bitmap image contained by the tile. */
	private Bitmap mBitmap;
	/** Geographical coordinates of the center of the image. */
	private GeoPoint mCenter;
	/** Position in the map grid of the tile in the x coordinate. */
	private int mGridX;
	/** Position in the map grid of the tile in the y coordinate. */
	private int mGridY;
	/** Type of map that represents the tile. */
	private String mMapType;
	/** Height of the tile in pixels. */
	private int mTileHeight;
	/** Width of the tile in pixels. */
	private int mTileWidth;
	/** Position of the tile in the x coordinate in the map. */
	private int mX;
	/** Position of the tile in the y coordinate in the map. */
	private int mY;
	/** Zoom level of the tile. */
	private int mZoom;


	/**
	 * Creates a new MapTipe instance.
	 * 
	 * @param bitmap
	 * @param tileWidth
	 * @param tileHeight
	 * @param x
	 * @param y
	 * @param center
	 * @param zoom
	 * @param mapType
	 */
	public MapTile(Bitmap bitmap, int tileWidth, int tileHeight, int gridX, int gridY, GeoPoint center, int zoom, String mapType) {

		mBitmap = bitmap;
		mGridX = gridX;
		mGridY = gridY;

		mCenter = center;
		mMapType = mapType;

		// zoom level of the tile.
		mZoom = zoom;

		// saves the size
		mTileWidth = tileWidth;
		mTileHeight = tileHeight;

		// position of the tile in the map considering its dimensions.
		mX = mGridX * mTileWidth;
		mY = mGridY * mTileHeight;

	}

	public int compareTo(MapTile another) {
		// compares initially on the X.
		if(this.getGridX() != another.getGridX())
		{
			if(this.getGridX() < another.getGridX())
				return -1;
			else
				return 1;
		}
		else // compares with Y;
		{
			if(this.getGridY() < another.getGridY())
				return -1;
			else if(this.getGridY() == another.getGridY())
				return 0;
			else
				return 1;
		}
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public GeoPoint getCenter() {
		return mCenter;
	}

	public int getGridX() {
		return mGridX;
	}

	public int getGridY() {
		return mGridY;
	}

	public int getHeight() {
		return mBitmap.getHeight();
	}

	public String getMapType() {
		return mMapType;
	}

	public String getSize() {
		return mTileWidth + "x" + mTileHeight;
	}

	public int getWidth() {
		return mBitmap.getWidth();
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public int getZoom() {
		return mZoom;
	}

	public void setCenter(GeoPoint value) {
		mCenter = value;
	}
}
