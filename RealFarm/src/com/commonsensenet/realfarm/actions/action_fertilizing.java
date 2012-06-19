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
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_fertilizing extends HelpEnabledActivity {
	private RealFarmProvider mDataProvider;
	private final action_fertilizing parentReference = this;
	private String units_fert = "0", fert_var_sel = "0", fert_day_sel;
	private int fert_no;
	private String fert_no_sel;
	private Context context = this;

	public void onBackPressed() {

		if (Global.WriteToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " Softkey "+ " click " + " Back_button " + " null " + " \r\n");

		}
		Intent adminintent = new Intent(action_fertilizing.this,
				Homescreen.class);

		startActivity(adminintent);
		action_fertilizing.this.finish();

		SoundQueue sq = SoundQueue.getInstance(); // audio integration
		sq.stop();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fertilizing_dialog);
		System.out.println("plant done");
		final TextView day_fert = (TextView) findViewById(R.id.dlg_lbl_day_fert);
		day_fert.setText("Today");
		fert_day_sel = "Today";

		final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);

		final ImageView bg_units_no_fert = (ImageView) findViewById(R.id.img_bg_units_no_fert);

		final ImageView bg_units_fert = (ImageView) findViewById(R.id.img_bg_units_fert);

		final ImageView bg_var_fert = (ImageView) findViewById(R.id.img_bg_var_fert);

		bg_day_fert.setImageResource(R.drawable.empty_not);

		playAudio(R.raw.clickingfertilising);

		if (Global.WriteToSD == true) {

			String logtime = getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt"," Homepage "+ " actionicon "+ " click " + " fertilizingicon " + " null " + " \r\n");

		}

		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_var_fert);
		item2 = (Button) findViewById(R.id.home_btn_units_fert);
		item3 = (Button) findViewById(R.id.home_btn_day_fert);
		item4 = (Button) findViewById(R.id.home_btn_units_no_fert);

		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this); // Integration
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.variety_fert_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " selection "+ " click " + " type_fertilizer " + " null " + " \r\n");

				}
				final Button fert1;
				final Button fert2;
				final Button fert3;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_var_fert);
				fert1 = (Button) dlg.findViewById(R.id.home_var_fert_1);
				fert2 = (Button) dlg.findViewById(R.id.home_var_fert_2);
				fert3 = (Button) dlg.findViewById(R.id.home_var_fert_3);

				((Button) dlg.findViewById(R.id.home_var_fert_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_var_fert_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_var_fert_3))
						.setOnLongClickListener(parentReference);

				fert1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Fertilizer 1");
						fert_var_sel = "Fertilizer 1";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " fertilizer1 " + " fertilizer1 " + " \r\n");

						}

						dlg.cancel();
					}
				});

				fert2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Fertilizer 2");
						fert_var_sel = "Fertilizer 2";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " fertilizer2 " + " fertilizer2 " + " \r\n");


						}
						dlg.cancel();
					}
				});

				fert3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Fertilizer 3");
						fert_var_sel = "Fertilizer 3";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " fertilizer3 " + " fertilizer3 " + " \r\n");


						}
						dlg.cancel();
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
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " selection "+ " click " + " units_fertilizer " + " null " + " \r\n");


				}

				final Button unit1;
				final Button unit2;
				final Button unit3;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_fert);
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

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bag of 10 Kgs");
						units_fert = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);

						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " units_fertilizer " + units_fert + " \r\n");


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
						units_fert = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " units_fertilizer " + units_fert + " \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Bag of 50 Kgs");
						units_fert = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " units_fertilizer " + units_fert + " \r\n");


						}
						dlg.cancel();
					}
				});

			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in day sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.days_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the day");
				Log.d("in day sowing dialog", "in dialog");
				dlg.show();

				final Button day1;
				final Button day2;
				final Button day3;
				final Button day4;
				final Button day5;

				((Button) dlg.findViewById(R.id.home_day_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_day_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_3))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_4))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_5))
						.setOnLongClickListener(parentReference);

				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " selection "+ " click " + " day_fertilizer " + " null " + " \r\n");


				}

				day1 = (Button) dlg.findViewById(R.id.home_day_1);
				day2 = (Button) dlg.findViewById(R.id.home_day_2);
				day3 = (Button) dlg.findViewById(R.id.home_day_3);
				day4 = (Button) dlg.findViewById(R.id.home_day_4);
				day5 = (Button) dlg.findViewById(R.id.home_day_5);

				((Button) dlg.findViewById(R.id.home_day_1))
						.setOnLongClickListener(parentReference); // added
				((Button) dlg.findViewById(R.id.home_day_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_3))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_4))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_day_5))
						.setOnLongClickListener(parentReference);

				day1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						day_fert.setText("Two week before");
						fert_day_sel = "Two week before";
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
						bg_day_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
		    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " day_fertilizer " + fert_day_sel + " \r\n");

						}
						dlg.cancel();
					}
				});

				day2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						day_fert.setText("One week before");
						fert_day_sel = "One week before";
						final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
						bg_day_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
		    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " day_fertilizer " + fert_day_sel + " \r\n");


						}
						dlg.cancel();
					}
				});

				day3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_fert.setText("Yesterday");
						fert_day_sel = "Yesterday";
						final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
						bg_day_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
		    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " day_fertilizer " + fert_day_sel + " \r\n");


						}
						dlg.cancel();
					}
				});
				day4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_fert.setText("Today");
						fert_day_sel = "Today";
						final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
						bg_day_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
		    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " day_fertilizer " + fert_day_sel + " \r\n");


						}
						dlg.cancel();
					}
				});
				day5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_fert.setText("Tomorrow");
						fert_day_sel = "Tomorrow";
						final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
						bg_day_fert.setImageResource(R.drawable.empty_not);
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
		    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " in_popup "+ " click " + " day_fertilizer " + fert_day_sel + " \r\n");


						}
						dlg.cancel();
					}
				});

			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_fert);

		item4.setOnClickListener(new View.OnClickListener() {
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
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " selection "+ " click " + " no_units_fertilizer " + " null " + " \r\n");
				}
				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						fert_no = mynp1.getValue();
						fert_no_sel = String.valueOf(fert_no);
						no_text.setText(fert_no_sel);
						if (fert_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);

							bg_units_no_fert
									.setImageResource(R.drawable.empty_not);

							if (Global.WriteToSD == true) {

								String logtime = getcurrenttime();
								mDataProvider.File_Log_Create("UIlog.txt",
										logtime + " -> ");
			    				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " number_picker "+ " click " + " no_units_fertilizer " + fert_no_sel + " \r\n");

							}
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						if (Global.WriteToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " number_picker "+ " click " + " cancel_no_units " + "cancelnumberentry" + " \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});

		Button btnNext = (Button) findViewById(R.id.fert_ok);
		Button cancel = (Button) findViewById(R.id.fert_cancel);

		btnNext.setOnLongClickListener(this); // Integration
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " Cancel "+ " click " + " Cancel_btn " + "null" + " \r\n");

				}
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " OK "+ " click " + " OK_btn " + "null" + " \r\n");

				}

				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2;
				if (units_fert.toString().equalsIgnoreCase("0") || fert_no == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " Incomplete "+ " Auto " + " ok_btn " + "units_fertilizer" + " \r\n");

					}

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (fert_var_sel.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);
					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " Incomplete "+ " Auto " + " ok_btn " + "type_fertilizer" + " \r\n");

					}

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0) {

					System.out.println("fertilizing writing");
					mDataProvider.setFertilizing(fert_no, fert_var_sel,
							units_fert, fert_day_sel, 1, 0);

					System.out.println("fertilizing reading");
					mDataProvider.getfertizing();

					if (Global.WriteToSD == true) {

						String logtime = getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt", logtime
								+ " -> ");
						mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " complete "+ " Auto " + " ok_btn " + "null" + " \r\n");
					}
					Intent adminintent = new Intent(action_fertilizing.this,
							Homescreen.class);

					startActivity(adminintent);
					action_fertilizing.this.finish();
					okAudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_fertilizing.this,
						Homescreen.class);

				startActivity(adminintent);
				action_fertilizing.this.finish();
				if (Global.WriteToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " home "+ " click " + " home_btn " + "header_home" + " \r\n");

				}

			}
		});

	}

	// @Override
	protected void initmissingval() {

		playAudio(R.raw.missinginfo);
	}

	protected void stopaudio() {
		SoundQueue.getInstance().stop();
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_var_fert) {

			playAudio(R.raw.selecttypeoffertilizer);

			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " audio "+ " longtap " + " type_fertilizer " + "Audio_played" + " \r\n");
			}

		}

		if (v.getId() == R.id.home_btn_units_fert) {

			playAudio(R.raw.selecttheunits);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " audio "+ " longtap " + " units_fertilizer " + "Audio_played" + " \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_units_no_fert) {

			playAudio(R.raw.selecttheunits);

			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
    			mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " audio "+ " longtap " + " units_fertilizer " + "Audio_played" + " \r\n");

			}
		}

		if (v.getId() == R.id.home_btn_day_fert) {

			playAudio(R.raw.selectthedate);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " audio "+ " longtap " + " day_fertilizer " + "Audio_played" + " \r\n");

			}
		}

		if (v.getId() == R.id.fert_ok) {

			playAudio(R.raw.ok);
		}

		if (v.getId() == R.id.fert_cancel) {

			playAudio(R.raw.cancel);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudio(R.raw.help);
			if (Global.WriteToSD == true) {

				String logtime = getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
				mDataProvider.File_Log_Create("UIlog.txt"," Fertilizing "+ " audio "+ " longtap " + " help_fertilizer " + "Audio_played" + " \r\n");


			}
		}

		if (v.getId() == R.id.home_var_fert_1) { // audio integration

			playAudio(R.raw.fertilizer1);

		}

		if (v.getId() == R.id.home_var_fert_2) {

			playAudio(R.raw.fertilizer2);

		}

		if (v.getId() == R.id.home_var_fert_3) {

			playAudio(R.raw.fertilizer3);

		}

		if (v.getId() == R.id.home_btn_units_1) {

			playAudio(R.raw.bagof10kg);

		}

		if (v.getId() == R.id.home_btn_units_2) {

			playAudio(R.raw.bagof20kg);

		}

		if (v.getId() == R.id.home_btn_units_3) {

			playAudio(R.raw.bagof50kg);

		}

		if (v.getId() == R.id.home_day_1) {

			playAudio(R.raw.twoweeksbefore);

		}

		if (v.getId() == R.id.home_day_2) {

			playAudio(R.raw.oneweekbefore);

		}

		if (v.getId() == R.id.home_day_3) {

			playAudio(R.raw.yesterday);

		}

		if (v.getId() == R.id.home_day_4) {

			playAudio(R.raw.todayonly);

		}

		if (v.getId() == R.id.home_day_5) {

			playAudio(R.raw.tomorrows);

		}

		return true;
	}

	protected void cancelAudio() {
		playAudio(R.raw.cancel);
		Intent adminintent = new Intent(action_fertilizing.this,
				Homescreen.class);

		startActivity(adminintent);
		action_fertilizing.this.finish();
	}

	protected void okAudio() {
		playAudio(R.raw.ok);
	}
}