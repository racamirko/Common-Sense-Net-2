package com.commonsensenet.realfarm.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;

/**
 * Class that wraps up the contents of a Plot, which is presented on a list
 * adapter.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class GeneralAggregateItemWrapper extends AggregateItemWrapper {

	public GeneralAggregateItemWrapper(View row) {
		super(row);
	}
	
	@Override
	public void populateFrom(AggregateItem aggregate, RealFarmProvider provider) {
		
		getLabelNews().setText(String.valueOf(aggregate.getNewsText()));
		getLabelLeft().setText(aggregate.getLeftText());
		getLabelCenter().setText(aggregate.getCenterText());
		getLabelRight().setText(aggregate.getRightText());

		if(!aggregate.getRightText().equals("")){
			getLabelRight().setText(aggregate.getRightText());
			// hack
			getRelativeLayoutRight().getLayoutParams().width = 150;
		}
					
		if(aggregate.getLeftBackground() != -1) getRelativeLayoutLeft().setBackgroundResource(aggregate.getLeftBackground());
		else getLabelLeft().setTextColor(Color.BLACK);
		if(aggregate.getCenterBackground() != -1) getRelativeLayoutCenter().setBackgroundResource(aggregate.getCenterBackground());
		else {
			getLabelCenter().setTextColor(Color.BLACK);
			// hack
			getRelativeLayoutCenter().getLayoutParams().width = 150;
			getLabelCenter().setTextSize(20);
		}
		if(aggregate.getCenterImage() != -1) getImageCenter().setImageResource(aggregate.getCenterImage());

		if(aggregate.getLeftImage() != -1) getImageLeft().setImageResource(aggregate.getLeftImage());
		if(aggregate.getLeftBottomImage() != -1) getImageLeftBottom().setImageResource(aggregate.getLeftBottomImage());

	}

}