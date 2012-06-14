package com.commonsensenet.realfarm.model;

import java.util.Date;

public class WFList {

	private int mId;
	private String mdate;
	private int mvalue;
	private String mtype;
	private String mdate1;
	private int mvalue1;
	private String mtype1;
	private int madminflag;


	
	public WFList(String date, int value, String type, String date1, int value1, String type1, int adminflag) {
	//	mId = id;
		mdate = date;
		mvalue = value;
		mtype = type;
    	mdate1 = date1;
        mvalue1 = value1;
	 	mtype1 = type1;
	 	madminflag = adminflag;
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
	public String getdate1() {
		return mdate1;
	}
	public int getvalue1() {
		return mvalue1;
	}
	public String gettype1() {
		return mtype1;
	}
	
	public int getadminflag() {
		return madminflag;
	}

}
