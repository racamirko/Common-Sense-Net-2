package com.commonsensenet.realfarm.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;

/**
 * Popup window, shows action list as icon and text like the one in Gallery3D
 * app.
 * 
 * @author Lorensius. W. T
 */
public class PlotInformationWindow extends CustomPopupWindow {
	protected static final int ANIM_AUTO = 5;
	protected static final int ANIM_GROW_FROM_CENTER = 3;
	protected static final int ANIM_GROW_FROM_LEFT = 1;
	protected static final int ANIM_GROW_FROM_RIGHT = 2;
	protected static final int ANIM_REFLECT = 4;
	protected static final int ANIM_NONE = 6;

	private ArrayList<ActionItem> mActionList;
	private int mAnimStyle;
	private final Context mContext;
	private final LayoutInflater mInflater;
	private ViewGroup mTrack;
	private final View mRoot;
	private ScrollView mScroller;
	private ViewGroup mActionsPanel;

	/**
	 * Constructor
	 * 
	 * @param anchor
	 *            {@link View} on where the popup window should be displayed
	 */
	public PlotInformationWindow(View anchor) {
		super(anchor);

		mActionList = new ArrayList<ActionItem>();
		mContext = anchor.getContext();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mRoot = mInflater.inflate(R.layout.plotinformation, null);

		setContentView(mRoot);

		mTrack = (ViewGroup) mRoot.findViewById(R.id.tracks);
		mScroller = (ScrollView) mRoot.findViewById(R.id.scroller);
		mActionsPanel = (ViewGroup) mRoot.findViewById(R.id.actionPanel);
		mAnimStyle = ANIM_GROW_FROM_CENTER;
	}

	/**
	 * Add action item
	 * 
	 * @param action
	 *            {@link ActionItem} object
	 */
	public void addActionItem(ActionItem action) {
		mActionList.add(action);
	}

	/**
	 * Create action list
	 */
	private void createActionList() {
		View view;
		String title;
		Drawable icon;
		int id;
		OnClickListener listener;

		for (int i = 0; i < mActionList.size(); i++) {
			title = mActionList.get(i).getTitle();
			icon = mActionList.get(i).getIcon();
			listener = mActionList.get(i).getListener();
			id = mActionList.get(i).getId();

			view = getActionItem(title, icon, listener);
			view.setId(id);

			view.setFocusable(true);
			view.setClickable(true);

			view.invalidate();
			view.forceLayout();
			mActionsPanel.addView(view);
		}
	}

	/**
	 * Get action item {@link View}
	 * 
	 * @param title
	 *            action item title
	 * @param icon
	 *            {@link Drawable} action item icon
	 * @param listener
	 *            {@link View.OnClickListener} action item listener
	 * @return action item {@link View}
	 */
	private View getActionItem(String title, Drawable icon,
			OnClickListener listener) {
		LinearLayout container = (LinearLayout) mInflater.inflate(
				R.layout.plotaction_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.icon);
		container.setBackgroundResource(R.drawable.cbutton);

		if (icon != null) {
			img.setImageDrawable(icon);
			//img.setBackgroundResource();
			img.setVisibility(View.VISIBLE);
		} else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (listener != null) {
			container.setOnClickListener(listener);
		}

		return container;
	}

	/**
	 * Set animation style
	 * 
	 * @param screenWidth
	 *            screen width
	 * @param requestedX
	 *            distance from left edge
	 * @param onTop
	 *            flag to indicate where the popup should be displayed. Set TRUE
	 *            if displayed on top of anchor view and vice versa
	 */
	private void setAnimationStyle(int screenWidth, int requestedX,
			boolean onTop) {

		switch (mAnimStyle) {
		case ANIM_GROW_FROM_LEFT:
			window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left
					: R.style.Animations_PopDownMenu_Left);
			break;

		case ANIM_GROW_FROM_RIGHT:
			window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right
					: R.style.Animations_PopDownMenu_Right);
			break;

