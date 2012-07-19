package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
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

public class action_fertilizing extends HelpEnabledActivityOld implements
		OnLongClickListener {
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** Reference to the current instance. */
	private final action_fertilizing mParentReference = this;
	private String units_fert = "0", fert_var_sel = "0", day_fert_sel = "0",
			day_fert_sel_1;
	private int fert_no, day_fert_int;
	private String fert_no_sel, months_fert = "0";

	public static final String LOG_TAG = "action_fertilizing";

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		startActivity(new Intent(action_fertilizing.this, Homescreen.class));
		action_fertilizing.this.finish();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");
		mDataProvider = RealFarmProvider.getInstance(this);

		super.onCreate(savedInstanceState, R.layout.fertilizing_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");

		final TextView day_fert = (TextView) findViewById(R.id.dlg_lbl_day_fert);
		final ImageView bg_day_fert = (ImageView) findViewById(R.id.img_bg_day_fert);
		final ImageView bg_units_no_fert = (ImageView) findViewById(R.id.img_bg_units_no_fert);
		final ImageView bg_units_fert = (ImageView) findViewById(R.id.img_bg_units_fert);
		final ImageView bg_var_fert = (ImageView) findViewById(R.id.img_bg_var_fert);
		final ImageView bg_month_fert = (ImageView) findViewById(R.id.img_bg_month_fert);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		playAudio(R.raw.clickingfertilising);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final Button item1;
		final Button item2;
		final Button item3;
		final Button item4;
		final Button item5;
		item1 = (Button) findViewById(R.id.home_btn_var_fert);
		item2 = (Button) findViewById(R.id.home_btn_units_fert);
		item3 = (Button) findViewById(R.id.home_btn_day_fert);
		item4 = (Button) findViewById(R.id.home_btn_units_no_fert);
		item5 = (Button) findViewById(R.id.home_btn_month_fert);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);

		final TableRow fertilizerName;
		final TableRow amount;
		final TableRow date;

		fertilizerName = (TableRow) findViewById(R.id.var_fert_tr);
		amount = (TableRow) findViewById(R.id.units_fert_tr);
		date = (TableRow) findViewById(R.id.day_fert_tr);

		amount.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		fertilizerName.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.variety_fert_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "type_fertilizer");

				final Button fert1;
				final Button fert2;
				final Button fert3;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_var_fert);
				fert1 = (Button) dlg.findViewById(R.id.home_var_fert_1);
				fert2 = (Button) dlg.findViewById(R.id.home_var_fert_2);
				fert3 = (Button) dlg.findViewById(R.id.home_var_fert_3);

				((Button) dlg.findViewById(R.id.home_var_fert_1))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_var_fert_2))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_var_fert_3))
						.setOnLongClickListener(mParentReference);

				fert1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");

						var_text.setText("DAP");
						fert_var_sel = "DAP";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "in_popup",
								"fertilizer1");

						dlg.cancel();
					}
				});

				fert2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						var_text.setText("FYM");
						fert_var_sel = "FYM";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "in_popup",
								"fertilizer2");

						dlg.cancel();
					}
				});

				fert3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Complex");
						fert_var_sel = "Complex";
						TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_var_fert.setImageResource(R.drawable.empty_not);
						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "in_popup",
								"fertilizer3");
						dlg.cancel();
					}
				});

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in units fert dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.units_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the units");
				Log.d("in units fert dialog", "in dialog");
				dlg.show();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "units_fertilizer");

				final Button unit1;
				final Button unit2;
				final Button unit3;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_fert);
				unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
				unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
				unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_btn_units_2))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_btn_units_3))
						.setOnLongClickListener(mParentReference);

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						var_text.setText("10 Kgs");
						units_fert = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units_fertilizer",
								units_fert);

						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("20 Kgs");
						units_fert = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units_fertilizer",
								units_fert);

						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("50 Kgs");
						units_fert = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);

						bg_units_fert.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units_fertilizer",
								units_fert);

						dlg.cancel();
					}
				});
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);

				// To change the default increments on the number picker.
				((NumberPicker) dlg.findViewById(R.id.numberpick))
						.setIncrementValue(1);

				dlg.setCancelable(true);
				dlg.setTitle("Choose the Date");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.dateinfo);

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				dlg.findViewById(R.id.number_ok).setOnLongClickListener(
						mParentReference);
				dlg.findViewById(R.id.number_cancel).setOnLongClickListener(
						mParentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg
								.findViewById(R.id.numberpick);

						day_fert_int = mynpd.getValue();
						day_fert_sel_1 = String.valueOf(day_fert_int);
						day_fert.setText(day_fert_sel_1);
						if (day_fert_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_day_fert.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
					}
				});

				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags for sowing",
								"cancel");
					}
				});
			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_fert);

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Number of bags");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				playAudio(R.raw.noofbags);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "no_units_fertilizer");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				dlg.findViewById(R.id.number_ok).setOnLongClickListener(
						mParentReference);
				dlg.findViewById(R.id.number_cancel).setOnLongClickListener(
						mParentReference);

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

							// tracks the application usage.
							ApplicationTracker.getInstance().logEvent(
									EventType.CLICK, LOG_TAG, "number_picker",
									"no_units_fertilizer", fert_no_sel);
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

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAudio();
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
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_2))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_3))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_4))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_5))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_6))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_7))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_8))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_9))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_10))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_11))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.home_month_12))
						.setOnLongClickListener(mParentReference);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_fert);

				month1.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						var_text.setText("01");
						months_fert = "01";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("02");
						months_fert = "02";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("03");
						months_fert = "03";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("04");
						months_fert = "04";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("05");
						months_fert = "05";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("06");
						months_fert = "06";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("07");
						months_fert = "07";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("08");
						months_fert = "08";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("09");
						months_fert = "09";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("10");
						months_fert = "10";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("11");
						months_fert = "11";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						var_text.setText("12");
						months_fert = "12";
						TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_fert.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});

		Button btnNext = (Button) findViewById(R.id.button_ok);
		Button cancel = (Button) findViewById(R.id.button_cancel);

		btnNext.setOnLongClickListener(this);
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "cancel");
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok_btn");

				int flag1, flag2, flag3;
				if (units_fert.toString().equalsIgnoreCase("0") || fert_no == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units_fertilizer");

				} else {
					flag1 = 0;
					TableRow tr_feedback = (TableRow) findViewById(R.id.units_fert_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (fert_var_sel.toString().equalsIgnoreCase("0")) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "type_fertilizer");

				} else {

					flag2 = 0;
					TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_fert.toString().equalsIgnoreCase("0")
						|| day_fert_int == 0) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_fert_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "type_fertilizer");

				} else {

					flag3 = 0;
					day_fert_sel = day_fert_sel_1 + "." + months_fert;
					TableRow tr_feedback = (TableRow) findViewById(R.id.var_fert_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0) {

					System.out.println("fertilizing writing");
					long result = mDataProvider.setFertilizing(Global.plotId, fert_no,
							fert_var_sel, units_fert, day_fert_sel, 1, 0);
					
					//System.out.println("fertilizing reading");
					//mDataProvider.getfertizing();

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.CLICK,
							LOG_TAG, "ok_button");

					startActivity(new Intent(action_fertilizing.this,
							Homescreen.class));
					action_fertilizing.this.finish();
					okAudio();

				} else {
					initmissingval();
				}
			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_fertilizing.this,
						Homescreen.class);

				startActivity(adminintent);
				action_fertilizing.this.finish();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");
			}
		});

	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.home_btn_var_fert) {

			playAudioalways(R.raw.selecttypeoffertilizer);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "type_fertilizer");

		}

		if (v.getId() == R.id.home_btn_units_fert) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.home_btn_units_no_fert) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units_fertilizer");
		}

		if (v.getId() == R.id.home_btn_day_fert) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day_fertilizer");
		}

		if (v.getId() == R.id.button_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.button_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudioalways(R.raw.help);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help_fertilizer");
		}

		if (v.getId() == R.id.home_var_fert_1) {
			playAudioalways(R.raw.fertilizer1);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_var_fert_2) {
			playAudioalways(R.raw.fertilizer2);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_var_fert_3) {
			playAudioalways(R.raw.fertilizer3);
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

		if (v.getId() == R.id.var_fert_tr) {
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.day_fert_tr) {
			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_fert_tr) {
			playAudioalways(R.raw.fertilizername);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_month_fert) {
			playAudioalways(R.raw.choosethemonth);
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

		if (v.getId() == R.id.home_month_6) { // added

			playAudioalways(R.raw.jun);
			ShowHelpIcon(v);
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