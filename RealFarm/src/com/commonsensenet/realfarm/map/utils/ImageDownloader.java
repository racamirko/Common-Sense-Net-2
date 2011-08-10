package com.commonsensenet.realfarm.map.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageDownloader {

	private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

		private final WeakReference<Notifiable> mNotifiableReference;
		private String mUrl;

		public BitmapDownloaderTask(Notifiable notifiable) {
			mNotifiableReference = new WeakReference<Notifiable>(notifiable);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			mUrl = params[0];
			return downloadBitmap(mUrl);
		}

		/**
		 * Once the image is downloaded, the referenced notifiable is notified.
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {

			// result is nulled if the task has been canceled.
			if (isCancelled()) {
				bitmap = null;
			}

			// if the reference is valid, it is notified that the download is
			// complete.
			if (mNotifiableReference != null) {
				Notifiable notifiable = mNotifiableReference.get();
				if (notifiable != null)
					notifiable.onDownloadComplete(bitmap, mUrl);
			}
		}
	}

	/**
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF. It it used to fix a bug in the Android system when a slow
	 * connection is experienced.
	 * 
	 */
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	/** Tag used in the logger to keep track of errors. */
	private static String LOG_TAG = "ImageDownloader";
	/** Mode in which the image will be downloaded. */
	private DownloadMode mMode = DownloadMode.NO_ASYNC_TASK;

	/**
	 * Downloads the specified image from the Internet. After the download is
	 * complete, the given Notifiable is notified. The downloading of the image
	 * will be done following the current DownloadMode.
	 * 
	 * @param url
	 *            The URL of the image to download.
	 * @param notifiable
	 *            Object that will be notified once the download is complete.
	 * 
	 */
	public void download(String url, Notifiable notifiable) {

		// TODO: must check if it not already downloading it.
		if (url == null) {
			return;
		}

		// downloading method differs depending on the mode.
		switch (mMode) {
		case NO_ASYNC_TASK:
			Bitmap bitmap = downloadBitmap(url);
			notifiable.onDownloadComplete(bitmap, url);
			break;
		case ASYNC_TASK:
			BitmapDownloaderTask task = new BitmapDownloaderTask(notifiable);
			task.execute(url);
			break;
		}
	}

	/**
	 * Downloads the Bitmap found in the given URL.
	 * 
	 * @param url
	 *            URL where the image is found.
	 * 
	 * @return a bitmap
	 */
	private Bitmap downloadBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;

		try {
			in = openHttpConnection(url);
			// FlushedInputStream fixes bug on slow speed connection.
			bitmap = BitmapFactory.decodeStream(new FlushedInputStream(in));
			in.close();
		} catch (IOException e) {
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		}
		return bitmap;
	}

	/**
	 * Gets the current mode in which images are downloaded.
	 * 
	 * @return current mode.
	 */
	public DownloadMode getMode() {
		return mMode;
	}

	/**
	 * Creates a new HTTP Connection with the configuration required to download
	 * the given URL.
	 * 
	 * @param urlString
	 *            the URL where the file will be downloaded.
	 * 
	 * @return an InputStream that represents the given URL.
	 * 
	 * @throws IOException
	 */
	private InputStream openHttpConnection(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection)) {
			throw new IOException("Not an HTTP connection");
		}
		try {

			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception e) {
			Log.w(LOG_TAG, "Error while opening the httpConnection " + url, e);
			throw new IOException("Error connecting");
		}

		return in;
	}

	/**
	 * Sets the mode in which the images will be downloaded.
	 * 
	 * @param mode
	 *            Mode in which the image will be downloaded.
	 */
	public void setMode(DownloadMode mode) {
		mMode = mode;
	}
}
