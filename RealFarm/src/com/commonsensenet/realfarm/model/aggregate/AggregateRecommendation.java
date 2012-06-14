package com.commonsensenet.realfarm.model.aggregate;

import java.util.Vector;

import com.commonsensenet.realfarm.model.Recommendation;

public class AggregateRecommendation extends Recommendation {

	protected Vector<Integer> mUserIds;

	public AggregateRecommendation(int id, int seedId, int actionId, String date) {
		super(id, seedId, actionId, date);
		mUserIds = new Vector<Integer>();
	}

	public void addUserId(int userId) {
		mUserIds.add(userId);
	}

	public Vector<Integer> getUserIds() {
		return mUserIds;
	}

}
