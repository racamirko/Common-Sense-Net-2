package com.commonsensenet.realfarm.actions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;

public class action_plant extends Activity {

	public void onBackPressed() {

		Intent adminintent = new Intent(action_plant.this, Homescreen.class);

		startActivity(adminintent);
		action_plant.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Plant details entered");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.harvest_dialog);
		System.out.println("plant done");

		final Button smiley1;
		final Button smiley2;
		final Button smiley3;
		smiley1 = (Button) findViewById(R.id.home_btn_har_1); /* Smileys are now disabled */
		smiley2 = (Button) findViewById(R.id.home_btn_har_1);
		smiley3 = (Button) findViewById(R.id.home_btn_har_1);

		smiley1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				smiley1.setBackgroundResource(R.drawable.smiley_good);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
			}
		});

		smiley2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
			}
		});

		smiley3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				smiley1.setBackgroundResource(R.drawable.smiley_good_not);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad);
			}
		});

	}
}