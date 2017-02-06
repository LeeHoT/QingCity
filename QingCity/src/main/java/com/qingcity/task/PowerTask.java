package com.qingcity.task;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.qingcity.service.PlayerService;
import com.qingcity.service.impl.PlayerServiceImpl;
import com.qingcity.util.ExecutorServiceUtil;

/**
 * 一分钟增加固定的体力
 * 
 * @author Administrator
 *
 */
@Component
public class PowerTask extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(PowerTask.class);

	private static final Integer POWER = 3;// 一分钟增加一个体力
	private PlayerService playerService;
	private int playerId;

	public PowerTask(int playerId) {
		playerService = new PlayerServiceImpl();
		playerId = this.playerId;

	}

	public PowerTask() {
	}

	private int count = 0;

	public int getCount() {
		return count;
	}

	@Override
	public void run() {
		logger.info("增加用户体力");

		count++;
		// if (count == 3) {
		// ExecutorServiceUtil.stop();
		// }
		System.out.println(System.currentTimeMillis());
		System.out.println("playerService is " + playerService);

	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		PowerTask pt = new PowerTask();
		ExecutorServiceUtil esu = new ExecutorServiceUtil();
		long start = System.currentTimeMillis();
		System.out.println("开始时间" + start);
		esu.run(pt, POWER, POWER, TimeUnit.SECONDS);
		esu.stop();

	}

}
