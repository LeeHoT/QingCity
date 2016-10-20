package com.qingcity.task;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.qingcity.service.PlayerService;
import com.qingcity.service.UserService;
import com.qingcity.service.impl.PlayerServiceImpl;
import com.qingcity.service.impl.UserServiceImpl;

/**
 * 一分钟增加固定的体力
 * 
 * @author Administrator
 *
 */
@Component
public class PowerTask extends TimerTask {
	//private static final Logger logger = LoggerFactory.getLogger(PowerTask.class);

	private static final Integer POWER = 1;// 一分钟增加一个体力
	private PlayerService playerService;
	private int playerId;
	public PowerTask(int playerId){
		playerService = new PlayerServiceImpl();
		playerId = this.playerId;
				
	}

	public PowerTask() {
		
	}

	@Override
	public void run() {
		//logger.info("增加用户体力");
		System.out.println(System.currentTimeMillis());
		System.out.println("playerService is " + playerService);

	}

}
