package com.commonsensenet.realfarm.map;

/**
 * 
 * 
 * @author Oscar Bola–os (oscar.bolanos@epfl.ch)
 *
 */
public class TileData {

	/** Coordinates of the center of the tile. */
	private GeoPoint mCenter;
	/** Type of map that represents the tile. */
	private String mMaptype;
	/** Size of the tile in pixels. */
	private String mSize;
	/** Position in the map grid of the tile in the x coordinate.*/
	private int mX;
	/** Position in the map grid of the tile in the y coordinate.*/
	private int mY;
	/** Zoom level of the tile. */
	private int mZoom;

	public TileData(GeoPoint center, int x, int y, String size, int zoom,
			String maptype) {
		mCenter = center;
		mMaptype = maptype;
		mSize = size;
		mX = x;
		mY = y;
		mZoom = zoom;
	}

	public GeoPoint getCenter() {
		return mCenter;
	}

	public String getMaptype() {
		return mMaptype;
	}

	public String getSize() {
		return mSize;
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
