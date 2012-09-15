package com.commonsensenet.realfarm.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Resource;

public class DialogAdapter extends ArrayAdapter<Resource> {

	public DialogAdapter(Context context, List<Resource> items) {
		super(context, android.R.layout.simple_list_item_1, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tpl_dialog_item, null);
		}
		Resource res = getItem(position);
		if (res != null) {
			TextView tt = (TextView) v.findViewById(R.id.dialog_row_text);
			ImageView im = (ImageView) v.findViewById(R.id.dialog_row_icon);
			ImageView im2 = (ImageView) v.findViewById(R.id.dialog_row_icon2);
			LinearLayout ll = (LinearLayout) v
					.findViewById(R.id.dialog_row_layout);
			if (tt != null) {
				tt.setText(res.getName());
				// if this month, set to bold
				if (res.getType() == RealFarmDatabase.RESOURCE_TYPE_MONTH) {
					SimpleDateFormat df = new SimpleDateFormat("MMM");
					Date date;
					try {
						date = df.parse(res.getShortName());
						if (date.getMonth() == Calendar.getInstance().get(
								Calendar.MONTH)) {
							tt.setTypeface(null, Typeface.BOLD);
						} else {
							tt.setTypeface(null, Typeface.NORMAL);
						}
					} catch (ParseException e) {
					}
				}
			}

			// only adds either the background or the icon, since both
			// are not compatible together.
			int resId = res.getBackgroundImage();
			if (ll != null && resId != -1) {
				ll.setBackgroundResource(resId);
				tt.setTextColor(Color.parseColor("#FFFFFF"));
				// hides both icons.
				im.setVisibility(View.GONE);
				im2.setVisibility(View.GONE);
			} else {

				resId = res.getImage1();
				if (resId != -1) {
					im.setImageResource(resId);
					im.setVisibility(View.VISIBLE);
				} else {
					im.setVisibility(View.GONE);
				}

				// adds the second image if available.
				int res2id = res.getImage2();
				if (res2id != -1) {
					im2.setImageResource(res2id);
					im2.setVisibility(View.VISIBLE);
				} else {
					im2.setVisibility(View.GONE);
				}
			}
		}
		return v;
	}
}