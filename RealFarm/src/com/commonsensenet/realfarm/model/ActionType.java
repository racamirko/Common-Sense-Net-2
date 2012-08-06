package com.commonsensenet.realfarm.model;


/**
 * 
 * @author Oscar Bola–os <@oscarbolanos>
 */
public class ActionType extends Resource {

	/** Name in Kannada of the ActionType. */
	private String mNameKannada;

	public ActionType(int id, String name, String nameKannada, int resource,
			int audio) {
		super(id, name, audio, resource, -1, -1, -1);

		mNameKannada = nameKannada;
	}

	public String getNameKannada() {
		return mNameKannada;
	}

}
