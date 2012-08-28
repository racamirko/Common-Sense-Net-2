package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public abstract class AggregateMarketActivity extends TopSelectorActivity implements OnItemClickListener, OnLongClickListener, OnItemLongClickListener{

	protected Resource daysSelectorData;
	protected List<AggregateItem> mItems;
	protected ListView mListView;
	protected AggregateItemAdapter mItemAdapter;
	protected int mActionTypeId = RealFarmDatabase.ACTION_TYPE_SELL_ID;	
	protected int currentAction = 0;
	protected LayoutInflater mLayoutInflater;
	

	public void onCreate(Bundle savedInstanceState, int resLayoutId, Context context) {
		super.onCreate(savedInstanceState, resLayoutId, context);
		mLayoutInflater = getLayoutInflater();
	}
	
	public void setList(){
		int cropSeedTypeId = topSelectorData.getId();

		mItems = null;
		mItems = ActionDataFactory.getData(currentAction, mDataProvider, mActionTypeId, cropSeedTypeId, daysSelectorData);

		// TODO AUDIO: This audio is played when there are no results (no seeds) for the top seed selector. Adapt the audio
		if(mItems == null || mItems.size() < 1) playAudio(R.raw.problems, true);

		// creates the data adapter.
		mItemAdapter = ActionDataFactory.getAdapter(currentAction, mActionTypeId, this, mItems, mDataProvider, mDataProvider);
		mListView = (ListView) findViewById(R.id.list_market_aggregates);
		mListView.setItemsCanFocus(false);
		mListView.setAdapter(mItemAdapter);

		// sets the listener
		mListView.setOnItemClickListener(this);
		// sets the listener for the sound
		mListView.setOnItemLongClickListener(this);

		super.setTopSelector(mActionTypeId);
		if(currentAction == RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_MARKET) setDaysSelector();
		
	}

	public void setList(int type, Resource choice) {		
		
		if (type == 2) { // change the query
			topSelectorData = choice;
		} else if (type == 1) { // days span
			daysSelectorData = choice;
		}
		
		setList();
	}

	private void setDaysSelector() {
		final TextView selectorText = (TextView) findViewById(R.id.days_selector_label);
		selectorText.setText("last "+daysSelectorData.getShortName());
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
		
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), position);
		ApplicationTracker.getInstance().flush();

		// gets the selected view using the position
		final AggregateItem selectedItem = mItemAdapter.getItem(position);
		// gets the wrapper to extract data directly from it.
		//AggregateItemWrapper selectedItemView = ActionDataFactory
		//		.getAggregateWrapper(view, mActionTypeId);

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
						ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "back");
						ApplicationTracker.getInstance().flush();
						dialog.dismiss();
					}
				});

		// sets the data of the header using the old view.
		RelativeLayout rl = (RelativeLayout)layout.findViewById(R.id.top_user_info);
		View tmpView = mLayoutInflater.inflate(R.layout.tpl_aggregate_item, null);
		copyView(selectedItem, tmpView);
		rl.addView(tmpView);

		// gets the data and data adapter.
		final List<UserAggregateItem> list = ActionDataFactory.getUserList(currentAction, topSelectorData.getId(), selectedItem, daysSelectorData, mDataProvider);
		// TODO AUDIO: In case there is no users in the result. Set an error message
		if(list == null || list.size() < 1) playAudio(R.raw.problems, true);


		// gets the ListView from the layout
		ListView userListView = (ListView) layout.findViewById(R.id.list_dialog_aggregate);

		// selectedItem.getSeedTypeId()

		UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
				this, list);
		// sets the adapter.
		userListView.setAdapter(userAdapter);

		// userListView.setOnItemLongClickListener(mParentReference);

		// disables the title in the dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// sets the view
		dialog.setContentView(layout);
		dialog.setCancelable(true);

		// displays the dialog & describes the topbar with audio
		dialog.show();
		makeAudioUserTopBar(false);

		ImageView helpDetail = (ImageView)layout.findViewById(R.id.aggr_details_img_help);
		LinearLayout dialogAggregateHeader = (LinearLayout)layout.findViewById(R.id.dialog_aggregate_header);

		helpDetail.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog help");
				ApplicationTracker.getInstance().flush();

				playAudio(R.raw.help, true);
				return true;
			}
		});
		
		helpDetail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog help");
				ApplicationTracker.getInstance().flush();

				playAudio(R.raw.help, true);
			}
		});

		dialogAggregateHeader.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				// Say something according to the layout's contents. This is the top header of the dialog to call people in the aggregates
				//makeAudioUserTopBar(true);
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog header");
				ApplicationTracker.getInstance().flush();
				makeAudioAggregateMarketItem(selectedItem, true);
				return true;
			}
		});
		
		dialogAggregateHeader.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO AUDIO: check the right audio
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog header");
				ApplicationTracker.getInstance().flush();
			}
		});

		userListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(), "dialog call "+list.get(position).getName());
				ApplicationTracker.getInstance().flush();
				
				// TODO: calling Mr ...
				UserAggregateItem choice = list.get(position);
				makeAudioCallUser(choice);
				
				String phoneNumber = choice.getTel();
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + phoneNumber));
				startActivity(intent);
			}
		});

		userListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), "dialog call "+list.get(position).getName());
				ApplicationTracker.getInstance().flush();
				
				// TODO: audio
				UserAggregateItem choice = list.get(position);
				makeAudioUserItem(choice);

				return true;
			}
		});

	}

	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// gets the selected view using the position
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK, getLogTag(), position);
		ApplicationTracker.getInstance().flush();
		makeAudioAggregateMarketItem(mItemAdapter.getItem(position), false);

		return true;
	}
	
	public void copyView(AggregateItem aggregate, View destination) {
		destination.setBackgroundColor(Color.LTGRAY);
		
		TextView tw = (TextView)destination.findViewById(R.id.label_news);
		tw.setText(aggregate.getNewsText());
		tw.setBackgroundColor(Color.parseColor("#FFFFCC"));		
		
		tw = (TextView)destination.findViewById(R.id.label_left);
		tw.setText(aggregate.getLeftText());
				
		RelativeLayout rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_left);
		if(aggregate.getLeftBackground() != -1) rl.setBackgroundResource(aggregate.getLeftBackground());
		else tw.setTextColor(Color.BLACK);

		tw = (TextView)destination.findViewById(R.id.label_center);
		tw.setText(aggregate.getCenterText());
		
		rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_center);
		if(aggregate.getCenterBackground() != -1) rl.setBackgroundResource(aggregate.getCenterBackground());
		else {
			tw.setTextColor(Color.BLACK);
			// hack
			if(!aggregate.getCenterText().equals("")){
				rl.getLayoutParams().width = 200;
				tw.setTextSize(20);
			} else{
				rl.getLayoutParams().width = 20;
				rl = (RelativeLayout)destination.findViewById(R.id.relative_layout_right);
				rl.getLayoutParams().width = 300;
			}
		}
		
		ImageView iw = (ImageView)destination.findViewById(R.id.image_center);
		if(aggregate.getCenterImage() != -1) iw.setImageResource(aggregate.getCenterImage());

		tw = (TextView)destination.findViewById(R.id.label_right);
		tw.setText(aggregate.getRightText());
		
		iw = (ImageView)destination.findViewById(R.id.image_left);
		if(aggregate.getLeftImage() != -1) iw.setImageResource(aggregate.getLeftImage());
		
		iw = (ImageView)destination.findViewById(R.id.image_left_bottom);
		if(aggregate.getLeftBottomImage() != -1) iw.setImageResource(aggregate.getLeftBottomImage());
	}
	
	private void makeAudioUserTopBar(boolean canHear){
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a30, true);
		
		// TODO AUDIO: if(!canHear) then you can't hear the audio when the sound is disabled

	}
	
	private void makeAudioCallUser(UserAggregateItem user){
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a20);
		
		int userName = user.getAudioName();
		// TODO AUDIO: "Calling Mr" + user.getAudio(). When the sound is turned off, nothing is heard
		// TODO  AUDIO: Test the int. if == -1, don't say anything
		System.out.println(userName);
	}
	
	protected void makeAudioUserItem(UserAggregateItem user) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a10, true);
		
		// Intro
		String date = user.getDate();
		int userName = user.getAudioName();
		int userLocation = user.getAudioLocation();		
		int action = mDataProvider.getActionTypeById(mActionTypeId).getAudio();
		
		// TODO  AUDIO: Say something here: "On" + say(date) + userName + "from" + userLocation + action
		// TODO  AUDIO: Test each of the int. if == -1, don't say anything
		System.out.println("On " + date + userName + " from " + userLocation + action);
		
		
		// Mid
		int variety = topSelectorData.getAudio();
		switch(mActionTypeId){
			case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
				int unit = user.getAudioRightImage();
				// TODO  AUDIO: Say something here: variety + "with" + unit + "per acre"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(variety + " with " + unit + " per acre");

				break;
			
			case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
				Double amount = Double.parseDouble(user.getLeftText());
				// TODO  AUDIO: Say something here: say(amount) + "quintal per acre of" + variety
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(amount + " quintal per acre of " + variety);

				break;
				
			case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
				int hours = Integer.parseInt(user.getLeftText());
				int irrigation = user.getAudioLeftImage();
				// TODO  AUDIO: Say something here: say(hours) + "through" + irrigation
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(hours + " through " + irrigation);

				break;
				
			case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
				int problem = user.getAudioLeftImage();
				// TODO  AUDIO: Say something here: problem + "with" + variety
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(problem + " with " + variety);

				break;
				
			case RealFarmDatabase.ACTION_TYPE_SELL_ID:
				int kg = user.getAudioLeftImage();
				int price = Integer.parseInt(user.getLeftText());
				// TODO  AUDIO: Say something here: variety + "at" + say(price) + "rupees per quintal of " + kg + "bags"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(variety + " at " + price + " rupees per quintal of " + kg + " bags");
				
				break;
				
			case RealFarmDatabase.ACTION_TYPE_SOW_ID:
				double numberOfSerus = Double.parseDouble(user.getLeftText());
				int treatment = user.getAudioCenterImage();
				int intercrop = user.getAudioLeftImage();
				// TODO  AUDIO: Say something here: say(numberOfSerus) + "serus per acre of" + treatment + variety + "as" + intercrop
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(numberOfSerus + " serus per acre of " + treatment + variety + " as " + intercrop);

				break;
				
			case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
				int uni = user.getAudioRightImage();
				int pesticide = user.getAudioLeftImage();
				int pb = user.getAudioCenterImage();
				// TODO  AUDIO: Say something here: uni + "per acre of" + pesticide + "on" + variety + "against" + pb
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(uni + " per acre of " + pesticide + " on " + variety + " against " + pb);
				
				break;
				
			default:
				break;
		}
		
		
		// Outro
		// TODO AUDIO: "To call " + userName + " touch here briefly"
		System.out.println("To call " + userName + " touch here briefly");
	}
	
	protected abstract void makeAudioAggregateMarketItem(AggregateItem item, boolean header);

}
