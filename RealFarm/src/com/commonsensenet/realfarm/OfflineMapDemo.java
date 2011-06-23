package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.commonsensenet.realfarm.map.GeoPoint;
import com.commonsensenet.realfarm.map.OfflineMapView;

public class OfflineMapDemo extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// displayWidth and displayHeight will change depending on screen
		// orientation. To get these dynamically, we should hook
		// onSizeChanged().
		// This simple example uses only landscape mode, so it's ok to get them
		// once on startup and use those values throughout.
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();

		// SampleView constructor must be constructed last as it needs the
		// displayWidth and displayHeight we just got.
		setContentView(new OfflineMapView(this, display.getWidth(),
				display.getHeight(), new GeoPoint("14.054162,77.16711")));
	}
}