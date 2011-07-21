package com.commonsensenet.realfarm.model;

public class Recommendation {

	private int rId;
	private int rActionId;
	private int rSeedId;
	private String rDate;

	public Recommendation(int id, int seedId, int actionId, String date) {
		rId = id;
		rSeedId = seedId;
		rActionId = actionId;
		rDate = date;
	}

	public int getId() {
		return rId;
	}

	public int getAction() {
		return rActionId;
	}
	
	public int getSeed(){
		return rSeedId;
	}
	
	public String getDate(){
		return rDate;
	}
	
}



