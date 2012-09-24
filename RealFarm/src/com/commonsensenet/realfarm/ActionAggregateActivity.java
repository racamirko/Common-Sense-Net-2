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
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class ActionAggregateActivity extends AggregateMarketActivity implements
		OnLongClickListener {

	/** Reference to the current instance. */
	private final Context context = this;
	/** Name of the active action. */
	private String mActionName = "";

	@Override
	public String getLogTag() {
		return this.getClass().getSimpleName() + " " + mActionName;
	}

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();
		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(), "back");
		ApplicationTracker.getInstance().flushAll();
		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.tpl_aggregate, context);

		// extracts the passed parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(RealFarmDatabase.TABLE_NAME_ACTIONTYPE)) {
			// gets the action name id
			mActionTypeId = extras
					.getInt(RealFarmDatabase.TABLE_NAME_ACTIONTYPE);
		}

		// gets the name of the action to perform.
		mActionName = mDataProvider.getActionTypeById(mActionTypeId).getName();

		// indicates that aggregates will be selected.
		mCurrentAction = TopSelectorActivity.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE;

		// default seed/crop type id
		// TODO: if user doesn't have a plot
		mTopSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId,
				mDataProvider, Global.userId);

		// loads the data.
		setList();

		ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		View crop = findViewById(R.id.aggr_crop);
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
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		help.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				playAudio(R.raw.help);

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// behaves like a back button press.
				onBackPressed();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));
			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, getLogTag(),
						getResources().getResourceEntryName(v.getId()));

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(
						mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,
						img_1, 2);
			}
		});
	}

	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.homepage, true);
		} else if (v.getId() == R.id.aggr_crop) {
			int crop = mTopSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId)
					.getAudio();

			addToSoundQueue(action);
			addToSoundQueue(R.raw.there);
			addToSoundQueue(crop);
			addToSoundQueue(R.raw.there_crop);
			playSound();
		} else if (v.getId() == R.id.aggr_img_help) {

			switch (mActionTypeId) {
			case RealFarmDatabase.ACTION_TYPE_SOW_ID:
				playAudio(R.raw.aggr_sow_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
				playAudio(R.raw.aggr_fert_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
				playAudio(R.raw.aggr_irrigate_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
				playAudio(R.raw.aggr_report_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
				playAudio(R.raw.aggr_spray_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
				playAudio(R.raw.aggr_harvest_help, true);
				break;
			case RealFarmDatabase.ACTION_TYPE_SELL_ID:
				playAudio(R.raw.aggr_selling_help, true);
				break;

			default:
				break;
			}

		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.back_button, true);
		}

		return true;
	}

	protected void makeAudioAggregateMarketItem(AggregateItem item,
			boolean header) {

		int variety = mTopSelectorData.getAudio();
		int number = item.getNews();
		int total = item.getTotal();

		switch (mActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
			int fertilizer = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (variety != -1) & (fertilizer != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.growing); // says "growing"
				play_integer(total);
				addToSoundQueue(R.raw.farmers);
				addToSoundQueue(fertilizer);
				addToSoundQueue(R.raw.put_to_plot);

				if (number > 0) {
					play_integer(number);
					add_action_aggregate(4); // say "people from past"
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_fertilize);
				}

				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			double amount = item.getResult();

			if ((total != -1) & (variety != -1) & (amount != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				play_integer(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(R.raw.every_acre);
				play_float(amount);
				addToSoundQueue(R.raw.quintal_average_yield);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.this_done_harvesting);
				if (number > 0) {
					play_integer(number);
					add_action_aggregate(4); // say "people from past"
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_harvest);
				}
				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			int irrigation = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (variety != -1) & (irrigation != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.growing);
				play_integer(total);
				addToSoundQueue(R.raw.farmers);
				addToSoundQueue(irrigation);
				addToSoundQueue(R.raw.irrigation_this_season);

				if (number > 0) {
					play_integer(number);
					add_action_aggregate(4); // say "people from past"
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					// says to know about farmers and irrigation duration
					addToSoundQueue(R.raw.about_farmers_irrigation);
				}
				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			int problem = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (problem != -1) & (variety != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				play_integer(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(problem);
				addToSoundQueue(R.raw.report_this_season);
				if (number > 0) {
					play_integer(number);
					addToSoundQueue(R.raw.people_about_this_last);
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					addToSoundQueue(R.raw.days_informed);
				}
				if (header == false) {
					// says "to know about farmers and reporting"
					addToSoundQueue(R.raw.about_farmers_report);
				}
				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_SELL_ID:
			long min = item.getSelector2();

			if ((total != -1)
					& (variety != -1)
					& (min != -1)
					& (min + RealFarmDatabase.SELLING_AGGREGATE_INCREMENT != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				play_integer(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.this_for_every_quintal);
				play_integer((int) min);
				addToSoundQueue(R.raw.from);
				play_integer((int) min
						+ RealFarmDatabase.SELLING_AGGREGATE_INCREMENT);
				addToSoundQueue(R.raw.to_rupees_sold);
				if (number > 0) {
					play_integer(number);
					addToSoundQueue(R.raw.people_about_this_last);
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					addToSoundQueue(R.raw.days_informed);
				}

				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_selling);
				}
				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			int treatment = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (variety != -1) & (treatment != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				play_integer(total);
				add_action_aggregate(1); // say "farmers"
				addToSoundQueue(variety);
				add_action_aggregate(2); // say "done_sowing"
				add_action_aggregate(3); // say "this season"
				addToSoundQueue(treatment);
				if (number > 0) {
					play_integer(number);
					add_action_aggregate(4); // say "people from past"
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					// say "to know about farmers and sowing"
					add_action_aggregate(6);
				}
				playSound();
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
			int prob = mDataProvider.getResourceImageById(item.getSelector2(),
					RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
			int pesticide = mDataProvider.getResourceImageById(
					item.getSelector3(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (prob != -1) & (variety != -1)
					& (pesticide != -1) & (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				play_integer(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(prob);
				addToSoundQueue(R.raw.there_informed_spray);
				addToSoundQueue(R.raw.and);
				addToSoundQueue(pesticide);
				addToSoundQueue(R.raw.used_as_medicine_this_season);
				if (number > 0) {
					play_integer(number);
					add_action_aggregate(4); // say "people from past"
					play_integer(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_spraying);
				}
				playSound();

			}
			break;

		default:
			break;
		}
	}

}
