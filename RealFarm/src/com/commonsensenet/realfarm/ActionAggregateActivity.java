package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class ActionAggregateActivity extends HelpEnabledActivityOld implements
		OnItemClickListener, OnLongClickListener, OnItemLongClickListener {

	/** Indicates the id of the activity that is being displayed. */
	private int mActionActionTypeId;
	/** ListAdapter used to handle the aggregates. */
	private AggregateItemAdapter mAggregateItemAdapter;
	/** ListView where the aggregate elements are shown. */
	private ListView mAggregatesListView;
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** LayoutInflater used to create the content of the details dialog. */
	private LayoutInflater mLayoutInflater;
	/** Reference to the current instance. */
	private final ActionAggregateActivity mParentReference = this;

	private List<AggregateItem> aggregates;

	protected void cancelAudio() {

		Intent adminintent = new Intent(ActionAggregateActivity.this,
				Homescreen.class);

		startActivity(adminintent);
	}

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"back");

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.sowing_aggregate);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);
		// gets the inflater used to populate the dialog.
		mLayoutInflater = getLayoutInflater();

		// extracts the passed parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(RealFarmDatabase.TABLE_NAME_ACTIONTYPE)) {
			// gets the action name id
			mActionActionTypeId = extras
					.getInt(RealFarmDatabase.TABLE_NAME_ACTIONTYPE);
		}

		// gets the action name object.
		ActionType actionType = mDataProvider
				.getActionTypeById(mActionActionTypeId);

		setList(0);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View action = findViewById(R.id.aggr_action);
		final View crop = findViewById(R.id.aggr_crop);
		Button back = (Button) findViewById(R.id.button_back);

		if (mActionActionTypeId == RealFarmDatabase.ACTION_TYPE_SOW_ID)
			crop.setVisibility(View.INVISIBLE);

		final ImageView actionTypeImage = (ImageView) findViewById(R.id.aggr_action_img);
		actionTypeImage.setImageResource(actionType.getImage1());

		home.setOnLongClickListener(this);
		back.setOnLongClickListener(this);
		help.setOnLongClickListener(this);
		action.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(ActionAggregateActivity.this,
						Homescreen.class));

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "home");
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "back");

			}
		});

		action.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				List<Resource> data = mDataProvider.getActionTypes();
				displayDialog(v, data, "Select the action", R.raw.problems,
						actionTypeImage, 1);
			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);

				List<Resource> data = mDataProvider.getSeedTypes();

				displayDialog(v, data, "Select the variety", R.raw.problems,
						img_1, 2);
			}
		});
	}

	public void setList(int seedTypeId) {
		// gets the list of aggregate data.
		aggregates = ActionDataFactory.getAggregateData(mActionActionTypeId,
				mDataProvider, seedTypeId);
		// creates the data adapter.
		mAggregateItemAdapter = new AggregateItemAdapter(this, aggregates,
				mActionActionTypeId, mDataProvider);

		// gets the list from the UI.
		mAggregatesListView = (ListView) findViewById(R.id.list_aggregates);
		// enables the focus on the items.
		mAggregatesListView.setItemsCanFocus(false);
		// sets the custom adapter.
		mAggregatesListView.setAdapter(mAggregateItemAdapter);
		// sets the listener
		mAggregatesListView.setOnItemClickListener(this);
		// sets the listener for the sound
		mAggregatesListView.setOnItemLongClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// gets the selected view using the position
		AggregateItem selectedItem = mAggregateItemAdapter.getItem(position);
		// gets the wrapper to extract data directly from it.
		AggregateItemWrapper selectedItemView = ActionDataFactory
				.getAggregateWrapper(view, mActionActionTypeId);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// loads the dialog layout
		View layout = mLayoutInflater.inflate(
				R.layout.dialog_aggregate_details, null);

		// gets the ListView from the layout
		ListView userListView = (ListView) layout
				.findViewById(R.id.list_dialog_aggregate);

		userListView.setItemsCanFocus(false);

		// adds the event to dismiss the dialog.
		layout.findViewById(R.id.button_back).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						// closes the dialog.
						dialog.dismiss();
					}
				});

		// sets the data of the header using the old view.
		((ImageView) layout.findViewById(R.id.icon_dialog_aggregate_crop))
				.setImageDrawable(selectedItemView.getTypeImage().getDrawable());
		((TextView) layout.findViewById(R.id.label_dialog_aggregate_type))
				.setText(selectedItemView.getTypeText().getText());

		// gets the detail container
		View detailCount = selectedItemView.getRow().findViewById(
				R.id.button_aggregate_detail);

		if (detailCount != null && detailCount.getVisibility() == View.VISIBLE) {
			// gets the TextView that contains the value.
			detailCount = selectedItemView.getRow().findViewById(
					R.id.label_aggregate_detail_count);
			((TextView) layout
					.findViewById(R.id.label_dialog_aggregate_detail_count))
					.setText(((TextView) detailCount).getText());
		} else { // hides the element.
			layout.findViewById(R.id.button_dialog_aggregate_detail)
					.setVisibility(View.INVISIBLE);

		}

		// gets the data and data adapter.
		List<UserAggregateItem> list = ActionDataFactory.getUserAggregateData(
				selectedItem, mDataProvider);

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

	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		playAudio(R.raw.problems, true);
		// TODO: Add the audio. See WeatherForecastActivity?

		switch (mActionActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			// retrieve what you need and say something.
			// int nbUsers = aggregates.get(position).getUserCount();
			break;

		}
		return true;
	}

	// TODO: put audio
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_action) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_crop) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.problems, true);
		}

		return true;
	}

	private void displayDialog(View v, final List<Resource> data,
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
					setList(data.get(position).getId());
				} else if (type == 1) { // change the action
					mActionActionTypeId = data.get(position).getId();
				}

				actionTypeImage
						.setImageResource(data.get(position).getImage1());

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), title, choice.getId());

				Toast.makeText(mParentReference, data.get(position).getName(),
						Toast.LENGTH_SHORT).show();

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
}
