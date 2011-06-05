package com.commonsensenet.realfarm.map;

public class GeoPoint {

	private int _latitude;
	private int _longitude;

	public GeoPoint(int latitude, int longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	public int getLatitude() {
		return _latitude;
	}

	public int getLongitud() {
		return _longitude;
	}
}
