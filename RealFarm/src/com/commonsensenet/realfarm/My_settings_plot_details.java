package com.commonsensenet.realfarm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.ownCamera.OwnCameraActivity;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

public class My_settings_plot_details extends HelpEnabledActivityOld {

	private final Context mContext = this;
	public static final String LOG_TAG = "enter_size";
	public static final String LOG_TAG2 = "add_plot_to_database";

	private RealFarmProvider mDataProvider;
	private String mMainCrop = "0";
	private String mPlotImage = "0";
	private int mSeedTypeId = 0;
	private String mSoilType = "0";
	private String mSize = "0";
	private final My_settings_plot_details parentReference = this;
	private HashMap<String, String> resultsMap;

	private void addPlotToDatabase() {

		Global.plotId = mDataProvider.insertPlot(Global.userId, mSeedTypeId,
				mPlotImage, mSoilType, 0, 0, Integer.parseInt(mSize));

		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG2,"add plot to database");
		
		Toast.makeText(
				getBaseContext(),
				"Plot Details is put to Database " + mPlotImage + " "
						+ mSoilType, Toast.LENGTH_SHORT).show();
	}

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
		
		resultsMap = new HashMap<String, String>(); 
		resultsMap.put("mSoilType", "0");
		resultsMap.put("mMainCrop", "0");

		ImageButton home1 = (ImageButton) findViewById(R.id.aggr_img_home1);
		ImageButton help1 = (ImageButton) findViewById(R.id.aggr_img_help1);
		help1.setOnLongClickListener(this);

		final TableRow plotImage; // 20-06-2012
		final TableRow soilType;
		final TableRow mainCrop;
		final TableRow size;

		plotImage = (TableRow) findViewById(R.id.plot_tr); // 20-06-2012
		soilType = (TableRow) findViewById(R.id.soiltype_tr);
		mainCrop = (TableRow) findViewById(R.id.maincrop_tr);
		size = (TableRow) findViewById(R.id.size_tr);


		plotImage.setOnLongClickListener(this); // 20-06-2012
		soilType.setOnLongClickListener(this);
		mainCrop.setOnLongClickListener(this);
		size.setOnLongClickListener(this);

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
		Button plotsize = (Button) findViewById(R.id.home_btn_size_plot);
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
		
		plotsize.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the size");
				dlg.show();
				
				// playAudio(R.raw.noofbags);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG, "size");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						TextView sizeTw = (TextView) findViewById(R.id.size_txt);

						NumberPicker mynp1 = (NumberPicker) dlg.findViewById(R.id.numberpick);
						mynp1.setIncrementValue(1); // TODO 0.1
						mSize = String.valueOf(mynp1.getValue());
						
						if (mynp1.getValue() != 0) {
							sizeTw.setText(mSize);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "no_of_bags",
								"cancel");
					}
				});

			}
		});

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
				stopAudio();
				final ArrayList<DialogData> m_entries = DialogArrayLists.getSoilTypeArray(v);
				displayDialog(v, m_entries, "mSoilType", "Select the soil type", R.raw.problems, R.id.dlg_lbl_soil_plot, R.id.soiltype_tr);
				
				/*final Dialog dlg = new Dialog(v.getContext());
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
				});*/

			}
		});

		// add the event listeners
		plotcrop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("in crop plot dialog", "in dialog");
				
				stopAudio();
				ArrayList<DialogData> m_entries = mDataProvider.getVarieties();
				displayDialog(v, m_entries, "mMainCrop", "Select the variety", R.raw.problems, R.id.dlg_lbl_crop_plot, R.id.maincrop_tr);
				
				/*final Dialog dlg = new Dialog(v.getContext());
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
				});*/

			}
		});

		plotok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				mSoilType = resultsMap.get("mSoilType");
				mMainCrop = resultsMap.get("mMainCrop");
				
				int flag1, flag2, flag3, flag4;
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
				
				if (mSize.toString().equalsIgnoreCase("0")) {

					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.size_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
				} else {

					flag4 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.size_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {

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

		if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_list_plot) {
			playAudio(R.raw.plotimage);
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

		if (v.getId() == R.id.plot_tr) {

			playAudio(R.raw.plotimage);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.soiltype_tr) {

			playAudio(R.raw.soiltype);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.maincrop_tr) {

			playAudio(R.raw.maincrop);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.size_tr) {

			// playAudio(R.raw.maincrop);
			ShowHelpIcon(v);
		}

		return true;
	}
	
	private void displayDialog(View v, final ArrayList<DialogData> m_entries, final String mapEntry, final String title, int entryAudio, final int varText, final int trFeedback){ 
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(), R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView)dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener(){ // TODO: adapt the audio in the db
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Does whatever is specific to the application
				Log.d("var "+position+" picked ", "in dialog");
				TextView var_text = (TextView) findViewById(varText);
				DialogData choice = m_entries.get(position);
				var_text.setText(choice.getName());
				resultsMap.put(mapEntry, choice.getValue());  
				TableRow tr_feedback = (TableRow) findViewById(trFeedback);
				tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(
						EventType.CLICK, LOG_TAG, title,
						choice.getValue());
				
				Toast.makeText(parentReference, resultsMap.get(mapEntry), Toast.LENGTH_SHORT).show();
						
				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + choice.getAudio(), null, null);
				playAudio(iden);
			}});

		mList.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + m_entries.get(position).getAudio(), null, null);
				playAudioalways(iden);
				return true;
			}});
	}
}
