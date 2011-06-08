package com.commonsensenet.realfarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

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

import com.commonsensenet.realfarm.map.GeoPoint;
import com.commonsensenet.realfarm.map.TileData;
import com.commonsensenet.realfarm.map.utils.ImageDownloader;
import com.commonsensenet.realfarm.map.utils.MapUrlBuilder;
import com.commonsensenet.realfarm.map.utils.Notifiable;

public class MapCrawler extends Activity implements Notifiable {

	/**
	 * Defines the ratio between pixels and distance in GoogleMaps. This ratio
	 * is for images with a zoom level of 17. By default, GoogleMaps doubles the
	 * area in every zoom level, so the pixel distance ratio can be modified
	 * accordingly
	 */
	public static final double DISTANCE_PIXEL_RATIO = 400.0 / 230.0;
	/** Indicates the size of the watermark in pixels. */
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
	/** Minimum size in pixels that the each tile can be. */
	public static final int MINIMUM_TILE_SIZE = 100;
	/** Default tile size in pixels. */
	public static final String TILE_SIZE = "400x426";

	/** External path in the sdcard where the maps will be stored. */
	private String mExternalDirectoryPath;

	/**
	 * Class used to download images. It handles the downloading process
	 * asynchronously.
	 */
	private ImageDownloader mImageDownloader;

	private int mImagesToDownload = 0;

	/** Information about the tiles that constitute the map. */
	private Hashtable<String, TileData> mMapTiles;

	/**
	 * Creates the folder were the images will be saved.
	 */
	private void createTargetFolder() {
		// create a File object for the parent directory
		File wallpaperDirectory = new File(mExternalDirectoryPath);
		// have the object build the directory structure, if needed.
		wallpaperDirectory.mkdirs();
	}

	/**
	 * Creates the URL's and downloads the images used to create the map.
	 */
	private void downloadMapImages() {

		String center = ((EditText) findViewById(R.id.etCenter)).getText()
				.toString();
		String mapType = ((Spinner) findViewById(R.id.spMapType))
				.getSelectedItem().toString().toLowerCase();
		// EditText etZoom = (EditText) findViewById(R.id.etZoom);

		// TODO: change this tile size.
		final int tileSize = 400;
		// TODO: change the radius.
		int radius = 700; // meters
		// TODO: change this zoom.
		int zoomLevel = 17; // initial zoom level.

		// initializes the structure used to download the images.
		mMapTiles = new Hashtable<String, TileData>();

		// number of pixels that should be covered
		// should be approximately 400
		double tmp = radius * DISTANCE_PIXEL_RATIO;

		// number of tiles needed to cover that distance.
		int tilesNeeded = (int) Math.ceil(tmp / tileSize);

		mImagesToDownload = tilesNeeded * tilesNeeded;

		Log.w("MapCrawler", "" + "Tiles needed to cover distance: "
				+ tilesNeeded);

		// if the number if tiles needed is odd it means that the center
		// of the grid will contain as a center the desired coordinate,
		// otherwise, if it is even, the desired coordinate will be
		// the center of the corners in the middle of the grid.
		if (tilesNeeded % 2 == 0) // even number of tile
		{
			// since it's an even grid, first the top left coordinate must be
			// located.
			String[] coordinates = center.split(",");

			// gets the coordinates from the given string.
			double centerLat = Double.parseDouble(coordinates[0]);
			double centerLon = Double.parseDouble(coordinates[1]);

			// distance between centers, in degrees, with zoom level 17.
			final double CONSTANT = 0.0043125;
			// size of the watermark in degrees, with zoom level 17.
			// the watermark will always be 26px high, given that the tile size
			// is between 100 and 640 pixels.
			final double WATERMARK = 0.000280;

			// fixes half the watermark since the size difference is
			// distributed.
			centerLat -= WATERMARK * 0.5; // 13px with zoom 17.

			double tileOffset = (int) (tilesNeeded * 0.5) - 0.5;

			// gets the first center
			double lat = centerLat + CONSTANT * tileOffset;
			double lon = centerLon - CONSTANT * tileOffset;

			// object used to create all the desired data.
			TileData tmpTileData;

			// calculates the coordinates.
			for (int x = 0; x < tilesNeeded; x++) {
				for (int y = 0; y < tilesNeeded; y++) {

					// creates the new tile data
					tmpTileData = new TileData(new GeoPoint(lat, lon), x, y,
							TILE_SIZE, zoomLevel, mapType);
					// stores the tile data using the URL as a key.
					mMapTiles.put(MapUrlBuilder.getTileUrl(tmpTileData),
							tmpTileData);

					lon += CONSTANT;

				}

				// updates the latitude and longitude
				lat += -CONSTANT + WATERMARK * 0.5;
				lon = centerLon - CONSTANT * 0.5;
			}

			Log.w("MapCrawler", "Grid created");

			// downloads the images
			Enumeration<String> keys = mMapTiles.keys();
			while (keys.hasMoreElements()) {
				mImageDownloader.download(keys.nextElement(), MapCrawler.this);
			}
		} else // odd number
		{

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// creates the downloader.
		mImageDownloader = new ImageDownloader();
		// gets the path of the external storage
		mExternalDirectoryPath = Environment.getExternalStorageDirectory()
				.toString() + MAPS_FOLDER;

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

				// downloads the center image and shows it as a preview
				mImageDownloader.download(MapUrlBuilder.getTileUrl(etCenter
						.getText().toString(), spMapType.getSelectedItem()
						.toString().toLowerCase(), Integer.parseInt(etZoom
						.getText().toString()), TILE_SIZE), MapCrawler.this);
			}
		});

		// creates the target folder
		createTargetFolder();
	}

	public void onDownloadComplete(Bitmap bitmap, String url) {

		if (bitmap != null) {
			// gets the view where the image will be saved.
			ImageView imgView = (ImageView) findViewById(R.id.imgPreview);
			imgView.setImageBitmap(bitmap);
			
			// gets the associated data with the image
			TileData data = mMapTiles.get(url);
			// saves the image.
			saveImage("mapTile_" + data.getX() + "_" + data.getY() + ".png",
					bitmap);

			mImagesToDownload--;

			if (mImagesToDownload == 0) {
				Toast.makeText(MapCrawler.this, "All images downloaded",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Saves the given Bitmap in the specified path. The Bitmap is stored using
	 * the PNG format.
	 * 
	 * @param fileName
	 *            name that the file will have
	 * @param bitmap
	 *            Bitmap to save as a file.
	 * 
	 * @return returns true if the file was saved successfully.
	 */
	public Boolean saveImage(String fileName, Bitmap bitmap) {
		OutputStream outStream = null;
		File file = new File(mExternalDirectoryPath, fileName);
		try {
			outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();

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
