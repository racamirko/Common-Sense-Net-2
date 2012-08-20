package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class SeedType extends Resource {

	/** CropType that this seed represents. */
	private int mCropTypeId;

	public SeedType(int id, String name, String shortName, int cropTypeId,
			int image, int audio) {
		super(id, name, shortName, audio, image, -1, -1, -1, -1);

		mCropTypeId = cropTypeId;
	}

	public int getCropTypeId() {
		return mCropTypeId;
	}

	@Override
	public String toString() {

		return String
				.format("[SeedType id='%s', name='%s', shortName='%s', cropTypeId='%d', image='%d', audio='%d']",
						mId, mName, mShortName, mCropTypeId, mImage1, mAudio);
	}
}
