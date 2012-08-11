package com.commonsensenet.realfarm;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.SoundQueue;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

/**
 * Activity that enables the contains the ActionBar and the Help button.
 * Corresponds to the base class of all activities in the application.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public abstract class HelpEnabledActivity extends SherlockActivity implements
		OnLongClickListener {

	/** MenuItem that represents the help button. */
	protected MenuItem mHelpItem;

	/**
	 * Gets the tag used to log the actions performed by the user. The tag is
	 * obtained from the name of the class.
	 * 
	 * @return the tag that represents the class.
	 */
	public String getLogTag() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void onBackPressed() {
		// stops any currently playing sound.
		stopAudio();

		// tracks the back button.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"back");

		// forces the application to flush its data.
		ApplicationTracker.getInstance().flush();

		// performs the system default operation.
		super.onBackPressed();
	}

	protected void onCreate(Bundle savedInstanceState) {
		// sets the global style of the application.
		setTheme(RealFarmApp.THEME);

		super.onCreate(savedInstanceState);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.ACTIVITY_VIEW,
				getLogTag());

		// enables full screen mode
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// enables the home button arrow.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
				R.drawable.bg_curved);
		getSupportActionBar().setBackgroundDrawable(bg);

		// ArrayAdapter<CharSequence> listAdapter = ArrayAdapter
		// .createFromResource(this, R.array.array_languages,
		// R.layout.sherlock_spinner_item);
		// listAdapter
		// .setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		// getSupportActionBar().setListNavigationCallbacks(listAdapter, null);
		// getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// adds the help button.
		mHelpItem = menu.add("Help").setIcon(R.drawable.ic_54px_help)
				.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public boolean onLongClick(View v) {

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				getLogTag(), v.getId());

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					getLogTag(), "help");

			playAudio(R.raw.help, true);
			return true;
		}

		switch (item.getItemId()) {
		case android.R.id.home:
			// goes back to the Homescreen since the back was clicked.
			Intent intent = new Intent(this, Homescreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					getLogTag(), "home");

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Plays the given resourceId. The sound is only played if
	 * Global.enabledAudio is true, otherwise it is not played.
	 * 
	 * @param resourceId
	 *            id of the audio.
	 */
	protected void playAudio(int resourceId) {
		// plays the sound if the id is valid.
		if (resourceId != -1) {
			playAudio(resourceId, false);
		}
	}

	/**
	 * Plays the given resourceId. The forcePlay flag can be use to play an
	 * audio no matter the Global.enableAudio setting.
	 * 
	 * @param resid
	 *            id of the audio to play.
	 * @param forcePlay
	 *            whether the Global.enableAudio setting is respected or not.
	 */
	protected void playAudio(int resid, Boolean forcePlay) {
		// checking for audio enable
		if (Global.isAudioEnabled || forcePlay) {
			// gets the singleton queue
			SoundQueue sq = SoundQueue.getInstance();
			// cleans any possibly playing sound
			sq.clean();
			// adds the sound to the queue
			sq.addToQueue(resid);
			// plays the sound
			sq.play();
		}
	}

	// TODO: Akshay's code needs to be merged here
	public void showHelpIcon(View v) {

	}

	/**
	 * Stops any active sound.
	 */
	protected void stopAudio() {

		// stops the sounds being played by the SoundQueue.
		SoundQueue.getInstance().stop();
	}
}
