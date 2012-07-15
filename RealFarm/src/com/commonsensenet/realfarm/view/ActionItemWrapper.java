package com.commonsensenet.realfarm.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.SeedType;
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

	// TODO: this should be checked.
	public void populateFrom(Action action, RealFarmProvider provider,
			Context context) {

		// gets the actionName
		ActionName actionName = provider.getActionNameById(action
				.getActionNameId());
		// gets the seed
		SeedType seedType = provider.getSeedById(action.getSeedTypeId());

		// sets the parts of the view.
		getActionIcon().setImageResource(actionName.getRes());
		getTitle().setText(actionName.getName());
		getDate().setText(DateHelper.formatDate(action.getDate(), context));
		if (seedType != null) {
			getCropIcon().setImageResource(seedType.getRes());
		} else {
			getCropIcon().setImageDrawable(null);
		}
	}
}
