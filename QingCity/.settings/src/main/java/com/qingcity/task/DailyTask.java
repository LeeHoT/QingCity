package com.qingcity.task;

import java.util.TimerTask;

public class DailyTask extends TimerTask{
	//private static final int SEASON = 3;// 三个月为一个赛季
	//private static final int GROWTHTIME = 1; // 1分钟增加一个体力
	//private static final int DAYTIME = 24 * 60 * 60 * 1000; // 一天的时间
	//private static final int WEEK = 7 * DAYTIME; // 一周的时间

	@Override
	public void run() {
		
		//检查当前时间和上次登录时间比较，，，如果是同一天，，则加载已经做过的任务
		//否则从任务列表中选取当钱阶段每日任务详情
		/**
		 * 每天00:00:00分清楚所有每日任务，并更新为最新的任务
		 * 
		 */
		System.out.println("每日任务");
		
	}

}
