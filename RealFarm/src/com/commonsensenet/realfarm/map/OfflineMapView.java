package com.commonsensenet.realfarm.map;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;

import com.commonsensenet.realfarm.R;

public class OfflineMapView extends View {

	private class MapLoaderTask extends AsyncTask<GeoPoint, Void, Map> {

		private GeoPoint mCenter;
		private WeakReference<View> mView;

		public MapLoaderTask(View view) {
			mView = new WeakReference<View>(view);
		}

		@Override
		protected Map doInBackground(GeoPoint... params) {
			mCenter = params[0];
			return Map.createMapFromCoordinate(mCenter);
		}

		/**
		 * Once the map is loaded it is asigned to the interface and it is forced to update.
		 */
		@Override
		protected void onPostExecute(Map map) {

			// result is nulled if the task has been canceled.
			if (isCancelled()) {
				map = null;
			}

			mMap = map;
			// forces the UI to update the map.
			if (mView.get() != null)
				mView.get().invalidate();

		}
	}
	
	/** Height of the display area in pixels. */
	private int mDisplayHeight;
	/** Width of the display area in pixels. */
	private int mDisplayWidth;
	/** Underlying map representation in charge of the tile system. */
	private Map mMap;
	/** Amount to scroll in the x coordinate product of the last ACTION_MOVE. */
	private float mScrollByX;
	/** Amount to scroll in the y coordinate product of the last ACTION_MOVE. */
	private float mScrollByY;
	/** Current position of the left corner of the scroll rectangle. */
	private int mScrollRectX;
	/** Current position of the top corner of the scroll rectangle. */
	private int mScrollRectY;
	/** Initial position in the x coordinate used to track the movement. */
	private float mStartX;
	/** Initial position in the y coordinate used to track the movement. */
	private float mStartY;

	public OfflineMapView(Context context, int displayWidth, int displayHeight,
			GeoPoint center) {
		super(context);

		// sets the current size of the screen in pixels.
		mDisplayWidth = displayWidth;
		mDisplayHeight = displayHeight;

		// initial values of scrolling variables.
		mScrollRectX = 0;
		mScrollRectY = 0;
		mScrollByX = 0;
		mScrollByY = 0;
		mStartX = 0;
		mStartY = 0;

		// loads the map asynchronously.
		MapLoaderTask mapLoaderTask = new MapLoaderTask(this);
		mapLoaderTask.execute(center);
	}

	private int clamp(int value, int min, int max) {
		return value < min ? min : (value > max ? max : value);
	}

	public GeoPoint getMapCenter() {
		throw new UnsupportedOperationException();
	}

	public int getZoomLevel() {
		throw new UnsupportedOperationException();
	}

	private Boolean isInside(int r2x, int r2y, int r2w, int r2h, int r1x,
			int r1y, int r1w, int r1h) {
		return !(r2x > (r1x + r1w) || (r2x + r2w) < r1x || r2y > (r1y + r1h) || (r2y + r2h) < r1y);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		// maps is not yet available.
		if (mMap == null)
			return;

		// Our move updates are calculated in ACTION_MOVE in the opposite
		// direction from how we want to move the scroll rectangle. Think of
		// this as dragging to the left being the same as sliding the scroll
		// rectangle to the right.
		int newScrollRectX = clamp(mScrollRectX - (int) mScrollByX, 0,
				mMap.getWidth() - mDisplayWidth);
		int newScrollRectY = clamp(mScrollRectY - (int) mScrollByY, 0,
				mMap.getHeight() - mDisplayHeight);

		// average visible tiles
		final int INITIAL_ARRAY_SIZE = 4;
		// contains the tiles that need to be rendered
		ArrayList<MapTile> tilesToDraw = new ArrayList<MapTile>(
				INITIAL_ARRAY_SIZE);

		// tile used to calculate modifications
		MapTile currentTile;

		// checks which tiles are visible.
		for (int x = 0; x < mMap.getTiles().size(); x++) {
			// gets the current tile of the iteration
			currentTile = mMap.getTiles().get(x);

			// checks if the tile is inside the Viewport
			if (isInside(currentTile.getX(), currentTile.getY(),
					currentTile.getWidth(), currentTile.getHeight(),
					newScrollRectX, newScrollRectY, mDisplayWidth,
					mDisplayHeight)) {
				tilesToDraw.add(currentTile);
			}
		}

		// draws only the visible tiles
		for (int x = 0; x < tilesToDraw.size(); x++) {
			// gets the tile of the current iteration
			currentTile = tilesToDraw.get(x);
			// draws the tile on the canvas
			canvas.drawBitmap(currentTile.getBitmap(), currentTile.getX()
					- newScrollRectX, currentTile.getY() - newScrollRectY, null);
		}

		// resets the current scroll coordinates to reflect the latest update
		mScrollRectX = newScrollRectX;
		mScrollRectY = newScrollRectY;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mMap == null)
			return true;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Remember our initial down event location.
			mStartX = event.getRawX();
			mStartY = event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			// gets movement values from the event
			float x = event.getRawX();
			float y = event.getRawY();

			// calculate move update considering the initial value and
			// the current one obtained from the event.
			mScrollByX = x - mStartX;
			mScrollByY = y - mStartY;

			// resets the start x and y position to the latest value.
			mStartX = x;
			mStartY = y;

			// force a redraw to update the map.
			invalidate();
			break;
		}
		// done with this event so consume it
		return true;
	}

	public void setZoomLevel(int value) {
		throw new UnsupportedOperationException();
	}
}
