package com.commonsensenet.realfarm.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.view.DialogAdapter;

public class action_selling extends HelpEnabledActivityOld {

	private final action_selling parentReference = this;
	private int sell_no;
	private String units_sell = "0";
	private String crop_sell = "0";
	private int date_sel;
	private String months_harvest = "0";
	private int sell_price;
	private int sell_no_rem = -1;
	private String units_rem_sell = "0";
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
		resultsMap.put("date_sel", "0");
		resultsMap.put("sell_no", "0");
		resultsMap.put("sell_price", "0");
		resultsMap.put("sell_no_rem", "0");

		// bg_day_sow.setImageResource(R.drawable.empty_not);

		final View item1 = (View) findViewById(R.id.dlg_lbl_crop_sell);
		final View item2 = (View) findViewById(R.id.dlg_lbl_date_sell);
		final View item3 = (View) findViewById(R.id.dlg_lbl_month_sell);
		final View item4 = (View) findViewById(R.id.dlg_lbl_unit_no_sell);
		final View item5 = (View) findViewById(R.id.dlg_lbl_unit_sell);
		final View item6 = (View) findViewById(R.id.dlg_lbl_price_sell);
		final View item7 = (View) findViewById(R.id.dlg_lbl_unit_no_rem_sell);
		final View item8 = (View) findViewById(R.id.dlg_lbl_unit_rem_sell);

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

		final View crop;
		final View date;
		final View quantity;
		final View priceperquint;
		final View remain;

		crop = (View) findViewById(R.id.crop_sell_tr);
		date = (View) findViewById(R.id.date_sell_tr);

		quantity = (View) findViewById(R.id.quant_sell_tr);
		priceperquint = (View) findViewById(R.id.price_sell_tr);
		remain = (View) findViewById(R.id.rem_quant_sell_tr);

		crop.setOnLongClickListener(this);
		date.setOnLongClickListener(this);
		quantity.setOnLongClickListener(this);
		priceperquint.setOnLongClickListener(this);
		remain.setOnLongClickListener(this);

		item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				ArrayList<DialogData> m_entries = mDataProvider.getCrops();
				displayDialog(v, m_entries, "crop_sell", "Select the crop",
						R.raw.problems, R.id.dlg_lbl_crop_sell,
						R.id.crop_sell_tr, 0);

			}
		});

		item2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the day", "date_sel", R.raw.dateinfo,
						1, 31, Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH), 1, 0,
						R.id.dlg_lbl_date_sell, R.id.date_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");
<<<<<<< HEAD
				
				ArrayList<DialogData> m_entries = mDataProvider.getDialogData(RealFarmDatabase.DIALOG_MONTH_ID);
				displayDialog(v, m_entries, "months_harvest", "Select the month", R.raw.bagof50kg, R.id.dlg_lbl_month_sell, R.id.date_sell_tr, 0);
=======

				ArrayList<DialogData> m_entries = DialogArrayLists
						.getMonthArray(v);
				displayDialog(v, m_entries, "months_harvest",
						"Select the month", R.raw.bagof50kg,
						R.id.dlg_lbl_month_sell, R.id.date_sell_tr, 0);
>>>>>>> Modified DB to include the Resources table

			}

		});

		item4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of bags", "sell_no",
						R.raw.dateinfo, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_sell, R.id.quant_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units sow dialog", "in dialog");
<<<<<<< HEAD
				
				ArrayList<DialogData> m_entries = mDataProvider.getDialogData(RealFarmDatabase.DIALOG_UNITS_ID);
				displayDialog(v, m_entries, "units_sell", "Select the unit", R.raw.problems, R.id.dlg_lbl_unit_sell, R.id.quant_sell_tr, 2);
=======

				final ArrayList<DialogData> m_entries = DialogArrayLists
						.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_sell", "Select the unit",
						R.raw.problems, R.id.dlg_lbl_unit_sell,
						R.id.quant_sell_tr, 2);
