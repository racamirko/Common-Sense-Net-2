package com.commonsensenet.realfarm.model;

import java.util.ArrayList;
import java.util.List;

public class Diary {

	private List<String> mActionDate;
	private List<Integer> mActionId;
	private List<Integer> mGrowingId;
	private List<Integer> mId;

	public Diary() {
		mId = new ArrayList<Integer>();
		mActionId = new ArrayList<Integer>();
		mActionDate = new ArrayList<String>();
		mGrowingId = new ArrayList<Integer>();
	}

	public void addItem(int id, int actionId, String actionDate, int growingId) {
		mId.add(id);
		mActionId.add(actionId);
		mActionDate.add(actionDate);
		mGrowingId.add(growingId);
	}

	public String getActionDate(int i) {
		return mActionDate.get(i);
	}

	public int getActionId(int i) {
		return mActionId.get(i);
	}

	public int getGrowingId(int i) {
		return mGrowingId.get(i);
	}

	public int getId(int i) {
		return mId.get(i);
	}

	public int getSize() {
		return mId.size();
	}
}
