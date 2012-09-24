package com.commonsensenet.realfarm.model;

public class Recommendation {

	private String mActionRequiredByDate;
	private int mAdviceId;
	private String mDataCollectionDate;
	private long mId;
	private int mIsUnread;
	private long mPlotId;
	private int mProbability;
	private int mSeverity;
	private long mTimestamp;
	private long mUserId;
	private String mValidThroughDate;

	public Recommendation(long id, long plotId, int adviceId,
			String dataCollectionDate, String actionReqByDate,
			String validThroughDate, int severity, int probablity,
			int isUnread, long timestamp) {
		mId = id;
		mPlotId = plotId;
		mAdviceId = adviceId;
		mDataCollectionDate = dataCollectionDate;
		mActionRequiredByDate = actionReqByDate;
		mValidThroughDate = validThroughDate;
		mSeverity = severity;
		mProbability = probablity;
		mIsUnread = isUnread;
		mTimestamp = timestamp;
	}

	public void setIsUnread(int value) {
		mIsUnread = value;
	}

	public String getDataCollectionDate() {
		return mDataCollectionDate;
	}

	public String getActionRequiredByDate() {
		return mActionRequiredByDate;
	}

	public int getAdviceId() {
		return mAdviceId;
	}

	public long getId() {
		return mId;
	}

	public int getIsUnread() {
		return mIsUnread;
	}

	public long getPlotId() {
		return mPlotId;
	}

	public int getProbablity() {
		return mProbability;
	}

	public int getSeverity() {
		return mSeverity;
	}

	public long getTimestamp() {
		return mTimestamp;
	}

	public long getUserId() {
		return mUserId;

	}

	public String getValidThroughDate() {
		return mValidThroughDate;
	}

	@Override
	public String toString() {

		return String
				.format("[Recommendation id='%d', plotId='%d', adviceId='%d', dataCollectionDate='%s', actionRequiredByDate='%s', validThroughDate='%s', severity='%d', probability='%d', isUnread='%d', timestamp='%d']",
						mId, mPlotId, mAdviceId, mDataCollectionDate,
						mActionRequiredByDate, mValidThroughDate, mSeverity,
						mProbability, mIsUnread, mTimestamp);
	}
}
