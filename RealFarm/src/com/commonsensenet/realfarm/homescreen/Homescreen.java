package com.commonsensenet.realfarm.homescreen;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.DummyHomescreenData;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Recommendation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Homescreen extends HelpEnabledActivity {
	private String logTag = "Homescreen";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.homescreen);

        Log.i(logTag, "App started");
        // setup listener to all buttons
        HomeActivityListener clckListener = new HomeActivityListener(this);
        initActionListener(clckListener);
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

	private void initActionListener(HomeActivityListener clckListener) {
		((Button) findViewById(R.id.home_btn_advice)).setOnClickListener(clckListener);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnLongClickListener(this);
	    ((Button) findViewById(R.id.home_btn_advice)).setOnTouchListener(this);
      

        ((Button) findViewById(R.id.home_btn_actions)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_actions)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_actions)).setOnTouchListener(this);

        ((Button) findViewById(R.id.home_btn_warn)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_warn)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_warn)).setOnTouchListener(this);
        
        ((Button) findViewById(R.id.home_btn_yield)).setOnClickListener(clckListener);
        ((Button) findViewById(R.id.home_btn_yield)).setOnLongClickListener(this);
        ((Button) findViewById(R.id.home_btn_yield)).setOnTouchListener(this);
        
        
        
        
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_fertilize)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_diary)).setOnTouchListener(this);
        
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_irrigate)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_plant)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_problem)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnClickListener(clckListener);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnLongClickListener(this);
        ((ImageButton) findViewById(R.id.btn_action_spray)).setOnTouchListener(this);

        ((ImageButton) findViewById(R.id.btn_action_yield)).setOnClickListener(clckListener);
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

	
	public enum InfoType { ADVICE, ACTIONS, WARN, YIELD };
}
