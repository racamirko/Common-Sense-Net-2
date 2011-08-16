package com.commonsensenet.realfarm.map;

import android.graphics.Bitmap;

public class MapTile implements Comparable<MapTile> {

	/** Geographical coordinates of the center of the image. */
	private GeoPoint mCenter;
	/** Position in the map grid of the tile in the x coordinate. */
	private int mGridX;
	/** Position in the map grid of the tile in the y coordinate. */
	private int mGridY;
	/** Path where the image is located. */
	private String mImagePath;
	/** Indicates whether the bitmap has already been loaded. */
	private boolean mIsBitmapLoaded;
	/** Type of map that represents the tile. */
	private String mMapType;
	/** Bitmap that represents the tile. */
	private Bitmap mTileBitmap;
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

	public MapTile(Bitmap bitmap, int tileWidth, int tileHeight, int gridX,
			int gridY, GeoPoint center, int zoom, String mapType) {

		mIsBitmapLoaded = true;

		mTileBitmap = bitmap;
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
	public MapTile(String imagePath, Bitmap defaultBitmap, int tileWidth,
			int tileHeight, int gridX, int gridY, GeoPoint center, int zoom,
			String mapType) {

		mIsBitmapLoaded = false;
		mImagePath = imagePath;

		mTileBitmap = defaultBitmap;
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
		if (this.getGridX() != another.getGridX()) {
			if (this.getGridX() < another.getGridX())
				return -1;
			else
				return 1;
		} else // compares with Y;
		{
			if (this.getGridY() < another.getGridY())
				return -1;
			else if (this.getGridY() == another.getGridY())
				return 0;
			else
				return 1;
		}
	}

	public void dispose() {
		if (mTileBitmap != null)
			mTileBitmap.recycle();
	}

	public synchronized Bitmap getBitmap() {
		return mTileBitmap;
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
		return mTileHeight;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public boolean getIsBitmapLoaded() {
		return mIsBitmapLoaded;
	}

	public String getMapType() {
		return mMapType;
	}

	public String getSize() {
		return mTileWidth + "x" + mTileHeight;
	}

	public int getWidth() {
		return mTileWidth;
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

	public synchronized void setBitmap(Bitmap bitmap) {

		// releases previous bitmap.
		// if(mTileBitmap != null) {
		// mTileBitmap.recycle();
		// }

		mTileBitmap = bitmap;
		mIsBitmapLoaded = true;
	}

	public void setCenter(GeoPoint value) {
		mCenter = value;
	}
}
