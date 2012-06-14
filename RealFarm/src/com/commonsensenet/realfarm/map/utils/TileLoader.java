package com.commonsensenet.realfarm.map.utils;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.commonsensenet.realfarm.map.MapTile;
import com.commonsensenet.realfarm.map.OfflineMapView;

/**
 * 
 * @see http 
 *      ://www.samcoles.co.uk/mobile/android-asynchronously-load-image-from-sd
 *      -card/
 * 
 */
public class TileLoader {

	private class SDLoadImageTask extends AsyncTask<MapTile, Void, Bitmap> {

		/** Tile that will be updated. */
		private MapTile mMapTile;

		@Override
		protected Bitmap doInBackground(MapTile... params) {
			mMapTile = params[0];
			return loadImageFromSDCard(mMapTile.getImagePath());
		}

		@Override
		protected void onPostExecute(Bitmap bmp) {
			SDLoadImageTask sdLoadTask = mCurrentTasks.get(mMapTile);
			// Change bitmap only if this process is still associated with
			// it
			if (this == sdLoadTask) {
				// clears the hash.
				mCurrentTasks.remove(mMapTile);
				// sets the bitmap
				mMapTile.setBitmap(bmp);
				// tells the map to refresh
				mMapView.invalidate();
			}
		}
	}

	/** Stores the active tasks. */
	private HashMap<MapTile, SDLoadImageTask> mCurrentTasks;
	/** View where the tiles are rendered. */
	private OfflineMapView mMapView;

	public TileLoader(OfflineMapView mapView) {
		mCurrentTasks = new HashMap<MapTile, TileLoader.SDLoadImageTask>();
		mMapView = mapView;
	}

	public void load(MapTile tile) {

		// checks if a download is currently ongoing.
		SDLoadImageTask sdLoadTask = mCurrentTasks.get(tile);
		if (sdLoadTask == null) {

			Log.d("TileLoader",
					"TileLoader: Pending tasks: " + mCurrentTasks.size());
			// creates a new task.
			SDLoadImageTask task = new SDLoadImageTask();
			// adds the task to the hash
			mCurrentTasks.put(tile, task);
			// starts loading the task.
			task.execute(tile);
		} else {
			Log.d("TileLoader", "TileLoader: Already in hash!");
		}
	}

	private Bitmap loadImageFromSDCard(String filePath) {

		// BitmapFactory.Options bfo = new BitmapFactory.Options();
		// bfo.inSampleSize = 4;
		// bfo.outWidth = 150;
		// bfo.outHeight = 150;
		// Bitmap photo = BitmapFactory.decodeFile(filePath, bfo);
		Bitmap photo = BitmapFactory.decodeFile(filePath);
		return photo;
	}
}