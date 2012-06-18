package com.commonsensenet.realfarm.model;

public class Plot {

	protected int mAdminFlag;
	protected int mDeleteFlag;
	protected int mPlotId;
	protected String mPlotImageName;
	protected int mplotSeedTypeId;
	protected int mPlotUserId;
	protected int mpointX;
	protected int mpointY;
	protected String mSoilType;

	public Plot(int PlotId, int PlotUserId, int plotSeedTypeId, int pointX,
			int pointY, String PlotImageName, String SoilType, int deleteFlag,
			int adminFlag) {
		mPlotId = PlotId;
		mPlotUserId = PlotUserId;
		mplotSeedTypeId = plotSeedTypeId;
		mpointX = pointX;
		mpointY = pointY;
		mPlotImageName = PlotImageName;
		mSoilType = SoilType;
		mDeleteFlag = deleteFlag;
		mAdminFlag = adminFlag;
	}

	public int getAdminFlag() {
		return mAdminFlag;
	}

	public int getDeleteFlag() {
		return mDeleteFlag;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public String getPlotImageName() {
		return mPlotImageName;
	}

	public int getplotSeedTypeId() {
		return mplotSeedTypeId;
	}

	public int getPlotUserId() {
		return mPlotUserId;
	}

	public int getpointX() {
		return mpointX;
	}

	public int getpointY() {
		return mpointY;
	}

	public String getSoilType() {
		return mSoilType;
	}

}
