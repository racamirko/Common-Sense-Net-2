package com.commonsensenet.realfarm.homescreen;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.commonsensenet.realfarm.OfflineMapDemo;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DummyHomescreenData;
import com.commonsensenet.realfarm.homescreen.aggregateview.AggregateView;
import com.commonsensenet.realfarm.model.Recommendation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
        super.onCreate(savedInstanceState, R.layout.homescreen);

        Log.i(logTag, "App started");
        // setup listener to all buttons
        initActionListener();
        initTiles();
        setHelpIcon(findViewById(R.id.helpIndicator));
    }

	protected void initTiles() {
		Log.i(logTag, "Initializing tiles");
		LinearLayout layAdvice = (LinearLayout) findViewById(R.id.home_lay_advice);
		LinearLayout layActions = (LinearLayout) findViewById(R.id.home_lay_actions);
		LinearLayout layWarn = (LinearLayout) findViewById(R.id.home_lay_warn);
		LinearLayout layYield = (LinearLayout) findViewById(R.id.home_lay_yield);
	
		populateTiles( InfoType.ADVICE, layAdvice );
		populateTiles( InfoType.ACTIONS, layActions );
		populateTiles( InfoType.WARN, layWarn );
		populateTiles( InfoType.YIELD, layYield );		
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
		((Button) findViewById(R.id.home_btn_advice)).setOnClickListener(this);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnLongClickListener(this);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnTouchListener(this);
      

        ((Button) findViewById(R.id.home_btn_actions)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_warn)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_warn)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_warn)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.home_btn_yield)).setOnClickListener(this);
        ((Button) findViewById(R.id.home_btn_yield)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_yield)).setOnTouchListener(this);
        
        
        
        
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnTouchListener(this);
	}
    
	@Override
    protected void initKannada(){
    	Log.i(logTag, "Init kannada");
    	TextView tmpText = (TextView) findViewById(R.id.home_lbl_actions);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_solved));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_advice);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_news));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_warnings);
    	tmpText.setTypeface(mKannadaTypeface);
    	tmpText.setText(getString(R.string.k_farmers));
    	
    	tmpText = (TextView) findViewById(R.id.home_lbl_yield);
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
		switch( v.getId() ){
			case R.id.btn_info_actions:
			case R.id.home_btn_actions:
				Log.d(logTag, "Starting actions info");
				inte = new Intent(this, AggregateView.class);
				inte.putExtra("type", "actions");
				this.startActivity(inte);
				break;
			case R.id.btn_info_advice:
			case R.id.home_btn_advice:
				Log.d(logTag, "Starting advice info");
				inte = new Intent(this, AggregateView.class);
				inte.putExtra("type", "advice");
				this.startActivity(inte);
				break;
			case R.id.btn_info_warn:
			case R.id.home_btn_warn:
				Log.d(logTag, "Starting warn info");
				inte = new Intent(this, AggregateView.class);
				inte.putExtra("type", "warn");
				this.startActivity(inte);
				break;
			case R.id.btn_info_yield:
			case R.id.home_btn_yield:
				Log.d(logTag, "Starting yield info");
				inte = new Intent(this, AggregateView.class);
				inte.putExtra("type", "yield");
				this.startActivity(inte);
				break;
			case R.id.btn_action_diary:
			case R.id.btn_action_fertilize:
			case R.id.btn_action_irrigate:
			case R.id.btn_action_plant:
			case R.id.btn_action_problem:
			case R.id.btn_action_spray:
			case R.id.btn_action_yield:
				this.startActivity(new Intent(this, OfflineMapDemo.class));
				break;
		}

		if( txt != "" )
			Toast.makeText(this.getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
	}
	
	public enum InfoType { ADVICE, ACTIONS, WARN, YIELD };
}
