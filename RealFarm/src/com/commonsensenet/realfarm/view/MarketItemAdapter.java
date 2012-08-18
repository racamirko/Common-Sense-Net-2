package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.MarketItem;

public class MarketItemAdapter extends ArrayAdapter<MarketItem> {
	/** Database provided that used to obtain the required data. */
	private RealFarmProvider mDataProvider;

	/**
	 * Creates a new UserAggregateItemAdapter instance.
	 */
	public MarketItemAdapter(Context context,
			List<MarketItem> marketItems,
			RealFarmProvider dataProvider) {
		super(context, android.R.layout.simple_list_item_1, marketItems);

		// TODO: this shouldn't be done!!
		mDataProvider = dataProvider;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tpl_market_item, null);
		}
		
		MarketItem marketData = getItem(position);
		if (marketData != null) {
			ImageView bagImage = (ImageView) v.findViewById(R.id.imageLeft);
			if(marketData.getWeight() != -1) bagImage.setImageResource(marketData.getWeight());
			
			TextView userCount = (TextView) v.findViewById(R.id.number);
			userCount.setText(String.valueOf(marketData.getUserCount()));
			
			TextView minMax = (TextView) v.findViewById(R.id.textRight);
			minMax.setText(String.valueOf(marketData.getMin()+" - "+marketData.getMax()+" /qt"));

		}
		return v;
		
	}
}
