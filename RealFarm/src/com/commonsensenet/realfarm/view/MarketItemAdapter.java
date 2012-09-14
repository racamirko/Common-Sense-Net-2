package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.AggregateItem;

public class MarketItemAdapter extends AggregateItemAdapter {

	/**
	 * Creates a new UserAggregateItemAdapter instance.
	 */
	public MarketItemAdapter(Context context, List<AggregateItem> marketItems,
			RealFarmProvider dataProvider) {
		super(context, marketItems, 1, dataProvider);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tpl_market_item, null);
		}

		AggregateItem marketData = getItem(position);
		if (marketData != null) {
			ImageView bagImage = (ImageView) v.findViewById(R.id.imageLeft);
			if (marketData.getCenterImage() != -1)
				bagImage.setImageResource(marketData.getCenterImage());

			TextView userCount = (TextView) v.findViewById(R.id.number);
			userCount.setText(String.valueOf(marketData.getNewsText()));

			TextView minMax = (TextView) v.findViewById(R.id.textRight);
			minMax.setText(marketData.getRightText());

		}
		return v;
	}
}
