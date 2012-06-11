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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
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
        super.onCreate(savedInstanceState, R.layout.metro_homescreen);

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
		// TODO: Query warnings
		LinearLayout layWarning = (LinearLayout) findViewById(R.id.home_lay_advice);
		LinearLayout layActions = (LinearLayout) findViewById(R.id.home_lay_actions);
	
//		populateTiles( InfoType.ADVICE, layAdvice );
//		populateTiles( InfoType.ACTIONS, layActions );
//		populateTiles( InfoType.WARN, layWarn );
//		populateTiles( InfoType.YIELD, layYield );		
	}
	
	protected void populateTiles( InfoType infoType, LinearLayout layout ){
		Vector<Recommendation> info = new Vector<Recommendation>();
		
		/* dummy implementation */
		DummyHomescreenData dummyData = new DummyHomescreenData(this, this, 5);
		Random rn = new Random();
		dummyData.generateDummyItems(rn.nextInt(5), info);
		RealFarmProvider dataProv = dummyData.getDataProvider();
		/* end dummy impl */
		
		Iterator<Recommendation> iter = info.iterator();
		while( iter.hasNext() ){
			Recommendation tmpRec = iter.next();
			ImageView tmpView = new ImageView(this);
			tmpView.setImageResource(dataProv.getActionNameById(tmpRec.getAction()).getRes());
			tmpView.setBackgroundResource(R.drawable.circular_icon_bg);
			layout.addView(tmpView);
			tmpView.getLayoutParams().height = 45;
			tmpView.getLayoutParams().width = 45;
		}
		info.clear();
	}

	private void initActionListener() {
		((Button) findViewById(R.id.home_btn_warnings)).setOnClickListener(this);
	    ((Button) findViewById(R.id.home_btn_warnings)).setOnLongClickListener(this);
	    ((Button) findViewById(R.id.home_btn_warnings)).setOnTouchListener(this);
      

        ((Button) findViewById(R.id.home_btn_actions)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_yields)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_yields)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_yields)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.home_btn_weather)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_weather)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_weather)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.home_btn_tutorials)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_tutorials)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_tutorials)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_homeactions)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_homeactions)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_homeactions)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_diary)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_diary)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_diary)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_market)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_market)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_market)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.home_btn_help)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.home_btn_help)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.home_btn_help)).setOnTouchListener(this);
	}
    
	@Override
    protected void initKannada(){
    	Log.i(logTag, "Init kannada");
    	TextView tmpText = (TextView) findViewById(R.id.homescreen_lbl_warn);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_reporting_of_problems));

    	tmpText = (TextView) findViewById(R.id.homescreen_lbl_action);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_news));

    	tmpText = (TextView) findViewById(R.id.homescreen_lbl_yields);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_harvest));

    	tmpText = (TextView) findViewById(R.id.hmscr_lbl_weather);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_wild_boar));

    	tmpText = (TextView) findViewById(R.id.hmscr_lbl_weather_today);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_today));
    	
    	tmpText = (TextView) findViewById(R.id.hmscr_lbl_weather_tomorrow);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_days));
    	
    	tmpText = (TextView) findViewById(R.id.hmscrn_lbl_tuts);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_news));

    	tmpText = (TextView) findViewById(R.id.hmscrn_lbl_diary);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_diary));

    	tmpText = (TextView) findViewById(R.id.hmscrn_lbl_market);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_harvest));
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
		if( v.getId() == R.id.home_btn_actions ){
			Log.d(logTag, "Starting actions info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "actions");
			this.startActivity(inte);
			return;
		}
		if( v.getId() == R.id.home_btn_yields){
			Log.d(logTag, "Starting yield info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		if( v.getId() == R.id.home_btn_warnings ){
			Log.d(logTag, "Starting warn info");
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "warn");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.home_btn_weather ){
			Log.d(logTag, "Starting weather info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.home_btn_tutorials ){
			Log.d(logTag, "Starting tutorials info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.home_btn_homeactions ){
			Log.d(logTag, "Starting weather info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.home_btn_diary ){
			Log.d(logTag, "Starting diary info");
			// TODO: change!
			inte = new Intent(this, AggregateView.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			return;
		}
		
		if( v.getId() == R.id.home_btn_market ){
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
	
	public enum InfoType { ADVICE, ACTIONS, WARN, YIELD };
}
