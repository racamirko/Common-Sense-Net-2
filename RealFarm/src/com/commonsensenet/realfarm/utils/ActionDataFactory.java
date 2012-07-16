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
import com.commonsensenet.realfarm.view.ProblemAggregateItemWrapper;
import com.commonsensenet.realfarm.view.SowAggregateItemWrapper;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public final class ActionDataFactory {

	public static List<AggregateItem> getAggregateData(int actionTypeId,
			RealFarmProvider dataProvider) {

		return dataProvider.getAggregateItems(actionTypeId);
	}

	public static List<UserAggregateItem> getUserAggregateData(
			AggregateItem aggregateItem, RealFarmProvider dataProvider) {
		switch (aggregateItem.getActionNameId()) {
		case RealFarmDatabase.ACTION_NAME_SOW_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionNameId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID));
		case RealFarmDatabase.ACTION_NAME_IRRIGATE_ID:
			return dataProvider
					.getUserAggregateItem(
							aggregateItem.getActionNameId(),
							aggregateItem
									.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_IRRIGATEMETHOD));
		case RealFarmDatabase.ACTION_NAME_REPORT_ID:
			return dataProvider.getUserAggregateItem(aggregateItem
					.getActionNameId(), aggregateItem
					.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_PROBLEMTYPE));
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
		case RealFarmDatabase.ACTION_NAME_SOW_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_NAME_IRRIGATE_ID:
			return new IrrigateAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_NAME_REPORT_ID:
			return new ProblemAggregateItemWrapper(row);
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
