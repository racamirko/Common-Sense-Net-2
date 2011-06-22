package com.commonsensenet.realfarm;

import java.util.Arrays;

import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;


/**
 * Minimum Polygon class for Android.
 */
 
public class Polygon {
 
    // Polygon coodinates.
    private int[] polyY, polyX;
    private int[] latit, longi;
 
    // Number of sides in the polygon.
    private int polySides;
 
    private int plotID;
 
    private int[] average;
    
    /**
     * Default constructor.
     * @param px Polygon y coods.
     * @param py Polygon x coods.
     * @param ps Polygon sides count.
     */
    public Polygon(int[] lat, int[] lon, int ps, int ID) {
        latit = lat;
        longi = lon;
        polySides = ps;
        plotID = ID;
        average = new int[lat.length];
    }
 
    public int[] getAverage( MapView mapView ){
    	convert(latit, longi, mapView);
		Arrays.sort(polyX);
		Arrays.sort(polyY);
    	average[0] = (int) polyX[0] - (int) ((polyX[0] -polyX[polyX.length-1])/2);
    	average[1] = (int) polyY[0] - (int) ((polyY[0] -polyY[polyY.length-1])/2);
    	return average;
    }
    
    public int[] getAverageLL(){
    	
    	
		int[] tempLatit = latit.clone();
		int[] tempLongi = longi.clone();
    	Arrays.sort(tempLatit);
		Arrays.sort(tempLongi);
    	average[0] = (int) tempLatit[0] - (int) ((tempLatit[0] -tempLatit[tempLatit.length-1])/2);
    	average[1] = (int) tempLongi[0] - (int) ((tempLongi[0] -tempLongi[tempLongi.length-1])/2);
    	return average;
    }
    
    
 
    public int getID(){
    	return plotID;
    }
    
    public int[] getX( MapView mapView ){
    	convert(latit, longi, mapView);
    	return polyX;
    }
 
    public int[] getY( MapView mapView ){
    	convert(latit, longi, mapView);
    	return polyY;
    }
    
    public void convert(int[] x1, int[] y1, MapView mapView){
    	
        int coord[][] = new int [2][x1.length];
        
        for (int i=0; i<x1.length;i++){
        	GeoPoint gPoint = new GeoPoint(x1[i], y1[i]);
            Point screenCoords = new Point();
            mapView.getProjection().toPixels(gPoint, screenCoords);
            
            coord[0][i] = screenCoords.x;
            coord[1][i] = screenCoords.y;
        }
        
        polyX = coord[0];
        polyY = coord[1];
    	
    }
    
    /**
     * Checks if the Polygon contains a point.
     * @see "http://alienryderflex.com/polygon/"
     * @param x Point horizontal pos.
     * @param y Point vertical pos.
     * @return Point is in Poly flag.
     */
 
    public boolean contains( int x, int y, MapView mapView ) {
 
        boolean oddTransitions = false;
        
       convert(latit, longi, mapView);
          
 
        for( int i = 0, j = polySides -1; i < polySides; j = i++ ) {
 
            if( ( polyY[ i ] < y && polyY[ j ] >= y ) || ( polyY[ j ] < y && polyY[ i ] >= y ) ) {
 
                if( polyX[ i ] + ( y - polyY[ i ] ) / ( polyY[ j ] - polyY[ i ] ) * ( polyX[ j ] - polyX[ i ] ) < x ) {
 
                    oddTransitions = !oddTransitions;          
                }
            }
        }
        return oddTransitions;
    }  
}