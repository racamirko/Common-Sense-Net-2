package com.commonsensenet.realfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.Toast;

import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

public class SM_enter extends HelpEnabledActivity {

	public void onBackPressed() {

		stopaudio();
		Intent adminintent123 = new Intent(SM_enter.this,
				ChoosePlotActivity.class);

		startActivity(adminintent123);
		SM_enter.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smenter_dialog);
		System.out.println("SM eneter");

		ImageButton home1;
		ImageButton help1;
		home1 = (ImageButton) findViewById(R.id.aggr_img_home1);
		help1 = (ImageButton) findViewById(R.id.aggr_img_help1);
		help1.setOnLongClickListener(this);

		home1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(SM_enter.this,
						Homescreen.class);
				startActivity(adminintent123);
				SM_enter.this.finish();

			}
		});

		Button btnNext = (Button) findViewById(R.id.sm_enter_ok);

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				NumberPicker mynp1 = (NumberPicker) findViewById(R.id.sm_enter_val);
				int sm_enter_val = mynp1.getValue();
				String sm_enter_no = String.valueOf(sm_enter_val);

				Toast.makeText(SM_enter.this,
						"User enetred " + sm_enter_no + " %", Toast.LENGTH_LONG)
						.show();
				int flag1;
				if (sm_enter_val == 0) {
					flag1 = 1;

					TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img_not);

				} else {
					flag1 = 0;

					TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);

					tr_feedback.setBackgroundResource(R.drawable.def_img);
				}

				if (flag1 == 0) {

					Intent adminintent = new Intent(SM_enter.this,
							Homescreen.class);

					startActivity(adminintent);
					SM_enter.this.finish();

				} else
					initmissingval();

			}
		});

	} // End of oncreate()

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help);
		}

		return true;
	}
}
