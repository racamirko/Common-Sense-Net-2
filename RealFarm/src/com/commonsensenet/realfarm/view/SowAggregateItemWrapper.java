package com.commonsensenet.realfarm.view;

import android.view.View;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class SowAggregateItemWrapper extends AggregateItemWrapper {

	public SowAggregateItemWrapper(View row) {
		super(row);
	}

	public void populateFrom(AggregateItem aggregate, RealFarmProvider provider) {

		SeedType seed = provider.getSeedById((Integer.valueOf(aggregate
				.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID))));
		getUserCount().setText(String.valueOf(aggregate.getUserCount()));
		getTypeText().setText(seed.getShortName());
		getTypeImage().setBackgroundResource(seed.getResource());

		getTreatmentCount()
				.setText(
						aggregate
								.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_RESOURCE1ID));
		getUserImg().setImageResource(R.drawable.sowingaggsection);

	}
}