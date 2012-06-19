package com.commonsensenet.realfarm.homescreen.aggregateview;

import java.util.Vector;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataFilter;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.MessageType;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProviderDummy;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DataAppearanceFactory;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;

public class AggregateView extends HelpEnabledActivity {
	public static String LOG_TAG = "AggregateView";
	protected BaseAdapter mDataAdpt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(LOG_TAG, "Aggregate view started");
		super.onCreate(savedInstanceState, R.layout.aggregate_view);

		Log.i(LOG_TAG, "Activity started");

		TextView lblTitle = (TextView) findViewById(R.id.aggr_lbl_title);
		ImageView imgIcon = (ImageView) findViewById(R.id.aggr_img_icon);

		AggregateDataFilter dataFilter = new AggregateDataFilter();
		String type = getIntent().getStringExtra("type");
		if (type.compareTo("actions") == 0) {
			Log.i(LOG_TAG, "displaying actions");
			lblTitle.setText(R.string.k_solved);
			imgIcon.setImageResource(R.drawable.ic_90px_sowing);
			dataFilter.setMessageType(MessageType.ACTION);
		}
		if (type.compareTo("advice") == 0) {
			Log.i(LOG_TAG, "displaying advice");
			lblTitle.setText(R.string.k_news);
			imgIcon.setImageResource(R.drawable.ic_90px_diary1);
			dataFilter.setMessageType(MessageType.ADVICE);
		}
		if (type.compareTo("warn") == 0) {
			Log.i(LOG_TAG, "displaying warn");
			lblTitle.setText(R.string.k_farmers);
			imgIcon.setImageResource(R.drawable.ic_90px_reporting);
			dataFilter.setMessageType(MessageType.WARN);
		}
		if (type.compareTo("yield") == 0) {
			Log.i(LOG_TAG, "displaying yields");
			lblTitle.setText(R.string.k_harvest);
			imgIcon.setImageResource(R.drawable.ic_90px_harvesting1);
			dataFilter.setMessageType(MessageType.YIELD);
		}

		/***** dummy data *****/
		AggregateDataProviderDummy dataProvider = new AggregateDataProviderDummy(
				getApplicationContext(), this);
		dataProvider.setNumOfItems(10);
		DataAppearanceFactory appearFactory = new DataAppearanceFactory(
				AggregateView.this, AggregateView.this);
		Vector<Object> dataItems = dataProvider.getItems(dataFilter);
		mDataAdpt = appearFactory.getDataAdapter(dataItems);
		// mDataAdpt = new DummyHomescreenData(getApplicationContext(), this,
		// 10);
		/***** end dummy data *****/

		// unique population for now
		ListView listview = (ListView) findViewById(R.id.aggr_items);
		listview.setAdapter(mDataAdpt);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO: should be removed at some point
				Log.i(LOG_TAG, "Item at position " + position + " clicked");
			}
		});
	}
}
