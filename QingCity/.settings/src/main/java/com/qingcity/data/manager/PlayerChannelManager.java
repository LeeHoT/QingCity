package com.qingcity.data.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class PlayerChannelManager {

	private static final Logger logger = LoggerFactory.getLogger(PlayerChannelManager.class);

	/**
	 * Map<userId,channel>
	 */
	private static Map<Integer, Channel> channelMap = new ConcurrentHashMap<Integer, Channel>();

	/**
	 * 添加玩家Channel
	 * 
	 * @param userId
	 *            玩家id
	 * @param channel
	 *            玩家Channel
	 */
	public synchronized static void add(int userId, Channel channel) {
		logger.info("Add channel:[{}] into channelMap,玩家[{}]已登录", userId, userId);
		channelMap.put(userId, channel);
	}

	/**
	 * 获取玩家Channel
	 * 
	 * @param userId
	 *            玩家id
	 * @return 当前客户端Channel
	 */
	public static Channel getChannel(int userId) {
		return channelMap.get(userId);
	}

	/**
	 * 移除玩家
	 * 
	 * @param Channel
	 */
	@SuppressWarnings("rawtypes")
	public static void removeChannel(Channel channel) {
		for (Map.Entry entry : channelMap.entrySet()) {
			if (entry.getValue() == channel) {
				logger.warn("Remove channel:[{}] from channelMap!,玩家[{}]掉线", channel, entry.getKey());
				channelMap.remove(entry.getKey());
			}
			logger.debug("当前共有[{}]个玩家在线", channelMap.size());
		}
	}

}
