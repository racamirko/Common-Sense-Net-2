package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * Activity that is able to play a given, showing required Playback controls.
 * 
 * Obtained from: http://www.41post.com/?p=3024
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public class VideoPlayerActivity extends Activity {
	/** View that is able to play a video. */
	private VideoView mVideoView;

	public void onBackPressed() {
		SoundQueue.getInstance().stop();

		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, this.getClass().getSimpleName(), "back");
		ApplicationTracker.getInstance().flushAll();

		startActivity(new Intent(VideoPlayerActivity.this, VideoActivity.class));
		VideoPlayerActivity.this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// sets the layout of the activity.
		setContentView(R.layout.act_video_player);

		ApplicationTracker.getInstance().logEvent(EventType.ACTIVITY_VIEW,
				Global.userId, this.getClass().getSimpleName());

		// gets the extras to extract the select video from there.
		Bundle extras = getIntent().getExtras();

		// determines the path of the video to load based on the passed
		// parameters.
		String videoPath = null;
		if (extras.containsKey(VideoActivity.SELECTED_VIDEO)) {
			videoPath = extras.getString(VideoActivity.SELECTED_VIDEO);
		}

		// get the VideoView from the layout file
		mVideoView = (VideoView) findViewById(R.id.video_player_videoview);
		// use this to get touch events
		mVideoView.requestFocus();
		// set the video URI, passing the video source path as an URI
		mVideoView.setVideoURI(Uri.parse(videoPath));

		// creates a new MediaController
		MediaController mediaController = new MediaController(this) {
			@Override
			public void show() {
				super.show(0);
			}
		};
		mediaController.setAnchorView(mVideoView);

		// sets the media controller.
		mVideoView.setMediaController(mediaController);

		// plays the video.
		mVideoView.start();
		// makes it always visible.
		// mediaController.show(0);

		// listens to the back button event.
		final Button video_back = (Button) findViewById(R.id.video_player_button_back);
		video_back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						Global.userId, this.getClass().getSimpleName(), "back");

				startActivity(new Intent(VideoPlayerActivity.this,
						VideoActivity.class));
				VideoPlayerActivity.this.finish();
			}
		});
	}
}
