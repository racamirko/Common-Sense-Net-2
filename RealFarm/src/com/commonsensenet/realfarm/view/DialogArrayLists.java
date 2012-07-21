package com.commonsensenet.realfarm.view;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.DialogData;

import java.util.ArrayList;

import android.view.View;

public class DialogArrayLists {

	public DialogArrayLists() {
	}

	public static ArrayList<DialogData> getMonthArray(View v){
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		DialogData dd1 = new DialogData();
		dd1.setName("01 January");
		int aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/jan", null, null);
		dd1.setAudio(aud);
		dd1.setValue("01");

		DialogData dd2 = new DialogData();
		dd2.setName("02 February");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/feb", null, null);
		dd2.setAudio(aud);
		dd2.setValue("02");

		DialogData dd3 = new DialogData();
		dd3.setName("03 March");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/mar", null, null);
		dd3.setAudio(aud);
		dd3.setValue("03");

		DialogData dd4 = new DialogData();
		dd4.setName("04 April");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/apr", null, null);
		dd4.setAudio(aud);
		dd4.setValue("04");

		DialogData dd5 = new DialogData();
		dd5.setName("05 May");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/may", null, null);
		dd5.setAudio(aud);
		dd5.setValue("05");

		DialogData dd6 = new DialogData();
		dd6.setName("06 June");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/jun", null, null);
		dd6.setAudio(aud);
		dd6.setValue("06");

		DialogData dd7 = new DialogData();
		dd7.setName("07 July");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/jul", null, null);
		dd7.setAudio(aud);
		dd7.setValue("07");

		DialogData dd8 = new DialogData();
		dd8.setName("08 August");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/aug", null, null);
		dd8.setAudio(aud);
		dd8.setValue("08");

		DialogData dd9 = new DialogData();
		dd9.setName("09 September");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/sep", null, null);
		dd9.setAudio(aud);
		dd9.setValue("09");

		DialogData dd10 = new DialogData();
		dd10.setName("10 October");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/oct", null, null);
		dd10.setAudio(aud);
		dd10.setValue("10");

		DialogData dd11 = new DialogData();
		dd11.setName("11 November");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/nov", null, null);
		dd11.setAudio(aud);
		dd11.setValue("11");

		DialogData dd12 = new DialogData();
		dd12.setName("12 December");
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/dec", null, null);
		dd12.setAudio(aud);
		dd12.setValue("12");

		m_entries.add(dd1);
		m_entries.add(dd2);
		m_entries.add(dd3);
		m_entries.add(dd4);
		m_entries.add(dd5);
		m_entries.add(dd6);
		m_entries.add(dd7);
		m_entries.add(dd8);
		m_entries.add(dd9);
		m_entries.add(dd10);
		m_entries.add(dd11);
		m_entries.add(dd12);

		return m_entries;
	} 

	public static ArrayList<DialogData> getTreatmentArray(View v){
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		DialogData dd1 = new DialogData();
		dd1.setName("Treated");
		dd1.setImage(R.drawable.ic_sowingseedtreated);
		int aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg", null, null);
		dd1.setAudio(aud);
		dd1.setValue("treated");
		DialogData dd2 = new DialogData();
		dd2.setName("Not treated");
		dd2.setImage(R.drawable.ic_sowingseednottreated);
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg", null, null);
		dd2.setAudio(aud);
		dd2.setValue("not treated");
		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getIntercropArray(View v){
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		DialogData dd1 = new DialogData();
		dd1.setName("Main Crop");
		dd1.setImage(R.drawable.ic_maincrop);
		int aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg", null, null);
		dd1.setAudio(aud);
		dd1.setValue("maincrop");
		DialogData dd2 = new DialogData();
		dd2.setName("Intercrop");
		dd2.setImage(R.drawable.ic_intercrop);
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg", null, null);
		dd2.setAudio(aud);
		dd2.setValue("intercrop");
		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getIrrigationArray(View v){
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		DialogData dd1 = new DialogData();
		dd1.setName("Flooding");
		dd1.setImage(R.drawable.ic_flooding);
		int aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg", null, null);
		dd1.setAudio(aud);
		dd1.setValue("Flooding");
		DialogData dd2 = new DialogData();
		dd2.setName("Sprinkling");
		dd2.setImage(R.drawable.ic_sprinkling);
		aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg", null, null);
		dd2.setAudio(aud);
		dd2.setValue("Sprinkling");
		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getItUnitsArray(View v, int dep, int arr, int inc){
		final ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		for(int i=dep; i<arr; i=i+inc) {
			DialogData dd = new DialogData();
			dd.setName("bag of "+i+" kgs");
			dd.setImage(R.drawable.ic_genericbaglarger);
			int aud = v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/" + "bagof"+i+"kg", null, null);
			dd.setAudio(aud);
			dd.setValue(i+"");
			dd.setNumber(i+"");
			m_entries.add(dd);
		}
		return m_entries;
	}
} 