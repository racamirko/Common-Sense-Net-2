package com.commonsensenet.realfarm.ownCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.My_settings_plot_details;
import com.commonsensenet.realfarm.R;

public class ViewPictureActivity extends Activity {

	public static final int MEDIA_TYPE_IMAGE = 1;
	protected static final String TAG = "Check_Image";
	protected Bitmap bMap = null;
	private ImageView iView = null;
	private Uri image_file_uri;
	private File image_file;
	private String image_path;
	private int year, month, day;
	private TextView displayDate;
	private TextView displayTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		Log.d(TAG, "Entered new acivity");

		displayDate = (TextView) findViewById(R.id.fillDate);
		displayTime = (TextView) findViewById(R.id.fillTime);

		// receive image in byte[] passed by OwnCameraActivity
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			CharSequence date_todisplay = (CharSequence) extras
					.get("date_selected");
			CharSequence time_todisplay = (CharSequence) extras
					.get("time_selected");
			int[] date = (int[]) extras.get("date");
			// int[] time = (int[]) extras.get("time");
			day = date[0];
			month = date[1];
			year = date[2];
			// hours = time[0];
			// minutes = time[1];

			displayDate.setText(date_todisplay);
			displayTime.setText(time_todisplay);
			image_file_uri = (Uri) extras.get("image_file_uri");
			Log.d(TAG, "Picture uri transfered to next activity"
					+ image_file_uri);
		} else {
			// setCurrentDateOnView();
			Log.d(TAG, "No data received");
		}

		// Add image to the imageview of the layout
		iView = (ImageView) findViewById(R.id.viewPhotoTakenView);

		ContentResolver c = getContentResolver();
		InputStream is = null;
		try {
			is = c.openInputStream(image_file_uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;

		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

		// rotate image
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Global._rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		iView.setImageBitmap(Global._rotated);

		// iView.setImageURI(image_file_uri);
		Log.d(TAG, "Image added to view" + image_file_uri);

		image_path = image_file_uri.getPath();
		image_file = new File(image_path);

		// Adding listener to the retake button
		ImageButton retakeButton = (ImageButton) findViewById(R.id.button_retake);
		retakeButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO: clean image and return to previous activity

				// delete image from sd card

				boolean deleted = image_file.delete();

				if (deleted)
					Log.d(TAG, "Image file deleted");
				else
					Log.d(TAG, "File still in gallery");

				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
						.parse("file://"
								+ Environment.getExternalStorageDirectory())));

				// go back to camera
				Intent intent = new Intent();
				intent.putExtra("retake", true);
				setResult(RESULT_OK, intent);
				Log.d(TAG, "Return to camera.");
				finish();
			}
		});

		// Adding listener to the save button
		ImageButton saveButton = (ImageButton) findViewById(R.id.button_save);
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				/*
				 * // add event to db : add image and time-date to db db =
				 * openOrCreateDatabase("EventsDB", MODE_PRIVATE, null); //
				 * month = month + 1; //
				 * db.execSQL("INSERT INTO EventsTable VALUES ('" + year + //
				 * "', '" // + month + "', '" + day + "', '" + hours + minutes
				 * // + "', '" + Constants.PICTURE + "','" + image_file_path //
				 * + "');"); month = month + 1;
				 * db.execSQL("INSERT INTO EventsTable VALUES ('" + year +
				 * "', '" + month + "', '" + day + "', '" + pad(hours) +
				 * pad(minutes) + "', '" + Constants.PICTURE + "','" +
				 * image_file + "');");
				 */
				Intent kintent = new Intent(ViewPictureActivity.this,
						My_settings_plot_details.class);
				Global.flag_camera = true;
				startActivity(kintent);
				ViewPictureActivity.this.finish();
			}
		});
	}

	// display current date
	public void setCurrentDateOnView() {

		displayDate = (TextView) findViewById(R.id.fillDate);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		displayDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append("-").append(month + 1).append("-")
				.append(year).append(" "));

		// set current time into textview

		displayTime = (TextView) findViewById(R.id.fillTime);

		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String formattedTime = sdf.format(currentDate);
		displayTime.setText(formattedTime);
		// hours = currentDate.getHours();
		// minutes = currentDate.getMinutes();
		// displayTime.setText(new
		// StringBuilder().append(hours).append(":").append(minutes));

	}

	// override on back pressed

	@Override
	public void onBackPressed() {
		/*
		 * Log.d(TAG, "Back button pressed-return to take photo"); Bundle bundle
		 * = new Bundle(); bundle.putBoolean("retake", true); Intent mIntent =
		 * new Intent(); mIntent.putExtras(bundle); setResult(RESULT_OK,
		 * mIntent); super.onBackPressed();
		 */
		Intent adminintent123 = new Intent(ViewPictureActivity.this,
				My_settings_plot_details.class);
		startActivity(adminintent123);
		ViewPictureActivity.this.finish();

	}

}
