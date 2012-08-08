package com.commonsensenet.realfarm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.utils.DateHelper;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class ActionItemWrapper {
	/** Date in which the action was incurred. */
	private TextView mDate;
	/** Icon that represents the action performed. */
	private ImageView mActionIcon;
	/** Icon that represents the plot. */
	private ImageView mPlotIcon;
	/** The View object that represents a single row inside the ListView. */
	private View mRow;
	/** Title line of the plot. */
	private TextView mTitle;

	/**
	 * Creates a new ActionItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public ActionItemWrapper(View row) {
		mRow = row;
	}

	public TextView getDate() {
		if (mDate == null) {
			mDate = (TextView) mRow.findViewById(R.id.label_diary_date);
		}
		return (mDate);
	}

	public ImageView getActionIcon() {
		if (mActionIcon == null) {
			mActionIcon = (ImageView) mRow.findViewById(R.id.icon_diary_action);
		}
		return (mActionIcon);
	}

	public ImageView getPlotIcon() {
		if (mPlotIcon == null) {
			mPlotIcon = (ImageView) mRow.findViewById(R.id.icon_diary_plot);
		}
		return (mPlotIcon);
	}

	public TextView getTitle() {

		if (mTitle == null) {
			mTitle = (TextView) mRow.findViewById(R.id.label_diary_title);
		}
		return (mTitle);
	}

	public void populateFrom(Action action, RealFarmProvider provider,
			Context context) {

		// gets the actionType
		ActionType actionType = provider.getActionTypeById(action
				.getActionTypeId());

		// gets the plot from the action.
		Plot plot = provider
				.getPlotById(action.getPlotId(), action.getUserId());

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		options.inSampleSize = 12;

		Bitmap bitmapImage = BitmapFactory.decodeFile(plot.getImagePath(),
				options);

		// sets the parts of the view.
		getActionIcon().setImageResource(actionType.getImage1());
		getTitle().setText(actionType.getName());
		getDate().setText(DateHelper.formatDate(action.getDate(), context));

		if (bitmapImage != null) {
			getPlotIcon().setImageBitmap(bitmapImage);
		}
	}
}
