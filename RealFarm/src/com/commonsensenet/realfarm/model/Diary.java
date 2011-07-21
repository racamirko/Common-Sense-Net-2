package com.commonsensenet.realfarm.model;

import java.util.List;

public class Diary {

	private List<Integer> mId;
	private List<Integer> mActionId;
	private List<String> mActionDate;
	private List<Integer> mGrowingId;
	public Diary() {
	}
	
	public void addItem(int id, int actionId, String actionDate, int growingId){
		mId.add(id);
		mActionId.add(actionId);
		mActionDate.add(actionDate);
		mGrowingId.add(growingId);
	}
	
	public int getSize(){
		return mId.size();
	}

	public int getId(int i) {
		return mId.get(i);
	}

	public int getActionId(int i) {
		return mActionId.get(i);
	}
	
	public String getActionDate(int i){
		return mActionDate.get(i);
	}
	
	public int getGrowingId(int i){
		return mGrowingId.get(i);
	}
}

	
