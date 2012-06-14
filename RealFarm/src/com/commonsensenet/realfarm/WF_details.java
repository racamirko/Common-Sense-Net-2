package com.commonsensenet.realfarm;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider.OnDataChangeListener;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.WFList;

import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;
import com.commonsensenet.realfarm.R;

public class WF_details extends HelpEnabledActivity implements OnDataChangeListener {
	View view;
	/** View where the items are displayed. */

	protected RealFarmProvider mDataProvider;

	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	Cursor cc;
	String log;
	public User ReadUser = null;
	public int Position; // Has copy of mainlistview position
	int wfvalue;
	String unit = "¡C";
	protected List<WFList> Wftodaydata;
	protected int wfsize;
	final Context context = this;
	String name;
	//MediaPlayer mp = null;

	public void onBackPressed() {

		Intent adminintent = new Intent(WF_details.this, Homescreen.class);

		startActivity(adminintent);
		WF_details.this.finish();
		
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
				Intent adminintent123 = new Intent(WF_details.this, Homescreen.class);
				startActivity(adminintent123);        
				WF_details.this.finish();
				
				                    
				}
	});	
   	    
		final Button wf1;
	    final Button wf2;
	    
	    wf1 = (Button) findViewById(R.id.home_btn_wf_1);
	    wf2 = (Button) findViewById(R.id.home_btn_wf_2);

	    
	    wf1.setOnLongClickListener(this);                             //Integration
	    wf2.setOnLongClickListener(this); 
		// fetch data from DB

		Wftodaydata = mDataProvider.getWFData();
		// Log.d("WFsize ","wfsize_str");
		wfsize = mDataProvider.getWFData().size() - 1;

		Log.d("WFsize ", "wfsize_str");

		int wfvalue = Wftodaydata.get(
				mDataProvider.getWFData().size() - 1).getvalue();

		String wftype = Wftodaydata.get(
				mDataProvider.getWFData().size() - 1).gettype();
		int wfvalue1 = Wftodaydata.get(
				mDataProvider.getWFData().size() - 1).getvalue1();
		String wftype1 = Wftodaydata.get(
				mDataProvider.getWFData().size() - 1).gettype1();
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

	/*	final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {

				System.out.println("WF details updating");
				text_1 = (TextView) findViewById(R.id.wf_text1);
				text_2 = (TextView) findViewById(R.id.wf_text2);
				text_4 = (TextView) findViewById(R.id.wf_text4);
				text_5 = (TextView) findViewById(R.id.wf_text5);

				img_1 = (ImageView) findViewById(R.id.wfimg_1);
				img_2 = (ImageView) findViewById(R.id.wfimg_2);

				arr[0] = R.drawable.sunny;
				arr[1] = R.drawable.rain;

				// fetch data from DB

				Wftodaydata = mDataProvider.getWFData();
				// Log.d("WFsize ","wfsize_str");
				wfsize = mDataProvider.getWFData().size() - 1;

				Log.d("WFsize ", "wfsize_str");

				int wfvalue = Wftodaydata.get(
						mDataProvider.getWFData().size() - 1).getvalue();

				String wftype = Wftodaydata.get(
						mDataProvider.getWFData().size() - 1).gettype();
				int wfvalue1 = Wftodaydata.get(
						mDataProvider.getWFData().size() - 1).getvalue1();
				String wftype1 = Wftodaydata.get(
						mDataProvider.getWFData().size() - 1).gettype1();
				Log.d("WFsize after", " wfsize");

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

				handler.postDelayed(this, 10000);

			}
		};

		handler.postDelayed(r, 10000);
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						if (i == 2) {
							// finish();
							i = 0;
						}
						sleep(10000);
						handler.post(r);
						// i++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};*/
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
	protected void initKannada() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onLongClick(View v) {
	
		if( v.getId() == R.id.home_btn_wf_1){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.weatherforecast);
			mp.start();
			
		}
		
       if( v.getId() == R.id.home_btn_wf_2){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.weatherforecast);
			mp.start();
			
		}
       
       if( v.getId() ==R.id.aggr_img_help ){
			
    			if(mp != null)
    			{
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
