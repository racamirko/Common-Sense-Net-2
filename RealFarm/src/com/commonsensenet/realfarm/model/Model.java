package com.commonsensenet.realfarm.model;

public interface Model {

	/** Indicates that the status has not been sent. */
	public static final int STATUS_UNSENT = 0;
	/** Indicates that the status has been sent. */
	public static final int STATUS_SENT = 1;
	/** Indicates that the Model has been confirmed. */
	public static final int STATUS_CONFIRMED = 2;

	/** Identifier of the Model in the corresponding Model Type. */
	public long getId();

	/** Model Type identifier. */
	public int getModelTypeId();

	/** Transforms the current object into a string that can be sent via SMS. */
	public String toSmsString();

}
