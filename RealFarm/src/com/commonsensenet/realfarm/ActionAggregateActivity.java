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
	private String mActionName = "";

	@Override
	public String getLogTag() {
		return this.getClass().getSimpleName() + " " + mActionName;
	}

	protected void cancelAudio() {

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));
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
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId,
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
				cancelAudio();
				stopAudio();

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
			int crop = topSelectorData.getAudio();
			int action = mDataProvider.getActionTypeById(mActionTypeId)
					.getAudio();

			System.out.println(action + " " + crop);
			addToSoundQueue(action);
			addToSoundQueue(R.raw.there);
			addToSoundQueue(crop);
			addToSoundQueue(R.raw.there_crop);
			playSound();
		} else if (v.getId() == R.id.aggr_img_help) {
			if (mActionName == "Sow") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Fertilize") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Irrigate") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Report") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Spray") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Harvest") {
				playAudio(R.raw.sow_help, true);
			} else if (mActionName == "Sell") {
				playAudio(R.raw.sow_help, true);
			}

		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.back_aggregates, true);
		}

		return true;
	}

	protected void makeAudioAggregateMarketItem(AggregateItem item,
			boolean header) {

		int variety = topSelectorData.getAudio();
		int number = item.getNews();
		int total = item.getTotal();

		switch (mActionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
			int fertilizer = mDataProvider.getResourceImageById(
					item.getSelector2(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			System.out
					.println(total
							+ " farmers growing "
							+ variety
							+ " have applied "
							+ fertilizer
							+ " their parcels this season."
							+ number
							+ " people have done it in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly once to view the farmers and how much they used");

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

			System.out
					.println(total
							+ " have harvested "
							+ variety
							+ " at an average yield of "
							+ amount
							+ " quintal per acre this season."
							+ number
							+ " people have harvested in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly once to view the farmers and their yields");

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

			System.out
					.println(total
							+ " farmers growing "
							+ variety
							+ " have "
							+ irrigation
							+ " their parcels this season."
							+ number
							+ " people have done it in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly to view farmers and their duration of irrigation");

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

			System.out
					.println(total
							+ " people have reported "
							+ problem
							+ " for "
							+ variety
							+ " this season."
							+ number
							+ " people have reported it in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly here to view the farmers and their dates of reporting the problem");

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

			System.out
					.println(total
							+ " people have sold "
							+ variety
							+ " at prices between "
							+ min
							+ " and "
							+ (min + RealFarmDatabase.SELLING_AGGREGATE_INCREMENT)
							+ " rupees per quintal "
							+ " this season."
							+ number
							+ " people have sold for this price in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly to view the farmers, the weight of the bags and their individual prices");

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

			System.out
					.println(total
							+ " farmers have sown "
							+ variety
							+ " and "
							+ treatment
							+ " the seeds this season."
							+ number
							+ " people have done it in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly to view the farmers and how much they sowed");

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
			System.out.println("total" + total);
			System.out.println("variety" + variety);
			System.out.println("treatment" + treatment);
			System.out.println("number" + number);
			System.out.println("days" + RealFarmDatabase.NUMBER_DAYS_NEWS);

			break;

		case RealFarmDatabase.ACTION_TYPE_SPRAY_ID:
			int prob = mDataProvider.getResourceImageById(item.getSelector2(),
					RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);
			int pesticide = mDataProvider.getResourceImageById(
					item.getSelector3(), RealFarmDatabase.TABLE_NAME_RESOURCE,
					RealFarmDatabase.COLUMN_NAME_RESOURCE_AUDIO);

			System.out
					.println(total
							+ " people have reported "
							+ prob
							+ " for "
							+ variety
							+ " and used "
							+ pesticide
							+ " as medicine this season."
							+ number
							+ " people have done it in the last"
							+ RealFarmDatabase.NUMBER_DAYS_NEWS
							+ " days. Touch briefly here to view the farmers and their dates of spraying");

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
