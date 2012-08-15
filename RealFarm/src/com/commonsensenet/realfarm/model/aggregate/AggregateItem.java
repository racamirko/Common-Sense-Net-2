package com.commonsensenet.realfarm.model.aggregate;

import java.util.Hashtable;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class AggregateItem {

	private int mActionTypeId;
	private int selectorId;
	private Hashtable<String, String> mAggregateValues;
	private int mUserCount = 0;
	private String rightText = "";
	private int rightBackground = -1;
	private String centerText = "";
	private int centerBackground = -1;
	private int userImage = -1;
	

	public AggregateItem(int actionTypeId) {
		mUserCount = 0;
		mActionTypeId = actionTypeId;

		// initializes the data structure
		mAggregateValues = new Hashtable<String, String>();

	}
	
	public AggregateItem(int actionTypeId, int userCount) {
		mUserCount = userCount;
		mActionTypeId = actionTypeId;

		// initializes the data structure
		mAggregateValues = new Hashtable<String, String>();

	}

	public void addValue(String key, String value) {
		mAggregateValues.put(key, value);
	}

	public int getActionTypeId() {
		return mActionTypeId;
	}

	public int getUserCount() {
		return mUserCount;
	}

	public String getValue(String key) {
		return mAggregateValues.get(key);
	}

	public void setActionTypeId(int value) {
		mActionTypeId = value;
	}

	public void setUserCount(int value) {
		mUserCount = value;
	}
	
	public void setRightText(String text){
		rightText = text;
	}
	
	public String getRightText(){
		return rightText;
	}
	
	public void setRightBackground(int res){
		rightBackground = res;
	}
	
	public int getRightBackground(){
		return rightBackground;
	}
	
	public void setCenterText(String text){
		centerText = text;
	}
	
	public String getCenterText(){
		return centerText;
	}
	
	public void setCenterBackground(int res){
		centerBackground = res;
	}
	
	public int getCenterBackground(){
		return centerBackground;
	}
	
	public void setSelectorId(int id){
		selectorId = id;
	}
	
	public int getSelectorId(){
		return selectorId;
	}
	
	public void setUserImage(int img){
		userImage = img;
	}
	
	public int getUserImage(){
		return userImage;
	}

}
