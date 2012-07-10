package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * Activity that enables the contains the ActionBar and the Help button.
 * Corresponds to the base class of all activities in the application.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public abstract class HelpEnabledActivity extends SherlockActivity implements
		OnLongClickListener {

	protected String getCurrentTime() {
		Calendar ctaq = Calendar.getInstance();
		SimpleDateFormat dfaq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String crntdt = dfaq.format(ctaq.getTime());
		Log.i("strtdat", crntdt);
		return crntdt;
	}

	@Override
	public void onBackPressed() {
		// stops any currently playing sound.
		stopAudio();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// sets the global style of the application.
		setTheme(RealFarmApp.THEME);
		super.onCreate(savedInstanceState);

		// enables fullscreen mode
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
		menu.add("Help").setIcon(R.drawable.ic_54px_help)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public boolean onLongClick(View v) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: handle the help button click.

		if (item.getTitle().toString().equals("Help")) {
			playAudio(R.raw.help);
			Toast.makeText(this, "Requested help!", Toast.LENGTH_SHORT).show();
			return true;
		}
		switch (item.getItemId()) {
		case android.R.id.home:
			// goes back to the Homescreen since the back was clicked.
			Intent intent = new Intent(this, Homescreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Plays the given resourceId. The sound is only played if
	 * Global.enabledAudio is true, otherwise it is not played.
	 * 
	 * @param resid
	 *            id of the audio.
	 */
	protected void playAudio(int resid) {
		if (Global.enableAudio) // checking for audio enable
		{
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

	protected void stopAudio() {
		SoundQueue.getInstance().stop();
	}
}
