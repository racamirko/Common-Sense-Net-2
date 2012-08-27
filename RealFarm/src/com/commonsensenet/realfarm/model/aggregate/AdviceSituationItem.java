package com.commonsensenet.realfarm.model.aggregate;

import java.util.ArrayList;

/**
 * 
 * @author Nguyen Lisa
 */
public class AdviceSituationItem {

	private long id = -1;
	private long plotId = -1;
	private long cropId = -1;
	private long problemId = -1;
	private String cropShortName = "";
	private String plotImage = "";
	private int cropBackground = -1;
	private String problemShortName = "";
	private int problemImage = -1;
	private int loss = -1;
	private int chance = -1;
	private int audio = -1;
	private int unread = 0;
	private long validDate = -1;
	private ArrayList<AdviceSolutionItem> items;
	private int cropAudio = -1;
	private int problemAudio = -1;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCropId() {
		return cropId;
	}
	public void setCropId(long id) {
		this.cropId = id;
	}
	public long getProblemId() {
		return problemId;
	}
	public void setProblemId(long id) {
		this.problemId = id;
	}
	public long getPlotId() {
		return plotId;
	}
	public void setPlotId(long pid) {
		this.plotId = pid;
	}
	public String getCropShortName() {
		return cropShortName;
	}
	public void setCropShortName(String name) {
		this.cropShortName = name;
	}
	public String getPlotImage() {
		return plotImage;
	}
	public void setPlotImage(String img) {
		this.plotImage = img;
	}
	public int getCropBackground() {
		return cropBackground;
	}
	public void setCropBackground(int bg) {
		this.cropBackground = bg;
	}
	public String getProblemShortName() {
		return problemShortName;
	}
	public void setProblemShortName(String name) {
		this.problemShortName = name;
	}
	public int getProblemImage() {
		return problemImage;
	}
	public void setProblemImage(int img) {
		this.problemImage = img;
	}
	public int getLoss() {
		return loss;
	}
	public void setLoss(int los) {
		this.loss = los;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int cha) {
		this.chance = cha;
	}
	public int getAudio() {
		return audio;
	}
	public void setAudio(int aud) {
		this.audio = aud;
	}
	public int getUnread() {
		return unread;
	}
	public void setCropAudio(int ca){
		cropAudio = ca;
	}
	public int getCropAudio(){
		return cropAudio;
	}
	public void setProblemAudio(int pa){
		problemAudio = pa;
	}
	public int getProblemAudio(){
		return problemAudio;
	}
	public void setUnread(int unr) {
		this.unread = unr;
	}
	public long getValidDate() {
		return validDate;
	}
	public void setValidDate(long vd) {
		this.validDate = vd;
	}
	public void setItems(ArrayList<AdviceSolutionItem> items) {
		this.items = items;
	}
	public ArrayList<AdviceSolutionItem> getItems() {
		return items;
	}		
}
