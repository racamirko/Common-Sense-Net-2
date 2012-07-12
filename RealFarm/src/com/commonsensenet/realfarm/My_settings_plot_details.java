package com.commonsensenet.realfarm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.ownCamera.OwnCameraActivity;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class My_settings_plot_details extends HelpEnabledActivity {

	private final Context mContext = this;

	private RealFarmProvider mDataProvider;
	private String mMainCrop = "0";
	private String mPlotImage = "0";
	private int mSeedTypeId = 0;
	private String mSoilType = "0";
	private final My_settings_plot_details parentReference = this;

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent123 = new Intent(My_settings_plot_details.this,
				Homescreen.class);
		startActivity(adminintent123);
		My_settings_plot_details.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		// sets the layout
		// setContentView(R.layout.my_settings_plot_details);
		// gets the database provided
		super.onCreate(savedInstanceState, R.layout.my_settings_plot_details); // 25-06-2012
		setHelpIcon(findViewById(R.id.helpIndicator));

		mDataProvider = RealFarmProvider.getInstance(mContext);

		ImageButton home1 = (ImageButton) findViewById(R.id.aggr_img_home1);
		ImageButton help1 = (ImageButton) findViewById(R.id.aggr_img_help1);
		help1.setOnLongClickListener(this);

		final Button plotImage; // 20-06-2012
		final Button soilType;
		final Button mainCrop;

		plotImage = (Button) findViewById(R.id.image_plot_txt_btn); // 20-06-2012
		soilType = (Button) findViewById(R.id.soiltype_plot_txt_btn);
		mainCrop = (Button) findViewById(R.id.maincrop_plot_txt_btn);

		plotImage.setOnLongClickListener(this); // 20-06-2012
		soilType.setOnLongClickListener(this);
		mainCrop.setOnLongClickListener(this);

		home1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(
						My_settings_plot_details.this, Homescreen.class);
				startActivity(adminintent123);
				My_settings_plot_details.this.finish();

			}
		});

		if (Global.cameraFlag == true) {
			Global.cameraFlag = false;
			final ImageView img_1;
			img_1 = (ImageView) findViewById(R.id.dlg_plot_img_test);
			mPlotImage = Global.plotImagePath;

			img_1.setImageBitmap(Global.rotated);

		}

		Button plotimage = (Button) findViewById(R.id.home_btn_list_plot);
		Button plotcrop = (Button) findViewById(R.id.home_btn_crop_plot);
		Button plotsoil = (Button) findViewById(R.id.home_btn_soil_plot);
		Button plotok = (Button) findViewById(R.id.button_ok);
		Button plotcancel = (Button) findViewById(R.id.button_cancel); // 25-06-2012

		((Button) findViewById(R.id.home_btn_list_plot))
				.setOnLongClickListener(parentReference); // Audio integration
		((Button) findViewById(R.id.home_btn_crop_plot))
				.setOnLongClickListener(parentReference);
		((Button) findViewById(R.id.home_btn_soil_plot))
				.setOnLongClickListener(parentReference);
		((Button) findViewById(R.id.button_ok))
				.setOnLongClickListener(parentReference);
		((Button) findViewById(R.id.button_cancel)) // 25-06-2012
				.setOnLongClickListener(parentReference);

		// PlotImage =(EditText) findViewById(R.id.plotimage);
		// SoilType = (EditText)findViewById(R.id.soiltype);
		// MainCrop = (EditText)findViewById(R.id.maincrop);

		plotimage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(My_settings_plot_details.this,
						OwnCameraActivity.class));

				// mPlotImage = "Image";

				My_settings_plot_details.this.finish();

			}
		});

		plotsoil.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in plot image dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.plot_soil_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Select the defualt image of the plot");
				dlg.show();

				final View unit1;
				final View unit2;
				final View unit3;

				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_img_soil_plot);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_soil_plot);
				unit1 = dlg.findViewById(R.id.button_soil_1);
				unit2 = dlg.findViewById(R.id.button_soil_2);
				unit3 = dlg.findViewById(R.id.button_soil_3);

				// adds the long click listener to enable the help feature.
				unit1.setOnLongClickListener(parentReference);
				unit2.setOnLongClickListener(parentReference);
				unit3.setOnLongClickListener(parentReference);

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");

						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Loamy");
						mSoilType = "Loamy";
						TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Sandy");
						mSoilType = "Sandy";
						TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Clay");
						mSoilType = "Clay";
						TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

			}
		});

		// add the event listeners
		plotcrop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in crop plot dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.dialog_variety);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Main Crop");

				dlg.show();

				final View crop1;
				final View crop2;
				final View crop3;
				final View crop4;
				final View crop5;
				final View crop6;

				final ImageView img_1 = (ImageView) findViewById(R.id.dlg_img_crop_plot);
				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_crop_plot);

				// gets all the crop buttons
				crop1 = dlg.findViewById(R.id.button_variety_1);
				crop2 = dlg.findViewById(R.id.button_variety_2);
				crop3 = dlg.findViewById(R.id.button_variety_3);
				crop4 = dlg.findViewById(R.id.button_variety_4);
				crop5 = dlg.findViewById(R.id.button_variety_5);
				crop6 = dlg.findViewById(R.id.button_variety_6);

				// adds the long click listener to enable the help functionality
				crop1.setOnLongClickListener(parentReference);
				crop2.setOnLongClickListener(parentReference);
				crop3.setOnLongClickListener(parentReference);
				crop4.setOnLongClickListener(parentReference);
				crop5.setOnLongClickListener(parentReference);
				crop6.setOnLongClickListener(parentReference);

				crop1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						mMainCrop = "Bajra";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);
						mSeedTypeId = 5; // added with audio integration
						tr_feedback.setBackgroundResource(R.drawable.def_img);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				crop2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Castor");
						mMainCrop = "Castor";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);
						mSeedTypeId = 6; // added with audio integration
						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				crop3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Cowpea");
						mMainCrop = "Cowpea";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);
						mSeedTypeId = 7; // added with audio integration
						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				crop4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_greengram_tiled);
						var_text.setText("Greengram");
						mMainCrop = "Greengram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);
						mSeedTypeId = 8; // added with audio integration
						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});
				crop5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_groundnut_tiled);
						var_text.setText("Groundnut");
						mMainCrop = "Groundnut";
						mSeedTypeId = 3; // added with audio integration
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});
				crop6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Horsegram");
						mMainCrop = "Horsegram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);
						mSeedTypeId = 9; // added with audio integration
						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

			}
		});

		plotok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				int flag1, flag2, flag3;
				if (mPlotImage.toString().equalsIgnoreCase("0")) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				// TODO: overrides image requirement
				flag1 = 0;

				if (mSoilType.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (mMainCrop.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {

					Intent adminintent = new Intent(
							My_settings_plot_details.this, Homescreen.class);

					startActivity(adminintent);
					My_settings_plot_details.this.finish();

					addPlotToDatabase();
					mDataProvider.getPlots();

				} else
					initmissingval();

			}
		});

		plotcancel.setOnClickListener(new View.OnClickListener() { // 25-06-2012
					public void onClick(View v) {

						SoundQueue.getInstance().stop();

						Intent adminintent123 = new Intent(
								My_settings_plot_details.this, Homescreen.class);
						startActivity(adminintent123);
						My_settings_plot_details.this.finish();

					}
				});
		/*
		 * PlotImage.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { System.out.println("in PlotImage"); //
		 * gridview.setAdapter(new ImageAdapter(this)); //
		 * gridview.setAdapter(new ImageAdapter(this)); // Intent adminintent =
		 * new Intent(My_settings_plot_details.this,
		 * VIAggrRecommendation.class); // startActivity(adminintent); // Dialog
		 * dlg = new Dialog(v.getContext());
		 * 
		 * 
		 * Dialog dlg = new Dialog(v.getContext()); //Tested OK
		 * dlg.setContentView(R.layout.plot_image); dlg.setCancelable(true); //
		 * parts TextView dlgDetals = (TextView)
		 * dlg.findViewById(R.id.dlg_lbl_details); ImageView Image1 =
		 * (ImageView) dlg.findViewById(R.id.ImageView01); ImageView Image2 =
		 * (ImageView) dlg.findViewById(R.id.ImageView02); ImageView Image3 =
		 * (ImageView) dlg.findViewById(R.id.ImageView25); ImageView Image4 =
		 * (ImageView) dlg.findViewById(R.id.ImageView27);
		 * 
		 * // ImageView imgAction = (ImageView)
		 * dlg.findViewById(R.id.dlg_img_action); // ImageView imgSeed =
		 * (ImageView) dlg.findViewById(R.id.dlg_img_seed); // dlg.setTitle(
		 * "test"); dlgDetals.setText(" set text" );
		 * 
		 * dlg.show(); }
		 * 
		 * });
		 */

		/*
		 * SoilType.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { System.out.println("in SoilType");
		 * 
		 * 
		 * Dialog dlg = new Dialog(v.getContext()); //Tested OK
		 * dlg.setContentView(R.layout.soil_type); dlg.setCancelable(true); //
		 * parts TextView dlgDetals = (TextView)
		 * dlg.findViewById(R.id.dlg_lbl_details); ImageView Image1 =
		 * (ImageView) dlg.findViewById(R.id.ImageView01); ImageView Image2 =
		 * (ImageView) dlg.findViewById(R.id.ImageView02); ImageView Image3 =
		 * (ImageView) dlg.findViewById(R.id.ImageView25); ImageView Image4 =
		 * (ImageView) dlg.findViewById(R.id.ImageView27);
		 * 
		 * // ImageView imgAction = (ImageView)
		 * dlg.findViewById(R.id.dlg_img_action); // ImageView imgSeed =
		 * (ImageView) dlg.findViewById(R.id.dlg_img_seed); // dlg.setTitle(
		 * "Soil Types");
		 * 
		 * //imgAction.setImageResource(
		 * mDataProvider.getActionNameById(1).getRes() );
		 * //imgSeed.setImageResource( mDataProvider.getSeedById(1).getRes());
		 * //Log.d(logTag,
		 * "Seed res id: "+String.valueOf(mDataProvider.getSeedById
		 * (1).getRes())); dlg.show(); }
		 * 
		 * });
		 */

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_help1) { // showhelp added 25-06-2012
			playAudio(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_list_plot) { // added
			playAudio(R.raw.plotimage);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.plot_img_1) { // added
			playAudio(R.raw.audio1);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.plot_img_2) { // added
			playAudio(R.raw.audio2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.plot_img_3) {
			playAudio(R.raw.audio3);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_soil_plot) {
			playAudio(R.raw.soiltype);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_soil_1) {
			playAudio(R.raw.loamy);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_soil_2) {
			playAudio(R.raw.sandy);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_soil_3) {
			playAudio(R.raw.clay);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_crop_plot) {
			playAudio(R.raw.yieldinfo);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_1) {
			playAudio(R.raw.bajra);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_2) {
			playAudio(R.raw.castor);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_3) {
			playAudio(R.raw.cowpea);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_4) {
			playAudio(R.raw.greengram);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_5) {
			playAudio(R.raw.groundnuts);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_variety_6) {
			playAudio(R.raw.horsegram);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.image_plot_txt_btn) {

			playAudio(R.raw.plotimage);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.soiltype_plot_txt_btn) {

			playAudio(R.raw.soiltype);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.maincrop_plot_txt_btn) {

			playAudio(R.raw.maincrop);
			ShowHelpIcon(v);
		}

		return true;
	}

	private void addPlotToDatabase() {

		Global.plotId = mDataProvider.insertPlot(Global.userId, mSeedTypeId,
				mPlotImage, mSoilType, 0, 0);

		Toast.makeText(
				getBaseContext(),
				"Plot Details is put to Database " + mPlotImage + " "
						+ mSoilType, Toast.LENGTH_SHORT).show();
	}
}
