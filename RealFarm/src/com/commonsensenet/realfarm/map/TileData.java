package com.commonsensenet.realfarm.map;

/**
 * Contains the information about a Tile used in a map.
 * 
 * @author Oscar Bola–os (oscar.bolanos@epfl.ch)
 * 
 */
public class TileData {

	/** Coordinates of the center of the tile. */
	private GeoPoint mCenter;
	/** Type of map that represents the tile. */
	private String mMapType;
	/** Height of the tile in pixels. */
	private int mTileHeight;
	/** Width of the tile in pixels. */
	private int mTileWidth;
	/** Position in the map grid of the tile in the x coordinate. */
	private int mX;
	/** Position in the map grid of the tile in the y coordinate. */
	private int mY;
	/** Zoom level of the tile. */
	private int mZoom;

	public TileData(GeoPoint center, int x, int y, int tileWidth,
			int tileHeight, int zoom, String mapType) {

		mCenter = center;
		mMapType = mapType;
		// saves the size
		mTileWidth = tileWidth;
		mTileHeight = tileHeight;
		// sets the position in the grid of the map.
		mX = x;
		mY = y;

		mZoom = zoom;
	}

	public GeoPoint getCenter() {
		return mCenter;
	}

	public String getMapType() {
		return mMapType;
	}

	public String getSize() {
		return mTileWidth + "x" + mTileHeight;
	}

	public String getUrl() {
		return "";
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
}
