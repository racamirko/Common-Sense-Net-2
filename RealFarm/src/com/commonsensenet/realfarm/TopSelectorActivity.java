package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.DialogAdapter;

public abstract class TopSelectorActivity extends HelpEnabledActivityOld
		implements OnItemClickListener, OnLongClickListener,
		OnItemLongClickListener {

	protected Resource topSelectorData;
	protected RealFarmProvider mDataProvider;
	protected LayoutInflater mLayoutInflater;

	public void onCreate(Bundle savedInstanceState, int resLayoutId,
			Context context) {
		super.onCreate(savedInstanceState, resLayoutId);
		mDataProvider = RealFarmProvider.getInstance(context);

	}

	protected void setTopSelector(int mActionTypeId) {
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
				ApplicationTracker.getInstance().flush();

				/*
				 * Toast.makeText(mParentReference,
				 * data.get(position).getName(), Toast.LENGTH_SHORT).show();
				 */

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
				ApplicationTracker.getInstance().flush();

				int iden = data.get(position).getAudio();
				playAudio(iden, true);
				return true;
			}
		});
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	protected abstract void setList(int type, Resource choice);
}
