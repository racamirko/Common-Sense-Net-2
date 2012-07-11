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

public class action_problem extends HelpEnabledActivity {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_problem parentReference = this;
	private String prob_var_sel = "0", prob_day_sel, months_prob = "0",
			prob_day_str;
	int prob_day_int;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_problem.this, Homescreen.class);

		startActivity(adminintent);
		action_problem.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_problem.this, Homescreen.class);

		startActivity(adminintent);
		action_problem.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.problem_dialog);
		// System.out.println("problem done");
		super.onCreate(savedInstanceState, R.layout.problem_dialog); // Needed
																		// to
																		// add
																		// help
																		// icon
		setHelpIcon(findViewById(R.id.helpIndicator));

		final TextView day_prob = (TextView) findViewById(R.id.dlg_lbl_day_prob);
		// final TextView month_prob = (TextView)
		// findViewById(R.id.dlg_lbl_month_prob);

		playAudio(R.raw.clickingfertilising);

		final ImageView bg_type_problem = (ImageView) findViewById(R.id.img_bg_type_prob);
		final ImageView bg_date_problem = (ImageView) findViewById(R.id.img_bg_day_prob);
		final ImageView bg_month_prob = (ImageView) findViewById(R.id.img_bg_month_prob);
		// bg_date_problem.setImageResource(R.drawable.empty_not);

		final Button item1;

		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_var_prob);
		final Button item2 = (Button) findViewById(R.id.home_btn_day_prob);
		final Button item3 = (Button) findViewById(R.id.home_btn_month_prob);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this); // Integration
		item2.setOnLongClickListener(this); // Integration
		item3.setOnLongClickListener(this); // Integration

		help.setOnLongClickListener(this);

		final Button problem; // 20-06-2012
		final Button Date;

		problem = (Button) findViewById(R.id.variety_sow_txt_btn); // 20-06-2012
		Date = (Button) findViewById(R.id.date_sow_txt_btn);

		problem.setOnLongClickListener(this); // 20-06-2012
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.prob_spraying_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final Button fert1;
				final Button fert2;
				final Button fert3;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_var_prob);
				fert1 = (Button) dlg.findViewById(R.id.home_prob_spray_1);
				fert2 = (Button) dlg.findViewById(R.id.home_prob_spray_2);
				fert3 = (Button) dlg.findViewById(R.id.home_prob_spray_3);

				((Button) dlg.findViewById(R.id.home_prob_spray_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_prob_spray_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_prob_spray_3))
						.setOnLongClickListener(parentReference);

				fert1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Problem 1");
						prob_var_sel = "Problem 1";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_type_problem.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				fert2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Problem 2");
						prob_var_sel = "Problem 2";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_type_problem.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

				fert3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Problem 3");
						prob_var_sel = "Problem 3";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_type_problem.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Date");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.dateinfo); // 20-06-2012

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider.File_Log_Create("UIlog.txt", " Fertilizing "
							+ " selection " + " click "
							+ " no_units_fertilizer " + " null " + " \r\n");
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
						prob_day_int = mynp1.getValue();
						prob_day_str = String.valueOf(prob_day_int);
						day_prob.setText(prob_day_str);
						if (prob_day_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);

							bg_date_problem
									.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");
							mDataProvider.File_Log_Create("UIlog.txt",
									" Fertilizing " + " number_picker "
											+ " click " + " cancel_no_units "
											+ "cancelnumberentry" + " \r\n");

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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_prob);

				month1.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("01");
						months_prob = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("02");
						months_prob = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("03");
						months_prob = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("04");
						months_prob = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("05");
						months_prob = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("06");
						months_prob = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("07");
						months_prob = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("08");
						months_prob = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("09");
						months_prob = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("10");
						months_prob = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("11");
						months_prob = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("12");
						months_prob = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_prob.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});

		Button btnNext = (Button) findViewById(R.id.prob_ok);
		Button cancel = (Button) findViewById(R.id.prob_cancel);

		btnNext.setOnLongClickListener(this); // Integration
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// Toast.makeText(action_fertilizing.this, "User enetred " +
				// fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1;
				if (prob_var_sel.toString().equalsIgnoreCase("0")) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_prob.toString().equalsIgnoreCase("0")
						|| prob_day_int == 0) {

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					prob_day_sel = prob_day_int + "." + months_prob;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0) {

					System.out.println("Problem writing");
					mDataProvider.setProblem(Global.userId, Global.plotId, prob_day_sel, prob_var_sel, 0, 0);

					// mDataProvider.setProblem(String day,String probType, int
					// sent, int admin);

					System.out.println("Problem reading");
					mDataProvider.getProblem();

					Intent adminintent = new Intent(action_problem.this,
							Homescreen.class);

					startActivity(adminintent);
					action_problem.this.finish();
					okaudio();

				} else {
					initmissingval();
				}
			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_problem.this,
						Homescreen.class);

				startActivity(adminintent);
				action_problem.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_var_prob) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_day_prob) {
			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.prob_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.prob_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_1) { // audio integration
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
			playAudioalways(R.raw.today);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_day_5) { // added
			playAudioalways(R.raw.tomorrows);
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

		if (v.getId() == R.id.home_btn_month_prob) { // added

			playAudioalways(R.raw.choosethemonth);
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
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.date_sow_txt_btn) { // 20-06-2012
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		return true;
	}
}