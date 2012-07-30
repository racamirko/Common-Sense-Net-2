package com.commonsensenet.realfarm.view;

import com.commonsensenet.realfarm.model.DialogData;
import com.commonsensenet.realfarm.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class DialogAdapter extends ArrayAdapter<DialogData> {

	private ArrayList<DialogData> items;
	private Context c;

	public DialogAdapter(Context context, int textViewResourceId, ArrayList<DialogData> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.c = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.mc_dialog_row, null);
		}
		DialogData dd = items.get(position);
		if (dd != null) {
			TextView tt = (TextView) v.findViewById(R.id.text);
			ImageView im2 = (ImageView) v.findViewById(R.id.icon2);
			TextView im = (TextView) v.findViewById(R.id.text2);
			LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll);
			if (tt != null) {
				tt.setText(dd.getName());                            
			}
			if(im2 != null){
				int id = dd.getImage2Res();
				//id = c.getResources().getIdentifier("com.commonsensenet.realfarm:drawable/" + dd.getImage2(), null, null);
				if(id != -1) im2.setImageResource(id);
			}
			if(im != null){     
				int id = dd.getImageRes();
				//id = c.getResources().getIdentifier("com.commonsensenet.realfarm:drawable/" + dd.getImage(), null, null);
				if(id != -1) im.setBackgroundResource(id);
				if(dd.getNumber() != -1) im.setText(dd.getNumber()+""); 
			}
			if(ll != null){     
				int id = dd.getBackgroundRes();
				//id = c.getResources().getIdentifier("com.commonsensenet.realfarm:drawable/" + dd.getBackground(), null, null);
				if(id != -1){
					ll.setBackgroundResource(id);
					tt.setTextColor(Color.parseColor("#FFFFFF"));   
				}
			}
		}
		return v;
	} 
} 