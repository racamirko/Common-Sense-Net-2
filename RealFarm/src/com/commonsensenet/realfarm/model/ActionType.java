package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class ActionType extends Resource {

	public ActionType(int id, String name, int image, int audio) {
		super(id, name, name, audio, image, -1, -1, -1);
	}

	@Override
	public String toString() {

		return String.format(
				"[ActionType id='%d', name='%s', image='%d', audio='%d']", mId,
				mName, mImage1, mAudio);
	}
}
