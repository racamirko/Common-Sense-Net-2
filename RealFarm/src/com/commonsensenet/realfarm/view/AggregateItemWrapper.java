package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public abstract class AggregateItemWrapper {
	protected ImageView mTypeImage;
	/** The View object that represents a single row inside the ListView. */
	protected View mRow;
	protected TextView mTypeText;
	protected TextView mUserCount;
	protected TextView mTreatmentCount;
	protected ImageView mUserImg;

	/**
	 * Creates a new AggregateItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public AggregateItemWrapper(View row) {
		this.mRow = row;
	}

	public ImageView getTypeImage() {
		if (mTypeImage == null) {
			mTypeImage = (ImageView) mRow
					.findViewById(R.id.icon_aggregate_crop);
		}
		return (mTypeImage);
	}

	public View getRow() {
		return mRow;
	}

	public TextView getTypeText() {

		if (mTypeText == null) {
			mTypeText = (TextView) mRow.findViewById(R.id.label_aggregate_type);
		}
		return (mTypeText);
	}

	public TextView getUserCount() {

		if (mUserCount == null) {
			mUserCount = (TextView) mRow
					.findViewById(R.id.label_aggregate_user_count);
		}
		return (mUserCount);
	}
	
	public ImageView getUserImg(){
		if (mUserImg == null) {
			mUserImg = (ImageView) mRow
					.findViewById(R.id.img_aggregate_user_count);
		}
		return (mUserImg);		
	}
	
	public TextView getTreatmentCount() {

		if (mTreatmentCount == null) {
			mTreatmentCount = (TextView) mRow
					.findViewById(R.id.label_aggregate_detail_count);
		}
		return (mTreatmentCount);
	}

	public abstract void populateFrom(AggregateItem aggregate,
			RealFarmProvider provider);
}
