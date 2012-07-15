package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;
import com.commonsensenet.realfarm.utils.ActionDataFactory;

public class AggregateItemAdapter extends ArrayAdapter<AggregateItem> {
	/** Database provided that used to obtain the required data. */
	private RealFarmProvider mDataProvider;

	/** Type of ActionName that the Adapter is handling. */
	private int mActionNameId;

	/**
	 * Creates a new AggregateItemAdapter instance.
	 */
	public AggregateItemAdapter(Context context,
			List<AggregateItem> aggregates, int actionNameId,
			RealFarmProvider dataProvider) {
		super(context, android.R.layout.simple_list_item_1, aggregates);

		// sets the type of action that is being handled.
		mActionNameId = actionNameId;
		// sets the data provider.
		mDataProvider = dataProvider;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AggregateItemWrapper wrapper = null;
		if (row == null) {
			wrapper = ActionDataFactory.getAggregateWrapper(getContext(), row,
					parent, mActionNameId);
			// extracts the row from the wrapper
			row = wrapper.getRow();
			// sets the tag to avoid looking for it.
			row.setTag(wrapper);
		} else {
			wrapper = (AggregateItemWrapper) row.getTag();
		}

		// populates the data from the AggregateItem
		wrapper.populateFrom(this.getItem(position), mDataProvider);
		return (row);
	}
}
