package com.commonsensenet.realfarm.overlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;

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
	protected static final int ANIM_NONE = 6;
	protected static final int ANIM_REFLECT = 4;

	private List<Action> mActionList;
	private ViewGroup mActionsPanel;
	private int mAnimStyle;
	private final Context mContext;
	/** Class used to extract the data from the database. */
	private RealFarmProvider mDataProvider;
	private final LayoutInflater mInflater;
	private ScrollView mScroller;
	private ViewGroup mTrack;
	private MediaPlayer mMediaPlayer;

	View.OnClickListener OnClickAction(final int actionIndex) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				
				// stops any previous sound being played.
				if(mMediaPlayer != null) {
					mMediaPlayer.stop();
					mMediaPlayer.release();
					mMediaPlayer = null;
				}
				
				mMediaPlayer = MediaPlayer.create(mContext, mActionList.get(actionIndex).getAudio());
				mMediaPlayer.start();
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

					public void onCompletion(MediaPlayer mp) {
						mp.release();
					}
				});
			}
		};
	}

	/**
	 * Constructor
	 * 
	 * @param anchor
	 *            {@link View} on where the popup window should be displayed
	 */
	public PlotInformationWindow(View anchor, RealFarmProvider dataProvider) {
		super(anchor);

		mActionList = new ArrayList<Action>();
		mContext = anchor.getContext();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDataProvider = dataProvider;

		// sets the root of the window.
		setContentView(mInflater.inflate(R.layout.plotinformation, null));

		// gets the elements contained in the window.
		mTrack = (ViewGroup) mRoot.findViewById(R.id.tracks);
		mScroller = (ScrollView) mRoot.findViewById(R.id.scroller);
		mActionsPanel = (ViewGroup) mRoot.findViewById(R.id.actionPanel);
		mAnimStyle = ANIM_GROW_FROM_CENTER;
	}

	/**
	 * Create action list
	 */
	private void createActionList() {
		View view;
		int icon;
		int id;

		for (int i = 0; i < mActionList.size(); i++) {
			icon = mActionList.get(i).getRes();
			// listener = mActionList.get(i).getListener();
			id = mActionList.get(i).getId();

			view = getActionItem(icon, OnClickAction(i));
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
	 * @param icon
	 *            {@link Drawable} action item icon
	 * @param listener
	 *            {@link View.OnClickListener} action item listener
	 * @return action item {@link View}
	 */
	private View getActionItem(int icon, OnClickListener listener) {
		LinearLayout container = (LinearLayout) mInflater.inflate(
				R.layout.plotaction_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.icon);
		img.setBackgroundResource(R.drawable.cbutton);
		img.setOnClickListener(listener);

		img.setClickable(true);
		img.setVisibility(View.VISIBLE);

		if (icon != -1)
			img.setImageResource(icon);
		else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (listener != null)
			img.setOnClickListener(listener);

		return container;
	}

	@Override
	protected void onShow() {
		super.onShow();
		// loads the actions from the database.
		mActionList = mDataProvider.getActionsList();
	}

	/**
	 * Set animation style
	 * 
	 * @param onTop
	 *            flag to indicate where the popup should be displayed. Set TRUE
	 *            if displayed on top of anchor view and vice versa
	 */
	private void setAnimationStyle(boolean onTop) {

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

		// gets the current position in the screen of the parent
		int[] location = new int[2];
		anchor.getLocationOnScreen(location);

		// loads the actions
		createActionList();

		// sets the animation of the window
		setAnimationStyle(true);

		// displays the window.
		window.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0],
				location[1]);
	}
}