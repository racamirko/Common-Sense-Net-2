package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.util.Log;
import android.view.View;

import com.commonsensenet.realfarm.model.Recommendation;

public class VIRecommendation extends VisualItemBase {
	private String logTag = "VIRecommendation";

	protected Recommendation recommendation;

	public VIRecommendation(Recommendation recommendation){
		this.recommendation = recommendation;
		Log.d(logTag, "created");
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
	public void onClick(View v) {
		throw new UnsupportedOperationException();
	}
	
}
