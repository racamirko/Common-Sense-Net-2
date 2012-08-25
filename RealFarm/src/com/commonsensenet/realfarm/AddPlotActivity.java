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

		// inserts the new action
		Global.plotId = mDataProvider.addPlot(Global.userId, mMainCrop, mSoilType, mPlotImage, mSize, mType);


		long i = mDataProvider.addAdvice(R.raw.problems, 1, 3, 1);
		long j = mDataProvider.addAdvice(R.raw.problems, 2, 4, 1);
		long k = mDataProvider.addAdvice(R.raw.problems, 3, 5, 1);
		
		System.out.println(i+" "+j);

		mDataProvider.addAdvicePiece(i, R.raw.problems, 1, 54, "Bla bla bla bla bla bla bla bla", 5);
		mDataProvider.addAdvicePiece(i, R.raw.problems, 2, 56, "gbsfassdfadfsdF", 5);
		mDataProvider.addAdvicePiece(j, R.raw.problems, 1, 55, "asdfasfdsdafasda", 5);
		mDataProvider.addAdvicePiece(k, R.raw.problems, 1, 57, "asdfasfdsdafasda", 5);
		mDataProvider.addRecommandation(Global.plotId, i, Global.userId, 5, new Date().getTime(), 130,60);
		mDataProvider.addRecommandation(Global.plotId, j, Global.userId, 5, (new Date().getTime())+500000000, 90,10);
		mDataProvider.addRecommandation(Global.plotId, k, Global.userId, 5, (new Date().getTime())+500000000, 80,2);

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
		
		soilList = mDataProvider.getSoilTypes();
		cropList = mDataProvider.getVarieties();
		typeList = mDataProvider.getResources(RealFarmDatabase.RESOURCE_TYPE_PLOT_TYPE);

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
				// TODO AUDIO: "Select the soil type" This is the audio that is heard when the selector dialog opens
				displayDialog(v, soilList, SOIL_TYPE, "Select the soil type",
						R.raw.problems, R.id.dlg_lbl_soil_plot,
						R.id.soiltype_tr, 0);
			}
		});

		plotcrop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in crop plot dialog", "in dialog");

				stopAudio();
				// TODO AUDIO: "Select the variety" This is the audio that is heard when the selector dialog opens
				displayDialog(v, cropList, MAIN_CROP, "Select the variety",
						R.raw.problems, R.id.dlg_lbl_crop_plot,
						R.id.maincrop_tr, 0);
			}
		});

		plotsize.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				// TODO AUDIO: "Enter the plot size in acres" This is the audio that is heard when the selector dialog opens
				// TODO AUDIO:  Text on tap on ok button in Number picker
				// TODO AUDIO:  Text on tap on cancel button in Number picker
				// TODO AUDIO:  Info on long tap on ok button in Number picker
				// TODO AUDIO:  Info on long tap on cancel button in Number picker
				displayDialogNP("Enter the plot size in acres", SIZE,
						R.raw.dateinfo, 0.0, 30.0, 2.0, 0.1, 1, R.id.size_txt,
						R.id.size_tr, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo, R.raw.dateinfo);
			}
		});
		
		plottype.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				// TODO AUDIO: "Select the plot type" This is the audio that is heard when the selector dialog opens
				displayDialog(v, typeList, TYPE, "Select the plot type",
						R.raw.problems, R.id.type_txt,
						R.id.type_tr, 0);
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {
		// shows the help icon based on the view.
		showHelpIcon(v);
				  
		if (v.getId() == R.id.size_txt) {
			// TODO AUDIO: "Select the day" default if nothing is in the field
			if(mResultsMap.get(SIZE).equals(defaultSize)) playAudio(R.raw.maincrop, true); 
			// TODO AUDIO: Say the number Double.parseDouble(mResultsMap.get(SIZE).toString());
			else playAudio(R.raw.problems, true); 
		} else if (v.getId() == R.id.dlg_lbl_crop_plot) {
			// TODO AUDIO: "Select the main crop" default if nothing is in the field
			if((Integer) mResultsMap.get(MAIN_CROP) == defaultCrop) playAudio(R.raw.maincrop, true); 
			else playAudio(cropList.get(((Integer)mResultsMap.get(MAIN_CROP))).getAudio(), true);
		} else if (v.getId() == R.id.dlg_lbl_soil_plot) {
			// TODO AUDIO: "Select the soil type" default if nothing is in the field
			if((Integer) mResultsMap.get(SOIL_TYPE) == defaultSoil) playAudio(R.raw.soiltype, true); 
			else playAudio(soilList.get(((Integer)mResultsMap.get(SOIL_TYPE))).getAudio(), true);
		} else if (v.getId() == R.id.type_txt) {
			// TODO AUDIO: "Select the main crop" default if nothing is in the field
			if((Integer) mResultsMap.get(TYPE) == defaultType) playAudio(R.raw.maincrop, true); 
			else playAudio(typeList.get(((Integer)mResultsMap.get(TYPE))).getAudio(), true);
		} 
		
		// TODO AUDIO: Check the remaining audio
		else if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help, true);
		} else if (v.getId() == R.id.dlg_plot_img_test) {
			playAudio(R.raw.plotimage, true);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.yieldinfo, true);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.yieldinfo, true);
		} else if (v.getId() == R.id.plot_tr) {
			playAudio(R.raw.plotimage, true);
		} else if (v.getId() == R.id.soiltype_tr) {
			playAudio(R.raw.soiltype, true);
		} else if (v.getId() == R.id.maincrop_tr) {
			playAudio(R.raw.maincrop, true);
		} else if (v.getId() == R.id.size_tr) {
			playAudio(R.raw.maincrop, true);
		} else if (v.getId() == R.id.type_tr) {
			playAudio(R.raw.maincrop, true);
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
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, "Plot image");
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.plot_tr, true);
		}

		if ((Integer)mResultsMap.get(SOIL_TYPE) != defaultSoil) {
			highlightField(R.id.soiltype_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, SOIL_TYPE);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.soiltype_tr, true);
		}

		if ((Integer)mResultsMap.get(MAIN_CROP) != defaultCrop) {
			highlightField(R.id.maincrop_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, MAIN_CROP);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.maincrop_tr, true);
		}

		if (mSize > Double.parseDouble(defaultSize)) {
			highlightField(R.id.size_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, SIZE);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.size_tr, true);
		}
		
		if ((Integer)mResultsMap.get(TYPE) != defaultType) {
			highlightField(R.id.type_tr, false);
		} else {
			ApplicationTracker.getInstance().logEvent(EventType.ERROR, TYPE);
			ApplicationTracker.getInstance().flush();
			isValid = false;
			highlightField(R.id.type_tr, true);
		}

		// if form is valid the plot is added to the database.
		if (isValid) {
			
			ApplicationTracker.getInstance().logEvent(EventType.CLICK, "data entered");
			ApplicationTracker.getInstance().flush();
			
			mSoilType = soilList.get((Integer)mResultsMap.get(SOIL_TYPE)).getId();
			mMainCrop = cropList.get((Integer)mResultsMap.get(MAIN_CROP)).getId();
			mType = typeList.get((Integer)mResultsMap.get(TYPE)).getId();
			
			addPlotToDatabase();
			return true;
		}
		return false;
	}
}
