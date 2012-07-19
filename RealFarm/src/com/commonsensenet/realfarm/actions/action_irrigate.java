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
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class action_irrigate extends HelpEnabledActivityOld {
	public static final String LOG_TAG = "action_irrigate";
	private Context context = this;
	private String fert_no_sel;
	private int hrs_irrigate = 0;
	private String hrs_irrigate_sel = "0", irr_method_sel = "0", irr_day_sel;
	private int irr_day_int;
	private RealFarmProvider mDataProvider;
	private String months_irr = "0", irr_day_str;

	private final action_irrigate parentReference = this;

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_irrigate.this, Homescreen.class);

		startActivity(adminintent);
		action_irrigate.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_irrigate.this, Homescreen.class);

		startActivity(adminintent);
		action_irrigate.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState, R.layout.irrigate_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");
		final TextView day_irr = (TextView) findViewById(R.id.dlg_lbl_day_irr);

		playAudio(R.raw.clickingfertilising);

		final ImageView bg_method_irr = (ImageView) findViewById(R.id.img_bg_method_irr);
		final ImageView bg_hrs_irr = (ImageView) findViewById(R.id.img_bg_hrs_irr);
		final ImageView bg_day_irr = (ImageView) findViewById(R.id.img_bg_day_irr);
		final ImageView bg_month_irr = (ImageView) findViewById(R.id.img_bg_month_irr);

		// bg_day_irr.setImageResource(R.drawable.empty_not);
		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_method_irr);

		item3 = (Button) findViewById(R.id.home_btn_day_irr);
		item2 = (Button) findViewById(R.id.home_btn_units_no_irr);
		item4 = (Button) findViewById(R.id.home_btn_month_irr);
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);

		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final TableRow method;
		final TableRow duration;
		final TableRow Date;

		method = (TableRow) findViewById(R.id.method_irr_tr);
		duration = (TableRow) findViewById(R.id.units_irr_tr);
		Date = (TableRow) findViewById(R.id.day_irr_tr);

		method.setOnLongClickListener(this);
		duration.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in irrigation method dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.method_irrigate_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the irrigation method");
				Log.d("in irrigation method", "in dialog");
				dlg.show();
				
				final Button meth1;
				final Button meth2;
				//final Button meth3;

				// final Button variety7;
				// final ImageView img_1 = img_1 = (ImageView)
				// findViewById(R.id.dlg_lbl_method_irr);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_method_irr);
				meth1 = (Button) dlg.findViewById(R.id.home_var_fert_1);
				meth2 = (Button) dlg.findViewById(R.id.home_var_fert_2);
				//meth3 = (Button) dlg.findViewById(R.id.home_var_fert_3);

				((Button) dlg.findViewById(R.id.home_var_fert_1))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_var_fert_2))
						.setOnLongClickListener(parentReference);
				// ((Button) dlg.findViewById(R.id.home_var_fert_3)).setOnLongClickListener(parentReference);

				meth1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Sprinkling");
						irr_method_sel = "Sprinkling";
						TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_method_irr.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});
				
				meth2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Flooding");
						irr_method_sel = "Flooding";
						TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_method_irr.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
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
						irr_day_int = mynp1.getValue();
						irr_day_str = String.valueOf(irr_day_int);
						day_irr.setText(irr_day_str);
						if (irr_day_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);

							bg_day_irr.setImageResource(R.drawable.empty_not);

							// tracks the application usage.
							// ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,"no_units_fertilizer", fert_no_sel);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG,
								"no_units_fertilizer", "cancel");
						dlg.cancel();
					}
				});
			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_irr);

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Number of bags");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.noofbags);

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
						hrs_irrigate = mynp1.getValue();
						hrs_irrigate_sel = String.valueOf(hrs_irrigate);
						no_text.setText(hrs_irrigate_sel);
						if (hrs_irrigate != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_hrs_irr.setImageResource(R.drawable.empty_not);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_irr);

				month1.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						var_text.setText("01");
						months_irr = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						var_text.setText("02");
						months_irr = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("03");
						months_irr = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("04");
						months_irr = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("05");
						months_irr = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("06");
						months_irr = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("07");
						months_irr = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("08");
						months_irr = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("09");
						months_irr = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("10");
						months_irr = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("11");
						months_irr = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_irr.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("12");
						months_irr = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_month_irr.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});

		Button btnNext = (Button) findViewById(R.id.irr_ok);
		Button cancel = (Button) findViewById(R.id.irr_cancel);

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
				int flag1, flag2, flag3;
				if (hrs_irrigate == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (irr_method_sel.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_irr.toString().equalsIgnoreCase("0")
						|| irr_day_int == 0) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag3 = 0;

					irr_day_sel = irr_day_int + "." + months_irr;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_irr_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {
					System.out.println("Irrigting Writing");
					mDataProvider.setIrrigation(Global.plotId, hrs_irrigate,
							"hours", irr_day_sel, irr_method_sel, 0, 0);

					System.out.println("Irrigting reading");
					mDataProvider.getharvesting();

					Intent adminintent = new Intent(action_irrigate.this,
							Homescreen.class);

					startActivity(adminintent);
					action_irrigate.this.finish();
					okaudio();

				} else
					initmissingval();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_irrigate.this,
						Homescreen.class);

				startActivity(adminintent);
				action_irrigate.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_method_irr) {
			playAudioalways(R.raw.method);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_no_irr) {
			playAudioalways(R.raw.noofhours);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_day_irr) {
			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.irr_ok) {
			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.irr_cancel) {
			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudioalways(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_var_fert_1) {
			playAudioalways(R.raw.method1);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_var_fert_2) {
			playAudioalways(R.raw.method2);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.home_var_fert_3) {
			playAudioalways(R.raw.method3);
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

		if (v.getId() == R.id.home_btn_month_irr) {
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

		if (v.getId() == R.id.method_irr_tr) {
			playAudioalways(R.raw.method);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_irr_tr) {
			playAudioalways(R.raw.duration);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.day_irr_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		return true;
	}
}