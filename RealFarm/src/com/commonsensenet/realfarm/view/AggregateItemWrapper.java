package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class AggregateItemWrapper {
	private TextView mCount;
	private ImageView mCropImage;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;
	private TextView mType;

	/**
	 * Creates a new AggregateItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public AggregateItemWrapper(View row) {
		this.mRow = row;
	}

	public TextView getCount() {

		if (mCount == null) {
			mCount = (TextView) mRow.findViewById(R.id.label_plot_title);
		}
		return (mCount);
	}

	public ImageView getCropImage() {
		if (mCropImage == null) {
			mCropImage = (ImageView) mRow.findViewById(R.id.icon_plot_crop);
		}
		return (mCropImage);
	}

	public TextView getType() {

		if (mType == null) {
			mType = (TextView) mRow.findViewById(R.id.label_plot_title);
		}
		return (mType);
	}

	public void populateFrom(Plot plot, RealFarmProvider provider) {

		// getType().setText(plot.getSoilType());
		// getCount().setText("12");
		// getCropImage().setImageResource(seed.getRes());
	}
}
