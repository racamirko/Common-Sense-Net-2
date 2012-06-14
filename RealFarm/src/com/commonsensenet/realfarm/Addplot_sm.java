package com.commonsensenet.realfarm;
import java.util.List;
import java.util.ArrayList;  
import java.util.Arrays;  

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ArrayAdapter;  
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;




import com.commonsensenet.realfarm.actions.action_fertilizing;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;


public class Addplot_sm extends HelpEnabledActivity{
View view;
/** View where the items are displayed. */

protected RealFarmProvider mDataProvider;
//MediaPlayer mp = null;                                      //integration

private ListView mainListView ;  
private ArrayAdapter<String> listAdapter ; 
Cursor cc;
String	log;
public User ReadUser=null;
public int Position;                           //Has copy of mainlistview position




final Context context = this;
String name;

 public void onBackPressed() {
	 if(mp != null)
		{
			mp.stop();
		mp.release();
			mp = null;
		}
							
	 Intent adminintent123 = new Intent(Addplot_sm.this, Homescreen.class);
		startActivity(adminintent123);        
		Addplot_sm.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplot_sm);
        System.out.println("In My_setting_plot_info call");
        
   
        
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        
    
    	Button AddPlot = (Button) findViewById(R.id.AddPlot);
    	Button SMvalue = (Button) findViewById(R.id.SMvalue);
    	
    	AddPlot.setOnLongClickListener(this);                        //Integration
    	SMvalue.setOnLongClickListener(this);
    	
   	   ImageButton home;
    	    ImageButton help;
    	    
    	    home = (ImageButton) findViewById(R.id.aggr_img_home1);
    	    help = (ImageButton) findViewById(R.id.aggr_img_help1);
    	    help.setOnLongClickListener(this);
     	

     		// add the event listeners
     	  AddPlot.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				System.out.println("In add user");
     		
     				Global.CallToPlotInfo=1;
     				Intent adminintent123 = new Intent(Addplot_sm.this, My_setting_plot_info.class);
     				startActivity(adminintent123);
     				Addplot_sm.this.finish();
     				
     			}
     		});
     		
     	  
     	// add the event listeners
     	 SMvalue.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				System.out.println("In add user");
     		
     				Global.actionno=7;
     				Intent adminintent123 = new Intent(Addplot_sm.this,Plot_Image.class);
     				startActivity(adminintent123);
     				Addplot_sm.this.finish();
     				
     			}
     		});
    	
    		
     	 home.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {
 				Intent adminintent123 = new Intent(Addplot_sm.this, Homescreen.class);
 				startActivity(adminintent123);        
 				Addplot_sm.this.finish();
 				
 				                    
 				}
  	});		
    		
     		 
     						
	}		                                                                           //End of oncreate()		

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub
		
	}
     	
	
	@Override
	public boolean onLongClick(View v) {                      //latest
	
		if( v.getId() == R.id.AddPlot){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.buttonpressplotinfo);
			mp.start();
			
		}
		
       if( v.getId() == R.id.SMvalue){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.buttonpresssoilmoisturevalue);
			mp.start();
			
		}
       if( v.getId() ==R.id.aggr_img_help1 ){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();
			
		}
      
       
       
       
       
      
		
		
	

		return true;
	}
	

	
	
} 
	
	

