package com.commonsensenet.realfarm.model;

public class Recommendation {

	private int rActionId;
	private String rDate;
	private int rId;
	private int rSeedId;

	public Recommendation(int id, int seedId, int actionId, String date) {
		rId = id;
		rSeedId = seedId;
		rActionId = actionId;
		rDate = date;
	}

	public int getAction() {
		return rActionId;
	}

	public String getDate() {
		return rDate;
	}

	public int getId() {
		return rId;
	}

	public int getSeed() {
		return rSeedId;
	}

}
