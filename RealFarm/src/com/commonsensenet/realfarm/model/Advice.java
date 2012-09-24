package com.commonsensenet.realfarm.model;

public class Advice {

	private String mAudioSequence;
	private int mId;
	private int mProblemTypeId;
	private int mSeedTypeId;
	private int mStageNumber;

	public Advice(int id, int problemTypeId, int seedTypeId,
			String audioSequence, int stageNumber) {
		mId = id;
		mProblemTypeId = problemTypeId;
		mSeedTypeId = seedTypeId;
		mAudioSequence = audioSequence;
		mStageNumber = stageNumber;

	}

	public String getAudioSequence() {
		return mAudioSequence;
	}

	public int getId() {
		return mId;
	}

	public int getProblemTypeId() {
		return mProblemTypeId;
	}

	public int getSeedTypeId() {
		return mSeedTypeId;
	}

	public int getStageNumber() {
		return mStageNumber;
	}

}
