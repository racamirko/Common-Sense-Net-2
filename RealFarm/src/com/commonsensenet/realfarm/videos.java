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

import com.commonsensenet.realfarm.PlayVideoResFolder;


public class videos extends HelpEnabledActivity{
View view;
/** View where the items are displayed. */

protected RealFarmProvider mDataProvider;

//MediaPlayer mp = null;
private ListView mainListView ;  
private ArrayAdapter<String> listAdapter ; 
Cursor cc;
String	log;
public User ReadUser=null;
public int Position;                           //Has copy of mainlistview position




final Context context = this;
String name;
private Intent i_resfolder;
 public void onBackPressed() {
	
							
	 Intent adminintent123 = new Intent(videos.this, Homescreen.class);
		startActivity(adminintent123);        
		videos.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos);
        System.out.println("In My_setting_plot_info call");
        
   
        
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        ImageButton home;
        ImageButton help;
    
    	Button video1 = (Button) findViewById(R.id.video1);
    	Button video2 = (Button) findViewById(R.id.video2);
    	
    	 home = (ImageButton) findViewById(R.id.aggr_img_home);
    	    help = (ImageButton) findViewById(R.id.aggr_img_help);
    	    
    	i_resfolder = new Intent(this,PlayVideoResFolder.class);
    	
    	
     		
     		
     	

     		// add the event listeners
    	video1.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				System.out.println("In add user");
     				Global.videosel=1;
     				startActivity(i_resfolder);
     				videos.this.finish();
     				
     			}
     		});
     		
     	  
     	// add the event listeners
    	video2.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				System.out.println("In add user");
     				Global.videosel=2;
     				startActivity(i_resfolder);
     				videos.this.finish();
     				
     			}
     		});
    	
    	home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(videos.this, Homescreen.class);
				startActivity(adminintent123);        
				videos.this.finish();
				
				                    
				}
 	});
    	
    
     						
	}		                                                                           //End of oncreate()		

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public boolean onLongClick(View v) {
	

       
       if( v.getId() ==R.id.aggr_img_help ){
			
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
	
	

