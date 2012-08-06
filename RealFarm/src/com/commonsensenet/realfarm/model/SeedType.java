package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class SeedType extends Resource {

	/** CropType that this seed represents. */
	private int mCropTypeId;
	/** Name of the seed in Kannada language. */
	private String mNameKannada;
	/** Short name of the SeedType. */
	private String mShortName;

	public SeedType(int id, String name, String nameKannada, String shortName,
			int cropTypeId, int resource, int audio) {
		super(id, name, name, audio, resource, -1, -1, -1);
		mShortName = shortName;
		mNameKannada = nameKannada;
		mCropTypeId = cropTypeId;
	}

	public int getCropTypeId() {
		return mCropTypeId;
	}

	public String getNameKannada() {
		return mNameKannada;
	}

	public String getShortName() {
		return mShortName;
	}

	@Override
	public String toString() {

		return String
				.format("[SeedType id='%s', name='%s', nameKannada='%s', shortName='%s', cropTypeId='%d', resource='%d', audio='%d']",
						mId, mName, mNameKannada, mShortName, mCropTypeId,
						mResource1, mAudio);
	}
}
