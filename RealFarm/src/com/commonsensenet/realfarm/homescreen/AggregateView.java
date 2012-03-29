package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.DummyHomescreenData;
import com.commonsensenet.realfarm.model.Recommendation;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AggregateView extends Activity {
	private String logTag = "AggregateView";
	protected DummyHomescreenData mDataAdpt;
	protected String randomText;
	
	private OnClickListener mCloseListener = new OnClickListener() {
	    public void onClick(View v) {
	    	Log.d(logTag, "Back clicked");
	    	finish();
	    }
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    randomText = "The default behaviour of the SlidingDrawer component is to maximize to a height of the position of the last component on the screen. But if the last component is at the very bottom, then the SlidingDrawer will not be apparently visible!";

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
		            Dialog dlg = new Dialog(AggregateView.this);
		        	dlg.setContentView(R.layout.dialog_info_detail);
		        	dlg.setCancelable(true);
		        	// parts
		        	TextView dlgDetals = (TextView) dlg.findViewById(R.id.dlg_lbl_details);
		        	ImageView imgIcon = (ImageView) dlg.findViewById(R.id.dlg_img_icon);
		        	// 
		        	DummyHomescreenData adapter = (DummyHomescreenData) parent.getAdapter();
		        	Recommendation rec = (Recommendation) adapter.getItem(position);
		        	
		        	dlg.setTitle( adapter.getDataProvider().getActionNameById(rec.getAction()).getName() );
		        	dlgDetals.setText( adapter.getDataProvider().getSeedById(rec.getSeed()).getName() + randomText);
		        	imgIcon.setImageResource( adapter.getDataProvider().getActionNameById(rec.getAction()).getRes() );
		        	dlg.show();
		        }});
        
    }

}
