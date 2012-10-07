package com.commonsensenet.realfarm;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;

import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.ActionType;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class ActionAggregateActivity extends AggregateMarketActivity implements
		OnLongClickListener {

	/** Name of the active action. */
	private String mActionName = "";

	@Override
	public String getLogTag() {
		return this.getClass().getSimpleName() + " " + mActionName;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.act_action_aggregate);

		// extracts the passed parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(RealFarmDatabase.TABLE_NAME_ACTIONTYPE)) {
			// gets the action name id
			mActionTypeId = extras
					.getInt(RealFarmDatabase.TABLE_NAME_ACTIONTYPE);
		}

		ActionType at = mDataProvider.getActionTypeById(mActionTypeId);
		// gets the name of the action to perform.
		mActionName = at.getName();

		// indicates that aggregates will be selected.
		mCurrentAction = TopSelectorActivity.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE;

		// gets the data to select.
		mTopSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId,
				mDataProvider, Global.userId);

		// sets the name and icon based on the action type.
		getSupportActionBar().setIcon(at.getImage1());
		getSupportActionBar().setTitle(mActionName);

		// loads the data.
		setList();

		Button back = (Button) findViewById(R.id.button_back);
		back.setOnLongClickListener(this);

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// behaves like a back button press.
				onBackPressed();
			}
		});
	}

	public boolean onLongClick(View v) {

		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		if (v.getId() == R.id.aggr_crop) {
			int crop = mTopSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId)
					.getAudio();

			addToSoundQueue(action);
			addToSoundQueue(R.raw.there);
			addToSoundQueue(crop);
			addToSoundQueue(R.raw.there_crop);
			playSound(true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.back_button, true);
		}

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), item.getTitle());

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
			}

			return true;
		}

		// asks the parent.
		return super.onOptionsItemSelected(item);
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
				addToSoundQueue(R.raw.growing);
				playInteger(total);
				addToSoundQueue(R.raw.farmers);
				addToSoundQueue(fertilizer);
				addToSoundQueue(R.raw.put_to_plot);

				if (number > 0) {
					playInteger(number);
					add_action_aggregate(4); // say "people from past"
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_fertilize);
				}

				playSound(true);
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			float amount = item.getResult();

			if ((total != -1) & (variety != -1) & (amount != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				playInteger(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(R.raw.every_acre);
				playFloat(amount);
				addToSoundQueue(R.raw.quintal_average_yield);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.this_done_harvesting);
				if (number > 0) {
					playInteger(number);
					add_action_aggregate(4); // say "people from past"
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_harvest);
				}
				playSound(true);
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
				playInteger(total);
				addToSoundQueue(R.raw.farmers);
				addToSoundQueue(irrigation);
				addToSoundQueue(R.raw.irrigation_this_season);

				if (number > 0) {
					playInteger(number);
					add_action_aggregate(4); // say "people from past"
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					// says to know about farmers and irrigation duration
					addToSoundQueue(R.raw.about_farmers_irrigation);
				}
				playSound(true);
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			int problem = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (problem != -1) & (variety != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				playInteger(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(problem);
				addToSoundQueue(R.raw.report_this_season);
				if (number > 0) {
					playInteger(number);
					addToSoundQueue(R.raw.people_about_this_last);
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					addToSoundQueue(R.raw.days_informed);
				}
				if (header == false) {
					// says "to know about farmers and reporting"
					addToSoundQueue(R.raw.about_farmers_report);
				}
				playSound(true);
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_SELL_ID:
			long min = item.getSelector2();
			long max = item.getSelector3();

			if ((total != -1) & (variety != -1) & (min != -1) & (max != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				playInteger(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.this_for_every_quintal);
				playInteger((int) min);
				addToSoundQueue(R.raw.from);
				playInteger((int) max);
				addToSoundQueue(R.raw.to_rupees_sold);
				if (number > 0) {
					playInteger(number);
					addToSoundQueue(R.raw.people_about_this_last);
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					addToSoundQueue(R.raw.days_informed);
				}

				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_selling);
				}
				playSound(true);
			}

			break;

		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			int treatment = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			if ((total != -1) & (variety != -1) & (treatment != -1)
					& (number != -1)
					& (RealFarmDatabase.NUMBER_DAYS_NEWS != -1)) {
				playInteger(total);
				add_action_aggregate(1); // say "farmers"
				addToSoundQueue(variety);
				add_action_aggregate(2); // say "done_sowing"
				add_action_aggregate(3); // say "this season"
				addToSoundQueue(treatment);
				if (number > 0) {
					playInteger(number);
					add_action_aggregate(4); // say "people from past"
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					// say "to know about farmers and sowing"
					add_action_aggregate(6);
				}
				playSound(true);
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
				playInteger(total);
				addToSoundQueue(R.raw.people);
				addToSoundQueue(variety);
				addToSoundQueue(R.raw.to);
				addToSoundQueue(prob);
				addToSoundQueue(R.raw.there_informed_spray);
				addToSoundQueue(R.raw.and);
				addToSoundQueue(pesticide);
				addToSoundQueue(R.raw.used_as_medicine_this_season);
				if (number > 0) {
					playInteger(number);
					add_action_aggregate(4); // say "people from past"
					playInteger(RealFarmDatabase.NUMBER_DAYS_NEWS);
					add_action_aggregate(5); // say "days done"

				}
				if (header == false) {
					addToSoundQueue(R.raw.about_farmers_spraying);
				}
				playSound(true);
			}
			break;

		default:
			break;
		}
	}
}
