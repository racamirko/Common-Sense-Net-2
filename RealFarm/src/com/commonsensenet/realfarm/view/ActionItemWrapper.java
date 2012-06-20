package com.commonsensenet.realfarm.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.utils.DateHelper;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os (@oscarbolanos)
 * 
 */
public class ActionItemWrapper {
	/** Date in which the action was incurred. */
	private TextView mDate;
	/** Icon that represents the action performed. */
	private ImageView mActionIcon;
	/** Icon that represents the main crop inside that plot. */
	private ImageView mCropIcon;
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
		this.mRow = row;
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

	public ImageView getCropIcon() {
		if (mCropIcon == null) {
			mCropIcon = (ImageView) mRow.findViewById(R.id.icon_diary_crop);
		}
		return (mCropIcon);
	}

	public TextView getTitle() {

		if (mTitle == null) {
			mTitle = (TextView) mRow.findViewById(R.id.label_diary_title);
		}
		return (mTitle);
	}

	public void populateFrom(Action action, RealFarmProvider provider,
			Context context) {

		String actionType = action.getActionType();
		int actionIconId = 0;

		// gets the icon based on the text.
		if (actionType.toString().equalsIgnoreCase("Sowing")) {
			actionIconId = R.drawable.ic_72px_sowing;
		} else if (actionType.toString().equalsIgnoreCase("Selling")) {
			actionIconId = R.drawable.ic_72px_irrigation1;
		} else if (actionType.toString().equalsIgnoreCase("Spraying")) {
			actionIconId = R.drawable.ic_72px_spraying3;
		} else if (actionType.toString().equalsIgnoreCase("Fertilizing")) {
			actionIconId = R.drawable.ic_72px_fertilizing1;
		} else if (actionType.toString().equalsIgnoreCase("Harvesting")) {
			actionIconId = R.drawable.ic_72px_harvesting1;
		} else if (actionType.toString().equalsIgnoreCase("Problem")) {
			actionIconId = R.drawable.ic_72px_reporting;
		} else if (actionType.toString().equalsIgnoreCase("Irrigate")) {
			actionIconId = R.drawable.ic_72px_irrigation2;
		}
		// Growing g = findGrowingById(actionList.get(i).getGrowingId());
		// Seed s = mSeeds.get(g.getSeedId());

		// String seedVariety = actionList.get(i).getSeedVariery();

		// sets the parts of the view.
		getActionIcon().setImageResource(actionIconId);
		getTitle().setText(actionType);
		getDate().setText(DateHelper.formatDate(action.getDay(), context));
		getCropIcon().setImageResource(R.drawable.pic_72px_castor);
	}
}
