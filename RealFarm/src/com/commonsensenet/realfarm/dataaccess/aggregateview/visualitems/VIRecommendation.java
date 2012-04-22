package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.view.View;

import com.commonsensenet.realfarm.model.Recommendation;

public class VIRecommendation extends VisualItemBase {
	protected Recommendation recommendation;

	public VIRecommendation(Recommendation recommendation){
		this.recommendation = recommendation;
	}

	@Override
	public View populateView(View view){
		return null;
	}

	@Override
	public Object getDataItem() {
		return recommendation;
	}
	
	@Override
	protected void populateLayoutAggregate(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void onClick(View v) {
		throw new UnsupportedOperationException();
	}
	
}
