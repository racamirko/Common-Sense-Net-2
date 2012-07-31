package com.commonsensenet.realfarm.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

//	private TextView mTreatmentCount;

/*	public TextView getTreatmentCount() {

		if (mTreatmentCount == null) {
			mTreatmentCount = (TextView) mRow
					.findViewById(R.id.label_aggregate_detail_count);
		}
		return (mTreatmentCount);
	}*/

	public void populateFrom(AggregateItem aggregate, RealFarmProvider provider) {

		SeedType seed = provider.getVarById(Integer.valueOf(aggregate
				.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_SEEDTYPEID)));
		getUserCount().setText(String.valueOf(aggregate.getUserCount()));
		getTypeText().setText(seed.getShortName());
		getTypeImage().setBackgroundResource(seed.getResBg());

		getTreatmentCount().setText(aggregate.getValue(RealFarmDatabase.COLUMN_NAME_ACTION_TREATMENT));		
		getUserImg().setImageResource(R.drawable.sowingaggsection);

	}
}