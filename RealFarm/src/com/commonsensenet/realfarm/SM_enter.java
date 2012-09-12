package com.commonsensenet.realfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.control.NumberPicker;

public class SM_enter extends HelpEnabledActivity {

	public void onBackPressed() {

		stopAudio();
		Intent adminintent123 = new Intent(SM_enter.this,
				ChoosePlotActivity.class);

		startActivity(adminintent123);
		SM_enter.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smenter_dialog);
		System.out.println("SM enter");

		ImageButton home1;
		ImageButton help1;
		home1 = (ImageButton) findViewById(R.id.aggr_img_home);
		help1 = (ImageButton) findViewById(R.id.aggr_img_help);
		help1.setOnLongClickListener(this);

		home1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(SM_enter.this,
						Homescreen.class);
				startActivity(adminintent123);
				SM_enter.this.finish();

			}
		});

		TableRow tr = (TableRow) findViewById(R.id.sm_enter_tr);
		NumberPicker np = new NumberPicker(SM_enter.this, 0, 100, 50, 1, 0);
		tr.addView(np);

		final TextView tw = (TextView) findViewById(R.id.tw);
		tw.setOnLongClickListener(this);

		ImageButton ok = (ImageButton) findViewById(R.id.ok);
		ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
		ok.setVisibility(View.GONE);
		cancel.setVisibility(View.GONE);

		Button btnNext = (Button) findViewById(R.id.button_ok);

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String sm_enter_no = String.valueOf(tw.getText().toString());

				Toast.makeText(SM_enter.this,
						"User enetred " + sm_enter_no + " %", Toast.LENGTH_LONG)
						.show();
				int flag1;
				if (sm_enter_no.equals("0")) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);
					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0) {
					startActivity(new Intent(SM_enter.this, Homescreen.class));
					SM_enter.this.finish();

				} else {
					playAudio(R.raw.missinginfo);
				}
			}
		});
	}

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.help);
		} else if (v.getId() == R.id.tw) {
			playAudio(R.raw.help);
		} else {
			return super.onLongClick(v);
		}

		return true;
	}
}
