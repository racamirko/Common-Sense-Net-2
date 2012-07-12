package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.AggregateItem;
import com.commonsensenet.realfarm.model.SeedType;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class AggregateItemWrapper {
	private TextView mUserCount;
	private ImageView mCropImage;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;
	private TextView mSeedType;

	/**
	 * Creates a new AggregateItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public AggregateItemWrapper(View row) {
		this.mRow = row;
	}

	public TextView getUserCount() {

		if (mUserCount == null) {
			mUserCount = (TextView) mRow
					.findViewById(R.id.label_aggregate_user_count);
		}
		return (mUserCount);
	}

	public ImageView getCropImage() {
		if (mCropImage == null) {
			mCropImage = (ImageView) mRow
					.findViewById(R.id.icon_aggregate_crop);
		}
		return (mCropImage);
	}

	public TextView getSeedType() {

		if (mSeedType == null) {
			mSeedType = (TextView) mRow.findViewById(R.id.label_aggregate_type);
		}
		return (mSeedType);
	}

	public void populateFrom(AggregateItem aggregate, RealFarmProvider provider) {

		SeedType seed = provider.getSeedById(aggregate.getSeedTypeId());
		getUserCount().setText(String.valueOf(aggregate.getUserCount()));
		getSeedType()
				.setText(
						(seed.getVariety() != null && !seed.getVariety()
								.equals("")) ? seed.getVariety() : seed
								.getName());
		getCropImage().setImageResource(seed.getResBg());
	}
}
