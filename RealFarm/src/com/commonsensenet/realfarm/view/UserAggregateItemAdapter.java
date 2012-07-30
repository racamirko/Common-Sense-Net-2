package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.aggregate.UserAggregateItem;

public class UserAggregateItemAdapter extends ArrayAdapter<UserAggregateItem> {
	/** Database provided that used to obtain the required data. */
	private RealFarmProvider mDataProvider;
	
	/**
	 * Creates a new UserAggregateItemAdapter instance.
	 */
	public UserAggregateItemAdapter(Context context,
			List<UserAggregateItem> userAggregates,
			RealFarmProvider dataProvider) {
		super(context, android.R.layout.simple_list_item_1, userAggregates);

		// TODO: this shouldn't be done!!
		mDataProvider = dataProvider;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		UserAggregateItemWrapper wrapper = null;
		if (row == null) {

			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(R.layout.tpl_user_aggregate_item, parent, false);
			wrapper = new UserAggregateItemWrapper(row);
			row.setTag(wrapper);
		} else {
			wrapper = (UserAggregateItemWrapper) row.getTag();
		}

		wrapper.populateFrom(this.getItem(position), mDataProvider);
		
		return (row);
	}
}
