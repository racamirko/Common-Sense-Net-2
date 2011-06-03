package com.commonsensenet.realfarm.maps.utils;

import java.util.Enumeration;
import java.util.Hashtable;


public class GoogleStaticMapsUrl {
	
	/** Defines the path where the images will be obtained, */
	public static final String GOOGLE_STATIC_MAPS_URL = "http://maps.google.com/maps/api/staticmap?";
	public static final String CENTER_PARAM = "center";
	public static final String MAP_TYPE_PARAM = "maptype";
	public static final String SENSOR_PARAM = "sensor";
	public static final String SIZE_PARAM = "size";
	public static final String ZOOM_PARAM = "zoom";

	// size=400x400
	// zoom=17
	// sensor=false
	// maptype=satellite
	// center=14.054162,77.16711
	
	/**
	 * Hashtable that contains all the keys all the keys and values used to
	 * request a Google Map.
	 */
	private Hashtable<String, String> mValues;

	/**
	 * Creates a new MapParams instance.
	 */
	public GoogleStaticMapsUrl() {
		mValues = new Hashtable<String, String>();
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

	/**
	 * Constructs a String that corresponds to the URL used to extract an image from the
	 * Google Static Map API. Parameters provided are appended to the URL with the format
	 * {key}={value}.
	 * 
	 * @return a String that corresponds to the URL where the image will be obtained.
	 */
	public String getUrlString() {
		StringBuffer urlParams = new StringBuffer(GOOGLE_STATIC_MAPS_URL);

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
}
