package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Action;
import com.commonsensenet.realfarm.model.Diary;
import com.commonsensenet.realfarm.model.Growing;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

public class PlotEditor extends Activity {

	private static int TABLE_NB_COLUMN = 3;
	private static int TEXT_HEADER_SIZE = 30;
	private RealFarmProvider mDataProvider;
	private int plotID = -1;
	private List<Seed> seedList = new ArrayList<Seed>();

	private void editAction(int action, int actionID) {

		if (action == 1) // user clicked ok
		{
			// add executed action to diary
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			// TODO: take growing id from user interface
			mDataProvider.setAction(actionID, 1, dateFormat.format(date));
			updateDiary();
			updateActions();
		}
		// else user clicked cancel => do nothing

	}

	@Override
	public void onBackPressed() {
		finish();
	}

	View.OnClickListener OnClickAction(final int actionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {

				// get more information about action
				LayoutInflater inflater = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);

				View layout = inflater.inflate(R.layout.plot_dialog,
						(ViewGroup) findViewById(R.id.layout_root));

				AlertDialog.Builder alert = new AlertDialog.Builder(
						PlotEditor.this);

				alert.setView(layout);

				String title = getResources().getString(R.string.editAction);
				String actionName = mDataProvider.getActionById(actionID)
						.getName();
				alert.setTitle(title + " " + actionName);

				// add button to select seed type
				LinearLayout vg = (LinearLayout) layout
						.findViewById(R.id.layout_root2);
				TextView tv = new TextView(PlotEditor.this);
				tv.setText(R.string.seed);
				tv.setTextSize(20);
				vg.addView(tv);

				if (actionName.compareTo("Sow") == 0) // action is sowing so all
														// seeds can be used
				{
					List<Seed> mseed = mDataProvider.getSeedsList();
					for (int i = 0; i < mseed.size(); i++) {
						Button nameView1 = new Button(PlotEditor.this);
						String seedName = mseed.get(i).getName();
						nameView1.setText(seedName);
						vg.addView(nameView1);
					}

				} else // only existing seeds can be used
				{
					for (int i = 0; i < seedList.size(); i++) {
						Button nameView1 = new Button(PlotEditor.this);
						Seed s = seedList.get(i);
						String seedName = s.getName();
						nameView1.setText(seedName);
						vg.addView(nameView1);
					}

				}

				// Parse reply
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								/* User clicked ok so do some stuff */

								editAction(1, actionID);

							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								/* User clicked cancel so do some stuff */
								editAction(2, actionID);
							}
						});
				alert.show();

			}
		};
	}

	View.OnClickListener OnClickDiary(final int lastActionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				mDataProvider.removeAction(lastActionID);
				updateDiary();
				updateActions();
			}
		};
	}

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

	/**
	 * UpdateActions()
	 */

	public void updateActions() {
		LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout1);
		container.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.actions);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container.addView(tv);

		// get all possible actions
		List<Action> tmpActionList = mDataProvider.getActionsList();

		// Create table layout to add
		TableLayout tl = new TableLayout(this);
		TableRow row1 = new TableRow(this);

		Action tmpAction;
		// for each possible action
		for (int x = 0; x < tmpActionList.size(); x++) {
			tmpAction = tmpActionList.get(x);

			int iterNb = (tmpAction.getId() % TABLE_NB_COLUMN) - 1;

			if (iterNb == 0) {
				tl.addView(row1);
				row1 = new TableRow(this);
			}

			Button b = new Button(this);
			b.setText(tmpAction.getName());
			int actionID = tmpAction.getId();
			b.setOnClickListener(OnClickAction(actionID));

			row1.addView(b, iterNb);

		}
		tl.addView(row1);

		container.addView(tl);

	}

	/**
	 * updateDiary()
	 */

	public void updateDiary() {

		LinearLayout container2 = (LinearLayout) findViewById(R.id.linearLayout2);

		container2.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.diary);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container2.addView(tv);

		Diary res = mDataProvider.getDiary(plotID);

		if (res != null) {
			for (int i = 0; i < res.getSize(); i++) {
				TextView nameView1 = new TextView(this);

//				Date date = new Date();
//				date.setTime(res[2][i]);

				nameView1.setText(i
						+ " "
						+ mDataProvider.getActionById(res.getActionId(i))
								.getName() + " " + res.getActionDate(i) + " "
						+ res.getGrowingId(i));
				// date.toLocaleString()

				int lastActionID = res.getActionId(i);

				nameView1.setOnClickListener(OnClickDiary(lastActionID));

				container2.addView(nameView1);

			}
		}
	}

	/**
	 * Update plot information
	 */

	public void updatePlotInformation() {
		LinearLayout container0 = (LinearLayout) findViewById(R.id.linearLayout0);

		container0.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.plot);
		tv.setTextSize(TEXT_HEADER_SIZE);
		container0.addView(tv);

		//Bitmap mBitmap = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			plotID = Integer.parseInt(extras.getString("ID"));
			// mBitmap = (Bitmap) extras.getParcelable("Bitmap");
		}

		// ImageView ima = new ImageView(this);
		// ima.setImageBitmap(mBitmap);
		// container0.addView(ima);

		// seeds =
		// mDataProvider.getPlotById(Integer.parseInt(plotID)).getSeeds();
		List<Growing> mGrowing = mDataProvider.getGrowingByPlotId(plotID);

		int ownerId = mDataProvider.getPlotById(plotID).getOwnerId();
		User plotOwner = mDataProvider.getUserById(ownerId);
		TextView nameView = new TextView(this);
		if (plotOwner != null)
			nameView.setText(plotOwner.getFirstName() + " "
					+ plotOwner.getLastName());
		else
			nameView.setText("Unknown");

		container0.addView(nameView);

		for (int i = 0; i < mGrowing.size(); i++) {
			TextView nameView1 = new TextView(this);
			Seed s = mDataProvider.getSeedById(mGrowing.get(i).getSeedId());
			seedList.add(s);
			String seedName = s.getName();
			nameView1.setText(seedName);
			container0.addView(nameView1);
		}

	}

	/**
	 * UpdateRecommentations
	 */

	public void updateRecommendations() {
		LinearLayout container0 = (LinearLayout) findViewById(R.id.linearLayout01);
		container0.removeAllViews();

		List<Recommendation> r = mDataProvider.getRecommendationsList();

		if (r.size() > 0) { // there are recommendations

			TextView tv = new TextView(this);
			tv.setText(R.string.recommendation);
			tv.setTextSize(TEXT_HEADER_SIZE);
			container0.addView(tv);

			TextView nameView = new TextView(this);

			container0.addView(nameView);
		}
		// else no recommendation => do nothing

	}
}
