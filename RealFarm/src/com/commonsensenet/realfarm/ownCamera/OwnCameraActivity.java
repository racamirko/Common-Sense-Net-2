package com.commonsensenet.realfarm.ownCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class OwnCameraActivity extends Activity implements
		SurfaceHolder.Callback, OnLongClickListener {

	/** Date format used to create the Timestamp. */
	protected static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
	/** Format of the Filename used to store the files. */
	protected static final String FILENAME_FORMAT = "%s%sIMG_%s.jpg";
	/** Tag used to log the activity of the class. */
	public static final String LOG_TAG = "OwnCameraActivity";

	public static final int MEDIA_TYPE_IMAGE = 1;

	/** Create a File for saving the image */
	private static File getOutputMediaFile(int type) {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"OwnCamera");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("OwnCamera", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());

		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(String.format(FILENAME_FORMAT,
					mediaStorageDir.getPath(), File.separator, timeStamp));
		} else {
			return null;
		}
		return mediaFile;
	}

	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
	}

	/** Camera used to take the picture. */
	private Camera mCamera;
	/** Surface used to render the view of the camera. */
	private SurfaceHolder mHolder;

	/**
	 * Callback used to detect when a picture has been taken. It receives the
	 * raw image which needs to be saved.
	 */
	private PictureCallback mPicture = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {

			// send picture to ViewPictureActivity
			if (data != null) {

				File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
				if (pictureFile == null) {
					Log.d(LOG_TAG,
							"Error creating media file, check storage permissions: ");
					return;
				}

				try {
					FileOutputStream fos = new FileOutputStream(pictureFile);
					fos.write(data);
					fos.close();
					// Toast.makeText(OwnCameraActivity.this, "Image saved.",
					// Toast.LENGTH_SHORT).show();

				} catch (FileNotFoundException e) {
					Log.d(LOG_TAG, "File not found: " + e.getMessage());
				} catch (IOException e) {
					Log.d(LOG_TAG, "Error accessing file: " + e.getMessage());
				}

				Intent intent = new Intent(getBaseContext(),
						ViewPictureActivity.class);

				Uri imageFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				intent.putExtra("image_file_uri", imageFileUri);
				startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
				OwnCameraActivity.this.finish();
			} else {
				Log.e(LOG_TAG, "No image");
			}
		}
	};

	/** Surface used to preview the camera. */
	private SurfaceView mPreview = null;

	private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;

	public void addToSoundQueue(int resid) {
		SoundQueue sq = SoundQueue.getInstance();
		// adds the sound to the queue
		sq.addToQueue(resid);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		Bundle extras = null;
		if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {
				extras = intent.getExtras();
			}

			boolean retake = false;
			if (extras != null) {
				retake = extras.getBoolean("retake");
			}
			// if sub-activity sends true reload main-activity to take a new
			// picture
			if (retake) {
				reload();
				Log.d(LOG_TAG, "reloading");
			} else
				Log.d(LOG_TAG, "Sub-activity does not want to reload");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.own_camera_main);

		// releases any active camera
		if (mCamera != null) {
			mCamera.release();
		}

		try {
			mCamera = Camera.open();
			// get Camera parameters
			Camera.Parameters params = mCamera.getParameters();
			// set the focus mode
			mCamera.setDisplayOrientation(90);
			params.setPictureFormat(ImageFormat.JPEG);
			// set Camera parameters
			mCamera.setParameters(params);
			Log.d(LOG_TAG, "Camera instantiated");
		} catch (Exception e) {
			// Camera not available
			Log.d(LOG_TAG, "Cannot instantiate camera");
		}

		// Create the preview view and set it as the content of the activity
		// mpreview = new CameraPreview(this, mcamera);

		// Install a SurfaceHolder.callback so we get notified
		// when the underlying surface is created and destroyed

		mPreview = new SurfaceView(getBaseContext());
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		FrameLayout previewLayout = (FrameLayout) findViewById(R.id.camera_preview);
		previewLayout.addView(mPreview);

		// Adding listener to the capture button
		View captureButton = findViewById(R.id.button_capture);
		captureButton.setOnLongClickListener(this);
		captureButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mCamera.takePicture(null, null, mPicture);
				Log.d(LOG_TAG, "Picture taken");
				Toast.makeText(OwnCameraActivity.this, "Picture taken.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "Ondestroy");
		if (mCamera != null)
			mCamera.stopPreview();
		releaseCamera();
		Log.d(LOG_TAG, "Activity destroyed");
	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button_capture) {
			addToSoundQueue(R.raw.plotimage);
			playSound();

		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mCamera != null)
			mCamera.stopPreview();
		Log.d(LOG_TAG, "Preview stoped");
		releaseCamera();
		Log.d(LOG_TAG, "Activity on pause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(LOG_TAG, "Activity restarting");
	}

	@Override
	protected void onResume() {
		Log.d(LOG_TAG, "Activity resuming");
		Log.e(getClass().getSimpleName(), "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(LOG_TAG, "Activity starting");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mCamera != null)
			mCamera.stopPreview();
		releaseCamera();
		Log.d(LOG_TAG, "Activity stopped");
	}

	public void playSound() {
		SoundQueue.getInstance().play();
	}

	private void releaseCamera() {
		if (mCamera != null) {
			Log.d(LOG_TAG, "Releasing camera");
			// release the camera for other applications
			mCamera.release();
			mCamera = null;
		}
	}

	public void reload() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	protected void stopAudio() {
		SoundQueue.getInstance().stop();
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

	// Camera preview methods
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
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
		}
		Log.d(LOG_TAG, "Preview destroyed");
	}
}