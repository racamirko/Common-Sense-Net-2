package com.commonsensenet.realfarm.aggregates;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.AggregateItem;
import com.commonsensenet.realfarm.model.UserAggregateItem;
import com.commonsensenet.realfarm.view.AggregateItemAdapter;
import com.commonsensenet.realfarm.view.UserAggregateItemAdapter;

public class sowing_aggregate extends HelpEnabledActivityOld implements
		OnLongClickListener, OnItemClickListener {
	private int aggr_action_no;

	/** ListAdapter used to handle the aggregates. */
	private AggregateItemAdapter mAggregateItemAdapter;
	/** ListView where the aggregate elements are shown. */
	private ListView mAggregatesListView;
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** Reference to the current instance. */
	private final sowing_aggregate mParentReference = this;

	protected void cancelaudio() {

		Intent adminintent = new Intent(sowing_aggregate.this, Homescreen.class);

		startActivity(adminintent);
		sowing_aggregate.this.finish();
	}

	private void changeaction_aggr() {

		if (aggr_action_no == 1) {
			Intent inte = new Intent(mParentReference, sowing_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

		if (aggr_action_no == 2) {
			Intent inte = new Intent(mParentReference,
					fertilize_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

		if (aggr_action_no == 3) {
			Intent inte = new Intent(mParentReference, irrigate_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

		if (aggr_action_no == 4) {
			Intent inte = new Intent(mParentReference, problem_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

		/*
		 * if(aggr_action_no == 2) { Intent inte = new Intent(mParentReference,
		 * spraying_aggregate.class); inte.putExtra("type", "yield");
		 * this.startActivity(inte); this.finish(); }
		 */
		if (aggr_action_no == 6) {
			Intent inte = new Intent(mParentReference, harvest_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

		if (aggr_action_no == 7) {
			Intent inte = new Intent(mParentReference, selling_aggregate.class);
			inte.putExtra("type", "yield");
			this.startActivity(inte);
			this.finish();
		}

	}

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		if (Global.writeToSD == true) {
			String logtime = getCurrentTime();
			mDataProvider.File_Log_Create("UIlog.txt", logtime + " -> ");
			mDataProvider.File_Log_Create("UIlog.txt", " Fertilizing "
					+ " Softkey " + " click " + " Back_button " + " null "
					+ " \r\n");
		}
		Intent adminintent = new Intent(sowing_aggregate.this, Homescreen.class);

		startActivity(adminintent);
		sowing_aggregate.this.finish();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.sowing_aggregate);

		// gets the data provider
		mDataProvider = RealFarmProvider.getInstance(this);

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

		// ImageButton btnLike = (ImageButton)
		// findViewById(R.id.aggr_item_sow_like1);

		// btnLike.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// if (v.getId() == R.id.aggr_item_sow_like1) {
		//
		// // for the like button
		// if (!liked) {
		// v.setBackgroundResource(R.drawable.circular_btn_green);
		// }
		// }
		//
		// }
		// });

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(sowing_aggregate.this,
						Homescreen.class);

				startActivity(adminintent);
				sowing_aggregate.this.finish();
				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user has clicked on home btn  in harvest*********** \r\n");
				}
			}
		});

		final Button action = (Button) findViewById(R.id.aggr_action);
		final Button crop = (Button) findViewById(R.id.aggr_crop);

		action.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

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

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_action_img);

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
						changeaction_aggr();
						dlg.cancel();
					}
				});

				aggr_fert.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_fertilize);
						aggr_action_no = 2;
						changeaction_aggr();
						dlg.cancel();
					}

				});

				aggr_irr.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_irrigate);
						aggr_action_no = 3;
						changeaction_aggr();
						dlg.cancel();
					}
				});

				aggr_prob.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_problem);
						aggr_action_no = 4;
						changeaction_aggr();
						dlg.cancel();
					}
				});
				aggr_spray.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_spray);
						aggr_action_no = 5;
						changeaction_aggr();
						dlg.cancel();
					}
				});
				aggr_harvest.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_harvest);
						aggr_action_no = 6;
						changeaction_aggr();
						dlg.cancel();
					}
				});

				aggr_sell.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						img_1.setImageResource(R.drawable.ic_sell);
						aggr_action_no = 7;
						changeaction_aggr();
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

		Button back = (Button) findViewById(R.id.button_back);
		back.setOnLongClickListener(this);

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelaudio();

				if (Global.writeToSD == true) {

					String logtime = getcurrenttime();
					mDataProvider
							.File_Log_Create("UIlog.txt", logtime + " -> ");
					mDataProvider
							.File_Log_Create("UIlog.txt",
									"***** user selected cancel in harvest*********** \r\n");

				}
			}

		});

	}

	public boolean onLongClick(View v) {

		return super.onLongClick(v);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// gets the selected view using the position
		AggregateItem selectedItem = mAggregateItemAdapter.getItem(position);

		// dialog used to request the information
		final Dialog dialog = new Dialog(this);

		ListView userListView = new ListView(this);
		List<UserAggregateItem> list = mDataProvider.getUserAggregateItem(
				selectedItem.getActionNameId(), selectedItem.getSeedTypeId());
		UserAggregateItemAdapter userAdapter = new UserAggregateItemAdapter(
				this, list, mDataProvider);
		// sets the adapter.
		userListView.setAdapter(userAdapter);

		// adds the event listener to detect the language selection.
		userListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// stores the selected language.
				// mSelectedLanguage = (String) parent.getAdapter().getItem(
				// position);

				// Toast.makeText(sowi.this,
				// "The Language selected is " + mSelectedLanguage,
				// Toast.LENGTH_SHORT).show();

				// closes the dialog.
				dialog.dismiss();
			}

		});

		// sets the view
		dialog.setContentView(userListView);
		// sets the properties of the dialog.
		dialog.setTitle("%MISSING TITTLE%");
		dialog.setCancelable(true);

		// displays the dialog.
		dialog.show();
	}
}