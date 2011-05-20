package com.commonsensenet.realfarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

public class MapCrawler extends Activity {
	private Bitmap mBitmap;
	/** Called when the activity is first created. */

	private String mExternalDirectoryPath;

	private Bitmap DownloadImage(String URL) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bitmap;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crawler);

		mExternalDirectoryPath = Environment.getExternalStorageDirectory()
				.toString();
		mBitmap = DownloadImage("http://www.allindiaflorist.com/imgs/arrangemen4.jpg");
		ImageView img = (ImageView) findViewById(R.id.img);
		img.setImageBitmap(mBitmap);
		saveImage("Prueba.png");
	}

	private InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

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
		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}
	
	public void saveImage(String fileName) {
		//
		OutputStream outStream = null;
		File file = new File(mExternalDirectoryPath, fileName);
		try {
			outStream = new FileOutputStream(file);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();

			Toast.makeText(MapCrawler.this, "Saved", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(MapCrawler.this, e.toString(), Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(MapCrawler.this, e.toString(), Toast.LENGTH_LONG)
					.show();
		}

	}
}
