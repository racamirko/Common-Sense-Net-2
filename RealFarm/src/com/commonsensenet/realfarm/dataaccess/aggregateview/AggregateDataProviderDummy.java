package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Random;
import java.util.Vector;

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
	protected int numOfItems;
	
	public AggregateDataProviderDummy(Context ctx, Activity activity) {
		super(ctx, activity);
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
		// get maximum numbers for each type
		int maxAct = dataProvider.getActionNamesList().size()-3;
		int maxSeed = dataProvider.getSeedsList().size()-3;
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
}
