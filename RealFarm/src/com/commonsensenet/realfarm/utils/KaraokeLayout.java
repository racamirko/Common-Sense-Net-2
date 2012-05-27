package com.commonsensenet.realfarm.utils;

import java.util.Iterator;
import java.util.Vector;

import com.commonsensenet.realfarm.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class KaraokeLayout extends FlowLayout implements PlaybackListener {
	protected Vector<String> mSoundsToPlay;
	protected int mChildPlaying;

	public KaraokeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSoundsToPlay = new Vector<String>();
		mChildPlaying = 0;
	}
	
	public void addView(View child){
		super.addView(child);
	}

	public void addView(View child, String soundName){
		super.addView(child);
		mSoundsToPlay.add(soundName);
	}
	
	public void play(){
		SoundQueue sq = SoundQueue.getInstance();
		sq.addListener(this);
		// add sounds
		Iterator<String> iter = mSoundsToPlay.iterator();
		while( iter.hasNext() ){
			sq.addToQueue(PathBuilder.getSound(iter.next()));
		}
		// play
		sq.play();
	}

	public void OnNextTrack() {
		mChildPlaying += 1;
		updatePlayingSignal();
	}

	public void OnBeginPlayback() {
		mChildPlaying = 0;
		updatePlayingSignal();
	}

	public void OnEndPlayback() {
		View lastView = getChildAt(getChildCount()-1);
		if( ! (lastView instanceof TextView) )
			return;
		TextView textview = (TextView) lastView;
		textview.setTextColor(R.color.color_normal);
	}

	private void updatePlayingSignal() {
		if( mChildPlaying > 0 ){
			View lastCtr = getChildAt(mChildPlaying-1);
			if( lastCtr instanceof TextView )
				((TextView)lastCtr).setTextColor(R.color.color_normal);
		}
		View curCtr = getChildAt(mChildPlaying);
		if( curCtr instanceof TextView )
			((TextView)curCtr).setTextColor(R.color.color_playback_highlight);
	}

}
