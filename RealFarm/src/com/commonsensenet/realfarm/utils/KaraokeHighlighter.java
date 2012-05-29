package com.commonsensenet.realfarm.utils;

import java.util.Iterator;
import java.util.Vector;

import com.commonsensenet.realfarm.R;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class KaraokeHighlighter implements PlaybackListener {
	private static String logTag = "KaraokeHighlighter";

	protected Vector<String> mSoundsToPlay;
	protected Vector<View> mPlaybackViews;
	protected int mChildPlaying;

	public KaraokeHighlighter(){
		mSoundsToPlay = new Vector<String>();
		mPlaybackViews = new Vector<View>();
		mChildPlaying = 0;
	}
	
	public void addItem(String pSound, View pComponent){
		mSoundsToPlay.add(pSound);
		mPlaybackViews.add(pComponent);
	}
	
	public void play(){
		SoundQueue sq = SoundQueue.getInstance();
		sq.addListener(this);
		mChildPlaying = 0;
		// add sounds
		Iterator<String> iter = mSoundsToPlay.iterator();
		Log.d(logTag, "Playing: ");
		while( iter.hasNext() ){
			String sndName = iter.next();
			sq.addToQueue(PathBuilder.getSound(sndName));
			Log.d(logTag, sndName);
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
		View lastView = mPlaybackViews.get(mPlaybackViews.size()-1);
		unhighlightView(lastView);
	}
	
	protected void updatePlayingSignal(){
		if( mChildPlaying > 0 ){
			View lastCtr = mPlaybackViews.get(mChildPlaying-1);
			unhighlightView(lastCtr);
		}
		View curCtr = mPlaybackViews.get(mChildPlaying);
		highlightView(curCtr);
	}
	
	protected void unhighlightView(View ctr){
		if(ctr == null)
			return;
		if( ctr instanceof TextView )
			((TextView)ctr).setTextColor(Color.WHITE);
		if( ctr instanceof ImageView )
			((ImageView)ctr).setColorFilter(null);
		if( ctr instanceof ImageButton )
			((ImageButton)ctr).setColorFilter(null);
	}
	
	protected void highlightView(View ctr){
		if(ctr == null)
			return;
		if( ctr instanceof TextView )
			((TextView)ctr).setTextColor(Color.YELLOW);
		if( ctr instanceof ImageView )
			((ImageView)ctr).setColorFilter(R.color.color_playback_highlight);
		if( ctr instanceof ImageButton )
			((ImageView)ctr).setColorFilter(R.color.color_playback_highlight);
	}
	
	public static String intToSound(int i){
		return "msg_num_"+Integer.toString(i)+".wav";
	}

}
