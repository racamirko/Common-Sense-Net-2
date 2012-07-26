package com.commonsensenet.realfarm.view;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.model.DialogData;

import java.util.ArrayList;

import android.view.View;

public class DialogArrayLists {

	public DialogArrayLists() {
	}

	public static ArrayList<DialogData> getMonthArray(View v) {
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();

		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/jan", null,
						null);
		DialogData dd1 = new DialogData("01 January", "01", -1, -1, aud, "01",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/feb", null,
						null);
		DialogData dd2 = new DialogData("02 February", "02", -1, -1, aud, "02",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/mar", null,
						null);
		DialogData dd3 = new DialogData("03 March", "03", -1, -1, aud, "03",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/apr", null,
						null);
		DialogData dd4 = new DialogData("04 April", "04", -1, -1, aud, "04",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/may", null,
						null);
		DialogData dd5 = new DialogData("05 May", "05", -1, -1, aud, "05", "",
				-1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/jun", null,
						null);
		DialogData dd6 = new DialogData("06 June", "06", -1, -1, aud, "06", "",
				-1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/jul", null,
						null);
		DialogData dd7 = new DialogData("07 July", "07", -1, -1, aud, "07", "",
				-1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/aug", null,
						null);
		DialogData dd8 = new DialogData("08 August", "08", -1, -1, aud, "08",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/sep", null,
						null);
		DialogData dd9 = new DialogData("09 September", "09", -1, -1, aud,
				"09", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/oct", null,
						null);
		DialogData dd10 = new DialogData("10 October", "10", -1, -1, aud, "10",
				"", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/nov", null,
						null);
		DialogData dd11 = new DialogData("11 November", "11", -1, -1, aud,
				"11", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/dec", null,
						null);
		DialogData dd12 = new DialogData("12 December", "12", -1, -1, aud,
				"12", "", -1);

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

	public static ArrayList<DialogData> getTreatmentArray(View v) {
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();

		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg",
						null, null);
		DialogData dd1 = new DialogData("Treated",
				R.drawable.ic_sowingseedtreated, -1, aud, "treated", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg",
						null, null);
		DialogData dd2 = new DialogData("Not treated",
				R.drawable.ic_sowingseednottreated, -1, aud, "not treated", "",
				-1);

		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getIntercropArray(View v) {
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();

		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg",
						null, null);
		DialogData dd1 = new DialogData("Main crop", R.drawable.ic_maincrop,
				-1, aud, "maincrop", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg",
						null, null);
		DialogData dd2 = new DialogData("Intercrop", R.drawable.ic_intercrop,
				-1, aud, "intercrop", "", -1);

		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getIrrigationArray(View v) {
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();

		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg",
						null, null);
		DialogData dd1 = new DialogData("Flooding", R.drawable.ic_flooding, -1,
				aud, "Flooding", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg",
						null, null);
		DialogData dd2 = new DialogData("Sprinkling", R.drawable.ic_sprinkling,
				-1, aud, "Sprinkling", "", -1);

		m_entries.add(dd1);
		m_entries.add(dd2);
		return m_entries;
	}

	public static ArrayList<DialogData> getSoilTypeArray(View v) {
		ArrayList<DialogData> m_entries = new ArrayList<DialogData>();

		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof10kg",
						null, null);
		DialogData dd1 = new DialogData("Loamy", -1, -1, aud, "Loamy", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg",
						null, null);
		DialogData dd2 = new DialogData("Sandy", -1, -1, aud, "Sandy", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/bagof20kg",
						null, null);

		DialogData dd3 = new DialogData("Clay", -1, -1, aud, "Clay", "", -1);

		m_entries.add(dd1);
		m_entries.add(dd2);
		m_entries.add(dd3);
		return m_entries;
	}

	public static ArrayList<DialogData> getItUnitsArray(View v, int dep,
			int arr, int inc) {
		final ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		for (int i = dep; i < arr; i = i + inc) {

			// int aud =
			// v.getContext().getResources().getIdentifier("com.commonsensenet.realfarm:raw/"
			// + "bagof"+i+"kg", null, null);
			int aud = v
					.getContext()
					.getResources()
					.getIdentifier(
							"com.commonsensenet.realfarm:raw/" + "bagof10kg",
							null, null);
			DialogData dd = new DialogData("bag of " + i + " kgs", i + "",
					R.drawable.ic_genericbaglarger, -1, aud, i + "", i + "", -1);

			m_entries.add(dd);
		}
		return m_entries;
	}
	
	public static ArrayList<DialogData> getSmileyArray(View v) {
		final ArrayList<DialogData> m_entries = new ArrayList<DialogData>();
		int aud = v
				.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/feedbackgood",
						null, null);
		DialogData dd1 = new DialogData("Good", R.drawable.smiley_good, -1,
				aud, "1", "", -1);

		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/feedbackmoderate",
						null, null);
		DialogData dd2 = new DialogData("Moderate", R.drawable.smiley_medium,
				-1, aud, "2", "", -1);
		
		aud = v.getContext()
				.getResources()
				.getIdentifier("com.commonsensenet.realfarm:raw/feedbackbad",
						null, null);
		DialogData dd3 = new DialogData("Bad", R.drawable.smiley_bad,
				-1, aud, "3", "", -1);

		m_entries.add(dd1);
		m_entries.add(dd2);
		m_entries.add(dd3);
		return m_entries;
	}
}