package com.commonsensenet.realfarm.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.commonsensenet.realfarm.map.utils.TileLoader;

public class OfflineMapView extends View {

	/** Helper class used to load the tiles from the SD.*/
	private final TileLoader mImageLoader;
	/** Current progress of the animation.*/
	private int mAnimationCounter;
	/** Number of updates required to perform the full animation. */
	private int mAnimationSteps;
	/** Handler used to control the animation and its progress.*/
	private Handler mAnimationHandler;
	/** Height of the display area in pixels. */
	private int mDisplayHeight;
	/** Width of the display area in pixels. */
	private int mDisplayWidth;
	/** Initial point where the animation should me done.*/
	private Point mInitialPoint;
	/** Underlying map representation in charge of the tile system. */
	private Map mMap;
	/** List of overlays added in the current map. */
	private ArrayList<Overlay> mOverlays;
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
	/** Target point where the map must animate to. */
	private Point mTargetPoint;

//	private Runnable mUpdateTimeTask = new Runnable() {
//		public void run() {
//
//			// calculates the displacement
//			mScrollByX = mScrollRectX
//					- (int) easeOutExpo(mAnimationCounter, mInitialPoint.x,
//							mTargetPoint.x - mInitialPoint.x, mAnimationSteps);
//			mScrollByY = mScrollRectY
//					- (int) easeOutExpo(mAnimationCounter, mInitialPoint.y,
//							mTargetPoint.y - mInitialPoint.y, mAnimationSteps);
//
//			// decreases the update timer.
//			mAnimationCounter++;
//
//			invalidate();
//			// ends the animation
//			if (mAnimationCounter >= mAnimationSteps
//					|| (Math.abs(mInitialPoint.x - mTargetPoint.x) < 2 && Math
//							.abs(mInitialPoint.y - mTargetPoint.y) < 2)) {
//				// updates final position.
//				mScrollRectX = mTargetPoint.x;
//				mScrollRectY = mTargetPoint.y;
//				// clears the points
//				mTargetPoint = null;
//				mInitialPoint = null;
//				// stops the handler
//				mAnimationHandler.removeCallbacks(mUpdateTimeTask);
//			} else {
//				// allows it to be called again
//				mAnimationHandler
//						.postAtTime(this, SystemClock.uptimeMillis() + 1);
//			}
//		}
//	};

	public OfflineMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// initializes the downloader.
		mImageLoader = new TileLoader(this);

		initOfflineMapView();

		// loads the desired map.
		// TODO: pass the coordinates as a parameter.
		mMap = Map.createMapFromCoordinate(new GeoPoint("14.054162,77.16711"),
				this);

		// loads the default map if the given point is not found.
		if (mMap == null)
			mMap = Map.createDefaultMap(this);

