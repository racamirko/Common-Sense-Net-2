package com.commonsensenet.realfarm.view;

import android.graphics.Color;
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
		getUserCount().setText(String.valueOf(aggregate.getUserCount()));
		getTypeText().setText(aggregate.getCenterText());
		if(aggregate.getCenterBackground() != -1) getTypeImage().setBackgroundResource(aggregate.getCenterBackground());
		else getTypeText().setTextColor(Color.BLACK);
		getTreatmentCount().setText(aggregate.getRightText());
		if(aggregate.getRightBackground() != -1) getTreatmentImg().setImageResource(aggregate.getRightBackground());
		
		// TODO: adapt this to all sections
		if(aggregate.getUserImage() != -1) getUserImg().setImageResource(aggregate.getUserImage());

	}
}