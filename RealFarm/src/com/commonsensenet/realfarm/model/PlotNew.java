package com.commonsensenet.realfarm.model;

public class PlotNew {

	protected int mPlotId;
	protected int mPlotUserId;
	protected String mPlotImageName;
	protected String mSoilType;
	protected String mMainCrop;
	protected int madminFlag;

	public PlotNew(int PlotId,int PlotUserId, String PlotImageName, String SoilType,String MainCrop, int adminFlag) {
		mPlotId = PlotId;
		mPlotUserId = PlotUserId;
		mPlotImageName = PlotImageName;
		mSoilType = SoilType;
		mMainCrop = MainCrop;
		madminFlag=adminFlag;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public int getPlotUserId() {
		return mPlotUserId;
	}

	public String getPlotImageName() {
		return mPlotImageName;
	}

	public String getSoilType() {
		return mSoilType;
	}
	

	public String getMainCrop() {
		return mMainCrop;
	}
	
	public int getadminFlag() {
		return madminFlag;
	}

	

}
