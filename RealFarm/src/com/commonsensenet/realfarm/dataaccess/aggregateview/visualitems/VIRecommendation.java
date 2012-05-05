package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.dataaccess.aggregateview.DummyHomescreenData;
import com.commonsensenet.realfarm.homescreen.aggregateview.AggregateView;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Seed;

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
			element = inflater.inflate(R.layout.vi_recommendation, parent, false);
		
		element.setTag(new Integer(layoutTag));
		// populate elements
        TextView lblDesc = (TextView) element.findViewById(R.id.lbl_desc);
        TextView lblDetail = (TextView) element.findViewById(R.id.lbl_detail);
        ImageView imgDesc = (ImageView) element.findViewById(R.id.img_desc);
        ImageButton btnLike = (ImageButton) element.findViewById(R.id.aggr_item_btn_like);
        Button btnMain = (Button) element.findViewById(R.id.btn_main_click);

        Seed seed = dataProvider.getSeedById(recommendation.getSeed());

        lblDesc.setText(dataProvider.getActionNameById(recommendation.getAction()).getName());
        lblDetail.setText(seed.getFullName());
        imgDesc.setImageResource(dataProvider.getActionNameById(recommendation.getAction()).getRes());
        

        btnLike.setOnClickListener(this);
        btnMain.setOnClickListener(this);

		return element;
	}

	@Override
	public Object getDataItem() {
		return recommendation;
	}
	
	@Override
	public void onClick(View v) {
		Log.d(logTag, "Click detected");
		if(v.getId() == R.id.aggr_item_btn_like){
			 // for the like button
			if(!liked){
				v.setBackgroundResource(R.drawable.circular_btn_green);
			} else {
				v.setBackgroundResource(R.drawable.circular_btn_normal);
			}
			liked = !liked;
		}
		if( v.getId() == R.id.btn_main_click ){
            Dialog dlg = new Dialog(v.getContext());
        	dlg.setContentView(R.layout.vi_recommendation_details);
        	dlg.setCancelable(true);
        	// parts
        	TextView dlgDetals = (TextView) dlg.findViewById(R.id.dlg_lbl_details);
        	ImageView imgAction = (ImageView) dlg.findViewById(R.id.dlg_img_action);
        	ImageView imgSeed = (ImageView) dlg.findViewById(R.id.dlg_img_seed);
        	// 
        	dlg.setTitle( dataProvider.getActionNameById(recommendation.getAction()).getName() );
        	dlgDetals.setText( dataProvider.getActionNameById(recommendation.getAction()).getName() + " " + dataProvider.getSeedById(recommendation.getSeed()).getName() );
        	imgAction.setImageResource( dataProvider.getActionNameById(recommendation.getAction()).getRes() );
        	imgSeed.setImageResource( dataProvider.getSeedById(recommendation.getSeed()).getRes());
        	Log.d(logTag, "Seed res id: "+String.valueOf(dataProvider.getSeedById(recommendation.getSeed()).getRes()));
        	dlg.show();

		}
	}
	
	@Override
	public int getLayoutTag(){
		return layoutTag;
	}

}
