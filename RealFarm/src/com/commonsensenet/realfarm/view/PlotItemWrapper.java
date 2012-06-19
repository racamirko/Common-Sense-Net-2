package com.commonsensenet.realfarm.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Seed;

public class PlotItemWrapper {
	private TextView mDescription;
	private ImageView mIcon;
	private TextView mTitle;
	private View mRow;

	public PlotItemWrapper(View row) {
		// the View object that represents a single row
		this.mRow = row;
	}

	public TextView getDescription() {
		if (mDescription == null) {
			mDescription = (TextView) mRow
					.findViewById(R.id.label_plot_description);
		}
		return (mDescription);
	}

	public ImageView getIcon() {
		if (mIcon == null) {
			mIcon = (ImageView) mRow.findViewById(R.id.icon_plot);
		}
		return (mIcon);
	}

	public TextView getTitle() {

		if (mTitle == null) {
			mTitle = (TextView) mRow.findViewById(R.id.label_plot_title);
		}
		return (mTitle);
	}

	public void populateFrom(Plot plot, RealFarmProvider provider) {

		// TODO: this shouldn't be done here due to performance issues!!!
		Seed seed = provider.getSeedById(plot.getSeedTypeId());

		getTitle().setText(plot.getSoilType());
		getDescription().setText(seed.getName());
		getIcon().setImageResource(seed.getRes());
		// TODO: use correct icon and rest of the values.
		// if (r.getType() == MenuItem.SCAN) { // Here we use the item type to
		// // associate the right icon
		// getIcon().setImageResource(R.drawable.ico_scan);
		// } else if (r.getType() == MenuItem.SEARCH) {
		// getIcon().setImageResource(R.drawable.ico_search);
		// } else if (r.getType() == MenuItem.HELP) {
		// getIcon().setImageResource(R.drawable.ico_help);
		// }
	}
}
