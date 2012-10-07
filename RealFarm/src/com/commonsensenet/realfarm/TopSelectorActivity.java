package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;

public abstract class TopSelectorActivity extends HelpEnabledActivity implements
		OnItemClickListener, OnLongClickListener, OnItemLongClickListener {

	/** Indicates that the selector works with aggregate data. */
	public static final int LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE = 1;
	/** Indicates that the Selector works with Market data. */
	public static final int LIST_WITH_TOP_SELECTOR_TYPE_MARKET = 2;

	/** Current Resource used as the active filter. */
	protected Resource mTopSelectorData;
	/** Inflater used to dynamically add layouts from XML. */
	protected LayoutInflater mLayoutInflater;

	public void onCreate(Bundle savedInstanceState, int layoutId) {
		super.onCreate(savedInstanceState, layoutId);

		// loads the inflater used to add new layouts.
		mLayoutInflater = getLayoutInflater();
	}

	// adds audio from 1000 to 10000
	protected void add_action_aggregate(int n) {

		switch (n) {
		case 1:
			mSoundQueue.addToQueue(R.raw.farmers); // say "farmers"
			break;
		case 2:
			mSoundQueue.addToQueue(R.raw.done_sowing); // says "done sowing"
			break;
		case 3:
			mSoundQueue.addToQueue(R.raw.this_season); // says "this season"
			break;
		case 4:
			mSoundQueue.addToQueue(R.raw.people_from_past); // says
															// "people from past"
			break;
		case 5:
			mSoundQueue.addToQueue(R.raw.days_done); // says "days done"
			break;
		case 6:
			mSoundQueue.addToQueue(R.raw.about_farmer_and_sowing_click); // says
			// "about farmers and sowing"
			break;
		case 7:
			mSoundQueue.addToQueue(R.raw.aggregate_in); // says "in"
			break;
		case 8:
			mSoundQueue.addToQueue(R.raw.every_acre); // says "every acre"
			break;
		case 9:
			mSoundQueue.addToQueue(R.raw.seru); // says "seru"
			break;
		case 10:
			mSoundQueue.addToQueue(R.raw.in_call_sowing); // says "sowing done"
			break;
		default:
			break;
		}
	}

	protected void setTopSelector(int mActionTypeId) {
		// ActionType actionType =
		// mDataProvider.getActionTypeById(mActionTypeId);
		// final ImageView actionImg = (ImageView)
		// findViewById(R.id.aggr_action);
		// actionImg.setImageResource(actionType.getImage1());
		// final ImageView selectorImg = (ImageView)
		// findViewById(R.id.aggr_crop_img);
		// selectorImg
		// .setBackgroundResource(mTopSelectorData.getBackgroundImage());
		// final TextView selectorText = (TextView)
		// findViewById(R.id.textView1);
		// selectorText.setText(mTopSelectorData.getShortName());
	}

	protected void displayDialog(View v, final List<Resource> data,
			final String title, int entryAudio,
			final ImageView actionTypeImage, final int type) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(), data);
		ListView mList = (ListView) dialog.findViewById(R.id.dialog_list);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio);

		// TODO AUDIO: adapt the audio in the database.
		mList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				Resource choice = data.get(position);

				setList(type, choice);

				if (actionTypeImage != null)
					actionTypeImage.setBackgroundResource(data.get(position)
							.getBackgroundImage());

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						"dialog " + choice.getShortName());

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
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
						Global.userId, getLogTag(),
						"dialog " + data.get(position).getShortName());

				int iden = data.get(position).getAudio();
				playAudio(iden, true);
				return true;
			}
		});
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	protected abstract void setList(int type, Resource choice);
}
