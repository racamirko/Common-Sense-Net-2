package com.commonsensenet.realfarm.model.aggregate;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.User;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class UserAggregateItem {
	
	String name = "";
	String date = "";
	String tel = "";
	String leftText = "";
	String rightText = "";
	int leftImage = -1;
	int centerImage = -1;
	int rightImage = -1;
	String avatarPath = "";


	public UserAggregateItem() {
		
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getTel() {
		return tel;
	}

	public void setAvatar(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	
	public String getAvatar() {
		return avatarPath;
	}

	public void setCenterImage(int img) {
		centerImage = img;
	}
	
	public int getCenterImage() {
		return centerImage;
	}
	
	public void setLeftImage(int img) {
		leftImage = img;
	}
	
	public int getLeftImage() {
		return leftImage;
	}
	
	public void setRightImage(int img) {
		rightImage = img;
	}
	
	public int getRightImage() {
		return rightImage;
	}
	
	public void setLeftText(String txt) {
		leftText = txt;
	}
	
	public String getLeftText() {
		return leftText;
	}
	
	public void setRightText(String txt) {
		rightText = txt;
	}

	public String getRightText() {
		return rightText;
	}
}
