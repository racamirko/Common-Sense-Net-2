package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_harvest extends HelpEnabledActivity { // Integration
	private Context context = this;
	private int feedback_sel;
	private int harvest_no, day_harvest_int;
	private String harvest_no_sel, units_harvest = "0", feedback_txt,
			months_harvest = "0", day_harvest_sel_1 = "0";
	private RealFarmProvider mDataProvider;
	private final action_harvest parentReference = this; // audio integration
	private String final_day_harvest;
	String mSelectedMonth;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_harvest.this, Homescreen.class);

		startActivity(adminintent);
		action_harvest.this.finish();
	}

	// MediaPlayer mp = null; //integration
	public void onBackPressed() {

		SoundQueue.getInstance().stop();
		if (Global.writeToSD == true) {

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

		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.harvest_dialog);

		super.onCreate(savedInstanceState, R.layout.harvest_dialog); // Needed
																		// to
																		// add
																		// help
																		// icon
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");
		final Button smiley1;
		final Button smiley2;
		final Button smiley3;

		final ImageView bg_day_harvest = (ImageView) findViewById(R.id.img_bg_day_harvest);
		final ImageView bg_units_no_harvest = (ImageView) findViewById(R.id.img_bg_units_no_harvest);
		final ImageView bg_units_harvest = (ImageView) findViewById(R.id.img_bg_units_harvest);
		final ImageView bg_month_harvest = (ImageView) findViewById(R.id.img_bg_month_harvest);

		smiley1 = (Button) findViewById(R.id.home_btn_har_1);
		smiley2 = (Button) findViewById(R.id.home_btn_har_2);
		smiley3 = (Button) findViewById(R.id.home_btn_har_3);
		smiley1.setBackgroundResource(R.drawable.smiley_good_not);
		smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley3.setBackgroundResource(R.drawable.smiley_bad_not);

		playAudio(R.raw.clickingharvest);

		if (Global.writeToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt",
					"***** In Action harvest*********** \r\n");

		}

		System.out.println("Plant details entered1");
		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;
		System.out.println("Plant details entered2");
		item1 = (Button) findViewById(R.id.home_btn_units_no_harvest);
		item2 = (Button) findViewById(R.id.home_btn_units_harvest);
		item3 = (Button) findViewById(R.id.home_btn_month_harvest);
		item4 = (Button) findViewById(R.id.home_btn_day_harvest);

		System.out.println("Plant details entered3");
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

		final Button harvest_date; // 20-06-2012
		final Button Amount;

		harvest_date = (Button) findViewById(R.id.variety_sow_txt_btn); // 20-06-2012
		Amount = (Button) findViewById(R.id.amount_sow_txt_btn);

		harvest_date.setOnLongClickListener(this); // 20-06-2012
		Amount.setOnLongClickListener(this);
		System.out.println("Plant details entered4");
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
				if (Global.writeToSD == true) {

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
				if (Global.writeToSD == true) {

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
				if (Global.writeToSD == true) {

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

				playAudio(R.raw.noofbags); // 20-06-2012

				if (Global.writeToSD == true) {

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

				((Button) dlg.findViewById(R.id.number_ok)) // 20-06-2012
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

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
							if (Global.writeToSD == true) {

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
						if (Global.writeToSD == true) {

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
				if (Global.writeToSD == true) {

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
						var_text.setText("10 Kgs");
						units_harvest = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_harvest.setImageResource(R.drawable.empty_not);
						if (Global.writeToSD == true) {

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
						var_text.setText("20 Kgs");
						units_harvest = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_harvest.setImageResource(R.drawable.empty_not);

						if (Global.writeToSD == true) {

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
						var_text.setText("50 Kgs");
						units_harvest = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_harvest.setImageResource(R.drawable.empty_not);

						if (Global.writeToSD == true) {

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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_harvest);

				month1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("01");
						months_harvest = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("02");
						months_harvest = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("03");
						months_harvest = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("04");
						months_harvest = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("05");
						months_harvest = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("06");
						months_harvest = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("07");
						months_harvest = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("08");
						months_harvest = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("09");
						months_harvest = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("10");
						months_harvest = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("11");
						months_harvest = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("12");
						months_harvest = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_harvest.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

				// callmonthlist();
			}

		});

		final TextView day_fert = (TextView) findViewById(R.id.dlg_lbl_day_harvest);

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Year when harvested");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.dateinfo); // 20-06-2012

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok)) // 20-06-2012
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.number_cancel))
						.setOnLongClickListener(parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						day_harvest_int = mynpd.getValue();
						day_harvest_sel_1 = String.valueOf(day_harvest_int);
						day_fert.setText(day_harvest_sel_1);
						if (day_harvest_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_day_harvest
									.setImageResource(R.drawable.empty_not);

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

		Button btnNext = (Button) findViewById(R.id.harvest_ok);
		Button cancel = (Button) findViewById(R.id.harvest_cancel); // integration

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

				// String feedback = String.valueOf(feedback_sel);
				// Toast.makeText(action_harvest.this,
				// "User selected feedback  " + feedback,
				// Toast.LENGTH_LONG).show();

				// to obtain the + - values
				if (Global.writeToSD == true) {

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

					if (Global.writeToSD == true) {

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
					if (Global.writeToSD == true) {

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

				if (months_harvest.toString().equalsIgnoreCase("0")
						|| day_harvest_int == 0) {
					flag3 = 1;

					TableRow tr_months = (TableRow) findViewById(R.id.harvest_date_tr);

					tr_months.setBackgroundResource(R.drawable.def_img_not);
					if (Global.writeToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider
								.File_Log_Create("UIlog.txt",
										"***** user has NOT filled date in harvest*********** \r\n");

					}
				} else {
					flag3 = 0;

					final_day_harvest = day_harvest_int + "." + months_harvest;

					TableRow tr_units = (TableRow) findViewById(R.id.harvest_date_tr);

					tr_units.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
					System.out.println("harvesting writing");
					mDataProvider.setHarvest(harvest_no, 0, units_harvest,
							final_day_harvest, feedback_txt, 1, 0);

					System.out.println("harvesting reading");
					mDataProvider.getharvesting();

					if (Global.writeToSD == true) {

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
				if (Global.writeToSD == true) {

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

	protected void callmonthlist() {
		// TODO Auto-generated method stub
		Log.d("in Lang selection", "in dialog");
		final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_harvest);
		final ImageView bg_month_harvest = (ImageView) findViewById(R.id.img_bg_month_harvest);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// gets the language list from the resources.
		Resources res = getResources();
		String[] languages = res.getStringArray(R.array.array_months);

		ListView monthList = new ListView(this);
		ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				languages);
		// sets the adapter.
		monthList.setAdapter(languageAdapter);

		// adds the event listener to detect the language selection.
		monthList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// stores the selected language.
				mSelectedMonth = (String) parent.getAdapter().getItem(position);

				TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);

				tr_feedback.setBackgroundResource(R.drawable.def_img);
				bg_month_harvest.setImageResource(R.drawable.empty_not);

				if (mSelectedMonth.toString().equalsIgnoreCase("January")) {
					var_text.setText("01");
					months_harvest = "01";
				}

				if (mSelectedMonth.toString().equalsIgnoreCase("February")) {
					var_text.setText("02");
					months_harvest = "02";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("March")) {
					var_text.setText("03");
					months_harvest = "03";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("April")) {
					var_text.setText("04");
					months_harvest = "04";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("May")) {
					var_text.setText("05");
					months_harvest = "05";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("June")) {
					var_text.setText("06");
					months_harvest = "06";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("July")) {
					var_text.setText("07");
					months_harvest = "07";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("August")) {
					var_text.setText("08");
					months_harvest = "08";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("September")) {
					var_text.setText("09");
					months_harvest = "09";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("October")) {
					var_text.setText("10");
					months_harvest = "10";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("November")) {
					var_text.setText("11");
					months_harvest = "11";
				}
				if (mSelectedMonth.toString().equalsIgnoreCase("December")) {
					var_text.setText("12");
					months_harvest = "12";
				}

				// closes the dialog.
				dialog.dismiss();
			}

		});

		// sets the view
		dialog.setContentView(monthList);
		// sets the properties of the dialog.
		dialog.setTitle("Please select the month");
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_har_1) {

			playAudioalways(R.raw.feedbackgood);
			ShowHelpIcon(v);
			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback good audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_har_2) {

			playAudioalways(R.raw.feedbackmoderate);
			ShowHelpIcon(v);
			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback medium audio in harvest*********** \r\n");

			}
		}
		if (v.getId() == R.id.home_btn_har_3) {

			playAudioalways(R.raw.feedbackbad);
			ShowHelpIcon(v);

			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to feeback bad audio in harvest*********** \r\n");

			}

		}
		if (v.getId() == R.id.harvest_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.harvest_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.aggr_img_help) {

			playAudioalways(R.raw.help);
			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to help audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_no_harvest
				|| v.getId() == R.id.home_btn_units_harvest) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);
			if (Global.writeToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider
						.File_Log_Create("UIlog.txt",
								"***** user has listened to units audio in harvest*********** \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_1) { // audio integration

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

		if (v.getId() == R.id.home_btn_day_harvest) { // added

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_btn_month_harvest) { // added

			playAudioalways(R.raw.choosethemonthwhenharvested);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_1) { // added

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.home_month_2) { // added

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_3) { // added

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_4) { // added

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_5) { // added

			playAudioalways(R.raw.may);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_6) { // added

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_7) { // added

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_8) { // added

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_9) { // added

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_10) { // added

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_11) { // added

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_12) { // added

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_1) { // added

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v); // added for help icon
		}
		if (v.getId() == R.id.home_month_2) { // added

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v); // added for help icon

		}

		if (v.getId() == R.id.home_month_3) { // added

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v); // added for help icon

		}

		if (v.getId() == R.id.home_month_4) { // added

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v); // added for help icon

		}

		if (v.getId() == R.id.home_month_5) { // added

			playAudioalways(R.raw.may);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_6) { // added

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_7) { // added

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_8) { // added

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_9) { // added

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_10) { // added

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_11) { // added

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.home_month_12) { // added

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.number_ok) { // added

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.number_cancel) { // added

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v); // added for help icon
		}

		if (v.getId() == R.id.variety_sow_txt_btn) { // 20-06-2012
			playAudioalways(R.raw.harvestyear);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.amount_sow_txt_btn) { // 20-06-2012
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		return true;
	}

	protected void stopaudio() {
		SoundQueue.getInstance().stop();
	}
}