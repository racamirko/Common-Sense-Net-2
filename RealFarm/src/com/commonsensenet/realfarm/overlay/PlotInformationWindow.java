package com.commonsensenet.realfarm.overlay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.utils.DateHelper;

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
	private List<ActionName> mActionList;
	/** Panel where the actions are contained. */
	private ViewGroup mActionsPanel;
	/** Animation style used to display the window. */
	private int mAnimStyle;
	/** Context used to load resources. */
	private final Context mContext;
	/** Currently selected growing id. */
	private int mCurrentGrowingId = -1;
	/** Currently selected quantity. */
	private int mCurrentQuantityId = -1;
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
	/** Stores currently used seeds. They are mapped by id. */
	private HashMap<Integer, Seed> mSeeds;
	/** View where the items are displayed. */
	private ViewGroup mItemsList;

	/**
	 * Creates a new PlotInformationWindow instance.
	 * 
	 * @param anchor
	 *            {@link View} on where the popup window should be displayed
	 */
	public PlotInformationWindow(View anchor, Plot plot,
			RealFarmProvider dataProvider) {
		super(anchor);

		mActionList = new ArrayList<ActionName>();
		mContext = anchor.getContext();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDataProvider = dataProvider;

		// sets the root of the window.
		setContentView(mInflater.inflate(R.layout.plotinformation, null));

		// gets the elements contained in the window.
		mItemsList = (ViewGroup) mRoot.findViewById(R.id.itemsList);
		// mScroller = (LinearLayout) mRoot.findViewById(R.id.windowContainer);
		mActionsPanel = (ViewGroup) mRoot.findViewById(R.id.actionPanel);
		mAnimStyle = ANIM_GROW_FROM_CENTER;

		// plot represented by the window.
		mPlot = plot;

		// loads the actions from the database.
		mActionList = mDataProvider.getActionNamesList();
		mSeeds = new HashMap<Integer, Seed>();

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

		for (int i = 0; i < mActionList.size(); i++) {
			view = getActionItem(mActionList.get(i).getRes(), OnClickAction(i));

			view.setFocusable(true);
			view.setClickable(true);

			view.invalidate();
			view.forceLayout();
			mActionsPanel.addView(view);
		}
	}

	private void editAction(int action, int actionID, Dialog dialog,
			int growingId, int quantityId) {

		// TODO: use quantityID, needs to be added to the database.
		// user clicked ok
		if (action == 1 && growingId != -1) {
			// add executed action to diary
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					RealFarmDatabase.DATE_FORMAT);
			Date date = new Date();
			mDataProvider.setAction(actionID, growingId,
					dateFormat.format(date));
		}

		// update lists
		updateDiary();
		// updateActions();

		// close popup window
		dialog.cancel();
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

		// inflates the layout
		LinearLayout container = (LinearLayout) mInflater.inflate(
				R.layout.plotaction_item, null);

		// sets the properties of the icon
		ImageView img = (ImageView) container.findViewById(R.id.icon);
		img.setImageResource(icon);

		// sets up the listener
		if (listener != null)
			img.setOnClickListener(listener);

		return container;
	}

	private View getDiaryItem(int icon, int icon2, String title, String date,
			OnClickListener listener) {

		// inflates the layout.
		RelativeLayout container = (RelativeLayout) mInflater.inflate(
				R.layout.diary_item, null);

		// gets the components to modify
		ImageView img = (ImageView) container.findViewById(R.id.icon);
		ImageView img2 = (ImageView) container.findViewById(R.id.icon2);
		TextView lblTitle = (TextView) container.findViewById(R.id.title);
		TextView lblDate = (TextView) container.findViewById(R.id.date);

		if (icon != -1)
			img.setImageResource(icon);
		else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (icon2 != -1)
			img2.setImageResource(icon2);
		else
			img2.setVisibility(View.INVISIBLE);

		if (title != null)
			lblTitle.setText(title);

		if (date != null)
			lblDate.setText(date);

		if (listener != null)
			container.setOnClickListener(listener);

		return container;
	}

	private View getGrowingItem(int icon, String name, String kannadaName) {

		// inflates the layout
		RelativeLayout container = (RelativeLayout) mInflater.inflate(
				R.layout.growing_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.icon);
		TextView lblTitle = (TextView) container.findViewById(R.id.firstLine);
		TextView tblKannada = (TextView) container
				.findViewById(R.id.secondLine);

		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/Kedage.dfont");
		tblKannada.setTypeface(tf);

		if (icon != -1)
			img.setImageResource(icon);
		else
			img.setImageResource(R.drawable.ic_menu_mylocation);

		if (name != null)
			lblTitle.setText(name);
		if (kannadaName != null)
			tblKannada.setText(kannadaName);

		// if (listener != null)
		// img.setOnClickListener(listener);

		return container;
	}

	private Dialog mCurrentAlert;

	View.OnClickListener OnClickAction(final int actionIndex) {
		return new View.OnClickListener() {

			public void onClick(View v) {

				ActionName currentAction = mActionList.get(actionIndex);

				// avoids opening other window.
				if (mCurrentAlert == null) {

					// get more information about action
					mCurrentAlert = new Dialog(mContext);
					mCurrentAlert.setContentView(R.layout.plot_dialog);

					// sets the title using the action name
					String actionName = mDataProvider.getActionNameById(
							currentAction.getId()).getName();
					mCurrentAlert.setTitle(actionName);

					// add button to select seed type, this tell us about
					// growing id
					TableLayout table = (TableLayout) mCurrentAlert
							.findViewById(R.id.TableLayout01);
					TableRow vg = new TableRow(mContext);

					TextView tv = new TextView(mContext);
					tv.setText(R.string.seed);
					tv.setTextSize(20);
					vg.addView(tv);

					// check tapped action
					// all seeds can be used
					if (actionName.compareTo("Sow") == 0) {
						List<Seed> allSeedList = new ArrayList<Seed>();
						allSeedList = mDataProvider.getSeedsList();

						// A new growing id will be created with one seed id
						for (int i = 0; i < allSeedList.size(); i++) {
							ImageView nameView1 = new ImageView(mContext);
							Seed s = allSeedList.get(i);
							nameView1.setScaleType(ScaleType.CENTER);
							nameView1.setImageResource(s.getRes());
							nameView1
									.setBackgroundResource(R.drawable.square_btn);

							int growingId = (int) mDataProvider.setGrowing(
									mPlot.getId(), s.getId());
							nameView1
									.setOnClickListener(OnClickGrowing(growingId));
							vg.addView(nameView1);
						}

					} else { // only existing seeds can be used
						for (int i = 0; i < mGrowing.size(); i++) {
							ImageView nameView1 = new ImageView(mContext);
							Seed s = mSeeds.get(mGrowing.get(i).getSeedId());
							nameView1.setBackgroundResource(s.getRes());
							nameView1
									.setOnClickListener(OnClickGrowing(mGrowing
											.get(i).getId()));
							vg.addView(nameView1);
						}
					}

					table.addView(vg, new TableLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					// Offer option about quantity
					TableRow vg2 = new TableRow(mContext);
					TextView tvv = new TextView(mContext);
					tvv.setText("Quantity");
					tvv.setTextSize(20);
					vg2.addView(tvv);

					Button b1 = new Button(mContext);
					b1.setText("Small");
					b1.setBackgroundResource(R.drawable.square_btn);
					vg2.addView(b1);
					Button b2 = new Button(mContext);
					b2.setBackgroundResource(R.drawable.square_btn);
					b2.setText("Medium");
					vg2.addView(b2);
					Button b3 = new Button(mContext);
					b3.setText("Large");
					b3.setBackgroundResource(R.drawable.square_btn);
					vg2.addView(b3);

					table.addView(vg2, new TableLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					// ok and cancel buttons
					ImageView iiv = (ImageView) mCurrentAlert
							.findViewById(R.id.cancelbutton);
					iiv.setOnClickListener(OnClickFinish(2, mCurrentAlert,
							currentAction.getId()));

					ImageView iiv2 = (ImageView) mCurrentAlert
							.findViewById(R.id.okbutton);
					iiv2.setOnClickListener(OnClickFinish(1, mCurrentAlert,
							currentAction.getId()));

					mCurrentAlert.setOnDismissListener(new OnDismissListener() {

						public void onDismiss(DialogInterface dialog) {
							// detects that window was closed.
							mCurrentAlert = null;

						}
					});
					mCurrentAlert.show();
					//
					if (currentAction.getName().equals("Diary")) {
						updateDiary();
					}

					// plays the sound related to the action
					// playSound(mActionList.get(actionIndex).getAudio());
				}
			}
		};
	}

	View.OnClickListener OnClickDiary(final int actionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				// removes the selected action
				mDataProvider.removeAction(actionID);
				// updates the UI
				updateDiary();
			}
		};
	}

	View.OnClickListener OnClickFinish(final int action, final Dialog dialog,
			final int actionID) {

		return new View.OnClickListener() {

			public void onClick(View v) {
				editAction(action, actionID, dialog, mCurrentGrowingId,
						mCurrentQuantityId);
			}
		};
	}

	View.OnClickListener OnClickGrowing(final int growingID) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				v.setPressed(true);
				v.setSelected(true);
				// store growing id
				mCurrentGrowingId = growingID;
			}
		};
	}

	/**
	 * Plays the given audio resource. Sounds previously playing are always
	 * stopped.
	 * 
	 * @param audioRes
	 *            audio resource id to play.
	 */
	private void playSound(int audioRes) {

		// stops any other currently playing sound.
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

		// plays the new sound.
		mMediaPlayer = MediaPlayer.create(mContext, audioRes);
		mMediaPlayer.start();
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				mMediaPlayer = null;
			}
		});
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

	private void updateDiary() {
		View view;
		String text;

		// removes all visual elements
		mItemsList.removeAllViews();

		List<Action> actionList = mDataProvider.getActionsByPlotId(mPlot
				.getId());

		// added from the end till the beginning to show new action on top.
		for (int i = actionList.size() - 1; i > -1; i--) {

			// gets the next action
			ActionName a = findActionNameById(actionList.get(i)
					.getActionNameId());
			Growing g = findGrowingById(actionList.get(i).getGrowingId());
			Seed s = mSeeds.get(g.getSeedId());

			// listener = mActionList.get(i).getListener();
			text = a.getName();

			view = getDiaryItem(a.getRes(), s.getRes(), text,
					DateHelper
							.formatDate(actionList.get(i).getDate(), mContext),
					OnClickDiary(actionList.get(i).getId()));
			view.setId(a.getId());

			view.setFocusable(true);
			view.setClickable(true);

			view.invalidate();
			view.forceLayout();
			mItemsList.addView(view);
		}
	}

	private ActionName findActionNameById(int actionNameId) {
		for (int x = 0; x < mActionList.size(); x++) {
			if (mActionList.get(x).getId() == actionNameId)
				return mActionList.get(x);
		}
		return null;
	}

	private Growing findGrowingById(int growingId) {
		for (int x = 0; x < mGrowing.size(); x++) {
			if (mGrowing.get(x).getId() == growingId)
				return mGrowing.get(x);
		}
		return null;
	}

	private void updatePlotInformation() {

		// Get growing areas of the plot
		mGrowing = mDataProvider.getGrowingsByPlotId(mPlot.getId());

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
			mSeeds.put(s.getId(), s);

			item = getGrowingItem(s.getRes(), s.getFullName(),
					s.getFullNameKannada());
			item.setId(mGrowing.get(i).getId());

			item.setFocusable(true);
			item.setClickable(true);

			item.invalidate();
			item.forceLayout();
			mItemsList.addView(item);
		}
	}
}