		case ANIM_GROW_FROM_CENTER:
			window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center
					: R.style.Animations_PopDownMenu_Center);
			break;

		case ANIM_REFLECT:
			window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Reflect
					: R.style.Animations_PopDownMenu_Reflect);
			break;

		case ANIM_AUTO:

			window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right
					: R.style.Animations_PopDownMenu_Right);

			break;
		}
	}

	/**
	 * Set animation style
	 * 
	 * @param animStyle
	 *            animation style, default is set to ANIM_AUTO
	 */
	public void setAnimStyle(int animStyle) {
		this.mAnimStyle = animStyle;
	}

	public void show() {
		preShow();

		int xPos, yPos;

		int[] location = new int[2];

		anchor.getLocationOnScreen(location);

		Rect anchorRect = new Rect(location[0], location[1], location[0]
				+ anchor.getWidth(), location[1] + anchor.getHeight());

		createActionList();

		mRoot.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mRoot.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootHeight = mRoot.getMeasuredHeight();
		int rootWidth = mRoot.getMeasuredWidth();

		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();

		// automatically get X coord of popup (top left)
		if ((anchorRect.left + rootWidth) > screenWidth) {
			xPos = anchorRect.left - (rootWidth - anchor.getWidth());
		} else {
			if (anchor.getWidth() > rootWidth) {
				xPos = anchorRect.centerX() - (rootWidth / 2);
			} else {
				xPos = anchorRect.left;
			}
		}

		int dyTop = anchorRect.top;
		int dyBottom = screenHeight - anchorRect.bottom;

		boolean onTop = (dyTop > dyBottom) ? true : false;

		if (onTop) {
			if (rootHeight > dyTop) {
				yPos = 15;
				LayoutParams l = mScroller.getLayoutParams();
				l.height = dyTop - anchor.getHeight();
			} else {
				yPos = anchorRect.top - rootHeight;
			}
		} else {
			yPos = anchorRect.bottom;

			if (rootHeight > dyBottom) {
				LayoutParams l = mScroller.getLayoutParams();
				l.height = dyBottom;
			}
		}

		setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

		window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}

	/**
	 * Show popup window. Popup is automatically positioned, on top or bottom of
	 * anchor view.
	 * 
	 */

	public void show(int[] coordinates) {

		preShow();

		int xPos, yPos;
		int[] location = new int[2];

		location[0] = coordinates[0];
		location[1] = coordinates[1];

		Rect anchorRect = new Rect(location[0], location[1], location[0]
				+ coordinates[2], location[1] + coordinates[3]);

		createActionList();

		mRoot.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mRoot.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootHeight = mRoot.getMeasuredHeight();
		int rootWidth = mRoot.getMeasuredWidth();

		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();

		// automatically get X coord of popup (top left)
		if ((anchorRect.left + rootWidth) > screenWidth) {
			xPos = anchorRect.left - (rootWidth - anchor.getWidth());
		} else {
			if (anchor.getWidth() > rootWidth) {
				xPos = anchorRect.centerX() - (rootWidth / 2);
			} else {
				xPos = anchorRect.left;
			}
		}

		int dyTop = anchorRect.top;
		int dyBottom = screenHeight - anchorRect.bottom;
		int margin = 20;
		boolean onTop = (dyTop > dyBottom) ? true : false;

		if (onTop) {
			if (rootHeight > dyTop) {
				yPos = 15;
				LayoutParams l = mScroller.getLayoutParams();
				l.height = dyTop - anchor.getHeight();
			} else {
				yPos = anchorRect.top - (rootHeight / 2) - margin;
			}
		} else {
			yPos = anchorRect.bottom + (rootHeight / 2) + margin;

			if (rootHeight > dyBottom) {
				LayoutParams l = mScroller.getLayoutParams();
				l.height = dyBottom;
			}
		}

		setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

		window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);

	}
}