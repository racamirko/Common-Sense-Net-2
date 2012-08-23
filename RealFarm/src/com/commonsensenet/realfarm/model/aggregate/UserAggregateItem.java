package com.commonsensenet.realfarm.model.aggregate;


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
	int audioName = -1;
	int audioLocation = -1;
	int audioLeftImage = -1;
	int audioCenterImage = -1;
	int audioRightImage = -1;

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
	
	public void setAudioName(int aud) {
		audioName = aud;
	}
	
	public int getAudioName() {
		return audioName;
	}
	
	public void setAudioLeftImage(int aud) {
		audioLeftImage = aud;
	}
	
	public int getAudioLeftImage() {
		return audioLeftImage;
	}
	
	public void setAudioCenterImage(int aud) {
		audioCenterImage = aud;
	}
	
	public int getAudioCenterImage() {
		return audioCenterImage;
	}
	
	public void setAudioRightImage(int aud) {
		audioRightImage = aud;
	}
	
	public int getAudioRightImage() {
		return audioRightImage;
	}
	
	public void setAudioLocation(int aud) {
		audioLocation = aud;
	}
	
	public int getAudioLocation() {
		return audioLocation;
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
