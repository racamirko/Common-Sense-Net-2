package com.commonsensenet.realfarm.model;

public class Resource {

	/** Audio Resource to play with the ActionType. */
	protected int mAudio;
	/** Background Image resource id. */
	protected int mBackgroundResource;
	/** Unique identifier of the Resource in the database. */
	protected int mId;
	/** Name of the Resource. */
	protected String mName;
	/** First Image resource id. */
	protected int mResource1;
	/** Second Image resource id. */
	protected int mResource2;
	/** Shortname of the resource, used mostly for displaying purposes. */
	protected String mShortName;
	/** Type of the resource. */
	protected int mType;

	public Resource() {
		mAudio = -1;
		mBackgroundResource = -1;
		mResource1 = -1;
		mResource2 = -1;
		mType = -1;
	}

	public Resource(int id, String name, String shortName, int audio,
			int resource1, int resource2, int backgroundResource, int type) {
		mName = name;
		mShortName = shortName;
		mResource1 = resource1;
		mResource2 = resource2;
		mAudio = audio;
		mId = id;
		mBackgroundResource = backgroundResource;
		mType = type;
	}

	public int getAudio() {
		return mAudio;
	}

	public int getBackgroundResource() {
		return mBackgroundResource;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getResource1() {
		return mResource1;
	}

	public int getResource2() {
		return mResource2;
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

	public void setBackgroundResource(int value) {
		mBackgroundResource = value;
	}

	public void setId(int value) {
		mId = value;
	}

	public void setName(String value) {
		mName = value;
	}

	public void setResource1(int value) {
		mResource1 = value;
	}

	public void setResource2(int value) {
		mResource2 = value;
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
				.format("[Resource id='%s', name='%s', shortName='%s', audio='%s', resource1='%s', resource2='%d', background='%d', type='%d']",
						mId, mName, mShortName, mAudio, mResource1, mResource2,
						mBackgroundResource, mType);

	}
}
