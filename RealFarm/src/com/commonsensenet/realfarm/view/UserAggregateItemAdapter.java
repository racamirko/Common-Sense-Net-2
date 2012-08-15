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
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tpl_user_aggregate_item, null);
		}
		
		UserAggregateItem userAggregate = getItem(position);
		if (userAggregate != null) {
			TextView nameView = (TextView) v.findViewById(R.id.label_user_aggregate_user_name);
			TextView dateView = (TextView) v.findViewById(R.id.label_user_aggregate_date);
			ImageView avatarView = (ImageView) v.findViewById(R.id.icon_user_aggregate_user_avatar);
			ImageView detailView = (ImageView) v.findViewById(R.id.icon_user_detail);
			TextView detailLabel = (TextView) v.findViewById(R.id.label_user_detail);
			
			nameView.setText(userAggregate.getName());
			dateView.setText(userAggregate.getDate());

			int resId = v.getResources().getIdentifier(userAggregate.getAvatar(), "drawable", "com.commonsensenet.realfarm");
			avatarView.setImageResource(resId);
			// TODO: of resource not valid, R.drawable.farmers
			
			if(userAggregate.getRightImg() != -1) detailView.setImageResource(userAggregate.getRightImg());
			detailLabel.setText(userAggregate.getRightText());
		}
		return v;
		
	}
}