>>>>>>> Modified DB to include the Resources table

			}
		});

		item6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();

				displayDialogNP("Enter the price", "sell_price",
						R.raw.dateinfo, 0, 9999, 3200, 50, 0,
						R.id.dlg_lbl_price_sell, R.id.price_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);
			}
		});

		item7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in variety sowing dialog", "in dialog");

				displayDialogNP("Choose the number of bags", "sell_no_rem",
						R.raw.dateinfo, 0, 200, 0, 1, 0,
						R.id.dlg_lbl_unit_no_rem_sell, R.id.rem_quant_sell_tr,
						R.raw.dateinfo, R.raw.dateinfo, R.raw.dateinfo,
						R.raw.dateinfo);

			}
		});

		item8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();
				Log.d("in units sow dialog", "in dialog");
<<<<<<< HEAD
				
				ArrayList<DialogData> m_entries = mDataProvider.getDialogData(RealFarmDatabase.DIALOG_UNITS_ID);
				displayDialog(v, m_entries, "units_rem_sell", "Select the unit", R.raw.problems, R.id.dlg_lbl_unit_rem_sell, R.id.rem_quant_sell_tr, 2);
=======

				final ArrayList<DialogData> m_entries = DialogArrayLists
						.getItUnitsArray(v, 20, 51, 1);
				displayDialog(v, m_entries, "units_rem_sell",
						"Select the unit", R.raw.problems,
						R.id.dlg_lbl_unit_rem_sell, R.id.rem_quant_sell_tr, 2);
