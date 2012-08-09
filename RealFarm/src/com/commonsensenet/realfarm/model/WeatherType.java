package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class WeatherType {

	/** Audio Resource to play with the WeatherType. */
	private int mAudio;
	/** Unique identifier of the WeatherType in the database. */
	private int mId;
	/** Identifier of the Image resource that represents the WeatherType. */
	private int mImage;
	/** Name of the Weather condition. */
	private String mName;

	public WeatherType(int id, String name, int image, int audio) {
		mId = id;
		mName = name;
		mImage = image;
		mAudio = audio;
	}

	public int getAudio() {
		return mAudio;
	}

	public int getId() {
		return mId;
	}

	public int getImage() {
		return mImage;
	}

	public String getName() {
		return mName;
	}

	@Override
	public String toString() {

		return String.format(
				"[WeatherType id='%s', name='%s', image='%d', audio='%d']",
				mId, mName, mImage, mAudio);
	}
}
