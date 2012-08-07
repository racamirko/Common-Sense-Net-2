package com.commonsensenet.realfarm.control;

import java.text.DecimalFormat;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;

public class NumberPicker extends LinearLayout implements OnClickListener,
		OnLongClickListener, OnTouchListener {

	private class RepetetiveUpdater implements Runnable {

		private double mIncrement = 0.1;

		public RepetetiveUpdater(double increment) {
			mIncrement = increment;
		}

		public void run() {
			if (mAutoIncrement) {
				increment(mIncrement);
				mHandler.postDelayed(new RepetetiveUpdater(mIncrement),
						REPEAT_DELAY);
			}
		}
	}

	private static final DecimalFormat ONE_DIGIT_FORMAT = new DecimalFormat(
			"#,###0.0");
	private static final long REPEAT_DELAY = 100;
	private static final DecimalFormat TWO_DIGIT_FORMAT = new DecimalFormat(
			"#,###0.00");

	private boolean mAutoIncrement = false;
	private double mCurNb = 10;
	private int mDigitNb = 0;
	private Handler mHandler = new Handler();
	private double mIncrementStep = 1;
	private double mMax = 20;
	private double mMin = 0;
	private ImageButton mMinusButton;
	private TextView mNumberTextView;
	private ImageButton mPlusButton;

	public NumberPicker(Context context) {
		super(context);
	}

	public NumberPicker(Context context, double min, double max, double curNb,
			double increment, int digitNb) {
		super(context);

		inflate(context, R.layout.numberpicker, this);

		mMin = min;
		mMax = max;
		mCurNb = curNb;
		mIncrementStep = increment;
		mDigitNb = digitNb;

		mMinusButton = (ImageButton) findViewById(R.id.button1);
		mPlusButton = (ImageButton) findViewById(R.id.button2);
		mNumberTextView = (TextView) findViewById(R.id.tw);

		mPlusButton.setOnClickListener(this);
		mMinusButton.setOnClickListener(this);
		mPlusButton.setOnTouchListener(this);
		mMinusButton.setOnTouchListener(this);
		mPlusButton.setOnLongClickListener(this);
		mMinusButton.setOnLongClickListener(this);

		increment(0);
	}

	public void increment(double inc) {
		double tmp = mCurNb + inc;

		mPlusButton.setImageResource(R.drawable.incactionactive);
		mMinusButton.setImageResource(R.drawable.decactionactive);

		if (tmp >= mMax) {
			mPlusButton.setImageResource(R.drawable.incactioninactive);
			mPlusButton.setPressed(false);
		} else if (tmp <= mMin) {
			mMinusButton.setImageResource(R.drawable.decactioninactive);
			mMinusButton.setPressed(false);
		}

		if (tmp > mMax) {
			mAutoIncrement = false;
		} else if (tmp < mMin) {
			mAutoIncrement = false;
		} else {
			mCurNb = tmp;
			if (mDigitNb == 0)
				mNumberTextView.setText((int) (mCurNb) + "");
			else if (mDigitNb == 1)
				mNumberTextView.setText(Double.valueOf(ONE_DIGIT_FORMAT
						.format(mCurNb)) + "");
			else
				mNumberTextView.setText(Double.valueOf(TWO_DIGIT_FORMAT
						.format(mCurNb)) + "");
		}
	}

	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			increment(-mIncrementStep);
		} else if (v.getId() == R.id.button2) {
			increment(mIncrementStep);
		}
	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button1) {
			mAutoIncrement = true;
			mHandler.post(new RepetetiveUpdater(-mIncrementStep));
		} else if (v.getId() == R.id.button2) {
			mAutoIncrement = true;
			mHandler.post(new RepetetiveUpdater(mIncrementStep));
		}

		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			mAutoIncrement = false;
		}

		return false;
	}

}
