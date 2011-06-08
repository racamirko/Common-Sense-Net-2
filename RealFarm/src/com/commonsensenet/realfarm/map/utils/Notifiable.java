package com.commonsensenet.realfarm.map.utils;

import android.graphics.Bitmap;

/**
 * Interface used to notify the status an image that is being downloaded.
 * 
 * @author Oscar Bola–os (oscar.bolanos@epfl.ch)
 * 
 */
public interface Notifiable {

	/**
	 * Notifies that the download is complete. The downloaded bitmap is given as
	 * a result as well as the url where it was downloaded.
	 * 
	 * @param bitmap
	 *            bitmap what was downloaded.
	 * @param url
	 *            url where the bitmap was downloaded.
	 */
	public void onDownloadComplete(Bitmap bitmap, String url);

}
