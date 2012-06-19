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

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_irrigate extends HelpEnabledActivity {
	private Context context = this;
	private int hrs_irrigate = 0;
	private String hrs_irrigate_sel = "0", irr_method_sel = "0", irr_day_sel;
	private RealFarmProvider mDataProvider;
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
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.irrigate_dialog);
		System.out.println("plant done");
		final TextView day_irr = (TextView) findViewById(R.id.dlg_lbl_day_irr);
		day_irr.setText("Today");
		irr_day_sel = "Today";

		playAudio(R.raw.clickingfertilising);

		final ImageView bg_method_irr = (ImageView) findViewById(R.id.img_bg_method_irr);
		final ImageView bg_hrs_irr = (ImageView) findViewById(R.id.img_bg_hrs_irr);
		final ImageView bg_day_irr = (ImageView) findViewById(R.id.img_bg_day_irr);

		bg_day_irr.setImageResource(R.drawable.empty_not);
		final Button item1;
		final Button item2;
		final Button item3;

		ImageButton home;
		ImageButton help;
		item1 = (Button) findViewById(R.id.home_btn_method_irr);

		item3 = (Button) findViewById(R.id.home_btn_day_irr);
		item2 = (Button) findViewById(R.id.home_btn_units_no_irr);

		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this); // Integration

		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in method irrigate dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.method_irrigate_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final Button meth1;
				// final Button meth2;
				final Button meth3;

				// final Button variety7;
				// final ImageView img_1 = img_1 = (ImageView)
				// findViewById(R.id.dlg_lbl_method_irr);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_method_irr);
				meth1 = (Button) dlg.findViewById(R.id.home_var_fert_1);
				// meth2 = (Button) dlg.findViewById(R.id.home_var_fert_2);
				meth3 = (Button) dlg.findViewById(R.id.home_var_fert_3);

				((Button) dlg.findViewById(R.id.home_var_fert_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_var_fert_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_var_fert_3))
						.setOnLongClickListener(parentReference);

				meth1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Method 1");
						irr_method_sel = "Method 1";
						TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_method_irr.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				meth1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Method 2");
						irr_method_sel = "Method 2";
						TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_method_irr.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				meth3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Method 3");
						irr_method_sel = "Method 3";
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

				day1 = (Button) dlg.findViewById(R.id.home_day_1);
				day2 = (Button) dlg.findViewById(R.id.home_day_2);
				day3 = (Button) dlg.findViewById(R.id.home_day_3);
				day4 = (Button) dlg.findViewById(R.id.home_day_4);
				day5 = (Button) dlg.findViewById(R.id.home_day_5);

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

				day1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						day_irr.setText("Two week before");
						irr_day_sel = "Two week before";
						bg_day_irr.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						dlg.cancel();
					}
				});

				day2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						day_irr.setText("One week before");
						irr_day_sel = "One week before";
						bg_day_irr.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

				day3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_irr.setText("Yesterday");
						irr_day_sel = "Yesterday";
						bg_day_irr.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});
				day4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_irr.setText("Today");
						irr_day_sel = "Today";
						bg_day_irr.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});
				day5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						day_irr.setText("Tomorrow");
						irr_day_sel = "Tomorrow";
						bg_day_irr.setImageResource(R.drawable.empty_not);
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

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
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

		Button btnNext = (Button) findViewById(R.id.irr_ok);
		Button cancel = (Button) findViewById(R.id.irr_cancel);

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
				int flag1, flag2;
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

				if (flag1 == 0 && flag2 == 0) {
					System.out.println("Irrigting Writing");
					mDataProvider.setIrrigation(hrs_irrigate, "hours",
							irr_day_sel, 0, 0, irr_method_sel);

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
			playAudio(R.raw.method);
		}

		if (v.getId() == R.id.home_btn_units_no_irr) {
			playAudio(R.raw.noofhours);
		}

		if (v.getId() == R.id.home_btn_day_irr) {
			playAudio(R.raw.selectthedate);
		}

		if (v.getId() == R.id.irr_ok) {
			playAudio(R.raw.ok);
		}

		if (v.getId() == R.id.irr_cancel) {
			playAudio(R.raw.cancel);
		}

		if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help);
		}

		if (v.getId() == R.id.home_var_fert_1) { // audio integration
			playAudio(R.raw.fertilizer1);

		}

		if (v.getId() == R.id.home_var_fert_2) { // added
			playAudio(R.raw.fertilizer2);

		}

		if (v.getId() == R.id.home_var_fert_3) { // added
			playAudio(R.raw.fertilizer3);
		}

		if (v.getId() == R.id.home_day_1) { // added
			playAudio(R.raw.twoweeksbefore);
		}

		if (v.getId() == R.id.home_day_2) { // added
			playAudio(R.raw.oneweekbefore);
		}

		if (v.getId() == R.id.home_day_3) { // added
			playAudio(R.raw.yesterday);
		}

		if (v.getId() == R.id.home_day_4) { // added
			playAudio(R.raw.todayonly);
		}

		if (v.getId() == R.id.home_day_5) { // added
			playAudio(R.raw.tomorrows);
		}

		return true;
	}
}