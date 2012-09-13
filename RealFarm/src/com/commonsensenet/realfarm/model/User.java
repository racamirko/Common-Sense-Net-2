package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class User implements Model {

	/** Identifier if the device the User has. */
	private String mDeviceId;
	/** First name of the User. */
	private String mFirstname;
	/** Unique User Identifier */
	private long mId;
	/** Path where the image of the User is located. */
	private String mImagePath;
	/**
	 * Flag that indicates whether the user was inserted by an administrator or
	 * not.
	 */
	private int mIsAdminAction;
	/** Flag that indicates whether the user is enabled or not. */
	private int mIsEnabled;
	/** Flag that indicates whether the user data has been sent to the server. */
	private int mIsSent;
	/** Lastname of the User. */
	private String mLastname;
	/** Location of the user. */
	private String mLocation;
	/** Audio resource that represents the location. */
	private int mLocationAudio;
	/** Number number used to contact the User. */
	private String mMobileNumber;
	/** Audio resource that represents the name. */
	private int mNameAudio;
	/** Timestamp that indicates the creation of the User. */
	private long mTimestamp;

	public User(long id, String firstname, String lastname,
			String mobileNumber, String deviceId, String imagePath,
			String location, int isSent, int isEnabled, int isAdminAction,
			long timestamp, int nameAudio, int locationAudio) {
		mId = id;
		mFirstname = firstname;
		mLastname = lastname;
		mMobileNumber = mobileNumber;
		mDeviceId = deviceId;
		mImagePath = imagePath;
		mLocation = location;
		mIsSent = isSent;
		mIsEnabled = isEnabled;
		mIsAdminAction = isAdminAction;
		mTimestamp = timestamp;
		mNameAudio = nameAudio;
		mLocationAudio = locationAudio;
	}

	public String getDeviceId() {
		return mDeviceId;
	}

	public String getFirstname() {
		return mFirstname;
	}

	public long getId() {
		return mId;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public int getIsAdminAction() {
		return mIsAdminAction;
	}

	public int getIsEnabled() {
		return mIsEnabled;
	}

	public int getIsSent() {
		return mIsSent;
	}

	public String getLastname() {
		return mLastname;
	}

	public String getLocation() {
		return mLocation;
	}

	public int getLocationAudio() {
		return mLocationAudio;
	}

	public String getMobileNumber() {
		return mMobileNumber;
	}

	public int getModelTypeId() {
		return 1002;
	}

	public int getNameAudio() {
		return mNameAudio;
	}

	public long getTimestamp() {
		return mTimestamp;
	}

	public String toSmsString() {
		return "%" + getModelTypeId() + "%" + getId() + "#" + getFirstname()
				+ "#" + getLastname() + "#" + getMobileNumber() + "#"
				+ getDeviceId() + "#" + getImagePath() + "#" + getLocation()
				+ "#" + getIsEnabled() + "#" + getIsAdminAction() + "#"
				+ getTimestamp() + "%";

	}

	@Override
	public String toString() {

		return String
				.format("[User id='%s', firstName='%s', lastName='%s', mobileNumber='%s', deviceId='%s', imagePath='%s', location='%s', isSent='%d', isEnabled='%d', isAdminAction='%d', timestamp='%s']",
						mId, mFirstname, mLastname, mMobileNumber, mDeviceId,
						mImagePath, mLocation, mIsSent, mIsEnabled,
						mIsAdminAction, mTimestamp);

	}
}
