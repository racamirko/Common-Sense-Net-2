package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_selling extends HelpEnabledActivityOld {

	private final action_selling parentReference = this;
	private int sell_no;
	private String sell_no_sel, units_sell = "0";
	private String crop_sell = "0";
	private int date_sel;
	private String date_sel_str = "0";
	private String months_harvest = "0";
	private int sell_price;
	private String sell_price_sel = "0";
	private int sell_no_rem;
	private String units_rem_sell = "0";
	private String sell_no_sel_rem = "0";

	public static final String LOG_TAG = "action_selling";

	protected void cancelaudio() {

		playAudio(R.raw.cancel);

		Intent adminintent = new Intent(action_selling.this, Homescreen.class);

		startActivity(adminintent);
		action_selling.this.finish();
	}

	public void onBackPressed() {

		SoundQueue.getInstance().stop();

		Intent adminintent = new Intent(action_selling.this, Homescreen.class);

		startActivity(adminintent);
		action_selling.this.finish();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.selling_dialog);
		setHelpIcon(findViewById(R.id.helpIndicator));

		System.out.println("selling done");

		playAudio(R.raw.clickingselling);

		// final ImageView bg_crop_sell = (ImageView)
		// findViewById(R.id.img_bg_units_no_sow);
		final ImageView bg_date_sell = (ImageView) findViewById(R.id.img_bg_date_sell);
		final ImageView bg_month_sell = (ImageView) findViewById(R.id.img_bg_month_sell);
		final ImageView bg_units_sell = (ImageView) findViewById(R.id.img_bg_units_sell);
		final ImageView bg_units_no_sell = (ImageView) findViewById(R.id.img_bg_units_no_sell);
		final ImageView bg_price_sell = (ImageView) findViewById(R.id.img_bg_price_sell);
		final ImageView bg_units_no_rem_sell = (ImageView) findViewById(R.id.img_bg_units_no_rem_sell);
		final ImageView bg_units_rem_sell = (ImageView) findViewById(R.id.img_bg_units_rem_sell);

		// bg_day_sow.setImageResource(R.drawable.empty_not);

		final Button item1 = (Button) findViewById(R.id.home_btn_crop_sell);
		final Button item2 = (Button) findViewById(R.id.home_btn_date_sell);
		final Button item3 = (Button) findViewById(R.id.home_btn_month_sell);
		final Button item4 = (Button) findViewById(R.id.home_btn_units_no_sell);
		final Button item5 = (Button) findViewById(R.id.home_btn_units_sell);
		final Button item6 = (Button) findViewById(R.id.home_btn_price_sell);
		final Button item7 = (Button) findViewById(R.id.home_btn_units_no_rem_sell);
		final Button item8 = (Button) findViewById(R.id.home_btn_units_rem_sell);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);

		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		item8.setOnLongClickListener(this);
		help.setOnLongClickListener(this);

		final Button crop;
		final Button date;
		final Button quantity;
		final Button priceperquint;
		final Button remain;

		crop = (Button) findViewById(R.id.crop_sell_txt_btn);
		date = (Button) findViewById(R.id.amount_sow_txt_btn);

		quantity = (Button) findViewById(R.id.quantity_sow_txt_btn);
		priceperquint = (Button) findViewById(R.id.treat_sow_txt_btn);
		remain = (Button) findViewById(R.id.remain_sow_txt_btn);

		crop.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		quantity.setOnLongClickListener(this);
		priceperquint.setOnLongClickListener(this);
		remain.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.dialog_variety);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the crop ");

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

				final ImageView img_1 = (ImageView) findViewById(R.id.img_bg_crop_sell);
				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_crop_sell);

				// gets the available varieties.
				variety1 = dlg.findViewById(R.id.button_variety_1);
				variety2 = dlg.findViewById(R.id.button_variety_2);
				variety3 = dlg.findViewById(R.id.button_variety_3);
				variety4 = dlg.findViewById(R.id.button_variety_4);
				variety5 = dlg.findViewById(R.id.button_variety_5);
				variety6 = dlg.findViewById(R.id.button_variety_6);

				// sets the long click listener to provide help support.
				variety1.setOnLongClickListener(parentReference);
				variety2.setOnLongClickListener(parentReference);
				variety3.setOnLongClickListener(parentReference);
				variety4.setOnLongClickListener(parentReference);
				variety5.setOnLongClickListener(parentReference);
				variety6.setOnLongClickListener(parentReference);

				variety1.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");

						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						crop_sell = "Bajra";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);

						dlg.cancel();
					}
				});

				variety2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Castor");
						crop_sell = "Castor";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);

						dlg.cancel();
					}
				});

				variety3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Cowpea");
						crop_sell = "Cowpea";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);

						dlg.cancel();
					}
				});

				variety4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_greengram_tiled);
						var_text.setText("Greengram");
						crop_sell = "Greengram";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);
						dlg.cancel();
					}
				});
				variety5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_groundnut_tiled);
						var_text.setText("Groundnut");
						crop_sell = "Groundnut";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);
						dlg.cancel();
					}
				});
				variety6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_90px_horsegram_tiled);
						var_text.setText("Horsegram");
						crop_sell = "Horsegram";

						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);
						tr_feedback.setBackgroundResource(R.drawable.def_img);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "variety", crop_sell);
						dlg.cancel();
					}
				});

			}
		});

		final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_date_sell);

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

				playAudio(R.raw.dateinfo);

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);

				dlg.findViewById(R.id.number_ok).setOnLongClickListener(
						parentReference);
				dlg.findViewById(R.id.number_cancel).setOnLongClickListener(
						parentReference);

				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						date_sel = mynpd.getValue();
						date_sel_str = String.valueOf(date_sel);
						no_text.setText(date_sel_str);
						if (date_sel != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_date_sell.setImageResource(R.drawable.empty_not);

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

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_month_sell);

				month1.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("January");
						months_harvest = "January";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("February");
						months_harvest = "February";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("March");
						months_harvest = "March";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("April");
						months_harvest = "April";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("May");
						months_harvest = "May";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("June");
						months_harvest = "June";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month7.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("July");
						months_harvest = "July";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month8.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("August");
						months_harvest = "August";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month9.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("September");
						months_harvest = "September";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month10.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("October");
						months_harvest = "October";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month11.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("November");
						months_harvest = "November";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);

						dlg.cancel();
					}
				});

				month12.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("December");
						months_harvest = "December";
						TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_month_sell.setImageResource(R.drawable.empty_not);
						dlg.cancel();
					}
				});

			}

		});

		final TextView no_text1 = (TextView) findViewById(R.id.dlg_lbl_unit_no_sell);

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

				playAudio(R.raw.noofbags); // 20-06-2012

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
						sell_no = mynp1.getValue();
						sell_no_sel = String.valueOf(sell_no);
						no_text1.setText(sell_no_sel);
						if (sell_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_sell
									.setImageResource(R.drawable.empty_not);

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

		item5.setOnClickListener(new View.OnClickListener() {
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

				// final ImageView img_1 = (ImageView)
				// findViewById(R.id.dlg_unit_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_unit_sell);
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

						var_text.setText("10 Kgs");
						units_sell = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);

						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");

						var_text.setText("Bag of 20 Kgs");
						units_sell = "20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");

						var_text.setText("50 Kgs");
						units_sell = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);
						dlg.cancel();
					}
				});

			}
		});

		final TextView no_text2 = (TextView) findViewById(R.id.dlg_lbl_price_sell);

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.numberentry_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Enter the Price");

				dlg.show();

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				no_ok.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						sell_price = mynp1.getValue();
						sell_price_sel = String.valueOf(sell_price);
						no_text2.setText(sell_price_sel);
						if (sell_price != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.price_sell_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_price_sell
									.setImageResource(R.drawable.empty_not);

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

		final TextView no_text3 = (TextView) findViewById(R.id.dlg_lbl_unit_no_rem_sell);

		item7.setOnClickListener(new View.OnClickListener() {
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
						sell_no_rem = mynp1.getValue();
						sell_no_sel_rem = String.valueOf(sell_no_rem);
						no_text3.setText(sell_no_sel_rem);
						if (sell_no_rem != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.rem_quant_sell_tr);

							tr_feedback
									.setBackgroundResource(R.drawable.def_img);
							bg_units_no_rem_sell
									.setImageResource(R.drawable.empty_not);

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

		item8.setOnClickListener(new View.OnClickListener() {
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

				// final ImageView img_1 = (ImageView)
				// findViewById(R.id.dlg_unit_sow);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_unit_rem_sell);
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

						var_text.setText("10 Kgs");
						units_rem_sell = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell
								.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");

						var_text.setText("20 Kgs");
						units_rem_sell = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell
								.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");

						var_text.setText("50 Kgs");
						units_rem_sell = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell
								.setImageResource(R.drawable.empty_not);

						// tracks the application usage.
						ApplicationTracker.getInstance().logEvent(
								EventType.CLICK, LOG_TAG, "bags", units_sell);
						dlg.cancel();
					}
				});

			}
		});

		Button btnNext = (Button) findViewById(R.id.sell_ok);
		Button cancel = (Button) findViewById(R.id.sell_cancel);

		btnNext.setOnLongClickListener(this); // Integration
		cancel.setOnLongClickListener(this);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();
			}

		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				int flag1, flag2, flag3, flag4, flag5;

				if (crop_sell.toString().equalsIgnoreCase("0")) {

					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (months_harvest.toString().equalsIgnoreCase("0")
						|| date_sel == 0) {

					flag2 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag2 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (units_sell.toString().equalsIgnoreCase("0") || sell_no == 0) {

					flag3 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag3 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (sell_price == 0) {
					flag4 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.price_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag4 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.price_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (units_rem_sell.toString().equalsIgnoreCase("0")
						|| sell_no_rem == 0) {

					flag5 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.rem_quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag5 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.rem_quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0
						&& flag5 == 0) {

					Intent adminintent = new Intent(action_selling.this,
							Homescreen.class);

					startActivity(adminintent);
					action_selling.this.finish();
					okaudio();

				} else {
					initmissingval();
				}

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_selling.this,
						Homescreen.class);

				startActivity(adminintent);
				action_selling.this.finish();

			}
		});

	}

	@Override
	public boolean onLongClick(View v) {

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

		if (v.getId() == R.id.home_btn_month_sell) {

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

		if (v.getId() == R.id.quantity_sow_txt_btn) {

			playAudioalways(R.raw.quantity);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.crop_sell_txt_btn) {

			playAudioalways(R.raw.crop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.amount_sow_txt_btn) {

			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.treat_sow_txt_btn) {

			playAudioalways(R.raw.priceperquintal);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.remain_sow_txt_btn) {

			playAudioalways(R.raw.remaining);
			ShowHelpIcon(v);
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

		if (v.getId() == R.id.home_btn_crop_sell) {

			playAudioalways(R.raw.crop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_date_sell) {

			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_month_sell) {

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_no_sell) {

			playAudioalways(R.raw.noofbags);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_sell) {
			playAudioalways(R.raw.keygis);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_price_sell) {

			playAudioalways(R.raw.value);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_no_rem_sell) {

			playAudioalways(R.raw.noofbags);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.home_btn_units_rem_sell) {

			playAudioalways(R.raw.keygis);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.aggr_img_help) {

			playAudioalways(R.raw.help);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.sell_ok) {

			playAudioalways(R.raw.ok);
			ShowHelpIcon(v);

		}
		if (v.getId() == R.id.sell_cancel) {

			playAudioalways(R.raw.cancel);
			ShowHelpIcon(v);

		}

		return true;
	}
}