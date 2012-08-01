package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class SeedType {

	/** Help audio used for the seed. */
	private int mAudio;
	/** CropType that this seed represents. */
	private int mCropTypeId;
	/** Unique identifier. */
	private int mId;
	/** Full name of the SeedType. */
	private String mName;
	/** Name of the seed in Kannada language. */
	private String mNameKannada;
	/** Identifier of the Image Resource. */
	private int mResource;
	/** Short name of the SeedType. */
	private String mShortName;

	public SeedType(int id, String name, String nameKannada, String shortName,
			int cropTypeId, int resource, int audio) {
		mId = id;
		mName = name;
		mShortName = shortName;
		mNameKannada = nameKannada;
		mResource = resource;
		mAudio = audio;
		mCropTypeId = cropTypeId;
	}

	public int getAudio() {
		return mAudio;
	}

	public int getCropTypeId() {
		return mCropTypeId;
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

	public int getResource() {
		return mResource;
	}

	public String getShortName() {
		return mShortName;
	}

	@Override
	public String toString() {

		return String
				.format("[SeedType id='%s', name='%s', nameKannada='%s', shortName='%s', cropTypeId='%d', resource='%d', audio='%d']",
						mId, mName, mNameKannada, mShortName, mCropTypeId,
						mResource, mAudio);
	}
}
