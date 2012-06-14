package com.commonsensenet.realfarm.utils;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

/**
 * Class that handles the Sounds. A list of sounds can be added to the Queue and
 * they will be played sequentially.
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class SoundQueue implements OnLoadCompleteListener {
	/** Identifier of the class used for logging. */
	private static String LOG_TAG = "SoundQueue";
	/** Singleton instance of the SoundQueue. ` */
	private static SoundQueue mInstance = null;

	/**
	 * Gets the singleton SoundQueue instance.
	 * 
	 * @return the SoundQueue instance.
	 */
	public static SoundQueue getInstance() {
		if (mInstance == null) {
			Log.i(LOG_TAG, "Initializing sound system");
			mInstance = new SoundQueue();
		}
		return mInstance;
	}

	/** Context used to load the sounds using the resourceId. */
	protected Context mContext;
	/** ResourceId of the sound that is currently playing. */
	protected int mPlayingSound;
	/** Queue that contains the sounds in the order that need to be played. */
	protected Queue<Integer> mResToPlay;
	/** SoundPool used to play the sounds in different channels. */
	protected SoundPool mSoundPool;

	/**
	 * Creates a new SoundQueue instance. All the variables are initialized.
	 */
	private SoundQueue() {
		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		mSoundPool.setOnLoadCompleteListener(this);
		mResToPlay = new LinkedList<Integer>();
		mPlayingSound = -1;
	}

	/**
	 * Adds a sound to the queue using its resourceId. The sounds will be played
	 * sequentially using play(). If a sound is currently being played, the new
	 * resource will be added to the end of the sequence.
	 * 
	 * @param pResId
	 *            the resourceId to play
	 */
	public void addToQueue(int pResId) {
		if (mContext == null) {
			Log.i(LOG_TAG, "Context not set, use init() to set it");
		}
		mResToPlay.add(pResId);
		mSoundPool.load(mContext, pResId, 1);
	}

	/**
	 * Removes all pending sounds
	 */
	public void clean() {
		mResToPlay.clear();
		mPlayingSound = -1;
	}

	/**
	 * Sets the Context where the sounds will be loaded.
	 * 
	 * @param context
	 *            the Context to use.
	 */
	public void init(Context context) {
		mContext = context;
	}

	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

		// removes the item from the list
		if (mPlayingSound != -1) {
			// unloads the previously played sound
			mSoundPool.unload(sampleId);
		}
		// continues playing the following element
		play();
	}

	/**
	 * Plays the next sound in the SoundQueue. If a sound is currently being
	 * played, it ignores it.
	 */
	public void play() {

		// plays the next file in the queue
		// if no sound is currently being played.
		if (!mResToPlay.isEmpty() && mPlayingSound == -1) {
			mPlayingSound = mResToPlay.poll();
			mSoundPool.play(mPlayingSound, 0.9f, 0.9f, 1, 0, 1.0f);
		}
	}

	/**
	 * Plays the currently playing sound and removes all pending sounds from the
	 * list.
	 */
	public void stop() {

		// checks if an element is available, if so stops the sound.
		if (mPlayingSound != -1) {
			mSoundPool.stop(mPlayingSound);
		}

		clean();
	}
}
