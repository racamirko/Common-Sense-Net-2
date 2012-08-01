package com.commonsensenet.realfarm.view;

import android.graphics.Color;
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
public class IrrigateAggregateItemWrapper extends AggregateItemWrapper {

	public IrrigateAggregateItemWrapper(View row) {
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

		getTypeText().setTextColor(Color.BLACK);
		/* getUserImg().setImageResource(R.drawable.irrigatingaggsection); */

		// getTypeImage().setImageResource(seed.getResBg());
	}
}
