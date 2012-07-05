package com.commonsensenet.realfarm.actions;

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

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_spraying extends HelpEnabledActivity {

	private Context context = this;
	private RealFarmProvider mDataProvider;

	private final action_spraying parentReference = this; // audio integration

	private String prob_sel_spray = "0", pest_sel_spray = "0",
			day_sel_spray = "0", unit_sel_spray = "0", day_spray_str, months_spray="0";
	private int spray_no, day_spray_int;
	private String spray_no_sel;

	protected void cancelaudio() {
	
		playAudio(R.raw.cancel);
	
		Intent adminintent = new Intent(action_spraying.this, Homescreen.class);

		startActivity(adminintent);
		action_spraying.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_spraying.this, Homescreen.class);

		startActivity(adminintent);
		action_spraying.this.finish();
		if (Global.writeToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider
					.File_Log_Create("UIlog.txt",
							"***** user has clicked soft key BACK in Spraying page*********** \r\n");

		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		Log.d("in spray dialog", "in dialog");
		
	//	super.onCreate(savedInstanceState);
	//	setContentView(R.layout.spraying_dialog);
		
		super.onCreate(savedInstanceState, R.layout.spraying_dialog);           //Needed to add help icon
		setHelpIcon(findViewById(R.id.helpIndicator));
		
		final TextView day_spray = (TextView) findViewById(R.id.dlg_lbl_day_spray);
		
		
		playAudio(R.raw.clickingspraying);
	
		if (Global.writeToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt",
					"***** In Action Spraying*********** \r\n");

		}
		final ImageView bg_prob_spray = (ImageView) findViewById(R.id.img_bg_prob_spray);
		final ImageView bg_pest_spray = (ImageView) findViewById(R.id.img_bg_pest_spray);
		final ImageView bg_units_no_spray = (ImageView) findViewById(R.id.img_bg_units_no_spray);
		final ImageView bg_units_spray = (ImageView) findViewById(R.id.img_bg_units_spray);
		final ImageView bg_day_spray = (ImageView) findViewById(R.id.img_bg_day_spray);
		final ImageView bg_month_spray = (ImageView) findViewById(R.id.img_bg_month_spray);
		//bg_day_spray.setImageResource(R.drawable.empty_not);

		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		final Button item5;

		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_prob_spray);
		item2 = (Button) findViewById(R.id.home_btn_pest_spray);
		item3 = (Button) findViewById(R.id.home_btn_units_spray);
		item4 = (Button) findViewById(R.id.home_btn_day_spray);
		item5 = (Button) findViewById(R.id.home_btn_units_no_spray);
		final Button item6 = (Button) findViewById(R.id.home_btn_month_spray);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this); // Integration
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		help.setOnLongClickListener(this);
		
		final Button Problems;                                                                  //20-06-2012
		final Button Amount;
		final Button Date;
		final Button PestName;
		
		
		Problems = (Button) findViewById(R.id.variety_sow_txt_btn);                            //20-06-2012
		PestName = (Button) findViewById(R.id.variety_pest_txt_btn);
		Amount = (Button) findViewById(R.id.amount_sow_txt_btn);
		Date = (Button) findViewById(R.id.date_sow_txt_btn);
		
	
		Problems.setOnLongClickListener(this);                                                 //20-06-2012
		PestName.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in problem spray dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.prob_spraying_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the problem for spraying");
				Log.d("in problem spray dialog", "in dialog");
				dlg.show();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of problem for Spraying*********** \r\n");

				}
				final Button prob1;
				final Button prob2;
				final Button prob3;

				// final Button variety7;
				// final ImageView img_1 = (ImageView)
				// findViewById(R.id.dlg_var_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_prob_spray);
				prob1 = (Button) dlg.findViewById(R.id.home_prob_spray_1);
				prob2 = (Button) dlg.findViewById(R.id.home_prob_spray_2);
				prob3 = (Button) dlg.findViewById(R.id.home_prob_spray_3);

				((Button) dlg.findViewById(R.id.home_prob_spray_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_prob_spray_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_prob_spray_3))
						.setOnLongClickListener(parentReference);

				prob1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Problem 1");
						prob_sel_spray = "Problem 1";
						TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_prob_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + prob_sel_spray
											+ " for Spraying*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				prob2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Problem 2");
						prob_sel_spray = "Problem 2";
						TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_prob_spray.setImageResource(R.drawable.empty_not);

						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + prob_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

				prob3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Problem 3");
						prob_sel_spray = "Problem 3";
						TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_prob_spray.setImageResource(R.drawable.empty_not);

						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + prob_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in pest spray dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.pest_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Pesticide");
				Log.d("in units spray dialog", "in dialog");
				dlg.show();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of pesticide for Spraying*********** \r\n");

				}

				final Button pest1;
				final Button pest2;
				final Button pest3;

				// final Button variety7;
				// final ImageView img_1 = (ImageView)
				// findViewById(R.id.dlg_var_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_pest_spray);
				pest1 = (Button) dlg.findViewById(R.id.home_pest_spray_1);
				pest2 = (Button) dlg.findViewById(R.id.home_pest_spray_2);
				pest3 = (Button) dlg.findViewById(R.id.home_pest_spray_3);

				((Button) dlg.findViewById(R.id.home_pest_spray_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_pest_spray_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_pest_spray_3))
						.setOnLongClickListener(parentReference);

				pest1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Monocrotophos");
						pest_sel_spray = "Monocrotophos";
						TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_pest_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + pest_sel_spray
											+ " for Spraying*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				pest2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Dimethoate");
						pest_sel_spray = "Dimethoate";
						TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_pest_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + pest_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

				pest3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Dithane M-45");
						pest_sel_spray = "Dithane M-45";
						TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_pest_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + pest_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units fert dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.units_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the units");
				Log.d("in units fert dialog", "in dialog");
				dlg.show();

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");

					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of units for Spraying*********** \r\n");

				}

				final Button unit1;
				final Button unit2;
				final Button unit3;

				 final ImageView img_1 = (ImageView) findViewById(R.id.img_bg_units_spray);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_spray);
				unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
				unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
				unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(parentReference); // audio
																	// integrtion
				((Button) dlg.findViewById(R.id.home_btn_units_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_units_3))
						.setOnLongClickListener(parentReference);

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 pickedkg ", "in dialog");
						// img_1.setMaxWidth(300);
						 img_1.setImageResource(R.drawable.kg10);
						var_text.setText("10 Kgs");
						unit_sel_spray = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + unit_sel_spray
											+ " for Spraying*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						 img_1.setImageResource(R.drawable.kg20);
						var_text.setText("20 Kgs");
						unit_sel_spray = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + unit_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						 img_1.setImageResource(R.drawable.kg50);
						var_text.setText("50 Kgs");
						unit_sel_spray = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_spray.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + unit_sel_spray
											+ " for Spraying*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		item4.setOnClickListener(new View.OnClickListener() {
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
						day_spray_int = mynpd.getValue();
						day_spray_str = String.valueOf(day_spray_int);
						day_spray.setText(day_spray_str);
						if (day_spray_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);
							bg_day_spray.setImageResource(R.drawable.empty_not);
							
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

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_spray);

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
									"***** In selection of no of bags for Spraying*********** \r\n");

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
						spray_no = mynp1.getValue();
						spray_no_sel = String.valueOf(spray_no);
						no_text.setText(spray_no_sel);
						if (spray_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_spray
									.setImageResource(R.drawable.empty_not);
							if (Global.writeToSD == true) {

								String logtime = getcurrenttime();
								mDataProvider.File_Log_Create("UIlog.txt",
										logtime + " -> ");

								mDataProvider
										.File_Log_Create(
												"UIlog.txt",
												"***** user selected"
														+ spray_no_sel
														+ " bags for Spraying*********** \r\n");

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
									.File_Log_Create(
											"UIlog.txt",
											"***** user selected camncel on entering number of bags for Spraying*********** \r\n");

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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_spray);

				month1.setOnClickListener(new View.OnClickListener() {
			

					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("01");
						months_spray = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("02");
						months_spray = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("03");
						months_spray = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("04");
						months_spray = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("05");
						months_spray = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("06");
						months_spray = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("07");
						months_spray = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("08");
						months_spray = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("09");
						months_spray = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("10");
						months_spray = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("11");
						months_spray = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("12");
						months_spray = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_spray.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});
		
		
		
		
		
		
		
		

		Button btnNext = (Button) findViewById(R.id.spray_ok);
		Button cancel = (Button) findViewById(R.id.spray_cancel); // Integration

		btnNext.setOnLongClickListener(this);
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
									"***** User clicked cancel on spraying page*********** \r\n");

				}
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** User clicked ok on fertilizer page*********** \r\n");

				}

				// Toast.makeText(action_spraying.this, "User enetred " +
				// no_units_sprayed + " kgs", Toast.LENGTH_LONG).show();

				int flag1, flag2, flag3, flag4;
				if (unit_sel_spray.toString().equalsIgnoreCase("0")
						|| spray_no == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT Filled units in spraying page*********** \r\n");

					}

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (pest_sel_spray.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT Filled pesticide in spraying page*********** \r\n");

					}
				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (prob_sel_spray.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT Filled problems in spraying page*********** \r\n");

					}
				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_spray.toString().equalsIgnoreCase("0") || day_spray_int == 0) {

					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT Filled problems in spraying page*********** \r\n");

					}
				} else {

					flag4 = 0;

					day_sel_spray = day_spray_int + "."+ months_spray;
					
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_spray_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {
					System.out.println("spraying writing");
					mDataProvider
							.setspraying(spray_no, unit_sel_spray,
									day_sel_spray, prob_sel_spray, 1, 0,
									pest_sel_spray);

					System.out.println("spraying reading");
					mDataProvider.getspraying();

					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has  Filled all details in spraying page*********** \r\n");

					}

					Intent adminintent = new Intent(action_spraying.this,
							Homescreen.class);

					startActivity(adminintent);
					action_spraying.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user has clicke on home btn in spraying page*********** \r\n");

				}

				Intent adminintent = new Intent(action_spraying.this,
						Homescreen.class);

				startActivity(adminintent);
				action_spraying.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_prob_spray) {
			playAudioalways(R.raw.selecttheproblem);
			ShowHelpIcon(v); 

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to problem audio in spray*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_pest_spray) {
			playAudioalways(R.raw.selectthepesticide);
			ShowHelpIcon(v); 

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to pest audio in spray*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_spray
				|| v.getId() == R.id.home_btn_units_no_spray) {
			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v); 

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to units audio in spray*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_day_spray) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v); 

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to day audio in spray*********** \r\n");

			}
		}

		if (v.getId() == R.id.spray_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.spray_cancel) {
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
								"***** user has listened to help audio in spray*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_prob_spray_1) { // added audio integration
			playAudioalways(R.raw.problem1);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_prob_spray_2) { // added
			playAudioalways(R.raw.problem2);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_prob_spray_3) { // added
			playAudioalways(R.raw.problem3);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_pest_spray_1) { // added
			playAudioalways(R.raw.pesticide1);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_pest_spray_2) { // added
			playAudioalways(R.raw.pesticide2);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_pest_spray_3) { // added
			playAudioalways(R.raw.pesticide3);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_btn_units_1) { // added

			playAudioalways(R.raw.bagof10kg);
			ShowHelpIcon(v); 

		}

		if (v.getId() == R.id.home_btn_units_2) { // added
			playAudioalways(R.raw.bagof20kg);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_btn_units_3) { // added
			playAudioalways(R.raw.bagof50kg);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_day_1) { // added
			playAudioalways(R.raw.twoweeksbefore);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_day_2) { // added
			playAudioalways(R.raw.oneweekbefore);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_day_3) { // added
			playAudioalways(R.raw.yesterday);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_day_4) { // added
			playAudioalways(R.raw.todayonly);
			ShowHelpIcon(v); 
		}

		if (v.getId() == R.id.home_day_5) { // added
			playAudioalways(R.raw.tomorrows);
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
		
		if (v.getId() == R.id.home_btn_month_spray) { // added

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);                                      //added for help icon
		}
		
		if (v.getId() == R.id.variety_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);                                     
		}
		
		if (v.getId() == R.id.date_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);                                      
		}
		
		
		if (v.getId() == R.id.amount_sow_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);                                      
		}
		if (v.getId() == R.id.variety_pest_txt_btn) {                        //20-06-2012
			playAudioalways(R.raw.pesticidename);
			ShowHelpIcon(v);                                     
		}
		
		if (v.getId() == R.id.number_ok) { // added

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);                                      //added for help icon
		}
		
		if (v.getId() == R.id.number_cancel) { // added

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);                                      //added for help icon
		}



		return true;
	}
}