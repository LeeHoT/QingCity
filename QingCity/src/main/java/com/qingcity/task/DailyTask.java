package com.qingcity.task;

import java.util.TimerTask;

public class DailyTask extends TimerTask{

	@Override
	public void run() {
		/**
		 * 每天00:00:00分清楚所有每日任务，并更新为最新的任务
		 * 
		 */
		System.out.println("每日任务");
		
	}

}
