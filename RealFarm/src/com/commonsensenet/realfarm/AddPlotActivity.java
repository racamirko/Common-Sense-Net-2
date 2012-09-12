package com.commonsensenet.realfarm;

import java.util.Date;
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

	private int defaultSoil = -1;
	private int defaultCrop = -1;
	private int defaultType = -1;
	private String defaultSize = "0.0";

	private List<Resource> soilList;
	private List<Resource> cropList;
	private List<Resource> typeList;

	/**
	 * Adds the current plot to the database.
	 */
	private void addPlotToDatabase() {
		Global.plotId = mDataProvider.addPlot(Global.userId, mMainCrop,
				mSoilType, mPlotImage, mSize, Global.IsAdmin, mType);

		if (!Global.IS_INITIALIZED) { // add advice related to plot

			long i = mDataProvider.addAdvice(R.raw.problems, 1, 3, 1);
			long j = mDataProvider.addAdvice(R.raw.problems, 2, 4, 1);
			long k = mDataProvider.addAdvice(R.raw.problems, 3, 5, 1);

			System.out.println(i + " " + j);

			mDataProvider.addAdvicePiece(i, R.raw.problems, 1, 54,
					"Bla bla bla bla bla bla bla bla", 5);
			mDataProvider.addAdvicePiece(i, R.raw.problems, 2, 56,
					"gbsfassdfadfsdF", 5);
			mDataProvider.addAdvicePiece(j, R.raw.problems, 1, 55,
					"asdfasfdsdafasda", 5);
			mDataProvider.addAdvicePiece(k, R.raw.problems, 1, 57,
					"asdfasfdsdafasda", 5);
			mDataProvider.addRecommendation(Global.plotId, i, Global.userId, 5,
					new Date().getTime(), 130, 60);
			mDataProvider.addRecommendation(Global.plotId, j, Global.userId, 5,
					(new Date().getTime()) + 500000000, 90, 10);
			mDataProvider.addRecommendation(Global.plotId, k, Global.userId, 5,
					(new Date().getTime()) + 500000000, 80, 2);
		}

		// logs the event
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(), "add plot to database");

		// shows the name of the added plot.
		/*
		 * Toast.makeText( getBaseContext(), "Plot Details is put to Database "
		 * + mPlotImage + " " + mSoilType, Toast.LENGTH_SHORT).show();
		 */
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.act_add_plot);

		soilList = mDataProvider.getSoilTypes();
		cropList = mDataProvider.getVarieties();
		typeList = mDataProvider
				.getResources(RealFarmDatabase.RESOURCE_TYPE_PLOT_TYPE);

		// adds the name of the fields to validate.
		mResultsMap.put(SOIL_TYPE, defaultSoil);
		mResultsMap.put(MAIN_CROP, defaultCrop);
		mResultsMap.put(SIZE, defaultSize);
		mResultsMap.put(TYPE, defaultType);

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
		plottype.setOnLongClickListener(this);

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

				displayDialog(v, soilList, SOIL_TYPE, "Select the soil type",
						R.raw.selectsoiltype, R.id.dlg_lbl_soil_plot,
						R.id.soiltype_tr, 0);
			}
		});

		plotcrop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in crop plot dialog", "in dialog");

				stopAudio();

				displayDialog(v, cropList, MAIN_CROP, "Select the variety",
						R.raw.select_the_variety, R.id.dlg_lbl_crop_plot,
						R.id.maincrop_tr, 0);
			}
		});

		plotsize.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialogNP("Enter the plot size in acres", SIZE,
						R.raw.enter_plot_size_acres, 0.0, 30.0, 2.0, 0.1, 1,
						R.id.size_txt, R.id.size_tr, R.raw.ok, R.raw.cancel,
						R.raw.plotsize_ok, R.raw.plotsize_cancel);
			}
		});

		plottype.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();

				displayDialog(v, typeList, TYPE, "Select the plot type",
						R.raw.select_plot_type, R.id.type_txt, R.id.type_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {
		// shows the help icon based on the view.
		showHelpIcon(v);

		if (v.getId() == R.id.size_txt) {

			if (mResultsMap.get(SIZE).equals(defaultSize))
				playAudio(R.raw.enter_plot_size_acres, true);

			else {
				play_float(Float.parseFloat(mResultsMap.get(SIZE).toString()));
				playSound();
			}
		} else if (v.getId() == R.id.dlg_lbl_crop_plot) {

			if ((Integer) mResultsMap.get(MAIN_CROP) == defaultCrop)
				playAudio(R.raw.select_maincrop, true);
			else
				playAudio(cropList.get(((Integer) mResultsMap.get(MAIN_CROP)))
						.getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_soil_plot) {

			if ((Integer) mResultsMap.get(SOIL_TYPE) == defaultSoil)
				playAudio(R.raw.selectsoiltype, true);
			else
				playAudio(soilList.get(((Integer) mResultsMap.get(SOIL_TYPE)))
						.getAudio(), true);
		} else if (v.getId() == R.id.type_txt) {

			if ((Integer) mResultsMap.get(TYPE) == defaultType)
				playAudio(R.raw.select_plot_type, true);
			else
				playAudio(typeList.get(((Integer) mResultsMap.get(TYPE)))
						.getAudio(), true);
		}

		else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help_parcel_enterdetails, true);
		} else if (v.getId() == R.id.dlg_plot_img_test) {
			playAudio(R.raw.take_plot_pic_touch, true);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.maincrop, true);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.size, true);
		} else if (v.getId() == R.id.plot_tr) {
			playAudio(R.raw.plotimage, true);
		} else if (v.getId() == R.id.soiltype_tr) {
			playAudio(R.raw.soiltype, true);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.maincrop, true);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.size, true);
		} else if (v.getId() == R.id.type_tr) {
			playAudio(R.raw.plot_type, true);
		} else if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.newparcel_save, true);
		} else if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.parcel_not_save_short, true);
		} else {
			return super.onLongClick(v);
		}

		return true;
	}

	@Override
	protected Boolean validateForm() {

		// values obtained that need to be validated.
		mSize = Double.valueOf(mResultsMap.get(SIZE).toString());

		boolean isValid = true;

		if (!mPlotImage.toString().equalsIgnoreCase("0")) {
			highlightField(R.id.plot_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, "Plot image");
			isValid = false;
			highlightField(R.id.plot_tr, true);
		}

		if ((Integer) mResultsMap.get(SOIL_TYPE) != defaultSoil) {
			highlightField(R.id.soiltype_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, SOIL_TYPE);
			isValid = false;
			highlightField(R.id.soiltype_tr, true);
		}

		if ((Integer) mResultsMap.get(MAIN_CROP) != defaultCrop) {
			highlightField(R.id.maincrop_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, MAIN_CROP);
			isValid = false;
			highlightField(R.id.maincrop_tr, true);
		}

		if (mSize > Double.parseDouble(defaultSize)) {
			highlightField(R.id.size_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, SIZE);
			isValid = false;
			highlightField(R.id.size_tr, true);
		}

		if ((Integer) mResultsMap.get(TYPE) != defaultType) {
			highlightField(R.id.type_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR,
					Global.userId, TYPE);
			isValid = false;
			highlightField(R.id.type_tr, true);
		}

		// if form is valid the plot is added to the database.
		if (isValid) {

			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "data entered");

			mSoilType = soilList.get((Integer) mResultsMap.get(SOIL_TYPE))
					.getId();
			mMainCrop = cropList.get((Integer) mResultsMap.get(MAIN_CROP))
					.getId();
			mType = typeList.get((Integer) mResultsMap.get(TYPE)).getId();

			addPlotToDatabase();
			return true;
		}
		return false;
	}
}
