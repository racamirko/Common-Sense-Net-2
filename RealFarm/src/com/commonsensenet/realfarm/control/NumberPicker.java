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

		private double inc = 0.1;

		public RepetetiveUpdater(double increment) {
			this.inc = increment;
		}

		public void run() {
			if (autoIncrement) {
				increment(inc);
				handler.postDelayed(new RepetetiveUpdater(inc), REPEAT_DELAY);
			}
		}
	}

	private static final long REPEAT_DELAY = 100;

	private boolean autoIncrement = false;
	private double curNb = 10;
	private int digitNb = 0;
	private Handler handler = new Handler();
	private double increment = 1;
	private double max = 20;

	private double min = 0;
	private ImageButton minus;
	private TextView number;

	private DecimalFormat oneDigit = new DecimalFormat("#,###0.0");
	private ImageButton plus;

	private DecimalFormat twoDigits = new DecimalFormat("#,###0.00");

	public NumberPicker(Context context) {
		super(context);
	}

	public NumberPicker(Context context, double min, double max, double curNb,
			double increment, int digitNb) {
		super(context);

		inflate(context, R.layout.numberpicker, this);

		this.min = min;
		this.max = max;
		this.curNb = curNb;
		this.increment = increment;
		this.digitNb = digitNb;

		minus = (ImageButton) findViewById(R.id.button1);
		plus = (ImageButton) findViewById(R.id.button2);
		number = (TextView) findViewById(R.id.tw);

		plus.setOnClickListener(this);
		minus.setOnClickListener(this);
		plus.setOnTouchListener(this);
		minus.setOnTouchListener(this);
		plus.setOnLongClickListener(this);
		minus.setOnLongClickListener(this);

		increment(0);
	}

	public void increment(double inc) {
		double tmp = curNb + inc;

		plus.setImageResource(R.drawable.incactionactive);
		minus.setImageResource(R.drawable.decactionactive);

		if (tmp >= max) {
			plus.setImageResource(R.drawable.incactioninactive);
			plus.setPressed(false);
		} else if (tmp <= min) {
			minus.setImageResource(R.drawable.decactioninactive);
			minus.setPressed(false);
		}

		if (tmp > max) {
			autoIncrement = false;
		} else if (tmp < min) {
			autoIncrement = false;
		} else {
			curNb = tmp;
			if (digitNb == 0)
				number.setText((int) (curNb) + "");
			else if (digitNb == 1)
				number.setText(Double.valueOf(oneDigit.format(curNb)) + "");
			else
				number.setText(Double.valueOf(twoDigits.format(curNb)) + "");
		}
	}

	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			increment(-increment);
		} else if (v.getId() == R.id.button2) {
			increment(increment);
		}
	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button1) {
			autoIncrement = true;
			handler.post(new RepetetiveUpdater(-increment));
		} else if (v.getId() == R.id.button2) {
			autoIncrement = true;
			handler.post(new RepetetiveUpdater(increment));
		}

		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			autoIncrement = false;
		}

		return false;
	}

}
