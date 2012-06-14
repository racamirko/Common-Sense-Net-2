package com.commonsensenet.realfarm.model;

import java.util.Date;

public class MarketPrice {

	private int mId;
	private String mdate;
	private int mvalue;
	private String mtype;
	private int madminflag;


	
	public MarketPrice(int id,String date,String type, int value,int adminflag) {
		mId = id;
		mdate = date;
		mvalue = value;
		mtype = type;
     	madminflag = adminflag;
	}
	
	
	public int getMarketPriceId() {
		return mId;
	}
	public String getDate() {
		return mdate;
	}

	public int getvalue() {
		return mvalue;
	}

	public String gettype() {
		return mtype;
	}
	

	
	public int getadminflag() {
		return madminflag;
	}

}

