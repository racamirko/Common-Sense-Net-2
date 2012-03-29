package com.commonsensenet.realfarm.homescreen;

import com.commonsensenet.realfarm.OfflineMapDemo;
import com.commonsensenet.realfarm.R;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivityListener implements OnClickListener, OnLongClickListener {
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
		Intent inte;
		switch( v.getId() ){
			case R.id.btn_info_actions:
				Log.d(logTag, "Starting actions info");
				inte = new Intent(mActivity, AggregateView.class);
				inte.putExtra("type", "actions");
				mActivity.startActivity(inte);
				break;
			case R.id.btn_info_advice:
				Log.d(logTag, "Starting advice info");
				inte = new Intent(mActivity, AggregateView.class);
				inte.putExtra("type", "advice");
				mActivity.startActivity(inte);
				break;
			case R.id.btn_info_warn:
				Log.d(logTag, "Starting warn info");
				inte = new Intent(mActivity, AggregateView.class);
				inte.putExtra("type", "warn");
				mActivity.startActivity(inte);
				break;
			case R.id.btn_info_yield:
				Log.d(logTag, "Starting yield info");
				inte = new Intent(mActivity, AggregateView.class);
				inte.putExtra("type", "yield");
				mActivity.startActivity(inte);
				break;
			case R.id.btn_action_diary:
			case R.id.btn_action_fertilize:
			case R.id.btn_action_irrigate:
			case R.id.btn_action_plant:
			case R.id.btn_action_problem:
			case R.id.btn_action_spray:
			case R.id.btn_action_yield:
				mActivity.startActivity(new Intent(mActivity, OfflineMapDemo.class));
				break;
		}

		if( txt != "" )
			Toast.makeText(mActivity.getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onLongClick(View v) {
		ProgressBar prgs = (ProgressBar) mActivity.findViewById(R.id.helpIndicator);
		prgs.setVisibility(prgs.VISIBLE);
		int loc[] = new int[2];
		v.getLocationOnScreen(loc);
		prgs.setPadding(loc[0], loc[1], 0, 0);
		Toast.makeText(mActivity, "Longclick", Toast.LENGTH_SHORT).show();
		return true;
	}

}
