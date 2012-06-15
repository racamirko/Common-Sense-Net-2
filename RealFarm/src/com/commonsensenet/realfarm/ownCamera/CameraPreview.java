package com.commonsensenet.realfarm.ownCamera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mcamera;
	protected static final String TAG = "Camera Preview";

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mcamera = camera;
		// Install a SurfaceHolder.callback so we get notified
		// when the underlying surface is created and destroyed
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder mholder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {
			mcamera.setPreviewDisplay(mHolder);
			mcamera.startPreview();
			Log.d(TAG,"Preview created");

		} catch (IOException e) {
			Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		} catch (Exception e) {
			Log.d(TAG, "Error - surfaceCreated: " + e.getMessage());
		}

	}

	public void surfaceDestroyed(SurfaceHolder mholder) {
		// empty. Take care of releasing the camera preview in your activity
		mcamera.stopPreview();
		Log.d(TAG,"Preview destroyed");
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
			mcamera.stopPreview();
		} catch (Exception e) {
			Log.d(TAG, "Unable to stop preview");
		}
		Parameters p = mcamera.getParameters();
		mcamera.setDisplayOrientation(90);
		mcamera.setParameters(p);
		Log.d(TAG,"Surface changed");

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mcamera.setPreviewDisplay(mholder);
			mcamera.startPreview();
			Log.d(TAG, "Starting camera preview ");
		} catch (IOException e) {
			Log.d(TAG, "Error starting camera preview " + e.getMessage());
		} catch (Exception e) {
			Log.d(TAG, "Error 2 starting camera preview " + e.getMessage());
		}

	}

}
