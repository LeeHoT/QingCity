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
 * @Date 2017年2月5日 下午1:22:21
 * @Description 公会频道
 */
public class SocietyGroup implements Groups {
	private static final Logger logger = LoggerFactory.getLogger(SocietyGroup.class);

	private static SocietyGroup instance = new SocietyGroup();

	public static SocietyGroup getInstance() {
		return instance;
	}

	private ChannelGroup societyChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 公会频道
	private Map<Integer, ChannelGroup> societyChannel = new LinkedHashMap<>();

	@Override
	public synchronized ChannelGroup getChannels(int societyId) {
		if (!societyChannel.containsKey(societyId)) {
			logger.error("不存在 [{}] 工会频道", societyId);
			return null;
		}
		return societyChannel.get(societyId);
	}

	public Integer getIdByChannel(Channel channel) {
		int societyId = 0;
		for (Map.Entry<Integer, ChannelGroup> sChannel : societyChannel.entrySet()) {
			if (sChannel.getValue().contains(channel)) {
				societyId = sChannel.getKey();
			}
		}
		return societyId;
	}

	@Override
	public int addToGroup(int societyId, Channel channel) {
		ChannelGroup channels = getChannels(societyId);// 取出指定频道内的玩家channel列表
		if (channels == null) {
			societyChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 公会频道
			societyChannels.add(channel);
			societyChannel.put(societyId, societyChannels);
		} else {
			channels.add(channel);// 将玩家添加进指定定频道
		}
		return societyChannel.get(societyId).size(); // 返回当前频道大小
	}

	@Override
	public int removeFromGroup(Channel channel) {
		// 获取当前玩家所在所在的公会频道
		int societyId = getIdByChannel(channel);
		ChannelGroup channels = getChannels(societyId);
		if (!channels.contains(channel)) {
			logger.error("公会[{}]中不存在Channel[{}]", societyId, channel);
			return -1;
		}
		channels.remove(channel);// 存在则移除
		return 1;
	}

	@Override
	public int getOnlineNum() {
		int count = 0;
		Iterator<Entry<Integer, ChannelGroup>> it = societyChannel.entrySet().iterator();
		while (it.hasNext()) {// 迭代所有频道
			Entry<Integer, ChannelGroup> entry = (Entry<Integer, ChannelGroup>) it.next();
			count += entry.getValue().size();
		}
		return count;
	}

	@Override
	public int changeChannel(int old_societyId, int new_societyId, Channel channel) {
		// 将玩家从原始公会频道移除
		removeFromGroup(channel);
		// 将玩家移入新的公会频道
		addToGroup(new_societyId, channel);
		return 1;
	}
}
