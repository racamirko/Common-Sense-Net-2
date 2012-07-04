package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_sowing extends HelpEnabledActivity {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_sowing parentReference = this; // audio integration
	private int sow_no, day_sow_int;
	private String sow_no_sel, day_sow_str, months_sow="0";
	private String treatment_sow = "0", days_sel_sow = "0", units_sow = "0",
			seed_sow = "0";

	protected void cancelaudio() {
	
		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		if (Global.writeToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider
					.File_Log_Create("UIlog.txt",
							"***** user has clicked soft key BACK in Spraying page*********** \r\n");

		}

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
	//	super.onCreate(savedInstanceState);
	//	setContentView(R.layout.sowing_dialog);
		
		super.onCreate(savedInstanceState, R.layout.sowing_dialog);           //Needed to add help icon
		setHelpIcon(findViewById(R.id.helpIndicator));    
		
		System.out.println("plant done");
		final TextView day_sow = (TextView) findViewById(R.id.dlg_lbl_day_sow);
		// final TextView month_sow = (TextView) findViewById(R.id.dlg_lbl_month_sow);
	
		playAudio(R.raw.thankyouclickingactionsowing);

		if (Global.writeToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt",
					"***** In Action Sowing*********** \r\n");

		}

		// final ImageView bg_var_sow = (ImageView)
		// findViewById(R.id.img_bg_var_sow);
		final ImageView bg_units_no_sow = (ImageView) findViewById(R.id.img_bg_units_no_sow);
		final ImageView bg_units_sow = (ImageView) findViewById(R.id.img_bg_units_sow);
		final ImageView bg_treatment_sow = (ImageView) findViewById(R.id.img_bg_treatment_sow);
		final ImageView bg_day_sow = (ImageView) findViewById(R.id.img_bg_day_sow);
		final ImageView bg_month_sow = (ImageView) findViewById(R.id.img_bg_month_sow);
	//	bg_day_sow.setImageResource(R.drawable.empty_not);

		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		final Button item5;
		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_var_sow);
		item2 = (Button) findViewById(R.id.home_btn_units_sow);
		item3 = (Button) findViewById(R.id.home_btn_day_sow);
		item4 = (Button) findViewById(R.id.home_btn_treat_sow);
		item5 = (Button) findViewById(R.id.home_btn_units_no_sow);
		final Button item6 = (Button) findViewById(R.id.home_btn_month_sow);
		
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this); // Integration
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);                  //25-06-2012
		help.setOnLongClickListener(this);
		
		final Button variety;                                                                  //20-06-2012
		final Button Amount;
		final Button Date;
		final Button Treatment;
		final Button Intercrop;
		
		variety = (Button) findViewById(R.id.variety_sow_txt_btn);                            //20-06-2012
		Amount = (Button) findViewById(R.id.amount_sow_txt_btn);
		Date = (Button) findViewById(R.id.date_sow_txt_btn);
		Treatment = (Button) findViewById(R.id.treat_sow_txt_btn);
		Intercrop = (Button) findViewById(R.id.intercrop_sow_txt_btn);
		
		variety.setOnLongClickListener(this);                                                 //20-06-2012
		Amount.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);
		Treatment.setOnLongClickListener(this);
		Intercrop.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.variety_sowing_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of variety of seed sowed in  Sowing*********** \r\n");

				}
				final Button variety1;
				final Button variety2;
				final Button variety3;
				final Button variety4;
				final Button variety5;
				final Button variety6;
				// final Button variety7;
				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_var_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_var_text_sow);
				variety1 = (Button) dlg.findViewById(R.id.home_btn_var_sow_1);
				variety2 = (Button) dlg.findViewById(R.id.home_btn_var_sow_2);
				variety3 = (Button) dlg.findViewById(R.id.home_btn_var_sow_3);
				variety4 = (Button) dlg.findViewById(R.id.home_btn_var_sow_4);
				variety5 = (Button) dlg.findViewById(R.id.home_btn_var_sow_5);
				variety6 = (Button) dlg.findViewById(R.id.home_btn_var_sow_6);

				((Button) dlg.findViewById(R.id.home_btn_var_sow_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_btn_var_sow_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_3))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_4))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_5))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_6))
						.setOnLongClickListener(parentReference);

				variety1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						seed_sow = "Bajra";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				variety2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Castor");
						seed_sow = "Castor";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				variety3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Cowpea");
						seed_sow = "Cowpea";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}

						dlg.cancel();
					}
				});

				variety4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_greengram_tiled);
						var_text.setText("Greengram");
						seed_sow = "Greengram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});
				variety5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_groundnut_tiled);
						var_text.setText("Groundnut");
						seed_sow = "Groundnut";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});
				variety6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Horsegram");
						seed_sow = "Horsegram";
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + seed_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units sow dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.units_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the units");
				Log.d("in units sow dialog", "in dialog");
				dlg.show();

				final Button unit1;
				final Button unit2;
				final Button unit3;

				// final ImageView img_1 = (ImageView)
				// findViewById(R.id.dlg_unit_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_unit_sow);
				unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
				unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
				unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(parentReference); // Audio
																	// integration
				((Button) dlg.findViewById(R.id.home_btn_units_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_units_3))
						.setOnLongClickListener(parentReference);
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of units for Sowing*********** \r\n");

				}

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("10 Kgs");
						units_sow = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sow.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("20 Kgs");
						units_sow = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sow.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("50 Kgs");
						units_sow = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sow.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Date");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				
				playAudio(R.raw.dateinfo);                  //20-06-2012
			

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				
				((Button) dlg.findViewById(R.id.number_ok))                              //20-06-2012
				.setOnLongClickListener(parentReference);
		((Button) dlg.findViewById(R.id.number_cancel)).setOnLongClickListener(parentReference);
		
		
				no_ok.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

				
				NumberPicker mynpd = (NumberPicker) dlg.findViewById(R.id.numberpick);
				day_sow_int = mynpd.getValue();
				day_sow_str = String.valueOf(day_sow_int);
				day_sow.setText(day_sow_str);
				if (day_sow_int != 0) {

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
					bg_day_sow.setImageResource(R.drawable.empty_not);
					
				}

				dlg.cancel();
			}
		});
		no_cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dlg.cancel();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt", logtime
							+ " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user selected cancel on selction of bags for Sowing*********** \r\n");

				}
			}
		});
			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in treat sow dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.treat_sow_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Select weather you have treated the seeds");
				Log.d("in treat sow dialog", "in dialog");
				dlg.show();

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of treatment to seeds for Sowing*********** \r\n");

				}

				final Button treat1;
				final Button treat2;

				// final ImageView img_ = (ImageView)
				// findViewById(R.id.dlg_unit_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_treat_sow);
				treat1 = (Button) dlg.findViewById(R.id.home_treat_sow_1);
				treat2 = (Button) dlg.findViewById(R.id.home_treat_sow_2);

				((Button) dlg.findViewById(R.id.home_treat_sow_1))
						.setOnLongClickListener(parentReference); // Audio
																	// integration
				((Button) dlg.findViewById(R.id.home_treat_sow_2))
						.setOnLongClickListener(parentReference);

				treat1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Treated");
						treatment_sow = "treated";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + treatment_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				treat2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("May not Treat");
						treatment_sow = "may not treat";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + treatment_sow
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_sow);

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Number of bags");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				
				playAudio(R.raw.noofbags);                  //20-06-2012
				
	

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** in selection of bags for Sowing*********** \r\n");

				}

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				
				((Button) dlg.findViewById(R.id.number_ok))                              //20-06-2012
				.setOnLongClickListener(parentReference);
		((Button) dlg.findViewById(R.id.number_cancel)).setOnLongClickListener(parentReference);
		
				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						sow_no = mynp1.getValue();
						sow_no_sel = String.valueOf(sow_no);
						no_text.setText(sow_no_sel);
						if (sow_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_sow
									.setImageResource(R.drawable.empty_not);
							if (Global.writeToSD == true) {

								String logtime = getcurrenttime();
								mDataProvider.File_Log_Create("UIlog.txt",
										logtime + " -> ");

								mDataProvider
										.File_Log_Create(
												"UIlog.txt",
												"***** user selected"
														+ sow_no_sel
														+ " of bags for Sowing*********** \r\n");

							}
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider
									.File_Log_Create("UIlog.txt",
											"***** user selected cancel on selction of bags for Sowing*********** \r\n");

						}
					}
				});

			}
		});

		
		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.months_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the month ");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final Button month1 = (Button) dlg
						.findViewById(R.id.home_month_1);
				final Button month2 = (Button) dlg
						.findViewById(R.id.home_month_2);
				final Button month3 = (Button) dlg
						.findViewById(R.id.home_month_3);
				final Button month4 = (Button) dlg
						.findViewById(R.id.home_month_4);
				final Button month5 = (Button) dlg
						.findViewById(R.id.home_month_5);
				final Button month6 = (Button) dlg
						.findViewById(R.id.home_month_6);
				final Button month7 = (Button) dlg
						.findViewById(R.id.home_month_7);
				final Button month8 = (Button) dlg
						.findViewById(R.id.home_month_8);
				final Button month9 = (Button) dlg
						.findViewById(R.id.home_month_9);
				final Button month10 = (Button) dlg
						.findViewById(R.id.home_month_10);
				final Button month11 = (Button) dlg
						.findViewById(R.id.home_month_11);
				final Button month12 = (Button) dlg
						.findViewById(R.id.home_month_12);

				((Button) dlg.findViewById(R.id.home_month_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_month_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_3))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_4))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_5))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_6))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_7))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_month_8))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_9))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_10))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_11))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_month_12))
						.setOnLongClickListener(parentReference);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_sow);

				month1.setOnClickListener(new View.OnClickListener() {
			

					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("01");
						months_sow = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("02");
						months_sow = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("03");
						months_sow = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("04");
						months_sow = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("05");
						months_sow = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("06");
						months_sow = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("07");
						months_sow = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("08");
						months_sow = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("09");
						months_sow = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("10");
						months_sow = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("11");
						months_sow = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("12");
						months_sow = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sow.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});
		
		
		
		
		
		
		
		
		
		
		final CheckBox intercrop = (CheckBox) findViewById(R.id.chkintercrop);

		intercrop.setOnLongClickListener(this); // audio integration

		intercrop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user selected intercrop for Sowing*********** \r\n");

					}
				}

			}
		});

		Button btnNext = (Button) findViewById(R.id.sow_ok);
		Button cancel = (Button) findViewById(R.id.sow_cancel);

		btnNext.setOnLongClickListener(this); // Integration
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user clicked cancel in Sowing*********** \r\n");

				}
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider.File_Log_Create("UIlog.txt",
							"***** user clicked OK in Sowing*********** \r\n");

				}

				// Toast.makeText(action_sowing.this, "User enetred " +
				// sow_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag3, flag4;
				if (seed_sow.toString().equalsIgnoreCase("0")) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled seed variety for Sowing*********** \r\n");

					}

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (units_sow.toString().equalsIgnoreCase("0") || sow_no == 0) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled units for Sowing*********** \r\n");

					}

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (treatment_sow.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled treatment for Sowing*********** \r\n");

					}

				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				
				if (months_sow.toString().equalsIgnoreCase("0") || day_sow_int ==0) {

					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled treatment for Sowing*********** \r\n");

					}

				} else {

					flag4 = 0;

					days_sel_sow = day_sow_int + "." + months_sow;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 ==0) {
					System.out.println("sowing writing");
					mDataProvider.setSowing(sow_no, seed_sow, units_sow,
							days_sel_sow, treatment_sow, 0, 0);

					System.out.println("sowing reading");
					mDataProvider.getsowing();

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");

						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has filled all details for Sowing*********** \r\n");

					}

					Intent adminintent = new Intent(action_sowing.this,
							Homescreen.class);
					startActivity(adminintent);
					action_sowing.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_sowing.this,
						Homescreen.class);

				startActivity(adminintent);
				action_sowing.this.finish();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user has clicked home btn in Sowing*********** \r\n");

				}

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_var_sow) {

			playAudioalways(R.raw.varietyofseedssowd);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");

				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to variety of seed audio in Sowing*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_sow
				|| v.getId() == R.id.home_btn_units_no_sow) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");

				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to units audio in Sowing*********** \r\n");
			}
		}

		if (v.getId() == R.id.home_btn_day_sow) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");

				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to day audio in Sowing*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_treat_sow) {

			playAudioalways(R.raw.treatmenttoseeds1);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");

				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to traetment audio in Sowing*********** \r\n");

			}
		}

		if (v.getId() == R.id.sow_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.sow_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");

				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to help audio in Sowing*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_var_sow_1) { // audio integration

			System.out.println("variety sow1 called");
			playAudioalways(R.raw.bajra);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_var_sow_2) {

			playAudioalways(R.raw.castor);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_var_sow_3) {

			playAudioalways(R.raw.cowpea);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_var_sow_4) {

			playAudioalways(R.raw.greengram);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_var_sow_5) {

			playAudioalways(R.raw.groundnut1);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_var_sow_6) {

			playAudioalways(R.raw.horsegram);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_units_1) {

			playAudioalways(R.raw.bagof10kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_units_2) {

			playAudioalways(R.raw.bagof20kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_units_3) {

			playAudioalways(R.raw.bagof50kg);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_1) {

			playAudioalways(R.raw.twoweeksbefore);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_2) {

			playAudioalways(R.raw.oneweekbefore);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_3) {

			playAudioalways(R.raw.yesterday);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_day_4) {
			playAudioalways(R.raw.todayonly);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_day_5) {
			playAudioalways(R.raw.tomorrows);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_treat_sow_1) {
			playAudioalways(R.raw.treatmenttoseeds2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_treat_sow_2) {
			playAudioalways(R.raw.treatmenttoseeds3);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.chkintercrop) {
			playAudioalways(R.raw.yieldinfo);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.variety_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.variety);
			ShowHelpIcon(v);                                      
		}
		
		
		if (v.getId() == R.id.amount_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);                                     
		}
		
	/*	if (v.getId() == R.id.variety_pest_txt_btn) {                        //20-06-2012
			playAudio(R.raw.pesticidename);
			ShowHelpIcon(v);                                      
		}
		*/
		
		if (v.getId() == R.id.treat_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.treatment);
			ShowHelpIcon(v);                                      
		}
		if (v.getId() == R.id.intercrop_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.intercrop);
			ShowHelpIcon(v);                                     
		}
		
		if (v.getId() == R.id.home_month_1) { // added

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v);                                      //added for help icon
		}
		if (v.getId() == R.id.home_month_2) { // added

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v);                                      //added for help icon

		}

		if (v.getId() == R.id.home_month_3) { // added

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v);                                      //added for help icon

		}

		if (v.getId() == R.id.home_month_4) { // added

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v);                                      //added for help icon

		}

		if (v.getId() == R.id.home_month_5) { // added

			playAudioalways(R.raw.may);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_6) { // added

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_7) { // added

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_8) { // added

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_9) { // added

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_10) { // added

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_11) { // added

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v);                                      //added for help icon
		}

		if (v.getId() == R.id.home_month_12) { // added

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v);                                      //added for help icon
		}

		
		if (v.getId() == R.id.home_btn_month_sow) { // added

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);                                      //added for help icon
		}
		
		if (v.getId() == R.id.number_ok) { // added

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);                                      //added for help icon
		}
		
		if (v.getId() == R.id.number_cancel) { // added

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);                                      //added for help icon
		}
		
		if (v.getId() == R.id.date_sow_txt_btn) { // added

			playAudioalways(R.raw.date);
			ShowHelpIcon(v);                                      //added for help icon
		}
		

		return true;
	}
}