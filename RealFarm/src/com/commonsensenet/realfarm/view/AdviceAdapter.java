package com.commonsensenet.realfarm.view;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonsensenet.realfarm.AdviceActivity;
import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.aggregate.AdviceSituationItem;
import com.commonsensenet.realfarm.model.aggregate.AdviceSolutionItem;

public class AdviceAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<AdviceSituationItem> groups;
	private AdviceActivity adviceActivity;

	public AdviceAdapter(Context context,
			ArrayList<AdviceSituationItem> groups, AdviceActivity adviceActivity) {
		this.context = context;
		this.groups = groups;
		this.adviceActivity = adviceActivity;
	}

	public void setGroups(ArrayList<AdviceSituationItem> gr) {
		groups = gr;
	}

	public ArrayList<AdviceSituationItem> getGroups() {
		return groups;
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
		ArrayList<AdviceSolutionItem> chList = groups.get(groupPosition)
				.getItems();
		return chList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		AdviceSituationItem group = (AdviceSituationItem) getGroup(groupPosition);
		AdviceSolutionItem child = (AdviceSolutionItem) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.tpl_ad_solution_row, null);
		}

		// child.getAudio();
		setSolutionView(view, child, group, groupPosition, childPosition);

		return view;
	}

	private void setSolutionView(View view, AdviceSolutionItem child,
			AdviceSituationItem group, final int groupPosition,
			final int childPosition) {
		RelativeLayout rl = (RelativeLayout) view
				.findViewById(R.id.left_background);
		if (child.getPesticideBackground() != -1)
			rl.setBackgroundResource(child.getPesticideBackground());
		else
			rl.setBackgroundColor(Color.TRANSPARENT);

		ImageView iw = (ImageView) view.findViewById(R.id.image_left);
		if (child.getPesticideImage() != -1)
			iw.setImageResource(child.getPesticideImage());
		else
			iw.setImageResource(Color.TRANSPARENT);

		TextView tv = (TextView) view.findViewById(R.id.left_text);
		tv.setText(child.getPesticideShortName());

		tv = (TextView) view.findViewById(R.id.number);
		tv.setText(String.valueOf(child.getNumber()));

		tv = (TextView) view.findViewById(R.id.center_left_text);
		tv.setText(String.valueOf(child.getDidIt()));

		tv = (TextView) view.findViewById(R.id.comment);
		tv.setText(child.getComment());

		tv = (TextView) view.findViewById(R.id.center_right_text);
		tv.setText(String.valueOf(child.getLikes()));

		ImageView like = (ImageView) view.findViewById(R.id.right_image);

		if (child.getPesticideShortName().equals("")) {
			tv.setVisibility(View.GONE);
			like.setVisibility(View.GONE);
		} else {
			tv.setVisibility(View.VISIBLE);
			if (group.getValidDate() < new Date().getTime()) {
				like.setVisibility(View.GONE);
			} else {
				like.setVisibility(View.VISIBLE);
				like.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						adviceActivity
								.onLikeClick(groupPosition, childPosition);
					}
				});

				like.setOnLongClickListener(new View.OnLongClickListener() {
					public boolean onLongClick(View v) {
						adviceActivity.onLikeLongClick(groupPosition,
								childPosition);
						return true;
					}
				});
				if (child.getHasLiked())
					like.setImageResource(R.drawable.plantofollowafterclick);
				else
					like.setImageResource(R.drawable.plantofollowbeforeclick);
			}
		}

		if (group.getValidDate() < new Date().getTime())
			setDatePassed((LinearLayout) view.findViewById(R.id.solution_row),
					0.5F);
		else
			setDatePassed((LinearLayout) view.findViewById(R.id.solution_row),
					1.0F);
	}

	public int getChildrenCount(int groupPosition) {
		ArrayList<AdviceSolutionItem> chList = groups.get(groupPosition)
				.getItems();
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

	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		AdviceSituationItem group = (AdviceSituationItem) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.tpl_ad_situation_row, null);
		}

		// group.setAudio(c.getInt(5));

		setSituationView(view, group);
		return view;
	}

	private void setSituationView(View view, AdviceSituationItem group) {
		TextView tv;
		tv = (TextView) view.findViewById(R.id.left_text);
		tv.setTypeface(null, Typeface.NORMAL);
		tv.setText(group.getCropShortName());
		if (group.getCropBackground() != -1)
			tv.setBackgroundResource(group.getCropBackground());

		tv = (TextView) view.findViewById(R.id.center_text);
		tv.setTypeface(null, Typeface.NORMAL);
		tv.setText(group.getProblemShortName());

		tv = (TextView) view.findViewById(R.id.loss);
		tv.setTypeface(null, Typeface.NORMAL);
		tv.setText(group.getLoss() + " kg/acre");

		tv = (TextView) view.findViewById(R.id.percentage);
		tv.setTypeface(null, Typeface.NORMAL);
		tv.setText(group.getChance() + "%");

		ImageView iw;
		iw = (ImageView) view.findViewById(R.id.image_left);
		if (group.getProblemImage() != -1)
			iw.setImageResource(group.getProblemImage());

		iw = (ImageView) view.findViewById(R.id.leftImage);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[16 * 1024];
		options.inSampleSize = 12;

		Bitmap bitmapImage = BitmapFactory.decodeFile(group.getPlotImage(),
				options);
		if (bitmapImage != null) {
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			Bitmap rotatedImage = Bitmap.createBitmap(bitmapImage, 0, 0,
					bitmapImage.getWidth(), bitmapImage.getHeight(), matrix,
					true);
			iw.setImageBitmap(rotatedImage);
		} else {
			iw.setImageResource(R.drawable.ic_plots);

		}

		LinearLayout ll = (LinearLayout) view.findViewById(R.id.situation_row);
		if (group.getValidDate() < new Date().getTime()) {
			setDatePassed(ll, 0.5F);
		} else {
			setDatePassed(ll, 1.0F);
			if (group.getUnread() == 0) {
				((TextView) (view.findViewById(R.id.left_text))).setTypeface(
						null, Typeface.BOLD);
				((TextView) (view.findViewById(R.id.center_text))).setTypeface(
						null, Typeface.BOLD);
				((TextView) (view.findViewById(R.id.loss))).setTypeface(null,
						Typeface.BOLD);
				((TextView) (view.findViewById(R.id.percentage))).setTypeface(
						null, Typeface.BOLD);
			}
		}
	}

	public void setDatePassed(View ll, float anim) {
		// ll.setBackgroundColor(Color.LTGRAY);
		AlphaAnimation alpha = new AlphaAnimation(anim, anim);
		alpha.setDuration(0);
		alpha.setFillAfter(true);
		ll.startAnimation(alpha);
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
