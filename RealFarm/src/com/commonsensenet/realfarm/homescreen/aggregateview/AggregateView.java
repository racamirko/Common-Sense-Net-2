package com.commonsensenet.realfarm.homescreen.aggregateview;

import com.commonsensenet.realfarm.R;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AggregateView extends HelpEnabledActivity {
	private String logTag = "AggregateView";
	protected DummyHomescreenData mDataAdpt;
	protected String randomText;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.i(logTag, "Aggregate view started");
        super.onCreate(savedInstanceState, R.layout.aggregate_view);
	    randomText = "The default behaviour of the SlidingDrawer component is to maximize to a height of the position of the last component on the screen. But if the last component is at the very bottom, then the SlidingDrawer will not be apparently visible!";

        Log.i(logTag, "Activity started");
        
        /***** dummy data *****/
        mDataAdpt = new DummyHomescreenData(getApplicationContext(), this, 10);
        /***** end dummy data *****/
        
        TextView lblTitle = (TextView) findViewById(R.id.aggr_lbl_title);
        ImageView imgIcon = (ImageView) findViewById(R.id.aggr_img_icon);

        String type = getIntent().getStringExtra("type");
        if( type.compareTo("actions") == 0 ){
        	Log.i(logTag, "displaying actions");
        	lblTitle.setText(R.string.k_solved);
        	imgIcon.setImageResource(R.drawable.ic_90px_sowing);
        }
        if( type.compareTo("advice") == 0 ){
        	Log.i(logTag, "displaying advice");
        	lblTitle.setText(R.string.k_news);
        	imgIcon.setImageResource(R.drawable.ic_90px_diary1);
        }
        if( type.compareTo("warn") == 0 ){
        	Log.i(logTag, "displaying warn");
        	lblTitle.setText(R.string.k_farmers);
        	imgIcon.setImageResource(R.drawable.ic_90px_reporting);
        }
        if( type.compareTo("yield") == 0 ){
        	Log.i(logTag, "displaying yields");
        	lblTitle.setText(R.string.k_harvest);
        	imgIcon.setImageResource(R.drawable.ic_90px_harvesting1);
        }

        // unique population for now
    	ListView listview = (ListView) findViewById(R.id.aggr_items);
    	listview.setAdapter(mDataAdpt);
    	listview.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        	Log.i(logTag, "Item at position "+ position+ " clicked");
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

	@Override
	protected void initKannada() {
        TextView lblTitle = (TextView) findViewById(R.id.aggr_lbl_title);
        lblTitle.setTypeface(mKannadaTypeface);
	}

}
