package com.commonsensenet.realfarm.model;

public class Seed {

	private int mId;
	private String mName;
	private String mVariety;

	public Seed(int id, String name, String variety) {
		mId = id;
		mName = name;
		mVariety = variety;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getVariety() {
		return mVariety;
	}

}
