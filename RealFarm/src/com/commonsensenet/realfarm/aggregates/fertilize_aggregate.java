package com.commonsensenet.realfarm.aggregates;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.commonsensenet.realfarm.Global;
import com.commonsensenet.realfarm.HelpEnabledActivityOld;
import com.commonsensenet.realfarm.Homescreen;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;

public class fertilize_aggregate extends HelpEnabledActivityOld implements
		OnLongClickListener {
	/** Database provider used to persist the data. */
	private RealFarmProvider mDataProvider;
	/** Reference to the current instance. */
	private final fertilize_aggregate mParentReference = this;
	boolean liked;
	int aggr_action_no;

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
		Intent adminintent = new Intent(fertilize_aggregate.this,
				Homescreen.class);

		startActivity(adminintent);
		fertilize_aggregate.this.finish();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Fertilizer Aggregate entered");
		mDataProvider = RealFarmProvider.getInstance(this);

		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.fertilizing_dialog);

		super.onCreate(savedInstanceState, R.layout.fertilize_aggregate);
		System.out.println("Fertilizer Aggregate entered");
		setHelpIcon(findViewById(R.id.helpIndicator));
		ImageButton btnLike = (ImageButton) findViewById(R.id.aggr_item_fert_like1);
		System.out.println("Fertilizer Aggregate entered");

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		help.setOnLongClickListener(this);

		final ImageButton fert1_aggr = (ImageButton) findViewById(R.id.variety_fert1_aggr_btn);
		final ImageButton fert2_aggr = (ImageButton) findViewById(R.id.variety_fert2_aggr_btn);
		fert1_aggr.setOnLongClickListener(this);
		fert2_aggr.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent adminintent = new Intent(fertilize_aggregate.this,
						Homescreen.class);

				startActivity(adminintent);
				fertilize_aggregate.this.finish();
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

		btnLike.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (v.getId() == R.id.aggr_item_fert_like1) {

					// for the like button
					if (!liked) {
						v.setBackgroundResource(R.drawable.circular_btn_green);
					}
					/*
					 * else {
					 * v.setBackgroundResource(R.drawable.circular_btn_normal);
					 * } liked = !liked;
					 */}

			}
		});

		ImageButton btnLike2 = (ImageButton) findViewById(R.id.aggr_item_fert_like2);
		System.out.println("Fertilizer Aggregate entered");
		btnLike2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (v.getId() == R.id.aggr_item_fert_like2) {

					// for the like button
					if (!liked) {
						v.setBackgroundResource(R.drawable.circular_btn_green);
					}
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
				// dlg.setTitle("Choose the Number of bags");
				// Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final Button aggr_sow;
				final Button aggr_fert;
				final Button aggr_irr;
				final Button aggr_prob;
				final Button aggr_spray;
				final Button aggr_harvest;
				final Button aggr_sell;
				// final Button variety7;
				final ImageView img_1;
				img_1 = (ImageView) findViewById(R.id.aggr_action_img);

				aggr_sow = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_sow);
				aggr_fert = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_fert);
				aggr_irr = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_irr);
				aggr_prob = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_prob);
				aggr_spray = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_spray);
				aggr_harvest = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_harvest);
				aggr_sell = (Button) dlg
						.findViewById(R.id.action_aggr_icon_btn_sell);

				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_sow))
						.setOnLongClickListener(mParentReference); // audio
																	// integration
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_fert))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_irr))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_prob))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_spray))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_harvest))
						.setOnLongClickListener(mParentReference);
				((Button) dlg.findViewById(R.id.action_aggr_icon_btn_sell))
						.setOnLongClickListener(mParentReference);

				aggr_sow.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						// img_1.setMaxWidth(300);
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
				// dlg.setTitle("Choose the Number of bags");
				// Log.d("in variety sowing dialog", "in dialog");
				dlg.show();

				final View variety1;
				final View variety2;
				final View variety3;
				final View variety4;
				final View variety5;
				final View variety6;

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);

				// gets the available varieties.
				variety1 = dlg.findViewById(R.id.button_variety_1);
				variety2 = dlg.findViewById(R.id.button_variety_2);
				variety3 = dlg.findViewById(R.id.button_variety_3);
				variety4 = dlg.findViewById(R.id.button_variety_4);
				variety5 = dlg.findViewById(R.id.button_variety_5);
				variety6 = dlg.findViewById(R.id.button_variety_6);

				// sets the long click listener to provide help support.
				variety1.setOnLongClickListener(mParentReference);
				variety2.setOnLongClickListener(mParentReference);
				variety3.setOnLongClickListener(mParentReference);
				variety4.setOnLongClickListener(mParentReference);
				variety5.setOnLongClickListener(mParentReference);
				variety6.setOnLongClickListener(mParentReference);

				variety1.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d("var 1 picked ", "in dialog");
						// img_1.setMaxWidth(300);
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

		final Button userslist = (Button) findViewById(R.id.txt_btn_1);
		final Button userslist_2 = (Button) findViewById(R.id.txt_btn_2);
		userslist.setOnLongClickListener(this);
		userslist_2.setOnLongClickListener(this);
		/*
		 * Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		 * 
		 * Integer[] image = { R.drawable.ic_72px_fertilizing2,
		 * R.drawable.ic_72px_fertilizing2, R.drawable.ic_72px_fertilizing2 };
		 * spinner.getLayoutParams().width = 3;
		 * 
		 * // Customise ArrayAdapter spinner.setAdapter(new
		 * SpinnerImgAdapter(this, R.layout.spinner_op, image));
		 */

		// userslist.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// final Dialog dlg = new Dialog(v.getContext());
		// dlg.setContentView(R.layout.user_list);
		// dlg.setCancelable(true);
		// // dlg.setTitle("Choose the Number of bags");
		// // Log.d("in variety sowing dialog", "in dialog");
		// dlg.show();
		//
		// ((Button) dlg.findViewById(R.id.user_1))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_2))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_3))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_4))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_5))
		// .setOnLongClickListener(mParentReference);
		//
		// }
		// });

		// userslist_2.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// final Dialog dlg = new Dialog(v.getContext());
		// dlg.setContentView(R.layout.user_list);
		// dlg.setCancelable(true);
		// // dlg.setTitle("Choose the Number of bags");
		// // Log.d("in variety sowing dialog", "in dialog");
		// dlg.show();
		//
		// ((Button) dlg.findViewById(R.id.user_1))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_2))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_3))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_4))
		// .setOnLongClickListener(mParentReference);
		// ((Button) dlg.findViewById(R.id.user_5))
		// .setOnLongClickListener(mParentReference);
		//
		// }
		// });

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

	protected void cancelaudio() {

		Intent adminintent = new Intent(fertilize_aggregate.this,
				Homescreen.class);

		startActivity(adminintent);
		fertilize_aggregate.this.finish();
	}

	private void changeaction_aggr() {
		// TODO Auto-generated method stub

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

	public boolean onLongClick(View v) {

		if (v.getId() == R.id.variety_fert1_aggr_btn) { // audio integration
			playAudioalways(R.raw.fertilizer1);
			ShowHelpIcon(v);
		}

		if (v.getId() == R.id.variety_fert2_aggr_btn) {
			playAudioalways(R.raw.fertilizer2);
			ShowHelpIcon(v);
		}
		if (v.getId() == R.id.txt_btn_1) { // audio integration
			playAudioalways(R.raw.fertilizer1);
			ShowHelpIcon(v);
		}

		return true;
	}

	protected void cancelAudio() {
		playAudio(R.raw.cancel);
		Intent adminintent = new Intent(fertilize_aggregate.this,
				Homescreen.class);

		startActivity(adminintent);
		fertilize_aggregate.this.finish();
	}

	protected void okAudio() {
		playAudio(R.raw.ok);
	}
}