package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Date;

import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.DateFilterModifier;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.MessageType;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.Seed;
import com.commonsensenet.realfarm.model.User;

public class AggregateDataFilter {
	protected MessageType typeFilter;
	protected Plot plot;
	protected Seed seed;
	protected User user;

	protected DateFilterModifier dateMod;
	protected Date dateValue;

	public AggregateDataFilter setMessageType(MessageType mesType) {
		typeFilter = mesType;
		return this;
	}

	public AggregateDataFilter setPlot(Plot plot) {
		this.plot = plot;
		return this;
	}

	public AggregateDataFilter setSeed(Seed seed) {
		this.seed = seed;
		return this;
	}

	public AggregateDataFilter setUser(User user) {
		this.user = user;
		return this;
	}

	public AggregateDataFilter setDate(Date date, DateFilterModifier dateMod) {
		this.dateValue = date;
		this.dateMod = dateMod;
		return this;
	}

}