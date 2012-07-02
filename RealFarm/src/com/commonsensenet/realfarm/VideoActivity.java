package com.commonsensenet.realfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.commonsensenet.realfarm.homescreen.Homescreen;

public class VideoActivity extends HelpEnabledActivity {
	/** Intent that is capable of playing the selected video. */
	private Intent mTargetIntent;

	public void onBackPressed() {

		startActivity(new Intent(VideoActivity.this, Homescreen.class));
		VideoActivity.this.finish();

		super.onBackPressed();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// sets the layout of the activity.
		setContentView(R.layout.act_video);

		// obtains the buttons from the form
		Button video1 = (Button) findViewById(R.id.button_video1);
		Button video2 = (Button) findViewById(R.id.button_video2);

		// adds the button listeners
		video1.setOnLongClickListener(this);
		video2.setOnLongClickListener(this);

		// common targetIntent
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
		} else if (v.getId() == R.id.button_video2) {
			playAudio(R.raw.video);
		}

		return super.onLongClick(v);
	}
}
