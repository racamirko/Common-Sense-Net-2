package com.commonsensenet.realfarm.actions;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class action_selling extends HelpEnabledActivity {                //Integration
	// MediaPlayer mp = null;  
	 protected RealFarmProvider mDataProvider;
	   private Context context=this;
	String quality_sell="0", selling_pickcheck="0";
	int sellprice_no, sell_no;
	String sellprice_no_sel, sell_no_sel, units_sell="0";
	 public void onBackPressed() {
			
		 if(mp != null)
			{
				mp.stop();
			mp.release();
				mp = null;
			}
			Intent adminintent = new Intent(action_selling.this,Homescreen.class);
			        
			      startActivity(adminintent);                        
			      action_selling.this.finish();
}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Plant details entered");
     	mDataProvider = RealFarmProvider.getInstance(context);
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.selling_dialog);
    	    	System.out.println("plant done");
    	    	if(mp != null)
				{
					mp.stop();
					mp.release();
					mp = null;
				}
				mp = MediaPlayer.create(this, R.raw.clickingselling);
				mp.start();
    	    	
    final Button item1;
   final  Button item2;
   final Button item3;
   final  Button item4;
    Button quintal;
    Button kgs;
  
    
    ImageButton home;
    ImageButton help;
    
    item1 = (Button) findViewById(R.id.home_btn_sell);
    item2 = (Button) findViewById(R.id.home_btn_sell_price);  
    item3 = (Button) findViewById(R.id.home_btn_units_no_sell);  
    item4 = (Button) findViewById(R.id.home_btn_units_sell); 
    //Integration
   // quintal = (Button) findViewById(R.id.button2);                        //Integration
  //  kgs = (Button) findViewById(R.id.button3);                        //Integration
    
    home = (ImageButton) findViewById(R.id.aggr_img_home);
    help = (ImageButton) findViewById(R.id.aggr_img_help);
    
    item1.setOnLongClickListener(this);                                     
    item2.setOnLongClickListener(this);
    item3.setOnLongClickListener(this);                                     
    item4.setOnLongClickListener(this);
 //   quintal.setOnLongClickListener(this);
  //  kgs.setOnLongClickListener(this);           
    help.setOnLongClickListener(this); 

	item1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 stopaudio();
				Log.d("in selling dialog", "in dialog");
				final Dialog dlg = new Dialog(v.getContext());
		    	dlg.setContentView(R.layout.quality_selling_dialog);
		    	dlg.setCancelable(true);
		    dlg.setTitle("Choose the Quality of seeds");
		    	Log.d("in selling dialog", "in dialog");
		    	dlg.show();
		    	
		    	
		    	 	    
		    	final Button quality1;
		    	final Button quality2;
		    	final Button quality3;
		    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_sell);
		     	quality1 = (Button) dlg.findViewById(R.id.sell_quality_1);
		     	quality2 = (Button) dlg.findViewById(R.id.sell_quality_2);
		     	quality3 = (Button) dlg.findViewById(R.id.sell_quality_3);
		     	
		     	quality1.setOnClickListener(new View.OnClickListener() {
		    			public void onClick(View v) {
		    				Log.d("quality 1 picked ", "in dialog");   
		    				var_text.setText("Good");
		    				quality_sell="Good";
		    				  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_tr);
				    	      	
				    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    				dlg.cancel();                      
		    				}
		     	});
		     	
		    	quality2.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("quality 2 picked ", "in dialog");    
	    				var_text.setText("Satisfactory");
	    				quality_sell="Satisfactory";
	    				  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_tr);
			    	      	
			    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    				dlg.cancel();                      
	    				}
	     	});
		    	
		    	quality3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("quality 3 picked ", "in dialog");    
	    				var_text.setText("Poor");
	    				quality_sell="Poor";
	    				  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_tr);
			    	      	
			    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    				dlg.cancel();                      
	    				}
	     	});
		    	
			}
		});
    
	final TextView no_text = (TextView) findViewById(R.id.dlg_lbl_sell_price); 
	
	item2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
				 stopaudio();	
					Log.d("in variety sowing dialog", "in dialog");
					final Dialog dlg = new Dialog(v.getContext());
			    	dlg.setContentView(R.layout.sellingprice_dialog);
			    	dlg.setCancelable(true);
			        dlg.setTitle("Enter the selling price");
			    	Log.d("in variety sowing dialog", "in dialog");
			    	dlg.show();
			    	
			    	  Button no_ok=(Button) dlg.findViewById(R.id.sellingprice_no_ok);
			   	   Button no_cancel=(Button) dlg.findViewById(R.id.sellingprice_no_cancel);
			   	no_ok.setOnClickListener(new View.OnClickListener() {
				    	public void onClick(View v) {
				    		
				    		  NumberPicker mynp2 = (NumberPicker) dlg.findViewById(R.id.sellingpriceno);
				    		    sellprice_no = mynp2.getValue();
				    		    sellprice_no_sel= String.valueOf(sellprice_no);
				    		    no_text.setText(sellprice_no_sel);
				    		//    Toast.makeText(action_selling.this, "User enetred " + sellprice_no_sel + " rupees", Toast.LENGTH_LONG).show();
				    		    if(sellprice_no !=0)
				    		    {
				    		    	 
				    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_price_tr);
				  	    	      	
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
	
	
	final TextView no_text_1 = (TextView) findViewById(R.id.dlg_lbl_unit_no_sell); 
	   
	item3.setOnClickListener(new View.OnClickListener() {
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
		    		    sell_no = mynp1.getValue();
		    		    sell_no_sel= String.valueOf(sell_no);
		    		    no_text_1.setText(sell_no_sel);

		    		    if(sell_no !=0)
		    		    {
		    		    	 
		    		    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
		  	    	      	
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
	
	
	item4.setOnClickListener(new View.OnClickListener() {
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
	    	
	    	final TextView var_text = (TextView) findViewById(R.id.dlg_lbl_units_sell); 
	    	unit1 = (Button) dlg.findViewById(R.id.home_btn_units_1);
	    	unit2 = (Button) dlg.findViewById(R.id.home_btn_units_2);
	    	unit3 = (Button) dlg.findViewById(R.id.home_btn_units_3);
	    	
	    	
	    	unit1.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				Log.d("var 1 picked ", "in dialog");
	    				//img_1.setMaxWidth(300);
	    			//	img_1.setImageResource(R.drawable.pic_90px_bajra_tiled);
	    				var_text.setText("Bag of 10 Kgs");
	    				units_sell = "Bag of 10 Kgs";
	    				TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
	    				//item1.setBackgroundResource(R.drawable.pic_90px_bajra_tiled);
	    				dlg.cancel();                      
	    				}
	     	});
	     	
	    	unit2.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 2 picked ", "in dialog");   
    			//	img_1.setImageResource(R.drawable.pic_90px_castor_tiled);
    				var_text.setText("Bag of 20 Kgs");
    				units_sell = "Bag of 20 Kgs";
TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
    				dlg.cancel();                      
    				}
     	});
	    	
	    	unit3.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Log.d("var 3 picked ", "in dialog");  
    				//img_1.setImageResource(R.drawable.pic_90px_cowpea_tiled);
    				var_text.setText("Bag of 50 Kgs");
    				units_sell = "Bag of 50 Kgs";
TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
    				dlg.cancel();                      
    				}
     	});
	    	
		
		}
	});
	
	
	
	
	
   
