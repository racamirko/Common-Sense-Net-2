package com.commonsensenet.realfarm.model.aggregate;

import com.commonsensenet.realfarm.model.AdvicePiece;
import com.commonsensenet.realfarm.model.Resource;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 * @author Nguyen Lisa
 */
public class AdviceSolutionItem {

	private boolean hasLiked = false;
	private int mActionAudio = -1;
	private int mActionId;
	private AdvicePiece mAdvicePiece;
	private int mLikeCount = 12;
	private Resource mResource;

	public int getActionAudio() {
		return mActionAudio;
	}

	public int getActionId() {
		return mActionId;
	}

	public AdvicePiece getAdvicePiece() {
		return mAdvicePiece;
	}

	public boolean getHasLiked() {
		return hasLiked;
	}

	public int getLikes() {
		return mLikeCount;
	}

	public Resource getResource() {
		return mResource;
	}

	public void setActionAudio(int aud) {
		this.mActionAudio = aud;
	}

	public void setActionId(int value) {
		mActionId = value;
	}

	public void setAdvicePiece(AdvicePiece value) {
		mAdvicePiece = value;
	}

	public void setHasLiked(boolean hl) {
		hasLiked = hl;
	}

	public void setLikes(int li) {
		this.mLikeCount = li;
	}

	public void setResource(Resource value) {
		mResource = value;
	}
}
