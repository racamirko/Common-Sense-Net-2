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
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
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

	/** MenuItem that represents the help button. */
	protected MenuItem mHelpItem;
	/** MenuItem that represents the home button. */
	protected MenuItem mHomeItem;

	public void add_audio_hundred(int n) {
		System.out.println("add_audio_hundred");
		SoundQueue sq = SoundQueue.getInstance();

		switch (n) {
		case 1:
			sq.addToQueue(R.raw.a100);
			break;
		case 2:
			sq.addToQueue(R.raw.a200);
			break;
		case 3:
			sq.addToQueue(R.raw.a300);
			break;
		case 4:
			sq.addToQueue(R.raw.a400);
			break;
		case 5:
			sq.addToQueue(R.raw.a500);
			break;
		case 6:
			sq.addToQueue(R.raw.a600);
			break;
		case 7:
			sq.addToQueue(R.raw.a700);
			break;
		case 8:
			sq.addToQueue(R.raw.a800);
			break;
		case 9:
			sq.addToQueue(R.raw.a900);
			break;
		case 10:
			sq.addToQueue(R.raw.a1000);
			break;
		default:
			break;
		}

	}

	public void add_audio_hundreds(int n) {
		System.out.println("add_audio_hundreds");
		SoundQueue sq = SoundQueue.getInstance();
		// sq.clean();

		switch (n) {
		case 1:
			sq.addToQueue(R.raw.a100s);
			break;
		case 2:
			sq.addToQueue(R.raw.a200s);
			break;
		case 3:
			sq.addToQueue(R.raw.a300s);
			break;
		case 4:
			sq.addToQueue(R.raw.a400s);
			break;
		case 5:
			sq.addToQueue(R.raw.a500s);
			break;
		case 6:
			sq.addToQueue(R.raw.a600s);
			break;
		case 7:
			sq.addToQueue(R.raw.a700s);
			break;
		case 8:
			sq.addToQueue(R.raw.a800s);
			break;
		case 9:
			sq.addToQueue(R.raw.a900s);
			break;
		case 10:
			sq.addToQueue(R.raw.a1000s);
			break;
		default:
			break;
		}

	}

	public void add_audio_single(int n) {
		System.out.println("add_audio_single");
		SoundQueue sq = SoundQueue.getInstance();
		// sq.clean();

		switch (n) {
		case 1:
			sq.addToQueue(R.raw.a1);
			break;
		case 2:
			sq.addToQueue(R.raw.a2);
			break;
		case 3:
			sq.addToQueue(R.raw.a3);
			break;
		case 4:
			sq.addToQueue(R.raw.a4);
			break;
		case 5:
			sq.addToQueue(R.raw.a5);
			break;
		case 6:
			sq.addToQueue(R.raw.a6);
			break;
		case 7:
			sq.addToQueue(R.raw.a7);
			break;
		case 8:
			sq.addToQueue(R.raw.a8);
			break;
		case 9:
			sq.addToQueue(R.raw.a9);
			break;
		case 10:
			sq.addToQueue(R.raw.a10);
			break;
		case 11:
			sq.addToQueue(R.raw.a11);
			break;
		case 12:
			sq.addToQueue(R.raw.a12);
			break;
		case 13:
			sq.addToQueue(R.raw.a13);
			break;
		case 14:
			sq.addToQueue(R.raw.a14);
			break;
		case 15:
			sq.addToQueue(R.raw.a15);
			break;
		case 16:
			sq.addToQueue(R.raw.a16);
			break;
		case 17:
			sq.addToQueue(R.raw.a17);
			break;
		case 18:
			sq.addToQueue(R.raw.a18);
			break;
		case 19:
			sq.addToQueue(R.raw.a19);
			break;
		case 20:
			sq.addToQueue(R.raw.a20);
			break;
		case 21:
			sq.addToQueue(R.raw.a21);
			break;
		case 22:
			sq.addToQueue(R.raw.a22);
			break;
		case 23:
			sq.addToQueue(R.raw.a23);
			break;
		case 24:
			sq.addToQueue(R.raw.a24);
			break;
		case 25:
			sq.addToQueue(R.raw.a25);
			break;
		case 26:
			sq.addToQueue(R.raw.a26);
			break;
		case 27:
			sq.addToQueue(R.raw.a27);
			break;
		case 28:
			sq.addToQueue(R.raw.a28);
			break;
		case 29:
			sq.addToQueue(R.raw.a29);
			break;
		case 30:
			sq.addToQueue(R.raw.a30);
			break;
		case 31:
			sq.addToQueue(R.raw.a31);
			break;
		case 32:
			sq.addToQueue(R.raw.a32);
			break;
		case 33:
			sq.addToQueue(R.raw.a33);
			break;
		case 34:
			sq.addToQueue(R.raw.a34);
			break;
		case 35:
			sq.addToQueue(R.raw.a35);
			break;
		case 36:
			sq.addToQueue(R.raw.a36);
			break;
		case 37:
			sq.addToQueue(R.raw.a37);
			break;
		case 38:
			sq.addToQueue(R.raw.a38);
			break;
		case 39:
			sq.addToQueue(R.raw.a39);
			break;
		case 40:
			sq.addToQueue(R.raw.a40);
			break;
		case 41:
			sq.addToQueue(R.raw.a41);
			break;
		case 42:
			sq.addToQueue(R.raw.a42);
			break;
		case 43:
			sq.addToQueue(R.raw.a43);
			break;
		case 44:
			sq.addToQueue(R.raw.a44);
			break;
		case 45:
			sq.addToQueue(R.raw.a45);
			break;
		case 46:
			sq.addToQueue(R.raw.a46);
			break;
		case 47:
			sq.addToQueue(R.raw.a47);
			break;
		case 48:
			sq.addToQueue(R.raw.a48);
			break;
		case 49:
			sq.addToQueue(R.raw.a49);
			break;
		case 50:
			sq.addToQueue(R.raw.a50);
			break;
		case 51:
			sq.addToQueue(R.raw.a51);
			break;
		case 52:
			sq.addToQueue(R.raw.a52);
			break;
		case 53:
			sq.addToQueue(R.raw.a53);
			break;
		case 54:
			sq.addToQueue(R.raw.a54);
			break;
		case 55:
			sq.addToQueue(R.raw.a55);
			break;
		case 56:
			sq.addToQueue(R.raw.a56);
			break;
		case 57:
			sq.addToQueue(R.raw.a57);
			break;
		case 58:
			sq.addToQueue(R.raw.a58);
			break;
		case 59:
			sq.addToQueue(R.raw.a59);
			break;
		case 60:
			sq.addToQueue(R.raw.a60);
			break;
		case 61:
			sq.addToQueue(R.raw.a61);
			break;
		case 62:
			sq.addToQueue(R.raw.a62);
			break;
		case 63:
			sq.addToQueue(R.raw.a63);
			break;
		case 64:
			sq.addToQueue(R.raw.a64);
			break;
		case 65:
			sq.addToQueue(R.raw.a65);
			break;
		case 66:
			sq.addToQueue(R.raw.a66);
			break;
		case 67:
			sq.addToQueue(R.raw.a67);
			break;
		case 68:
			sq.addToQueue(R.raw.a68);
			break;
		case 69:
			sq.addToQueue(R.raw.a69);
			break;
		case 70:
			sq.addToQueue(R.raw.a70);
			break;
		case 71:
			sq.addToQueue(R.raw.a71);
			break;
		case 72:
			sq.addToQueue(R.raw.a72);
			break;
		case 73:
			sq.addToQueue(R.raw.a73);
			break;
		case 74:
			sq.addToQueue(R.raw.a74);
			break;
		case 75:
			sq.addToQueue(R.raw.a75);
			break;
		case 76:
			sq.addToQueue(R.raw.a76);
			break;
		case 77:
			sq.addToQueue(R.raw.a77);
			break;
		case 78:
			sq.addToQueue(R.raw.a78);
			break;
		case 79:
			sq.addToQueue(R.raw.a79);
			break;
		case 80:
			sq.addToQueue(R.raw.a80);
			break;
		case 81:
			sq.addToQueue(R.raw.a81);
			break;
		case 82:
			sq.addToQueue(R.raw.a82);
			break;
		case 83:
			sq.addToQueue(R.raw.a83);
			break;
		case 84:
			sq.addToQueue(R.raw.a84);
			break;
		case 85:
			sq.addToQueue(R.raw.a85);
			break;
		case 86:
			sq.addToQueue(R.raw.a86);
			break;
		case 87:
			sq.addToQueue(R.raw.a87);
			break;
		case 88:
			sq.addToQueue(R.raw.a88);
			break;
		case 89:
			sq.addToQueue(R.raw.a89);
			break;
		case 90:
			sq.addToQueue(R.raw.a90);
			break;
		case 91:
			sq.addToQueue(R.raw.a91);
			break;
		case 92:
			sq.addToQueue(R.raw.a92);
			break;
		case 93:
			sq.addToQueue(R.raw.a93);
			break;
		case 94:
			sq.addToQueue(R.raw.a94);
			break;
		case 95:
			sq.addToQueue(R.raw.a95);
			break;
		case 96:
			sq.addToQueue(R.raw.a96);
			break;
		case 97:
			sq.addToQueue(R.raw.a97);
			break;
		case 98:
			sq.addToQueue(R.raw.a98);
			break;
		case 99:
			sq.addToQueue(R.raw.a99);
			break;
		case 100:
			sq.addToQueue(R.raw.a100);
			break;
		case 101:
			sq.addToQueue(R.raw.a0); // plays zero
			break;
		case 102:
			sq.addToQueue(R.raw.point); // plays point
			break;

		default:
			break;
		}

	}

	// adds audio from 1000 to 10000
	public void add_audio_thousand(int n) {
		System.out.println("add_audio_thousand");
		SoundQueue sq = SoundQueue.getInstance();
		// sq.clean();

		switch (n) {
		case 1:
			sq.addToQueue(R.raw.a1000);
			break;
		case 2:
			sq.addToQueue(R.raw.a2000);
			break;
		case 3:
			sq.addToQueue(R.raw.a3000);
			break;
		case 4:
			sq.addToQueue(R.raw.a4000);
			break;
		case 5:
			sq.addToQueue(R.raw.a5000);
			break;
		case 6:
			sq.addToQueue(R.raw.a6000);
			break;
		case 7:
			sq.addToQueue(R.raw.a7000);
			break;
		case 8:
			sq.addToQueue(R.raw.a8000);
			break;
		case 9:
			sq.addToQueue(R.raw.a9000);
			break;
		case 10:
			sq.addToQueue(R.raw.a10000);
			break;
		default:
			break;
		}
	}

	public void add_audio_thousands(int n) {
		System.out.println("add_audio_thousands");
		SoundQueue sq = SoundQueue.getInstance();

		switch (n) {
		case 1:
			sq.addToQueue(R.raw.a1000s);
			break;
		case 2:
			sq.addToQueue(R.raw.a2000s);
			break;
		case 3:
			sq.addToQueue(R.raw.a3000s);
			break;
		case 4:
			sq.addToQueue(R.raw.a4000s);
			break;
		case 5:
			sq.addToQueue(R.raw.a5000s);
			break;
		case 6:
			sq.addToQueue(R.raw.a6000s);
			break;
		case 7:
			sq.addToQueue(R.raw.a7000s);
			break;
		case 8:
			sq.addToQueue(R.raw.a8000s);
			break;
		case 9:
			sq.addToQueue(R.raw.a9000s);
			break;
		case 10:
			sq.addToQueue(R.raw.a10000s);
			break;
		default:
			break;
		}

	}

	/**
	 * Adds the sound with the given resource id to the SoundQueue.
	 * 
	 * @param resid
	 *            resource identifier
	 */
	protected void addToSoundQueue(int resid) {
		SoundQueue sq = SoundQueue.getInstance();
		// adds the sound to the queue
		sq.addToQueue(resid);
	}

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
		ApplicationTracker.getInstance().logEvent(EventType.CLICK,
				Global.userId, getLogTag(), "back");

		// forces the application to flush its data.
		ApplicationTracker.getInstance().flushAll();

		// performs the system default operation.
		super.onBackPressed();
	}

	protected void onCreate(Bundle savedInstanceState) {
		// sets the global style of the application.
		setTheme(RealFarmApp.THEME);

		super.onCreate(savedInstanceState);

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.ACTIVITY_VIEW,
				Global.userId, getLogTag());

		// enables full screen mode
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// adds the extended background as default.
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
				R.drawable.bg_curved_extended);
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

		// adds the home button.
		mHomeItem = menu.add("Home").setIcon(R.drawable.ic_home)
				.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

		// adds the help button.
		mHelpItem = menu.add("Help").setIcon(R.drawable.ic_help)
				.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	public boolean onLongClick(View v) {

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.LONG_CLICK,
				Global.userId, getLogTag(),
				getResources().getResourceEntryName(v.getId()));

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.equals(mHelpItem)) {

			// tracks the application usage
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "help");

			playAudio(R.raw.help, true);
			return true;
		} else if (item.equals(mHomeItem)) {
			// goes back to the Homescreen since the back was clicked.
			Intent intent = new Intent(this, Homescreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			// tracks the application usage.
			ApplicationTracker.getInstance().logEvent(EventType.CLICK,
					Global.userId, getLogTag(), "home");

			return true;
		} else { // asks the parent.
			return super.onOptionsItemSelected(item);
		}
	}

	public void play_float(float num) {

		int result = (int) (num * 100);
		int quot = result / 100;
		int rem = result % 100;

		System.out.println("double" + num);
		System.out.println("result" + result);
		// System.out.println("result1"+result1);
		System.out.println("quot" + quot);
		System.out.println("rem" + rem);

		if (quot == 0) {
			add_audio_single(101); // says "zero"
		} else {
			add_audio_single(quot);
		}

		if ((quot == 0) & (rem <= 90)) {
			int rem1 = rem / 10;
			add_audio_single(102); // says "point"
			add_audio_single(rem1);
		}
		if ((rem <= 9) & (rem != 0)) // To handle cases 2.09, ..etc
		{
			System.out.println("in rem<9 ");
			add_audio_single(102); // says point
			add_audio_single(101); // says number after decimal point
			add_audio_single(rem);
		}
		if ((rem > 9) & (quot != 0)) // To handle cases 2.90, ..etc
		{
			System.out.println("in rem>9 ");
			if ((rem != 25) & (rem != 50) & (rem != 75) & (rem != 0)) {
				int rem1 = rem / 10;
				int rem2 = rem % 10;

				add_audio_single(102); // says point
				add_audio_single(rem1); // //says number after decimal point
				if (rem2 != 0) {
					add_audio_single(rem2); // says second number after decimal
											// point
				}
			}
		}
		if ((rem == 25) || ((rem == 50) & (quot != 0)) || (rem == 75)) {
			if (rem == 25) {
				SoundQueue.getInstance().addToQueue(R.raw.quarter);
			}
			if (rem == 50) {
				SoundQueue.getInstance().addToQueue(R.raw.half);
			}
			if (rem == 75) {
				SoundQueue.getInstance().addToQueue(R.raw.three_fourth);
			}
		}
		// System.out.println("sound played in float");
		// SoundQueue.getInstance().play();
	}

	public void play_integer(int num) {
		if (num >= 1000) {
			if (num % 1000 == 0) {
				int quot = num / 1000;
				add_audio_thousand(quot);
			} else if (num % 1000 != 0) {
				int quot = num / 1000;
				add_audio_thousands(quot);

				int rem1 = num % 1000;
				num = rem1;
			}

		}

		if ((num > 100) & (num < 1000)) {
			if (num % 100 == 0) // adds audio from 101 to 999
			{
				int quot = num / 100;
				add_audio_hundred(quot);
			} else if (num % 100 != 0) {
				int quot = num / 100;
				add_audio_hundreds(quot);

				int rem = num % 100;
				add_audio_single(rem);
			}
		}
		if (num <= 100) // adds audio from 1 to 100
		{
			if ((num % 100 != 0) | (num % 100 == 0) | (num % 100 == 100)) {
				if (num % 100 == 100) {
					add_audio_single(101); // adds audio "zero"
				} else if (num % 100 == 0) {
					add_audio_single(100); // adds audio "100"
				} else {
					add_audio_single(num % 100);
				}

			}
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

	protected void playSound() {
		SoundQueue.getInstance().play();
	}

	// TODO: icon needs to be displayed when the help is clicked.
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
