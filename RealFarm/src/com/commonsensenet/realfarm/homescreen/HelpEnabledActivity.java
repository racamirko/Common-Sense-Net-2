package com.commonsensenet.realfarm.homescreen;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.utils.SoundQueue;

public abstract class HelpEnabledActivity extends Activity implements
		OnLongClickListener, OnTouchListener {
	public class HelpAnimation extends AlphaAnimation {
		protected View mViewAnimated; // animation icon
		protected View mViewAssociated; // associated view on which we're
										// dispalying the help

		public HelpAnimation(float fromAlpha, float toAlpha) {
			super(fromAlpha, toAlpha);

			setAnimationListener(new Animation.AnimationListener() {

				// @Override
				public void onAnimationEnd(Animation animation) {
					HelpEnabledActivity.this.showHelp(HelpAnimation.this
							.getViewAssociated());
					HelpEnabledActivity.this.setHelpMode(false);
					HelpEnabledActivity.this.mHelpIcon
							.setVisibility(View.INVISIBLE);
				}

				// @Override
				public void onAnimationRepeat(Animation animation) {
				}

				// @Override
				public void onAnimationStart(Animation animation) {
				}
			});

		}

		public View getViewAnimated() {
			return mViewAnimated;
		}

		public View getViewAssociated() {
			return mViewAssociated;
		}

		public void setViewAnimated(View mViewAnimated) {
			this.mViewAnimated = mViewAnimated;
		}

		public void setViewAssociated(View mViewAssociated) {
			this.mViewAssociated = mViewAssociated;
		}

	}

	protected void stopaudio() {
		SoundQueue.getInstance().stop();
	}


	protected void initmissingval() {
	
		playAudio(R.raw.missinginfo);
	
		//ShowHelpIcon(v);  
	}

	protected void okaudio() {
		playAudio(R.raw.ok);
	}

	private static final String LOG_TAG = "HelpEnabledActivity";

	protected HelpAnimation mAnimFadeIn;
	protected View mHelpIcon;
	protected boolean mHelpMode;

	public String getcurrenttime() {
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat", crntdt);
		return crntdt;

	}

	public View getHelpIcon() {
		return mHelpIcon;
	}

	public boolean getHelpMode() {
		return mHelpMode;
	}

	public void onCreate(Bundle savedInstanceState, int resLayoutId) {
		super.onCreate(savedInstanceState);
		mHelpMode = false;

		mAnimFadeIn = new HelpAnimation(0.0f, 1.0f);
		Log.i(LOG_TAG, "created");

		setContentView(resLayoutId);
	}

	// @Override
	public boolean onLongClick(View v) {
		// position
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth() - mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0] + v.getWidth() / 2 - iconWidth / 2, loc[1]
				- iconHeight - 20, 0, 0);
		Log.d(LOG_TAG, "Showing help at: " + loc[0] + " , " + loc[1]);

		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);

		return true;
	}

	// @Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP && getHelpMode()) {
			Animation a = new AlphaAnimation(1.0f, 0.0f);
			a.setDuration(500);
			mHelpIcon.startAnimation(a);
			mHelpIcon.setVisibility(View.INVISIBLE);
			setHelpMode(false);
			return true;
		}
		// In case we weren't in the help mode let the rest of the stack process
		// the event
		return false;
	}

	public void setHelpIcon(View helpIcon) {
		this.mHelpIcon = helpIcon;
		mAnimFadeIn.setViewAnimated(helpIcon);
	}

	public void setHelpMode(boolean active) {
		mHelpMode = active;
	}


	protected void playAudio(int resid) // audio integration
	{
		if (Global.enableAudio) // checking for audio enable
		{
			// gets the singleton queue
			SoundQueue sq = SoundQueue.getInstance();
			// cleans any possibly playing sound
			sq.clean();
			// adds the sound to the queue
			sq.addToQueue(resid);
			// plays the sound
			sq.play();
		}
	
	}
	
	
	protected void playAudioalways(int resid) // audio integration
	{
			// gets the singleton queue
			SoundQueue sq = SoundQueue.getInstance();
			// cleans any possibly playing sound
			sq.clean();
			// adds the sound to the queue
			sq.addToQueue(resid);
			// plays the sound
			sq.play();
		
	}
	
	public void ShowHelpIcon(View v)              //added witth audio integration //20-06-2012
	{
		
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth() - mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0] + v.getWidth() / 2 - iconWidth / 2, loc[1]
				- iconHeight - 20, 0, 0);
		Log.d(LOG_TAG, "Showing help at: " + loc[0] + " , " + loc[1]);

		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);
	
	}
	
	public void showHelp(View v) {
	

		/*
		 * if(mp != null) //Integration { mp.stop(); mp.release(); mp = null; }
		 * mp = MediaPlayer.create(this, R.raw.audio1); mp.start();
		 */

		if (v.getId() == R.id.hmscrn_btn_actions
				|| v.getId() == R.id.home_btn_actions) { // Integration

			playAudio(R.raw.audio1);

		}
		if (v.getId() == R.id.hmscrn_btn_advice
				|| v.getId() == R.id.home_btn_advice) {

			playAudio(R.raw.audio2);
		}
		if (v.getId() == R.id.hmscrn_btn_sound || v.getId() == R.id.hmscrn_btn_sound) {

			playAudio(R.raw.audio3);
		}
		
		if (v.getId() == R.id.hmscrn_help_button || v.getId() == R.id.hmscrn_help_button) {

			playAudio(R.raw.audio3);
		}
		if (v.getId() == R.id.hmscrn_btn_yield
				|| v.getId() == R.id.hmscrn_btn_yield) {

			playAudio(R.raw.audio4);
		}

		if (v.getId() == R.id.hmscrn_btn_weather || v.getId() == R.id.hmscrn_btn_weather) {

			playAudio(R.raw.weatherforecast);
		}
// End of big icons

		if (v.getId() == R.id.btn_action_sowing) {
			playAudio(R.raw.sowing);

		}

		if (v.getId() == R.id.btn_action_harvesting) {
			playAudio(R.raw.harvesting);
		}

		if (v.getId() == R.id.btn_action_selling) {
			playAudio(R.raw.selling);
		}

		if (v.getId() == R.id.btn_action_fertilize) {
			playAudio(R.raw.fertilizing);
		}

		if (v.getId() == R.id.btn_action_spray) {
			playAudio(R.raw.spraying);
		}

		if (v.getId() == R.id.hmscrn_btn_plots) {
			playAudio(R.raw.mysettings);
		}

		if (v.getId() == R.id.btn_action_irrigate) {
			playAudio(R.raw.irrigate);
		}
		if (v.getId() == R.id.hmscrn_btn_market) {
			playAudio(R.raw.marketprice);
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.video);
		}

		if (v.getId() == R.id.btn_action_problem) {
			playAudio(R.raw.problems);
		}

		if (v.getId() == R.id.hmscrn_btn_diary) { // changes
			playAudio(R.raw.dairy);
		}

		// TODO: make a table mapping IDs to sound files
	}
	
	public void onBackPressed() {
		// stops any currently playing sound.
		stopAudio();

		super.onBackPressed();
	}

	protected String getCurrentTime() {
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat", crntdt);
		return crntdt;
	}

	protected void stopAudio() {
		SoundQueue.getInstance().stop();
	}



}
