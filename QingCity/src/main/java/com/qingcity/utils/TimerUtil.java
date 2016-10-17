package com.qingcity.utils;

import java.util.Calendar;
import java.util.Date;
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

	public static final int GROWTHTIME = 1; // 1分钟增加一个体力
	public static final int DAYTIME = 24 * 60 * 60 * 1000; // 一天的时间
	public static final int WEEK = 7 * DAYTIME; // 一周的时间

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

	public static void main(String[] args) {
		TimerUtil t = new TimerUtil();
		t.GrowthTask(1);
	}

}
