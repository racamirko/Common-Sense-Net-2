package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnDataChangeListener;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WFList;

public class Marketprice_details extends HelpEnabledActivity implements
		OnDataChangeListener {

	protected RealFarmProvider mDataProvider;
	public User ReadUser = null;
	public int Position; // Has copy of mainlistview position
	private int mpvalue, wfvalue;
	private String unit = "Rs";
	protected List<WFList> Wftodaydata;
	protected int wfsize;
	final Context context = this;

	public void onBackPressed() {

		Intent adminintent = new Intent(Marketprice_details.this,
				Homescreen.class);

		startActivity(adminintent);
		Marketprice_details.this.finish();

		// eliminates the listener.
		mDataProvider.setWFDataChangeListener(null);
	}

	TextView text_1;
	TextView text_2;
	TextView text_4;
	TextView text_5;
	ImageView img_1;
	ImageView img_2;
	int[] arr = new int[2];
	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		System.out.println("WF details entered");
		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.wf_details);
		// RelativeLayout relLay = (RelativeLayout)
		// findViewById(R.id.RelativeLayout93);
		// setContentView(R.id.linearLayout19);

		mDataProvider = RealFarmProvider.getInstance(context);
		mDataProvider.setWFDataChangeListener(this);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.mp_details);

		// home_btn_gn_good_mp

		final Button GN_good;
		final Button GN_medium;
		final Button GN_poor;

		final Button Today_gnut;
		// final Button Today_castor;

		ImageButton home;
		ImageButton help;

		GN_good = (Button) findViewById(R.id.home_btn_gn_good_mp);
		GN_medium = (Button) findViewById(R.id.home_btn_gn_medium_mp);
		GN_poor = (Button) findViewById(R.id.home_btn_gn_poor_mp);
		Today_gnut = (Button) findViewById(R.id.home_btn_mp_1);// btn
		// Today_castor = (Button) findViewById(R.id.home_btn_mp_2);

		home = (ImageButton) findViewById(R.id.aggr_img_home1);
		help = (ImageButton) findViewById(R.id.aggr_img_help);

		GN_good.setOnLongClickListener(this); // Integration
		GN_medium.setOnLongClickListener(this);
		GN_poor.setOnLongClickListener(this);
		Today_gnut.setOnLongClickListener(this);
		// Today_castor.setOnLongClickListener(this);

		help.setOnLongClickListener(this);

		GN_good.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

		GN_medium.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

		GN_poor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

		TextView text_1;
		// TextView text_2;

		text_1 = (TextView) findViewById(R.id.mp_text1);
		// text_2 = (TextView) findViewById(R.id.mp_text2);

		mpvalue = 4800;
		text_1.setText(mpvalue + unit);
		// text_4.setText(wfvalue1 + unit);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(Marketprice_details.this,
						Homescreen.class);

				startActivity(adminintent);
				Marketprice_details.this.finish();

			}
		});

	}

	public void onDataChanged(int WF_Size, String WF_Date, int WF_Value,
			String WF_Type, String WF_Date1, int WF_Value1, String WF_Type1,
			int WF_adminflag) {

		System.out.println("WF details updating");
		text_1 = (TextView) findViewById(R.id.wf_text1);
		text_2 = (TextView) findViewById(R.id.wf_text2);
		text_4 = (TextView) findViewById(R.id.wf_text4);
		text_5 = (TextView) findViewById(R.id.wf_text5);

		img_1 = (ImageView) findViewById(R.id.wfimg_1);
		img_2 = (ImageView) findViewById(R.id.wfimg_2);

		arr[0] = R.drawable.sunny;
		arr[1] = R.drawable.rain;

		wfvalue = WF_Value;
		final String wftype = WF_Type;
		final int wfvalue1 = WF_Value1;
		final String wftype1 = WF_Type1;

		System.out.println("changing layout of WF");
		// getWindow().setTitle("Weather Forecast"); //
		// setBackgroundDrawableResource(arr[0]);

		// based on Type change the img1, img2

		img_1.setImageResource(R.drawable.sunny);
		img_2.setImageResource(R.drawable.rain);

		// change the temperature values
		text_1.setText(wfvalue + unit);
		text_4.setText(wfvalue1 + unit);

		// Change the weather forcast msg

		text_2.setText(wftype);
		text_5.setText(wftype1);

		System.out.println("WF details finished updating");

	}

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.home_btn_mp_1) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.seekepura1);
			mp.start();

		}

		/*
		 * if( v.getId() == R.id.home_btn_mp_2){
		 * 
		 * if(mp != null) { mp.stop(); mp.release(); mp = null; } mp =
		 * MediaPlayer.create(this, R.raw.seekepura1); mp.start();
		 * 
		 * }
		 */
		if (v.getId() == R.id.home_btn_gn_good_mp) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.goodqualityprice1);
			mp.start();

		}

		if (v.getId() == R.id.home_btn_gn_medium_mp) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.mediumqualityprice1);
			mp.start();

		}
		if (v.getId() == R.id.home_btn_gn_poor_mp) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.poorqualityprice1);
			mp.start();

		}

		if (v.getId() == R.id.aggr_img_help) {

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
