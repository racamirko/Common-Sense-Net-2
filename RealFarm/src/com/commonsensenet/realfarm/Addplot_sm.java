package com.commonsensenet.realfarm;

import com.commonsensenet.realfarm.R;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.model.User;

public class Addplot_sm extends HelpEnabledActivity {

	final Context context = this;
	protected RealFarmProvider mDataProvider;
	public int Position; // Has copy of mainlistview position

	public User ReadUser = null;

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}

		Intent adminintent123 = new Intent(Addplot_sm.this, Homescreen.class);
		startActivity(adminintent123);
		Addplot_sm.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addplot_sm);
		System.out.println("In My_setting_plot_info call");

		mDataProvider = RealFarmProvider.getInstance(context); // Working

		Button AddPlot = (Button) findViewById(R.id.AddPlot);
		Button SMvalue = (Button) findViewById(R.id.SMvalue);

		AddPlot.setOnLongClickListener(this); // Integration
		SMvalue.setOnLongClickListener(this);

		ImageButton home;
		ImageButton help;

		home = (ImageButton) findViewById(R.id.aggr_img_home1);
		help = (ImageButton) findViewById(R.id.aggr_img_help1);
		help.setOnLongClickListener(this);

		// add the event listeners
		AddPlot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");

				Global.CallToPlotInfo = 1;
				Intent adminintent123 = new Intent(Addplot_sm.this,
						My_setting_plot_info.class);
				startActivity(adminintent123);
				Addplot_sm.this.finish();

			}
		});

		// add the event listeners
		SMvalue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");

				Global.actionno = 7;
				Intent adminintent123 = new Intent(Addplot_sm.this,
						Plot_Image.class);
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

	} // End of oncreate()

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.AddPlot) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.buttonpressplotinfo);
			mp.start();

		}

		if (v.getId() == R.id.SMvalue) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.buttonpresssoilmoisturevalue);
			mp.start();

		}
		if (v.getId() == R.id.aggr_img_help1) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();

		}

		return true;
	}

}
