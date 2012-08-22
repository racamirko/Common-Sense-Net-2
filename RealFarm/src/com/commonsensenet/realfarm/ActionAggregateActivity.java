package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class ActionAggregateActivity extends AggregateMarketActivity implements OnLongClickListener {


	/** LayoutInflater used to create the content of the details dialog. */
	/** Reference to the current instance. */
	private final Context context = this;


	protected void cancelAudio() {

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));
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
		
		// extracts the passed parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(RealFarmDatabase.TABLE_NAME_ACTIONTYPE)) {
			// gets the action name id
			mActionTypeId = extras
					.getInt(RealFarmDatabase.TABLE_NAME_ACTIONTYPE);
		}

		super.onCreate(savedInstanceState, R.layout.tpl_aggregate, context);
		currentAction = RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE;

		// default seed/crop type id
		// TODO: if user doesn't have a plot
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId, mDataProvider, Global.userId);
		setList();

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View crop = findViewById(R.id.aggr_crop);
		Button back = (Button) findViewById(R.id.button_back);


		home.setOnLongClickListener(this);
		back.setOnLongClickListener(this);
		help.setOnLongClickListener(this);
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

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,img_1, 2);
			}
		});
		
	}

	// TODO AUDIO: check the right audio
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_crop) {
			int crop = topSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId).getAudio();
			// TODO AUDIO: Say something: action + crop
			System.out.println(action +" "+ crop);

			playAudio(topSelectorData.getAudio(), true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.problems, true);
		}

		return true;
	}
	
	protected void makeAudioAggregateMarketItem(AggregateItem item) {
		// TODO AUDIO: Dummy audio. To be removed.
		playAudio(R.raw.a2, true);
		
		int variety = topSelectorData.getAudio();
		int number = item.getNews();
		int total = item.getTotal();

		switch(mActionTypeId){
			case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
				int fertilizer =  mDataProvider.getResourceImageById(item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				// TODO  AUDIO: Say something here: say(total) + " farmers growing " + variety + " have applied " + fertilizer + " their parcels this season." + say(number) + "have done it in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly once to view the farmers and how much they used"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " farmers growing " + variety + " have applied " + fertilizer + " their parcels this season." +number + " people have done it in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly once to view the farmers and how much they used");

				break;
			
			case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
				double amount = item.getResult();
				// TODO  AUDIO: Say something here: say(total) + " have harvested " + variety + " at an average yield of " + amount + " quintal per acre this season." + say(number) + " people have harvested in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly once to view the farmers and their yields"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " have harvested " + variety + " at an average yield of " + amount + " quintal per acre this season." +number + " people have harvested in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly once to view the farmers and their yields");

				break;
				
			case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
				int irrigation = mDataProvider.getResourceImageById(item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				// TODO  AUDIO: Say something here: say(total) + " farmers growing " + variety + " have " + irrigation + " their parcels this season." + say(number) + "have done it in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly to view farmers and their duration of irrigation"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " farmers growing " + variety + " have " + irrigation + " their parcels this season." +number + " people have done it in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly to view farmers and their duration of irrigation");

				break;
				
			case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
				int problem = mDataProvider.getResourceImageById(item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				// TODO  AUDIO: Say something here: say(total) + " have reported " + problem + " for " + variety + " this season." + say(number) + "have reported it in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly here to view the farmers and their dates of reporting the problem"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " people have reported " + problem + " for " + variety + " this season." +number + " people have reported it in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly here to view the farmers and their dates of reporting the problem");

				break;
				
			case RealFarmDatabase.ACTION_TYPE_SELL_ID:
				long min = item.getSelector2();
				// TODO  AUDIO: Say something here: say(total) + " people have sold " + variety + " at prices between " + say(min) + " and " + say(min+RealFarmDatabase.SELLING_AGGREGATE_INCREMENT) + " rupees per quintal " + " this season." + say(number) + " people have sold for this price in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly to view the farmers, the weight of the bags and their individual prices"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " people have sold " + variety + " at prices between " + min + " and " + (min+RealFarmDatabase.SELLING_AGGREGATE_INCREMENT) + " rupees per quintal " + " this season." + number + " people have sold for this price in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly to view the farmers, the weight of the bags and their individual prices");
				
				break;
				
			case RealFarmDatabase.ACTION_TYPE_SOW_ID:
				int treatment = mDataProvider.getResourceImageById(item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				// TODO  AUDIO: Say something here: say(total) + " farmers have sown " + variety + "and" + treatment + " the seeds this season." + say(number) + "have done it in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly to view the farmers and how much they sowed"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " farmers have sown " + variety + " and " + treatment + " the seeds this season." +number + " people have done it in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly to view the farmers and how much they sowed");

				break;
				
			case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
				int prob = mDataProvider.getResourceImageById(item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				int pesticide = mDataProvider.getResourceImageById(item.getSelector3(), RealFarmDatabase.TABLE_NAME_RESOURCE, RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
				// TODO  AUDIO: Say something here: say(total) + " have reported " + prob + " for " + variety + " and used " + pesticide + " as medicine this season." + say(number) + "have done it in the last" + say(RealFarmDatabase.NUMBER_DAYS_NEWS) + " days. Touch briefly here to view the farmers and their dates of spraying"
				// TODO  AUDIO: Test each of the int. if == -1, don't say anything
				System.out.println(total + " people have reported " + prob + " for " + variety + " and used " + pesticide + " as medicine this season." +number + " people have done it in the last" + RealFarmDatabase.NUMBER_DAYS_NEWS + " days. Touch briefly here to view the farmers and their dates of spraying");

				break;
				
			default:
				break;
		}
	}
	
}
