package com.commonsensenet.realfarm.view;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.Resource;

public class DialogAdapter extends ArrayAdapter<Resource> {

	public DialogAdapter(Context context, int textViewResourceId,
			List<Resource> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.mc_dialog_row, null);
		}
		Resource res = getItem(position);
		if (res != null) {
			TextView tt = (TextView) v.findViewById(R.id.dialog_row_text);
			ImageView im = (ImageView) v.findViewById(R.id.dialog_row_icon);
			LinearLayout ll = (LinearLayout) v
					.findViewById(R.id.dialog_row_layout);
			if (tt != null) {
				tt.setText(res.getName());
			}

			// only adds either the background or the icon, since both
			// are not compatible together.
			int resId = res.getBackgroundResource();
			if (ll != null && resId != -1) {
				ll.setBackgroundResource(resId);
				tt.setTextColor(Color.parseColor("#FFFFFF"));
			} else {

				resId = res.getResource1();
				if (resId != -1) {
					im.setImageResource(resId);
				}
			}
		}
		return v;
	}
}