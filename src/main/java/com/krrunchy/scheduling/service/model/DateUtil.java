package com.krrunchy.scheduling.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public final class DateUtil {
	
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
	
	private DateUtil() {
		
	}
	
	/**
	 * Returns an object of @link {Optional} type that represents a Date instance from a given string.
	 * @param dateString
	 * @return
	 */
	public static final Optional<Date> parseDate(String dateString) {
		Date dateObj;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			dateObj = formatter.parse(dateString);	
			return Optional.of(dateObj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	/**
	 * Returns formatted date in string form
	 * @param date
	 * @return
	 */
	public static final String getStringFrom(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT);
		return formatter.format(date);
	}
}
