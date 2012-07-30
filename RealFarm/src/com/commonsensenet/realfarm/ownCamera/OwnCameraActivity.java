package com.commonsensenet.realfarm.ownCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;

public class OwnCameraActivity extends Activity implements
		SurfaceHolder.Callback {

	private final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final String LINE_SEP = System.getProperty("line.separator");
	public static final String LOG_TAG = "Main Camera Activity";
	private Camera mCamera;
	private SurfaceHolder mHolder;
	private SurfaceView mPreview = null;
	private Uri mTargetResource = Media.EXTERNAL_CONTENT_URI;
	private int year, month, day;
	private int hours, minutes;
	private TextView mDisplayDate;
	private TextView mDisplayTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.own_camera_main);

		// displayDate = (TextView) findViewById(R.id.fillDate);
		// displayTime = (TextView) findViewById(R.id.fillTime);

		// setCurrentDateOnView();

		// create an instance of camera

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
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				mCamera.takePicture(null, null, mPicture);
				Log.d(LOG_TAG, "Picture taken");
				Toast.makeText(OwnCameraActivity.this, "Picture taken.",
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuItem item = menu.add(0, 0, 0, "View Pictures");
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						OwnCameraActivity.this.mTargetResource);
				startActivity(intent);
				return true;
			}
		});
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		Bundle extras = null;
		if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {

			if (resultCode == RESULT_OK)
				extras = intent.getExtras();

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

	// display current date
	public void setCurrentDateOnView() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		mDisplayDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append("-").append(month + 1).append("-")
				.append(year).append(" "));

		// set current time into textview

		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String formattedTime = sdf.format(currentDate);
		mDisplayTime.setText(formattedTime);
		hours = currentDate.getHours();
		minutes = currentDate.getMinutes();
		// displayTime.setText(new
		// StringBuilder().append(hours).append(":").append(minutes));

	}

	/** send raw image to view picture activity, image will be saved there */

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
					Toast.makeText(OwnCameraActivity.this, "Image saved.",
							Toast.LENGTH_SHORT).show();

				} catch (FileNotFoundException e) {
					Log.d(LOG_TAG, "File not found: " + e.getMessage());
				} catch (IOException e) {
					Log.d(LOG_TAG, "Error accessing file: " + e.getMessage());
				}

				Intent intent = new Intent(getBaseContext(),
						ViewPictureActivity.class);

				Uri image_file_uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				intent.putExtra("image_file_uri", image_file_uri);
				// intent.putExtra("date_selected", displayDate.getText());
				// intent.putExtra("time_selected", displayTime.getText());
				int[] date = { day, month, year };
				int[] time = { hours, minutes };
				intent.putExtra("date", date);
				intent.putExtra("time", time);
				startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
				OwnCameraActivity.this.finish();
			} else {
				Log.e(LOG_TAG, "No image");
			}
		}
	};

	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
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

		if (this.mCamera != null) {
			this.mCamera.stopPreview();
			this.mCamera.release();
		}
		Log.d(LOG_TAG, "Preview destroyed");
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
			this.mCamera.stopPreview();
		} catch (Exception e) {
			Log.d(LOG_TAG, "Unable to stop preview");
		}
		Parameters p = this.mCamera.getParameters();
		this.mCamera.setDisplayOrientation(90);
		this.mCamera.setParameters(p);
		Log.d(LOG_TAG, "Surface changed");

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			this.mCamera.setPreviewDisplay(mholder);
			this.mCamera.startPreview();
			Log.d(LOG_TAG, "Starting camera preview ");
		} catch (IOException e) {
			Log.d(LOG_TAG, "Error starting camera preview " + e.getMessage());
		} catch (Exception e) {
			Log.d(LOG_TAG, "Error 2 starting camera preview " + e.getMessage());
		}

	}

	public void reload() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (this.mCamera != null)
			this.mCamera.stopPreview();
		Log.d(LOG_TAG, "Preview stoped");
		releaseCamera();
		Log.d(LOG_TAG, "Activity on pause");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "Ondestroy");
		if (this.mCamera != null)
			this.mCamera.stopPreview();
		releaseCamera();
		Log.d(LOG_TAG, "Activity destroyed");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (this.mCamera != null)
			this.mCamera.stopPreview();
		releaseCamera();
		Log.d(LOG_TAG, "Activity stopped");
	}

	@Override
	protected void onRestart() {

		super.onRestart();
		Log.d(LOG_TAG, "Activity restarting");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(LOG_TAG, "Activity starting");

	}

	@Override
	protected void onResume() {
		Log.d(LOG_TAG, "Activity resuming");
		Log.e(getClass().getSimpleName(), "onResume");
		super.onResume();
	}

	private void releaseCamera() {
		if (this.mCamera != null) {
			Log.d(LOG_TAG, "Releasing camera");
			this.mCamera.release(); // release the camera for other applications
			this.mCamera = null;
		}
	}

	/** create a file uri for saving the image */

	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

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

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +

			"IMG_" + timeStamp + ".jpg");
		} else
			return null;
		return mediaFile;
	}

}