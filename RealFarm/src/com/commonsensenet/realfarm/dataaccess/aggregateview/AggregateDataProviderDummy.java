package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;
import com.commonsensenet.realfarm.model.Recommendation;

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
	
	public AggregateDataProviderDummy(Context ctx, Activity activity) {
		super(ctx, activity);
		dateFormat = new SimpleDateFormat(RealFarmDatabase.DATE_FORMAT);
		calendar = Calendar.getInstance();
		
		maxAct = dataProvider.getActionNamesList().size()-3;
		maxSeed = dataProvider.getSeedsList().size()-3;
		
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
		Recommendation tmpObj;
		for( int runner = 0; runner < numOfItems; ++runner){
			int actionId = 3+rn.nextInt(maxAct);
			int seedId = 3+rn.nextInt(maxSeed);
			tmpObj = new Recommendation(runner, seedId, actionId, "date");
			infoCont.add(tmpObj);
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
}
