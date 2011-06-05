package com.commonsensenet.realfarm.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.commonsensenet.realfarm.R;

public class MapView extends View {

	// private Rect _size;
	// private int _zoom;

	/** Height if the display area in pixels. */
	private int mDisplayHeight;
	/** Width of the display area in pixels. */
	private int mDisplayWidth;
	/** Total height of the map in pixels. */
	private int mMapHeight;
	/** Total width of the map in pixels. */
	private int mMapWidth;
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
	/** Matrix containing the tiles of the map. */
	private ArrayList<MapTile> mTiles;

	public MapView(Context context, int displayWidth, int displayHeight) {
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

		// creates the matrix that contains that contain the tiles.
		mTiles = new ArrayList<MapTile>();

		// loads the map with the initial zoom
		loadMap();
	}

	private int clamp(int value, int min, int max) {
		return value < min ? min : (value > max ? max : value);
	}

	private int getMapHeight() {
		return mMapHeight;
	}

	private int getMapWidth() {
		return mMapWidth;
	}

	private Boolean isInside(int r2x, int r2y, int r2w, int r2h, int r1x,
			int r1y, int r1w, int r1h) {
		return !(r2x > (r1x + r1w) || (r2x + r2w) < r1x || r2y > (r1y + r1h) || (r2y + r2h) < r1y);
	}

	/**
	 * Loads the map and prepares all the required structures.
	 */
	private void loadMap() {

		int[][] tiles = {
				{ -1, R.drawable.ck_pura_17_2, -1 },
				{ R.drawable.ck_pura_17_4, R.drawable.ck_pura_17_5,
						R.drawable.ck_pura_17_6 },
				{ -1, R.drawable.ck_pura_17_8, R.drawable.ck_pura_17_9 } };

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != -1) {
					mTiles.add(new MapTile(BitmapFactory.decodeResource(
							getResources(), tiles[x][y]), y * 640, x * 640));
				}
			}
		}

		// sets the size of the map.
		mMapWidth = 640 * 3;
		mMapHeight = 640 * 3;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		// Our move updates are calculated in ACTION_MOVE in the opposite
		// direction from how we want to move the scroll rect. Think of this
		// as dragging to the left being the same as sliding the scroll rect
		// to the right.
		int newScrollRectX = clamp(mScrollRectX - (int) mScrollByX, 0,
				getMapWidth() - mDisplayWidth);
		int newScrollRectY = clamp(mScrollRectY - (int) mScrollByY, 0,
				getMapHeight() - mDisplayHeight);

		// contains the tiles that need to be rendered
		ArrayList<MapTile> tilesToDraw = new ArrayList<MapTile>(4);

		// tile used to calculate modifications
		MapTile currentTile;

		// checks which tiles are visible.
		for (int x = 0; x < mTiles.size(); x++) {
			// gets the current tile of the iteration
			currentTile = mTiles.get(x);

			// checks if the tile is inside the viewport
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
}
