package com.commonsensenet.realfarm.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

public class SoundQueue implements OnLoadCompleteListener {
	private static String logTag = "SoundQueue";

	protected SoundPool mSoundPool;
	protected Queue<Integer> mResToPlay;
	protected Queue<Integer> mResReady;
	protected HashMap<Integer, Integer> mSoundMap;
	protected Context mCtx;
	
	private static SoundQueue mInstance = null;
	
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
		mSoundMap.put(resId, sampleId);
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
	
	public void play(){
//		while(!mResToPlay.isEmpty()){
//			try {
//				synchronized (this) {
//					wait(1000);					
//				}
//			} catch (InterruptedException e) { }
//		}
		while( !mResReady.isEmpty() ){
			mSoundPool.play(mResReady.poll(), 0.9f, 0.9f, 1, 0, 1.0f);
			try {
				synchronized (this) {
					wait(2000);
				}
			} catch (InterruptedException e) {
				// nothing, just wait
			}
		}
	}
}
