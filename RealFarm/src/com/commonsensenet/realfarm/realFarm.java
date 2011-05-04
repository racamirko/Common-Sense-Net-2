package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class realFarm extends MapActivity {
	
	private MapController myMapController;
	private SlidingDrawer slidingDrawer; 
	Button drawerButton;
	LocationManager lm;

    
	@Override public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
        
		// Load layout
		setContentView(R.layout.main);

		// Define Location manager
		//LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Define map
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setSatellite(true);
        mapView.setBuiltInZoomControls(true);
        myMapController = mapView.getController();
        myMapController.setZoom(20);

        
        List<Overlay> mapOverlays = mapView.getOverlays();
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        MyOverlay itemizedoverlay = new MyOverlay(drawable, this);
        GeoPoint ckPura = new GeoPoint(14054563,77167003);
        myMapController.animateTo(ckPura);
        
        OverlayItem overlayitem = new OverlayItem(ckPura, "Hello!", "I'm in ckPura!");
        itemizedoverlay.addOverlay(overlayitem);

        mapOverlays.add(itemizedoverlay);
        
        // Define action on here button press
        
        final Button button = (Button) findViewById(R.id.topBtn);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        	    lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            	LocationListener locationListenerGps = new MyLocationListener();
        		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            }
        });

        drawerButton = (Button) findViewById(R.id.drawerHandle);
        slidingDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer);
        
        /*
        slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

        	@Override
        	public void onDrawerOpened() {
        	}
        	});

        	slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
        	@Override
        	public void onDrawerClosed() {
        	}
        	});
        	*/
    }
 
	
    
    @Override protected boolean isRouteDisplayed() {
        return false;
    }
    
    
    
    public class MyLocationListener implements LocationListener {
	    public void onLocationChanged(Location location) {
	    	if (location != null) {
	    		double lat = location.getLatitude();
	    		double lng = location.getLongitude();
	    		GeoPoint p  = new GeoPoint((int) lat * 1000000, (int) lng * 1000000);
	    		myMapController.animateTo(p);
	    	}
	    	

	    }
	    
	    public void onProviderDisabled(String provider) {
	    	Toast.makeText( getApplicationContext(),provider+" Disabled",Toast.LENGTH_SHORT).show();
	    }

	    
	    public void onProviderEnabled(String provider) {
	    	Toast.makeText( getApplicationContext(),provider+" Enabled",Toast.LENGTH_SHORT).show();
	    }
	    
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }
	    
    }
    
}