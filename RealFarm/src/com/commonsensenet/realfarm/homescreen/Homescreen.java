package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class Homescreen extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Realfarm - homescreen", "App started");
        setContentView(R.layout.homescreen);
        // setup listener to all buttons
        HomeActivityListener clckListener = new HomeActivityListener(this);
        ((ImageButton) findViewById(R.id.btn_info_actions)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_advice)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_warn)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_info_yield)).setOnClickListener(clckListener);
        
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnClickListener(clckListener);
    }

}
