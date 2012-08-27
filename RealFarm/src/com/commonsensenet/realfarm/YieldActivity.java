package com.commonsensenet.realfarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class YieldActivity extends HelpEnabledActivityOld {

	private final Context context = this;
	private RealFarmProvider mDataProvider;
	private boolean sel_1;
	private boolean sel_2;
	private boolean sel_3;
	private boolean sel_4;
	private boolean sel_5;
	private boolean sel_6;

	public static final String LOG_TAG = "YieldDetailsActivity";

	public void onCreate(Bundle savedInstanceState) {
		System.out.println("WF details entered");
		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.wf_details);
		// RelativeLayout relLay = (RelativeLayout)
		// findViewById(R.id.RelativeLayout93);
		// setContentView(R.id.linearLayout19);

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.yielddetails);

		final Button smiley1 = (Button) findViewById(R.id.home_btn_yield_sel_1);
		final Button smiley2 = (Button) findViewById(R.id.home_btn_yield_sel_2);
		final Button smiley3 = (Button) findViewById(R.id.home_btn_yield_sel_3);
		final Button smiley4 = (Button) findViewById(R.id.home_btn_yield_sel_4);
		final Button smiley5 = (Button) findViewById(R.id.home_btn_yield_sel_5);
		final Button smiley6 = (Button) findViewById(R.id.home_btn_yield_sel_6);
		smiley1.setBackgroundResource(R.drawable.smiley_good_not);
		smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
		smiley4.setBackgroundResource(R.drawable.smiley_good_not);
		smiley5.setBackgroundResource(R.drawable.smiley_medium_not);
		smiley6.setBackgroundResource(R.drawable.smiley_bad_not);

		smiley1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_1) {
					smiley1.setBackgroundResource(R.drawable.smiley_good);
					sel_1 = true;
				} else {
					smiley1.setBackgroundResource(R.drawable.smiley_good_not);
					sel_1 = false;
				}
			}
		});

		smiley2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_2) {
					smiley2.setBackgroundResource(R.drawable.smiley_good);
					sel_2 = true;
				} else {
					smiley2.setBackgroundResource(R.drawable.smiley_good_not);
					sel_2 = false;
				}
			}
		});

		smiley3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_3) {
					smiley3.setBackgroundResource(R.drawable.smiley_good);
					sel_3 = true;
				} else {
					smiley3.setBackgroundResource(R.drawable.smiley_good_not);
					sel_3 = false;
				}
			}
		});

		smiley4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_4) {
					smiley4.setBackgroundResource(R.drawable.smiley_good);
					sel_4 = true;
				} else {
					smiley4.setBackgroundResource(R.drawable.smiley_good_not);
					sel_4 = false;
				}
			}
		});

		smiley5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_5) {
					smiley5.setBackgroundResource(R.drawable.smiley_good);
					sel_5 = true;
				} else {
					smiley5.setBackgroundResource(R.drawable.smiley_good_not);
					sel_5 = false;
				}
			}
		});

		smiley6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sel_6) {
					smiley6.setBackgroundResource(R.drawable.smiley_good);
					sel_6 = true;
				} else {
					smiley6.setBackgroundResource(R.drawable.smiley_good_not);
					sel_6 = false;
				}
			}
		});

		Button back = (Button) findViewById(R.id.button_back);
		back.setOnLongClickListener(this);

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "back");
			}

		});
		
	}

	protected void cancelaudio() {

		Intent adminintent = new Intent(YieldActivity.this, Homescreen.class);
		startActivity(adminintent);
		YieldActivity.this.finish();
	}
}
