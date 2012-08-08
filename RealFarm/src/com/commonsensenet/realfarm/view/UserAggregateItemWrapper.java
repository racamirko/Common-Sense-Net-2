package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class UserAggregateItemWrapper {

	private TextView mDate;
	private ImageView mUserAvatar;
	private TextView mUserName;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;

	/**
	 * Creates a new AggregateItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public UserAggregateItemWrapper(View row) {
		mRow = row;
	}

	public TextView getDate() {

		if (mDate == null) {
			mDate = (TextView) mRow
					.findViewById(R.id.label_user_aggregate_date);
		}
		return (mDate);
	}

	public ImageView getUserAvatar() {
		if (mUserAvatar == null) {
			mUserAvatar = (ImageView) mRow
					.findViewById(R.id.icon_user_aggregate_user_avatar);
		}
		return (mUserAvatar);
	}

	public TextView getUserName() {

		if (mUserName == null) {
			mUserName = (TextView) mRow
					.findViewById(R.id.label_user_aggregate_user_name);
		}
		return (mUserName);
	}

	public void populateFrom(UserAggregateItem userAggregate,
			RealFarmProvider provider) {

		getUserName().setText(
				userAggregate.getUser().getFirstname() + " "
						+ userAggregate.getUser().getLastname());
		getDate().setText(userAggregate.getDate());

		int resId = mRow.getResources().getIdentifier(
				userAggregate.getUser().getImagePath(), "drawable",
				"com.commonsensenet.realfarm");

		getUserAvatar().setImageResource(resId);

	}
}
