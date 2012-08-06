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

	public static final String PLOT_IMAGE = "plotImage";
	public static final String SOIL_TYPE = "soilType";
	public static final String MAIN_CROP = "mainCrop";
	public static final String SIZE = "size";

	private String mMainCrop = "0";
	private String mPlotImage = "0";
	private int mSeedTypeId = 0;
	private String mSize = "0";
	private String mSoilType = "0";

	/**
	 * Adds the current plot to the database.
	 */
	private void addPlotToDatabase() {

		// inserts the new action
		Global.plotId = (int) mDataProvider.addPlot(Global.userId, mSeedTypeId,
				mPlotImage, mSoilType, Float.parseFloat(mSize), 0, 0);

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
		mResultsMap.put(SOIL_TYPE, "0");
		mResultsMap.put(MAIN_CROP, "0");
		mResultsMap.put(SIZE, "0");

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
						R.raw.dateinfo, 0, 50, 0, 0.1, 1, R.id.size_txt,
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
		mSoilType = mResultsMap.get(SOIL_TYPE).toString();
		mMainCrop = mResultsMap.get(MAIN_CROP).toString();
		mSize = mResultsMap.get(SIZE).toString();

		int flag1, flag2, flag3, flag4;
		if (mPlotImage.toString().equalsIgnoreCase("0")) {
			flag1 = 1;

			View tr_feedback = findViewById(R.id.plot_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);

		} else {
			flag1 = 0;

			View tr_feedback = findViewById(R.id.plot_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		// TODO: overrides image requirement
		flag1 = 0;

		if (mSoilType.toString().equalsIgnoreCase("0")) {

			flag2 = 1;

			View tr_feedback = findViewById(R.id.soiltype_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		} else {

			flag2 = 0;

			View tr_feedback = findViewById(R.id.soiltype_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (mMainCrop.toString().equalsIgnoreCase("0")) {

			flag3 = 1;

			View tr_feedback = findViewById(R.id.maincrop_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		} else {

			flag3 = 0;

			View tr_feedback = findViewById(R.id.maincrop_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (mSize.toString().equalsIgnoreCase("0")) {

			flag4 = 1;

			View tr_feedback = findViewById(R.id.size_tr);
			tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		} else {

			flag4 = 0;

			View tr_feedback = findViewById(R.id.size_tr);
			tr_feedback
					.setBackgroundResource(android.R.drawable.list_selector_background);
		}

		if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {

			addPlotToDatabase();
			return true;
		}
		return false;
	}
}
