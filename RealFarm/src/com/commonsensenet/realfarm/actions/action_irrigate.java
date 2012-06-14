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

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.control.NumberPicker;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.homescreen.Homescreen;

public class action_irrigate extends HelpEnabledActivity  {                  //integration
	//MediaPlayer mp = null;
	protected RealFarmProvider mDataProvider;
	int hrs_irrigate=0;
	String hrs_irrigate_sel = "0", irr_method_sel ="0", irr_day_sel;
	 int fert_no;
	 String fert_no_sel;
	private Context context=this;
	 public void onBackPressed() {
			
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			Intent adminintent = new Intent(action_irrigate.this,Homescreen.class);
			        
			      startActivity(adminintent);                        
			      action_irrigate.this.finish();
}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Plant details entered");
    	mDataProvider = RealFarmProvider.getInstance(context);
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.irrigate_dialog);
    	    	System.out.println("plant done");
    	    	final TextView day_irr = (TextView) findViewById(R.id.dlg_lbl_day_irr);
    	    	day_irr.setText("Today");
    	    	irr_day_sel="Today";
    	    	if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.clickingfertilising);
				mp.start();
    	    	
    final Button item1;
    final Button item2;
    final Button item3;
    final Button item4;
    ImageButton home;
    ImageButton help;
    item1 = (Button) findViewById(R.id.home_btn_method_irr);
   
    item3 = (Button) findViewById(R.id.home_btn_day_irr);
    item2 = (Button) findViewById(R.id.home_btn_units_no_irr);
    
    home = (ImageButton) findViewById(R.id.aggr_img_home);
    help = (ImageButton) findViewById(R.id.aggr_img_help);
    
    
    item1.setOnLongClickListener(this);                             //Integration
 
    item2.setOnLongClickListener(this); 
    item3.setOnLongClickListener(this);
    help.setOnLongClickListener(this); 

	item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 stopaudio();	
				Log.d("in method irrigate dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
		    	dlg.setContentView(R.layout.method_irrigate_dialog);
		    	dlg.setCancelable(true);
		        dlg.setTitle("Choose the Variety of seed sowed");
		    	Log.d("in variety sowing dialog", "in dialog");
		    	dlg.show();

		    	final Button meth1;
		    	final Button meth2;
		    	final Button meth3;
	
	//	    	final Button variety7;
		    	final ImageView img_1;
		 //   	img_1 = (ImageView) findViewById(R.id.dlg_lbl_method_irr);
		    	
		    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_method_irr);
		    	meth1 = (Button) dlg.findViewById(R.id.home_var_fert_1);
		    	meth2 = (Button) dlg.findViewById(R.id.home_var_fert_2);
		    	meth3 = (Button) dlg.findViewById(R.id.home_var_fert_3);
		    	
		    	
		    	meth1.setOnClickListener(new View.OnClickListener() {
		    			public void onClick(View v) {
		    				Log.d("var 1 picked ", "in dialog");
		    				//img_1.setMaxWidth(300);
		    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
		    				var_text.setText("Method 1");
		    				irr_method_sel = "Method 1";
		    				 TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);
		 	    	      	
		 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
		    				dlg.cancel();                      
		    				}
		     	});
		     	
		    	meth1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 2 picked ", "in dialog");   
	    				//img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
	    				var_text.setText("Method 2");
	    				irr_method_sel = "Method 2";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                       
	    				}
	     	});
		    	
		    	meth3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 3 picked ", "in dialog");  
	    			//	img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
	    				var_text.setText("Method 3");
	    				irr_method_sel = "Method 3";
	    				 TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);
	 	    	      	
	 	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                       
	    				}
	     	});
		    	
		    	
			
			}
		});
    
	

	
	item3.setOnClickListener(new View.OnClickListener() {
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
	    		    	
	    	final ImageView img_1;
	    	
	    	
	    	
	    	day1 = (Button) dlg.findViewById(R.id.home_day_1);
	    	day2 = (Button) dlg.findViewById(R.id.home_day_2);
	    	day3 = (Button) dlg.findViewById(R.id.home_day_3);
	    	day4 = (Button) dlg.findViewById(R.id.home_day_4);
	    	day5 = (Button) dlg.findViewById(R.id.home_day_5);
	    	
	    	day1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				day_irr.setText("Two week before");
	    				irr_day_sel="Two week before";
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	day2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    			//	img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				day_irr.setText("One week before");
    				irr_day_sel="One week before";
    				dlg.cancel();                      
    				}
     	});
	    	
	    	day3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_irr.setText("Yesterday");
    				irr_day_sel="Yesterday";
    				dlg.cancel();                      
    				}
     	});
	    	day4.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_irr.setText("Today");
    				irr_day_sel="Today";
    				dlg.cancel();                      
    				}
     	});
	    	day5.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				day_irr.setText("Tomorrow");
    				irr_day_sel="Tomorrow";
    				dlg.cancel();                      
    				}
     	});
	    	
	    	
		
		}
	});
	
	final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_unit_no_irr); 
   
	item2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();	
			Log.d("in variety sowing dialog", "in dialog");
			final Dialog dlg = new Dialog(v.getContext());
	    	dlg.setContentView(R.layout.numberentry_dialog);
	    	dlg.setCancelable(true);
	        dlg.setTitle("Choose the Number of bags");
	    	Log.d("in variety sowing dialog", "in dialog");
	    	dlg.show();
	    	
	    	  Button no_ok=(Button) dlg.findViewById(R.id.number_ok);
	   	   Button no_cancel=(Button) dlg.findViewById(R.id.number_cancel);
	   	no_ok.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		
		    		  NumberPicker mynp1 = (NumberPicker) dlg.findViewById(R.id.numberpick);
		    		  hrs_irrigate = mynp1.getValue();
		    		  hrs_irrigate_sel= String.valueOf(hrs_irrigate);
		    		    no_text.setText(hrs_irrigate_sel);
		    		    if(hrs_irrigate !=0)
		    		    {
		    		    	 
		    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);
		  	    	      	
		  	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    		    	
		    		    }
		    		    
		    		    dlg.cancel(); 
		    	}
	   	 });
	   	no_cancel.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		dlg.cancel(); 	
	    	}
	    	});
	   	
	   
	   	   
		}
	});
	
  
	
	   Button btnNext=(Button) findViewById(R.id.irr_ok);
	   Button cancel=(Button) findViewById(R.id.irr_cancel);
	   
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
	    		 if(hrs_irrigate == 0)
	    		    {
	    			 flag1 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    			 
	    			 
	    		    }
	    		 else
	    		 {
	    			 flag1 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_irr_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		
	    		 if(irr_method_sel.toString().equalsIgnoreCase("0") )
	    		    {
	    		    
	    			 flag2 =1;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
	    	      
	    		    }
	    		 else
	    		 {
	    			 
	    			 flag2 =0;
	    	    	  
	    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.method_irr_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    		 }
	    		 
	    		 
	    		 
	    		 if(flag1 ==0 && flag2 ==0) 
	    		    {
	    		    	
	    		    	 Intent adminintent = new Intent(action_irrigate.this,Homescreen.class);
	    			        
	    			      startActivity(adminintent);                        
	    			      action_irrigate.this.finish();
	    			      okaudio();
	    		    	 
	    		    }
	    		 else
	    			 initmissingval();
	    		 
	    		 
	    	}
	    });
	    
	    home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(action_irrigate.this,Homescreen.class);
	        
	      startActivity(adminintent);                        
	      action_irrigate.this.finish();
				
				                    
				}
 	});
    
    
    
    
    }

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
	public boolean onLongClick(View v) {
	
		if( v.getId() == R.id.home_btn_method_irr){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selecttypeoffertilizer);
			mp.start();
			
		}
	
       
       if( v.getId() == R.id.home_btn_units_no_irr){
			
    			if(mp != null)
    			{
    				mp.stop();
    				mp.release();
    				mp = null;
    			}
    			mp = MediaPlayer.create(this, R.raw.selecttheunits);
    			mp.start();
    			
    		}
       
       if( v.getId() == R.id.home_btn_day_irr){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.selectthedate);
			mp.start();
			
		}
       
       if( v.getId() == R.id.irr_ok){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.ok);
			mp.start();
			
		}
       
       if( v.getId() == R.id.irr_cancel){
			
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
		Intent adminintent = new Intent(action_irrigate.this,Homescreen.class);
        
	      startActivity(adminintent);                        
	      action_irrigate.this.finish();
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
	
   
    
    
    
    
    
    
}