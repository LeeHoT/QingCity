package com.qingcity.data.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * 
 * @author leehotin
 * @Date 2017年2月5日 上午10:34:46
 * @Description 玩家客户端与服务器通信记录管理器
 */
public class PlayerManager {

	private static final Logger logger = LoggerFactory.getLogger(PlayerManager.class);

	private static final PlayerManager instance = new PlayerManager();

	public static final PlayerManager getInstance() {
		return instance;
	}

	/**
	 * channel上次通信时间
	 */
	public  Map<Channel, Long> lastPingTime = new ConcurrentHashMap<Channel, Long>();

	/**
	 * 获取上次通信时间
	 * 
	 * @return
	 */
	public  Long getLastPingTime(Channel channel) {
		return lastPingTime.get(channel);

	}

	public void add(Channel channel) {
		lastPingTime.put(channel, System.currentTimeMillis());
	}

	public void remove(Channel channel) {
		if (!isExist(channel)) {
			logger.warn("channel [{}] don't exist,please check again!", channel);
			return;
		}
		lastPingTime.remove(channel);
	}

	public void updateTime(Channel channel) {
		if (!isExist(channel)) {
			logger.warn("channel [{}] don't exist,please check again!", channel);
			return;
		}
		lastPingTime.replace(channel, System.currentTimeMillis());

	}
	
	public boolean isExist(Channel channel) {
		// 检查当前channel是否存在
		return lastPingTime.containsKey(channel);
	}


}
