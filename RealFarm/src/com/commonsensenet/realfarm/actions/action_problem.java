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
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class action_problem extends HelpEnabledActivity  {                  //integration
	//MediaPlayer mp = null;
	protected RealFarmProvider mDataProvider;
	final action_problem parentReference = this;                 //audio integration
	
	String prob_var_sel ="0", prob_day_sel;
	
	
	private Context context=this;
	 public void onBackPressed() {
			
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			Intent adminintent = new Intent(action_problem.this,Homescreen.class);
			        
			      startActivity(adminintent);                        
			      action_problem.this.finish();
			      
			      SoundQueue sq = SoundQueue.getInstance();    //audio integration
					sq.stop(); 
}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Plant details entered");
    	mDataProvider = RealFarmProvider.getInstance(context);
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.problem_dialog);
    	    	System.out.println("problem done");
    	    	final TextView day_fert = (TextView) findViewById(R.id.dlg_lbl_day_prob);
    	    	day_fert.setText("Today");
    	    	prob_day_sel="Today";
    	
    	    	 if(Global.EnableAudio==true)                        //checking for audio enable
    			 {
    	    	if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.clickingfertilising);
				mp.start();
    			 }
    	    	
    	    	 final ImageView bg_type_problem = (ImageView) findViewById(R.id.img_bg_type_prob);
    	    	 final ImageView bg_date_problem = (ImageView) findViewById(R.id.img_bg_date_prob);
    	     	bg_date_problem.setImageResource(R.drawable.empty_not);
    	    	 
    final Button item1;
   
    ImageButton home;
    ImageButton help;
    item1 = (Button) findViewById(R.id.home_btn_var_prob);
    final Button item2 = (Button) findViewById(R.id.home_btn_day_prob);
    
    home = (ImageButton) findViewById(R.id.aggr_img_home);
    help = (ImageButton) findViewById(R.id.aggr_img_help);
    
    
    item1.setOnLongClickListener(this);                             //Integration
   
    help.setOnLongClickListener(this); 

	item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 stopaudio();	
				Log.d("in variety sowing dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
		    	dlg.setContentView(R.layout.prob_spraying_dialog);
		    	dlg.setCancelable(true);
		        dlg.setTitle("Choose the Variety of seed sowed");
		    	Log.d("in variety sowing dialog", "in dialog");
		    	dlg.show();

		    	final Button fert1;
		    	final Button fert2;
		    	final Button fert3;
	
	//	    	final Button variety7;
		    	final ImageView img_1;
		    	
		    	
		    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_var_prob);
		    	fert1 = (Button) dlg.findViewById(R.id.home_prob_spray_1);
		    	fert2 = (Button) dlg.findViewById(R.id.home_prob_spray_2);
		    	fert3 = (Button) dlg.findViewById(R.id.home_prob_spray_3);
		    	
		    	 ((Button) dlg.findViewById(R.id.home_prob_spray_1)).setOnLongClickListener(parentReference);  //audio integration
	                ((Button) dlg.findViewById(R.id.home_prob_spray_2)).setOnLongClickListener(parentReference);
	                ((Button) dlg.findViewById(R.id.home_prob_spray_3)).setOnLongClickListener(parentReference);		    	
		    	
		    	
		    	fert1.setOnClickListener(new View.OnClickListener() {
		    			public void onClick(View v) {
		    				Log.d("var 1 picked ", "in dialog");
		    				//img_1.setMaxWidth(300);
		    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
		    				var_text.setText("Problem 1");
		    				prob_var_sel = "Problem 1";
		    				 TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);
		 	    	      	
		 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		 	    	      	bg_type_problem.setImageResource(R.drawable.empty_not);
		    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
		    				dlg.cancel();                      
		    				}
		     	});
		     	
		    	fert2.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 2 picked ", "in dialog");   
	    				//img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
	    				var_text.setText("Problem 2");
	    				prob_var_sel = "Problem 2";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	 	    	      	bg_type_problem.setImageResource(R.drawable.empty_not);
	    				dlg.cancel();                      
	    				}
	     	});
		    	
		    	fert3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 3 picked ", "in dialog");  
	    			//	img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
	    				var_text.setText("Problem 3");
	    				prob_var_sel = "Problem 3";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	 	    	      	bg_type_problem.setImageResource(R.drawable.empty_not);
	    				dlg.cancel();                      
	    				}
	     	});
		    	
		    	
			
			}
		});
 
	item2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			Log.d("in day sowing dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.days_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the day");
	    	Log.d("in day sowing dialog", "in dialog");
	    	dlg.show();

	    	final Button day1;
	    	final Button day2;
	    	final Button day3;
	    	final Button day4;
	    	final Button day5;
	    		    	
	    	
	    	
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
	    				day_fert.setText("Two week before");
	    				prob_day_sel="Two week before";
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	day2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    			//	img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				day_fert.setText("One week before");
    				prob_day_sel="One week before";
    				dlg.cancel();                      
    				}
     	});
	    	
	    	day3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_fert.setText("Yesterday");
    				prob_day_sel="Yesterday";
    				dlg.cancel();                      
    				}
     	});
	    	day4.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_fert.setText("Today");
    				prob_day_sel="Today";
    				dlg.cancel();                      
    				}
     	});
	    	day5.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_fert.setText("Tomorrow");
    				prob_day_sel="Tomorrow";
    				dlg.cancel();                      
    				}
     	});
	    	
	    	
		
		}
	});
	
	
  
	
	   Button btnNext=(Button) findViewById(R.id.prob_ok);
	   Button cancel=(Button) findViewById(R.id.prob_cancel);
	   
	   btnNext.setOnLongClickListener(this);                           //Integration
	   cancel.setOnLongClickListener(this);
	   
	   cancel.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
              cancelaudio();
	    	}
	   
	    	});
	   
	    btnNext.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		
	    		
	    	//	 Toast.makeText(action_fertilizing.this, "User enetred " + fert_no_sel + "kgs", Toast.LENGTH_LONG).show();
	    		 int flag1, flag2,flag3;
	    		 if(prob_var_sel.toString().equalsIgnoreCase("0") )
	    		    {
	    			 flag1 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    			 
	    			 
	    		    }
	    		 else
	    		 {
	    			 flag1 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.var_prob_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		
	    		
	    		 
	    		 if(flag1 ==0) 
	    		    {
	    		    
	    			 System.out.println("Problem writing");
	    				mDataProvider.setProblem(prob_day_sel,prob_var_sel,0, 0);
	    			
	    				//mDataProvider.setProblem(String day,String probType, int sent, int admin);
	    				
	    				
	    				
	    				System.out.println("Problem reading");
	    				mDataProvider.getProblem();
	    			 
	    			 
	    		    	 Intent adminintent = new Intent(action_problem.this,Homescreen.class);
	    			        
	    			      startActivity(adminintent);                        
	    			      action_problem.this.finish();
	    			      okaudio();
	    		    	 
	    		    }
	    		 else
	    			 initmissingval();
	    		 
	    		 
	    	}
	    });
	    
	    home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_problem.this,Homescreen.class);
	        
	      startActivity(adminintent);                        
	      action_problem.this.finish();
				
				                    
				}
 	});
    
    
    
    
    }

	//@Override
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
	public boolean onLongClick(View v) {
	
		if( v.getId() == R.id.home_btn_var_prob){
			
			 if(Global.EnableAudio==true)                        //checking for audio enable
			 {
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.problems);
			mp.start();
			 }
			
		}
		
   
       
       if( v.getId() == R.id.home_btn_day_prob){
			
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
			
		}
       
       if( v.getId() == R.id.prob_ok){
			
    	   
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
       
       if( v.getId() == R.id.prob_cancel){
		
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
			
		}
       
       
       if( v.getId() == R.id.home_prob_spray_1){                      //audio integration
      		
      		playAudio(R.raw.problem1);
      		
      	}
         
         if( v.getId() == R.id.home_prob_spray_2){                      //added
     		
     		playAudio(R.raw.problem2);
     		
     	}
         
         if( v.getId() == R.id.home_prob_spray_3){                      //added
     		
     		playAudio(R.raw.problem3);
     		
     	}
         
         if( v.getId() == R.id.home_day_1){                      //added
        		
        		playAudio(R.raw.twoweeksbefore);
        		
        	}
         
         if( v.getId() == R.id.home_day_2){                      //added
        		
        		playAudio(R.raw.oneweekbefore);
        		
        	}
         
         if( v.getId() == R.id.home_day_3){                      //added
        		
        		playAudio(R.raw.yesterday);
        		
        	}
         
         if( v.getId() == R.id.home_day_4){                      //added
        		
        		playAudio(R.raw.today);
        		
        	}
         
         if( v.getId() == R.id.home_day_5){                      //added
        		
        		playAudio(R.raw.tomorrows);
        		
        	}
          
		
	

		return true;
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
		Intent adminintent = new Intent(action_problem.this,Homescreen.class);
        
	      startActivity(adminintent);                        
	      action_problem.this.finish();
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
	
   
	public void playAudio(int resid)                         //audio integration
    {
	 if(Global.EnableAudio==true)                        //checking for audio enable
	 {
	// System.out.println("play audio called");
    SoundQueue sq = SoundQueue.getInstance();
	// stops any sound that could be playing.
	sq.stop();
	
	sq.addToQueue(resid);
	//sq.addToQueue(R.raw.treatmenttoseeds3);
	sq.play();
	 }

    	
    } 
    
    
    
    
    
}