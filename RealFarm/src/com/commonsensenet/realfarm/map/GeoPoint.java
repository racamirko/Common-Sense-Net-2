package com.commonsensenet.realfarm.map;

/**
 * Class that represents a pair of latitude and longitude on Earth.
 * 
 * @author Oscar Bola–os (oscar.bolanos@epfl.ch)
 * 
 */
public class GeoPoint {

	// (double)Math.round(value * 100000) / 100000
	/** Latitude of the GeoPoint in degrees. */
	private double _latitude;
	/** Longitude of the GeoPoint in degrees. */
	private double _longitude;

	/**
	 * Creates a new instance of GeoPoint with the given coordinates. The values
	 * received for latitude and longitude are not trimmed to match the Mercator
	 * projection.
	 * 
	 * @param latitude
	 *            the point's latitude
	 * @param longitude
	 *            the point's longitude
	 */
	public GeoPoint(double latitude, double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	/**
	 * Creates a new instance of GeoPoint using the given String. The
	 * coordinates are extracted from the string, assuming that they are
	 * separated by a comma.
	 * 
	 * @param center
	 *            string that contains both coordinates separated by a comma.
	 */
	public GeoPoint(String center) {
		String[] coord = center.split(",");

		_latitude = Double.parseDouble(coord[0]);
		_longitude = Double.parseDouble(coord[1]);
	}

	/**
	 * Returns the latitude of the GeoPoint in degrees.
	 * 
	 * @return the latitude in degrees
	 */
	public double getLatitude() {
		return _latitude;
	}

	/**
	 * Returns the longitude of the GeoPoint in degrees.
	 * 
	 * @return the longitude
	 */
	public double getLongitude() {
		return _longitude;
	}

	@Override
	public String toString() {
		return _latitude + "," + _longitude;
	}
}
