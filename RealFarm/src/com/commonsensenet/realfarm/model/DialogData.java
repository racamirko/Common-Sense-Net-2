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
		this.mName = name;
		this.mResource1 = imgRes;
		this.mResource2 = img2Res;
		this.mAudio = audioRes;
		this.mId = id;
		this.mBackgroundRes = backgroundRes;
		this.mShortName = name;
	}

	public DialogData(int id, String name, String shortName, int imgRes,
			int img2Res, int audioRes, int backgroundRes, int number) {
		this.mName = name;
		this.mResource1 = imgRes;
		this.mResource2 = img2Res;
		this.mAudio = audioRes;
		this.mId = id;
		this.mBackgroundRes = backgroundRes;
		this.mShortName = shortName;
		this.mNumber = number;
	}

	public DialogData(int id, String name, String shortName,
			int imgRes, int img2Res, int audioRes, int backgroundRes) {
		this.mName = name;
		this.mResource1 = imgRes;
		this.mResource2 = img2Res;
		this.mAudio = audioRes;
		this.mId = id;
		this.mBackgroundRes = backgroundRes;
		this.mShortName = shortName;
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
		this.mAudio = audio;
	}

	public void setBackground(int background) {
		this.mBackgroundRes = background;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public void setImage(int img) {
		this.mResource1 = img;
	}

	public void setImage2(int img) {
		this.mResource2 = img;
	}

	public void setName(String name) {
		this.mShortName = name;
		this.mName = name;
	}

	public void setNumber(int number) {
		this.mNumber = number;
	}

	public void setShortName(String name) {
		this.mShortName = name;
	}
}
