package com.commonsensenet.realfarm.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.IrrigateAggregateItemWrapper;
import com.commonsensenet.realfarm.view.SowAggregateItemWrapper;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public final class ActionDataFactory {

	public static List<AggregateItem> getAggregateData(int actionTypeId,
			RealFarmProvider dataProvider, int seedTypeId) {

		switch (actionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			return dataProvider.getAggregateItems(actionTypeId, seedTypeId);

		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			return dataProvider.getAggregateItems(actionTypeId, -1);
		case RealFarmDatabase.ACTION_TYPE_SELL_ID:
			return dataProvider.getAggregateItems(actionTypeId, -1);

		default:
			return null;
		}
	}

	public static List<UserAggregateItem> getUserAggregateData(
			AggregateItem aggregateItem, RealFarmProvider dataProvider) {
		switch (aggregateItem.getActionTypeId()) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionTypeId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID));
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionTypeId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionTypeId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
			// TODO: Resource1ID has a satisfaction type, is this what we want?
		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionTypeId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
		default:
			return null;
		}
	}

	public static AggregateItemWrapper getAggregateWrapper(Context context,
			View row, ViewGroup parent, int actionTypeId) {

		// item to return
		AggregateItemWrapper wrapper;

		// layout inflator service.
		LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// for now all the wrappers use the same layout.
		row = li.inflate(R.layout.tpl_sowing_aggregate_item, parent, false);

		// gets the appropriate type of AggregateItemWrapper
		wrapper = getAggregateWrapper(row, actionTypeId);

		return wrapper;
	}

	public static AggregateItemWrapper getAggregateWrapper(View row,
			int actionTypeId) {
		switch (actionTypeId) {
		case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			return new IrrigateAggregateItemWrapper(row);
		default:
			return null;
		}
	}

	/**
	 * This class can not be instantiated.
	 */
	private ActionDataFactory() {

	}
}
