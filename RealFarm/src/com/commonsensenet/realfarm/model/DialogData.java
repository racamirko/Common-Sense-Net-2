package com.commonsensenet.realfarm.model;

public class DialogData {

	private int mAudio = -1;
	private int mBackgroundRes = -1;
	private int mId;
	private String mName;
	private int mNumber = -1;
	private int mResource1 = -1;
	private int mResource2 = -1;
	private String mShortName = "";

	public DialogData() {
	}

	public DialogData(int id, String name, int imgRes, int img2Res,
			int audioRes, int backgroundRes) {
		mName = name;
		mResource1 = imgRes;
		mResource2 = img2Res;
		mAudio = audioRes;
		mId = id;
		mBackgroundRes = backgroundRes;
		mShortName = name;
	}

	public DialogData(int id, String name, String shortName, int imgRes,
			int img2Res, int audioRes, int backgroundRes, int number) {
		mName = name;
		mResource1 = imgRes;
		mResource2 = img2Res;
		mAudio = audioRes;
		mId = id;
		mBackgroundRes = backgroundRes;
		mShortName = shortName;
		mNumber = number;
	}

	public DialogData(int id, String name, String shortName, int imgRes,
			int img2Res, int audioRes, int backgroundRes) {
		mName = name;
		mResource1 = imgRes;
		mResource2 = img2Res;
		mAudio = audioRes;
		mId = id;
		mBackgroundRes = backgroundRes;
		mShortName = shortName;
	}

	public int getAudioRes() {
		return mAudio;
	}

	public int getBackgroundRes() {
		return mBackgroundRes;
	}

	public int getId() {
		return mId;
	}

	public int getImage2Res() {
		return mResource2;
	}

	public int getImageRes() {
		return mResource1;
	}

	public String getName() {
		return mName;
	}

	public int getNumber() {
		return mNumber;
	}

	public String getShortName() {
		return mShortName;
	}

	public void setAudio(int audio) {
		mAudio = audio;
	}

	public void setBackground(int background) {
		mBackgroundRes = background;
	}

	public void setId(int id) {
		mId = id;
	}

	public void setImage(int img) {
		mResource1 = img;
	}

	public void setImage2(int img) {
		mResource2 = img;
	}

	public void setName(String name) {
		mShortName = name;
		mName = name;
	}

	public void setNumber(int number) {
		mNumber = number;
	}

	public void setShortName(String name) {
		mShortName = name;
	}
}
