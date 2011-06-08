package com.commonsensenet.realfarm.map.utils;

import com.commonsensenet.realfarm.map.TileData;

public class MapUrlBuilder {

	private static final String CENTER_PARAM = "center";
	private static final String MAP_TYPE_PARAM = "maptype";
	/** Defines the path where the images will be obtained, */
	private static final String MAPS_URL = "http://maps.google.com/maps/api/staticmap?";
	private static final String SENSOR_PARAM = "sensor";
	private static final String SIZE_PARAM = "size";
	private static final String ZOOM_PARAM = "zoom";

	public static String getTileUrl(String center, String mapType, int zoom,
			String size) {
		StringBuffer urlParams = new StringBuffer(MAPS_URL);

		// adds the necessary parameters to the URL
		urlParams.append(CENTER_PARAM + "=" + center);
		urlParams.append("&");
		urlParams.append(MAP_TYPE_PARAM + "=" + mapType);
		urlParams.append("&");
		urlParams.append(ZOOM_PARAM + "=" + zoom);
		urlParams.append("&");
		urlParams.append(SIZE_PARAM + "=" + size);
		urlParams.append("&");
		urlParams.append(SENSOR_PARAM + "=false");

		return urlParams.toString();
	}

	public static String getTileUrl(TileData tileData) {
		StringBuffer urlParams = new StringBuffer(MAPS_URL);

		// adds the necessary parameters to the URL
		urlParams.append(CENTER_PARAM + "=" + tileData.getCenter().toString());
		urlParams.append("&");
		urlParams.append(MAP_TYPE_PARAM + "=" + tileData.getMaptype());
		urlParams.append("&");
		urlParams.append(ZOOM_PARAM + "=" + tileData.getZoom());
		urlParams.append("&");
		urlParams.append(SIZE_PARAM + "=" + tileData.getSize());
		urlParams.append("&");
		urlParams.append(SENSOR_PARAM + "=false");

		return urlParams.toString();
	}
}
