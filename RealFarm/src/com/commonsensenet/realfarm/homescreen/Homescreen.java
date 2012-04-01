package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.Toast;

public class Homescreen extends HelpEnabledActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Realfarm - homescreen", "App started");
        setContentView(R.layout.homescreen);
        // setup listener to all buttons
        HomeActivityListener clckListener = new HomeActivityListener(this);
        ((ImageButton) findViewById(R.id.btn_info_actions)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_actions)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_info_actions)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_info_advice)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_advice)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_info_advice)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_info_warn)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_warn)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_info_warn)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_info_yield)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_yield)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_info_yield)).setOnTouchListener(this);
        
        
        
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnLongClickListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnLongClickListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnLongClickListener(this);

        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnLongClickListener(this);

        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnLongClickListener(this);

        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnLongClickListener(this);

        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnLongClickListener(this);
        setHelpIcon(findViewById(R.id.helpIndicator));
    }

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exitTitle)
				.setMessage(R.string.exitMsg)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Exit the activity
								Homescreen.this.finish();
							}
						}).show();
	}
	
}
