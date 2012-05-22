package com.commonsensenet.realfarm.dataaccess.aggregateview.visualitems;

import java.util.Iterator;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmProvider;
import com.commonsensenet.realfarm.homescreen.HelpEnabledActivity;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;
import com.commonsensenet.realfarm.model.aggregate.AggregateRecommendation;
import com.commonsensenet.realfarm.utils.FlowLayout;
import com.commonsensenet.realfarm.utils.SoundQueue;

public class VIAggrRecommendation extends VisualItemBase {
	private String logTag = "VIAggrRecommendation";
	private static final int layoutTag = 2;

	protected AggregateRecommendation aggrRec;
	protected boolean liked; // TODO should be moved to the data level

	public VIAggrRecommendation(AggregateRecommendation aggrRec, Context ctx, RealFarmProvider dataProvider){
		super(ctx,dataProvider);
		this.aggrRec = aggrRec;
		Log.d(logTag, "created");
	}

	@Override
	public View populateView(View view, ViewGroup parent, LayoutInflater inflater){
		Log.d(logTag, "populateView");
		View element;
		if( view != null && (Integer) view.getTag() == layoutTag )
			element = view;
		else
			element = inflater.inflate(R.layout.vi_aggr_recommendation, parent, false);
		
		element.setTag(new Integer(layoutTag));
		// populate elements
		TextView lblCountPeople = (TextView) element.findViewById(R.id.lbl_count_people);
        ImageView imgAction = (ImageView) element.findViewById(R.id.img_action);
        ImageView imgPlant = (ImageView) element.findViewById(R.id.img_plant);
        ImageButton btnLike = (ImageButton) element.findViewById(R.id.aggr_item_btn_like);
        Button btnMain = (Button) element.findViewById(R.id.btn_main_click);

        Seed seed = dataProvider.getSeedById(aggrRec.getSeed());

        lblCountPeople.setText(String.valueOf(aggrRec.getUserIds().size())+"  ");
        imgAction.setImageResource(dataProvider.getActionNameById(aggrRec.getAction()).getRes());
        imgPlant.setImageResource(seed.getResBg());

        btnLike.setOnClickListener(this);
        btnMain.setOnClickListener(this);

		return element;
	}

	@Override
	public Object getDataItem() {
		return aggrRec;
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
        	dlg.setContentView(R.layout.vi_aggr_recommendation_details);
        	dlg.setCancelable(true);
        	Typeface kannadaTypeface = ((HelpEnabledActivity) ctx).getKannadaTypeface();
        	// parts
        	TextView lblDetals = (TextView) dlg.findViewById(R.id.dlg_lbl_details);
        	ImageView imgAction = (ImageView) dlg.findViewById(R.id.dlg_img_action);
        	ImageView imgSeed = (ImageView) dlg.findViewById(R.id.dlg_img_seed);
        	FlowLayout peopleList = (FlowLayout) dlg.findViewById(R.id.dlg_linlay_userDataDetails);
        	ImageButton btnSound = (ImageButton) dlg.findViewById(R.id.dlg_btn_audio_play);
        	// 
        	lblDetals.setTypeface(kannadaTypeface);
        	lblDetals.setText( dataProvider.getActionNameById(aggrRec.getAction()).getNameKannada() + " " + dataProvider.getSeedById(aggrRec.getSeed()).getNameKannada() );
        	imgAction.setImageResource( dataProvider.getActionNameById(aggrRec.getAction()).getRes() );
        	imgSeed.setImageResource( dataProvider.getSeedById(aggrRec.getSeed()).getResBg());
        	// fill user list
        	Iterator<Integer> iterPeople = aggrRec.getUserIds().iterator();
        	
        	while( iterPeople.hasNext() ){
        		Integer usrId = iterPeople.next();
        		ImageButton btnFarmer = (ImageButton) dlg.getLayoutInflater().inflate(R.layout.element_farmer, peopleList, false);
        		User usr = dataProvider.getUserById(usrId);
        		
//        		int resID = dlg.getContext().getResources().getIdentifier(usr.getUserImgName(), "drawable", "com.commonsensenet.realfarm");
        		String folder = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        		btnFarmer.setImageBitmap(BitmapFactory.decodeFile(folder+"/"+usr.getUserImgName()));
        		btnFarmer.setOnClickListener(this);
        		btnFarmer.setTag(usrId);
        		peopleList.addView(btnFarmer);
        	}
        	
        	btnSound.setOnClickListener(this);
        	
        	Log.d(logTag, "Seed res id: "+String.valueOf(dataProvider.getSeedById(aggrRec.getSeed()).getRes()));
        	dlg.show();
		}
		
		if( v.getId() == R.id.dlg_btn_audio_play ){
			playAudio();
		}
		
		if( v.getId() == R.id.btn_farmer ){
			Integer usrId = (Integer) v.getTag();
			String audioFileName = dataProvider.getUserById(usrId).getUsrAudioFile();
			Toast.makeText(ctx, "Playing sound:"+ audioFileName, Toast.LENGTH_SHORT).show();

//			MediaPlayer mp = MediaPlayer.create(ctx, tmpUri);
//			mp.start();
		}
	}
	
	@Override
	public int getLayoutTag(){
		return layoutTag;
	}

	public void playAudio() {
		Toast.makeText(ctx, "Showing help for aggregated recommencation: "+ dataProvider.getActionNameById(aggrRec.getAction()).getName() + " " + dataProvider.getSeedById(aggrRec.getSeed()).getName() 
					   , Toast.LENGTH_SHORT).show();

		// TODO: should play several audio files
		// MediaPlayer mp = MediaPlayer.create(ctx, Uri.parse("file://sdcard/realfarm_data/audio/msg_action.wav"));
//		SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//		sp.setOnLoadCompleteListener( new OnLoadCompleteListener() {
//			
//			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//				soundPool.play(sampleId, 0.9f, 0.9f, 1, 0, 1.0f);
//			}
//		} );
//		int sndId = sp.load( ctx, R.raw.msg_action, 1);
//		
//		sp.play(sndId, 1.0f, 1.0f, 1, 0, 1.0f);
		SoundQueue sq = SoundQueue.getInstance();
		sq.addToQueue(R.raw.audio1);
		sq.addToQueue(R.raw.msg_plant);
		sq.addToQueue(R.raw.msg_user);
		sq.addToQueue(R.raw.msg_action);
		sq.play();
	}

}
