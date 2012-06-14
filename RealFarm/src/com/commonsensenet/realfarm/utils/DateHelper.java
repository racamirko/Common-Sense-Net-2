package com.commonsensenet.realfarm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;

/**
 * Helper functions to handle the date.
 * 
 * @author Oscar Bolanos (oscar.bolanos@epfl.ch)
 * 
 */
public class DateHelper {

	/**
	 * Calculates the difference in days between two dates. To do so, it
	 * substracts the milliseconds between each date and then divides this value
	 * by the length of a day in milliseconds.
	 * 
	 * @param dateEarly
	 *            initial date to calculate
	 * @param dateLater
	 *            later date to calculate
	 * 
	 * @return differente in dayts between the given dates
	 */
	public static long calculateDays(Date dateEarly, Date dateLater) {
		return (dateLater.getTime() - dateEarly.getTime())
				/ (24 * 60 * 60 * 1000);
	}

	/**
	 * Formats the date. The format corresponds only to reference dates: today,
	 * yesterday, X days ago and X weeks ago.
	 * 
	 * @param date
	 *            the date to format.
	 * @param context
	 *            application context used for locatization.
	 * 
	 * @return a string with the formatted date.
	 */
	public static String formatDate(String date, Context context) {

		try {
			// extracts the date.
			Date dateTime = new SimpleDateFormat(RealFarmDatabase.DATE_FORMAT)
					.parse(date);
			// gets current date
			Calendar today = Calendar.getInstance();

			// calculates the difference
			long dayDif = calculateDays(dateTime, today.getTime());

			if (dayDif == 0)
				return context.getString(R.string.dateToday);
			else if (dayDif == 1)
				return context.getString(R.string.dateYesterday);
			else if (dayDif < 15)
				return String.format(context.getString(R.string.dateLastWeek),
						dayDif);
			else {
				return String.format(
						context.getString(R.string.dateMoreThanAWeek),
						(int) Math.floor(dayDif / 7));
			}
		} catch (ParseException e) {
			return date;
		}
	}
}
