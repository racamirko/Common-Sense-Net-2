package com.commonsensenet.realfarm;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.AggregateItem;
import com.commonsensenet.realfarm.model.UserAggregateItem;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.AggregateItemWrapper;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class ActionAggregateActivity extends HelpEnabledActivityOld implements
		OnLongClickListener, OnItemClickListener {
	/** Name used to log the activity of the class. */
	public static final String LOG_TAG = "sowing_aggregate";

	protected int aggr_action_no;
	/** ListAdapter used to handle the aggregates. */
	private AggregateItemAdapter mAggregateItemAdapter;
	/** ListView where the aggregate elements are shown. */
	private ListView mAggregatesListView;
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** LayoutInflater used to create the content of the details dialog. */
	private LayoutInflater mLayoutInflater;
	/** Reference to the current instance. */
	private final ActionAggregateActivity mParentReference = this;

	protected void cancelAudio() {

		Intent adminintent = new Intent(ActionAggregateActivity.this,
				Homescreen.class);

		startActivity(adminintent);
	}

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, LOG_TAG,
				"back");

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.sowing_aggregate);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);
		// gets the inflater used to populate the dialog.
		mLayoutInflater = getLayoutInflater();

		// gets the aggregates from the database.
		List<AggregateItem> aggregates = mDataProvider.getAggregateItems(3);

		mAggregateItemAdapter = new AggregateItemAdapter(this, aggregates,
				mDataProvider);

		// gets the list from the UI.
		mAggregatesListView = (ListView) findViewById(R.id.list_aggregates);
		// enables the focus on the items.
		mAggregatesListView.setItemsCanFocus(false);
		// sets the custom adapter.
		mAggregatesListView.setAdapter(mAggregateItemAdapter);
		// sets the listener
		mAggregatesListView.setOnItemClickListener(this);

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(ActionAggregateActivity.this,
						Homescreen.class));

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "home");
			}
		});

		Button back = (Button) findViewById(R.id.button_back);
		back.setOnLongClickListener(this);

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						LOG_TAG, "back");

			}
		});

		final View action = findViewById(R.id.aggr_action);
		final View crop = findViewById(R.id.aggr_crop);

		final ImageView img_1 = (ImageView) findViewById(R.id.aggr_action_img);

		action.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// creates the dialog.
				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.action_aggr_sel_dialog);
				dlg.setCancelable(true);
				dlg.show();

				final View aggr_sow;
				final View aggr_fert;
				final View aggr_irr;
				final View aggr_prob;
				final View aggr_spray;
				final View aggr_harvest;
				final View aggr_sell;

				aggr_sow = dlg.findViewById(R.id.action_aggr_icon_btn_sow);
				aggr_fert = dlg.findViewById(R.id.action_aggr_icon_btn_fert);
				aggr_irr = dlg.findViewById(R.id.action_aggr_icon_btn_irr);
				aggr_prob = dlg.findViewById(R.id.action_aggr_icon_btn_prob);
				aggr_spray = dlg.findViewById(R.id.action_aggr_icon_btn_spray);
				aggr_harvest = dlg
						.findViewById(R.id.action_aggr_icon_btn_harvest);
				aggr_sell = dlg.findViewById(R.id.action_aggr_icon_btn_sell);

				// adds the long click event to provide help support.
				aggr_sow.setOnLongClickListener(mParentReference);
				aggr_fert.setOnLongClickListener(mParentReference);
				aggr_irr.setOnLongClickListener(mParentReference);
				aggr_prob.setOnLongClickListener(mParentReference);
				aggr_spray.setOnLongClickListener(mParentReference);
				aggr_harvest.setOnLongClickListener(mParentReference);
				aggr_sell.setOnLongClickListener(mParentReference);

				aggr_sow.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_sow);
						aggr_action_no = 1;
						dlg.cancel();
					}
				});

				aggr_fert.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_fertilize);
						aggr_action_no = 2;
						dlg.cancel();
					}

				});

				aggr_irr.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_irrigate);
						aggr_action_no = 3;
						dlg.cancel();
					}
				});

				aggr_prob.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_problem);
						aggr_action_no = 4;
						dlg.cancel();
					}
				});
				aggr_spray.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_spray);
						aggr_action_no = 5;
						dlg.cancel();
					}
				});
				aggr_harvest.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_harvest);
						aggr_action_no = 6;
						dlg.cancel();
					}
				});

				aggr_sell.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_sell);
						aggr_action_no = 7;
						dlg.cancel();
					}
				});

			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final Dialog dlg = new Dialog(v.getContext());
				dlg.setContentView(R.layout.dialog_variety);
				dlg.setCancelable(true);
				dlg.show();

				final View variety1;
				final View variety2;
				final View variety3;
				final View variety4;
				final View variety5;
				final View variety6;

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);

				variety1 = dlg.findViewById(R.id.button_variety_1);
				variety2 = dlg.findViewById(R.id.button_variety_2);
				variety3 = dlg.findViewById(R.id.button_variety_3);
				variety4 = dlg.findViewById(R.id.button_variety_4);
				variety5 = dlg.findViewById(R.id.button_variety_5);
				variety6 = dlg.findViewById(R.id.button_variety_6);

				variety1.setOnLongClickListener(mParentReference);
				variety2.setOnLongClickListener(mParentReference);
				variety3.setOnLongClickListener(mParentReference);
				variety4.setOnLongClickListener(mParentReference);
				variety5.setOnLongClickListener(mParentReference);
				variety5.setOnLongClickListener(mParentReference);

				variety1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_bajra);

						dlg.cancel();
					}
				});

				variety2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 2 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_castor);

						dlg.cancel();
					}
				});

				variety3.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_cowpea);
						dlg.cancel();
					}
				});

				variety4.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_greengram);

						dlg.cancel();
					}
				});
				variety5.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_groundnut);

						dlg.cancel();
					}
				});
				variety6.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 3 picked ", "in dialog");
						img_1.setImageResource(R.drawable.pic_72px_horsegram);

						dlg.cancel();
					}
				});

			}
		});
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// gets the selected view using the position
		AggregateItem selectedItem = mAggregateItemAdapter.getItem(position);
		AggregateItemWrapper selectedItemView = new AggregateItemWrapper(view);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		// loads the dialog layout
		View layout = mLayoutInflater.inflate(
				R.layout.dialog_aggregate_details, null);

		// gets the ListView from the layout
		ListView userListView = (ListView) layout
				.findViewById(R.id.list_dialog_aggregate);

		// adds the event to dismiss the dialog.
		layout.findViewById(R.id.button_back).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						// closes the dialog.
						dialog.dismiss();

					}
				});

		// sets the data of the header using the old view.
		((ImageView) layout.findViewById(R.id.icon_dialog_aggregate_crop))
				.setImageDrawable(selectedItemView.getCropImage().getDrawable());
		((TextView) layout.findViewById(R.id.label_dialog_aggregate_type))
				.setText(selectedItemView.getSeedType().getText());

		// gets the data and data adapter.
		List<UserAggregateItem> list = mDataProvider.getUserAggregateItem(
				selectedItem.getActionNameId(), selectedItem.getSeedTypeId());
		UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
				this, list, mDataProvider);
		// sets the adapter.
		userListView.setAdapter(userAdapter);

		// disables the title in the dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// sets the view
		dialog.setContentView(layout);
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	}

	public boolean onLongClick(View v) {

		return super.onLongClick(v);
	}
}