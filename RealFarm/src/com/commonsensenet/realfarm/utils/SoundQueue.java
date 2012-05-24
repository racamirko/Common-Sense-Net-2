package com.commonsensenet.realfarm.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.media.AudioManager;
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
	protected HashMap<Integer, Integer> mSoundMap;
	protected Context mCtx;
	
	private static SoundQueue mInstance = null;
	
	public class SoundLoadTask extends AsyncTask<Uri, Integer, Integer> {
		protected SoundPool mSoundPool1;

		public SoundLoadTask(SoundPool pSoundPool){
			mSoundPool1 = pSoundPool;
		}
		
		@Override
		protected Integer doInBackground(Uri... params) {
			mSoundPool1.load(params[0].getPath(), 1);
			return 0;
		}
		
	}

	public class SoundPlayTask extends AsyncTask<Integer, Integer, Integer> {

		public SoundPlayTask(){ }
		
		@Override
		protected Integer doInBackground(Integer... params) {
			while(!mResToPlay.isEmpty()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// nothing
				}
			}
			
			while( !mResReady.isEmpty() ){
				mSoundPool.play(mResReady.poll(), 0.9f, 0.9f, 1, 0, 1.0f);
				try {
					synchronized (this) {
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
					// nothing, just wait
				}
			}

			return 0;
		}
		
	}

	
	private SoundQueue(Context pCtx){
		// hidden constructor
		mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		mSoundPool.setOnLoadCompleteListener(this);
		mResToPlay = new LinkedList<Integer>();
		mResReady = new LinkedList<Integer>();
		mSoundMap = new HashMap<Integer, Integer>();
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
		int resId = mResToPlay.poll();
//		mSoundMap.put(resId, sampleId);
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
	}
	
	public void play(){
		SoundPlayTask player = new SoundPlayTask();
		player.execute(0);
	}
}
