package com.commonsensenet.realfarm.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_spraying extends HelpEnabledActivity {//Integration
//	MediaPlayer mp = null;     
	 protected RealFarmProvider mDataProvider;
	   private Context context=this;
	   
	   final action_spraying parentReference = this;                   //audio integration
	   
	String prob_sel_spray="0", pest_sel_spray="0",day_sel_spray="0", unit_sel_spray="0"; 
	int spray_no;
	String spray_no_sel;
	 public void onBackPressed() {
			
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			Intent adminintent = new Intent(action_spraying.this,Homescreen.class);
			        
			      startActivity(adminintent);                        
			      action_spraying.this.finish();
			      if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user has clicked soft key BACK in Spraying page*********** \r\n");
				
					}
			      
			      SoundQueue sq = SoundQueue.getInstance();    //audio integration
					sq.stop(); 
}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Plant details entered");
     	mDataProvider = RealFarmProvider.getInstance(context);
    	Log.d("in spray dialog", "in dialog");
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.spraying_dialog);
    	      	final TextView day_spray = (TextView) findViewById(R.id.dlg_lbl_day_spray);
    	      	day_spray.setText("Today");
    	      	day_sel_spray="Today";
    	      	
    	      	 if(Global.EnableAudio==true)                        //checking for audio enable
    			 {
    	      	if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.clickingspraying);
				mp.start();
    			 }
				
				
				if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** In Action Spraying*********** \r\n");
			
				}
				 final ImageView bg_prob_spray = (ImageView) findViewById(R.id.img_bg_prob_spray);
				 final ImageView bg_pest_spray = (ImageView) findViewById(R.id.img_bg_pest_spray);
				 final ImageView bg_units_no_spray = (ImageView) findViewById(R.id.img_bg_units_no_spray);
				 final ImageView bg_units_spray = (ImageView) findViewById(R.id.img_bg_units_spray);
				 final ImageView bg_day_spray = (ImageView) findViewById(R.id.img_bg_day_spray);
				 
	    	     	bg_day_spray.setImageResource(R.drawable.empty_not);
				
				
    final Button item1;
    final Button item2;
    final Button item3;
    final Button item4;
    final Button item5;
    
    ImageButton home;
    ImageButton help;
    item1 = (Button) findViewById(R.id.home_btn_prob_spray);
    item2 = (Button) findViewById(R.id.home_btn_pest_spray);
    item3 = (Button) findViewById(R.id.home_btn_units_spray);
    item4 = (Button) findViewById(R.id.home_btn_day_spray);
    item5 = (Button) findViewById(R.id.home_btn_units_no_spray);
    
    home = (ImageButton) findViewById(R.id.aggr_img_home);
    help = (ImageButton) findViewById(R.id.aggr_img_help);
    
    item1.setOnLongClickListener(this);                                       //Integration
    item2.setOnLongClickListener(this);
    item3.setOnLongClickListener(this);
    item4.setOnLongClickListener(this);
    item5.setOnLongClickListener(this);
    help.setOnLongClickListener(this);
    
	item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopaudio();	
				Log.d("in problem spray dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
		    	dlg.setContentView(R.layout.prob_spraying_dialog);
		    	dlg.setCancelable(true);
		        dlg.setTitle("Choose the problem for spraying");
		    	Log.d("in problem spray dialog", "in dialog");
		    	dlg.show();
		    	if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** In selection of problem for Spraying*********** \r\n");
			
				}
		    	final Button prob1;
		    	final Button prob2;
		    	final Button prob3;
	
	//	    	final Button variety7;
		    	final ImageView img_1;
		    	img_1 = (ImageView) findViewById(R.id.dlg_var_sow);
		    	
		    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_prob_spray);
		    	prob1 = (Button) dlg.findViewById(R.id.home_prob_spray_1);
		    	prob2 = (Button) dlg.findViewById(R.id.home_prob_spray_2);
		    	prob3 = (Button) dlg.findViewById(R.id.home_prob_spray_3);
		    	
	   	 ((Button) dlg.findViewById(R.id.home_prob_spray_1)).setOnLongClickListener(parentReference);  //audio integration
          ((Button) dlg.findViewById(R.id.home_prob_spray_2)).setOnLongClickListener(parentReference);
         ((Button) dlg.findViewById(R.id.home_prob_spray_3)).setOnLongClickListener(parentReference);
		    	
		    	
		    	prob1.setOnClickListener(new View.OnClickListener() {
		    			public void onClick(View v) {
		    				Log.d("var 1 picked ", "in dialog");
		    				//img_1.setMaxWidth(300);
		    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
		    				var_text.setText("Problem 1");
		    				prob_sel_spray= "Problem 1";
		    				 TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);
		 	    	      	
		 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		 	    	      	bg_prob_spray.setImageResource(R.drawable.empty_not);
		 	    	   	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ prob_sel_spray + " for Spraying*********** \r\n");
						
					
						}
		    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
		    				dlg.cancel();                      
		    				}
		     	});
		     	
		    	prob2.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 2 picked ", "in dialog");   
	    				//img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
	    				var_text.setText("Problem 2");
	    				prob_sel_spray= "Problem 2";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	 	    	      	bg_prob_spray.setImageResource(R.drawable.empty_not);

	 	    	    	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ prob_sel_spray + " for Spraying*********** \r\n");
						
					
						}
	    				dlg.cancel();                      
	    				}
	     	});
		    	
		    	prob3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 3 picked ", "in dialog");  
	    			//	img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
	    				var_text.setText("Problem 3");
	    				prob_sel_spray= "Problem 3";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	 	    	      	bg_prob_spray.setImageResource(R.drawable.empty_not);

	 	    	    	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ prob_sel_spray + " for Spraying*********** \r\n");
						
					
						}
	    				dlg.cancel();                      
	    				}
	     	});
		    	
		    	
			
			}
		});
    
	item2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			stopaudio();	
			Log.d("in pest spray dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.pest_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the Pesticide");
	    	Log.d("in units spray dialog", "in dialog");
	    	dlg.show();
	     	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
		
			mDataProvider.File_Log_Create("UIlog.txt","***** In selection of pesticide for Spraying*********** \r\n");
			
		
			}
		
	     	final Button pest1;
	    	final Button pest2;
	    	final Button pest3;

