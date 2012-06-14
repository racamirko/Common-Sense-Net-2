package com.commonsensenet.realfarm.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.commonsensenet.realfarm.map.utils.TileLoader;

public class OfflineMapView extends View {

	/** Current progress of the animation. */
	private int mAnimationCounter;
	/** Handler used to control the animation and its progress. */
	private Handler mAnimationHandler;
	/** Number of updates required to perform the full animation. */
	private int mAnimationSteps;
	/** Height of the display area in pixels. */
	private int mDisplayHeight;
	/** Width of the display area in pixels. */
	private int mDisplayWidth;
	/** Helper class used to load the tiles from the SD. */
	private TileLoader mImageLoader;
	/** Initial point where the animation should me done. */
	private Point mInitialPoint;
	/** Underlying map representation in charge of the tile system. */
	private Map mMap;
	/** Used to notify that a plot was tapped. */
	private OnOverlayTappedListener mOnPlotTappedListener;
	/** List of overlays added in the current map. */
	private ArrayList<Overlay> mOverlays;
	
	/** Amount to scroll in the x,y coordinate product of the last ACTION_MOVE. */
	private PointF mScrollBy;	
	/** Current position of the top left corner of the scroll rectangle. */
	private Point mScrollRect;	
	/** Initial position in the x,y coordinate used to track the movement. */
	private PointF mStartPt;
	/** Target point where the map must animate to. */
	private Point mTargetPoint;

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			// calculates the displacement
			mScrollBy.x = mScrollRect.x
					- (int) easeOutExpo(mAnimationCounter, mInitialPoint.x,
							mTargetPoint.x - mInitialPoint.x, mAnimationSteps);
			mScrollBy.y = mScrollRect.y
					- (int) easeOutExpo(mAnimationCounter, mInitialPoint.y,
							mTargetPoint.y - mInitialPoint.y, mAnimationSteps);

			// decreases the update timer.
			mAnimationCounter++;

