package com.commonsensenet.realfarm;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

public class My_settings_plot_details extends HelpEnabledActivity {
	// MediaPlayer mp = null;
	ContentValues User_Details = new ContentValues();
	ContentValues users = new ContentValues();
	public int Click_Count = 0;
	public Button btn_write, btn_read;
	public EditText et_write, et_read, et_fileName1, et_fileName2,
			et_writtenText;
	String FirstName, LastName, MobileNumber;
	SQLiteDatabase db;
	long dbreturn;
	public RealFarmProvider mDataProvider;
	int tmp;
	String PlotImage = "0";
	String SoilType = "0", MainCrop = "0";

	final Context context = this;
	int Setting_info = 1, Home = 2;

	public void onBackPressed() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		Intent adminintent123 = new Intent(My_settings_plot_details.this,
				Homescreen.class);
		startActivity(adminintent123);
		My_settings_plot_details.this.finish();

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_settings_plot_details);
		mDataProvider = RealFarmProvider.getInstance(context); // Working

		ImageButton home1;
		ImageButton help1;
		home1 = (ImageButton) findViewById(R.id.aggr_img_home1);
		help1 = (ImageButton) findViewById(R.id.aggr_img_help1);
		help1.setOnLongClickListener(this);

		home1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(
						My_settings_plot_details.this, Homescreen.class);
				startActivity(adminintent123);
				My_settings_plot_details.this.finish();

			}
		});

		Button plotimage = (Button) findViewById(R.id.home_btn_list_plot);
		Button plotcrop = (Button) findViewById(R.id.home_btn_crop_plot);
		Button plotsoil = (Button) findViewById(R.id.home_btn_soil_plot);
		Button plotok = (Button) findViewById(R.id.home_btn_plot_ok);

		// PlotImage =(EditText) findViewById(R.id.plotimage);
		// SoilType = (EditText)findViewById(R.id.soiltype);
		// MainCrop = (EditText)findViewById(R.id.maincrop);

		plotimage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in plot image dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.plot_img_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Select the defualt image of the plot");
				dlg.show();

				final Button unit1;
				final Button unit2;
				final Button unit3;

				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_img_plot);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_plot_img);
				unit1 = (Button) dlg.findViewById(R.id.plot_img_1);
				unit2 = (Button) dlg.findViewById(R.id.plot_img_2);
				unit3 = (Button) dlg.findViewById(R.id.plot_img_3);

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Plot A");
						PlotImage = "Plot A";
						TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Plot B");
						PlotImage = "Plot B";
						TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Plot C");
						PlotImage = "Plot C";
						TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

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

				final Button unit1;
				final Button unit2;
				final Button unit3;

				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_img_soil_plot);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_soil_plot);
				unit1 = (Button) dlg.findViewById(R.id.plot_soil_1);
				unit2 = (Button) dlg.findViewById(R.id.plot_soil_2);
				unit3 = (Button) dlg.findViewById(R.id.plot_soil_3);

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Loamy");
						SoilType = "Loamy";
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
						SoilType = "Sandy";
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
						SoilType = "Clay";
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
				dlg.setContentView(R.layout.plot_crop_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Main Crop");

				dlg.show();

				final Button crop1;
				final Button crop2;
				final Button crop3;
				final Button crop4;
				final Button crop5;
				final Button crop6;
				// final Button variety7;
				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_img_crop_plot);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_crop_plot);
				crop1 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_1);
				crop2 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_2);
				crop3 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_3);
				crop4 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_4);
				crop5 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_5);
				crop6 = (Button) dlg.findViewById(R.id.home_btn_plot_crop_6);

				crop1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						MainCrop = "Bajra";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

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
						MainCrop = "Castor";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				crop3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Cowpea");
						MainCrop = "Cowpea";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

				crop4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_greengram_tiled);
						var_text.setText("Greengram");
						MainCrop = "Greengram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});
				crop5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_groundnut_tiled);
						var_text.setText("Groundnut");
						MainCrop = "Groundnut";
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
						MainCrop = "Horsegram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.maincrop_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						dlg.cancel();
					}
				});

			}
		});

		plotok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				int flag1, flag2, flag3;
				if (PlotImage.toString().equalsIgnoreCase("0")) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.plot_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (SoilType.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.soiltype_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (MainCrop.toString().equalsIgnoreCase("0")) {

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

					PutPlotToDatabase();
					mDataProvider.getAllPlotList();

				} else
					initmissingval();

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

	public void PutPlotToDatabase() {
		// System.out.println("User data is put to database");

		// Global.plotId++;
		// Global.userId++;

		String plotImage12 = PlotImage;
		String SoilType12 = SoilType;
		String MainCrop12 = MainCrop;

		mDataProvider.setPlotNew(plotImage12, SoilType12, MainCrop12);

		Toast.makeText(getBaseContext(),
				"Plot Details is put to Database" + plotImage12 + SoilType12,
				Toast.LENGTH_SHORT).show();
	}

	// @Override
	protected void initmissingval() {
		// TODO Auto-generated method stub
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		mp = MediaPlayer.create(this, R.raw.missinginfo);
		mp.start();
	}

	protected void stopaudio() {
		// TODO Auto-generated method stub
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}

	}

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_help1) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();

		}

		return true;
	}

}
