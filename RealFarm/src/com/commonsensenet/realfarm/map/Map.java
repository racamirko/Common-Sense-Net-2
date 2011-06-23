package com.commonsensenet.realfarm.map;

import java.io.File;
import java.util.ArrayList;

import com.commonsensenet.realfarm.MapCrawler;
import com.commonsensenet.realfarm.R;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class Map {

	private static int[][] tiles = {
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
		File mapDir = new File(externalDirectoryPath + +center.getLatitude()
				+ "," + center.getLongitude() + "/");

		File[] mapFiles = mapDir.listFiles();

		for (int x = 0; x < tiles.length; x++) {
			String[] fileName = mapFiles[x].getName().split("_,.");
			Log.d("test", fileName.toString());
		}

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != -1) {
					tmpMap.mTiles.add(new MapTile(BitmapFactory.decodeResource(
							view.getResources(), tiles[x][y]), y
							* MapCrawler.TILE_SIZE, x * MapCrawler.TILE_SIZE));
				}
			}
		}

		// sets the size of the map.
		tmpMap.mWidth = MapCrawler.TILE_SIZE * 4;
		tmpMap.mHeight = MapCrawler.TILE_SIZE * 4;

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
