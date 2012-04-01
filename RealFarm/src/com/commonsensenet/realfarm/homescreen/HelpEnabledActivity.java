package com.commonsensenet.realfarm.homescreen;


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

public class HelpEnabledActivity extends Activity implements OnLongClickListener, OnTouchListener {
	private String logTag = "HelpEnabledActivity";
	protected HelpAnimation mAnimFadeIn;
	protected boolean mHelpMode;
	protected View helpIcon;
	
	public class HelpAnimation extends AlphaAnimation {
		protected View mViewAnimated; // animation icon
		protected View mViewAssociated; // associated view on which we're dispalying the help

		public HelpAnimation(float fromAlpha, float toAlpha) {
			super(fromAlpha, toAlpha);
			
			setAnimationListener(new Animation.AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					HelpEnabledActivity.this.showHelp( HelpAnimation.this.getViewAssociated() );
					HelpEnabledActivity.this.setHelpMode(false);
					HelpEnabledActivity.this.helpIcon.setVisibility(View.INVISIBLE);
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelpMode = false;

        mAnimFadeIn = new HelpAnimation(0.0f, 1.0f);
        Log.i( logTag, "created");
    }
	
	@Override
	public boolean onLongClick(View v) {
		// position
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = helpIcon.getWidth()-helpIcon.getPaddingLeft();
		int iconHeight = helpIcon.getHeight() - helpIcon.getPaddingTop();
		helpIcon.setPadding(loc[0]+v.getWidth()/2-iconWidth/2, loc[1] - iconHeight, 0, 0);
		Log.i(logTag, "Showing help at: "+loc[0]+" , "+loc[1]);
		
		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(2000);
		helpIcon.setVisibility(View.VISIBLE);
		helpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);

		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if( event.getAction() == MotionEvent.ACTION_UP && getHelpMode() ){
			Animation a = new AlphaAnimation(1.0f, 0.0f);
			a.setDuration(500);
			helpIcon.startAnimation(a);
			helpIcon.setVisibility(View.INVISIBLE);
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
		// TODO: make a table mapping IDs to sound files
//		v.getId(); // find
	}
	
	public View getHelpIcon() {
		return helpIcon;
	}

	public void setHelpIcon(View helpIcon) {
		this.helpIcon = helpIcon;
		mAnimFadeIn.setViewAnimated(helpIcon);
	}

	
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
