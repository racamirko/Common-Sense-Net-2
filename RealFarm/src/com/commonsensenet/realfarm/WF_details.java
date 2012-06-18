package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
import com.commonsensenet.realfarm.utils.SoundQueue;

public class WF_details extends HelpEnabledActivity implements
		OnDataChangeListener {
	int[] arr = new int[2];
	final Context context = this;
	int i = 0;
	ImageView img_1;
	ImageView img_2;
	/** View where the items are displayed. */

	protected RealFarmProvider mDataProvider;
	String name;
	public int Position; // Has copy of mainlistview position
	public User ReadUser = null;

	// MediaPlayer mp = null;

	TextView text_1;

	TextView text_2;
	TextView text_4;
	TextView text_5;
	String unit = "¡C";
	protected int wfsize;
	protected List<WFList> Wftodaydata;
	int wfvalue;

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {

		Intent adminintent = new Intent(WF_details.this, Homescreen.class);

		startActivity(adminintent);
		WF_details.this.finish();

		// eliminates the listener.
		mDataProvider.setWFDataChangeListener(null);
		
		SoundQueue sq = SoundQueue.getInstance();    //audio integration
		sq.stop();        //audio integration
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

		setContentView(R.layout.wf_details);

		text_1 = (TextView) findViewById(R.id.wf_text1);
		text_2 = (TextView) findViewById(R.id.wf_text2);
		text_4 = (TextView) findViewById(R.id.wf_text4);
		text_5 = (TextView) findViewById(R.id.wf_text5);

		img_1 = (ImageView) findViewById(R.id.wfimg_1);
		img_2 = (ImageView) findViewById(R.id.wfimg_2);

		arr[0] = R.drawable.sun;
		arr[1] = R.drawable.lightrain;

		ImageButton home;
		ImageButton help;
		home = (ImageButton) findViewById(R.id.aggr_img_home);
		help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(WF_details.this,
						Homescreen.class);
				startActivity(adminintent123);
				WF_details.this.finish();

			}
		});

		final Button wf1;
		final Button wf2;

		final Button wf3;
		final Button wf4;
		final Button wf5;
	
		 wf1 = (Button) findViewById(R.id.home_btn_wf_1);                    //Audio Integration
		    wf2 = (Button) findViewById(R.id.home_btn_wf_2);
		    wf3 = (Button) findViewById(R.id.home_btn_wf_3);
		    wf4 = (Button) findViewById(R.id.home_btn_wf_4);
		    wf5 = (Button) findViewById(R.id.home_btn_wf_5);

		    wf1.setOnLongClickListener(this);                             //Audio Integration
		    wf2.setOnLongClickListener(this); 
		    wf3.setOnLongClickListener(this); 
		    wf4.setOnLongClickListener(this); 
		    wf5.setOnLongClickListener(this); 
		// fetch data from DB

		Wftodaydata = mDataProvider.getWFData();
		// Log.d("WFsize ","wfsize_str");
		wfsize = mDataProvider.getWFData().size() - 1;

		Log.d("WFsize ", "wfsize_str");

		int wfvalue = Wftodaydata.get(mDataProvider.getWFData().size() - 1)
				.getvalue();

		String wftype = Wftodaydata.get(mDataProvider.getWFData().size() - 1)
				.gettype();
		int wfvalue1 = Wftodaydata.get(mDataProvider.getWFData().size() - 1)
				.getvalue1();
		String wftype1 = Wftodaydata.get(mDataProvider.getWFData().size() - 1)
				.gettype1();
		Log.d("WFsize after", " wfsize");

		System.out.println("changing layout of WF");
		// getWindow().setTitle("Weather Forecast"); //
		// setBackgroundDrawableResource(arr[0]);

		// based on Type change the img1, img2

		img_1.setImageResource(R.drawable.sun);
		img_2.setImageResource(R.drawable.lightrain);

		// change the temperature values
		text_1.setText(wfvalue + unit);
		text_4.setText(wfvalue1 + unit);

		// Change the weather forcast msg

		text_2.setText(wftype);
		text_5.setText(wftype1);

		System.out.println("WF details finished updating");

		// wfhandler.updateWFData(10,"2324",25, "sgsg", "32",32, "sgsf", 1);

		/*
		 * final Handler handler = new Handler(); final Runnable r = new
		 * Runnable() { public void run() {
		 * 
		 * System.out.println("WF details updating"); text_1 = (TextView)
		 * findViewById(R.id.wf_text1); text_2 = (TextView)
		 * findViewById(R.id.wf_text2); text_4 = (TextView)
		 * findViewById(R.id.wf_text4); text_5 = (TextView)
		 * findViewById(R.id.wf_text5);
		 * 
		 * img_1 = (ImageView) findViewById(R.id.wfimg_1); img_2 = (ImageView)
		 * findViewById(R.id.wfimg_2);
		 * 
		 * arr[0] = R.drawable.sunny; arr[1] = R.drawable.rain;
		 * 
		 * // fetch data from DB
		 * 
		 * Wftodaydata = mDataProvider.getWFData(); //
		 * Log.d("WFsize ","wfsize_str"); wfsize =
		 * mDataProvider.getWFData().size() - 1;
		 * 
		 * Log.d("WFsize ", "wfsize_str");
		 * 
		 * int wfvalue = Wftodaydata.get( mDataProvider.getWFData().size() -
		 * 1).getvalue();
		 * 
		 * String wftype = Wftodaydata.get( mDataProvider.getWFData().size() -
		 * 1).gettype(); int wfvalue1 = Wftodaydata.get(
		 * mDataProvider.getWFData().size() - 1).getvalue1(); String wftype1 =
		 * Wftodaydata.get( mDataProvider.getWFData().size() - 1).gettype1();
		 * Log.d("WFsize after", " wfsize");
		 * 
		 * System.out.println("changing layout of WF"); //
		 * getWindow().setTitle("Weather Forecast"); // //
		 * setBackgroundDrawableResource(arr[0]);
		 * 
		 * // based on Type change the img1, img2
		 * 
		 * img_1.setImageResource(R.drawable.sunny);
		 * img_2.setImageResource(R.drawable.rain);
		 * 
		 * // change the temperature values text_1.setText(wfvalue + unit);
		 * text_4.setText(wfvalue1 + unit);
		 * 
		 * // Change the weather forcast msg
		 * 
		 * text_2.setText(wftype); text_5.setText(wftype1);
		 * 
		 * System.out.println("WF details finished updating");
		 * 
		 * handler.postDelayed(this, 10000);
		 * 
		 * } };
		 * 
		 * handler.postDelayed(r, 10000); Thread thread = new Thread() {
		 * 
		 * @Override public void run() { try { while (true) { if (i == 2) { //
		 * finish(); i = 0; } sleep(10000); handler.post(r); // i++; } } catch
		 * (InterruptedException e) { e.printStackTrace(); } } };
		 */
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

		/*
		 * final Handler handler=new Handler(); final Runnable r = new
		 * Runnable() { public void run() {
		 * 
		 * System.out.println("changing layout of WF"); //
		 * getWindow().setTitle("Weather Forecast"); //
		 * setBackgroundDrawableResource(arr[0]);
		 * 
		 * //based on Type change the img1, img2
		 * 
		 * img_1.setImageResource(R.drawable.sunny);
		 * img_2.setImageResource(R.drawable.rain);
		 * 
		 * // change the temperature values text_1.setText(wfvalue+unit);
		 * text_4.setText(wfvalue1+unit);
		 * 
		 * //Change the weather forcast msg
		 * 
		 * text_2.setText(wftype); text_5.setText(wftype1);
		 * 
		 * handler.postDelayed(this, 60000);
		 * 
		 * } };
		 * 
		 * handler.postDelayed(r, 60000); Thread thread = new Thread() {
		 * 
		 * @Override public void run() { try { while(true) { if(i== 2){
		 * //finish(); i=0; } sleep(60000); handler.post(r); //i++; } } catch
		 * (InterruptedException e) { e.printStackTrace(); } } };
		 */

	}

	@Override
	public boolean onLongClick(View v) {

	/*	if (v.getId() == R.id.home_btn_wf_1) {               //Commented Audio integration

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.weatherforecast);
			mp.start();

		}

		if (v.getId() == R.id.home_btn_wf_2) {

			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.weatherforecast);
			mp.start();

		}*/


		 if( v.getId() ==R.id.aggr_img_help ){                 //Audio integration
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			 	
			 
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();
			 }
			
		}
		
