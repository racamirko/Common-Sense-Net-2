package com.commonsensenet.realfarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final int[] images;
	Resources res;
	
	
	public MySimpleArrayAdapter(Context context, String[] values,int[] images) {
		super(context, R.layout.plot_list1, values);
		this.context = context;
		this.values = values;
		this.images = images;
		res = context.getResources();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.plot_image1, parent, false);
		
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		
		textView.setText(values[position]);
		
	
		
		//Drawable d = res.getDrawable(R.drawable.plot_3);
		//	imageView.setImageDrawable(d);
		Drawable d = res.getDrawable(images[position]);
		
		imageView.setImageDrawable(d);
		
			
		
		return rowView;
	}
	
	public void onClick(View v) {
	System.out.println("CLICKED!! ");
}
}
