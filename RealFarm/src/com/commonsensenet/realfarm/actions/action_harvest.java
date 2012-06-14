package com.commonsensenet.realfarm.actions;

import java.text.DateFormat.Field;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.Settings;
import com.commonsensenet.realfarm.WF_details;
import com.commonsensenet.realfarm.admincall;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class action_harvest extends HelpEnabledActivity {      // Integration
	   int feedback_sel; int harvest_no;
	   String harvest_no_sel, units_harvest="0",  strDateTime="0", feedback_txt;
	   protected RealFarmProvider mDataProvider;
	   private Context context=this;
	//   MediaPlayer mp = null;                                      //integration
	 public void onBackPressed() {
			
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
		 if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has clicked back button in harvest*********** \r\n");
		
			}
			Intent adminintent = new Intent(action_harvest.this,Homescreen.class);
			        
			      startActivity(adminintent);                        
			      action_harvest.this.finish();
}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Plant details entered");
    	mDataProvider = RealFarmProvider.getInstance(context);
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.harvest_dialog);
    	    	System.out.println("plant done");
    	    	if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.clickingharvest);
				mp.start();
				
				if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** In Action harvest*********** \r\n");
			
				}
				
    final Button smiley1;
    final Button smiley2;
    final Button smiley3;
    final Button item1;
    final  Button item2;
    final  Button item3;
    ImageButton home;
    ImageButton help;
    
    item1 = (Button) findViewById(R.id.home_btn_units_no_harvest);  
    item2 = (Button) findViewById(R.id.home_btn_units_harvest);
    item3 = (Button) findViewById(R.id.home_btn_harvest_date);
    
    smiley1 = (Button) findViewById(R.id.home_btn_har_1);
    smiley2 = (Button) findViewById(R.id.home_btn_har_2);
    smiley3 = (Button) findViewById(R.id.home_btn_har_3);
    
    home = (ImageButton) findViewById(R.id.aggr_img_home);
    help = (ImageButton) findViewById(R.id.aggr_img_help);
    item1.setOnLongClickListener(this);                                     
    item2.setOnLongClickListener(this);
    item3.setOnLongClickListener(this);
  
 
 
    
    smiley1.setOnLongClickListener(this);                                       //Integration
    smiley2.setOnLongClickListener(this);
    smiley3.setOnLongClickListener(this);
  
    help.setOnLongClickListener(this); 
   
    
    smiley1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 stopaudio();	
				feedback_sel=1;
				 feedback_txt="good";
				smiley1.setBackgroundResource(R.drawable.smiley_good);
				smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
				smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
		    	 TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
		        	
		        	tr_feedback.setBackgroundResource(R.drawable.def_img);
		        	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected feeback good*********** \r\n");
				
					}
			}
		});
    
    
    smiley2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			  feedback_sel=2;
			  feedback_txt="medium";
			smiley1.setBackgroundResource(R.drawable.smiley_good_not);
			smiley2.setBackgroundResource(R.drawable.smiley_medium);
			smiley3.setBackgroundResource(R.drawable.smiley_bad_not);
	    	 TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
	        	
	        	tr_feedback.setBackgroundResource(R.drawable.def_img);
	        	if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** user selected feeback medium*********** \r\n");
			
				}
		}
	});
    
    smiley3.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			 feedback_sel=3;
			 feedback_txt="bad";
			smiley1.setBackgroundResource(R.drawable.smiley_good_not);
			smiley2.setBackgroundResource(R.drawable.smiley_medium_not);
			smiley3.setBackgroundResource(R.drawable.smiley_bad);
	    	 TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
	        	
	        	tr_feedback.setBackgroundResource(R.drawable.def_img);
	        	if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** user selected feeback bad*********** \r\n");
			
				}
		}
	});
    
    
    
    final TextView no_text_1 = (TextView) findViewById(R.id.dlg_lbl_unit_no_harvest); 
	   
	item1.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			Log.d("in variety sowing dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.numberentry_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the Number of bags");
	    	Log.d("in variety sowing dialog", "in dialog");
	    	dlg.show();
	    	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** in selection of no of bags in harvest *********** \r\n");
		
			}
	    	  Button no_ok=(Button) dlg.findViewById(R.id.number_ok);
	   	   Button no_cancel=(Button) dlg.findViewById(R.id.number_cancel);
	   	no_ok.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		
		    		  NumberPicker mynp1 = (NumberPicker) dlg.findViewById(R.id.numberpick);
		    		    harvest_no = mynp1.getValue();
		    		    harvest_no_sel= String.valueOf(harvest_no);
		    		    no_text_1.setText(harvest_no_sel);

		    		    if(harvest_no !=0)
		    		    {
		    		    	 
		    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);
		  	    	      	
		  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		  	    	      if(Global.WriteToSD==true)
							{
								
							String	logtime=getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
							mDataProvider.File_Log_Create("UIlog.txt","***** user selected "+harvest_no_sel+ "of bags in harvest*********** \r\n");
						
							}
		    		    }
		    		    
		    		    dlg.cancel(); 
		    	}
	   	 });
	   	no_cancel.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		dlg.cancel(); 	
	    		if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** user selected cancel in no of bags in harvest*********** \r\n");
			
				}
	    	}
	    	});
	   	
	   
	   	   
		}
	});
	
	
	item2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			Log.d("in units fert dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.units_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the units");
	    	Log.d("in units fert dialog", "in dialog");
	    	dlg.show();

	    	final Button unit1;
	    	final Button unit2;
	    	final Button unit3;
	    	
	    	final ImageView img_1;
	    	img_1 = (ImageView) findViewById(R.id.dlg_unit_sow);
	    	
	    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_harvest); 
	    	unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
	    	unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
	    	unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);
	    	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** In selection of units in harvest*********** \r\n");
		
			}
	    	
	    	unit1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				var_text.setText("Bag of 10 Kgs");
	    				units_harvest = "Bag of 10 Kgs";
	    				TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    	      	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected "+units_harvest+ " units in harvest*********** \r\n");
					
						}
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	unit2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    			//	img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				var_text.setText("Bag of 20 Kgs");
    				units_harvest = "Bag of 20 Kgs";
TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    	     	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected "+units_harvest+ " units in harvest*********** \r\n");
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
	    	unit3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				var_text.setText("Bag of 50 Kgs");
    				units_harvest = "Bag of 50 Kgs";
TableRow tr_feedback = (TableRow) findViewById(R.id.units_harvest_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    	     	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected "+units_harvest+ " units in harvest*********** \r\n");
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
		
		}
	});
	
	
	   final TextView no_text_2 = (TextView) findViewById(R.id.dlg_lbl_harvest_date); 
	   
		item3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 stopaudio();	
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
		    	dlg.setContentView(R.layout.dateenter_dialog);
		    	dlg.setCancelable(true);
		        dlg.setTitle("Choose the date when harvested");
		    	Log.d("in variety sowing dialog", "in dialog");
		    	dlg.show();
		    	
		    	
		  	  Button no_ok=(Button) dlg.findViewById(R.id.date_ok);
		   	   Button no_cancel=(Button) dlg.findViewById(R.id.date_cancel);
		   	no_ok.setOnClickListener(new View.OnClickListener() {
			    	public void onClick(View v) {
			    		   final   DatePicker dp = (DatePicker) dlg.findViewById(R.id.harvest_datePicker);
			    		strDateTime = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
			    		    no_text_2.setText(strDateTime);

			    		    
			    		    	 
			    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.harvest_date_tr);
			  	    	      	
			  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
			    		  
			  	    	   	if(Global.WriteToSD==true)
							{
								
							String	logtime=getcurrenttime();
							mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
							mDataProvider.File_Log_Create("UIlog.txt","***** user selected "+strDateTime+ " date in harvest*********** \r\n");
						
							}
			    		    dlg.cancel(); 
			    	}
		   	 });
		   	no_cancel.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		dlg.cancel(); 	

	  	    	   	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected cancel in date in harvest*********** \r\n");
				
					}
		    	}
		    	});
		    	
			}
			
		});
    
    
    
    
    // To obtain the date entered by the user
    
   Button btnNext=(Button) findViewById(R.id.harvest_ok);              
   Button cancel=(Button) findViewById(R.id.home_btn_wf_2);             //integration
   
   btnNext.setOnLongClickListener(this);
   cancel.setOnLongClickListener(this);
   
   cancel.setOnClickListener(new View.OnClickListener() {
   	public void onClick(View v) {
         cancelaudio();

   	   	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user selected cancel in harvest*********** \r\n");
		
			}
   	}
  
   	});
   
    btnNext.setOnClickListener(new View.OnClickListener() {

    	
    	
          public void onClick(View v) {
            
            int flag1, flag2,flag3;
  //          Toast.makeText(action_harvest.this, "User selected " + strDateTime + "Time", Toast.LENGTH_LONG).show(); //Generate a toast only if you want 
          //  finish();   // If you want to continue on that TimeDateActivity
             // If you want to go to new activity that code you can also write here
      
// to know which feedback user clicked
    
            String feedback= String.valueOf(feedback_sel);
  //          Toast.makeText(action_harvest.this, "User selected feedback  " + feedback, Toast.LENGTH_LONG).show(); 
            
    // to obtain the + - values
         	if(Global.WriteToSD==true)
    		{
    			
    		String	logtime=getcurrenttime();
    		mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
    		mDataProvider.File_Log_Create("UIlog.txt","***** user clciked ok  in harvest*********** \r\n");
    	
    		}

    if(feedback_sel ==0)
    {
    	 flag1 =1;
    	 // Toast.makeText(action_harvest.this, " Please enter the feedback", Toast.LENGTH_LONG).show();
    	  TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
      	
      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);

  	   	if(Global.WriteToSD==true)
		{
			
		String	logtime=getcurrenttime();
		mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
		mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT filled feedback in harvest*********** \r\n");
	
		}
    }
    else
    {
    	flag1 =0;
    	 TableRow tr_feedback = (TableRow) findViewById(R.id.tableRow_feedback);
       	
       	tr_feedback.setBackgroundResource(R.drawable.def_img);
    	
    }
    if(units_harvest.toString().equalsIgnoreCase("0") || harvest_no ==0)
    {
    	 flag2 =1;
    	//  Toast.makeText(action_harvest.this, " Please enter the kgs", Toast.LENGTH_LONG).show();
    	  
    	  TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);
         	
         	tr_units.setBackgroundResource(R.drawable.def_img_not);
         	if(Global.WriteToSD==true)
    		{
    			
    		String	logtime=getcurrenttime();
    		mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
    		mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT filled units in harvest*********** \r\n");
    	
    		}
    }
    else
    {	flag2 =0;
    TableRow tr_units = (TableRow) findViewById(R.id.units_harvest_tr);
 	
 	tr_units.setBackgroundResource(R.drawable.def_img);
    }
   
    if(strDateTime.toString().equalsIgnoreCase("0") )
    {
    	 flag3 =1;
    	//  Toast.makeText(action_harvest.this, " Please enter the kgs", Toast.LENGTH_LONG).show();
    	  
    	  TableRow tr_units = (TableRow) findViewById(R.id.harvest_date_tr);
         	
         	tr_units.setBackgroundResource(R.drawable.def_img_not);
         	if(Global.WriteToSD==true)
    		{
    			
    		String	logtime=getcurrenttime();
    		mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
    		mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT filled date in harvest*********** \r\n");
    	
    		}
    }
    else
    {	flag3 =0;
    TableRow tr_units = (TableRow) findViewById(R.id.harvest_date_tr);
 	
 	tr_units.setBackgroundResource(R.drawable.def_img);
    }
    
    
    
    if(flag1 ==0 && flag2 ==0  && flag3 ==0) 
    {
    	System.out.println("harvesting writing");
		mDataProvider.setHarvest(harvest_no, 0, units_harvest, strDateTime, feedback_txt,
				1, 0);

		System.out.println("harvesting reading");
		mDataProvider.getharvesting();
 
	 	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has filled all details in harvest*********** \r\n");
		
			}
    	 Intent adminintent = new Intent(action_harvest.this,Homescreen.class);
	        
	      startActivity(adminintent);                        
	      action_harvest.this.finish();
    	 okaudio();
    	 
    }
    else
    	initmissingval() ;
    
    
    
          }});
    
    home.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			Intent adminintent = new Intent(action_harvest.this,Homescreen.class);
	        
		      startActivity(adminintent);                        
		      action_harvest.this.finish();
		   	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has clicked on home btn  in harvest*********** \r\n");
		
			}
			                    
			}
	});
    }

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub
		
	}
	
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
	protected void cancelaudio() {
		// TODO Auto-generated method stub
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
		}
		mp = MediaPlayer.create(this, R.raw.cancel);
		mp.start();
		Intent adminintent = new Intent(action_harvest.this,Homescreen.class);
        
	      startActivity(adminintent);                        
	      action_harvest.this.finish();
	}
	protected void okaudio() {
		// TODO Auto-generated method stub
		if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.ok);
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
	public boolean onLongClick(View v) {                      //latest
	
		
		
    
       if( v.getId() == R.id.home_btn_har_1){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.feedbackgood);
			mp.start();
		 	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to feeback good audio in harvest*********** \r\n");
		
			}
		}
       
       if( v.getId() == R.id.home_btn_har_2){
			
  			if(mp != null)
  			{
  				mp.stop();
  				mp.release();
  				mp = null;
  			}
  			mp = MediaPlayer.create(this, R.raw.feedbackmoderate);
  			mp.start();
  			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to feeback medium audio in harvest*********** \r\n");
		
			}
  		}
       if( v.getId() == R.id.home_btn_har_3){
			
 			if(mp != null)
 			{
 				mp.stop();
 				mp.release();
 				mp = null;
 			}
 			mp = MediaPlayer.create(this, R.raw.feedbackbad);
 			mp.start();
 			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to feeback bad audio in harvest*********** \r\n");
		
			}
 			
 		}
       if( v.getId() == R.id.harvest_ok){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.ok);
			mp.start();
			
		}
       if( v.getId() == R.id.home_btn_wf_2){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.cancel);
			mp.start();
			
		}
       if( v.getId() ==R.id.aggr_img_help ){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to help audio in harvest*********** \r\n");
		
			}
		}
      
       if( v.getId() == R.id.home_btn_units_no_harvest || v.getId() == R.id.home_btn_units_harvest ){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selecttheunits);
			mp.start();
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to units audio in harvest*********** \r\n");
		
			}
		}
		
	

		return true;
	}
}