//	    	final Button variety7;
	    	final ImageView img_1;
	    	img_1 = (ImageView) findViewById(R.id.dlg_var_sow);
	    	
	    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_pest_spray);
	    	pest1 = (Button) dlg.findViewById(R.id.home_pest_spray_1);
	    	pest2 = (Button) dlg.findViewById(R.id.home_pest_spray_2);
	    	pest3 = (Button) dlg.findViewById(R.id.home_pest_spray_3);
	    	
	  	 ((Button) dlg.findViewById(R.id.home_pest_spray_1)).setOnLongClickListener(parentReference);  //audio integration
         ((Button) dlg.findViewById(R.id.home_pest_spray_2)).setOnLongClickListener(parentReference);
        ((Button) dlg.findViewById(R.id.home_pest_spray_3)).setOnLongClickListener(parentReference);
	    	
	    	
	    	
	    	
	    	pest1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				var_text.setText("Pesticide 1");
	    				pest_sel_spray= "Pesticide 1";
	    				  TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);
	  	    	      	
	  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	  	  	      	bg_pest_spray.setImageResource(R.drawable.empty_not);
	  	    	   	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ pest_sel_spray + " for Spraying*********** \r\n");
					
				
					}
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	pest2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    				//img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				var_text.setText("Pesticide 2");
    				pest_sel_spray= "Pesticide 2";
    				  TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);
  	    	      	
  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
  	    	     	bg_pest_spray.setImageResource(R.drawable.empty_not);
  	    		   	if(Global.WriteToSD==true)
  	    						{
  	    							
  	    						String	logtime=getcurrenttime();
  	    						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
  	    					
  	    						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ pest_sel_spray + " for Spraying*********** \r\n");
  	    						
  	    					
  	    						}
    				dlg.cancel();                      
    				}
     	});
	    	
	    	pest3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    			//	img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				var_text.setText("Pesticide 3");
    				pest_sel_spray= "Pesticide 3";
    				  TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);
  	    	      	
  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
  	    	     	bg_pest_spray.setImageResource(R.drawable.empty_not);
  	    		   	if(Global.WriteToSD==true)
  	    						{
  	    							
  	    						String	logtime=getcurrenttime();
  	    						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
  	    					
  	    						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ pest_sel_spray + " for Spraying*********** \r\n");
  	    						
  	    					
  	    						}
    				dlg.cancel();                      
    				}
     	});
	    	
		}
	});

	item3.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			stopaudio();	
			Log.d("in units fert dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.units_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the units");
	    	Log.d("in units fert dialog", "in dialog");
	    	dlg.show();

		   	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** In selection of units for Spraying*********** \r\n");
						
					
						}
	    	
	    	final Button unit1;
	    	final Button unit2;
	    	final Button unit3;
	    	
	    	final ImageView img_1;
	    	img_1 = (ImageView) findViewById(R.id.dlg_unit_sow);
	    	
	    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_spray);
	    	unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
	    	unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
	    	unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);
	    	
   	 ((Button) dlg.findViewById(R.id.home_btn_units_1)).setOnLongClickListener(parentReference);  //audio integrtion
     ((Button) dlg.findViewById(R.id.home_btn_units_2)).setOnLongClickListener(parentReference);
     ((Button) dlg.findViewById(R.id.home_btn_units_3)).setOnLongClickListener(parentReference);
	    	
	    	
	    	unit1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				var_text.setText("Bag of 10 Kgs");
	    				unit_sel_spray="Bag of 10 Kgs";
	    				  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
	  	    	      	
	  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	  	    	     	bg_units_spray.setImageResource(R.drawable.empty_not);
	  	    		   	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ unit_sel_spray + " for Spraying*********** \r\n");
						
					
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
    				unit_sel_spray="Bag of 20 Kgs";
    				  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
  	    	      	
  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
  	    	    	bg_units_spray.setImageResource(R.drawable.empty_not);
  	    	    	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ unit_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
	    	unit3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				var_text.setText("Bag of 50 Kgs");
    				unit_sel_spray="Bag of 50 Kgs";
    				  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
  	    	      	
  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
  	    	    	bg_units_spray.setImageResource(R.drawable.empty_not);
  	    	    	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ unit_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
		
		}
	});
	
	
	
	item4.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			stopaudio();	
			Log.d("in day spray dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.days_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the day");
	    	Log.d("in day spray dialog", "in dialog");
	    	dlg.show();
	      	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
		
			mDataProvider.File_Log_Create("UIlog.txt","***** In selection of DAY for Spraying*********** \r\n");
			
		
			}
	    	final Button day1;
	    	final Button day2;
	    	final Button day3;
	    	final Button day4;
	    	final Button day5;
	    		    	
	    	final ImageView img_1;
	    	img_1 = (ImageView) findViewById(R.id.dlg_unit_sow);
	    	
	   
	    	day1 = (Button) dlg.findViewById(R.id.home_day_1);
	    	day2 = (Button) dlg.findViewById(R.id.home_day_2);
	    	day3 = (Button) dlg.findViewById(R.id.home_day_3);
	    	day4 = (Button) dlg.findViewById(R.id.home_day_4);
	    	day5 = (Button) dlg.findViewById(R.id.home_day_5);
	    	
	    	 ((Button) dlg.findViewById(R.id.home_day_1)).setOnLongClickListener(parentReference);  //audio integration
             ((Button) dlg.findViewById(R.id.home_day_2)).setOnLongClickListener(parentReference);
              ((Button) dlg.findViewById(R.id.home_day_3)).setOnLongClickListener(parentReference);
              ((Button) dlg.findViewById(R.id.home_day_4)).setOnLongClickListener(parentReference);
              ((Button) dlg.findViewById(R.id.home_day_5)).setOnLongClickListener(parentReference);
	    	
	    	day1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				day_spray.setText("Two week before");
	    				day_sel_spray="Two week before";
	    			  	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ day_sel_spray + " for Spraying*********** \r\n");
						
					
						}
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	day2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    			//	img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				day_spray.setText("One week before");
    				day_sel_spray="One week before";
    			 	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ day_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
	    	day3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_spray.setText("Yesterday");
    				day_sel_spray="Yesterday";
    			 	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ day_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	day4.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_spray.setText("Today");
    				day_sel_spray="Today";
    			 	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ day_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	day5.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_spray.setText("Tomorrow");
    				day_sel_spray="Tomorrow";
    			 	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				
					mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ day_sel_spray + " for Spraying*********** \r\n");
					
				
					}
    				dlg.cancel();                      
    				}
     	});
	    	
	    	
	    	
		}
	});
   
	
	final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_spray); 
	   
	item5.setOnClickListener(new View.OnClickListener() {
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
		
			mDataProvider.File_Log_Create("UIlog.txt","***** In selection of no of bags for Spraying*********** \r\n");
			
		
			}
	    	  Button no_ok=(Button) dlg.findViewById(R.id.number_ok);
	   	   Button no_cancel=(Button) dlg.findViewById(R.id.number_cancel);
	   	no_ok.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		
		    		  NumberPicker mynp1 = (NumberPicker) dlg.findViewById(R.id.numberpick);
		    		    spray_no = mynp1.getValue();
		    		    spray_no_sel= String.valueOf(spray_no);
		    		    no_text.setText(spray_no_sel);
		    		    if(spray_no !=0)
		    		    {
		    		    	 
		    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
		  	    	      	
		  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		  	    	    	bg_units_no_spray.setImageResource(R.drawable.empty_not);
		  	    	   	if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					
						mDataProvider.File_Log_Create("UIlog.txt","***** user selected"+ spray_no_sel + " bags for Spraying*********** \r\n");
						
					
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
			
				mDataProvider.File_Log_Create("UIlog.txt","***** user selected camncel on entering number of bags for Spraying*********** \r\n");
				
			
				}
	    	}
	    	});
	   	
	   
	   	   
		}
	});
	
	
	
	
	
	  Button btnNext=(Button) findViewById(R.id.spray_ok);  
	  Button cancel=(Button) findViewById(R.id.spray_cancel);                    //Integration
	  
	  btnNext.setOnLongClickListener(this);
	  cancel.setOnLongClickListener(this);
	   
	  cancel.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
            cancelaudio();
        	if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** User clicked cancel on spraying page*********** \r\n");
		
			}
	    	}
	   
	    	});
	  
	  
	    btnNext.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** User clicked ok on fertilizer page*********** \r\n");
			
				}
	    		    		
	    	//	 Toast.makeText(action_spraying.this, "User enetred " + no_units_sprayed + " kgs", Toast.LENGTH_LONG).show();
	    		 
	    		 
	    		 
	    		 int flag1, flag2,flag3;
	    		 if(unit_sel_spray.toString().equalsIgnoreCase("0") || spray_no == 0)
	    		    {
	    			 flag1 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    	      	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT Filled units in spraying page*********** \r\n");
				
					}
	    			 
	    		    }
	    		 else
	    		 {
	    			 flag1 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		
	    		 if(pest_sel_spray.toString().equalsIgnoreCase("0") )
	    		    {
	    		    
	    			 flag2 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    	      	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT Filled pesticide in spraying page*********** \r\n");
				
					}
	    		    }
	    		 else
	    		 {
	    			 
	    			 flag2 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.pest_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		 
	    		 if(prob_sel_spray.toString().equalsIgnoreCase("0") )
	    		    {
	    		    
	    			 flag3 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    	      	if(Global.WriteToSD==true)
					{
						
					String	logtime=getcurrenttime();
					mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
					mDataProvider.File_Log_Create("UIlog.txt","***** user has NOT Filled problems in spraying page*********** \r\n");
				
					}
	    		    }
	    		 else
	    		 {
	    			 
	    			 flag3 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.prob_spray_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		 
	    		 
	    		 if(flag1 ==0 && flag2 ==0 && flag3 ==0) 
	    		    {
	    			 System.out.println("spraying writing");
	    				mDataProvider.setspraying(spray_no, unit_sel_spray, day_sel_spray, prob_sel_spray, 1, 0,
	    						pest_sel_spray);

	    				System.out.println("spraying reading");
	    				mDataProvider.getspraying();
	    					    	 
	    				if(Global.WriteToSD==true)
						{
							
						String	logtime=getcurrenttime();
						mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
						mDataProvider.File_Log_Create("UIlog.txt","***** user has  Filled all details in spraying page*********** \r\n");
					
						}
	    				
	    		    	 Intent adminintent = new Intent(action_spraying.this,Homescreen.class);
	    			        
	    			      startActivity(adminintent);                        
	    			      action_spraying.this.finish();
	    		    	 okaudio();
	    		    	 
	    		    }
	    		 else
	    			 initmissingval();
	    		 
	    		 
	    	}
	    });

	    home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if(Global.WriteToSD==true)
				{
					
				String	logtime=getcurrenttime();
				mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
				mDataProvider.File_Log_Create("UIlog.txt","***** user has clicke on home btn in spraying page*********** \r\n");
			
				}
				
				Intent adminintent = new Intent(action_spraying.this,Homescreen.class);
		        
			      startActivity(adminintent);                        
			      action_spraying.this.finish();
				
				                    
				}
 	});
	
	
	
	
    }

	@Override
	protected void initKannada() {
		// TODO Auto-generated method stub
		
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
		Intent adminintent = new Intent(action_spraying.this,Homescreen.class);
        
	      startActivity(adminintent);                        
	      action_spraying.this.finish();
	}
	protected void okaudio() {
		// TODO Auto-generated method stub
		 if(Global.EnableAudio==true)                        //checking for audio enable
		 {
		if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.ok);
			mp.start();
		 }
		
	    	 
		
	}
	
	protected void initmissingval() {
		// TODO Auto-generated method stub
		 if(Global.EnableAudio==true)                        //checking for audio enable
		 {
		if(mp != null)
		{
			mp.stop();
			mp.release();
			mp = null;
		}
		mp = MediaPlayer.create(this, R.raw.missinginfo);
		mp.start();
		 }
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
	
		if( v.getId() == R.id.home_btn_prob_spray){
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selecttheproblem);
			mp.start();
			 }
			
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to problem audio in spray*********** \r\n");
		
			}
		}
		
       if( v.getId() == R.id.home_btn_pest_spray){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selectthepesticide);
			mp.start();
			 }
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to pest audio in spray*********** \r\n");
		
			}
		}
       
       if( v.getId() == R.id.home_btn_units_spray || v.getId() == R.id.home_btn_units_no_spray){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selecttheunits);
			mp.start();
			 }
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to units audio in spray*********** \r\n");
		
			}
		}
       
       if( v.getId() == R.id.home_btn_day_spray){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
  			if(mp != null)
  			{
  				mp.stop();
  				mp.release();
  				mp = null;
  			}
  			mp = MediaPlayer.create(this, R.raw.selectthedate);
  			mp.start();
			 }
  			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to day audio in spray*********** \r\n");
		
			}
  		}
       if( v.getId() == R.id.spray_ok){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
    			if(mp != null)
    			{
    				mp.stop();
    				mp.release();
    				mp = null;
    			}
    			mp = MediaPlayer.create(this, R.raw.ok);
    			mp.start();
			 }
    			
    		}
  		
       if( v.getId() == R.id.spray_cancel){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
    			if(mp != null)
    			{
    				mp.stop();
    				mp.release();
    				mp = null;
    			}
    			mp = MediaPlayer.create(this, R.raw.cancel);
    			mp.start();
			 }
    			
    		}
       if( v.getId() ==R.id.aggr_img_help ){
			
    	   if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.help);
			mp.start();
			 }
			if(Global.WriteToSD==true)
			{
				
			String	logtime=getcurrenttime();
			mDataProvider.File_Log_Create("UIlog.txt",logtime+" -> ");
			mDataProvider.File_Log_Create("UIlog.txt","***** user has listened to help audio in spray*********** \r\n");
		
			}
		}
       
       if( v.getId() == R.id.home_prob_spray_1){                 //added audio integration
      		
      		playAudio(R.raw.problem1);
      		
      	}
         
         if( v.getId() == R.id.home_prob_spray_2){                 //added
     		
     		playAudio(R.raw.problem2);
     		
     	}
         
         if( v.getId() == R.id.home_prob_spray_3){                 //added
     		
     		playAudio(R.raw.problem3);
     		
     	}
         
         if( v.getId() == R.id.home_pest_spray_1){                 //added
        		
        		playAudio(R.raw.pesticide1);
        		
        	}
       		
         
         if( v.getId() == R.id.home_pest_spray_2){                 //added
        		
        		playAudio(R.raw.pesticide2);
        		
        	}
       		
         
         if( v.getId() == R.id.home_pest_spray_3){                 //added
        		
        		playAudio(R.raw.pesticide3);
        		
        	}
       		
         
         if( v.getId() == R.id.home_btn_units_1){                 //added
       		
       		playAudio(R.raw.bagof10kg);
       		
       	}
         
         if( v.getId() == R.id.home_btn_units_2){                 //added
       		
       		playAudio(R.raw.bagof20kg);
       		
       	}
         
         if( v.getId() == R.id.home_btn_units_3){                 //added
       		
       		playAudio(R.raw.bagof50kg);
       		
       	}
         
         if( v.getId() == R.id.home_day_1){                 //added
      		
      		playAudio(R.raw.twoweeksbefore);
      		
      	}
         
         if( v.getId() == R.id.home_day_2){                 //added
      		
      		playAudio(R.raw.oneweekbefore);
      		
      	}
         
         
         if( v.getId() == R.id.home_day_3){                 //added
      		
      		playAudio(R.raw.yesterday);
      		
      	}
         
         
         if( v.getId() == R.id.home_day_4){                 //added
      		
      		playAudio(R.raw.todayonly);
      		
      	}
         
         if( v.getId() == R.id.home_day_5){                 //added
      		
      		playAudio(R.raw.tomorrows);
      		
      	}
     		
         
  		
      
		
		
		
	

		return true;
	}
	
	 public void playAudio(int resid)                        //audio integration
	    {
		 if(Global.EnableAudio==true)                        //checking for audio enable
		 {
		 System.out.println("play audio called");
	    SoundQueue sq = SoundQueue.getInstance();
		// stops any sound that could be playing.
		sq.stop();
		
		sq.addToQueue(resid);
		//sq.addToQueue(R.raw.treatmenttoseeds3);
		sq.play();
		 }

	    	
	    }
    

    
    
    
    
    
    
    
    
    
    
    
}