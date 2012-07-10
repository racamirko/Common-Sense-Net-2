package com.commonsensenet.realfarm.dataaccess.aggregateview;

import java.util.Date;

import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.DateFilterModifier;
import com.commonsensenet.realfarm.dataaccess.aggregateview.AggregateDataProvider.MessageType;
import com.commonsensenet.realfarm.model.Plot;
import com.commonsensenet.realfarm.model.SeedType;
import com.commonsensenet.realfarm.model.User;

public class AggregateDataFilter {
	protected DateFilterModifier dateMod;
	protected Date dateValue;
	protected Plot plot;
	protected SeedType seed;
	protected MessageType typeFilter;
	protected User user;

	public AggregateDataFilter setDate(Date date, DateFilterModifier dateMod) {
		this.dateValue = date;
		this.dateMod = dateMod;
		return this;
	}

	public AggregateDataFilter setMessageType(MessageType mesType) {
		typeFilter = mesType;
		return this;
	}

	public AggregateDataFilter setPlot(Plot plot) {
		this.plot = plot;
		return this;
	}

	public AggregateDataFilter setSeed(SeedType seed) {
		this.seed = seed;
		return this;
	}

	public AggregateDataFilter setUser(User user) {
		this.user = user;
		return this;
	}

}