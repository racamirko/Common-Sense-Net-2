package com.commonsensenet.realfarm.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.AdviceActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;
import com.commonsensenet.realfarm.utils.DateHelper;


public class AdviceAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<AdviceSituationItem> groups;
	private AdviceActivity adviceActivity;
	
	public AdviceAdapter(Context context, ArrayList<AdviceSituationItem> groups, AdviceActivity adviceActivity) {
		this.context = context;
		this.groups = groups;
		this.adviceActivity = adviceActivity;
	}

	public void addItem(AdviceSolutionItem item, AdviceSituationItem group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		ArrayList<AdviceSolutionItem> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
	}
	
	public AdviceSolutionItem getChild(int groupPosition, int childPosition) {
		ArrayList<AdviceSolutionItem> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		AdviceSolutionItem child = (AdviceSolutionItem) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.tpl_ad_solution_row, null);
		}
		
		TextView tv = (TextView) view.findViewById(R.id.comment);
		tv.setText(child.getName().toString());
		tv.setTag(child.getTag());
		
		ImageView like = (ImageView) view.findViewById(R.id.right_image);
		
		like.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				adviceActivity.onLikeClick(groupPosition, childPosition);
			}
		});
		
		like.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				adviceActivity.onLikeLongClick(groupPosition, childPosition);
				return true;
			}
		});
		return view;
	}

	public int getChildrenCount(int groupPosition) {
		ArrayList<AdviceSolutionItem> chList = groups.get(groupPosition).getItems();
		return chList.size();
	}

	public AdviceSituationItem getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
		AdviceSituationItem group = (AdviceSituationItem) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.tpl_ad_situation_row, null);
		}
		
		//group.setAudio(c.getInt(5));
		
		TextView tv;
		tv = (TextView) view.findViewById(R.id.left_text);
		tv.setText(group.getCropShortName());
		if(group.getCropBackground() != -1) tv.setBackgroundResource(group.getCropBackground());
		
		tv = (TextView) view.findViewById(R.id.center_text);
		tv.setText(group.getProblemShortName());
		
		tv = (TextView) view.findViewById(R.id.loss);
		tv.setText(group.getLoss()+" kg/acre");
		
		tv = (TextView) view.findViewById(R.id.percentage);
		tv.setText(group.getChance()+"%");
		
		ImageView iw;
		iw = (ImageView) view.findViewById(R.id.image_left);
		if(group.getProblemImage() != -1) iw.setImageResource(group.getProblemImage());
		
		iw = (ImageView) view.findViewById(R.id.leftImage);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		options.inSampleSize = 12;

		
		// gets the bitmap from the file system.
		Bitmap bitmapImage = BitmapFactory.decodeFile(group.getPlotImage(), options);
		if (bitmapImage != null){
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			Bitmap rotatedImage = Bitmap.createBitmap(bitmapImage, 0, 0,
					bitmapImage.getWidth(), bitmapImage.getHeight(), matrix,
					true);
			iw.setImageBitmap(rotatedImage);
		}
		else iw.setImageResource(R.drawable.plotssection);
		return view;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