/*	 final Button quality1;
 	quality1 = (Button) findViewById(R.id.sell_quality_1);
	
 	quality1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("quality 1 picked dialog", "in dialog");    	
				
				
			}
 	});
	
   */

	    final Button pickup;
	    final Button checkin;
	    pickup = (Button) findViewById(R.id.home_btn_checkin);
	    checkin = (Button) findViewById(R.id.home_btn_pickup);
	    
	    pickup.setOnLongClickListener(this);                         //Integration
	    checkin.setOnLongClickListener(this); 
	 
	    pickup.setOnClickListener(new View.OnClickListener() {
			

			public void onClick(View v) {
				 stopaudio();
				pickup.setBackgroundResource(R.drawable.empty_80_40btnsel);
				checkin.setBackgroundResource(R.drawable.empty_80_40btn);
				selling_pickcheck="pickup";
				  TableRow tr_feedback = (TableRow) findViewById(R.id.pick_check_tr);
	    	      	
	    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
			}
		});
    
    
	    checkin.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			 stopaudio();
			  pickup.setBackgroundResource(R.drawable.empty_80_40btn);
			  checkin.setBackgroundResource(R.drawable.empty_80_40btnsel);
			  selling_pickcheck="checkin";
			  TableRow tr_feedback = (TableRow) findViewById(R.id.pick_check_tr);
  	      	
  	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		}
	});
	    
	    
	    
	    
	    

		   Button btnNext=(Button) findViewById(R.id.selling_ok);
		   Button cancel=(Button) findViewById(R.id.home_btn_wf_2);
		   
		   btnNext.setOnLongClickListener(this);                       //Integration 
		   cancel.setOnLongClickListener(this);
		   
		   cancel.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
	              cancelaudio();
		    	}
		   
		    	});
		   
		    btnNext.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		
		    		System.out.println("in ok clicked");
		    		 int flag1, flag2,flag3,flag4;
		    		 if(quality_sell.toString().equalsIgnoreCase("0") )
		    		    {
		    			 flag1 =1;
		    		//	 System.out.println("1 details clicked");
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		    			 
		    			 
		    		    }
		    		 else
		    		 {
		    			 flag1 =0;
		    			
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    		 }
		    		
		    		 if(selling_pickcheck.toString().equalsIgnoreCase("0") )
		    		    {
		    	//		 System.out.println("2 details clicked");
		    			 flag2 =1;
		    		//	 Toast.makeText(action_selling.this, "User enetred in 2", Toast.LENGTH_LONG).show();
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.pick_check_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		    		    }
		    		 else
		    		 {
		    			 
		    			 flag2 =0;
		    		//	 Toast.makeText(action_selling.this, " not User enetred in 2", Toast.LENGTH_LONG).show(); 
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.pick_check_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    		 }
		    		 
		    		 
		    		 if(sellprice_no == 0 )
		    		    {
		    		//	 System.out.println("3 details clicked");
		    			 flag3 =1;
		    			// Toast.makeText(action_selling.this, "User enetred " + selling_price + " rupees", Toast.LENGTH_LONG).show();
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_price_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		    		    }
		    		 else
		    		 {
		    			 
		    			 flag3 =0;
		    		//	 Toast.makeText(action_selling.this, "User enetred " + selling_price + " rupees", Toast.LENGTH_LONG).show();
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.quality_sell_price_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    		 }
		    		 
		   		 if(units_sell.toString().equalsIgnoreCase("0") || sell_no == 0)
		    		    {
		    		//	 System.out.println("4 details clicked");
		    			 flag4 =1;
		    		//	 Toast.makeText(action_selling.this, "User enetred " + selling_quintal + " quintals", Toast.LENGTH_LONG).show();
			    		 
			    	//	 Toast.makeText(action_selling.this, "User enetred " + selling_kg + " kgs", Toast.LENGTH_LONG).show();
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img_not);
		    		    }
		    		 else
		    		 {
		    			 
		    			 flag4 =0;
		    		//	 Toast.makeText(action_selling.this, "User enetred " + selling_quintal + " quintals", Toast.LENGTH_LONG).show();
			    		 
			    	//	 Toast.makeText(action_selling.this, "User enetred " + selling_kg + " kgs", Toast.LENGTH_LONG).show();
		    	    	  TableRow tr_feedback = (TableRow) findViewById(R.id.units_sell_tr);
		    	      	
		    	      	tr_feedback.setBackgroundResource(R.drawable.def_img);
		    		 }
		    		 
		    		 
		    		
		    		 
		    		 if(flag1 ==0 && flag2 ==0 && flag3 ==0 && flag4 ==0) 
		    		    {
		    			 System.out.println("selling writing");
		    				mDataProvider.setselling(sell_no, 0, units_sell, "Today", sellprice_no, quality_sell,
		    						selling_pickcheck, 1, 0);

		    				System.out.println("selling reading");
		    				mDataProvider.getselling();
		    			 	 	 
		    		    	 Intent adminintent = new Intent(action_selling.this,Homescreen.class);
		    			        
		    			      startActivity(adminintent);                        
		    			      action_selling.this.finish();
		    			      okaudio();
		    		    	 
		    		    }
		    		 else
		    			 initmissingval() ;
		    		 
		    		 
		    	}
		    });
		    
		    home.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent adminintent = new Intent(action_selling.this,Homescreen.class);
			        
				      startActivity(adminintent);                        
				      action_selling.this.finish();
					                    
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
		Intent adminintent = new Intent(action_selling.this,Homescreen.class);
        
	      startActivity(adminintent);                        
	      action_selling.this.finish();
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
    
	@Override
	public boolean onLongClick(View v) {                      //latest
	
		if( v.getId() == R.id.home_btn_sell_price){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.enterpricedetails);
			mp.start();
			
		}
		
       if( v.getId() == R.id.home_btn_sell){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.qualityofseeds);
			mp.start();
			
		}
       
       
       if( v.getId() == R.id.home_btn_units_no_sell || v.getId() == R.id.home_btn_units_sell ){
			
    			if(mp != null)
    			{
    				mp.stop();
    				mp.release();
    				mp = null;
    			}
    			mp = MediaPlayer.create(this, R.raw.selecttheunits);
    			mp.start();
    			
    		}
       
   
       if( v.getId() == R.id.home_btn_pickup){
			
 			if(mp != null)
 			{
 				mp.stop();
 				mp.release();
 				mp = null;
 			}
 			mp = MediaPlayer.create(this, R.raw.pickup);
 			mp.start();
 			
 		}
       if( v.getId() == R.id.home_btn_checkin){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.checkin);
			mp.start();
			
		}
       if( v.getId() == R.id.selling_ok){
			
			if(mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
			mp = MediaPlayer.create(this, R.raw.ok);
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

    
    
    
    
    
    
    
    
    
    
    
}