package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.model.Recommendation;

public class VIRecommendation extends VisualItemBase {
	private String logTag = "VIRecommendation";
	private static final int layoutTag = 1;

	protected Recommendation recommendation;
	protected boolean liked; // TODO should be moved to the data level

	public VIRecommendation(Recommendation recommendation, RealFarmProvider dataProvider){
		super(dataProvider);
		this.recommendation = recommendation;
		Log.d(logTag, "created");
	}

	@Override
	public View populateView(View view, ViewGroup parent, LayoutInflater inflater){
		Log.d(logTag, "populateView");
		View element;
		if( view != null && (Integer) view.getTag() == layoutTag )
			element = view;
		else
			element = inflater.inflate(R.layout.aggregate_item, parent, false);
		
		element.setTag(new Integer(layoutTag));
		// populate elements
        TextView lblDesc = (TextView) element.findViewById(R.id.lbl_desc);
        TextView lblDetail = (TextView) element.findViewById(R.id.lbl_detail);
        ImageView imgDesc = (ImageView) element.findViewById(R.id.img_desc);
        ImageButton btnLike = (ImageButton) element.findViewById(R.id.aggr_item_btn_like);

        lblDesc.setText( dataProvider.getActionNameById(recommendation.getAction()).getName());
        lblDetail.setText( dataProvider.getSeedById(recommendation.getSeed()).getName());
        imgDesc.setImageResource(dataProvider.getActionNameById(recommendation.getAction()).getRes()); 
        
        btnLike.setOnClickListener(this);

		return element;
	}

	@Override
	public Object getDataItem() {
		return recommendation;
	}
	
	@Override
	public void onClick(View v) {
		 // for the like button
		if(!liked){
			v.setBackgroundResource(R.drawable.circular_btn_green);
		} else {
			v.setBackgroundResource(R.drawable.circular_btn_normal);
		}
		liked = !liked;
	}
	
	@Override
	public int getLayoutTag(){
		return layoutTag;
	}

}
