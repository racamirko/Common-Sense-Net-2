package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.DialogArrayLists;

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
	private int sell_no_rem = -1;
	private String units_rem_sell = "0";
	private String sell_no_sel_rem = "0";
	private HashMap<String, String> resultsMap;
	private RealFarmProvider mDataProvider;

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
		
		mDataProvider = RealFarmProvider.getInstance(this);

		System.out.println("selling done");

		playAudio(R.raw.clickingselling);
		
		resultsMap = new HashMap<String, String>();
		resultsMap.put("crop_sell", "0");
		resultsMap.put("months_harvest", "0");
		resultsMap.put("units_sell", "0");
		resultsMap.put("units_rem_sell", "0");

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

		final TableRow crop;
		final TableRow date;
		final TableRow quantity;
		final TableRow priceperquint;
		final TableRow remain;

		crop = (TableRow) findViewById(R.id.crop_sell_tr);
		date = (TableRow) findViewById(R.id.date_sell_tr);

		quantity = (TableRow) findViewById(R.id.quant_sell_tr);
		priceperquint = (TableRow) findViewById(R.id.price_sell_tr);
		remain = (TableRow) findViewById(R.id.rem_quant_sell_tr);

		crop.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		quantity.setOnLongClickListener(this);
		priceperquint.setOnLongClickListener(this);
		remain.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				
				ArrayList<DialogData> m_entries = mDataProvider.getCropsThisSeason();
				displayDialog(v, m_entries, "crop_sell", "Select the crop", R.raw.problems, R.id.dlg_lbl_crop_sell, R.id.crop_sell_tr);

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
				dlg.setTitle("Choose the day");
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
									.setBackgroundResource(android.R.drawable.list_selector_background);
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
				
				ArrayList<DialogData> m_entries = DialogArrayLists.getMonthArray(v);
				displayDialog(v, m_entries, "months_harvest", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_sell, R.id.date_sell_tr);

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
									.setBackgroundResource(android.R.drawable.list_selector_background);
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
				
				final ArrayList<DialogData> m_entries = DialogArrayLists.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_sell", "Select the unit", R.raw.problems, R.id.dlg_lbl_unit_sell, R.id.quant_sell_tr);

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
									.setBackgroundResource(android.R.drawable.list_selector_background);
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
						if (sell_no_rem != -1) {

							TableRow tr_feedback = (TableRow) findViewById(R.id.rem_quant_sell_tr);

							tr_feedback
									.setBackgroundResource(android.R.drawable.list_selector_background);
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
				
				final ArrayList<DialogData> m_entries = DialogArrayLists.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_rem_sell", "Select the unit", R.raw.problems, R.id.dlg_lbl_unit_rem_sell, R.id.quant_sell_tr);

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
				
				crop_sell = resultsMap.get("crop_sell");
				months_harvest = resultsMap.get("months_harvest");
				units_sell = resultsMap.get("units_sell");
				units_rem_sell = resultsMap.get("units_rem_sell");

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
						|| sell_no_rem == -1) {

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

		if (v.getId() == R.id.quant_sell_tr) {

			playAudioalways(R.raw.quantity);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.crop_sell_tr) {

			playAudioalways(R.raw.crop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.date_sell_tr) {

			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.price_sell_tr) {

			playAudioalways(R.raw.priceperquintal);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.rem_quant_sell_tr) {

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
	
	private void displayDialog(View v, final ArrayList<DialogData> m_entries, final String mapEntry, final String title, int entryAudio, final int varText, final int trFeedback){ 
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(), R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView)dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener(){ // TODO: adapt the audio in the db
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Does whatever is specific to the application
				Log.d("var "+position+" picked ", "in dialog");
				TextView var_text = (TextView) findViewById(varText);
				DialogData choice = m_entries.get(position);
				var_text.setText(choice.getName());
				resultsMap.put(mapEntry, choice.getValue());  
				TableRow tr_feedback = (TableRow) findViewById(trFeedback);
				tr_feedback.setBackgroundResource(android.R.drawable.list_selector_background);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(
						EventType.CLICK, LOG_TAG, title,
						choice.getValue());
				
				Toast.makeText(parentReference, resultsMap.get(mapEntry), Toast.LENGTH_SHORT).show();
						
				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + choice.getAudio(), null, null);
				playAudio(iden);
			}});

		mList.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				//view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + m_entries.get(position).getAudio(), null, null);
				playAudioalways(iden);
				return true;
			}});
	}
}