package com.qingcity.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class TimeUtil {

	private static TimeUtil instance = new TimeUtil();
	private static final int SEASON = 3;// 三个月为一个赛季
	private static String timeStrFormat = "yyyy-MM-dd HH:mm:ss";

	public static TimeUtil getInstance() {
		return instance;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return Date
	 */
	public static Date getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		return date;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts;
	}

	/**
	 * 获取时间差值
	 */
	public static long getDifferenceTime(long time) {
		return System.currentTimeMillis() - time;
	}

	/**
	 * String的类型必须形如： yyyy-MM-dd hh:mm:ss[.f...] 这样的格式， 中括号表示可选，否则报错！！！
	 * 
	 * @param t
	 * @return
	 */
	public static Timestamp String2Timestamp(String t) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeStrFormat);
		Timestamp ts = null;

		try {
			System.out.println("string2timestamp : " + sdf.parse(t).getTime());
			ts = new Timestamp(sdf.parse(t).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;

	}

	/**
	 * Timestamp 转化为 yyyy-MM-dd 格式字符串
	 * 
	 * @param ts
	 * @return
	 */
	public static String Timestamp2String(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeStrFormat);
		return sdf.format(ts);
	}

	public static Date Timestamp2Date(Timestamp ts) {
		return String2Date(Timestamp2String(ts));
	}

	/**
	 * Date 类型转化我Timestamp 、 父类无法直接转为子类，听过先转为String 转为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp Date2Timestamp(Date date) {
		System.out.println("date2timestamp: " + date);
		String d = Date2String(date);
		return String2Timestamp(d);

	}

	/**
	 * java.util.Date 类型的时间转化为Sql Date 类型的数据
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date UtilDate2SQLDate(Date date) {
		// 先转为字符串
		String udt = Date2String(date);
		// 再转为sql Date
		return String2SQLDate(udt);
	}

	/**
	 * String 转化为SqlDate类型
	 * 
	 * @param str
	 * @return
	 */
	public static java.sql.Date String2SQLDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeStrFormat);
		Date udt = null;
		try {
			udt = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date sdt = new java.sql.Date(udt.getTime());
		return sdt;
	}

	/**
	 * Date类型转为字符串，格式为: YYYY-MM-DD
	 * 
	 * @param date
	 * @return
	 */
	public static String Date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeStrFormat);
		String time = sdf.format(date);
		System.out.println("dddddddddddddddddddd:" + time);
		return time;
	}

	/**
	 * 字符串转为Date类型,要求字符串类型为 YYYY-MM-DD
	 * 
	 * @param time
	 * @return
	 */
	public static Date String2Date(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeStrFormat);
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前是第几赛季 三个月未一个赛季
	 * 
	 * @param date
	 *            当前时间或者某个指定的时间
	 * @return 返回赛季
	 */
	public static int getSeason(Date date) {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;// 获取当前月
		// 判断本月属于哪个季度
		int season = (month - 1) / SEASON + 1;
		return season;
	}

	/**
	 * 获取一年中的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar now = new GregorianCalendar();
		now.setFirstDayOfWeek(Calendar.MONDAY);// 设置每周的第一天为周一。注：美国为周日，和中国不同
		now.setMinimalDaysInFirstWeek(7);
		now.setTime(date);
		return now.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取每个月中的日期
	 * 
	 * @param date
	 *            当前或指定时间
	 * @return 返回日期
	 */
	public static int getDay() {
		Calendar now = new GregorianCalendar();
		int day = now.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static Date getStartTime() {
		long current = System.currentTimeMillis();// 当前时间毫秒数
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		System.out.println(new Timestamp(zero));// 今天零点零分零秒
		return new Timestamp(zero);
	}

	public static Date getEndTime() {
		long current = System.currentTimeMillis();// 当前时间毫秒数
		long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
		return new Timestamp(twelve);
	}

	public static boolean isToday(Date date) {
		Date zero = getStartTime();
		Date twelve = getEndTime();
		if (date.before(twelve) && date.after(zero)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		TimeUtil time = getInstance();
		Date date = new Date();
		System.out.println(time.Date2String(date)); // test Date2String
		System.out.println("当前是第" + time.getSeason(date) + "赛季"); // test
		System.out.println("当前是第" + time.getWeek(date) + "周");// test getWeek
		System.out.println("今天是" + time.getDay() + "号"); // test getDay
		String d = "2016-11-20 10:25:23";
		System.out.println(time.Date2Timestamp(date).after(time.String2Timestamp(d)));
		date = time.String2Date(d); // test String2Date
		System.out.println(date);
		System.out.println("Date 转化为字符串 " + time.Date2String(date));
		Timestamp ts = time.Date2Timestamp(date);
		System.out.println("Date 转化为Timestamp " + ts);
		System.out.println("String 转化为Timestamp " + time.String2Timestamp(d));
		System.out.println("Timestamp 转化为 Date " + time.Timestamp2Date(ts));
		System.out.println("Timestamp 转化为String " + time.Timestamp2String(ts));
		System.out.println("String 转化为 Date " + time.String2Date(d));

	}

}
