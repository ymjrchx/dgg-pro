package net.dgg.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description 日期工具类
 * @author Ma Hong
 * @date 2018-09-10 14:14
 * @version 1.0
 */
public class DggDateUtil {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
	private static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

	public static String nowDateTime() {
		return now(DEFAULT_FORMAT);
	}

	public static String nowDate() {
		return now(DEFAULT_FORMAT_DATE);
	}

	public static String nowTime() {
		return now(DEFAULT_FORMAT_TIME);
	}

	public static String now(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.now().format(dtf);
	}

	public static String format(Date date, String pattern) {
		return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_FORMAT);
	}

	public static String format(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(dtf);
	}

	public static String format(LocalDateTime localDateTime) {
		return format(localDateTime, DEFAULT_FORMAT);
	}

	public static Date parseDate(String time) {
		return parseDate(time, DEFAULT_FORMAT);
	}

	public static Date parseDate(String time, String pattern) {
		return Date.from(parseLocalDateTime(time, pattern).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime parseLocalDateTime(String time, String pattern) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(time, dtf);
	}

	public static int nowYear() {
		return LocalDate.now().getYear();
	}

	public static int nowMonth() {
		return LocalDate.now().getMonthValue();
	}

}