			invalidate();
			// ends the animation
			if (mAnimationCounter >= mAnimationSteps
					|| (Math.abs(mInitialPoint.x - mTargetPoint.x) < 2 && Math
							.abs(mInitialPoint.y - mTargetPoint.y) < 2)) {
				// updates final position.
				mScrollRect.x = mTargetPoint.x;
				mScrollRect.y = mTargetPoint.y;
				// clears the points
				mTargetPoint = null;
				mInitialPoint = null;
				// stops the handler
				mAnimationHandler.removeCallbacks(mUpdateTimeTask);
				mAnimationHandler = null;
			} else {
				// allows it to be called again
				mAnimationHandler.postAtTime(this,
						SystemClock.uptimeMillis() + 1);
			}
		}
	};

	public OfflineMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initOfflineMapView(null);
	}

	public OfflineMapView(Context context, GeoPoint center) {
		super(context);
		initOfflineMapView(center);
	}
	
	private void initOfflineMapView(GeoPoint center) {
		if(mImageLoader != null)
			return; // initialize only once
		
		// sets the current size of the screen in pixels.
		mDisplayWidth = getWidth();
		mDisplayHeight = getHeight();

		// initializes the overlay list
		mOverlays = new ArrayList<Overlay>();

		// initial values of scrolling variables.
		mScrollRect = new Point(0,0);
		mScrollBy = new PointF(0,0);
		mStartPt = new PointF(0,0);
		
		// initializes the downloader.
		mImageLoader = new TileLoader(this);

		if( center == null)
			mMap = Map.createMapFromCoordinate(new GeoPoint("14.054162,77.16711"), this);
		else
			mMap = Map.createMapFromCoordinate(center, this);
		
		// loads the default map if the given point is not found.
		if (mMap == null)
			mMap = Map.createDefaultMap(this);

		// centers the map.
		centerMap();
	}

	public void animateTo(Point point) {

		// resets touch values.
		mScrollBy.x = 0;
		mScrollBy.y = 0;
		mStartPt.x = 0;
		mStartPt.y = 0;

		// transforms the point to the local coordinate system.
		mTargetPoint = new Point(clamp((int) (mMap.getWidth() * 0.5)
				- (int) (mDisplayWidth * 0.5) + point.x, 0, mMap.getWidth()
				- mDisplayWidth), clamp((int) (mMap.getHeight() * 0.5)
				- (int) (mDisplayHeight * 0.5) + point.y, 0, mMap.getHeight()
				- mDisplayHeight));
		// stores the initial point
		mInitialPoint = new Point(mScrollRect.x, mScrollRect.y);

		// resets the animation counter
		mAnimationCounter = 0;
		mAnimationSteps = (int) (euclideanDistance(mInitialPoint, mTargetPoint) / 10);

		if (mAnimationSteps != 0) {
			// starts the timer used to animate the map.
			mAnimationHandler = new Handler();
			mAnimationHandler.postDelayed(mUpdateTimeTask, 100);
		} else {
			// updates final position.
			mScrollRect.x = mTargetPoint.x;
			mScrollRect.y = mTargetPoint.y;
			// clears the points
			mTargetPoint = null;
			mInitialPoint = null;
		}
	}

	private void centerMap() {
		mScrollRect.x = (int) (mMap.getWidth() * 0.5)
				- (int) (mDisplayWidth * 0.5);
		mScrollRect.y = (int) (mMap.getHeight() * 0.5)
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
	 * @param p1
	 *            a point.
	 * @param p2
	 *            another point.
	 * 
	 * @return the distance between the given points.
	 */
	private double euclideanDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	public Point getCenterPoint() {
		Point absoluteCenter = mMap.getCenterPoint();

		absoluteCenter.offset(-mScrollRect.x, -mScrollRect.y);
		return absoluteCenter;
	}

	public final List<Overlay> getOverlays() {
		return mOverlays;
	}

	public int getZoomLevel() {
		return mMap.getZoomLevel();
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
		int newScrollRectX = clamp(mScrollRect.x - (int) mScrollBy.x, 0,
				mMap.getWidth() - mDisplayWidth);
		int newScrollRectY = clamp(mScrollRect.y - (int) mScrollBy.y, 0,
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
			if (!currentTile.getIsBitmapLoaded())
				mImageLoader.load(currentTile);
		}

		// resets the current scroll coordinates to reflect the latest update
		mScrollRect.x = newScrollRectX;
		mScrollRect.y = newScrollRectY;

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

		// stops the current animation.
		if (mAnimationHandler != null) {
			mAnimationHandler.removeCallbacks(mUpdateTimeTask);
			mAnimationHandler = null;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// checks the overlays for events.
			synchronized (mOverlays) {
				boolean processed = false;
				for (int x = 0; x < mOverlays.size(); x++) {
					processed = mOverlays.get(x).onTouchEvent(event, this);
					if (processed) {
						if (mOnPlotTappedListener != null)
							mOnPlotTappedListener.onOverlayTapped(mOverlays
									.get(x));
						break;
					}
				}
			}
			// Remember our initial down event location.
			mStartPt.x = event.getRawX();
			mStartPt.y = event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			// gets movement values from the event
			float x = event.getRawX();
			float y = event.getRawY();

			// calculate move update considering the initial value and
			// the current one obtained from the event.
			mScrollBy.x = x - mStartPt.x;
			mScrollBy.y = y - mStartPt.y;

			// resets the start x and y position to the latest value.
			mStartPt.x = x;
			mStartPt.y = y;

			// force a redraw to update the map.
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			// resets the scroll values.
			mScrollBy.x = 0;
			mScrollBy.y = 0;
		}
		// done with this event so consume it
		return true;
	}

	public void setOnOverlayTappedListener(OnOverlayTappedListener l) {
		mOnPlotTappedListener = l;
	}

	public void setZoomLevel(int value) {
		throw new UnsupportedOperationException();
	}
}
