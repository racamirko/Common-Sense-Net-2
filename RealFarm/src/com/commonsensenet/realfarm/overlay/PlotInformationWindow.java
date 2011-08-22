package com.commonsensenet.realfarm.overlay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.Diary;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

/**
 * 
 * @author Oscar Bolanos (oscar.bolanos@epfl.ch)
 * 
 */
public class PlotInformationWindow extends CustomPopupWindow {
	protected static final int ANIM_AUTO = 5;
	protected static final int ANIM_GROW_FROM_CENTER = 3;
	protected static final int ANIM_GROW_FROM_LEFT = 1;
	protected static final int ANIM_GROW_FROM_RIGHT = 2;
	protected static final int ANIM_NONE = 6;
	protected static final int ANIM_REFLECT = 4;

	/** Actions supported by the UI. */
	private List<Action> mActionList;
	/** Panel where the actions are contained. */
	private ViewGroup mActionsPanel;
	/** Animation style used to display the window. */
	private int mAnimStyle;
	/** Context used to load resources. */
	private final Context mContext;
	/** Class used to extract the data from the database. */
	private RealFarmProvider mDataProvider;
	/** List of growing patches inside the plot. */
	private List<Growing> mGrowing;
	/** Inflater used to generate in runtime the layout. */
	private final LayoutInflater mInflater;
	/** MediaPlayer used to play the audio. */
	private MediaPlayer mMediaPlayer;
	/** Plot represented on the window. */
	private Plot mPlot;
	private List<Seed> mSeedsList;
	// private ScrollView mScroller;
	private ViewGroup mTrack;