>>>>>>> Modified DB to include the Resources table

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
				date_sel = Integer.parseInt(resultsMap.get("date_sel"));
				sell_no = Integer.parseInt(resultsMap.get("sell_no"));
				sell_price = Integer.parseInt(resultsMap.get("sell_price"));
				sell_no_rem = Integer.parseInt(resultsMap.get("sell_no_rem"));

				int flag1, flag2, flag3, flag4, flag5;

				if (crop_sell.toString().equalsIgnoreCase("0")) {

					flag1 = 1;

					View tr_feedback = (View) findViewById(R.id.crop_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag1 = 0;

					View tr_feedback = (View) findViewById(R.id.crop_sell_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (months_harvest.toString().equalsIgnoreCase("0")
						|| date_sel == 0) {

					flag2 = 1;

					View tr_feedback = (View) findViewById(R.id.date_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag2 = 0;

					View tr_feedback = (View) findViewById(R.id.date_sell_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (units_sell.toString().equalsIgnoreCase("0") || sell_no == 0) {

					flag3 = 1;

					View tr_feedback = (View) findViewById(R.id.quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag3 = 0;

					View tr_feedback = (View) findViewById(R.id.quant_sell_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (sell_price == 0) {
					flag4 = 1;

					View tr_feedback = (View) findViewById(R.id.price_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag4 = 0;

					View tr_feedback = (View) findViewById(R.id.price_sell_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
				}

				if (units_rem_sell.toString().equalsIgnoreCase("0")
						|| sell_no_rem == -1) {

					flag5 = 1;

					View tr_feedback = (View) findViewById(R.id.rem_quant_sell_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {

					flag5 = 0;

					View tr_feedback = (View) findViewById(R.id.rem_quant_sell_tr);

					tr_feedback
							.setBackgroundResource(android.R.drawable.list_selector_background);
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
		if (v.getId() == R.id.date_sell_tr) {

			playAudioalways(R.raw.choosethemonth);
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

		if (v.getId() == R.id.dlg_lbl_crop_sell) {

			playAudioalways(R.raw.crop);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_date_sell) {

			playAudioalways(R.raw.date);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_month_sell) {

			playAudioalways(R.raw.choosethemonth);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_sell) {

			playAudioalways(R.raw.noofbags);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_sell) {
			playAudioalways(R.raw.keygis);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_price_sell) {

			playAudioalways(R.raw.value);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_no_rem_sell) {

			playAudioalways(R.raw.noofbags);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.dlg_lbl_unit_rem_sell) {

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

	private void putBackgrounds(DialogData choice, TextView var_text,
			int imageType) {
		if (choice.getBackgroundRes() != -1)
			var_text.setBackgroundResource(choice.getBackgroundRes());
		if (imageType == 1 || imageType == 2) {
			BitmapDrawable bd = (BitmapDrawable) parentReference.getResources()
					.getDrawable(choice.getImageRes());
			int width = bd.getBitmap().getWidth();
			if (width > 80)
				width = 80;

			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(10, 0, 80 - width - 20, 0);
			var_text.setLayoutParams(llp);

			var_text.setBackgroundResource(choice.getImageRes());
			if (imageType == 1)
				var_text.setTextColor(Color.TRANSPARENT);
			else {
				var_text.setGravity(Gravity.TOP);
				var_text.setPadding(0, 0, 0, 0);
				var_text.setTextSize(20);
				var_text.setTextColor(Color.BLACK);
			}
		}
	}

	private void displayDialog(View v, final ArrayList<DialogData> m_entries,
			final String mapEntry, final String title, int entryAudio,
			final int varText, final int trFeedback, final int imageType) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView) dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener() { // TODO: adapt
			// the audio
			// in the db
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				TextView var_text = (TextView) findViewById(varText);
				DialogData choice = m_entries.get(position);
				var_text.setText(choice.getShortName());
				resultsMap.put(mapEntry, choice.getValue());
				View tr_feedback = (View) findViewById(trFeedback);
				tr_feedback
						.setBackgroundResource(android.R.drawable.list_selector_background);

				// put backgrounds (specific to the application) TODO: optimize
				// the resize
				putBackgrounds(choice, var_text, imageType);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, title, choice.getValue());

				Toast.makeText(parentReference, resultsMap.get(mapEntry),
						Toast.LENGTH_SHORT).show();

				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + choice.getAudio(), null, null);
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + m_entries.get(position).getAudio(), null, null);
				playAudioalways(iden);
				return true;
			}
		});
	}

	private void displayDialogNP(String title, final String mapEntry,
			int openAudio, double min, double max, double init, double inc,
			int nbDigits, int textField, int tableRow, final int okAudio,
			final int cancelAudio, final int infoOkAudio,
			final int infoCancelAudio) {

		final Dialog dialog = new Dialog(parentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		playAudio(openAudio); // opening audio

		if (!resultsMap.get(mapEntry).equals("0")
				&& !resultsMap.get(mapEntry).equals("-1"))
			init = Double.valueOf(resultsMap.get(mapEntry));

		NumberPicker np = new NumberPicker(parentReference, min, max, init,
				inc, nbDigits);
		dialog.setContentView(np);

		final TextView tw_sow = (TextView) findViewById(textField);
		final View tr_feedback = (View) findViewById(tableRow);

		final TextView tw = (TextView) dialog.findViewById(R.id.tw);
		ImageButton ok = (ImageButton) dialog.findViewById(R.id.ok);
		ImageButton cancel = (ImageButton) dialog.findViewById(R.id.cancel);
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String result = tw.getText().toString();
				resultsMap.put(mapEntry, result);
				tw_sow.setText(result);
				tr_feedback
						.setBackgroundResource(android.R.drawable.list_selector_background);
				Toast.makeText(parentReference, result, Toast.LENGTH_LONG)
						.show();
				dialog.cancel();
				playAudio(okAudio); // ok audio
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.cancel();
				playAudio(cancelAudio); // cancel audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "amount", "cancel");
			}
		});
		ok.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoOkAudio); // info audio
				return true;
			}
		});
		cancel.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoCancelAudio); // info audio
				return true;
			}
		});
		tw.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				// String num = tw.getText().toString();
				playAudio(R.raw.dateinfo); // info audio
				return false;
			}
		});

		dialog.show();
	}
}