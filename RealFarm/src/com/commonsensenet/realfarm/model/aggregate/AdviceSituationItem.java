package com.commonsensenet.realfarm.model.aggregate;

import java.util.ArrayList;

/**
 * 
 * @author Nguyen Lisa
 */
public class AdviceSituationItem {

	private String audioSequence = "";
	private int chance = -1;
	private int cropAudio = -1;
	private int cropBackground = -1;
	private long cropId = -1;
	private String cropShortName = "";
	private long id = -1;
	private ArrayList<AdviceSolutionItem> items;
	private int loss = -1;
	private long plotId = -1;
	private String plotImage = "";
	private int problemAudio = -1;
	private long problemId = -1;
	private int problemImage = -1;
	private String problemShortName = "";
	private int unread = 0;
	private long validDate = -1;

	public String getAudioSequence() {
		return audioSequence;
	}

	public int getChance() {
		return chance;
	}

	public int getCropAudio() {
		return cropAudio;
	}

	public int getCropBackground() {
		return cropBackground;
	}

	public long getCropId() {
		return cropId;
	}

	public String getCropShortName() {
		return cropShortName;
	}

	public long getId() {
		return id;
	}

	public ArrayList<AdviceSolutionItem> getItems() {
		return items;
	}

	public int getLoss() {
		return loss;
	}

	public long getPlotId() {
		return plotId;
	}

	public String getPlotImage() {
		return plotImage;
	}

	public int getProblemAudio() {
		return problemAudio;
	}

	public long getProblemId() {
		return problemId;
	}

	public int getProblemImage() {
		return problemImage;
	}

	public String getProblemShortName() {
		return problemShortName;
	}

	public int getUnread() {
		return unread;
	}

	public long getValidDate() {
		return validDate;
	}

	public void setAudioSequence(String audioSequence) {
		this.audioSequence = audioSequence;
	}

	public void setChance(int cha) {
		this.chance = cha;
	}

	public void setCropAudio(int ca) {
		cropAudio = ca;
	}

	public void setCropBackground(int bg) {
		this.cropBackground = bg;
	}

	public void setCropId(long id) {
		this.cropId = id;
	}

	public void setCropShortName(String name) {
		this.cropShortName = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setItems(ArrayList<AdviceSolutionItem> items) {
		this.items = items;
	}

	public void setLoss(int los) {
		this.loss = los;
	}

	public void setPlotId(long pid) {
		this.plotId = pid;
	}

	public void setPlotImage(String img) {
		this.plotImage = img;
	}

	public void setProblemAudio(int pa) {
		problemAudio = pa;
	}

	public void setProblemId(long id) {
		this.problemId = id;
	}

	public void setProblemImage(int img) {
		this.problemImage = img;
	}

	public void setProblemShortName(String name) {
		this.problemShortName = name;
	}

	public void setUnread(int unr) {
		this.unread = unr;
	}

	public void setValidDate(long vd) {
		this.validDate = vd;
	}
}
