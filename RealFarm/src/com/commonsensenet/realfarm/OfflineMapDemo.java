package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.map.GeoPoint;
import com.commonsensenet.realfarm.map.OfflineMapView;
import com.commonsensenet.realfarm.map.OnOverlayTappedListener;
import com.commonsensenet.realfarm.map.Overlay;
import com.commonsensenet.realfarm.model.Log;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.overlay.ActionItem;
import com.commonsensenet.realfarm.overlay.PlotInformationWindow;
import com.commonsensenet.realfarm.overlay.PlotOverlay;
import com.commonsensenet.realfarm.overlay.QuickAction;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class OfflineMapDemo extends Activity {

	/** Location of the village used for this demo. */
	public static final GeoPoint CKPURA_LOCATION = new GeoPoint(14054563,
			77167003);

	/** Window used to displayed the plot information. */
	private PlotInformationWindow mCurrentWindow;
	/** Class used to extract the data from the database. */
	private RealFarmProvider mDataProvider;
	/** View that handles the map of the area. */
	private OfflineMapView mOfflineMap;
	/** List of Polygons that represent the plots available. */
	private List<Plot> mPlots;

	/**
	 * Intercepts the back button press by the user in the main screen and
	 * requests a confirmation from the user before quitting.
	 * 
	 */
	@Override
	public void onBackPressed() {
//		new AlertDialog.Builder(this)
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.setTitle(R.string.exitTitle)
//				.setMessage(R.string.exitMsg)
//				.setNegativeButton(android.R.string.cancel, null)
//				.setPositiveButton(android.R.string.ok,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// Exit the activity
//								OfflineMapDemo.this.finish();
//							}
//						}).show();
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// sets the layout
		setContentView(R.layout.main);

		// gets the map from the UI.
		mOfflineMap = (OfflineMapView) findViewById(R.id.offlineMap);
		mOfflineMap.setOnOverlayTappedListener(new OnOverlayTappedListener() {

			public void onOverlayTapped(Overlay overlay) {

				PlotOverlay po = (PlotOverlay) overlay;
				
				mDataProvider.logAction(Log.MAIN_MAP_PLOT_CLICKED, "plotId: "+po.getPlot().getId());

				// only one window can be displayed at the time.
				if (mCurrentWindow == null) {

					// displays the information about the plot on a different
					// window.
					mCurrentWindow = new PlotInformationWindow(mOfflineMap, po
							.getPlot(), mDataProvider);
					// detects when it gets closed.
					mCurrentWindow
							.setOnDismissListener(new OnDismissListener() {
								public void onDismiss() {
									mDataProvider.logAction(Log.PLOTINFO_DISMISS, null);
									// clears the current window when it gets
									// closed.
									mCurrentWindow = null;
								}
							});
					// shows the window.
					mCurrentWindow.show();
				}

				// Intent myIntent = new Intent();
				// myIntent.setClass(mOfflineMap.getContext(),
				// PlotEditor.class);
				// int test = po.getPlot().getId();
				// myIntent.putExtra("ID", Integer.toString(test));
				// mOfflineMap.getContext().startActivity(myIntent);
			}
		});

		// sets the items included in the action bar.
		setUpActionBar();

		// deletes the current database.
		getApplicationContext().deleteDatabase(RealFarmDatabase.DB_NAME);

//		RealFarmDatabase db = mainApp.setDatabase();
		mDataProvider = RealFarmProvider.getInstance(getApplicationContext());
		RealFarmApp mainApp = ((RealFarmApp) getApplicationContext());
		mainApp.setDatabase(mDataProvider.getDatabase());

		// Define overlays
		List<Overlay> mapOverlays = mOfflineMap.getOverlays();

		// gets the id of the user.
		RealFarmDatabase.MAIN_USER_ID = mDataProvider.getUserByMobile(
				RealFarmDatabase.DEVICE_ID).getUserId();

		// adds an overlay for each plot found.
		mPlots = mDataProvider.getPlotsList();
		for (int x = 0; x < mPlots.size(); x++) {
			mapOverlays.add(new PlotOverlay(mPlots.get(x)));
		}

		mDataProvider.logAction(Log.MAIN_OPENED, null);
	}

	/**
	 * Create options menu
	 * 
	 * @param Menu
	 *            Android menu object
	 * @return boolean true if options menu created successfully
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mOfflineMap.dispose();
	}

	/**
	 * Detects presses on options menu and creates corresponding activity
	 * 
	 * @param MenuItem
	 *            clicked menu item
	 * @return boolean true if item pressed
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		if( item.getItemId() == R.id.settings ){
			mDataProvider.logAction(Log.MAIN_MENU_SETTINGS_CLICKED, null);
			Intent myIntent = new Intent(OfflineMapDemo.this, Settings.class);
			startActivity(myIntent);
			return true;			
		}
			
		if( item.getItemId() == R.id.help ){
			mDataProvider.logAction(Log.MAIN_MENU_HELP_CLICKED, null);
			// TODO: add help support
			return true;			
		}
//		default:
		return super.onOptionsItemSelected(item);
	}

	public void sendSMS(String phoneNumber, String message) {
		// PendingIntent pi = PendingIntent.getActivity(this, 0, new
		// Intent(this,
		// OfflineMapDemo.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

	private void setUpActionBar() {

		// gets the action bar.
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);

		actionBar.removeAllActions();
		actionBar.setBackgroundColor(Color.LTGRAY);

		// Home action button
		actionBar.setHomeAction(new Action() {
			public int getDrawable() {
				return R.drawable.ic_48px_home;
			}

			public void performAction(View view) {
				mDataProvider.logAction(Log.MAIN_ACTIONBAR_HOME_CLICKED, null);
				// navigates to the center of the map.
//				mOfflineMap.animateTo(new Point(0, 0));
				OfflineMapDemo.this.finish();
			}
		});

		// my field
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_48px_myfields;
			}

			public void performAction(View view) {
				mDataProvider.logAction(Log.MAIN_ACTIONBAR_PLOTS_OPEN, null);
				
				final QuickAction qa = new QuickAction(view);

				// creates an action item for each available plot.
				ActionItem tmpItem;
				for (int x = 0; x < mPlots.size(); x++) {

					// only adds the plot to the list if it belongs to the user.
					if (mPlots.get(x).getOwnerId() == RealFarmDatabase.MAIN_USER_ID) {
						// creates a new item based
						tmpItem = new ActionItem();
						tmpItem.setTitle("Plot " + mPlots.get(x).getId());
						tmpItem.setId(x);
						tmpItem.setIcon(getResources().getDrawable(
								R.drawable.ic_dialog_map));
						tmpItem.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								
								mDataProvider.logAction(Log.MAIN_ACTIONBAR_PLOTS_PLOT_CLICKED, "plotId: "+mPlots.get(v.getId()).getId());

								// animates to the center of the plot
								Point center = mPlots.get(v.getId())
										.getCenterCoordinate();
								mOfflineMap.animateTo(center);
							}
						});
						// adds the item
						qa.addActionItem(tmpItem);
					}
				}
				qa.show();
			}
		});

		// news button in action bar
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_48px_news;
			}

			public void performAction(View view) {
				mDataProvider.logAction(Log.MAIN_ACTIONBAR_NEWS_OPEN, null);
				final QuickAction qa1 = new QuickAction(view);

				ActionItem tem = new ActionItem();
				tem.setTitle("No news yet");
				tem.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						mDataProvider.logAction(Log.MAIN_ACTIONBAR_NEWS_NEWS_CLICKED, null);
					}
				});
				qa1.addActionItem(tem);
				qa1.show();
			}
		});
	}
}