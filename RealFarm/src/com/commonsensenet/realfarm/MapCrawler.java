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
	 * Defines the ratio between distance and pixels in GoogleMaps. This ratios
	 * is for images with a zoom level of 17. By default, GoogleMaps doubles the
	 * area in every zoom level, so the ratio for other zoom levels can be
	 * calculated accordingly.
	 */
	public static final double DISTANCE_PIXEL_RATIO = 230.0 / 400.0;
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
	/**
	 * Defines the ratio between pixels and distance in GoogleMaps. This ratio
	 * is for images with a zoom level of 17. By default, GoogleMaps doubles the
	 * area in every zoom level, so the ratio for other zoom levels can be
	 * calculated accordingly.
	 */
	public static final double PIXEL_DISTANCE_RATIO = 400.0 / 230.0;
	/** Default size of the tiles in pixels. */
	public static final int TILE_SIZE = 400;

	/** External path in the sdcard where the maps will be stored. */
	private String mExternalDirectoryPath;
	/**
	 * Class used to download images. It handles the downloading process
	 * asynchronously.
	 */
	private ImageDownloader mImageDownloader;
	/** Number of images that needs to be downloaded to complete the maps. */
	private int mImagesToDownload = 0;
	/** Information about the tiles that constitute the map. */
	private Hashtable<String, TileData> mMapTiles;

	/** Folder in which the images will be saved. */
	private String mTargetFolder;

	/**
	 * Creates the folder were the images will be saved.
	 * 
	 * @param folderName
	 *            name of the folder. It represents the coordinates of the
	 *            center of the map.
	 */
	private void createTargetFolder(String folderName) {
		// create a File object for the parent directory
		File mapDir = new File(mExternalDirectoryPath + folderName);

		// deletes any pre-existing folder.
		if (mapDir.exists())
			mapDir.delete();

		// have the object build the directory structure, if needed.
		mapDir.mkdirs();
	}

	/**
	 * Creates the URL's and downloads the images used to create the map.
	 */
	private void downloadMapImages() {

		String centerString = ((EditText) findViewById(R.id.etCenter))
				.getText().toString();
		String mapType = ((Spinner) findViewById(R.id.spMapType))
				.getSelectedItem().toString().toLowerCase();
		// EditText etZoom = (EditText) findViewById(R.id.etZoom);

		int radius = Integer.parseInt(((EditText) findViewById(R.id.etRadius))
				.getText().toString());

		// TODO: change this zoom.
		int zoomLevel = 17; // initial zoom level.

		// gets the center coordinates from the interface
		GeoPoint center = new GeoPoint(centerString);

		// stores the path were the maps will be downloaded
		mTargetFolder = centerString + "/";

		// creates the target folder
		createTargetFolder(mTargetFolder);

		// generates the tiles for the initial zoom level.
		generateMapTiles(center.getLatitude(), center.getLongitude(), radius,
				zoomLevel, mapType);

		// downloads the images
		Enumeration<String> keys = mMapTiles.keys();
		while (keys.hasMoreElements()) {
			mImageDownloader.download(keys.nextElement(), MapCrawler.this);
		}
	}

	/**
	 * Generates the map tiles for the desired zoom level. These tiles are
	 * created using the given coordinate and the radius of the area that has to
	 * be covered. The generated tile information is inserted into the mMapTiles
	 * hash.
	 * 
	 * @param centerLat
	 *            latitude of the center point of the map.
	 * @param centerLon
	 *            longitude of the center point of the map.
	 * @param radius
	 *            radius in meters of the area that wants to be covered.
	 * @param zoomLevel
	 *            zoom level of the images
	 * @param mapType
	 *            type of map that will be used, it can be satellite, terrain,
	 *            roadmap and hybrid.
	 */
	private void generateMapTiles(double centerLat, double centerLon,
			double radius, int zoomLevel, String mapType) {

		// distance between centers, in degrees, with zoom level 17, with 400px images.
		final double CONSTANT =  0.0043125;
		// size of the watermark in degrees, with zoom level 17.
		// the watermark will always be 26px high, given that the tile size
		// is between 100 and 640 pixels.
		final double WATERMARK = 0.000280;

		// fixes half the watermark since the size difference is
		// distributed.
		centerLat -= WATERMARK * 0.5; // 13px with zoom 17.

		// offset used to calculate the required tiles.
		double tileOffset;

		// number of pixels that should be covered
		double tmp = radius * PIXEL_DISTANCE_RATIO;

		// number of tiles needed to cover that distance.
		int tilesNeeded = (int) Math.ceil(tmp / TILE_SIZE);

		// stores the number of maps to download.
		mImagesToDownload = tilesNeeded * tilesNeeded;

		// if the number if tiles needed is odd it means that the center
		// of the grid will contain as a center the desired coordinate,
		// otherwise, if it is even, the desired coordinate will be
		// the center of the corners in the middle of the grid.
		if (tilesNeeded % 2 == 0) {
			tileOffset = (int) (tilesNeeded * 0.5) - 0.5;
		} else {
			tileOffset = (int) ((tilesNeeded - 1) * 0.5);
		}

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
						TILE_SIZE, TILE_SIZE + GOOGLE_MAPS_WATERMARK_SIZE,
						zoomLevel, mapType);
				// stores the tile data using the URL as a key.
				mMapTiles.put(MapUrlBuilder.getTileUrl(tmpTileData),
						tmpTileData);

				lon += CONSTANT;
			}

			// updates latitude
			lat += -CONSTANT + WATERMARK * 0.5;
			// resets the longitude
			lon = centerLon - CONSTANT * tileOffset;
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
		// structure were the generated tiles are stored.
		mMapTiles = new Hashtable<String, TileData>();

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
						.getText().toString()), TILE_SIZE + "x"
						+ (TILE_SIZE + GOOGLE_MAPS_WATERMARK_SIZE)),
						MapCrawler.this);
			}
		});
	}

	/**
	 * 
	 * Method called then the download by the ImageDownloader is complete.
	 * 
	 * @param bitmap
	 *            bitmap that represents the downloaded image.
	 * @param url
	 *            url where the image was downloaded.
	 */
	public void onDownloadComplete(Bitmap bitmap, String url) {

		if (bitmap != null) {
			// gets the view where the image will be saved.
			ImageView imgView = (ImageView) findViewById(R.id.imgPreview);
			imgView.setImageBitmap(bitmap);

			// obtains and removes the value from the hash.
			TileData data = mMapTiles.remove(url);
			// saves the image.
			saveImage(
					mTargetFolder + "mapTile_" + data.getX() + "_"
							+ data.getY() + ".png", bitmap);

			if (mMapTiles.isEmpty()) {
				Toast.makeText(MapCrawler.this,
						"Downloaded " + mImagesToDownload + " image(s)",
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

		// removes any previously existing file with the same name
		if (file.exists()) {
			file.delete();
		}

		try {
			outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, outStream);
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
