package com.commonsensenet.realfarm.ownCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.commonsensenet.realfarm.AddPlotActivity;
import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;

public class ViewPictureActivity extends Activity {

	/** Tag used to log the activity of the class. */
	public static final String LOG_TAG = "ViewPictureActivity";
	public static final int MEDIA_TYPE_IMAGE = 1;
	private File mImageFile;
	private Uri mImageFileUri;
	private String mImagePath;
	private ImageView mImageView = null;

	@Override
	public void onBackPressed() {

		Intent adminintent123 = new Intent(ViewPictureActivity.this,
				AddPlotActivity.class);
		startActivity(adminintent123);
		ViewPictureActivity.this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		Log.d(LOG_TAG, "Entered new acivity");

		// receive image in byte[] passed by OwnCameraActivity
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			mImageFileUri = (Uri) extras.get("image_file_uri");
			Log.d(LOG_TAG, "Picture uri transfered to next activity"
					+ mImageFileUri);
		} else {
			// setCurrentDateOnView();
			Log.d(LOG_TAG, "No data received");
		}

		// Add image to the ImageView of the layout
		mImageView = (ImageView) findViewById(R.id.viewPhotoTakenView);

		ContentResolver c = getContentResolver();
		InputStream is = null;
		try {
			is = c.openInputStream(mImageFileUri);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;

		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

		// rotate image
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Global.rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		mImageView.setImageBitmap(Global.rotated);

		Log.d(LOG_TAG, "Image added to view" + mImageFileUri);

		mImagePath = mImageFileUri.getPath();
		mImageFile = new File(mImagePath);
		Global.plotImagePath = mImagePath;

		// Adding listener to the retake button
		Button retakeButton = (Button) findViewById(R.id.button_cancel);
		retakeButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO: clean image and return to previous activity
				// delete image from sd card

				boolean deleted = mImageFile.delete();

				if (deleted)
					Log.d(LOG_TAG, "Image file deleted");
				else
					Log.d(LOG_TAG, "File still in gallery");

				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
						.parse("file://"
								+ Environment.getExternalStorageDirectory())));

				// go back to camera
				Intent intent = new Intent();
				intent.putExtra("retake", true);
				setResult(RESULT_OK, intent);
				Log.d(LOG_TAG, "Return to camera.");
				finish();
			}
		});

		// Adding listener to the save button
		Button saveButton = (Button) findViewById(R.id.button_ok);
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Global.cameraFlag = true;

				startActivity(new Intent(ViewPictureActivity.this,
						AddPlotActivity.class));
				ViewPictureActivity.this.finish();
			}
		});
	}
}
