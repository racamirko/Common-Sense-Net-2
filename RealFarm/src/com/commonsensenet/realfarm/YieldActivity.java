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

	public static final String LOG_TAG = "YieldDetailsActivity";

	public void onCreate(Bundle savedInstanceState) {

		mDataProvider = RealFarmProvider.getInstance(context);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.yielddetails);



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
