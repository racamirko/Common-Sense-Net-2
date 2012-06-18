package com.commonsensenet.realfarm.model;

public class PlotNew {

	protected int mPlotId;
	protected int mPlotUserId;
	
	protected int mplotSeedTypeId;
	protected int mpointX;
	protected int mpointY;
	protected String mPlotImageName;
	protected String mSoilType;
	protected int mdeleteFlag;
	protected int madminFlag;

	public PlotNew(int PlotId,int PlotUserId, int plotSeedTypeId,int pointX, int pointY,
			String PlotImageName, String SoilType,int deleteFlag, int adminFlag) {
		mPlotId = PlotId;
		mPlotUserId = PlotUserId;
		mplotSeedTypeId=plotSeedTypeId;
		mpointX=pointX;
		mpointY=pointY;
		mPlotImageName = PlotImageName;
		mSoilType = SoilType;
		mdeleteFlag=deleteFlag;
		madminFlag=adminFlag;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public int getPlotUserId() {
		return mPlotUserId;
	}

	public int getplotSeedTypeId() {
		return mplotSeedTypeId;
	}
	public int getpointX() {
		return mpointX;
	}
	public int getpointY() {
		return mpointY;
	}
	public String getPlotImageName() {
		return mPlotImageName;
	}

	public String getSoilType() {
		return mSoilType;
	}
	

	
	public int getdeleteFlag() {
		return mdeleteFlag;
	}
	
	public int getadminFlag() {
		return madminFlag;
	}

	

}
