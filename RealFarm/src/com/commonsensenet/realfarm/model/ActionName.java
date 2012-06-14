package com.commonsensenet.realfarm.model;

public class ActionName {

	private int mAudio;
	private int mId;
	private String mName;
	private int mRes;
	private String mNameKannada;
	private int madminFlag;

	public ActionName(int id, String name, String nameKannada, int resource, int audio, int adminFlag) {
		mId = id;
		mName = name;
		mRes = resource;
		mAudio = audio;
		mNameKannada = nameKannada;
		madminFlag=adminFlag;
	}

	public int getAudio() {
		return mAudio;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getRes() {
		return mRes;
	}
	
	public String getNameKannada() {
		return mNameKannada;
	}
	
	public int getadminFlag() {
		return madminFlag;
	}

}
