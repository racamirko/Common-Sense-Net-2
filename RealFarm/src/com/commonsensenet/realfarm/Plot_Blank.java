package com.commonsensenet.realfarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.User;

public class Plot_Blank extends Activity {

	protected RealFarmProvider mDataProvider;

	public User ReadUser = null;
	public int Position; // Has copy of mainlistview position

	final Context context = this;
	String name;

	public void onBackPressed() {

		// Intent adminintent123 = new Intent(Plot_Blank.this,
		// Plot_Image.class);
		// startActivity(adminintent123);
		Plot_Blank.this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plot_list);
		System.out.println("Plot Blank");

		mDataProvider = RealFarmProvider.getInstance(context); // Working

	} // End of oncreate()

}
