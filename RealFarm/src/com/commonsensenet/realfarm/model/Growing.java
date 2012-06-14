package com.commonsensenet.realfarm.model;

public class Growing {
	private int mId;
	private int mPlotId;
	private int mSeedId;

	// TODO: support for intercropping
	public Growing(int id, int plotId, int seedId) {
		mId = id;
		mPlotId = plotId;
		mSeedId = seedId;
	}

	public boolean belongsTo(int id) {

		if (mPlotId == id)
			return true;

		return false;
	}

	public int getId() {
		return mId;
	}

	public int getPlotId() {
		return mPlotId;
	}

	public int getSeedId() {
		return mSeedId;
	}
}
