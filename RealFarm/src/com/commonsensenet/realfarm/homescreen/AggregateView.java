package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.DummyHomescreenData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AggregateView extends Activity {
	private String logTag = "AggregateView";
	protected DummyHomescreenData mDataAdpt;
	
	private OnClickListener mCloseListener = new OnClickListener() {
	    public void onClick(View v) {
	    	Log.d(logTag, "Back clicked");
	    	finish();
	    }
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(logTag, "Activity started");
        setContentView(R.layout.aggregate_view);
        ((ImageButton)findViewById(R.id.btn_aggregate_back)).setOnClickListener(mCloseListener);
        
        mDataAdpt = new DummyHomescreenData(getApplicationContext(), this, 20);

        String type = getIntent().getStringExtra("type");
        if( type.compareTo("actions") == 0 ){
        	// set actios dataset
        	Toast.makeText(getApplicationContext(), "Actions!", Toast.LENGTH_SHORT).show();
        }
        if( type.compareTo("advice") == 0 ){
        	// set actios dataset
        	Toast.makeText(getApplicationContext(), "Advices!", Toast.LENGTH_SHORT).show();
        }
        if( type.compareTo("warn") == 0 ){
        	// set actios dataset
        	Toast.makeText(getApplicationContext(), "Warnings!", Toast.LENGTH_SHORT).show();
        }
        if( type.compareTo("yield") == 0 ){
        	// set actios dataset
        	Toast.makeText(getApplicationContext(), "Yield!", Toast.LENGTH_SHORT).show();
        }

        // unique population for now
    	ListView listview = (ListView) findViewById(R.id.aggr_items);
    	listview.setAdapter(mDataAdpt);
    	listview.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Toast.makeText(AggregateView.this, "" + position, Toast.LENGTH_SHORT).show();
		        }});
        
    }

}
