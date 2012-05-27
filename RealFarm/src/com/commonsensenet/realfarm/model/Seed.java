package com.commonsensenet.realfarm.model;

public class Seed {

	private String mAudioRes;
	private int mDaysToHarvest;
	private int mId;
	private String mName;
	private String mNameKannada;
	private int mRes;
	private int mResBg;
	private String mVariety;
	private String mVarietyKannada;

	public Seed(int id, String name, String nameKannada, int res, String audioRes,
			int daysToHarvest, String variety, String varietyKannada, int resBg) {
		mId = id;
		mName = name;
		mNameKannada = nameKannada;
		mRes = res;
		mAudioRes = audioRes;
		mDaysToHarvest = daysToHarvest;
		mVariety = variety;
		mVarietyKannada = varietyKannada;
		mResBg = resBg;
	}

	public String getAudiores() {
		return mAudioRes;
	}

	public int getDaysToHarvest() {
		return mDaysToHarvest;
	}

	public String getFullName() {
		if (mVariety != null)
			return mName + " - " + mVariety;
		else
			return mName;
	}

	public String getFullNameKannada() {
		if (mVarietyKannada != null)
			return mNameKannada + " - " + mVarietyKannada;
		else
			return mNameKannada;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getNameKannada() {
		return mNameKannada;
	}

	public int getRes() {
		return mRes;
	}

	public String getVariety() {
		return mVariety;
	}

	public String getVarietyKannada() {
		return mVarietyKannada;
	}

	public int getResBg() {
		return mResBg;
	}

	public void setResBg(int mResBg) {
		this.mResBg = mResBg;
	}

}
