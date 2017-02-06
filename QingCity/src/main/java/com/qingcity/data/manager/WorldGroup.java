package com.qingcity.data.manager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.data.manager.Groups;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * @author leehotin
 * @Date 2017年2月5日 下午1:22:01
 * @Description 世界频道
 */
public class WorldGroup implements Groups {
	private static final Logger logger = LoggerFactory.getLogger(WorldGroup.class);

	private static WorldGroup instance = new WorldGroup();

	public static WorldGroup getInstance() {
		return instance;
	}

	private ChannelGroup worldChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 世界频道
	private Map<Integer, ChannelGroup> worldChannel = new LinkedHashMap<>();

	@Override
	public int addToGroup(int identify, Channel channel) {
		ChannelGroup channels = getChannels(identify);// 取出指定频道内的玩家channel列表
		if (channels == null) {
			worldChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
			worldChannels.add(channel);
			worldChannel.put(identify, worldChannels);
		} else {
			channels.add(channel);// 将玩家添加进指定频道
		}
		return worldChannel.get(identify).size(); // 返回当前频道大小
	}

	@Override
	public int removeFromGroup(Channel channel) {
		// 获取当前玩家所在所在的世界频道
		int worldId = getIdByChannel(channel);
		ChannelGroup channels = getChannels(worldId);
		if (!channels.contains(channel)) {
			logger.error("世界频道[{}]中不存在Channel[{}]", worldId, channel);
			return -1;
		}
		channels.remove(channel);
		return 0;
	}

	public Integer getIdByChannel(Channel channel) {
		int wroldId = 0;
		for (Map.Entry<Integer, ChannelGroup> wChannel : worldChannel.entrySet()) {
			if (wChannel.getValue().contains(channel)) {
				wroldId = wChannel.getKey();
			}
		}
		return wroldId;
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
