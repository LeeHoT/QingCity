package com.qingcity.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qingcity.task.DailyTask;
import com.qingcity.task.PowerTask;

@Component
public class TimerUtil {

	private static TimerUtil instance = new TimerUtil();

	public static TimerUtil getInstance() {
		return instance;
	}

	public static final int SEASON = 3;// 三个月为一个赛季
	public static final int GROWTHTIME = 1; // 1分钟增加一个体力
	public static final int DAYTIME = 24 * 60 * 60 * 1000; // 一天的时间
	public static final int WEEK = 7 * DAYTIME; // 一周的时间

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private Timer timer;

	/**
	 * 每日任务
	 * 
	 */

	public void DailyTaskUtil() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 15); // 控制时
		calendar.set(Calendar.MINUTE, 36); // 控制分
		calendar.set(Calendar.SECOND, 00); // 控制秒
		Date taskTime = calendar.getTime(); // 得出执行任务的时间,此处为今天的00：00：00
		timer.scheduleAtFixedRate(new DailyTask(), taskTime, DAYTIME);// 这里设定将延时每天固定执行

	}

	/**
	 * 成长任务 -- 体力增加
	 * 
	 */
	public void GrowthTask(int playerId) {
		PowerTask growth = new PowerTask(playerId);
		Date currentTime = new Date();// 获取当前时间
		System.out.println(currentTime);
		System.out.println(playerId);
		System.out.println(growth);
		timer = new Timer();
		timer.scheduleAtFixedRate(growth, currentTime, GROWTHTIME * 60 * 1000);
	}

	/**
	 * 获取系统当前时间 long
	 */
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取系统当前时间 Date
	 */
	public Date getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		return date;
	}

	/**
	 * 获取时间差值
	 */
	public long getDifferenceTime(long time) {
		return getCurrentTime() - time;
	}

	/**
	 * String的类型必须形如： yyyy-MM-dd hh:mm:ss[.f...] 这样的格式， 中括号表示可选，否则报错！！！
	 * 
	 * @param t
	 * @return
	 */
	public Timestamp String2Timestamp(String t) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Timestamp ts = null;
		try {
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
	public String Timestamp2String(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(ts);
	}

	public Date Timestamp2Date(Timestamp ts) {
		return String2Date(Timestamp2String(ts));
	}

	/**
	 * Date 类型转化我Timestamp 、 父类无法直接转为子类，听过先转为String 转为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public Timestamp Date2Timestamp(Date date) {
		String d = Date2String(date);
		return String2Timestamp(d);

	}

	/**
	 * java.util.Date 类型的时间转化为Sql Date 类型的数据
	 * 
	 * @param date
	 * @return
	 */
	public java.sql.Date UtilDate2SQLDate(Date date) {
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
	public java.sql.Date String2SQLDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
	public String Date2String(Date date) {
		// SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 字符串转为Date类型,要求字符串类型为 YYYY-MM-DD
	 * 
	 * @param time
	 * @return
	 */
	public Date String2Date(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
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
	public int getSeason(Date date) {
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
	public int getWeek(Date date) {
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
	public int getDay() {
		Date date = new Date();
		String d = "2016-11-20";
		System.out.println(String2Date(d).getTime());
		System.out.println("+++++++" + date.getTime());

		Calendar now = new GregorianCalendar();
		int day = now.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static void main(String[] args) {
		TimerUtil time = getInstance();
		Date date = new Date();
		System.out.println("............." + time.Date2Timestamp(date));
		System.out.println(time.Date2String(date)); // test Date2String
		System.out.println("当前是第" + time.getSeason(date) + "赛季"); // test
																	// getSeason
		System.out.println("当前是第" + time.getWeek(date) + "周");// test getWeek
		System.out.println("今天是" + time.getDay() + "号"); // test getDay
		String d = "2016-11-20";
		date = time.String2Date(d); // test String2Date
		System.out.println(date);
		System.out.println("Date 转化为字符串 " + time.Date2String(date));
		Timestamp ts = time.Date2Timestamp(date);
		System.out.println("Date 转化为Timestamp " + ts);
		System.out.println("String 转化为Timestamp " + time.String2Timestamp(d));
		System.out.println("Timestamp 转化为 Date " + time.Timestamp2Date(ts));
		System.out.println("Timestamp 转化为String " + time.Timestamp2String(ts));
		System.out.println("String 转化为 Date " + time.String2Date(d));
		System.out.println(time.Date2Timestamp(date) == time.String2Timestamp(d));

	}

}
