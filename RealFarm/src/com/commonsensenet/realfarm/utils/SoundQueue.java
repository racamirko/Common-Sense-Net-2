package com.commonsensenet.realfarm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class SoundQueue implements OnLoadCompleteListener {
	private static String logTag = "SoundQueue";

	protected SoundPool mSoundPool;
	protected Queue<Integer> mResToPlay;
	protected Queue<Integer> mResReady;
	protected Queue<Integer> mDurations;
	protected HashMap<Integer, Integer> mSoundMap;
	protected Context mCtx;
	protected Vector<PlaybackListener> mToNotify;
	
	private static SoundQueue mInstance = null;
	
	public class SoundPlayTask extends AsyncTask<Integer, Integer, Integer> {

		public SoundPlayTask(){ }
		
		@Override
		protected Integer doInBackground(Integer... params) {
			while(!mResToPlay.isEmpty()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// nothing
				}
			}
			
			publishProgress(0);
			while( !mResReady.isEmpty() ){
				int lDuration = mDurations.poll();
				mSoundPool.play(mResReady.poll(), 0.9f, 0.9f, 1, 0, 1.0f);
				try {
					synchronized (this) {
						Thread.sleep(lDuration);
					}
				if(!mResReady.isEmpty())
					publishProgress(1);
				} catch (InterruptedException e) {
					// nothing, just wait
				}
			}

			publishProgress(2);
			return 0;
		}
		
		protected void onProgressUpdate (Integer... values){
			switch(values[0]){
				case 0:
					notifyPlaybackBegin();
					break;
				case 1:
					notifyPlaybackNext();
					break;
				case 2:
					notifyPlaybackEnd();
			}
		}

		private void notifyPlaybackEnd() {
			for( PlaybackListener listener : mToNotify ){
				listener.OnEndPlayback();
			}
			mToNotify.clear();
		}

		private void notifyPlaybackNext() {
			for( PlaybackListener listener : mToNotify ){
				listener.OnNextTrack();
			}
		}

		private void notifyPlaybackBegin() {
			for( PlaybackListener listener : mToNotify ){
				listener.OnBeginPlayback();
			}
		}
		
	}

	
	private SoundQueue(Context pCtx){
		// hidden constructor
		mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		mSoundPool.setOnLoadCompleteListener(this);
		mResToPlay = new LinkedList<Integer>();
		mResReady = new LinkedList<Integer>();
		mDurations = new LinkedList<Integer>();
		mSoundMap = new HashMap<Integer, Integer>();
		mToNotify = new Vector<PlaybackListener>();
		mCtx = pCtx;
	}
	
	public static SoundQueue getInstance(Context pCtx){
		if( mInstance == null ){
			Log.i(logTag, "Initializing sound system");
			mInstance = new SoundQueue(pCtx);
		}
		return mInstance;
	}

	public static SoundQueue getInstance(){
		if( mInstance == null ){
			Log.e(logTag, "Sound system not initialized");
			return null; // or - throw exception
		}
		return mInstance;
	}
	
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		// TODO: handle status
		mResToPlay.poll();
		mResReady.add(sampleId);
	}

	public void addToQueue(int pResId){
		if( !mSoundMap.containsKey(pResId) ){
			mResToPlay.add(pResId);
			mSoundPool.load(mCtx, pResId, 1);
		}
		else
			mResReady.add(mSoundMap.get(pResId));
	}
	
	public void addToQueue(Uri pUri){
		mResToPlay.add(1);
		mSoundPool.load(pUri.getPath(), 1);
		// get duration in a silly way
		MediaPlayer mp = MediaPlayer.create(mCtx, pUri);
		mDurations.add(mp.getDuration());
		mp.release();
	}
	
	public void play(){
		SoundPlayTask player = new SoundPlayTask();
		player.execute(0);
	}
	
	/**
	 * 
	 * Note: after the end of playback, the listener is automatically removed
	 * 
	 * @param listener
	 */
	public void addListener(PlaybackListener listener){
		mToNotify.add(listener);
	}
	
}
