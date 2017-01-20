package com.qingcity.chat.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 公会频道
 * 
 * @author leehotin
 *
 */
public class SocietyGroup implements Groups {
	private static final Logger logger = LoggerFactory.getLogger(SocietyGroup.class);

	public static ChannelGroup societyChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 世界频道
	public static Map<Integer, ChannelGroup> societyChannel = new LinkedHashMap<>();

	static {
		// 查询所有工会id,此处假定有两个公会，id分别为1和2
		List<Integer> societyList = new ArrayList<>();
		societyList.add(1);
		societyList.add(2);
		// 迭代公会id
		for (Integer societyId : societyList) {
			societyChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);// 公会频道
			societyChannel.put(societyId, societyChannels);
		}

	}

	@Override
	public ChannelGroup getChannels(int societyId) {
		if (!societyChannel.containsKey(societyId)) {
			logger.error("不存在 [{}] 工会", societyId);
			return null;
		}
		return societyChannel.get(societyId);
	}

	@Override
	public int addToGroup(int societyId, Channel channel) {
		ChannelGroup channels = getChannels(societyId);// 取出指定频道内的玩家channel列表
		if (channels == null) {
			logger.error("公会[{}]不存在,无法进入公会频道", societyId);
			return -1;
		}
		channels.add(channel);// 将玩家添加进指定定频道
		return channels.size(); // 返回当前频道大小
	}

	@Override
	public int removeFromGroup(int societyId, Channel channel) {
		// 获取当前玩家所在所在的世界频道
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
		removeFromGroup(old_societyId, channel);
		// 将玩家移入新的公会频道
		addToGroup(new_societyId, channel);
		return 1;
	}
}
