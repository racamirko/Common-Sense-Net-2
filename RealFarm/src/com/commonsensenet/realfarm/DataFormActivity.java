package com.commonsensenet.realfarm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.DateHelper;
import com.commonsensenet.realfarm.view.DialogAdapter;

public abstract class DataFormActivity extends HelpEnabledActivity {

	/** Reference to the current class used in internal classes. */
	private final DataFormActivity mParentReference = this;
	/** Results map used to handle the validation of the form. */
	protected HashMap<String, Object> mResultsMap;

	protected void displayDialog(View v, final List<Resource> data,
			final String propertyKey, final String title, int audio,
			final int textFieldId, final int rowFeedbackId, final int imageType) {

		// creates a new dialog and configures it.
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		// creates an adapter that handles the data.
		DialogAdapter adapter = new DialogAdapter(v.getContext(), data);
		ListView mList = (ListView) dialog.findViewById(R.id.dialog_list);
		mList.setAdapter(adapter);

		// opens the dialog.
		dialog.show();

		playAudio(audio);

		// TODO: adapt the audio in the database.
		mList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// selected resource.
				Resource choice = data.get(position);

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), propertyKey,
						choice.getName());

				// sets the short name of the resource.
				TextView var_text = (TextView) findViewById(textFieldId);
				var_text.setText(choice.getShortName());

				// deselects the field.
				highlightField(rowFeedbackId, false);

				// saves the id of the selected item.
				// mResultsMap.put(propertyKey, choice.getId());
				mResultsMap.put(propertyKey, position);

				// put backgrounds (specific to the application) TODO: optimize
				// the resize
				putBackgrounds(choice, var_text, imageType);

				/*
				 * Toast.makeText(mParentReference,
				 * mResultsMap.get(propertyKey).toString(),
				 * Toast.LENGTH_SHORT).show();
				 */

				// plays the name of the chosen option.
				int iden = choice.getAudio();
				playAudio(iden);

				// closes the dialog.
				dialog.dismiss();
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO: adapt the audio in the database
				int iden = data.get(position).getAudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						data.get(position).getName());

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

		// creates the dialog what will be shown.
		final Dialog dialog = new Dialog(mParentReference);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		// plays the open audio.
		playAudio(openAudio);

		// modifies the initial value if there is an existing value.
		if (mResultsMap.get(mapEntry) != null
				&& Double.valueOf(mResultsMap.get(mapEntry).toString()) != 0.0) {
			init = Double.valueOf(mResultsMap.get(mapEntry).toString());
		}

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

				// tracks the action.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "numberPicker",
						"ok " + result);

				// adds the value into the result map
				mResultsMap.put(mapEntry, result);
				tw_sow.setText(result);
				feedbackRow
						.setBackgroundResource(android.R.drawable.list_selector_background);

				// closes the dialog.
				dialog.dismiss();

				// playing number
				float number = Float.valueOf(textView.getText().toString());
				int result1 = (int) (number * 100);
				int rem = result1 % 100;

				if ((rem == 100) || (rem == 0)) {
					float num = Float.valueOf(textView.getText().toString());
					int temp = (int) num;
					if (rem != 100) {
						playInteger(temp);
					} else {
						add_audio_single(101);
					}
					playSound();
				} else {
					playFloat(Float.valueOf(textView.getText().toString()));
					playSound();
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.cancel();
				playAudio(cancelAudio);
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(), "numberPicker", "cancel");

			}
		});
		ok.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(), "numberPicker", "ok");
				playAudio(infoOkAudio, true);
				return true;
			}
		});
		cancel.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(), "numberPicker", "cancel");
				playAudio(infoCancelAudio, true);
				return true;
			}
		});
		textView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(), "numberPicker", "text",
						textView.getText());

				float number = Float.valueOf(textView.getText().toString());
				int result = (int) (number * 100);
				int rem = result % 100;

				if ((rem == 100) || (rem == 0)) {
					float num = Float.valueOf(textView.getText().toString());
					int temp = (int) num;
					if (rem != 100) {
						playInteger(temp);
					} else {
						add_audio_single(101);
					}
					playSound(true);
				} else {

					playFloat(Float.valueOf(textView.getText().toString()));
					playSound(true);

				}
				return false;
			}
		});

		dialog.show();
	}

	protected boolean validDate(int day, int monthId) {
		Resource monthResource = mDataProvider.getResourceById(monthId);
		int month = monthResource.getValue();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (month > Calendar.getInstance().get(Calendar.MONTH)
				|| (month == Calendar.getInstance().get(Calendar.MONTH) && day > Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH)))
			month = (year - 1);
		return DateHelper.validDate(day, month, year);
	}

	protected Date getDate(int day, int monthId) {
		Resource monthResource = mDataProvider.getResourceById(monthId);
		int month = monthResource.getValue();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (month > Calendar.getInstance().get(Calendar.MONTH)
				|| (month == Calendar.getInstance().get(Calendar.MONTH) && day > Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH)))
			calendar.set(Calendar.YEAR, (year - 1));

		return calendar.getTime();
	}

	/**
	 * Changes the background of the View to a highlighted or normal state.
	 * 
	 * @param id
	 *            the id of the View to modify
	 * @param isHighlighted
	 *            whether to highlight or not the View.
	 */
	protected void highlightField(int id, boolean isHighlighted) {
		View fieldView = findViewById(id);
		fieldView.setBackgroundResource(isHighlighted ? R.drawable.def_img_not
				: android.R.drawable.list_selector_background);
	}

	/**
	 * Indicates that at least one field is missing.
	 */
	protected void missingValue() {
		playAudio(R.raw.missinginfo);
	}

	public void onCreate(Bundle savedInstanceState, int layoutId) {
		super.onCreate(savedInstanceState, layoutId);

		// map used to automatize the validation.
		mResultsMap = new HashMap<String, Object>();

		// gets the action buttons.
		View okButton = findViewById(R.id.button_ok);
		View cancelButton = findViewById(R.id.button_cancel);

		// adds the long click listeners to enable the help function.
		okButton.setOnLongClickListener(this);
		cancelButton.setOnLongClickListener(this);

		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (validateForm()) {
					playAudio(R.raw.ok); // TODO: say something here?

					Intent intent = new Intent(DataFormActivity.this,
							Homescreen.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(intent);
					DataFormActivity.this.finish();
				} else {
					missingValue();
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playAudio(R.raw.cancel); // TODO: say something here?
				// equivalent to pressing the back button.
				onBackPressed();
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button_ok) {
			playAudio(R.raw.ok);
		} else if (v.getId() == R.id.button_cancel) {
			playAudio(R.raw.cancel);
		} else {
			return super.onLongClick(v);
		}

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		showHelpIcon(v);

		return true;
	}

	protected void putBackgrounds(Resource choice, TextView textView,
			int imageType) {

		if (choice.getBackgroundImage() != -1) {
			textView.setBackgroundResource(choice.getBackgroundImage());
		}

		// TODO: orbolanos: what is this for?
		if (imageType == 1 || imageType == 2) {
			BitmapDrawable bd = (BitmapDrawable) mParentReference
					.getResources().getDrawable(choice.getImage1());

			// validates the maximum width.
			int width = bd.getBitmap().getWidth();
			if (width > 80) {
				width = 80;
			}

			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(10, 0, 80 - width - 20, 0);
			textView.setLayoutParams(llp);
			textView.setBackgroundResource(choice.getImage1());

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

	public void play_day_audio(int n) // For audio added
	{

		switch (n) {
		case 1:
			playAudio(R.raw.a1, true);
			break;
		case 2:
			playAudio(R.raw.a2, true);
			break;
		case 3:
			playAudio(R.raw.a3, true);
			break;
		case 4:
			playAudio(R.raw.a4, true);
			break;
		case 5:
			playAudio(R.raw.a5, true);
			break;
		case 6:
			playAudio(R.raw.a6, true);
			break;
		case 7:
			playAudio(R.raw.a7, true);
			break;
		case 8:
			playAudio(R.raw.a8, true);
			break;
		case 9:
			playAudio(R.raw.a9, true);
			break;
		case 10:
			playAudio(R.raw.a10, true);
			break;
		case 11:
			playAudio(R.raw.a11, true);
			break;
		case 12:
			playAudio(R.raw.a12, true);
			break;
		case 13:
			playAudio(R.raw.a13, true);
			break;
		case 14:
			playAudio(R.raw.a14, true);
			break;
		case 15:
			playAudio(R.raw.a15, true);
			break;
		case 16:
			playAudio(R.raw.a16, true);
			break;
		case 17:
			playAudio(R.raw.a17, true);
			break;
		case 18:
			playAudio(R.raw.a18, true);
			break;
		case 19:
			playAudio(R.raw.a19, true);
			break;
		case 20:
			playAudio(R.raw.a21, true);
			break;
		case 21:
			playAudio(R.raw.a21, true);
			break;
		case 22:
			playAudio(R.raw.a22, true);
			break;
		case 23:
			playAudio(R.raw.a23, true);
			break;
		case 24:
			playAudio(R.raw.a24, true);
			break;
		case 25:
			playAudio(R.raw.a25, true);
			break;
		case 26:
			playAudio(R.raw.a26, true);
			break;
		case 27:
			playAudio(R.raw.a27, true);
			break;
		case 28:
			playAudio(R.raw.a28, true);
			break;
		case 29:
			playAudio(R.raw.a29, true);
			break;
		case 30:
			playAudio(R.raw.a30, true);
			break;
		case 31:
			playAudio(R.raw.a31, true);
			break;

		default:
			break;
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
