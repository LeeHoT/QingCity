package com.qingcity.data.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * @author leehotin
 * @Date 2017年2月5日 上午10:33:50
 * @Description 角色通到管理器
 */
public class PlayerChannelManager {

	private static final Logger logger = LoggerFactory.getLogger(PlayerChannelManager.class);

	private static PlayerChannelManager instance = new PlayerChannelManager();

	public static PlayerChannelManager getInstance() {
		return instance;
	}

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
	public synchronized void add(int userId, Channel channel) {
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
	public Channel getChannel(int userId) {
		return channelMap.get(userId);
	}

	/**
	 * 根据channel值获取玩家Id
	 * 
	 * @param channel
	 * @return channel 对应的玩家Id
	 */
	public Integer getIdByChannel(Channel channel) {
		int userId = 0;
		for (Map.Entry<Integer, Channel> pChannel : channelMap.entrySet()) {
			if (pChannel.getValue().equals(channel)) {
				userId = pChannel.getKey();
			}
		}
		return userId;
	}

	/**
	 * 移除玩家
	 * 
	 * @param Channel
	 */
	@SuppressWarnings("rawtypes")
	public void removeChannel(Channel channel) {
		for (Map.Entry entry : channelMap.entrySet()) {
			if (entry.getValue() == channel) {
				logger.warn("Remove channel:[{}] from channelMap!,玩家[{}]掉线", channel, entry.getKey());
				channelMap.remove(entry.getKey());
			}
			logger.debug("当前共有[{}]个玩家在线", channelMap.size());
		}
	}
}
