package com.commonsensenet.realfarm;

import android.graphics.Bitmap;

public class Global {

	/** Indicates whether the current user is an Administrator. */
	public static int isAdmin = 0;
	/** Id of the current plot. */
	public static long plotId = -1;
	/** Rotated Bitmap of the taken picture. */
	public static Bitmap rotated;
	/** Class representing the action to be performed. */
	public static Class<?> selectedAction = null;
	/** Identifier of the current user. */
	public static long userId = -1;

}
