package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.User;

public class PlotEditor extends Activity {

	private RealFarmProvider mDataProvider;
	private int[][] seeds;
	private static int TABLE_NB_COLUMN = 3;
	private static int TEXT_HEADER_SIZE = 30;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plot_editor);

		RealFarmApp mainApp = ((RealFarmApp) getApplicationContext());
		RealFarmDatabase db = mainApp.getDatabase();
		mDataProvider = new RealFarmProvider(db);

		/*
		 * Display information about plot
		 */
		updatePlotInformation();

		/*
		 * Display set of possible actions
		 */
		updateRecommendations();

		/*
		 * Display set of possible actions
		 */
		updateActions();

		/*
		 * Display diary
		 */
		updateDiary();

	}

	public void updatePlotInformation() {
		LinearLayout container0 = (LinearLayout) findViewById(R.id.linearLayout0);

		container0.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.plot);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container0.addView(tv);

		String plotID = null;
		Bitmap mBitmap = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			plotID = extras.getString("ID");
			mBitmap = (Bitmap) extras.getParcelable("Bitmap");
		}

		ImageView ima = new ImageView(this);
		ima.setImageBitmap(mBitmap);
		container0.addView(ima);

		seeds = mDataProvider.getPlotSeed(Integer.parseInt(plotID));
		User plotOwner = mDataProvider.getPlotOwner(Integer.parseInt(plotID));

		TextView nameView = new TextView(this);
		if (plotOwner != null)
			nameView.setText(plotOwner.getFirstName() + " " + plotOwner.getLastName());
		else
			nameView.setText("Unknown");

		container0.addView(nameView);

		for (int i = 0; i < seeds.length; i++) {
			TextView nameView1 = new TextView(this);
			String seedName = mDataProvider.getSeedName(seeds[i][1]);
			nameView1.setText(seedName);
			container0.addView(nameView1);
		}

	}

	public void updateRecommendations() {
		LinearLayout container0 = (LinearLayout) findViewById(R.id.linearLayout01);
		container0.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.recommendation);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container0.addView(tv);

		TextView nameView = new TextView(this);
		nameView.setText("No recommendations so far");
		container0.addView(nameView);

	}

	public void updateActions() {
		LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout1);
		container.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.actions);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container.addView(tv);

		// get all possible actions
		List<Action> tmpActionList = mDataProvider.getActions();

		// Get all executed actions
//		long[][] res = mDataProvider.getDiary(seeds[0][0]);

		// Create table layout to add
		TableLayout tl = new TableLayout(this);
		TableRow row1 = new TableRow(this);
		
		Action tmpAction;
		// for each possible action
		for(int x = 0; x < tmpActionList.size(); x++)
		{
			tmpAction = tmpActionList.get(x);
			
			int iterNb = (tmpAction.getId() % TABLE_NB_COLUMN)-1;
			
			if (iterNb == 0){
				tl.addView(row1);
				row1 = new TableRow(this);
			}

//			// Remove actions already executed
//			if ((res == null) || (!arrayContains(res[1], key))) {

				Button b = new Button(this);
				b.setText(tmpAction.getName());
				int actionID = tmpAction.getId();

				b.setOnClickListener(OnClickAction(actionID));
//				container.addView(b);
				
				row1.addView(b, iterNb);				
				
//			}
		}
		tl.addView(row1);
		
		container.addView(tl);

	}

	View.OnClickListener OnClickAction(final int actionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				mDataProvider.setAction(actionID, seeds[0][0],
						dateFormat.format(date));
				updateDiary();
			}
		};
	}

	private boolean arrayContains(long[] res, int key) {

		if (res == null)
			return false;

		for (int i = 0; i < res.length; i++) {

			if (res[i] == key)
				return true;
		}
		return false;
	}

	public void updateDiary() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);

		container2.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.diary);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container2.addView(tv);

		long[][] res = mDataProvider.getDiary(seeds[0][0]);

		if (res != null) {
			for (int i = 0; i < res[0].length; i++) {
				TextView nameView1 = new TextView(this);

				Date date = new Date();
				date.setTime(res[2][i]);

				nameView1.setText(i + " "
						+ mDataProvider.getActionById((int) res[1][i]).getName() + " "
						+ date.toLocaleString());
				int lastActionID = (int) res[0][i];

				nameView1.setOnClickListener(OnClickDiary(lastActionID));

				container2.addView(nameView1);

			}
		}
		updateActions();

	}

	View.OnClickListener OnClickDiary(final int lastActionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				mDataProvider.removeAction(lastActionID);
				updateActions();
				updateDiary();
			}
		};
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
