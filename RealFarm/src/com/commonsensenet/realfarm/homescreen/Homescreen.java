package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Homescreen extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.i("Realfarm - homescreen", "App started");
        setContentView(R.layout.homescreen);
    }

}
