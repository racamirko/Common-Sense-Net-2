package com.commonsensenet.realfarm.homescreen;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.commonsensenet.realfarm.OfflineMapDemo;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DummyHomescreenData;
import com.commonsensenet.realfarm.homescreen.aggregateview.AggregateView;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.utils.PathBuilder;
import com.commonsensenet.realfarm.utils.SoundQueue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Mirko Raca <mirko.raca@epfl.ch>
 *
 */
public class Homescreen extends HelpEnabledActivity implements OnClickListener {
	private String logTag = "Homescreen";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.india_homescreen);

        Log.i(logTag, "App started");
        // setup listener to all buttons
        PathBuilder.init(this);
        initDb();
        initActionListener();
        initTiles();
        initSoundSys();
        checkSdCard();
        setHelpIcon(findViewById(R.id.helpIndicator));
    }
    
    protected void checkSdCard(){
    	
    	String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
        	Log.e(logTag, "SD card not present");
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("SD card not present. Application will terminate.")
        	       .setCancelable(false)
        	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                Homescreen.this.finish();
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();
        }
    }
    
    protected void initSoundSys(){
    	Log.i(logTag, "Init sound sys");
    	SoundQueue.getInstance(this); // dummy call
    }
    
    protected void initDb(){
		Log.i(logTag, "Resetting database");
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);
    }

	protected void initTiles() {
		Log.i(logTag, "Initializing tiles");
		infoFill_setDiaryNumber();
		infoFill_setVideoNumber();
		infoFill_setAdviceNumber();
		infoFill_setYieldNumber();
		infoFill_setMarket();
		infoFill_setWeather();
	}
	
	protected void infoFill_setDiaryNumber(){
		TextView lbl = (TextView) findViewById(R.id.hmscrn_lbl_diary_number);
		Random rn = new Random();
		lbl.setText( Integer.toString(rn.nextInt(15)) );
	}
	
	protected void infoFill_setVideoNumber(){
		TextView lbl = (TextView) findViewById(R.id.hmscrn_lbl_video_number);
		Random rn = new Random();
		lbl.setText( Integer.toString(rn.nextInt(15)) );
	}
	
	protected void infoFill_setAdviceNumber(){
		TextView lbl = (TextView) findViewById(R.id.hmscrn_lbl_advice_number);
		Random rn = new Random();
		lbl.setText( Integer.toString(rn.nextInt(15)) );
	}

	protected void infoFill_setYieldNumber(){
		TextView lbl = (TextView) findViewById(R.id.hmscrn_lbl_yield_number);
		Random rn = new Random();
		lbl.setText( Integer.toString(rn.nextInt(15)*1000) );
	}
	
	protected void infoFill_setMarket(){
		TextView lblNum = (TextView) findViewById(R.id.hmscrn_lbl_market_number);
//		TextView lblUnit = (TextView) findViewById(R.id.hmscrn_lbl_market_unit); // to be used if needed
		Random rn = new Random();
		lblNum.setText( Integer.toString(rn.nextInt(15)*1000) );
	}
	
	protected void infoFill_setWeather(){
		TextView lbl = (TextView) findViewById(R.id.hmscrn_lbl_weather);
		ImageView imgWeather = (ImageView) findViewById(R.id.hmscrn_img_weather);
		Random rn = new Random();
		lbl.setText( Integer.toString(10+rn.nextInt(30)) + "°C" );
		
		switch(rn.nextInt(7)){
			case 0:
				imgWeather.setImageResource(R.drawable.ic_64px_chance_of_rain);
				break;
			case 1:
				imgWeather.setImageResource(R.drawable.ic_64px_cloudy);
				break;
			case 2:
				imgWeather.setImageResource(R.drawable.ic_64px_fog);
				break;
			case 3:
				imgWeather.setImageResource(R.drawable.ic_64px_mostly_cloudy);
				break;
			case 4:
				imgWeather.setImageResource(R.drawable.ic_64px_mostly_sunny);
				break;
			case 5:
				imgWeather.setImageResource(R.drawable.ic_64px_rain);
				break;
			case 6:
				imgWeather.setImageResource(R.drawable.ic_64px_sunny);
				break;
		}
	}
	
	private void initActionListener() {
		((ImageButton) findViewById(R.id.hmscrn_usr_icon)).setOnClickListener(this);
	    ((ImageButton) findViewById(R.id.hmscrn_usr_icon)).setOnLongClickListener(this);
	    ((ImageButton) findViewById(R.id.hmscrn_usr_icon)).setOnTouchListener(this);
      
        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_plots)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.hmscrn_help_button)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.hmscrn_help_button)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.hmscrn_help_button)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.hmscrn_btn_sound)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.hmscrn_btn_sound)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.hmscrn_btn_sound)).setOnTouchListener(this);
        
        // Actions
        ((Button) findViewById(R.id.hmscrn_btn_action_plant)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_plant)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_plant)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.hmscrn_btn_action_fertilize)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_fertilize)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_fertilize)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_action_irrigate)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_irrigate)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_irrigate)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_action_report)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_report)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_report)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_action_spray)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_spray)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_spray)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.hmscrn_btn_action_yield)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_yield)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_yield)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_action_sell)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_sell)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_action_sell)).setOnTouchListener(this);
        
        // bottom columns
        ((Button) findViewById(R.id.hmscrn_btn_notifs)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_notifs)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_notifs)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_warnings)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_warnings)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_warnings)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_weather)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_weather)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_weather)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_advice)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_advice)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_advice)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_video)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_video)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_video)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_yield)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_yield)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_yield)).setOnTouchListener(this);

        ((Button) findViewById(R.id.hmscrn_btn_market)).setOnClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_market)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.hmscrn_btn_market)).setOnTouchListener(this);

	}
    
	@Override
    protected void initKannada(){
    	Log.i(logTag, "Init kannada");
    	
    	TextView tmpText = (TextView) findViewById(R.id.hmscrn_lbl_username);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_used));

    	tmpText = (TextView) findViewById(R.id.hmscrn_lbl_plots);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_my_field));

    	tmpText = (TextView) findViewById(R.id.hmscrn_lbl_diary);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_diary));

	}

	@Override
	public void onBackPressed() {
		Homescreen.this.finish();
		
		// Production code, commented out for quicker development
//		new AlertDialog.Builder(this)
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.setTitle(R.string.exitTitle)
//				.setMessage(R.string.exitMsg)
//				.setNegativeButton(android.R.string.cancel, null)
//				.setPositiveButton(android.R.string.ok,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// Exit the activity
//								Homescreen.this.finish();
//							}
//						}).show();
	}

	public void onClick(View v) {
		Log.i(logTag, "Button clicked!");
		String txt = "";
		Intent inte;
		if( v.getId() == R.id.hmscrn_btn_advice ){
			Log.d(logTag, "Starting actions info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "actions");
			this.startActivity(inte);
			return;
		}
		if( v.getId() == R.id.hmscrn_btn_yield){
			Log.d(logTag, "Starting yield info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		if( v.getId() == R.id.hmscrn_btn_warnings ){
			Log.d(logTag, "Starting warn info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "warn");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.hmscrn_btn_weather ){
			Log.d(logTag, "Starting weather info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.hmscrn_btn_video ){
			Log.d(logTag, "Starting tutorials info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.hmscrn_btn_diary ){
			Log.d(logTag, "Starting diary info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.hmscrn_btn_market ){
			Log.d(logTag, "Starting market info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}

		
		// other buttons
//		case R.id.btn_action_diary:
//		case R.id.btn_action_fertilize:
//		case R.id.btn_action_irrigate:
//		case R.id.btn_action_plant:
//		case R.id.btn_action_problem:
//		case R.id.btn_action_spray:
//		case R.id.btn_action_yield:
		
		this.startActivity(new Intent(this, OfflineMapDemo.class));

		if( txt != "" )
			Toast.makeText(this.getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
	}
	
	public enum InfoType { ADVICE, ACTIONS, WARN, YIELD }

}
