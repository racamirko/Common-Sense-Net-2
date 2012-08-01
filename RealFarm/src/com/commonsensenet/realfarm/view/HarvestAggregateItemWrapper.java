package com.commonsensenet.realfarm.view;

import android.view.View;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class HarvestAggregateItemWrapper extends AggregateItemWrapper {

	public HarvestAggregateItemWrapper(View row) {
		super(row);
	}

	public void populateFrom(AggregateItem aggregate, RealFarmProvider provider) {

		getUserCount().setText(String.valueOf(aggregate.getUserCount()));
		getTypeText()
				.setText(
						aggregate
								.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));

		// hides the aggregate detail
		mRow.findViewById(R.id.button_aggregate_detail).setVisibility(
				View.INVISIBLE);

		// getTypeImage().setImageResource(seed.getResBg());
	}
}