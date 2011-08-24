package com.commonsensenet.realfarm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.commonsensenet.realfarm.R;
import com.commonsensenet.realfarm.dataaccess.RealFarmDatabase;

public class DateHelper {
	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static String formatDate(String date, Context context) {

		try {
			Date dateTime = new SimpleDateFormat(RealFarmDatabase.DATE_FORMAT)
					.parse(date);

			// creates calendars.
			Calendar givenDate = Calendar.getInstance();
			Calendar today = Calendar.getInstance();
			Calendar yesterday = Calendar.getInstance();
			Calendar oneWeek = Calendar.getInstance();

			// adjuts calendars
			givenDate.setTime(dateTime);
			yesterday.add(Calendar.DATE, -1);
			oneWeek.add(Calendar.DATE, -7);

			if (givenDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)
					&& givenDate.get(Calendar.DAY_OF_YEAR) == today
							.get(Calendar.DAY_OF_YEAR)) {
				return context.getString(R.string.dateToday);
			} else if (givenDate.get(Calendar.YEAR) == yesterday
					.get(Calendar.YEAR)
					&& givenDate.get(Calendar.DAY_OF_YEAR) == yesterday
							.get(Calendar.DAY_OF_YEAR)) {
				return context.getString(R.string.dateYesterday);
			} else {
				long dayDif = daysBetween(oneWeek, givenDate);
				
				if(dayDif < 8)
					return context.getString(R.string.dateLastWeek);
				else
					return String.format(context.getString(R.string.dateMoreThanAWeek), dayDif);	
			}	
		} catch (ParseException e) {
			return date;
		}
	}
}
