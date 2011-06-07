package com.commonsensenet.realfarm.map.utils;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapUrl {

	private static final String CENTER_PARAM = "center";
	private static final String MAP_TYPE_PARAM = "maptype";
	/** Defines the path where the images will be obtained, */
	private static final String MAPS_URL = "http://maps.google.com/maps/api/staticmap?";
	private static final String SENSOR_PARAM = "sensor";
	private static final String SIZE_PARAM = "size";
	private static final String ZOOM_PARAM = "zoom";

	// size=400x400
	// zoom=17
	// sensor=false
	// maptype=satellite
	// center=14.054162,77.16711

	private Hashtable<String, String> mValues;

	/**
	 * Creates a new MapParams instance.
	 */
	public MapUrl(String center, String mapType, String size, String zoom) {
		mValues = new Hashtable<String, String>();
		addParam(CENTER_PARAM, center);
		addParam(MAP_TYPE_PARAM, mapType);
		addParam(SIZE_PARAM, size);
		addParam(ZOOM_PARAM, zoom);
		addParam(SENSOR_PARAM, "false");
	}

	/**
	 * Adds a parameters to parameters list.
	 * 
	 * @param key
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter.
	 */
	public void addParam(String key, String value) {
		mValues.put(key, value);
	}

	public String getCenter() {
		return mValues.get(CENTER_PARAM);
	}

	public String getMapType() {
		return mValues.get(MAP_TYPE_PARAM);
	}

	public String getSensor() {
		return mValues.get(SENSOR_PARAM);
	}

	public String getSize() {
		return mValues.get(SIZE_PARAM);
	}

	/**
	 * Constructs a String that corresponds to the URL used to extract an image
	 * from the Google Static Map API. Parameters provided are appended to the
	 * URL with the format {key}={value}.
	 * 
	 * @return a String that corresponds to the URL where the image will be
	 *         obtained.
	 */
	public String getUrlString() {
		StringBuffer urlParams = new StringBuffer(MAPS_URL);

		Enumeration<String> keys = mValues.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			urlParams.append(key + "=" + mValues.get(key));

			if (keys.hasMoreElements()) {
				urlParams.append("&");
			}
		}

		return urlParams.toString();
	}

	public String getZoom() {
		return mValues.get(ZOOM_PARAM);
	}

	public void setCenter(String value) {
		addParam(CENTER_PARAM, value);
	}

	public void setMapType(String value) {
		addParam(MAP_TYPE_PARAM, value);
	}

	public void setSensor(String value) {
		addParam(SENSOR_PARAM, value);
	}

	public void setSize(String value) {
		addParam(SIZE_PARAM, value);
	}

	public void setZoom(String value) {
		addParam(ZOOM_PARAM, value);
	}
}
