package com.commonsensenet.realfarm.model;

public class MarketItem {
	
	private int weight = 0;
	private int userCount = 0;
	private int min = 0;
	private int max = 0;
	
	public MarketItem(int w, int uc, int mi, int ma){
		weight = w;
		userCount = uc;
		min = mi;
		max = ma;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getUserCount(){
		return userCount;
	}

	public int getMin(){
		return min;
	}
	
	public int getMax(){
		return max;
	}
}
