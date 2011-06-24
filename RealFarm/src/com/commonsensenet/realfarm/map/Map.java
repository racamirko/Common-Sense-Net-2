package com.commonsensenet.realfarm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;

import com.commonsensenet.realfarm.MapCrawler;
import com.commonsensenet.realfarm.R;

public class Map {

	public static int[][] DEFAULT_MAP = {
			{ R.drawable.maptile_0_0, R.drawable.maptile_0_1,
					R.drawable.maptile_0_2, R.drawable.maptile_0_3 },
			{ R.drawable.maptile_1_0, R.drawable.maptile_1_1,
					R.drawable.maptile_1_2, R.drawable.maptile_1_3 },
			{ R.drawable.maptile_2_0, R.drawable.maptile_2_1,
					R.drawable.maptile_2_2, R.drawable.maptile_2_3 },
			{ R.drawable.maptile_3_0, R.drawable.maptile_3_1,
					R.drawable.maptile_3_2, R.drawable.maptile_3_3 } };

	public static Map createMapFromCoordinate(GeoPoint center, View view) {
		Map tmpMap = new Map();

		String externalDirectoryPath = Environment
				.getExternalStorageDirectory().toString()
				+ MapCrawler.MAPS_FOLDER;

		File mapDir = new File(externalDirectoryPath + center.getLatitude()
				+ "," + center.getLongitude() + "/");

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

			// position of the tile in the list
			// tilePos = (xValue + 1) * numberOfTiles - numberOfTiles + yValue; 
		
			tmpMap.mTiles.add(new MapTile(BitmapFactory.decodeFile(imagePath), MapCrawler.TILE_SIZE, MapCrawler.TILE_SIZE,
					xValue, yValue, new GeoPoint(Integer.parseInt(fileName[3]), Integer.parseInt(fileName[4])),
							17, "satellite"));
		}

		// sets the size of the map.
		tmpMap.mWidth = MapCrawler.TILE_SIZE * numberOfTiles;
		tmpMap.mHeight = MapCrawler.TILE_SIZE * numberOfTiles;
		
		// sorts the images according to the grid position
		Collections.sort(tmpMap.mTiles);

		return tmpMap;
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
