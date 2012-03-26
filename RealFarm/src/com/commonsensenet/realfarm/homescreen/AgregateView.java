package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AgregateView extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Realfarm - agregate view", "App started");
        setContentView(R.layout.aggregate_view);
    }

}
