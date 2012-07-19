package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

public class action_sowing extends HelpEnabledActivityOld {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_sowing parentReference = this;
	private int sow_no;
	private int day_sow_int;
	private String sow_no_sel;
	private String day_sow_str;
	private String months_sow = "0";
	private String treatment_sow = "0";
	private String cropType_sow = "0";
	private String days_sel_sow = "0";
	private String units_sow = "0";
	private int seed_sow = 0;

	public static final String LOG_TAG = "action_sowing";

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		Intent adminintent = new Intent(action_sowing.this, Homescreen.class);

		startActivity(adminintent);
		action_sowing.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState, R.layout.sowing_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("plant done");
		final TextView day_sow = (TextView) findViewById(R.id.dlg_lbl_day_sow);
		// final TextView month_sow = (TextView)
		// findViewById(R.id.dlg_lbl_month_sow);

		playAudio(R.raw.thankyouclickingactionsowing);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.PAGE_VIEW, LOG_TAG);

		final ImageView bg_units_no_sow = (ImageView) findViewById(R.id.img_bg_units_no_sow);
		final ImageView bg_units_sow = (ImageView) findViewById(R.id.img_bg_units_sow);
		final ImageView bg_treatment_sow = (ImageView) findViewById(R.id.img_bg_treatment_sow);
		final ImageView bg_day_sow = (ImageView) findViewById(R.id.img_bg_day_sow);
		final ImageView bg_month_sow = (ImageView) findViewById(R.id.img_bg_month_sow);

		final View item1;
		final View item2;
		final View item3;
		final View item4;
		final View item5;
		final View item7;
		View home;
		View help;
		item1 = findViewById(R.id.home_btn_var_sow);
		item2 = findViewById(R.id.home_btn_units_sow);
		item3 = findViewById(R.id.home_btn_day_sow);
		item4 = findViewById(R.id.home_btn_treat_sow);
		item5 = findViewById(R.id.home_btn_units_no_sow);
		item7 = findViewById(R.id.home_btn_intercrop_sow);

		final Button item6 = (Button) findViewById(R.id.home_btn_month_sow);
		home = findViewById(R.id.aggr_img_home);
		help = findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final TableRow variety;
		final TableRow Amount;
		final TableRow Date;
		final TableRow Treatment;
		final TableRow Intercrop;

		variety = (TableRow) findViewById(R.id.seed_type_sow_tr);
		Amount = (TableRow) findViewById(R.id.units_sow_tr);
		Date = (TableRow) findViewById(R.id.day_sow_tr);
		Treatment = (TableRow) findViewById(R.id.treatment_sow_tr);
		Intercrop = (TableRow) findViewById(R.id.intercrop_sow_tr);

		variety.setOnLongClickListener(this);
		Amount.setOnLongClickListener(this);
		Date.setOnLongClickListener(this);
		Treatment.setOnLongClickListener(this);
		Intercrop.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.dialog_variety);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the Variety of seed sowed");
				Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "variety");

				final View variety1;
				final View variety2;
				final View variety3;
				final View variety4;
				final View variety5;
				final View variety6;

				final ImageView img_1 = (ImageView) findViewById(R.id.dlg_var_sow);
				final TextView var_text = (TextView) findViewById(R.id.dlg_var_text_sow);

				// gets the available varieties
				variety1 = dlg.findViewById(R.id.button_variety_1);
				variety2 = dlg.findViewById(R.id.button_variety_2);
				variety3 = dlg.findViewById(R.id.button_variety_3);
				variety4 = dlg.findViewById(R.id.button_variety_4);
				variety5 = dlg.findViewById(R.id.button_variety_5);
				variety6 = dlg.findViewById(R.id.button_variety_6);

				// sets the long click listener for help support
				variety1.setOnLongClickListener(parentReference);
				variety2.setOnLongClickListener(parentReference);
				variety3.setOnLongClickListener(parentReference);
				variety4.setOnLongClickListener(parentReference);
				variety5.setOnLongClickListener(parentReference);
				variety6.setOnLongClickListener(parentReference);

				variety1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						seed_sow = 1;

						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);

						dlg.cancel();
					}
				});

				variety2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Castor");
						seed_sow = 2;

						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);

						dlg.cancel();
					}
				});

				variety3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Cowpea");
						seed_sow = 3;

						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);

						dlg.cancel();
					}
				});

				variety4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_greengram_tiled);
						var_text.setText("Greengram");
						seed_sow = 4;

						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);

						dlg.cancel();
					}
				});
				variety5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_groundnut_tiled);
						var_text.setText("Groundnut");
						seed_sow = 5;

						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);

						dlg.cancel();
					}
				});
				variety6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Horsegram");
						seed_sow = 6;
						TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", seed_sow);
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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_unit_sow);
				unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
				unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
				unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);

				dlg.findViewById(R.id.home_btn_units_1).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.home_btn_units_2).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.home_btn_units_3).setOnLongClickListener(
						parentReference);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "units");

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

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units", units_sow);

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

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units", units_sow);

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

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units", units_sow);

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
						day_sow_int = mynpd.getValue();
						day_sow_str = String.valueOf(day_sow_int);
						day_sow.setText(day_sow_str);
						if (day_sow_int != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);
							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_day_sow.setImageResource(R.drawable.empty_not);

						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "units", "cancel");
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

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "treatment");

				final Button treat1;
				final Button treat2;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_treat_sow);
				treat1 = (Button) dlg.findViewById(R.id.home_treat_sow_1);
				treat2 = (Button) dlg.findViewById(R.id.home_treat_sow_2);

				dlg.findViewById(R.id.home_treat_sow_1).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.home_treat_sow_2).setOnLongClickListener(
						parentReference);

				treat1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");

						var_text.setText("Treated");
						treatment_sow = "treated";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "treatment",
								treatment_sow);

						dlg.cancel();
					}
				});

				treat2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");

						var_text.setText("May not Treat");
						treatment_sow = "may not treat";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "treatment",
								treatment_sow);

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

				playAudio(R.raw.noofbags);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "bags");

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				dlg.findViewById(R.id.number_ok).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.number_cancel).setOnLongClickListener(
						parentReference);

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

							// tracks the application usage.
							ApplicationTracker.getInstance().logEvent(
									EventType.CLICK, LOG_TAG, "bags",
									sow_no_sel);
						}

						dlg.cancel();
					}
				});
				no_cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						dlg.cancel();

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", "cancel");
					}
				});

			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in intercrop sow dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.treat_sow_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Main crop or intercrop?");
				Log.d("in intercrop sow dialog", "in dialog");
				dlg.show();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "intercrop");

				final Button treat1;
				final Button treat2;
				final TextView mainC;
				final TextView interC;

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_intercrop_sow);
				treat1 = (Button) dlg.findViewById(R.id.home_treat_sow_1);
				treat2 = (Button) dlg.findViewById(R.id.home_treat_sow_2);
				mainC = (TextView) dlg.findViewById(R.id.dlg_lbl_details);
				interC = (TextView) dlg.findViewById(R.id.dlg_lbl_details2);
				
				mainC.setText("Main crop");
				interC.setText("Intercrop");

				dlg.findViewById(R.id.home_treat_sow_1).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.home_treat_sow_2).setOnLongClickListener(
						parentReference);

				treat1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");

						var_text.setText("Main crop");
						cropType_sow = "main crop";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "intercrop",
								cropType_sow);

						dlg.cancel();
					}
				});

				treat2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");

						var_text.setText("Intercrop");
						cropType_sow = "intercrop";
						TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_treatment_sow.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "intercrop",
								cropType_sow);

						dlg.cancel();
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

		Button btnNext = (Button) findViewById(R.id.sow_ok);
		Button cancel = (Button) findViewById(R.id.sow_cancel);

		btnNext.setOnLongClickListener(this); // Integration
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "cancel");
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "ok");

				// Toast.makeText(action_sowing.this, "User enetred " +
				// sow_no_sel + "kgs", Toast.LENGTH_LONG).show();
				int flag1, flag2, flag3, flag4, flag5;
				if (seed_sow == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "variety");

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.seed_type_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (units_sow.toString().equalsIgnoreCase("0") || sow_no == 0) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "units");

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.units_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (treatment_sow.toString().equalsIgnoreCase("0")) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "treatment");

				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.treatment_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_sow.toString().equalsIgnoreCase("0")
						|| day_sow_int == 0) {

					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "day");

				} else {

					flag4 = 0;

					days_sel_sow = day_sow_int + "." + months_sow;
					TableRow tr_feedback = (TableRow) findViewById(R.id.day_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}
				
				if (cropType_sow.toString().equalsIgnoreCase("0")) {

					flag5 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.intercrop_sow_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

					// tracks the application usage.
					ApplicationTracker.getInstance().logEvent(EventType.ERROR,
							LOG_TAG, "intercrop");

				} else {

					flag5 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.intercrop_sow_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
					System.out.println("sowing writing");
					mDataProvider.setSowing(Global.plotId, sow_no, seed_sow,
							units_sow, days_sel_sow, treatment_sow, 0, 0, cropType_sow);

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

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");

			}
		});

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_var_sow) {

			playAudioalways(R.raw.varietyofseedssowd);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "variety");
		}

		if (v.getId() == R.id.home_btn_units_sow
				|| v.getId() == R.id.home_btn_units_no_sow) {

			playAudioalways(R.raw.selecttheunits);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "units");
		}

		if (v.getId() == R.id.home_btn_day_sow) {

			playAudioalways(R.raw.selectthedate);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "day");
		}

		if (v.getId() == R.id.home_btn_treat_sow) {

			playAudioalways(R.raw.treatmenttoseeds1);
			ShowHelpIcon(v);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "treatment");
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

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
					LOG_TAG, "help");
		}

		if (v.getId() == R.id.button_variety_1) {

			System.out.println("variety sow1 called");
			playAudioalways(R.raw.bajra);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_2) {

			playAudioalways(R.raw.castor);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_3) {

			playAudioalways(R.raw.cowpea);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_4) {

			playAudioalways(R.raw.greengram);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_5) {

			playAudioalways(R.raw.groundnut1);
			ShowHelpIcon(v);

		}

		if (v.getId() == R.id.button_variety_6) {

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

		if (v.getId() == R.id.seed_type_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.variety);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.units_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.amount);
			ShowHelpIcon(v);
		}

		/*
		 * if (v.getId() == R.id.variety_pest_txt_btn) { //20-06-2012
		 * playAudio(R.raw.pesticidename); ShowHelpIcon(v); }
		 */

		if (v.getId() == R.id.treatment_sow_tr) { // 20-06-2012
			playAudioalways(R.raw.treatment);
			ShowHelpIcon(v);
		}
		
		if (v.getId() == R.id.intercrop_sow_tr || v.getId() == R.id.home_btn_intercrop_sow) { // 20-06-2012 + added
			playAudioalways(R.raw.intercrop);
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

		if (v.getId() == R.id.home_btn_month_sow) { // added

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

		if (v.getId() == R.id.day_sow_tr) { // added

			playAudioalways(R.raw.date);
			ShowHelpIcon(v); // added for help icon
		}

		return true;
	}
}