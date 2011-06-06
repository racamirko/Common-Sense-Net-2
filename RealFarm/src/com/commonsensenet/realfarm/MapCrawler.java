package com.commonsensenet.realfarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.commonsensenet.realfarm.map.utils.GoogleStaticMapsUrl;
import com.commonsensenet.realfarm.map.utils.ImageDownloader;
import com.commonsensenet.realfarm.map.utils.ImageDownloaderNotifiable;

public class MapCrawler extends Activity implements ImageDownloaderNotifiable {

	/**
	 * Defines the ratio between pixels and distance in GoogleMaps. This ratio
	 * is for images with a zoom level of 17. By default, GoogleMaps doubles the
	 * area in every zoom level, so the pixel distance ratio can be modified
	 * accordingly
	 */
	public static final double DISTANCE_PIXEL_RATIO = 400.0 / 230.0;
	/** Indicates the size of the watermark in pixels. 25.8 */
	public static final int GOOGLE_MAPS_WATERMARK_SIZE = 26;
	/** Path where the maps will be downloaded. */
	public static final String MAPS_FOLDER = "/realfarm/maps/";
	/** Defines the maximum level of zoom that will be fetched. */
	public static final int MAX_ZOOM_LEVEL = 20;

	/**
	 * Defines the maximum amount of zoom levels that can be obtained from the
	 * initial zoom.
	 */
	public static final int MAX_ZOOM_LEVELS = 3;

	// min size should be 100x100
	public static final String TILE_SIZE = "400x400";

	private String mExternalDirectoryPath;
	/**
	 * Class used to download images. It handles the downloading process
	 * asynchronously.
	 */
	private ImageDownloader mImageDownloader;

	/**
	 * Creates the URL's and downloads the images used to create the map.
	 */
	private void downloadMapImages() {

		String center = ((EditText) findViewById(R.id.etCenter)).getText()
				.toString();
		String mapType = ((Spinner) findViewById(R.id.spMapType))
				.getSelectedItem().toString().toLowerCase();
		// EditText etZoom = (EditText) findViewById(R.id.etZoom);

		final int tileSize = 400;
		int radius = 460; // meters
		String zoomLevel = "17"; // initial zoom level.

		// number of pixels that should be covered
		double tmp = radius * DISTANCE_PIXEL_RATIO; // should be approximately
													// 400

		// number of tiles needed to cover that distance.
		int tilesNeeded = (int) Math.ceil(tmp / tileSize);

		// if the number if tiles needed is odd it means that the center
		// of the grid will contain as a center the desired coordinate,
		// otherwise, if it is even, the desired coordinate will be
		// the center of the corners in the middle of the grid.
		Log.w("MapCrawler", "" + "Tiles needed to cover distance: "
				+ tilesNeeded);

		// creates the grid in order to assign the URLs
		String[][] grid = new String[tilesNeeded][tilesNeeded];

		if (tilesNeeded % 2 == 0) // even number
		{
			// since it's an even grid, first the top left coordinate must be
			// located.
			String[] coordinates = center.split(",");

			// gets the coordinates from the given string.
			double centerLat = Double.parseDouble(coordinates[0]);
			double centerLon = Double.parseDouble(coordinates[1]);

			// fixes the latitude to take into consideration the Google
			// watermark.
			final double CONSTANT = 0.0043125;
			final double WATERMARK = 0.000280;

			centerLat -= WATERMARK * 0.5; // 13px with 17 zoom.

			// gets the first center
			double lat = centerLat + CONSTANT * 0.5;
			double lon = centerLon - CONSTANT * 0.5;

			for (int x = 0; x < tilesNeeded; x++) {
				for (int y = 0; y < tilesNeeded; y++) {
					grid[x][y] = prepareURL(lat + "," + lon, mapType,
							zoomLevel, "400x426");

					lon += CONSTANT;

				}
				lat += -CONSTANT + WATERMARK * 0.5;
				lon = centerLon - CONSTANT * 0.5;
			}
			Log.w("MapCrawler", "Grid created");

			// sends the strings to download using the helper class
			for (int x = 0; x < tilesNeeded; x++) {
				for (int y = 0; y < tilesNeeded; y++) {
					mImageDownloader.download(grid[x][y], null);
				}
			}

		} else // odd number
		{

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.crawler);

		// creates the data that will be displayed in the spinner.
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.arrayMapTypes,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// sets the data
		Spinner s = (Spinner) findViewById(R.id.spMapType);
		s.setAdapter(adapter);

		// gets the buttons from the layout
		Button btnDownload = (Button) findViewById(R.id.btnDownload);
		Button btnPreview = (Button) findViewById(R.id.btnPreview);

		// creates the downloader.
		mImageDownloader = new ImageDownloader();

		// add the event listeners
		btnDownload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				downloadMapImages();
			}
		});

		btnPreview.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// gets the values from the UI.
				EditText etCenter = (EditText) findViewById(R.id.etCenter);
				Spinner spMapType = (Spinner) findViewById(R.id.spMapType);
				EditText etZoom = (EditText) findViewById(R.id.etZoom);

				// downloads the image
				mImageDownloader.download(
						prepareURL(etCenter.getText().toString(), spMapType
								.getSelectedItem().toString().toLowerCase(),
								etZoom.getText().toString(), TILE_SIZE),
						MapCrawler.this);
			}
		});

		// gets the path of the external storage
		mExternalDirectoryPath = Environment.getExternalStorageDirectory()
				.toString();

		/*
		 * mBitmap =
		 * downloadImage("http://www.allindiaflorist.com/imgs/arrangemen4.jpg");
		 * ImageView img = (ImageView) findViewById(R.id.imgPreview);
		 * img.setImageBitmap(mBitmap); saveImage("Prueba.png");
		 */
	}

	public void onDownloadComplete(Bitmap bitmap) {
	
		// gets the view where the image will be saved.
		ImageView imgView = (ImageView) findViewById(R.id.imgPreview);
		imgView.setImageBitmap(bitmap);
	}

	protected String prepareURL(String center, String maptype, String zoom,
			String size) {

		GoogleStaticMapsUrl params = new GoogleStaticMapsUrl();
		params.addParam(GoogleStaticMapsUrl.CENTER_PARAM,
				center.replaceAll(" ", "+"));
		params.addParam(GoogleStaticMapsUrl.MAP_TYPE_PARAM, maptype);
		params.addParam(GoogleStaticMapsUrl.ZOOM_PARAM, zoom);
		params.addParam(GoogleStaticMapsUrl.SIZE_PARAM, size);
		params.addParam(GoogleStaticMapsUrl.SENSOR_PARAM, "false");

		return params.getUrlString();
	}

	/**
	 * Saves the given Bitmap in the specified path. The Bitmap is stored
	 * using the PNG format.
	 * 
	 * @param fileName name that the file will have
	 * @param bitmap Bitmap to save as a file.
	 * 
	 * @return returns true if the file was saved successfully.
	 */
	public Boolean saveImage(String fileName, Bitmap bitmap) {
		//
		OutputStream outStream = null;
		File file = new File(mExternalDirectoryPath, fileName);
		try {
			outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();

			Toast.makeText(MapCrawler.this, "Saved", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(MapCrawler.this, e.toString(), Toast.LENGTH_LONG)
					.show();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(MapCrawler.this, e.toString(), Toast.LENGTH_LONG)
					.show();
			
			return false;
		}
		
		return true;
	}
}
