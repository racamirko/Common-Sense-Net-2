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
import com.commonsensenet.realfarm.actions.action_harvest;
import com.commonsensenet.realfarm.actions.action_irrigate;
import com.commonsensenet.realfarm.actions.action_selling;
import com.commonsensenet.realfarm.actions.action_sowing;
import com.commonsensenet.realfarm.actions.action_spraying;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

import com.commonsensenet.realfarm.overlay.PlotInformationWindow;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;


public class Plot_Image extends Activity{
View view;
/** View where the items are displayed. */

protected RealFarmProvider mDataProvider;


private ListView mainListView ;  
private ArrayAdapter<String> listAdapter ; 
Cursor cc;
String	log;
public User ReadUser=null;
public int Position;                           //Has copy of mainlistview position
private PlotInformationWindow mCurrentWindow;



final Context context = this;
String name;

 public void onBackPressed() {
	
							
	 Intent adminintent123 = new Intent(Plot_Image.this, Homescreen.class);
		startActivity(adminintent123);        
		Plot_Image.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_image);
        System.out.println("In My_setting_plot_info call");
        int no_of_plots;
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        
        no_of_plots = mDataProvider.getAllPlotList().size();
 		 String no_of_plots_str= String.valueOf(no_of_plots);
    	
		
    
        		ListViewSettings();
    	
    	
		 Toast.makeText(Plot_Image.this, "No of Plots     " + no_of_plots_str , Toast.LENGTH_LONG).show();
//test
     		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     		

			public void onItemClick(AdapterView parent, View v, int position, long id){
     		        // Start your Activity according to the item just clicked.
     		    	
				
		
				
     	   	System.out.println("in main list SHORT CLICK of Plot_Image ");
     		    	if(	Global.actionno == 1)
     		    	{
     		    		Intent adminintent123 = new Intent(Plot_Image.this, action_sowing.class);
     		 			startActivity(adminintent123);
     		 			Plot_Image.this.finish();
     		    		
     		    	}
     		    	
     		    	if(	Global.actionno == 2)
     		    	{
     		    		  Intent adminintent123 = new Intent(Plot_Image.this, action_harvest.class);
     		 			startActivity(adminintent123);
     		 			Plot_Image.this.finish();
     		    	}
     		    	if(	Global.actionno == 3)
     		    	{
     		    		  Intent adminintent123 = new Intent(Plot_Image.this, action_selling.class);
     		 			startActivity(adminintent123);
     		 			Plot_Image.this.finish();
     		    	}
     		    	if(	Global.actionno == 4)
     		    	{
     		    		  Intent adminintent123 = new Intent(Plot_Image.this, action_fertilizing.class);
     		 			startActivity(adminintent123);
     		 			Plot_Image.this.finish();
     		    	}
     		    	if(	Global.actionno == 5)
     		    	{
     		    		  Intent adminintent123 = new Intent(Plot_Image.this, action_spraying.class);
     		 			startActivity(adminintent123);
     		 			Plot_Image.this.finish();
     		    	}
     		   	if(	Global.actionno == 6)
 		    	{
 		    			 			

 		    	}
     		    
     		  	if(	Global.actionno == 7)
 		    	{
     		  		Intent adminintent123 = new Intent(Plot_Image.this, SM_enter.class);
 		 			startActivity(adminintent123);
 		 			Plot_Image.this.finish();			
     		 	
 		 			
 		    	}
     			if(	Global.actionno == 8)
 		    	{
     		  		Intent adminintent123 = new Intent(Plot_Image.this, action_irrigate.class);
 		 			startActivity(adminintent123);
 		 			Plot_Image.this.finish();			
     		 	
 		 			
 		    	}
     	 
		//	Plot_Image.this.finish();
			
     		    }
     		});
     		
     		
    		
    		
     		 
     						
	}		                                                                           //End of oncreate()		
     	
	
	public void ListViewSettings() {
		
		
			
			View view = null;
			String text;

			mainListView = (ListView) findViewById(R.id.mainListView);

			mainListView.setItemsCanFocus(true);
			String[] planets = new String[] {  }; // Sets parameters for list view
			ArrayList<String> planetList = new ArrayList<String>();
			planetList.addAll(Arrays.asList(planets));
			listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,
					planetList);
			mainListView.setAdapter(listAdapter);
			
			
			//gets the users from the database.
		List<PlotNew> userList = mDataProvider.getAllPlotList();

			// adds the plot into the list adapter.
			for (int x = 0; x < userList.size(); x++) {
				listAdapter.add("Plot id:  "+ userList.get(x).getPlotId() + " " + "Soil type:  "+ userList.get(x).getSoilType());
				
				
			}
		}


	
	
} 
	
	