if( v.getId() == R.id.home_btn_wf_1){                       
			
			System.out.println(wfvalue);
		//	playAudio(wfvalue);
			playAudio(28);
		}
		
       if( v.getId() == R.id.home_btn_wf_2){
			
    	   System.out.println(wfvalue);
    	//   playAudio(wfvalue);
    	   playAudio(22);
			
		}
       if( v.getId() == R.id.home_btn_wf_3){
			
    	   System.out.println(wfvalue);
    	//   playAudio(wfvalue);
    	   playAudio(18);
			
		}
       if( v.getId() == R.id.home_btn_wf_4){
			
    	   System.out.println(wfvalue);
    	//   playAudio(wfvalue);
    	   playAudio(22);
			
		}
       if( v.getId() == R.id.home_btn_wf_5){
			
    	   System.out.println(wfvalue);
    	//   playAudio(wfvalue);
    	   playAudio(20);
			
		}
       

		return true;
	}
	
	public void playAudio(int Currentvalue)                        //Audio Integartion
	{
	//	System.out.println("play audio called");
	//	System.out.println(Currentvalue);
		
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
		
		SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();
	
if(Global.EnableAudio==true)                        //checking for audio enable
{
		if(Currentvalue==1)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a1);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==2)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a2);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
			
		}
		if(Currentvalue==3)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a3);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==4)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a4);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==5)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a5);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==6)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a6);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==7)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a7);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==8)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a8);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==9)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a9);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		if(Currentvalue==10)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a10);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
		
		if(Currentvalue==11)
		{
			sq.addToQueue(R.raw.todayweatherforecast);
			sq.addToQueue(R.raw.a11);
			sq.addToQueue(R.raw.degree);
			sq.addToQueue(R.raw.and);
			sq.addToQueue(R.raw.weather);
			sq.addToQueue(R.raw.sunny);
			sq.play();
		}
	if(Currentvalue==12)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a12);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
		}
	if(Currentvalue==13)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a13);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==14)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a14);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==15)
	{
		
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a15);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==16)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a16);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==17)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a17);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==18)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a18);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.stormy);
		sq.play();
	}
	if(Currentvalue==19)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a19);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==20)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a20);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.overcast);
		sq.play();
	}
	if(Currentvalue==21)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a21);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==22)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a22);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.lightshowers);
		sq.play();
	}
	if(Currentvalue==23)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a23);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==24)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a24);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==25)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a25);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==26)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a26);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==27)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a27);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==28)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a28);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==29)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a29);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
	if(Currentvalue==30)
	{
		sq.addToQueue(R.raw.todayweatherforecast);
		sq.addToQueue(R.raw.a30);
		sq.addToQueue(R.raw.degree);
		sq.addToQueue(R.raw.and);
		sq.addToQueue(R.raw.weather);
		sq.addToQueue(R.raw.sunny);
		sq.play();
	}
}
	}


}
