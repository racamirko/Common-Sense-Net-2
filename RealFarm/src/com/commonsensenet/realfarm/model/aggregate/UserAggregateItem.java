package com.commonsensenet.realfarm.model.aggregate;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class UserAggregateItem {

	private int audioCenterImage = -1;
	private int audioLeftImage = -1;
	private int audioLocation = -1;
	private int audioName = -1;
	private int audioRightImage = -1;
	private String avatarPath = "";
	private int centerImage = -1;
	private String date = "";
	private long id = -1;
	private int leftImage = -1;
	private String leftText = "";
	private String name = "";
	private int rightImage = -1;
	private String rightText = "";
	private String tel = "";

	public UserAggregateItem(long id) {
		this.id = id;
	}

	public int getAudioCenterImage() {
		return audioCenterImage;
	}

	public int getAudioLeftImage() {
		return audioLeftImage;
	}

	public int getAudioLocation() {
		return audioLocation;
	}

	public int getAudioName() {
		return audioName;
	}

	public int getAudioRightImage() {
		return audioRightImage;
	}

	public String getAvatar() {
		return avatarPath;
	}

	public int getCenterImage() {
		return centerImage;
	}

	public String getDate() {
		return date;
	}

	public long getId() {
		return id;
	}

	public int getLeftImage() {
		return leftImage;
	}

	public String getLeftText() {
		return leftText;
	}

	public String getName() {
		return name;
	}

	public int getRightImage() {
		return rightImage;
	}

	public String getRightText() {
		return rightText;
	}

	public String getTel() {
		return tel;
	}

	public void setAudioCenterImage(int aud) {
		audioCenterImage = aud;
	}

	public void setAudioLeftImage(int aud) {
		audioLeftImage = aud;
	}

	public void setAudioLocation(int aud) {
		audioLocation = aud;
	}

	public void setAudioName(int aud) {
		audioName = aud;
	}

	public void setAudioRightImage(int aud) {
		audioRightImage = aud;
	}

	public void setAvatar(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public void setCenterImage(int img) {
		centerImage = img;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLeftImage(int img) {
		leftImage = img;
	}

	public void setLeftText(String txt) {
		leftText = txt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRightImage(int img) {
		rightImage = img;
	}

	public void setRightText(String txt) {
		rightText = txt;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
