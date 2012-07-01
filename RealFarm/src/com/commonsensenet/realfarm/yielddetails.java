package com.commonsensenet.realfarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.aggregates.fertilize_aggregate;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnDataChangeListener;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class yielddetails extends HelpEnabledActivity implements
		OnDataChangeListener {

	private int[] arr = new int[2];
	private final Context context = this;
	private ImageView img_1;
	private ImageView img_2;
	private RealFarmProvider mDataProvider;
	private int mpvalue, wfvalue;
	private TextView text_1;
	private TextView text_2;
	private TextView text_4;
	private TextView text_5;
	private String unit = "Rs";
	 boolean sel_1, sel_2, sel_3, sel_4, sel_5, sel_6 ;
	public void onBackPressed() {

		Intent adminintent = new Intent(yielddetails.this,
				Homescreen.class);

		startActivity(adminintent);
		yielddetails.this.finish();

		// eliminates the listener.
		mDataProvider.setWFDataChangeListener(null);

		SoundQueue sq = SoundQueue.getInstance(); // audio integration
		sq.stop();
	}

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

		setContentView(R.layout.yielddetails);
		
		final Button smiley1 = (Button) findViewById(R.id.home_btn_yield_sel_1);
		final	Button smiley2 = (Button) findViewById(R.id.home_btn_yield_sel_2);
		final	Button smiley3 = (Button) findViewById(R.id.home_btn_yield_sel_3);
		final	Button smiley4 = (Button) findViewById(R.id.home_btn_yield_sel_4);
		final	Button smiley5 = (Button) findViewById(R.id.home_btn_yield_sel_5);
		final	Button smiley6 = (Button) findViewById(R.id.home_btn_yield_sel_6);
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
			sel_1 =true;
		} else {
			smiley1.setBackgroundResource(R.drawable.smiley_good_not);
			sel_1 =false;
		}
			}
		});
		
		
		smiley2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		if (!sel_2) {
			smiley2.setBackgroundResource(R.drawable.smiley_good);
			sel_2 =true;
		} else {
			smiley2.setBackgroundResource(R.drawable.smiley_good_not);
			sel_2 =false;
		}
			}
		});
		
		smiley3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		if (!sel_3) {
			smiley3.setBackgroundResource(R.drawable.smiley_good);
			sel_3 =true;
		} else {
			smiley3.setBackgroundResource(R.drawable.smiley_good_not);
			sel_3 =false;
		}
			}
		});
		
		
		smiley4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		if (!sel_4) {
			smiley4.setBackgroundResource(R.drawable.smiley_good);
			sel_4 =true;
		} else {
			smiley4.setBackgroundResource(R.drawable.smiley_good_not);
			sel_4 =false;
		}
			}
		});
		
		smiley5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		if (!sel_5) {
			smiley5.setBackgroundResource(R.drawable.smiley_good);
			sel_5 =true;
		} else {
			smiley5.setBackgroundResource(R.drawable.smiley_good_not);
			sel_5 =false;
		}
			}
		});
		
		smiley6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		if (!sel_6) {
			smiley6.setBackgroundResource(R.drawable.smiley_good);
			sel_6 =true;
		} else {
			smiley6.setBackgroundResource(R.drawable.smiley_good_not);
			sel_6 =false;
		}
			}
		});
		
		Button back = (Button) findViewById(R.id.back);
	    back.setOnLongClickListener(this);
	
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user selected cancel in harvest*********** \r\n");

				}
			}

		});
		
		
		
		
		
/*
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
	public boolean onLongClick(View v) { // latest

		if (v.getId() == R.id.aggr_img_help) { // Audio integration

			playAudio(R.raw.help);

		}

		if (v.getId() == R.id.home_btn_mp_1) {

			System.out.println(wfvalue);

			playAudio(111); // "111" corresponds to ckpura

		}

		if (v.getId() == R.id.home_btn_gn_good_mp) {

			playAudio(11); // "11" corresponds to good

		}
		if (v.getId() == R.id.home_btn_gn_medium_mp) {

			playAudio(12); // "12" corresponds to medium

		}
		if (v.getId() == R.id.home_btn_gn_poor_mp) {

			playAudio(13); // "13" corresponds to poor

		}

		return true;
	}

	public void playAudio(int Currentvalue) // Audio integration
	{
		if (Global.enableAudio) // checking for audio enable
		{
		
		System.out.println("play audio called");
		System.out.println(Currentvalue);

		SoundQueue sq = SoundQueue.getInstance();
		sq.stop();
		if (Global.enableAudio == true) // checking for audio enable
		{
			if (Currentvalue == 111) {
				// sq.addToQueue(R.raw.seekepura1);

				// sq.addToQueue(R.raw.a4000);

				sq.addToQueue(R.raw.todayinpavagdamarket);
				sq.addToQueue(R.raw.a1);
				sq.addToQueue(R.raw.quintals);
				sq.addToQueue(R.raw.groundnut1);
				sq.addToQueue(R.raw.value);
				sq.addToQueue(R.raw.a4800);
				sq.addToQueue(R.raw.rupees2);

				sq.play();
			}

			if (Currentvalue == 11) // 11
			{
				// SoundQueue sq = SoundQueue.getInstance();
				sq.addToQueue(R.raw.goodqualityprice1);

				sq.addToQueue(R.raw.a8000);
				sq.addToQueue(R.raw.rupees2);

				sq.play();
			}
			if (Currentvalue == 12) // 12
			{
				// SoundQueue sq = SoundQueue.getInstance();
				sq.addToQueue(R.raw.mediumqualityprice1);

				sq.addToQueue(R.raw.a7000);
				sq.addToQueue(R.raw.rupees2);

				sq.play();
			}

			if (Currentvalue == 13) {
				// SoundQueue sq = SoundQueue.getInstance();
				sq.addToQueue(R.raw.poorqualityprice1); // 13

				sq.addToQueue(R.raw.a6000);
				sq.addToQueue(R.raw.rupees2);

				sq.play();
			}

			if (Currentvalue == 1000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a1000);

				sq.play();
			}
			if (Currentvalue == 2000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a2000);
				sq.play();
			}
			if (Currentvalue == 3000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a3000);
				sq.play();
			}
			if (Currentvalue == 4000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a4000);
				sq.play();
			}
			if (Currentvalue == 5000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a5000);
				sq.play();
			}
			if (Currentvalue == 6000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a6000);
				sq.play();
			}
			if (Currentvalue == 7000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a7000);
				sq.play();
			}
			if (Currentvalue == 8000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a8000);
				sq.play();
			}
			
			if (Currentvalue == 10000) {
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.a10000);
				sq.play();
			}

			if (Currentvalue == R.raw.help) // added
			{
				// SoundQueue sq = SoundQueue.getInstance();

				sq.addToQueue(R.raw.help);
				sq.play();
			}

			// sq.addToQueue(Currentvalue);
			// sq.play();

		}
	}
	}*/
}
	
protected void cancelaudio() {
		

		Intent adminintent = new Intent(yielddetails.this, Homescreen.class);

		startActivity(adminintent);
		yielddetails.this.finish();
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
}
