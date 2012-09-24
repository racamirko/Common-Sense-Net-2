package com.commonsensenet.realfarm.model;

public class AdvicePiece {

	private int mAdviceId;
	private int mAudio;
	private String mComment;
	private int mId;
	private int mOrderNumber;
	private int mSuggestedActionId;
	private int mSuggestedResourceId;

	public AdvicePiece(int id, int audio, String comment, int adviceId,
			int suggestedActionId, int suggestedResourceId, int orderNumber) {
		mId = id;
		mAudio = audio;
		mComment = comment;
		mAdviceId = adviceId;
		mSuggestedActionId = suggestedActionId;
		mSuggestedResourceId = suggestedResourceId;
		mOrderNumber = orderNumber;
	}

	public int getAdviceId() {
		return mAdviceId;
	}

	public int getAudio() {
		return mAudio;
	}

	public String getComment() {
		return mComment;
	}

	public int getId() {
		return mId;
	}

	public int getOrderNumber() {
		return mOrderNumber;
	}

	public int getSuggestedActionId() {
		return mSuggestedActionId;
	}

	public int getSuggestedResourceId() {
		return mSuggestedResourceId;
	}
}
