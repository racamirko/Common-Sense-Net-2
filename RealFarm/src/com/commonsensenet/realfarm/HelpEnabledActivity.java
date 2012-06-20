package com.commonsensenet.realfarm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

/**
 * Activity that enables the contains the ActionBar and the Help button.
 * Corresponds to the base class of all activities in the application.
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * 
 */
public abstract class HelpEnabledActivity extends SherlockActivity {
	@Override
	public void onBackPressed() {
		// stops any currently playing sound.
		SoundQueue.getInstance().stop();
		super.onBackPressed();
	}

	protected void playAudio(int resid) // audio integration
	{
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

	public void onCreate(Bundle savedInstanceState) {
		// sets the global style of the application.
		setTheme(RealFarmApp.THEME);
		super.onCreate(savedInstanceState);

		// enables the home button arrow.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// adds the help button.
		menu.add("Help").setIcon(R.drawable.help)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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
}
