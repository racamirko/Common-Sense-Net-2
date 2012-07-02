package com.commonsensenet.realfarm.ownCamera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	protected static final String LOG_TAG = "CameraPreview";
	/** Camera interface used to capture the pictures. */
	private Camera mCamera;
	/** Holder used to detect the SurfaceView events. */
	private SurfaceHolder mHolder;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		// Install a SurfaceHolder.callback so we get notified
		// when the underlying surface is created and destroyed
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceChanged(SurfaceHolder mholder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			Log.d(LOG_TAG, "Unable to stop preview");
		}
		Parameters p = mCamera.getParameters();
		mCamera.setDisplayOrientation(90);
		mCamera.setParameters(p);
		Log.d(LOG_TAG, "Surface changed");

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mholder);
			mCamera.startPreview();
			Log.d(LOG_TAG, "Starting camera preview ");
		} catch (IOException e) {
			Log.d(LOG_TAG, "Error starting camera preview " + e.getMessage());
		} catch (Exception e) {
			Log.d(LOG_TAG, "Error 2 starting camera preview " + e.getMessage());
		}
	}

	public void surfaceCreated(SurfaceHolder mholder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
			Log.d(LOG_TAG, "Preview created");

		} catch (IOException e) {
			Log.d(LOG_TAG, "Error setting camera preview: " + e.getMessage());
		} catch (Exception e) {
			Log.d(LOG_TAG, "Error - surfaceCreated: " + e.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder mholder) {
		// empty. Take care of releasing the camera preview in your activity
		mCamera.stopPreview();
		Log.d(LOG_TAG, "Preview destroyed");
	}
}
