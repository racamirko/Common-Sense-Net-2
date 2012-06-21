package com.commonsensenet.realfarm;

import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class VideoActivity extends HelpEnabledActivity implements
		OnLongClickListener {
	/** Intent that is capable of playing the selected video. */
	private Intent mTargetIntent;

	public void onBackPressed() {
		SoundQueue.getInstance().stop();

		Intent adminintent123 = new Intent(VideoActivity.this, Homescreen.class);
		startActivity(adminintent123);
		VideoActivity.this.finish();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout of the activity.
		setContentView(R.layout.act_video);

		Button video1 = (Button) findViewById(R.id.button_video1);
		Button video2 = (Button) findViewById(R.id.button_video2);

		video1.setOnLongClickListener(this);
		video2.setOnLongClickListener(this);

		mTargetIntent = new Intent(this, VideoPlayerActivity.class);

		// add the event listeners
		video1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Global.videosel = 1;
				startActivity(mTargetIntent);
				VideoActivity.this.finish();
			}
		});

		// add the event listeners
		video2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Global.videosel = 2;
				startActivity(mTargetIntent);
				VideoActivity.this.finish();
			}
		});
	}

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.button_video1) {
			playAudio(R.raw.video);
		}

		if (v.getId() == R.id.button_video2) {
			playAudio(R.raw.video);
		}

		return true;
	}
}
