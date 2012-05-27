package com.commonsensenet.realfarm.utils;

/**
 * PlaybackListener
 * 
 * Interface to receive signals from SoundQueue.
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 *
 */
public interface PlaybackListener {
	public void OnNextTrack();
	public void OnBeginPlayback();
	public void OnEndPlayback();
}
