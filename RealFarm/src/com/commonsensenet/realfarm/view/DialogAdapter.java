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

	private Context mContext;

	public DialogAdapter(Context context, int textViewResourceId,
			List<Resource> items) {
		super(context, textViewResourceId, items);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.mc_dialog_row, null);
		}
		Resource res = getItem(position);
		if (res != null) {
			TextView tt = (TextView) v.findViewById(R.id.dialog_row_text);
			ImageView im2 = (ImageView) v.findViewById(R.id.dialog_row_icon);
			TextView im = (TextView) v.findViewById(R.id.dialog_row_text2);
			LinearLayout ll = (LinearLayout) v
					.findViewById(R.id.dialog_row_layout);
			if (tt != null) {
				tt.setText(res.getName());
			}
			if (im2 != null) {
				int id = res.getResource2();
				if (id != -1) {
					im2.setImageResource(id);
				}
			}
			if (im != null) {
				int id = res.getResource1();
				if (id != -1) {
					im.setBackgroundResource(id);
				}
				// if (res.getNumber() != -1) {
				// im.setText(res.getNumber() + "");
				// }
			}
			if (ll != null) {
				int id = res.getBackgroundResource();
				if (id != -1) {
					ll.setBackgroundResource(id);
					tt.setTextColor(Color.parseColor("#FFFFFF"));
				}
			}
		}
		return v;
	}
}