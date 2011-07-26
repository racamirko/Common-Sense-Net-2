package com.commonsensenet.realfarm.model;

public class Seed {

	private int mId;
	private String mName;
	private String mVariety;
	private int mRes;

	public Seed(int id, String name, String variety, int res) {
		mId = id;
		mName = name;
		mVariety = variety;
		mRes = res;
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

	public int getRes(){
		return mRes;
	}
}
