package com.commonsensenet.realfarm.model.aggregate;

import android.widget.TextView;

import com.commonsensenet.realfarm.R;

/**
 * 
 * @author Nguyen Lisa
 */
public class AdviceSolutionItem {
	
	private int audio = -1;
	private int id = -1;
	private int pesticideBackground = -1;
	private int pesticideImage = -1;
	private String pesticideShortName = "";
	private String comment = "";
	private int number = 1;
	private int likes = 12;
	private int didIt = 12;
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void setAudio(int aud){
		this.audio = aud;
	}
	public int getAudio(){
		return audio;
	}
	public void setPesticideBackground(int bg){
		this.pesticideBackground = bg;
	}
	public int getPesticideBackground(){
		return pesticideBackground;
	}
	public void setPesticideImage(int img){
		this.pesticideImage = img;
	}
	public int getPesticideImage(){
		return pesticideImage;
	}
	public void setNumber(int nb){
		this.number = nb;
	}
	public int getNumber(){
		return number;
	}
	public void setLikes(int li){
		this.likes = li;
	}
	public int getLikes(){
		return likes;
	}
	public void setDidIt(int di){
		this.didIt = di;
	}
	public int getDidIt(){
		return didIt;
	}
	public void setPesticideShortName(String name){
		this.pesticideShortName = name;
	}
	public String getPesticideShortName(){
		return pesticideShortName;
	}
	public void setComment(String com){
		this.comment = com;
	}
	public String getComment(){
		return comment;
	}
}
