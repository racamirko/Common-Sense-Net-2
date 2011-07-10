package com.commonsensenet.realfarm.model;

public class Action {

	private int mId;
	private String mName;

	public Action(int id, String name) {
		mId = id;
		mName = name;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}
}
