package com.commonsensenet.realfarm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.graphics.BitmapFactory;
import android.os.Environment;

import com.commonsensenet.realfarm.MapCrawler;

public class Map {

	public static Map createMapFromCoordinate(GeoPoint center) {
		Map tmpMap = new Map();

		// gets the path from the system
		String externalDirectoryPath = Environment
				.getExternalStorageDirectory().toString()
				+ MapCrawler.MAPS_FOLDER;

		// gets the file that represents the location
		File mapDir = new File(externalDirectoryPath + center.getLatitude()
				+ "," + center.getLongitude() + "/");

		// if the folder exists loads the map from it.
		if (mapDir.exists()) {
			String[] fileNames = mapDir.list();

			int numberOfTiles = (int) Math.sqrt(fileNames.length);

			int xValue, yValue;

			// !! x and y are inverted when loading.
			for (int x = 0; x < fileNames.length; x++) {
				String[] fileName = fileNames[x].split("_");
				String imagePath = mapDir.getPath() + "/" + fileNames[x];

				// position in the grid.
				xValue = Integer.parseInt(fileName[2]);
				yValue = Integer.parseInt(fileName[1]);

				tmpMap.mTiles.add(new MapTile(BitmapFactory
						.decodeFile(imagePath), MapCrawler.TILE_SIZE,
						MapCrawler.TILE_SIZE, xValue, yValue, new GeoPoint(
								Integer.parseInt(fileName[3]), Integer
										.parseInt(fileName[4])), 17,
						"satellite"));
			}

			// sets the size of the map.
			tmpMap.mWidth = MapCrawler.TILE_SIZE * numberOfTiles;
			tmpMap.mHeight = MapCrawler.TILE_SIZE * numberOfTiles;

			// sorts the images according to the grid position to be able to
			// render them correctly.
			Collections.sort(tmpMap.mTiles);

			return tmpMap;
		}
		return null;
	}

	/** Total height of the map in pixels. */
	private int mHeight;
	/** Initial center coordinate of the map. */
	private GeoPoint mMapCenter;
	/** Matrix containing the tiles of the map. */
	private ArrayList<MapTile> mTiles;
	/** Total width of the map in pixels. */
	private int mWidth;
	/** Current zoom of the map. */
	private int mZoom;

	public Map() {
		mZoom = 17;
		mTiles = new ArrayList<MapTile>();

	}

	public void dispose() {
		for (int x = 0; x < mTiles.size(); x++) {
			mTiles.get(x).getBitmap().recycle();
		}
	}

	public int getHeight() {
		return mHeight;
	}

	public GeoPoint getMapCenter() {
		return mMapCenter;
	}

	public ArrayList<MapTile> getTiles() {
		return mTiles;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getZoom() {
		return mZoom;
	}
}
