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

	/** Indicates if the Audio is enabled or not. */
	public static boolean isAudioEnabled = true;
	/** Identifier of the class used for logging. */
	private static final String LOG_TAG = "SoundQueue";
	/** Singleton instance of the SoundQueue. */
	private static SoundQueue sInstance = null;

	/**
	 * Gets the singleton SoundQueue instance.
	 * 
	 * @return the SoundQueue instance.
	 */
	public static SoundQueue getInstance() {

		// follows the singleton pattern to create a new instance.
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

		// initializes the data structure used to hold the media players.
		mResToPlay = new LinkedList<MediaPlayer>();
		mCurrentMediaPlayer = null;
	}

	/**
	 * Adds a sound to the queue using its resourceId. The sounds will be played
	 * sequentially using play(). If a sound is currently being played, the new
	 * resource will be added to the end of the sequence.
	 * 
	 * @param resourceId
	 *            the resourceId to play
	 */
	public void addToQueue(int resourceId) {

		if (mContext == null) {
			Log.i(LOG_TAG, "Context not set, use init() to set it");
		}

		// adds the id to the queue to play.
		if (mResToPlay != null) {
			// only adds the audio if valid.
			if (resourceId != -1) {
				mResToPlay.add(MediaPlayer.create(mContext, resourceId));
			}
		} else {
			Log.d(LOG_TAG, "Queue is not initialized!!");
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

		// cancels any forcePlay setting.
		mForcePlay = false;

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

	public void onCompletion(MediaPlayer mediaPlayer) {

		// releases the active media player.
		mediaPlayer.setOnCompletionListener(null);
		mediaPlayer.release();

		// clears the reference.
		mCurrentMediaPlayer = null;

		// plays the next sound using the stored forcePlay setting.
		play(mForcePlay);
	}

	/**
	 * Plays the next sound in the SoundQueue respecting the current sound
	 * setting.
	 */
	public void play() {

		// does not override the isEnabledSound setting.
		play(false);
	}

	/**
	 * Plays the next sound in the SoundQueue. The sound is only played if the
	 * <code>isAudioEnabled</code> is <code>true</code> or if it is forced no
	 * matter the <code>isAudioEnabled</code> setting. If a sound is currently
	 * being played, it will continue the process normally.
	 * 
	 * @param forcePlay
	 *            whether or not to override the <code>isAudioEnabled</code>
	 *            setting.
	 */
	public void play(Boolean forcePlay) {

		// stores the last used forcePlay setting.
		mForcePlay = forcePlay;

		if (isAudioEnabled || forcePlay) {
			// plays the next file in the queue
			// if no sound is currently being played.
			if (!mResToPlay.isEmpty() && mCurrentMediaPlayer == null) {
				// gets the next element.
				mCurrentMediaPlayer = mResToPlay.poll();
				mCurrentMediaPlayer.setOnCompletionListener(this);
				// plays the current sound.
				mCurrentMediaPlayer.start();
			}
		} else {
			Log.d(LOG_TAG,
					"Cleared the pending audios since the sound is disabled.");
			clean();
		}
	}

	/** Stores the last forcePlay setting. */
	private boolean mForcePlay = false;

	/**
	 * Plays the currently playing sound and removes all pending sounds from the
	 * list.
	 */
	public void stop() {

		// removes any other existing sound in the queue.
		clean();
	}
}
