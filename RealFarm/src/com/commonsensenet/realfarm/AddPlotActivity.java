package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.ownCamera.OwnCameraActivity;
import com.commonsensenet.realfarm.ownCamera.ViewPictureActivity;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class AddPlotActivity extends DataFormActivity {

	public static final String MAIN_CROP = "mainCrop";
	public static final String PLOT_IMAGE = "plotImage";
	public static final String SIZE = "size";
	public static final String TYPE = "type";
	public static final String SOIL_TYPE = "soilType";

	private int mMainCrop;
	private String mPlotImage = "0";
	private double mSize;
	private int mType;
	private int mSoilType;

	/**
	 * Adds the current plot to the database.
	 */
	private void addPlotToDatabase() {

		// inserts the new action
		Global.plotId = mDataProvider.addPlot(Global.userId, mMainCrop,
				mSoilType, mPlotImage, mSize, mType);

		// logs the event
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"add plot to database");

		// shows the name of the added plot.
		/*Toast.makeText(
				getBaseContext(),
				"Plot Details is put to Database " + mPlotImage + " "
						+ mSoilType, Toast.LENGTH_SHORT).show();*/
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.act_add_plot);

		// adds the name of the fields to validate.
		mResultsMap.put(SOIL_TYPE, -1);
		mResultsMap.put(MAIN_CROP, -1);
		mResultsMap.put(SIZE, "0.0");
		mResultsMap.put(TYPE, -1);

		final View plotImageRow;
		final View soilTypeRow;
		final View mainCropRow;
		final View sizeRow;
		final View typeRow;

		// gets the rows from the layout
		plotImageRow = findViewById(R.id.plot_tr);
		soilTypeRow = findViewById(R.id.soiltype_tr);
		mainCropRow = findViewById(R.id.maincrop_tr);
		sizeRow = findViewById(R.id.size_tr);
		typeRow = findViewById(R.id.type_tr);

		// adds the long click listener to enable the help function.
		plotImageRow.setOnLongClickListener(this);
		soilTypeRow.setOnLongClickListener(this);
		mainCropRow.setOnLongClickListener(this);
		sizeRow.setOnLongClickListener(this);
		typeRow.setOnLongClickListener(this);

		View plotimage = findViewById(R.id.dlg_plot_img_test);
		View plotsoil = findViewById(R.id.dlg_lbl_soil_plot);
		View plotcrop = findViewById(R.id.dlg_lbl_crop_plot);
		View plotsize = findViewById(R.id.size_txt);
		View plottype = findViewById(R.id.type_txt);

		// checks if the image path is present.
		Bundle extras = getIntent().getExtras();

		if (extras != null
				&& extras.containsKey(ViewPictureActivity.IMAGE_PATH)) {
			mPlotImage = (String) extras.get(ViewPictureActivity.IMAGE_PATH);
			((ImageView) plotimage).setImageBitmap(Global.rotated);
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
				List<Resource> data = mDataProvider.getSoilTypes();
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
						R.raw.dateinfo, 0.0, 30.0, 2.0, 0.1, 1, R.id.size_txt,
						R.id.size_tr, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo, R.raw.dateinfo);
			}
		});
		
		plottype.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				List<Resource> data = mDataProvider.getResources(RealFarmDatabase.RESOURCE_TYPE_PLOT_TYPE);
				displayDialog(v, data, TYPE, "Select the plot type",
						R.raw.problems, R.id.type_txt,
						R.id.type_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		// shows the help icon based on the view.
		showHelpIcon(v);

		if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help);
		} else if (v.getId() == R.id.dlg_plot_img_test) {
			playAudio(R.raw.plotimage);
		} else if (v.getId() == R.id.dlg_lbl_soil_plot) {
			playAudio(R.raw.soiltype);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.yieldinfo);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.yieldinfo);
		} else if (v.getId() == R.id.plot_tr) {
			playAudio(R.raw.plotimage);
		} else if (v.getId() == R.id.soiltype_tr) {
			playAudio(R.raw.soiltype);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.maincrop);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.maincrop);
		} else if (v.getId() == R.id.type_tr) {
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
		mType = (Integer) mResultsMap.get(TYPE);

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

		if (mSize > 0) {
			highlightField(R.id.size_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.size_tr, true);
		}
		
		if (mType != -1) {
			highlightField(R.id.type_tr, false);
		} else {
			isValid = false;
			highlightField(R.id.type_tr, true);
		}

		// if form is valid the plot is added to the database.
		if (isValid) {
			addPlotToDatabase();
			return true;
		}
		return false;
	}
}
