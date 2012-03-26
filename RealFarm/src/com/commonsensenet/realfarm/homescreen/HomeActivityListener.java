package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivityListener implements OnClickListener {
	protected Activity mActivity;
	private String logTag = "HomeActivityListener";
	
	public HomeActivityListener( Activity pActivity ){
		Log.d(logTag, "Homescreen listener created");
		mActivity = pActivity;
	}

	@Override
	public void onClick(View v) {
		Log.i(logTag, "Button clicked!");
		String txt = ""; 
		switch( v.getId() ){
			case R.id.btn_info_actions:
				txt = "Actions!"; break;
			case R.id.btn_info_advice:
				txt = "Advice!"; break;
			case R.id.btn_info_warn:
				txt = "Warn!"; break;
			case R.id.btn_info_yield:
				txt = "Yield!"; break;
			case R.id.btn_action_diary:
				txt = "Dear diary"; break;
			case R.id.btn_action_fertilize:
				txt = "Fertilize"; break;
			case R.id.btn_action_irrigate:
				txt = "Irrigate"; break;
			case R.id.btn_action_plant:
				txt = "Plant"; break;
			case R.id.btn_action_problem:
				txt = "Problem!!"; break;
			case R.id.btn_action_spray:
				txt = "Spray"; break;
			case R.id.btn_action_yield:
				txt = "Yield action"; break;
		}

		Toast.makeText(mActivity.getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
	}

}
