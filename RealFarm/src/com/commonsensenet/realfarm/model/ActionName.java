package com.commonsensenet.realfarm.model;

public class ActionName {

	private String mAudio;
	private int mId;
	private String mName;
	private int mRes;
	private String mNameKannada;

	public ActionName(int id, String name, String nameKannada, int resource, String audio) {
		mId = id;
		mName = name;
		mRes = resource;
		mAudio = audio;
		mNameKannada = nameKannada;
	}

	public String getAudio() {
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

}
