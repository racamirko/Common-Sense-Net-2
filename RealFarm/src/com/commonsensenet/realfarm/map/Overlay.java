package com.commonsensenet.realfarm.map;

import android.graphics.Canvas;

public abstract class Overlay {

	public void draw(Canvas canvas, OfflineMapView offlineMapView) {

	}

	public boolean onTap(GeoPoint point, OfflineMapView offlineMapView) {
		return false;
	}
}
