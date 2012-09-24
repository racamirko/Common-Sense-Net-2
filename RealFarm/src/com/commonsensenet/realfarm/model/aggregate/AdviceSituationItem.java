package com.commonsensenet.realfarm.model.aggregate;

import java.util.ArrayList;

import com.commonsensenet.realfarm.model.Advice;
import com.commonsensenet.realfarm.model.Recommendation;
import com.commonsensenet.realfarm.model.Resource;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * @author Nguyen Lisa
 */
public class AdviceSituationItem {

	private Advice mAdvice;
	private long mPlotId;
	private String mPlotImagePath;
	private Recommendation mRecommendation;
	private Resource mProblem;
	private ArrayList<AdviceSolutionItem> mSolutionItems;

	public Advice getAdvice() {
		return mAdvice;
	}

	public ArrayList<AdviceSolutionItem> getItems() {
		return mSolutionItems;
	}

	public long getPlotId() {
		return mPlotId;
	}

	public String getPlotImage() {
		return mPlotImagePath;
	}

	public Recommendation getRecommendation() {
		return mRecommendation;
	}

	public Resource getProblem() {
		return mProblem;
	}

	public void setAdvice(Advice value) {
		mAdvice = value;
	}

	public void setItems(ArrayList<AdviceSolutionItem> items) {
		this.mSolutionItems = items;
	}

	public void setPlotId(long pid) {
		this.mPlotId = pid;
	}

	public void setPlotImage(String img) {
		this.mPlotImagePath = img;
	}

	public void setRecommendation(Recommendation value) {
		mRecommendation = value;
	}

	public void setResource(Resource value) {
		mProblem = value;
	}

}
