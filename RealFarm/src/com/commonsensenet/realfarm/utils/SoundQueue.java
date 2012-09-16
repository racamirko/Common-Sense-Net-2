package com.commonsensenet.realfarm.utils;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

/**
 * Class that handles the Sounds. A list of sounds can be added to the Queue and
 * they will be played sequentially.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class SoundQueue implements OnCompletionListener {

	/** Identifier of the class used for logging. */
	private static final String LOG_TAG = "SoundQueue";
	/** Singleton instance of the SoundQueue. ` */
	private static SoundQueue sInstance = null;

	/**
	 * Gets the singleton SoundQueue instance.
	 * 
	 * @return the SoundQueue instance.
	 */
	public static SoundQueue getInstance() {
		if (sInstance == null) {
			Log.i(LOG_TAG, "Initializing sound system");
			sInstance = new SoundQueue();
		}
		return sInstance;
	}

	/** Context used to load the sounds using the resourceId. */
	protected Context mContext;
	/** Current MediaPlayer used to play the sound. */
	protected MediaPlayer mCurrentMediaPlayer;
	/** Queue that contains the sounds in the order that need to be played. */
	protected Queue<MediaPlayer> mResToPlay;

	/**
	 * Creates a new SoundQueue instance. All the variables are initialized.
	 */
	private SoundQueue() {

		mResToPlay = new LinkedList<MediaPlayer>();
		mCurrentMediaPlayer = null;
	}

	/**
	 * Adds a sound to the queue using its resourceId. The sounds will be played
	 * sequentially using play(). If a sound is currently being played, the new
	 * resource will be added to the end of the sequence.
	 * 
	 * @param pResId
	 *            the resourceId to play
	 */
	public void addToQueue(int resId) {

		if (mContext == null) {
			Log.i(LOG_TAG, "Context not set, use init() to set it");
		}

		// adds the id to the queue to play.
		if (mResToPlay != null) {
			mResToPlay.add(MediaPlayer.create(mContext, resId));
		} else {
			Log.d(LOG_TAG, "Queue is empty!!");
		}
	}

	/**
	 * Removes all pending sounds
	 */
	public void clean() {

		// disposes the active sound.
		if (mCurrentMediaPlayer != null) {
			mCurrentMediaPlayer.setOnCompletionListener(null);
			mCurrentMediaPlayer.stop();
			mCurrentMediaPlayer.release();
			mCurrentMediaPlayer = null;
		}

		// deletes all the active MediaPlayers in the queue
		for (MediaPlayer p : mResToPlay) {
			p.release();
		}

		// clears the data structures
		mResToPlay.clear();
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

	public void onCompletion(MediaPlayer arg0) {

		// releases the active media player.
		arg0.setOnCompletionListener(null);
		arg0.release();
		// clears the reference.
		mCurrentMediaPlayer = null;
		// plays the next sound
		play();
	}

	/**
	 * Plays the next sound in the SoundQueue. If a sound is currently being
	 * played, it ignores it.
	 */
	public void play() {

		// plays the next file in the queue
		// if no sound is currently being played.
		if (!mResToPlay.isEmpty() && mCurrentMediaPlayer == null) {
			// gets the next element.
			mCurrentMediaPlayer = mResToPlay.poll();
			mCurrentMediaPlayer.setOnCompletionListener(this);
			// plays the current sound.
			mCurrentMediaPlayer.start();
		}
	}

	/**
	 * Plays the currently playing sound and removes all pending sounds from the
	 * list.
	 */
	public void stop() {

		// removes any other existing sound in the queue.
		clean();
	}

}
