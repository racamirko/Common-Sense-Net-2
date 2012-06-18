package com.commonsensenet.realfarm;

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
import com.commonsensenet.realfarm.utils.SoundQueue;

public class videos extends HelpEnabledActivity {

	final Context context = this;

	private Intent i_resfolder;
	protected RealFarmProvider mDataProvider;

	String name;
	public int Position; // Has copy of mainlistview position
	public User ReadUser = null;

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {

		Intent adminintent123 = new Intent(videos.this, Homescreen.class);
		startActivity(adminintent123);
		videos.this.finish();
		
		SoundQueue sq = SoundQueue.getInstance();    //audio integration
		sq.stop(); 
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos);
		System.out.println("In My_setting_plot_info call");

		mDataProvider = RealFarmProvider.getInstance(context); // Working
		ImageButton home, help;
		// ImageButton help;

		Button video1 = (Button) findViewById(R.id.video1);
		Button video2 = (Button) findViewById(R.id.video2);

		 video1.setOnLongClickListener(this);                     //audio integration
    	 video2.setOnLongClickListener(this);

		home = (ImageButton) findViewById(R.id.aggr_img_home);           //audio integration
		 help = (ImageButton) findViewById(R.id.aggr_img_help);
		 
		 help.setOnLongClickListener(this);  

		i_resfolder = new Intent(this, PlayVideoResFolder.class);

		// add the event listeners
		video1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");
				Global.videosel = 1;
				startActivity(i_resfolder);
				videos.this.finish();

			}
		});

		// add the event listeners
		video2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("In add user");
				Global.videosel = 2;
				startActivity(i_resfolder);
				videos.this.finish();

			}
		});

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(videos.this,
						Homescreen.class);
				startActivity(adminintent123);
				videos.this.finish();

			}
		});

	} // End of onCreate()

	@Override
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_help) {

			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();
			 }

		}
		
		 if( v.getId() == R.id.video1){
	    		
	    		playAudio(R.raw.video);                     //added
	    		
	    	}
	       
	       if( v.getId() == R.id.video2){
	   		
	   		playAudio(R.raw.video);                          //added
	   		
	   	}

		return true;
	}
	
	 public void playAudio(int resid)
	    {
		 if(Global.EnableAudio==true)                        //checking for audio enable
		 {
		 System.out.println("play audio called");
	    SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();
		
		sq.addToQueue(resid);
		//sq.addToQueue(R.raw.treatmenttoseeds3);
		sq.play();
		 }

	    	
	    }

}
