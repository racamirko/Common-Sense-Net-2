package com.commonsensenet.realfarm.control;

import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class kannadaNumberPicker extends LinearLayout {

	private class RepetetiveUpdater implements Runnable {
		public void run() {
			if (mAutoIncrement) {
				increment();
				mRepeatUpdateHandler.postDelayed(new RepetetiveUpdater(),
						REPEAT_DELAY);
			} else if (mAutoDecrement) {
				decrement();
				mRepeatUpdateHandler.postDelayed(new RepetetiveUpdater(),
						REPEAT_DELAY);
			}
		}
	}

	private static final int ELEMENT_HEIGHT = 70;
	private static final int ELEMENT_WIDTH = 50;
	private static final int MAXIMUM = 999;
	private static final int MINIMUM = 0;
	private static final long REPEAT_DELAY = 50;
	private static final int TEXT_SIZE = 25;

	private boolean mAutoDecrement;
	private boolean mAutoIncrement;
	private Button mDecrement;
	private Button mIncrement;
	private Handler mRepeatUpdateHandler;
	private Integer mValue;
	private EditText mValueText;

	public kannadaNumberPicker(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		// sets the default values.
		mAutoDecrement = false;
		mAutoIncrement = false;
		mRepeatUpdateHandler = new Handler();

		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LayoutParams elementParams = new LinearLayout.LayoutParams(
				ELEMENT_HEIGHT, ELEMENT_WIDTH);

		// init the individual elements
		initDecrementButton(context);
		initValueEditText(context);
		initIncrementButton(context);

		// Can be configured to be vertical or horizontal
		// Thanks for the help, LinearLayout!
		if (getOrientation() == VERTICAL) {
			addView(mIncrement, elementParams);
			addView(mValueText, elementParams);
			addView(mDecrement, elementParams);
		} else {
			addView(mDecrement, elementParams);
			addView(mValueText, elementParams);
			addView(mIncrement, elementParams);
		}
	}

	public void decrement() {
		if (mValue > MINIMUM) {
			mValue--;
			mValueText.setText(mValue.toString());
		}
	}

	public int getValue() {
		return mValue;
	}

	public void increment() {
		if (mValue < MAXIMUM) {
			mValue++;
			mValueText.setText(mValue.toString());
		}
	}

	private void initDecrementButton(Context context) {
		mDecrement = new Button(context);
		mDecrement.setTextSize(TEXT_SIZE);
		mDecrement.setText("-");

		// Decrement once for a click
		mDecrement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				decrement();
			}
		});

		// Auto Decrement for a long click
		mDecrement.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				mAutoDecrement = true;
				mRepeatUpdateHandler.post(new RepetetiveUpdater());
				return false;
			}
		});

		// When the button is released, if we're auto decrementing, stop
		mDecrement.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP
						&& mAutoDecrement) {
					mAutoDecrement = false;
				}
				return false;
			}
		});
	}

	private void initIncrementButton(Context context) {
		mIncrement = new Button(context);
		mIncrement.setTextSize(TEXT_SIZE);
		mIncrement.setText("+");

		// Increment once for a click
		mIncrement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				increment();
			}
		});

		// Auto increment for a long click
		mIncrement.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				mAutoIncrement = true;
				mRepeatUpdateHandler.post(new RepetetiveUpdater());
				return false;
			}
		});

		// When the button is released, if we're auto incrementing, stop
		mIncrement.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP
						&& mAutoIncrement) {
					mAutoIncrement = false;
				}
				return false;
			}
		});
	}

	private void initValueEditText(Context context) {

		mValue = new Integer(0);

		mValueText = new EditText(context);
		mValueText.setTextSize(TEXT_SIZE);
		mValueText.setEnabled(false);

		mValueText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int arg1, KeyEvent event) {
				int backupValue = mValue;
				try {
					mValue = Integer.parseInt(((EditText) v).getText()
							.toString());
				} catch (NumberFormatException nfe) {
					mValue = backupValue;
				}
				return false;
			}
		});

		// Highlight the number when we get focus
		mValueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					((EditText) v).selectAll();
				}
			}
		});
		mValueText.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		mValueText.setText(mValue.toString());
		mValueText.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	public void setValue(int value) {
		// checks that it does not exceed the maximum
		if (value > MAXIMUM) {
			value = MAXIMUM;
		}
		// and the minimum
		if (value < MINIMUM) {
			value = MINIMUM;
		}

		// sets the value
		mValue = value;
		mValueText.setText(mValue.toString());
	}
}
