package com.commonsensenet.realfarm;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;
import com.commonsensenet.realfarm.utils.ActionDataFactory;
import com.commonsensenet.realfarm.utils.ApplicationTracker;
import com.commonsensenet.realfarm.utils.ApplicationTracker.EventType;

public class ActionAggregateActivity extends AggregateMarketActivity implements OnLongClickListener {


	/** LayoutInflater used to create the content of the details dialog. */
	/** Reference to the current instance. */
	private final Context context = this;


	protected void cancelAudio() {

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));
	}

	public void onBackPressed() {

		// stops all active audio.
		stopAudio();

		// tracks the application usage.
		ApplicationTracker.getInstance().logEvent(EventType.CLICK, getLogTag(),
				"back");

		startActivity(new Intent(ActionAggregateActivity.this, Homescreen.class));

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// extracts the passed parameters
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(RealFarmDatabase.TABLE_NAME_ACTIONTYPE)) {
			// gets the action name id
			mActionTypeId = extras
					.getInt(RealFarmDatabase.TABLE_NAME_ACTIONTYPE);
		}

		super.onCreate(savedInstanceState, R.layout.tpl_aggregate, context);
		currentAction = RealFarmDatabase.LIST_WITH_TOP_SELECTOR_TYPE_AGGREGATE;

		// default seed/crop type id
		// TODO: if user doesn't have a plot
		topSelectorData = ActionDataFactory.getTopSelectorData(mActionTypeId, mDataProvider, Global.userId);
		setList();

		final ImageButton home = (ImageButton) findViewById(R.id.aggr_img_home);
		final ImageButton help = (ImageButton) findViewById(R.id.aggr_img_help);
		final View crop = findViewById(R.id.aggr_crop);
		Button back = (Button) findViewById(R.id.button_back);


		home.setOnLongClickListener(this);
		back.setOnLongClickListener(this);
		help.setOnLongClickListener(this);
		crop.setOnLongClickListener(this);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startActivity(new Intent(ActionAggregateActivity.this,
						Homescreen.class));

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "home");
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancelAudio();

				// tracks the application usage.
				ApplicationTracker.getInstance().logEvent(EventType.CLICK,
						getLogTag(), "back");

			}
		});

		crop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final ImageView img_1 = (ImageView) findViewById(R.id.aggr_crop_img);
				List<Resource> data = ActionDataFactory.getTopSelectorList(mActionTypeId, mDataProvider);
				displayDialog(v, data, "Select the variety", R.raw.problems,img_1, 2);
			}
		});
		
	}

	// TODO: put audio
	public boolean onLongClick(View v) {

		if (v.getId() == R.id.aggr_img_home) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_crop) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.aggr_img_help) {
			playAudio(R.raw.problems, true);
		} else if (v.getId() == R.id.button_back) {
			playAudio(R.raw.problems, true);
		}

		return true;
	}

	
}
