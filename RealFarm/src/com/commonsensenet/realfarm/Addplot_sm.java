package com.commonsensenet.realfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class Addplot_sm extends HelpEnabledActivity {
	
	public void onBackPressed() {
		SoundQueue.getInstance().stop();

		Intent adminintent123 = new Intent(Addplot_sm.this, Homescreen.class);
		startActivity(adminintent123);
		Addplot_sm.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplot_sm);

		Button btnAddPlot = (Button) findViewById(R.id.button_add_plot);
		Button btnSoilMoisture = (Button) findViewById(R.id.button_soil_moisture);

		btnAddPlot.setOnLongClickListener(this);
		btnSoilMoisture.setOnLongClickListener(this);

		ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home1);
		ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help1);
		help.setOnLongClickListener(this);

		// add the event listeners
		btnAddPlot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Global.CallToPlotInfo = 1;
				Intent adminintent123 = new Intent(Addplot_sm.this,
						My_settings_plot_details.class); // My_settings_plot_info
				startActivity(adminintent123);
				Addplot_sm.this.finish();

			}
		});

		// add the event listeners
		btnSoilMoisture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Global.actionno = 7;
				Intent adminintent123 = new Intent(Addplot_sm.this,
						ChoosePlotActivity.class);
				startActivity(adminintent123);
				Addplot_sm.this.finish();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(Addplot_sm.this,
						Homescreen.class);
				startActivity(adminintent123);
				Addplot_sm.this.finish();
			}
		});
	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.button_add_plot) {
			playAudio(R.raw.buttonpressplotinfo);
		} else if (v.getId() == R.id.button_soil_moisture) {
			playAudio(R.raw.buttonpresssoilmoisturevalue);
		} else if (v.getId() == R.id.aggr_img_help1) {
			playAudio(R.raw.help);
		}

		return true;
	}
}
