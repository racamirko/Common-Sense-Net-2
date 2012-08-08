package com.commonsensenet.realfarm.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.SeedType;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class PlotItemWrapper {
	/** Description line of the plot. */
	private TextView mDescription;
	/** Icon that represents the plot. */
	private ImageView mIcon;
	/** Icon that represents the main crop inside that plot. */
	private ImageView mCropIcon;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;
	/** Title line of the plot. */
	private TextView mTitle;

	/**
	 * Creates a new PlotItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public PlotItemWrapper(View row) {
		mRow = row;
	}

	public TextView getDescription() {
		if (mDescription == null) {
			mDescription = (TextView) mRow
					.findViewById(R.id.label_plot_description);
		}
		return (mDescription);
	}

	public ImageView getIcon() {
		if (mIcon == null) {
			mIcon = (ImageView) mRow.findViewById(R.id.icon_plot);
		}
		return (mIcon);
	}

	public ImageView getCropIcon() {
		if (mCropIcon == null) {
			mCropIcon = (ImageView) mRow.findViewById(R.id.icon_plot_crop);
		}
		return (mCropIcon);
	}

	public TextView getTitle() {

		if (mTitle == null) {
			mTitle = (TextView) mRow.findViewById(R.id.label_plot_title);
		}
		return (mTitle);
	}

	public void populateFrom(Plot plot, RealFarmProvider provider) {

		// TODO: this shouldn't be done here due to performance issues!!!
		SeedType seed = provider.getSeedById(plot.getSeedTypeId());
		Resource soilType = provider.getSoilTypeById(plot.getSoilTypeId());

		// Bitmap bitmap = BitmapFactory.decodeFile(plot.getImageName());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		options.inSampleSize = 12;

		Bitmap bitmapImage = BitmapFactory.decodeFile(plot.getImagePath(),
				options);

		getIcon().setImageBitmap(bitmapImage);
		getTitle().setText(soilType.getName());
		getDescription().setText(seed.getName());
		getCropIcon().setImageResource(seed.getImage1());
	}
}
