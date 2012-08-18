package com.commonsensenet.realfarm.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
	/** The View object that represents a single row inside the ListView. */
	protected View mRow;

	protected RelativeLayout relativeLayoutLeft;
	protected RelativeLayout relativeLayoutCenter;
	protected RelativeLayout relativeLayoutRight;
	protected TextView labelLeft;
	protected TextView labelNews;
	protected TextView labelCenter;
	protected TextView labelRight;
	protected ImageView imageLeft;
	protected ImageView imageCenter;
	protected ImageView imageLeftBottom;
	
	/**
	 * Creates a new AggregateItemWrapper instance.
	 * 
	 * @param row
	 *            the View where the info will be presented.
	 */
	public AggregateItemWrapper(View row) {
		mRow = row;
	}
	
	public RelativeLayout getRelativeLayoutLeft() {
		if (relativeLayoutLeft == null) {
			relativeLayoutLeft = (RelativeLayout) mRow
					.findViewById(R.id.relative_layout_left);
		}
		return (relativeLayoutLeft);
	}
	
	public RelativeLayout getRelativeLayoutRight() {
		if (relativeLayoutRight == null) {
			relativeLayoutRight = (RelativeLayout) mRow
					.findViewById(R.id.relative_layout_right);
		}
		return (relativeLayoutRight);
	}
	
	public RelativeLayout getRelativeLayoutCenter() {
		if (relativeLayoutCenter == null) {
			relativeLayoutCenter = (RelativeLayout) mRow
					.findViewById(R.id.relative_layout_center);
		}
		return (relativeLayoutCenter);
	}
	
	public TextView getLabelLeft() {
		if (labelLeft == null) {
			labelLeft = (TextView) mRow
					.findViewById(R.id.label_left);
		}
		return (labelLeft);
	}

	public TextView getLabelRight() {
		if (labelRight == null) {
			labelRight = (TextView) mRow
					.findViewById(R.id.label_right);
		}
		return (labelRight);
	}
	
	public TextView getLabelCenter() {
		if (labelCenter == null) {
			labelCenter = (TextView) mRow
					.findViewById(R.id.label_center);
		}
		return (labelCenter);
	}
	
	public TextView getLabelNews() {
		if (labelNews == null) {
			labelNews = (TextView) mRow
					.findViewById(R.id.label_news);
		}
		return (labelNews);
	}
	
	public ImageView getImageLeft() {
		if (imageLeft == null) {
			imageLeft = (ImageView) mRow
					.findViewById(R.id.image_left);
		}
		return (imageLeft);
	}
	
	public ImageView getImageCenter() {
		if (imageCenter == null) {
			imageCenter = (ImageView) mRow
					.findViewById(R.id.image_center);
		}
		return (imageCenter);
	}
	
	public ImageView getImageLeftBottom() {
		if (imageLeftBottom == null) {
			imageLeftBottom = (ImageView) mRow
					.findViewById(R.id.image_left_bottom);
		}
		return (imageLeftBottom);
	}
	
	public View getRow() {
		return mRow;
	}
	
	public abstract void populateFrom(AggregateItem aggregate,
			RealFarmProvider provider);

	public void copyView(AggregateItem aggregate, View destination) {
		destination.setBackgroundColor(Color.LTGRAY);
		
		TextView tw = (TextView)destination.findViewById(R.id.label_news);
		tw.setText(aggregate.getNewsText());
		tw.setBackgroundColor(Color.parseColor("#FFFFCC"));		
		
		tw = (TextView)destination.findViewById(R.id.label_left);
		tw.setText(aggregate.getLeftText());
				
		RelativeLayout rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_left);
		if(aggregate.getLeftBackground() != -1) rl.setBackgroundResource(aggregate.getLeftBackground());
		else tw.setTextColor(Color.BLACK);

		tw = (TextView)destination.findViewById(R.id.label_center);
		tw.setText(aggregate.getCenterText());
		
		rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_center);
		if(aggregate.getCenterBackground() != -1) rl.setBackgroundResource(aggregate.getCenterBackground());
		else {
			tw.setTextColor(Color.BLACK);
			// hack
			rl.getLayoutParams().width = 200;
			tw.setTextSize(20);
		}
		
		ImageView iw = (ImageView)destination.findViewById(R.id.image_center);
		if(aggregate.getCenterImage() != -1) iw.setImageResource(aggregate.getCenterImage());

		tw = (TextView)destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());
		
		iw = (ImageView)destination.findViewById(R.id.image_left);
		if(aggregate.getLeftImage() != -1) iw.setImageResource(aggregate.getLeftImage());
		
		iw = (ImageView)destination.findViewById(R.id.image_left_bottom);
		if(aggregate.getLeftBottomImage() != -1) iw.setImageResource(aggregate.getLeftBottomImage());
	}
}
