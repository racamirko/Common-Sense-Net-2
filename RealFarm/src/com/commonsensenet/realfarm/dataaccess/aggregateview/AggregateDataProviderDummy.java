package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.aggregate.AggregateRecommendation;

import android.app.Activity;
import android.content.Context;

/**
 * AggregateDataProviderDummy
 * 
 * Randomly populated data for testing purposes.
 * 
 * @author: Mirko Raca <mirko.raca@epfl.ch>
 */
public class AggregateDataProviderDummy extends AggregateDataProvider {
	protected int numOfItems, maxAct, maxSeed, maxUsr;
	protected SimpleDateFormat dateFormat;
	protected Calendar calendar;
	public static final int NUM_OF_TYPES = 2;
	
	public AggregateDataProviderDummy(Context ctx, Activity activity) {
		super(ctx, activity);
		dateFormat = new SimpleDateFormat(RealFarmDatabase.DATE_FORMAT);
		calendar = Calendar.getInstance();
		
		maxAct = dataProvider.getActionNamesList().size()-3;
		maxSeed = dataProvider.getSeedsList().size()-3;
		maxUsr = dataProvider.getUserCount();
	}

	@Override
	public Vector<Object> getItems( AggregateDataFilter filter ){
		Vector<Object> results = new Vector<Object>();
		
		// TODO: different types based on different filters
		generateDummyItems(numOfItems, results);
		
		return results;
	}
	
	public void generateDummyItems(int numOfItems, Vector<Object> infoCont){
		Random rn = new Random();
		// generation
		infoCont.clear();
		for( int runner = 0; runner < numOfItems; ++runner){
			switch( rn.nextInt(NUM_OF_TYPES) ){
				case 0:
					infoCont.add( generateRecommendation(rn, runner) );
					break;
				case 1:
					infoCont.add( generateAggrRecommendation(rn, runner) );
					break;
			}
		}
	}

	public int getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}
	
	// =============== Generator functions
	
	protected Recommendation generateRecommendation(Random rn, int id){
		int actionId = 3+rn.nextInt(maxAct);
		int seedId = 3+rn.nextInt(maxSeed);
		String date = dateFormat.format(calendar.getTime());
		
		return new Recommendation(id, seedId, actionId, date);
	}
	
	protected AggregateRecommendation generateAggrRecommendation(Random rn, int id){
		int actionId = 3+rn.nextInt(maxAct);
		int seedId = 3+rn.nextInt(maxSeed);
		String date = dateFormat.format(calendar.getTime());
		// default list of all users
		
		Vector<Integer> allUsrs = new Vector<Integer>(maxUsr);
		for( int runner = 0; runner < maxUsr; ++runner )
			allUsrs.add(runner);

		// randomly remove a set
		// TODO: stopped here
		//		remove rnd number of points
		// use those users to fill the field
		// TODO: 2) make the visible layout and Activity for viewing
		
		AggregateRecommendation aggrRec = new AggregateRecommendation(id, seedId, actionId, date);
		aggrRec.addUserId(userId);
		
		return aggrRec;
	}
}
