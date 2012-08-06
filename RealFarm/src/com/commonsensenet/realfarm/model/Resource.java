package com.commonsensenet.realfarm.model;

public class Resource {

	/** Audio Resource to play with the ActionType. */
	protected int mAudio;
	protected int mBackgroundResource;
	protected int mId;
	protected String mName;
	protected int mResource1;
	protected int mResource2;
	protected int mType;

	public Resource() {
		mAudio = -1;
		mBackgroundResource = -1;
		mResource1 = -1;
		mResource2 = -1;
		mType = -1;
	}

	public Resource(int id, String name, int audio, int resource1,
			int resource2, int backgroundResource, int type) {
		mName = name;
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

	public void setResource(int value) {
		mResource1 = value;
	}

	public void setResource2(int value) {
		mResource2 = value;
	}

	public void setType(int value) {
		mType = value;
	}
}
