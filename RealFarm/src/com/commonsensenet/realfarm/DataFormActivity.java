package com.commonsensenet.realfarm;

import java.util.ArrayList;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;

public abstract class DataFormActivity extends HelpEnabledActivity {

	/** Name used to log the activity of the class. */
	public static String LOG_TAG;

	/** Access to the underlying database of the application. */
	protected RealFarmProvider mDataProvider;
	/** Reference to the current class used in internal classes. */
	private final DataFormActivity mParentReference = this;
	/** Results map used to handle the validation of the form. */
	protected HashMap<String, String> mResultsMap;

	protected void displayDialog(View v, final ArrayList<DialogData> entries,
			final String mapEntry, final String title, int entryAudio,
			final int varText, final int trFeedback, final int imageType) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, entries);
		ListView mList = (ListView) dialog.findViewById(R.id.liste);
		mList.setAdapter(adapter);

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
				DialogData choice = entries.get(position);
				var_text.setText(choice.getShortName());
				mResultsMap.put(mapEntry, choice.getValue());
				View tr_feedback = findViewById(trFeedback);
				tr_feedback
						.setBackgroundResource(android.R.drawable.list_selector_background);

				// put backgrounds (specific to the application) TODO: optimize
				// the resize
				putBackgrounds(choice, var_text, imageType);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, title, choice.getValue());

				Toast.makeText(mParentReference, mResultsMap.get(mapEntry),
						Toast.LENGTH_SHORT).show();

				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO: adapt the audio in the db
				int iden = entries.get(position).getAudioRes();

				playAudio(iden, true);
				return true;
			}
		});
	}

	protected void displayDialogNP(String title, final String mapEntry,
			int openAudio, double min, double max, double init, double inc,
			int nbDigits, int textField, int tableRow, final int okAudio,
			final int cancelAudio, final int infoOkAudio,
			final int infoCancelAudio) {

		final Dialog dialog = new Dialog(mParentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		playAudio(openAudio);

		if (!mResultsMap.get(mapEntry).equals("0"))
			init = Double.valueOf(mResultsMap.get(mapEntry));

		NumberPicker np = new NumberPicker(mParentReference, min, max, init,
				inc, nbDigits);
		dialog.setContentView(np);

		final TextView tw_sow = (TextView) findViewById(textField);
		final View feedbackRow = findViewById(tableRow);
		final TextView textView = (TextView) dialog.findViewById(R.id.tw);

		View ok = dialog.findViewById(R.id.ok);
		View cancel = dialog.findViewById(R.id.cancel);

		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String result = textView.getText().toString();
				mResultsMap.put(mapEntry, result);
				tw_sow.setText(result);
				feedbackRow
						.setBackgroundResource(android.R.drawable.list_selector_background);
				Toast.makeText(mParentReference, result, Toast.LENGTH_LONG)
						.show();
				dialog.cancel();
				playAudio(okAudio);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.cancel();
				playAudio(cancelAudio);
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "amount", "cancel");
			}
		});
		ok.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoOkAudio);
				return true;
			}
		});
		cancel.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				playAudio(infoCancelAudio);
				return true;
			}
		});
		textView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				// String num = tw.getText().toString();
				playAudio(R.raw.dateinfo);
				return false;
			}
		});

		dialog.show();
	}

	protected void initMissingValue() {
		playAudio(R.raw.missinginfo);
	}

	public void onCreate(Bundle savedInstanceState, int layoutId, String logTag) {
		super.onCreate(savedInstanceState);

		// tag used to log the events.
		LOG_TAG = logTag;

		// sets the layout
		setContentView(layoutId);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

		// map used to automatize the validation.
		mResultsMap = new HashMap<String, String>();

		// gets the action buttons.
		View plotok = findViewById(R.id.button_ok);
		View plotcancel = findViewById(R.id.button_cancel);

		// adds the long click listeners to enable the help function.
		plotok.setOnLongClickListener(this);
		plotcancel.setOnLongClickListener(this);

		plotok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (validateForm()) {
					startActivity(new Intent(DataFormActivity.this,
							Homescreen.class));
					DataFormActivity.this.finish();
				} else {
					initMissingValue();
				}
			}
		});

		plotcancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// equivalent to pressing the back button.
				onBackPressed();
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.ok);
			showHelpIcon(v);
		} else if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.cancel);
			showHelpIcon(v);
		}

		return true;
	}

	protected void putBackgrounds(DialogData choice, TextView textView,
			int imageType) {

		if (choice.getBackgroundRes() != -1) {
			textView.setBackgroundResource(choice.getBackgroundRes());
		}

		if (imageType == 1 || imageType == 2) {
			BitmapDrawable bd = (BitmapDrawable) mParentReference
					.getResources().getDrawable(choice.getImageRes());

			// validates the maximum width.
			int width = bd.getBitmap().getWidth();
			if (width > 80) {
				width = 80;
			}

			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(10, 0, 80 - width - 20, 0);
			textView.setLayoutParams(llp);
			textView.setBackgroundResource(choice.getImageRes());

			if (imageType == 1) {
				textView.setTextColor(Color.TRANSPARENT);
			} else {
				textView.setGravity(Gravity.TOP);
				textView.setPadding(0, 0, 0, 0);
				textView.setTextSize(20);
				textView.setTextColor(Color.BLACK);
			}
		}
	}

	/**
	 * Allows custom validation of the form. If true is returned it means the
	 * form is valid.
	 * 
	 * @return true if the form is valid, otherwise false.
	 */
	protected abstract Boolean validateForm();
}
