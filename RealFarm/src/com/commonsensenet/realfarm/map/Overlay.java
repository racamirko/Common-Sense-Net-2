package com.commonsensenet.realfarm.map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

public abstract class Overlay {

	public void draw(Canvas canvas, OfflineMapView offlineMapView) {

	}

	// public boolean onTap(GeoPoint point, OfflineMapView offlineMapView) {
	// return false;
	// }

	public boolean onTap(Point point, OfflineMapView offlineMapView) {
		return false;
	}

	public boolean onTouchEvent(MotionEvent e, OfflineMapView mapView) {
		return false;
	}
}