	/**
	 * Creates a new PlotInformationWindow instance.
	 * 
	 * @param anchor
	 *            {@link View} on where the popup window should be displayed
	 */
	public PlotInformationWindow(View anchor, Plot plot,
			RealFarmProvider dataProvider) {
		super(anchor);

		mActionList = new ArrayList<Action>();
		mContext = anchor.getContext();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDataProvider = dataProvider;

		// sets the root of the window.
		setContentView(mInflater.inflate(R.layout.plotinformation, null));

		// gets the elements contained in the window.
		mTrack = (ViewGroup) mRoot.findViewById(R.id.itemsList);
		// mScroller = (LinearLayout) mRoot.findViewById(R.id.windowContainer);
		mActionsPanel = (ViewGroup) mRoot.findViewById(R.id.actionPanel);
		mAnimStyle = ANIM_GROW_FROM_CENTER;

		// plot represented by the window.
		mPlot = plot;
		// loads the actions from the database.
		mActionList = mDataProvider.getActionsList();
		mSeedsList = new ArrayList<Seed>();

		// cancel button
		ImageView iiv = (ImageView) mRoot.findViewById(R.id.cancelbutton);
		iiv.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PlotInformationWindow.this.window.dismiss();
			}
		});

		// ok button
		ImageView iiv2 = (ImageView) mRoot.findViewById(R.id.okbutton);
		iiv2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO: action should be added
				PlotInformationWindow.this.window.dismiss();
			}
		});
	}

	/**
	 * Displays the actions that the user can perform.
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

	private View getDiaryItem(int icon, String title, String date, OnClickListener listener) {
		RelativeLayout container = (RelativeLayout) mInflater.inflate(
				R.layout.diary_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.icon);
		TextView lblTitle = (TextView) container.findViewById(R.id.title);
		TextView lblDate = (TextView) container.findViewById(R.id.date);
		img.setBackgroundResource(R.drawable.cbutton);
		img.setClickable(false);

		container.setClickable(true);
		container.setFocusable(true);

		if (icon != -1)
			img.setImageResource(icon);
		else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (title != null)
			lblTitle.setText(title);
		
		if(date != null)
			lblDate.setText(date);

		if (listener != null)
			container.setOnClickListener(listener);

		return container;
	}

	private View getGrowingItem(int icon, String title) {
		RelativeLayout container = (RelativeLayout) mInflater.inflate(
				R.layout.growing_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.icon);
		TextView lblTitle = (TextView) container.findViewById(R.id.firstLine);
		TextView tblKannada = (TextView) container
				.findViewById(R.id.secondLine);
		// img.setBackgroundResource(R.drawable.cbutton);
		// img.setOnClickListener(listener);

		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/Kedage.dfont");
		tblKannada.setTypeface(tf);

		container.setClickable(true);
		container.setFocusable(true);

		if (icon != -1)
			img.setImageResource(icon);
		else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (title != null) {
			lblTitle.setText(title);
			tblKannada.setText("ಖಾಯಿಲೆ");
		}

		// if (listener != null)
		// img.setOnClickListener(listener);

		return container;
	}

	private void loadDiary() {
		View view;
		String text;

		// removes all visual elements
		mTrack.removeAllViews();

		Diary diary = mDataProvider.getDiary(mPlot.getId());

		for (int i = 0; i < diary.getSize(); i++) {

			// gets the next action
			Action a = mDataProvider.getActionById(diary.getActionId(i));

			// listener = mActionList.get(i).getListener();
			text = i + " " + a.getName();

			view = getDiaryItem(a.getRes(), text,formatDate(diary.getActionDate(i)), OnClickAction(a.getId()));
			view.setId(a.getId());

			view.setFocusable(true);
			view.setClickable(true);

			view.invalidate();
			view.forceLayout();
			mTrack.addView(view);
		}
	}

	private String formatDate(String date) {

		try {
			Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTime);

			Calendar today = Calendar.getInstance();
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DATE, -1);
			SimpleDateFormat timeFormatter = new SimpleDateFormat("MMM dd");

			if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
					&& calendar.get(Calendar.DAY_OF_YEAR) == today
							.get(Calendar.DAY_OF_YEAR)) {
				return "Today";
			} else if (calendar.get(Calendar.YEAR) == yesterday
					.get(Calendar.YEAR)
					&& calendar.get(Calendar.DAY_OF_YEAR) == yesterday
							.get(Calendar.DAY_OF_YEAR)) {
				return "Yesterday";
			} else {
				return timeFormatter.format(dateTime);
			}
		} catch (ParseException e) {
			return date;
		}
	}

	View.OnClickListener OnClickAction(final int actionIndex) {
		return new View.OnClickListener() {

			public void onClick(View v) {

				Action currentAction = mActionList.get(actionIndex);
				if (currentAction.getName().equals("Diary")) {
					loadDiary();
				}

				if (mMediaPlayer != null) {
					mMediaPlayer.stop();
					mMediaPlayer.release();
					mMediaPlayer = null;
				}

				mMediaPlayer = MediaPlayer.create(mContext,
						mActionList.get(actionIndex).getAudio());
				mMediaPlayer.start();
				mMediaPlayer
						.setOnCompletionListener(new OnCompletionListener() {

							public void onCompletion(MediaPlayer mp) {
								mp.release();
								mMediaPlayer = null;

							}
						});
			}
		};
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
		// loads the plot information
		updatePlotInformation();

		// sets the animation of the window
		setAnimationStyle(true);

		// displays the window.
		window.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0],
				location[1]);
	}

	public void updatePlotInformation() {

		// Get growing areas of the plot
		mGrowing = mDataProvider.getGrowingByPlotId(mPlot.getId());

		// gets the owner of the plot.
		User plotOwner = mDataProvider.getUserById(mPlot.getOwnerId());

		// displays the owner information in the header of the window.
		TextView txtOwnerName = (TextView) mRoot.findViewById(R.id.firstLine);
		if (plotOwner != null)
			txtOwnerName.setText(plotOwner.getFirstName() + " "
					+ plotOwner.getLastName());
		else
			txtOwnerName.setText("Unknown");

		Path path = PlotOverlay.getPathFromPlot(mPlot);
		// gets the bounds of the plot.
		RectF plotBounds = new RectF();
		path.computeBounds(plotBounds, true);

		// paint used for the path
		Paint paint = new Paint();
		paint.setStrokeWidth(7);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeWidth(3);
		paint.setColor(0x64FF0000);

		// draw in bitmap
		Bitmap myBitmap = Bitmap.createBitmap((int) plotBounds.width(),
				(int) plotBounds.height(), Config.ARGB_8888);
		Canvas myCanvas = new Canvas(myBitmap);
		// draws the given path into the canvas.
		myCanvas.drawPath(path, paint);

		// limits the size of the bitmap.
		// TODO: I think the proportions are not kept.
		myBitmap = Bitmap.createScaledBitmap(myBitmap, 100, 100, false);

		ImageView imgIcon = (ImageView) mRoot.findViewById(R.id.icon);
		imgIcon.setImageBitmap(myBitmap);

		View item;

		// lists the growing areas
		for (int i = 0; i < mGrowing.size(); i++) {

			// gets the seed used in the growing part of the plot.
			Seed s = mDataProvider.getSeedById(mGrowing.get(i).getSeedId());
			mSeedsList.add(s);

			item = getGrowingItem(s.getRes(), s.getFullName());
			item.setId(mGrowing.get(i).getId());

			item.setFocusable(true);
			item.setClickable(true);

			item.invalidate();
			item.forceLayout();
			mTrack.addView(item);
		}
	}
}