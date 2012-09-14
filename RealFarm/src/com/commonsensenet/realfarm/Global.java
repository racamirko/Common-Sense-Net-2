package com.commonsensenet.realfarm;

import android.graphics.Bitmap;

public class Global {

	/** Indicates whether the current user is an Administrator. */
	public static int IsAdmin = 0;
	/** Indicates if the Audio is enabled or not. */
	public static boolean isAudioEnabled = true;
	/** Id of the current plot. */
	public static long plotId = -1;
	/** Rotated Bitmap of the taken picture. */
	public static Bitmap rotated;
	/** Class representing the action to be performed. */
	public static Class<?> selectedAction = null;
	/** Identifier of the current user. */
	public static long userId = -1;

}
