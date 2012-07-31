package com.commonsensenet.realfarm;

import java.util.ArrayList;
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
import com.commonsensenet.realfarm.model.ActionName;
import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.DialogAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class ActionAggregateActivity extends HelpEnabledActivityOld implements OnItemClickListener, OnLongClickListener, OnItemLongClickListener{
	/** Name used to log the activity of the class. */
	public static final String LOG_TAG = "sowing_aggregate";

	/** Indicates the id of the activity that is being displayed. */
	private int mActiveActionNameId;
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
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG, "back");

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
		if (extras != null && extras.containsKey("actionName")) {
			// gets the action name id
			mActiveActionNameId = extras.getInt("actionName");
		}

		// gets the action name object.
		ActionName actionName = mDataProvider.getActionNameById(mActiveActionNameId);
		
		/*Toast toast = Toast.makeText(getApplicationContext(), actionName.getName(), Toast.LENGTH_SHORT);
		toast.show();
		toast = Toast.makeText(getApplicationContext(), actionName.getRes(), Toast.LENGTH_SHORT);
		toast.show();*/

		setList(0);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View action = findViewById(R.id.aggr_action);
		final View crop = findViewById(R.id.aggr_crop);
		Button back = (Button) findViewById(R.id.button_back);
		
		if(mActiveActionNameId == RealFarmDatabase.ACTION_NAME_SOW_ID) crop.setVisibility(View.INVISIBLE);
		
		final ImageView actionNameImage = (ImageView) findViewById(R.id.aggr_action_img);
		actionNameImage.setImageResource(actionName.getRes());
		
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
						LOG_TAG, "home");
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "back");

			}
		});

		action.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				ArrayList<DialogData> m_entries = mDataProvider.getAction();
				displayDialog(v, m_entries, "Select the action", R.raw.problems, actionNameImage, 1);
			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);

				ArrayList<DialogData> m_entries = mDataProvider.getVarieties();
				m_entries.add(new DialogData("All", "All", R.drawable.icon, -1, R.raw.problems, "0", R.drawable.icon));
				displayDialog(v, m_entries, "Select the variety", R.raw.problems, img_1, 2);

				//R.drawable.pic_72px_groundnut
			}
		});
	}

	public void setList(int seedTypeId){
		// gets the list of aggregate data.
		aggregates = ActionDataFactory.getAggregateData(mActiveActionNameId, mDataProvider, seedTypeId);
		// creates the data adapter.
		mAggregateItemAdapter = new AggregateItemAdapter(this, aggregates, mActiveActionNameId, mDataProvider); 

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
				.getAggregateWrapper(view, mActiveActionNameId);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// loads the dialog layout
		View layout = mLayoutInflater.inflate(
				R.layout.dialog_aggregate_details, null);

		// gets the ListView from the layout
		ListView userListView = (ListView) layout
				.findViewById(R.id.list_dialog_aggregate);

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

		// disables the title in the dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// sets the view
		dialog.setContentView(layout);
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	}
	
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// gets the selected view using the position
		playAudioalways(R.raw.problems);
		// TODO: Add the audio. See WeatherForecastActivity?
		
		switch(mActiveActionNameId){
			case RealFarmDatabase.ACTION_NAME_SOW_ID:
				int nbUsers = aggregates.get(position).getUserCount(); // retrieve what you need and say something
			break;
		
		}
				
		return true;
	}

	public boolean onLongClick(View v) {
		playAudioalways(R.raw.problems);
		
		if (v.getId() == R.id.aggr_img_home) { // TODO: put audios

			playAudioalways(R.raw.problems);
		}
		if (v.getId() == R.id.aggr_action) { // TODO: put audios

			playAudioalways(R.raw.problems);
		}
		if (v.getId() == R.id.aggr_crop) { // TODO: put audios

			playAudioalways(R.raw.problems);
		}
		if (v.getId() == R.id.aggr_img_help) { // TODO: put audios

			playAudioalways(R.raw.problems);
		}
		if (v.getId() == R.id.button_back) { // TODO: put audios

			playAudioalways(R.raw.problems);
		}

		return true;
	}
	
	private void displayDialog(View v, final ArrayList<DialogData> m_entries, final String title, int entryAudio, final ImageView actionNameImage, final int type) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.mc_dialog);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		DialogAdapter m_adapter = new DialogAdapter(v.getContext(),
				R.layout.mc_dialog_row, m_entries);
		ListView mList = (ListView) dialog.findViewById(R.id.liste);
		mList.setAdapter(m_adapter);

		dialog.show();
		playAudio(entryAudio); // TODO: onOpen

		mList.setOnItemClickListener(new OnItemClickListener() { // TODO: adapt
			// the audio
			// in the db
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Does whatever is specific to the application
				Log.d("var " + position + " picked ", "in dialog");
				DialogData choice = m_entries.get(position);
				
				if(type == 2){ // change the query
					setList(Integer.parseInt(m_entries.get(position).getValue()));
				} else if(type == 1) {  // change the action
					mActiveActionNameId = Integer.parseInt(m_entries.get(position).getValue());
				}
				
				actionNameImage.setImageResource(m_entries.get(position).getImageRes());

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, title, choice.getValue());

				Toast.makeText(mParentReference, m_entries.get(position).getName(),Toast.LENGTH_SHORT).show();

				// onClose
				dialog.cancel();
				int iden = choice.getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + choice.getAudio(), null, null);
				playAudio(iden);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO: adapt the audio in the db
				int iden = m_entries.get(position).getAudioRes();
				// view.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
				// + m_entries.get(position).getAudio(), null, null);
				playAudio(iden);
				return true;
			}
		});
	}
}
