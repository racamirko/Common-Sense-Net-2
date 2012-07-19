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
import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_problem extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_problem parentReference = this;
	private String prob_var_sel = "0", prob_crop_sel = "0", prob_day_sel, months_prob = "0",
			prob_day_str;
	private int prob_day_int;

	public static final String LOG_TAG = "action_problem";

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
		super.onCreate(savedInstanceState, R.layout.problem_dialog);
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
		final Button item4;

		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_var_prob);
		item4 = (Button) findViewById(R.id.home_btn_var_prob4);
		final Button item2 = (Button) findViewById(R.id.home_btn_day_prob);
		final Button item3 = (Button) findViewById(R.id.home_btn_month_prob);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		final TableRow problem;
		final TableRow crop;
		final TableRow Date;

		problem = (TableRow) findViewById(R.id.var_prob_tr);
		crop = (TableRow) findViewById(R.id.var_prob_tr4);
		Date = (TableRow) findViewById(R.id.day_prob_tr);

		problem.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);
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
						.setOnLongClickListener(parentReference);
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
				dlg.setTitle("Choose the day");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();
				
				playAudio(R.raw.dateinfo);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "no_units_fertilizer");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				((Button) dlg.findViewById(R.id.number_ok))
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

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "number_picker",
								"cancel_no_units", "cancelnumberentry");

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
						.setOnLongClickListener(parentReference);
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
						.setOnLongClickListener(parentReference);
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
		
		item4.setOnClickListener(new View.OnClickListener() {
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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_var_prob4);
				fert1 = (Button) dlg.findViewById(R.id.home_prob_spray_1);
				fert2 = (Button) dlg.findViewById(R.id.home_prob_spray_2);
				fert3 = (Button) dlg.findViewById(R.id.home_prob_spray_3);

				((Button) dlg.findViewById(R.id.home_prob_spray_1))
						.setOnLongClickListener(parentReference);
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
						prob_crop_sel = "Problem 1";
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
						prob_crop_sel = "Problem 2";
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
						prob_crop_sel = "Problem 3";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_type_problem.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}
		});

		Button btnNext = (Button) findViewById(R.id.prob_ok);
		Button cancel = (Button) findViewById(R.id.prob_cancel);

		btnNext.setOnLongClickListener(this);
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
				int flag1, flag2, flag4;
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
					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag2 = 0;

					prob_day_sel = prob_day_int + "." + months_prob;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_prob_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				if (prob_crop_sel.toString().equalsIgnoreCase("0")) {
					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag4 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr4);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag4 == 0) {

					System.out.println("Problem writing");
					mDataProvider.setProblem(Global.plotId, prob_day_sel,
							prob_var_sel, 0, 0, prob_crop_sel);

					startActivity(new Intent(action_problem.this,
							Homescreen.class));
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
		playAudioalways(R.raw.date);
		if (v.getId() == R.id.home_btn_var_prob) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.home_btn_var_prob4) {
			// playAudioalways(R.raw.problems); // TODO: put the audio
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

		if (v.getId() == R.id.home_prob_spray_1) {
			playAudioalways(R.raw.problem1);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_2) {
			playAudioalways(R.raw.problem2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_prob_spray_3) {
			playAudioalways(R.raw.problem3);
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
			playAudioalways(R.raw.today);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_day_5) {
			playAudioalways(R.raw.tomorrows);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_1) {

			playAudioalways(R.raw.jan);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.home_month_2) {

			playAudioalways(R.raw.feb);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_3) {

			playAudioalways(R.raw.mar);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_4) {

			playAudioalways(R.raw.apr);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_month_5) {

			playAudioalways(R.raw.may);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_6) {

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_7) {

			playAudioalways(R.raw.jul);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_8) {

			playAudioalways(R.raw.aug);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_9) {

			playAudioalways(R.raw.sep);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_10) {

			playAudioalways(R.raw.oct);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_11) {

			playAudioalways(R.raw.nov);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_month_12) {

			playAudioalways(R.raw.dec);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_month_prob) {

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.number_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.number_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.var_prob_tr) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.var_prob_tr4) {
			playAudioalways(R.raw.problems);
			ShowHelpIcon(v);
		}


		if (v.getId() == R.id.day_prob_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}
		
		return true;
	}
}