package com.commonsensenet.realfarm.model;

public class Action {

	private int mId;
	private String mName;
	private int mRes;
	
	public Action(int id, String name, int resource) {
		mId = id;
		mName = name;
		mRes = resource;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}
	
	public int getRes(){
		return mRes;
	}
}
