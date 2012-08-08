package com.commonsensenet.realfarm.model;

public class Resource {

	/** Audio Resource to play with the ActionType. */
	protected int mAudio;
	/** Background Image resource id. */
	protected int mBackgroundImage;
	/** Unique identifier of the Resource in the database. */
	protected int mId;
	/** Name of the Resource. */
	protected String mName;
	/** First Image resource id. */
	protected int mImage1;
	/** Second Image resource id. */
	protected int mImage2;
	/** Shortname of the resource, used mostly for displaying purposes. */
	protected String mShortName;
	/** Type of the resource. */
	protected int mType;

	public Resource() {
		mAudio = -1;
		mBackgroundImage = -1;
		mImage1 = -1;
		mImage2 = -1;
		mType = -1;
	}

	public Resource(int id, String name, String shortName, int audio,
			int resource1, int resource2, int backgroundResource, int type) {
		mName = name;
		mShortName = shortName;
		mImage1 = resource1;
		mImage2 = resource2;
		mAudio = audio;
		mId = id;
		mBackgroundImage = backgroundResource;
		mType = type;
	}

	public int getAudio() {
		return mAudio;
	}

	public int getBackgroundImage() {
		return mBackgroundImage;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getImage1() {
		return mImage1;
	}

	public int getImage2() {
		return mImage2;
	}

	public String getShortName() {
		return mShortName;
	}

	public int getType() {
		return mType;
	}

	public void setAudio(int value) {
		mAudio = value;
	}

	public void setBackgroundImage(int value) {
		mBackgroundImage = value;
	}

	public void setId(int value) {
		mId = value;
	}

	public void setName(String value) {
		mName = value;
	}

	public void setImage1(int value) {
		mImage1 = value;
	}

	public void setImage2(int value) {
		mImage2 = value;
	}

	public void setShortName(String value) {
		mShortName = value;
	}

	public void setType(int value) {
		mType = value;
	}

	@Override
	public String toString() {

		return String
				.format("[Resource id='%s', name='%s', shortName='%s', audio='%s', image1='%s', image2='%d', backgroundImage='%d', type='%d']",
						mId, mName, mShortName, mAudio, mImage1, mImage2,
						mBackgroundImage, mType);

	}
}
