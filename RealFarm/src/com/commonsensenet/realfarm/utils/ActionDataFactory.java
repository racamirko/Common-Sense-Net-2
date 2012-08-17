package com.commonsensenet.realfarm.utils;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.SowAggregateItemWrapper;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public final class ActionDataFactory {

	public static Resource getTopSelectorData(int actionTypeId, RealFarmProvider dataProvider, long userId){
		switch (actionTypeId) {
			case RealFarmDatabase.ACTION_TYPE_SELL_ID:
				// if user doesn't have a plot, select groundnut (id = 1)
				Resource res = dataProvider.getTopSelectorDataCrop(userId, 1);
				return res;
			default:
				// if user doesn't have a plot, select TMV2 (id = 4)
				Resource res2 = dataProvider.getTopSelectorDataVar(userId, 4);
				return res2;
		}
	}
	
	// TODO: get varieties and crops THIS SEASON
	public static List<Resource> getTopSelectorList(int actionTypeId, RealFarmProvider dataProvider){
		switch (actionTypeId) {
			case RealFarmDatabase.ACTION_TYPE_SELL_ID:
				return dataProvider.getCropTypes();
			default:
				return dataProvider.getVarieties();
		}
	}

	public static List<AggregateItem> getAggregateData(int actionTypeId,
			RealFarmProvider dataProvider, int seedTypeId) {

		switch (actionTypeId) {

			case RealFarmDatabase.ACTION_TYPE_SOW_ID:
				return dataProvider.getAggregateItemsSow(actionTypeId, seedTypeId);
			case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
				return dataProvider.getAggregateItemsFertilize(actionTypeId, seedTypeId);
			case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
				return dataProvider.getAggregateItemsIrrigate(actionTypeId, seedTypeId);
			case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
				return dataProvider.getAggregateItemsReport(actionTypeId, seedTypeId);
			case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
				return dataProvider.getAggregateItemsHarvest(actionTypeId, seedTypeId);
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
			return dataProvider.getUserAggregateItemSow(aggregateItem.getActionTypeId(), aggregateItem.getSelector1(), aggregateItem.getSelector2());
			//return dataProvider.getUserAggregateItem(aggregateItem.getActionTypeId(), aggregateItem.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID));
		case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:	
			return dataProvider.getUserAggregateItemFertilize(aggregateItem.getActionTypeId(), aggregateItem.getSelector1(), aggregateItem.getSelector2());
			//return dataProvider.getUserAggregateItem(aggregateItem.getActionTypeId(), aggregateItem.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID));
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			return dataProvider.getUserAggregateItemIrrigate(aggregateItem.getActionTypeId(), aggregateItem.getSelector1(), aggregateItem.getSelector2());
			// return dataProvider.getUserAggregateItem(aggregateItem.getActionTypeId(), aggregateItem.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			return dataProvider.getUserAggregateItemReport(aggregateItem.getActionTypeId(), aggregateItem.getSelector1(), aggregateItem.getSelector2());
			//return dataProvider.getUserAggregateItem(aggregateItem.getActionTypeId(), aggregateItem.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
			// TODO: Resource1ID has a satisfaction type, is this what we want?
		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			return dataProvider.getUserAggregateItemHarvest(aggregateItem.getActionTypeId(), aggregateItem.getSelector1());
			//return dataProvider.getUserAggregateItem(aggregateItem.getActionTypeId(), aggregateItem.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
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
		row = li.inflate(R.layout.tpl_aggregate_item, parent, false);

		// gets the appropriate type of AggregateItemWrapper
		wrapper = getAggregateWrapper(row, actionTypeId);

		return wrapper;
	}

	public static AggregateItemWrapper getAggregateWrapper(View row,
			int actionTypeId) {
		switch (actionTypeId) {
		/*case RealFarmDatabase.ACTION_TYPE_SOW_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_TYPE_FERTILIZE_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_TYPE_IRRIGATE_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_TYPE_REPORT_ID:
			return new SowAggregateItemWrapper(row);
		case RealFarmDatabase.ACTION_TYPE_HARVEST_ID:
			return new SowAggregateItemWrapper(row);
			//return new IrrigateAggregateItemWrapper(row);*/
		default:
			return new SowAggregateItemWrapper(row);
		}
	}

	/**
	 * This class can not be instantiated.
	 */
	private ActionDataFactory() {

	}
}
