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
import android.graphics.drawable.Drawable;
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




import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;


public class My_setting_plot_info extends Activity{
View view;
/** View where the items are displayed. */

protected RealFarmProvider mDataProvider;


private ListView mainListView ;  
private ArrayAdapter<String> listAdapter ; 
Cursor cc;
String	log;
public User ReadUser=null;
public int Position;                           //Has copy of mainlistview position




final Context context = this;
String name;

 public void onBackPressed() {
	
							
	 Intent adminintent123 = new Intent(My_setting_plot_info.this, Homescreen.class);
		startActivity(adminintent123);        
						  My_setting_plot_info.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting_plot_info);
        System.out.println("In My_setting_plot_info call");
        
   
        
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        
    
    	Button AddPlot = (Button) findViewById(R.id.AddPlot);
     		//Button DeleteUser;
     	//	Button DeleteUser = (Button) findViewById(R.id.DeleteUser);
    	ListViewSettings();
    	//listimgview();
     		
    	
     	

     		// add the event listeners
     	  AddPlot.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				System.out.println("In add user");
     		
     				Global.CallToPlotInfo=1;
     				Intent adminintent123 = new Intent(My_setting_plot_info.this, My_settings_plot_details.class);
     				startActivity(adminintent123);
     			   My_setting_plot_info.this.finish();
     				ListViewSettings();
     			//  listimgview();
     			}
     		});
     		
    	
    		
//final List<User> userList =mDataProvider.getUserList();                                 //Working
    		
     		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     		    public void onItemClick(AdapterView parent, View v, int position, long id){
     		        // Start your Activity according to the item just clicked.
     		    	
     		    	System.out.println("in main list SHORT CLICK of my_setting_plot_info ");
     		    	
     		   // 	Position=position+1;
				//	ReadUser= mDataProvider.getUserById(position+1);
			//		mDataProvider.getUserList();
     		    	
     		 // 	Intent AdmincallToHome = new Intent(admincall.this, Homescreen.class);  // This works  When user is pressed it goes to homescreen
     			//	startActivity(AdmincallToHome);
			
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
	
	

