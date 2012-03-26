package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class AggregateView extends Activity {
	private String logTag = "AggregateView";
	
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
        
    }

}
