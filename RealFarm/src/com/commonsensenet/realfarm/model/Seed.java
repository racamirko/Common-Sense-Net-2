package com.commonsensenet.realfarm.model;

public class Seed {

	private int mAudioRes;
	private int mDaysToHarvest;
	private int mId;
	private String mName;
	private String mNameKannada;
	private int mRes;
	private String mVariety;
	private String mVarietyKannada;

	public Seed(int id, String name, String nameKannada, int res, int audioRes,
			int daysToHarvest, String variety, String varietyKannada) {
		mId = id;
		mName = name;
		mNameKannada = nameKannada;
		mRes = res;
		mAudioRes = audioRes;
		mDaysToHarvest = daysToHarvest;
		mVariety = variety;
		mVarietyKannada = varietyKannada;

	}

	public int getAudiores() {
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

	// + COLUMN_NAME_SEEDTYPE_ID + " integer primary key, "
	// + COLUMN_NAME_SEEDTYPE_NAME + " text not null, "
	// + COLUMN_NAME_SEEDTYPE_NAMEKANNADA + " text, "
	// + COLUMN_NAME_SEEDTYPE_RESOURCE + " integer, "
	// + COLUMN_NAME_SEEDTYPE_AUDIO + " integer, "
	// + COLUMN_NAME_SEEDTYPE_DAYSTOHARVEST + " integer, "
	// + COLUMN_NAME_SEEDTYPE_VARIETY + " text, "
	// + COLUMN_NAME_SEEDTYPE_VARIETYKANNADA + " text "

}
