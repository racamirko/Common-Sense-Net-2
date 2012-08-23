package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public abstract class HelpEnabledActivityOld extends Activity implements OnLongClickListener {

	public String getLogTag() {
		return this.getClass().getSimpleName();
	}

	public class HelpAnimation extends AlphaAnimation {
		protected View mViewAnimated; // animation icon
		protected View mViewAssociated; // associated view on which we're
										// dispalying the help

		public HelpAnimation(float fromAlpha, float toAlpha) {
			super(fromAlpha, toAlpha);

			setAnimationListener(new Animation.AnimationListener() {

				// @Override
				public void onAnimationEnd(Animation animation) {
					HelpEnabledActivityOld.this.showHelp(HelpAnimation.this
							.getViewAssociated());
					HelpEnabledActivityOld.this.setHelpMode(false);
					HelpEnabledActivityOld.this.mHelpIcon
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

	protected String getCurrentTime() {
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

	protected void initmissingval() {

		playAudio(R.raw.missinginfo);

		// ShowHelpIcon(v);
	}

	protected void okaudio() {
		playAudio(R.raw.ok);
	}

	public void onBackPressed() {
		// stops any currently playing sound.
		stopAudio();

		super.onBackPressed();
	}

	public void onCreate(Bundle savedInstanceState, int resLayoutId) {
		super.onCreate(savedInstanceState);
		mHelpMode = false;

		mAnimFadeIn = new HelpAnimation(0.0f, 1.0f);
		
		ApplicationTracker.getInstance().logEvent(EventType.ACTIVITY_VIEW, getLogTag());
		ApplicationTracker.getInstance().flush();

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
		Log.d(getLogTag(), "Showing help at: " + loc[0] + " , " + loc[1]);

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

	/**
	 * Plays the given resourceId. The sound is only played if
	 * Global.enabledAudio is true, otherwise it is not played.
	 * 
	 * @param resourceId
	 *            id of the audio.
	 */
	protected void playAudio(int resourceId) {
		// plays the sound if the id is valid.
		if (resourceId != -1) {
			playAudio(resourceId, false);
		}
	}

	/**
	 * Plays the given resourceId. The forcePlay flag can be use to play an
	 * audio no matter the Global.enableAudio setting.
	 * 
	 * @param resid
	 *            id of the audio to play.
	 * @param forcePlay
	 *            whether the Global.enableAudio setting is respected or not.
	 */
	protected void playAudio(int resid, Boolean forcePlay) {
		// checking for audio enable
		if (Global.isAudioEnabled || forcePlay) {
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

	public void setHelpIcon(View helpIcon) {
		this.mHelpIcon = helpIcon;
		mAnimFadeIn.setViewAnimated(helpIcon);
	}

	public void setHelpMode(boolean active) {
		mHelpMode = active;
	}

	public void showHelp(View v) {

		if (v.getId() == R.id.hmscrn_btn_actions) {
			playAudio(R.raw.audio1, true);
		}
		if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.audio2, true);
		}
		if (v.getId() == R.id.hmscrn_btn_sound) {
			playAudio(R.raw.audio3, true);
		}

		// if (v.getId() == R.id.hmscrn_help_button || v.getId() ==
		// R.id.hmscrn_help_button) {
		//
		// playAudioalways(R.raw.audio3);
		// }
		if (v.getId() == R.id.hmscrn_btn_yield) {
			playAudio(R.raw.audio4, true);
		}

		if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.weatherforecast, true);
		}
		// End of big icons

		if (v.getId() == R.id.hmscrn_btn_market) {
			playAudio(R.raw.marketprice, true);
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.video, true);
		}

		// if (v.getId() == R.id.hmscrn_btn_diary) { // changes
		// playAudioalways(R.raw.dairy);
		// }

		// if (v.getId() == R.id.hmscrn_imgbtn_notifs) { // changes
		// playAudioalways(R.raw.dairy);
		// }

		// TODO: make a table mapping IDs to sound files
	}

	public void showHelpIcon(View v) {

		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth() - mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0] + v.getWidth() / 2 - iconWidth / 2, loc[1]
				- iconHeight - 20, 0, 0);
		Log.d(getLogTag(), "Showing help at: " + loc[0] + " , " + loc[1]);

		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);

	}

	protected void stopAudio() {
		SoundQueue.getInstance().stop();
	}
	
}
