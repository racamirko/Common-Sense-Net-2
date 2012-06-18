package com.commonsensenet.realfarm.homescreen;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

public abstract class HelpEnabledActivity extends Activity implements OnLongClickListener, OnTouchListener {
	private String logTag = "HelpEnabledActivity";
	protected HelpAnimation mAnimFadeIn;
	protected boolean mHelpMode;
	protected Typeface mKannadaTypeface;
	protected View mHelpIcon;
	protected MediaPlayer mp = null;
	public class HelpAnimation extends AlphaAnimation {
		protected View mViewAnimated; // animation icon
		protected View mViewAssociated; // associated view on which we're dispalying the help

		public HelpAnimation(float fromAlpha, float toAlpha) {
			super(fromAlpha, toAlpha);
			
			setAnimationListener(new Animation.AnimationListener() {
				
//				@Override
				public void onAnimationStart(Animation animation) {
				}
				
//				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				
//				@Override
				public void onAnimationEnd(Animation animation) {
					HelpEnabledActivity.this.showHelp( HelpAnimation.this.getViewAssociated() );
					HelpEnabledActivity.this.setHelpMode(false);
					HelpEnabledActivity.this.mHelpIcon.setVisibility(View.INVISIBLE);
				} }
			);
			
		}

		public View getViewAnimated() {
			return mViewAnimated;
		}

		public void setViewAnimated(View mViewAnimated) {
			this.mViewAnimated = mViewAnimated;
		}
		
		public View getViewAssociated() {
			return mViewAssociated;
		}

		public void setViewAssociated(View mViewAssociated) {
			this.mViewAssociated = mViewAssociated;
		}


	}
	
    public void onCreate(Bundle savedInstanceState, int resLayoutId) {
        super.onCreate(savedInstanceState);
        mHelpMode = false;

        mAnimFadeIn = new HelpAnimation(0.0f, 1.0f);
        Log.i( logTag, "created");
        
        setContentView(resLayoutId);
        // Kannada typeface
        mKannadaTypeface = Typeface.createFromAsset(getAssets(),"fonts/tunga.ttf");
        initKannada();
    }
	
//	@Override
	public boolean onLongClick(View v) {
		// position
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth()-mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0]+v.getWidth()/2-iconWidth/2, loc[1] - iconHeight - 20, 0, 0);
		Log.d(logTag, "Showing help at: "+loc[0]+" , "+loc[1]);
		
		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);
		
		
	

		return true;
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if( event.getAction() == MotionEvent.ACTION_UP && getHelpMode() ){
			Animation a = new AlphaAnimation(1.0f, 0.0f);
			a.setDuration(500);
			mHelpIcon.startAnimation(a);
			mHelpIcon.setVisibility(View.INVISIBLE);
			setHelpMode(false);
			return true;
		}
		// In case we weren't in the help mode let the rest of the stack process the event
		return false;
	}
	
	public void setHelpMode(boolean active){
		mHelpMode = active;
	}
	
	public boolean getHelpMode(){
		return mHelpMode;
	}


	public void showHelp(View v){
		Toast.makeText(getApplicationContext(), "Showing help for "+v.getId(), Toast.LENGTH_SHORT).show();
		
	/*	if(mp != null)                                                //Integration
		{
			mp.stop();
			mp.release();
			mp = null;
		}
		mp = MediaPlayer.create(this, R.raw.audio1);
		mp.start();*/
		
		
	if( v.getId() == R.id.btn_info_actions || v.getId() == R.id.home_btn_actions ){           //Integration
		
		 if(Global.EnableAudio==true)                        //checking for audio enable
		 {
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.audio1);
			mp.start();
		 }
		}
		if( v.getId() == R.id.btn_info_advice || v.getId() == R.id.home_btn_advice){
			
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
		{
				mp.stop();
				mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.audio2);
			mp.start();
			 }
		}
		if( v.getId() == R.id.btn_info_warn || v.getId() == R.id.home_btn_warn ){
			
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.audio3);
			mp.start();
			 }
		}
		if( v.getId() == R.id.btn_info_yield || v.getId() == R.id.home_btn_yield ){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.audio4);
			mp.start();
			 }
		}
		
		if( v.getId() == R.id.btn_info_yield|| v.getId() == R.id.home_btn_wf ){
		
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
		if(mp != null)
		{
			mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.weatherforecast);
		mp.start();
			 }
		}    
		
		if( v.getId() == R.id.btn_info_yield|| v.getId() == R.id.home_btn_wf ){
			
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.weatherforecast);
			mp.start();
			 }
			}               //End of big icons
		
		if( v.getId() == R.id.btn_action_plant){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.sowing);
			mp.start();
			 }
	
		}
		
		if( v.getId() == R.id.btn_action_yield){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.harvesting);
			mp.start();
			 }
	}
		
		if( v.getId() == R.id.btn_action_selling ){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
		}
			mp = MediaPlayer.create(this, R.raw.selling);
		mp.start();
			 }
		}
	
		
		if( v.getId() == R.id.btn_action_fertilize){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.fertilizing);
			mp.start();
			 }
		}
		
		if( v.getId() == R.id.btn_action_spray ){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
		{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.spraying);
			mp.start();
			 }
		}
		
		if (v.getId() == R.id.home_btn_PlotInfo) {
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.mysettings);
			mp.start();
			 }
	}
		
		
		if (v.getId() == R.id.btn_action_irrigate) {
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.irrigate);
			mp.start();
			 }
			

		}
		if (v.getId() == R.id.home_btn_marketprice) {
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.marketprice);
			mp.start();
			 }
	}
		
		if (v.getId() == R.id.btn_action_videos) {
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.video);
			mp.start();
			 }
	}
		
		if (v.getId() == R.id.btn_action_problem) {
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
			mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.problems);
			mp.start();
			 }
			
	}
		
		if (v.getId() == R.id.btn_action_diary) {                   //changes
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.dairy);
				mp.start();
			 }
				

			}
		
		// TODO: make a table mapping IDs to sound files
	}
	
	public View getHelpIcon() {
		return mHelpIcon;
	}

	public void setHelpIcon(View helpIcon) {
		this.mHelpIcon = helpIcon;
		mAnimFadeIn.setViewAnimated(helpIcon);
	}
	
	public Typeface getKannadaTypeface() {
		return mKannadaTypeface;
	}

	public String getcurrenttime()
	{
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat",crntdt);
		return crntdt;
		
	}
	
	
	protected abstract void initKannada();
	
//	public void testAnim(){
//		Log.i(logTag, "Should see animation now");
//		ImageView helpIcon = (ImageView) mActivity.findViewById(R.id.helpIndicator);
//		mAnimFadeIn.setViewAnimated(helpIcon);
//		mAnimFadeIn.setDuration(2000);
//		helpIcon.startAnimation(mAnimFadeIn);
//	}
//	
//	public void testAnimOut(){
//		AlphaAnimation ani = new AlphaAnimation(1.0f, 0.0f);
//		ani.setDuration(3000);
//		ImageView helpIcon = (ImageView) mActivity.findViewById(R.id.helpIndicator);
//		helpIcon.startAnimation(ani);
//	}

}
