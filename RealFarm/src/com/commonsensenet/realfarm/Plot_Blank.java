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




import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.Homescreen;

import com.commonsensenet.realfarm.model.PlotNew;
import com.commonsensenet.realfarm.model.User;

import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.OfflineMapDemo;


public class Plot_Blank extends Activity{
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
	
							
	 //Intent adminintent123 = new Intent(Plot_Blank.this, Plot_Image.class);
	//	startActivity(adminintent123);        
		Plot_Blank.this.finish();
}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_list);
        System.out.println("Plot Blank");
        
        mDataProvider = RealFarmProvider.getInstance(context);    //Working
        
    
    
     						
	}		                                                                           //End of oncreate()		
     	
	
	
} 
	
	

