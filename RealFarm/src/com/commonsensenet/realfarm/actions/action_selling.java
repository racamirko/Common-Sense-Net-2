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

public class action_selling extends HelpEnabledActivity {
	private Context context = this;
	private RealFarmProvider mDataProvider;
	private final action_selling parentReference = this;
	private String quality_sell = "0", selling_pickcheck = "0";
	private int sellprice_no, sell_no;
	private String sellprice_no_sel, sell_no_sel, units_sell = "0";
	String crop_sell;
	int date_sel;
	String date_sel_str;
	String months_harvest;
    int sell_price;
	String sell_price_sel;
	 int sell_no_rem;
	 String units_rem_sell;
 String sell_no_sel_rem;
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
		System.out.println("selling details entered");
		mDataProvider = RealFarmProvider.getInstance(context);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selling_dialog);
		System.out.println("selling done");

		playAudio(R.raw.clickingselling);
		
		final ImageView bg_crop_sell = (ImageView) findViewById(R.id.img_bg_units_no_sow);
		final ImageView bg_date_sell = (ImageView) findViewById(R.id.img_bg_date_sell);
		final ImageView bg_month_sell = (ImageView) findViewById(R.id.img_bg_month_sell);
		final ImageView bg_units_sell = (ImageView) findViewById(R.id.img_bg_units_sell);
		final ImageView bg_units_no_sell = (ImageView) findViewById(R.id.img_bg_units_no_sell);
		final ImageView bg_price_sell = (ImageView) findViewById(R.id.img_bg_price_sell);
		final ImageView bg_units_no_rem_sell = (ImageView) findViewById(R.id.img_bg_units_no_rem_sell);
		final ImageView bg_units_rem_sell = (ImageView) findViewById(R.id.img_bg_units_rem_sell);
		
		//bg_day_sow.setImageResource(R.drawable.empty_not);
		
		

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
		
		
		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.variety_sowing_dialog);
				dlg.setCancelable(true);
				dlg.setTitle("Choose the crop ");
				
				dlg.show();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** In selection of variety of seed sowed in  Sowing*********** \r\n");

				}
				final Button variety1;
				final Button variety2;
				final Button variety3;
				final Button variety4;
				final Button variety5;
				final Button variety6;
				// final Button variety7;
				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.img_bg_crop_sell);

				final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_crop_sell);
				variety1 = (Button) dlg.findViewById(R.id.home_btn_var_sow_1);
				variety2 = (Button) dlg.findViewById(R.id.home_btn_var_sow_2);
				variety3 = (Button) dlg.findViewById(R.id.home_btn_var_sow_3);
				variety4 = (Button) dlg.findViewById(R.id.home_btn_var_sow_4);
				variety5 = (Button) dlg.findViewById(R.id.home_btn_var_sow_5);
				variety6 = (Button) dlg.findViewById(R.id.home_btn_var_sow_6);

				((Button) dlg.findViewById(R.id.home_btn_var_sow_1))
						.setOnLongClickListener(parentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.home_btn_var_sow_2))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_3))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_4))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_5))
						.setOnLongClickListener(parentReference);
				((Button) dlg.findViewById(R.id.home_btn_var_sow_6))
						.setOnLongClickListener(parentReference);

				variety1.setOnClickListener(new View.OnClickListener() {
					

					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bajra");
						crop_sell = "Bajra";
						TableRow tr_feedback = (TableRow) findViewById(R.id.crop_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
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
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}
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
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}

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
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}
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
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}
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
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + crop_sell
											+ " for Sowing*********** \r\n");

						}
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

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				no_ok.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

						NumberPicker mynpd = (NumberPicker) dlg.findViewById(R.id.numberpick);
						date_sel = mynpd.getValue();
						date_sel_str = String.valueOf(date_sel);
						no_text.setText(date_sel_str);
						if (date_sel != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.date_sell_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);
							bg_date_sell.setImageResource(R.drawable.empty_not);
							
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

				

				Button no_ok = (Button) dlg.findViewById(R.id.number_ok);
				Button no_cancel = (Button) dlg
						.findViewById(R.id.number_cancel);
				no_ok.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						NumberPicker mynp1 = (NumberPicker) dlg
								.findViewById(R.id.numberpick);
						sell_no = mynp1.getValue();
						sell_no_sel = String.valueOf(sell_no);
						no_text1.setText(sell_no_sel);
						if (sell_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);
							bg_units_no_sell.setImageResource(R.drawable.empty_not);
							
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

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(parentReference); // Audio
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
									"***** In selection of units for Sowing*********** \r\n");

				}

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bag of 10 Kgs");
						units_sell = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Bag of 20 Kgs");
						units_sell = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Bag of 50 Kgs");
						units_sell = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
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
						if (sell_no != 0) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.price_sell_tr);

							tr_feedback.setBackgroundResource(R.drawable.def_img);
							bg_price_sell.setImageResource(R.drawable.empty_not);
							
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

							tr_feedback.setBackgroundResource(R.drawable.def_img);
							bg_units_no_rem_sell.setImageResource(R.drawable.empty_not);
							
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

				((Button) dlg.findViewById(R.id.home_btn_units_1))
						.setOnLongClickListener(parentReference); // Audio
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
									"***** In selection of units for Sowing*********** \r\n");

				}

				unit1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
						// img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
						var_text.setText("Bag of 10 Kgs");
						units_rem_sell = "Bag of 10 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit2.setOnClickListener(new View.OnClickListener() {
					

					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
						var_text.setText("Bag of 20 Kgs");
						units_rem_sell = "Bag of 20 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

				unit3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						// img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
						var_text.setText("Bag of 50 Kgs");
						units_rem_sell = "Bag of 50 Kgs";
						TableRow tr_feedback = (TableRow) findViewById(R.id.quant_sell_tr);

						tr_feedback.setBackgroundResource(R.drawable.def_img);
						bg_units_rem_sell.setImageResource(R.drawable.empty_not);
						// item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
						if (Global.writeToSD == true) {

							String logtime = getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt", logtime
									+ " -> ");

							mDataProvider.File_Log_Create("UIlog.txt",
									"***** user selected" + units_sell
											+ " for Sowing*********** \r\n");

						}
						dlg.cancel();
					}
				});

			}
		});
		
		
		
		
		
		
		
		
		
		

	}

	@Override
	public boolean onLongClick(View v) { // latest

		
		return true;
	}
}