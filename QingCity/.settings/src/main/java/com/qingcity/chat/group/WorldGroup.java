package com.qingcity.chat.group;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.constants.ChatConstant;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 世界频道
 * 
 * @author leehotin
 *
 */
public class WorldGroup implements Groups {
	private static final Logger logger = LoggerFactory.getLogger(WorldGroup.class);
	public static ChannelGroup worldChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 世界频道
	public static Map<Integer, ChannelGroup> worldChannel = new LinkedHashMap<>();

	static {
		worldChannel.put(ChatConstant.WORLD_MSG_CHANNEL, worldChannels);
	}

	@Override
	public int addToGroup(int identify, Channel channel) {
		ChannelGroup channels = getChannels(identify);// 取出指定频道内的玩家channel列表
		if (channels == null) {
			logger.error("世界频道[{}]不存在,无法进入公会频道", identify);
			return -1;
		}
		channels.add(channel);// 将玩家添加进指定定频道
		return channels.size(); // 返回当前频道大小
	}

	@Override
	public int removeFromGroup(int identify, Channel channel) {
		// 获取当前玩家所在所在的世界频道
		ChannelGroup channels = getChannels(identify);
		if (!channels.contains(channel)) {
			logger.error("世界频道[{}]中不存在Channel[{}]", identify, channel);
			return -1;
		}
		channels.remove(channel);
		return 0;
	}

	@Override
	public int getOnlineNum() {
		int count = 0;
		Iterator<Entry<Integer, ChannelGroup>> it = worldChannel.entrySet().iterator();
		while (it.hasNext()) {// 迭代所有频道
			Entry<Integer, ChannelGroup> entry = (Entry<Integer, ChannelGroup>) it.next();
			count += entry.getValue().size();
		}
		return count;
	}

	@Override
	public int changeChannel(int old_identify, int new_identify, Channel channel) {
		System.out.println("暂不支持切换世界频道");
		return -1;
	}

	@Override
	public ChannelGroup getChannels(int identify) {
		if (!worldChannel.containsKey(identify)) {
			logger.error("不存在世界频道[{}]", identify);
			return null;
		}
		return worldChannel.get(identify);
	}

}
