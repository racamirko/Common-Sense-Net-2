package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class ActionType {

	private int mAudio;
	/** Unique I*/
	private int mId;
	private String mName;
	private String mNameKannada;
	private int mResource;

	public ActionType(int id, String name, String nameKannada, int resource,
			int audio) {
		mId = id;
		mName = name;
		mResource = resource;
		mAudio = audio;
		mNameKannada = nameKannada;
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

	public String getNameKannada() {
		return mNameKannada;
	}

	public int getResource() {
		return mResource;
	}

}
