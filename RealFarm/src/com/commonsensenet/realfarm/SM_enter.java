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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ArrayAdapter;  
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;




import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;


public class SM_enter extends HelpEnabledActivity{
View view;
/** View where the items are displayed. */

protected RealFarmProvider mDataProvider;


 
Cursor cc;
String	log;
public User ReadUser=null;
public int Position;                           //Has copy of mainlistview position




final Context context = this;
String name;

 public void onBackPressed() {
	
	 stopaudio() ;
	 Intent adminintent123 = new Intent(SM_enter.this, Plot_Image.class);
		startActivity(adminintent123);        
		SM_enter.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smenter_dialog);
        System.out.println("SM eneter");
        
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        
        ImageButton home1;
  	    ImageButton help1;
		home1 = (ImageButton) findViewById(R.id.aggr_img_home1);
   	    help1 = (ImageButton) findViewById(R.id.aggr_img_help1);
   	    help1.setOnLongClickListener(this);
		
		
   	 home1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent123 = new Intent(SM_enter.this, Homescreen.class);
				startActivity(adminintent123);        
				SM_enter.this.finish();
				
				                    
				}
	});	

 	   Button btnNext=(Button) findViewById(R.id.sm_enter_ok);  
 	   
 	    btnNext.setOnClickListener(new View.OnClickListener() {
 	    	public void onClick(View v) {
 	    		
 	    		  NumberPicker mynp1 = (NumberPicker)findViewById(R.id.sm_enter_val);
 	    		     int sm_enter_val = mynp1.getValue();
 	    		   String sm_enter_no= String.valueOf(sm_enter_val);
 	    		
 	    		 Toast.makeText(SM_enter.this, "User enetred " + sm_enter_no + " %", Toast.LENGTH_LONG).show();
 	    		 int flag1;
 	    		 if(sm_enter_val == 0 )
 	    		    {
 	    			 flag1 =1;
 	    	    	  
 	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);
 	    	      	
 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
 	    			 
 	    			 
 	    		    }
 	    		 else
 	    		 {
 	    			 flag1 =0;
 	    	    	  
 	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.sm_enter_tr);
 	    	      	
 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
 	    		 }
 	    		
 	    		 
 	    		 if(flag1 ==0) 
 	    	    {
 	    	    	
 	    	    	   		    	 
 	    	    	 Intent adminintent = new Intent(SM_enter.this,Homescreen.class);
 	    		        
 	    		      startActivity(adminintent);                        
 	    		      SM_enter.this.finish();
 	    	    	 
 	    	    	 
 	    	    }
 	    		 else
 	    			 initmissingval() ;
 	    		 
 	   	}
	    });
        
        
 	  
        
        
        
     						
	}		                                                                           //End of oncreate()		
     	
	
	//@Override
	protected void initmissingval() {
		// TODO Auto-generated method stub
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
		}
		mp = MediaPlayer.create(this, R.raw.missinginfo);
		mp.start();
	}
    
	protected void stopaudio() {
		// TODO Auto-generated method stub
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
		}
		
	}

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub
		
	}
	
	

@Override
public boolean onLongClick(View v) {

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


	
	

