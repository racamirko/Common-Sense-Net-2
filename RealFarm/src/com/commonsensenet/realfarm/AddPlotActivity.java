package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.ownCamera.OwnCameraActivity;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class AddPlotActivity extends DataFormActivity {

	/** Name used to log the activity of the class. */
	public static final String LOG_TAG = "AddPlotActivity";

	public static final String MAIN_CROP = "mainCrop";
	public static final String PLOT_IMAGE = "plotImage";
	public static final String SIZE = "size";
	public static final String SOIL_TYPE = "soilType";

	private int mMainCrop = -1;
	private String mPlotImage = "0";
	private double mSize = -1;
	private int mSoilType = -1;

	/**
	 * Adds the current plot to the database.
	 */
	private void addPlotToDatabase() {

		// inserts the new action
		Global.plotId = (int) mDataProvider.addPlot(Global.userId, mMainCrop,
				mPlotImage, mSoilType, mSize, 0, 0);

		// logs the event
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"add plot to database");

		// shows the name of the added plot.
		Toast.makeText(
				getBaseContext(),
				"Plot Details is put to Database " + mPlotImage + " "
						+ mSoilType, Toast.LENGTH_SHORT).show();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.act_add_plot, LOG_TAG);

		// adds the name of the fields to validate.
		mResultsMap.put(SOIL_TYPE, -1);
		mResultsMap.put(MAIN_CROP, -1);
		mResultsMap.put(SIZE, 0.0f);

		final View plotImageRow;
		final View soilTypeRow;
		final View mainCropRow;
		final View sizeRow;

		// gets the rows from the layout
		plotImageRow = findViewById(R.id.plot_tr);
		soilTypeRow = findViewById(R.id.soiltype_tr);
		mainCropRow = findViewById(R.id.maincrop_tr);
		sizeRow = findViewById(R.id.size_tr);

		// adds the long click listener to enable the help function.
		plotImageRow.setOnLongClickListener(this);
		soilTypeRow.setOnLongClickListener(this);
		mainCropRow.setOnLongClickListener(this);
		sizeRow.setOnLongClickListener(this);

		View plotimage = findViewById(R.id.dlg_plot_img_test);
		View plotsoil = findViewById(R.id.dlg_lbl_soil_plot);
		View plotcrop = findViewById(R.id.dlg_lbl_crop_plot);
		View plotsize = findViewById(R.id.size_txt);

		// if an image is available it is set to the view.
		if (Global.cameraFlag) {
			// saves the image path
			mPlotImage = Global.plotImagePath;
			((ImageView) plotimage).setImageBitmap(Global.rotated);

			Global.cameraFlag = false;
		}

		// adds the long click listeners to enable the help function.
		plotimage.setOnLongClickListener(this);
		plotsoil.setOnLongClickListener(this);
		plotcrop.setOnLongClickListener(this);
		plotsize.setOnLongClickListener(this);

		plotimage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				startActivity(new Intent(AddPlotActivity.this,
						OwnCameraActivity.class));

				// TODO: orbolanos: is this necessary?
				AddPlotActivity.this.finish();

			}
		});

		plotsoil.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in plot image dialog", "in dialog");
				stopAudio();
				List<Resource> data = mDataProvider
						.getResources(RealFarmDatabase.RESOURCE_TYPE_SOILTYPE);
				displayDialog(v, data, SOIL_TYPE, "Select the soil type",
						R.raw.problems, R.id.dlg_lbl_soil_plot,
						R.id.soiltype_tr, 0);
			}
		});

		plotcrop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in crop plot dialog", "in dialog");

				stopAudio();
				List<Resource> data = mDataProvider.getVarieties();
				displayDialog(v, data, MAIN_CROP, "Select the variety",
						R.raw.problems, R.id.dlg_lbl_crop_plot,
						R.id.maincrop_tr, 0);
			}
		});

		plotsize.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Enter the plot size in acres", SIZE,
						R.raw.dateinfo, 0, 30, 2, 0.1, 1, R.id.size_txt,
						R.id.size_tr, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo, R.raw.dateinfo);

			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// shows the help icon based on the view.
		showHelpIcon(v);

		if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help);
		}

		if (v.getId() == R.id.dlg_plot_img_test) {
			playAudio(R.raw.plotimage);
		}

		if (v.getId() == R.id.dlg_lbl_soil_plot) {
			playAudio(R.raw.soiltype);
		}

		if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.yieldinfo);
		}

		if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.yieldinfo);
		}

		if (v.getId() == R.id.plot_tr) {
			playAudio(R.raw.plotimage);
		}

		if (v.getId() == R.id.soiltype_tr) {
			playAudio(R.raw.soiltype);
		}

		if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.maincrop);
		}

		if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.maincrop);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// values obtained that need to be validated.
		mSoilType = (Integer) mResultsMap.get(SOIL_TYPE);
		mMainCrop = (Integer) mResultsMap.get(MAIN_CROP);
		mSize = Double.valueOf(mResultsMap.get(SIZE).toString());

		boolean isValid = true;

		if (!mPlotImage.toString().equalsIgnoreCase("0")) {
			highlightField(R.id.plot_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.plot_tr, true);
		}

		if (mSoilType != -1) {

			highlightField(R.id.soiltype_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.soiltype_tr, true);
		}

		if (mMainCrop != -1) {
			highlightField(R.id.maincrop_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.maincrop_tr, true);
		}

		if (mSize != -1) {
			highlightField(R.id.size_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.size_tr, true);
		}

		// if form is valid the plot is added to the database.
		if (isValid) {

			addPlotToDatabase();
			return true;
		}
		return false;
	}
}