		// centers the map.
		centerMap();
	}

	public OfflineMapView(Context context, GeoPoint center) {
		super(context);

		// initializes the downloader.
		mImageLoader = new TileLoader(this);
		
		initOfflineMapView();

		// creates a new map instance with the given center.
		mMap = Map.createMapFromCoordinate(center, this);

		// loads the default map if the given point is not found.
		if (mMap == null)
			mMap = Map.createDefaultMap(this);

		// centers the map.
		centerMap();
	}

	public void animateTo(Point point) {

		// resets touch values.
		mScrollByX = 0;
		mScrollByY = 0;
		mStartX = 0;
		mStartY = 0;

		// transforms the point to the local coordinate system.
		mTargetPoint = new Point(clamp((int) (mMap.getWidth() * 0.5)
				- (int) (mDisplayWidth * 0.5) + point.x, 0, mMap.getWidth() - mDisplayWidth),
				clamp((int) (mMap.getHeight() * 0.5)
						- (int) (mDisplayHeight * 0.5) + point.y, 0,
						mMap.getHeight() - mDisplayHeight));
		// stores the initial point
		mInitialPoint = new Point(mScrollRectX, mScrollRectY);

		// resets the animation counter
		mAnimationCounter = 0;
		mAnimationSteps = (int) (euclideanDistance(mInitialPoint, mTargetPoint) / 10);

		if (mAnimationSteps != 0) {
			// starts the timer used to animate the map.
			mAnimationHandler = new Handler();
			// mAnimationHandler.removeCallbacks(mUpdateTimeTask);
			// mAnimationHandler.postDelayed(mUpdateTimeTask, 100);
		} else {
			// updates final position.
			mScrollRectX = mTargetPoint.x;
			mScrollRectY = mTargetPoint.y;
			// clears the points
			mTargetPoint = null;
			mInitialPoint = null;
		}
	}

	private void centerMap() {
		mScrollRectX = (int) (mMap.getWidth() * 0.5)
				- (int) (mDisplayWidth * 0.5);
		mScrollRectY = (int) (mMap.getHeight() * 0.5)
				- (int) (mDisplayHeight * 0.5);
	}

	private int clamp(int value, int min, int max) {
		return value < min ? min : (value > max ? max : value);
	}

	public void dispose() {
		if (mMap != null)
			mMap.dispose();
		mMap = null;
	}

	/**
	 * Easing equation function for a simple linear tweening, with no easing.
	 * 
	 * @param t
	 *            Current time (in frames or seconds)
	 * @param b
	 *            Starting value
	 * @param c
	 *            Change needed in value
	 * @param d
	 *            Expected easing duration (in frame or seconds)
	 * 
	 * @return the correct value
	 */
	protected double easeNone(int t, double b, double c, int d) {
		return c * t / d + b;
	}

	/**
	 * Easing equation function for a simple linear tweening, with out expo
	 * easing.
	 * 
	 * @param t
	 *            Current time (in frames or seconds)
	 * @param b
	 *            Starting value
	 * @param c
	 *            Change needed in value
	 * @param d
	 *            Expected easing duration (in frame or seconds)
	 * 
	 * @return the correct value
	 */
	protected double easeOutExpo(int t, double b, double c, int d) {
		return (t == d) ? b + c : c * 1.001 * (-Math.pow(2, -10 * t / d) + 1)
				+ b;
	}

	/**
	 * Calculates the distance between two given points.
	 * 
	 * @param p1 a point.
	 * @param p2 another point.
	 * 
	 * @return the distance between the given points.
	 */
	private double euclideanDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	public Point getCenterPoint() {
		Point absoluteCenter = mMap.getCenterPoint();

		absoluteCenter.offset(-mScrollRectX, -mScrollRectY);
		return absoluteCenter;
	}

	public final List<Overlay> getOverlays() {
		return mOverlays;
	}

	public int getZoomLevel() {
		return mMap.getZoomLevel();
	}

	private void initOfflineMapView() {

		// sets the current size of the screen in pixels.
		mDisplayWidth = getWidth();
		mDisplayHeight = getHeight();

		// initializes the overlay list
		mOverlays = new ArrayList<Overlay>();

		// initial values of scrolling variables.
		mScrollRectX = 0;
		mScrollRectY = 0;
		mScrollByX = 0;
		mScrollByY = 0;
		mStartX = 0;
		mStartY = 0;
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

		// move updates are calculated in ACTION_MOVE in the opposite
		// direction from how we want to move the scroll rectangle. think of
		// this as dragging to the left being the same as sliding the scroll
		// rectangle to the right.
		int newScrollRectX = clamp(mScrollRectX - (int) mScrollByX, 0,
				mMap.getWidth() - mDisplayWidth);
		int newScrollRectY = clamp(mScrollRectY - (int) mScrollByY, 0,
				mMap.getHeight() - mDisplayHeight);

		// average visible tiles
		final int INITIAL_ARRAY_SIZE = 6;
		// contains the tiles that need to be rendered
		ArrayList<MapTile> tilesToDraw = new ArrayList<MapTile>(
				INITIAL_ARRAY_SIZE);

		// tile used to calculate modifications
		MapTile currentTile;

		// checks which tiles are visible.
		for (int x = 0; x < mMap.getTiles().size(); x++) {
			// gets the current tile of the iteration
			currentTile = mMap.getTiles().get(x);

			// checks if the tile is inside the view port
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
			if(!currentTile.getIsBitmapLoaded())
				mImageLoader.load(currentTile);
		}

		// resets the current scroll coordinates to reflect the latest update
		mScrollRectX = newScrollRectX;
		mScrollRectY = newScrollRectY;

		// draws the overlays on top of the map.
		synchronized (mOverlays) {
			for (int x = 0; x < mOverlays.size(); x++) {
				mOverlays.get(x).draw(canvas, this);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		boolean valueWasInvalid = mDisplayWidth == 0;

		mDisplayWidth = w;
		mDisplayHeight = h;

		// sends the map to the center.
		if (valueWasInvalid) {
			centerMap();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mMap == null)
			return true;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// checks the overlays for events.
			synchronized (mOverlays) {
				boolean processed = false;
				for (int x = 0; x < mOverlays.size(); x++) {
					processed = mOverlays.get(x).onTouchEvent(event, this);
					if (processed)
						break;
				}
			}
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
		case MotionEvent.ACTION_UP:
			// resets the scroll values.
			mScrollByX = 0;
			mScrollByY = 0;
		}
		// done with this event so consume it
		return true;
	}

	public void setZoomLevel(int value) {
		throw new UnsupportedOperationException();
	}
}
