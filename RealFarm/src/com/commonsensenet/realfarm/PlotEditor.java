package com.commonsensenet.realfarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
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
	private int currentGrowingId = -1;
	private int currentQuantityId = -1;
	private RealFarmProvider mDataProvider;
	private List<Growing> mGrowing;
	private int plotID = -1;
	private List<Seed> seedList = new ArrayList<Seed>();

	private void editAction(int action, int actionID, Dialog dialog,
			int growingId, int quantityId) {

		// TODO: use quantityID, needs to be added to the database.

		// user clicked ok
		if (action == 1 && growingId != -1) {
			// add executed action to diary
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			mDataProvider.setAction(actionID, growingId,
					dateFormat.format(date));
		}

		// update lists
		updateDiary();
		updateActions();

		// close popup window
		dialog.cancel();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	View.OnClickListener OnClickAction(final int actionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {

				// get more information about action
				Dialog alert = new Dialog(PlotEditor.this);
				alert.setContentView(R.layout.plot_dialog);

				// set popup title
				String actionName = mDataProvider.getActionById(actionID)
						.getName();
				alert.setTitle(actionName);

				// add button to select seed type, this tell us about growing id
				TableLayout table = (TableLayout) alert
						.findViewById(R.id.TableLayout01);
				TableRow vg = new TableRow(PlotEditor.this);

				TextView tv = new TextView(PlotEditor.this);
				tv.setText(R.string.seed);
				tv.setTextSize(20);
				vg.addView(tv);

				// check tapped action
				if (actionName.compareTo("Sow") == 0) // all seeds can be used
				{
					List<Seed> allSeedList = new ArrayList<Seed>();
					allSeedList = mDataProvider.getSeedsList();

					// A new growing id will be created with one seed id
					for (int i = 0; i < allSeedList.size(); i++) {
						ImageView nameView1 = new ImageView(PlotEditor.this);
						Seed s = allSeedList.get(i);
						nameView1.setScaleType(ScaleType.CENTER);
						nameView1.setImageResource(s.getRes());
						nameView1
								.setBackgroundResource(R.drawable.cbuttonsquare);

						int growingId = (int) mDataProvider.setGrowing(plotID,
								s.getId());
						nameView1.setOnClickListener(OnClickGrowing(growingId));
						vg.addView(nameView1);
					}

				} else { // only existing seeds can be used
					for (int i = 0; i < mGrowing.size(); i++) {
						ImageView nameView1 = new ImageView(PlotEditor.this);
						Seed s = mDataProvider.getSeedById(mGrowing.get(i)
								.getSeedId());
						nameView1.setBackgroundResource(s.getRes());
						nameView1.setOnClickListener(OnClickGrowing(mGrowing
								.get(i).getId()));
						vg.addView(nameView1);
					}
				}

				table.addView(vg, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				// Offer option about quantity
				TableRow vg2 = new TableRow(PlotEditor.this);
				TextView tvv = new TextView(PlotEditor.this);
				tvv.setText("Quantity");
				tvv.setTextSize(20);
				vg2.addView(tvv);

				Button b1 = new Button(PlotEditor.this);
				b1.setText("Small");
				b1.setBackgroundResource(R.drawable.cbuttonsquare);
				vg2.addView(b1);
				Button b2 = new Button(PlotEditor.this);
				b2.setBackgroundResource(R.drawable.cbuttonsquare);
				b2.setText("Medium");
				vg2.addView(b2);
				Button b3 = new Button(PlotEditor.this);
				b3.setText("Large");
				b3.setBackgroundResource(R.drawable.cbuttonsquare);
				vg2.addView(b3);

				table.addView(vg2, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				// ok and cancel buttons
				ImageView iiv = (ImageView) alert
						.findViewById(R.id.cancelbutton);
				iiv.setOnClickListener(OnClickFinish(2, alert, actionID));

				ImageView iiv2 = (ImageView) alert.findViewById(R.id.okbutton);
				iiv2.setOnClickListener(OnClickFinish(1, alert, actionID));

				alert.show();

			}
		};
	}

	View.OnClickListener OnClickDiary(final int actionID) {
		return new View.OnClickListener() {

			public void onClick(View v) {
				// removes the selected action
				mDataProvider.removeAction(actionID);
				// updates the UI
				updateDiary();
				updateActions();
			}
		};
	}

	View.OnClickListener OnClickFinish(final int action, final Dialog dialog,
			final int actionID) {

		return new View.OnClickListener() {

			public void onClick(View v) {
				editAction(action, actionID, dialog, currentGrowingId,
						currentQuantityId);

			}
		};

	}

	View.OnClickListener OnClickGrowing(final int growingID) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				v.setPressed(true);
				// store growing id
				currentGrowingId = growingID;
			}
		};
	}

	View.OnClickListener OnClickQuantity(final int quantityId) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				v.setBackgroundColor(Color.RED);
				currentQuantityId = quantityId;
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

		// Displays the plot information.
		updatePlotInformation();

		// Displays the available recommendations.
		updateRecommendations();

		// Displays the set of possible actions.
		updateActions();

		// Displays the diary.
		updateDiary();

	}

	/**
	 * UpdateActions()
	 */

	public void updateActions() {
		LinearLayout container = (LinearLayout) findViewById(R.id.layoutactions);
		container.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.actions);
		tv.setTextSize(TEXT_HEADER_SIZE);
		tv.setTextColor(Color.BLACK);
		container.addView(tv);

		// get all possible actions
		List<Action> tmpActionList = mDataProvider.getActionsList();

		// Create table layout to add
		TableLayout tl = new TableLayout(this);
		TableRow row1 = new TableRow(this);
		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		int leftMargin = 20;
		int topMargin = 2;
		int rightMargin = 20;
		int bottomMargin = 20;

		tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
				bottomMargin);

		row1.setLayoutParams(tableRowParams);

		Action tmpAction;
		// for each possible action
		for (int x = 0; x < tmpActionList.size(); x++) {
			tmpAction = tmpActionList.get(x);

			int iterNb = (tmpAction.getId() % TABLE_NB_COLUMN) - 1;

			if (iterNb == 0) {
				tl.addView(row1);
				row1 = new TableRow(this);
				row1.setLayoutParams(tableRowParams);
			}

			ImageView ib = new ImageView(this);
			ib.setImageResource(tmpAction.getRes());
			ib.setBackgroundResource(R.drawable.cbutton);
			int actionID = tmpAction.getId();
			ib.setOnClickListener(OnClickAction(actionID));
			row1.addView(ib, iterNb);

		}
		tl.addView(row1);

		container.addView(tl);

	}

	/**
	 * updateDiary()
	 */
	public void updateDiary() {

		// Load main diary layout and clean
		LinearLayout containerDiary = (LinearLayout) findViewById(R.id.layoutdiary);
		containerDiary.removeAllViews();

		// Define header
		TextView tv = new TextView(this);
		tv.setText(R.string.diary);
		tv.setTextSize(TEXT_HEADER_SIZE);
		tv.setTextColor(Color.BLACK);
		containerDiary.addView(tv);

		// Define list of diary elements
		TableLayout table = new TableLayout(this);
		Diary res = mDataProvider.getDiary(plotID);
		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		if (res != null) { // if not empty
			for (int i = 0; i < res.getSize(); i++) {

				TableRow tr = new TableRow(this);
				tr.setLayoutParams(tableRowParams);

				Action a = mDataProvider.getActionById(res.getActionId(i));

				// Get icon of action
				ImageView iv = new ImageView(this);
				iv.setBackgroundResource(a.getRes());
				iv.setLayoutParams(new TableRow.LayoutParams(40, 40));
				tr.addView(iv);

				// Get text related to action
				TextView nameView1 = new TextView(this);
				nameView1.setText(i + " " + a.getName() + " "
						+ res.getActionDate(i) + " " + res.getGrowingId(i));

				int lastActionID = res.getId(i);
				nameView1.setOnClickListener(OnClickDiary(lastActionID));
				nameView1.setTextColor(Color.BLACK);
				tr.addView(nameView1);

				// Add to list of elements
				table.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			}
		}

		// Add to interface
		containerDiary.addView(table);
	}

	/**
	 * Update plot information
	 */

	public void updatePlotInformation() {

		LinearLayout container0 = (LinearLayout) findViewById(R.id.layoutheader);

		container0.removeAllViews();

		TextView tv = new TextView(this);
		tv.setText(R.string.plot);
		tv.setTextSize(TEXT_HEADER_SIZE);
		tv.setTextColor(Color.BLACK);
		container0.addView(tv);

		// Bitmap mBitmap = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			plotID = Integer.parseInt(extras.getString("ID"));
		}

		// Get growing areas of the plot
		mGrowing = mDataProvider.getGrowingByPlotId(plotID);

		// Get plot owner
		int ownerId = mDataProvider.getPlotById(plotID).getOwnerId();

		// Get plot user
		User plotOwner = mDataProvider.getUserById(ownerId);

		// Add username
		TextView nameView = new TextView(this);
		if (plotOwner != null)
			nameView.setText(plotOwner.getFirstName() + " "
					+ plotOwner.getLastName());
		else
			nameView.setText("Unknown");

		nameView.setTextColor(Color.BLACK);
		container0.addView(nameView);

		// Add list of growing areas
		LinearLayout llMain = new LinearLayout(this);
		llMain.setOrientation(LinearLayout.HORIZONTAL);
		llMain.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		for (int i = 0; i < mGrowing.size(); i++) {
			TextView nameView1 = new TextView(this);
			Seed s = mDataProvider.getSeedById(mGrowing.get(i).getSeedId());
			seedList.add(s);
			String seedName = s.getName();
			nameView1.setText(seedName);
			nameView1.setTextColor(Color.BLACK);
			// llMain.addView(nameView1);

			ImageView iv = new ImageView(PlotEditor.this);
			iv.setBackgroundResource(s.getRes());
			LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			iv.setLayoutParams(ll);
			llMain.addView(iv);
		}

		container0.addView(llMain);
	}

	/**
	 * UpdateRecommentations
	 */

	public void updateRecommendations() {
		LinearLayout container0 = (LinearLayout) findViewById(R.id.layoutrec);
		container0.removeAllViews();

		List<Recommendation> r = mDataProvider.getRecommendationsList();

		// Are there recommendations for this type of plot?

		for (int i = 0; i < r.size(); i++) {

			if (seedList.contains(r.get(i).getSeed())) {

				TextView tv = new TextView(this);
				tv.setText(R.string.recommendation);
				tv.setTextSize(TEXT_HEADER_SIZE);
				tv.setTextColor(Color.BLACK);
				container0.addView(tv);

				TextView nameView = new TextView(this);
				nameView.setTextColor(Color.BLACK);
				container0.addView(nameView);

			}

		}

		// else no recommendation => do nothing

	}
}
