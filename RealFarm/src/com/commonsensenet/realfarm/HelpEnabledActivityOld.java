package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.MarketItemAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public abstract class HelpEnabledActivityOld extends Activity implements
OnItemClickListener, OnLongClickListener, OnItemLongClickListener {

	public String getLogTag() {
		return this.getClass().getSimpleName();
	}

	public class HelpAnimation extends AlphaAnimation {
		protected View mViewAnimated; // animation icon
		protected View mViewAssociated; // associated view on which we're
										// dispalying the help

		public HelpAnimation(float fromAlpha, float toAlpha) {
			super(fromAlpha, toAlpha);

			setAnimationListener(new Animation.AnimationListener() {

				// @Override
				public void onAnimationEnd(Animation animation) {
					HelpEnabledActivityOld.this.showHelp(HelpAnimation.this
							.getViewAssociated());
					HelpEnabledActivityOld.this.setHelpMode(false);
					HelpEnabledActivityOld.this.mHelpIcon
							.setVisibility(View.INVISIBLE);
				}

				// @Override
				public void onAnimationRepeat(Animation animation) {
				}

				// @Override
				public void onAnimationStart(Animation animation) {
				}
			});

		}

		public View getViewAnimated() {
			return mViewAnimated;
		}

		public View getViewAssociated() {
			return mViewAssociated;
		}

		public void setViewAnimated(View mViewAnimated) {
			this.mViewAnimated = mViewAnimated;
		}

		public void setViewAssociated(View mViewAssociated) {
			this.mViewAssociated = mViewAssociated;
		}

	}

	protected HelpAnimation mAnimFadeIn;
	protected View mHelpIcon;
	protected boolean mHelpMode;

	public String getcurrenttime() {
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat", crntdt);
		return crntdt;

	}

	protected String getCurrentTime() {
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat", crntdt);
		return crntdt;
	}

	public View getHelpIcon() {
		return mHelpIcon;
	}

	public boolean getHelpMode() {
		return mHelpMode;
	}

	protected void initmissingval() {

		playAudio(R.raw.missinginfo);

		// ShowHelpIcon(v);
	}

	protected void okaudio() {
		playAudio(R.raw.ok);
	}

	public void onBackPressed() {
		// stops any currently playing sound.
		stopAudio();

		super.onBackPressed();
	}

	public void onCreate(Bundle savedInstanceState, int resLayoutId, Context context) {
		super.onCreate(savedInstanceState);
		mHelpMode = false;

		mAnimFadeIn = new HelpAnimation(0.0f, 1.0f);

		setContentView(resLayoutId);
		
		
		/* Should be there */

		mDataProvider = RealFarmProvider.getInstance(context);
		mLayoutInflater = getLayoutInflater();

	}

	// @Override
	public boolean onLongClick(View v) {
		// position
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth() - mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0] + v.getWidth() / 2 - iconWidth / 2, loc[1]
				- iconHeight - 20, 0, 0);
		Log.d(getLogTag(), "Showing help at: " + loc[0] + " , " + loc[1]);

		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);

		return true;
	}

	// @Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP && getHelpMode()) {
			Animation a = new AlphaAnimation(1.0f, 0.0f);
			a.setDuration(500);
			mHelpIcon.startAnimation(a);
			mHelpIcon.setVisibility(View.INVISIBLE);
			setHelpMode(false);
			return true;
		}
		// In case we weren't in the help mode let the rest of the stack process
		// the event
		return false;
	}

	/**
	 * Plays the given resourceId. The sound is only played if
	 * Global.enabledAudio is true, otherwise it is not played.
	 * 
	 * @param resourceId
	 *            id of the audio.
	 */
	protected void playAudio(int resourceId) {
		// plays the sound if the id is valid.
		if (resourceId != -1) {
			playAudio(resourceId, false);
		}
	}

	/**
	 * Plays the given resourceId. The forcePlay flag can be use to play an
	 * audio no matter the Global.enableAudio setting.
	 * 
	 * @param resid
	 *            id of the audio to play.
	 * @param forcePlay
	 *            whether the Global.enableAudio setting is respected or not.
	 */
	protected void playAudio(int resid, Boolean forcePlay) {
		// checking for audio enable
		if (Global.isAudioEnabled || forcePlay) {
			// gets the singleton queue
			SoundQueue sq = SoundQueue.getInstance();
			// cleans any possibly playing sound
			sq.clean();
			// adds the sound to the queue
			sq.addToQueue(resid);
			// plays the sound
			sq.play();
		}
	}

	public void setHelpIcon(View helpIcon) {
		this.mHelpIcon = helpIcon;
		mAnimFadeIn.setViewAnimated(helpIcon);
	}

	public void setHelpMode(boolean active) {
		mHelpMode = active;
	}

	public void showHelp(View v) {

		if (v.getId() == R.id.hmscrn_btn_actions) {
			playAudio(R.raw.audio1, true);
		}
		if (v.getId() == R.id.hmscrn_btn_advice) {
			playAudio(R.raw.audio2, true);
		}
		if (v.getId() == R.id.hmscrn_btn_sound) {
			playAudio(R.raw.audio3, true);
		}

		// if (v.getId() == R.id.hmscrn_help_button || v.getId() ==
		// R.id.hmscrn_help_button) {
		//
		// playAudioalways(R.raw.audio3);
		// }
		if (v.getId() == R.id.hmscrn_btn_yield) {
			playAudio(R.raw.audio4, true);
		}

		if (v.getId() == R.id.hmscrn_btn_weather) {
			playAudio(R.raw.weatherforecast, true);
		}
		// End of big icons

		if (v.getId() == R.id.hmscrn_btn_market) {
			playAudio(R.raw.marketprice, true);
		}

		if (v.getId() == R.id.hmscrn_btn_video) {
			playAudio(R.raw.video, true);
		}

		// if (v.getId() == R.id.hmscrn_btn_diary) { // changes
		// playAudioalways(R.raw.dairy);
		// }

		// if (v.getId() == R.id.hmscrn_imgbtn_notifs) { // changes
		// playAudioalways(R.raw.dairy);
		// }

		// TODO: make a table mapping IDs to sound files
	}

	public void showHelpIcon(View v) {

		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		int iconWidth = mHelpIcon.getWidth() - mHelpIcon.getPaddingLeft();
		int iconHeight = mHelpIcon.getHeight() - mHelpIcon.getPaddingTop();
		mHelpIcon.setPadding(loc[0] + v.getWidth() / 2 - iconWidth / 2, loc[1]
				- iconHeight - 20, 0, 0);
		Log.d(getLogTag(), "Showing help at: " + loc[0] + " , " + loc[1]);

		mAnimFadeIn.setViewAssociated(v);
		mAnimFadeIn.setDuration(500);
		mHelpIcon.setVisibility(View.VISIBLE);
		mHelpIcon.startAnimation(mAnimFadeIn);
		setHelpMode(true);

	}

	protected void stopAudio() {
		SoundQueue.getInstance().stop();
	}
	
	/* Needs to be there */
	
	protected Resource topSelectorData;
	protected Resource daysSelectorData;
	protected RealFarmProvider mDataProvider;
	protected List<AggregateItem> mItems;
	protected ListView mListView;
	protected AggregateItemAdapter mItemAdapter;
	protected int mActionTypeId = RealFarmDatabase.ACTION_TYPE_SELL_ID;	
	protected int currentAction = 0;
	protected LayoutInflater mLayoutInflater;

	protected abstract void cancelAudio();
	
	public void setList() {		
		int cropSeedTypeId = topSelectorData.getId();
		
		mItems = null;
		switch(currentAction){
			case RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET: mItems = mDataProvider.getMarketData(cropSeedTypeId, - daysSelectorData.getValue());
			break;
			case RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE: mItems = ActionDataFactory.getAggregateData(mActionTypeId, mDataProvider, cropSeedTypeId);
			break;
		}
		
		if(mItems == null || mItems.size() < 1) playAudio(R.raw.problems, true);

		// creates the data adapter.
		switch(currentAction){
			case RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET: mItemAdapter = new MarketItemAdapter(this, mItems, mDataProvider);
					mListView = (ListView) findViewById(R.id.list_market_prices);
					mListView.setItemsCanFocus(false);
					mListView.setAdapter(mItemAdapter);
			break;
			case RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE: mItemAdapter = new AggregateItemAdapter(this, mItems, mActionTypeId, mDataProvider);
					mListView = (ListView) findViewById(R.id.list_aggregates);
					mListView.setItemsCanFocus(false);
					mListView.setAdapter(mItemAdapter);
			break;
		}
		

		// gets the list from the UI.
		// enables the focus on the items.
		
		// sets the listener
		mListView.setOnItemClickListener(this);
		// sets the listener for the sound
		mListView.setOnItemLongClickListener(this);
		
		setTopSelector();
		if(currentAction == RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET) setDaysSelector();
	}

	private void setDaysSelector() {
		final TextView selectorText = (TextView) findViewById(R.id.days_selector_label);
		selectorText.setText("last "+daysSelectorData.getShortName());
	}

	private void setTopSelector() {
		ActionType actionType = mDataProvider.getActionTypeById(mActionTypeId);
		final ImageView actionImg = (ImageView) findViewById(R.id.aggr_action);
		actionImg.setImageResource(actionType.getImage1());
		final ImageView selectorImg = (ImageView) findViewById(R.id.aggr_crop_img);
		selectorImg.setBackgroundResource(topSelectorData.getBackgroundImage());
		final TextView selectorText = (TextView) findViewById(R.id.textView1);
		selectorText.setText(topSelectorData.getShortName());
	}
	
	protected void displayDialog(View v, final List<Resource> data,
			final String title, int entryAudio,
			final ImageView actionTypeImage, final int type) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, data);
		ListView mList = (ListView) dialog.findViewById(R.id.dialog_list);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio);

		// TODO: adapt the audio in the database.
		mList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				Resource choice = data.get(position);

				// TODO: this won't work.
				if (type == 2) { // change the query
					topSelectorData = data.get(position);
				} else if (type == 1) { // days span
					daysSelectorData = data.get(position);
				}
				setList();

				if(actionTypeImage != null) actionTypeImage
				.setBackgroundResource(data.get(position).getBackgroundImage());

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), title, choice.getId());

				/*Toast.makeText(mParentReference, data.get(position).getName(),
						Toast.LENGTH_SHORT).show();*/

				// onClose
				dialog.cancel();
				int iden = choice.getAudio();
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			// TODO: adapt the audio in the database
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int iden = data.get(position).getAudio();
				playAudio(iden);
				return true;
			}
		});
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
		
		// gets the selected view using the position
		AggregateItem selectedItem = null;
		
		selectedItem = mItemAdapter.getItem(position);
		// gets the wrapper to extract data directly from it.
		AggregateItemWrapper selectedItemView = ActionDataFactory
				.getAggregateWrapper(view, mActionTypeId);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// loads the dialog layout
		View layout = mLayoutInflater.inflate(
				R.layout.dialog_aggregate_details, null);

		// adds the event to dismiss the dialog.
		layout.findViewById(R.id.button_back).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						// closes the dialog.
						dialog.dismiss();
					}
				});

		// sets the data of the header using the old view.
		RelativeLayout rl = (RelativeLayout)layout.findViewById(R.id.top_user_info);
		View tmpView = mLayoutInflater.inflate(R.layout.tpl_aggregate_item, null);
		selectedItemView.copyView(selectedItem, tmpView);
		rl.addView(tmpView);

		// gets the data and data adapter.
		final List<UserAggregateItem> list = getListTmp(selectedItem);
					
		if(list == null || list.size() < 1) playAudio(R.raw.problems, true);

		
		// gets the ListView from the layout
		ListView userListView = (ListView) layout.findViewById(R.id.list_dialog_aggregate);

		// selectedItem.getSeedTypeId()

		UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
				this, list, mDataProvider);
		// sets the adapter.
		userListView.setAdapter(userAdapter);

		// userListView.setOnItemLongClickListener(mParentReference);

		// disables the title in the dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// sets the view
		dialog.setContentView(layout);
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
		playAudio(R.raw.problems, true);

		ImageView helpDetail = (ImageView)layout.findViewById(R.id.aggr_details_img_help);
		LinearLayout dialogAggregateHeader = (LinearLayout)layout.findViewById(R.id.dialog_aggregate_header);

		helpDetail.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				playAudio(R.raw.a10, true);
				return false;
			}
		});
		
		dialogAggregateHeader.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// TODO; say something according to the layout's contents. This is the top header of the dialog to call people in the aggregates
				playAudio(R.raw.problems, true);
				return false;
			}
		});

		userListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO: calling Mr ...
				playAudio(R.raw.problems, true);
				UserAggregateItem choice = list.get(position);
				String phoneNumber = choice.getTel();

				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + phoneNumber));
				startActivity(intent);
			}
		});

		userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				// TODO: audio
				UserAggregateItem choice = list.get(position);
				playAudio(R.raw.problems, true);

				return true;
			}
		});

	}

	private List<UserAggregateItem> getListTmp(AggregateItem selectedItem) {
		
		if(currentAction == RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE){
			return ActionDataFactory.getUserAggregateData(selectedItem, mDataProvider);
		} else if (currentAction == RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET){
			return mDataProvider.getUserMarketData(topSelectorData.getId(), selectedItem.getSelector1(), - daysSelectorData.getValue());
		}
		return null;
	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		playAudio(R.raw.problems, true);
		// TODO: Add the audio.

		switch (mActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			// retrieve what you need and say something.
			// int nbUsers = aggregates.get(position).getUserCount();
			break;

		}
		return true;
	}
	
}
