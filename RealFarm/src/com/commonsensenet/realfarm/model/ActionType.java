package com.commonsensenet.realfarm.model;

/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class ActionType extends Resource {

	public ActionType(int id, String name, int resource, int audio) {
		super(id, name, name, audio, resource, -1, -1, -1);
	}

	@Override
	public String toString() {

		return String.format(
				"[ActionType id='%d', name='%s', resource='%d', audio='%d']",
				mId, mName, mResource1, mAudio);
	}
}
