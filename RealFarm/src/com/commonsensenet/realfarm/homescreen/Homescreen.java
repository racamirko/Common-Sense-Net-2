package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Homescreen extends HelpEnabledActivity {
	private String logTag = "Homescreen";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(logTag, "App started");
        setContentView(R.layout.homescreen);
        // setup listener to all buttons
        HomeActivityListener clckListener = new HomeActivityListener(this);
        initActionListener(clckListener);
        initKannada();
        setHelpIcon(findViewById(R.id.helpIndicator));
    }

	private void initActionListener(HomeActivityListener clckListener) {
		((Button) findViewById(R.id.home_btn_advice)).setOnClickListener(clckListener);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnLongClickListener(this);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnTouchListener(this);
      

        ((Button) findViewById(R.id.home_btn_actions)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_actions)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_warn)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_warn)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_warn)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.home_btn_yield)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_yield)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_yield)).setOnTouchListener(this);
        
        
        
        
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnTouchListener(this);
	}
    
    protected void initKannada(){
    	TextView tmpText = (TextView) findViewById(R.id.home_lbl_actions);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_the_pest));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_advice);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_the_disease));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_actions);
    	tmpText.setTypeface(mKannadaTypeface);
//    	tmpText.setText(getString(R.string.k_));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_actions);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_the_pest));
    }

	@Override
	public void onBackPressed() {
		Homescreen.this.finish();
		
		// Production code, commented out for quicker development
//		new AlertDialog.Builder(this)
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.setTitle(R.string.exitTitle)
//				.setMessage(R.string.exitMsg)
//				.setNegativeButton(android.R.string.cancel, null)
//				.setPositiveButton(android.R.string.ok,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// Exit the activity
//								Homescreen.this.finish();
//							}
//						}).show();
	}
	
}
