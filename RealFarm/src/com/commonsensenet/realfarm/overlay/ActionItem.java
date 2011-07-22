package com.commonsensenet.realfarm.overlay;

import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;

/**
 * Action item, displayed as menu with icon and text.
 * 
 * @author Lorensius. W. L. T
 * 
 */
public class ActionItem {
	/** Icon representing the ActionItem */
	private Drawable mIcon;
	/** Reference of an object that the ActionItem handles. */
	private int mId;
	/** Listener used to handle click events. */
	private OnClickListener mListener;
	private String mOwner;

	/** Displayed title of the ActionItem. */
	private String mTitle;

	/**
	 * Creates a new ActionItem instance.
	 */
	public ActionItem() {
	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 *            {@link Drawable} action icon
	 */
	public ActionItem(Drawable icon) {
		mIcon = icon;
	}

	/**
	 * Get action icon
	 * 
	 * @return {@link Drawable} action icon
	 */
	public Drawable getIcon() {
		return mIcon;
	}

	public int getId() {
		return mId;
	}

	/**
	 * Get on click listener
	 * 
	 * @return on click listener {@link View.OnClickListener}
	 */
	public OnClickListener getListener() {
		return mListener;
	}

	public String getOwner() {
		return mOwner;
	}

	/**
	 * Get action title
	 * 
	 * @return action title
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * Set action icon
	 * 
	 * @param icon
	 *            {@link Drawable} action icon
	 */
	public void setIcon(Drawable icon) {
		mIcon = icon;
	}

	public void setId(int id) {
		mId = id;
	}

	/**
	 * Set on click listener
	 * 
	 * @param listener
	 *            on click listener {@link View.OnClickListener}
	 */
	public void setOnClickListener(OnClickListener listener) {
		mListener = listener;
	}

	public void setOwner(String owner) {
		mOwner = owner;
	}

	/**
	 * Set action title
	 * 
	 * @param title
	 *            action title
	 */
	public void setTitle(String title) {
		mTitle = title;
	}

}