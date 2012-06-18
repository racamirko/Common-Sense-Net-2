package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_harvest extends HelpEnabledActivity { // Integration
	int feedback_sel;
	int harvest_no;
	String year_harvest;
	String harvest_no_sel, units_harvest = "0", strDateTime = "0",
			feedback_txt, months_harvest = "0";
	protected RealFarmProvider mDataProvider;
	private Context context = this;
	final action_harvest parentReference = this; // audio integration

	// MediaPlayer mp = null; //integration
	public void onBackPressed() {

		SoundQueue.getInstance().stop();
		if (Global.WriteToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider
					.File_Log_Create("UIlog.txt",
							"***** user has clicked back button in harvest*********** \r\n");

		}
		Intent adminintent = new Intent(action_harvest.this, Homescreen.class);

		startActivity(adminintent);
		action_harvest.this.finish();

		SoundQueue sq = SoundQueue.getInstance(); // audio integration
		sq.stop();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.harvest_dialog);
		System.out.println("plant done");
		final Button smiley1;
		final Button smiley2;
		final Button smiley3;

		final ImageView bg_month_harvest = (ImageView) findViewById(R.id.img_bg_month_harvest);
		final ImageView bg_year_harvest = (ImageView) findViewById(R.id.img_bg_year_harvest);
		final ImageView bg_units_no_harvest = (ImageView) findViewById(R.id.img_bg_units_no_harvest);
		final ImageView bg_units_harvest = (ImageView) findViewById(R.id.img_bg_units_harvest);

		smiley1 = (Button) findViewById(R.id.home_btn_har_1);
		smiley2 = (Button) findViewById(R.id.home_btn_har_2);
		smiley3 = (Button) findViewById(R.id.home_btn_har_3);
		smiley1.setBackgroundResource(R.drawable.smiley_good_not);
		smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

		playAudio(R.raw.clickingharvest);

		if (Global.WriteToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt",
					"***** In Action harvest*********** \r\n");

		}

		final TextView harvest_year_set = (TextView) findViewById(R.id.dlg_lbl_harvest_year);
		harvest_year_set.setText("2012");
		year_harvest = "2012";
		bg_year_harvest.setImageResource(R.drawable.empty_not);
		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;

		item1 = (Button) findViewById(R.id.home_btn_units_no_harvest);
		item2 = (Button) findViewById(R.id.home_btn_units_harvest);
		item3 = (Button) findViewById(R.id.home_btn_harvest_date);
		item4 = (Button) findViewById(R.id.home_btn_harvest_year);

		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);
		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		smiley1.setOnLongClickListener(this); // Integration
		smiley2.setOnLongClickListener(this);
		smiley3.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		smiley1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 1;
				feedback_txt = "good";
				smiley1.setBackgroundResource(R.drawable.smiley_good);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

				tr_feedback.setBackgroundResource(R.drawable.def_img);
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt",
							"***** user selected feeback good*********** \r\n");

				}
			}
		});

		smiley2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 2;
				feedback_txt = "medium";
				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

				tr_feedback.setBackgroundResource(R.drawable.def_img);
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user selected feeback medium*********** \r\n");

				}
			}
		});

		smiley3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				feedback_sel = 3;
				feedback_txt = "bad";
				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad);
				TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

				tr_feedback.setBackgroundResource(R.drawable.def_img);
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt",
							"***** user selected feeback bad*********** \r\n");

				}
			}
		});

		final TextView no_text_1 = (TextView) findViewById(R.id.dlg_lbl_unit_no_harvest);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Number of bags");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** in selection of no of bags in harvest *********** \r\n");

				}
				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						harvest_no = mynp1.getValue();
						harvest_no_sel = String.valueOf(harvest_no);
						no_text_1.setText(harvest_no_sel);

						if (harvest_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_harvest
									.setImageResource(R.drawable.empty_not);
							if (Global.WriteToSD == true) {

								String logtime = getcurrenttime();
								mDataProvider.File_Log_Create("UIlog.txt",
										logtime + " -> ");
								mDataProvider
										.File_Log_Create(
												"UIlog.txt",
												"***** user selected "
														+ harvest_no_sel
														+ "of bags in harvest*********** \r\n");

							}
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider
									.File_Log_Create("UIlog.txt",
											"***** user selected cancel in no of bags in harvest*********** \r\n");

						}
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units fert dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.units_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the units");
				Log.d("in units fert dialog", "in dialog");
				dlg.show();

				final Button unit1;
				final Button unit2;
				final Button unit3;

				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.dlg_unit_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_harvest);
				unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
				unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
				unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_btn_units_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_units_3))
						.setOnLongClickListener(parentReference);
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of units in harvest*********** \r\n");

				}

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bag of 10 Kgs");
						units_harvest = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_harvest.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider
									.File_Log_Create(
											"UIlog.txt",
											"***** user selected "
													+ units_harvest
													+ " units in harvest*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Bag of 20 Kgs");
						units_harvest = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_harvest.setImageResource(R.drawable.empty_not);

						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider
									.File_Log_Create(
											"UIlog.txt",
											"***** user selected "
													+ units_harvest
													+ " units in harvest*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Bag of 50 Kgs");
						units_harvest = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_harvest.setImageResource(R.drawable.empty_not);

						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider
									.File_Log_Create(
											"UIlog.txt",
											"***** user selected "
													+ units_harvest
													+ " units in harvest*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		final TextView no_text_2 = (TextView) findViewById(R.id.dlg_lbl_harvest_date);

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.months_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the month when harvested");
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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_harvest_date);

				month1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("January");
						months_harvest = "January";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("February");
						months_harvest = "February";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("March");
						months_harvest = "March";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("April");
						months_harvest = "April";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("May");
						months_harvest = "May";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("June");
						months_harvest = "June";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("July");
						months_harvest = "July";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("August");
						months_harvest = "August";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("September");
						months_harvest = "September";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("October");
						months_harvest = "October";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("November");
						months_harvest = "November";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("December");
						months_harvest = "December";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);
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
				dlg.setContentView(R.layout.years_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Year when harvested");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final Button year1 = (Button) dlg
						.findViewById(R.id.home_year_1);
				final Button year2 = (Button) dlg
						.findViewById(R.id.home_year_2);

				year1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						harvest_year_set.setText("2011");
						year_harvest = "2011";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_year_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});
				year2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						harvest_year_set.setText("2012");
						year_harvest = "2012";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_year_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

			}

		});

		Button btnNext = (Button) findViewById(R.id.harvest_ok);
		Button cancel = (Button) findViewById(R.id.home_btn_wf_2); // integration

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user selected cancel in harvest*********** \r\n");

				}
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				int flag1, flag2, flag3;
				// Toast.makeText(action_harvest.this, "User selected " +
				// strDateTime + "Time", Toast.LENGTH_LONG).show(); //Generate a
				// toast only if you want
				// finish(); // If you want to continue on that TimeDateActivity
				// If you want to go to new activity that code you can also
				// write here

				// to know which feedback user clicked

				String feedback = String.valueOf(feedback_sel);
				// Toast.makeText(action_harvest.this,
				// "User selected feedback  " + feedback,
				// Toast.LENGTH_LONG).show();

				// to obtain the + - values
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user clciked ok  in harvest*********** \r\n");

				}

				if (feedback_sel == 0) {
					flag1 = 1;
					// Toast.makeText(action_harvest.this,
					// " Please enter the feedback", Toast.LENGTH_LONG).show();
					TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled feedback in harvest*********** \r\n");

					}
				} else {
					flag1 = 0;
					TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);

					tr_feedback.setBackgroundResource(R.drawable.def_img);

				}
				if (units_harvest.toString().equalsIgnoreCase("0")
						|| harvest_no == 0) {
					flag2 = 1;
					// Toast.makeText(action_harvest.this,
					// " Please enter the kgs", Toast.LENGTH_LONG).show();

					TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);

					tr_units.setBackgroundResource(R.drawable.def_img_not);
					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled units in harvest*********** \r\n");

					}
				} else {
					flag2 = 0;
					TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);

					tr_units.setBackgroundResource(R.drawable.def_img);
				}

				if (months_harvest.toString().equalsIgnoreCase("0")) {
					flag3 = 1;
					Toast.makeText(action_harvest.this,
							" Please enter the kgs", Toast.LENGTH_LONG).show();

					TableRow tr_months = (TableRow) findViewById(R.id.harvest_date_tr);

					tr_months.setBackgroundResource(R.drawable.def_img_not);
					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled date in harvest*********** \r\n");

					}
				} else {
					flag3 = 0;
					TableRow tr_units = (TableRow) findViewById(R.id.harvest_date_tr);

					tr_units.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
					System.out.println("harvesting writing");
					mDataProvider.setHarvest(harvest_no, 0, units_harvest,
							year_harvest + months_harvest, feedback_txt, 1, 0);

					System.out.println("harvesting reading");
					mDataProvider.getharvesting();

					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has filled all details in harvest*********** \r\n");

					}
					Intent adminintent = new Intent(action_harvest.this,
							Homescreen.class);

					startActivity(adminintent);
					action_harvest.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_harvest.this,
						Homescreen.class);

				startActivity(adminintent);
				action_harvest.this.finish();
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user has clicked on home btn  in harvest*********** \r\n");

				}

			}
		});
	}

	protected void initmissingval() {
		playAudio(R.raw.missinginfo);
	}

	protected void cancelaudio() {
		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_harvest.this, Homescreen.class);

		startActivity(adminintent);
		action_harvest.this.finish();
	}

	protected void okaudio() {

		playAudio(R.raw.ok);

	}

	protected void stopaudio() {
		SoundQueue.getInstance().stop();
	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_har_1) {

			playAudio(R.raw.feedbackgood);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback good audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_har_2) {

			playAudio(R.raw.feedbackmoderate);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback medium audio in harvest*********** \r\n");

			}
		}
		if (v.getId() == R.id.home_btn_har_3) {

			playAudio(R.raw.feedbackbad);

			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback bad audio in harvest*********** \r\n");

			}

		}
		if (v.getId() == R.id.harvest_ok) {

			playAudio(R.raw.ok);

		}
		if (v.getId() == R.id.home_btn_wf_2) {

			playAudio(R.raw.cancel);

		}
		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to help audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_no_harvest
				|| v.getId() == R.id.home_btn_units_harvest) {

			playAudio(R.raw.selecttheunits);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to units audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_1) { // audio integration

			playAudio(R.raw.bagof10kg);

		}

		if (v.getId() == R.id.home_btn_units_2) { // added

			playAudio(R.raw.bagof20kg);

		}

		if (v.getId() == R.id.home_btn_units_3) { // added

			playAudio(R.raw.bagof50kg);

		}

		if (v.getId() == R.id.home_btn_harvest_date) { // added

			playAudio(R.raw.choosethemonthwhenharvested);

		}

		if (v.getId() == R.id.home_btn_harvest_year) { // added

			playAudio(R.raw.selectthedate);

		}

		if (v.getId() == R.id.home_month_1) { // added

			playAudio(R.raw.jan);

		}
		if (v.getId() == R.id.home_month_2) { // added

			playAudio(R.raw.feb);

		}

		if (v.getId() == R.id.home_month_3) { // added

			playAudio(R.raw.mar);

		}

		if (v.getId() == R.id.home_month_4) { // added

			playAudio(R.raw.apr);

		}

		if (v.getId() == R.id.home_month_5) { // added

			playAudio(R.raw.may);

		}

		if (v.getId() == R.id.home_month_6) { // added

			playAudio(R.raw.jun);

		}

		if (v.getId() == R.id.home_month_7) { // added

			playAudio(R.raw.jul);

		}

		if (v.getId() == R.id.home_month_8) { // added

			playAudio(R.raw.aug);

		}

		if (v.getId() == R.id.home_month_9) { // added

			playAudio(R.raw.sep);

		}

		if (v.getId() == R.id.home_month_10) { // added

			playAudio(R.raw.oct);

		}

		if (v.getId() == R.id.home_month_11) { // added

			playAudio(R.raw.nov);

		}

		if (v.getId() == R.id.home_month_12) { // added

			playAudio(R.raw.dec);

		}

		return true;
	}

	public void playAudio(int resid) {
		if (Global.EnableAudio == true) // checking for audio enable
		{
			System.out.println("play audio called");
			SoundQueue sq = SoundQueue.getInstance();
			// stops any sound that could be playing.
			sq.stop();

			sq.addToQueue(resid);
			// sq.addToQueue(R.raw.treatmenttoseeds3);
			sq.play();
		}

	}
}