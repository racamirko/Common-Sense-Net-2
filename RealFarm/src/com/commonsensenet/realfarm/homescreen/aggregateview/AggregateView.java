package com.commonsensenet.realfarm.homescreen.aggregateview;

import java.util.Vector;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataFilter;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.MessageType;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProviderDummy;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DataAppearanceFactory;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DummyHomescreenData;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.model.Recommendation;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AggregateView extends HelpEnabledActivity {
	private String logTag = "AggregateView";
	protected BaseAdapter mDataAdpt;
	protected String randomText;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.i(logTag, "Aggregate view started");
        super.onCreate(savedInstanceState, R.layout.aggregate_view);
	    randomText = "The default behaviour of the SlidingDrawer component is to maximize to a height of the position of the last component on the screen. But if the last component is at the very bottom, then the SlidingDrawer will not be apparently visible!";

        Log.i(logTag, "Activity started");
                
        TextView lblTitle = (TextView) findViewById(R.id.aggr_lbl_title);
        ImageView imgIcon = (ImageView) findViewById(R.id.aggr_img_icon);

        AggregateDataFilter dataFilter = new AggregateDataFilter();
        String type = getIntent().getStringExtra("type");
        if( type.compareTo("actions") == 0 ){
        	Log.i(logTag, "displaying actions");
        	lblTitle.setText(R.string.k_solved);
        	imgIcon.setImageResource(R.drawable.ic_90px_sowing);
        	dataFilter.setMessageType(MessageType.ACTION);
        }
        if( type.compareTo("advice") == 0 ){
        	Log.i(logTag, "displaying advice");
        	lblTitle.setText(R.string.k_news);
        	imgIcon.setImageResource(R.drawable.ic_90px_diary1);
        	dataFilter.setMessageType(MessageType.ADVICE);
        }
        if( type.compareTo("warn") == 0 ){
        	Log.i(logTag, "displaying warn");
        	lblTitle.setText(R.string.k_farmers);
        	imgIcon.setImageResource(R.drawable.ic_90px_reporting);
        	dataFilter.setMessageType(MessageType.WARN);
        }
        if( type.compareTo("yield") == 0 ){
        	Log.i(logTag, "displaying yields");
        	lblTitle.setText(R.string.k_harvest);
        	imgIcon.setImageResource(R.drawable.ic_90px_harvesting1);
        	dataFilter.setMessageType(MessageType.YIELD);
        }
        
        /***** dummy data *****/
        AggregateDataProviderDummy dataProvider = new AggregateDataProviderDummy(getApplicationContext(), this);
        dataProvider.setNumOfItems(10);
        DataAppearanceFactory appearFactory = new DataAppearanceFactory(AggregateView.this, AggregateView.this);
        Vector<Object> dataItems = dataProvider.getItems(dataFilter); 
        mDataAdpt = appearFactory.getDataAdapter(dataItems);
//        mDataAdpt = new DummyHomescreenData(getApplicationContext(), this, 10);
        /***** end dummy data *****/

        // unique population for now
    	ListView listview = (ListView) findViewById(R.id.aggr_items);
    	listview.setAdapter(mDataAdpt);
    	listview.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        	Log.i(logTag, "Item at position "+ position+ " clicked"); // TODO should be removed at some point
		        }});
        
    }

	@Override
	protected void initKannada() {
        TextView lblTitle = (TextView) findViewById(R.id.aggr_lbl_title);
        lblTitle.setTypeface(mKannadaTypeface);
	}

}
