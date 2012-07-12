package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class SeedType {

	private int mAudioRes;
	private int mId;
	private String mName;
	private String mNameKannada;
	private int mRes;
	private int mResBg;
	private String mVariety;
	private String mVarietyKannada;

	public SeedType(int id, String name, String nameKannada, int res,
			int audioRes, String variety, String varietyKannada, int resBg) {
		mId = id;
		mName = name;
		mNameKannada = nameKannada;
		mRes = res;
		mAudioRes = audioRes;
		mVariety = variety;
		mVarietyKannada = varietyKannada;
		mResBg = resBg;
	}

	public int getAudioRes() {
		return mAudioRes;
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

	public int getResBg() {
		return mResBg;
	}

	public String getVariety() {
		return mVariety;
	}

	public String getVarietyKannada() {
		return mVarietyKannada;
	}

	public void setResBg(int mResBg) {
		this.mResBg = mResBg;
	}

	@Override
	public String toString() {

		return String
				.format("[SeedType id='%s', name='%s', nameKannada='%s', res='%d', audio='%d', variety='%s', varietyKannada='%s', resBG='%d']",
						mId, mName, mNameKannada, mRes, mAudioRes, mVariety,
						mVarietyKannada, mResBg);

	}

}
